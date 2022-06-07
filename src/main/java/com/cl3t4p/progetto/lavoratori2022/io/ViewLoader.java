package com.cl3t4p.progetto.lavoratori2022.io;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class ViewLoader {


    private Stage primaryStage;

    private String extension = ".fxml";
    final String base_path;
    public ViewLoader(String base_path) {
        this.base_path = base_path;
    }
    public ViewLoader(String base_path,String extension) {
        this.base_path = base_path;
        this.extension = extension;
    }

    private Parent loadParent(String file_name){
        Parent parent;
        try {
            parent = FXMLLoader.load(getClass().getResource(base_path+file_name+extension));
            parent.getStylesheets().add("css/global.css");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return parent;
    }


    public void loadView(String name){
        Parent root= loadParent(name);
        Scene scene=new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
