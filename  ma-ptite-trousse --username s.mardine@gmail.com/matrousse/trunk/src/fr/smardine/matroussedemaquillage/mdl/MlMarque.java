package fr.smardine.matroussedemaquillage.mdl;

import fr.smardine.matroussedemaquillage.base.accesTable.AccesTableTrousseMarque;

/**
 * classe gerant la marque d'un produit
 * @author smardine
 */
public class MlMarque {

	private final int idMarque;
	private final String nomMarque;

	/**
	 * @param p_idMarque
	 */
	public MlMarque(int p_idMarque) {
		AccesTableTrousseMarque accesTrousse = new AccesTableTrousseMarque(null);
		this.idMarque = p_idMarque;
		this.nomMarque = accesTrousse.getNomMarque(idMarque);
	}

	/**
	 * @return le nom de la marque
	 */
	public String getNomMarque() {
		return nomMarque;
	}
}
