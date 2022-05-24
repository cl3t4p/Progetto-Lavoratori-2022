package progetto;

import java.io.IOException;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import progetto.database.PostDriver;

/**
 *
 * @author 1blan
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException{

        Scene scene;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LOGIN.fxml"));
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
        //launch(args);
        String user = "marco01";
        String pass = "12345";
        try {
            PostDriver postDriver = new PostDriver("postgres","example","postgres","localhost",5432);
            Dipendente dipendente = postDriver.getDipendenteByUserAndPassword(user,pass);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}

