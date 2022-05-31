package progetto;


import javafx.application.Application;
import javafx.stage.Stage;
import progetto.data.DataRepo;
import progetto.database.PostDriver;
import progetto.io.ViewLoader;

/**
 *
 * @author 1blan
 */
public class Main extends Application {

    static PostDriver postDriver;
    static ViewLoader loader = new ViewLoader("/view/");

    static DataRepo dataRepo = new DataRepo();

    @Override
    public void start(Stage stage) {
        //TODO Replace MENU with LOGIN
        stage.setResizable(false);

        loader.setPrimaryStage(stage);
        loader.loadView("AGG_LAV_OPZ");
    }

    public static void main(String[] args) {
        postDriver = new PostDriver("postgres","example","postgres","localhost",5432);
        launch(args);
    }

    public static PostDriver getPostDriver() {
        return postDriver;
    }

    public static ViewLoader getLoader() {
        return loader;
    }

    public static DataRepo getDataRepo() {
        if(dataRepo == null)
            dataRepo = new DataRepo();
        return dataRepo;
    }
}

