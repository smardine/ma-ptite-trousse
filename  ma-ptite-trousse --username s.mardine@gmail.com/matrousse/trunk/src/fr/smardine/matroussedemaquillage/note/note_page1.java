package fr.smardine.matroussedemaquillage.note;

import helper.SerialisableHelper;
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
import fr.smardine.matroussedemaquillage.base.accesTable.AccesTableNotes;
import fr.smardine.matroussedemaquillage.base.accesTable.AccesTableParams;
import fr.smardine.matroussedemaquillage.mdl.MlListeNote;
import fr.smardine.matroussedemaquillage.mdl.MlNote;
import fr.smardine.matroussedemaquillage.note.noteListAdapter.ViewHolder;
import fr.smardine.matroussedemaquillage.param.tab_param;
import fr.smardine.matroussedemaquillage.recherche.Recherche;
import fr.smardine.matroussedemaquillage.remplir.SuperActivity;
import fr.smardine.matroussedemaquillage.variableglobale.ActivityParam;
import fr.smardine.matroussedemaquillage.variableglobale.EnTheme;

/**
 * @author smardine
 */
public class note_page1 extends SuperActivity implements OnItemClickListener,
		OnClickListener, OnItemLongClickListener, INoteActivity {

	// ArrayList<produitNote> produitNote = new ArrayList<produitNote>();
	ImageView BtAddNote, BtSupprTtteNote;
	ListView NoteListView;
	AlertDialog.Builder adTitre, adSupprNote, adHelp;
	// Intent intentSaisieNote;
	noteListAdapter adpt;
	// private BDAcces objBd;
	// AlertDialog.Builder adPlusieurCat,adAucuneCat;
	String Txt01, Txt02;
	// String Id = "";
	// String Titre = "";

	int VISIBLE = 1, INVISIBLE = 4, GONE = 8;
	private MlNote note;

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ExceptionHandler.register(this,
		// "http://simon.mardine.free.fr/trousse_maquillage/test/server.php","ma_ptite_trousse");
		ChoisiLeTheme();

		initComposantVisuel();

		// objBd = new BDAcces(this);
		this.setTitle("Notes");

		// popUp("OnCreate-pageDupplique");
		AfficheLeContenu("Tout", NoteListView);

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
			case 2000:
				Toast.makeText(this, "Recherche", 1000).show();
				Intent intentRecherche = new Intent(this, Recherche.class);
				intentRecherche.putExtra(ActivityParam.LaunchFromNotePage1,
						true);
				// on demarre la nouvelle activité
				startActivity(intentRecherche);
				termineActivity();
				break;
			case 2001:
				Toast.makeText(this, "Paramètres", 1000).show();
				Intent intentParametres = new Intent(this, tab_param.class);
				intentParametres.putExtra(ActivityParam.LaunchFromNotePage1,
						true);
				// on demarre la nouvelle activité
				startActivity(intentParametres);
				termineActivity();
				break;

			case 2003:
				adHelp = new AlertDialog.Builder(this);
				adHelp.setTitle("Aide");
				adHelp.setIcon(R.drawable.ad_question);
				adHelp.setMessage("En cliquant sur une des notes vous afficherez le détail de celle ci.\n"
						+ "En appuyant longtemps sur une note, vous pourrez la supprimer.\n"
						+ "L'icone en forme de poubelle vous permettra de supprimer toutes les notes.\n"
						+ "L'icone en forme de + vous permettra de créer une note.");
				adHelp.setPositiveButton("Ok", null);
				adHelp.show();

		}
		Log.i("", "" + item.getTitle());
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 
	 */
	// private void termineActivity() {
	// finish();
	// }

	private void AfficheLeContenu(String p_typeRecherche,
			ListView p_produitListView) {
		MlListeNote lstNote = null;

		if (p_typeRecherche.equals("Tout")) {
			AccesTableNotes accesNote = new AccesTableNotes(
					getApplicationContext());
			lstNote = accesNote.getListeNote();
			if (lstNote.size() == 0) {
				AlertDialog.Builder adAlertNoNotes = new AlertDialog.Builder(
						this);
				adAlertNoNotes.setTitle("Pour Information");
				adAlertNoNotes
						.setMessage("Aucune note n'est encore enregistrée");
				adAlertNoNotes.setIcon(R.drawable.ad_attention);
				adAlertNoNotes.setNegativeButton("Ok", null);
				adAlertNoNotes.show();
				BtSupprTtteNote.setVisibility(INVISIBLE);
			} else {
				BtSupprTtteNote.setVisibility(VISIBLE);
			}

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
		p_produitListView.setLayoutAnimation(controller);

		// paramètrer l'adapteur correspondant
		adpt = new noteListAdapter(this, lstNote);
		// paramèter l'adapter sur la listview
		p_produitListView.setAdapter(adpt);

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
			// on demarre la nouvelle activité
			startActivity(intentRecherche);
			termineActivity();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onItemClick(AdapterView<?> p_Parent, View p_view,
			int p_position, long p_id) {
		// TODO Auto-generated method stub
		// int Itemposition = Parent.getSelectedItemPosition();

		// int ChildCount = Parent.getChildCount();
		// View view1 = Parent.getChildAt(position);

		ViewHolder holder = (ViewHolder) p_view.getTag();

		Txt01 = (String) holder.TvIdNote.getText();
		Txt02 = (String) holder.TvTitreNote.getText();
		note = new MlNote(Integer.parseInt(Txt01), this);
		Intent intentSaisieNote = new Intent(note_page1.this, note_saisie.class);
		transfereMlNoteToActivity(intentSaisieNote);
		// intentSaisieNote.putExtra(ActivityParam.IdNote, Txt01);
		startActivity(intentSaisieNote);
		termineActivity();

	}

	@Override
	public void onClick(final View v) {
		// TODO Auto-generated method stub
		if (v.equals(BtAddNote)) {
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
							// boolean succes = objBd.InsertDonnéedansTable(
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
		if (v.equals(BtSupprTtteNote)) {
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
							AccesTableNotes accesNote = new AccesTableNotes(v
									.getContext());
							accesNote.deleteTable();
							// objBd.open();
							// int nbChanmpSupprime = objBd.deleteTable("Notes",
							// "1", null);
							//
							// if (nbChanmpSupprime > 0) {
							// popUp("nb de notes supprimees: "
							// + nbChanmpSupprime);
							// }
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
				// popUp("Cliqué sur l'Id "+Txt01+" Titre "+Txt02);

			}
		});
		dial.setNegativeButton("Non", null);
		dial.show();
		return false;
	}

	protected void gotoSupprNote(String idNote) {
		AccesTableNotes accesNote = new AccesTableNotes(this);
		accesNote.deleteNote(Integer.parseInt(idNote));
		// TODO Auto-generated method stub
		// String whereClause = "id_note=?";
		// String[] WhereArgs = new String[] { idNote };
		//
		// // objBd.open();
		// int nbChampEffacé = objBd.deleteTable("Notes", whereClause,
		// WhereArgs);

		// produitNote.removeAll(produitNote);
		AfficheLeContenu("Tout", NoteListView);
		// objBd.close();
	}

	@Override
	public void initComposantVisuel() {
		NoteListView = (ListView) this.findViewById(R.id.produitListViewNote);
		BtAddNote = (ImageView) this.findViewById(R.id.IvAddNote);
		BtSupprTtteNote = (ImageView) findViewById(R.id.IvJeterNote);

		NoteListView.setOnItemClickListener(this);
		NoteListView.setOnItemLongClickListener(this);

		BtAddNote.setOnClickListener(this);
		BtSupprTtteNote.setOnClickListener(this);

	}

	@Override
	public void ChoisiLeTheme() {
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

	}

	@Override
	public MlNote recupereNoteFromPreviousActivity() {

		return null;
	}

	@Override
	public void transfereMlNoteToActivity(Intent p_intent) {
		if (note == null) {
			note = new MlNote();
		}

		p_intent.putExtra(MlNote.class.getCanonicalName(),
				SerialisableHelper.serialize(note));

	}

}
