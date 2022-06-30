package com.cl3t4p.progetto.lavoratori2022.fx.components;


import com.cl3t4p.progetto.lavoratori2022.database.exception.JavaFXDataError;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import lombok.Setter;

import java.util.Map;


public class ButtonColumn extends TableColumn<Map<String,String>, Void>  {
    private final Callback<Map<String,String>,Void> callback;


    @Setter
    private String msgError = "";

    @Setter
    private String buttonText = "-";

    /**
     * Create a ButtonColumn
     *
     * @param text     Text that will apear on top of the column
     */
    public ButtonColumn(String text, Callback<Map<String,String>,Void> callback) {
        super(text);
        this.callback = callback;
        setCellFactory(getButtonFactory());
        setEditable(false);
        setResizable(false);
        setReorderable(false);
        setStyle("-fx-alignment: CENTER;");
        setPrefWidth(25);
    }




    private Callback<TableColumn<Map<String,String>, Void>, TableCell<Map<String,String>, Void>> getButtonFactory() {
        return new Callback<>() {
            @Override
            public TableCell<Map<String,String>, Void> call(final TableColumn<Map<String,String>, Void> param) {
                return new TableCell<>() {
                    private final Button btn = new Button(buttonText);
                    {
                        btn.setPrefWidth(getWidth() - 2);
                        btn.setOnAction(e -> {
                            try {
                                if (getTableView().getItems().size() == 1 && !msgError.isEmpty())
                                    throw new JavaFXDataError(msgError);
                                Map<String,String> key = getTableView().getItems().get(getIndex());
                                getTableView().getItems().remove(getIndex());
                                callback.call(key);
                            } catch (JavaFXDataError error) {
                                error.printFX();
                            }
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

