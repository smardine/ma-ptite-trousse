package fr.smardine.matroussedemaquillage;

import helper.DateHelper;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import widget.CountdownWidget;
import widget.majWidget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.ViewSwitcher.ViewFactory;
import fr.smardine.matroussedemaquillage.base.BDAcces;
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

	BDAcces objBd;
	Context ctx = null;

	boolean auMoinsUnProduitPermié = false, auMoinsUnProduitPresquePermié = false;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ExceptionHandler.register(this, "http://simon.mardine.free.fr/trousse_maquillage/test/server.php","ma_ptite_trousse");
		setContentView(R.layout.entrypoint);
		this.setTitle("Ma p'tite trousse");
		// creation du thread qui va rafraichir les valeur de progression et de vitesse
		mSwitcher = (ImageSwitcher) findViewById(R.id.ImageSwitcher01);
		mSwitcher.setFactory(this);
		ctx = this;
		isLaunchFromMain = getIntent().getBooleanExtra(ActivityParam.LaunchFromMain, false);
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

		// final ProgressBar Progress = (ProgressBar) findViewById(R.id.ProgressBar01);
		//
		// // on affecte des valeurs aux composant
		// Progress.setProgress(total);
		System.out.println("Progression: " + total);

		if (total < 10) {
			mSwitcher.setImageResource(mImageIds[0]);
		}

		if (total >= 10 && total < 20) {
			mSwitcher.setImageResource(mImageIds[1]);
		}
		if (total >= 20 && total < 30) {
			mSwitcher.setImageResource(mImageIds[2]);
		}
		if (total >= 30 && total < 40) {
			mSwitcher.setImageResource(mImageIds[3]);
		}
		if (total >= 40 && total < 50) {
			mSwitcher.setImageResource(mImageIds[4]);
		}
		if (total >= 50 && total < 60) {
			mSwitcher.setImageResource(mImageIds[5]);
		}
		if (total >= 60 && total < 70) {
			mSwitcher.setImageResource(mImageIds[6]);
		}
		if (total >= 80 && total < 90) {
			mSwitcher.setImageResource(mImageIds[7]);
		}
		if (total >= 90 && total < 100) {
			mSwitcher.setImageResource(mImageIds[8]);
		}

	}

	private void updateUIFermeture() {

		// final ProgressBar Progress = (ProgressBar) findViewById(R.id.ProgressBar01);

		// on affecte des valeurs aux composant
		// Progress.setProgress(total);
		System.out.println("Progression: " + total);

		if (total < 10) {
			mSwitcher.setImageResource(mImageIds[8]);
		}

		if (total >= 10 && total < 20) {
			mSwitcher.setImageResource(mImageIds[7]);
		}
		if (total >= 20 && total < 30) {
			mSwitcher.setImageResource(mImageIds[9]);
		}
		if (total >= 30 && total < 40) {
			mSwitcher.setImageResource(mImageIds[5]);
		}
		if (total >= 40 && total < 50) {
			mSwitcher.setImageResource(mImageIds[4]);
		}
		if (total >= 50 && total < 60) {
			mSwitcher.setImageResource(mImageIds[3]);
		}
		if (total >= 60 && total < 70) {
			mSwitcher.setImageResource(mImageIds[2]);
		}
		if (total >= 80 && total < 90) {
			mSwitcher.setImageResource(mImageIds[1]);
		}
		if (total >= 90 && total < 100) {
			mSwitcher.setImageResource(mImageIds[0]);
		}

		if (total >= 100) {
			// on a fini tt les traitement avant fermeture => on quitte completement l'appli
			majwidget();
			finish();
			onStop();
			onDestroy();
			// a faire avant System.exit pour supprimer correctement toute les données presentes en memoire
			System.runFinalizersOnExit(true);
			System.exit(0);
		}

	}

	/**
	 * permet d'afficher une alerte a l'utilisateur si un des produits est perimé ou sur le point de l'etre
	 * @param AuMoinsUnProduitPerimé - boolean
	 * @param AuMoinsUnProduitPresquePermié - boolean
	 */
	@SuppressWarnings("rawtypes")
	public void gotoPrevienUtilisateur(boolean AuMoinsUnProduitPerimé, boolean AuMoinsUnProduitPresquePermié) {

		// objBd.open();
		String[] champ = { "AfficheAlerte", "DureeViePeremp", "Theme" };
		ArrayList[] Param = objBd.renvoi_param(champ);
		// objBd.close();
		if ("true".equals(Param[0].get(0))) {
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
	}

	/**
	 * 
	 */
	private void termineActivity() {
		majwidget();
		finish();
	}

	private void majwidget() {
		Context context = this;
		majWidget.updateWidget(context);
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
	 * permet de determiner une date a partir d'un dateTime (Long) ainsi qu'un nb de jour (int)
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
	 * determine au format Long le nb de milliseconde avant la permenption d'un produit
	 * @param dateAVerif Long
	 * @return la difference entre la date du jour et la valeur passée en param.
	 */
	public long verifDatePeremAtteinte(long dateAVerif) {

		long dateDuJour = System.currentTimeMillis();
		long differenceEntredateAVerifetDateDuJour = dateAVerif - dateDuJour;

		return differenceEntredateAVerifetDateDuJour;
	}

	/**
	 * permet de determiner une date a partir d'un dateTime (Long) ainsi qu'un nb de jour (int)
	 * @param days int
	 * @return la nouvelle date
	 */
	public static Date getDateBeforeDays(int days) {
		long backDateMS = System.currentTimeMillis() - ((long) days) * 24 * 60 * 60 * 1000;
		Date backDate = new Date();
		backDate.setTime(backDateMS);
		return backDate;
	}

	/**
	 * Exécuté que l'activité arrêtée via un "stop" redémarre. La fonction onRestart() est suivie de la fonction onStart().
	 */
	@Override
	protected void onRestart() {
		super.onRestart();
		// popUp("onRestart()");
	}

	/**
	 * Exécuté lorsque l'activité devient visible à l'utilisateur. La fonction onStart() est suivie de la fonction onResume().
	 */

	@Override
	protected void onStart() {
		super.onStart();
		// updateUI();

		// popUp("onStart()");
	}

	/**
	 * Exécutée a chaque passage en premier plan de l'activité. Ou bien, si l'activité passe à nouveau en premier (si une autre activité
	 * était passé en premier plan entre temps). La fonction onResume() est suivie de l'exécution de l'activité.
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

	@SuppressWarnings({ "rawtypes" })
	private void CalculDatePeremtionEtMajDansBase(String dateAchat1, String dureeVie, String idProduit) throws Exception {
		dureeVie = dureeVie.replace("[", "").replace("]", "");

		try {
			// int DureeVie = 0;
			// DureeVie = Integer.valueOf(dureeVie);
			//
			// String tabAchat[] = dateAchat1.split("-");
			// int jourAchat = Integer.parseInt(tabAchat[0]);
			// int mois = Integer.parseInt(tabAchat[1]) - 1;// les mois commence à 0 (janvier) et se termine à 11 (decembre)
			// int annee = Integer.parseInt(tabAchat[2]) - 1900;// les années commence à 0(1900), pour avoir l'année exacte a partir d'une
			// // velur contenu dans un string, il faut retrancher 1900 a la valeur de
			// // l'année.
			// // exemple, l'année 2010 est considérée comme 2010-1900 = 110
			//
			// Date DateAchatAuformatDate = new Date(annee, mois, jourAchat);
			// long DateAchatEnMilli = DateAchatAuformatDate.getTime();// on recupere la date d'achat au format milliseconde
			objBd.open();
			String[] champ = { "AfficheAlerte", "DureeViePeremp", "Theme" };
			ArrayList[] Param = objBd.renvoi_param(champ);
			objBd.close();
			int NbDeJourAvantPeremp = Integer.parseInt(Param[1].get(0).toString().replace("[", "").replace("]", ""));

			boolean isPerime = DateHelper.isProduitPerime(dateAchat1, dureeVie);
			String perime;
			if (isPerime) {
				perime = "true";
				auMoinsUnProduitPermié = true;
			} else {
				perime = "false";
			}
			Date datePeremp = DateHelper.getDatePeremption(dateAchat1, dureeVie);
			boolean isPresquePerime = DateHelper.isProduitPresquePerime(dateAchat1, dureeVie, NbDeJourAvantPeremp);
			String presqueperime;
			if (isPresquePerime) {
				presqueperime = "true";
				auMoinsUnProduitPresquePermié = true;
			} else {
				presqueperime = "false";
			}
			// int nbJours = DureeVie * NbDeJourAvantPeremp;
			// Date DatePeremption1 = getDateAfterDays(DateAchatEnMilli, nbJours);// on calcule la date de permetpion en fonction de la date
			// // d'achat+nb de jour donné par l'utilisateur
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			String Date_Peremption = dateFormat.format(datePeremp);// date de peremtion au format jj/mm/aaaa

			long DatePeremtInMilli = datePeremp.getTime(); // on converti la date de permeption en milliseconde
			String Table11 = "produit_Enregistre";
			ContentValues modifiedValues11 = new ContentValues();
			modifiedValues11.put("Date_Peremption", Date_Peremption);
			modifiedValues11.put("Date_Peremption_milli", DatePeremtInMilli);
			modifiedValues11.put("IS_PERIME", perime);
			modifiedValues11.put("IS_PRESQUE_PERIME", presqueperime);
			String whereClause11 = "id_produits=?";
			String[] whereArgs11 = new String[] { "" + idProduit + "" };
			objBd.open();
			// int nbLigneModifiée1 = objBd.majTable(Table11, modifiedValues11, whereClause11, whereArgs11);
			objBd.majTable(Table11, modifiedValues11, whereClause11, whereArgs11);
			// objBd.close();
			// String Message="nb de ligne modifiée:"+nbLigneModifiée1;
		} catch (Exception e) {
			System.out.println("erreur calcul date peremp " + e.getMessage());
			return;
		}

	}

	/**
	 * suite a un bug => correction des champs en base pour enlever les caractere "[" et "]"
	 * @param IdProduit String => necessaire a la mise a jour de la table
	 * @return 1 si la ligne a été modifiée, 0 si rien n'a été fait.
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public int verifErreurEnregistrementDsBase(String IdProduit) throws Exception {

		String[] Colonnes = { "nom_produit", "nom_souscatergorie", "nom_categorie", "numero_Teinte", "Duree_Vie", "Date_Peremption",
				"DateAchat", "nom_marque" };

		objBd.open();
		ArrayList[] trousse_final = objBd.renvoi_liste_TrousseFinalComplete(Colonnes, IdProduit);
		// objBd.close();
		String Table = "produit_Enregistre";
		String Nom_Produit = trousse_final[0].toString().trim();
		String SousCat = trousse_final[1].toString();
		String Cat = trousse_final[2].toString();
		String Numeroteinte = trousse_final[3].toString();
		String DurreeVie = trousse_final[4].toString();
		String DatePeremption = trousse_final[5].toString();
		String DateAchat = trousse_final[6].toString();
		String NomMarque = trousse_final[7].toString();

		ContentValues modifiedValues = new ContentValues();
		modifiedValues.put("nom_produit", Nom_Produit.trim().replace("[", "").replace("]", ""));
		modifiedValues.put("nom_souscatergorie", SousCat.trim().replace("[", "").replace("]", ""));
		modifiedValues.put("nom_categorie", Cat.trim().replace("[", "").replace("]", ""));
		modifiedValues.put("numero_Teinte", Numeroteinte.trim().replace("[", "").replace("]", ""));
		modifiedValues.put("Duree_Vie", DurreeVie.trim().replace("[", "").replace("]", ""));
		modifiedValues.put("Date_Peremption", DatePeremption.trim().replace("[", "").replace("]", ""));
		modifiedValues.put("DateAchat", DateAchat.trim().replace("[", "").replace("]", ""));
		modifiedValues.put("nom_marque", NomMarque.trim().replace("[", "").replace("]", ""));
		// modifiedValues.put("Date_Peremption_milli", DatePeremtInMilli.trim().replace("[", "").replace("]",""));

		String whereClause = "id_produits=?";
		String[] whereArgs = new String[] { "" + IdProduit + "" };

		objBd.open();
		int nbdechamp = objBd.majTable(Table, modifiedValues, whereClause, whereArgs);
		// objBd.close();

		return nbdechamp;

	}

	/**
	 * Suite a un bug, l'utilisateur pouvait rentrer un chiffre aberent, => ca faisait planter l'appli car on attend un "int" dans ce champ.
	 * Maintenant si la valeur est superieure à 99 ou inferieur à 0 => on met 30 jours.
	 */
	public void CorrectionTableparam() {

		String Table = "Param";
		ContentValues modifiedValues = new ContentValues();
		modifiedValues.put("DureeViePeremp", 30);
		String whereClause = "DureeViePeremp>? or DureeViePeremp<?";
		String[] whereArgs = new String[] { "" + 99 + "", "" + 0 + "" };

		objBd.open();
		/* int nbLigneModifiée = */objBd.majTable(Table, modifiedValues, whereClause, whereArgs);
		objBd.close();

	}

	/**
	 * 
	 */
	public void remplissageBase() {

		// try {
		//
		// objBd.open();
		// String[] ScriptSql = G_remplirBase.SCRIPT_REMPLIR_BASE_TEST;
		// for (int i = 0; i < ScriptSql.length; i++) {
		// objBd.execSQL(ScriptSql[i]);
		// }
		// objBd.close();
		//
		// // objBd.close();
		// } catch (Exception e) {
		// System.out.println("message d'erreur " + e);
		// }

	}

	/**
	 * La fonction onStop() est exécutée : - lorsque l'activité n'est plus en premier plan - ou bien lorsque l'activité va être détruite
	 * Cette fonction est suivie : - de la fonction onRestart() si l'activité passe à nouveau en premier plan - de la fonction onDestroy()
	 * lorsque l'activité se termine ou bien lorsque le système décide de l'arrêter
	 */
	@Override
	protected void onStop() {
		super.onStop();
		if (isLaunchFromMain) {
			handler.removeCallbacks(updateTimeTaskFermeture);
		} else {
			handler.removeCallbacks(updateTimeTask);
		}

		objBd.close();

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
			objBd = new BDAcces(ctx);
			objBd.open();
			int nbDenregistrement = objBd.renvoi_nbChamp("produit_Enregistre");
			objBd.close();

			// Correction de parametres si l'utilisateur a mis n'importe quoi comme valeur.
			CorrectionTableparam();

			if (nbDenregistrement > 0) {
				String[] colonne = new String[] { "Date_Peremption_milli", "id_produits", "DateAchat", "Duree_Vie" };
				objBd.open();

				ArrayList[] datePerem = objBd.VerifAuDemarrage(colonne, "", "");
				// objBd.close();
				// / dans le cas ou il y a des caracteres bizarre dans un des champs => correction

				// int nbdeDateEnregistré = datePerem[0].size();
				// long DateAVerif;
				// on commence par recalculer la date de permeption suite au bug de calcul lors de l'entrée du produit:
				for (int j = 0; j < nbDenregistrement; j++) {
					if (j > 0) {
						total = (100 * j) / nbDenregistrement;
						// total = total / 2;

					}
					String s_idProduit = datePerem[1].get(j).toString().replace("[", "").replace("]", "");

					// //////////////////////////////////
					// Correction des champs en base ////
					// //////////////////////////////////
					try {
						/* int nbEnregistrementCorrigé = */verifErreurEnregistrementDsBase(datePerem[1].get(j).toString());
					} catch (Exception e1) {

						System.out.println("erreur dans verifEnregistrement ds base " + e1.getMessage());
					}

					// //////////////////////////////////////////
					// ///////RECALCUL DES DATES DE PEREMTION ///
					// //////////////////////////////////////////
					String DateAchat1 = datePerem[2].get(j).toString();
					String DureeVie = datePerem[3].get(j).toString();

					try {
						CalculDatePeremtionEtMajDansBase(DateAchat1, DureeVie, s_idProduit);
					} catch (Exception e) {

						System.out.println("erreur dans calculDatepermp " + e.getMessage());// e.printStackTrace();
					}

				}

			}

			if (auMoinsUnProduitPermié == true || auMoinsUnProduitPresquePermié == true) {
				gotoPrevienUtilisateur(auMoinsUnProduitPermié, auMoinsUnProduitPresquePermié);
			} else {
				gotoLancePageMain();

			}

			return auMoinsUnProduitPermié;
		}

	}

	private final Integer[] mImageIds = { R.drawable.eclair01, R.drawable.eclair02, R.drawable.eclair03, R.drawable.eclair04,
			R.drawable.eclair05, R.drawable.eclair06, R.drawable.eclair07, R.drawable.eclair08, R.drawable.eclair09 };

	@Override
	public View makeView() {
		ImageView i = new ImageView(this);
		i.setBackgroundColor(0xFF000000);
		i.setScaleType(ImageView.ScaleType.FIT_CENTER);
		i.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
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
		objBd = new BDAcces(p_ctx);
		objBd.close();
		String cheminBase = objBd.getPath();
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

		// si une base appellée "trousse_baseé existe, la supprimer, ca correspond a l'ancien format de sauvegarde

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

		File fichierSurCarteSD = new File(PATH + "trousse_base" + sYear + sMonth + sDay);

		result = ManipFichier.copier(baseDansTel, fichierSurCarteSD);

		total = 85;
		try {
			Thread.sleep(timeToSleep);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}

		// si la sauvegarde s'est bien passée, on verifie que l'on a pas + de 10 sauvegarde, sinon, on suppr la + ancienne.
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
