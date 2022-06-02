package progetto;


import javafx.application.Application;
import javafx.stage.Stage;
import progetto.data.DataRepo;
import progetto.database.PostDriver;
import progetto.database.exception.JavaFXError;
import progetto.io.ViewLoader;

import java.sql.SQLException;

public class Main extends Application {

    static PostDriver postDriver;
    static ViewLoader loader = new ViewLoader("/view/");

    static DataRepo dataRepo = new DataRepo();

    @Override
    public void start(Stage stage) {
        if(!postDriver.testConnection()){
            JavaFXError.fxErrorMSG("Database connection error!");
            return;
        }




        stage.setResizable(false);
        loader.setPrimaryStage(stage);

        //TODO Replace AGG_LAV_OPZ with LOGIN
        //Testing
        try {
            dataRepo.setDipendente(postDriver.getDipendenteByUserAndPassword("marco01","12345"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //End Testing
        loader.loadView("AGGIUNGI_LAVORATORE");
        //loader.loadView("LOGIN");
    }

    public static void main(String[] args) {
        postDriver = new PostDriver("postgres","23rqwef8hu9fipnujo!","postgres","db.cl3t4p.com",5432);
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

