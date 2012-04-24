package fr.smardine.matroussedemaquillage.param;

import java.io.File;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import fr.smardine.matroussedemaquillage.ManipFichier;
import fr.smardine.matroussedemaquillage.R;
import fr.smardine.matroussedemaquillage.base.accesTable.AccesTableParams;
import fr.smardine.matroussedemaquillage.base.accesTable.AccesTableProduitEnregistre;

/**
 * @author smardine
 */
public class tab4 extends Activity implements OnClickListener {
	// BDAcces objBd;
	Button videBase, sauvegardeBase, importeBase;
	AlertDialog.Builder adSauvegarde, adImport, adImportResult;
	private int itemChoisi;
	private final String PATH = "/sdcard/ma_trousse/";
	private File baseDansTel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.parametres_tab4);
		videBase = (Button) findViewById(R.id.BtvideBase);
		sauvegardeBase = (Button) findViewById(R.id.BtSauvegarde);
		importeBase = (Button) findViewById(R.id.BtImporte);
		videBase.setOnClickListener(this);
		sauvegardeBase.setOnClickListener(this);
		importeBase.setOnClickListener(this);
		adSauvegarde = new AlertDialog.Builder(this);
		adSauvegarde.setTitle("Resultat de la copie sur la carte SD");
		adSauvegarde.setPositiveButton("Ok", null);
		adImport = new AlertDialog.Builder(this);
		adImport.setTitle("Quelle base voulez vous recuperer?");
		adImportResult = new AlertDialog.Builder(this);
		adImportResult
				.setTitle("Resultat de la recuperation depuis la carte SD");
		adImportResult.setPositiveButton("Ok", null);

	}

	@Override
	public void onClick(View p_v) {
		if (p_v.equals(videBase)) {
			// objBd = new BDAcces(this);
			// objBd.open();
			AccesTableProduitEnregistre accesProd = new AccesTableProduitEnregistre(
					p_v.getContext());
			accesProd.deleteTable();
			// int nbLignedeleted = objBd.deleteTable("produit_Enregistre",
			// null, null);
			// System.out.println(nbLignedeleted + " lignes effacée");
			// objBd.close();
		}
		if (p_v.equals(sauvegardeBase)) {
			// objBd = new BDAcces(this);
			// objBd.close();
			AccesTableParams accesParam = new AccesTableParams(this);
			String cheminBase = accesParam.getDatabasePath();
			// String cheminBase = objBd.getPath();
			File baseDansTel = new File(cheminBase);
			String PATH = "/sdcard/ma_trousse/";
			File path = new File(PATH);
			if (!path.exists()) {
				path.mkdirs();
			}
			int mYear;
			int mMonth;
			int mDay;
			final Calendar c = Calendar.getInstance();
			mYear = c.get(Calendar.YEAR);
			mMonth = c.get(Calendar.MONTH) + 1;
			mDay = c.get(Calendar.DAY_OF_MONTH);

			String sYear = "" + mYear;
			String sMonth;
			if (mMonth < 10) {
				sMonth = "0" + mMonth;
			} else {
				sMonth = "" + mMonth;
			}
			String sDay;
			if (mDay < 10) {
				sDay = "0" + mDay;
			} else {
				sDay = "" + mDay;
			}

			File fichierSurCarteSD = new File(PATH + "trousse_base" + sYear
					+ sMonth + sDay);

			boolean result = ManipFichier
					.copier(baseDansTel, fichierSurCarteSD);
			if (result) {
				adSauvegarde.setMessage("Operation reussie");
			} else {
				adSauvegarde.setMessage("Opération echouée");
			}
			adSauvegarde.show();

		}
		if (p_v.equals(importeBase)) {

			// objBd = new BDAcces(this);
			// objBd.close();
			AccesTableParams accesParam = new AccesTableParams(this);
			String cheminBase = accesParam.getDatabasePath();
			baseDansTel = new File(cheminBase);

			File path = new File(PATH);
			if (!path.exists()) {
				path.mkdirs();
			}

			final String[] NomFichier = recupereListeFichier(PATH);

			adImport.setSingleChoiceItems(NomFichier, 0,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int item) {
							/* User clicked on a radio button do some stuff */

							itemChoisi = item;

						}
					});
			adImport.setPositiveButton("Celui la",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface p_dialog,
								int p_which) {
							// TODO Auto-generated method stub
							String nomChoisi = NomFichier[itemChoisi]
									.toString();
							File fichierSurCarteSD = new File(PATH
									+ "trousse_base" + nomChoisi);
							if (!fichierSurCarteSD.exists()) {
								adImportResult
										.setMessage("Impossible de recuperer la base, le fichier n'existe pas");
								adImportResult.show();
								return;
							}
							boolean result = ManipFichier.copier(
									fichierSurCarteSD, baseDansTel);
							if (result) {
								adImportResult.setMessage("Operation reussie");
							} else {
								adImportResult.setMessage("Opération echouée");
							}
							adImportResult.show();

						}
					});

			adImport.show();

		}

	}

	private String[] recupereListeFichier(String directoryPath) {
		String[] liste = null;
		File directory = new File(directoryPath);

		// if (!directory.exists()) {
		// //
		// System.out.println("Le fichier/répertoire '"+directoryPath+"' n'existe pas");
		// } else if (directory.isFile()) {
		//
		// // nbDossier--;
		// // nbFichier++;
		//
		// } else {
		if (directory.isDirectory()) {
			File[] subfiles = directory.listFiles();

			if (subfiles != null) {// si subfiles=null, c'est que le dossier
									// a des restriction d'acces
				liste = new String[subfiles.length];
				for (int i = 0; i < subfiles.length; i++) {
					// LanceComptage(subfiles[i].toString());
					liste[i] = subfiles[i].toString()
							.substring(
									subfiles[i].toString().lastIndexOf(
											"trousse_base") + 12);
				}
				return liste;
			}

		}
		// }
		return liste;
	}

}
