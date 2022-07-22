package com.cl3t4p.progetto.lavoratori2022;


import com.cl3t4p.progetto.lavoratori2022.repo.DataRepo;
import com.cl3t4p.progetto.lavoratori2022.database.PostDriver;
import com.cl3t4p.progetto.lavoratori2022.fx.JavaFXError;
import com.cl3t4p.progetto.lavoratori2022.io.ViewLoader;
import com.cl3t4p.progetto.lavoratori2022.repo.MainRepo;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main extends Application {

    @Getter
    static MainRepo repo;
    @Getter
    static ViewLoader loader = new ViewLoader("/view/");
    @Getter
    static DataRepo dataRepo = new DataRepo();

    @Override
    public void start(Stage stage) {
        stage.setTitle("Registro lavoratori");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/extra/icon.png")));

        try {
            loadDBConfig();
        } catch (IOException e) {
            JavaFXError.show("Database config does not exists!");
        }
        if (!repo.testConnection()) {
            JavaFXError.show("Database connection error!");
            return;
        }
        stage.setResizable(false);
        loader.setPrimaryStage(stage);

        loader.loadView("LOGIN");
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void loadDBConfig() throws IOException {
        FileReader reader = new FileReader(getClass().getResource("/db.properties").getPath());
        Properties p = new Properties();
        p.load(reader);
        repo = new PostDriver(p.getProperty("username"), p.getProperty("password"), p.getProperty("db_name"), p.getProperty("host"), Integer.parseInt(p.getProperty("port")));
    }


}

