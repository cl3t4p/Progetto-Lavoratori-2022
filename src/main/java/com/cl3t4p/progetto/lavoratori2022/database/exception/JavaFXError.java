package com.cl3t4p.progetto.lavoratori2022.database.exception;

import javafx.scene.control.Alert;

public enum JavaFXError {
    DB_ERROR("Errore DB");

    final String error;

    JavaFXError(String s) {
        this.error = s;
    }


    public void showError() {
        showError(error);
    }
    public void printContent(String content){
        showError(this.error,content);
    }

    public static void showError(String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Errore");
        alert.setHeaderText(content);
        alert.show();
    }
    public static void showError(String title,String content){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(content);
        alert.show();
    }


}
