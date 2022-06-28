package com.cl3t4p.progetto.lavoratori2022.fx.controllers.main;

import com.cl3t4p.progetto.lavoratori2022.database.exception.JavaFXError;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.cl3t4p.progetto.lavoratori2022.data.type.Dipendente;
import com.cl3t4p.progetto.lavoratori2022.Main;

import java.sql.SQLException;

public class LoginController {
    @FXML
    private TextField txuser;
    @FXML
    private PasswordField txpass;

    @FXML
    private void login(ActionEvent event) {
        String username = txuser.getText();
        String password = txpass.getText();
        try {
            Dipendente dipendente = Main.getPostDriver().getDipendenteByUserAndPassword(username, password);
            if (dipendente != null) {
                Main.getDataRepo().setDipendente(dipendente);
                Main.getLoader().loadView("MENU");
            } else
                JavaFXError.show("Username o password errati!");
        } catch (SQLException e) {
            JavaFXError.DB_ERROR.show();
        }
    }
}
