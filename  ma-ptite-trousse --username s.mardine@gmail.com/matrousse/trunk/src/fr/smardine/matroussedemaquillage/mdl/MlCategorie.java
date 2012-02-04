package fr.smardine.matroussedemaquillage.mdl;

import fr.smardine.matroussedemaquillage.mdl.cat.EnCategorie;
import fr.smardine.matroussedemaquillage.mdl.cat.EnTypeCategorie;

public class MlCategorie {

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
