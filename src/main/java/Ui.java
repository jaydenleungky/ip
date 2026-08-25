import java.util.Scanner;

/**
 * Handles all interaction with the user: reading input and printing output.
 */
public class Ui {
    private static final String LINE =
            "____________________________________________________________";
    private static final String BANNER = "  ____ ___   ____ ___  \n"
            + " / ___/ _ \\ / ___/ _ \\ \n"
            + "| |  | | | | |  | | | |\n"
            + "| |__| |_| | |__| |_| |\n"
            + " \\____\\___/ \\____\\___/ \n";

    private final Scanner scanner = new Scanner(System.in);

    public void showWelcome() {
        System.out.println(LINE);
        System.out.println(BANNER);
        System.out.println("Hello! I'm Coco.");
        System.out.println("What can I do for you?");
        System.out.println(LINE);
    }

    public void showGoodbye() {
        System.out.println(LINE);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(LINE);
    }

    public void showLine() {
        System.out.println(LINE);
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void close() {
        scanner.close();
    }

    public void showError(String message) {
        System.out.println(message);
    }

    public void showTaskAdded(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    public void showTaskRemoved(Task task, int taskCount) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    public void showTaskMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
    }

    public void showTaskUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
    }

    public void showTaskList(TaskList tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }
}
