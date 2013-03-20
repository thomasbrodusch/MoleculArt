package controle;
import java.sql.*;
/** ######	UNDER CONSTRUCTION ######<br/>
 * Classe de recherche dans la BDD locale (mode recherche offline).
 * @author Brodusch Thomas
 *@version 1.0
 */
public class BDSearch {
	public Statement stmt;
	
	public BDSearch(Statement stmt){
		this.stmt = stmt;
		
	}
	
	// méthode pour tester l'envoi et le rapatriement de requête SQL.
	public void testSearch(){
		ResultSet resultats = null;
		try {
			resultats = this.stmt.executeQuery("SELECT idmol,nom,auteur,date FROM molecules WHERE idmol ='102D' ");
			boolean asNext = resultats.next();
			while (asNext) {
				System.out.println (resultats.getString(1)+ " | " + resultats.getString(2) + " | "+ resultats.getString(3)+" | "+ resultats.getString(4));
				
				asNext = resultats.next();
		}
		resultats.close();
		} catch (SQLException e) {
		e.printStackTrace();
		}
	}
	
}
