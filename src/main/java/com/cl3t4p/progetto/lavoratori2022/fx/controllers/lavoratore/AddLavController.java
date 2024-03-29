package com.cl3t4p.progetto.lavoratori2022.fx.controllers.lavoratore;

import com.cl3t4p.progetto.lavoratori2022.Main;
import com.cl3t4p.progetto.lavoratori2022.functions.validation.RegexChecker;
import com.cl3t4p.progetto.lavoratori2022.repo.DataRepo;
import com.cl3t4p.progetto.lavoratori2022.exception.JavaFXDataError;
import com.cl3t4p.progetto.lavoratori2022.fx.components.numbertf.LongTextField;
import com.cl3t4p.progetto.lavoratori2022.repo.EmergenzaRepo;
import com.cl3t4p.progetto.lavoratori2022.repo.LavoratoreRepo;
import com.cl3t4p.progetto.lavoratori2022.type.Dipendente;
import com.cl3t4p.progetto.lavoratori2022.type.Emergenza;
import com.cl3t4p.progetto.lavoratori2022.type.Lavoratore;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.ResourceBundle;


@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddLavController implements Initializable {


    final LavoratoreRepo lavRepo = Main.getRepo().getLavoratoreRepo();
    final EmergenzaRepo emeRepo = Main.getRepo().getEmergenzaRepo();
    final DataRepo dataRepo = Main.getDataRepo();

    @FXML
    GridPane eme_pane;
    @FXML
    Button main_button,remove_button;
    @FXML
    DatePicker data_inizio, data_fine, data_nascita;
    @FXML
    Label email_invalida, tel_invalido, id_dipendente, nascita_invalida, data_in_invalida, data_fin_invalida, id_lavoratore;
    @FXML
    TextField nome, cognome, luogo_nascita, nazionalita, indirizzo, email, em_nome, em_cognome, em_email;
    @FXML
    LongTextField telefono, em_telefono;
    @FXML
    ChoiceBox<String> automunito;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        email.focusedProperty().addListener(this::check_email);
        telefono.focusedProperty().addListener(this::check_number);
        data_nascita.setOnAction(this::checkNascita);

        Dipendente dipendente = dataRepo.getDipendente();
        id_dipendente.setText(id_dipendente.getText() + dipendente.getId());


        if (dataRepo.getLavoratore_id() != null)
            setupModify();
    }


    private void setupModify() {
        remove_button.setVisible(true);
        id_lavoratore.setText(id_lavoratore.getText() + dataRepo.getLavoratore_id());
        id_lavoratore.setVisible(true);
        eme_pane.setVisible(false);
        main_button.setText("CONFERMA MODIFICHE");
        main_button.setOnAction(this::modifica_lavoratore);
        setupLavoratore();
    }

    /**
     * This method will fill the fields with the data of the lavoratore that is being edited.
     */
    private void setupLavoratore() {
        Lavoratore lavoratore = lavRepo.getLavoratoreByID(dataRepo.getLavoratore_id());
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



    /***
     * This method will return a Emergenza object with the data entered in the fields and it will check if the data is valid.
     * @throws JavaFXDataError if the data is not valid.
     */
    private Emergenza getEmergenza() throws JavaFXDataError {
        Emergenza emergenza = new Emergenza();
        emergenza.setNome(em_nome.getText());
        emergenza.setCognome(em_cognome.getText());
        emergenza.setEmail(em_email.getText());
        if (em_telefono.getValue() == null) {
            throw new JavaFXDataError("Telefono emergenza non valido!");
        }
        emergenza.setTelefono(em_telefono.getValue());
        if (!emergenza.validate()) {
            throw new JavaFXDataError("Campi emergenza non compilati o errati");
        }
        return emergenza;
    }

    /***
     * This method will return a Lavoratore object with the data entered in the fields and it will check if the data is valid.
     * @throws JavaFXDataError if the data is not valid.
     */
    private Lavoratore getLavoratore() throws JavaFXDataError {
        Lavoratore lavoratore = new Lavoratore();
        lavoratore.setNome(nome.getText());
        lavoratore.setCognome(cognome.getText());
        lavoratore.setLuogo_nascita(luogo_nascita.getText());
        if (checkData(data_nascita))
            throw new JavaFXDataError("Data di nascita non valida");

        lavoratore.setData_nascita(Date.valueOf(data_nascita.getValue()));
        lavoratore.setNazionalita(nazionalita.getText());
        lavoratore.setIndirizzo(indirizzo.getText());
        if (telefono.getValue() == null)
            throw new JavaFXDataError("Telefono non valido");

        lavoratore.setTelefono(telefono.getValue());
        lavoratore.setEmail(email.getText());
        lavoratore.setAutomunito(automunito.getValue());
        if (checkData(data_inizio))
            throw new JavaFXDataError("Data inizio non valida");

        lavoratore.setInizio_disponibile(Date.valueOf(data_inizio.getValue()));
        if (checkData(data_fine))
            throw new JavaFXDataError("Data fine non valida");

        lavoratore.setFine_disponibile(Date.valueOf(data_fine.getValue()));
        if (telefono.getValue() == null)
            throw new JavaFXDataError("Telefono emergenze non valido");

        if (!lavoratore.validate())
            throw new JavaFXDataError("Campo vuoto o errore nell'inserimento di un dato");
        if(data_inizio.getValue().isAfter(data_fine.getValue()))
            throw new JavaFXDataError("La data di inizio non puó essere dopo la data di fine");
        if(data_nascita.getValue().isAfter(LocalDate.now()))
            throw new JavaFXDataError("La data di nascita non puó essere dopo la data odierna");
        lavoratore.setId_dipendente(dataRepo.getDipendente().getId());
        return lavoratore;
    }

    private void modifica_lavoratore(ActionEvent event) {
        try {
            Lavoratore lavoratore = getLavoratore();
            lavoratore.setId(dataRepo.getLavoratore_id());
            try {
                lavRepo.updateLavoratore(lavoratore);
                Main.getLoader().loadView("LAVORATORE");

            } catch (SQLException e) {
                throw new JavaFXDataError("Database Error!");
            }
        } catch (JavaFXDataError e) {
            e.show();
        }
    }

    @FXML
    private void inserimento_lavoratore(ActionEvent event) {
        try {
            Lavoratore lavoratore = getLavoratore();
            Emergenza emergenza = getEmergenza();
            try {
                int id = lavRepo.addLavoratore(lavoratore);
                dataRepo.setLavoratore_id(id);
                emeRepo.addEmergenza(emergenza, id);
                Main.getLoader().loadView("LAVORATORE");
            } catch (SQLException e) {
                throw new JavaFXDataError("Database Error!");
            }
        } catch (JavaFXDataError e) {
            e.show();
        }
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

    @FXML
    private void back(ActionEvent event) {
        Main.getDataRepo().setLavoratore_id(null);
        Main.getLoader().loadView("MENU");
    }

    @FXML
    private void extraMenu(ActionEvent event) {
        Main.getLoader().loadView("MENU_OPZ");
    }

    @FXML
    private void emergenzeMenu(ActionEvent event) {
        Main.getLoader().loadView("MENU_EMERGENZE");
    }

    @FXML
    private void delLav(ActionEvent event) {
        ButtonType si = new ButtonType("Si", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.NONE,
                "Si è sicuri di voler eliminare il lavoratore?",
                si,
                no);

        alert.setTitle("Conferma");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.orElse(si) == si) {
            int id = dataRepo.getLavoratore_id();
            lavRepo.delLavoratore(id);
            dataRepo.setLavoratore_id(null);
            Main.getLoader().loadView("MENU");
        }

    }
}
