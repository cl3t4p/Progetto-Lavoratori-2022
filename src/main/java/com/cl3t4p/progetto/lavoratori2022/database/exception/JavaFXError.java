package com.cl3t4p.progetto.lavoratori2022.database.exception;

import javafx.scene.control.Alert;

/***
 * This enum is used to print an error message in a JavaFX window.
 */
public enum JavaFXError {
    DB_ERROR("Errore DB");

    final String error;

    JavaFXError(String s) {
        this.error = s;
    }


    public void show() {
        show(error);
    }
    public void printContent(String content){
        show(this.error,content);
    }

    public static void show(String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Errore");
        alert.setHeaderText(content);
        alert.show();
    }
    public static void show(String title, String content){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(content);
        alert.show();
    }


}
