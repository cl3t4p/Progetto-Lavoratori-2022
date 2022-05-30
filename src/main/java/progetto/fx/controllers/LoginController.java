package progetto.fx.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import progetto.Main;
import progetto.database.exception.JavaFXDataError;

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
                throw new JavaFXDataError("Wrong Username or Password");
        }catch (JavaFXDataError e ){
            e.printFX();
        } catch (SQLException e) {
            System.out.println("test");
            new JavaFXDataError("Database error").printFX();
        }
    }
}
