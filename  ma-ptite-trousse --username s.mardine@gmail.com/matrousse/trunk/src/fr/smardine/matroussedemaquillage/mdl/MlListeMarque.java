package fr.smardine.matroussedemaquillage.mdl;

import java.util.ArrayList;
import java.util.List;

public class MlListeMarque extends ArrayList<MlMarque> {

	private final List<MlMarque> list = new ArrayList<MlMarque>();

	/**
	 * Constructeur
	 */
	public MlListeMarque() {

	}

	/**
	 * @return une liste de MlProduit
	 */
	public List<MlMarque> getListeNote() {
		return list;
	}

}
