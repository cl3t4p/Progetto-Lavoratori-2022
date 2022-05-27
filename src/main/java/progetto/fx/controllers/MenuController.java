package progetto.fx.controllers;

import javafx.event.ActionEvent;
import progetto.Main;

public class MenuController {
    public void ricerca_lav(ActionEvent event) {
        Main.getLoader().loadView("RICERCA_LAVORATORE");
    }

    public void aggiungi_lavoratore(ActionEvent event) {
        Main.getLoader().loadView("AGGIUNGI_LAVORATORE");
    }

    public void modifica_anagrafica(ActionEvent event) {
        Main.getLoader().loadView("MODIFICA_ANAGRAFICA");
    }
}
