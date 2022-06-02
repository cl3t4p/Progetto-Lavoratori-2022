package progetto.database;


import progetto.Dipendente;
import progetto.Lavoratore;
import progetto.Lavoro;

import java.sql.*;
import java.util.*;

public class PostDriver {
    private Connection connection;
    private final String user, pass, db_name, host;
    private final int port;

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
        return DriverManager.getConnection(String.format("jdbc:postgresql://%s:%d/%s", host, port, db_name), user, pass);
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

    public Lavoratore getLavoratoreByID(int id) throws SQLException {
        String sql = "SELECT * FROM lavoratore WHERE id=?";
        PreparedStatement statement = getConnection().prepareStatement(sql);
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
                ", telefono, email, automunito, inizio_periodo_disp, fine_periodo_disp, nome_emergenze" +
                ", cognome_emergenze, telefono_emergenze, email_emergenze,id_dipendente)" +
                " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) RETURNING id;";
        List<String> fields = List.of("nome", "cognome", "luogo_nascita", "data_nascita"
                , "nazionalita", "indirizzo", "telefono", "email","automunito",
                "inizio_disponibile","fine_disponibile","nome_emergenze","cognome_emergenze","telefono_emergenze","email_emergenze","id_dipendente");
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

    public void removeLavoratore(int id) throws SQLException {
        String sql="DELETE FROM lavoratore WHERE id=?";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setInt(1,id);
        statement.executeUpdate();
    }



    public Set<Lavoratore> getAllLavoratori(int limit) throws SQLException {
        String sql = "SELECT * from lavoratore LIMIT ?";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setInt(1,limit);
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
        while (resultSet.next()){
            try {
                lavoratori.add(SQLMapper.deserializeSQL(resultSet,Lavoratore.class));
            } catch (IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        }
        return lavoratori;
    }

    public Set<String> getPatentiByID(int id) throws SQLException {
        String sql = "SELECT pat_lav.nome_patente FROM lavoratore INNER JOIN pat_lav ON(lavoratore.id=pat_lav.id_lavoratore) WHERE lavoratore.id=?";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setInt(1,id);
        ResultSet resultSet = statement.executeQuery();
        Set<String> patenti = new HashSet<>();
        while (resultSet.next())
            patenti.add(resultSet.getString(1));
        return patenti;
    }
    public void addPatenteByID(int id, String patente) throws SQLException {
        String sql = "INSERT INTO pat_lav (id_lavoratore, nome_patente) VALUES (?, ?)";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setInt(1,id);
        statement.setString(2,patente);
        statement.executeUpdate();
    }
    public void delPatenteByID(int id, String patente) throws SQLException {
        String sql = "DELETE FROM pat_lav WHERE ( id_lavoratore = ? AND nome_patente = ?);";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setInt(1,id);
        statement.setString(2,patente);
        statement.executeUpdate();
    }


    public int addLavoro(Lavoro lavoro) throws SQLException {
        String sql = "INSERT INTO lavoro_svolto " +
                "(id,inizio_periodo,fine_periodo,nome_azienda,mansione_svolta,luogo_lavoro,retribuzione_lorda_giornaliera ,id_lavoratore) " +
                "VALUES (?,?,?,?,?,?,?,?)";
        List<String> fields = List.of("id","inizio_periodo","fine_periodo","nome_azienda","mansione","luogo","retribuzione" ,"id_lavoratore");
        PreparedStatement statement = getConnection().prepareStatement(sql);
        try {
            SQLMapper.serializeSQL(statement,lavoro,fields);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return statement.executeUpdate();
    }


    public String getComuniByName(String name) throws SQLException {
        String sql = "SELECT DISTINCT * FROM comune WHERE nome_comune ILIKE ?";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setString(1,name.toUpperCase(Locale.ROOT));
        ResultSet resultSet = statement.executeQuery();
        if(!resultSet.next())
            return "";
        return resultSet.getString(1);
    }


    public List<String> getAllPatenti() throws SQLException {
        String sql = "SELECT * FROM patente";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        List<String> patenti = new ArrayList<>();
        while (resultSet.next())
            patenti.add(resultSet.getString(1));
        return patenti.stream().sorted().toList();
    }


    public Dipendente getDipendenteByUserAndPassword(String user,String pass) throws SQLException {
        String sql = "SELECT * FROM dipendente WHERE username=? and password=?";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setString(1,user);
        statement.setString(2,pass);
        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next())
            try {
                return (SQLMapper.deserializeSQL(resultSet,Dipendente.class));
            } catch (IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        return null;
    }

   public String getLinguaLike(String name) throws SQLException {;
        String sql = "SELECT DISTINCT nome_lingua FROM lingua WHERE nome_lingua ILIKE ?";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setString(1,name);
        ResultSet resultSet = statement.executeQuery();
        if(!resultSet.next())
            return "";
        return resultSet.getString(1);
   }


    private void addLingua(String lingua) throws SQLException {
        String sql = "INSERT INTO lingua (nome_lingua) VALUES (?)";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setString(1,lingua);
        statement.executeUpdate();
    }


    public void addLinguaByID(int id_lavoratore, String lingua) throws SQLException {
        System.out.println(lingua);
        String key = getLinguaLike(lingua);
        System.out.println("Test");
        System.out.println(key);
        if(key.isEmpty()){
            key = lingua.substring(0,1).toUpperCase()+lingua.substring(1);
            addLingua(key);
        }
        String sql = "INSERT INTO lingua_lav(nome_lingua,id_lavoratore) VALUES(?,?)";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setString(1,key);
        statement.setInt(2,id_lavoratore);
        statement.executeUpdate();
    }


    public void delLinguaByID(int lav_id, String key) throws SQLException {
        String sql = "DELETE FROM lingua_lav WHERE ( id_lavoratore = ? AND nome_lingua = ?);";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setInt(1,lav_id);
        statement.setString(2,key);
        statement.executeUpdate();
    }

    public List<String> getLingueByID(int id) throws SQLException {
        String sql = "SELECT lingua_lav.nome_lingua FROM lavoratore INNER JOIN lingua_lav ON(lavoratore.id=lingua_lav.id_lavoratore) WHERE lavoratore.id=?";
        return getListString(id, sql);
    }


    public String getEspLike(String name) throws SQLException {;
        String sql = "SELECT DISTINCT nome_esperienza FROM esperienza WHERE nome_esperienza ILIKE ?";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setString(1,name);
        ResultSet resultSet = statement.executeQuery();
        if(!resultSet.next())
            return "";
        return resultSet.getString(1);
    }
    private void addEsp(String esp) throws SQLException {
        String sql = "INSERT INTO esperienza (nome_esperienza) VALUES (?)";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setString(1,esp);
        statement.executeUpdate();
    }


    public void addEspByID(int id_lavoratore, String esperienza) throws SQLException {
        String key = getEspLike(esperienza);
        if(key.isEmpty()){
            key = esperienza;
            addEsp(key);
        }
        String sql = "INSERT INTO esp_lav(esperienza,id_lavoratore) VALUES(?,?)";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setString(1,key);
        statement.setInt(2,id_lavoratore);
        statement.executeUpdate();
    }


    public void delEspByID(int lav_id, String key) throws SQLException {
        String sql = "DELETE FROM esp_lav WHERE ( id_lavoratore = ? AND esperienza = ?);";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setInt(1,lav_id);
        statement.setString(2,key);
        statement.executeUpdate();
    }

    public List<String> getEspByID(int id) throws SQLException {
        String sql = "SELECT esp_lav.esperienza FROM lavoratore INNER JOIN esp_lav ON(lavoratore.id=esp_lav.id_lavoratore) WHERE lavoratore.id=?";
        return getListString(id, sql);
    }

    public void delComunebyID(int lav_id, String key) throws SQLException {
        String sql = "DELETE FROM lav_comune WHERE ( id_lavoratore = ? AND comune = ?);";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setInt(1,lav_id);
        statement.setString(2,key);
        statement.executeUpdate();
    }

    public void addComuneByID(String comune, int id_lavoratore) throws SQLException {
        String sql = "INSERT INTO lav_comune(comune,id_lavoratore) VALUES(?,?)";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setString(1,comune.toUpperCase());
        statement.setInt(2,id_lavoratore);
        statement.executeUpdate();
    }

    public List<String> getComuniByID(int lavoratore_id) throws SQLException {
        String sql = "SELECT lav_comune.comune FROM lavoratore INNER JOIN lav_comune ON(lavoratore.id=lav_comune.id_lavoratore) WHERE lavoratore.id=?";
        return getListString(lavoratore_id, sql);
    }
    private List<String> getListString(int lavoratore_id, String sql) throws SQLException {
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setInt(1,lavoratore_id);
        ResultSet resultSet = statement.executeQuery();
        List<String> list = new ArrayList<>();
        while (resultSet.next())
            list.add(resultSet.getString(1));
        return list.stream().sorted().toList();
    }

    public List<String> getComuneILike(String name) throws SQLException {
        name = "%"+name+"%";
        String sql = "SELECT * FROM comune WHERE nome_comune ILIKE ?";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setString(1,name.toUpperCase(Locale.ROOT));
        return getAllComuni(statement);
    }

    public List<String> getComuneILike(String name, int limit) throws SQLException {
        name = "%"+name+"%";
        String sql = "SELECT * FROM comune WHERE nome_comune ILIKE ? LIMIT ?";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setString(1,name.toUpperCase(Locale.ROOT));
        statement.setInt(2,limit);
        return getAllComuni(statement);
    }


    private List<String> getAllComuni(PreparedStatement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery();
        List<String> list = new ArrayList<>();
        while (resultSet.next())
            list.add(resultSet.getString(1));
        return list.stream().sorted().toList();
    }
}


