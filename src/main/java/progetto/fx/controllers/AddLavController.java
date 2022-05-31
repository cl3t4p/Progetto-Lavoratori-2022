package progetto.fx.controllers;

import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import progetto.Comune;
import progetto.Lavoratore;
import progetto.Main;
import progetto.data.RegexChecker;
import progetto.database.PostDriver;
import progetto.database.exception.JavaFXDataError;
import progetto.fx.components.NumberField;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;


public class AddLavController implements Initializable {

    public DatePicker data_inizio,data_fine,data_nascita;
    public Label email_invalida,tel_invalido;
    PostDriver postDriver = Main.getPostDriver();

    public TextField nome,cognome,luogo_nascita,nazionalita
            ,indirizzo,email
            ,em_nome,em_cognome,em_telefono,em_email
            ,lingua,id_dipendente,esperienza;

    public NumberField telefono;
    public ComboBox<String> comune;
    public ChoiceBox<String> patente,automunito;
    public AnchorPane agg_lavoratore;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            patente.getItems().addAll(postDriver.getAllPatentI().stream().toList());
            comune.getEditor().setOnKeyReleased(this::comune_search);
            email.focusedProperty().addListener(this::check_email);
            if(Main.getDataRepo().getDipendente() != null)
             id_dipendente.setText(String.valueOf(Main.getDataRepo().getDipendente().getId()));
            id_dipendente.setEditable(false);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    private void check_email(Observable observable) {
        if(email.isFocused())return;
        if(email.getText().isEmpty()) {
            email_invalida.setVisible(false);
            return;
        }
        email_invalida.setVisible(!RegexChecker.EMAIL.validate(email.getText()));
    }


    public void agg_patente(ActionEvent event) {

    }

    public void agg_esperienza(ActionEvent event) {
    }

    public void agg_comune(ActionEvent event) {


    }

    public void agg_lingua(ActionEvent event) {
    }

    public void inserimento_lavoratore(ActionEvent event)  {

        try {
            Lavoratore lavoratore = new Lavoratore();
            lavoratore.setNome(nome.getText());
            lavoratore.setCognome(cognome.getText());
            lavoratore.setLuogo_nascita(luogo_nascita.getText());
            if (data_nascita.getValue() == null) {
                throw new JavaFXDataError("Data di nascita non valida");
            }
            lavoratore.setData_nascita(Date.valueOf(data_nascita.getValue()));
            lavoratore.setNazionalita(nazionalita.getText());
            lavoratore.setIndirizzo(indirizzo.getText());
            if (telefono.getValue() == null) {
                throw new JavaFXDataError("Telefono non valido");
            }
            lavoratore.setTelefono(telefono.getValue());
            lavoratore.setEmail(email.getText());
            lavoratore.setAutomunito(automunito.getValue());
            if (data_inizio.getValue() == null) {
                throw new JavaFXDataError("Data inizio non valida");
            }
            lavoratore.setInizio_disponibile(Date.valueOf(data_inizio.getValue()));
            if (data_fine.getValue() == null) {
                throw new JavaFXDataError("Data fine non valida");
            }
            lavoratore.setFine_disponibile(Date.valueOf(data_inizio.getValue()));
            lavoratore.setNome_emergenze(em_nome.getText());
            lavoratore.setCognome_emergenze(em_cognome.getText());
            if (telefono.getValue() == null) {
                throw new JavaFXDataError("Telefono emergenze non valido");
            }
            lavoratore.setTelefono_emergenze(telefono.getValue());
            lavoratore.setEmail_emergenze(em_email.getText());
            if (!lavoratore.validate()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERRORE");
                alert.setHeaderText("Campo vuoto o errore nell'inserimento di un dato");
            }
            try {
                Main.getDataRepo().setLavoratore_id(postDriver.addLavoratore(lavoratore));
                Main.getLoader().loadView("AGG_LAV_OPZ");
            } catch (SQLException e) {
                throw new JavaFXDataError("Database Error!");
            }
        }catch (JavaFXDataError e){
            e.printFX();
        }



    }
    public void generatore_id_lav(ActionEvent event) {

    }

    public void back(ActionEvent event) {
        Main.getLoader().loadView("MENU");
    }






    public void comune_search(KeyEvent event) {
        //Todo fix error clear when pressed space
        if(event.getCode().equals(KeyCode.ENTER)){
            List<Comune> comuni;
            try {
                comuni = postDriver.getComuneILike(comune.getEditor().getText(),20);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            comune.getItems().clear();
            comune.getItems().addAll(comuni.stream()
                    .map(Comune::getNome_comune)
                    .toList());
            comune.show();
        }
    }



}
