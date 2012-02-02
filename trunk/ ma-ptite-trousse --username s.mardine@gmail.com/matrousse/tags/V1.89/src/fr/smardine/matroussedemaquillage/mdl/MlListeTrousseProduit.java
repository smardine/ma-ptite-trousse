package fr.smardine.matroussedemaquillage.mdl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author smardine
 */
public class MlListeTrousseProduit extends ArrayList<MlTrousseProduit> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7780960990084934214L;
	private final List<MlTrousseProduit> list = new ArrayList<MlTrousseProduit>();

	/**
	 * @return une liste de MlProduit
	 */
	public List<MlTrousseProduit> getListProduit() {
		return list;
	}

}
