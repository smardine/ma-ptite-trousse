package fr.smardine.matroussedemaquillage.remplir;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import fr.smardine.matroussedemaquillage.Main;
import fr.smardine.matroussedemaquillage.R;
import fr.smardine.matroussedemaquillage.base.BDAcces;
import fr.smardine.matroussedemaquillage.base.accesTable.AccesTableParams;
import fr.smardine.matroussedemaquillage.base.accesTable.AccesTableTrousseProduits;
import fr.smardine.matroussedemaquillage.note.note_page1;
import fr.smardine.matroussedemaquillage.param.tab_param;
import fr.smardine.matroussedemaquillage.recherche.Recherche;
import fr.smardine.matroussedemaquillage.variableglobale.ActivityParam;
import fr.smardine.matroussedemaquillage.variableglobale.EnTheme;

public class formulaire_entree_page3 extends Activity implements
		OnClickListener {
	Button BoutonValider3;

	private BDAcces objBd;
	@SuppressWarnings("rawtypes")
	ArrayList[] TrousseTempo;
	// ////////////////////
	private TextView mDateDisplay;
	private Button mPickDate;
	private ImageView BoutonAide;
	private EditText nomProduit, numeroTeinte, dureeVie;

	private int mYear;
	private int mMonth;
	private int mDay;

	private boolean NouveauProduit = false, Duppliquer = false,
			acceuil = false;

	static final int DATE_DIALOG_ID = 0;
	AlertDialog.Builder adManqueInfo, adAide, adRecap, adFiniOuPas;
	String MarqueChoisie = "";
	String nomDuProduit = "";
	String numeroDeTeinte = "";
	String DateAchat = "";
	String DureeVie = "";
	String Cat = "";

	// /////////////////////////

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ExceptionHandler.register(this,
		// "http://simon.mardine.free.fr/trousse_maquillage/test/server.php","ma_ptite_trousse");
		ChoisiLeTheme();

		// capture our View elements
		mDateDisplay = (TextView) findViewById(R.id.dateDisplay_page3);
		mPickDate = (Button) findViewById(R.id.pickDate_page3);
		nomProduit = (EditText) findViewById(R.id.EditTextNom_page3);
		numeroTeinte = (EditText) findViewById(R.id.EditTextTeinte_page3);
		dureeVie = (EditText) findViewById(R.id.EditTextDureeDeVie_page3);
		BoutonValider3 = (Button) this.findViewById(R.id.ButtonValider3_page3);
		BoutonAide = (ImageView) this.findViewById(R.id.IvAide_page3);
		BoutonAide.setOnClickListener(this);

		/*
		 * nomProduit.addTextChangedListener(new TextWatcher(){
		 * @SuppressWarnings("unused") int len=0;
		 * @Override public void afterTextChanged(Editable s) { String str =
		 * nomProduit.getText().toString(); if(!str.equals("")){ String str1 =
		 * UpperCaseWords(str); nomProduit.setText(""+str1+""); } }
		 * @Override public void beforeTextChanged(CharSequence arg0, int arg1,
		 * int arg2, int arg3) { String str = nomProduit.getText().toString();
		 * len = str.length(); }
		 * @Override public void onTextChanged(CharSequence s, int start, int
		 * before, int count) { } });
		 */
		adFiniOuPas = new AlertDialog.Builder(this);
		adFiniOuPas.setTitle("Que voulez vous faire?");
		adFiniOuPas.setIcon(R.drawable.ad_attention);
		CharSequence[] items = { "Ajouter un produit", "Dupliquer un produit",
				"Revenir à la page d'accueil" };
		adFiniOuPas.setSingleChoiceItems(items, -1,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int item) {
						/* User clicked on a radio button do some stuff */

						switch (item) {
							case 0:
								NouveauProduit = true;
								break;
							case 1:
								Duppliquer = true;
								break;
							case 2:
								acceuil = true;
								break;
						}

					}
				});

		adFiniOuPas.setPositiveButton("Ok",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int id) {
						AccesTableTrousseProduits accesTrousse = new AccesTableTrousseProduits(
								getApplicationContext());
						accesTrousse.reinitProduitChoisi();
						// String Table = "trousse_produits";
						// ContentValues modifiedValues = new ContentValues();
						// modifiedValues.put("ischecked", "false");
						// String whereClause = "ischecked=?";
						// String[] whereArgs = new String[] { "true" };

						// objBd.open();
						// int nbdechamp = objBd.majTable(Table, modifiedValues,
						// whereClause, whereArgs);
						objBd.deleteTable("trousse_tempo", "1", null);
						// System.out.println("Nombre de champ modifié : " +
						// nbdechamp);
						// objBd.close();

						if (NouveauProduit) {// on retourne a la 1° fenetre du
												// formulaire
							Intent intent = new Intent(
									formulaire_entree_page3.this,
									formulaire_entree_page1bis.class);
							// on demarre la nouvelle activité
							intent.putExtra(ActivityParam.LaunchFromPageRecap,
									true);
							startActivity(intent);
							termineActivity();
						}
						if (Duppliquer) {// on revient a la page d'acceuil
							Intent intent = new Intent(
									formulaire_entree_page3.this,
									choix_produit_a_duppliquer.class);
							// on demarre la nouvelle activité
							intent.putExtra(ActivityParam.LaunchFromPageRecap,
									true);
							startActivity(intent);
							termineActivity();
						}
						if (acceuil) {
							Intent intent = new Intent(
									formulaire_entree_page3.this, Main.class);
							// on demarre la nouvelle activité
							intent.putExtra(ActivityParam.LaunchFromPageRecap,
									true);
							startActivity(intent);
							termineActivity();
						}

					}

				});
		dureeVie.addTextChangedListener(new TextWatcher() {
			@SuppressWarnings("unused")
			int len = 0;

			@Override
			public void afterTextChanged(Editable s) {
				String str = dureeVie.getText().toString();
				if (!str.equals("")) {
					int ValeurRentrée = Integer.parseInt(str);
					if (ValeurRentrée > 99) {
						dureeVie.setText("99");
					}
					if (ValeurRentrée <= 0) {
						dureeVie.setText("1");
					}
				} else {
					dureeVie.setText("1");
				}

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

				String str = dureeVie.getText().toString();
				len = str.length();
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

		});

		/*
		 * Animlineaire anim = new Animlineaire (false,true);
		 * mDateDisplay.startAnimation(anim.getAnim());
		 * mPickDate.startAnimation(anim.getAnim());
		 * nomProduit.startAnimation(anim.getAnim());
		 * numeroTeinte.startAnimation(anim.getAnim());
		 * dureeVie.startAnimation(anim.getAnim());
		 * BoutonValider3.startAnimation(anim.getAnim());
		 * BoutonAide.startAnimation(anim.getAnim());
		 */

		adAide = new AlertDialog.Builder(this);
		adAide.setTitle("Aide");
		/*
		 * CharSequence[] items = { "Mascara (3 à 6 mois)",
		 * "Fond  de teint(6 à 12 mois )"
		 * ,"Blush-Poudre (12 à 18 mois)","Fards (12 à 18 mois)"
		 * ,"Crayons (12 à 36 mois)", "Rouge à lèvres (12 à 36 mois)",
		 * "Pinceau (nettoyage tout les mois)"
		 * ,"Vernis à ongle (Voir sur la bouteille)"}; /*
		 * adAide.setSingleChoiceItems(items , -1, new
		 * DialogInterface.OnClickListener() { public void
		 * onClick(DialogInterface dialog, int item){ /* User clicked on a radio
		 * button do some stuff
		 */
		/*
		 * if (item==0){//Mascara NbMoisChoisiBas=3; NbMoisChoisiHaut=6; }if
		 * (item==1){//fond de teint NbMoisChoisiBas=6; NbMoisChoisiHaut=12; }
		 * if (item==2){//blush-poudres NbMoisChoisiBas=12; NbMoisChoisiHaut=18;
		 * } if (item==3){//fards NbMoisChoisiBas=12; NbMoisChoisiHaut=18; } if
		 * (item==4){//crayons NbMoisChoisiBas=12; NbMoisChoisiHaut=36; } if
		 * (item==5){//Rouges à lèvres NbMoisChoisiBas=12; NbMoisChoisiHaut=36;
		 * } if (item==6){//pinceaux NbMoisChoisiBas=1; NbMoisChoisiHaut=1; } if
		 * (item==7){//Vernis NbMoisChoisiBas=6; NbMoisChoisiHaut=12; } } });
		 */

		adAide.setMessage("La durée de vie de vos produits de maquillage est inscrite au dos de l'emballage\n\nDurée de vie moyenne:\n"
				+ "Mascaras : 3 à 6 mois\n"
				+ "Fonds de teint : 6 à 12 mois\n"
				+ "Blush-Poudres :\n"
				+ "12 à 18 mois\n"
				+ "Fards : 12 à 18 mois\n"
				+ "Crayons :\n"
				+ "12 à 36 mois\n"
				+ "Rouges à lèvres : 12 à 36 mois\n"
				+ "Pinceaux : nettoyage tout les mois\n"
				+ "\n"
				+ "Attention, ces informations sont fournies à titre indicatif uniquement.\n"
				+ "Quelques conseils :\n"
				+ "Un produits périmé à une odeur et une texture inhabituelle.N'hesitez pas à jeter tout produit suspect, plus particulierement ceux en contact avec vos yeux.\n"
				+ "Stockez vos produits dans un endroits sec, à l'abri des fluctuations de température.");

		adAide.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int id) {

				// dureeVie.setText(""+NbMoisChoisiBas);

			}
		});

		// add a click listener to the button
		mPickDate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});

		// get the current date
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		objBd = new BDAcces(this);
		BoutonValider3.setOnClickListener(this);

		adManqueInfo = new AlertDialog.Builder(this);
		adManqueInfo.setTitle("Attention");
		adManqueInfo.setIcon(R.drawable.ad_attention);
		adManqueInfo
				.setMessage("Vous avez oublié de rentrer certaines informations.");
		adManqueInfo.setPositiveButton("Ok", null);

		this.setTitle("Choix noms & dates");

		// popUp("OnCreate-page3");

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
	private void ChoisiLeTheme() {
		AccesTableParams accesParam = new AccesTableParams(this);
		switch (accesParam.getThemeChoisi()) {
			case Bisounours:
				setContentView(R.layout.theme_bisounours_formulaire_entree_page3);
				break;
			case Classique:
				accesParam.majTheme(EnTheme.Fleur);
				ChoisiLeTheme();
				break;
			case Fleur:
				setContentView(R.layout.theme_fleur_formulaire_entree_page3);
				break;
		}

		// objBd = new BDAcces(this);
		// //objBd.open();
		// String[] champ = { "AfficheAlerte", "DureeViePeremp", "Theme" };
		// @SuppressWarnings("rawtypes")
		// ArrayList[] Param = objBd.renvoi_param(champ);
		//
		// String nomThemeChoisi = Param[2].get(0).toString().trim();
		// if (EnTheme.Bisounours.getLib().equals(nomThemeChoisi)) {
		// setContentView(R.layout.theme_bisounours_formulaire_entree_page3);
		//
		// }
		// if (EnTheme.Classique.getLib().equals(nomThemeChoisi)) {
		// // setContentView(R.layout.formulaire_entree_page3);
		// AccesTableParams accesParam = new AccesTableParams(this);
		// accesParam.majTheme(EnTheme.Fleur);
		// // ContentValues values = new ContentValues();
		// // values.put("Theme", EnTheme.Fleur.getLib());
		// //
		// // //objBd.open();
		// // objBd.majTable("Param", values, "", null);
		// // //objBd.close();
		// // ChoisiLeTheme();
		//
		// }
		// if (EnTheme.Fleur.getLib().equals(nomThemeChoisi)) {
		// setContentView(R.layout.theme_fleur_formulaire_entree_page3);
		// }

		// objBd.close();
	}

	private void onCreateMenu(Menu menu) {
		SubMenu recherche = menu.addSubMenu(0, 2000, 1, "Recherche");
		recherche.setIcon(R.drawable.menu_recherche);
		SubMenu note = menu.addSubMenu(0, 2002, 2, "Notes");
		note.setIcon(R.drawable.menu_note);
		SubMenu parametre = menu.addSubMenu(0, 2001, 3, "Parametres");
		parametre.setIcon(R.drawable.menu_param); // icone systeme
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		onCreateMenu(menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// évènement appelé lorsqu'un menu est choisi
		switch (item.getItemId()) {
			// l'identifiant integer est moins gourmand en ressource que le
			// string
			case 2000:
				Toast.makeText(this, "Recherche", 1000).show();
				Intent intentRecherche = new Intent(this, Recherche.class);
				intentRecherche.putExtra(ActivityParam.LaunchFromPage1, true);
				// on demarre la nouvelle activité
				startActivity(intentRecherche);
				termineActivity();
				break;
			case 2001:
				Toast.makeText(this, "Paramètres", 1000).show();
				Intent intentParametres = new Intent(this, tab_param.class);
				intentParametres.putExtra(ActivityParam.LaunchFromPageRecap,
						true);
				intentParametres.putExtra(ActivityParam.Marque,
						MarqueChoisie.trim());
				intentParametres.putExtra(ActivityParam.DurreeDeVie, dureeVie
						.getText().toString().trim());
				intentParametres.putExtra(ActivityParam.DateAchat, mDateDisplay
						.getText().toString().trim());
				intentParametres.putExtra(ActivityParam.NumeroDeTeinte,
						numeroTeinte.getText().toString().trim());
				intentParametres.putExtra(ActivityParam.NomProduit, nomProduit
						.getText().toString().trim());
				// on demarre la nouvelle activité
				startActivity(intentParametres);
				termineActivity();
				break;
			case 2002:
				Toast.makeText(this, "Notes", 1000).show();
				Intent intentNote = new Intent(this, note_page1.class);
				// on demarre la nouvelle activité
				startActivity(intentNote);
				termineActivity();
				break;

		}
		Log.i("", "" + item.getTitle());
		return super.onOptionsItemSelected(item);
	}

	/*
	 * public String UpperCaseWords(String line){ line =
	 * line.trim().toLowerCase(); String data[] = line.split("\\s"); line = "";
	 * for(int i =0;i< data.length;i++) { if(data[i].length()>1) { line = line +
	 * data[i].substring(0,1).toUpperCase()+data[i].substring(1)+" "; } else {
	 * line = line + data[i].toUpperCase(); } } return line.trim(); }
	 */

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
			case DATE_DIALOG_ID:
				return new DatePickerDialog(this, mDateSetListener, mYear,
						mMonth, mDay);
		}
		return null;
	}

	// updates the date we display in the TextView
	private void updateDisplay() {

		mDateDisplay.setText(new StringBuilder().append(mDay).append("/")
		// Month is 0 based so add 1
				.append(mMonth + 1).append("/").append(mYear).append(" "));
	}

	// the callback received when the user "sets" the date in the dialog
	private final DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDisplay();
		}
	};

	@Override
	public void onClick(View v) {

		if (v == BoutonAide) {
			adAide.show();
		}
		if (v == BoutonValider3) {

			nomDuProduit = nomProduit.getText().toString().trim();
			numeroDeTeinte = numeroTeinte.getText().toString().trim();
			DateAchat = mDateDisplay.getText().toString().trim();
			DureeVie = dureeVie.getText().toString().trim();
			// objBd.open();
			@SuppressWarnings("rawtypes")
			ArrayList[] Categorie_Cochée = objBd
					.renvoiCategorieEtProduitCochée();
			// objBd.close();
			Cat = Categorie_Cochée[0].toString().replace("[", "")
					.replace("]", "").trim();
			if (nomDuProduit.equals("") || numeroDeTeinte.equals("")
					|| DateAchat.equals("") || DureeVie.equals("")) {
				adManqueInfo.show();
			} else {
				adRecap = new AlertDialog.Builder(this);
				adRecap.setTitle("Souhaitez-vous ajouter ce produit à ma p’tite trousse ?");
				adRecap.setMessage(Cat + "\n" + MarqueChoisie + "\n"
						+ nomDuProduit + "\n" + "Numéro de teinte : "
						+ numeroDeTeinte + "\n" + "Date d'achat : " + DateAchat
						+ "\n" + "Durée de vie : " + DureeVie + " mois");
				adRecap.setPositiveButton("Oui",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int id) {

								insereProduitDansTableEtPrposeLaSuite();

							}
						});
				adRecap.setNegativeButton("Non", null);

				adRecap.show();

			}

		}
	}

	@SuppressWarnings("rawtypes")
	protected void insereProduitDansTableEtPrposeLaSuite() {
		// objBd.open();
		// **
		// Remplissage de la table temporaire
		ContentValues values = new ContentValues();
		values.put("nom_marque_choisie", MarqueChoisie);
		values.put("nom_produit", nomDuProduit);
		values.put("numero_teinte", numeroDeTeinte);
		values.put("DateAchat", DateAchat.replaceAll("/", "-").trim());
		values.put("Duree_Vie", DureeVie);
		objBd.deleteTable("trousse_tempo", "1", null);
		boolean insertOk = objBd.InsertDonnéedansTable("trousse_tempo", values);
		// fin du remplissage de la table temporaire.
		// recuperation des infos dans la table temporaires et insertion dans la
		// table produit finale.
		ArrayList[] Categorie_Cochée = objBd.renvoiCategorieEtProduitCochée();

		String[] Colonnes = { "nom_marque_choisie", "nom_produit",
				"numero_Teinte", "DateAchat", "Duree_Vie" };
		ArrayList[] trousse_tempo = objBd.renvoi_liste_ValeurTroussetempo(
				"trousse_tempo", Colonnes);

		ContentValues valuesProduitsFinal = new ContentValues();
		valuesProduitsFinal.put("nom_produit", trousse_tempo[1].toString()
				.replace("[", "").replace("]", ""));
		valuesProduitsFinal.put("nom_marque", trousse_tempo[0].toString()
				.replace("[", "").replace("]", ""));
		valuesProduitsFinal.put("nom_souscatergorie", Categorie_Cochée[0]
				.toString().replace("[", "").replace("]", ""));
		valuesProduitsFinal.put("nom_categorie", Categorie_Cochée[1].toString()
				.replace("[", "").replace("]", ""));
		valuesProduitsFinal.put("numero_Teinte", trousse_tempo[2].toString()
				.replace("[", "").replace("]", ""));
		valuesProduitsFinal.put("DateAchat", trousse_tempo[3].toString()
				.replace("[", "").replace("]", ""));
		valuesProduitsFinal.put("Duree_Vie", trousse_tempo[4].toString()
				.replace("[", "").replace("]", ""));
		// calcul de la date de peremption problable

		int nbMoisDurreeDeVie = Integer.parseInt(trousse_tempo[4].toString()
				.replace("[", "").replace("]", ""));
		int nbJours = nbMoisDurreeDeVie * 30;

		String DateAchat = trousse_tempo[3].toString().replace("[", "")
				.replace("]", "");
		String tabAchat[] = DateAchat.split("-");
		int jourAchat = Integer.parseInt(tabAchat[0]);
		int mois = Integer.parseInt(tabAchat[1]) - 1;// les mois commence à 0
														// (janvier) et se
														// termine à 11
														// (decembre)
		int annee = Integer.parseInt(tabAchat[2]) - 1900;// les années commence
															// à 0(1900), pour
															// avoir l'année
															// exacte a partir
															// d'une
															// velur contenu
															// dans un string,
															// il faut
															// retrancher 1900 a
															// la valeur de
															// l'année.
		// exemple, l'année 2010 est considérée comme 2010-1900 = 110

		Date DateAchatAuformatDate = new Date(annee, mois, jourAchat);
		long DateAchatEnMilli = DateAchatAuformatDate.getTime();// on recupere
																// la date
																// d'achat au
																// format
																// milliseconde

		Date DatePeremption = getDateAfterDays(DateAchatEnMilli, nbJours);// on
																			// calcule
																			// la
																			// date
																			// de
																			// permetpion
																			// en
																			// fonction
																			// de
																			// la
																			// date
																			// d'achat+nb
																			// de
																			// jour
																			// donné
																			// par
																			// l'utilisateur
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String Date_Peremption = dateFormat.format(DatePeremption);// date de
																	// peremtion
																	// au format
																	// jj/mm/aaaa

		long DatePeremtInMilli = DatePeremption.getTime(); // on converti la
															// date de
															// permeption en
															// milliseconde
		valuesProduitsFinal.put("Date_Peremption_milli", DatePeremtInMilli);// et
																			// on
																			// le
																			// stocke
																			// en
																			// base
		// ////////////////////////////////////////////////////////
		valuesProduitsFinal.put("Date_Peremption", Date_Peremption);
		insertOk = objBd.InsertDonnéedansTable("produit_Enregistre",
				valuesProduitsFinal);
		if (insertOk) {
			popUp("Insert Ok");
			AccesTableTrousseProduits accesTrousse = new AccesTableTrousseProduits(
					this);
			accesTrousse.reinitProduitChoisi();
			// String Table = "trousse_produits";
			// ContentValues modifiedValues = new ContentValues();
			// modifiedValues.put("ischecked", "false");
			// String whereClause = "ischecked=?";
			// String[] whereArgs = new String[] { "true" };

			// objBd.open();
			// int nbdechamp = objBd.majTable(Table, modifiedValues,
			// whereClause, whereArgs);
			objBd.deleteTable("trousse_tempo", "1", null);
			// System.out.println("Nombre de champ modifié : " + nbdechamp);
			// objBd.close();

			// //objBd.close();
			adFiniOuPas.show();
		} else {
			// popUp("Insert Pas Ok");
			// String Table = "trousse_produits";
			// ContentValues modifiedValues = new ContentValues();
			// modifiedValues.put("ischecked", "false");
			// String whereClause = "ischecked=?";
			// String[] whereArgs = new String[] { "true" };
			//
			// //objBd.open();
			// int nbdechamp = objBd.majTable(Table, modifiedValues,
			// whereClause, whereArgs);
			// objBd.deleteTable("trousse_tempo", "1", null);
			// System.out.println("Nombre de champ modifié : " + nbdechamp);
			// //objBd.close();

		}
		// popUp("date peremption: "+Date_Peremption);
		// objBd.close();

	}

	public static Date getDateAfterDays(long dateEnMilli, int days) {
		long backDateMS = dateEnMilli + ((long) days) * 24 * 60 * 60 * 1000;
		Date backDate = new Date();
		backDate.setTime(backDateMS);
		return backDate;
	}

	public void popUp(String message) {
		// Toast.makeText(this, message, 1).show();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		popUp("onRestart()-Page3");
	}

	/**
	 * Exécuté lorsque l'activité devient visible à l'utilisateur. La fonction
	 * onStart() est suivie de la fonction onResume().
	 */
	@Override
	protected void onStart() {
		super.onStart();
		popUp("onStart()-Page3");
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

		// boolean IsCalledFromPage1 =
		// getIntent().getBooleanExtra(ActivityParam.LaunchFromPage1, false);
		// boolean IsCalledFromDupplique =
		// getIntent().getBooleanExtra(ActivityParam.LaunchFromDuppliquer,
		// false);
		//
		// if (IsCalledFromPage1 || IsCalledFromDupplique) {

		MarqueChoisie = getIntent().getStringExtra(ActivityParam.Marque).trim();
		DureeVie = getIntent().getStringExtra(ActivityParam.DurreeDeVie).trim();
		DateAchat = getIntent().getStringExtra(ActivityParam.DateAchat).trim();
		numeroDeTeinte = getIntent().getStringExtra(
				ActivityParam.NumeroDeTeinte).trim();
		nomDuProduit = getIntent().getStringExtra(ActivityParam.NomProduit)
				.trim();
		if (DateAchat.equals("")) {
			updateDisplay();
		} else {
			mDateDisplay.setText(new StringBuilder().append(DateAchat
					.replaceAll("-", "/")));
		}
		if (!nomDuProduit.equals("")) {
			nomProduit.setText(nomDuProduit);
		}
		if (!numeroDeTeinte.equals("")) {
			numeroTeinte.setText(numeroDeTeinte);
		}
		if (!DureeVie.equals("")) {
			dureeVie.setText(DureeVie);
		}
		// }

		// if (IsCalledFromPageRecap) {
		// MarqueChoisie = getIntent().getStringExtra("MarqueChoisie").trim();
		// DateAchat = getIntent().getStringExtra("DateAchat").trim();
		// nomDuProduit = getIntent().getStringExtra("NomProduit").trim();
		// numeroDeTeinte = getIntent().getStringExtra("NumTeinte").trim();
		// DureeVie = getIntent().getStringExtra("DurreeDeVie").trim();
		// if (DateAchat.equals("")) {
		// updateDisplay();
		// } else {
		// mDateDisplay.setText(new
		// StringBuilder().append("Date choisie: ").append(DateAchat));
		// }
		// if (!nomDuProduit.equals("")) {
		// nomProduit.setText(nomDuProduit);
		// }
		// if (!numeroDeTeinte.equals("")) {
		// numeroTeinte.setText(numeroDeTeinte);
		// }
		// if (!DureeVie.equals("")) {
		// dureeVie.setText(DureeVie);
		// }
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
		// popUp("onStop-Page3");
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
			Intent page2 = new Intent(this, formulaire_entree_page1bis.class);
			page2.putExtra(ActivityParam.Marque, MarqueChoisie.trim());
			page2.putExtra(ActivityParam.DurreeDeVie, DureeVie.trim());
			page2.putExtra(ActivityParam.DateAchat, DateAchat.trim());
			page2.putExtra(ActivityParam.NumeroDeTeinte, numeroDeTeinte.trim());
			page2.putExtra(ActivityParam.NomProduit, nomDuProduit.trim());
			page2.putExtra(ActivityParam.LaunchFromPageRecapBack, true);
			startActivity(page2);
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

	public void OnDestroy() {
		// popUp("OnDestroy");
		super.onDestroy();

	}

}
