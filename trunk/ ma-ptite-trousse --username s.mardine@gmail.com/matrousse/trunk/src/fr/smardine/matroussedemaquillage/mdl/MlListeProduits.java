package fr.smardine.matroussedemaquillage.mdl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author smardine
 */
public class MlListeProduits extends ArrayList<MlProduit> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6276645582012365963L;
	private final List<MlProduit> list = new ArrayList<MlProduit>();

	/**
	 * @return une liste de MlProduit
	 */
	public List<MlProduit> getListProduit() {

		return list;
	}
}
