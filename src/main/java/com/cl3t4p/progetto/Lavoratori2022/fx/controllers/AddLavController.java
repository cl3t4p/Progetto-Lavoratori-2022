package com.cl3t4p.progetto.Lavoratori2022.fx.controllers;

import com.cl3t4p.progetto.Lavoratori2022.Lavoratore;
import com.cl3t4p.progetto.Lavoratori2022.database.exception.JavaFXDataError;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import com.cl3t4p.progetto.Lavoratori2022.Main;
import com.cl3t4p.progetto.Lavoratori2022.data.RegexChecker;
import com.cl3t4p.progetto.Lavoratori2022.database.PostDriver;
import com.cl3t4p.progetto.Lavoratori2022.database.exception.JavaFXError;
import com.cl3t4p.progetto.Lavoratori2022.fx.components.NumberField;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;


public class AddLavController implements Initializable {



    PostDriver postDriver = Main.getPostDriver();

    @FXML
    private Button main_button;
    @FXML
    private DatePicker data_inizio,data_fine,data_nascita;
    @FXML
    private Label email_invalida,tel_invalido,id_dipendente,nascita_invalida,data_in_invalida,data_fin_invalida,id_label,id_lavoratore;
    @FXML
    private TextField nome,cognome,luogo_nascita,nazionalita
            ,indirizzo,email
            ,em_nome,em_cognome,em_email;
    @FXML
    private NumberField telefono,em_telefono;
    @FXML
    private ChoiceBox<String> automunito;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        email.focusedProperty().addListener(this::check_email);
        telefono.focusedProperty().addListener(this::check_number);
        data_nascita.setOnAction(this::checkNascita);

        if(Main.getDataRepo().getDipendente() != null) {
            id_dipendente.setText(String.valueOf(Main.getDataRepo().getDipendente().getId()));
            id_label.setVisible(true);
        }
        if(Main.getDataRepo().getLavoratore_id() != null){
            id_lavoratore.setText(id_lavoratore.getText()+Main.getDataRepo().getLavoratore_id());
            id_lavoratore.setVisible(true);
            main_button.setText("MODIFICA LAVORATORE");
            main_button.setOnAction(this::modifica_lavoratore);
        }



    }


    @FXML
    private void checkNascita(ActionEvent event) {
        if(data_nascita.getValue() != null)
            nascita_invalida.setVisible(Date.valueOf(data_nascita.getValue()).after(Date.valueOf(LocalDate.now())));
    }

    @FXML
    private void checkDate(ActionEvent event){
        if(data_inizio.getValue() == null || data_fine.getValue() == null){
            nascita_invalida.setVisible(false);
            return;
        }
        data_fin_invalida.setVisible(Date.valueOf(data_inizio.getValue()).after(Date.valueOf(data_fine.getValue())));
        data_in_invalida.setVisible(Date.valueOf(data_inizio.getValue()).after(Date.valueOf(data_fine.getValue())));
    }



    private void check_number(Observable observable){
        if(telefono.isFocused())return;
        if(telefono.getText().isEmpty()) {
            tel_invalido.setVisible(false);
            return;
        }
        tel_invalido.setVisible(!RegexChecker.TEL_NUMBER.validate(telefono.getText()));
    }
    private void check_email(Observable observable) {
        if(email.isFocused())return;
        if(email.getText().isEmpty()) {
            email_invalida.setVisible(false);
            return;
        }
        email_invalida.setVisible(!RegexChecker.EMAIL.validate(email.getText()));
    }

    private boolean checkData(DatePicker date){
        try {
            date.getValue();
        }catch (DateTimeParseException e){
            return true;
        }
        return date.getValue() == null;
    }


    private void modifica_lavoratore(ActionEvent event){
        try {
            Lavoratore lavoratore = new Lavoratore();
            lavoratore.setNome(nome.getText());
            lavoratore.setCognome(cognome.getText());
            lavoratore.setLuogo_nascita(luogo_nascita.getText());
            if (checkData(data_nascita)) {
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
            if (checkData(data_inizio)) {
                throw new JavaFXDataError("Data inizio non valida");
            }
            lavoratore.setInizio_disponibile(Date.valueOf(data_inizio.getValue()));
            if (checkData(data_fine)) {
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
                JavaFXError.fxErrorMSG("Campo vuoto o errore nell'inserimento di un dato");
                return;
            }
            try {
                Main.getDataRepo().setLavoratore_id(postDriver.addLavoratore(lavoratore));
                Main.getLoader().loadView("AGGIUNGI_LAVORATORE");
            } catch (SQLException e) {
                throw new JavaFXDataError("Database Error!");
            }
        }catch (JavaFXDataError e){
            e.printFX();
        }
    }

    @FXML
    private void inserimento_lavoratore(ActionEvent event)  {
        try {
            Lavoratore lavoratore = new Lavoratore();
            lavoratore.setNome(nome.getText());
            lavoratore.setCognome(cognome.getText());
            lavoratore.setLuogo_nascita(luogo_nascita.getText());
            if (checkData(data_nascita)) {
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
            if (checkData(data_inizio)) {
                throw new JavaFXDataError("Data inizio non valida");
            }
            lavoratore.setInizio_disponibile(Date.valueOf(data_inizio.getValue()));
            if (checkData(data_fine)) {
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
                JavaFXError.fxErrorMSG("Campo vuoto o errore nell'inserimento di un dato");
                return;
            }
            try {
                Main.getDataRepo().setLavoratore_id(postDriver.addLavoratore(lavoratore));
                Main.getLoader().loadView("AGGIUNGI_LAVORATORE");
            } catch (SQLException e) {
                throw new JavaFXDataError("Database Error!");
            }
        }catch (JavaFXDataError e){
            e.printFX();
        }
    }

    @FXML
    private void back(ActionEvent event) {
        Main.getDataRepo().setLavoratore_id(null);
        Main.getLoader().loadView("MENU");
    }










}
