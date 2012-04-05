package fr.smardine.matroussedemaquillage.factory;

import helper.DateHelper;

import java.util.ArrayList;

import fr.smardine.matroussedemaquillage.mdl.MlCategorie;
import fr.smardine.matroussedemaquillage.mdl.MlProduit;
import fr.smardine.matroussedemaquillage.mdl.cat.EnTypeCategorie;

public class MlProduitEnregistreFactory {

	public MlProduitEnregistreFactory() {

	}

	public MlProduit creationMlProduitEnregistre(ArrayList<Object> p_list) {
		MlProduit p = new MlProduit();

		for (int i = 0; i < p_list.size(); i++) {
			if (i < 8) {
				traitePremierePartie(i, p, p_list);
			} else {
				traiteDeuxiemePartie(i, p, p_list);
			}

		}
		return p;
	}

	private void traiteDeuxiemePartie(int p_idx, MlProduit p_produit,
			ArrayList<Object> p_list) {
		if (p_idx == 8) {
			p_produit.setMarque((String) p_list.get(p_idx));
		} else if (p_idx == 9) {
			p_produit.setDatePeremMilli((Long) p_list.get(p_idx));
		} else if (p_idx == 10) {
			p_produit.setPerime(Boolean.getBoolean((String) p_list.get(p_idx)));
		} else if (p_idx == 11) {
			p_produit.setPresquePerime(Boolean.getBoolean((String) p_list
					.get(p_idx)));
		} else if (p_idx == 12) {
			if (p_list.get(p_idx) != null) {
				p_produit.setNbJourAvantPeremp(Integer.parseInt((String) p_list
						.get(p_idx)));
			}

		}

	}

	private void traitePremierePartie(int p_idx, MlProduit p_produit,
			ArrayList<Object> p_list) {
		if (p_idx == 0) {
			p_produit.setIdProduit((Integer) p_list.get(p_idx));
		} else if (p_idx == 1) {
			p_produit.setNomProduit((String) p_list.get(p_idx));
		} else if (p_idx == 2) {
			p_produit.setCategorie(new MlCategorie(EnTypeCategorie
					.getEnumFromValue((String) p_list.get(3)), MlProduit
					.rechercheSousCat((String) p_list.get(2))));
		} else if (p_idx == 4) {
			p_produit.setTeinte((String) p_list.get(p_idx));
		} else if (p_idx == 5) {
			p_produit.setDateAchat(DateHelper
					.getDateFromDatabase((String) p_list.get(p_idx)));
		} else if (p_idx == 6) {
			p_produit.setDureeVie(Integer.parseInt((String) p_list.get(p_idx)));
		} else if (p_idx == 7) {
			p_produit.setDatePeremption(DateHelper
					.getDateFromDatabase((String) p_list.get(p_idx)));
		}

	}

}
