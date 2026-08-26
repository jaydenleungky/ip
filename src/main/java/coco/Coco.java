package coco;

/**
 * Entry point and main loop of the Coco chatbot. Wires together a Ui,
 * Storage, and TaskList, and dispatches each parsed command to them.
 */
public class Coco {
    private final Storage storage;
    private final Ui ui;
    private final TaskList tasks;

    /**
     * Creates a Coco instance backed by the data file at the given path,
     * loading any tasks already saved there.
     *
     * @param filePath Path to the data file used to save/load tasks.
     */
    public Coco(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList(storage.load());
    }

    /** Runs the chatbot's read-parse-execute loop until the user says "bye". */
    public void run() {
        ui.showWelcome();
        while (true) {
            String input = ui.readCommand();
            Command command = Parser.parseCommand(input);
            if (command == Command.BYE) {
                break;
            }

            ui.showLine();
            try {
                switch (command) {
                case LIST:
                    ui.showTaskList(tasks);
                    break;
                case MARK: {
                    int index = Parser.parseIndex(input, "mark", tasks.size());
                    tasks.get(index).markAsDone();
                    storage.save(tasks.asList());
                    ui.showTaskMarked(tasks.get(index));
                    break;
                }
                case UNMARK: {
                    int index = Parser.parseIndex(input, "unmark", tasks.size());
                    tasks.get(index).markAsNotDone();
                    storage.save(tasks.asList());
                    ui.showTaskUnmarked(tasks.get(index));
                    break;
                }
                case DELETE: {
                    int index = Parser.parseIndex(input, "delete", tasks.size());
                    Task removed = tasks.remove(index);
                    storage.save(tasks.asList());
                    ui.showTaskRemoved(removed, tasks.size());
                    break;
                }
                case TODO:
                    addTask(Parser.parseTodo(input));
                    break;
                case DEADLINE:
                    addTask(Parser.parseDeadline(input));
                    break;
                case EVENT:
                    addTask(Parser.parseEvent(input));
                    break;
                case FIND:
                    ui.showFindResults(tasks.find(Parser.parseFind(input)));
                    break;
                default:
                    throw new CocoException("Boy, what that mean?");
                }
            } catch (CocoException e) {
                ui.showError(e.getMessage());
            }
            ui.showLine();
        }
        ui.close();
        ui.showGoodbye();
    }

    /**
     * Adds a task to the list, persists the updated list, and reports the
     * addition to the user.
     *
     * @param task Task to add.
     */
    private void addTask(Task task) {
        tasks.add(task);
        storage.save(tasks.asList());
        ui.showTaskAdded(task, tasks.size());
    }

    /**
     * Starts the chatbot, saving/loading tasks from ./data/coco.txt.
     *
     * @param args Not used.
     */
    public static void main(String[] args) {
        new Coco("data/coco.txt").run();
    }
}
