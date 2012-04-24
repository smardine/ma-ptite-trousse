package fr.smardine.matroussedemaquillage.param;

import helper.SerialisableHelper;
import widget.majWidget;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import fr.smardine.matroussedemaquillage.Main;
import fr.smardine.matroussedemaquillage.mdl.MlNote;
import fr.smardine.matroussedemaquillage.mdl.MlProduit;
import fr.smardine.matroussedemaquillage.note.note_page1;
import fr.smardine.matroussedemaquillage.note.note_saisie;
import fr.smardine.matroussedemaquillage.recherche.Recherche;
import fr.smardine.matroussedemaquillage.recherche.affiche_detail;
import fr.smardine.matroussedemaquillage.recherche.recherche_produit_perime;
import fr.smardine.matroussedemaquillage.remplir.choix_produit_a_duppliquer;
import fr.smardine.matroussedemaquillage.remplir.formulaire_entree_page1bis;
import fr.smardine.matroussedemaquillage.remplir.formulaire_entree_page3;
import fr.smardine.matroussedemaquillage.variableglobale.ActivityParam;

public class SuperParamActivity extends Activity {

	private MlProduit produit;
	private MlNote note;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		recupereMlProduitfromPreviousActivity();
		recupereMlNoteFromPreviousActivity();
	}

	/**
	 * Exécuté lorsque l'activité devient visible à l'utilisateur. La fonction
	 * onStart() est suivie de la fonction onResume().
	 */
	@Override
	protected void onStart() {
		super.onStart();
	}

	/**
	 * Exécutée a chaque passage en premier plan de l'activité. Ou bien, si
	 * l'activité passe à nouveau en premier (si une autre activité était passé
	 * en premier plan entre temps). La fonction onResume() est suivie de
	 * l'exécution de l'activité.
	 */
	@Override
	protected void onResume() {
		super.onResume();
	}

	/**
	 * La fonction onStop() est exécutée : - lorsque l'activité n'est plus en
	 * premier plan - ou bien lorsque l'activité va être détruite Cette fonction
	 * est suivie : - de la fonction onRestart() si l'activité passe à nouveau
	 * en premier plan - de la fonction onDestroy() lorsque l'activité se
	 * termine ou bien lorsque le système décide de l'arrêter
	 */
	@Override
	protected void onStop() {
		super.onStop();
	}

	/**
	 * La fonction onPause() est suivie : - d'un onResume() si l'activité passe
	 * à nouveau en premier plan - d'un onStop() si elle devient invisible à
	 * l'utilisateur L'exécution de la fonction onPause() doit être rapide, car
	 * la prochaine activité ne démarrera pas tant que l'exécution de la
	 * fonction onPause() n'est pas terminée.
	 */
	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onRestart() {
		super.onRestart();

	}

	/**
	 * 
	 */
	public void OnDestroy() {
		super.onDestroy();

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			new majWidget(this, false);
			Intent intent = null;
			boolean isLaunchByNotePage1 = getIntent().getBooleanExtra(
					ActivityParam.LaunchFromNotePage1, false);
			boolean isLaunchByNoteSaisie = getIntent().getBooleanExtra(
					ActivityParam.LaunchFromNoteSaisie, false);
			boolean isLaunchByAfficheDetail = getIntent().getBooleanExtra(
					ActivityParam.LaunchFromAfficheDetail, false);
			boolean isLaunchByProduitPerime = getIntent().getBooleanExtra(
					ActivityParam.LaunchFromRechercheProduitPerime, false);
			boolean isLaunchByRecherche = getIntent().getBooleanExtra(
					ActivityParam.LaunchFromRecherche, false);
			boolean isLaunchByDupplique = getIntent().getBooleanExtra(
					ActivityParam.LaunchFromDuppliquer, false);
			boolean isLaunchByPage1 = getIntent().getBooleanExtra(
					ActivityParam.LaunchFromPage1, false);
			boolean isLaunchBypagerecap = getIntent().getBooleanExtra(
					ActivityParam.LaunchFromPageRecap, false);
			boolean isLaunchByMain = getIntent().getBooleanExtra(
					"LaunchFromMain", false);
			if (isLaunchByNotePage1) {
				intent = new Intent(this, note_page1.class);

			}
			if (isLaunchByNoteSaisie) {
				// String idNote = getIntent()
				// .getStringExtra(ActivityParam.IdNote);
				intent = new Intent(this, note_saisie.class);
				transfereMlNoteToActivity(intent);
				// intent.putExtra(ActivityParam.IdNote, idNote);
			}
			if (isLaunchByAfficheDetail) {
				// String idProduit = getIntent().getStringExtra(
				// ActivityParam.IdProduit);
				intent = new Intent(this, affiche_detail.class);
				transfereMlProduitToActivity(intent);
				// intent.putExtra(ActivityParam.IdProduit, idProduit);
				intent.putExtra(
						ActivityParam.LaunchFromRecherche,
						getIntent().getBooleanExtra(
								ActivityParam.LaunchFromRecherche, false));
				intent.putExtra(
						ActivityParam.LaunchFromRechercheProduitPerime,
						getIntent().getBooleanExtra(
								ActivityParam.LaunchFromRechercheProduitPerime,
								false));
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
				// String MarqueChoisie = getIntent().getStringExtra(
				// ActivityParam.Marque);
				// String DureeVie = getIntent().getStringExtra(
				// ActivityParam.DurreeDeVie);
				// String DateChoisie = getIntent().getStringExtra(
				// ActivityParam.DateAchat);
				// String numTeinte = getIntent().getStringExtra(
				// ActivityParam.NumeroDeTeinte);
				// String nomProduitRecup = getIntent().getStringExtra(
				// ActivityParam.NomProduit);

				intent = new Intent(this, formulaire_entree_page1bis.class);
				transfereMlProduitToActivity(intent);
				// intent.putExtra(ActivityParam.Marque, MarqueChoisie.trim());
				// intent.putExtra(ActivityParam.DurreeDeVie, DureeVie.trim());
				// intent.putExtra(ActivityParam.DateAchat, DateChoisie.trim());
				// intent.putExtra(ActivityParam.NumeroDeTeinte,
				// numTeinte.trim());
				// intent.putExtra(ActivityParam.NomProduit,
				// nomProduitRecup.trim());

			}
			if (isLaunchBypagerecap) {
				// String MarqueChoisie = getIntent().getStringExtra(
				// ActivityParam.Marque);
				// String DureeVie = getIntent().getStringExtra(
				// ActivityParam.DurreeDeVie);
				// String DateChoisie = getIntent().getStringExtra(
				// ActivityParam.DateAchat);
				// String numTeinte = getIntent().getStringExtra(
				// ActivityParam.NumeroDeTeinte);
				// String nomProduitRecup = getIntent().getStringExtra(
				// ActivityParam.NomProduit);

				intent = new Intent(this, formulaire_entree_page3.class);
				transfereMlProduitToActivity(intent);
				// intent.putExtra(ActivityParam.Marque, MarqueChoisie.trim());
				// intent.putExtra(ActivityParam.DurreeDeVie, DureeVie.trim());
				// intent.putExtra(ActivityParam.DateAchat, DateChoisie.trim());
				// intent.putExtra(ActivityParam.NumeroDeTeinte,
				// numTeinte.trim());
				// intent.putExtra(ActivityParam.NomProduit,
				// nomProduitRecup.trim());
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
			new majWidget(this, false);
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

	public MlNote recupereMlNoteFromPreviousActivity() {
		byte[] extra = getIntent().getByteArrayExtra(
				MlNote.class.getCanonicalName());
		if (extra != null) {
			Object o = SerialisableHelper.deserializeObject(extra);
			if (o instanceof MlNote) {
				note = (MlNote) o;
			}
		}

		return note;
	}

	public MlProduit recupereMlProduitfromPreviousActivity() {

		byte[] extra = getIntent().getByteArrayExtra(
				MlProduit.class.getCanonicalName());
		if (extra != null) {
			Object o = SerialisableHelper.deserializeObject(extra);
			if (o instanceof MlProduit) {
				produit = (MlProduit) o;
			}
		}

		return produit;
	}

	public void transfereMlProduitToActivity(Intent p_intent) {
		if (produit == null) {
			produit = new MlProduit();
		}

		p_intent.putExtra(MlProduit.class.getCanonicalName(),
				SerialisableHelper.serialize(produit));

	}

	public void transfereMlNoteToActivity(Intent p_intent) {
		if (note == null) {
			note = new MlNote();
		}
		p_intent.putExtra(MlNote.class.getCanonicalName(),
				SerialisableHelper.serialize(note));
	}

}
