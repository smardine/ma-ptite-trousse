package fr.smardine.matroussedemaquillage.param;

import widget.majWidget;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import fr.smardine.matroussedemaquillage.R;
import fr.smardine.matroussedemaquillage.base.BDAcces;
import fr.smardine.matroussedemaquillage.base.accesTable.AccesTableParams;
import fr.smardine.matroussedemaquillage.variableglobale.EnActionParDefaut;
import fr.smardine.matroussedemaquillage.variableglobale.ValeurParDefaut;

/**
 * @author smardine
 */
public class tab1 extends SuperParamActivity implements OnClickListener,
		ColorPickerDialog.OnColorChangedListener, OnItemSelectedListener {
	EditText textView;
	CheckBox CbAfficheAlerte;
	BDAcces objBd;
	Button BtColorPicker;
	TextView ApercuCouleur;
	Spinner sp;
	ImageView IvPreviewWidget;

	private static final String[] mStrings = {
			EnActionParDefaut.RECHERCHE.getLib(),
			EnActionParDefaut.PAGE_PRINC.getLib(),
			EnActionParDefaut.PERIME.getLib() };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.parametres_tab1);
		textView = (EditText) findViewById(R.id.Param_DureeVie);
		CbAfficheAlerte = (CheckBox) findViewById(R.id.CbAfficheAlerte);
		BtColorPicker = (Button) findViewById(R.id.BtColorPicker);
		BtColorPicker.setOnClickListener(this);
		ApercuCouleur = (TextView) findViewById(R.id.TvPreviewColor);
		IvPreviewWidget = (ImageView) findViewById(R.id.IvPreviewWidget);

		/**
		 * on met la couleur du fond dès la creation de la fenetre
		 */
		ValeurParDefaut val = new ValeurParDefaut(this);
		ApercuCouleur.setTextColor(val.getCouleurPastille());
		verifieLeThemeChoisi();

		// le spinner qui permet de choisir la periodicité
		sp = (Spinner) findViewById(R.id.SpWidget);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, mStrings);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp.setAdapter(adapter);
		sp.setOnItemSelectedListener(this);
		String action = val.getActionParDefaut();
		for (EnActionParDefaut act : EnActionParDefaut.values()) {
			if (act.getLib().equals(action)) {
				sp.setSelection(act.getCode());
			}
		}

		AccesTableParams accesParam = new AccesTableParams(this);

		CbAfficheAlerte.setChecked(accesParam.getAfficheAlerte());

		textView.setText("" + accesParam.getDureeViePeremption());

		textView.addTextChangedListener(new TextWatcher() {

			// int len=0;
			@Override
			public void afterTextChanged(Editable s) {
				String str = textView.getText().toString();
				if (!str.equals("")) {
					int valeurRentree = Integer.parseInt(str);
					if (valeurRentree > 99) {
						textView.setText("99");
					}
					if (valeurRentree <= 0) {
						textView.setText("1");
					}
				}

				/*
				 * if(str.length()==4*&& len <str.length()*){
				 * textView.append("-"); }
				 */
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

				// String str = textView.getText().toString();
				// len = str.length();
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

		});

	}

	/**
	 * permet de changer l'icone du widget sur l'objet ImageView
	 */
	private void verifieLeThemeChoisi() {
		AccesTableParams accesParam = new AccesTableParams(this);
		switch (accesParam.getThemeChoisi()) {
			case Bisounours:
				IvPreviewWidget.setImageResource(R.drawable.icone_bisounours);
				break;
			case Fleur:
				IvPreviewWidget.setImageResource(R.drawable.icone1);
				break;
		}

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
		AlertDialog.Builder ad = new AlertDialog.Builder(this);
		ad.setTitle("Attention");
		ad.setMessage("Merci de saisir une valeur comprise entre 1 et 99 mois");
		ad.setPositiveButton("Ok", null);

		String DurreeViePeremp = textView.getText().toString();
		if (DurreeViePeremp.equals("")) {
			ad.show();
		} else {

			boolean IsChecked = CbAfficheAlerte.isChecked();

			AccesTableParams accesParam = new AccesTableParams(this);
			accesParam.majAfficheAlerte(IsChecked);
			accesParam.majDureeVie(DurreeViePeremp);
			// ContentValues values = new ContentValues();
			// values.put("AfficheAlerte", AfficheAlerte);
			// values.put("DureeViePeremp", DurreeViePeremp);
			//
			// //objBd.open();
			// objBd.majTable("Param", values, "", null);
			// //objBd.close();

		}
	}

	@Override
	public void onClick(View p_v) {
		if (p_v.equals(BtColorPicker)) {
			ValeurParDefaut val = new ValeurParDefaut(this);
			new ColorPickerDialog(this, this, val.getCouleurPastille()).show();
		}

	}

	@Override
	public void colorChanged(int p_color) {
		ApercuCouleur.setTextColor(p_color);
		ValeurParDefaut val = new ValeurParDefaut(this);
		val.setCouleurPastille(p_color);
		new majWidget(this, false);

	}

	@Override
	public void onItemSelected(AdapterView<?> Parent, View view, int position,
			long id) {
		if (id == EnActionParDefaut.RECHERCHE.getCode()) {
			ValeurParDefaut val = new ValeurParDefaut(this);
			val.setActionParDefaut(EnActionParDefaut.RECHERCHE.getLib());

		}
		if (id == EnActionParDefaut.PAGE_PRINC.getCode()) {
			ValeurParDefaut val = new ValeurParDefaut(this);
			val.setActionParDefaut(EnActionParDefaut.PAGE_PRINC.getLib());
		}
		if (id == EnActionParDefaut.PERIME.getCode()) {
			ValeurParDefaut val = new ValeurParDefaut(this);
			val.setActionParDefaut(EnActionParDefaut.PERIME.getLib());
		}
		new majWidget(this, false);

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

}
