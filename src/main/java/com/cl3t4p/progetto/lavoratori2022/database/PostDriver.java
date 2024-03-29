package com.cl3t4p.progetto.lavoratori2022.database;

import com.cl3t4p.progetto.lavoratori2022.database.filter.FilterBuilder;
import com.cl3t4p.progetto.lavoratori2022.database.filter.PostFilterBuilder;
import com.cl3t4p.progetto.lavoratori2022.database.postsql.*;
import com.cl3t4p.progetto.lavoratori2022.repo.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostDriver implements MainRepo {
    private final String user, pass, db_name, host;
    private final int port;
    private final DataSource source;

    private Connection connection;

    PostComune postComune = new PostComune(this);
    PostDipendente postDipendente = new PostDipendente(this);
    PostEmergenza postEmergenza = new PostEmergenza(this);
    PostEsperienza postEsperienza = new PostEsperienza(this);
    PostLavoratore postLavoratore = new PostLavoratore(this);
    LavoroRepo postLavoro = new PostLavoro(this);
    LinguaRepo postLingua = new PostLingua(this);
    PostPatente postPatente = new PostPatente(this);

    public PostDriver(String user, String pass, String db_name, String host, int port) {
        this.user = user;
        this.pass = pass;
        this.db_name = db_name;
        this.host = host;
        this.port = port;
        source = null;

    }

    public PostDriver(DataSource source) {
        this.user = "";
        this.pass = "";
        this.db_name = "";
        this.host = "";
        this.port = 0;
        this.source = source;
    }


    @Override
    public Connection getConnection() {
        if(source != null) {
            try {
                connection = source.getConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (connection == null)
            try {
                connection = getNewConnection();
            } catch (ClassNotFoundException | SQLException e) {
                throw new RuntimeException(e);
            }
        return connection;
    }

    private Connection getNewConnection() throws SQLException, ClassNotFoundException {
        if(source != null) {
            try {
                return source.getConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(String.format("jdbc:postgresql://%s:%d/%s", host, port, db_name), user,
                pass);
    }


    @Override
    public boolean testConnection() {
        try {
            getNewConnection();
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ComuneRepo getComuneRepo() {
        return postComune;
    }

    @Override
    public EmergenzaRepo getEmergenzaRepo() {
        return postEmergenza;
    }

    @Override
    public EsperienzaRepo getEsperienzaRepo() {
        return postEsperienza;
    }

    @Override
    public LavoratoreRepo getLavoratoreRepo() {
        return postLavoratore;
    }

    @Override
    public LavoroRepo getLavoroRepo() {
        return postLavoro;
    }

    @Override
    public LinguaRepo getLinguaRepo() {
        return postLingua;
    }

    @Override
    public PatenteRepo getPatenteRepo() {
        return postPatente;
    }

    @Override
    public DipendenteRepo getDipendenteRepo() {
        return postDipendente;
    }

    @Override
    public FilterBuilder getFilterBuilderInstance() {
        return new PostFilterBuilder();
    }

}

