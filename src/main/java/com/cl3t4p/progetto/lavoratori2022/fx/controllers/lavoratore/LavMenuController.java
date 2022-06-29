package com.cl3t4p.progetto.lavoratori2022.fx.controllers.lavoratore;

import com.cl3t4p.progetto.lavoratori2022.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;


public class LavMenuController implements Initializable {

    @FXML
    private Label id_lav;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(Main.getDataRepo().getLavoratore_id() != null) {
            id_lav.setText(id_lav.getText() + Main.getDataRepo().getLavoratore_id());
        }
    }


    public void aggLavoro(ActionEvent event) {
        Main.getLoader().loadView("AGGIUNGI_LAVORO");
    }

    public void modLav(ActionEvent event) {
        Main.getLoader().loadView("MODIFICA_AGG_LAVORATORE");
    }

    public void modEme(ActionEvent event) {
        Main.getLoader().loadView("MENU_EMERGENZE");
    }

    public void modOpz(ActionEvent event) {
        Main.getLoader().loadView("AGG_LAV_OPZ");
    }

    public void back(ActionEvent event) {
        Main.getDataRepo().setLavoratore_id(null);
        Main.getLoader().loadView("MENU");
    }


}
