package com.cl3t4p.progetto.lavoratori2022.fx.components.button;


import com.cl3t4p.progetto.lavoratori2022.fx.components.button.factory.ICellButtonFactory;
import com.cl3t4p.progetto.lavoratori2022.fx.components.button.factory.LastStandingFactory;
import com.cl3t4p.progetto.lavoratori2022.fx.components.button.factory.NothingFactory;
import com.cl3t4p.progetto.lavoratori2022.fx.components.button.factory.RemoveFactory;
import javafx.util.Callback;

import java.util.Map;

/**
 * This class is used to create a column with a button that will call a callback function.
 */
public class CellButtonFactoryFactory {

    private final Callback<Map<String, String>, Void> callback;
    private final String text;

    public CellButtonFactoryFactory(Callback<Map<String, String>, Void> callback, String text) {
        this.callback = callback;
        this.text = text;
    }

    public CellButtonFactoryFactory(Callback<Map<String, String>, Void> callback) {
        this.callback = callback;
        this.text = "-";
    }

    public ICellButtonFactory getCellFactory(ColumnAction action){
        return switch (action) {
            case REMOVE -> new RemoveFactory(callback, text);
            case LAST_STANDING -> new LastStandingFactory(callback, text);
            case NOTHING -> new NothingFactory(callback, text);
        };
    }

}
