package fr.smardine.matroussedemaquillage.base.accesTable;

import android.content.ContentValues;
import android.content.Context;
import fr.smardine.matroussedemaquillage.base.structure.EnStructParam;
import fr.smardine.matroussedemaquillage.base.structure.EnTable;
import fr.smardine.matroussedemaquillage.factory.RequeteFactory;
import fr.smardine.matroussedemaquillage.variableglobale.EnTheme;

/**
 * @author smardine Gere l'acces a la table Param
 */
public class AccesTableParams {

	private final RequeteFactory requeteFact;

	/**
	 * @param p_ctx le contexte
	 */
	public AccesTableParams(Context p_ctx) {
		requeteFact = new RequeteFactory(p_ctx);
	}

	/**
	 * 
	 */
	public void CorrigeTableParam() {
		ContentValues modifiedValues = new ContentValues();
		modifiedValues.put(EnStructParam.DUREE_VIE_PEREMP.getNomChamp(), 30);
		String whereClause = EnStructParam.DUREE_VIE_PEREMP.getNomChamp()
				+ ">? or " + EnStructParam.DUREE_VIE_PEREMP.getNomChamp()
				+ "<?";
		String[] whereArgs = new String[] { "" + 99 + "", "" + 0 + "" };
		requeteFact.majTable(EnTable.PARAM, modifiedValues, whereClause,
				whereArgs);
	}

	/**
	 * 
	 */
	public void desactiveAlerte() {
		ContentValues values = new ContentValues();
		values.put("AfficheAlerte", "false");
		requeteFact.majTable(EnTable.PARAM, values, "", null);
	}

	/**
	 * @param p_theme
	 */
	public void majTheme(EnTheme p_theme) {
		ContentValues values = new ContentValues();
		values.put("Theme", p_theme.getLib());
		requeteFact.majTable(EnTable.PARAM, values, "", null);

	}

	/**
	 * @param p_affiche
	 */
	public void majAfficheAlerte(String p_affiche) {
		ContentValues values = new ContentValues();
		values.put("AfficheAlerte", p_affiche);
		requeteFact.majTable(EnTable.PARAM, values, "", null);
	}

	/**
	 * @param p_dureeVie
	 */
	public void majDureeVie(String p_dureeVie) {
		ContentValues values = new ContentValues();
		values.put("DureeViePeremp", p_dureeVie);
		requeteFact.majTable(EnTable.PARAM, values, "", null);
	}

}
