package com.cl3t4p.progetto.lavoratori2022.fx.components.button;



import com.cl3t4p.progetto.lavoratori2022.fx.components.button.factory.ICellButtonFactory;
import javafx.scene.control.TableColumn;

import java.util.Map;

public class DefaultColumn extends TableColumn<Map<String, String>, Void> {


    public DefaultColumn(ICellButtonFactory factory) {
        setPrefWidth(25);
        setCellFactory(factory.getButtonFactory());
        setEditable(false);
        setResizable(false);
        setReorderable(false);
        setStyle("-fx-alignment: CENTER;");
    }

}
