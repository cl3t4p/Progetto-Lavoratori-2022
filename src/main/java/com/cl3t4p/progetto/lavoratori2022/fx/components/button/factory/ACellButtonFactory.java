package com.cl3t4p.progetto.lavoratori2022.fx.components.button.factory;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.util.Map;

public abstract class ACellButtonFactory implements ICellButtonFactory {

    protected final Callback<Map<String, String>, Void> callback;
    protected final String text;

    public ACellButtonFactory(Callback<Map<String, String>, Void> callback, String text) {
        this.callback = callback;
        this.text = text;
    }

    @Override
    public Callback<TableColumn<Map<String, String>, Void>, TableCell<Map<String, String>, Void>> getButtonFactory() {
        return null;
    }
}
