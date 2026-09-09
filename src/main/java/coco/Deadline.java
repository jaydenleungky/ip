package coco;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * A task that must be done by a specific date.
 */
public class Deadline extends Task {
    private static final DateTimeFormatter DISPLAY_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy", Locale.ENGLISH);

    protected LocalDate by;

    /**
     * Creates a new, not-yet-done deadline.
     *
     * @param description Description of the deadline.
     * @param by Date the deadline is due by.
     */
    public Deadline(String description, LocalDate by) {
        super(description);
        // Parser.parseDeadline is responsible for rejecting an empty
        // description before ever reaching this constructor.
        assert !description.isEmpty() : "description should already be validated by Parser";
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(DISPLAY_FORMAT) + ")";
    }

    @Override
    public String toSaveFormat() {
        return "D | " + super.toSaveFormat() + " | " + by;
    }
}
