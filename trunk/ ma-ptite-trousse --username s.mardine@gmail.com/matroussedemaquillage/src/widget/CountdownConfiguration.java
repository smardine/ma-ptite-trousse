package widget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import fr.smardine.matroussedemaquillage.R;
import fr.smardine.matroussedemaquillage.variableglobale.EnActionParDefaut;
import fr.smardine.matroussedemaquillage.variableglobale.EnPeriodicite;

public class CountdownConfiguration extends Activity {

	private final Context self = this;

	private int appWidgetId;
	private Spinner sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// get the appWidgetId of the appWidget being configured

		Intent launchIntent = getIntent();

		Bundle extras = launchIntent.getExtras();
		if (extras != null) {
			appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
		}

		// set the result for cancel first

		// if the user cancels, then the appWidget

		// should not appear

		Intent cancelResultValue = new Intent();

		cancelResultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

		setResult(RESULT_CANCELED, cancelResultValue);

		// show the user interface of configuration

		setContentView(R.layout.configuration);

		// le spinner qui permet de choisir la periodicité
		sp = (Spinner) findViewById(R.id.SpWidget);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mStrings);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp.setAdapter(adapter);

		// the OK button

		Button ok = (Button) findViewById(R.id.BtOkConfWidget);

		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// change the result to OK
				int positionSpinner = sp.getSelectedItemPosition();

				if (positionSpinner == EnActionParDefaut.RECHERCHE.getCode()) {

				}
				if (positionSpinner == EnActionParDefaut.PAGE_PRINC.getCode()) {

				}
				if (positionSpinner == EnActionParDefaut.PERIME.getCode()){
					
				}

				Intent resultValue = new Intent();

				resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,

				appWidgetId);

				setResult(RESULT_OK, resultValue);

				// finish closes activity

				// and sends the OK result

				// the widget will be be placed on the home screen

				finish();

			}

		});

		// cancel button

		Button cancel = (Button) findViewById(R.id.BtCancelConfWidget);

		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// finish sends the already configured cancel result

				// and closes activity

				finish();

			}

		});

	}

	private static final String[] mStrings = { EnActionParDefaut.RECHERCHE.getLib(), EnActionParDefaut.PAGE_PRINC.getLib(),EnActionParDefaut.PERIME.getLib() };

}
