package com.cl3t4p.progetto.lavoratori2022.fx.components;

import com.cl3t4p.progetto.lavoratori2022.fx.components.button.ATableColumn;
import com.cl3t4p.progetto.lavoratori2022.fx.components.button.factory.LastStandingColumn;
import com.cl3t4p.progetto.lavoratori2022.fx.components.button.factory.NothingColumn;
import com.cl3t4p.progetto.lavoratori2022.fx.components.button.factory.RemoveColumn;
import javafx.util.Callback;

import java.util.Map;

/***
 * This class is used to create a column with a button that will call a callback function.
 */
public class ButtonColumnFactory {

    private final Callback<Map<String, String>, Void> callback;
    private final String text;

    public ButtonColumnFactory(Callback<Map<String, String>, Void> callback, String text) {
        this.callback = callback;
        this.text = text;
    }

    public ButtonColumnFactory(Callback<Map<String, String>, Void> callback) {
        this.callback = callback;
        this.text = "-";
    }


    /**
     * This method is used to create a ATableColumn with a button that will call a callback function.
     * and it will show a error message if the user try to remove the last element of the table.
     * @param msgError the message that will be shown if the user try to remove the last element of the table.
     */
    public ATableColumn getLastStandingColumn(String msgError) {
        return new LastStandingColumn(callback, text,msgError);
    }


    /**
     * This method is used to create a ATableColumn with a button that will call a callback function.
     * and when the user press the button it will also remove the element from the table.
     */
    public ATableColumn getRemoveColumn() {
        return new RemoveColumn(callback, text);
    }

    /**
     * This method is used to create a ATableColumn with a button that will call a callback function.
     */
    public ATableColumn getNothingColumn() {
        return new NothingColumn(callback, text);
    }
}
