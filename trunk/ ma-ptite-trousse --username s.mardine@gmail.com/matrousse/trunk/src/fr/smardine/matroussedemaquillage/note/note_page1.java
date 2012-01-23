package fr.smardine.matroussedemaquillage.note;

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
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import fr.smardine.matroussedemaquillage.Main;
import fr.smardine.matroussedemaquillage.R;
import fr.smardine.matroussedemaquillage.base.BDAcces;
import fr.smardine.matroussedemaquillage.base.accesTable.AccesTableNotes;
import fr.smardine.matroussedemaquillage.base.accesTable.AccesTableParams;
import fr.smardine.matroussedemaquillage.mdl.MlListeNote;
import fr.smardine.matroussedemaquillage.note.noteListAdapter.ViewHolder;
import fr.smardine.matroussedemaquillage.param.tab_param;
import fr.smardine.matroussedemaquillage.recherche.Recherche;
import fr.smardine.matroussedemaquillage.variableglobale.ActivityParam;
import fr.smardine.matroussedemaquillage.variableglobale.EnTheme;

public class note_page1 extends Activity implements OnItemClickListener,
		OnClickListener, OnItemLongClickListener {

	// ArrayList<produitNote> produitNote = new ArrayList<produitNote>();
	ImageView BtAddNote, BtSupprTtteNote;
	ListView NoteListView;
	AlertDialog.Builder adTitre, adSupprNote, adHelp;
	Intent intentSaisieNote;
	noteListAdapter adpt;
	private BDAcces objBd;
	// AlertDialog.Builder adPlusieurCat,adAucuneCat;
	String Txt01, Txt02;
	String Id = "";
	String Titre = "";

	int VISIBLE = 1, INVISIBLE = 4, GONE = 8;

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ExceptionHandler.register(this,
		// "http://simon.mardine.free.fr/trousse_maquillage/test/server.php","ma_ptite_trousse");
		ChoisiLeTheme();

		NoteListView = (ListView) this.findViewById(R.id.produitListViewNote);
		BtAddNote = (ImageView) this.findViewById(R.id.IvAddNote);
		BtSupprTtteNote = (ImageView) findViewById(R.id.IvJeterNote);

		NoteListView.setOnItemClickListener(this);
		NoteListView.setOnItemLongClickListener(this);

		BtAddNote.setOnClickListener(this);
		BtSupprTtteNote.setOnClickListener(this);

		objBd = new BDAcces(this);
		this.setTitle("Notes");

		// popUp("OnCreate-pageDupplique");
		AfficheLeContenu("Tout", NoteListView);

	}

	/**
	 * 
	 */
	private void ChoisiLeTheme() {

		AccesTableParams accesParam = new AccesTableParams(this);
		switch (accesParam.getThemeChoisi()) {
			case Bisounours:
				setContentView(R.layout.theme_bisounours_note_page1);

				break;
			case Classique:
				accesParam.majTheme(EnTheme.Fleur);
				ChoisiLeTheme();
				break;
			case Fleur:
				setContentView(R.layout.theme_fleur_note_page1);
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
		// setContentView(R.layout.theme_bisounours_note_page1);
		//
		// }
		// if (EnTheme.Classique.getLib().equals(nomThemeChoisi)) {
		// // setContentView(R.layout.note_page1);
		// AccesTableParams accesParam = new AccesTableParams(this);
		// accesParam.majTheme(EnTheme.Fleur);
		// ChoisiLeTheme();
		//
		// }
		// if (EnTheme.Fleur.getLib().equals(nomThemeChoisi)) {
		// setContentView(R.layout.theme_fleur_note_page1);
		// }

		// objBd.close();
	}

	private void onCreateMenu(Menu menu) {
		SubMenu recherche = menu.addSubMenu(1, 2000, 1, "Recherche");
		recherche.setIcon(R.drawable.menu_recherche);
		SubMenu parametre = menu.addSubMenu(3, 2001, 3, "Parametres");
		parametre.setIcon(R.drawable.menu_param); // icone systeme
		SubMenu help = menu.addSubMenu(4, 2003, 4, "Aide");
		help.setIcon(R.drawable.ad_question);
		menu.setGroupEnabled(1, true);
		menu.setGroupEnabled(2, false);
		menu.setGroupEnabled(3, true);
		menu.setGroupEnabled(4, true);
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
			case 2000:
				Toast.makeText(this, "Recherche", 1000).show();
				Intent intentRecherche = new Intent(this, Recherche.class);
				intentRecherche.putExtra(ActivityParam.LaunchFromNotePage1,
						true);
				// on demarre la nouvelle activit�
				startActivity(intentRecherche);
				termineActivity();
				break;
			case 2001:
				Toast.makeText(this, "Param�tres", 1000).show();
				Intent intentParametres = new Intent(this, tab_param.class);
				intentParametres.putExtra(ActivityParam.LaunchFromNotePage1,
						true);
				// on demarre la nouvelle activit�
				startActivity(intentParametres);
				termineActivity();
				break;

			case 2003:
				AlertDialog.Builder adHelp = new AlertDialog.Builder(this);
				adHelp.setTitle("Aide");
				adHelp.setIcon(R.drawable.ad_question);
				adHelp.setMessage("En cliquant sur une des notes vous afficherez le d�tail de celle ci.\n"
						+ "En appuyant longtemps sur une note, vous pourrez la supprimer.\n"
						+ "L'icone en forme de poubelle vous permettra de supprimer toutes les notes.\n"
						+ "L'icone en forme de + vous permettra de cr�er une note.");
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

	private void AfficheLeContenu(String TypeRecherche, ListView produitListView) {
		MlListeNote lstNote = null;
		// objBd.open();

		if (TypeRecherche.equals("Tout")) {
			AccesTableNotes accesNote = new AccesTableNotes(
					getApplicationContext());
			lstNote = accesNote.getListeNote();
			if (lstNote.size() == 0) {
				AlertDialog.Builder adAlertNoNotes = new AlertDialog.Builder(
						this);
				adAlertNoNotes.setTitle("Pour Information");
				adAlertNoNotes
						.setMessage("Aucune note n'est encore enregistr�e");
				adAlertNoNotes.setIcon(R.drawable.ad_attention);
				adAlertNoNotes.setNegativeButton("Ok", null);
				adAlertNoNotes.show();
				BtSupprTtteNote.setVisibility(INVISIBLE);
			} else {
				BtSupprTtteNote.setVisibility(VISIBLE);
			}

			// String[] Colonnes = { "id_note", "Titre" };
			//
			// ArrayList[] ListeProduits = objBd.renvoi_liste_Note(Colonnes,
			// "id_note", "", "", null);
			// int nbdobjet = ListeProduits[0].size();
			// if (nbdobjet != 0) {
			// for (int j = 0; j < nbdobjet; j++) {
			// String IdProduit = ListeProduits[0].get(j).toString();
			// String NomProduits = ListeProduits[1].get(j).toString();
			// produitNote2.add(new produitNote(IdProduit, NomProduits));
			// }
			// BtSupprTtteNote.setVisibility(VISIBLE);
			// } else {
			// AlertDialog.Builder adAlertNoNotes = new AlertDialog.Builder(
			// this);
			// adAlertNoNotes.setTitle("Pour Information");
			// adAlertNoNotes
			// .setMessage("Aucune note n'est encore enregistr�e");
			// adAlertNoNotes.setIcon(R.drawable.ad_attention);
			// adAlertNoNotes.setNegativeButton("Ok", null);
			// adAlertNoNotes.show();
			// BtSupprTtteNote.setVisibility(INVISIBLE);
			//
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
		adpt = new noteListAdapter(this, lstNote);
		// param�ter l'adapter sur la listview
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
		// popUp("onStop-Page1");
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
			Main.putExtra(ActivityParam.LaunchFromNotePage1, true);
			startActivity(Main);
			termineActivity();
			return true;
		}
		if (keyCode == KeyEvent.KEYCODE_SEARCH) {
			Intent intentRecherche = new Intent(this, Recherche.class);
			// on demarre la nouvelle activit�
			startActivity(intentRecherche);
			termineActivity();
		}
		return super.onKeyDown(keyCode, event);
	}

	public void OnDestroy() {
		// popUp("OnDestroy-Page1");
		super.onDestroy();

	}

	@Override
	public void onItemClick(AdapterView<?> Parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		// int Itemposition = Parent.getSelectedItemPosition();

		// int ChildCount = Parent.getChildCount();
		// View view1 = Parent.getChildAt(position);

		ViewHolder holder = (ViewHolder) view.getTag();

		Txt01 = (String) holder.TvIdNote.getText();
		Txt02 = (String) holder.TvTitreNote.getText();

		intentSaisieNote = new Intent(note_page1.this, note_saisie.class);
		intentSaisieNote.putExtra(ActivityParam.IdNote, Txt01);
		startActivity(intentSaisieNote);
		termineActivity();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == BtAddNote) {
			popUp("Ajouter note");

			final EditText inputProduit = new EditText(this);
			adTitre = new AlertDialog.Builder(this);
			adTitre.setTitle("Titre");
			adTitre.setMessage("Veuillez renseigner le titre de la note");

			adTitre.setView(inputProduit);
			adTitre.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
								int whichButton) {
							String value = inputProduit.getText().toString();
							// objBd.open();
							// MlNote note = new MlNote();
							// note.setTitre(value);
							// note.setMessage("");
							AccesTableNotes accesNotes = new AccesTableNotes(
									getApplicationContext());
							boolean succes = accesNotes
									.createNewNote(value, "");
							// ContentValues values = new ContentValues();
							// values.put("Titre", value);
							// values.put("Message", " ");
							// boolean succes = objBd.InsertDonn�edansTable(
							// "Notes", values);
							if (succes) {
								popUp("insertReussi");
							}
							// produitNote.removeAll(produitNote);
							AfficheLeContenu("Tout", NoteListView);
						}
					});
			adTitre.setNegativeButton("Annuler",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
								int whichButton) {
							// Canceled.
						}
					});
			adTitre.show();

		}
		if (v == BtSupprTtteNote) {
			popUp("Supprimer ttes les notes");

			adSupprNote = new AlertDialog.Builder(this);
			adSupprNote.setTitle("Question");
			adSupprNote
					.setMessage("Confirmer vous la suppression de toutes les notes?");
			adSupprNote.setIcon(R.drawable.ad_question);

			adSupprNote.setPositiveButton("Oui",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
								int whichButton) {

							// objBd.open();
							int nbChanmpSupprime = objBd.deleteTable("Notes",
									"1", null);

							if (nbChanmpSupprime > 0) {
								popUp("nb de notes supprimees: "
										+ nbChanmpSupprime);
							}
							// produitNote.removeAll(produitNote);
							AfficheLeContenu("Tout", NoteListView);
						}
					});
			adSupprNote.setNegativeButton("Annuler",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
								int whichButton) {
							// Canceled.
						}
					});
			adSupprNote.show();
		}

	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View view,
			int position, long id) {
		ViewHolder holder = (ViewHolder) view.getTag();

		Txt01 = (String) holder.TvIdNote.getText();
		Txt02 = (String) holder.TvTitreNote.getText();

		final AlertDialog.Builder dial = new AlertDialog.Builder(this);
		dial.setTitle("Suppression");
		dial.setIcon(R.drawable.ad_question);
		dial.setMessage("Confirmez vous la suppression de la note suivante: "
				+ "" + Txt02);

		dial.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int id) {
				gotoSupprNote(Txt01);
				// gotoDuppliqueEtLanceFormPage3(Txt01);
				// popUp("Cliqu� sur l'Id "+Txt01+" Titre "+Txt02);

			}
		});
		dial.setNegativeButton("Non", null);
		dial.show();
		return false;
	}

	@SuppressWarnings("unused")
	protected void gotoSupprNote(String idNote) {
		// TODO Auto-generated method stub
		String whereClause = "id_note=?";
		String[] WhereArgs = new String[] { idNote };

		// objBd.open();
		int nbChampEffac� = objBd.deleteTable("Notes", whereClause, WhereArgs);

		// produitNote.removeAll(produitNote);
		AfficheLeContenu("Tout", NoteListView);
		// objBd.close();
	}

}
