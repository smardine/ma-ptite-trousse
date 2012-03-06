package fr.smardine.matroussedemaquillage.factory;

import helper.DateHelper;

import java.util.ArrayList;

import fr.smardine.matroussedemaquillage.mdl.MlCategorie;
import fr.smardine.matroussedemaquillage.mdl.MlProduit;
import fr.smardine.matroussedemaquillage.mdl.cat.EnTypeCategorie;

public class MlProduitFactory {

	public MlProduitFactory() {

	}

	public MlProduit creationMlProduit(ArrayList<Object> p_list) {
		MlProduit p = new MlProduit();
		for (int i = 0; i < p_list.size(); i++) {
			if (i == 0) {
				p.setIdProduit((Integer) p_list.get(i));
			} else if (i == 1) {
				p.setNomProduit((String) p_list.get(i));
			} else if (i == 2) {
				p.setCategorie(new MlCategorie(EnTypeCategorie
						.getEnumFromValue((String) p_list.get(3)), MlProduit
						.rechercheSousCat((String) p_list.get(2))));
			} else if (i == 4) {
				p.setTeinte((String) p_list.get(i));
			} else if (i == 5) {
				p.setDateAchat(DateHelper.getDateFromDatabase((String) p_list
						.get(i)));
			} else if (i == 6) {
				p.setDureeVie(Integer.parseInt((String) p_list.get(i)));
			} else if (i == 7) {
				p.setDatePeremption(DateHelper
						.getDateFromDatabase((String) p_list.get(i)));
			} else if (i == 8) {
				p.setMarque((String) p_list.get(i));
			} else if (i == 9) {
				p.setDatePeremMilli((Long) p_list.get(i));
			} else if (i == 10) {
				p.setPerime(Boolean.getBoolean((String) p_list.get(i)));
			} else if (i == 11) {
				p.setPresquePerime(Boolean.getBoolean((String) p_list.get(i)));
			} else if (i == 12) {
				p.setNbJourAvantPeremp(Integer.parseInt((String) p_list.get(i)));
			}
		}
		return p;
	}

}
