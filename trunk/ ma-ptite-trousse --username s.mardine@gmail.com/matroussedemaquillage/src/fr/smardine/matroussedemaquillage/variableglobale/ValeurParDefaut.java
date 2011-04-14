package fr.smardine.matroussedemaquillage.variableglobale;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import fr.smardine.matroussedemaquillage.R;

/**
 * recuperer
 * @author smardine
 */
public class ValeurParDefaut {
	private final SharedPreferences preferences;
	private String ACTION_PAR_DEFAUT;
	private int PASTILLE_COULEUR;

	public ValeurParDefaut(Context ctx) {
		preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
		ACTION_PAR_DEFAUT = preferences.getString("default_action", EnActionParDefaut.PERIME.getLib());
		PASTILLE_COULEUR = preferences.getInt("pastille_color", R.array.pastilleColor);
	}

	public String getActionParDefaut() {
		return ACTION_PAR_DEFAUT;
	}

	public void setActionParDefaut(String p_actionParDefaut) {
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("default_action", p_actionParDefaut);
		ACTION_PAR_DEFAUT = p_actionParDefaut;
		editor.commit();
	}

	public int getCouleurPastille() {
		return PASTILLE_COULEUR;
	}

	public void setCouleurPastille(int p_pastilleColor) {
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt("pastille_color", p_pastilleColor);
		PASTILLE_COULEUR = p_pastilleColor;
		editor.commit();
	}

}
