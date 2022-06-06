package com.cl3t4p.progetto.Lavoratori2022;


import com.cl3t4p.progetto.Lavoratori2022.data.DataRepo;
import com.cl3t4p.progetto.Lavoratori2022.io.ViewLoader;
import javafx.application.Application;
import javafx.stage.Stage;
import com.cl3t4p.progetto.Lavoratori2022.database.PostDriver;
import com.cl3t4p.progetto.Lavoratori2022.database.exception.JavaFXError;
import lombok.Getter;

import java.io.FileNotFoundException;
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
        try {
            loadConfig();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        launch(args);
    }

    private static void loadConfig() throws IOException {
        FileReader reader=new FileReader("db.properties");
        Properties p=new Properties();
        p.load(reader);
        postDriver = new PostDriver(p.getProperty("username"),p.getProperty("password"),p.getProperty("db_name"),p.getProperty("host"), (Integer) p.get("port"));
    }


}

