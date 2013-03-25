package controle;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/** 
 * Classe de telechargement de fichier .PDB ou .XML -> HTTP
 * @author Brodusch Thomas
 * @version 1.13.2.12
 */
public class DownloaderHTTP {

	public FileOutputStream fos = null;
	private String lienPDB = "http://www.rcsb.org/pdb/files/";
	private InputStream reader;
	public String pdbXml = ".";
	public String filename;
	long startTime = System.currentTimeMillis();

	/**
	 * Constructeur
	 * 
	 * @param filename
	 *            nom du fichier (PDBiD) a telecharger.
	 * @param pdbXml
	 *            format du fichier desire : .pdb ou .xml
	 */
	public DownloaderHTTP(String filename, String pdbXml) {
		this.filename = filename;
		this.pdbXml += pdbXml;
	}

	/**
	 * Initialisation
	 * 
	 * @see #connectServer()
	 * @see #execDownload()
	 */
	public void init() {

		// Connection.
		this.connectServer();

		// Creation du repertoire tmp/ si non existant.
		File tmp = new File("tmp");
		if (!tmp.exists()) {
			tmp.mkdirs();
		}

		// Download.
		this.execDownload();

		// Reinitialisation des variable pour le prochain download.
		this.filename = "";

	}

	/**
	 * Gestion de la connection au server HTTP<br/>
	 * Va tester si le server est up: <br/>
	 * Si server est up -> connection + telechargement du fichier .pdb/.xml <br/>
	 * Si server est down -> .
	 */
	public void connectServer() {

		try {
			// Rajout de l'extension du fichier.
			this.filename += this.pdbXml;

			URL url = new URL(this.lienPDB + this.filename);
			HttpURLConnection urlConn = (HttpURLConnection) url
					.openConnection();
			// Connection.
			urlConn.connect();

			this.reader = url.openStream();
			// System.out.println("[ INFO ] - connected to HTTP - RCSB.org server is up :) ###");

		} catch (Exception e) {
			// System.out.println("[ ERROR ] - HTTP RCSB.org server is unreachable ###\n");
		}

	}

	/**
	 * Telechargement S'occupe de rapatrier le fichier .pdb/.xml distant, dans
	 * le dossier local tmp/ de l'application.
	 * 
	 * 
	 */

	public void execDownload() {

		try {
			/*
			 * Prepare un "buffered file writer" pour ecrire sur "out" le
			 * fichier du website.
			 */
			FileOutputStream writer = new FileOutputStream("tmp/"
					+ this.filename);
			byte[] buffer = new byte[153600];
			int bytesRead = 0;

//			System.out
//					.println("[ INFO ] - reading file 150KB blocks at a time. ###\n");

			while ((bytesRead = this.reader.read(buffer)) > 0) {
				writer.write(buffer, 0, bytesRead);
				buffer = new byte[153600];
			}

//			long endTime = System.currentTimeMillis();

//			System.out
//					.println("[ INFO ] - download done. 'tmp/" + this.filename
//							+ "' " + (new Integer(totalBytesRead).toString())
//							+ " bytes read ("
//							+ (new Long(endTime - startTime).toString())
//							+ "ms). ###\n");
			writer.close();
			reader.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
