package fr.smardine.matroussedemaquillage.recherche;

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
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import fr.smardine.matroussedemaquillage.R;
import fr.smardine.matroussedemaquillage.base.BDAcces;
import fr.smardine.matroussedemaquillage.modifier.modif_cat;
import fr.smardine.matroussedemaquillage.note.note_page1;
import fr.smardine.matroussedemaquillage.param.tab_param;
import fr.smardine.matroussedemaquillage.variableglobale.EnTheme;

public class affiche_detail extends Activity implements OnClickListener {

	long DatePeremtInMilli = 0;
	TextView CategorieDetail, DateAchatDetail, DatePeremtionDetail;
	TextView nomProduitDetail, numeroTeinteDetail;
	TextView MarqueDetail;

	ImageView BTChangerCat, BTChangerMarque, BTChangerNom, BTChangerTeinte, BTChangerDateAchat, BTChangerDatePermeption;
	private BDAcces objBd;
	String DuréeVie = "";
	String IdProduit;

	@SuppressWarnings("rawtypes")
	ArrayList[] trousse_final;
	boolean Continue;
	boolean IsCalledFromMain;
	boolean IsAffichageProduitPerimé;
	AlertDialog.Builder adChoixNbMois, adMarque, adProduit, adTeinte;
	int nbMoisDurreeDeVie = 0;
	int RequestCodePage1 = 1, RequestCodePage2 = 2, RequestCodePage3 = 3, mYear, mMonth, mDay;

	// ///////////////////

	static final int DATE_DIALOG_ID = 0;

	// /////////////////////////

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ExceptionHandler.register(this, "http://simon.mardine.free.fr/trousse_maquillage/test/server.php","ma_ptite_trousse");
		choisiLeTheme();

		// capture our View elements
		CategorieDetail = (TextView) findViewById(R.id.Categorie2_detail);
		MarqueDetail = (TextView) findViewById(R.id.Marque2_detail);
		nomProduitDetail = (TextView) findViewById(R.id.Nom2_detail);
		numeroTeinteDetail = (TextView) findViewById(R.id.Teinte2_detail);
		DateAchatDetail = (TextView) findViewById(R.id.dateDisplay2_detail);
		DatePeremtionDetail = (TextView) findViewById(R.id.DureeVie2_detail);

		BTChangerCat = (ImageView) findViewById(R.id.IvChangerCat);
		BTChangerMarque = (ImageView) findViewById(R.id.IvChangerMarque);
		BTChangerNom = (ImageView) findViewById(R.id.IvChangerProduit);
		BTChangerTeinte = (ImageView) findViewById(R.id.IvChangerTeinte);
		BTChangerDateAchat = (ImageView) findViewById(R.id.IvChangerDateAchat);
		BTChangerDatePermeption = (ImageView) findViewById(R.id.IvChangerDatePeremption);

		objBd = new BDAcces(this);

		updateDisplay();

		this.setTitle("Détails");
		BTChangerCat.setOnClickListener(this);
		BTChangerMarque.setOnClickListener(this);
		BTChangerNom.setOnClickListener(this);
		BTChangerTeinte.setOnClickListener(this);
		BTChangerDateAchat.setOnClickListener(this);
		BTChangerDatePermeption.setOnClickListener(this);

		IsCalledFromMain = getIntent().getBooleanExtra("calledFromMain", false);
		IsAffichageProduitPerimé = getIntent().getBooleanExtra("AfficheProduitPerimé", false);

		popUp("OnCreate-pageDetail");

		BTChangerDateAchat.setOnClickListener(this);

		// get the current date
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 
	 */
	private void choisiLeTheme() {
		objBd = new BDAcces(this);
		objBd.open();
		String[] champ = { "AfficheAlerte", "DureeViePeremp", "Theme" };
		@SuppressWarnings("rawtypes")
		ArrayList[] Param = objBd.renvoi_param(champ);

		String nomThemeChoisi = Param[2].get(0).toString().trim();
		if (EnTheme.Bisounours.getLib().equals(nomThemeChoisi)) {
			setContentView(R.layout.theme_bisounours_affiche_detail);

		}
		if (EnTheme.Classique.getLib().equals(nomThemeChoisi)) {
			setContentView(R.layout.affiche_detail);

		}
		if (EnTheme.Fleur.getLib().equals(nomThemeChoisi)) {
			setContentView(R.layout.theme_fleur_affiche_detail);
		}

		objBd.close();
	}

	private void onCreateMenu(Menu menu) {
		SubMenu note = menu.addSubMenu(0, 2002, 2, "Notes");
		note.setIcon(R.drawable.menu_note);
		SubMenu parametre = menu.addSubMenu(0, 2001, 3, "Parametres");
		parametre.setIcon(R.drawable.menu_param); // icone systeme
		SubMenu help = menu.addSubMenu(0, 2003, 4, "Aide");
		help.setIcon(R.drawable.ad_question);
		// m.add(0, 1000, 0, "menu 1").setChecked(true).setCheckable(true);
		// m.add(0, 1001, 0, "menu 2").setEnabled(false);
		// pourquoi sous-classer une méthode ? = pour limiter les ressources
		// privilégier la création "à la demande" le menu est créé uniquement
		// lorsque la touche physique <menu> est appuyée par l'utilisateur
		// r.add(0, 1002, 0, "menu 3");
		// r.add(0, 1003, 0, "menu 4");
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
			// l'identifiant integer est moins gourmand en ressource que le string
			case 2001:
				Toast.makeText(this, "Paramètres", 1000).show();
				Intent intentParametres = new Intent(this, tab_param.class);
				// on demarre la nouvelle activité
				startActivity(intentParametres);
				finish();
				break;
			case 2002:
				Toast.makeText(this, "Notes", 1000).show();
				Intent intentNote = new Intent(this, note_page1.class);
				// on demarre la nouvelle activité
				startActivity(intentNote);
				finish();
				break;
			case 2003:
				AlertDialog.Builder adHelp = new AlertDialog.Builder(this);
				adHelp.setTitle("Aide");
				adHelp.setIcon(R.drawable.ad_question);
				adHelp.setMessage("En cliquant les icones en forme de flèches,\n" + "vous pourrez modifier votre produit");
				adHelp.setPositiveButton("Ok", null);
				adHelp.show();
		}
		Log.i("", "" + item.getTitle());
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
			case DATE_DIALOG_ID:
				return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);

		}
		return null;
	}

	/**
	 * This utility method returns a future date after number of days.
	 * @param days
	 * @return
	 */
	public static Date getDateAfterDays(long dateEnMilli, int days) {
		long backDateMS = dateEnMilli + ((long) days) * 24 * 60 * 60 * 1000;
		Date backDate = new Date();
		backDate.setTime(backDateMS);
		return backDate;
	}

	// updates the date we display in the TextView

	private void updateDisplay() {
		objBd.open();
		IdProduit = getIntent().getStringExtra("IDProduit").trim();
		String[] Colonnes = { "nom_produit", "nom_souscatergorie", "nom_categorie", "numero_Teinte", "Duree_Vie", "Date_Peremption",
				"DateAchat", "nom_marque" };

		trousse_final = objBd.renvoi_liste_TrousseFinalComplete(Colonnes, IdProduit);

		CategorieDetail.setText(new StringBuilder().append(trousse_final[1].toString().replace("[", "").replace("]", "")));
		MarqueDetail.setText(new StringBuilder().append(trousse_final[7].toString().replace("[", "").replace("]", "")));
		nomProduitDetail.setText(new StringBuilder().append(trousse_final[0].toString().replace("[", "").replace("]", "")));
		numeroTeinteDetail.setText(new StringBuilder().append(trousse_final[3].toString().replace("[", "").replace("]", "")));
		DateAchatDetail
				.setText(new StringBuilder().append(trousse_final[6].toString().replace("[", "").replace("]", "").replace("-", "/")));
		DatePeremtionDetail.setText(new StringBuilder().append(trousse_final[5].toString().replace("[", "").replace("]", "")
				.replace("-", "/")));

		DuréeVie = trousse_final[4].toString().replace("[", "").replace("]", "");
		objBd.close();
	}

	@SuppressWarnings("unused")
	private void majTableProduit() {
		// TODO Auto-generated method stub

		String[] Colonnes = { "nom_produit", "nom_souscatergorie", "nom_categorie", "numero_Teinte", "Duree_Vie", "Date_Peremption",
				"DateAchat", "nom_marque" };

		objBd.open();
		trousse_final = objBd.renvoi_liste_TrousseFinalComplete(Colonnes, IdProduit);
		objBd.close();
		String Table = "produit_Enregistre";
		String Nom_Produit = nomProduitDetail.getText().toString().trim().replace("[", "").replace("]", "");
		String SousCat = trousse_final[1].toString().replace("[", "").replace("]", "");
		String Cat = trousse_final[2].toString().replace("[", "").replace("]", "");
		String Numeroteinte = numeroTeinteDetail.getText().toString().trim().replace("[", "").replace("]", "");
		String DurreeVie = "";
		if (nbMoisDurreeDeVie == 0) {// aucun changement apporté par l'utilisateur, on garde la valeur en base
			DurreeVie = trousse_final[4].toString().replace("[", "").replace("]", "");
		} else {
			DurreeVie = "" + nbMoisDurreeDeVie + "";
		}

		String DatePeremption = DatePeremtionDetail.getText().toString().trim().replace("[", "").replace("]", "").replace("/", "-");
		String DateAchat = DateAchatDetail.getText().toString().trim().replace("[", "").replace("]", "").replace("/", "-");
		String NomMarque = MarqueDetail.getText().toString().trim().replace("[", "").replace("]", "");

		ContentValues modifiedValues = new ContentValues();
		modifiedValues.put("nom_produit", Nom_Produit);
		modifiedValues.put("nom_souscatergorie", SousCat);
		modifiedValues.put("nom_categorie", Cat);
		modifiedValues.put("numero_Teinte", Numeroteinte);
		modifiedValues.put("Duree_Vie", DurreeVie);
		modifiedValues.put("Date_Peremption", DatePeremption);
		modifiedValues.put("DateAchat", DateAchat);
		modifiedValues.put("nom_marque", NomMarque);
		modifiedValues.put("Date_Peremption_milli", DatePeremtInMilli);

		String whereClause = "id_produits=?";
		String[] whereArgs = new String[] { "" + IdProduit + "" };
		objBd = new BDAcces(this);
		objBd.open();
		int nbdechamp = objBd.majTable(Table, modifiedValues, whereClause, whereArgs);
		// objBd.deleteTable("trousse_tempo","1",null);
		// System.out.println("Nombre de champ modifie dans la table produit_Enregistre : "+nbdechamp+" sur l'id n° "+Id_Produits+"nom cat="+cat+"nom sous cat"
		// + souscat);
		objBd.close();
	}

	// the callback received when the user "sets" the date in the dialog
	private final DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			// updateDisplay();
			DateAchatDetail.setText(new StringBuilder().append(mDay).append("/")
			// Month is 0 based so add 1
					.append(mMonth + 1).append("/").append(mYear).append(" "));
		}
	};

	public void popUp(String message) {
		// Toast.makeText(this, message, 1).show();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		// popUp("onRestart()-PageDetail");
	}

	/**
	 * Exécuté lorsque l'activité devient visible à l'utilisateur. La fonction onStart() est suivie de la fonction onResume().
	 */
	@Override
	protected void onStart() {
		super.onStart();
		// popUp("onStart()-PageDetail");
	}

	/**
	 * Exécutée a chaque passage en premier plan de l'activité. Ou bien, si l'activité passe à nouveau en premier (si une autre activité
	 * était passé en premier plan entre temps). La fonction onResume() est suivie de l'exécution de l'activité.
	 */
	@Override
	protected void onResume() {
		super.onResume();
		// popUp("onResume()-PageDetail");

	}

	/**
	 * La fonction onStop() est exécutée : - lorsque l'activité n'est plus en premier plan - ou bien lorsque l'activité va être détruite
	 * Cette fonction est suivie : - de la fonction onRestart() si l'activité passe à nouveau en premier plan - de la fonction onDestroy()
	 * lorsque l'activité se termine ou bien lorsque le système décide de l'arrêter
	 */
	@Override
	protected void onStop() {
		super.onStop();
		// popUp("onStop-PageDetail");
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
			Intent recherche;
			if (IsCalledFromMain && IsAffichageProduitPerimé) {
				recherche = new Intent(this, recherche_produit_perime.class);
				recherche.putExtra("calledFromMain", true);
				recherche.putExtra("AfficheProduitPerimé", true);
			} else {
				recherche = new Intent(this, recherchetabsbuttons.class);
			}
			majTableProduit();
			startActivity(recherche);
			finish();
			return true;
		}
		if (keyCode == KeyEvent.KEYCODE_SEARCH) {
			Intent intentRecherche = new Intent(this, recherchetabsbuttons.class);
			// on demarre la nouvelle activité
			startActivity(intentRecherche);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	public void OnDestroy() {
		popUp("OnDestroy-PageDetail");
		super.onDestroy();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == BTChangerCat) {
			Intent modifcat = new Intent(this, modif_cat.class);
			modifcat.putExtra("ID_Produit", IdProduit);
			modifcat.putExtra("LaunchByDetail", true);

			startActivity(modifcat);
			finish();

		}
		if (v == BTChangerDateAchat) {
			showDialog(DATE_DIALOG_ID);
		}
		if (v == BTChangerDatePermeption) {
			final EditText inputDurreeVie = new EditText(this);
			inputDurreeVie.addTextChangedListener(new TextWatcher() {
				@SuppressWarnings("unused")
				int len = 0;

				@Override
				public void afterTextChanged(Editable s) {
					String str = inputDurreeVie.getText().toString();
					if (!str.equals("")) {
						int ValeurRentrée = Integer.parseInt(str);
						if (ValeurRentrée > 99) {
							inputDurreeVie.setText("99");
						}
						if (ValeurRentrée <= 0) {
							inputDurreeVie.setText("1");
						}
					} else {
						inputDurreeVie.setText("1");
					}

				}

				@Override
				public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

					String str = inputDurreeVie.getText().toString();
					len = str.length();
				}

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
				}

			});
			// get the current date
			adChoixNbMois = new AlertDialog.Builder(this);
			adChoixNbMois.setTitle("Durrée de vie");
			adChoixNbMois.setMessage("Merci de renseigner la durée de vie de votre produit\n(en nombre de mois) ");
			// Set an EditText view to get user input
			inputDurreeVie.setText(DuréeVie);
			inputDurreeVie.setInputType(InputType.TYPE_CLASS_NUMBER);
			adChoixNbMois.setView(inputDurreeVie);
			adChoixNbMois.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int whichButton) {
					String value = inputDurreeVie.getText().toString();
					// Do something with value!
					if (value.equals("")) {
						value = "1";
					}
					nbMoisDurreeDeVie = Integer.parseInt(value);
					int nbJours = nbMoisDurreeDeVie * 30;

					String DateAchat = DateAchatDetail.getText().toString().trim();
					String tabAchat[] = DateAchat.split("/");
					int jourAchat = Integer.parseInt(tabAchat[0]);
					int mois = Integer.parseInt(tabAchat[1]) - 1;// les mois commence à 0 (janvier) et se termine à 11 (decembre)
					int annee = Integer.parseInt(tabAchat[2]) - 1900;// les années commence à 0(1900), pour avoir l'année exacte a partir
																		// d'une velur contenu dans un string, il faut retrancher 1900 a la
																		// valeur de l'année.
					// exemple, l'année 2010 est considérée comme 2010-1900 = 110

					Date DateAchatAuformatDate = new Date(annee, mois, jourAchat);
					DatePeremtInMilli = DateAchatAuformatDate.getTime();// on recupere la date d'achat au format milliseconde
					Date DatePeremption = getDateAfterDays(DatePeremtInMilli, nbJours);// on calcule la date de permetpion en fonction de la
																						// date d'achat+nb de jour donné par l'utilisateur
					DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					String Date_Peremption = dateFormat.format(DatePeremption);// date de peremtion au format jj/mm/aaaa

					DatePeremtInMilli = DatePeremption.getTime(); // on converti la date de permeption en milliseconde

					DatePeremtionDetail.setText(Date_Peremption);
				}
			});
			adChoixNbMois.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int whichButton) {
					// Canceled.
				}
			});
			adChoixNbMois.show();
		}
		if (v == BTChangerMarque) {
			final AutoCompleteTextView inputMarque = new AutoCompleteTextView(this);
			adMarque = new AlertDialog.Builder(this);
			adMarque.setTitle("Choix de la marque");
			adMarque.setMessage("Veuillez de renseigner la marque");
			inputMarque.setText(MarqueDetail.getText().toString());
			adMarque.setView(inputMarque);
			objBd.open();
			String[] Marque = objBd.renvoi_liste_ValeurDansString("trousse_marques", "nom_marque");

			objBd.close();
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_marque_auto, Marque);
			inputMarque.setAdapter(adapter);
			inputMarque.setThreshold(1);// des le premier caractere tapé, on affiche la iste.
			adMarque.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int whichButton) {
					String value = inputMarque.getText().toString();
					MarqueDetail.setText(value);
				}
			});

			adMarque.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int whichButton) {
					// Canceled.
				}
			});
			adMarque.show();
		}
		if (v == BTChangerNom) {
			final EditText inputProduit = new EditText(this);
			adProduit = new AlertDialog.Builder(this);
			adProduit.setTitle("Choix du produit");
			adProduit.setMessage("Veuillez renseigner le nom du produit");
			inputProduit.setText(nomProduitDetail.getText().toString());
			adProduit.setView(inputProduit);
			adProduit.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int whichButton) {
					String value = inputProduit.getText().toString();
					nomProduitDetail.setText(value);
				}
			});
			adProduit.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int whichButton) {
					// Canceled.
				}
			});
			adProduit.show();
		}
		if (v == BTChangerTeinte) {
			final EditText inputTeinte = new EditText(this);
			adTeinte = new AlertDialog.Builder(this);
			adTeinte.setTitle("Choix de la teinte");
			adTeinte.setMessage("Veuillez renseigner la teinte du produit");
			inputTeinte.setText(numeroTeinteDetail.getText().toString());
			adTeinte.setView(inputTeinte);
			adTeinte.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int whichButton) {
					String value = inputTeinte.getText().toString();
					numeroTeinteDetail.setText(value);
				}
			});
			adTeinte.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int whichButton) {
					// Canceled.
				}
			});
			adTeinte.show();
		}

	}

}
