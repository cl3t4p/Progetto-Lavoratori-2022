package com.cl3t4p.progetto.lavoratori2022.fx.controllers.lavoratore;

import com.cl3t4p.progetto.lavoratori2022.Main;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class LavMenuController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
        Main.getLoader().loadView("MENU");
    }
}
