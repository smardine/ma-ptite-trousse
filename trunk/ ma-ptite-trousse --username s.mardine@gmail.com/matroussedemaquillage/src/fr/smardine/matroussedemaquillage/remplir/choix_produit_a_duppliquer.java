package fr.smardine.matroussedemaquillage.remplir;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import fr.smardine.matroussedemaquillage.Main;
import fr.smardine.matroussedemaquillage.R;
import fr.smardine.matroussedemaquillage.base.BDAcces;
import fr.smardine.matroussedemaquillage.note.note_page1;
import fr.smardine.matroussedemaquillage.param.tab_param;
import fr.smardine.matroussedemaquillage.recherche.produitRecherche;
import fr.smardine.matroussedemaquillage.recherche.produitRechercheListAdapter;
import fr.smardine.matroussedemaquillage.recherche.produitRechercheListAdapter.ViewHolder;
import fr.smardine.matroussedemaquillage.recherche.recherchetabsbuttons;
import fr.smardine.matroussedemaquillage.variableglobale.EnTheme;

public class choix_produit_a_duppliquer extends Activity implements OnItemClickListener {

	ArrayList<produitRecherche> produitDupplique = new ArrayList<produitRecherche>();
	ArrayList<produitRecherche> produitDuppliqueTitre = new ArrayList<produitRecherche>();
	ListView ProduitListView1, ProduitListViewTitre;

	produitRechercheListAdapter adpt;
	private BDAcces objBd;
	// AlertDialog.Builder adPlusieurCat,adAucuneCat;
	String Txt01, Txt02, Txt03;
	String MarqueChoisie = "";
	String DureeVie = "";
	String DateChoisie = "";
	String numTeinte = "";
	String nomProduitRecup = "";
	int VISIBLE = 1, INVISIBLE = 4, GONE = 8;

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ExceptionHandler.register(this, "http://simon.mardine.free.fr/trousse_maquillage/test/server.php","ma_ptite_trousse");
		choisiLeTheme();

		ProduitListView1 = (ListView) this.findViewById(R.id.produitListViewDupplic);
		ProduitListViewTitre = (ListView) this.findViewById(R.id.produitListViewDupplicTitre);

		ProduitListView1.setOnItemClickListener(this);

		objBd = new BDAcces(this);
		this.setTitle("Choix du produit à duppliquer");

		popUp("OnCreate-pageDupplique");
		AfficheLeContenu("Titre", produitDuppliqueTitre, ProduitListViewTitre);
		AfficheLeContenu("Tout", produitDupplique, ProduitListView1);

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
			setContentView(R.layout.theme_bisounours_affiche_liste_produit_a_duppliquer);

		}
		if (EnTheme.Classique.getLib().equals(nomThemeChoisi)) {
			setContentView(R.layout.affiche_liste_produit_a_duppliquer);

		}
		if (EnTheme.Fleur.getLib().equals(nomThemeChoisi)) {
			setContentView(R.layout.theme_fleur_affiche_liste_produit_a_duppliquer);
		}

		objBd.close();
	}

	private void onCreateMenu(Menu menu) {
		SubMenu recherche = menu.addSubMenu(1, 2000, 1, "Recherche");
		recherche.setIcon(R.drawable.menu_recherche);
		SubMenu note = menu.addSubMenu(2, 2002, 2, "Notes");
		note.setIcon(R.drawable.menu_note);
		SubMenu parametre = menu.addSubMenu(3, 2001, 3, "Parametres");
		parametre.setIcon(R.drawable.menu_param); // icone systeme
		SubMenu help = menu.addSubMenu(4, 2003, 4, "Aide");
		help.setIcon(R.drawable.ad_question);

		menu.setGroupEnabled(1, true);
		menu.setGroupEnabled(2, true);
		menu.setGroupEnabled(3, true);
		menu.setGroupEnabled(4, false);

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
			case 2000:
				Toast.makeText(this, "Recherche", 1000).show();
				Intent intentRecherche = new Intent(this, recherchetabsbuttons.class);
				intentRecherche.putExtra("calledFromMain", true);
				// on demarre la nouvelle activité
				startActivity(intentRecherche);
				finish();
				break;
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
				adHelp.setMessage("Le bouton Remplir vous permet de créer un nouveau produit.\n"
						+ "Le bouton Périmé vous permet d'afficher la liste de vos produits périmés (si il y en a).\n"
						+ "Le bouton Notes vous permettra d'accéder aux notes.\n"
						+ "Le bouton \"Loupe\" de votre téléphone vous donnera accès a la recherche de produit.\n"
						+ "Le bouton \"Menu\" de votre téléphone vous fera apparaitre les options ainsi que des raccourcis.\n"
						+ "Les boutons \"Menu\" et \"Loupe\" de votre téléphone sont actifs dans toutes les fenêtres de l'application.");
				adHelp.setPositiveButton("Ok", null);
				adHelp.show();
		}
		Log.i("", "" + item.getTitle());
		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("rawtypes")
	private void AfficheLeContenu(String TypeRecherche, ArrayList<produitRecherche> produitFinal, ListView produitListView) {

		objBd.open();
		if (TypeRecherche.equals("Titre")) {
			produitDuppliqueTitre.add(new produitRecherche("", "Marque", "Produit", "Catégorie"));
		}
		if (TypeRecherche.equals("Tout")) {

			String[] Colonnes = { "id_produits", "nom_produit", "nom_marque", "nom_souscatergorie" };

			ArrayList[] ListeProduits = objBd.renvoi_liste_TrousseFinal(Colonnes, "id_produits", "", "", null);
			int nbdobjet = ListeProduits[0].size();
			if (nbdobjet != 0) {
				for (int j = 0; j < nbdobjet; j++) {
					String IdProduit = ListeProduits[0].get(j).toString();
					String NomProduits = ListeProduits[1].get(j).toString();
					String NomMarque = ListeProduits[2].get(j).toString();
					String NomSousCat = ListeProduits[3].get(j).toString();
					produitDupplique.add(new produitRecherche(IdProduit, NomMarque, NomProduits, NomSousCat));
				}
			} else {

			}
		}
		objBd.close();

		// animation d'affichage cascade du haut vers le bas
		AnimationSet set = new AnimationSet(true);
		Animation animation = new AlphaAnimation(0.0f, 1.0f);
		animation.setDuration(100);
		set.addAnimation(animation);
		animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
				-1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		animation.setDuration(100);
		set.addAnimation(animation);
		LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);
		produitListView.setLayoutAnimation(controller);

		// paramètrer l'adapteur correspondant
		adpt = new produitRechercheListAdapter(this, produitFinal);
		// paramèter l'adapter sur la listview
		produitListView.setAdapter(adpt);

	}

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

		boolean IsCalledFromMain = getIntent().getBooleanExtra("calledFromMain", false);
		boolean IsCalledFromPageRecap = getIntent().getBooleanExtra("calledFromRecap", false);
		boolean IsCalledFromDetail = getIntent().getBooleanExtra("LaunchByDetail", false);

		if (IsCalledFromMain || IsCalledFromPageRecap) {
			popUp("IscreatFormRecap: " + IsCalledFromPageRecap);
			popUp("IscreatFormMain: " + IsCalledFromMain);
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
		boolean IsCalledFromPage2 = getIntent().getBooleanExtra("LaunchByPage2", false);
		if (IsCalledFromPage2) {
			MarqueChoisie = getIntent().getStringExtra("MarqueChoisie").trim();
			DureeVie = getIntent().getStringExtra("DurreeDeVie").trim();
			DateChoisie = getIntent().getStringExtra("DateAchat").trim();
			numTeinte = getIntent().getStringExtra("NumTeinte").trim();
			nomProduitRecup = getIntent().getStringExtra("NomProduit").trim();
		}
		if (IsCalledFromDetail) {
			MarqueChoisie = getIntent().getStringExtra("MarqueChoisie").trim();
			DureeVie = getIntent().getStringExtra("DurreeDeVie").trim();
			DateChoisie = getIntent().getStringExtra("DateAchat").trim();
			numTeinte = getIntent().getStringExtra("NumTeinte").trim();
			nomProduitRecup = getIntent().getStringExtra("NomProduit").trim();
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
			Main.putExtra("calledFromDupplique", true);
			startActivity(Main);
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

	@Override
	@SuppressWarnings("unused")
	public void onItemClick(AdapterView<?> Parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		int Itemposition = Parent.getSelectedItemPosition();

		int ChildCount = Parent.getChildCount();
		View view1 = Parent.getChildAt(position);

		ViewHolder holder = (ViewHolder) view.getTag();

		Txt01 = (String) holder.text01.getText();
		Txt02 = (String) holder.text02.getText();
		Txt03 = (String) holder.text03.getText();

		final AlertDialog.Builder dial = new AlertDialog.Builder(this);
		dial.setTitle("Que voulez vous faire?");
		dial.setIcon(R.drawable.ad_question);
		dial.setMessage("Confirmez vous la dupplication du produit suivant: " + "" + Txt03);

		dial.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int id) {

				gotoDuppliqueEtLanceFormPage3(Txt01);

			}
		});
		dial.setNegativeButton("Non", null);
		dial.show();
	}

	@SuppressWarnings("rawtypes")
	protected void gotoDuppliqueEtLanceFormPage3(String IdProduit) {
		// TODO Auto-generated method stub
		objBd.open();
		// IdProduit=getIntent().getStringExtra("IDProduit").trim();
		String[] Colonnes = { "nom_produit", "nom_souscatergorie", "nom_categorie", "numero_Teinte", "Duree_Vie", "Date_Peremption",
				"DateAchat", "nom_marque" };

		ArrayList[] trousse_final = objBd.renvoi_liste_TrousseFinalComplete(Colonnes, IdProduit);
		nomProduitRecup = trousse_final[0].toString().replace("[", "").replace("]", "");
		String SousCat = trousse_final[1].toString().replace("[", "").replace("]", "");
		numTeinte = trousse_final[3].toString().replace("[", "").replace("]", "");
		DureeVie = trousse_final[4].toString().replace("[", "").replace("]", "");
		DateChoisie = trousse_final[6].toString().replace("[", "").replace("]", "");
		MarqueChoisie = trousse_final[7].toString().replace("[", "").replace("]", "");

		objBd.close();

		// on rempli "trousse tempo" avec les valeurs de Categories et sous categorie afin que cescase soient cochée lors de l'affichage de
		// la page1 du formulaire
		String Table = "trousse_marques";
		ContentValues modifiedValues = new ContentValues();
		modifiedValues.put("ischecked", "true");
		String whereClause = "nom_marque=?";
		String[] whereArgs = new String[] { MarqueChoisie };
		objBd.open();
		int nbdechamp = objBd.majTable(Table, modifiedValues, whereClause, whereArgs);

		System.out.println("Nombre de champ modifié : " + nbdechamp);
		objBd.close();

		String Table1 = "trousse_produits";
		ContentValues modifiedValues1 = new ContentValues();
		modifiedValues1.put("ischecked", "true");
		String whereClause1 = "nom_souscatergorie=?";
		String[] whereArgs1 = new String[] { SousCat };

		objBd.open();
		int nbdechamp1 = objBd.majTable(Table1, modifiedValues1, whereClause1, whereArgs1);

		System.out.println("Nombre de champ modifié : " + nbdechamp1);
		objBd.close();
		//
		Intent intentPage3Dupplique = new Intent(this, formulaire_entree_page3.class);
		// on demarre la nouvelle activité
		intentPage3Dupplique.putExtra("MarqueChoisie", MarqueChoisie.trim());
		intentPage3Dupplique.putExtra("DurreeDeVie", DureeVie.trim());
		intentPage3Dupplique.putExtra("DateAchat", DateChoisie.trim());
		intentPage3Dupplique.putExtra("NumTeinte", numTeinte.trim());
		intentPage3Dupplique.putExtra("NomProduit", nomProduitRecup.trim());

		intentPage3Dupplique.putExtra("LaunchByDupplique", true);

		startActivity(intentPage3Dupplique);
		finish();

	}

}
