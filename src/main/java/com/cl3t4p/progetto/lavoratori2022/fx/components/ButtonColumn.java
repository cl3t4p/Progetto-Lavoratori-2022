package com.cl3t4p.progetto.lavoratori2022.fx.components;


import com.cl3t4p.progetto.lavoratori2022.database.exception.JavaFXDataError;
import com.cl3t4p.progetto.lavoratori2022.database.exception.JavaFXError;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import lombok.Setter;

import java.sql.SQLException;


public class ButtonColumn<K, V> extends TableColumn<V, Void>  {
    final DB_Exec<K, V> db_exec;
    final K id;


    @Setter
    private String msgError = "";

    @Setter
    private String buttonText = "-";

    /**
     * Create a ButtonColumn
     *
     * @param text     Text that will apear on top of the column
     * @param id       ID of the lavoratore
     * @param db_exec  Executable for the database data
     */
    public ButtonColumn(String text, K id, DB_Exec<K, V> db_exec) {
        super(text);
        this.db_exec = db_exec;
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
                    private final Button btn = new Button(buttonText);
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

    public interface DB_Exec<K, V> {
        void databaseExec(K lav_id, V key) throws SQLException;
    }
}

