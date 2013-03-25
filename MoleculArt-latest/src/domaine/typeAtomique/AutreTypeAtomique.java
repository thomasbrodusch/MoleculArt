package domaine.typeAtomique;

import java.awt.Color;
/**
 * Classe repr�sentant les types atomiques class�s comme autres
 * @author Kevin Lavergne & Damien Kozak
 * @version v1.3
 */

public class AutreTypeAtomique extends TypeAtomique
{
	private String nomType;
/**
 * Constructeur
 * @param nomType
 */
	public AutreTypeAtomique(String nomType) {
		super();
		this.setRayonVanDerWaals(this.lireFichierRayon(nomType));
		this.nomType=nomType;
	}
/**
 * Renvoie le type atomique d'un atome
 */
	public String getType()
	{
		return this.nomType;
	}
	/**
	 * Renvoie la couleur par d�faut du type atomique
	 */
	public Color getCouleur() {
		return deepPink;
	}
}