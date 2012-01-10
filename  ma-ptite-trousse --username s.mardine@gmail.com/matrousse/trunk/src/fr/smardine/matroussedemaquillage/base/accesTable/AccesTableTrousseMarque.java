package fr.smardine.matroussedemaquillage.base.accesTable;

import android.content.ContentValues;
import android.content.Context;
import fr.smardine.matroussedemaquillage.base.structure.EnTable;
import fr.smardine.matroussedemaquillage.factory.RequeteFactory;

public class AccesTableTrousseMarque {

	private final RequeteFactory requeteFact;

	/**
	 * @param p_ctx le contexte
	 */
	public AccesTableTrousseMarque(Context p_ctx) {
		requeteFact = new RequeteFactory(p_ctx);

	}

	public void majMarqueChoisi(String p_marqueChoisie) {
		ContentValues modifiedValues = new ContentValues();
		modifiedValues.put("ischecked", "true");
		String whereClause = "nom_marque=?";
		String[] whereArgs = new String[] { p_marqueChoisie };
		requeteFact.majTable(EnTable.TROUSSE_MARQUE, modifiedValues, whereClause, whereArgs);
	}
}
