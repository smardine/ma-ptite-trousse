package fr.smardine.matroussedemaquillage.base.accesTable;

import android.content.ContentValues;
import android.content.Context;
import fr.smardine.matroussedemaquillage.base.RequeteFactory;
import fr.smardine.matroussedemaquillage.base.structure.EnStructParam;
import fr.smardine.matroussedemaquillage.base.structure.EnTable;
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
	 * Si les params de durre de vie sont >99 ou <0 => on fixe la valeur à 30
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
	 * @return le nb de jour avant de prevenir l'utilisateur qu'un produit va
	 *         etre perimé
	 */
	public int getDureeViePeremption() {
		String requete = "SELECT "
				+ EnStructParam.DUREE_VIE_PEREMP.getNomChamp() + " FROM "
				+ EnTable.PARAM.getNomTable();

		return Integer.parseInt(requeteFact.get1Champ(requete));

	}

	/**
	 * desactiver l'aletre sur les message perimé au demarrage de l'appli
	 */
	public void desactiveAlerte() {
		ContentValues values = new ContentValues();
		values.put("AfficheAlerte", "false");
		requeteFact.majTable(EnTable.PARAM, values, "", null);
	}

	/**
	 * Mettre a jour le theme de l'appli
	 * @param p_theme
	 * @return true ou false
	 */
	public boolean majTheme(EnTheme p_theme) {
		ContentValues values = new ContentValues();
		values.put("Theme", p_theme.getLib());
		return (requeteFact.majTable(EnTable.PARAM, values, "", null) > 0);

	}

	/**
	 * @param p_affiche
	 */
	public void majAfficheAlerte(boolean p_affiche) {
		ContentValues values = new ContentValues();
		if (p_affiche) {
			values.put("AfficheAlerte", "true");
		} else {
			values.put("AfficheAlerte", "false");
		}

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

	/**
	 * @return le theme choisi par l'utilisateur
	 */
	public EnTheme getThemeChoisi() {
		String requete = "SELECT " + EnStructParam.THEME.getNomChamp()
				+ " FROM " + EnTable.PARAM.getNomTable();
		String nomTheme = requeteFact.get1Champ(requete);
		return EnTheme.getThemeFromValue(nomTheme);

	}

	/**
	 * @return l'etat d'affichage de l'alerte produit perime (true ou false)
	 */
	public boolean getAfficheAlerte() {
		String requete = "SELECT " + EnStructParam.AFFICHE_ALERTE.getNomChamp()
				+ " FROM " + EnTable.PARAM.getNomTable();
		if ("true".equals(requeteFact.get1Champ(requete))) {
			return true;
		}
		return false;

	}

	/**
	 * @return le chemin de la bdd
	 */
	public String getDatabasePath() {
		return requeteFact.getDatabasePath();
	}

}
