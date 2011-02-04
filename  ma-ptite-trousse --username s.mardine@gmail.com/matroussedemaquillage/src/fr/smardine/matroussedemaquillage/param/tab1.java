package fr.smardine.matroussedemaquillage.param;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.CheckBox;
import android.widget.EditText;
import fr.smardine.matroussedemaquillage.Main;
import fr.smardine.matroussedemaquillage.R;
import fr.smardine.matroussedemaquillage.base.BDAcces;
import fr.smardine.matroussedemaquillage.recherche.recherchetabsbuttons;

public class tab1 extends Activity {
	EditText textView;
	CheckBox CbAfficheAlerte;
	BDAcces objBd;

	@SuppressWarnings("rawtypes")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.parametres_tab1);
		textView = (EditText) findViewById(R.id.Param_DureeVie);
		CbAfficheAlerte = (CheckBox) findViewById(R.id.CbAfficheAlerte);
		objBd = new BDAcces(this);
		objBd.open();
		String[] colonnes = { "AfficheAlerte", "DureeViePeremp", "Theme" };
		ArrayList[] Param = objBd.renvoi_param(colonnes);

		if (Param[0].get(0).equals("true")) {
			CbAfficheAlerte.setChecked(true);
		}
		textView.setText(Param[1].get(0).toString());

		objBd.close();
		textView.addTextChangedListener(new TextWatcher() {

			// int len=0;
			@Override
			public void afterTextChanged(Editable s) {
				String str = textView.getText().toString();
				if (!str.equals("")) {
					int ValeurRentrée = Integer.parseInt(str);
					if (ValeurRentrée > 99) {
						textView.setText("99");
					}
					if (ValeurRentrée <= 0) {
						textView.setText("1");
					}
				}

				/*
				 * if(str.length()==4*&& len <str.length()*){ textView.append("-"); }
				 */
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

				// String str = textView.getText().toString();
				// len = str.length();
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

		});

	}

	@Override
	protected void onRestart() {
		super.onRestart();
		// popUp("onRestart()-Page2");
	}

	/**
	 * Exécuté lorsque l'activité devient visible à l'utilisateur. La fonction onStart() est suivie de la fonction onResume().
	 */
	@Override
	protected void onStart() {
		super.onStart();
		// popUp("onStart()-Page2");
	}

	/**
	 * Exécutée a chaque passage en premier plan de l'activité. Ou bien, si l'activité passe à nouveau en premier (si une autre activité
	 * était passé en premier plan entre temps). La fonction onResume() est suivie de l'exécution de l'activité.
	 */
	@Override
	protected void onResume() {
		super.onResume();
		// popUp("onResume()-Page2");

	}

	/**
	 * La fonction onStop() est exécutée : - lorsque l'activité n'est plus en premier plan - ou bien lorsque l'activité va être détruite
	 * Cette fonction est suivie : - de la fonction onRestart() si l'activité passe à nouveau en premier plan - de la fonction onDestroy()
	 * lorsque l'activité se termine ou bien lorsque le système décide de l'arrêter
	 */
	@Override
	protected void onStop() {
		super.onStop();
		// popUp("onStop-Page2");
	}

	/**
	 * La fonction onPause() est suivie : - d'un onResume() si l'activité passe à nouveau en premier plan - d'un onStop() si elle devient
	 * invisible à l'utilisateur L'exécution de la fonction onPause() doit être rapide, car la prochaine activité ne démarrera pas tant que
	 * l'exécution de la fonction onPause() n'est pas terminée.
	 */
	@Override
	protected void onPause() {
		super.onPause();
		AlertDialog.Builder ad = new AlertDialog.Builder(this);
		ad.setTitle("Attention");
		ad.setMessage("Merci de saisir une valeur comprise entre 1 et 99 mois");
		ad.setPositiveButton("Ok", null);

		String DurreeViePeremp = textView.getText().toString();
		if (DurreeViePeremp.equals("")) {
			ad.show();
		} else {
			String AfficheAlerte = "";

			boolean IsChecked = CbAfficheAlerte.isChecked();
			if (IsChecked) {
				AfficheAlerte = "true";
			} else {
				AfficheAlerte = "false";
			}

			ContentValues values = new ContentValues();
			values.put("AfficheAlerte", AfficheAlerte);
			values.put("DureeViePeremp", DurreeViePeremp);

			objBd.open();
			objBd.majTable("Param", values, "", null);
			objBd.close();

		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent main = new Intent(this, Main.class);
			main.putExtra("calledFromParam", true);
			startActivity(main);
			finish();
			return true;
		}
		if (keyCode == KeyEvent.KEYCODE_SEARCH) {
			Intent intentRecherche = new Intent(this, recherchetabsbuttons.class);
			// on demarre la nouvelle activité
			startActivity(intentRecherche);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	public void OnDestroy() {
		super.onDestroy();
		// popUp("OnDestroy-Page2");

	}

}
