package progetto;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import progetto.database.PostDriver;
import progetto.io.ViewLoader;

/**
 *
 * @author 1blan
 */
public class Main extends Application {

    static PostDriver postDriver;
    static ViewLoader loader;

    @Override
    public void start(Stage stage) {
        loader.setPrimaryStage(stage);
        //TODO Replace MENU with LOGIN
        loader.loadView("MENU");
    }
            /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        postDriver = new PostDriver("postgres","example","postgres","localhost",5432);
        loader = new ViewLoader("/view/");
        loader.add("AGGIUNGI_LAVORATORE");
        loader.add("AGGIUNGI_LAVORO");
        loader.add("LOGIN");
        loader.add("MENU");
        loader.add("MODIFICA_ANAGRAFICA");
        loader.add("RICERCA_LAVORATORE");
        loader.load();
        launch(args);
    }

    public static PostDriver getPostDriver() {
        return postDriver;
    }

    public static ViewLoader getLoader() {
        return loader;
    }
}

