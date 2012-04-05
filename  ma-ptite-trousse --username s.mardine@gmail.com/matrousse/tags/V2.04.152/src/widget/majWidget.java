package widget;

import helper.DateHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import fr.smardine.matroussedemaquillage.R;
import fr.smardine.matroussedemaquillage.base.BDAcces;
import fr.smardine.matroussedemaquillage.base.accesTable.AccesTableParams;
import fr.smardine.matroussedemaquillage.base.accesTable.AccesTableProduitEnregistre;
import fr.smardine.matroussedemaquillage.mdl.MlListeProduits;
import fr.smardine.matroussedemaquillage.mdl.MlProduit;
import fr.smardine.matroussedemaquillage.recherche.recherche_produit_perime;
import fr.smardine.matroussedemaquillage.variableglobale.EnActionParDefaut;
import fr.smardine.matroussedemaquillage.variableglobale.EnTheme;
import fr.smardine.matroussedemaquillage.variableglobale.ValeurParDefaut;

/**
 * @author smardine
 */
public class majWidget {
	BDAcces objBd;
	private final Context ctx;
	private final String TAG = this.getClass().getCanonicalName();

	/**
	 * constructeur, met a jour le widget:
	 * <ul>
	 * <li>affiche le nb de produits perime</li>
	 * <li>met en accord le logo du widget avec le theme choisi dans l'appli.
	 * @param context
	 * @param majAussiBdd
	 */
	public majWidget(Context context, boolean majAussiBdd) {
		// objBd = new BDAcces(context);
		this.ctx = context;

		if (majAussiBdd) {
			majBddProduitPerime(context);
		}

		// conaitre le nb de produit perimé dans la trousse:
		// String SQL = "SELECT " + "id_produits "//
		// + "FROM produit_Enregistre "//
		// + "where " //
		// + "(IS_PERIME='true' or IS_PRESQUE_PERIME='true') ";
		AccesTableProduitEnregistre accesProduit = new AccesTableProduitEnregistre(
				ctx);
		int nbProds = accesProduit.getNbProduitPerimeOuPresque();
		// //objBd.open();
		// int nbProds = objBd.revoiNbProdPerimeOuPresquePerime(SQL);
		// //objBd.close();

		ValeurParDefaut val = new ValeurParDefaut(context);
		String action = val.getActionParDefaut();

		Intent intent = new Intent(context, recherche_produit_perime.class);
		if (action.equals(EnActionParDefaut.PERIME.getLib()) && nbProds == 0) {
			intent = new Intent(context,
					EnActionParDefaut.PAGE_PRINC.getClasses());
		} else {
			for (EnActionParDefaut act : EnActionParDefaut.values()) {
				if (act.getLib().equals(action)) {
					intent = new Intent(context, act.getClasses());
				}
			}
		}

		// Get the layout for the App Widget and attach an on-click listener to
		// the button

		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
				intent, 0);
		AppWidgetManager appWidgetManager = AppWidgetManager
				.getInstance(context);

		ComponentName provider = new ComponentName(context,
				CountdownWidget.class);
		int[] widgetIDs = appWidgetManager.getAppWidgetIds(provider);

		for (int widgetId : widgetIDs) {
			RemoteViews remoteView = new RemoteViews(context.getPackageName(),
					R.layout.countdownwidget);
			VerifLeTheme(remoteView);
			remoteView.setTextColor(R.id.WidgetTextView,
					val.getCouleurPastille());
			remoteView.setOnClickPendingIntent(R.id.WidgetImageView,
					pendingIntent);
			if (nbProds > 0) {
				remoteView.setTextViewText(R.id.WidgetTextView, "" + nbProds);

			} else {
				remoteView.setTextViewText(R.id.WidgetTextView, "");
			}

			appWidgetManager.updateAppWidget(widgetId, remoteView);
		}

	}

	private void majBddProduitPerime(Context p_context) {
		Log.d(TAG, "maj bdd produit perime dans maj Widget");

		AccesTableProduitEnregistre accesProduits = new AccesTableProduitEnregistre(
				ctx);
		int nbDenregistrement = accesProduits.getNbEnregistrement();

		if (nbDenregistrement > 0) {
			MlListeProduits lstProds = accesProduits.getListeProduits();

			for (MlProduit p : lstProds) {
				verifErreurEnregistrementDsBase(p);
				CalculDatePeremtionEtMajDansBase(p.getDateAchat(),
						p.getDureeVie(), p.getIdProduit());
			}

		}
	}

	/**
	 * @param p_produit
	 */
	public void verifErreurEnregistrementDsBase(MlProduit p_produit) {
		AccesTableProduitEnregistre accesProduit = new AccesTableProduitEnregistre(
				ctx);
		accesProduit.CorrigeProduitsEnregistre(p_produit);

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
			} else {
				presqueperime = "false";
			}
			// on calcule la date de permetpion en fonction de la
			// date d'achat+nb de jour donné par l'utilisateur
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			String Date_Peremption = dateFormat.format(datePeremp);// date de
																	// peremtion
																	// au format
																	// jj/mm/aaaa

			long DatePeremtInMilli = datePeremp.getTime(); // on converti la
															// date de
															// permeption en
															// milliseconde

			AccesTableProduitEnregistre accesProduit = new AccesTableProduitEnregistre(
					ctx);
			accesProduit.majDatepremeption(p_idProduit, Date_Peremption,
					DatePeremtInMilli, perime, presqueperime);

		} catch (Exception e) {
			Log.e(TAG, "erreur calcul date peremp " + e.getMessage());
			return;
		}

	}

	private void VerifLeTheme(RemoteViews remoteView) {

		AccesTableParams accesParam = new AccesTableParams(ctx);
		switch (accesParam.getThemeChoisi()) {
			case Bisounours:
				remoteView.setImageViewResource(R.id.WidgetImageView,
						R.drawable.icone_bisounours);
				break;

			case Classique:
				accesParam.majTheme(EnTheme.Fleur);
			case Fleur:
				remoteView.setImageViewResource(R.id.WidgetImageView,
						R.drawable.icone1);
				break;
		}

		// objBd.open();
		// String[] champ = { "AfficheAlerte", "DureeViePeremp", "Theme" };
		// ArrayList<String>[] Param = objBd.renvoi_param(champ);
		// // objBd.close();
		//
		// String nomThemeChoisi = Param[2].get(0).toString().trim();
		//
		// if (EnTheme.Bisounours.getLib().equals(nomThemeChoisi)) {
		// remoteView.setImageViewResource(R.id.WidgetImageView,
		// R.drawable.icone_bisounours);
		// }
		// if (EnTheme.Classique.getLib().equals(nomThemeChoisi)) {
		// // on supprime le theme classique car trop buggué visuellement,
		// // dans le cas ou un utilisateur aurait gardé ce theme,
		// // on force l'application du theme "Fleur" et on relance la
		// // verification du theme
		// AccesTableParams accesParam = new AccesTableParams(ctx);
		// accesParam.majTheme(EnTheme.Fleur);
		// // ContentValues values = new ContentValues();
		// // values.put("Theme", EnTheme.Fleur.getLib());
		// //
		// // //objBd.open();
		// // objBd.majTable("Param", values, "", null);
		// // //objBd.close();
		// remoteView.setImageViewResource(R.id.WidgetImageView,
		// R.drawable.icone1);
		// }
		// if (EnTheme.Fleur.getLib().equals(nomThemeChoisi)) {
		// remoteView.setImageViewResource(R.id.WidgetImageView,
		// R.drawable.icone1);
		// }

	}

}
