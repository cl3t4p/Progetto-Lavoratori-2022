package com.cl3t4p.progetto.lavoratori2022.exception;


import javafx.scene.control.Alert;


/***
 * This class is used to throw an exception and the exception can be shown to the user.
 */
public class JavaFXDataError extends Exception {
    public JavaFXDataError() {
    }

    public JavaFXDataError(String s) {
        super(s);
    }

    public void show() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Errore");
        alert.setHeaderText(getMessage());
        alert.show();
    }
}
