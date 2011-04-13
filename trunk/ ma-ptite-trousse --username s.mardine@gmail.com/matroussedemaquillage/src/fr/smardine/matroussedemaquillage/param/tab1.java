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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import fr.smardine.matroussedemaquillage.Main;
import fr.smardine.matroussedemaquillage.R;
import fr.smardine.matroussedemaquillage.base.BDAcces;
import fr.smardine.matroussedemaquillage.note.note_page1;
import fr.smardine.matroussedemaquillage.note.note_saisie;
import fr.smardine.matroussedemaquillage.recherche.Recherche;
import fr.smardine.matroussedemaquillage.recherche.affiche_detail;
import fr.smardine.matroussedemaquillage.recherche.recherche_produit_perime;
import fr.smardine.matroussedemaquillage.remplir.choix_produit_a_duppliquer;
import fr.smardine.matroussedemaquillage.remplir.formulaire_entree_page1bis;
import fr.smardine.matroussedemaquillage.remplir.formulaire_entree_page3;
import fr.smardine.matroussedemaquillage.variableglobale.ActivityParam;

public class tab1 extends Activity implements OnClickListener, ColorPickerDialog.OnColorChangedListener {
	EditText textView;
	CheckBox CbAfficheAlerte;
	BDAcces objBd;
	Button BtColorPicker;
	TextView ApercuCouleur;

	@SuppressWarnings("rawtypes")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.parametres_tab1);
		textView = (EditText) findViewById(R.id.Param_DureeVie);
		CbAfficheAlerte = (CheckBox) findViewById(R.id.CbAfficheAlerte);
		BtColorPicker = (Button) findViewById(R.id.BtColorPicker);
		BtColorPicker.setOnClickListener(this);
		ApercuCouleur = (TextView) findViewById(R.id.TvPreviewColor);

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
			Intent intent = null;
			boolean isLaunchByNotePage1 = getIntent().getBooleanExtra(ActivityParam.LaunchFromNotePage1, false);
			boolean isLaunchByNoteSaisie = getIntent().getBooleanExtra(ActivityParam.LaunchFromNoteSaisie, false);
			boolean isLaunchByAfficheDetail = getIntent().getBooleanExtra(ActivityParam.LaunchFromAfficheDetail, false);
			boolean isLaunchByProduitPerime = getIntent().getBooleanExtra(ActivityParam.LaunchFromRechercheProduitPerime, false);
			boolean isLaunchByRecherche = getIntent().getBooleanExtra(ActivityParam.LaunchFromRecherche, false);
			boolean isLaunchByDupplique = getIntent().getBooleanExtra(ActivityParam.LaunchFromDuppliquer, false);
			boolean isLaunchByPage1 = getIntent().getBooleanExtra(ActivityParam.LaunchFromPage1, false);
			boolean isLaunchBypagerecap = getIntent().getBooleanExtra(ActivityParam.LaunchFromPageRecap, false);
			boolean isLaunchByMain = getIntent().getBooleanExtra("LaunchFromMain", false);
			if (isLaunchByNotePage1) {
				intent = new Intent(this, note_page1.class);

			}
			if (isLaunchByNoteSaisie) {
				String idNote = getIntent().getStringExtra(ActivityParam.IdNote);
				intent = new Intent(this, note_saisie.class);
				intent.putExtra(ActivityParam.IdNote, idNote);
			}
			if (isLaunchByAfficheDetail) {
				String idProduit = getIntent().getStringExtra(ActivityParam.IdProduit);
				intent = new Intent(this, affiche_detail.class);
				intent.putExtra(ActivityParam.IdProduit, idProduit);
				intent.putExtra(ActivityParam.LaunchFromRecherche, getIntent().getBooleanExtra(ActivityParam.LaunchFromRecherche, false));
				intent.putExtra(ActivityParam.LaunchFromRechercheProduitPerime,
						getIntent().getBooleanExtra(ActivityParam.LaunchFromRechercheProduitPerime, false));
			}
			if (isLaunchByProduitPerime) {
				intent = new Intent(this, recherche_produit_perime.class);
			}
			if (isLaunchByRecherche) {
				intent = new Intent(this, Recherche.class);
			}
			if (isLaunchByDupplique) {
				intent = new Intent(this, choix_produit_a_duppliquer.class);
			}
			if (isLaunchByPage1) {
				String MarqueChoisie = getIntent().getStringExtra(ActivityParam.Marque);
				String DureeVie = getIntent().getStringExtra(ActivityParam.DurreeDeVie);
				String DateChoisie = getIntent().getStringExtra(ActivityParam.DateAchat);
				String numTeinte = getIntent().getStringExtra(ActivityParam.NumeroDeTeinte);
				String nomProduitRecup = getIntent().getStringExtra(ActivityParam.NomProduit);

				intent = new Intent(this, formulaire_entree_page1bis.class);
				intent.putExtra(ActivityParam.Marque, MarqueChoisie.trim());
				intent.putExtra(ActivityParam.DurreeDeVie, DureeVie.trim());
				intent.putExtra(ActivityParam.DateAchat, DateChoisie.trim());
				intent.putExtra(ActivityParam.NumeroDeTeinte, numTeinte.trim());
				intent.putExtra(ActivityParam.NomProduit, nomProduitRecup.trim());

			}
			if (isLaunchBypagerecap) {
				String MarqueChoisie = getIntent().getStringExtra(ActivityParam.Marque);
				String DureeVie = getIntent().getStringExtra(ActivityParam.DurreeDeVie);
				String DateChoisie = getIntent().getStringExtra(ActivityParam.DateAchat);
				String numTeinte = getIntent().getStringExtra(ActivityParam.NumeroDeTeinte);
				String nomProduitRecup = getIntent().getStringExtra(ActivityParam.NomProduit);

				intent = new Intent(this, formulaire_entree_page3.class);
				intent.putExtra(ActivityParam.Marque, MarqueChoisie.trim());
				intent.putExtra(ActivityParam.DurreeDeVie, DureeVie.trim());
				intent.putExtra(ActivityParam.DateAchat, DateChoisie.trim());
				intent.putExtra(ActivityParam.NumeroDeTeinte, numTeinte.trim());
				intent.putExtra(ActivityParam.NomProduit, nomProduitRecup.trim());
			}
			if (isLaunchByMain) {
				intent = new Intent(this, Main.class);
			}
			intent.putExtra(ActivityParam.LaunchFromParam, true);
			startActivity(intent);
			termineActivity();
			return true;
		}
		if (keyCode == KeyEvent.KEYCODE_SEARCH) {
			Intent intentRecherche = new Intent(this, Recherche.class);
			// on demarre la nouvelle activité
			startActivity(intentRecherche);
			termineActivity();
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 
	 */
	private void termineActivity() {
		finish();
	}

	public void OnDestroy() {
		super.onDestroy();
		// popUp("OnDestroy-Page2");

	}

	@Override
	public void onClick(View p_v) {
		if (p_v == BtColorPicker) {
			new ColorPickerDialog(this, this, 0).show();
		}

	}

	@Override
	public void colorChanged(int p_color) {
		ApercuCouleur.setBackgroundColor(p_color);

	}

}
