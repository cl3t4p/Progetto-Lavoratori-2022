/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package progetto;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author 1blan
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException{

        Scene scene;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/LOGIN.fxml"));
        try{
            scene = new Scene(loader.load());
        }
        catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        stage.setScene(scene);
        stage.show(); 
    }
            /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}

