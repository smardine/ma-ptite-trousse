package fr.smardine.matroussedemaquillage.remplir;

import helper.SerialisableHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.apis.animation.Animlineaire;

import fr.smardine.matroussedemaquillage.Main;
import fr.smardine.matroussedemaquillage.R;
import fr.smardine.matroussedemaquillage.alertDialog.AlertDialogFactory;
import fr.smardine.matroussedemaquillage.alertDialog.type.EnTypeAlertDialogAttention;
import fr.smardine.matroussedemaquillage.alertDialog.type.EnTypeAlertDialogChoixCat;
import fr.smardine.matroussedemaquillage.alertDialog.type.EnTypeAlertDialogOuiNon;
import fr.smardine.matroussedemaquillage.base.accesTable.AccesTableParams;
import fr.smardine.matroussedemaquillage.base.accesTable.AccesTableTrousseMarque;
import fr.smardine.matroussedemaquillage.base.accesTable.AccesTableTrousseProduits;
import fr.smardine.matroussedemaquillage.mdl.MlCategorie;
import fr.smardine.matroussedemaquillage.mdl.MlListeMarque;
import fr.smardine.matroussedemaquillage.mdl.MlProduit;
import fr.smardine.matroussedemaquillage.mdl.cat.EnCategorie;
import fr.smardine.matroussedemaquillage.note.note_page1;
import fr.smardine.matroussedemaquillage.param.tab_param;
import fr.smardine.matroussedemaquillage.recherche.Recherche;
import fr.smardine.matroussedemaquillage.variableglobale.ActivityParam;
import fr.smardine.matroussedemaquillage.variableglobale.EnTheme;

/**
 * @author smardine
 */
public class formulaire_entree_page1bis extends Activity implements
		OnClickListener {

	private Button BoutonValider;
	private ImageView BtVisage, BtYeux, BtLevres, BtAutres;
	private AutoCompleteTextView textViewMarque;

	private AlertDialog.Builder adPlusieurCat, adAucuneCat, adNouvelleMarque,
			adAucuneMarque;;
	// String MarqueChoisie = "";
	// String DureeVie = "";
	// String DateChoisie = "";
	// String numTeinte = "";
	// String nomProduitRecup = "";
	private Intent intentRecherche, intentParametres, intentNote;
	// String[] Marque;
	// private ContentValues modifiedValues;
	// private String whereClause;
	static EnCategorie categorieChoisie;
	private MlProduit produit;
	private AlertDialogFactory af;

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// ExceptionHandler.register(this,
		// "http://simon.mardine.free.fr/trousse_maquillage/test/server.php","ma_ptite_trousse");
		ChoisiLeTheme();

		BtVisage = (ImageView) this.findViewById(R.id.ImageViewVisage_page1);
		BtYeux = (ImageView) this.findViewById(R.id.ImageViewYeux_page1);
		BtLevres = (ImageView) findViewById(R.id.ImageViewLevres_page1);
		BtAutres = (ImageView) this.findViewById(R.id.ImageViewAutres_page1);
		BoutonValider = (Button) this.findViewById(R.id.ButtonValider2_Page1);
		textViewMarque = (AutoCompleteTextView) findViewById(R.id.autocomplete_marque_Page1);

		BtVisage.setOnClickListener(this);
		BtYeux.setOnClickListener(this);
		BtLevres.setOnClickListener(this);
		BtAutres.setOnClickListener(this);
		BoutonValider.setOnClickListener(this);

		AlertDialogFactory adFact = new AlertDialogFactory(this);
		adPlusieurCat = adFact
				.getAttentionDialog(EnTypeAlertDialogAttention.PLUSIEUR_CAT);
		//
		adAucuneCat = adFact
				.getAttentionDialog(EnTypeAlertDialogAttention.AUCUNE_CAT);
		// adAucuneCat = new AlertDialog.Builder(this);
		// adAucuneCat.setTitle("Attention");
		// adAucuneCat.setIcon(R.drawable.ad_attention);
		// adAucuneCat.setMessage("Vous n'avez selectionné aucune categorie. \n"
		// + "Merci d'en choisir au moins une.");
		// adAucuneCat.setPositiveButton("Ok", null);

		adAucuneMarque = adFact
				.getAttentionDialog(EnTypeAlertDialogAttention.AUCUNE_MARQUE);
		// adAucuneMarque = new AlertDialog.Builder(this);
		// adAucuneMarque.setTitle("Attention");
		// adAucuneMarque.setIcon(R.drawable.ad_attention);
		// adAucuneMarque
		// .setMessage("Vous n'avez rentré aucune marque \nMerci d'en saisir une");
		// adAucuneMarque.setPositiveButton("Ok", null);
		adNouvelleMarque = adFact
				.getOuiNonDialog(EnTypeAlertDialogOuiNon.NOUVELLE_MARQUE);
		// adNouvelleMarque = new AlertDialog.Builder(this);
		// adNouvelleMarque.setIcon(R.drawable.ad_attention);
		// adNouvelleMarque.setTitle("Petite vérification");
		// adNouvelleMarque
		// .setMessage("Nouvelle marque\nCette marque est inconnue de \"Ma p'tite trousse\"\nSouhaitez vous la partager avec les autres utilisateurs? (Connexion Edge, 3G ou wifi requise)");
		adNouvelleMarque.setPositiveButton("Oui",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						// popUp("Ok, c'est bon, l'utilisateur confirme");
						PostMarqueSurServeur(textViewMarque.getText()
								.toString());

					}
				});
		// adNouvelleMarque.setNegativeButton("Non",
		// new DialogInterface.OnClickListener() {
		//
		// @Override
		// public void onClick(DialogInterface dialog, int which) {
		//
		// }
		// });
		// objBd = new BDAcces(this);
		this.setTitle("Choix de la catégorie et de la marque");

		popUp("OnCreate-page1");

		// objBd = new BDAcces(this);
		// objBd.open();

		// Marque = objBd.renvoi_liste_ValeurDansString("trousse_marques",
		// "nom_marque");

		AccesTableTrousseMarque accesMarque = new AccesTableTrousseMarque(this);
		MlListeMarque lstMarque = accesMarque.getListeMarques();
		String[] Marque = new String[lstMarque.size()];
		for (int i = 0; i < lstMarque.size(); i++) {
			Marque[i] = lstMarque.get(i).getNomMarque();
		}

		// objBd.close();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.list_item_marque_auto, Marque);
		textViewMarque.setAdapter(adapter);

	}

	/**
	 * 
	 */
	private void ChoisiLeTheme() {

		AccesTableParams accesParam = new AccesTableParams(this);
		switch (accesParam.getThemeChoisi()) {
			case Bisounours:
				setContentView(R.layout.theme_bisounours_formulaire_entree_page1bis);
				break;
			case Classique:
				accesParam.majTheme(EnTheme.Fleur);
				ChoisiLeTheme();
				break;
			case Fleur:
				setContentView(R.layout.theme_fleur_formulaire_entree_page1bis);
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
		// setContentView(R.layout.theme_bisounours_formulaire_entree_page1bis);
		//
		// }
		// if (EnTheme.Classique.getLib().equals(nomThemeChoisi)) {
		// // setContentView(R.layout.formulaire_entree_page1bis);
		// AccesTableParams accesParam = new AccesTableParams(this);
		// accesParam.majTheme(EnTheme.Fleur);
		// // ContentValues values = new ContentValues();
		// // values.put("Theme", EnTheme.Fleur.getLib());
		// //
		// // //objBd.open();
		// // objBd.majTable("Param", values, "", null);
		// // //objBd.close();
		// ChoisiLeTheme();
		//
		// }
		// if (EnTheme.Fleur.getLib().equals(nomThemeChoisi)) {
		// setContentView(R.layout.theme_fleur_formulaire_entree_page1bis);
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
				intentRecherche = new Intent(this, Recherche.class);
				intentRecherche.putExtra(ActivityParam.LaunchFromPage1, true);
				// on demarre la nouvelle activité
				startActivity(intentRecherche);
				termineActivity();
				break;
			case 2001:
				Toast.makeText(this, "Paramètres", 1000).show();
				intentParametres = new Intent(this, tab_param.class);

				intentParametres.putExtra(MlProduit.class.getCanonicalName(),
						SerialisableHelper.serialize(produit));

				// intentParametres.putExtra(ActivityParam.Marque,
				// textViewMarque
				// .getText().toString().trim());
				// intentParametres.putExtra(ActivityParam.DurreeDeVie,
				// DureeVie.trim());
				// intentParametres.putExtra(ActivityParam.DateAchat,
				// DateChoisie.trim());
				// intentParametres.putExtra(ActivityParam.NumeroDeTeinte,
				// numTeinte.trim());
				// intentParametres.putExtra(ActivityParam.NomProduit,
				// nomProduitRecup.trim());
				intentParametres.putExtra(ActivityParam.LaunchFromPage1, true);
				// on demarre la nouvelle activité
				startActivity(intentParametres);
				termineActivity();
				break;
			case 2002:
				Toast.makeText(this, "Notes", 1000).show();
				intentNote = new Intent(this, note_page1.class);
				// on demarre la nouvelle activité
				startActivity(intentNote);
				termineActivity();
				break;

		}
		Log.i("", "" + item.getTitle());
		return super.onOptionsItemSelected(item);
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
	 * Exécuté lorsque l'activité devient visible à l'utilisateur. La fonction
	 * onStart() est suivie de la fonction onResume().
	 */
	@Override
	protected void onStart() {
		super.onStart();
		// popUp("onStart()-Page1");
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

		boolean isCalledFromMain = getIntent().getBooleanExtra(
				ActivityParam.LaunchFromMain, false);
		boolean isCalledFromPageRecap = getIntent().getBooleanExtra(
				ActivityParam.LaunchFromPageRecap, false);
		boolean isCalledFromPageRecapBack = getIntent().getBooleanExtra(
				ActivityParam.LaunchFromPageRecapBack, false);
		// boolean isCalledFromDetail =
		// getIntent().getBooleanExtra(ActivityParam.LaunchFromAfficheDetail,
		// false);
		// boolean isCalledFromDupplique =
		// getIntent().getBooleanExtra(ActivityParam.LaunchFromDuppliquer,
		// false);
		boolean isCalledFromParam = getIntent().getBooleanExtra(
				ActivityParam.LaunchFromParam, false);
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
		if (isCalledFromMain || isCalledFromPageRecap) {
			// popUp("IscreatFormRecap: " + isCalledFromPageRecap);
			// popUp("IscreatFormMain: " + isCalledFromMain);

			AccesTableTrousseProduits accesTrousse = new AccesTableTrousseProduits(
					this);
			accesTrousse.reinitProduitChoisi();

		}

		if (isCalledFromParam || isCalledFromPageRecapBack) {
			byte[] extra = getIntent().getByteArrayExtra(
					MlProduit.class.getCanonicalName());
			Object o = SerialisableHelper.deserializeObject(extra);
			if (o instanceof MlProduit) {
				produit = (MlProduit) o;
				textViewMarque.setText(produit.getMarque());
				// DureeVie =
				// getIntent().getStringExtra(ActivityParam.DurreeDeVie)
				// .trim();
				// DateChoisie =
				// getIntent().getStringExtra(ActivityParam.DateAchat)
				// .trim();
				// numTeinte = getIntent()
				// .getStringExtra(ActivityParam.NumeroDeTeinte).trim();
				// nomProduitRecup = getIntent().getStringExtra(
				// ActivityParam.NomProduit).trim();

			}

			// textView.setText(MarqueChoisie);
			// Animlineaire anim = new Animlineaire();
			// anim.setDroiteversGauche(250);
			// Animlineaire anim1 = new Animlineaire();
			// anim1.setDroiteversGauche(300);
			// Animlineaire anim2 = new Animlineaire();
			// anim2.setDroiteversGauche(350);
			// Animlineaire anim3 = new Animlineaire();
			// anim3.setDroiteversGauche(400);
			//
			// BtVisage.startAnimation(anim);
			// BtYeux.startAnimation(anim1);
			// BtLevres.startAnimation(anim2);
			// BtAutres.startAnimation(anim3);
		}

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
		popUp("onStop-Page1");
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

			Intent Main = new Intent(this, Main.class);
			Main.putExtra(ActivityParam.LaunchFromPage1, true);
			startActivity(Main);
			termineActivity();
			return true;
		}
		if (keyCode == KeyEvent.KEYCODE_SEARCH) {
			intentRecherche = new Intent(this, Recherche.class);
			// on demarre la nouvelle activité
			startActivity(intentRecherche);
			termineActivity();
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

	protected void PostMarqueSurServeur(String p_marque) {
		String TAG = "fr.smardine.matroussedemaquillage.remplir";
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(
				"http://simon.mardine.free.fr/trousse_maquillage/nouveautes/postmarque.php");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();

		nvps.add(new BasicNameValuePair("marque", p_marque));
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

			// We don't care about the response, so we just hope it went well
			// and on
			// with it

			HttpResponse response = httpClient.execute(httpPost);

			BufferedReader reader = null;

			reader = new BufferedReader(new InputStreamReader(response
					.getEntity().getContent()));

			String strLine;

			while ((strLine = reader.readLine()) != null) {
				Log.d(TAG, "reponse du post : " + strLine);
			}
		} catch (IOException e) {

			Log.d(TAG, "IOException: " + e);
			return;
			// e.printStackTrace();
		} catch (IllegalStateException e1) {

			Log.d(TAG, "IllegalStateException: " + e1);
			return;
		}
	}

	@Override
	public void onClick(View v) {
		if (af == null) {
			af = new AlertDialogFactory(this);
		}

		// cette methode prend en charge le fait que le controle clicqué soit un
		// bouton de cateegorie ou pas.
		traiteBoutonCat(v);
		if (v == textViewMarque) {
			textViewMarque.setFocusable(true);
		} else if (v == BoutonValider) {

			// on recupere le nom de marque choisi par l'utilisateur
			if (textViewMarque.getText().toString().equals("")) {
				adAucuneMarque.show();
				return;
			}

			// on verifie qu'au moins une categorie est cochée.

			int nbCatCochee = new AccesTableTrousseProduits(this)
					.getNbProduitCochee();

			AccesTableTrousseMarque accesMarque = new AccesTableTrousseMarque(
					this);

			boolean isMarqueEnbase = accesMarque.isMarqueExist(textViewMarque
					.getText().toString());

			if (nbCatCochee > 1) {
				adPlusieurCat.show();
				return;
			} else if (nbCatCochee == 0) {
				adAucuneCat.show();
				return;
			} else if (!isMarqueEnbase) {
				accesMarque.majMarqueChoisi(
						textViewMarque.getText().toString(), false);
				accesMarque
						.createNewMarque(textViewMarque.getText().toString());

				adNouvelleMarque.show();
				return;
			} else {
				Intent intent = new Intent(formulaire_entree_page1bis.this,
						formulaire_entree_page3.class);
				if (produit == null) {
					produit = new MlProduit();
				}
				af.getChoixCatSingleClickListener();

				produit.setCategorie(new MlCategorie(af
						.getChoixCatSingleClickListener().getCategorieMere(),
						af.getChoixCatSingleClickListener()
								.getSousCategorieChoisie()));

				produit.setMarque(textViewMarque.getText().toString());
				// try {
				// p.setDureeVie(Integer.parseInt(DureeVie.trim()));
				// } catch (Exception e) {
				// p.setDureeVie(0);
				// }
				//
				// p.setDateAchat(DateHelper.getDateFromDatabase(DateChoisie
				// .trim()));
				// p.setTeinte(numTeinte.trim());
				// p.setNomProduit(nomProduitRecup.trim());

				intent.putExtra(MlProduit.class.getCanonicalName(),
						SerialisableHelper.serialize(produit));

				// intent.putExtra(ActivityParam.Marque, MarqueChoisie.trim());
				// intent.putExtra(ActivityParam.DurreeDeVie, DureeVie.trim());
				// intent.putExtra(ActivityParam.DateAchat, DateChoisie.trim());
				// intent.putExtra(ActivityParam.NumeroDeTeinte,
				// numTeinte.trim());
				// intent.putExtra(ActivityParam.NomProduit,
				// nomProduitRecup.trim());
				intent.putExtra(ActivityParam.LaunchFromPage1, true);
				startActivity(intent);
				termineActivity();
			}

		}

	}

	private void traiteBoutonCat(View p_v) {
		AlertDialog.Builder adChoixCat = null;
		if (p_v == BtVisage) {// si le bouton cliqué est le "BoutonVisage"
			adChoixCat = af.getChoixCatDialog(EnTypeAlertDialogChoixCat.VISAGE);
			adChoixCat.show();
			return;
		} else if (p_v == BtYeux) {
			adChoixCat = af.getChoixCatDialog(EnTypeAlertDialogChoixCat.YEUX);
			adChoixCat.show();
			return;
		} else if (p_v == BtLevres) {
			adChoixCat = af.getChoixCatDialog(EnTypeAlertDialogChoixCat.LEVRE);
			adChoixCat.show();
			return;
		} else if (p_v == BtAutres) {
			adChoixCat = af.getChoixCatDialog(EnTypeAlertDialogChoixCat.AUTRE);
			adChoixCat.show();
			return;
		}

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
	// private void majTable() {
	// // //objBd.open();
	// AccesTableTrousseProduits accesTrousseProds = new
	// AccesTableTrousseProduits(
	// this);
	//
	// accesTrousseProds.majSouscatChoisie(categorieChoisie);
	// // AccesTableTrousseTempo accestempo = new AccesTableTrousseTempo(this);
	// // accestempo.deleteTable();
	// // int nbdechamp = objBd.majTable("trousse_produits", modifiedValues,
	// // whereClause, categorieChoisie);
	// // System.out.println("Nombre de champ modifié : " + nbdechamp);
	// // objBd.deleteTable("trousse_tempo", "1", null);
	// // //objBd.close();
	//
	// }

	// /**
	// * @param p_categorie la categorie recherchée (Visage,Yeux,Levres...)
	// * @return
	// */
	// private String[] recupereSousCategorie(String p_categorie) {
	//
	// AccesTableTrousseProduits accesProduits = new AccesTableTrousseProduits(
	// this);
	// MlListeTrousseProduit ListeProduits = accesProduits
	// .getListeTrousseProduit(p_categorie);
	// String[] NomProduits = new String[ListeProduits.size()];
	//
	// for (int j = 0; j < ListeProduits.size(); j++) {
	// NomProduits[j] = ListeProduits.get(j).getNomSousCat().getLib();
	// }
	//
	// return NomProduits;
	// }
	//
	// private int recupereIndiceSousCategorieCochee(String p_categorie) {
	// int indiceProduitCoche = -1;
	// AccesTableTrousseProduits accesProduits = new AccesTableTrousseProduits(
	// this);
	// MlListeTrousseProduit ListeProduits = accesProduits
	// .getListeTrousseProduit(p_categorie);
	//
	// for (int j = 0; j < ListeProduits.size(); j++) {
	// // NomProduits[j] = ListeProduits.get(j).toString();
	// boolean isChecked = ListeProduits.get(j).isChecked();
	// if (isChecked) {
	// indiceProduitCoche = j;
	// break;
	// }
	// }
	// // objBd.close();
	// return indiceProduitCoche;
	// }

	// private boolean verfieAuMoinsUneCategorieSelectionnee() {
	// AccesTableTrousseProduits accesProduit = new AccesTableTrousseProduits(
	// this);
	//
	// MlListeTrousseProduit lstCatCochee = accesProduit
	// .getListeProduitCochee();
	// // objBd.open();
	// // ArrayList[] ListeCategorieCochée = objBd.renvoiCategorieCochée();
	// // int nbCategorieCochées = ListeCategorieCochée[0].size();
	// String NomProduits = "";
	// for (MlTrousseProduit tp : lstCatCochee) {
	// NomProduits = tp.getNomSousCat().getLib();
	// }
	// // for (int j = 0; j < nbCatCochee; j++) {
	// // NomProduits = ListeCategorieCochée[0].get(j).toString();
	// // }
	//
	// return ((lstCatCochee.size() == 1) && (!NomProduits.equals("aucun")));
	//
	// }

}
