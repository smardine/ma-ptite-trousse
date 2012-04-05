package fr.smardine.matroussedemaquillage.mdl;

import java.io.Serializable;

import fr.smardine.matroussedemaquillage.mdl.cat.EnCategorie;
import fr.smardine.matroussedemaquillage.mdl.cat.EnTypeCategorie;

public class MlCategorie implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1498661669298220239L;
	private final EnTypeCategorie categorie;
	private final EnCategorie sousCategorie;

	public MlCategorie(EnTypeCategorie p_categorie, EnCategorie p_sousCategorie) {
		this.categorie = p_categorie;
		this.sousCategorie = p_sousCategorie;

	}

	public EnTypeCategorie getCategorie() {
		return categorie;
	}

	public EnCategorie getSousCategorie() {
		return sousCategorie;
	}

}
