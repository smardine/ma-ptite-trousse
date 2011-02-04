package fr.smardine.matroussedemaquillage.modifier;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import fr.smardine.matroussedemaquillage.R;
import fr.smardine.matroussedemaquillage.base.BDAcces;
import fr.smardine.matroussedemaquillage.recherche.affiche_detail;
import fr.smardine.matroussedemaquillage.recherche.recherchetabsbuttons;
import fr.smardine.matroussedemaquillage.variableglobale.EnCategorieAutres;
import fr.smardine.matroussedemaquillage.variableglobale.EnCategorieLevre;
import fr.smardine.matroussedemaquillage.variableglobale.EnCategorieVisage;
import fr.smardine.matroussedemaquillage.variableglobale.EnCategorieYeux;
import fr.smardine.matroussedemaquillage.variableglobale.EnTheme;

public class modif_cat extends Activity implements OnClickListener {
	// Button BoutonValider;
	ImageView BtVisage, BtYeux, BtLevres, BtAutres;
	// boolean dejacliquéVisage=false,dejacliquéYeux=false,dejacliquéLevres=false,dejacliquéAutres=false;
	// ArrayList<produit> produitsCat1 = new ArrayList<produit>();
	// ArrayList<produit> produitsCat2 = new ArrayList<produit>();
	// ArrayList<produit> produitsCat3 = new ArrayList<produit>();
	// ArrayList<produit> produitsCat4 = new ArrayList<produit>();
	// ListView ProduitListView1,ProduitListView2,ProduitListView3,ProduitListView4;
	// produitListAdapter adpt;
	private BDAcces objBd;
	// AlertDialog.Builder adPlusieurCat,adAucuneCat;
	String MarqueChoisie = "";
	String DureeVie = "";
	String DateChoisie = "";
	String numTeinte = "";
	String nomProduitRecup = "";
	String Id_Produit;
	private ContentValues modifiedValues;
	private String whereClause;
	private String[] whereArgs;

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ExceptionHandler.register(this, "http://simon.mardine.free.fr/trousse_maquillage/test/server.php","ma_ptite_trousse");
		choisiLeTheme();

		// ProduitListView1 = (ListView)this.findViewById(R.id.produitListView01_page1);
		// ProduitListView2 = (ListView) this.findViewById(R.id.produitListView02_page1);
		// ProduitListView3 = (ListView)this.findViewById(R.id.produitListView03_page1);
		// ProduitListView4 = (ListView) this.findViewById(R.id.produitListView04_page1);

		BtVisage = (ImageView) this.findViewById(R.id.ImageViewVisage_page1);
		BtYeux = (ImageView) this.findViewById(R.id.ImageViewYeux_page1);
		BtLevres = (ImageView) findViewById(R.id.ImageViewLevres_page1);
		BtAutres = (ImageView) this.findViewById(R.id.ImageViewAutres_page1);
		// BoutonValider = (Button)this.findViewById(R.id.ButtonValider_page1);

		BtVisage.setOnClickListener(this);
		BtYeux.setOnClickListener(this);
		BtLevres.setOnClickListener(this);
		BtAutres.setOnClickListener(this);
		// BoutonValider.setOnClickListener(this);
		//
		// adPlusieurCat = new AlertDialog.Builder(this);
		// adPlusieurCat.setTitle("Attention");
		// adPlusieurCat.setIcon(R.drawable.ad_attention);
		// adPlusieurCat.setMessage("Vous avez séléctionné plus d'une categorie \n" +
		// "Veuillez n'en choisir qu'une.");
		// adPlusieurCat.setPositiveButton("Ok",null);
		//
		//
		// adAucuneCat = new AlertDialog.Builder(this);
		// adAucuneCat.setTitle("Attention");
		// adAucuneCat.setIcon(R.drawable.ad_attention);
		// adAucuneCat.setMessage("Vous n'avez selectionné aucune categorie. \n" +
		// "Merci d'en choisir au moins une.");
		// adAucuneCat.setPositiveButton("Ok",null);
		objBd = new BDAcces(this);
		this.setTitle("Choix de la catégorie");

		popUp("OnCreate-page1");

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

	@Override
	public void onClick(View v) {

		String Table = "trousse_produits";
		modifiedValues = new ContentValues();
		modifiedValues.put("ischecked", "false");
		whereClause = "ischecked=?";
		whereArgs = new String[] { "true" };

		objBd.open();
		objBd.majTable(Table, modifiedValues, whereClause, whereArgs);
		objBd.close();

		if (v == BtVisage) {// si le bouton cliqué est le "BoutonVisage"
			String[] NomProduits = recupereSousCategorie("Visage");
			AlertDialog.Builder adChoixVisage = new AlertDialog.Builder(this);
			adChoixVisage.setTitle("Visage");
			adChoixVisage.setSingleChoiceItems(NomProduits, -1, new DialogInterface.OnClickListener() {
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
					majTableEtLancePage2();
				}
			});
			adChoixVisage.setNegativeButton("Annuler", null);
			adChoixVisage.show();

		}
		if (v == BtYeux) {

			String[] NomProduits = recupereSousCategorie("Yeux");
			AlertDialog.Builder adChoixYeux = new AlertDialog.Builder(this);
			adChoixYeux.setTitle("Yeux");
			adChoixYeux.setSingleChoiceItems(NomProduits, -1, new DialogInterface.OnClickListener() {
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
					majTableEtLancePage2();
				}
			});
			adChoixYeux.setNegativeButton("Annuler", null);
			adChoixYeux.show();

		}
		if (v == BtLevres) {
			String[] NomProduits = recupereSousCategorie("Levres");
			AlertDialog.Builder adChoixLevre = new AlertDialog.Builder(this);
			adChoixLevre.setTitle("Levres");
			adChoixLevre.setSingleChoiceItems(NomProduits, -1, new DialogInterface.OnClickListener() {
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
					majTableEtLancePage2();
				}
			});
			adChoixLevre.setNegativeButton("Annuler", null);
			adChoixLevre.show();

		}
		if (v == BtAutres) {
			String[] NomProduits = recupereSousCategorie("Autres");
			AlertDialog.Builder adChoixAutres = new AlertDialog.Builder(this);
			adChoixAutres.setTitle("Autres");
			adChoixAutres.setSingleChoiceItems(NomProduits, -1, new DialogInterface.OnClickListener() {
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
					majTableEtLancePage2();
				}
			});
			adChoixAutres.setNegativeButton("Annuler", null);
			adChoixAutres.show();

		}

	}

	/**
	 * @param p_categorie TODO
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

	protected void majTableEtLancePage2() {

		/**
		 * @throws SQLException
		 */

		objBd.open();
		int nbdechamp = objBd.majTable("trousse_produits", modifiedValues, whereClause, whereArgs);
		System.out.println("Nombre de champ modifié : " + nbdechamp);
		objBd.deleteTable("trousse_tempo", "1", null);
		objBd.close();

		// popUp("On Continue");
		// on créer une nouvelle activité avec comme point de depart "Main" et comme destination "FicheClient"
		Intent intent = new Intent(modif_cat.this, affiche_detail.class);
		// on demarre la nouvelle activité
		objBd = new BDAcces(this);
		objBd.open();
		@SuppressWarnings("rawtypes")
		ArrayList[] Categorie_Cochée = objBd.renvoiCategorieEtProduitCochée();
		String SousCat = Categorie_Cochée[0].toString().replace("[", "").replace("]", "");
		String Cat = Categorie_Cochée[1].toString().replace("[", "").replace("]", "");

		intent.putExtra("IDProduit", Id_Produit);
		intent.putExtra("LaunchByModifCat", true);

		majTableProduit(Integer.parseInt(Id_Produit), SousCat, Cat);
		remetAZeroLaTableCat();
		objBd.close();
		startActivity(intent);
		finish();

	}

	private void majTableProduit(int Id_Produits, String souscat, String cat) {
		// TODO Auto-generated method stub
		String Table = "produit_Enregistre";
		ContentValues modifiedValues = new ContentValues();
		modifiedValues.put("nom_souscatergorie", souscat);
		modifiedValues.put("nom_categorie", cat);
		String whereClause = "id_produits=?";
		String[] whereArgs = new String[] { "" + Id_Produits + "" };
		objBd = new BDAcces(this);
		objBd.open();
		int nbdechamp = objBd.majTable(Table, modifiedValues, whereClause, whereArgs);
		// objBd.deleteTable("trousse_tempo","1",null);
		System.out.println("Nombre de champ modifie dans la table produit_Enregistre : " + nbdechamp + " sur l'id n° " + Id_Produits
				+ "nom cat=" + cat + "nom sous cat" + souscat);
		objBd.close();
	}

	private void remetAZeroLaTableCat() {
		// TODO Auto-generated method stub
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

	// @SuppressWarnings("rawtypes")
	// private boolean VerfieAuMoinsUneCategorieSelectionnée() {
	//
	// objBd.open();
	// ArrayList[] ListeCategorieCochée = objBd.renvoiCategorieCochée();
	// int nbCategorieCochées = ListeCategorieCochée[0].size();
	// String NomProduits="";
	// for (int j=0;j<nbCategorieCochées;j++){
	// NomProduits = ListeCategorieCochée[0].get(j).toString();
	// }
	//
	// if ((nbCategorieCochées==1) && (NomProduits.equals("aucun"))){
	// //popUp ("Vous n'avez selectionné aucune catégorie");
	// objBd.close();
	// return false;
	// }
	// else{
	// //popUp("On Continue");
	// objBd.close();
	// return true;
	// }
	//
	//
	// }
	// @SuppressWarnings("rawtypes")
	// private int NombreDeCategorieSelectionnée() {
	//
	// objBd.open();
	// ArrayList[] ListeCategorieCochée = objBd.renvoiCategorieCochée();
	// int nbCategorieCochées = ListeCategorieCochée[0].size();
	//
	// objBd.close();
	// return nbCategorieCochées;
	//
	//
	// }
	//
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

		boolean IsCalledFromDetail = getIntent().getBooleanExtra("LaunchByDetail", false);

		if (IsCalledFromDetail) {
			Id_Produit = getIntent().getStringExtra("ID_Produit").trim();
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

			Intent affiche_detail = new Intent(this, affiche_detail.class);
			affiche_detail.putExtra("IDProduit", Id_Produit);
			startActivity(affiche_detail);
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
		popUp("OnDestroy-Page1");
		super.onDestroy();

	}

}
