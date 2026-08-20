import java.util.Scanner;

public class Coco {
    private static final String LINE =
            "____________________________________________________________";
    private static final int MAX_TASKS = 100;

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

        Task[] tasks = new Task[MAX_TASKS];
        int taskCount = 0;

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                break;
            }

            System.out.println(LINE);
            if (input.equals("list")) {
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + "." + tasks[i]);
                }
            } else if (input.startsWith("mark ")) {
                int index = Integer.parseInt(input.substring(5).trim()) - 1;
                tasks[index].markAsDone();
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("  " + tasks[index]);
            } else if (input.startsWith("unmark ")) {
                int index = Integer.parseInt(input.substring(7).trim()) - 1;
                tasks[index].markAsNotDone();
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("  " + tasks[index]);
            } else if (input.startsWith("todo ")) {
                String description = input.substring(5).trim();
                taskCount = addTask(tasks, taskCount, new Todo(description));
            } else if (input.startsWith("deadline ")) {
                String rest = input.substring(9);
                int byIndex = rest.indexOf("/by ");
                String description = rest.substring(0, byIndex).trim();
                String by = rest.substring(byIndex + 4).trim();
                taskCount = addTask(tasks, taskCount, new Deadline(description, by));
            } else if (input.startsWith("event ")) {
                String rest = input.substring(6);
                int fromIndex = rest.indexOf("/from ");
                int toIndex = rest.indexOf("/to ");
                String description = rest.substring(0, fromIndex).trim();
                String from = rest.substring(fromIndex + 6, toIndex).trim();
                String to = rest.substring(toIndex + 4).trim();
                taskCount = addTask(tasks, taskCount, new Event(description, from, to));
            } else {
                taskCount = addTask(tasks, taskCount, new Task(input));
            }
            System.out.println(LINE);
        }
        scanner.close();

        System.out.println(LINE);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(LINE);
    }

    private static int addTask(Task[] tasks, int taskCount, Task task) {
        tasks[taskCount] = task;
        taskCount++;
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        return taskCount;
    }
}
