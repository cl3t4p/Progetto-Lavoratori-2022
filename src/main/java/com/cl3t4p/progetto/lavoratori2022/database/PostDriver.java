package com.cl3t4p.progetto.lavoratori2022.database;

import com.cl3t4p.progetto.lavoratori2022.data.type.Dipendente;
import com.cl3t4p.progetto.lavoratori2022.data.type.Emergenza;
import com.cl3t4p.progetto.lavoratori2022.data.type.Lavoratore;
import com.cl3t4p.progetto.lavoratori2022.data.type.Lavoro;
import com.cl3t4p.progetto.lavoratori2022.model.LavModel;

import java.sql.*;
import java.util.*;

public class PostDriver implements LavModel {
    private final String user, pass, db_name, host;
    private final int port;
    private Connection connection;

    public PostDriver(String user, String pass, String db_name, String host, int port) {
        this.user = user;
        this.pass = pass;
        this.db_name = db_name;
        this.host = host;
        this.port = port;
    }

    public Connection getConnection() {
        if (connection == null)
            try {
                connection = getNewConnectino();
            } catch (ClassNotFoundException | SQLException e) {
                throw new RuntimeException(e);
            }
        return connection;
    }

    private Connection getNewConnectino() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(String.format("jdbc:postgresql://%s:%d/%s", host, port, db_name), user,
                pass);
    }

    public boolean testConnection() {
        try {
            getNewConnectino();
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Lavoratore getLavoratoreByID(int id) {
        String sql = "SELECT * FROM lavoratore WHERE id=?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql);) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next())
                return null;
            Lavoratore lavoratore;
            try {
                lavoratore = SQLMapper.deserializeSQL(resultSet, Lavoratore.class);
            } catch (IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
            return lavoratore;
        } catch (SQLException e) {
            return null;
        }

    }

    public int login(String username, String password) throws SQLException {
        String sql = "SELECT id FROM dipendente WHERE username=? and password=?";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setString(1, username);
        statement.setString(2, password);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        return resultSet.getInt(1);
    }

    public int addLavoratore(Lavoratore lavoratore) throws SQLException {
        String sql = "INSERT INTO lavoratore" +
                "(nome, cognome, luogo_nascita, data_nascita, nazionalita, indirizzo" +
                ", telefono, email, automunito, inizio_periodo_disp, fine_periodo_disp" +
                ",id_dipendente)" +
                " VALUES (?,?,?,?,?,?,?,?,?,?,?,?) RETURNING id;";
        List<String> fields = List.of("nome", "cognome", "luogo_nascita", "data_nascita", "nazionalita", "indirizzo",
                "telefono", "email", "automunito",
                "inizio_disponibile", "fine_disponibile", "id_dipendente");
        PreparedStatement statement = getConnection().prepareStatement(sql);
        try {
            SQLMapper.serializeSQL(statement, lavoratore, fields);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        ResultSet set = statement.executeQuery();
        set.next();
        return set.getInt(1);
    }

    public Integer updateLavoratore(Lavoratore lavoratore) throws SQLException {
        String sql = "UPDATE lavoratore SET " +
                "nome=?, cognome=?, luogo_nascita=?, data_nascita=?, nazionalita=?, indirizzo=?, telefono=?, email=?, automunito=?, inizio_periodo_disp=?, fine_periodo_disp=? WHERE id=?";
        List<String> fields = List.of("nome", "cognome", "luogo_nascita", "data_nascita", "nazionalita", "indirizzo",
                "telefono", "email", "automunito",
                "inizio_disponibile", "fine_disponibile");
        PreparedStatement statement = getConnection().prepareStatement(sql);
        try {
            SQLMapper.serializeSQL(statement, lavoratore, fields);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        statement.setInt(12, lavoratore.getId());
        return statement.executeUpdate();
    }

    public void removeLavoratore(int id) throws SQLException {
        String sql = "DELETE FROM lavoratore WHERE id=?";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
    }

    public Set<Lavoratore> getAllLavoratori(int limit) throws SQLException {
        String sql = "SELECT * from lavoratore LIMIT ?";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setInt(1, limit);
        ResultSet resultSet = statement.executeQuery();
        return getAllLavoratori(resultSet);
    }

    public Set<Lavoratore> getAllLavoratori() throws SQLException {
        String sql = "SELECT * from lavoratore";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        return getAllLavoratori(resultSet);
    }

    private Set<Lavoratore> getAllLavoratori(ResultSet resultSet) throws SQLException {
        Set<Lavoratore> lavoratori = new HashSet<>();
        while (resultSet.next()) {
            try {
                lavoratori.add(SQLMapper.deserializeSQL(resultSet, Lavoratore.class));
            } catch (IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        }
        return lavoratori;
    }

    public List<String> getPatentiByID(int id) {
        String sql = "SELECT pat_lav.nome_patente FROM lavoratore INNER JOIN pat_lav ON(lavoratore.id=pat_lav.id_lavoratore) WHERE lavoratore.id=?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql);){
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            List<String> patenti = new ArrayList<>();
            while (resultSet.next())
                patenti.add(resultSet.getString(1));
            return patenti;
        } catch (SQLException e) {
            return List.of();
        }

    }

    public void addPatenteByID(int id, String patente) throws SQLException {
        String sql = "INSERT INTO pat_lav (id_lavoratore, nome_patente) VALUES (?, ?)";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setInt(1, id);
        statement.setString(2, patente);
        statement.executeUpdate();
    }

    public boolean delPatenteByID(int id, String patente)  {
        String sql = "DELETE FROM pat_lav WHERE ( id_lavoratore = ? AND nome_patente = ?);";
        try(PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.setString(2, patente);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public String getComuniByName(String name) throws SQLException {
        String sql = "SELECT DISTINCT * FROM comune WHERE nome_comune ILIKE ?";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setString(1, name.toUpperCase(Locale.ROOT));
        ResultSet resultSet = statement.executeQuery();
        if (!resultSet.next())
            return "";
        return resultSet.getString(1);
    }

    public List<String> getAllPatenti() {
        String sql = "SELECT * FROM patente";
        try(PreparedStatement statement = getConnection().prepareStatement(sql);) {
            ResultSet resultSet = statement.executeQuery();
            List<String> patenti = new ArrayList<>();
            while (resultSet.next())
                patenti.add(resultSet.getString(1));
            return patenti.stream().sorted().toList();
        } catch (SQLException ignored) {
            return List.of();
        }
    }

    public Dipendente getDipendenteByUserAndPassword(String user, String pass) throws SQLException {
        String sql = "SELECT * FROM dipendente WHERE username=? and password=?";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setString(1, user);
        statement.setString(2, pass);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next())
            try {
                return (SQLMapper.deserializeSQL(resultSet, Dipendente.class));
            } catch (IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        return null;
    }

    public String getLinguaLike(String name) throws SQLException {
        ;
        String sql = "SELECT DISTINCT nome_lingua FROM lingua WHERE nome_lingua ILIKE ?";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();
        if (!resultSet.next())
            return "";
        return resultSet.getString(1);
    }

    private void addLingua(String lingua) throws SQLException {
        String sql = "INSERT INTO lingua (nome_lingua) VALUES (?)";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setString(1, lingua);
        statement.executeUpdate();
    }

    public void addLinguaByID(int id_lavoratore, String lingua) throws SQLException {
        System.out.println(lingua);
        String key = getLinguaLike(lingua);
        System.out.println("Test");
        System.out.println(key);
        if (key.isEmpty()) {
            key = lingua.substring(0, 1).toUpperCase() + lingua.substring(1);
            addLingua(key);
        }
        String sql = "INSERT INTO lingua_lav(nome_lingua,id_lavoratore) VALUES(?,?)";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setString(1, key);
        statement.setInt(2, id_lavoratore);
        statement.executeUpdate();
    }

    public boolean delLinguaByID(int lav_id, String key){
        String sql = "DELETE FROM lingua_lav WHERE ( id_lavoratore = ? AND nome_lingua = ?);";
        try(PreparedStatement statement = getConnection().prepareStatement(sql);) {
            statement.setInt(1, lav_id);
            statement.setString(2, key);
            return statement.executeUpdate() > 0;
        }catch (SQLException e){
            return false;
        }

    }

    public List<String> getLingueByID(int id) {
        String sql = "SELECT lingua_lav.nome_lingua FROM lavoratore INNER JOIN lingua_lav ON(lavoratore.id=lingua_lav.id_lavoratore) WHERE lavoratore.id=?";
        return getListString(id, sql);
    }

    public String getEspLike(String name) throws SQLException {
        ;
        String sql = "SELECT DISTINCT nome_esperienza FROM esperienza WHERE nome_esperienza ILIKE ?";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();
        if (!resultSet.next())
            return "";
        return resultSet.getString(1);
    }

    private void addEsp(String esp) throws SQLException {
        String sql = "INSERT INTO esperienza (nome_esperienza) VALUES (?)";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setString(1, esp);
        statement.executeUpdate();
    }

    public void addEspByID(int id_lavoratore, String esperienza) throws SQLException {
        String key = getEspLike(esperienza);
        if (key.isEmpty()) {
            key = esperienza;
            addEsp(key);
        }
        String sql = "INSERT INTO esp_lav(esperienza,id_lavoratore) VALUES(?,?)";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, key);
            statement.setInt(2, id_lavoratore);
            statement.executeUpdate();
        }
    }

    public boolean delEspByID(int lav_id, String key) {
        String sql = "DELETE FROM esp_lav WHERE ( id_lavoratore = ? AND esperienza = ?);";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, lav_id);
            statement.setString(2, key);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public List<String> getEspByID(int id) {
        String sql = "SELECT esp_lav.esperienza FROM lavoratore INNER JOIN esp_lav ON(lavoratore.id=esp_lav.id_lavoratore) WHERE lavoratore.id=?";
        return getListString(id, sql);
    }

    public boolean delComunebyID(int lav_id, String key) {
        String sql = "DELETE FROM lav_comune WHERE ( id_lavoratore = ? AND comune = ?);";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, lav_id);
            statement.setString(2, key);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
           return false;
        }
    }

    public void addComuneByID(String comune, int id_lavoratore) throws SQLException {
        String sql = "INSERT INTO lav_comune(comune,id_lavoratore) VALUES(?,?)";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, comune.toUpperCase());
            statement.setInt(2, id_lavoratore);
            statement.executeUpdate();
        }
    }

    public List<String> getComuniByID(int lavoratore_id){
        String sql = "SELECT lav_comune.comune FROM lavoratore INNER JOIN lav_comune ON(lavoratore.id=lav_comune.id_lavoratore) WHERE lavoratore.id=?";
        return getListString(lavoratore_id, sql);
    }

    private List<String> getListString(int lavoratore_id, String sql) {
        ResultSet resultSet;
        try(PreparedStatement statement = getConnection().prepareStatement(sql);) {

            statement.setInt(1, lavoratore_id);
            resultSet = statement.executeQuery();
            List<String> list = new ArrayList<>();
            while (resultSet.next())
                list.add(resultSet.getString(1));
            return list.stream().sorted().toList();
        } catch (SQLException e) {
           return List.of();
        }
    }

    public List<String> getComuneILike(String name) throws SQLException {
        name = "%" + name + "%";
        String sql = "SELECT * FROM comune WHERE nome_comune ILIKE ?";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setString(1, name.toUpperCase(Locale.ROOT));
        return getAllComuni(statement);
    }

    public List<String> getComuneILike(String name, int limit) throws SQLException {
        name = "%" + name + "%";
        String sql = "SELECT * FROM comune WHERE nome_comune ILIKE ? LIMIT ?";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setString(1, name.toUpperCase(Locale.ROOT));
        statement.setInt(2, limit);
        return getAllComuni(statement);
    }

    private List<String> getAllComuni(PreparedStatement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery();
        List<String> list = new ArrayList<>();
        while (resultSet.next())
            list.add(resultSet.getString(1));
        return list.stream().sorted().toList();
    }

    private int addEmergenza(Emergenza emergenza) throws SQLException {
        int id = getEmergenzeIDByValues(emergenza.toMap());
        if (id == -1) {
            String sql = "INSERT INTO emergenza (nome,cognome,telefono,email) VALUES (?,?,?,?)RETURNING id;";
            PreparedStatement statement = getConnection().prepareStatement(sql);
            statement.setString(1, emergenza.getNome());
            statement.setString(2, emergenza.getCognome());
            statement.setLong(3, emergenza.getTelefono());
            statement.setString(4, emergenza.getEmail());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                id = resultSet.getInt(1);
        }
        return id;
    }

    private int getEmergenzeIDByValues(Map<String, String> emergenza) throws SQLException {
        String sql = "SELECT id FROM emergenza WHERE nome=? AND cognome=? AND telefono=? AND email=?";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setString(1, emergenza.get("nome"));
        statement.setString(2, emergenza.get("cognome"));
        statement.setLong(3, Long.parseLong(emergenza.get("telefono")));
        statement.setString(4, emergenza.get("email"));
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return -1;
    }

    public boolean addEmergenza(Emergenza emergenza, Integer id) throws SQLException {
        int em_id = addEmergenza(emergenza);

        if (checkIfEmergenzaIsLinked(em_id, id))
            return false;

        String sql = "INSERT INTO emergenza_lav (id_emergenza,id_lavoratore) VALUES (?,?)";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setInt(1, em_id);
        statement.setInt(2, id);
        statement.executeUpdate();
        return true;
    }

    private boolean checkIfEmergenzaIsLinked(int id_lavoratore, int id_emergenza) throws SQLException {
        String sql = "SELECT * FROM emergenza_lav WHERE id_lavoratore=? AND id_emergenza=?";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setInt(1, id_lavoratore);
        statement.setInt(2, id_emergenza);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }

    public List<Emergenza> getEmergenze(int lavoratore_id) {
        String sql = "SELECT emergenza.id,emergenza.nome,emergenza.cognome,emergenza.telefono,emergenza.email FROM emergenza INNER JOIN emergenza_lav ON(emergenza.id=emergenza_lav.id_emergenza) WHERE emergenza_lav.id_lavoratore=?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, lavoratore_id);
            ResultSet resultSet = statement.executeQuery();
            List<Emergenza> emergenze = new ArrayList<>();
            while (resultSet.next()) {
                try {
                    emergenze.add(SQLMapper.deserializeSQL(resultSet, Emergenza.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return emergenze;
        } catch (SQLException e) {
            return List.of();
        }
    }

    public boolean delEmergenzeByID(int lav_id, Map<String, String> emergenza) {
        String sql = "DELETE FROM emergenza_lav WHERE id_lavoratore=? AND id_emergenza=?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            int eme_id = getEmergenzeIDByValues(emergenza);
            statement.setInt(1, lav_id);
            statement.setInt(2, eme_id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public Set<Lavoratore> researchLavoratore(ResearchCreator creator) throws SQLException {
        PreparedStatement statement = creator.getSQLStatment(getConnection());
        return getAllLavoratori(statement.executeQuery());
    }



    public int addLavoro(Lavoro lavoro) throws SQLException {
        String sql = "INSERT INTO lavoro_svolto " +
                "(inizio_periodo,fine_periodo,nome_azienda,mansione_svolta,luogo_lavoro,retribuzione_lorda_giornaliera ,id_lavoratore) "
                +
                "VALUES (?,?,?,?,?,?,?)RETURNING id;";
        List<String> fields = List.of("inizio_periodo", "fine_periodo", "nome_azienda", "mansione", "luogo",
                "retribuzione", "id_lavoratore");
        PreparedStatement statement = getConnection().prepareStatement(sql);
        try {
            SQLMapper.serializeSQL(statement, lavoro, fields);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next())
            return resultSet.getInt(1);
        return -1;
    }

    public List<Lavoro> getLavoroByLavID(Integer lavoratore_id) {
        String sql = "SELECT * FROM lavoro_svolto WHERE id_lavoratore=?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, lavoratore_id);
            ResultSet resultSet = statement.executeQuery();
            List<Lavoro> lavori = new ArrayList<>();
            while (resultSet.next()) {
                try {
                    lavori.add(SQLMapper.deserializeSQL(resultSet, Lavoro.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return lavori;
        } catch (SQLException e) {
            return List.of();
        }
    }

    public boolean deleteLavoroByID(Integer id)  {
        String sql = "DELETE FROM lavoro_svolto WHERE id=?";
        PreparedStatement statement = null;
        try {
            statement = getConnection().prepareStatement(sql);
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }
}

