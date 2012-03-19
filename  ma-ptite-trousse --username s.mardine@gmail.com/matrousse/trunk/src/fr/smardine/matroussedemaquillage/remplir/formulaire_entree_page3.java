package fr.smardine.matroussedemaquillage.remplir;

import helper.DateHelper;
import helper.SerialisableHelper;

import java.util.Calendar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
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
import fr.smardine.matroussedemaquillage.R;
import fr.smardine.matroussedemaquillage.alertDialog.AlertDialogFactory;
import fr.smardine.matroussedemaquillage.alertDialog.type.EnTypeAlertDialogAide;
import fr.smardine.matroussedemaquillage.alertDialog.type.EnTypeAlertDialogAttention;
import fr.smardine.matroussedemaquillage.alertDialog.type.EnTypeAlertDialogSingleChoice;
import fr.smardine.matroussedemaquillage.base.accesTable.AccesTableParams;
import fr.smardine.matroussedemaquillage.base.accesTable.AccesTableProduitEnregistre;
import fr.smardine.matroussedemaquillage.base.accesTable.AccesTableTrousseProduits;
import fr.smardine.matroussedemaquillage.mdl.MlProduit;
import fr.smardine.matroussedemaquillage.note.note_page1;
import fr.smardine.matroussedemaquillage.param.tab_param;
import fr.smardine.matroussedemaquillage.recherche.Recherche;
import fr.smardine.matroussedemaquillage.variableglobale.ActivityParam;
import fr.smardine.matroussedemaquillage.variableglobale.EnTheme;

/**
 * @author smardine
 */
public class formulaire_entree_page3 extends SuperActivity implements
		OnClickListener, IremplissageActivity {
	Button BoutonValider3;

	private TextView mDateDisplay;
	private Button mPickDate;
	private ImageView BoutonAide;
	private EditText nomProduit, numeroTeinte, dureeVie;

	private int mYear;
	private int mMonth;
	private int mDay;

	// private final boolean NouveauProduit = false, Duppliquer = false,
	// acceuil = false;

	static final int DATE_DIALOG_ID = 0;
	AlertDialog.Builder adManqueInfo, adAide, adRecap, adFiniOuPas;
	// String MarqueChoisie = "";
	// String nomDuProduit = "";
	// String numeroDeTeinte = "";
	// String DateAchat = "";
	// String DureeVie = "";
	// MlTrousseProduit Cat;

	private MlProduit produit;

	// /////////////////////////

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ExceptionHandler.register(this,
		// "http://simon.mardine.free.fr/trousse_maquillage/test/server.php","ma_ptite_trousse");

		initComposantVisuel();
		adFiniOuPas = new AlertDialogFactory(this)
				.getSingleChoiceDialog(EnTypeAlertDialogSingleChoice.FINIOUPAS);

		// adFiniOuPas = new AlertDialog.Builder(this);
		// adFiniOuPas.setTitle("Que voulez vous faire?");
		// adFiniOuPas.setIcon(R.drawable.ad_attention);
		// CharSequence[] items = { "Ajouter un produit",
		// "Dupliquer un produit",
		// "Revenir à la page d'accueil" };
		// adFiniOuPas.setSingleChoiceItems(items, -1,
		// new DialogInterface.OnClickListener() {
		//
		// @Override
		// public void onClick(DialogInterface dialog, int item) {
		// /* User clicked on a radio button do some stuff */
		//
		// switch (item) {
		// case 0:
		// NouveauProduit = true;
		// break;
		// case 1:
		// Duppliquer = true;
		// break;
		// case 2:
		// acceuil = true;
		// break;
		// }
		//
		// }
		// });

		// adFiniOuPas.setPositiveButton("Ok",
		// new DialogInterface.OnClickListener() {
		// @Override
		// public void onClick(DialogInterface dialog, int id) {
		// new AccesTableTrousseProduits(getApplicationContext())
		// .reinitProduitChoisi();
		// Intent intent = null;
		// if (NouveauProduit) {
		// intent = new Intent(formulaire_entree_page3.this,
		// formulaire_entree_page1bis.class);
		// }
		// if (Duppliquer) {// on revient a la page d'acceuil
		// intent = new Intent(formulaire_entree_page3.this,
		// choix_produit_a_duppliquer.class);
		// }
		// if (acceuil) {
		// intent = new Intent(formulaire_entree_page3.this,
		// Main.class);
		// }
		// intent.putExtra(ActivityParam.LaunchFromPageRecap, true);
		// startActivity(intent);
		// termineActivity();
		// }
		// });
		dureeVie.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				String str = dureeVie.getText().toString();
				if (!str.equals("")) {
					int valeurRentree = Integer.parseInt(str);
					if (valeurRentree > 99) {
						dureeVie.setText("99");
					} else if (valeurRentree <= 0) {
						dureeVie.setText("1");
					}
				} else {
					dureeVie.setText("1");
				}
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

		});

		adAide = new AlertDialogFactory(this)
				.getAideDialog(EnTypeAlertDialogAide.AIDE_PAGE3);

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

		// objBd = new BDAcces(this);
		BoutonValider3.setOnClickListener(this);
		adManqueInfo = new AlertDialogFactory(this)
				.getAttentionDialog(EnTypeAlertDialogAttention.MANQUE_INFO);

		this.setTitle("Choix noms & dates");
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

				transfereMlProduitToActivity(intentParametres);
				intentParametres.putExtra(ActivityParam.LaunchFromPageRecap,
						true);

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
			if (checkInfoManquante()) {
				adManqueInfo.show();
				return;
			}
			// nomDuProduit = nomProduit.getText().toString().trim();
			// numeroDeTeinte = numeroTeinte.getText().toString().trim();
			// DateAchat = mDateDisplay.getText().toString().trim();
			// DureeVie = dureeVie.getText().toString().trim();
			// objBd.open();
			// AccesTableTrousseProduits accesProduit = new
			// AccesTableTrousseProduits(
			// this);
			// MlListeTrousseProduit lstProdCoche = accesProduit
			// .getListeProduitCochee();
			// ArrayList[] Categorie_Cochée = objBd
			// .renvoiCategorieEtProduitCochée();
			// objBd.close();
			// Cat = lstProdCoche.get(0);

			adRecap = new AlertDialog.Builder(this);
			adRecap.setTitle("Souhaitez-vous ajouter ce produit à ma p’tite trousse ?");
			adRecap.setMessage(produit.getCategorie().getSousCategorie()
					.getLib()
					+ "\n"
					+ produit.getMarque()
					+ "\n"
					+ nomProduit.getText()
					+ "\n"
					+ "Numéro de teinte : "
					+ numeroTeinte.getText()
					+ "\n"
					+ "Date d'achat : "
					+ mDateDisplay.getText().toString()
					+ "\n"
					+ "Durée de vie : " + dureeVie.getText() + " mois");
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

	private boolean checkInfoManquante() {
		return (nomProduit.getText().toString().trim().equals("")//
				|| numeroTeinte.getText().toString().trim().equals("")//
				|| mDateDisplay.getText().toString().trim().equals("") //
		|| dureeVie.getText().toString().trim().equals(""));

	}

	private void insereProduitDansTableEtPrposeLaSuite() {

		AccesTableTrousseProduits accesTrousseProduit = new AccesTableTrousseProduits(
				this);
		// MlListeTrousseProduit lstProduitCoche = accesTrousseProduit
		// .getListeProduitCochee();

		produit.setNomProduit(nomProduit.getText().toString().trim());
		// p.setMarque(MarqueChoisie);
		// produit.setCategorie(new MlCategorie(
		// lstProduitCoche.get(0).getNomCat(), lstProduitCoche.get(0)
		// .getNomSousCat()));

		// p.setNomCat(lstProduitCoche.get(0).getNomCat());
		produit.setTeinte(numeroTeinte.getText().toString().trim());
		produit.setDateAchat(DateHelper.getDateFromDatabase(mDateDisplay
				.getText().toString().trim().replaceAll("/", "-").trim()));
		produit.setDureeVie(Integer.parseInt(dureeVie.getText().toString()
				.trim()));

		AccesTableProduitEnregistre accesProduit = new AccesTableProduitEnregistre(
				this);
		boolean insertOk = accesProduit.insertProduit(produit);

		if (insertOk) {
			popUp("Insert Ok");
			AccesTableTrousseProduits accesTrousse = new AccesTableTrousseProduits(
					this);
			accesTrousse.reinitProduitChoisi();

			adFiniOuPas.show();
		}

	}

	/**
	 * Exécutée a chaque passage en premier plan de l'activité. Ou bien, si
	 * l'activité passe à nouveau en premier (si une autre activité était passé
	 * en premier plan entre temps). La fonction onResume() est suivie de
	 * l'exécution de l'activité.
	 */
	@Override
	public void onResume() {
		super.onResume();

		// boolean IsCalledFromPage1 =
		// getIntent().getBooleanExtra(ActivityParam.LaunchFromPage1, false);
		// boolean IsCalledFromDupplique =
		// getIntent().getBooleanExtra(ActivityParam.LaunchFromDuppliquer,
		// false);
		//
		// if (IsCalledFromPage1 || IsCalledFromDupplique) {
		recupereMlProduitfromPreviousActivity();

		// MarqueChoisie =
		// getIntent().getStringExtra(ActivityParam.Marque).trim();
		// DureeVie =
		// getIntent().getStringExtra(ActivityParam.DurreeDeVie).trim();
		// DateAchat =
		// getIntent().getStringExtra(ActivityParam.DateAchat).trim();
		// numeroDeTeinte = getIntent().getStringExtra(
		// ActivityParam.NumeroDeTeinte).trim();
		// nomDuProduit = getIntent().getStringExtra(ActivityParam.NomProduit)
		// .trim();
		if (produit.getDateAchat() == null) {
			updateDisplay();
		} else {
			mDateDisplay.setText(new StringBuilder().append(DateHelper
					.getDateforDatabase(produit.getDateAchat()).replaceAll("-",
							"/")));
		}
		if (produit.getNomProduit() != null) {
			nomProduit.setText(produit.getNomProduit());
		}
		if (produit.getTeinte() != null) {
			numeroTeinte.setText(produit.getTeinte());
		}
		if (produit.getDureeVie() != 0) {
			dureeVie.setText("" + produit.getDureeVie());
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent page2 = new Intent(this, formulaire_entree_page1bis.class);

			transfereMlProduitToActivity(page2);

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

	@Override
	public MlProduit recupereMlProduitfromPreviousActivity() {
		byte[] extra = getIntent().getByteArrayExtra(
				MlProduit.class.getCanonicalName());
		Object o = SerialisableHelper.deserializeObject(extra);
		if (o instanceof MlProduit) {
			produit = (MlProduit) o;
		}

		return produit;
	}

	@Override
	public void transfereMlProduitToActivity(Intent p_intent) {
		if (produit == null) {
			produit = new MlProduit();
		}
		produit.setDureeVie(Integer.parseInt(dureeVie.getText().toString()
				.trim()));
		produit.setDateAchat(DateHelper.getDateFromDatabase(mDateDisplay
				.getText().toString().trim()));
		produit.setTeinte(numeroTeinte.getText().toString().trim());
		produit.setNomProduit(nomProduit.getText().toString().trim());

		p_intent.putExtra(MlProduit.class.getCanonicalName(),
				SerialisableHelper.serialize(produit));

	}

	@Override
	public void initComposantVisuel() {
		ChoisiLeTheme();
		mDateDisplay = (TextView) findViewById(R.id.dateDisplay_page3);
		mPickDate = (Button) findViewById(R.id.pickDate_page3);
		nomProduit = (EditText) findViewById(R.id.EditTextNom_page3);
		numeroTeinte = (EditText) findViewById(R.id.EditTextTeinte_page3);
		dureeVie = (EditText) findViewById(R.id.EditTextDureeDeVie_page3);
		BoutonValider3 = (Button) this.findViewById(R.id.ButtonValider3_page3);
		BoutonAide = (ImageView) this.findViewById(R.id.IvAide_page3);
		BoutonAide.setOnClickListener(this);

	}

	@Override
	public void ChoisiLeTheme() {

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
	}

}
