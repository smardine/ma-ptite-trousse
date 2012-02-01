package fr.smardine.matroussedemaquillage.base.accesTable;

import android.content.Context;
import fr.smardine.matroussedemaquillage.base.RequeteFactory;

/**
 * @author smardine Acces a la table des Produit temporaires
 */
public class AccesTableTrousseTempo {

	private final RequeteFactory requeteFact;

	/**
	 * @param p_ctx le contexte
	 */
	public AccesTableTrousseTempo(Context p_ctx) {
		requeteFact = new RequeteFactory(p_ctx);
	}

	/**
	 * Suppression complte du contenu de la table
	 */
	public void deleteTable() {
		 requeteFact.deleteTable(EnTable.TROUSSE_TEMPO, "1", null);
	}

}
