package com.cl3t4p.progetto.lavoratori2022.fx.controllers.main;

import com.cl3t4p.progetto.lavoratori2022.Main;
import com.cl3t4p.progetto.lavoratori2022.fx.JavaFXError;
import com.cl3t4p.progetto.lavoratori2022.repo.DataRepo;
import com.cl3t4p.progetto.lavoratori2022.repo.DipendenteRepo;
import com.cl3t4p.progetto.lavoratori2022.type.Dipendente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginController {

    final DipendenteRepo dipendenteRepo = Main.getRepo().getDipendenteRepo();
    final DataRepo dataRepo = Main.getDataRepo();

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
            dataRepo.setDipendente(dipendente);
            Main.getLoader().loadView("MENU");
        } else
            JavaFXError.show("Username o password errati!");
    }

    @FXML
    private void enterPress(KeyEvent event) {
        if(event.getCode().equals(KeyCode.ENTER))
            login(new ActionEvent());
    }
}
