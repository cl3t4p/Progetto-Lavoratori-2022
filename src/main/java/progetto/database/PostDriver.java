package progetto.database;


import progetto.Comune;
import progetto.Dipendente;
import progetto.Lavoratore;
import progetto.Lavoro;

import java.sql.*;
import java.util.*;

public class PostDriver {
    private Connection connection;
    private final String user,pass,db_name,host;
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
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(String.format("jdbc:postgresql://%s:%d/%s", host, port, db_name), user, pass);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        return connection;
    }



    public Lavoratore getLavoratore(int id) throws SQLException {
        String sql = "SELECT * FROM lavoratore WHERE id=?";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setInt(1,id);
        ResultSet resultSet = statement.executeQuery();
        if(!resultSet.next())
            return null;
        Lavoratore lavoratore;
        try {
            lavoratore = SQLMapper.deserializeSQL(resultSet,Lavoratore.class);
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
        return lavoratore;
    }
    public boolean login(String username,String password) throws SQLException {
        String sql = "SELECT id FROM dipendente WHERE username=? and password=?";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setString(1,username);
        statement.setString(2,password);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }
    public Set<Lavoratore> getLavoratori(int limit) throws SQLException {
        String sql = "SELECT * from lavoratore LIMIT ?";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setInt(1,limit);
        ResultSet resultSet = statement.executeQuery();
        return getLavoratori(resultSet);
    }

    public Set<Lavoratore> getLavoratori() throws SQLException {
        String sql = "SELECT * from lavoratore";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        return getLavoratori(resultSet);
    }

    private Set<Lavoratore> getLavoratori(ResultSet resultSet) throws SQLException {
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


    public int addLavoro(Lavoro lavoro) throws SQLException {
        String sql = "INSERT INTO lavoro_svolto " +
                "(id,inizio_periodo,fine_periodo,nome_azienda,mansione_svolta,luogo_lavoro,retribuzione_lorda_giornaliera ,id_lavoratore) " +
                "VALUES (?,?,?,?,?,?,?,?)";
        Set<String> fields = Set.of("id","inizio_periodo","fine_periodo","nome_azienda","mansione","luogo","retribuzione" ,"id_lavoratore");
        PreparedStatement statement = getConnection().prepareStatement(sql);
        try {
            SQLMapper.serializeSQL(statement,lavoro,fields);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return statement.executeUpdate();
    }
    public List<Comune> getComune(String name) throws SQLException {
        name = "%"+name+"%";
        String sql = "SELECT * FROM comune WHERE nome_comune ILIKE ?";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setString(1,name.toUpperCase(Locale.ROOT));
        return getComunes(statement);
    }

    public List<Comune> getComune(String name,int limit) throws SQLException {
        name = "%"+name+"%";
        String sql = "SELECT * FROM comune WHERE nome_comune ILIKE ? LIMIT ?";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setString(1,name.toUpperCase(Locale.ROOT));
        statement.setInt(2,limit);
        return getComunes(statement);
    }

    private List<Comune> getComunes(PreparedStatement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery();
        List<Comune> comuni = new ArrayList<>();
        while (resultSet.next()){
            try {
                comuni.add(SQLMapper.deserializeSQL(resultSet,Comune.class));
            } catch (IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        }
        return comuni;
    }

    public Comune getComuneByName(String name) throws SQLException {
        String sql = "SELECT * FROM comune WHERE nome_comune ILIKE ?";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setString(1,name.toUpperCase(Locale.ROOT));
        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next())
            try {
                return (SQLMapper.deserializeSQL(resultSet,Comune.class));
            } catch (IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        return null;
    }
    public int addLavCoume(Comune comune, int id_lavoratore) throws SQLException {
        String sql = "INSERT INTO lav_comune(comune,id_lavoratore) VALUES(?,?)";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setString(1,comune.getNome_comune().toUpperCase(Locale.ROOT));
        statement.setInt(2,id_lavoratore);
        return statement.executeUpdate();
    }

    public Set<String> getPatenti() throws SQLException {
        String sql = "SELECT * FROM patente";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        Set<String> patenti = new HashSet<>();
        while (resultSet.next())
            patenti.add(resultSet.getString(1));
        return patenti;
    }

    public Set<String> getLinguaLike(String name,int limite) throws SQLException {
        name = "%"+name+"%";
        String sql = "SELECT * FROM lingua WHERE nome_lingua ILIKE ? LIMIT ?";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setString(1,name);
        statement.setInt(2,limite);
        ResultSet resultSet = statement.executeQuery();
        Set<String> strings = new HashSet<>();
        while (resultSet.next()){
            strings.add(resultSet.getString(1));
        }
        return strings;
    }

    public int addLavLingua(String lingua, int id_lavoratore) throws SQLException {
        String sql = "INSERT INTO lingua_lav(nome_lingua,id_lavoratore) VALUES(?,?)";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setString(1,lingua);
        statement.setInt(2,id_lavoratore);
        return statement.executeUpdate();
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



}


