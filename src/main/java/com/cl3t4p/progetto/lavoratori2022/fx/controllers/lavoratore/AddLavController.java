package com.cl3t4p.progetto.lavoratori2022.fx.controllers.lavoratore;

import com.cl3t4p.progetto.lavoratori2022.repo.DataRepo;
import com.cl3t4p.progetto.lavoratori2022.data.type.Emergenza;
import com.cl3t4p.progetto.lavoratori2022.data.type.Lavoratore;
import com.cl3t4p.progetto.lavoratori2022.database.exception.JavaFXDataError;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import com.cl3t4p.progetto.lavoratori2022.Main;
import com.cl3t4p.progetto.lavoratori2022.data.checks.RegexChecker;
import com.cl3t4p.progetto.lavoratori2022.database.PostDriver;
import com.cl3t4p.progetto.lavoratori2022.fx.components.NumberField;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;


public class AddLavController implements Initializable {


    PostDriver postDriver = Main.getPostDriver();
    DataRepo dataRepo = Main.getDataRepo();

    @FXML
    public GridPane eme_pane;
    @FXML
    private Button main_button;
    @FXML
    private DatePicker data_inizio, data_fine, data_nascita;
    @FXML
    private Label email_invalida, tel_invalido, id_dipendente, nascita_invalida, data_in_invalida, data_fin_invalida, id_lavoratore;
    @FXML
    private TextField nome, cognome, luogo_nascita, nazionalita, indirizzo, email, em_nome, em_cognome, em_email;
    @FXML
    private NumberField telefono, em_telefono;
    @FXML
    private ChoiceBox<String> automunito;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        email.focusedProperty().addListener(this::check_email);
        telefono.focusedProperty().addListener(this::check_number);
        data_nascita.setOnAction(this::checkNascita);

        if (dataRepo.getDipendente() != null) {
            id_dipendente.setText(id_dipendente.getText() + dataRepo.getDipendente().getId());
            id_dipendente.setVisible(true);
        }
        if (dataRepo.getLavoratore_id() != null) {
            id_lavoratore.setText(id_lavoratore.getText() + dataRepo.getLavoratore_id());
            id_lavoratore.setVisible(true);

            eme_pane.setVisible(false);

            main_button.setText("MODIFICA LAVORATORE");
            main_button.setOnAction(this::modifica_lavoratore);

            try {
                setupLavoratore();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //Insert default values of the lavoratore
    private void setupLavoratore() throws SQLException {
        Lavoratore lavoratore = postDriver.getLavoratoreByID(dataRepo.getLavoratore_id());
        nome.setText(lavoratore.getNome());
        cognome.setText(lavoratore.getCognome());
        luogo_nascita.setText(lavoratore.getLuogo_nascita());
        data_nascita.setValue(lavoratore.getData_nascita().toLocalDate());
        nazionalita.setText(lavoratore.getNazionalita());
        indirizzo.setText(lavoratore.getIndirizzo());
        telefono.setText(String.valueOf(lavoratore.getTelefono()));
        email.setText(lavoratore.getEmail());
        data_inizio.setValue(lavoratore.getInizio_disponibile().toLocalDate());
        data_fine.setValue(lavoratore.getFine_disponibile().toLocalDate());
        automunito.setValue(lavoratore.getAutomunito());
    }


    @FXML
    private void checkNascita(ActionEvent event) {
        if (data_nascita.getValue() != null)
            nascita_invalida.setVisible(Date.valueOf(data_nascita.getValue()).after(Date.valueOf(LocalDate.now())));
    }

    @FXML
    private void checkDate(ActionEvent event) {
        if (data_inizio.getValue() == null || data_fine.getValue() == null) {
            nascita_invalida.setVisible(false);
            return;
        }
        data_fin_invalida.setVisible(Date.valueOf(data_inizio.getValue()).after(Date.valueOf(data_fine.getValue())));
        data_in_invalida.setVisible(Date.valueOf(data_inizio.getValue()).after(Date.valueOf(data_fine.getValue())));
    }


    private void check_number(Observable observable) {
        if (telefono.isFocused()) return;
        if (telefono.getText().isEmpty()) {
            tel_invalido.setVisible(false);
            return;
        }
        tel_invalido.setVisible(!RegexChecker.TEL_NUMBER.validate(telefono.getText()));
    }

    private void check_email(Observable observable) {
        if (email.isFocused()) return;
        if (email.getText().isEmpty()) {
            email_invalida.setVisible(false);
            return;
        }
        email_invalida.setVisible(!RegexChecker.EMAIL.validate(email.getText()));
    }

    private boolean checkData(DatePicker date) {
        try {
            date.getValue();
        } catch (DateTimeParseException e) {
            return true;
        }
        return date.getValue() == null;
    }


    private void modifica_lavoratore(ActionEvent event) {
        try {
            Lavoratore lavoratore = getLavoratore();
            lavoratore.setId(dataRepo.getLavoratore_id());
            try {
                dataRepo.setLavoratore_id(postDriver.updateLavoratore(lavoratore));
                Main.getLoader().loadView("AGGIUNGI_LAVORATORE");

            } catch (SQLException e) {
                throw new JavaFXDataError("Database Error!");
            }
        } catch (JavaFXDataError e) {
            e.printFX();
        }
    }

    @FXML
    private void inserimento_lavoratore(ActionEvent event) {
        try {
            Lavoratore lavoratore = getLavoratore();
            Emergenza emergenza = getEmergenza();
            try {
                int id = postDriver.addLavoratore(lavoratore);
                dataRepo.setLavoratore_id(id);
                postDriver.addEmergenza(emergenza, id);
                Main.getLoader().loadView("AGGIUNGI_LAVORATORE");
            } catch (SQLException e) {
                throw new JavaFXDataError("Database Error!");
            }
        } catch (JavaFXDataError e) {
            e.printFX();
        }
    }

    private Emergenza getEmergenza() throws JavaFXDataError {
        Emergenza emergenza = new Emergenza();
        emergenza.setNome(em_nome.getText());
        emergenza.setCognome(em_cognome.getText());
        emergenza.setEmail(em_email.getText());
        emergenza.setTelefono(em_telefono.getValue());
        if (em_telefono.getValue() == null) {
            throw new JavaFXDataError("Telefono emergenza non valido!");
        }
        if (!emergenza.validate()) {
            throw new JavaFXDataError("Campi emergenza non compilati o errati");
        }
        return emergenza;
    }

    private Lavoratore getLavoratore() throws JavaFXDataError {
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
        if (telefono.getValue() == null) {
            throw new JavaFXDataError("Telefono emergenze non valido");
        }
        if (!lavoratore.validate()) {
            throw new JavaFXDataError("Campo vuoto o errore nell'inserimento di un dato");
        }
        return lavoratore;
    }

    @FXML
    private void back(ActionEvent event) {
        Main.getLoader().loadView("MENU_LAVORATORE");
    }

    @FXML
    private void extraMenu(ActionEvent event) {
        Main.getLoader().loadView("AGG_LAV_OPZ");
    }

    @FXML
    private void emergenzeMenu(ActionEvent event) {
        Main.getLoader().loadView("MENU_EMERGENZE");
    }


}
