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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import fr.smardine.matroussedemaquillage.R;
import fr.smardine.matroussedemaquillage.base.accesTable.AccesTableNotes;
import fr.smardine.matroussedemaquillage.base.accesTable.AccesTableParams;
import fr.smardine.matroussedemaquillage.mdl.MlNote;
import fr.smardine.matroussedemaquillage.param.tab_param;
import fr.smardine.matroussedemaquillage.recherche.Recherche;
import fr.smardine.matroussedemaquillage.variableglobale.ActivityParam;
import fr.smardine.matroussedemaquillage.variableglobale.EnTheme;

/**
 * @author smardine
 */
public class note_saisie extends Activity implements OnClickListener {

	// private BDAcces objBd;
	String IdNote = "", Titre = "", Message = "";
	TextView txtTitre;
	TextView editMessage;
	ImageView ChangerTitre, ChangerMessage;
	AlertDialog.Builder adTitre, adMessage;

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ExceptionHandler.register(this,
		// "http://simon.mardine.free.fr/trousse_maquillage/test/server.php","ma_ptite_trousse");
		ChoisiLeTheme();

		txtTitre = (TextView) findViewById(R.id.TvTitreNote);
		editMessage = (TextView) findViewById(R.id.TvMessageNote);
		ChangerTitre = (ImageView) findViewById(R.id.IvChangerTitre);
		ChangerMessage = (ImageView) findViewById(R.id.IvChangerMessage);

		ChangerTitre.setOnClickListener(this);
		ChangerMessage.setOnClickListener(this);

		IdNote = getIntent().getStringExtra(ActivityParam.IdNote).trim();

		MlNote n = new MlNote(Integer.parseInt(IdNote), this);

		Titre = n.getTitre();
		Message = n.getMessage();
		txtTitre.setText(Titre);
		editMessage.setText(Message);
		// objBd = new BDAcces(this);
		// // objBd.open();
		// String[] Colonnes = { "id_note", "Titre", "Message" };
		// String condition = "id_note=?";
		// String[] args = { IdNote };
		// ArrayList[] notes = objBd.renvoi_liste_NoteTotale(Colonnes, "", "",
		// condition, args);
		//
		// // objBd.close();
		// int nbdobjet = notes[0].size();
		// if (nbdobjet != 0) {
		// for (int j = 0; j < nbdobjet; j++) {
		// // IdProduit = notes[0].get(j).toString();
		// Titre = notes[1].get(j).toString();
		// Message = notes[2].get(j).toString();
		//
		// txtTitre.setText(Titre);
		// editMessage.setText(Message);
		// }
		// }

		this.setTitle("Saisie de la note");
		// popUp("OnCreate-page2");

	}

	/**
	 * 
	 */
	private void ChoisiLeTheme() {
		AccesTableParams accesParam = new AccesTableParams(this);
		switch (accesParam.getThemeChoisi()) {
			case Bisounours:
				setContentView(R.layout.theme_bisounours_note_saisie);

				break;
			case Classique:
				accesParam.majTheme(EnTheme.Fleur);
				ChoisiLeTheme();
				break;
			case Fleur:
				setContentView(R.layout.theme_fleur_note_saisie);
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
		// setContentView(R.layout.theme_bisounours_note_saisie);
		//
		// }
		// if (EnTheme.Classique.getLib().equals(nomThemeChoisi)) {
		// // setContentView(R.layout.note_saisie);
		// AccesTableParams accesParam = new AccesTableParams(this);
		// accesParam.majTheme(EnTheme.Fleur);
		// ChoisiLeTheme();
		//
		// }
		// if (EnTheme.Fleur.getLib().equals(nomThemeChoisi)) {
		// setContentView(R.layout.theme_fleur_note_saisie);
		// }

		// objBd.close();
	}

	@Override
	public void onClick(View v) {

		if (v == ChangerTitre) {
			final EditText inputTeinte = new EditText(this);
			adTitre = new AlertDialog.Builder(this);
			adTitre.setTitle("Modification du titre");
			adTitre.setIcon(R.drawable.button_modifier);
			inputTeinte.setText(txtTitre.getText().toString());
			adTitre.setView(inputTeinte);
			adTitre.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
								int whichButton) {
							String value = inputTeinte.getText().toString();
							txtTitre.setText(value);
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
		if (v == ChangerMessage) {
			final EditText inputTeinte = new EditText(this);
			adMessage = new AlertDialog.Builder(this);
			adMessage.setTitle("Modification du message");
			adMessage.setIcon(R.drawable.button_modifier);
			inputTeinte.setText(editMessage.getText().toString());
			adMessage.setView(inputTeinte);
			adMessage.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
								int whichButton) {
							String value = inputTeinte.getText().toString();
							editMessage.setText(value);
						}
					});
			adMessage.setNegativeButton("Annuler",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
								int whichButton) {
							// Canceled.
						}
					});
			adMessage.show();
		}

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
		// popUp("onRestart()-Page2");
	}

	/**
	 * Exécuté lorsque l'activité devient visible à l'utilisateur. La fonction
	 * onStart() est suivie de la fonction onResume().
	 */
	@Override
	protected void onStart() {
		super.onStart();
		// popUp("onStart()-Page2");
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
		popUp("onResume()-Page2");

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
		popUp("onStop-Page2");
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

	private void onCreateMenu(Menu menu) {
		SubMenu recherche = menu.addSubMenu(0, 2000, 1, "Recherche");
		recherche.setIcon(R.drawable.menu_recherche);
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
		Intent intentRecherche;
		Intent intentParametres;

		// évènement appelé lorsqu'un menu est choisi
		switch (item.getItemId()) {
			// l'identifiant integer est moins gourmand en ressource que le
			// string
			case 2000:
				Toast.makeText(this, "Recherche", 1000).show();
				intentRecherche = new Intent(this, Recherche.class);
				intentRecherche.putExtra(ActivityParam.LaunchFromNoteSaisie,
						true);
				// on demarre la nouvelle activité
				startActivity(intentRecherche);
				termineActivity();
				break;
			case 2001:
				Toast.makeText(this, "Paramètres", 1000).show();
				intentParametres = new Intent(this, tab_param.class);
				intentParametres.putExtra(ActivityParam.LaunchFromNoteSaisie,
						true);
				intentParametres.putExtra(ActivityParam.IdNote, IdNote);
				// on demarre la nouvelle activité
				startActivity(intentParametres);
				termineActivity();
				break;

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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Titre = txtTitre.getText().toString();
			Message = editMessage.getText().toString();

			AccesTableNotes accesNote = new AccesTableNotes(this);
			accesNote.majTitreEtMessage(IdNote, Titre, Message);
			//
			// ContentValues modifiedValues = new ContentValues();
			// modifiedValues.put("Titre", Titre);
			// modifiedValues.put("Message", Message);
			// String whereClause = "id_note=?";
			// String[] whereArgs = { IdNote };
			// //objBd.open();
			// int nbCHampModif = objBd.majTable("Notes", modifiedValues,
			// whereClause, whereArgs);

			// Log.d("Modif note", ">>nb de champ modifié: " + nbCHampModif);

			Intent note_page1 = new Intent(this, note_page1.class);
			note_page1.putExtra(ActivityParam.LaunchFromNoteSaisie, true);
			startActivity(note_page1);
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

	/**
	 * 
	 */
	public void OnDestroy() {
		super.onDestroy();
		popUp("OnDestroy-Page2");

	}

}
