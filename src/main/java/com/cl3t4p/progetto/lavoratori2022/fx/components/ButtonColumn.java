package com.cl3t4p.progetto.lavoratori2022.fx.components;


import com.cl3t4p.progetto.lavoratori2022.database.exception.JavaFXDataError;
import com.cl3t4p.progetto.lavoratori2022.database.exception.JavaFXError;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.sql.SQLException;


public class ButtonColumn<K, V> extends TableColumn<V, Void> implements Cloneable {
    final DB_Exec<K, V> db_exec;
    final K id;
    private final String msgError;

    /**
     * Create a ButtonColumn that will give an error if the user try to delete the last item
     *
     * @param text     Text that will apear on top of the column
     * @param id       ID of the lavoratore
     * @param db_exec  Executable for the database data
     * @param msgError Message that will appear if the user try to eliminate the last element
     */
    public ButtonColumn(String text, K id, DB_Exec<K, V> db_exec, String msgError) {
        super(text);
        this.db_exec = db_exec;
        this.msgError = msgError;
        this.id = id;
        setCellFactory(getButtonFactory());
        setEditable(false);
        setResizable(false);
        setReorderable(false);
        setPrefWidth(30);
    }

    public ButtonColumn(String text, K id, DB_Exec<K, V> db_exec) {
        super(text);
        this.db_exec = db_exec;
        this.msgError = "";
        this.id = id;
        setCellFactory(getButtonFactory());
        setEditable(false);
        setResizable(false);
        setReorderable(false);
        setPrefWidth(30);
    }


    private Callback<TableColumn<V, Void>, TableCell<V, Void>> getButtonFactory() {
        return new Callback<>() {
            @Override
            public TableCell<V, Void> call(final TableColumn<V, Void> param) {
                return new TableCell<>() {
                    private final Button btn = new Button("-");

                    {
                        btn.setOnAction(e -> {
                            try {
                                if (getTableView().getItems().size() == 1 && !msgError.isEmpty())
                                    throw new JavaFXDataError(msgError);
                                V key = getTableView().getItems().get(getIndex());
                                getTableView().getItems().remove(getIndex());
                                db_exec.databaseExec(id, key);
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                                JavaFXError.DB_ERROR.fxMSG();
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

    @Override
    public ButtonColumn<K, V> clone() {
        try {
            return (ButtonColumn<K, V>) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }


    public static interface DB_Exec<K, V> {
        void databaseExec(K lav_id, V key) throws SQLException;
    }
}

