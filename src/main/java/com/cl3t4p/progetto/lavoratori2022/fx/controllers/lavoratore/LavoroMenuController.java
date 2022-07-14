package com.cl3t4p.progetto.lavoratori2022.fx.controllers.lavoratore;

import com.cl3t4p.progetto.lavoratori2022.Main;
import com.cl3t4p.progetto.lavoratori2022.database.DataRepo;
import com.cl3t4p.progetto.lavoratori2022.exception.JavaFXDataError;
import com.cl3t4p.progetto.lavoratori2022.fx.JavaFXError;
import com.cl3t4p.progetto.lavoratori2022.fx.components.DoubleTextField;
import com.cl3t4p.progetto.lavoratori2022.fx.components.button.CellButtonFactoryFactory;
import com.cl3t4p.progetto.lavoratori2022.fx.components.button.ColumnAction;
import com.cl3t4p.progetto.lavoratori2022.fx.components.table.TableData;
import com.cl3t4p.progetto.lavoratori2022.repo.LavoroRepo;
import com.cl3t4p.progetto.lavoratori2022.type.Lavoro;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Map;
import java.util.ResourceBundle;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class LavoroMenuController implements Initializable {


    final LavoroRepo lavoroRepo = Main.getRepo().getLavoroRepo();

    final DataRepo dataRepo = Main.getDataRepo();
    @FXML
    TableData lav_view;

    @FXML
    DatePicker ini_per, fine_per;

    @FXML
    TableColumn<Map<String, String>, String> col_nome, col_mansione, col_luogo, col_retri, col_ini, col_fine, col_id;

    @FXML
    Label lav_id;

    @FXML
    TextField nome, luogo, mansione;

    @FXML
    DoubleTextField retri;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (dataRepo.getLavoratore_id() != null) {
            lav_id.setText(lav_id.getText() + dataRepo.getLavoratore_id());
            lav_id.setVisible(true);
        }

        CellButtonFactoryFactory factory = new CellButtonFactoryFactory(key -> {
            if (!lavoroRepo.deleteLavoroByID(Integer.valueOf(key.get("id"))))
                JavaFXError.DB_ERROR.printContent("Errore nella cancellazione dell'lavoro");
            lav_view.refreshData();
            return null;
        });


        lav_view.setSupplier(() -> TableData.toMap(lavoroRepo.getLavoroByLavID(dataRepo.getLavoratore_id())));
        lav_view.setButtonColumn(factory.getCellFactory(ColumnAction.REMOVE));

        lav_view.setupColumn(col_id, "id", 30);
        lav_view.setupColumn(col_nome, "nome_azienda");
        lav_view.setupColumn(col_mansione, "mansione");
        lav_view.setupColumn(col_luogo, "luogo");
        lav_view.setupColumn(col_retri, "retribuzione");
        lav_view.setupColumn(col_ini, "inizio_periodo");
        lav_view.setupColumn(col_fine, "fine_periodo");
    }


    public void back(ActionEvent event) {
        Main.getDataRepo().setLavoratore_id(null);
        Main.getLoader().loadView("MENU");
    }

    public void addLavoro(ActionEvent event) {
        try {
            Lavoro lavoro = getLavoro();
            if(!lavoro.validate())
            {
                JavaFXError.INVALID_DATA.printContent("Campi vuoti o errati");
                return;
            }
            LocalDate start = fine_per.getValue();
            LocalDate now = LocalDate.now();
            if (Period.between(start, now).getYears() > 5) {
                JavaFXError.INVALID_DATE.printContent("La data di fine periodo deve essere inferiore ai 5 anni");
                return;
            }
            if (lavoroRepo.addLavoro(lavoro) == -1) {
                throw new JavaFXDataError();
            }
        } catch (SQLException | JavaFXDataError e) {
            e.printStackTrace();
            JavaFXError.DB_ERROR.printContent("Errore nell'inserimento del lavoro");
        }
        lav_view.refreshData();
    }


    private Lavoro getLavoro() {
        Lavoro lavoro = new Lavoro();
        lavoro.setLuogo(luogo.getText());
        lavoro.setMansione(mansione.getText());
        lavoro.setNome_azienda(nome.getText());
        lavoro.setRetribuzione(retri.getValue().intValue());
        lavoro.setInizio_periodo(Date.valueOf(ini_per.getValue()));
        lavoro.setFine_periodo(Date.valueOf(fine_per.getValue()));
        lavoro.setId_lavoratore(dataRepo.getLavoratore_id());
        return lavoro;
    }
}
