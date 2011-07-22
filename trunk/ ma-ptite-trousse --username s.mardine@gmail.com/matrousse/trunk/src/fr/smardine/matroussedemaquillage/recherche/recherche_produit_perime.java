package fr.smardine.matroussedemaquillage.recherche;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
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
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;
import fr.smardine.matroussedemaquillage.Main;
import fr.smardine.matroussedemaquillage.R;
import fr.smardine.matroussedemaquillage.base.BDAcces;
import fr.smardine.matroussedemaquillage.note.note_page1;
import fr.smardine.matroussedemaquillage.param.tab_param;
import fr.smardine.matroussedemaquillage.recherche.produitRechercheListAdapter.ViewHolder;
import fr.smardine.matroussedemaquillage.variableglobale.ActivityParam;
import fr.smardine.matroussedemaquillage.variableglobale.EnTheme;

public class recherche_produit_perime extends Activity implements OnClickListener, OnItemClickListener, OnItemLongClickListener {
	ToggleButton Cat, Marque, Tout;
	EditText EtFiltrage;
	ArrayList<produitRecherche> produitRecherche = new ArrayList<produitRecherche>();
	ArrayList<produitRecherche> produitRechercheTitre = new ArrayList<produitRecherche>();
	int VISIBLE = 1, INVISIBLE = 4, GONE = 8;
	ListView ProduitListView1, ProduitListViewTitre;
	produitRechercheListAdapter adpt;
	BDAcces objBd;
	AlertDialog.Builder adAucunProduit;
	Context ctx = recherche_produit_perime.this;
	// TextView RechercheTxt1;
	String MarqueChoisie;
	String DureeVie;
	String DateChoisie;
	String numTeinte;
	String nomProduitRecup;
	String Txt01, Txt02, Txt03;
	String TypeDeRecherche;
	protected boolean detail;
	protected boolean newNote;
	boolean IsCalledFromMain;

	@Override
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ExceptionHandler.register(this, "http://simon.mardine.free.fr/trousse_maquillage/test/server.php");

		IsCalledFromMain = getIntent().getBooleanExtra(ActivityParam.LaunchFromMain, false);

		objBd = new BDAcces(this);

		ChoisiLeTheme();

		// RechercheTxt1 = (TextView) this.findViewById(R.id.Text1Rech);
		ProduitListView1 = (ListView) this.findViewById(R.id.produitListViewRecherche);
		ProduitListViewTitre = (ListView) this.findViewById(R.id.produitListViewRechercheTitre);

		Cat = (ToggleButton) findViewById(R.id.BTcat);
		Marque = (ToggleButton) findViewById(R.id.BTmarque);
		Tout = (ToggleButton) findViewById(R.id.BTtout);
		EtFiltrage = (EditText) findViewById(R.id.EtFiltrage);

		ProduitListView1.setOnItemClickListener(this);
		ProduitListView1.setOnItemLongClickListener(this);
		Cat.setOnClickListener(this);
		Marque.setOnClickListener(this);
		Tout.setOnClickListener(this);

		adAucunProduit = new AlertDialog.Builder(this);
		adAucunProduit.setTitle("Pour information");
		adAucunProduit.setMessage("Aucun de vos produit n'est périmé actuellement");
		adAucunProduit.setPositiveButton("Ok", null);

		EtFiltrage.addTextChangedListener(new TextWatcher() {
			@SuppressWarnings("unused")
			int len = 0;

			@Override
			public void afterTextChanged(Editable s) {
				String str = EtFiltrage.getText().toString();
				filtreSelonSaisieEtBtActive(str, Cat.isChecked(), Marque.isChecked(), Tout.isChecked());
			}

			@Override
			public void beforeTextChanged(CharSequence p_arg0, int p_arg1, int p_arg2, int p_arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence p_s, int p_start, int p_before, int p_count) {
				// TODO Auto-generated method stub

			}
		});

		/*
		 * int largeurBtCat = Marque.getWidth(); Cat.setWidth(largeurBtCat); Tout.setWidth(largeurBtCat);
		 */

		filtreSelonSaisieEtBtActive("", Cat.isChecked(), Marque.isChecked(), Tout.isChecked());
		this.setTitle("Produit(s) périmé(s)");

	}

	/**
	 * Permet de faire un filtrage "a la volée" quand l'utilisateur saisie quelque chose dans ce champ, ca rafraichit la liste des elements
	 * a afficher.
	 * @param p_txtFiltrage String le texte support de filtrage
	 * @param p_isCatChecked boolean Le BtCat est activé
	 * @param p_isMarqueChecked boolean Le BtMarque est activé
	 * @param p_isToutChecked boolean Le BtTout est activé.
	 */
	protected void filtreSelonSaisieEtBtActive(String p_txtFiltrage, boolean p_isCatChecked, boolean p_isMarqueChecked,
			boolean p_isToutChecked) {
		if (p_isCatChecked) {
			produitRecherche.removeAll(produitRecherche);
			produitRechercheTitre.removeAll(produitRechercheTitre);
			AfficheLeContenu("TitreCat", produitRechercheTitre, ProduitListViewTitre, null);
			AfficheLeContenu("CatégorieAvecFiltrage", produitRecherche, ProduitListView1, p_txtFiltrage);

		}
		if (p_isMarqueChecked) {
			produitRecherche.removeAll(produitRecherche);
			produitRechercheTitre.removeAll(produitRechercheTitre);
			AfficheLeContenu("TitreMarque", produitRechercheTitre, ProduitListViewTitre, null);
			AfficheLeContenu("MarqueAvecFiltrage", produitRecherche, ProduitListView1, p_txtFiltrage);

		}
		if (p_isToutChecked) {
			produitRecherche.removeAll(produitRecherche);
			produitRechercheTitre.removeAll(produitRechercheTitre);
			AfficheLeContenu("TitreTout", produitRechercheTitre, ProduitListViewTitre, null);
			AfficheLeContenu("ToutAvecFiltrage", produitRecherche, ProduitListView1, p_txtFiltrage);

		}

	}

	@SuppressWarnings("rawtypes")
	private void ChoisiLeTheme() {
		// TODO Auto-generated method stub

		objBd.open();
		String[] champ = { "AfficheAlerte", "DureeViePeremp", "Theme" };
		ArrayList[] Param = objBd.renvoi_param(champ);
		objBd.close();

		String nomThemeChoisi = Param[2].get(0).toString().trim();

		if (EnTheme.Bisounours.getLib().equals(nomThemeChoisi)) {
			setContentView(R.layout.theme_bisounours_affiche_liste_produit_perime);
		}
		if (EnTheme.Classique.getLib().equals(nomThemeChoisi)) {
//			setContentView(R.layout.affiche_liste_produit_perime);
			ContentValues values = new ContentValues();
			values.put("Theme", EnTheme.Fleur.getLib());

			objBd.open();
			objBd.majTable("Param", values, "", null);
			objBd.close();
			ChoisiLeTheme();
		}
		if (EnTheme.Fleur.getLib().equals(nomThemeChoisi)) {
			setContentView(R.layout.theme_fleur_affiche_liste_produit_perime);

		}

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
				intentParametres.putExtra(ActivityParam.LaunchFromRechercheProduitPerime, true);
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
			case 2003:
				AlertDialog.Builder adHelp = new AlertDialog.Builder(this);
				adHelp.setTitle("Aide");
				adHelp.setIcon(R.drawable.ad_question);
				adHelp.setMessage("En cliquant sur un produit vous pourrez afficher le détail de celui-ci ou le copier dans une nouvelle note.\n"
						+ "En appuyant longtemps sur un produit, vous pourrez le supprimer.\n");
				adHelp.setPositiveButton("Ok", null);
				adHelp.show();
		}
		Log.i("", "" + item.getTitle());
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 
	 */
	private void termineActivity() {
		finish();
	}

	@SuppressWarnings("unused")
	@Override
	public void onItemClick(AdapterView<?> Parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		int Itemposition = Parent.getSelectedItemPosition();

		int ChildCount = Parent.getChildCount();
		View view1 = Parent.getChildAt(position);

		ViewHolder holder = (ViewHolder) view.getTag();

		Txt01 = (String) holder.text01.getText();
		Txt02 = (String) holder.text02.getText();
		Txt03 = (String) holder.text03.getText();

		AlertDialog.Builder dial = new AlertDialog.Builder(this);
		dial.setTitle("Que voulez vous faire avec :" + "\"" + Txt03 + "\"");
		dial.setIcon(R.drawable.ad_question);
		CharSequence[] items = { "Afficher les details", "Copier le produit dans une note" };
		dial.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int item) {
				/* User clicked on a radio button do some stuff */
				if (item == 0) {// afficher les details
					detail = true;
				}
				if (item == 1) {// créer une nouvelle note avec ce produit
					newNote = true;
				}

			}
		});
		dial.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int id) {

				if (detail) {// on affiche le detail du produit
					gotoAffDetail(Txt01);
				}
				if (newNote) {// on supprime le produit
					// gotoSupprDetail(Txt01,Txt03);
					gotoCreateNewNote(Txt01);
				}
			}
		});
		dial.show();
	}

	@SuppressWarnings("rawtypes")
	protected void gotoCreateNewNote(String idProduit) {
		// TODO Auto-generated method stub
		objBd.open();
		String[] colonne = { "nom_produit",// 0
				"nom_souscatergorie",// 1
				"nom_categorie",// 2
				"numero_Teinte",// 3
				"DateAchat",// 4
				"Duree_Vie",// 5
				"Date_Peremption",// 6
				"nom_marque" };// 7
		ArrayList[] trousse_final = objBd.renvoi_liste_TrousseFinalComplete(colonne, idProduit);
		objBd.close();
		String Nom_Produit = trousse_final[0].toString().replace("[", "").replace("]", "");
		String SousCat = trousse_final[1].toString().replace("[", "").replace("]", "");
		// String Cat = trousse_final[2].toString().replace("[", "").replace("]","");
		String Numeroteinte = trousse_final[3].toString().replace("[", "").replace("]", "");
		String DateAchat = trousse_final[4].toString().replace("[", "").replace("]", "");
		String DurreeVie = trousse_final[5].toString().replace("[", "").replace("]", "");
		String DatePeremption = trousse_final[6].toString().replace("[", "").replace("]", "");
		String NomMarque = trousse_final[7].toString().replace("[", "").replace("]", "");

		ContentValues values = new ContentValues();
		values.put("Titre", "[Auto] " + Nom_Produit + " " + NomMarque);
		values.put("Message", "Produit acheté le: " + DateAchat + "\n" + "Catégorie du produit: " + SousCat + "\n" + "Numéro de teinte: "
				+ Numeroteinte + "\n" + "Durée de vie du produit: " + DurreeVie + " mois\n" + "Date de péremption: " + DatePeremption
				+ "\n");

		objBd.open();
		objBd.InsertDonnéedansTable("Notes", values);
		objBd.close();

	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View view, int position, long id) {
		ViewHolder holder = (ViewHolder) view.getTag();

		Txt01 = (String) holder.text01.getText();
		Txt02 = (String) holder.text02.getText();
		Txt03 = (String) holder.text03.getText();

		final AlertDialog.Builder dial = new AlertDialog.Builder(this);
		dial.setTitle("Suppression");
		dial.setIcon(R.drawable.ad_question);
		dial.setMessage("Confirmez vous la suppression du produit suivant: " + "" + Txt03);

		dial.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int id) {
				gotoSupprProduit(Txt01, Txt03);

			}
		});
		dial.setNegativeButton("Non", null);
		dial.show();
		return false;
	}

	protected void gotoSupprProduit(final String IdProduit, String NomProduit) {
		// TODO Auto-generated method stub
		AlertDialog.Builder adSuppr = new AlertDialog.Builder(this);
		final AlertDialog.Builder ProduitSuppr = new AlertDialog.Builder(this);
		ProduitSuppr.setTitle("Resultat");
		ProduitSuppr.setPositiveButton("Ok", null);

		adSuppr.setTitle("Petite vérification");
		adSuppr.setMessage("Confirmer-vous la suppression du produit suivant: " + NomProduit);
		adSuppr.setPositiveButton("Oui", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int id) {
				String whereClause = "id_produits=?";
				String[] WhereArgs = new String[] { IdProduit };

				objBd.open();
				int nbChampEffacé = objBd.deleteTable("produit_Enregistre", whereClause, WhereArgs);
				if (nbChampEffacé != 1) {
					ProduitSuppr.setMessage("Erreur lors de la suppression");
					ProduitSuppr.show();
				}
				filtreSelonSaisieEtBtActive(EtFiltrage.getText().toString(), Cat.isChecked(), Marque.isChecked(), Tout.isChecked());
				objBd.close();

			}
		});
		adSuppr.setNegativeButton("Non", null);
		adSuppr.show();
	}

	protected void gotoAffDetail(String IdProduit) {
		// TODO Auto-generated method stub
		Intent intentDetail = new Intent(this, affiche_detail.class);
		// on demarre la nouvelle activité
		intentDetail.putExtra(ActivityParam.IdProduit, IdProduit);
		intentDetail.putExtra(ActivityParam.LaunchFromMain, IsCalledFromMain);
		intentDetail.putExtra(ActivityParam.LaunchFromRechercheProduitPerime, true);
		startActivity(intentDetail);
		termineActivity();

	}

	@Override
	public void onClick(View v) {

		if (v == Cat) {
			Cat.setChecked(true);
			Marque.setChecked(false);
			Tout.setChecked(false);
			String filtrage = EtFiltrage.getText().toString();
			filtreSelonSaisieEtBtActive(filtrage, Cat.isChecked(), Marque.isChecked(), Tout.isChecked());
			// produitRecherche.removeAll(produitRecherche);
			// produitRechercheTitre.removeAll(produitRechercheTitre);
			// AfficheLeContenu("TitreCat", produitRechercheTitre, ProduitListViewTitre, null);
			// AfficheLeContenu("Catégorie", produitRecherche, ProduitListView1, null);
		}
		if (v == Marque) {
			Cat.setChecked(false);
			Marque.setChecked(true);
			Tout.setChecked(false);
			String filtrage = EtFiltrage.getText().toString();
			filtreSelonSaisieEtBtActive(filtrage, Cat.isChecked(), Marque.isChecked(), Tout.isChecked());
			// produitRecherche.removeAll(produitRecherche);
			// produitRechercheTitre.removeAll(produitRechercheTitre);
			// AfficheLeContenu("TitreMarque", produitRechercheTitre, ProduitListViewTitre, null);
			// AfficheLeContenu("Marque", produitRecherche, ProduitListView1, null);
		}
		if (v == Tout) {
			Cat.setChecked(false);
			Marque.setChecked(false);
			Tout.setChecked(true);
			String filtrage = EtFiltrage.getText().toString();
			filtreSelonSaisieEtBtActive(filtrage, Cat.isChecked(), Marque.isChecked(), Tout.isChecked());
			// produitRecherche.removeAll(produitRecherche);
			// produitRechercheTitre.removeAll(produitRechercheTitre);
			// AfficheLeContenu("TitreTout", produitRechercheTitre, ProduitListViewTitre, null);
			// AfficheLeContenu("Tout", produitRecherche, ProduitListView1, null);
		}

	}

	@SuppressWarnings("rawtypes")
	private void AfficheLeContenu(String TypeRecherche, ArrayList<produitRecherche> produitFinal, ListView produitListView,
			String p_Filtrage) {

		objBd.open();

		if (TypeRecherche.equals("TitreCat")) {
			produitFinal.add(new produitRecherche("", "Date Peremp.", "Produit", "Marque"));
		}

		if (TypeRecherche.equals("CatégorieAvecFiltrage")) {

			String[] Colonnes = { "id_produits", "nom_produit", "Date_Peremption", "nom_marque" };

			String SQL = "SELECT " + "id_produits,nom_produit,Date_Peremption,nom_marque "//
					+ "FROM produit_Enregistre "//
					+ "where " //
					+ "(IS_PERIME='true' or IS_PRESQUE_PERIME='true') " //
					+ "ORDER BY Date_Peremption";

			ArrayList[] ListeProduits = objBd.renvoi_liste_TrousseFinalAvecFiltrage(SQL, Colonnes);
			int nbdobjet = ListeProduits[0].size();
			if (nbdobjet != 0) {
				for (int j = 0; j < nbdobjet; j++) {
					String IdProduit = ListeProduits[0].get(j).toString().replace("[", "").replace("]", "");
					String NomProduits = ListeProduits[1].get(j).toString().replace("[", "").replace("]", "");
					String NomCatégorie = ListeProduits[2].get(j).toString().replace("[", "").replace("]", "");
					String Marque = ListeProduits[3].get(j).toString().replace("[", "").replace("]", "");
					produitFinal.add(new produitRecherche(IdProduit, NomCatégorie.replaceAll("-", "/"), NomProduits, Marque));
				}
			} // else {
				// adAucunProduit.show();
				// }
		}
		if (TypeRecherche.equals("TitreMarque")) {
			produitFinal.add(new produitRecherche("", "Date Peremp", "Produit", "Marque"));
		}

		if (TypeRecherche.equals("MarqueAvecFiltrage")) {

			String[] Colonnes = { "id_produits", "nom_marque", "nom_produit", "Date_Peremption" };
			String SQL = "SELECT " //
					+ "id_produits,nom_produit,nom_marque,Date_Peremption" //
					+ " FROM produit_Enregistre"//
					+ " where (nom_marque LIKE '%" + p_Filtrage
					+ "%' and (IS_PERIME='true' or IS_PRESQUE_PERIME='true'))"
					+ "ORDER BY nom_marque";
			ArrayList[] ListeProduits = objBd.renvoi_liste_TrousseFinalAvecFiltrage(SQL, Colonnes);
			int nbdobjet = ListeProduits[0].size();
			if (nbdobjet != 0) {
				for (int j = 0; j < nbdobjet; j++) {
					String idProduit = ListeProduits[0].get(j).toString();
					String nomMarque = ListeProduits[1].get(j).toString();
					String nomProduit = ListeProduits[2].get(j).toString();
					String datePeremp = ListeProduits[3].get(j).toString();
					produitFinal.add(new produitRecherche(idProduit, datePeremp.replaceAll("-", "/"), nomProduit, nomMarque));
				}
			} // else {
				// adAucunProduit.show();
				// }
		}
		if (TypeRecherche.equals("TitreTout")) {
			produitFinal.add(new produitRecherche("", "Date péremp.", "Produit", "Marque"));
		}

		if (TypeRecherche.equals("ToutAvecFiltrage")) {

			String[] Colonnes = { "id_produits", "Date_Peremption", "nom_produit", "nom_marque" };

			String SQL = "SELECT" + " id_produits,Date_Peremption,nom_produit,nom_marque " + "FROM produit_Enregistre "
					+ "where ((nom_produit LIKE '%" + p_Filtrage + "%' " + "or nom_marque LIKE '%" + p_Filtrage + "%' "
					+ "or Date_Peremption LIKE '%" + p_Filtrage + "%') and (IS_PERIME='true' or IS_PRESQUE_PERIME='true'))"
					+ "ORDER BY id_produits";
			ArrayList[] ListeProduits = objBd.renvoi_liste_TrousseFinalAvecFiltrage(SQL, Colonnes);
			int nbdobjet = ListeProduits[0].size();
			if (nbdobjet != 0) {
				for (int j = 0; j < nbdobjet; j++) {
					String IdProduit = ListeProduits[0].get(j).toString();
					String datePeremption = ListeProduits[1].get(j).toString();
					String nomProduit = ListeProduits[2].get(j).toString();
					String nomMarque = ListeProduits[3].get(j).toString();
					produitFinal.add(new produitRecherche(IdProduit, datePeremption.replaceAll("-", "/"), nomProduit, nomMarque));
				}
			} // else {
				// adAucunProduit.show();
				// }
		}
		// if (TypeRecherche.equals("TitrePerime")) {
		// produitFinal.add(new produitRecherche("", "Date péremp.", "Produit", "Marque"));
		// }
		// if (TypeRecherche.equals("Perimé")) {
		//
		// String[] Colonnes = { "id_produits", "nom_produit", "Date_Peremption", "nom_marque" };
		// String condition = "IS_PERIME=? or IS_PRESQUE_PERIME=?";
		// String[] args = { "true", "true" };
		//
		// ArrayList[] ListeProduits = objBd.renvoi_liste_TrousseFinal(Colonnes, "id_produits", "", condition, args);
		// int nbdobjet = ListeProduits[0].size();
		// if (nbdobjet != 0) {
		// for (int j = 0; j < nbdobjet; j++) {
		// String IdProduit = ListeProduits[0].get(j).toString();
		// String NomProduits = ListeProduits[1].get(j).toString();
		// String Date_Peremption = ListeProduits[2].get(j).toString();
		// String Marque = ListeProduits[3].get(j).toString();
		// produitFinal.add(new produitRecherche(IdProduit, Date_Peremption, NomProduits, Marque));
		// }
		// } else {
		// adAucunProduit.show();
		// }
		// }

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
			Main.putExtra(ActivityParam.LaunchFromRechercheProduitPerime, true);
			startActivity(Main);
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
		popUp("OnDestroy-Page1");
		super.onDestroy();

	}

}
