package fr.smardine.matroussedemaquillage.factory;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import fr.smardine.matroussedemaquillage.base.BDAcces;
import fr.smardine.matroussedemaquillage.base.structure.EnTable;

/**
 * @author smardine S'occupe de gerer les requetes en bases.
 */
public class RequeteFactory {

	private final Context ctx;
	private final BDAcces bdAcces;

	/**
	 * @param p_ctx
	 */
	public RequeteFactory(Context p_ctx) {
		this.ctx = p_ctx;
		this.bdAcces = new BDAcces(ctx);

	}

	/**
	 * @param p_requete
	 * @return 1 champ la quete doit etre de type SELECT UNCHAMP FROM UNETABLE
	 *         [where...]
	 */
	public String get1Champ(String p_requete) {
		bdAcces.open();
		Cursor objCursor = bdAcces.getMdb().rawQuery(p_requete, null);
		String result = objCursor.getString(0);
		objCursor.close();
		bdAcces.close();
		return result;

	}

	/**
	 * @param p_table
	 * @return le nombre d'enregistrement(s) dans une table
	 */
	public int getNombreEnregistrement(EnTable p_table) {
		bdAcces.open();
		Cursor objCursor = bdAcces.getMdb().query(p_table.getNomTable(), null,
				null, null, null, null, null);
		int iNbChamp = objCursor.getCount();
		objCursor.close();
		bdAcces.close();
		return iNbChamp;
	}

	/**
	 * @param p_requete
	 * @return une liste de resultat
	 */
	public List<ArrayList<String>> getListeDeChamp(String p_requete) {
		ArrayList<ArrayList<String>> lstRetour = new ArrayList<ArrayList<String>>();
		bdAcces.open();
		Cursor c = bdAcces.getMdb().rawQuery(p_requete, null);
		while (c.moveToNext()) {
			ArrayList<String> lstIntermediaire = new ArrayList<String>();
			for (int i = 0; i < c.getColumnCount(); i++) {
				lstIntermediaire.add(c.getString(i));
			}
			lstRetour.add(lstIntermediaire);
		}
		c.close();
		bdAcces.close();
		return lstRetour;

	}

	/**
	 * @param p_table
	 * @param p_modifiedValue
	 * @param p_whereClause
	 * @param p_whereArgs
	 * @return le nombre d'enregistrement affecté
	 */
	public int majTable(EnTable p_table, ContentValues p_modifiedValue,
			String p_whereClause, String[] p_whereArgs) {
		bdAcces.open();
		int nb = bdAcces.getMdb().update(p_table.getNomTable(),
				p_modifiedValue, p_whereClause, p_whereArgs);
		bdAcces.close();
		return nb;
	}

	/**
	 * @param p_table
	 * @param p_whereClause
	 * @param p_whereArgs
	 * @return le nombre de ligne suprrimée(s)
	 */
	public int deleteTable(EnTable p_table, String p_whereClause,
			String[] p_whereArgs) {
		bdAcces.open();
		int nb = bdAcces.getMdb().delete(p_table.getNomTable(), p_whereClause,
				p_whereArgs);
		bdAcces.close();
		return nb;
	}
}
