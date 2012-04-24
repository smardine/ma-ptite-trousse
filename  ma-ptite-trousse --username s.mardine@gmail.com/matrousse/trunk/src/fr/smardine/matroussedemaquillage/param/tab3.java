package fr.smardine.matroussedemaquillage.param;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import fr.smardine.matroussedemaquillage.R;
import fr.smardine.matroussedemaquillage.variableglobale.G_changeLog;

/**
 * @author smardine
 */
public class tab3 extends Activity {
	// BDAcces objBd;
	TextView txtView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.parametres_tab3);
		txtView = (TextView) findViewById(R.id.TvChangeLog);
		String texte = G_changeLog.Changement;
		txtView.setText(texte);
	}

}
