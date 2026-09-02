package coco;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * A single row in the chat log: a speech-bubble-style label aligned to one
 * side, styled differently for the user's messages vs. Coco's replies.
 */
public class DialogBox extends HBox {
    private DialogBox(String text, String styleClass, Pos alignment) {
        Label message = new Label(text);
        message.setWrapText(true);
        message.setMaxWidth(260);
        message.getStyleClass().add(styleClass);
        setAlignment(alignment);
        getChildren().add(message);
    }

    /**
     * Creates a dialog box for a message the user typed.
     *
     * @param text Message text.
     * @return The dialog box, right-aligned.
     */
    public static DialogBox getUserDialog(String text) {
        return new DialogBox(text, "user-bubble", Pos.TOP_RIGHT);
    }

    /**
     * Creates a dialog box for one of Coco's replies.
     *
     * @param text Message text.
     * @return The dialog box, left-aligned.
     */
    public static DialogBox getCocoDialog(String text) {
        return new DialogBox(text, "coco-bubble", Pos.TOP_LEFT);
    }
}
