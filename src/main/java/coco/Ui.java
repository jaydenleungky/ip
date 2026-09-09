package coco;

import java.util.List;
import java.util.Scanner;

/**
 * Handles all interaction with the user: reading input and printing output.
 * Building each message is split into its own *Message method returning the
 * text, separate from printing it, so the GUI can reuse the exact wording
 * (via Coco.getResponse) instead of a second, driftable copy.
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
        System.out.println(greetingMessage());
        System.out.println(LINE);
    }

    /** Returns the greeting shown at startup (without the console banner). */
    public String greetingMessage() {
        return "Hello! I'm Coco.\nWhat can I do for you?";
    }

    /** Prints the goodbye message shown when the user exits. */
    public void showGoodbye() {
        System.out.println(LINE);
        System.out.println(goodbyeMessage());
        System.out.println(LINE);
    }

    /** Returns the goodbye message shown when the user exits. */
    public String goodbyeMessage() {
        return "Bye. Hope to see you again soon!";
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
     * Returns confirmation text that a task was added.
     *
     * @param task Task that was added.
     * @param taskCount Total number of tasks after adding it.
     * @return The confirmation message.
     */
    public String taskAddedMessage(Task task, int taskCount) {
        return "Got it. I've added this task:\n  " + task
                + "\nNow you have " + taskCount + " tasks in the list.";
    }

    /**
     * Returns confirmation text that a task was removed.
     *
     * @param task Task that was removed.
     * @param taskCount Total number of tasks after removing it.
     * @return The confirmation message.
     */
    public String taskRemovedMessage(Task task, int taskCount) {
        return "Noted. I've removed this task:\n  " + task
                + "\nNow you have " + taskCount + " tasks in the list.";
    }

    /**
     * Returns confirmation text that a task was marked as done.
     *
     * @param task Task that was marked.
     * @return The confirmation message.
     */
    public String taskMarkedMessage(Task task) {
        return "Nice! I've marked this task as done:\n  " + task;
    }

    /**
     * Returns confirmation text that a task was marked as not done.
     *
     * @param task Task that was unmarked.
     * @return The confirmation message.
     */
    public String taskUnmarkedMessage(Task task) {
        return "OK, I've marked this task as not done yet:\n  " + task;
    }

    /**
     * Returns confirmation text that a recurring deadline was advanced to
     * its next occurrence instead of being marked done, since it doesn't
     * really finish.
     *
     * @param deadline The recurring deadline that was advanced.
     * @return The confirmation message.
     */
    public String taskRecurredMessage(Deadline deadline) {
        return "This is a recurring task, so I've moved it to its next "
                + "occurrence instead of marking it done:\n  " + deadline;
    }

    /**
     * Returns every task in the list, numbered from 1, as one block of text.
     *
     * @param tasks Tasks to list.
     * @return The formatted list.
     */
    public String taskListMessage(TaskList tasks) {
        StringBuilder message = new StringBuilder("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            message.append("\n").append(i + 1).append(".").append(tasks.get(i));
        }
        return message.toString();
    }

    /**
     * Returns every matching task from a "find" search, numbered from 1, as
     * one block of text.
     *
     * @param matches Matching tasks.
     * @return The formatted list.
     */
    public String findResultsMessage(List<Task> matches) {
        StringBuilder message = new StringBuilder("Here are the matching tasks in your list:");
        for (int i = 0; i < matches.size(); i++) {
            message.append("\n").append(i + 1).append(".").append(matches.get(i));
        }
        return message.toString();
    }
}
