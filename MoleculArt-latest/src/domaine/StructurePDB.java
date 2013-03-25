/**
 * @author Kevin Lavergne & Damien Kozak
 * @version v1.3
 */

package domaine;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.biojava.bio.structure.Author;

/**
 * Classe représentant la structure pour la représentation d'une molécule
 * 
 * 
 */
public class StructurePDB {

	private String pdbId;
	private String titre;
	private String[] auteurs;
	private Date datePublication;
	private ArrayList<Modele> modeles;

	/**
	 * Constructeur
	 * 
	 * @param pdbId
	 * @param titre
	 * @param auteurs
	 * @param datePublication
	 */
	public StructurePDB(String pdbId, String titre, List<Author> auteurs,
			Date datePublication) {
		this.pdbId = pdbId;
		this.titre = titre;
		this.auteurs = new String[auteurs.size()];
		for (int i = 0; i < auteurs.size(); i++) {
			this.auteurs[i] = auteurs.get(i).toString();
		}
		this.datePublication = datePublication;
		this.modeles = new ArrayList<Modele>();
	}

	/**
	 * Indique la séquence de chaque des chaines de la molecule
	 * 
	 * @return String[]
	 */
	public String[] getSequences() {
		return this.getModele(0).getSequences();
	}

	/**
	 * Renvoie un modele de representation de la structure
	 * 
	 * @param indice
	 */
	public Modele getModele(int indice) {
		return modeles.get(indice);
	}

	/**
	 * Renvoie le nombre de modele de représentation différents
	 */
	public int size() {
		return this.modeles.size();
	}

	/**
	 * Ajoute un modele de représentation dans la structure
	 * 
	 * @param modele
	 */
	public void addModele(Modele modele) {
		this.modeles.add(modele);
	}

	/**
	 * Renvoie les coordonnées d'un modele de représentation
	 * 
	 * @param indiceModel
	 */
	public double[] barycentre(int indiceModel) {
		double x = 0;
		double y = 0;
		double z = 0;
		int nbAtome = 0;
		for (int i = 0; i < this.getModele(indiceModel).size(); i++) {
			for (Residus r : this.getModele(indiceModel).getChaine(i)
					.getResidus()) {
				for (Atome a : r.getAtomes()) {
					x += a.getX();
					y += a.getY();
					z += a.getZ();
					nbAtome++;
				}
			}
		}
		x = x / nbAtome;
		y = y / nbAtome;
		z = z / nbAtome;
		return new double[] { x, y, z };
	}

	/**
	 * Renvoie l'identifiant de la molécule (pdbID)
	 */
	public String getPdbId() {
		return pdbId;
	}

	/**
	 * Renvoie le titre de la structure
	 */
	public String getTitre() {
		return titre;
	}

	/**
	 * Renvoie la liste des auteurs de la représentation de la molécule
	 */
	public String[] getAuteurs() {
		return auteurs;
	}

	/**
	 * Renvoie la date de publication de la molécule
	 */
	public Date getDatePublication() {
		return datePublication;
	}

	/**
	 * Renvoie la liste des modele de représentation
	 */
	public ArrayList<Modele> getModeles() {
		return modeles;
	}

	/**
	 * Renvoie la liste des différents types atomiques distinct
	 */
	public Atome[] listAtomes() {
		ArrayList<Atome> list = new ArrayList<Atome>();
		for (Chaine c : this.getModele(0).getChaines()) {
			for (Residus r : c.getResidus()) {
				for (Atome a : r.getAtomes()) {
					if (!list.contains(a)) {
						list.add(a);
					}
				}
			}
		}
		return list.toArray(new Atome[1]);
	}

	/**
	 * Renvoie la liste des différents types résidus distinct
	 */
	public Residus[] listResidus() {
		ArrayList<Residus> list = new ArrayList<Residus>();
		for (Chaine c : this.getModele(0).getChaines()) {
			for (Residus r : c.getResidus()) {
				if (!list.contains(r)) {
					list.add(r);
				}
			}
		}
		return list.toArray(new Residus[1]);
	}

	/**
	 * Renvoie la liste des différentes chaines
	 */
	public Chaine[] listChaines() {
		return this.getModele(0).getChaines().toArray(new Chaine[1]);
	}

	/**
	 * Change la couleur d'une chaine
	 * @param id
	 * @param couleur
	 */
	public void changeColorChaine(String id, Color couleur) {
		for (Modele m : this.getModeles()) {
			for (Chaine c : m.getChaines()) {
				if (c.getId().equals(id))
					c.setCouleur(couleur);
			}
		}
	}

	/**
	 * Change la couleur d'un certain type de residus
	 * 
	 * @param id
	 * @param couleur
	 */
	public void changeColorResidus(String id, Color couleur) {
		for (Modele m : this.getModeles()) {
			for (Chaine c : m.getChaines()) {
				for (Residus r : c.getResidus()) {
					if (r.getType().getType().equals(id))
						r.setCouleur(couleur);
				}
			}
		}
	}

	/**
	 * Change la couleur d'un certain type d'atome
	 * 
	 * @param id
	 * @param couleur
	 */
	public void changeColorAtome(String id, Color couleur) {
		for (Modele m : this.getModeles()) {
			for (Chaine c : m.getChaines()) {
				for (Residus r : c.getResidus()) {
					for (Atome a : r.getAtomes()) {
						if (a.getElem().getType().equals(id))
							a.setColor(couleur);
					}
				}
			}
		}
	}

	/**
	 * Permet de redessiner un modele de représentation en fonction du
	 * contrainte de choix de couleur
	 */
	public void redessiner(int contrainte, int modele) {
		this.getModele(modele).redessiner(contrainte);
	}
}
