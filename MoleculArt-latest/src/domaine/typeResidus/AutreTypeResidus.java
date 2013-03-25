/**
 * @author Kevin Lavergne & Damien Kozak
 * @version v1.3
 */
package domaine.typeResidus;

import java.awt.Color;
/**
 * Classe repr�sentant les types de r�sidus class�s comme autres (non proteine et non nucleique)
 *
 */

public class AutreTypeResidus extends TypeResidus {

	private String nomType;
	/**
	 * Constructeur
	 * @param nomType
	 */
	public AutreTypeResidus(String nomType) {
		this.nomType=nomType;
	}
	/**
	 * Renvoie le type du r�sidus
	 */
	public String getType()
	{
		return this.nomType;
	}
	/**
	 * Renvoie la couleur par d�faut du r�sidus
	 */
	public Color getCouleur()
	{
		return new Color(190,160,110);
	}
}
