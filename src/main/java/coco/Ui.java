package coco;

import java.util.List;
import java.util.Scanner;

/**
 * Handles all interaction with the user: reading input and printing output.
 * Each show* method prints to the console; the corresponding *Message
 * method it delegates to builds the same text without printing it, so a
 * GUI can reuse the exact wording instead of duplicating it.
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
        System.out.println(taskAddedMessage(task, taskCount));
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
     * Prints confirmation that a task was removed.
     *
     * @param task Task that was removed.
     * @param taskCount Total number of tasks after removing it.
     */
    public void showTaskRemoved(Task task, int taskCount) {
        System.out.println(taskRemovedMessage(task, taskCount));
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
     * Prints confirmation that a task was marked as done.
     *
     * @param task Task that was marked.
     */
    public void showTaskMarked(Task task) {
        System.out.println(taskMarkedMessage(task));
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
     * Prints confirmation that a task was marked as not done.
     *
     * @param task Task that was unmarked.
     */
    public void showTaskUnmarked(Task task) {
        System.out.println(taskUnmarkedMessage(task));
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
     * Prints every task in the list, numbered from 1.
     *
     * @param tasks Tasks to print.
     */
    public void showTaskList(TaskList tasks) {
        System.out.println(taskListMessage(tasks));
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
     * Prints every matching task from a "find" search, numbered from 1.
     *
     * @param matches Matching tasks to print.
     */
    public void showFindResults(List<Task> matches) {
        System.out.println(findResultsMessage(matches));
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
