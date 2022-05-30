package progetto;


import progetto.data.CheckData;
import progetto.data.FieldChecker;
import progetto.data.RegexChecker;
import progetto.database.annotation.SQLDInfo;

import java.sql.Date;


//@Data
//@FieldDefaults(level = AccessLevel.PRIVATE)
public class Lavoratore implements CheckData {

    @FieldChecker
    int id,id_dipendente;
    String nome, cognome, luogo_nascita;
    Date data_nascita;
    String nazionalita, indirizzo;
    @FieldChecker
    long telefono;
    @FieldChecker
    String email;
    String automunito;
    @SQLDInfo(sql_name = "inizio_periodo_disp")
    Date inizio_disponibile;
    @SQLDInfo(sql_name = "fine_periodo_disp")
    Date fine_disponibile;
    String nome_emergenze;
    String cognome_emergenze;
    String email_emergenze;
    long telefono_emergenze;

    public Lavoratore() {
    }

    public boolean validate() {
        if(!CheckData.super.validate()){
            return false;
        }
        if(!RegexChecker.EMAIL.validate(email)){
            return false;
        }
        return RegexChecker.TEL_NUMBER.validate(String.valueOf(telefono));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public int getId_dipendente() {
        return id_dipendente;
    }

    public void setId_dipendente(int id_dipendente) {
        this.id_dipendente = id_dipendente;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getLuogo_nascita() {
        return luogo_nascita;
    }

    public void setLuogo_nascita(String luogo_nascita) {
        this.luogo_nascita = luogo_nascita;
    }

    public Date getData_nascita() {
        return data_nascita;
    }

    public void setData_nascita(Date data_nascita) {
        this.data_nascita = data_nascita;
    }

    public String getNazionalita() {
        return nazionalita;
    }

    public void setNazionalita(String nazionalita) {
        this.nazionalita = nazionalita;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAutomunito() {
        return automunito;
    }

    public void setAutomunito(String automunito) {
        this.automunito = automunito;
    }

    public Date getInizio_disponibile() {
        return inizio_disponibile;
    }

    public void setInizio_disponibile(Date inizio_disponibile) {
        this.inizio_disponibile = inizio_disponibile;
    }

    public Date getFine_disponibile() {
        return fine_disponibile;
    }

    public void setFine_disponibile(Date fine_disponibile) {
        this.fine_disponibile = fine_disponibile;
    }

    public String getNome_emergenze() {
        return nome_emergenze;
    }

    public void setNome_emergenze(String nome_emergenze) {
        this.nome_emergenze = nome_emergenze;
    }

    public String getCognome_emergenze() {
        return cognome_emergenze;
    }

    public void setCognome_emergenze(String cognome_emergenze) {
        this.cognome_emergenze = cognome_emergenze;
    }

    public String getEmail_emergenze() {
        return email_emergenze;
    }

    public void setEmail_emergenze(String email_emergenze) {
        this.email_emergenze = email_emergenze;
    }

    public long getTelefono_emergenze() {
        return telefono_emergenze;
    }

    public void setTelefono_emergenze(long telefono_emergenze) {
        this.telefono_emergenze = telefono_emergenze;
    }
}
