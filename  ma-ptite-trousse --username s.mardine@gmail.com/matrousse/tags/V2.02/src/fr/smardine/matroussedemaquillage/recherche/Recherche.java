package fr.smardine.matroussedemaquillage.recherche;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import fr.smardine.matroussedemaquillage.Main;
import fr.smardine.matroussedemaquillage.R;
import fr.smardine.matroussedemaquillage.base.accesTable.AccesTableNotes;
import fr.smardine.matroussedemaquillage.base.accesTable.AccesTableParams;
import fr.smardine.matroussedemaquillage.base.accesTable.AccesTableProduitEnregistre;
import fr.smardine.matroussedemaquillage.mdl.MlListeProduits;
import fr.smardine.matroussedemaquillage.mdl.MlProduit;
import fr.smardine.matroussedemaquillage.note.note_page1;
import fr.smardine.matroussedemaquillage.param.tab_param;
import fr.smardine.matroussedemaquillage.recherche.produitRechercheListAdapter.ViewHolder;
import fr.smardine.matroussedemaquillage.variableglobale.ActivityParam;
import fr.smardine.matroussedemaquillage.variableglobale.EnCategorieFiltrage;
import fr.smardine.matroussedemaquillage.variableglobale.EnTheme;

/**
 * @author smardine
 */
public class Recherche extends Activity implements OnClickListener,
		OnItemClickListener, OnItemLongClickListener {
	// ToggleButton Cat, Marque, Tout;
	EditText EtFiltrage;
	Button BtFiltrerPar;
	ArrayList<produitRecherche> produitRecherche = new ArrayList<produitRecherche>();
	ArrayList<produitRecherche> produitRechercheTitre = new ArrayList<produitRecherche>();
	int VISIBLE = 1, INVISIBLE = 4, GONE = 8;
	ListView ProduitListView1, ProduitListViewTitre;
	produitRechercheListAdapter adpt;
	// BDAcces objBd;
	AlertDialog.Builder adAucunProduit, adChoixFiltrage;
	Context ctx = Recherche.this;
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
	String filtrageChoisi;

	@Override
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ExceptionHandler.register(this,
		// "http://simon.mardine.free.fr/trousse_maquillage/test/server.php");

		IsCalledFromMain = getIntent().getBooleanExtra(
				ActivityParam.LaunchFromMain, false);

		// objBd = new BDAcces(this);

		ChoisiLeTheme();
		BtFiltrerPar = (Button) findViewById(R.id.BtFiltrerPar);
		BtFiltrerPar.setOnClickListener(this);
		// Cat = (ToggleButton) findViewById(R.id.BTcat);
		// Marque = (ToggleButton) findViewById(R.id.BTmarque);
		// Tout = (ToggleButton) findViewById(R.id.BTtout);
		EtFiltrage = (EditText) findViewById(R.id.EtFiltrage);

		EtFiltrage.addTextChangedListener(new TextWatcher() {
			@SuppressWarnings("unused")
			int len = 0;

			@Override
			public void afterTextChanged(Editable s) {
				String str = EtFiltrage.getText().toString();
				filtreSelonSaisieEtBtActive(str, filtrageChoisi);
			}

			@Override
			public void beforeTextChanged(CharSequence p_arg0, int p_arg1,
					int p_arg2, int p_arg3) {

			}

			@Override
			public void onTextChanged(CharSequence p_s, int p_start,
					int p_before, int p_count) {

			}
		});

		ProduitListView1 = (ListView) this
				.findViewById(R.id.produitListViewRecherche);
		ProduitListViewTitre = (ListView) this
				.findViewById(R.id.produitListViewRechercheTitre);

		ProduitListView1.setOnItemClickListener(this);
		ProduitListView1.setOnItemLongClickListener(this);
		// Cat.setOnClickListener(this);
		// Marque.setOnClickListener(this);
		// Tout.setOnClickListener(this);

		/*
		 * int largeurBtCat = Marque.getWidth(); Cat.setWidth(largeurBtCat);
		 * Tout.setWidth(largeurBtCat);
		 */

		adAucunProduit = new AlertDialog.Builder(this);
		adAucunProduit.setTitle("Pour information");
		adAucunProduit
				.setMessage("Aucun produit n'est actuellement enregistr� dans Ma p'tite trousse");
		adAucunProduit.setPositiveButton("Ok", null);

		adChoixFiltrage = new AlertDialog.Builder(this);
		adChoixFiltrage
				.setTitle("Merci de choisir un filtre pour votre recherche?");
		adChoixFiltrage.setIcon(R.drawable.ad_question);
		CharSequence[] items = { EnCategorieFiltrage.Tout.getLib(),
				EnCategorieFiltrage.Marque.getLib(),
				EnCategorieFiltrage.Categorie.getLib() };
		adChoixFiltrage.setSingleChoiceItems(items, -1,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int item) {
						/* User clicked on a radio button do some stuff */

						switch (item) {
							case 0:
								filtrageChoisi = EnCategorieFiltrage.Tout
										.getLib();

								break;
							case 1:
								filtrageChoisi = EnCategorieFiltrage.Marque
										.getLib();
								break;
							case 2:
								filtrageChoisi = EnCategorieFiltrage.Categorie
										.getLib();
								break;
						}

					}
				});
		adChoixFiltrage.setPositiveButton("Ok",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface p_dialog, int p_which) {
						BtFiltrerPar.setText("Filtrer par: " + filtrageChoisi);
						String filtrage = EtFiltrage.getText().toString();
						filtreSelonSaisieEtBtActive(filtrage, filtrageChoisi);
					}
				});
		adChoixFiltrage.setNegativeButton("Annuler", null);

		filtreSelonSaisieEtBtActive("", EnCategorieFiltrage.Tout.getLib());
		this.setTitle("Recherche d'un produit");

	}

	/**
	 * Permet de faire un filtrage "a la vol�e" quand l'utilisateur saisie
	 * quelque chose dans ce champ, ca rafraichit la liste des elements a
	 * afficher.
	 * @param p_txtFiltrage String le texte support de filtrage
	 * @param p_isCatChecked boolean Le BtCat est activ�
	 * @param p_isMarqueChecked boolean Le BtMarque est activ�
	 * @param p_isToutChecked boolean Le BtTout est activ�.
	 */
	protected void filtreSelonSaisieEtBtActive(String p_txtFiltrage,
			String p_filtrageChoisi) {
		if (EnCategorieFiltrage.Categorie.getLib().equals(p_filtrageChoisi)) {
			produitRecherche.removeAll(produitRecherche);
			produitRechercheTitre.removeAll(produitRechercheTitre);
			AfficheLeContenu("TitreCat", produitRechercheTitre,
					ProduitListViewTitre, null);
			AfficheLeContenu("Cat�gorieAvecFiltrage", produitRecherche,
					ProduitListView1, p_txtFiltrage);

		}
		if (EnCategorieFiltrage.Marque.getLib().equals(p_filtrageChoisi)) {
			produitRecherche.removeAll(produitRecherche);
			produitRechercheTitre.removeAll(produitRechercheTitre);
			AfficheLeContenu("TitreMarque", produitRechercheTitre,
					ProduitListViewTitre, null);
			AfficheLeContenu("MarqueAvecFiltrage", produitRecherche,
					ProduitListView1, p_txtFiltrage);

		}
		if (EnCategorieFiltrage.Tout.getLib().equals(p_filtrageChoisi)) {
			produitRecherche.removeAll(produitRecherche);
			produitRechercheTitre.removeAll(produitRechercheTitre);
			AfficheLeContenu("TitreTout", produitRechercheTitre,
					ProduitListViewTitre, null);
			AfficheLeContenu("ToutAvecFiltrage", produitRecherche,
					ProduitListView1, p_txtFiltrage);

		}

	}

	private void ChoisiLeTheme() {

		AccesTableParams accesParam = new AccesTableParams(this);
		switch (accesParam.getThemeChoisi()) {
			case Bisounours:
				setContentView(R.layout.theme_bisounours_recherche);
				break;
			case Classique:
				accesParam.majTheme(EnTheme.Fleur);
				ChoisiLeTheme();
				break;
			case Fleur:
				setContentView(R.layout.theme_fleur_recherche);
				break;
		}
		// objBd.open();
		// String[] champ = { "AfficheAlerte", "DureeViePeremp", "Theme" };
		// ArrayList[] Param = objBd.renvoi_param(champ);
		// //objBd.close();
		//
		// String nomThemeChoisi = Param[2].get(0).toString().trim();
		//
		// if (EnTheme.Bisounours.getLib().equals(nomThemeChoisi)) {
		// setContentView(R.layout.theme_bisounours_recherche);
		// }
		// if (EnTheme.Classique.getLib().equals(nomThemeChoisi)) {
		// // setContentView(R.layout.recherche);
		// AccesTableParams accesParam = new AccesTableParams(ctx);
		// accesParam.majTheme(EnTheme.Fleur);
		//
		// ChoisiLeTheme();
		// }
		// if (EnTheme.Fleur.getLib().equals(nomThemeChoisi)) {
		// setContentView(R.layout.theme_fleur_recherche);
		//
		// }

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
		// pourquoi sous-classer une m�thode ? = pour limiter les ressources
		// privil�gier la cr�ation "� la demande" le menu est cr�� uniquement
		// lorsque la touche physique <menu> est appuy�e par l'utilisateur
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
		// �v�nement appel� lorsqu'un menu est choisi
		switch (item.getItemId()) {
			// l'identifiant integer est moins gourmand en ressource que le
			// string
			case 2001:
				Toast.makeText(this, "Param�tres", 1000).show();
				Intent intentParametres = new Intent(this, tab_param.class);
				intentParametres.putExtra(ActivityParam.LaunchFromRecherche,
						true);
				// on demarre la nouvelle activit�
				startActivity(intentParametres);
				termineActivity();
				break;
			case 2002:
				Toast.makeText(this, "Notes", 1000).show();
				Intent intentNote = new Intent(this, note_page1.class);
				// on demarre la nouvelle activit�
				startActivity(intentNote);
				termineActivity();
				break;
			case 2003:
				AlertDialog.Builder adHelp = new AlertDialog.Builder(this);
				adHelp.setTitle("Aide");
				adHelp.setIcon(R.drawable.ad_question);
				adHelp.setMessage("En cliquant sur un produit vous pourrez afficher le d�tail de celui-ci ou le copier dans une nouvelle note.\n"
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
	public void onItemClick(AdapterView<?> Parent, View view, int position,
			long id) {

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
		CharSequence[] items = { "Afficher les details",
				"Copier le produit dans une note" };
		dial.setSingleChoiceItems(items, -1,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int item) {
						/* User clicked on a radio button do some stuff */
						if (item == 0) {// afficher les details
							detail = true;
						}
						if (item == 1) {// cr�er une nouvelle note avec ce
										// produit
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
					gotoCreateNewNote(Integer.parseInt(Txt01));
				}
			}
		});
		dial.show();
	}

	protected void gotoCreateNewNote(int p_idProduit) {

		MlProduit p = new MlProduit(p_idProduit, this);
		AccesTableNotes accesNote = new AccesTableNotes(ctx);
		accesNote.createNewNoteDepuisProduit(p);

		// objBd.open();
		// String[] colonne = { "nom_produit",// 0
		// "nom_souscatergorie",// 1
		// "nom_categorie",// 2
		// "numero_Teinte",// 3
		// "DateAchat",// 4
		// "Duree_Vie",// 5
		// "Date_Peremption",// 6
		// "nom_marque" };// 7
		// ArrayList[] trousse_final = objBd.renvoi_liste_TrousseFinalComplete(
		// colonne, idProduit);
		// // objBd.close();
		// String Nom_Produit = trousse_final[0].toString().replace("[", "")
		// .replace("]", "");
		// String SousCat = trousse_final[1].toString().replace("[", "")
		// .replace("]", "");
		// // String Cat = trousse_final[2].toString().replace("[",
		// // "").replace("]","");
		// String Numeroteinte = trousse_final[3].toString().replace("[", "")
		// .replace("]", "");
		// String DateAchat = trousse_final[4].toString().replace("[", "")
		// .replace("]", "");
		// String DurreeVie = trousse_final[5].toString().replace("[", "")
		// .replace("]", "");
		// String DatePeremption = trousse_final[6].toString().replace("[", "")
		// .replace("]", "");
		// String NomMarque = trousse_final[7].toString().replace("[", "")
		// .replace("]", "");

		// ContentValues values = new ContentValues();
		// values.put("Titre", "[Auto] " + p.getNomProduit() + " " +
		// p.getMarque());
		// values.put("Message", "Produit achet� le: " + p.getDateAchat() + "\n"
		// + "Cat�gorie du produit: " + p.getNomSousCat() + "\n"
		// + "Num�ro de teinte: " + p.getTeinte() + "\n"
		// + "Dur�e de vie du produit: " + p.getDureeVie() + " mois\n"
		// + "Date de p�remption: " + p.getDatePeremption() + "\n");
		//
		// // objBd.open();
		// objBd.InsertDonn�edansTable("Notes", values);
		// objBd.close();

	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View view,
			int position, long id) {
		ViewHolder holder = (ViewHolder) view.getTag();

		Txt01 = (String) holder.text01.getText();
		Txt02 = (String) holder.text02.getText();
		Txt03 = (String) holder.text03.getText();

		final AlertDialog.Builder dial = new AlertDialog.Builder(this);
		dial.setTitle("Suppression");
		dial.setIcon(R.drawable.ad_question);
		dial.setMessage("Confirmez vous la suppression du produit suivant: "
				+ "" + Txt03);

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

		AlertDialog.Builder adSuppr = new AlertDialog.Builder(this);
		final AlertDialog.Builder ProduitSuppr = new AlertDialog.Builder(this);
		ProduitSuppr.setTitle("Resultat");
		ProduitSuppr.setPositiveButton("Ok", null);

		adSuppr.setTitle("Petite v�rification");
		adSuppr.setMessage("Confirmer-vous la suppression du produit suivant: "
				+ NomProduit);
		adSuppr.setPositiveButton("Oui", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int id) {
				AccesTableProduitEnregistre accesProd = new AccesTableProduitEnregistre(
						ctx);
				boolean succes = accesProd.deleteProduit(Integer
						.parseInt(IdProduit));
				if (!succes) {
					ProduitSuppr.setMessage("Erreur lors de la suppression");
					ProduitSuppr.show();
				}
				filtreSelonSaisieEtBtActive(EtFiltrage.getText().toString(),
						filtrageChoisi);
				// objBd.close();

			}
		});
		adSuppr.setNegativeButton("Non", null);
		adSuppr.show();
	}

	protected void gotoAffDetail(String IdProduit) {

		Intent intentDetail = new Intent(this, affiche_detail.class);
		// on demarre la nouvelle activit�
		intentDetail.putExtra(ActivityParam.IdProduit, IdProduit);
		intentDetail.putExtra(ActivityParam.LaunchFromMain, IsCalledFromMain);
		intentDetail.putExtra(ActivityParam.AfficheProduitPerime, false);
		startActivity(intentDetail);
		termineActivity();

	}

	@Override
	public void onClick(View v) {

		if (v == BtFiltrerPar) {
			adChoixFiltrage.show();
		}

		// if (v == Cat) {
		// Cat.setChecked(true);
		// Marque.setChecked(false);
		// Tout.setChecked(false);
		// String filtrage = EtFiltrage.getText().toString();
		// filtreSelonSaisieEtBtActive(filtrage, Cat.isChecked(),
		// Marque.isChecked(), Tout.isChecked());
		//
		// }
		// if (v == Marque) {
		// Cat.setChecked(false);
		// Marque.setChecked(true);
		// Tout.setChecked(false);
		// String filtrage = EtFiltrage.getText().toString();
		// filtreSelonSaisieEtBtActive(filtrage, Cat.isChecked(),
		// Marque.isChecked(), Tout.isChecked());
		//
		// }
		// if (v == Tout) {
		// Cat.setChecked(false);
		// Marque.setChecked(false);
		// Tout.setChecked(true);
		// String filtrage = EtFiltrage.getText().toString();
		// filtreSelonSaisieEtBtActive(filtrage, Cat.isChecked(),
		// Marque.isChecked(), Tout.isChecked());
		//
		// }

	}

	private void AfficheLeContenu(String TypeRecherche,
			ArrayList<produitRecherche> produitFinal, ListView produitListView,
			String p_Filtrage) {

		// objBd.open();

		if (TypeRecherche.equals("TitreCat")) {
			produitFinal.add(new produitRecherche(-1, "Cat�gorie", "Produit",
					"Marque"));
		}

		if (TypeRecherche.equals("Cat�gorieAvecFiltrage")) {

			// String[] Colonnes = { "id_produits", "nom_produit",
			// "nom_souscatergorie", "nom_marque" };
			//
			// String SQL =
			// "SELECT id_produits,nom_produit,nom_souscatergorie,nom_marque FROM produit_Enregistre where nom_souscatergorie LIKE '%"
			// + p_Filtrage + "%' ORDER BY nom_souscatergorie";
			// ArrayList[] ListeProduits = objBd
			// .renvoi_liste_TrousseFinalAvecFiltrage(SQL, Colonnes);
			AccesTableProduitEnregistre accesProduit = new AccesTableProduitEnregistre(
					ctx);
			MlListeProduits lstProduit = accesProduit
					.getListeProduitsAvecFiltrageSurCategorie(p_Filtrage);
			for (MlProduit p : lstProduit) {
				produitFinal.add(new produitRecherche(p.getIdProduit(), p
						.getNomCat(), p.getNomProduit(), p.getMarque()));
			}
			// int nbdobjet = ListeProduits[0].size();
			// if (nbdobjet != 0) {
			// for (int j = 0; j < nbdobjet; j++) {
			// String IdProduit = ListeProduits[0].get(j).toString()
			// .replace("[", "").replace("]", "");
			// String NomProduits = ListeProduits[1].get(j).toString()
			// .replace("[", "").replace("]", "");
			// String NomCat�gorie = ListeProduits[2].get(j).toString()
			// .replace("[", "").replace("]", "");
			// String Marque = ListeProduits[3].get(j).toString()
			// .replace("[", "").replace("]", "");
			// produitFinal.add(new produitRecherche(IdProduit,
			// NomCat�gorie, NomProduits, Marque));
			// }
			// } // else {
			// adAucunProduit.show();
			// }
		}
		if (TypeRecherche.equals("TitreMarque")) {
			produitFinal.add(new produitRecherche(-1, "Marque", "Produit",
					"Cat�gorie"));
		}

		if (TypeRecherche.equals("MarqueAvecFiltrage")) {
			AccesTableProduitEnregistre accesProduit = new AccesTableProduitEnregistre(
					ctx);
			MlListeProduits lstProduit = accesProduit
					.getListeProduitsAvecFiltrageSurMarque(p_Filtrage);
			for (MlProduit p : lstProduit) {
				produitFinal.add(new produitRecherche(p.getIdProduit(), p
						.getNomCat(), p.getNomProduit(), p.getMarque()));
			}

			// String[] Colonnes = { "id_produits", "nom_produit", "nom_marque",
			// "nom_souscatergorie" };
			// String SQL =
			// "SELECT id_produits,nom_produit,nom_souscatergorie,nom_marque FROM produit_Enregistre where nom_marque LIKE '%"
			// + p_Filtrage + "%' ORDER BY nom_marque";
			// ArrayList[] ListeProduits = objBd
			// .renvoi_liste_TrousseFinalAvecFiltrage(SQL, Colonnes);
			// int nbdobjet = ListeProduits[0].size();
			// if (nbdobjet != 0) {
			// for (int j = 0; j < nbdobjet; j++) {
			// String IdProduit = ListeProduits[0].get(j).toString();
			// String NomProduits = ListeProduits[1].get(j).toString();
			// String NomMarque = ListeProduits[2].get(j).toString();
			// String Cat = ListeProduits[3].get(j).toString();
			// produitFinal.add(new produitRecherche(IdProduit, NomMarque,
			// NomProduits, Cat));
			// }
			// } // else {
			// adAucunProduit.show();
			// }
		}
		if (TypeRecherche.equals("TitreTout")) {
			produitFinal.add(new produitRecherche(-1, "Marque", "Produit",
					"Cat�gorie"));
		}
		// if (TypeRecherche.equals("Tout")) {
		//
		// String[] Colonnes = { "id_produits", "nom_produit", "nom_marque",
		// "nom_souscatergorie" };
		//
		// ArrayList[] ListeProduits = objBd.renvoi_liste_TrousseFinal(Colonnes,
		// "id_produits", "", "", null);
		// int nbdobjet = ListeProduits[0].size();
		// if (nbdobjet != 0) {
		// for (int j = 0; j < nbdobjet; j++) {
		// String IdProduit = ListeProduits[0].get(j).toString();
		// String NomProduits = ListeProduits[1].get(j).toString();
		// String NomMarque = ListeProduits[2].get(j).toString();
		// String Cat = ListeProduits[3].get(j).toString();
		// produitFinal.add(new produitRecherche(IdProduit, NomMarque,
		// NomProduits, Cat));
		// }
		// } else {
		// adAucunProduit.show();
		// }
		// }
		if (TypeRecherche.equals("ToutAvecFiltrage")) {
			AccesTableProduitEnregistre accesProduit = new AccesTableProduitEnregistre(
					ctx);
			MlListeProduits lstProduit = accesProduit
					.getListeProduitsAvecFiltrageSurTout(p_Filtrage);
			for (MlProduit p : lstProduit) {
				produitFinal.add(new produitRecherche(p.getIdProduit(), p
						.getNomCat(), p.getNomProduit(), p.getMarque()));
			}

			// String[] Colonnes = { "id_produits", "nom_produit", "nom_marque",
			// "nom_souscatergorie" };
			//
			// String SQL = "SELECT"
			// + " id_produits,nom_produit,nom_souscatergorie,nom_marque "
			// + "FROM produit_Enregistre " + "where nom_produit LIKE '%"
			// + p_Filtrage + "%' " + "or nom_marque LIKE '%" + p_Filtrage
			// + "%' " + "or nom_souscatergorie LIKE '%" + p_Filtrage
			// + "%' ORDER BY id_produits";
			// ArrayList[] ListeProduits = objBd
			// .renvoi_liste_TrousseFinalAvecFiltrage(SQL, Colonnes);
			// int nbdobjet = ListeProduits[0].size();
			// if (nbdobjet != 0) {
			// for (int j = 0; j < nbdobjet; j++) {
			// String IdProduit = ListeProduits[0].get(j).toString();
			// String NomProduits = ListeProduits[1].get(j).toString();
			// String NomMarque = ListeProduits[2].get(j).toString();
			// String Cat = ListeProduits[3].get(j).toString();
			// produitFinal.add(new produitRecherche(IdProduit, NomMarque,
			// NomProduits, Cat));
			// }
			// } // else {
			// adAucunProduit.show();
			// }
		}
		if (TypeRecherche.equals("TitrePerime")) {
			produitFinal.add(new produitRecherche(-1, "Date p�remp.",
					"Produit", "Marque"));
		}
		if (TypeRecherche.equals("Perim�")) {
			AccesTableProduitEnregistre accesProduit = new AccesTableProduitEnregistre(
					ctx);
			MlListeProduits lstProduit = accesProduit.getListeProduitsPerime();
			if (lstProduit.size() == 0) {
				adAucunProduit.show();
			}
			for (MlProduit p : lstProduit) {
				produitFinal.add(new produitRecherche(p.getIdProduit(), p
						.getNomCat(), p.getNomProduit(), p.getMarque()));
			}
			// String[] Colonnes = { "id_produits", "nom_produit",
			// "Date_Peremption", "nom_marque" };
			// String condition = "IS_PERIME=? or IS_PRESQUE_PERIME=?";
			// String[] args = { "true", "true" };
			//
			// ArrayList[] ListeProduits = objBd.renvoi_liste_TrousseFinal(
			// Colonnes, "id_produits", "", condition, args);
			// int nbdobjet = ListeProduits[0].size();
			// if (nbdobjet != 0) {
			// for (int j = 0; j < nbdobjet; j++) {
			// String IdProduit = ListeProduits[0].get(j).toString();
			// String NomProduits = ListeProduits[1].get(j).toString();
			// String Date_Peremption = ListeProduits[2].get(j).toString();
			// String Marque = ListeProduits[3].get(j).toString();
			// produitFinal.add(new produitRecherche(IdProduit,
			// Date_Peremption, NomProduits, Marque));
			// }
			// } else {
			// adAucunProduit.show();
			// }
		}

		// objBd.close();
		// animation d'affichage cascade du haut vers le bas
		AnimationSet set = new AnimationSet(true);
		Animation animation = new AlphaAnimation(0.0f, 1.0f);
		animation.setDuration(100);
		set.addAnimation(animation);
		animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
				-1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		animation.setDuration(100);
		set.addAnimation(animation);
		LayoutAnimationController controller = new LayoutAnimationController(
				set, 0.5f);
		produitListView.setLayoutAnimation(controller);

		// param�trer l'adapteur correspondant
		adpt = new produitRechercheListAdapter(this, produitFinal);
		// param�ter l'adapter sur la listview
		produitListView.setAdapter(adpt);

	}

	/**
	 * @param message
	 */
	public void popUp(String message) {
		// Toast.makeText(this, message, 1).show();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		// popUp("onRestart()-Page1");
	}

	/**
	 * Ex�cut� lorsque l'activit� devient visible � l'utilisateur. La fonction
	 * onStart() est suivie de la fonction onResume().
	 */
	@Override
	protected void onStart() {
		super.onStart();
		// popUp("onStart()-Page1");
	}

	/**
	 * Ex�cut�e a chaque passage en premier plan de l'activit�. Ou bien, si
	 * l'activit� passe � nouveau en premier (si une autre activit� �tait pass�
	 * en premier plan entre temps). La fonction onResume() est suivie de
	 * l'ex�cution de l'activit�.
	 */
	@Override
	protected void onResume() {
		super.onResume();

	}

	/**
	 * La fonction onStop() est ex�cut�e : - lorsque l'activit� n'est plus en
	 * premier plan - ou bien lorsque l'activit� va �tre d�truite Cette fonction
	 * est suivie : - de la fonction onRestart() si l'activit� passe � nouveau
	 * en premier plan - de la fonction onDestroy() lorsque l'activit� se
	 * termine ou bien lorsque le syst�me d�cide de l'arr�ter
	 */
	@Override
	protected void onStop() {
		super.onStop();
		popUp("onStop-Page1");
	}

	/**
	 * La fonction onPause() est suivie : - d'un onResume() si l'activit� passe
	 * � nouveau en premier plan - d'un onStop() si elle devient invisible �
	 * l'utilisateur L'ex�cution de la fonction onPause() doit �tre rapide, car
	 * la prochaine activit� ne d�marrera pas tant que l'ex�cution de la
	 * fonction onPause() n'est pas termin�e.
	 */
	@Override
	protected void onPause() {
		super.onPause();

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			Intent Main = new Intent(this, Main.class);
			Main.putExtra(ActivityParam.LaunchFromRecherche, true);
			startActivity(Main);
			termineActivity();
			return true;
		}
		if (keyCode == KeyEvent.KEYCODE_SEARCH) {
			// on ne fait rien, on est deja dans cette activity

		}
		return super.onKeyDown(keyCode, event);
	}

	/**
 * 
 */
	public void OnDestroy() {
		popUp("OnDestroy-Page1");
		super.onDestroy();

	}

}
