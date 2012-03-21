package fr.smardine.matroussedemaquillage.remplir;

import helper.SerialisableHelper;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
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
import fr.smardine.matroussedemaquillage.note.note_page1;
import fr.smardine.matroussedemaquillage.param.tab_param;
import fr.smardine.matroussedemaquillage.recherche.Recherche;
import fr.smardine.matroussedemaquillage.variableglobale.ActivityParam;
import fr.smardine.matroussedemaquillage.variableglobale.EnTheme;

/**
 * @author smardine
 */
public class formulaire_entree_page1bis extends SuperActivity implements
		OnClickListener, IremplissageActivity {

	private Button BoutonValider;
	private ImageView BtVisage, BtYeux, BtLevres, BtAutres;
	private AutoCompleteTextView textViewMarque;

	private Intent intentRecherche, intentParametres, intentNote;

	private MlProduit produit;
	private AlertDialogFactory af;

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// ExceptionHandler.register(this,
		// "http://simon.mardine.free.fr/trousse_maquillage/test/server.php","ma_ptite_trousse");
		initComposantVisuel();

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
				transfereMlProduitToActivity(intentParametres);
				// intentParametres.putExtra(MlProduit.class.getCanonicalName(),
				// SerialisableHelper.serialize(produit));

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
	 * Exécutée a chaque passage en premier plan de l'activité. Ou bien, si
	 * l'activité passe à nouveau en premier (si une autre activité était passé
	 * en premier plan entre temps). La fonction onResume() est suivie de
	 * l'exécution de l'activité.
	 */
	@Override
	public void onResume() {
		super.onResume();

		boolean isCalledFromMain = getIntent().getBooleanExtra(
				ActivityParam.LaunchFromMain, false);
		boolean isCalledFromPageRecap = getIntent().getBooleanExtra(
				ActivityParam.LaunchFromPageRecap, false);
		boolean isCalledFromPageRecapBack = getIntent().getBooleanExtra(
				ActivityParam.LaunchFromPageRecapBack, false);

		boolean isCalledFromParam = getIntent().getBooleanExtra(
				ActivityParam.LaunchFromParam, false);

		if (isCalledFromMain || isCalledFromPageRecap) {
			AccesTableTrousseProduits accesTrousse = new AccesTableTrousseProduits(
					this);
			accesTrousse.reinitProduitChoisi();
		}

		if (isCalledFromParam || isCalledFromPageRecapBack) {
			recupereMlProduitfromPreviousActivity();
		}
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
				Builder adAucuneMarque = af
						.getAttentionDialog(EnTypeAlertDialogAttention.AUCUNE_MARQUE);
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

			//

			if (nbCatCochee > 1) {
				Builder adPlusieurCat = af
						.getAttentionDialog(EnTypeAlertDialogAttention.PLUSIEUR_CAT);
				adPlusieurCat.show();
				return;
			} else if (nbCatCochee == 0) {
				Builder adAucuneCat = af
						.getAttentionDialog(EnTypeAlertDialogAttention.AUCUNE_CAT);
				adAucuneCat.show();
				return;
			} else if (!isMarqueEnbase) {
				accesMarque.majMarqueChoisi(
						textViewMarque.getText().toString(), false);
				accesMarque
						.createNewMarque(textViewMarque.getText().toString());
				Builder adNouvelleMarque = af.getOuiNonDialog(
						EnTypeAlertDialogOuiNon.NOUVELLE_MARQUE, textViewMarque
								.getText().toString());
				adNouvelleMarque.show();
				return;
			} else {
				Intent intent = new Intent(formulaire_entree_page1bis.this,
						formulaire_entree_page3.class);
				transfereMlProduitToActivity(intent);

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

	@Override
	public MlProduit recupereMlProduitfromPreviousActivity() {
		byte[] extra = getIntent().getByteArrayExtra(
				MlProduit.class.getCanonicalName());
		Object o = SerialisableHelper.deserializeObject(extra);
		if (extra != null) {
			if (o instanceof MlProduit) {
				produit = (MlProduit) o;
				textViewMarque.setText(produit.getMarque());
			}
		}

		return produit;
	}

	@Override
	public void transfereMlProduitToActivity(Intent p_intent) {
		if (produit == null) {
			produit = new MlProduit();
		}
		if (af != null) {
			produit.setCategorie(new MlCategorie(af
					.getChoixCatSingleClickListener().getCategorieMere(), af
					.getChoixCatSingleClickListener().getSousCategorieChoisie()));

		}
		produit.setMarque(textViewMarque.getText().toString());

		p_intent.putExtra(MlProduit.class.getCanonicalName(),
				SerialisableHelper.serialize(produit));

	}

	@Override
	public void initComposantVisuel() {
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

		this.setTitle("Choix de la catégorie et de la marque");

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

	@Override
	public void ChoisiLeTheme() {

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
	}

}
