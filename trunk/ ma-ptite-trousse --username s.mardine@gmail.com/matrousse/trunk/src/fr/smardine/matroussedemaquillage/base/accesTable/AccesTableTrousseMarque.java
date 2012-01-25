package fr.smardine.matroussedemaquillage.base.accesTable;

import java.util.ArrayList;

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
	public void majMarqueChoisi(String p_marqueChoisie, boolean p_isChecked) {
		ContentValues modifiedValues = new ContentValues();
		if (p_isChecked) {
			modifiedValues.put("ischecked", "true");
		} else {
			modifiedValues.put("ischecked", "false");
		}

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

	/**
	 * @return la liste complete des marques enregstrée en base
	 */
	public ArrayList<String> getListeMarques() {
		String requete = "SELECT " + EnStructMarque.NOM.getNomChamp()
				+ " FROM " + EnTable.TROUSSE_MARQUE.getNomTable();
		return requeteFact.getListeDeChamp(requete).get(0);
	}

	public boolean createNewMarque(String p_nomMarque) {
		ContentValues values = new ContentValues();
		values.put("nom_marque", p_nomMarque);
		values.put("ischecked", "false");
		return requeteFact.insertDansTable(EnTable.TROUSSE_MARQUE, values);
	}

	public boolean isMarqueExist(String p_nomMarque) {
		ArrayList<String> lstMarque = getListeMarques();
		for (String s : lstMarque) {
			if (s.equals(p_nomMarque)) {
				return true;
			}
		}
		return false;
	}
}
