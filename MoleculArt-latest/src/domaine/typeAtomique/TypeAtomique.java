package domaine.typeAtomique;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * Classe représentant le type atomique d'un atome
 * @author Kevin Lavergne & Damien Kozak
 * @version v1.3
 */
public abstract class TypeAtomique implements ConstantesCouleurAtome {

	private double rayonVanDerWaals;
	/**
	 * Constructeur
	 */
	public TypeAtomique() {
		rayonVanDerWaals = this.lireFichierRayon(this.getClass()
				.getSimpleName());
	}
	/**
	 * Permet de lire le rayon de Van der Waals dans un fichier
	 * @param type
	 */
	public double lireFichierRayon(String type) {
		double rayon = 0;
		try {
			File file = new File("RayonVanDerWaals.txt");
			BufferedReader buff = new BufferedReader(new FileReader(file));
			try {
				String line;
				StringTokenizer st;
				while ((line = buff.readLine()) != null) {
					st = new StringTokenizer(line, ":");
					if (st.nextToken().equals(type)) {
						rayon = Double.parseDouble(st.nextToken());
					}
				}
			} finally {
				buff.close();
			}
		} catch (IOException ioe) {
			rayon = 200;
			System.out.println("Erreur --" + ioe.toString());
		}
		return rayon;
	}
	/**
	 * Renvoie la couleur par défaut du type atomique
	 */
	public abstract Color getCouleur();
	/**
	 * Renvoie le rayon de Van der Waals du type atomique
	 */
	public double getRayonVanDerWaals() {
		return this.rayonVanDerWaals;
	}
	/**
	 * Change le rayon de Van der Waals du type atomique
	 */
	public void setRayonVanDerWaals(double rayon) {
		this.rayonVanDerWaals = rayon;
	}
	/**
	 * Renvoie le type atomique de l'atome
	 */
	public String getType() {
		return this.getClass().getSimpleName();
	}
}
