package progetto;

import progetto.database.annotation.SQLDInfo;
import progetto.database.exception.DatabaseDataError;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;

/**
 *
 * @author 1blan
 */

public class Lavoratore {
    int id;
    String nome, cognome, luogo_nascita;

    Date data_nascita;
    String nazionalita, indirizzo;
    int telefono;
    String mail, automunito;
    Date inizio_disponibile, fine_disponibile;
    String nome_emergenza, cognome_emergenza, mail_emergenza;
    int telefono_emergenza;

    public Lavoratore() {
    }

    public void setData_nascita(Date data_nascita) {
        this.data_nascita = data_nascita;
    }

    public void setInizio_disponibile(Date inizio_disponibile) {
        this.inizio_disponibile = inizio_disponibile;
    }

    public void setFine_disponibile(Date fine_disponibile) {
        this.fine_disponibile = fine_disponibile;
    }

    public Date getData_nascita() {
        return data_nascita;
    }

    public Date getInizio_disponibile() {
        return inizio_disponibile;
    }

    public Date getFine_disponibile() {
        return fine_disponibile;
    }    

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setLuogo_nascita(String luogo_nascita) {
        this.luogo_nascita = luogo_nascita;
    }

    public void setNazionalita(String nazionalita) {
        this.nazionalita = nazionalita;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setAutomunito(String automunito) {
        this.automunito = automunito;
    }

    public void setNome_emergenza(String nome_emergenza) {
        this.nome_emergenza = nome_emergenza;
    }

    public void setCognome_emergenza(String cognome_emergenza) {
        this.cognome_emergenza = cognome_emergenza;
    }

    public void setTelefono_emergenza(int telefono_emergenza) {
        this.telefono_emergenza = telefono_emergenza;
    }

    public void setMail_emergenza(String mail_emergenza) {
        this.mail_emergenza = mail_emergenza;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getLuogo_nascita() {
        return luogo_nascita;
    }

    public String getNazionalita() {
        return nazionalita;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public int getTelefono() {
        return telefono;
    }

    public String getMail() {
        return mail;
    }

    public String getAutomunito() {
        return automunito;
    }

    public String getNome_emergenza() {
        return nome_emergenza;
    }

    public String getCognome_emergenza() {
        return cognome_emergenza;
    }

    public int getTelefono_emergenza() {
        return telefono_emergenza;
    }

    public String getMail_emergenza() {
        return mail_emergenza;
    }
                 
    
}
