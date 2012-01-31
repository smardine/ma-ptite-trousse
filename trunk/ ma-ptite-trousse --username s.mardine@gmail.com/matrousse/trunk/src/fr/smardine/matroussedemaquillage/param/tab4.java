package fr.smardine.matroussedemaquillage.param;

import java.io.File;
import java.util.Calendar;

import widget.majWidget;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import fr.smardine.matroussedemaquillage.Main;
import fr.smardine.matroussedemaquillage.ManipFichier;
import fr.smardine.matroussedemaquillage.R;
import fr.smardine.matroussedemaquillage.base.BDAcces;
import fr.smardine.matroussedemaquillage.base.accesTable.AccesTableProduitEnregistre;
import fr.smardine.matroussedemaquillage.note.note_page1;
import fr.smardine.matroussedemaquillage.note.note_saisie;
import fr.smardine.matroussedemaquillage.recherche.Recherche;
import fr.smardine.matroussedemaquillage.recherche.affiche_detail;
import fr.smardine.matroussedemaquillage.recherche.recherche_produit_perime;
import fr.smardine.matroussedemaquillage.remplir.choix_produit_a_duppliquer;
import fr.smardine.matroussedemaquillage.remplir.formulaire_entree_page1bis;
import fr.smardine.matroussedemaquillage.remplir.formulaire_entree_page3;
import fr.smardine.matroussedemaquillage.variableglobale.ActivityParam;

/**
 * @author smardine
 */
public class tab4 extends Activity implements OnClickListener {
	BDAcces objBd;
	Button videBase, sauvegardeBase, importeBase;
	AlertDialog.Builder adSauvegarde, adImport, adImportResult;
	private int itemChoisi;
	private final String PATH = "/sdcard/ma_trousse/";
	private File baseDansTel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.parametres_tab4);
		videBase = (Button) findViewById(R.id.BtvideBase);
		sauvegardeBase = (Button) findViewById(R.id.BtSauvegarde);
		importeBase = (Button) findViewById(R.id.BtImporte);
		videBase.setOnClickListener(this);
		sauvegardeBase.setOnClickListener(this);
		importeBase.setOnClickListener(this);
		adSauvegarde = new AlertDialog.Builder(this);
		adSauvegarde.setTitle("Resultat de la copie sur la carte SD");
		adSauvegarde.setPositiveButton("Ok", null);
		adImport = new AlertDialog.Builder(this);
		adImport.setTitle("Quelle base voulez vous recuperer?");
		adImportResult = new AlertDialog.Builder(this);
		adImportResult
				.setTitle("Resultat de la recuperation depuis la carte SD");
		adImportResult.setPositiveButton("Ok", null);

	}

	@Override
	protected void onRestart() {
		super.onRestart();
		// popUp("onRestart()-Page2");
	}

	/**
	 * Exécuté lorsque l'activité devient visible à l'utilisateur. La fonction
	 * onStart() est suivie de la fonction onResume().
	 */
	@Override
	protected void onStart() {
		super.onStart();
		// popUp("onStart()-Page2");
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
		// popUp("onResume()-Page2");

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
		// popUp("onStop-Page2");
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
					ActivityParam.LaunchFromMain, false);

			if (isLaunchByNotePage1) {
				intent = new Intent(this, note_page1.class);

			}
			if (isLaunchByNoteSaisie) {
				String idNote = getIntent()
						.getStringExtra(ActivityParam.IdNote);
				intent = new Intent(this, note_saisie.class);
				intent.putExtra(ActivityParam.IdNote, idNote);
			}
			if (isLaunchByAfficheDetail) {
				String idProduit = getIntent().getStringExtra(
						ActivityParam.IdProduit);
				intent = new Intent(this, affiche_detail.class);
				intent.putExtra(ActivityParam.IdProduit, idProduit);
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
				String MarqueChoisie = getIntent().getStringExtra(
						ActivityParam.Marque);
				String DureeVie = getIntent().getStringExtra(
						ActivityParam.DurreeDeVie);
				String DateChoisie = getIntent().getStringExtra(
						ActivityParam.DateAchat);
				String numTeinte = getIntent().getStringExtra(
						ActivityParam.NumeroDeTeinte);
				String nomProduitRecup = getIntent().getStringExtra(
						ActivityParam.NomProduit);

				intent = new Intent(this, formulaire_entree_page1bis.class);
				intent.putExtra(ActivityParam.Marque, MarqueChoisie.trim());
				intent.putExtra(ActivityParam.DurreeDeVie, DureeVie.trim());
				intent.putExtra(ActivityParam.DateAchat, DateChoisie.trim());
				intent.putExtra(ActivityParam.NumeroDeTeinte, numTeinte.trim());
				intent.putExtra(ActivityParam.NomProduit,
						nomProduitRecup.trim());

			}
			if (isLaunchBypagerecap) {
				String MarqueChoisie = getIntent().getStringExtra(
						ActivityParam.Marque);
				String DureeVie = getIntent().getStringExtra(
						ActivityParam.DurreeDeVie);
				String DateChoisie = getIntent().getStringExtra(
						ActivityParam.DateAchat);
				String numTeinte = getIntent().getStringExtra(
						ActivityParam.NumeroDeTeinte);
				String nomProduitRecup = getIntent().getStringExtra(
						ActivityParam.NomProduit);

				intent = new Intent(this, formulaire_entree_page3.class);
				intent.putExtra(ActivityParam.Marque, MarqueChoisie.trim());
				intent.putExtra(ActivityParam.DurreeDeVie, DureeVie.trim());
				intent.putExtra(ActivityParam.DateAchat, DateChoisie.trim());
				intent.putExtra(ActivityParam.NumeroDeTeinte, numTeinte.trim());
				intent.putExtra(ActivityParam.NomProduit,
						nomProduitRecup.trim());
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

	/**
	 * 
	 */
	public void OnDestroy() {
		super.onDestroy();
		// popUp("OnDestroy-Page2");

	}

	@Override
	public void onClick(View p_v) {
		if (p_v == videBase) {
			// objBd = new BDAcces(this);
			// objBd.open();
			AccesTableProduitEnregistre accesProd = new AccesTableProduitEnregistre(
					p_v.getContext());
			accesProd.deleteTable();
			// int nbLignedeleted = objBd.deleteTable("produit_Enregistre",
			// null, null);
			// System.out.println(nbLignedeleted + " lignes effacée");
			// objBd.close();
		}
		if (p_v == sauvegardeBase) {
			objBd = new BDAcces(this);
			// objBd.close();
			String cheminBase = objBd.getPath();
			File baseDansTel = new File(cheminBase);
			String PATH = "/sdcard/ma_trousse/";
			File path = new File(PATH);
			if (!path.exists()) {
				path.mkdirs();
			}
			int mYear;
			int mMonth;
			int mDay;
			final Calendar c = Calendar.getInstance();
			mYear = c.get(Calendar.YEAR);
			mMonth = c.get(Calendar.MONTH) + 1;
			mDay = c.get(Calendar.DAY_OF_MONTH);

			String sYear = "" + mYear;
			String sMonth;
			if (mMonth < 10) {
				sMonth = "0" + mMonth;
			} else {
				sMonth = "" + mMonth;
			}
			String sDay;
			if (mDay < 10) {
				sDay = "0" + mDay;
			} else {
				sDay = "" + mDay;
			}

			File fichierSurCarteSD = new File(PATH + "trousse_base" + sYear
					+ sMonth + sDay);

			boolean result = ManipFichier
					.copier(baseDansTel, fichierSurCarteSD);
			if (result) {
				adSauvegarde.setMessage("Operation reussie");
			} else {
				adSauvegarde.setMessage("Opération echouée");
			}
			adSauvegarde.show();

		}
		if (p_v == importeBase) {

			objBd = new BDAcces(this);
			// objBd.close();
			String cheminBase = objBd.getPath();
			baseDansTel = new File(cheminBase);

			File path = new File(PATH);
			if (!path.exists()) {
				path.mkdirs();
			}

			final String[] NomFichier = recupereListeFichier(PATH);

			adImport.setSingleChoiceItems(NomFichier, 0,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int item) {
							/* User clicked on a radio button do some stuff */

							itemChoisi = item;

						}
					});
			adImport.setPositiveButton("Celui la",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface p_dialog,
								int p_which) {
							// TODO Auto-generated method stub
							String nomChoisi = NomFichier[itemChoisi]
									.toString();
							File fichierSurCarteSD = new File(PATH
									+ "trousse_base" + nomChoisi);
							if (!fichierSurCarteSD.exists()) {
								adImportResult
										.setMessage("Impossible de recuperer la base, le fichier n'existe pas");
								adImportResult.show();
								return;
							}
							boolean result = ManipFichier.copier(
									fichierSurCarteSD, baseDansTel);
							if (result) {
								adImportResult.setMessage("Operation reussie");
							} else {
								adImportResult.setMessage("Opération echouée");
							}
							adImportResult.show();

						}
					});

			adImport.show();

		}

	}

	private String[] recupereListeFichier(String directoryPath) {
		String[] liste = null;
		File directory = new File(directoryPath);

		if (!directory.exists()) {
			// System.out.println("Le fichier/répertoire '"+directoryPath+"' n'existe pas");
		} else if (directory.isFile()) {

			// nbDossier--;
			// nbFichier++;

		} else {
			if (directory.isDirectory()) {
				File[] subfiles = directory.listFiles();

				if (subfiles != null) {// si subfiles=null, c'est que le dossier
										// a des restriction d'acces
					liste = new String[subfiles.length];
					for (int i = 0; i < subfiles.length; i++) {
						// LanceComptage(subfiles[i].toString());
						liste[i] = subfiles[i].toString().substring(
								subfiles[i].toString().lastIndexOf(
										"trousse_base") + 12);
					}
					return liste;
				}

			}
		}
		return liste;
	}

}
