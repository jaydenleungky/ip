package coco;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ParserTest {

    @Test
    public void parseTodo_validDescription_returnsTodoWithDescription() throws CocoException {
        Task task = Parser.parseTodo("todo read book");
        assertEquals("[T][ ] read book", task.toString());
    }

    @Test
    public void parseTodo_emptyDescription_exceptionThrown() {
        CocoException e = assertThrows(CocoException.class, () -> Parser.parseTodo("todo"));
        assertEquals("Sorry, todo description cannot be empty!", e.getMessage());
    }

    @Test
    public void parseTodo_blankDescription_exceptionThrown() {
        assertThrows(CocoException.class, () -> Parser.parseTodo("todo    "));
    }

    @Test
    public void parseDeadline_validDate_returnsDeadlineWithParsedAndFormattedDate() throws CocoException {
        Task task = Parser.parseDeadline("deadline return book /by 2019-10-15");
        assertEquals("[D][ ] return book (by: Oct 15 2019)", task.toString());
    }

    @Test
    public void parseDeadline_missingByMarker_exceptionThrown() {
        CocoException e = assertThrows(CocoException.class,
                () -> Parser.parseDeadline("deadline return book"));
        assertTrue(e.getMessage().contains("/by"));
    }

    @Test
    public void parseDeadline_emptyDescription_exceptionThrown() {
        assertThrows(CocoException.class, () -> Parser.parseDeadline("deadline /by 2019-10-15"));
    }

    @Test
    public void parseDeadline_emptyDate_exceptionThrown() {
        CocoException e = assertThrows(CocoException.class,
                () -> Parser.parseDeadline("deadline return book /by"));
        assertEquals("Sorry, the date for a deadline cannot be empty!", e.getMessage());
    }

    @Test
    public void parseDeadline_invalidDateFormat_exceptionThrown() {
        CocoException e = assertThrows(CocoException.class,
                () -> Parser.parseDeadline("deadline return book /by tomorrow"));
        assertTrue(e.getMessage().contains("not a valid date"));
    }

    @Test
    public void parseEvent_validFromAndTo_returnsEventWithBothFields() throws CocoException {
        Task task = Parser.parseEvent("event meeting /from Mon 2pm /to 4pm");
        assertEquals("[E][ ] meeting (from: Mon 2pm to: 4pm)", task.toString());
    }

    @Test
    public void parseEvent_missingFromAndTo_exceptionThrown() {
        assertThrows(CocoException.class, () -> Parser.parseEvent("event meeting"));
    }

    @Test
    public void parseEvent_toMarkerBeforeFromMarker_exceptionThrown() {
        assertThrows(CocoException.class,
                () -> Parser.parseEvent("event meeting /to 4pm /from Mon 2pm"));
    }

    @Test
    public void parseEvent_emptyFromValue_exceptionThrown() {
        assertThrows(CocoException.class,
                () -> Parser.parseEvent("event meeting /from /to 4pm"));
    }

    @Test
    public void parseIndex_validNumber_returnsZeroBasedIndex() throws CocoException {
        assertEquals(2, Parser.parseIndex("mark 3", "mark", 5));
    }

    @Test
    public void parseIndex_missingArgument_exceptionThrown() {
        CocoException e = assertThrows(CocoException.class,
                () -> Parser.parseIndex("mark", "mark", 5));
        assertEquals("Sorry, tell me which task number to mark!", e.getMessage());
    }

    @Test
    public void parseIndex_nonNumericArgument_exceptionThrown() {
        assertThrows(CocoException.class, () -> Parser.parseIndex("mark abc", "mark", 5));
    }

    @Test
    public void parseIndex_outOfRangeTooHigh_exceptionThrown() {
        CocoException e = assertThrows(CocoException.class,
                () -> Parser.parseIndex("mark 6", "mark", 5));
        assertEquals("Sorry, there is no task number 6!", e.getMessage());
    }

    @Test
    public void parseIndex_zero_exceptionThrown() {
        assertThrows(CocoException.class, () -> Parser.parseIndex("mark 0", "mark", 5));
    }
}
