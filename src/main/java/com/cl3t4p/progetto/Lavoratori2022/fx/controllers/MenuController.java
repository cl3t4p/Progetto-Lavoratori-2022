package com.cl3t4p.progetto.Lavoratori2022.fx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.cl3t4p.progetto.Lavoratori2022.Main;

public class MenuController {
    @FXML
    private void ricerca_lav(ActionEvent event) {
        Main.getLoader().loadView("RICERCA_LAVORATORE");
    }
    @FXML
    private void aggiungi_lavoratore(ActionEvent event) {
        Main.getLoader().loadView("AGGIUNGI_LAVORATORE");
    }
    @FXML
    private void modifica_anagrafica(ActionEvent event) {
        Main.getLoader().loadView("MODIFICA_ANAGRAFICA");
    }
}