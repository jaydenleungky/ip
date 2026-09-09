package coco;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads tasks from, and saves tasks to, a data file on disk so the task
 * list survives across runs of the chatbot.
 */
public class Storage {
    private final Path filePath;

    /**
     * Creates a Storage that reads from and writes to the given file path.
     *
     * @param filePath Path (relative or absolute) to the data file.
     */
    public Storage(String filePath) {
        this.filePath = Paths.get(filePath);
    }

    /**
     * Loads tasks from the data file. If the file (or its parent folder)
     * does not exist yet, e.g. on first run, an empty list is returned
     * instead of failing.
     *
     * @return The loaded tasks, in save-file order.
     */
    public List<Task> load() {
        List<Task> tasks = new ArrayList<>();
        if (!Files.exists(filePath)) {
            return tasks;
        }
        try {
            for (String line : Files.readAllLines(filePath)) {
                if (line.isBlank()) {
                    continue;
                }
                Task task = parseLine(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            System.out.println("Sorry, I couldn't read your saved tasks ("
                    + e.getMessage() + "). Starting with an empty list.");
        }
        return tasks;
    }

    /**
     * Writes the given tasks to the data file, creating the parent folder
     * first if it doesn't exist yet.
     *
     * @param tasks Tasks to save, in the order they should be written.
     */
    public void save(List<Task> tasks) {
        try {
            Path parent = filePath.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
            List<String> lines = tasks.stream().map(Task::toSaveFormat).toList();
            Files.write(filePath, lines);
        } catch (IOException e) {
            System.out.println("Sorry, I couldn't save your tasks (" + e.getMessage() + ").");
        }
    }

    /**
     * Parses one saved line back into a Task.
     *
     * @param line One line from the data file, e.g. "T | 1 | read book".
     * @return The parsed Task, or null if the line is malformed (e.g. from
     *         manual editing of the data file) and should be skipped.
     */
    private static Task parseLine(String line) {
        String[] parts = line.split("\\s*\\|\\s*");
        if (parts.length < 3) {
            return null;
        }
        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task;
        switch (type) {
        case "T":
            task = new Todo(description);
            break;
        case "D":
            if (parts.length < 4) {
                return null;
            }
            try {
                task = new Deadline(description, LocalDate.parse(parts[3]));
            } catch (DateTimeParseException e) {
                return null;
            }
            break;
        case "E":
            if (parts.length < 5) {
                return null;
            }
            task = new Event(description, parts[3], parts[4]);
            break;
        default:
            return null;
        }

        if (isDone) {
            task.markAsDone();
        }
        return task;
    }
}
