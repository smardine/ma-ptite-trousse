package fr.smardine.matroussedemaquillage.param;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class tab_param extends TabActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final TabHost tabHost = getTabHost();

		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("Alerte produit")
				.setContent(new Intent(this, tab1.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));

		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("Theme")
				.setContent(new Intent(this, tab2.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));

		// This tab sets the intent flag so that it is recreated each time
		// the tab is clicked.
		tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("Info.")
				.setContent(new Intent(this, tab3.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
	}

}
