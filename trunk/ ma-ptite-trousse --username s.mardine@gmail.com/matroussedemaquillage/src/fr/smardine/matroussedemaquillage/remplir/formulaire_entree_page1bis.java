package fr.smardine.matroussedemaquillage.remplir;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.apis.animation.Animlineaire;

import fr.smardine.matroussedemaquillage.Main;
import fr.smardine.matroussedemaquillage.R;
import fr.smardine.matroussedemaquillage.base.BDAcces;
import fr.smardine.matroussedemaquillage.note.note_page1;
import fr.smardine.matroussedemaquillage.param.tab_param;
import fr.smardine.matroussedemaquillage.recherche.Recherche;
import fr.smardine.matroussedemaquillage.variableglobale.ActivityParam;
import fr.smardine.matroussedemaquillage.variableglobale.EnCategorieAutres;
import fr.smardine.matroussedemaquillage.variableglobale.EnCategorieLevre;
import fr.smardine.matroussedemaquillage.variableglobale.EnCategorieVisage;
import fr.smardine.matroussedemaquillage.variableglobale.EnCategorieYeux;
import fr.smardine.matroussedemaquillage.variableglobale.EnTheme;

public class formulaire_entree_page1bis extends Activity implements OnClickListener {

	Button BoutonValider;
	ImageView BtVisage, BtYeux, BtLevres, BtAutres;
	AutoCompleteTextView textView;

	private BDAcces objBd;
	AlertDialog.Builder adPlusieurCat, adAucuneCat, adNouvelleMarque, adAucuneMarque;;
	String MarqueChoisie = "";
	String DureeVie = "";
	String DateChoisie = "";
	String numTeinte = "";
	String nomProduitRecup = "";
	Intent intentRecherche, intentParametres, intentNote;
	String[] Marque;
	private ContentValues modifiedValues;
	private String whereClause;
	private String[] whereArgs;

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ExceptionHandler.register(this, "http://simon.mardine.free.fr/trousse_maquillage/test/server.php","ma_ptite_trousse");
		choisiLeTheme();

		BtVisage = (ImageView) this.findViewById(R.id.ImageViewVisage_page1);
		BtYeux = (ImageView) this.findViewById(R.id.ImageViewYeux_page1);
		BtLevres = (ImageView) findViewById(R.id.ImageViewLevres_page1);
		BtAutres = (ImageView) this.findViewById(R.id.ImageViewAutres_page1);
		BoutonValider = (Button) this.findViewById(R.id.ButtonValider2_Page1);
		textView = (AutoCompleteTextView) findViewById(R.id.autocomplete_marque_Page1);

		BtVisage.setOnClickListener(this);
		BtYeux.setOnClickListener(this);
		BtLevres.setOnClickListener(this);
		BtAutres.setOnClickListener(this);
		BoutonValider.setOnClickListener(this);

		adPlusieurCat = new AlertDialog.Builder(this);
		adPlusieurCat.setTitle("Attention");
		adPlusieurCat.setIcon(R.drawable.ad_attention);
		adPlusieurCat.setMessage("Vous avez séléctionné plus d'une categorie \n" + "Veuillez n'en choisir qu'une.");
		adPlusieurCat.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface p_dialog, int p_which) {
				String Table = "trousse_produits";
				modifiedValues = new ContentValues();
				modifiedValues.put("ischecked", "false");
				whereClause = "ischecked=?";
				whereArgs = new String[] { "true" };

				objBd.open();
				objBd.majTable(Table, modifiedValues, whereClause, whereArgs);
				objBd.close();

			}
		});

		adAucuneCat = new AlertDialog.Builder(this);
		adAucuneCat.setTitle("Attention");
		adAucuneCat.setIcon(R.drawable.ad_attention);
		adAucuneCat.setMessage("Vous n'avez selectionné aucune categorie. \n" + "Merci d'en choisir au moins une.");
		adAucuneCat.setPositiveButton("Ok", null);

		adAucuneMarque = new AlertDialog.Builder(this);
		adAucuneMarque.setTitle("Attention");
		adAucuneMarque.setIcon(R.drawable.ad_attention);
		adAucuneMarque.setMessage("Vous n'avez rentré aucune marque \nMerci d'en saisir une");
		adAucuneMarque.setPositiveButton("Ok", null);

		adNouvelleMarque = new AlertDialog.Builder(this);
		adNouvelleMarque.setIcon(R.drawable.ad_attention);
		adNouvelleMarque.setTitle("Petite vérification");
		adNouvelleMarque
				.setMessage("Nouvelle marque\nCette marque est inconnue de \"Ma p'tite trousse\"\nSouhaitez vous la partager avec les autres utilisateurs? (Connexion Edge, 3G ou wifi requise)");
		adNouvelleMarque.setPositiveButton("Oui", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				// popUp("Ok, c'est bon, l'utilisateur confirme");
				PostMarqueSurServeur(textView.getText().toString());

			}
		});
		adNouvelleMarque.setNegativeButton("Non", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		// objBd = new BDAcces(this);
		this.setTitle("Choix de la catégorie et de la marque");

		popUp("OnCreate-page1");

		objBd = new BDAcces(this);
		objBd.open();

		Marque = objBd.renvoi_liste_ValeurDansString("trousse_marques", "nom_marque");

		objBd.close();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_marque_auto, Marque);
		textView.setAdapter(adapter);

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
			setContentView(R.layout.theme_bisounours_formulaire_entree_page1bis);

		}
		if (EnTheme.Classique.getLib().equals(nomThemeChoisi)) {
			setContentView(R.layout.formulaire_entree_page1bis);

		}
		if (EnTheme.Fleur.getLib().equals(nomThemeChoisi)) {
			setContentView(R.layout.theme_fleur_formulaire_entree_page1bis);
		}

		objBd.close();
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
			// l'identifiant integer est moins gourmand en ressource que le string
			case 2000:
				Toast.makeText(this, "Recherche", 1000).show();
				intentRecherche = new Intent(this, Recherche.class);
				intentRecherche.putExtra(ActivityParam.LaunchFromPage1, true);
				// on demarre la nouvelle activité
				startActivity(intentRecherche);
				finish();
				break;
			case 2001:
				Toast.makeText(this, "Paramètres", 1000).show();
				intentParametres = new Intent(this, tab_param.class);
				intentParametres.putExtra(ActivityParam.LaunchFromPage1, true);
				// on demarre la nouvelle activité
				startActivity(intentParametres);
				finish();
				break;
			case 2002:
				Toast.makeText(this, "Notes", 1000).show();
				intentNote = new Intent(this, note_page1.class);
				// on demarre la nouvelle activité
				startActivity(intentNote);
				finish();
				break;

		}
		Log.i("", "" + item.getTitle());
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {

		if (v == BtVisage) {// si le bouton cliqué est le "BoutonVisage"
			String[] NomProduits = recupereSousCategorie("Visage");
			int idProdCoche = recupereIndiceSousCategorieCochee("Visage");
			AlertDialog.Builder adChoixVisage = new AlertDialog.Builder(this);
			adChoixVisage.setTitle("Visage");
			adChoixVisage.setSingleChoiceItems(NomProduits, idProdCoche, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int item) {
					/* User clicked on a radio button do some stuff */

					if (item == EnCategorieVisage.FONDS_DE_TEINTS.getCode()) {
						modifiedValues = new ContentValues();
						modifiedValues.put("ischecked", "true");
						whereClause = "nom_souscatergorie=?";
						whereArgs = new String[] { EnCategorieVisage.FONDS_DE_TEINTS.getLib() };
					}
					if (item == EnCategorieVisage.Correcteurs_Bases.getCode()) {
						modifiedValues = new ContentValues();
						modifiedValues.put("ischecked", "true");
						whereClause = "nom_souscatergorie=?";
						whereArgs = new String[] { EnCategorieVisage.Correcteurs_Bases.getLib() };
					}
					if (item == EnCategorieVisage.Blush.getCode()) {
						modifiedValues = new ContentValues();
						modifiedValues.put("ischecked", "true");
						whereClause = "nom_souscatergorie=?";
						whereArgs = new String[] { EnCategorieVisage.Blush.getLib() };
					}
					if (item == EnCategorieVisage.Poudres.getCode()) {
						modifiedValues = new ContentValues();
						modifiedValues.put("ischecked", "true");
						whereClause = "nom_souscatergorie=?";
						whereArgs = new String[] { EnCategorieVisage.Poudres.getLib() };
					}
				}
			});
			adChoixVisage.setPositiveButton("Choisir", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int id) {
					majTable();
				}
			});
			adChoixVisage.setNegativeButton("Annuler", null);
			adChoixVisage.show();

		}
		if (v == BtYeux) {

			String[] NomProduits = recupereSousCategorie("Yeux");
			int idProdCoche = recupereIndiceSousCategorieCochee("Yeux");
			AlertDialog.Builder adChoixYeux = new AlertDialog.Builder(this);
			adChoixYeux.setTitle("Yeux");
			adChoixYeux.setSingleChoiceItems(NomProduits, idProdCoche, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int item) {
					/* User clicked on a radio button do some stuff */

					if (item == EnCategorieYeux.Bases.getCode()) {
						modifiedValues = new ContentValues();
						modifiedValues.put("ischecked", "true");
						whereClause = "nom_souscatergorie=?";
						whereArgs = new String[] { EnCategorieYeux.Bases.getLib() };
					}
					if (item == EnCategorieYeux.Crayons_Eyeliners.getCode()) {
						modifiedValues = new ContentValues();
						modifiedValues.put("ischecked", "true");
						whereClause = "nom_souscatergorie=?";
						whereArgs = new String[] { EnCategorieYeux.Crayons_Eyeliners.getLib() };
					}
					if (item == EnCategorieYeux.Fards.getCode()) {
						modifiedValues = new ContentValues();
						modifiedValues.put("ischecked", "true");
						whereClause = "nom_souscatergorie=?";
						whereArgs = new String[] { EnCategorieYeux.Fards.getLib() };
					}
					if (item == EnCategorieYeux.Mascaras.getCode()) {
						modifiedValues = new ContentValues();
						modifiedValues.put("ischecked", "true");
						whereClause = "nom_souscatergorie=?";
						whereArgs = new String[] { EnCategorieYeux.Mascaras.getLib() };
					}
				}
			});
			adChoixYeux.setPositiveButton("Choisir", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int id) {
					majTable();
				}
			});
			adChoixYeux.setNegativeButton("Annuler", null);
			adChoixYeux.show();

		}
		if (v == BtLevres) {
			String[] NomProduits = recupereSousCategorie("Levres");
			int idProdCoche = recupereIndiceSousCategorieCochee("Levres");
			AlertDialog.Builder adChoixLevre = new AlertDialog.Builder(this);
			adChoixLevre.setTitle("Levres");
			adChoixLevre.setSingleChoiceItems(NomProduits, idProdCoche, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int item) {
					/* User clicked on a radio button do some stuff */

					if (item == EnCategorieLevre.Crayons_contour.getCode()) {
						modifiedValues = new ContentValues();
						modifiedValues.put("ischecked", "true");
						whereClause = "nom_souscatergorie=?";
						whereArgs = new String[] { EnCategorieLevre.Crayons_contour.getLib() };
					}
					if (item == EnCategorieLevre.RougesAlevres.getCode()) {
						modifiedValues = new ContentValues();
						modifiedValues.put("ischecked", "true");
						whereClause = "nom_souscatergorie=?";
						whereArgs = new String[] { EnCategorieLevre.RougesAlevres.getLib() };
					}

				}
			});
			adChoixLevre.setPositiveButton("Choisir", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int id) {
					majTable();
				}
			});
			adChoixLevre.setNegativeButton("Annuler", null);
			adChoixLevre.show();

		}
		if (v == BtAutres) {
			String[] NomProduits = recupereSousCategorie("Autres");
			int idProdCoche = recupereIndiceSousCategorieCochee("Autres");
			AlertDialog.Builder adChoixAutres = new AlertDialog.Builder(this);
			adChoixAutres.setTitle("Autres");
			adChoixAutres.setSingleChoiceItems(NomProduits, idProdCoche, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int item) {
					/* User clicked on a radio button do some stuff */

					if (item == EnCategorieAutres.Pinceaux.getCode()) {
						modifiedValues = new ContentValues();
						modifiedValues.put("ischecked", "true");
						whereClause = "nom_souscatergorie=?";
						whereArgs = new String[] { EnCategorieAutres.Pinceaux.getLib() };
					}
					if (item == EnCategorieAutres.VernisAongles.getCode()) {
						modifiedValues = new ContentValues();
						modifiedValues.put("ischecked", "true");
						whereClause = "nom_souscatergorie=?";
						whereArgs = new String[] { EnCategorieAutres.VernisAongles.getLib() };
					}

				}
			});
			adChoixAutres.setPositiveButton("Choisir", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int id) {
					majTable();
				}
			});
			adChoixAutres.setNegativeButton("Annuler", null);
			adChoixAutres.show();

		}
		if (v == textView) {
			textView.setFocusable(true);
		}
		if (v == BoutonValider) {
			// majTable();
			// on recupere le nom de marque choisi par l'utilisateur
			boolean isMarqueEnbase = verifMarqueEnBase();
			// on verifie qu'au moins une categorie est cochée.
			boolean isOk = VerfieAuMoinsUneCategorieSelectionnée();
			int nbCatCochee = NombreDeCategorieSelectionnée();
			if (!isOk || nbCatCochee > 1 || !isMarqueEnbase) {
				if (!isOk) {
					adAucuneCat.show();
				}
				if (nbCatCochee > 1) {
					adPlusieurCat.show();
				}
				if (!isMarqueEnbase) {
					if (MarqueChoisie.equals("")) {
						adAucuneMarque.show();
					} else {
						ContentValues values = new ContentValues();
						values.put("nom_marque", MarqueChoisie);
						values.put("ischecked", "false");

						objBd.open();
						objBd.InsertDonnéedansTable("trousse_marques", values);
						objBd.close();
						adNouvelleMarque.show();
					}

				}

			} else {
				Intent intent = new Intent(formulaire_entree_page1bis.this, formulaire_entree_page3.class);
				intent.putExtra(ActivityParam.Marque, MarqueChoisie.trim());
				intent.putExtra(ActivityParam.DurreeDeVie, DureeVie.trim());
				intent.putExtra(ActivityParam.DateAchat, DateChoisie.trim());
				intent.putExtra(ActivityParam.NumeroDeTeinte, numTeinte.trim());
				intent.putExtra(ActivityParam.NomProduit, nomProduitRecup.trim());
				intent.putExtra(ActivityParam.LaunchFromPage1, true);
				startActivity(intent);
				finish();
			}

		}

	}

	/**
 * 
 */
	private boolean verifMarqueEnBase() {
		MarqueChoisie = textView.getText().toString();
		boolean MarqueenBase = false;
		objBd.open();
		Marque = objBd.renvoi_liste_ValeurDansString("trousse_marques", "nom_marque");
		objBd.close();
		if (!MarqueChoisie.equals("")) {
			// on le compare à la liste des marques enregistrée en base
			for (int i = 0; i < Marque.length; i++) {
				if (Marque[i].contains(MarqueChoisie)) {
					MarqueenBase = true;
				}
			}
		} else {
			if (MarqueChoisie.equals("")) {
				return false;
			}

		}
		return MarqueenBase;
	}

	/**
	 * @throws SQLException
	 */
	public void majTable() throws SQLException {
		objBd.open();
		int nbdechamp = objBd.majTable("trousse_produits", modifiedValues, whereClause, whereArgs);
		System.out.println("Nombre de champ modifié : " + nbdechamp);
		objBd.deleteTable("trousse_tempo", "1", null);
		objBd.close();

	}

	/**
	 * @param p_categorie la categorie recherchée (Visage,Yeux,Levres...)
	 * @return
	 */
	private String[] recupereSousCategorie(String p_categorie) {
		objBd.open();
		@SuppressWarnings("rawtypes")
		ArrayList[] ListeProduits = objBd.renvoi_liste_produits(p_categorie);
		String[] NomProduits = new String[ListeProduits[0].size()];
		for (int j = 0; j < ListeProduits[0].size(); j++) {
			NomProduits[j] = ListeProduits[0].get(j).toString();
		}
		objBd.close();
		return NomProduits;
	}

	private int recupereIndiceSousCategorieCochee(String p_categorie) {
		int indiceProduitCoche = -1;
		objBd.open();

		@SuppressWarnings("rawtypes")
		ArrayList[] ListeProduits = objBd.renvoi_liste_produits(p_categorie);
		String[] NomProduits = new String[ListeProduits[0].size()];
		for (int j = 0; j < ListeProduits[0].size(); j++) {
			NomProduits[j] = ListeProduits[0].get(j).toString();
			String isChecked = ListeProduits[1].get(j).toString();
			if ("true".equals(isChecked)) {
				indiceProduitCoche = j;
			}
		}
		objBd.close();
		return indiceProduitCoche;
	}

	@SuppressWarnings("rawtypes")
	private boolean VerfieAuMoinsUneCategorieSelectionnée() {

		objBd.open();
		ArrayList[] ListeCategorieCochée = objBd.renvoiCategorieCochée();
		int nbCategorieCochées = ListeCategorieCochée[0].size();
		String NomProduits = "";
		for (int j = 0; j < nbCategorieCochées; j++) {
			NomProduits = ListeCategorieCochée[0].get(j).toString();
		}

		if ((nbCategorieCochées == 1) && (NomProduits.equals("aucun"))) {
			// popUp ("Vous n'avez selectionné aucune catégorie");
			objBd.close();
			return false;
		} else {
			// popUp("On Continue");
			objBd.close();
			return true;
		}

	}

	@SuppressWarnings("rawtypes")
	private int NombreDeCategorieSelectionnée() {

		objBd.open();
		ArrayList[] ListeCategorieCochée = objBd.renvoiCategorieCochée();
		int nbCategorieCochées = ListeCategorieCochée[0].size();

		objBd.close();
		return nbCategorieCochées;

	}

	// private void AfficheLeContenu(String Catégorie,ArrayList<produit> produits,
	// ListView produitListView ) {
	//
	// if (!Catégorie.equals("")){
	// objBd.open();
	// @SuppressWarnings("rawtypes")
	// ArrayList[] ListeProduits = objBd.renvoi_liste_produits(Catégorie);
	// int nbdobjet = ListeProduits[0].size();
	// for (int j=0;j<nbdobjet;j++){
	// String NomProduits = ListeProduits[0].get(j).toString();
	// String IsChecked = ListeProduits[1].get(j).toString();
	// produits.add (new produit (NomProduits,IsChecked));
	// }
	// objBd.close();
	// }
	//
	//
	// //animation d'affichage cascade du haut vers le bas
	// AnimationSet set = new AnimationSet(true);
	// Animation animation = new AlphaAnimation(0.0f, 1.0f);
	// animation.setDuration(100);
	// set.addAnimation(animation);
	// animation = new TranslateAnimation(
	// Animation.RELATIVE_TO_SELF, 0.0f,Animation.RELATIVE_TO_SELF, 0.0f,
	// Animation.RELATIVE_TO_SELF, -1.0f,Animation.RELATIVE_TO_SELF, 0.0f
	// );
	// animation.setDuration(100);
	// set.addAnimation(animation);
	// LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);
	// produitListView.setLayoutAnimation(controller);
	//
	// //paramètrer l'adapteur correspondant
	// adpt = new produitListAdapter(this, produits);
	// //paramèter l'adapter sur la listview
	// produitListView.setAdapter(adpt);
	//
	// }
	public void popUp(String message) {
		// Toast.makeText(this, message, 1).show();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		// popUp("onRestart()-Page1");
	}

	/**
	 * Exécuté lorsque l'activité devient visible à l'utilisateur. La fonction onStart() est suivie de la fonction onResume().
	 */
	@Override
	protected void onStart() {
		super.onStart();
		// popUp("onStart()-Page1");
	}

	/**
	 * Exécutée a chaque passage en premier plan de l'activité. Ou bien, si l'activité passe à nouveau en premier (si une autre activité
	 * était passé en premier plan entre temps). La fonction onResume() est suivie de l'exécution de l'activité.
	 */
	@Override
	protected void onResume() {
		super.onResume();

		boolean isCalledFromMain = getIntent().getBooleanExtra(ActivityParam.LaunchFromMain, false);
		boolean isCalledFromPageRecap = getIntent().getBooleanExtra(ActivityParam.LaunchFromPageRecap, false);
		boolean isCalledFromDetail = getIntent().getBooleanExtra(ActivityParam.LaunchFromAfficheDetail, false);
		boolean isCalledFromDupplique = getIntent().getBooleanExtra(ActivityParam.LaunchFromDuppliquer, false);

		if (isCalledFromMain || isCalledFromPageRecap) {
			// popUp("IscreatFormRecap: " + isCalledFromPageRecap);
			// popUp("IscreatFormMain: " + isCalledFromMain);
			Animlineaire anim = new Animlineaire();
			anim.setDroiteversGauche(500);
			Animlineaire anim1 = new Animlineaire();
			anim1.setDroiteversGauche(550);
			Animlineaire anim2 = new Animlineaire();
			anim2.setDroiteversGauche(600);
			Animlineaire anim3 = new Animlineaire();
			anim3.setDroiteversGauche(650);

			BtVisage.startAnimation(anim);
			BtYeux.startAnimation(anim1);
			BtLevres.startAnimation(anim2);
			BtAutres.startAnimation(anim3);

			String Table = "trousse_produits";
			ContentValues modifiedValues = new ContentValues();
			modifiedValues.put("ischecked", "false");
			String whereClause = "ischecked=?";
			String[] whereArgs = new String[] { "true" };
			objBd = new BDAcces(this);
			objBd.open();
			int nbdechamp = objBd.majTable(Table, modifiedValues, whereClause, whereArgs);
			objBd.deleteTable("trousse_tempo", "1", null);
			System.out.println("Nombre de champ modifié : " + nbdechamp);
			objBd.close();

		}

		if (isCalledFromDetail) {
			MarqueChoisie = getIntent().getStringExtra(ActivityParam.Marque).trim();
			DureeVie = getIntent().getStringExtra(ActivityParam.DurreeDeVie).trim();
			DateChoisie = getIntent().getStringExtra(ActivityParam.DateAchat).trim();
			numTeinte = getIntent().getStringExtra(ActivityParam.NumeroDeTeinte).trim();
			nomProduitRecup = getIntent().getStringExtra(ActivityParam.NomProduit).trim();
			Animlineaire anim = new Animlineaire();
			anim.setDroiteversGauche(250);
			Animlineaire anim1 = new Animlineaire();
			anim1.setDroiteversGauche(300);
			Animlineaire anim2 = new Animlineaire();
			anim2.setDroiteversGauche(350);
			Animlineaire anim3 = new Animlineaire();
			anim3.setDroiteversGauche(400);

			BtVisage.startAnimation(anim);
			BtYeux.startAnimation(anim1);
			BtLevres.startAnimation(anim2);
			BtAutres.startAnimation(anim3);
		}
		if (isCalledFromDupplique) {
			MarqueChoisie = getIntent().getStringExtra(ActivityParam.Marque).trim();
			DureeVie = getIntent().getStringExtra(ActivityParam.DurreeDeVie).trim();
			DateChoisie = getIntent().getStringExtra(ActivityParam.DateAchat).trim();
			numTeinte = getIntent().getStringExtra(ActivityParam.NumeroDeTeinte).trim();
			nomProduitRecup = getIntent().getStringExtra(ActivityParam.NomProduit).trim();
			Animlineaire anim = new Animlineaire();
			anim.setDroiteversGauche(250);
			Animlineaire anim1 = new Animlineaire();
			anim1.setDroiteversGauche(300);
			Animlineaire anim2 = new Animlineaire();
			anim2.setDroiteversGauche(350);
			Animlineaire anim3 = new Animlineaire();
			anim3.setDroiteversGauche(400);

			BtVisage.startAnimation(anim);
			BtYeux.startAnimation(anim1);
			BtLevres.startAnimation(anim2);
			BtAutres.startAnimation(anim3);
		}

	}

	/**
	 * La fonction onStop() est exécutée : - lorsque l'activité n'est plus en premier plan - ou bien lorsque l'activité va être détruite
	 * Cette fonction est suivie : - de la fonction onRestart() si l'activité passe à nouveau en premier plan - de la fonction onDestroy()
	 * lorsque l'activité se termine ou bien lorsque le système décide de l'arrêter
	 */
	@Override
	protected void onStop() {
		super.onStop();
		popUp("onStop-Page1");
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

			Intent Main = new Intent(this, Main.class);
			Main.putExtra(ActivityParam.LaunchFromPage1, true);
			startActivity(Main);
			finish();
			return true;
		}
		if (keyCode == KeyEvent.KEYCODE_SEARCH) {
			Intent intentRecherche = new Intent(this, Recherche.class);
			// on demarre la nouvelle activité
			startActivity(intentRecherche);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	public void OnDestroy() {
		popUp("OnDestroy-Page1");
		super.onDestroy();

	}

	protected void PostMarqueSurServeur(String Marque) {
		String TAG = "fr.smardine.matroussedemaquillage.remplir";
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost("http://simon.mardine.free.fr/trousse_maquillage/nouveautes/postmarque.php");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();

		nvps.add(new BasicNameValuePair("marque", Marque));
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		} catch (UnsupportedEncodingException e) {

			Log.d(TAG, "UnsupportedEncodingException: " + e);
			// e.printStackTrace();
		}

		// We don't care about the response, so we just hope it went well and on with it
		HttpResponse response = null;
		try {
			response = httpClient.execute(httpPost);
		} catch (ClientProtocolException e) {

			Log.d(TAG, "ClientProtocolException: " + e);
			// e.printStackTrace();
		} catch (IOException e) {

			Log.d(TAG, "IOException: " + e);
			// e.printStackTrace();
		}
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		} catch (IllegalStateException e1) {

			Log.d(TAG, "IllegalStateException: " + e1);
		} catch (IOException e1) {

			Log.d(TAG, "IOException: " + e1);
			// e1.printStackTrace();
		}
		String strLine;
		try {
			while ((strLine = reader.readLine()) != null) {
				Log.d(TAG, "reponse du post : " + strLine);
			}
		} catch (IOException e) {

			Log.d(TAG, "IOException: " + e);
			// e.printStackTrace();
		}
	}

}
