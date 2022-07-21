package com.cl3t4p.progetto.lavoratori2022.fx.controllers.main;

import com.cl3t4p.progetto.lavoratori2022.Main;
import com.cl3t4p.progetto.lavoratori2022.fx.JavaFXError;
import com.cl3t4p.progetto.lavoratori2022.repo.DipendenteRepo;
import com.cl3t4p.progetto.lavoratori2022.type.Dipendente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginController {

    final DipendenteRepo dipendenteRepo = Main.getRepo().getDipendenteRepo();

    @FXML
    TextField txuser;
    @FXML
    PasswordField txpass;

    @FXML
    private void login(ActionEvent event) {
        String username = txuser.getText();
        String password = txpass.getText();
        Dipendente dipendente = dipendenteRepo.getDipendenteByUserAndPassword(username, password);
        if (dipendente != null) {
            Main.getMemRepo().setDipendente(dipendente);
            Main.getLoader().loadView("MENU");
        } else
            JavaFXError.show("Username o password errati!");
    }
}
