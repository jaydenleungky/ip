package coco;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Makes sense of raw user input: identifies the command and parses it into
 * the corresponding Task or task-index argument.
 */
public class Parser {
    /**
     * Identifies which command the given input line is asking for.
     *
     * @param input Raw user input line.
     * @return The matching Command, or Command.UNKNOWN if none match.
     */
    public static Command parseCommand(String input) {
        return Command.fromInput(input);
    }

    /**
     * Parses a "todo" command into a Todo task.
     *
     * @param input Raw user input line, e.g. "todo read book".
     * @return The parsed Todo.
     * @throws CocoException If the description is empty.
     */
    public static Task parseTodo(String input) throws CocoException {
        String description = argumentsOf(input, Command.TODO).trim();
        if (description.isEmpty()) {
            throw new CocoException("Sorry, todo description cannot be empty!");
        }
        return new Todo(description);
    }

    /**
     * Parses a "deadline" command into a Deadline task.
     *
     * @param input Raw user input line, e.g.
     *              "deadline return book /by 2019-10-15".
     * @return The parsed Deadline.
     * @throws CocoException If the '/by' marker is missing, the description
     *                       or date is empty, or the date isn't a valid
     *                       yyyy-mm-dd date.
     */
    public static Task parseDeadline(String input) throws CocoException {
        String rest = argumentsOf(input, Command.DEADLINE);
        int byIndex = rest.indexOf("/by");
        if (byIndex == -1) {
            throw new CocoException(
                    "Sorry, a deadline needs a '/by' date! Try: deadline "
                            + "<description> /by <date>");
        }
        String description = rest.substring(0, byIndex).trim();
        String byText = rest.substring(byIndex + "/by".length()).trim();
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

    /**
     * Parses an "event" command into an Event task.
     *
     * @param input Raw user input line, e.g.
     *              "event meeting /from Mon 2pm /to 4pm".
     * @return The parsed Event.
     * @throws CocoException If the '/from' or '/to' marker is missing (or
     *                       out of order), or the description, start, or
     *                       end time is empty.
     */
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
        String from = rest.substring(fromIndex + "/from".length(), toIndex).trim();
        String to = rest.substring(toIndex + "/to".length()).trim();
        if (description.isEmpty()) {
            throw new CocoException("Sorry, event description cannot be empty!");
        }
        if (from.isEmpty() || to.isEmpty()) {
            throw new CocoException("Sorry, an event needs both a start and end time!");
        }
        return new Event(description, from, to);
    }

    /**
     * Parses a "find" command into the keyword to search for.
     *
     * @param input Raw user input line, e.g. "find book".
     * @return The trimmed keyword.
     * @throws CocoException If the keyword is empty.
     */
    public static String parseFind(String input) throws CocoException {
        String keyword = argumentsOf(input, Command.FIND).trim();
        if (keyword.isEmpty()) {
            throw new CocoException("Sorry, tell me what to find!");
        }
        return keyword;
    }

    /**
     * Parses the task number out of a "mark"/"unmark"/"delete" command and
     * converts it to a zero-based index.
     *
     * @param input Raw user input line, e.g. "mark 2".
     * @param commandWord The command word the input starts with, e.g. "mark".
     * @param taskCount Current number of tasks, used to validate range.
     * @return Zero-based index of the referenced task.
     * @throws CocoException If the task number is missing, not a number, or
     *                       out of range.
     */
    public static int parseIndex(String input, String commandWord, int taskCount)
            throws CocoException {
        String arg = input.length() > commandWord.length()
                ? input.substring(commandWord.length()).trim()
                : "";
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

    /**
     * Strips the leading command word off the input, returning whatever
     * follows it (or an empty string if there's nothing after it).
     *
     * @param input Raw user input line.
     * @param command Command whose keyword should be stripped.
     * @return The remainder of the input after the command word.
     */
    private static String argumentsOf(String input, Command command) {
        String keyword = command.name().toLowerCase();
        return input.equals(keyword) ? "" : input.substring(keyword.length() + 1);
    }
}
