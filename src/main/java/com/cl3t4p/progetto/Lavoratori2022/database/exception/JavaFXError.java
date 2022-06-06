package com.cl3t4p.progetto.Lavoratori2022.database.exception;

import javafx.scene.control.Alert;

public enum JavaFXError {
    DB_ERROR("Database Error!");

    final String msg;
    JavaFXError(String s) {
        this.msg = s;
    }
    public void fxMSG(){
        fxErrorMSG(msg);
    }
    public static void fxErrorMSG(String msg){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Errore");
        alert.setHeaderText(msg);
        alert.show();
    }



}
