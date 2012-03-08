package fr.smardine.matroussedemaquillage.factory;

import java.util.ArrayList;

import fr.smardine.matroussedemaquillage.mdl.MlProduit;
import fr.smardine.matroussedemaquillage.mdl.MlTrousseProduit;
import fr.smardine.matroussedemaquillage.mdl.cat.EnTypeCategorie;

public class MlTrousseProduitFactory {

	public MlTrousseProduitFactory() {

	}

	public MlTrousseProduit construitTrousseProduit(ArrayList<Object> p_list) {
		MlTrousseProduit p = new MlTrousseProduit();

		for (int i = 0; i < p_list.size(); i++) {
			if (i == 0) {
				p.setIdTrousseproduit((Integer) p_list.get(i));
			} else if (i == 1) {
				p.setNomSousCat(MlProduit.rechercheSousCat((String) p_list
						.get(i)));
			} else if (i == 2) {
				p.setNomCat(EnTypeCategorie.getEnumFromValue((String) p_list
						.get(i)));
			} else if (i == 3) {
				p.setChecked(Boolean.parseBoolean((String) p_list.get(i)));
			}
		}

		return p;
	}
}
