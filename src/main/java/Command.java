public enum Command {
    LIST,
    MARK,
    UNMARK,
    DELETE,
    TODO,
    DEADLINE,
    EVENT,
    BYE,
    UNKNOWN;

    public static Command fromInput(String input) {
        String keyword = input.split(" ", 2)[0];
        for (Command command : values()) {
            if (command != UNKNOWN && command.name().toLowerCase().equals(keyword)) {
                return command;
            }
        }
        return UNKNOWN;
    }
}
