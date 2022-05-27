package progetto.fx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import progetto.Main;
import progetto.database.exception.DatabaseDataError;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    public TextField txuser;
    public PasswordField txpass;

    public void login(ActionEvent event) {
        String username = txuser.getText();
        String password = txpass.getText();
        try {
            if(Main.getPostDriver().login(username,password)){
                Main.getLoader().loadView("MENU");
            }else
                throw new DatabaseDataError("Wrong Username or Password");
        }catch (DatabaseDataError e ){
            e.printFX();
        } catch (SQLException e) {
            System.out.println("test");
            new DatabaseDataError("Database error").printFX();
        }
    }
}
