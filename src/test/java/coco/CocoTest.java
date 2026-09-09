package coco;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class CocoTest {

    @TempDir
    Path tempDir;

    private Coco newCoco() {
        return new Coco(tempDir.resolve("coco.txt").toString());
    }

    @Test
    public void greet_newInstance_returnsWelcomeMessage() {
        assertEquals("Hello! I'm Coco.\nWhat can I do for you?", newCoco().greet());
    }

    @Test
    public void getResponse_addTodoThenList_reportsAdditionThenListsIt() {
        Coco coco = newCoco();

        String addReply = coco.getResponse("todo read book");
        assertTrue(addReply.contains("read book"));
        assertTrue(addReply.contains("Now you have 1 tasks in the list."));

        String listReply = coco.getResponse("list");
        assertEquals("Here are the tasks in your list:\n1.[T][ ] read book", listReply);
    }

    @Test
    public void getResponse_markThenUnmark_flipsDoneStatusInReply() {
        Coco coco = newCoco();
        coco.getResponse("todo read book");

        String markReply = coco.getResponse("mark 1");
        assertTrue(markReply.contains("[X]"));

        String unmarkReply = coco.getResponse("unmark 1");
        assertTrue(unmarkReply.contains("[ ]"));
    }

    @Test
    public void getResponse_deleteOnlyTask_leavesEmptyList() {
        Coco coco = newCoco();
        coco.getResponse("todo read book");

        String deleteReply = coco.getResponse("delete 1");
        assertTrue(deleteReply.contains("Now you have 0 tasks in the list."));
        assertEquals("Here are the tasks in your list:", coco.getResponse("list"));
    }

    @Test
    public void getResponse_markRecurringDeadline_advancesToNextOccurrenceInsteadOfMarkingDone() {
        Coco coco = newCoco();
        coco.getResponse("deadline standup /by 2026-01-06 /every weekly");

        String firstMarkReply = coco.getResponse("mark 1");
        assertTrue(firstMarkReply.contains("recurring"));
        assertTrue(firstMarkReply.contains("Jan 13 2026"));
        assertTrue(firstMarkReply.contains("[ ]"));

        String secondMarkReply = coco.getResponse("mark 1");
        assertTrue(secondMarkReply.contains("Jan 20 2026"));
        assertTrue(secondMarkReply.contains("[ ]"));

        assertEquals("Here are the tasks in your list:\n1.[D][ ] standup (by: Jan 20 2026) (every: weekly)",
                coco.getResponse("list"));
    }

    @Test
    public void getResponse_markNonRecurringDeadline_marksDoneNormally() {
        Coco coco = newCoco();
        coco.getResponse("deadline return book /by 2026-01-06");

        String markReply = coco.getResponse("mark 1");

        assertTrue(markReply.contains("marked this task as done"));
        assertTrue(markReply.contains("[X]"));
    }

    @Test
    public void getResponse_findMatchingKeyword_returnsOnlyMatchingTasks() {
        Coco coco = newCoco();
        coco.getResponse("todo read book");
        coco.getResponse("todo buy groceries");

        String findReply = coco.getResponse("find book");
        assertEquals("Here are the matching tasks in your list:\n1.[T][ ] read book", findReply);
    }

    @Test
    public void getResponse_invalidCommand_returnsErrorMessageInsteadOfThrowing() {
        assertEquals("Boy, what that mean?", newCoco().getResponse("blah"));
    }

    @Test
    public void getResponse_bye_returnsGoodbyeMessage() {
        assertEquals("Bye. Hope to see you again soon!", newCoco().getResponse("bye"));
    }
}
