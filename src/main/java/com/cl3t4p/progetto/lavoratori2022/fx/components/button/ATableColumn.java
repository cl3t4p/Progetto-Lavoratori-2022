package com.cl3t4p.progetto.lavoratori2022.fx.components.button;


import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.util.Map;

public abstract class ATableColumn extends TableColumn<Map<String, String>, Void> implements IButtonFactory {
    protected final Callback<Map<String, String>, Void> callback;
    protected final String text;

    protected ATableColumn(Callback<Map<String, String>, Void> callback, String text) {
        this.callback = callback;

        setPrefWidth(25);
        if (text.isBlank()) {
            this.text = "-";
        }else
            this.text = text;

        setCellFactory(getButtonFactory());
        setEditable(false);
        setResizable(false);
        setReorderable(false);
        setStyle("-fx-alignment: CENTER;");
    }




}
