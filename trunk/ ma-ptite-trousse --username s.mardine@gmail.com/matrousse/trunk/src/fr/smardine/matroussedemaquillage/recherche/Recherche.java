package fr.smardine.matroussedemaquillage.recherche;

import helper.DateHelper;

import java.util.ArrayList;

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
public class Recherche extends SuperRechercheActivity implements
		OnClickListener, OnItemClickListener, OnItemLongClickListener {
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
	private boolean detail;
	private boolean newNote;
	boolean IsCalledFromMain;
	EnCategorieFiltrage filtrageChoisi;

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

			// int len = 0;

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

		adAucunProduit = new AlertDialog.Builder(this);
		adAucunProduit.setTitle("Pour information");
		adAucunProduit
				.setMessage("Aucun produit n'est actuellement enregistré dans Ma p'tite trousse");
		adAucunProduit.setPositiveButton("Ok", null);

		adChoixFiltrage = new AlertDialog.Builder(this);
		adChoixFiltrage
				.setTitle("Merci de choisir un filtre pour votre recherche?");
		adChoixFiltrage.setIcon(R.drawable.ad_question);
		CharSequence[] items = { EnCategorieFiltrage.TOUT.getLib(),
				EnCategorieFiltrage.MARQUE.getLib(),
				EnCategorieFiltrage.CATEGORIE.getLib() };
		adChoixFiltrage.setSingleChoiceItems(items, -1,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int item) {
						/* User clicked on a radio button do some stuff */

						switch (item) {
							case 0:
								filtrageChoisi = EnCategorieFiltrage.TOUT;
								break;
							case 1:
								filtrageChoisi = EnCategorieFiltrage.MARQUE;
								break;
							case 2:
								filtrageChoisi = EnCategorieFiltrage.CATEGORIE;
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

		filtreSelonSaisieEtBtActive("", EnCategorieFiltrage.TOUT);
		this.setTitle("Recherche d'un produit");

	}

	/**
	 * Permet de faire un filtrage "a la volée" quand l'utilisateur saisie
	 * quelque chose dans ce champ, ca rafraichit la liste des elements a
	 * afficher.
	 * @param p_txtFiltrage String le texte support de filtrage
	 * @param p_isCatChecked boolean Le BtCat est activé
	 * @param p_isMarqueChecked boolean Le BtMarque est activé
	 * @param p_isToutChecked boolean Le BtTout est activé.
	 */
	protected void filtreSelonSaisieEtBtActive(String p_txtFiltrage,
			EnCategorieFiltrage p_filtrageChoisi) {

		produitRecherche.removeAll(produitRecherche);
		produitRechercheTitre.removeAll(produitRechercheTitre);
		AfficheLeContenu(p_filtrageChoisi, produitRechercheTitre,
				ProduitListViewTitre, p_txtFiltrage, false);

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
			// l'identifiant integer est moins gourmand en ressource que le
			// string
			case 2001:
				Toast.makeText(this, "Paramètres", 1000).show();
				Intent intentParametres = new Intent(this, tab_param.class);
				intentParametres.putExtra(ActivityParam.LaunchFromRecherche,
						true);
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
	// @Override
	// private void termineActivity() {
	// finish();
	// }

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
						if (item == 1) {// créer une nouvelle note avec ce
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

		adSuppr.setTitle("Petite vérification");
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
		// on demarre la nouvelle activité
		intentDetail.putExtra(ActivityParam.IdProduit, IdProduit);
		intentDetail.putExtra(ActivityParam.LaunchFromMain, IsCalledFromMain);
		intentDetail.putExtra(ActivityParam.AfficheProduitPerime, false);
		startActivity(intentDetail);
		termineActivity();

	}

	@Override
	public void onClick(View v) {

		if (v.equals(BtFiltrerPar)) {
			adChoixFiltrage.show();
		}

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
		// if (keyCode == KeyEvent.KEYCODE_SEARCH) {
		// // on ne fait rien, on est deja dans cette activity
		//
		// }
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void AfficheLeContenu(EnCategorieFiltrage p_TypeRecherche,
			ArrayList<produitRecherche> produitFinal, ListView produitListView,
			String p_Filtrage, boolean rechPerime) {

		AccesTableProduitEnregistre accesProduit = new AccesTableProduitEnregistre(
				this);
		if (rechPerime) {
			produitFinal.add(new produitRecherche("-1", "Date Peremp.",
					"Produit", "Marque"));
			MlListeProduits lstProduit = accesProduit
					.getListeProduitPerimeFiltree(p_TypeRecherche, p_Filtrage);
			for (MlProduit p : lstProduit) {
				produitFinal.add(new produitRecherche("" + p.getIdProduit(),
						DateHelper.getDateforDatabase(p.getDatePeremption()), p
								.getNomProduit(), p.getMarque()));
			}
		} else {
			produitFinal.add(new produitRecherche("-1", "Catégorie", "Produit",
					"Marque"));
			MlListeProduits lstProduit = accesProduit.getListeProduitFiltree(
					p_TypeRecherche, p_Filtrage);
			for (MlProduit p : lstProduit) {
				produitFinal.add(new produitRecherche("" + p.getIdProduit(), p
						.getCategorie().getCategorie().name(), p
						.getNomProduit(), p.getMarque()));
			}
		}

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

		// paramètrer l'adapteur correspondant
		produitRechercheListAdapter adpt = new produitRechercheListAdapter(
				this, produitFinal);
		// paramèter l'adapter sur la listview
		produitListView.setAdapter(adpt);

	}

}
