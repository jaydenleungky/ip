package coco;

import java.util.List;
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

    /** Prints the banner, greeting, and prompt shown at startup. */
    public void showWelcome() {
        System.out.println(LINE);
        System.out.println(BANNER);
        System.out.println("Hello! I'm Coco.");
        System.out.println("What can I do for you?");
        System.out.println(LINE);
    }

    /** Prints the goodbye message shown when the user exits. */
    public void showGoodbye() {
        System.out.println(LINE);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(LINE);
    }

    /** Prints a divider line, used to separate one response from the next. */
    public void showLine() {
        System.out.println(LINE);
    }

    /**
     * Reads one line of input typed by the user.
     *
     * @return The raw input line.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /** Releases the input scanner's resources. */
    public void close() {
        scanner.close();
    }

    /**
     * Prints an error message to the user.
     *
     * @param message Message to show.
     */
    public void showError(String message) {
        System.out.println(message);
    }

    /**
     * Prints confirmation that a task was added.
     *
     * @param task Task that was added.
     * @param taskCount Total number of tasks after adding it.
     */
    public void showTaskAdded(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    /**
     * Prints confirmation that a task was removed.
     *
     * @param task Task that was removed.
     * @param taskCount Total number of tasks after removing it.
     */
    public void showTaskRemoved(Task task, int taskCount) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    /**
     * Prints confirmation that a task was marked as done.
     *
     * @param task Task that was marked.
     */
    public void showTaskMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
    }

    /**
     * Prints confirmation that a task was marked as not done.
     *
     * @param task Task that was unmarked.
     */
    public void showTaskUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
    }

    /**
     * Prints every task in the list, numbered from 1.
     *
     * @param tasks Tasks to print.
     */
    public void showTaskList(TaskList tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    /**
     * Prints every matching task from a "find" search, numbered from 1.
     *
     * @param matches Matching tasks to print.
     */
    public void showFindResults(List<Task> matches) {
        System.out.println("Here are the matching tasks in your list:");
        for (int i = 0; i < matches.size(); i++) {
            System.out.println((i + 1) + "." + matches.get(i));
        }
    }
}
