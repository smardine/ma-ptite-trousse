package widget;

import helper.DateHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import fr.smardine.matroussedemaquillage.R;
import fr.smardine.matroussedemaquillage.base.BDAcces;
import fr.smardine.matroussedemaquillage.base.accesTable.AccesTableProduitEnregistre;
import fr.smardine.matroussedemaquillage.recherche.recherche_produit_perime;
import fr.smardine.matroussedemaquillage.variableglobale.EnActionParDefaut;
import fr.smardine.matroussedemaquillage.variableglobale.EnTheme;
import fr.smardine.matroussedemaquillage.variableglobale.ValeurParDefaut;

/**
 * @author smardine
 */
public class majWidget {
	BDAcces objBd;

	/**
	 * constructeur, met a jour le widget:
	 * <ul>
	 * <li>affiche le nb de produits perime</li>
	 * <li>met en accord le logo du widget avec le theme choisi dans l'appli.
	 * @param context
	 */
	public majWidget(Context context, boolean majAussiBdd) {
		objBd = new BDAcces(context);

		if (majAussiBdd) {
			majBddProduitPerime(context);
		}

		// conaitre le nb de produit perimé dans la trousse:
		String SQL = "SELECT " + "id_produits "//
				+ "FROM produit_Enregistre "//
				+ "where " //
				+ "(IS_PERIME='true' or IS_PRESQUE_PERIME='true') ";

		objBd.open();
		int nbProds = objBd.revoiNbProdPerimeOuPresquePerime(SQL);
		objBd.close();

		ValeurParDefaut val = new ValeurParDefaut(context);
		String action = val.getActionParDefaut();

		Intent intent = new Intent(context, recherche_produit_perime.class);
		if (action.equals(EnActionParDefaut.PERIME.getLib()) && nbProds == 0) {
			intent = new Intent(context, EnActionParDefaut.PAGE_PRINC.getClasses());
		} else {
			for (EnActionParDefaut act : EnActionParDefaut.values()) {
				if (act.getLib().equals(action)) {
					intent = new Intent(context, act.getClasses());
				}
			}
		}

		// Get the layout for the App Widget and attach an on-click listener to the button

		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

		ComponentName provider = new ComponentName(context, CountdownWidget.class);
		int[] widgetIDs = appWidgetManager.getAppWidgetIds(provider);

		for (int widgetId : widgetIDs) {
			RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.countdownwidget);
			VerifLeTheme(remoteView);
			remoteView.setTextColor(R.id.WidgetTextView, val.getCouleurPastille());
			remoteView.setOnClickPendingIntent(R.id.WidgetImageView, pendingIntent);
			if (nbProds > 0) {
				remoteView.setTextViewText(R.id.WidgetTextView, "" + nbProds);

			} else {
				remoteView.setTextViewText(R.id.WidgetTextView, "");
			}

			appWidgetManager.updateAppWidget(widgetId, remoteView);
		}

	}

	private void majBddProduitPerime(Context p_context) {
		System.out.println("maj bdd produit perime dans maj Widget");
		// objBd.open();
		AccesTableProduitEnregistre accesProduit = new AccesTableProduitEnregistre(p_context);
		int nbDenregistrement = accesProduit.getNbEnregistrement();
		// int nbDenregistrement = objBd.renvoi_nbChamp("produit_Enregistre");
		// objBd.close();

		if (nbDenregistrement > 0) {
			String[] colonne = new String[] { "Date_Peremption_milli", "id_produits", "DateAchat", "Duree_Vie" };
			objBd.open();

			ArrayList[] datePerem = objBd.VerifAuDemarrage(colonne, "", "");

			// on commence par recalculer la date de permeption suite au bug de calcul lors de l'entrée du produit:
			for (int j = 0; j < nbDenregistrement; j++) {
				int total = (100 * j) / nbDenregistrement;
				System.out.println("Pourcentage: " + total + " maj bdd produit perime dans maj Widget");
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
			objBd.close();
		}

	}

	public int verifErreurEnregistrementDsBase(String IdProduit) throws Exception {

		String[] Colonnes = { "nom_produit", "nom_souscatergorie", "nom_categorie", "numero_Teinte", "Duree_Vie", "Date_Peremption",
				"DateAchat", "nom_marque" };

		objBd.open();
		ArrayList[] trousse_final = objBd.renvoi_liste_TrousseFinalComplete(Colonnes, IdProduit);

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

		String whereClause = "id_produits=?";
		String[] whereArgs = new String[] { "" + IdProduit + "" };

		int nbdechamp = objBd.majTable(Table, modifiedValues, whereClause, whereArgs);
		objBd.close();

		return nbdechamp;

	}

	private void CalculDatePeremtionEtMajDansBase(String dateAchat1, String dureeVie, String idProduit) throws Exception {
		dureeVie = dureeVie.replace("[", "").replace("]", "");

		try {

			objBd.open();
			String[] champ = { "AfficheAlerte", "DureeViePeremp", "Theme" };
			ArrayList[] Param = objBd.renvoi_param(champ);
			objBd.close();
			int NbDeJourAvantPeremp = Integer.parseInt(Param[1].get(0).toString().replace("[", "").replace("]", ""));

			boolean isPerime = DateHelper.isProduitPerime(dateAchat1, dureeVie);
			String perime;
			if (isPerime) {
				perime = "true";
			} else {
				perime = "false";
			}
			Date datePeremp = DateHelper.getDatePeremption(dateAchat1, dureeVie);
			boolean isPresquePerime = DateHelper.isProduitPresquePerime(dateAchat1, dureeVie, NbDeJourAvantPeremp);
			String presqueperime;
			if (isPresquePerime) {
				presqueperime = "true";
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

			objBd.majTable(Table11, modifiedValues11, whereClause11, whereArgs11);
			objBd.close();

		} catch (Exception e) {
			System.out.println("erreur calcul date peremp " + e.getMessage());
			return;
		}

	}

	@SuppressWarnings("rawtypes")
	private void VerifLeTheme(RemoteViews remoteView) {
		objBd.open();
		String[] champ = { "AfficheAlerte", "DureeViePeremp", "Theme" };
		ArrayList<String>[] Param = objBd.renvoi_param(champ);
		objBd.close();

		String nomThemeChoisi = Param[2].get(0).toString().trim();

		if (EnTheme.Bisounours.getLib().equals(nomThemeChoisi)) {
			remoteView.setImageViewResource(R.id.WidgetImageView, R.drawable.icone_bisounours);
		}
		if (EnTheme.Classique.getLib().equals(nomThemeChoisi)) {
			// on supprime le theme classique car trop buggué visuellement,
			// dans le cas ou un utilisateur aurait gardé ce theme,
			// on force l'application du theme "Fleur" et on relance la verification du theme
			ContentValues values = new ContentValues();
			values.put("Theme", EnTheme.Fleur.getLib());

			objBd.open();
			objBd.majTable("Param", values, "", null);
			objBd.close();
			remoteView.setImageViewResource(R.id.WidgetImageView, R.drawable.icone1);
		}
		if (EnTheme.Fleur.getLib().equals(nomThemeChoisi)) {
			remoteView.setImageViewResource(R.id.WidgetImageView, R.drawable.icone1);
		}

	}

}
