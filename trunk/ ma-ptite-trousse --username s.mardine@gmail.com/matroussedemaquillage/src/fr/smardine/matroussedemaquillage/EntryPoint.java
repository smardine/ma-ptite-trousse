package fr.smardine.matroussedemaquillage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import fr.smardine.matroussedemaquillage.base.BDAcces;
import fr.smardine.matroussedemaquillage.variableglobale.ActivityParam;

public class EntryPoint extends Activity {

	String Date = "";

	int total;

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
		updateUI();
		handler.removeCallbacks(updateTimeTask);
		handler.postDelayed(updateTimeTask, 50);
		ctx = this;

	}

	private final Handler handler = new Handler();
	private final Runnable updateTimeTask = new Runnable() {
		@Override
		public void run() {
			updateUI();
			handler.postDelayed(this, 50);
		}
	};

	private void updateUI() {

		final ProgressBar Progress = (ProgressBar) findViewById(R.id.ProgressBar01);
		// on affecte des valeurs aux composant
		Progress.setProgress(total);
		System.out.println("Progression: " + total);

	}

	@SuppressWarnings("rawtypes")
	public void gotoPrevienUtilisateur(boolean AuMoinsUnProduitPerimé, boolean AuMoinsUnProduitPresquePermié) {
		// TODO Auto-generated method stub

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
			finish();

		} else {
			remplissageBase();
			Intent intentMain = new Intent(EntryPoint.this, Main.class);
			// on demarre la nouvelle activité
			intentMain.putExtra(ActivityParam.LaunchFromEntryPoint, true);
			intentMain.putExtra(ActivityParam.AfficheProduitPerime, false);
			startActivity(intentMain);
			finish();
		}
	}

	public void gotoLancePageMain() {

		remplissageBase();
		Intent Main = new Intent(EntryPoint.this, Main.class);
		Main.putExtra(ActivityParam.LaunchFromEntryPoint, true);
		Main.putExtra(ActivityParam.AfficheProduitPerime, false);
		startActivity(Main);
		finish();

	}

	public static Date getDateAfterDays(long dateEnMilli, int days) {
		long backDateMS = dateEnMilli + ((long) days) * 24 * 60 * 60 * 1000;
		Date backDate = new Date();
		backDate.setTime(backDateMS);
		return backDate;
	}

	public long verifDatePeremAtteinte(long dateAVerif) {
		// TODO Auto-generated method stub
		long dateDuJour = System.currentTimeMillis();
		long differenceEntredateAVerifetDateDuJour = dateAVerif - dateDuJour;

		return differenceEntredateAVerifetDateDuJour;
	}

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

	@SuppressWarnings("unchecked")
	@Override
	protected void onResume() {
		super.onResume();
		handler.removeCallbacks(updateTimeTask);
		handler.postDelayed(updateTimeTask, 1000);

		@SuppressWarnings("unused")
		AsyncTask<?, ?, ?> execute = new CheckTask().execute("");

	}

	@SuppressWarnings("rawtypes")
	private void CalculDatePeremtionEtMajDansBase(String dateAchat1, String dureeVie, String idProduit) throws Exception {
		dureeVie = dureeVie.replace("[", "").replace("]", "");

		try {
			int DureeVie = 0;
			DureeVie = Integer.valueOf(dureeVie);

			String tabAchat[] = dateAchat1.split("-");
			int jourAchat = Integer.parseInt(tabAchat[0]);
			int mois = Integer.parseInt(tabAchat[1]) - 1;// les mois commence à 0 (janvier) et se termine à 11 (decembre)
			int annee = Integer.parseInt(tabAchat[2]) - 1900;// les années commence à 0(1900), pour avoir l'année exacte a partir d'une
																// velur contenu dans un string, il faut retrancher 1900 a la valeur de
																// l'année.
			// exemple, l'année 2010 est considérée comme 2010-1900 = 110

			Date DateAchatAuformatDate = new Date(annee, mois, jourAchat);
			long DateAchatEnMilli = DateAchatAuformatDate.getTime();// on recupere la date d'achat au format milliseconde
			objBd.open();
			String[] champ = { "AfficheAlerte", "DureeViePeremp", "Theme" };
			ArrayList[] Param = objBd.renvoi_param(champ);
			objBd.close();

			int NbDeJourAvantPeremp = Integer.parseInt(Param[1].get(0).toString().replace("[", "").replace("]", ""));
			int nbJours = DureeVie * NbDeJourAvantPeremp;
			Date DatePeremption1 = getDateAfterDays(DateAchatEnMilli, nbJours);// on calcule la date de permetpion en fonction de la date
																				// d'achat+nb de jour donné par l'utilisateur
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			String Date_Peremption = dateFormat.format(DatePeremption1);// date de peremtion au format jj/mm/aaaa

			long DatePeremtInMilli = DatePeremption1.getTime(); // on converti la date de permeption en milliseconde
			String Table11 = "produit_Enregistre";
			ContentValues modifiedValues11 = new ContentValues();
			modifiedValues11.put("Date_Peremption", Date_Peremption);
			modifiedValues11.put("Date_Peremption_milli", DatePeremtInMilli);
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

	@SuppressWarnings("rawtypes")
	/**
	 * suite a un bug => correction des champs en base pour enlever les caractere "[" et "]"
	 * @param String IdProduit => necessaire a la mise a jour de la table
	 * @return 1 si la ligne a été modifiée, 0 si rien n'a été fait.
	 */
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
		// TODO Auto-generated method stub

	}

	/**
	 * Suite a un bug, l'utilisateur pouvait rentrer un chiffre aberent, => ca faisait planter l'appli car on attend un "int" dans ce champ.
	 * Maintenant si la valeur est superieure à 99 ou inferieur à 0 => on met 30 jours.
	 */
	public void CorrectionTableparam() {
		// TODO Auto-generated method stub

		String Table = "Param";
		ContentValues modifiedValues = new ContentValues();
		modifiedValues.put("DureeViePeremp", 30);
		String whereClause = "DureeViePeremp>? or DureeViePeremp<?";
		String[] whereArgs = new String[] { "" + 99 + "", "" + 0 + "" };

		objBd.open();
		/* int nbLigneModifiée = */objBd.majTable(Table, modifiedValues, whereClause, whereArgs);
		objBd.close();

	}

	public void remplissageBase() {
		// // TODO Auto-generated method stub
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
		handler.removeCallbacks(updateTimeTask);
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

	public void OnDestroy() {
		super.onDestroy();

	}

	public void popUp(String message) {
		// Toast.makeText(this, message, 1).show();
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

				int nbdeDateEnregistré = datePerem[0].size();
				long DateAVerif;
				// on commence par recalculer la date de permeption suite au bug de calcul lors de l'entrée du produit:
				for (int j = 0; j < nbDenregistrement; j++) {
					if (j > 0) {
						total = (100 * j) / nbDenregistrement;
						total = total / 2;

					}
					String s_idProduit = datePerem[1].get(j).toString().replace("[", "").replace("]", "");

					// //////////////////////////////////
					// Correction des champs en base ////
					// //////////////////////////////////
					try {
						/* int nbEnregistrementCorrigé = */verifErreurEnregistrementDsBase(datePerem[1].get(j).toString());
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						// e1.printStackTrace();
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
						// TODO Auto-generated catch block
						System.out.println("erreur dans calculDatepermp " + e.getMessage());// e.printStackTrace();
					}

				}

				for (int i = 0; i < nbdeDateEnregistré; i++) {
					if (i > 0) {
						total = (100 * i) / nbDenregistrement;
						total = (total / 2) + 49;

					}

					if (datePerem[0].get(i) != null) {
						DateAVerif = Long.parseLong((String) datePerem[0].get(i));
						int idProduit = Integer.parseInt((String) datePerem[1].get(i));
						long intervalleEnMilliseconde = verifDatePeremAtteinte(DateAVerif);
						int nbJour = (int) (intervalleEnMilliseconde / (24 * 60 * 60 * 1000));
						if (nbJour <= 0) {// on a deja depassé la date limite
							/*
							 * String Message = "DateDepassée sur l'id produit n° "+datePerem[1].get(i); popUp(Message);
							 */
							String Table1 = "produit_Enregistre";
							ContentValues modifiedValues1 = new ContentValues();
							modifiedValues1.put("IS_PERIME", "true");
							modifiedValues1.put("IS_PRESQUE_PERIME", "false");
							modifiedValues1.put("NB_JOUR_AVANT_PEREMP", "" + nbJour + "");
							String whereClause1 = "id_produits=?";
							String[] whereArgs1 = new String[] { "" + idProduit + "" };
							objBd.open();
							/* int nbLigneModifiée1 = */objBd.majTable(Table1, modifiedValues1, whereClause1, whereArgs1);
							// objBd.close();
							// String Message211="nb de ligne modifiée:"+nbLigneModifiée1;
							auMoinsUnProduitPermié = true;
						}

						// on convertir la valeur en milliseconde en nb de jour
						if (nbJour <= 30 && nbJour > 0) {// on previens l'utilisateur
							/*
							 * String Message = "On va atteindre une date de permeption sur l'id produit n° "+datePerem[1].get(i);
							 * popUp(Message);
							 */
							String Table1 = "produit_Enregistre";
							ContentValues modifiedValues1 = new ContentValues();
							modifiedValues1.put("IS_PERIME", "false");
							modifiedValues1.put("IS_PRESQUE_PERIME", "true");
							modifiedValues1.put("NB_JOUR_AVANT_PEREMP", "" + nbJour + "");
							String whereClause1 = "id_produits=?";
							String[] whereArgs1 = new String[] { "" + idProduit + "" };
							objBd.open();
							/* int nbLigneModifiée1 = */objBd.majTable(Table1, modifiedValues1, whereClause1, whereArgs1);
							objBd.close();
							// String Message211="nb de ligne modifiée:"+nbLigneModifiée1;
							auMoinsUnProduitPresquePermié = true;
						}
						if (nbJour > 30) {
							/*
							 * String Message = "le produit suivant n'est pas périmé et ne va pas etre perimé: n° "+datePerem[1].get(i);
							 * popUp(Message);
							 */
							String Table1 = "produit_Enregistre";
							ContentValues modifiedValues1 = new ContentValues();
							modifiedValues1.put("IS_PERIME", "false");
							modifiedValues1.put("IS_PRESQUE_PERIME", "false");
							modifiedValues1.put("NB_JOUR_AVANT_PEREMP", "" + nbJour + "");
							String whereClause1 = "id_produits=?";
							String[] whereArgs1 = new String[] { "" + idProduit + "" };
							objBd.open();
							/* int nbLigneModifiée1 = */objBd.majTable(Table1, modifiedValues1, whereClause1, whereArgs1);
							objBd.close();
							// String Message211="nb de ligne modifiée:"+nbLigneModifiée1;

						}
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

}
