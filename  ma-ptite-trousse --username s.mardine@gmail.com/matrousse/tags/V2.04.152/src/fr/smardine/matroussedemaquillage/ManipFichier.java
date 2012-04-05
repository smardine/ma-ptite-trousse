package fr.smardine.matroussedemaquillage;

import java.io.File;
import java.io.FilenameFilter;

import android.util.Log;

/**
 * @author smardine
 */
public class ManipFichier {

	private final static String TAG = ManipFichier.class.getCanonicalName();

	/**
	 * Deplace un fichier
	 * @param source -File le fichier source
	 * @param destination -File le fichier de destination
	 * @return result -Boolean vrai si ca a marché
	 */

	public static boolean deplacer(File source, File destination) {
		if (!destination.exists()) {
			// On essaye avec renameTo
			boolean result = source.renameTo(destination);
			if (!result) {
				// On essaye de copier
				result = true;
				result &= copier(source, destination);
				result &= source.delete();

			}
			return (result);
		} else {
			// Si le fichier destination existe, on annule ...
			return (false);
		}
	}

	/**
	 * copie un fichier
	 * @param source -File le fichier source
	 * @param destination -File le fichier de destination
	 * @return resultat -Boolean vrai si ca a marché
	 */
	public static boolean copier(File source, File destination) {
		boolean resultat = false;

		// Declaration des flux
		java.io.FileInputStream sourceFile = null;
		java.io.FileOutputStream destinationFile = null;

		try {
			// Création du fichier destination (si le fichier existe deja, on le
			// supprime):
			if (destination.exists()) {
				destination.delete();
			}
			boolean isFileCreated = destination.createNewFile();
			if (!isFileCreated) {
				return false;
			}

			// Ouverture des flux
			sourceFile = new java.io.FileInputStream(source);
			destinationFile = new java.io.FileOutputStream(destination);

			// Lecture par segment de 0.5Mo
			byte buffer[] = new byte[512 * 1024];
			int nbLecture;

			while ((nbLecture = sourceFile.read(buffer)) != -1) {
				destinationFile.write(buffer, 0, nbLecture);
			}

			// Copie réussie
			resultat = true;
		} catch (java.io.FileNotFoundException f) {
			resultat = false;
		} catch (java.io.IOException e) {
			resultat = false;
		} finally {
			// Quoi qu'il arrive, on ferme les flux
			try {
				sourceFile.close();
			} catch (Exception e) {
				resultat = false;
			}
			try {
				destinationFile.close();
			} catch (Exception e) {
				resultat = false;
			}
		}
		return (resultat);
	}

	/**
	 * Suppresion du contenu d'un repertoire avec un filtre sur l'extension
	 * @param RepAVider - File repertoire à vider.
	 * @param extension -Final String extension sous la forme ".eml"
	 */
	public static void DeleteContenuRepertoireAvecFiltre(File RepAVider,
			final String extension) {
		if (RepAVider.isDirectory()) {
			File[] list = RepAVider.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.toLowerCase().endsWith(extension);
				}
			});
			if (list != null) {
				for (int i = 0; i < list.length; i++) {
					// Appel récursif sur les sous-répertoires
					DeleteContenuRepertoireAvecFiltre(list[i], extension);
				}
			} else {
				Log.e(TAG, RepAVider + " : Erreur de lecture.");

			}

		}

		if (RepAVider.isFile()) {
			// listModel.addElement (RepAVider.getName());
			RepAVider.delete();
			// nbFichier++;
			// nbFichierLabel.setText("Nombre de fichier(s) a traiter:"+nbFichier);

		}
	}

}
