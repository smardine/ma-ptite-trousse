package fr.smardine.matroussedemaquillage.mdl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author smardine
 */
public class MlListeMarque extends ArrayList<MlMarque> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8980107716273754718L;
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
