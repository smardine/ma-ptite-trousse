package fr.smardine.matroussedemaquillage;

import helper.DateHelper;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import widget.majWidget;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher.ViewFactory;
import fr.smardine.matroussedemaquillage.base.accesTable.AccesTableParams;
import fr.smardine.matroussedemaquillage.base.accesTable.AccesTableProduitEnregistre;
import fr.smardine.matroussedemaquillage.mdl.MlListeProduits;
import fr.smardine.matroussedemaquillage.mdl.MlProduit;
import fr.smardine.matroussedemaquillage.variableglobale.ActivityParam;

/**
 * point d'entrée de l'application, lance les calculs de date de peremption
 * @author sims
 */
public class EntryPoint extends Activity implements ViewFactory {

	String Date = "";
	ImageSwitcher mSwitcher;
	int total;
	private boolean isLaunchFromMain = false;
	long timeToSleep = 500L;

	// BDAcces objBd;
	Context ctx = null;

	boolean auMoinsUnProduitPerime = false,
			auMoinsUnProduitPresquePerime = false;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ExceptionHandler.register(this,
		// "http://simon.mardine.free.fr/trousse_maquillage/test/server.php","ma_ptite_trousse");
		setContentView(R.layout.entrypoint);
		this.setTitle("Ma p'tite trousse");
		// creation du thread qui va rafraichir les valeur de progression et de
		// vitesse
		mSwitcher = (ImageSwitcher) findViewById(R.id.ImageSwitcher01);
		mSwitcher.setFactory(this);
		ctx = this;
		isLaunchFromMain = getIntent().getBooleanExtra(
				ActivityParam.LaunchFromMain, false);
		if (isLaunchFromMain) {
			updateUIFermeture();
			handler.removeCallbacks(updateTimeTaskFermeture);
			handler.postDelayed(updateTimeTaskFermeture, 50);

		} else {
			updateUI();
			handler.removeCallbacks(updateTimeTask);
			handler.postDelayed(updateTimeTask, 50);

		}

	}

	private final Handler handler = new Handler();
	private final Runnable updateTimeTask = new Runnable() {
		@Override
		public void run() {
			updateUI();
			handler.postDelayed(this, 500);
		}
	};
	private final Runnable updateTimeTaskFermeture = new Runnable() {
		@Override
		public void run() {
			updateUIFermeture();
			handler.postDelayed(this, 50);
		}
	};

	private void updateUI() {

		if (total < 10) {
			mSwitcher.setImageResource(mImageIds[0]);
		} else if (total >= 10 && total < 20) {
			mSwitcher.setImageResource(mImageIds[1]);
		} else if (total >= 20 && total < 30) {
			mSwitcher.setImageResource(mImageIds[2]);
		} else if (total >= 30 && total < 40) {
			mSwitcher.setImageResource(mImageIds[3]);
		} else if (total >= 40 && total < 50) {
			mSwitcher.setImageResource(mImageIds[4]);
		} else if (total >= 50 && total < 60) {
			mSwitcher.setImageResource(mImageIds[5]);
		} else if (total >= 60 && total < 70) {
			mSwitcher.setImageResource(mImageIds[6]);
		} else if (total >= 80 && total < 90) {
			mSwitcher.setImageResource(mImageIds[7]);
		} else

			mSwitcher.setImageResource(mImageIds[8]);

	}

	private void updateUIFermeture() {

		if (total < 10) {
			mSwitcher.setImageResource(mImageIds[8]);
		} else if (total >= 10 && total < 20) {
			mSwitcher.setImageResource(mImageIds[7]);
		} else if (total >= 20 && total < 30) {
			mSwitcher.setImageResource(mImageIds[9]);
		} else if (total >= 30 && total < 40) {
			mSwitcher.setImageResource(mImageIds[5]);
		} else if (total >= 40 && total < 50) {
			mSwitcher.setImageResource(mImageIds[4]);
		} else if (total >= 50 && total < 60) {
			mSwitcher.setImageResource(mImageIds[3]);
		} else if (total >= 60 && total < 70) {
			mSwitcher.setImageResource(mImageIds[2]);
		} else if (total >= 80 && total < 90) {
			mSwitcher.setImageResource(mImageIds[1]);
		} else if (total >= 90 && total < 100) {
			mSwitcher.setImageResource(mImageIds[0]);
		} else {
			// on a fini tt les traitement avant fermeture => on quitte
			// completement l'appli
			majwidget();
			finish();
			onStop();
			onDestroy();
			// a faire avant System.exit pour supprimer correctement toute les
			// données presentes en memoire
			System.runFinalizersOnExit(true);
			System.exit(0);
		}

	}

	/**
	 * permet d'afficher une alerte a l'utilisateur si un des produits est
	 * perimé ou sur le point de l'etre
	 * @param p_auMoinsUnproduitPerime - boolean
	 * @param p_auMoinsUnProduitPresquePerime - boolean
	 */

	public void gotoPrevienUtilisateur(boolean p_auMoinsUnproduitPerime,
			boolean p_auMoinsUnProduitPresquePerime) {
		AccesTableParams accesParam = new AccesTableParams(ctx);

		if (accesParam.getAfficheAlerte()) {
			Intent intentRecherche = new Intent(EntryPoint.this, Main.class);
			intentRecherche.putExtra(ActivityParam.LaunchFromEntryPoint, true);
			intentRecherche.putExtra(ActivityParam.AfficheProduitPerime, true);
			// on demarre la nouvelle activité
			startActivity(intentRecherche);
			termineActivity();
		} else {
			remplissageBase();
			Intent intentMain = new Intent(EntryPoint.this, Main.class);
			// on demarre la nouvelle activité
			intentMain.putExtra(ActivityParam.LaunchFromEntryPoint, true);
			intentMain.putExtra(ActivityParam.AfficheProduitPerime, false);
			startActivity(intentMain);
			termineActivity();
		}
		// //objBd.open();
		// String[] champ = { "AfficheAlerte", "DureeViePeremp", "Theme" };
		// ArrayList[] Param = objBd.renvoi_param(champ);
		// // //objBd.close();
		// if ("true".equals(Param[0].get(0))) {
		// Intent intentRecherche = new Intent(EntryPoint.this, Main.class);
		// intentRecherche.putExtra(ActivityParam.LaunchFromEntryPoint, true);
		// intentRecherche.putExtra(ActivityParam.AfficheProduitPerime, true);
		// // on demarre la nouvelle activité
		// startActivity(intentRecherche);
		// termineActivity();
		//
		// } else {
		// remplissageBase();
		// Intent intentMain = new Intent(EntryPoint.this, Main.class);
		// // on demarre la nouvelle activité
		// intentMain.putExtra(ActivityParam.LaunchFromEntryPoint, true);
		// intentMain.putExtra(ActivityParam.AfficheProduitPerime, false);
		// startActivity(intentMain);
		// termineActivity();
		// }
	}

	/**
	 * 
	 */
	private void termineActivity() {
		//
		finish();
	}

	private void majwidget() {
		Context context = this;
		new majWidget(context, false);

	}

	/**
	 * affiche la page principale de l'appli,sans l'alerte "produit perimé"
	 */
	public void gotoLancePageMain() {

		remplissageBase();
		Intent Main = new Intent(EntryPoint.this, Main.class);
		Main.putExtra(ActivityParam.LaunchFromEntryPoint, true);
		Main.putExtra(ActivityParam.AfficheProduitPerime, false);
		startActivity(Main);
		termineActivity();

	}

	/**
	 * permet de determiner une date a partir d'un dateTime (Long) ainsi qu'un
	 * nb de jour (int)
	 * @param dateEnMilli Long
	 * @param days int
	 * @return la nouvelle date
	 */
	public static Date getDateAfterDays(long dateEnMilli, int days) {
		long backDateMS = dateEnMilli + ((long) days) * 24 * 60 * 60 * 1000;
		Date backDate = new Date();
		backDate.setTime(backDateMS);
		return backDate;
	}

	/**
	 * determine au format Long le nb de milliseconde avant la permenption d'un
	 * produit
	 * @param dateAVerif Long
	 * @return la difference entre la date du jour et la valeur passée en param.
	 */
	public long verifDatePeremAtteinte(long dateAVerif) {

		long dateDuJour = System.currentTimeMillis();
		long differenceEntredateAVerifetDateDuJour = dateAVerif - dateDuJour;

		return differenceEntredateAVerifetDateDuJour;
	}

	/**
	 * permet de determiner une date a partir d'un dateTime (Long) ainsi qu'un
	 * nb de jour (int)
	 * @param days int
	 * @return la nouvelle date
	 */
	public static Date getDateBeforeDays(int days) {
		long backDateMS = System.currentTimeMillis() - ((long) days) * 24 * 60
				* 60 * 1000;
		Date backDate = new Date();
		backDate.setTime(backDateMS);
		return backDate;
	}

	/**
	 * Exécuté que l'activité arrêtée via un "stop" redémarre. La fonction
	 * onRestart() est suivie de la fonction onStart().
	 */
	@Override
	protected void onRestart() {
		super.onRestart();
		// popUp("onRestart()");
	}

	/**
	 * Exécuté lorsque l'activité devient visible à l'utilisateur. La fonction
	 * onStart() est suivie de la fonction onResume().
	 */

	@Override
	protected void onStart() {
		super.onStart();
		// updateUI();

		// popUp("onStart()");
	}

	/**
	 * Exécutée a chaque passage en premier plan de l'activité. Ou bien, si
	 * l'activité passe à nouveau en premier (si une autre activité était passé
	 * en premier plan entre temps). La fonction onResume() est suivie de
	 * l'exécution de l'activité.
	 */

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	protected void onResume() {
		super.onResume();
		if (isLaunchFromMain) {
			handler.removeCallbacks(updateTimeTaskFermeture);
			handler.postDelayed(updateTimeTaskFermeture, 1000);
			AsyncTask<?, ?, ?> execute = new FermetureTask().execute("");

		} else {
			handler.removeCallbacks(updateTimeTask);
			handler.postDelayed(updateTimeTask, 1000);
			AsyncTask<?, ?, ?> execute = new CheckTask().execute("");
		}

	}

	private void CalculDatePeremtionEtMajDansBase(Date dateAchat1,
			int p_dureeVie, int p_idProduit) {

		try {
			AccesTableParams accesParams = new AccesTableParams(ctx);
			int NbDeJourAvantPeremp = accesParams.getDureeViePeremption();

			boolean isPerime = DateHelper.isProduitPerime(dateAchat1,
					p_dureeVie);
			String perime;
			if (isPerime) {
				perime = "true";
				auMoinsUnProduitPerime = true;
			} else {
				perime = "false";
			}
			Date datePeremp = DateHelper.getDatePeremption(dateAchat1,
					p_dureeVie);
			boolean isPresquePerime = DateHelper.isProduitPresquePerime(
					dateAchat1, p_dureeVie, NbDeJourAvantPeremp);
			String presqueperime;
			if (isPresquePerime) {
				presqueperime = "true";
				auMoinsUnProduitPresquePerime = true;
			} else {
				presqueperime = "false";
			}
			// on calcule la date de permetpion en fonction de la
			// date d'achat+nb de jour donné par l'utilisateur
			String Date_Peremption = DateHelper.getDateforDatabase(datePeremp);
			// DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			// String Date_Peremption = dateFormat.format(datePeremp);// date de
			// // peremtion
			// // au format
			// // jj/mm/aaaa

			long DatePeremtInMilli = datePeremp.getTime(); // on converti la
															// date de
															// permeption en
															// milliseconde

			AccesTableProduitEnregistre accesProduit = new AccesTableProduitEnregistre(
					ctx);
			accesProduit.majDatepremeption(p_idProduit, Date_Peremption,
					DatePeremtInMilli, perime, presqueperime);

		} catch (Exception e) {
			System.out.println("erreur calcul date peremp " + e.getMessage());
			return;
		}

	}

	/**
	 * suite a un bug => correction des champs en base pour enlever les
	 * caractere "[" et "]"
	 * @param p_produit int => necessaire a la mise a jour de la table
	 */

	public void verifErreurEnregistrementDsBase(MlProduit p_produit) {

		AccesTableProduitEnregistre accesProduit = new AccesTableProduitEnregistre(
				ctx);
		accesProduit.CorrigeProduitsEnregistre(p_produit);

	}

	/**
	 * Suite a un bug, l'utilisateur pouvait rentrer un chiffre aberent, => ca
	 * faisait planter l'appli car on attend un "int" dans ce champ. Maintenant
	 * si la valeur est superieure à 99 ou inferieur à 0 => on met 30 jours.
	 */
	public void CorrectionTableparam() {
		AccesTableParams accesParam = new AccesTableParams(ctx);
		accesParam.CorrigeTableParam();
	}

	/**
	 * 
	 */
	public void remplissageBase() {

		// try {
		//
		// //objBd.open();
		// String[] ScriptSql = G_remplirBase.SCRIPT_REMPLIR_BASE_TEST;
		// for (int i = 0; i < ScriptSql.length; i++) {
		// objBd.execSQL(ScriptSql[i]);
		// }
		// //objBd.close();
		//
		// // //objBd.close();
		// } catch (Exception e) {
		// System.out.println("message d'erreur " + e);
		// }

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
		if (isLaunchFromMain) {
			handler.removeCallbacks(updateTimeTaskFermeture);
		} else {
			handler.removeCallbacks(updateTimeTask);
		}

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

	/**
	 * destruction de l'instance de l'activity
	 */
	public void OnDestroy() {
		super.onDestroy();

	}

	/**
	 * permetd'afficher un message a l'utilisateur sous forme de popup
	 * @param message String le message a afficher
	 */
	public void popUp(String message) {
		// Toast.makeText(this, message, 1).show();
	}

	@SuppressWarnings("rawtypes")
	private class FermetureTask extends AsyncTask {
		@Override
		protected Object doInBackground(Object... p_arg0) {
			// on sauvegarde la base sur la carte SD
			total = 25;
			boolean resultatSauvegarde = false;

			resultatSauvegarde = lanceSauvegarde(ctx);

			total = 100;
			// fin de la sauvegarde sur la carte SD.

			return resultatSauvegarde;
		}
	}

	@SuppressWarnings("rawtypes")
	private class CheckTask extends AsyncTask {

		@Override
		protected Object doInBackground(Object... p_arg0) {
			// objBd = new BDAcces(ctx);
			// //objBd.open();
			AccesTableProduitEnregistre accesProduits = new AccesTableProduitEnregistre(
					ctx);
			int nbDenregistrement = accesProduits.getNbEnregistrement();

			// Correction de parametres si l'utilisateur a mis n'importe quoi
			// comme valeur.
			CorrectionTableparam();

			if (nbDenregistrement > 0) {
				MlListeProduits lstProds = accesProduits.getListeProduits();
				int count = 0;

				for (MlProduit p : lstProds) {
					count++;
					total = (100 * count) / nbDenregistrement;
					verifErreurEnregistrementDsBase(p);
					CalculDatePeremtionEtMajDansBase(p.getDateAchat(),
							p.getDureeVie(), p.getIdProduit());
				}

			}

			if (auMoinsUnProduitPerime == true
					|| auMoinsUnProduitPresquePerime == true) {
				gotoPrevienUtilisateur(auMoinsUnProduitPerime,
						auMoinsUnProduitPresquePerime);
			} else {
				gotoLancePageMain();

			}

			return auMoinsUnProduitPerime;
		}

	}

	private final Integer[] mImageIds = { R.drawable.eclair01,
			R.drawable.eclair02, R.drawable.eclair03, R.drawable.eclair04,
			R.drawable.eclair05, R.drawable.eclair06, R.drawable.eclair07,
			R.drawable.eclair08, R.drawable.eclair09 };

	@Override
	public View makeView() {
		ImageView i = new ImageView(this);
		i.setBackgroundColor(0xFF000000);
		i.setScaleType(ImageView.ScaleType.FIT_CENTER);
		i.setLayoutParams(new ImageSwitcher.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		return i;
	}

	protected boolean lanceSauvegarde(Context p_ctx) {
		boolean result = false;
		total = 35;
		try {
			Thread.sleep(timeToSleep);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
		AccesTableParams accesParam = new AccesTableParams(this);
		String cheminBase = accesParam.getDatabasePath();
		File baseDansTel = new File(cheminBase);
		String PATH = "/sdcard/ma_trousse/";
		File path = new File(PATH);
		if (!path.exists()) {
			path.mkdirs();
		}
		total = 45;
		try {
			Thread.sleep(timeToSleep);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}

		// si une base appellée "trousse_baseé existe, la supprimer, ca
		// correspond a l'ancien format de sauvegarde

		File f = new File(PATH + "trousse_base");
		if (f.exists()) {
			boolean delete = f.delete();
			if (!delete) {
				f.deleteOnExit();
			}
		}
		total = 55;
		try {
			Thread.sleep(timeToSleep);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
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
		total = 65;
		try {
			Thread.sleep(timeToSleep);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}

		File fichierSurCarteSD = new File(PATH + "trousse_base" + sYear
				+ sMonth + sDay);

		result = ManipFichier.copier(baseDansTel, fichierSurCarteSD);

		total = 85;
		try {
			Thread.sleep(timeToSleep);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}

		// si la sauvegarde s'est bien passée, on verifie que l'on a pas + de 10
		// sauvegarde, sinon, on suppr la + ancienne.
		if (result) {
			Comptage compte = new Comptage(PATH);
			int nbFichier = compte.getNbFichier();
			if (nbFichier > 5) {
				if (compte.supprFichierPlusAncien(PATH)) {
					return true;
				}
			}
			return result;

		}
		return result;
	}
}
