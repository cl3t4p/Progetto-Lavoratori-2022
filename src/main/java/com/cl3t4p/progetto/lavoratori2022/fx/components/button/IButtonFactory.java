package com.cl3t4p.progetto.lavoratori2022.fx.components.button;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.util.Map;

public interface IButtonFactory {

    Callback<TableColumn<Map<String, String>, Void>, TableCell<Map<String, String>, Void>> getButtonFactory();
}
