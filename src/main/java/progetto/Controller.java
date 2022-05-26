package progetto;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import progetto.database.PostDriver;

/**
 *
 * @author 1blan
 */
public class Controller {

    PostDriver postDriver = new PostDriver("postgres","example","postgres","localhost",5432);

    @FXML
    TextField tx,tx1,tx2,tx3,tx4,tx5,tx6,tx8,tx7,tx9,tx12,tx13,tx14,tx15,tx16,tx17,tx18,tx19,tx20,tx21,tx22,tx23,tx24,tx25,tx26,tx27,tx28,tx29,tx30,tx31,tx32,tx33,tx34,tx35,tx36,tx37,tx38,txuser,txpass;
    @FXML
    TextArea txArea,txArea1,txArea2;
    @FXML
    DatePicker dp1,dp2,dp3,dp4,dp5,dp6,dp10,dp11;
    @FXML
    RadioButton rb1,rb2;
    
    public Connection connwect() {
        String conUrl = "jdbc:postgresql://localhost:5434/db1";
        String username = "postgres";
        String password = "19111999";
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            conn=DriverManager.getConnection(conUrl,username ,password);
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return conn; 
     }
    
    public void login(ActionEvent event) throws Exception{ 
        Connection conn=connect();
        String u = txuser.getText();
        String p = txpass.getText();
        String sqlString = "SELECT id FROM dipendente WHERE username=? and password=?";
        PreparedStatement pst = conn.prepareStatement(sqlString);
        pst.setString(1, u);
        pst.setString(2, p);
        ResultSet rs = pst.executeQuery();
        if( rs.next()){
            Parent root= FXMLLoader.load(getClass().getResource("view/MENU.fxml"));
            Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene=new Scene(root);
            stage.setScene(scene);
            stage.show();          
        } else {
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERRORE");
            alert.setHeaderText("USERNAME E/O PASSWORD NON CORRETTI");
            if(alert.showAndWait().get() == ButtonType.OK){   
            }    
       }   
    }

    private Connection connect() {
        return postDriver.getConnection();
    }

    public void inserimento_lavoratore() throws Exception{
        Lavoratore l=new Lavoratore();
        Connection conn=connect();
        try {
            l.setId(Integer.parseInt(tx.getText()));
            l.setNome(tx1.getText());
            l.setCognome(tx2.getText());
            l.setLuogo_nascita(tx3.getText());
            l.setNazionalita(tx5.getText());
            l.setIndirizzo(tx6.getText());
            l.setTelefono(Integer.parseInt(tx7.getText()));
            l.setMail(tx8.getText());
            l.setAutomunito(tx9.getText());
            l.setNome_emergenza(tx12.getText());
            l.setCognome_emergenza(tx13.getText());
            l.setTelefono_emergenza(Integer.parseInt(tx14.getText()));
            l.setMail_emergenza(tx15.getText());
        } catch (Exception e) {
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERRORE");
            alert.setHeaderText("Campo vuoto o errore nell'inserimento di un dato");
            if(alert.showAndWait().get() == ButtonType.OK){            
            }   
        }

        try{
            if(Date.valueOf(dp4.getValue()).after(Date.valueOf(LocalDate.now()))){
                throw new Exception();
            } else{
                l.setData_nascita(Date.valueOf(dp4.getValue())); 
            }
        }catch ( Exception e) {
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERRORE");
            alert.setHeaderText("Data nascita inserita non corretta");
            if(alert.showAndWait().get() == ButtonType.OK){
            }           
            dp4.setValue(null);
        }

        try{
            if(Date.valueOf(dp10.getValue()).before(Date.valueOf(LocalDate.now())) || Date.valueOf(dp11.getValue()).before(Date.valueOf(dp10.getValue()))){
                throw new Exception();
            } else{
                l.setInizio_disponibile(Date.valueOf(dp10.getValue()));
                l.setFine_disponibile(Date.valueOf(dp11.getValue()));
            }
        }catch ( Exception e) {
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERRORE");
            alert.setHeaderText("Date disponibilità inserite non corrette");
            if(alert.showAndWait().get() == ButtonType.OK){
            }           
            dp10.setValue(null);
            dp11.setValue(null);
        }

	try ( PreparedStatement pst = conn.prepareStatement ("INSERT INTO lavoratore (id, nome, cognome, luogo_nascita, data_nascita, nazionalita, indirizzo, telefono, email, automunito, inizio_periodo_disp, fine_periodo_disp, nome_emergenze, cognome_emergenze, telefono_emergenze, email_emergenze, id_dipendente) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" ) ) {
		pst.clearParameters() ;
		pst.setInt( 1, l.getId());
		pst.setString ( 2, l.getNome() ) ;
		pst.setString( 3, l.getCognome() );
		pst.setString( 4, l.getLuogo_nascita() );
                pst.setDate(5, (Date) l.getData_nascita());
		pst.setString( 6, l.getNazionalita() );
		pst.setString( 7, l.getIndirizzo() );
		pst.setInt( 8, l.getTelefono() );
		pst.setString( 9, l.getMail() );
		pst.setString( 10, l.getAutomunito() );
                pst.setDate(11, (Date) l.getInizio_disponibile());
                pst.setDate(12, (Date) l.getFine_disponibile());              
		pst.setString( 13, l.getNome_emergenza() );
		pst.setString( 14, l.getCognome_emergenza() );
		pst.setInt( 15, l.getTelefono_emergenza() );
                pst.setString( 16, l.getMail_emergenza() );
               	pst.setInt( 17, Integer.parseInt(tx26.getText()));
	        pst.executeUpdate ();
                Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("CONFERMA");
                alert.setHeaderText("Lavoratore inserito in modo corretto");
                if(alert.showAndWait().get() == ButtonType.OK){
                } 
	} catch ( Exception e) {
                Alert alert =new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERRORE LAVORATORE NON INSERITO");
                alert.setHeaderText("ID già presente oppure si è verificato un altro errore");
                if(alert.showAndWait().get() == ButtonType.OK){
                } 
	}                 
    } 
    
    public void ricerca_lav(ActionEvent event) throws IOException{
        Parent root= FXMLLoader.load(getClass().getResource("view/RICERCA_LAVORATORE.fxml"));
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        stage.show();       
    }
    
    public void modifica_anagrafica(ActionEvent event) throws IOException{
        Parent root= FXMLLoader.load(getClass().getResource("view/MODIFICA_ANAGRAFICA.fxml"));
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        stage.show();       
    }
    
    public void aggiungi_lavoratore(ActionEvent event) throws IOException{ 
        Parent root= FXMLLoader.load(getClass().getResource("view/AGGIUNGI_LAVORATORE.fxml"));
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        stage.show();      
    }
    
    public void aggiungi_lavoro_svolto(ActionEvent event) throws IOException{ 
        Parent root= FXMLLoader.load(getClass().getResource("view/AGGIUNGI_LAVORO.fxml"));
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        stage.show();  
    }
    
    public void carica_lav() throws SQLException{
        txArea.setEditable(false);
        Connection conn=connect();
        String sqlString = "SELECT * from lavoratore";
        PreparedStatement pst = conn.prepareStatement(sqlString);
        ResultSet rs = pst.executeQuery();
        if( rs.next()){
            do{
                txArea.appendText("ID: "+rs.getString(1)+ "      NOME: " + rs.getString(2)+ "      COGNOME: " +rs.getString(3)+"\n");
            }while( rs.next());
       } else {
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERRORE");
            alert.setHeaderText("Non sono presenti dati");
            if(alert.showAndWait().get() == ButtonType.OK){            
            }    
       }
    }
    
    public void back(ActionEvent event) throws IOException{ 
        Parent root= FXMLLoader.load(getClass().getResource("view/MENU.fxml"));
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        stage.show();              
    }
    
    public void aggiungi_lavoro() throws SQLException {
        Lavoro l=new Lavoro();
        try{
            l.setId(Integer.parseInt(tx25.getText()));
            l.setNome_azienda(tx21.getText());
            l.setMansione(tx22.getText());
            l.setLuogo(tx23.getText());
            l.setRetribuzione(Integer.parseInt(tx24.getText()));

            if(Date.valueOf(dp1.getValue()).after(Date.valueOf(LocalDate.now())) || Date.valueOf(dp2.getValue()).before(Date.valueOf(dp1.getValue()))){
                throw new Exception();
            } else{
                l.setInizio_periodo(Date.valueOf(dp1.getValue()));
                l.setFine_periodo(Date.valueOf(dp2.getValue()));
            }
        }catch ( Exception e) {
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERRORE");
            alert.setHeaderText("Dati inseriti non corretti");
            if(alert.showAndWait().get() == ButtonType.OK){
            }           
            dp1.setValue(null);
            dp2.setValue(null);
        }
        Connection conn=connect();     
        try(PreparedStatement pst = conn.prepareStatement ("INSERT INTO lavoro_svolto (id,inizio_periodo,fine_periodo,nome_azienda,mansione_svolta,luogo_lavoro,retribuzione_lorda_giornaliera ,id_lavoratore) VALUES (?,?,?,?,?,?,?,?)" )){
            pst.clearParameters() ;
            pst.setInt( 1, l.getId());
            pst.setDate(2, (Date) l.getInizio_periodo()) ;
            pst.setDate(3, (Date) l.getFine_periodo()) ;
            pst.setString( 4, l.getNome_azienda() );
            pst.setString( 5, l.getMansione() );
            pst.setString( 6, l.getLuogo() );
            pst.setInt( 7, l.getRetribuzione() );
            pst.setInt( 8, Integer.parseInt(tx20.getText()) );
            pst.executeUpdate ();
            Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("CONFERMA");
            alert.setHeaderText("Lavoro inserito in modo corretto");
            if(alert.showAndWait().get() == ButtonType.OK){
            } 
        }catch(Exception e){
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERRORE");
            alert.setHeaderText("Lavoro non inserito");
            if(alert.showAndWait().get() == ButtonType.OK){            
            }  
        }
  
    }

    //TODO Riprendere da qua per il driver aggiungere tabella
    public void agg_comune() throws SQLException{ 
        try{
            Connection conn=connect();
            String sqlString = "SELECT * FROM comune WHERE nome_comune=?";
            PreparedStatement ps = conn.prepareStatement(sqlString);
            ps.setString(1, tx18.getText());
            ResultSet rs = ps.executeQuery();
            if( rs.next()){
                ps = conn.prepareStatement("INSERT INTO lav_comune(comune,id_lavoratore) VALUES(?,?)");
                ps.setString(1, tx18.getText());            
                ps.setInt(2, Integer.parseInt(tx.getText()));
                ps.executeUpdate();     
            } else {
                ps = conn.prepareStatement("INSERT INTO comune(nome_comune) VALUES(?)");
                ps.setString(1, tx18.getText());
                ps.executeUpdate();     
                ps = conn.prepareStatement("INSERT INTO lav_comune(comune,id_lavoratore) VALUES(?,?)");
                ps.setString(1, tx18.getText());            
                ps.setInt(2, Integer.parseInt(tx.getText()));
                ps.executeUpdate();      
            }   
       }catch(Exception e){
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERRORE");
            alert.setHeaderText("Inserire prima lavoratore oppure il comune è già stato asscociato");
            if(alert.showAndWait().get() == ButtonType.OK){            
            }   
        }
        tx18.setText("");
    }
    
    public void agg_patente(){ 
        try{
            //String c=tx16.getText();
            //int d=Integer.parseInt(tx.getText());
            Connection conn=connect();
            String sqlString = "SELECT * FROM patente WHERE tipo_patente=?";
            PreparedStatement pst = conn.prepareStatement(sqlString);
            pst.setString(1, tx16.getText());
            ResultSet rs = pst.executeQuery();
            if( rs.next()){
                pst = conn.prepareStatement("INSERT INTO pat_lav(nome_patente,id_lavoratore) VALUES(?,?)");
                pst.setString(1, tx16.getText());            
                pst.setInt(2, Integer.parseInt(tx.getText()));
                pst.executeUpdate();
             } else {
                pst = conn.prepareStatement("INSERT INTO patente(tipo_patente) VALUES(?)");
                pst.setString(1, tx16.getText());
                pst.executeUpdate();
                pst = conn.prepareStatement("INSERT INTO pat_lav(nome_patente,id_lavoratore) VALUES(?,?)");
                pst.setString(1, tx16.getText());            
                pst.setInt(2, Integer.parseInt(tx.getText()));
                pst.executeUpdate();      
           }  
       }catch(Exception e){
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERRORE");
            alert.setHeaderText("Inserire prima lavoratore oppure la patente è già stata asscociata");
            if(alert.showAndWait().get() == ButtonType.OK){            
            }   
        }
        tx16.setText("");    
    } 

    public void agg_lingua() throws Exception{ 
        try{
            //String c=tx19.getText();
            //int d=Integer.parseInt(tx.getText());
            Connection conn=connect();
            String sqlString = "SELECT * FROM lingua WHERE nome_lingua=?";
            PreparedStatement pst = conn.prepareStatement(sqlString);
            pst.setString(1, tx19.getText());
            ResultSet rs = pst.executeQuery();
            if( rs.next()){
                pst = conn.prepareStatement("INSERT INTO lingua_lav(nome_lingua,id_lavoratore) VALUES(?,?)");
                pst.setString(1, tx19.getText());            
                pst.setInt(2, Integer.parseInt(tx.getText()));
                pst.executeUpdate();
            } else {
                pst = conn.prepareStatement("INSERT INTO lingua(nome_lingua) VALUES(?)");
                pst.setString(1, tx19.getText());
                pst.executeUpdate();
                pst = conn.prepareStatement("INSERT INTO lingua_lav(nome_lingua,id_lavoratore) VALUES(?,?)");
                pst.setString(1, tx19.getText());            
                pst.setInt(2, Integer.parseInt(tx.getText()));
                pst.executeUpdate();
            }   
        }catch(Exception e){
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERRORE");
            alert.setHeaderText("Inserire prima lavoratore oppure la lingua è già stata asscociata");
            if(alert.showAndWait().get() == ButtonType.OK){            
            }   
        }
        tx19.setText("");
    }   
    
    public void agg_esperienza() throws SQLException{ 
        try{
            Connection conn=connect();
            String sqlString = "SELECT * FROM esperienza WHERE nome_esperienza=?";
            PreparedStatement pst = conn.prepareStatement(sqlString);
            pst.setString(1, tx17.getText());
            ResultSet rs = pst.executeQuery();
            if( rs.next()){
                pst = conn.prepareStatement("INSERT INTO esp_lav(esperienza,id_lavoratore) VALUES(?,?)");
                pst.setString(1, tx17.getText());            
                pst.setInt(2, Integer.parseInt(tx.getText()));
                pst.executeUpdate();
            } else {
                pst = conn.prepareStatement("INSERT INTO esperienza(nome_esperienza) VALUES(?)");
                pst.setString(1, tx17.getText());
                pst.executeUpdate();
                pst = conn.prepareStatement("INSERT INTO esp_lav(esperienza,id_lavoratore) VALUES(?,?)");
                pst.setString(1, tx17.getText());            
                pst.setInt(2, Integer.parseInt(tx.getText()));
                pst.executeUpdate();
           }  
        }catch(Exception e){
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERRORE");
            alert.setHeaderText("Inserire prima lavoratore oppure l'esperienza è già stata asscociata");
            if(alert.showAndWait().get() == ButtonType.OK){            
            }   
        }
        tx17.setText("");
    }   
    
    public void generatore_id_lav() throws SQLException{
        Connection conn=connect();
        int i = 0;
        String sqlString = "SELECT COUNT (*) FROM lavoratore";
        PreparedStatement pst = conn.prepareStatement(sqlString);
        ResultSet rs = pst.executeQuery();
        if(rs.next()){
            i = rs.getInt(1);
        }
        tx.setText(String.valueOf(i+1));
    }
    
    public void generatore_id_lavoro() throws SQLException{
        Connection conn=connect();
        int i = 0;
        String sqlString = "SELECT COUNT (*) FROM lavoro_svolto";
        PreparedStatement pst = conn.prepareStatement(sqlString);
        ResultSet rs = pst.executeQuery();
        if(rs.next()){
            i = rs.getInt(1);
        }
        tx25.setText(String.valueOf(i+1));
    }
       
    public void cerca_lav() throws SQLException{
        Connection conn=connect();
        String sqlString = "SELECT * from lavoratore WHERE id=?";                     
        PreparedStatement pst = conn.prepareStatement(sqlString);
        pst.setInt(1, Integer.parseInt(tx31.getText()));
        ResultSet rs = pst.executeQuery();
        if( rs.next()){
            tx27.setText(rs.getString(2));
            tx28.setText(rs.getString(3));
            tx29.setText(rs.getString(4));
            dp3.setValue(rs.getDate(5).toLocalDate());
       } else {
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERRORE");
            if(alert.showAndWait().get() == ButtonType.OK){            
            }    
       }
    }
   
    public void conferma_modifica() {
        Lavoratore l=new Lavoratore();
        Connection conn=connect();
        try {
            l.setId(Integer.parseInt(tx31.getText()));
            l.setNome(tx27.getText());
            l.setCognome(tx28.getText());
            l.setLuogo_nascita(tx29.getText());
        } catch (Exception e) {
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERRORE");
            alert.setHeaderText("Dato inserito non corretto");
            if(alert.showAndWait().get() == ButtonType.OK){            
            }   
        }
        try{
            if(Date.valueOf(dp3.getValue()).after(Date.valueOf(LocalDate.now()))){
                throw new Exception();
            } else{
                l.setData_nascita(Date.valueOf(dp3.getValue()));
            }
        }catch ( Exception e) {
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERRORE");
            alert.setHeaderText("Data nascita inserita non corretta");
            if(alert.showAndWait().get() == ButtonType.OK){
            }           
            dp3.setValue(null);
        }
        try ( PreparedStatement pst = conn.prepareStatement ("UPDATE lavoratore SET nome=?, cognome=?, luogo_nascita=?, data_nascita=? WHERE id=?" ) ) {
		pst.clearParameters() ;
		pst.setString ( 1, l.getNome() ) ;
		pst.setString( 2, l.getCognome() );
		pst.setString( 3, l.getLuogo_nascita() );
                pst.setDate(4, (Date) l.getData_nascita());
                pst.setInt( 5, l.getId());
	        pst.executeUpdate ();
                Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("CONFERMA");
                alert.setHeaderText("Lavoratore inserito con modifiche");
                if(alert.showAndWait().get() == ButtonType.OK){
                } 
	} catch ( Exception e) {
                Alert alert =new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERRORE MODIFICA NON INSERITA");
                alert.setHeaderText("ID NON presente oppure altro errore");
                if(alert.showAndWait().get() == ButtonType.OK){
                } 
	} 
    }
    
    public void add_nome(){
        String text = tx32.getText();
        if(text.isEmpty()){
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERRORE");
            alert.setHeaderText("Campo vuoto");
            if(alert.showAndWait().get() == ButtonType.OK){
            }            
        } else {
            txArea2.appendText(" lavoratore.nome='"+text+"'");
        }
    }
    
    public void add_cognome(){
        String text = tx33.getText();
        if(text.isEmpty()){
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERRORE");
            alert.setHeaderText("Campo vuoto");
            if(alert.showAndWait().get() == ButtonType.OK){
            }            
        } else {
            txArea2.appendText(" lavoratore.cognome='"+text+"'");
        }
    }
  
    public void add_luogo_residenza(){
        String text = tx36.getText();
        if(text.isEmpty()){
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERRORE");
            alert.setHeaderText("Campo vuoto");
            if(alert.showAndWait().get() == ButtonType.OK){
            }            
        } else {
            txArea2.appendText(" lavoratore.indirizzo='"+text+"'");
        }
    }
    
    public void add_automunito(){
        String text = tx37.getText();
        if(text.isEmpty()){
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERRORE");
            alert.setHeaderText("Campo vuoto");
            if(alert.showAndWait().get() == ButtonType.OK){
            }            
        } else {
            txArea2.appendText(" lavoratore.automunito='"+text+"'");
        }
    }
    
    public void add_lingua(){
        String text = tx34.getText();
        if(text.isEmpty()){
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERRORE");
            alert.setHeaderText("Campo vuoto");
            if(alert.showAndWait().get() == ButtonType.OK){
            }            
        } else {
            txArea2.appendText(" lingua_lav.nome_lingua='"+text+"'");
        }
    }
    
    public void add_mansione(){
        String text = tx35.getText();
        if(text.isEmpty()){
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERRORE");
            alert.setHeaderText("Campo vuoto");
            if(alert.showAndWait().get() == ButtonType.OK){
            }            
        } else {
            txArea2.appendText(" esp_lav.esperienza='"+text+"'");
        }
    }
  
    public void add_patente(){
        String text = tx38.getText();
        if(text.isEmpty()){
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERRORE");
            alert.setHeaderText("Campo vuoto");
            if(alert.showAndWait().get() == ButtonType.OK){
            }            
        } else {
            txArea2.appendText(" pat_lav.nome_patente='"+text+"'");
        }
    }
    
    public void add_data(){
        Date d1 = Date.valueOf(dp5.getValue());
        Date d2 = Date.valueOf(dp6.getValue());
        try{
            if(d1.before(Date.valueOf(LocalDate.now())) || d2.before(d1)){
                throw new Exception();
            } else{
                txArea2.appendText("( '"+d1+"' BETWEEN lavoratore.inizio_periodo_disp AND lavoratore.fine_periodo_disp AND '"+d2+"' BETWEEN inizio_periodo_disp AND fine_periodo_disp)");           
            }
        }catch ( Exception e) {
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERRORE");
            alert.setHeaderText("Date disponibilità inserite non corrette oppure campi vuoti");
            if(alert.showAndWait().get() == ButtonType.OK){
            }           
            dp5.setValue(null);
            dp6.setValue(null);
        }
    }
    
    public void and_or(){
        if(rb1.isSelected()){
            txArea2.appendText(" AND ");           
        }else if(rb2.isSelected()){
            txArea2.appendText(" OR ");
        }   
    }
    
    public void ricerca_lavoratore() {
        try{
            Connection conn=connect();
            txArea1.setEditable(false);
            String s=txArea2.getText();
            String StringSql = "SELECT DISTINCT lavoratore.id, lavoratore.nome, lavoratore.cognome FROM lavoratore INNER JOIN pat_lav ON (lavoratore.id=pat_lav.id_lavoratore) INNER JOIN lingua_lav ON (lavoratore.id=lingua_lav.id_lavoratore) INNER JOIN lav_comune ON (lavoratore.id=lav_comune.id_lavoratore) INNER JOIN esp_lav ON (lavoratore.id=esp_lav.id_lavoratore) WHERE "+s;
            Statement pst = conn.createStatement();
            ResultSet rs = pst.executeQuery(StringSql);
            do{
                txArea1.appendText("ID: "+rs.getString(1)+ "      NOME: " + rs.getString(2)+ "      COGNOME: " +rs.getString(3)+"\n");
            }while(rs.next());
        }catch ( Exception e) {
                Alert alert =new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERRORE");
                alert.setHeaderText("Stringa di ricerca non corretta");
                if(alert.showAndWait().get() == ButtonType.OK){
                } 
	}
    }     
}
