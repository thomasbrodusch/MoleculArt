package controle;
import java.sql.*;
import java.util.*;

/**
* Classe de connection a la database locale de l'application.
* @author Brodusch Thomas
* @version 1.13.2.5
* 
*/

public class BDConnect {

	private String login;
	private String pass;
	private String bddUrl;
	private String bddType;
	private Statement stmt;
	
/**
 * @param login
 * 		login de la BDD.
 * @param pass
 * 		pass de la BDD.
 * @param bddType
 * 		type de la BDD: "mysql"/"postgresql".
 * @param bddUrl
 * 		url de la BDD.
 */
	public BDConnect(String login, String pass, String bddType, String bddUrl){
		this.login	= login;
		this.pass	= pass;
		this.bddType	= bddType;
		this.bddUrl	= bddUrl;
			//System.out.println("### init ok... ###");					
	}
	
/**
 * Initialisation de la connection.
 * Charge en premier temps le pilote de la BDD.
 * Si chargement du pilote reussi -> on etablit la connection a la BDD.
 * @see #loadPilot()
 */
	public void init(){
	
		if( this.loadPilot() ){ // Si pilote charg��, connection !
			this.connect();
		}
	}
/**
 * 	Chargement du pilote en fonction du type de BDD.
 * @return Retourne "true" le pilote a ete charge avec succes sinon retourne "false".
 *
 */
	public boolean loadPilot(){
	// Chargement du pilote.
		
		try {
			if (this.bddType == "postgresql" ){
			Class.forName("org.postgresql.Driver").newInstance();
			}
			if (this.bddType == "mysql" ){
				Class.forName("com.mysql.jdbc.Driver").newInstance();
			}
			System.out.println("[ INFO ] - database pilote '"+ this.bddType+"' loaded with success ! ###");	
			return true;
		} catch (Exception e) {
			System.out.println("[ ERROR ] - database pilot can't load ! ###\n");
			e.printStackTrace();
			return false;
			//System.exit(99);
		}
	}
/**
 * Connection a la BDD.
 * 
 */
	public void connect(){	
		try {
			String DBurl = "jdbc:"+this.bddType+"://"+this.bddUrl;
			
			Properties props = new Properties(); 
			props.setProperty("user",this.login); 
			props.setProperty("password",this.pass); 
			//props.setProperty("ssl","true"); fontionne ssi le serveur �� activ�� SSL.
			
			Connection connect = DriverManager.getConnection(DBurl, props);
			
			this.stmt = connect.createStatement();
				System.out.println("[ INFO ] - connected on the database '"+this.bddUrl+"' with success ! ###");					
		} catch (SQLException e) { 
			System.out.println("[ ERROR ] - unable to connect on the database'"+this.bddUrl+"' - database down/wrong password? ###\n");
			e.printStackTrace();	//ps:rapport erreur complet.
		}
	}
	
	public Statement getStmt(){
		return this.stmt;
	}
		
	
}