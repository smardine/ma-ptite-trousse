package fr.smardine.matroussedemaquillage.recherche;

/**
 * @author smardine
 */
public class produitRecherche {
	int champ1;
	String champ2;
	String champ3;
	String champ4;

	/**
	 * @param i
	 * @param _dateAchat
	 * @param _DatePeremption
	 * @param _AutreInfo
	 */
	public produitRecherche(int i, String _dateAchat, String _DatePeremption,
			String _AutreInfo) {
		champ1 = i;
		champ2 = _dateAchat;
		champ3 = _DatePeremption;
		champ4 = _AutreInfo;
	}
}
