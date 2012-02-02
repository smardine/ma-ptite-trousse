package fr.smardine.matroussedemaquillage.mdl;

import android.content.Context;
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
	 * @param p_ctx
	 */
	public MlMarque(int p_idMarque, Context p_ctx) {
		AccesTableTrousseMarque accesTrousse = new AccesTableTrousseMarque(
				p_ctx);
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
