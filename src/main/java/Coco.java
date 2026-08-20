import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Coco {
    private static final String LINE =
            "____________________________________________________________";

    public static void main(String[] args) {
        String banner = "  ____ ___   ____ ___  \n"
                + " / ___/ _ \\ / ___/ _ \\ \n"
                + "| |  | | | | |  | | | |\n"
                + "| |__| |_| | |__| |_| |\n"
                + " \\____\\___/ \\____\\___/ \n";

        System.out.println(LINE);
        System.out.println(banner);
        System.out.println("Hello! I'm Coco.");
        System.out.println("What can I do for you?");
        System.out.println(LINE);

        List<Task> tasks = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                break;
            }

            System.out.println(LINE);
            try {
                if (input.equals("list")) {
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + "." + tasks.get(i));
                    }
                } else if (input.equals("mark") || input.startsWith("mark ")) {
                    int index = parseTaskIndex(input, "mark", tasks.size());
                    tasks.get(index).markAsDone();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("  " + tasks.get(index));
                } else if (input.equals("unmark") || input.startsWith("unmark ")) {
                    int index = parseTaskIndex(input, "unmark", tasks.size());
                    tasks.get(index).markAsNotDone();
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println("  " + tasks.get(index));
                } else if (input.equals("delete") || input.startsWith("delete ")) {
                    int index = parseTaskIndex(input, "delete", tasks.size());
                    Task removed = tasks.remove(index);
                    System.out.println("Noted. I've removed this task:");
                    System.out.println("  " + removed);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                } else if (input.equals("todo") || input.startsWith("todo ")) {
                    String description = input.equals("todo") ? "" : input.substring(5).trim();
                    if (description.isEmpty()) {
                        throw new CocoException("Sorry, todo description cannot be empty!");
                    }
                    addTask(tasks, new Todo(description));
                } else if (input.equals("deadline") || input.startsWith("deadline ")) {
                    String rest = input.equals("deadline") ? "" : input.substring(9);
                    int byIndex = rest.indexOf("/by");
                    if (byIndex == -1) {
                        throw new CocoException(
                                "Sorry, a deadline needs a '/by' date! Try: deadline "
                                        + "<description> /by <date>");
                    }
                    String description = rest.substring(0, byIndex).trim();
                    String by = rest.substring(byIndex + 3).trim();
                    if (description.isEmpty()) {
                        throw new CocoException("Sorry, deadline description cannot be empty!");
                    }
                    if (by.isEmpty()) {
                        throw new CocoException("Sorry, the date for a deadline cannot be empty!");
                    }
                    addTask(tasks, new Deadline(description, by));
                } else if (input.equals("event") || input.startsWith("event ")) {
                    String rest = input.equals("event") ? "" : input.substring(6);
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
                        throw new CocoException(
                                "Sorry, an event needs both a start and end time!");
                    }
                    addTask(tasks, new Event(description, from, to));
                } else {
                    throw new CocoException("Boy, what that mean?");
                }
            } catch (CocoException e) {
                System.out.println(e.getMessage());
            }
            System.out.println(LINE);
        }
        scanner.close();

        System.out.println(LINE);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(LINE);
    }

    private static int parseTaskIndex(String input, String command, int taskCount)
            throws CocoException {
        String arg = input.length() > command.length() ? input.substring(command.length()).trim() : "";
        if (arg.isEmpty()) {
            throw new CocoException("Sorry, tell me which task number to " + command + "!");
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

    private static void addTask(List<Task> tasks, Task task) {
        tasks.add(task);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
    }
}
