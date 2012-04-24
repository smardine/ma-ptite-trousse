package fr.smardine.matroussedemaquillage.param;

import widget.majWidget;
import android.os.Bundle;
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
import fr.smardine.matroussedemaquillage.R;
import fr.smardine.matroussedemaquillage.base.BDAcces;
import fr.smardine.matroussedemaquillage.base.accesTable.AccesTableParams;
import fr.smardine.matroussedemaquillage.variableglobale.EnTheme;

/**
 * @author smardine
 */
public class tab2 extends SuperParamActivity implements OnItemSelectedListener,
		ViewFactory {
	ImageSwitcher mSwitcher;

	Spinner s1;
	BDAcces objBd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.parametres_tab2);

		s1 = (Spinner) findViewById(R.id.SpinnerTheme);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, mStrings);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s1.setAdapter(adapter);

		s1.setOnItemSelectedListener(this);

		mSwitcher = (ImageSwitcher) findViewById(R.id.switcher);
		mSwitcher.setFactory(this);
		mSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,
				android.R.anim.fade_in));
		mSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,
				android.R.anim.fade_out));

		verifieLeThemeChoisi();

	}

	/**
	 * 
	 */
	private void verifieLeThemeChoisi() {

		AccesTableParams accesParam = new AccesTableParams(this);
		switch (accesParam.getThemeChoisi()) {
			case Bisounours:
				s1.setSelection(EnTheme.Bisounours.getCode());
				break;
			case Fleur:
				s1.setSelection(EnTheme.Fleur.getCode());
				break;
		}

	}

	private static final String[] mStrings = { EnTheme.Fleur.getLib(),
			EnTheme.Bisounours.getLib() };

	@Override
	public void onItemSelected(AdapterView<?> Parent, View view, int position,
			long id) {

		// if (id == EnTheme.Classique.getCode()) {
		// AccesTableParams accesParam = new AccesTableParams(this);
		// accesParam.majTheme(EnTheme.Classique);
		// // ContentValues values = new ContentValues();
		// // values.put("Theme", EnTheme.Classique.getLib());
		// //
		// // //objBd.open();
		// // objBd.majTable("Param", values, "", null);
		// // //objBd.close();
		// mSwitcher.setImageResource(mImageIds[EnTheme.Classique.getCode()]);
		// }
		if (id == EnTheme.Fleur.getCode()) {
			AccesTableParams accesParam = new AccesTableParams(this);
			accesParam.majTheme(EnTheme.Fleur);
			mSwitcher.setImageResource(mImageIds[EnTheme.Fleur.getCode()]);
		}
		if (id == EnTheme.Bisounours.getCode()) {
			AccesTableParams accesParam = new AccesTableParams(this);
			accesParam.majTheme(EnTheme.Bisounours);
			mSwitcher.setImageResource(mImageIds[EnTheme.Bisounours.getCode()]);
		}

		new majWidget(this, false);
	}

	private final Integer[] mImageIds = { R.drawable.acceuil_fleur,
			R.drawable.acceuil_bisounours };

	@Override
	public void onNothingSelected(AdapterView<?> p_arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public View makeView() {
		ImageView i = new ImageView(this);
		i.setBackgroundColor(0xFF000000);
		i.setScaleType(ImageView.ScaleType.FIT_CENTER);
		i.setLayoutParams(new ImageSwitcher.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		return i;
	}

}
