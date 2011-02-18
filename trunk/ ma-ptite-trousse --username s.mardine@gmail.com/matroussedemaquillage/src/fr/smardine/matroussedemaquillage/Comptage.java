package fr.smardine.matroussedemaquillage;

import java.io.File;
import java.util.Calendar;

/**
 * @author smardine
 */
public class Comptage {
	private static int nbDossier = 0;
	private int nbFichier = 0;

	/**
	 * @param directoryPath le chemin du dossier a traiter
	 */
	public Comptage(String directoryPath) {

		LanceComptage(directoryPath);

	}

	private void LanceComptage(String directoryPath) {
		// TODO Auto-generated method stub
		File directory = new File(directoryPath);

		if (!directory.exists()) {
			// System.out.println("Le fichier/répertoire '"+directoryPath+"' n'existe pas");
		} else if (directory.isFile()) {

			nbDossier--;
			nbFichier++;

		} else {
			if (directory.isDirectory()) {
				File[] subfiles = directory.listFiles();

				if (subfiles != null) {// si subfiles=null, c'est que le dossier a des restriction d'acces
					nbDossier = subfiles.length + nbDossier;
					for (int i = 0; i < subfiles.length; i++) {
						LanceComptage(subfiles[i].toString());
					}
				}

			}
		}
	}

	/**
	 * permet de supprimer le fichier le plus ancien present dans "Path" Pour cela on se base sur le nom de fichier qui est
	 * "trousse_baseAAAAmmJJ"
	 * @param Path String -le nom du sossier sur la carte SD
	 * @return true si ca a marché.
	 */
	public boolean supprFichierPlusAncien(String Path) {
		File directory = new File(Path);
		Calendar datePlusAncienne = null;
		if (!directory.exists()) {
			return false;
		} else if (directory.isFile()) {
		} else {
			if (directory.isDirectory()) {
				File[] subfiles = directory.listFiles();
				if (subfiles != null) {// si subfiles=null, c'est que le dossier a des restriction d'acces

					for (int i = 0; i < subfiles.length; i++) {
						String name = subfiles[i].toString();
						if (!name.equals(Path + "trousse_base")) {
							String date = name.substring(name.lastIndexOf("trousse_base") + 12);
							int annee = Integer.parseInt(date.substring(0, 4));
							int mois = Integer.parseInt(date.substring(4, 6));
							int jour = Integer.parseInt(date.substring(6));
							// les mois commence à 0 (janvier) et se termine à 11 (decembre)
							// les années commence à 0(1900), pour avoir l'année exacte a partir d'une
							// velur contenu dans un string, il faut retrancher 1900 a la valeur de
							// l'année.
							// exemple, l'année 2010 est considérée comme 2010-1900 = 110
							// Calendar dateFichier = new Calendar(annee - 1900, mois - 1, jour);
							Calendar dateFichier = Calendar.getInstance();
							dateFichier.set(annee, mois, jour);

							if (datePlusAncienne == null) {
								datePlusAncienne = dateFichier;
							}
							if (dateFichier.before(datePlusAncienne)) {
								datePlusAncienne = dateFichier;
							}
						}
						// supprFichierPlusAncien(subfiles[i].toString());
					}
				}

			}
			// normalement, on a du pouvoir identifier quelle etait le fichier avec la date la plus ancienne.
			int jour = datePlusAncienne.get(Calendar.DAY_OF_MONTH);
			int mois = datePlusAncienne.get(Calendar.MONTH);
			int annee = datePlusAncienne.get(Calendar.YEAR);
			String sYear = "" + annee;
			String sDay;
			if (jour < 10) {
				sDay = "0" + jour;
			} else {
				sDay = "" + jour;
			}
			String sMonth;
			if (mois < 10) {
				sMonth = "0" + mois;
			} else {
				sMonth = "" + mois;
			}
			String nomFichierASuppr = "trousse_base" + sYear + sMonth + sDay;
			File f = new File(Path + nomFichierASuppr);
			if (!f.delete()) {
				f.deleteOnExit();
				return true;
			}
			return !f.exists();
		}
		return false;

	}

	/**
	 * @return le nombre de fichier present dans le dossier
	 */
	public int getNbFichier() {
		return nbFichier;
	}

	/**
	 * @return le nombre de dossier present dans le dossier racine.
	 */
	public int getNbDossier() {
		return nbDossier;
	}

}
