package com.cl3t4p.progetto.lavoratori2022;


import com.cl3t4p.progetto.lavoratori2022.repo.DataRepo;
import com.cl3t4p.progetto.lavoratori2022.io.ViewLoader;
import javafx.application.Application;
import javafx.stage.Stage;
import com.cl3t4p.progetto.lavoratori2022.database.PostDriver;
import com.cl3t4p.progetto.lavoratori2022.database.exception.JavaFXError;
import lombok.Getter;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class Main extends Application {

    @Getter
    static PostDriver postDriver;
    @Getter
    static ViewLoader loader = new ViewLoader("/view/");
    @Getter
    static DataRepo dataRepo = new DataRepo();

    @Override
    public void start(Stage stage) {
        try {
            loadDBConfig();
        } catch (IOException e) {
            JavaFXError.show("Database config does not exists!");
        }
        if (!postDriver.testConnection()) {
            JavaFXError.show("Database connection error!");
            return;
        }


        stage.setResizable(false);
        loader.setPrimaryStage(stage);

        //TODO Replace AGG_LAV_OPZ with LOGIN
        //Testing
        try {
            dataRepo.setDipendente(postDriver.getDipendenteByUserAndPassword("marco01", "12345"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //End Testing
        loader.loadView("AGG_LAV_OPZ");
        //loader.loadView("LOGIN");
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void loadDBConfig() throws IOException {
        FileReader reader = new FileReader(getClass().getResource("/db.properties").getPath());
        Properties p = new Properties();
        p.load(reader);
        postDriver = new PostDriver(p.getProperty("username"), p.getProperty("password"), p.getProperty("db_name"), p.getProperty("host"), Integer.parseInt(p.getProperty("port")));
    }


}

