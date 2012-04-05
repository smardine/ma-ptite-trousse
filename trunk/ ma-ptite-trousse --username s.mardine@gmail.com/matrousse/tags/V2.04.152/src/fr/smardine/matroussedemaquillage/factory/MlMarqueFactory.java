package fr.smardine.matroussedemaquillage.factory;

import java.util.ArrayList;

import fr.smardine.matroussedemaquillage.mdl.MlMarque;

public class MlMarqueFactory {

	public MlMarqueFactory() {

	}

	public MlMarque creationMlMarque(ArrayList<Object> p_list) {
		MlMarque m = new MlMarque();
		for (int i = 0; i < p_list.size(); i++) {
			if (i == 0) {
				m.setIdMarque((Integer) p_list.get(i));
			} else if (i == 1) {
				m.setNomMarque((String) p_list.get(i));
			} else if (i == 2) {
				m.setChecked(Boolean.getBoolean((String) p_list.get(i)));
			}
		}
		return m;
	}
}
