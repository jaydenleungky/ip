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

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                break;
            }
            System.out.println(LINE);
            System.out.println(input);
            System.out.println(LINE);
        }
        scanner.close();

        System.out.println(LINE);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(LINE);
    }
}
