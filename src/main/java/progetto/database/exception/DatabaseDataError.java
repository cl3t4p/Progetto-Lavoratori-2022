package progetto.database.exception;


import javafx.scene.control.Alert;


public class DatabaseDataError extends Exception{
    public DatabaseDataError(String s) {
        super(s);
    }
    public void printFX(String string){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Errore");
        alert.setHeaderText(string);
        alert.show();
        alert.showAndWait().get();
    }
    public void printFX(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Errore");
        alert.setHeaderText(getMessage());
        alert.show();
       // alert.showAndWait().get();
    }
}
