package fr.smardine.matroussedemaquillage.param;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
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
import fr.smardine.matroussedemaquillage.variableglobale.G_changeLog;

public class tab3 extends Activity {
	BDAcces objBd;
	TextView txtView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.parametres_tab3);
		txtView = (TextView) findViewById(R.id.TvChangeLog);
		String texte = G_changeLog.Changement;
		txtView.setText(texte);
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
			// on demarre la nouvelle activit�
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

}
