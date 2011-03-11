package fr.smardine.matroussedemaquillage.param;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.ViewSwitcher.ViewFactory;
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
import fr.smardine.matroussedemaquillage.variableglobale.EnTheme;

public class tab2 extends Activity implements OnItemSelectedListener, ViewFactory {
	ImageSwitcher mSwitcher;

	Spinner s1;
	BDAcces objBd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.parametres_tab2);

		s1 = (Spinner) findViewById(R.id.SpinnerTheme);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mStrings);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s1.setAdapter(adapter);

		s1.setOnItemSelectedListener(this);

		mSwitcher = (ImageSwitcher) findViewById(R.id.switcher);
		mSwitcher.setFactory(this);
		mSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
		mSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));

		verifieLeThemeChoisi();

	}

	/**
	 * 
	 */
	private void verifieLeThemeChoisi() {
		objBd = new BDAcces(this);
		objBd.open();
		String[] colonnes = { "AfficheAlerte", "DureeViePeremp", "Theme" };
		@SuppressWarnings("rawtypes")
		ArrayList[] Param = objBd.renvoi_param(colonnes);

		String nomThemeChoisi = Param[2].get(0).toString().trim();
		if (EnTheme.Bisounours.getLib().equals(nomThemeChoisi)) {

			s1.setSelection(EnTheme.Bisounours.getCode());
		}
//		if (EnTheme.Classique.getLib().equals(nomThemeChoisi)) {
//
//			s1.setSelection(EnTheme.Classique.getCode());
//		}
		if (EnTheme.Fleur.getLib().equals(nomThemeChoisi)) {

			s1.setSelection(EnTheme.Fleur.getCode());
		}
		objBd.close();
	}

	private static final String[] mStrings = { EnTheme.Fleur.getLib(), EnTheme.Bisounours.getLib() };

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
			boolean isLaunchByMain = getIntent().getBooleanExtra(ActivityParam.LaunchFromMain, false);

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

	/**
	 * 
	 */
	public void OnDestroy() {
		super.onDestroy();
		// popUp("OnDestroy-Page2");

	}

	@Override
	public void onItemSelected(AdapterView<?> Parent, View view, int position, long id) {
		// TODO Auto-generated method stub

		if (id == EnTheme.Classique.getCode()) {
			ContentValues values = new ContentValues();
			values.put("Theme", EnTheme.Classique.getLib());

			objBd.open();
			objBd.majTable("Param", values, "", null);
			objBd.close();
			mSwitcher.setImageResource(mImageIds[EnTheme.Classique.getCode()]);
		}
		if (id == EnTheme.Fleur.getCode()) {
			ContentValues values = new ContentValues();
			values.put("Theme", EnTheme.Fleur.getLib());

			objBd.open();
			objBd.majTable("Param", values, "", null);
			objBd.close();
			mSwitcher.setImageResource(mImageIds[EnTheme.Fleur.getCode()]);
		}
		if (id == EnTheme.Bisounours.getCode()) {
			ContentValues values = new ContentValues();
			values.put("Theme", EnTheme.Bisounours.getLib());

			objBd.open();
			objBd.majTable("Param", values, "", null);
			objBd.close();
			mSwitcher.setImageResource(mImageIds[EnTheme.Bisounours.getCode()]);
		}
	}

	private final Integer[] mImageIds = { R.drawable.acceuil_fleur, R.drawable.acceuil_bisounours };

	@Override
	public void onNothingSelected(AdapterView<?> p_arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public View makeView() {
		ImageView i = new ImageView(this);
		i.setBackgroundColor(0xFF000000);
		i.setScaleType(ImageView.ScaleType.FIT_CENTER);
		i.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		return i;
	}

}
