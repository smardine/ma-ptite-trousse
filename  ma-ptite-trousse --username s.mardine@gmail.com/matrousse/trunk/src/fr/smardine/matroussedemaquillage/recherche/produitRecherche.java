package fr.smardine.matroussedemaquillage.recherche;

/**
 * @author smardine
 */
public class produitRecherche {
	String champ1;
	String champ2;
	String champ3;
	String champ4;

	/**
	 * @param p_index
	 * @param p_dateAchat
	 * @param p_datePeremption
	 * @param p_autreInfo
	 */
	public produitRecherche(String p_index, String p_dateAchat,
			String p_datePeremption, String p_autreInfo) {
		champ1 = p_index;
		champ2 = p_dateAchat;
		champ3 = p_datePeremption;
		champ4 = p_autreInfo;
	}
}
