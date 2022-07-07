package com.cl3t4p.progetto.lavoratori2022.fx.components.button.factory;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.util.Map;

public class NothingFactory extends ACellButtonFactory {

    public NothingFactory(Callback<Map<String, String>, Void> callback, String text) {
        super(callback, text);
    }


    @Override
    public Callback<TableColumn<Map<String, String>, Void>, TableCell<Map<String, String>, Void>> getButtonFactory() {
        return new Callback<>() {
            @Override
            public TableCell<Map<String, String>, Void> call(TableColumn<Map<String, String>, Void> param) {
                return new TableCell<>() {
                    private final Button btn = new Button(text);

                    {
                        btn.setPrefWidth(getWidth() - 2);
                        btn.setOnAction(e -> {
                            Map<String, String> key = getTableView().getItems().get(getIndex());
                            callback.call(key);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
            }
        };
    }
}
