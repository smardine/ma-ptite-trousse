package fr.smardine.matroussedemaquillage;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.example.android.apis.animation.Animlineaire;

import fr.smardine.matroussedemaquillage.base.BDAcces;
import fr.smardine.matroussedemaquillage.note.note_page1;
import fr.smardine.matroussedemaquillage.param.tab_param;
import fr.smardine.matroussedemaquillage.recherche.recherchetabsbuttons;
import fr.smardine.matroussedemaquillage.remplir.choix_produit_a_duppliquer;
import fr.smardine.matroussedemaquillage.remplir.formulaire_entree_page1bis;
import fr.smardine.matroussedemaquillage.variableglobale.EnTheme;

public class Main extends Activity implements OnClickListener {
	ImageView BtRemplir, BtPerim�, BtDuppliquer, BtNotes;
	Intent intentFormPage1, intentRecherche, intentDupplique, intentParametres, intentNote;
	AlertDialog.Builder adSortie, adHelp,adInfoProduitPerim�;
	BDAcces objBd;
	Context ctx = Main.this;
	boolean nouveau = false, dupplique = false;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ExceptionHandler.register(this, "http://simon.mardine.free.fr/trousse_maquillage/test/server.php","ma_ptite_trousse");
		
		adInfoProduitPerim� = new AlertDialog.Builder(this);
		adInfoProduitPerim�.setTitle("Alerte");
		adInfoProduitPerim�.setMessage("Un ou plusieur(s) produit(s) sont perim�(s) ou arrivent a leur date de permeption, voulez vous afficher ces produits?\n"
						+ "Vous pouvez d�sactiver cette alerte en passant par le bouton \"menu\" puis \"parametre\"");
		adInfoProduitPerim�.setPositiveButton("Oui", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int id) {
				
				Intent intentRecherche = new Intent(Main.this, recherchetabsbuttons.class);
				intentRecherche.putExtra("calledFromMain", true);
				// on demarre la nouvelle activit�
				startActivity(intentRecherche);
				finish();

			}
		});
		adInfoProduitPerim�.setNegativeButton("Non", null);
		
		adSortie = new AlertDialog.Builder(ctx);
		adSortie.setTitle("Petite v�rification");
		adSortie.setIcon(R.drawable.ad_question);
		adSortie.setMessage("Souhaitez vous quitter ma p'tite trousse?");
		adSortie.setPositiveButton("Oui", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				// popUp("Ok, c'est bon, k'utilisateur confirme");
				finish();
				onStop();
				onDestroy();
				// a faire avant System.exit pour supprimer correctement toute les donn�es presentes en memoire
				System.runFinalizersOnExit(true);
				System.exit(0);

			}
		});
		adSortie.setNegativeButton("Non", null);

		this.setTitle("Ma p'tite trousse");

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
		menu.setGroupEnabled(4, true);

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
			// l'identifiant integer est moins gourmand en ressource que le string
			case 2000:
				// Toast.makeText(this, "Recherche", 1000).show();
				intentRecherche = new Intent(Main.this, recherchetabsbuttons.class);
				intentRecherche.putExtra("calledFromMain", true);
				// on demarre la nouvelle activit�
				startActivity(intentRecherche);
				finish();
				break;
			case 2001:
				// Toast.makeText(this, "Param�tres", 1000).show();
				intentParametres = new Intent(Main.this, tab_param.class);
				// on demarre la nouvelle activit�
				startActivity(intentParametres);
				finish();
				break;
			case 2002:
				// Toast.makeText(this, "Notes", 1000).show();
				intentNote = new Intent(Main.this, note_page1.class);
				// on demarre la nouvelle activit�
				startActivity(intentNote);
				finish();
				break;
			case 2003:
				adHelp = new AlertDialog.Builder(this);
				adHelp.setTitle("Aide");
				adHelp.setIcon(R.drawable.ad_question);
				adHelp.setMessage("Le bouton Remplir vous permet de cr�er un nouveau produit.\n"
						+ "Le bouton P�rim� vous permet d'afficher la liste de vos produits p�rim�s (si il y en a).\n"
						+ "Le bouton Notes vous permettra d'acc�der aux notes.\n"
						+ "Le bouton \"Loupe\" de votre t�l�phone vous donnera acc�s a la recherche de produit.\n"
						+ "Le bouton \"Menu\" de votre t�l�phone vous fera apparaitre les options ainsi que des raccourcis.\n"
						+ "Les boutons \"Menu\" et \"Loupe\" de votre t�l�phone sont actifs dans toutes les fen�tres de l'application.");
				adHelp.setPositiveButton("Ok", null);
				adHelp.show();
		}
		Log.i("", "" + item.getTitle());
		return super.onOptionsItemSelected(item);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public void onClick(View v) {

		if (v == BtRemplir) {
			objBd = new BDAcces(ctx);
			objBd.open();
			int nbDenregistrement = objBd.renvoi_nbChamp("produit_Enregistre");
			objBd.close();
			if (nbDenregistrement > 0) {
				AlertDialog.Builder adChoixDupplique = new AlertDialog.Builder(this);
				adChoixDupplique.setTitle("Que voulez vous faire ?");
				CharSequence[] items = { "Ajouter un produit", "Dupliquer un produit" };
				adChoixDupplique.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int item) {
						/* User clicked on a radio button do some stuff */
						if (item == 0) {// afficher les details
							nouveau = true;
						}
						if (item == 1) {// supprimer le produit
							dupplique = true;
						}

					}
				});
				adChoixDupplique.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int id) {

						if (nouveau) {// on rentre un nouveau produit
							intentFormPage1 = new Intent(Main.this, formulaire_entree_page1bis.class);
							intentFormPage1.putExtra("calledFromMain", true);
							// on demarre la nouvelle activit�
							startActivity(intentFormPage1);
							finish();
						}
						if (dupplique) {// on dupplique un produit existant
							intentDupplique = new Intent(Main.this, choix_produit_a_duppliquer.class);
							intentDupplique.putExtra("calledFromMain", true);
							// on demarre la nouvelle activit�
							startActivity(intentDupplique);
							finish();
						}
					}
				});
				adChoixDupplique.show();
			} else {
				intentFormPage1 = new Intent(Main.this, formulaire_entree_page1bis.class);
				intentFormPage1.putExtra("calledFromMain", true);
				// on demarre la nouvelle activit�
				startActivity(intentFormPage1);
				finish();
			}

		}
		if (v == BtDuppliquer) {// on verifie si on a au moins un enregistrement, si oui, on permet la dpplication,
			// sinon, on indique a l'utilisateur que la dupplication est impossible
			objBd = new BDAcces(ctx);
			objBd.open();
			int nbDenregistrement = objBd.renvoi_nbChamp("produit_Enregistre");

			if (nbDenregistrement <= 0) {

				AlertDialog.Builder adAucunProduit = new AlertDialog.Builder(this);
				adAucunProduit = new AlertDialog.Builder(this);
				adAucunProduit.setTitle("Pour information");
				adAucunProduit.setMessage("Il n' y a pas encore de produit enregistr� dans Ma p'tite trousse.\n"
						+ "Cette fonction sera accessible quand vous aurez rentr� au moins un produit.");
				adAucunProduit.setPositiveButton("Ok", null);
				adAucunProduit.show();

			} else {
				intentDupplique = new Intent(Main.this, choix_produit_a_duppliquer.class);
				intentDupplique.putExtra("calledFromMain", true);
				// on demarre la nouvelle activit�
				startActivity(intentDupplique);
				finish();
			}
			objBd.close();

		}
		if (v == BtNotes) {
			intentNote = new Intent(Main.this, note_page1.class);
			intentNote.putExtra("calledFromMain", true);
			startActivity(intentNote);
			finish();

		}
		if (v == BtPerim�) {
			objBd = new BDAcces(ctx);
			objBd.open();
			String[] Colonnes = { "id_produits", "nom_produit", "Date_Peremption", "nom_marque" };
			String condition = "IS_PERIME=? or IS_PRESQUE_PERIME=?";
			String[] args = { "true", "true" };

			ArrayList[] ListeProduits = objBd.renvoi_liste_TrousseFinal(Colonnes, "id_produits", "", condition, args);
			int nbdobjet = ListeProduits[0].size();
			if (nbdobjet != 0) {
				intentRecherche = new Intent(Main.this, recherchetabsbuttons.class);
				intentRecherche.putExtra("calledFromMain", true);
				intentRecherche.putExtra("AfficheProduitPerim�", true);
				// on demarre la nouvelle activit�
				startActivity(intentRecherche);
				finish();
			} else {
				AlertDialog.Builder adAucunProduit = new AlertDialog.Builder(this);
				adAucunProduit = new AlertDialog.Builder(this);
				adAucunProduit.setTitle("Pour information");
				adAucunProduit.setMessage("Aucun de vos produit n'est p�rim� actuellement");
				adAucunProduit.setPositiveButton("Ok", null);
				adAucunProduit.show();

			}

		}
	}

	/**
	 * Ex�cut� que l'activit� arr�t�e via un "stop" red�marre. La fonction onRestart() est suivie de la fonction onStart().
	 */
	@Override
	protected void onRestart() {
		super.onRestart();
		// popUp("onRestart()");
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			adSortie.show();
			return true;
		}
		if (keyCode == KeyEvent.KEYCODE_SEARCH) {
			intentRecherche = new Intent(Main.this, recherchetabsbuttons.class);
			intentRecherche.putExtra("calledFromMain", true);
			// on demarre la nouvelle activit�
			startActivity(intentRecherche);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * Ex�cut� lorsque l'activit� devient visible � l'utilisateur. La fonction onStart() est suivie de la fonction onResume().
	 */
	@Override
	protected void onStart() {
		super.onStart();
		// updateUI();

		// popUp("onStart()");
	}

	/**
	 * Ex�cut�e a chaque passage en premier plan de l'activit�. Ou bien, si l'activit� passe � nouveau en premier (si une autre activit�
	 * �tait pass� en premier plan entre temps). La fonction onResume() est suivie de l'ex�cution de l'activit�.
	 */
	@Override
	protected void onResume() {
		super.onResume();
		/*
		 * handler.removeCallbacks(updateTimeTask); handler.postDelayed(updateTimeTask, 1000);
		 */

		ChoisiLeTheme();
		
		boolean isLaunchFromEntrypoint = getIntent().getBooleanExtra("calledFromEntryPoint", false);
		if (isLaunchFromEntrypoint){
			boolean isMessageAlerteAAfficher = getIntent().getBooleanExtra("AfficheProduitPerim�", false);
			if (isMessageAlerteAAfficher){
				adInfoProduitPerim�.show();
			}
		}

		/*
		 * AlphaAnimation anim11 = new AlphaAnimation(1, 0.2f); anim11.setDuration (5000); BtRemplir.startAnimation (anim11);
		 */

	}

	@SuppressWarnings("rawtypes")
	private void ChoisiLeTheme() {
		// TODO Auto-generated method stub
		objBd = new BDAcces(ctx);
		objBd.open();
		String[] champ = { "AfficheAlerte", "DureeViePeremp", "Theme" };
		ArrayList[] Param = objBd.renvoi_param(champ);
		objBd.close();

		String nomThemeChoisi = Param[2].get(0).toString().trim();

		if (EnTheme.Bisounours.getLib().equals(nomThemeChoisi)) {
			setContentView(R.layout.theme_bisounours_main);
			BtRemplir = (ImageView) ((Activity) ctx).findViewById(R.id.IvBouton1);
			BtPerim� = (ImageView) ((Activity) ctx).findViewById(R.id.IvBouton2);
			// BtDuppliquer=(ImageView)ctx.findViewById(R.id.IvBouton2);
			BtNotes = (ImageView) ((Activity) ctx).findViewById(R.id.IvBouton3);

			BtRemplir.setOnClickListener(this);
			BtPerim�.setOnClickListener(this);
			BtNotes.setOnClickListener(this);

			Animlineaire anim = new Animlineaire();
			anim.setDroiteversGauche(250);
			Animlineaire anim1 = new Animlineaire();
			anim1.setDroiteversGauche(750);
			Animlineaire anim2 = new Animlineaire();
			anim2.setDroiteversGauche(500);
			Animlineaire anim3 = new Animlineaire();
			anim3.setBasversHaut(400);

			BtRemplir.startAnimation(anim.getAnim());
			BtPerim�.startAnimation(anim1.getAnim());

			BtNotes.startAnimation(anim3.getAnim());
		}
		if (EnTheme.Classique.getLib().equals(nomThemeChoisi)) {
			setContentView(R.layout.main);
			BtRemplir = (ImageView) ((Activity) ctx).findViewById(R.id.IvRemplir);
			BtPerim� = (ImageView) ((Activity) ctx).findViewById(R.id.IvPerime);
			BtDuppliquer = (ImageView) ((Activity) ctx).findViewById(R.id.IvDuppliquer);
			BtNotes = (ImageView) ((Activity) ctx).findViewById(R.id.IvNote);

			BtRemplir.setOnClickListener(this);
			BtPerim�.setOnClickListener(this);
			BtDuppliquer.setOnClickListener(this);
			BtNotes.setOnClickListener(this);

			Animlineaire anim = new Animlineaire();
			anim.setDroiteversGauche(250);
			Animlineaire anim1 = new Animlineaire();
			anim1.setDroiteversGauche(750);
			Animlineaire anim2 = new Animlineaire();
			anim2.setDroiteversGauche(500);
			Animlineaire anim3 = new Animlineaire();
			anim3.setBasversHaut(400);

			BtRemplir.startAnimation(anim.getAnim());
			BtPerim�.startAnimation(anim1.getAnim());
			BtDuppliquer.startAnimation(anim2.getAnim());
			BtNotes.startAnimation(anim3.getAnim());

		}
		if (EnTheme.Fleur.getLib().equals(nomThemeChoisi)) {
			setContentView(R.layout.theme_fleur_main);
			BtRemplir = (ImageView) ((Activity) ctx).findViewById(R.id.IvBouton1);
			BtPerim� = (ImageView) ((Activity) ctx).findViewById(R.id.IvBouton2);
			// BtDuppliquer=(ImageView)ctx.findViewById(R.id.IvBouton2);
			BtNotes = (ImageView) ((Activity) ctx).findViewById(R.id.IvBouton3);

			BtRemplir.setOnClickListener(this);
			BtPerim�.setOnClickListener(this);
			BtNotes.setOnClickListener(this);

			Animlineaire anim = new Animlineaire();
			anim.setDroiteversGauche(250);
			Animlineaire anim1 = new Animlineaire();
			anim1.setDroiteversGauche(750);
			Animlineaire anim2 = new Animlineaire();
			anim2.setDroiteversGauche(500);
			Animlineaire anim3 = new Animlineaire();
			anim3.setBasversHaut(400);

			BtRemplir.startAnimation(anim.getAnim());
			BtPerim�.startAnimation(anim1.getAnim());

			BtNotes.startAnimation(anim3.getAnim());
		}

		// si l'utilisateur a mis n'imlporte quoi comme valeur pour ce champs (>99)
		// on refixe arbitrairement la valeur � 30.
	}

	/**
	 * La fonction onStop() est ex�cut�e : - lorsque l'activit� n'est plus en premier plan - ou bien lorsque l'activit� va �tre d�truite
	 * Cette fonction est suivie : - de la fonction onRestart() si l'activit� passe � nouveau en premier plan - de la fonction onDestroy()
	 * lorsque l'activit� se termine ou bien lorsque le syst�me d�cide de l'arr�ter
	 */
	@Override
	protected void onStop() {
		super.onStop();
		// handler.removeCallbacks(updateTimeTask);
	}

	/**
	 * La fonction onPause() est suivie : - d'un onResume() si l'activit� passe � nouveau en premier plan - d'un onStop() si elle devient
	 * invisible � l'utilisateur L'ex�cution de la fonction onPause() doit �tre rapide, car la prochaine activit� ne d�marrera pas tant que
	 * l'ex�cution de la fonction onPause() n'est pas termin�e.
	 */
	@Override
	protected void onPause() {
		super.onPause();

		if (isFinishing()) {// si le SYSTEME detecte que l'on sort de l'application
			popUp("onPause, l'utilisateur � demand� la fermeture via un finish()");

			finish();
			onStop();
			onDestroy();
			// a faire avant System.exit pour supprimer correctement toute les donn�es presentes en memoire
			System.runFinalizersOnExit(true);
			System.exit(0);
		} else {// sinon, on pert juste le "focus sur l'appli (lors d'un appel telephonique entrant par exemple)

		}
	}

	public void OnDestroy() {
		super.onDestroy();
		/*
		 * if ( handler != null ) handler.removeCallbacks(updateTimeTask); handler = null;
		 */

	}

	public void popUp(String message) {
		// Toast.makeText(this, message, 1).show();
	}

}
