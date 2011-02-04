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
					int ValeurRentr�e = Integer.parseInt(str);
					if (ValeurRentr�e > 99) {
						textView.setText("99");
					}
					if (ValeurRentr�e <= 0) {
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
	 * Ex�cut� lorsque l'activit� devient visible � l'utilisateur. La fonction onStart() est suivie de la fonction onResume().
	 */
	@Override
	protected void onStart() {
		super.onStart();
		// popUp("onStart()-Page2");
	}

	/**
	 * Ex�cut�e a chaque passage en premier plan de l'activit�. Ou bien, si l'activit� passe � nouveau en premier (si une autre activit�
	 * �tait pass� en premier plan entre temps). La fonction onResume() est suivie de l'ex�cution de l'activit�.
	 */
	@Override
	protected void onResume() {
		super.onResume();
		// popUp("onResume()-Page2");

	}

	/**
	 * La fonction onStop() est ex�cut�e : - lorsque l'activit� n'est plus en premier plan - ou bien lorsque l'activit� va �tre d�truite
	 * Cette fonction est suivie : - de la fonction onRestart() si l'activit� passe � nouveau en premier plan - de la fonction onDestroy()
	 * lorsque l'activit� se termine ou bien lorsque le syst�me d�cide de l'arr�ter
	 */
	@Override
	protected void onStop() {
		super.onStop();
		// popUp("onStop-Page2");
	}

	/**
	 * La fonction onPause() est suivie : - d'un onResume() si l'activit� passe � nouveau en premier plan - d'un onStop() si elle devient
	 * invisible � l'utilisateur L'ex�cution de la fonction onPause() doit �tre rapide, car la prochaine activit� ne d�marrera pas tant que
	 * l'ex�cution de la fonction onPause() n'est pas termin�e.
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
			// on demarre la nouvelle activit�
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
