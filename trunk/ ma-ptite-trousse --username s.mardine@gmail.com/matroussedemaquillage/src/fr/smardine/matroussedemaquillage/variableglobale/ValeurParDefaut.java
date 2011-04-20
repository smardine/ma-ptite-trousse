package fr.smardine.matroussedemaquillage.variableglobale;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * recuperer
 * @author smardine
 */
public class ValeurParDefaut {
	private final SharedPreferences preferences;
	private String ACTION_PAR_DEFAUT;
	private int PASTILLE_COULEUR;

	/**
	 * Constructeur
	 * @param ctx - le contexte de l'activity qui lance cette classe
	 */
	public ValeurParDefaut(Context ctx) {
		preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
		ACTION_PAR_DEFAUT = preferences.getString("default_action", EnActionParDefaut.PERIME.getLib());
		PASTILLE_COULEUR = Integer.parseInt(preferences.getString("pastille_color", "-65536"));

	}

	/**
	 * obtenir l'action par defaut
	 * @return String
	 */
	public String getActionParDefaut() {
		return ACTION_PAR_DEFAUT;
	}

	/**
	 * Positionner l'action par defaut
	 * @param p_actionParDefaut - String
	 */
	public void setActionParDefaut(String p_actionParDefaut) {
		SharedPreferences.Editor editor = preferences.edit();

		editor.putString("default_action", p_actionParDefaut);
		ACTION_PAR_DEFAUT = p_actionParDefaut;
		editor.commit();
	}

	/**
	 * Obtenir la couleur de la pastille
	 * @return int
	 */
	public int getCouleurPastille() {
		return PASTILLE_COULEUR;
	}

	/**
	 * positionner la couleur de la pastille
	 * @param p_pastilleColor - int
	 */
	public void setCouleurPastille(int p_pastilleColor) {
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("pastille_color", "" + p_pastilleColor + "");
		PASTILLE_COULEUR = p_pastilleColor;
		editor.commit();
	}

}
