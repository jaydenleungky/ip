package coco;

import java.util.ArrayList;
import java.util.List;

/**
 * Wraps the in-memory list of tasks and the operations to add/remove/access them.
 */
public class TaskList {
    private final List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task remove(int index) {
        return tasks.remove(index);
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public int size() {
        return tasks.size();
    }

    /**
     * Returns the underlying list, e.g. so Storage can save it to disk.
     */
    public List<Task> asList() {
        return tasks;
    }
}
