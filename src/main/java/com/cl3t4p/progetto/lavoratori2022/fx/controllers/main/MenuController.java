package com.cl3t4p.progetto.lavoratori2022.fx.controllers.main;

import com.cl3t4p.progetto.lavoratori2022.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MenuController {
    @FXML
    private void ricerca_lav(ActionEvent event) {
        Main.getLoader().loadView("RICERCA");
    }

    @FXML
    private void aggiungi_lavoratore(ActionEvent event) {
        Main.getLoader().loadView("LAVORATORE");
    }

    @FXML
    private void modifica_anagrafica(ActionEvent event) {
        Main.getLoader().loadView("MODIFICA");
    }
}
