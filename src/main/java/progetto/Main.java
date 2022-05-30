package progetto;


import javafx.application.Application;
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
        //TODO Replace MENU with LOGIN
        loader.setPrimaryStage(stage);
        loader.loadView("MENU");
    }

    public static void main(String[] args) {
        postDriver = new PostDriver("postgres","example","postgres","localhost",5432);
        loader = new ViewLoader("/view/");
        loader.add("AGGIUNGI_LAVORATORE");
        loader.add("AGGIUNGI_LAVORO");
        loader.add("LOGIN");
        loader.add("MENU");
        loader.add("MODIFICA_ANAGRAFICA");
        loader.add("RICERCA_LAVORATORE");
        loader.reload();
        launch(args);
    }

    public static PostDriver getPostDriver() {
        return postDriver;
    }

    public static ViewLoader getLoader() {
        return loader;
    }
}

