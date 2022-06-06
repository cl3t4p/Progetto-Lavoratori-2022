package com.cl3t4p.progetto.Lavoratori2022.database.exception;


import javafx.scene.control.Alert;


public class JavaFXDataError extends Exception{
    public JavaFXDataError(String s) {
        super(s);
    }

    public void printFX(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Errore");
        alert.setHeaderText(getMessage());
        alert.show();
    }
}
