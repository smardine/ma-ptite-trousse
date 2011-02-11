package fr.smardine.matroussedemaquillage.param;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.ViewSwitcher.ViewFactory;
import fr.smardine.matroussedemaquillage.Main;
import fr.smardine.matroussedemaquillage.R;
import fr.smardine.matroussedemaquillage.base.BDAcces;
import fr.smardine.matroussedemaquillage.recherche.Recherche;
import fr.smardine.matroussedemaquillage.variableglobale.ActivityParam;
import fr.smardine.matroussedemaquillage.variableglobale.EnTheme;

public class tab2 extends Activity implements OnItemSelectedListener, ViewFactory {
	ImageSwitcher mSwitcher;

	Spinner s1;
	BDAcces objBd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.parametres_tab2);

		s1 = (Spinner) findViewById(R.id.SpinnerTheme);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mStrings);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s1.setAdapter(adapter);

		s1.setOnItemSelectedListener(this);

		mSwitcher = (ImageSwitcher) findViewById(R.id.switcher);
		mSwitcher.setFactory(this);
		mSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
		mSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));

		verifieLeThemeChoisi();

	}

	/**
	 * 
	 */
	private void verifieLeThemeChoisi() {
		objBd = new BDAcces(this);
		objBd.open();
		String[] colonnes = { "AfficheAlerte", "DureeViePeremp", "Theme" };
		@SuppressWarnings("rawtypes")
		ArrayList[] Param = objBd.renvoi_param(colonnes);

		String nomThemeChoisi = Param[2].get(0).toString().trim();
		if (EnTheme.Bisounours.getLib().equals(nomThemeChoisi)) {

			s1.setSelection(EnTheme.Bisounours.getCode());
		}
		if (EnTheme.Classique.getLib().equals(nomThemeChoisi)) {

			s1.setSelection(EnTheme.Classique.getCode());
		}
		if (EnTheme.Fleur.getLib().equals(nomThemeChoisi)) {

			s1.setSelection(EnTheme.Fleur.getCode());
		}
		objBd.close();
	}

	private static final String[] mStrings = { EnTheme.Classique.getLib(), EnTheme.Fleur.getLib(), EnTheme.Bisounours.getLib() };

	@Override
	protected void onRestart() {
		super.onRestart();
		// popUp("onRestart()-Page2");
	}

	/**
	 * Ex�cut� lorsque l'activit� devient visible � l'utilisateur. La fonction onStart() est suivie de la fonction onResume().
	 */
	@Override
	protected void onStart() {
		super.onStart();
		// popUp("onStart()-Page2");
	}

	/**
	 * Ex�cut�e a chaque passage en premier plan de l'activit�. Ou bien, si l'activit� passe � nouveau en premier (si une autre activit�
	 * �tait pass� en premier plan entre temps). La fonction onResume() est suivie de l'ex�cution de l'activit�.
	 */
	@Override
	protected void onResume() {
		super.onResume();
		// popUp("onResume()-Page2");

	}

	/**
	 * La fonction onStop() est ex�cut�e : - lorsque l'activit� n'est plus en premier plan - ou bien lorsque l'activit� va �tre d�truite
	 * Cette fonction est suivie : - de la fonction onRestart() si l'activit� passe � nouveau en premier plan - de la fonction onDestroy()
	 * lorsque l'activit� se termine ou bien lorsque le syst�me d�cide de l'arr�ter
	 */
	@Override
	protected void onStop() {
		super.onStop();
		// popUp("onStop-Page2");
	}

	/**
	 * La fonction onPause() est suivie : - d'un onResume() si l'activit� passe � nouveau en premier plan - d'un onStop() si elle devient
	 * invisible � l'utilisateur L'ex�cution de la fonction onPause() doit �tre rapide, car la prochaine activit� ne d�marrera pas tant que
	 * l'ex�cution de la fonction onPause() n'est pas termin�e.
	 */
	@Override
	protected void onPause() {
		super.onPause();

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent main = new Intent(this, Main.class);
			main.putExtra(ActivityParam.LaunchFromParam, true);
			startActivity(main);
			finish();
			return true;
		}
		if (keyCode == KeyEvent.KEYCODE_SEARCH) {
			Intent intentRecherche = new Intent(this, Recherche.class);
			// on demarre la nouvelle activit�
			startActivity(intentRecherche);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	public void OnDestroy() {
		super.onDestroy();
		// popUp("OnDestroy-Page2");

	}

	@Override
	public void onItemSelected(AdapterView<?> Parent, View view, int position, long id) {
		// TODO Auto-generated method stub

		if (id == EnTheme.Classique.getCode()) {
			ContentValues values = new ContentValues();
			values.put("Theme", EnTheme.Classique.getLib());

			objBd.open();
			objBd.majTable("Param", values, "", null);
			objBd.close();
			mSwitcher.setImageResource(mImageIds[EnTheme.Classique.getCode()]);
		}
		if (id == EnTheme.Fleur.getCode()) {
			ContentValues values = new ContentValues();
			values.put("Theme", EnTheme.Fleur.getLib());

			objBd.open();
			objBd.majTable("Param", values, "", null);
			objBd.close();
			mSwitcher.setImageResource(mImageIds[EnTheme.Fleur.getCode()]);
		}
		if (id == EnTheme.Bisounours.getCode()) {
			ContentValues values = new ContentValues();
			values.put("Theme", EnTheme.Bisounours.getLib());

			objBd.open();
			objBd.majTable("Param", values, "", null);
			objBd.close();
			mSwitcher.setImageResource(mImageIds[EnTheme.Bisounours.getCode()]);
		}
	}

	private final Integer[] mImageIds = { R.drawable.acceuil, R.drawable.acceuil_fleur, R.drawable.acceuil_bisounours };

	@Override
	public void onNothingSelected(AdapterView<?> p_arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public View makeView() {
		ImageView i = new ImageView(this);
		i.setBackgroundColor(0xFF000000);
		i.setScaleType(ImageView.ScaleType.FIT_CENTER);
		i.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		return i;
	}

}
