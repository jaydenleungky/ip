import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Makes sense of raw user input: identifies the command and parses it into
 * the corresponding Task or task-index argument.
 */
public class Parser {
    public static Command parseCommand(String input) {
        return Command.fromInput(input);
    }

    public static Task parseTodo(String input) throws CocoException {
        String description = argumentsOf(input, Command.TODO).trim();
        if (description.isEmpty()) {
            throw new CocoException("Sorry, todo description cannot be empty!");
        }
        return new Todo(description);
    }

    public static Task parseDeadline(String input) throws CocoException {
        String rest = argumentsOf(input, Command.DEADLINE);
        int byIndex = rest.indexOf("/by");
        if (byIndex == -1) {
            throw new CocoException(
                    "Sorry, a deadline needs a '/by' date! Try: deadline "
                            + "<description> /by <date>");
        }
        String description = rest.substring(0, byIndex).trim();
        String byText = rest.substring(byIndex + 3).trim();
        if (description.isEmpty()) {
            throw new CocoException("Sorry, deadline description cannot be empty!");
        }
        if (byText.isEmpty()) {
            throw new CocoException("Sorry, the date for a deadline cannot be empty!");
        }
        LocalDate by;
        try {
            by = LocalDate.parse(byText);
        } catch (DateTimeParseException e) {
            throw new CocoException("Sorry, '" + byText
                    + "' is not a valid date! Please use yyyy-mm-dd, e.g. 2019-10-15.");
        }
        return new Deadline(description, by);
    }

    public static Task parseEvent(String input) throws CocoException {
        String rest = argumentsOf(input, Command.EVENT);
        int fromIndex = rest.indexOf("/from");
        int toIndex = rest.indexOf("/to");
        if (fromIndex == -1 || toIndex == -1 || toIndex < fromIndex) {
            throw new CocoException(
                    "Sorry, an event needs '/from' and '/to'! Try: event "
                            + "<description> /from <start> /to <end>");
        }
        String description = rest.substring(0, fromIndex).trim();
        String from = rest.substring(fromIndex + 5, toIndex).trim();
        String to = rest.substring(toIndex + 3).trim();
        if (description.isEmpty()) {
            throw new CocoException("Sorry, event description cannot be empty!");
        }
        if (from.isEmpty() || to.isEmpty()) {
            throw new CocoException("Sorry, an event needs both a start and end time!");
        }
        return new Event(description, from, to);
    }

    public static int parseIndex(String input, String commandWord, int taskCount)
            throws CocoException {
        String arg = input.length() > commandWord.length()
                ? input.substring(commandWord.length()).trim() : "";
        if (arg.isEmpty()) {
            throw new CocoException("Sorry, tell me which task number to " + commandWord + "!");
        }
        int index;
        try {
            index = Integer.parseInt(arg) - 1;
        } catch (NumberFormatException e) {
            throw new CocoException("Sorry, '" + arg + "' is not a valid task number!");
        }
        if (index < 0 || index >= taskCount) {
            throw new CocoException("Sorry, there is no task number " + (index + 1) + "!");
        }
        return index;
    }

    private static String argumentsOf(String input, Command command) {
        String keyword = command.name().toLowerCase();
        return input.equals(keyword) ? "" : input.substring(keyword.length() + 1);
    }
}
