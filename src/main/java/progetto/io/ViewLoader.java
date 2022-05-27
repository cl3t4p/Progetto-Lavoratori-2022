package progetto.io;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ViewLoader {

    private Stage primaryStage;
    private String extension = ".fxml";
    final String base_path;
    private final Set<String> file_names = new HashSet<>();
    private final HashMap<String,Parent> parents = new HashMap<>();

    public ViewLoader(String base_path) {
        this.base_path = base_path;
    }
    public ViewLoader(String base_path,String extension) {
        this.base_path = base_path;
        this.extension = extension;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void add(Set<String> names){
        file_names.addAll(names);
    }
    public void add(String... names){
        file_names.addAll(Set.of(names));
    }
    public void add(String name){
        file_names.add(name);
    }

    public void load() {
        for (String file_name : file_names) {
            Parent parent;
            try {
                parent = FXMLLoader.load(getClass().getResource(base_path+file_name+extension));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            parents.put(file_name,parent);
        }
    }


    public Parent getView(String name){
        return parents.get(name);
    }
    public void loadView(String name){
        primaryStage.setScene(new Scene(getView(name)));
        primaryStage.show();
    }
}