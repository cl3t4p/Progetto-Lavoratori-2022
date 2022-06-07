package com.cl3t4p.progetto.lavoratori2022.fx.components;


import com.cl3t4p.progetto.lavoratori2022.database.exception.JavaFXError;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.sql.SQLException;


public class ButtonColumn extends TableColumn<String, Void> implements Cloneable{
    final DB_Exec db_exec;
    final int id;

    public ButtonColumn(String text, int id, DB_Exec db_exec) {
        super(text);
        this.db_exec = db_exec;
        this.id = id;
        setCellFactory(getButtonFactory());
        setEditable(false);
        setResizable(false);
        setReorderable(false);
        setPrefWidth(30);
    }


    private Callback<TableColumn<String, Void>, TableCell<String, Void>> getButtonFactory(){
        return new Callback<>() {
            @Override
            public TableCell<String, Void> call(final TableColumn<String, Void> param) {
                return new TableCell<>() {
                    private final Button btn = new Button("-");
                    {
                        btn.setOnAction(e -> {
                            try {
                                String key = getTableView().getItems().get(getIndex());
                                getTableView().getItems().remove(getIndex());
                                db_exec.databaseExec(id,key);
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                                JavaFXError.DB_ERROR.fxMSG();
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

    @Override
    public ButtonColumn clone() {
        try {
            return (ButtonColumn) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }




    public static interface DB_Exec {
        void databaseExec(int lav_id,String key) throws SQLException;
    }
}

