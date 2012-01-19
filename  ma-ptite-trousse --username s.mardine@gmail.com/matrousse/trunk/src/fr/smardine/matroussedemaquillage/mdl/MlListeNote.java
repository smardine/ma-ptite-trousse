package fr.smardine.matroussedemaquillage.mdl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author smardine
 */
public class MlListeNote extends ArrayList<MlNote> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7994787432928120542L;
	private final List<MlNote> list = new ArrayList<MlNote>();

	/**
	 * Constructeur
	 */
	public MlListeNote() {

	}

	/**
	 * @return une liste de MlProduit
	 */
	public List<MlNote> getListProduit() {
		return list;
	}

}
