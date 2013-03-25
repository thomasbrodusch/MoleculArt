package controle;


import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.biojava.bio.structure.Atom;
import org.biojava.bio.structure.Chain;
import org.biojava.bio.structure.Group;
import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.StructureException;
import org.biojava.bio.structure.io.PDBFileReader;

import domaine.Atome;
import domaine.Chaine;
import domaine.Modele;
import domaine.Residus;
import domaine.StructurePDB;
/**
 * Classe de parsage des fichiers pdb pour creer un structure de représentation d'une molécule
 * @author Kévin Lavergne
 * @version 1.3
 */
public class ParseurPDB {

	/**
	 * Permet de créer la structure en parsant un fichier pdb et le télécharchant si il n'est pas présent
	 * @param idMol
	 * @return StructurePDB
	 * @throws StructureException
	 * @throws IOException
	 */
	public StructurePDB creerStruturePDB(String idMol) throws StructureException, IOException {

		File f = new File("tmp/" + idMol + ".pdb");
		if (!f.exists()) {
			DownloaderHTTP downloader = new DownloaderHTTP(idMol, "pdb");
			downloader.init();
		}
		return this.parsage(f);
	}

	/**
	 * Permet d'utiliser le parseur Biojava et de construire notre structurePDB en extrayant les informations utiles
	 * @throws StructureException 
	 * @throws IOException 
	 */
	public StructurePDB parsage(File fichierName) throws StructureException, IOException {
		PDBFileReader pdbreader = new PDBFileReader();
		StructurePDB sPDB = null;
			Structure s = pdbreader.getStructure(fichierName);
			sPDB = new StructurePDB(
						s.getPDBCode(),
						s.getPDBHeader().getTitle(),
						s.getJournalArticle().getAuthorList(),
					new Date(s.getJournalArticle().getPublicationDate())
						);
			for (int i = 0; i < s.nrModels(); i++) {
				sPDB.addModele(creerModele(s.getModel(i)));
			}
		return sPDB;
	}
	/**
	 * Créer un modele ainsi que l'ensemble de ses chaines
	 * @param m
	 * @return Modele
	 * @throws StructureException
	 */
	private Modele creerModele(List<Chain> m) throws StructureException {
		Modele modele = new Modele();
		for (int i = 0; i < m.size(); i++) {
			modele.addChaine(creerChaine(m.get(i)));
		}
		return modele;
	}

	/**
	 * Créer un chaine ainsi que l'ensemble de ses résidus
	 * @param c
	 * @return Chaine
	 * @throws StructureException
	 */
	private Chaine creerChaine(Chain c) throws StructureException {
		Chaine chaine = new Chaine(c.getChainID());
		for (int i = 0; i < c.getAtomGroups().size(); i++) {
			chaine.addResidus(creerResidus(c.getAtomGroup(i)));
		}
		return chaine;

	}

	/**
	 * Créer un residus ainsi que l'ensemble de ses atomes
	 * @param r
	 * @return Residus
	 * @throws StructureException
	 */
	private Residus creerResidus(Group r) throws StructureException {
		Residus residus = new Residus(r.getResidueNumber().getSeqNum()
				.intValue(), r.getPDBName());
		for (int i = 0; i < r.getAtoms().size(); i++) {
			residus.addAtome(creerAtome(r.getAtom(i)));
		}
		return residus;
	}

	/**
	 * Créer un atome
	 * @param a
	 * @return Atome
	 * @throws StructureException
	 */
	private Atome creerAtome(Atom a) throws StructureException {
		Atome atome = new Atome(a.getX(), a.getY(), a.getZ(), a.getFullName(),
				a.getPDBserial(), a.getElement().name());
		return atome;
	}

}
