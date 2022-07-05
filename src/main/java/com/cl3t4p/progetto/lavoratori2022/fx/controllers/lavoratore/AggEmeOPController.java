package com.cl3t4p.progetto.lavoratori2022.fx.controllers.lavoratore;

import com.cl3t4p.progetto.lavoratori2022.Main;
import com.cl3t4p.progetto.lavoratori2022.data.checks.RegexChecker;
import com.cl3t4p.progetto.lavoratori2022.fx.components.button.CellButtonFactoryFactory;
import com.cl3t4p.progetto.lavoratori2022.fx.components.button.ColumnAction;
import com.cl3t4p.progetto.lavoratori2022.type.Emergenza;
import com.cl3t4p.progetto.lavoratori2022.exception.JavaFXDataError;
import com.cl3t4p.progetto.lavoratori2022.fx.JavaFXError;
import com.cl3t4p.progetto.lavoratori2022.fx.components.LongTextField;
import com.cl3t4p.progetto.lavoratori2022.fx.components.TableData;
import com.cl3t4p.progetto.lavoratori2022.repo.EmergenzaRepo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;

public class AggEmeOPController implements Initializable {


    private final EmergenzaRepo emeRepo = Main.getRepo().getEmergenzaRepo();

    @FXML
    private TableData eme_view;
    @FXML
    private Label lav_id;
    @FXML
    private TableColumn<Map, String> col_nome, col_cognome, col_telefono, col_email;
    @FXML
    private TextField em_nome, em_cognome, em_email;
    @FXML
    private LongTextField em_telefono;
    private int lavoratore_id;


    @Override
    public void initialize(URL url, ResourceBundle resource) {
        lavoratore_id = Main.getDataRepo().getLavoratore_id();
        lav_id.setText(lav_id.getText() + lavoratore_id);


        CellButtonFactoryFactory factory = new CellButtonFactoryFactory(key -> {
            if (!emeRepo.delEmergenzeByID(lavoratore_id, key))
                JavaFXError.DB_ERROR.printContent("Errore nella cancellazione dell'emergenza");
            eme_view.refreshData();
            return null;
        });


        eme_view.setSupplier(() -> TableData.toMap(emeRepo.getEmergenze(lavoratore_id)));
        eme_view.setButtonColumn(factory.getCellFactory(ColumnAction.LAST_STANDING));
        eme_view.setupColumn(col_nome, "nome");
        eme_view.setupColumn(col_cognome, "cognome");
        eme_view.setupColumn(col_telefono, "telefono");
        eme_view.setupColumn(col_email, "email");
        eme_view.refreshData();

    }


    @FXML
    private void addEmergenza(ActionEvent event) {
        try {
            Emergenza emergenza = getEmergenza();
            if (!emeRepo.addEmergenza(emergenza, lavoratore_id)) throw new JavaFXDataError("Il contatto esiste già");
            eme_view.refreshData();
        } catch (JavaFXDataError e) {
            e.show();
        } catch (SQLException e) {
            JavaFXError.DB_ERROR.show();
        }

    }

    /***
     * This method will return a Emergenza object with the data entered in the fields and it will check if the data is valid.
     * @throws JavaFXDataError if the data is not valid.
     */
    private Emergenza getEmergenza() throws JavaFXDataError {
        Emergenza emergenza = new Emergenza();
        emergenza.setNome(em_nome.getText());
        emergenza.setCognome(em_cognome.getText());
        if (!RegexChecker.EMAIL.validate(em_email.getText())) {
            throw new JavaFXDataError("Email emergenza non valida!");
        }
        emergenza.setEmail(em_email.getText());

        if (em_telefono.getValue() == null || !RegexChecker.TEL_NUMBER.validate(em_telefono.getText())) {
            throw new JavaFXDataError("Telefono emergenza non valido!");
        }
        emergenza.setTelefono(em_telefono.getValue());
        if (!emergenza.validate()) {
            throw new JavaFXDataError("Campi emergenza non compilati o errati");
        }
        return emergenza;
    }

    public void back(ActionEvent event) {
        Main.getLoader().loadView("MODIFICA_AGG_LAVORATORE");
    }
}
