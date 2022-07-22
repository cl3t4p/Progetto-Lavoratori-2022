package com.cl3t4p.progetto.lavoratori2022.fx.controllers.view;

import com.cl3t4p.progetto.lavoratori2022.Main;
import com.cl3t4p.progetto.lavoratori2022.repo.DataRepo;
import com.cl3t4p.progetto.lavoratori2022.repo.LavoratoreRepo;
import com.cl3t4p.progetto.lavoratori2022.type.Dipendente;
import com.cl3t4p.progetto.lavoratori2022.type.Lavoratore;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LavoratoreViewController implements Initializable {

    final DataRepo dataRepo = Main.getDataRepo();
    final LavoratoreRepo lavRepo = Main.getRepo().getLavoratoreRepo();


    @FXML
    Label id_dipendente, id_lavoratore;
    @FXML
    TextField nome, cognome, luogo_nascita, nazionalita, indirizzo, email, telefono, automunito, data_nascita, data_fine, data_inizio;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Dipendente dipendente = dataRepo.getDipendente();
        id_dipendente.setText(id_dipendente.getText() + dipendente.getId());
        id_lavoratore.setText(id_lavoratore.getText() + dataRepo.getLavoratore_id());
        setupLavoratore();
    }

    public void back(ActionEvent event) {
        Main.getLoader().loadView("MENU");
    }

    public void extra(ActionEvent event) {
        Main.getLoader().loadView("VIEW_OPZ");
    }

    public void emergenze(ActionEvent event) {
        Main.getLoader().loadView("VIEW_EMERGENZE");
    }


    private void setupLavoratore() {
        Lavoratore lavoratore = lavRepo.getLavoratoreByID(dataRepo.getLavoratore_id());
        nome.setText(lavoratore.getNome());
        cognome.setText(lavoratore.getCognome());
        luogo_nascita.setText(lavoratore.getLuogo_nascita());
        data_nascita.setText(lavoratore.getData_nascita().toString());
        nazionalita.setText(lavoratore.getNazionalita());
        indirizzo.setText(lavoratore.getIndirizzo());
        telefono.setText(String.valueOf(lavoratore.getTelefono()));
        email.setText(lavoratore.getEmail());
        data_inizio.setText(lavoratore.getInizio_disponibile().toString());
        data_fine.setText(lavoratore.getFine_disponibile().toString());
        automunito.setText(lavoratore.getAutomunito());
    }
}
