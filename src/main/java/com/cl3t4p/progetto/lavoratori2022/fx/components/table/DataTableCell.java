package com.cl3t4p.progetto.lavoratori2022.fx.components.table;


import com.cl3t4p.progetto.lavoratori2022.fx.components.Toast;
import javafx.scene.control.TableCell;
import javafx.scene.control.Tooltip;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Map;

/**
 * TableCell that help display the value and if the value is too long,
 * it will add a tooltip with the full value.
 * Also, it will copy the value to the clipboard when the user click 2 times on the cell.
 */
public class DataTableCell extends TableCell<Map<String, String>, String> {

    public DataTableCell() {
        // Copy item to clipboard when double clicked.
        setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY))
                if (mouseEvent.getClickCount() == 2) {
                    ClipboardContent content = new ClipboardContent();
                    content.putString(getItem());
                    Clipboard.getSystemClipboard().setContent(content);
                    Toast.makeText(this, "Copied to clipboard", 500, 500);
                }
        });

        // Check if the text is clipped and if it is, add a tooltip.
        needsLayoutProperty().addListener((observable, oldValue, newValue) -> {
            String original = getItem();
            Text text = (Text) lookup(".text"); // "text" is the style class of Text
            String actual = text.getText();

            if (!actual.isEmpty() && !original.equals(actual)) {
                Tooltip tooltip = new Tooltip(getItem());
                tooltip.setFont(Font.font("Verdana", 10));
                setTooltip(tooltip);
            }
        });

    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null) {
            setText(item);
        }
    }

}
