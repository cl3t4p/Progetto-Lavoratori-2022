package com.cl3t4p.progetto.lavoratori2022.fx.components.button.factory;

import com.cl3t4p.progetto.lavoratori2022.fx.JavaFXError;
import com.cl3t4p.progetto.lavoratori2022.fx.components.button.ATableColumn;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.util.Map;

public class LastStandingColumn extends ATableColumn {

    final String errorMsg;

    public LastStandingColumn(Callback<Map<String, String>, Void> callback, String text, String errorMsg) {
        super(callback,text);
        this.errorMsg = errorMsg;
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
                            if(getTableView().getItems().size() == 1){
                                JavaFXError.show(errorMsg);
                                return;
                            }
                            Map<String, String> key = getTableView().getItems().get(getIndex());
                            getTableView().getItems().remove(getIndex());
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
