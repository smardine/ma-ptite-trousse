package fr.smardine.matroussedemaquillage.recherche;

public class produitRecherche {
	String champ1;
	String champ2;
	String champ3;
	String champ4;
	
	
	public produitRecherche(String _nom,String _dateAchat,String _DatePeremption,String _AutreInfo) {
		champ1 = _nom;
		champ2 = _dateAchat;
		champ3 = _DatePeremption;
		champ4 = _AutreInfo;
	}
}
