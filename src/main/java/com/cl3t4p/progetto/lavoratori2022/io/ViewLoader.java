package com.cl3t4p.progetto.lavoratori2022.io;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Setter;

import java.io.IOException;

/**
 * This class is used to load FXML files.
 */
public class ViewLoader {


    @Setter
    private Stage primaryStage;

    private String extension = ".fxml";
    final String base_path;


    /**
     * @param base_path is the path of the FXML files.
     */
    public ViewLoader(String base_path) {
        this.base_path = base_path;
    }


    /**
     * @param base_path is the path of the FXML files.
     * @param extension is the extension of the FXML files.
     */
    public ViewLoader(String base_path, String extension) {
        this.base_path = base_path;
        this.extension = extension;
    }


    /**
     * Returns the loaded FXML file and if controller is not null, it will be set to the controller of the loaded FXML file.
     *
     * @param file_name  is the name of the file without the extension.
     * @param controller is the controller of the loaded FXML file.
     * @return the loaded FXML file.
     */
    private Parent loadParent(String file_name, Object controller) {
        Parent parent;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(base_path + file_name + extension));
            if (controller != null)
                loader.setController(controller);
            parent = loader.load();
            parent.getStylesheets().add("css/global.css");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return parent;
    }


    /**
     * Load the FXML file and show it in a new window.
     *
     * @param name is the name of the file / scene without the extension.
     */
    public void loadView(String name) {
        loadView(name, null);
    }

    /**
     * Load the FXML file and show it in a new window. If controller is not null, it will be set to the controller of the loaded FXML file.
     *
     * @param name       is the name of the file / scene without the extension.
     * @param controller is the controller of the loaded FXML file.
     */
    public void loadView(String name, Object controller) {
        Parent root = loadParent(name, controller);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}
