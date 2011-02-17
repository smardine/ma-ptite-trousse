package fr.smardine.matroussedemaquillage.param;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import fr.smardine.matroussedemaquillage.variableglobale.ActivityParam;

public class tab_param extends TabActivity implements OnTabChangeListener {
	Intent tab1, tab2, tab3, tab4;
	TabHost tabHost;
	private boolean isLaunchByNotePage1;
	private boolean isLaunchByNoteSaisie;
	private boolean isLaunchByAfficheDetail;
	private boolean isLaunchByProduitPerime;
	private boolean isLaunchByRecherche;
	private boolean isLaunchByDupplique;
	private boolean isLaunchByPage1;
	private boolean isLaunchBypagerecap;
	private boolean isLaunchByMain;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		tabHost = getTabHost();

		tab1 = new Intent(this, tab1.class);
		tab2 = new Intent(this, tab2.class);
		tab3 = new Intent(this, tab3.class);
		tab4 = new Intent(this, tab4.class);

		// Recuperation des parametres d'activity (provenance + valeurs eventuelles) //

		isLaunchByNotePage1 = getIntent().getBooleanExtra(ActivityParam.LaunchFromNotePage1, false);
		isLaunchByNoteSaisie = getIntent().getBooleanExtra(ActivityParam.LaunchFromNoteSaisie, false);
		isLaunchByAfficheDetail = getIntent().getBooleanExtra(ActivityParam.LaunchFromAfficheDetail, false);
		isLaunchByProduitPerime = getIntent().getBooleanExtra(ActivityParam.LaunchFromRechercheProduitPerime, false);
		isLaunchByRecherche = getIntent().getBooleanExtra(ActivityParam.LaunchFromRecherche, false);
		isLaunchByDupplique = getIntent().getBooleanExtra(ActivityParam.LaunchFromDuppliquer, false);
		isLaunchByPage1 = getIntent().getBooleanExtra(ActivityParam.LaunchFromPage1, false);
		isLaunchBypagerecap = getIntent().getBooleanExtra(ActivityParam.LaunchFromPageRecap, false);
		isLaunchByMain = getIntent().getBooleanExtra("LaunchFromMain", false);

		if (isLaunchByNotePage1) {
			tab1.putExtra(ActivityParam.LaunchFromNotePage1, true);
			tab2.putExtra(ActivityParam.LaunchFromNotePage1, true);
			tab3.putExtra(ActivityParam.LaunchFromNotePage1, true);
			tab4.putExtra(ActivityParam.LaunchFromNotePage1, true);
		}
		if (isLaunchByNoteSaisie) {
			String idNote = getIntent().getStringExtra(ActivityParam.IdNote);
			tab1.putExtra(ActivityParam.LaunchFromNoteSaisie, true);
			tab1.putExtra(ActivityParam.IdNote, idNote);
			tab2.putExtra(ActivityParam.LaunchFromNoteSaisie, true);
			tab2.putExtra(ActivityParam.IdNote, idNote);
			tab3.putExtra(ActivityParam.LaunchFromNoteSaisie, true);
			tab3.putExtra(ActivityParam.IdNote, idNote);
			tab4.putExtra(ActivityParam.LaunchFromNoteSaisie, true);
			tab4.putExtra(ActivityParam.IdNote, idNote);
		}
		if (isLaunchByAfficheDetail) {
			String idProduit = getIntent().getStringExtra(ActivityParam.IdProduit);

			tab1.putExtra(ActivityParam.IdProduit, idProduit);
			tab1.putExtra(ActivityParam.LaunchFromRecherche, getIntent().getBooleanExtra(ActivityParam.LaunchFromRecherche, false));
			tab1.putExtra(ActivityParam.LaunchFromRechercheProduitPerime,
					getIntent().getBooleanExtra(ActivityParam.LaunchFromRechercheProduitPerime, false));
			tab1.putExtra(ActivityParam.LaunchFromAfficheDetail, true);

			tab2.putExtra(ActivityParam.IdProduit, idProduit);
			tab2.putExtra(ActivityParam.LaunchFromRecherche, getIntent().getBooleanExtra(ActivityParam.LaunchFromRecherche, false));
			tab2.putExtra(ActivityParam.LaunchFromRechercheProduitPerime,
					getIntent().getBooleanExtra(ActivityParam.LaunchFromRechercheProduitPerime, false));
			tab2.putExtra(ActivityParam.LaunchFromAfficheDetail, true);

			tab3.putExtra(ActivityParam.IdProduit, idProduit);
			tab3.putExtra(ActivityParam.LaunchFromRecherche, getIntent().getBooleanExtra(ActivityParam.LaunchFromRecherche, false));
			tab3.putExtra(ActivityParam.LaunchFromRechercheProduitPerime,
					getIntent().getBooleanExtra(ActivityParam.LaunchFromRechercheProduitPerime, false));
			tab3.putExtra(ActivityParam.LaunchFromAfficheDetail, true);

			tab4.putExtra(ActivityParam.IdProduit, idProduit);
			tab4.putExtra(ActivityParam.LaunchFromRecherche, getIntent().getBooleanExtra(ActivityParam.LaunchFromRecherche, false));
			tab4.putExtra(ActivityParam.LaunchFromRechercheProduitPerime,
					getIntent().getBooleanExtra(ActivityParam.LaunchFromRechercheProduitPerime, false));
			tab4.putExtra(ActivityParam.LaunchFromAfficheDetail, true);
		}
		if (isLaunchByProduitPerime) {
			tab1.putExtra(ActivityParam.LaunchFromRechercheProduitPerime, true);
			tab2.putExtra(ActivityParam.LaunchFromRechercheProduitPerime, true);
			tab3.putExtra(ActivityParam.LaunchFromRechercheProduitPerime, true);
			tab4.putExtra(ActivityParam.LaunchFromRechercheProduitPerime, true);
		}

		if (isLaunchByRecherche) {
			tab1.putExtra(ActivityParam.LaunchFromRecherche, true);
			tab2.putExtra(ActivityParam.LaunchFromRecherche, true);
			tab3.putExtra(ActivityParam.LaunchFromRecherche, true);
			tab4.putExtra(ActivityParam.LaunchFromRecherche, true);
		}
		if (isLaunchByDupplique) {
			tab1.putExtra(ActivityParam.LaunchFromDuppliquer, true);
			tab2.putExtra(ActivityParam.LaunchFromDuppliquer, true);
			tab3.putExtra(ActivityParam.LaunchFromDuppliquer, true);
			tab4.putExtra(ActivityParam.LaunchFromDuppliquer, true);
		}
		if (isLaunchByPage1) {
			String MarqueChoisie = getIntent().getStringExtra(ActivityParam.Marque);
			String DureeVie = getIntent().getStringExtra(ActivityParam.DurreeDeVie);
			String DateChoisie = getIntent().getStringExtra(ActivityParam.DateAchat);
			String numTeinte = getIntent().getStringExtra(ActivityParam.NumeroDeTeinte);
			String nomProduitRecup = getIntent().getStringExtra(ActivityParam.NomProduit);

			tab1.putExtra(ActivityParam.Marque, MarqueChoisie.trim());
			tab1.putExtra(ActivityParam.DurreeDeVie, DureeVie.trim());
			tab1.putExtra(ActivityParam.DateAchat, DateChoisie.trim());
			tab1.putExtra(ActivityParam.NumeroDeTeinte, numTeinte.trim());
			tab1.putExtra(ActivityParam.NomProduit, nomProduitRecup.trim());
			tab1.putExtra(ActivityParam.LaunchFromPage1, true);

			tab2.putExtra(ActivityParam.Marque, MarqueChoisie.trim());
			tab2.putExtra(ActivityParam.DurreeDeVie, DureeVie.trim());
			tab2.putExtra(ActivityParam.DateAchat, DateChoisie.trim());
			tab2.putExtra(ActivityParam.NumeroDeTeinte, numTeinte.trim());
			tab2.putExtra(ActivityParam.NomProduit, nomProduitRecup.trim());
			tab2.putExtra(ActivityParam.LaunchFromPage1, true);

			tab3.putExtra(ActivityParam.Marque, MarqueChoisie.trim());
			tab3.putExtra(ActivityParam.DurreeDeVie, DureeVie.trim());
			tab3.putExtra(ActivityParam.DateAchat, DateChoisie.trim());
			tab3.putExtra(ActivityParam.NumeroDeTeinte, numTeinte.trim());
			tab3.putExtra(ActivityParam.NomProduit, nomProduitRecup.trim());
			tab3.putExtra(ActivityParam.LaunchFromPage1, true);

			tab4.putExtra(ActivityParam.Marque, MarqueChoisie.trim());
			tab4.putExtra(ActivityParam.DurreeDeVie, DureeVie.trim());
			tab4.putExtra(ActivityParam.DateAchat, DateChoisie.trim());
			tab4.putExtra(ActivityParam.NumeroDeTeinte, numTeinte.trim());
			tab4.putExtra(ActivityParam.NomProduit, nomProduitRecup.trim());
			tab4.putExtra(ActivityParam.LaunchFromPage1, true);
		}

		if (isLaunchBypagerecap) {
			String MarqueChoisie = getIntent().getStringExtra(ActivityParam.Marque);
			String DureeVie = getIntent().getStringExtra(ActivityParam.DurreeDeVie);
			String DateChoisie = getIntent().getStringExtra(ActivityParam.DateAchat);
			String numTeinte = getIntent().getStringExtra(ActivityParam.NumeroDeTeinte);
			String nomProduitRecup = getIntent().getStringExtra(ActivityParam.NomProduit);

			tab1.putExtra(ActivityParam.Marque, MarqueChoisie.trim());
			tab1.putExtra(ActivityParam.DurreeDeVie, DureeVie.trim());
			tab1.putExtra(ActivityParam.DateAchat, DateChoisie.trim());
			tab1.putExtra(ActivityParam.NumeroDeTeinte, numTeinte.trim());
			tab1.putExtra(ActivityParam.NomProduit, nomProduitRecup.trim());
			tab1.putExtra(ActivityParam.LaunchFromPageRecap, true);

			tab2.putExtra(ActivityParam.Marque, MarqueChoisie.trim());
			tab2.putExtra(ActivityParam.DurreeDeVie, DureeVie.trim());
			tab2.putExtra(ActivityParam.DateAchat, DateChoisie.trim());
			tab2.putExtra(ActivityParam.NumeroDeTeinte, numTeinte.trim());
			tab2.putExtra(ActivityParam.NomProduit, nomProduitRecup.trim());
			tab2.putExtra(ActivityParam.LaunchFromPageRecap, true);

			tab3.putExtra(ActivityParam.Marque, MarqueChoisie.trim());
			tab3.putExtra(ActivityParam.DurreeDeVie, DureeVie.trim());
			tab3.putExtra(ActivityParam.DateAchat, DateChoisie.trim());
			tab3.putExtra(ActivityParam.NumeroDeTeinte, numTeinte.trim());
			tab3.putExtra(ActivityParam.NomProduit, nomProduitRecup.trim());
			tab3.putExtra(ActivityParam.LaunchFromPageRecap, true);

			tab4.putExtra(ActivityParam.Marque, MarqueChoisie.trim());
			tab4.putExtra(ActivityParam.DurreeDeVie, DureeVie.trim());
			tab4.putExtra(ActivityParam.DateAchat, DateChoisie.trim());
			tab4.putExtra(ActivityParam.NumeroDeTeinte, numTeinte.trim());
			tab4.putExtra(ActivityParam.NomProduit, nomProduitRecup.trim());
			tab4.putExtra(ActivityParam.LaunchFromPageRecap, true);
		}
		if (isLaunchByMain) {
			tab1.putExtra(ActivityParam.LaunchFromMain, true);
			tab2.putExtra(ActivityParam.LaunchFromMain, true);
			tab3.putExtra(ActivityParam.LaunchFromMain, true);
			tab4.putExtra(ActivityParam.LaunchFromMain, true);
		}

		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("Alerte produit").setContent(tab1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));

		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("Theme").setContent(tab2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));

		// This tab sets the intent flag so that it is recreated each time
		// the tab is clicked.
		tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("Info.").setContent(tab3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));

		tabHost.addTab(tabHost.newTabSpec("tab4").setIndicator("Maintenance").setContent(tab4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));

		tabHost.setOnTabChangedListener(this);

	}

	@Override
	public void onTabChanged(String p_tabId) {
		if (isLaunchByNotePage1) {
			tab1.putExtra(ActivityParam.LaunchFromNotePage1, true);
			tab2.putExtra(ActivityParam.LaunchFromNotePage1, true);
			tab3.putExtra(ActivityParam.LaunchFromNotePage1, true);
			tab4.putExtra(ActivityParam.LaunchFromNotePage1, true);
		}
		if (isLaunchByNoteSaisie) {
			String idNote = getIntent().getStringExtra(ActivityParam.IdNote);
			tab1.putExtra(ActivityParam.LaunchFromNoteSaisie, true);
			tab1.putExtra(ActivityParam.IdNote, idNote);
			tab2.putExtra(ActivityParam.LaunchFromNoteSaisie, true);
			tab2.putExtra(ActivityParam.IdNote, idNote);
			tab3.putExtra(ActivityParam.LaunchFromNoteSaisie, true);
			tab3.putExtra(ActivityParam.IdNote, idNote);
			tab4.putExtra(ActivityParam.LaunchFromNoteSaisie, true);
			tab4.putExtra(ActivityParam.IdNote, idNote);
		}
		if (isLaunchByAfficheDetail) {
			String idProduit = getIntent().getStringExtra(ActivityParam.IdProduit);

			tab1.putExtra(ActivityParam.IdProduit, idProduit);
			tab1.putExtra(ActivityParam.LaunchFromRecherche, getIntent().getBooleanExtra(ActivityParam.LaunchFromRecherche, false));
			tab1.putExtra(ActivityParam.LaunchFromRechercheProduitPerime,
					getIntent().getBooleanExtra(ActivityParam.LaunchFromRechercheProduitPerime, false));
			tab1.putExtra(ActivityParam.LaunchFromAfficheDetail, true);

			tab2.putExtra(ActivityParam.IdProduit, idProduit);
			tab2.putExtra(ActivityParam.LaunchFromRecherche, getIntent().getBooleanExtra(ActivityParam.LaunchFromRecherche, false));
			tab2.putExtra(ActivityParam.LaunchFromRechercheProduitPerime,
					getIntent().getBooleanExtra(ActivityParam.LaunchFromRechercheProduitPerime, false));
			tab2.putExtra(ActivityParam.LaunchFromAfficheDetail, true);

			tab3.putExtra(ActivityParam.IdProduit, idProduit);
			tab3.putExtra(ActivityParam.LaunchFromRecherche, getIntent().getBooleanExtra(ActivityParam.LaunchFromRecherche, false));
			tab3.putExtra(ActivityParam.LaunchFromRechercheProduitPerime,
					getIntent().getBooleanExtra(ActivityParam.LaunchFromRechercheProduitPerime, false));
			tab3.putExtra(ActivityParam.LaunchFromAfficheDetail, true);

			tab4.putExtra(ActivityParam.IdProduit, idProduit);
			tab4.putExtra(ActivityParam.LaunchFromRecherche, getIntent().getBooleanExtra(ActivityParam.LaunchFromRecherche, false));
			tab4.putExtra(ActivityParam.LaunchFromRechercheProduitPerime,
					getIntent().getBooleanExtra(ActivityParam.LaunchFromRechercheProduitPerime, false));
			tab4.putExtra(ActivityParam.LaunchFromAfficheDetail, true);
		}
		if (isLaunchByProduitPerime) {
			tab1.putExtra(ActivityParam.LaunchFromRechercheProduitPerime, true);
			tab2.putExtra(ActivityParam.LaunchFromRechercheProduitPerime, true);
			tab3.putExtra(ActivityParam.LaunchFromRechercheProduitPerime, true);
			tab4.putExtra(ActivityParam.LaunchFromRechercheProduitPerime, true);
		}

		if (isLaunchByRecherche) {
			tab1.putExtra(ActivityParam.LaunchFromRecherche, true);
			tab2.putExtra(ActivityParam.LaunchFromRecherche, true);
			tab3.putExtra(ActivityParam.LaunchFromRecherche, true);
			tab4.putExtra(ActivityParam.LaunchFromRecherche, true);
		}
		if (isLaunchByDupplique) {
			tab1.putExtra(ActivityParam.LaunchFromDuppliquer, true);
			tab2.putExtra(ActivityParam.LaunchFromDuppliquer, true);
			tab3.putExtra(ActivityParam.LaunchFromDuppliquer, true);
			tab4.putExtra(ActivityParam.LaunchFromDuppliquer, true);
		}
		if (isLaunchByPage1) {
			String MarqueChoisie = getIntent().getStringExtra(ActivityParam.Marque);
			String DureeVie = getIntent().getStringExtra(ActivityParam.DurreeDeVie);
			String DateChoisie = getIntent().getStringExtra(ActivityParam.DateAchat);
			String numTeinte = getIntent().getStringExtra(ActivityParam.NumeroDeTeinte);
			String nomProduitRecup = getIntent().getStringExtra(ActivityParam.NomProduit);

			tab1.putExtra(ActivityParam.Marque, MarqueChoisie.trim());
			tab1.putExtra(ActivityParam.DurreeDeVie, DureeVie.trim());
			tab1.putExtra(ActivityParam.DateAchat, DateChoisie.trim());
			tab1.putExtra(ActivityParam.NumeroDeTeinte, numTeinte.trim());
			tab1.putExtra(ActivityParam.NomProduit, nomProduitRecup.trim());
			tab1.putExtra(ActivityParam.LaunchFromPage1, true);

			tab2.putExtra(ActivityParam.Marque, MarqueChoisie.trim());
			tab2.putExtra(ActivityParam.DurreeDeVie, DureeVie.trim());
			tab2.putExtra(ActivityParam.DateAchat, DateChoisie.trim());
			tab2.putExtra(ActivityParam.NumeroDeTeinte, numTeinte.trim());
			tab2.putExtra(ActivityParam.NomProduit, nomProduitRecup.trim());
			tab2.putExtra(ActivityParam.LaunchFromPage1, true);

			tab3.putExtra(ActivityParam.Marque, MarqueChoisie.trim());
			tab3.putExtra(ActivityParam.DurreeDeVie, DureeVie.trim());
			tab3.putExtra(ActivityParam.DateAchat, DateChoisie.trim());
			tab3.putExtra(ActivityParam.NumeroDeTeinte, numTeinte.trim());
			tab3.putExtra(ActivityParam.NomProduit, nomProduitRecup.trim());
			tab3.putExtra(ActivityParam.LaunchFromPage1, true);

			tab4.putExtra(ActivityParam.Marque, MarqueChoisie.trim());
			tab4.putExtra(ActivityParam.DurreeDeVie, DureeVie.trim());
			tab4.putExtra(ActivityParam.DateAchat, DateChoisie.trim());
			tab4.putExtra(ActivityParam.NumeroDeTeinte, numTeinte.trim());
			tab4.putExtra(ActivityParam.NomProduit, nomProduitRecup.trim());
			tab4.putExtra(ActivityParam.LaunchFromPage1, true);
		}

		if (isLaunchBypagerecap) {
			String MarqueChoisie = getIntent().getStringExtra(ActivityParam.Marque);
			String DureeVie = getIntent().getStringExtra(ActivityParam.DurreeDeVie);
			String DateChoisie = getIntent().getStringExtra(ActivityParam.DateAchat);
			String numTeinte = getIntent().getStringExtra(ActivityParam.NumeroDeTeinte);
			String nomProduitRecup = getIntent().getStringExtra(ActivityParam.NomProduit);

			tab1.putExtra(ActivityParam.Marque, MarqueChoisie.trim());
			tab1.putExtra(ActivityParam.DurreeDeVie, DureeVie.trim());
			tab1.putExtra(ActivityParam.DateAchat, DateChoisie.trim());
			tab1.putExtra(ActivityParam.NumeroDeTeinte, numTeinte.trim());
			tab1.putExtra(ActivityParam.NomProduit, nomProduitRecup.trim());
			tab1.putExtra(ActivityParam.LaunchFromPageRecap, true);

			tab2.putExtra(ActivityParam.Marque, MarqueChoisie.trim());
			tab2.putExtra(ActivityParam.DurreeDeVie, DureeVie.trim());
			tab2.putExtra(ActivityParam.DateAchat, DateChoisie.trim());
			tab2.putExtra(ActivityParam.NumeroDeTeinte, numTeinte.trim());
			tab2.putExtra(ActivityParam.NomProduit, nomProduitRecup.trim());
			tab2.putExtra(ActivityParam.LaunchFromPageRecap, true);

			tab3.putExtra(ActivityParam.Marque, MarqueChoisie.trim());
			tab3.putExtra(ActivityParam.DurreeDeVie, DureeVie.trim());
			tab3.putExtra(ActivityParam.DateAchat, DateChoisie.trim());
			tab3.putExtra(ActivityParam.NumeroDeTeinte, numTeinte.trim());
			tab3.putExtra(ActivityParam.NomProduit, nomProduitRecup.trim());
			tab3.putExtra(ActivityParam.LaunchFromPageRecap, true);

			tab4.putExtra(ActivityParam.Marque, MarqueChoisie.trim());
			tab4.putExtra(ActivityParam.DurreeDeVie, DureeVie.trim());
			tab4.putExtra(ActivityParam.DateAchat, DateChoisie.trim());
			tab4.putExtra(ActivityParam.NumeroDeTeinte, numTeinte.trim());
			tab4.putExtra(ActivityParam.NomProduit, nomProduitRecup.trim());
			tab4.putExtra(ActivityParam.LaunchFromPageRecap, true);
		}
		if (isLaunchByMain) {
			tab1.putExtra(ActivityParam.LaunchFromMain, true);
			tab2.putExtra(ActivityParam.LaunchFromMain, true);
			tab3.putExtra(ActivityParam.LaunchFromMain, true);
			tab4.putExtra(ActivityParam.LaunchFromMain, true);
		}

	}

}
