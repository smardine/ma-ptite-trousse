package fr.smardine.matroussedemaquillage.base.accesTable;

import android.content.ContentValues;
import android.content.Context;
import fr.smardine.matroussedemaquillage.base.RequeteFactory;
import fr.smardine.matroussedemaquillage.base.structure.EnStructMarque;
import fr.smardine.matroussedemaquillage.base.structure.EnTable;

/**
 * @author smardine
 */
public class AccesTableTrousseMarque {

	private final RequeteFactory requeteFact;

	/**
	 * @param p_ctx le contexte
	 */
	public AccesTableTrousseMarque(Context p_ctx) {
		requeteFact = new RequeteFactory(p_ctx);

	}

	/**
	 * mets a jour la marque choisie par l'utilisateur
	 * @param p_marqueChoisie
	 */
	public void majMarqueChoisi(String p_marqueChoisie) {
		ContentValues modifiedValues = new ContentValues();
		modifiedValues.put("ischecked", "true");
		String whereClause = "nom_marque=?";
		String[] whereArgs = new String[] { p_marqueChoisie };
		requeteFact.majTable(EnTable.TROUSSE_MARQUE, modifiedValues,
				whereClause, whereArgs);
	}

	/**
	 * @param p_idMarque
	 * @return le nom de la marque en fonction de l'id
	 */
	public String getNomMarque(int p_idMarque) {
		String requete = "SELECT " + EnStructMarque.NOM.getNomChamp()
				+ " FROM " + EnTable.TROUSSE_MARQUE.getNomTable() + " WHERE "
				+ EnStructMarque.ID.getNomChamp() + "=" + p_idMarque;

		return requeteFact.get1Champ(requete);
	}
}
