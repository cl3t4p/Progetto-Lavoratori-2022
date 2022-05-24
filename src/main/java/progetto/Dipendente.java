package progetto;

import progetto.database.annotation.SQLDInfo;

import java.time.LocalDate;

/**
 *
 * @author 1blan
 */
public class Dipendente {
    int id;
    String nome;
    String cognome;
    String luogo_nascita;
    LocalDate data_nascita;
    String nazionalita;
    String indirizzo;
    int telefono;
    @SQLDInfo(sql_name = "email")
    String mail;   
    String username;
    String password;   

    
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

    public void setData_nascita(LocalDate data_nascita) {
        this.data_nascita = data_nascita;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public LocalDate getData_nascita() {
        return data_nascita;
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

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
     
    
}
