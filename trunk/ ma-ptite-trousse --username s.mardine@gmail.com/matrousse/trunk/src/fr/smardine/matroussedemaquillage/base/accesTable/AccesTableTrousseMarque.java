package fr.smardine.matroussedemaquillage.base.accesTable;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import fr.smardine.matroussedemaquillage.base.RequeteFactory;
import fr.smardine.matroussedemaquillage.base.structure.EnStructMarque;
import fr.smardine.matroussedemaquillage.base.structure.EnTable;
import fr.smardine.matroussedemaquillage.mdl.MlListeMarque;
import fr.smardine.matroussedemaquillage.mdl.MlMarque;

/**
 * @author smardine
 */
public class AccesTableTrousseMarque {

	private final RequeteFactory requeteFact;
	private final Context ctx;

	/**
	 * @param p_ctx le contexte
	 */
	public AccesTableTrousseMarque(Context p_ctx) {
		this.ctx = p_ctx;
		requeteFact = new RequeteFactory(p_ctx);

	}

	/**
	 * mets a jour la marque choisie par l'utilisateur
	 * @param p_marqueChoisie
	 * @param p_isChecked
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
	public MlListeMarque getListeMarques() {
		MlListeMarque lstRetour = new MlListeMarque();
		String requete = "SELECT " + EnStructMarque.ID.getNomChamp() + " FROM "
				+ EnTable.TROUSSE_MARQUE.getNomTable();

		// return requeteFact.getListeDeChamp(requete).get(0);

		List<ArrayList<String>> lstResult = requeteFact
				.getListeDeChamp(requete);
		for (int i = 0; i < lstResult.size(); i++) {
			ArrayList<String> unEnsemble = lstResult.get(i);
			for (String s : unEnsemble) {
				int idMarque = Integer.parseInt(s);
				MlMarque m = new MlMarque(idMarque, ctx);
				lstRetour.add(m);
			}

		}
		return lstRetour;
	}

	/**
	 * @param p_nomMarque
	 * @return true ou false
	 */
	public boolean createNewMarque(String p_nomMarque) {
		ContentValues values = new ContentValues();
		values.put("nom_marque", p_nomMarque);
		values.put("ischecked", "false");
		return requeteFact.insertDansTable(EnTable.TROUSSE_MARQUE, values);
	}

	/**
	 * @param p_nomMarque
	 * @return true ou false
	 */
	public boolean isMarqueExist(String p_nomMarque) {
		MlListeMarque lstMarque = getListeMarques();
		for (MlMarque m : lstMarque) {
			if (m.getNomMarque().equals(p_nomMarque)) {
				return true;
			}
		}
		return false;
	}
}
