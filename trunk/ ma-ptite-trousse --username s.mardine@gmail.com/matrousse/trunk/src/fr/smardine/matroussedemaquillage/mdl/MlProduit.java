package fr.smardine.matroussedemaquillage.mdl;

import java.sql.Date;

/**
 * Classe gerant les produits en base
 * @author smardine
 */
public class MlProduit {
	private int idProduit;
	private Date dateAchat;
	private Date datePeremption;
	private int nbJourDureeVie;
	private MlCategorie categorie;
	private MlSousCategorie sousCategorie;
	private MlMarque marque;
	private MlTeinte teinte;
	private boolean isPerime;

	public MlProduit() {

	}

}
