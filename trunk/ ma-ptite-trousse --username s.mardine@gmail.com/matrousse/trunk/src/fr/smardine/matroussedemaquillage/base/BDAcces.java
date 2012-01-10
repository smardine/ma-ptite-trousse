package fr.smardine.matroussedemaquillage.base;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import fr.smardine.matroussedemaquillage.factory.RequeteFactory;

/**
 * @author smardine
 */
public class BDAcces {

	private static final String PRODUITS_TABLE = "trousse_produits";

	private static final String MARQUES_TABLE = "trousse_marques";

	private static final String TEMPO_TABLE = "trousse_tempo";

	private static final String DATABASE_NAME = "trousse_base";

	private final Context mCtx;

	/**
	 * 
	 */
	public DatabaseHelper mDbHelper;
	/**
	 * 
	 */
	private SQLiteDatabase mDb;

	/**
	 * 
	 */
	// private SQLiteQueryBuilder mbbuilder;

	private static final String TAG = "BDAcces";
	private static final int DATABASE_VERSION = 14;
	private static String Message_Erreur = "";

	/**
	 * @param ctx
	 */
	public BDAcces(Context ctx) {
		this.mCtx = ctx;
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			G_creation_base creation = new G_creation_base();
			for (String s : creation.getallCreation()) {
				db.execSQL(s);
			}

			G_maj_base maj = new G_maj_base();
			for (String s : maj.getAllVersion()) {
				db.execSQL(s);
			}

			// Log.e(TAG, "erreur application script: " + Script);
			// Log.e(TAG, "erreur: " + Message_Erreur);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
			G_maj_base maj = new G_maj_base();

			switch (oldVersion) {
				case 1:
					LanceMiseAJour(db, maj.getVersion2());
				case 2:
					LanceMiseAJour(db, maj.getVersion3());
				case 3:
					LanceMiseAJour(db, maj.getVersion4());
				case 4:
					LanceMiseAJour(db, maj.getVersion5());
				case 5:
					LanceMiseAJour(db, maj.getVersion6());
				case 6:
					LanceMiseAJour(db, maj.getVersion7());
				case 7:
					LanceMiseAJour(db, maj.getVersion8());
				case 8:
					LanceMiseAJour(db, maj.getVersion9());
				case 9:
					LanceMiseAJour(db, maj.getVersion10());
				case 10:
					LanceMiseAJour(db, maj.getVersion11());
				case 11:
					LanceMiseAJour(db, maj.getVersion12());
				case 12:
					LanceMiseAJour(db, maj.getVersion13());
				case 13:
					LanceMiseAJour(db, maj.getVersion14());

			}

		}

		private void LanceMiseAJour(SQLiteDatabase p_db, List<String> p_lstScripts) {
			for (String s : p_lstScripts) {
				p_db.execSQL(s);
			}

		}
	}

	/**
	 * @throws SQLException
	 */
	public void open() throws SQLException {
		// si la base est deja ouverte, on la ferme.
		if (mDb != null && mDb.isOpen()) {
			close();
		}
		// ouverture du helper
		mDbHelper = new DatabaseHelper(mCtx);
		// ouverture de la base
		mDb = mDbHelper.getWritableDatabase();
	}

	/**
	 * @return le path de la database
	 */
	public String getPath() {
		return mDb.getPath();
	}

	/**
	 * ferme la connexion a la database
	 * @throws SQLException
	 */
	public void close() throws SQLException {
		// si il reste des transaction active, on les ferme.
		if (mDb.inTransaction()) {
			mDb.endTransaction();
		}
		// on commence par fermer la base
		mDb.close();
		// on ferme le helper.
		mDbHelper.close();
	}

	// /**
	// * @param colonne
	// * @param OrderBy
	// * @param GroupBy
	// * @return la liste des produits enregistres en base
	// */
	// public Cursor recupererLaListeDesProduits(String[] colonne, String OrderBy, String GroupBy) {
	// try {
	//
	// String condition = "";
	// String[] conditionArgs = null;
	//
	// String having = "";
	//
	// return mDb.query("produit_Enregistre", colonne, condition, conditionArgs, GroupBy, having, OrderBy);
	//
	// } catch (SQLException e) {
	// Log.d(TAG, ">> recupererLaListeDesBenefs ERROR: " + e.getLocalizedMessage());
	// throw e;
	// }
	// }

	/**
	 * @param ScriptSQL
	 * @return le nombre de produit perimé
	 */
	public int revoiNbProdPerimeOuPresquePerime(String ScriptSQL) {
		RequeteFactory requeteFact = new RequeteFactory(mCtx);
		return Integer.parseInt(requeteFact.get1Champ(ScriptSQL));
		// Cursor objCursor = mDb.rawQuery(ScriptSQL, null);
		// int itotal = objCursor.getCount();
		// objCursor.close();
		// return itotal;
	}

	/**
	 * @param ScriptSQL
	 * @param p_colonnes
	 * @return la liste complete des produits
	 */
	public ArrayList<String>[] renvoi_liste_TrousseFinalAvecFiltrage(String ScriptSQL, String[] p_colonnes) {
		// String SQL = "SELECT " + p_colonnes + " FROM produit_Enregistre where nom_souscatergorie LIKE '%?%'";

		Cursor objCursor = mDb.rawQuery(ScriptSQL, null);
		int iPostidProduit = objCursor.getColumnIndex(p_colonnes[0]);
		int iPostnomProduit = objCursor.getColumnIndex(p_colonnes[1]);
		int iPostDateAchat = objCursor.getColumnIndex(p_colonnes[2]);
		int iPostDateAchat1 = objCursor.getColumnIndex(p_colonnes[3]);

		int itotal = objCursor.getCount();
		ArrayList<String> aTableRetourId = new ArrayList<String>();
		ArrayList<String> aTableRetourNom = new ArrayList<String>();
		ArrayList<String> aTableRetourDateAchat = new ArrayList<String>();
		ArrayList<String> aTableRetourDateAchat1 = new ArrayList<String>();

		objCursor.moveToFirst();

		ArrayList<String>[] aTableRetour = new ArrayList[4];

		/* Check if our result was valid. */
		if (objCursor != null) {
			for (int i = 0; i < itotal; i++) {
				String resultId = "", resultnom_produits = "", resultDateAchat = "", resultDateAchat1 = "";// ,resultDatePeremption="";
				resultId = objCursor.getString(iPostidProduit);
				resultnom_produits = objCursor.getString(iPostnomProduit);
				resultDateAchat = objCursor.getString(iPostDateAchat);
				resultDateAchat1 = objCursor.getString(iPostDateAchat1);

				if (!resultId.equals(null)) {
					aTableRetourId.add(resultId);
				} else {
					aTableRetourId.add("vide");
				}
				if (!resultnom_produits.equals(null)) {
					aTableRetourNom.add(resultnom_produits);
				} else {
					aTableRetourNom.add("vide");
				}

				if (!resultDateAchat.equals(null)) {
					aTableRetourDateAchat.add(resultDateAchat);
				} else {
					aTableRetourDateAchat.add("vide");
				}

				if (!resultDateAchat1.equals(null)) {
					aTableRetourDateAchat1.add(resultDateAchat1);
				} else {
					aTableRetourDateAchat1.add("vide");
				}

				objCursor.moveToNext();
			}
		}
		objCursor.close();
		aTableRetour[0] = aTableRetourId;
		aTableRetour[1] = aTableRetourNom;
		aTableRetour[2] = aTableRetourDateAchat;
		aTableRetour[3] = aTableRetourDateAchat1;

		return aTableRetour;
	}

	/**
	 * @param colonne
	 * @param OrderBy
	 * @param GroupBy
	 * @param condition
	 * @param conditionArgs
	 * @return la liste des produits
	 */
	public ArrayList<String>[] renvoi_liste_TrousseFinal(String[] colonne, String OrderBy, String GroupBy, String condition,
			String[] conditionArgs) {

		// String[] colonne= new String[]{"nom_produit","DateAchat","Date_Peremption"};
		// String condition = "nom_categorie='"+Catégorie+"'";
		// String condition = "";
		// String[] conditionArgs = null;

		String having = "";

		Cursor objCursor = mDb.query("produit_Enregistre", colonne, condition, conditionArgs, GroupBy, having, OrderBy);
		int iPostidProduit = objCursor.getColumnIndex(colonne[0]);
		int iPostnomProduit = objCursor.getColumnIndex(colonne[1]);
		int iPostDateAchat = objCursor.getColumnIndex(colonne[2]);
		int iPostDateAchat1 = objCursor.getColumnIndex(colonne[3]);

		int itotal = objCursor.getCount();
		ArrayList<String> aTableRetourId = new ArrayList<String>();
		ArrayList<String> aTableRetourNom = new ArrayList<String>();
		ArrayList<String> aTableRetourDateAchat = new ArrayList<String>();
		ArrayList<String> aTableRetourDateAchat1 = new ArrayList<String>();

		objCursor.moveToFirst();
		ArrayList<String>[] aTableRetour = new ArrayList[5];

		/* Check if our result was valid. */
		if (objCursor != null) {
			for (int i = 0; i < itotal; i++) {
				String resultId = "", resultnom_produits = "", resultDateAchat = "", resultDateAchat1 = "";// ,resultDatePeremption="";
				resultId = objCursor.getString(iPostidProduit);
				resultnom_produits = objCursor.getString(iPostnomProduit);
				resultDateAchat = objCursor.getString(iPostDateAchat);
				resultDateAchat1 = objCursor.getString(iPostDateAchat1);

				if (!resultId.equals(null)) {
					aTableRetourId.add(resultId);
				} else {
					aTableRetourId.add("vide");
				}
				if (!resultnom_produits.equals(null)) {
					aTableRetourNom.add(resultnom_produits);
				} else {
					aTableRetourNom.add("vide");
				}

				if (!resultDateAchat.equals(null)) {
					aTableRetourDateAchat.add(resultDateAchat);
				} else {
					aTableRetourDateAchat.add("vide");
				}

				if (!resultDateAchat1.equals(null)) {
					aTableRetourDateAchat1.add(resultDateAchat1);
				} else {
					aTableRetourDateAchat1.add("vide");
				}

				objCursor.moveToNext();
			}
		}
		objCursor.close();
		aTableRetour[0] = aTableRetourId;
		aTableRetour[1] = aTableRetourNom;
		aTableRetour[2] = aTableRetourDateAchat;
		aTableRetour[3] = aTableRetourDateAchat1;
		// aTableRetour[2]=aTableRetourDatePeremption;

		return aTableRetour;
	}

	/**
	 * @param colonne
	 * @param OrderBy
	 * @param GroupBy
	 * @param condition
	 * @param conditionArgs
	 * @return la liste des notes enregistrees en base
	 */
	public ArrayList<String>[] renvoi_liste_Note(String[] colonne, String OrderBy, String GroupBy, String condition, String[] conditionArgs) {

		// String[] colonne= new String[]{"nom_produit","DateAchat","Date_Peremption"};
		// String condition = "nom_categorie='"+Catégorie+"'";
		// String condition = "";
		// String[] conditionArgs = null;

		String having = "";

		Cursor objCursor = mDb.query("Notes", colonne, condition, conditionArgs, GroupBy, having, OrderBy);
		int iPostidProduit = objCursor.getColumnIndex(colonne[0]);
		int iPostnomProduit = objCursor.getColumnIndex(colonne[1]);

		int itotal = objCursor.getCount();
		ArrayList<String> aTableRetourId = new ArrayList<String>();
		ArrayList<String> aTableRetourNom = new ArrayList<String>();

		objCursor.moveToFirst();
		ArrayList<String>[] aTableRetour = new ArrayList[3];

		/* Check if our result was valid. */
		if (objCursor != null) {
			for (int i = 0; i < itotal; i++) {
				String resultId = "", resultnom_produits = "";
				resultId = objCursor.getString(iPostidProduit);
				resultnom_produits = objCursor.getString(iPostnomProduit);

				if (!resultId.equals(null)) {
					aTableRetourId.add(resultId);
				} else {
					aTableRetourId.add("vide");
				}
				if (!resultnom_produits.equals(null)) {
					aTableRetourNom.add(resultnom_produits);
				} else {
					aTableRetourNom.add("vide");
				}

				objCursor.moveToNext();
			}
		}
		objCursor.close();
		aTableRetour[0] = aTableRetourId;
		aTableRetour[1] = aTableRetourNom;

		return aTableRetour;
	}

	/**
	 * @param colonne
	 * @param OrderBy
	 * @param GroupBy
	 * @param condition
	 * @param conditionArgs
	 * @return la liste complete des notes en base.
	 */
	public ArrayList<String>[] renvoi_liste_NoteTotale(String[] colonne, String OrderBy, String GroupBy, String condition,
			String[] conditionArgs) {

		// String[] colonne= new String[]{"nom_produit","DateAchat","Date_Peremption"};
		// String condition = "id_note='"+Catégorie+"'";
		// String condition = "";
		// String[] conditionArgs = null;

		String having = "";

		Cursor objCursor = mDb.query("Notes", colonne, condition, conditionArgs, GroupBy, having, OrderBy);
		int iPostidProduit = objCursor.getColumnIndex(colonne[0]);
		int iPostnomProduit = objCursor.getColumnIndex(colonne[1]);
		int iPostMessage = objCursor.getColumnIndex(colonne[2]);

		int itotal = objCursor.getCount();
		ArrayList<String> aTableRetourId = new ArrayList<String>();
		ArrayList<String> aTableRetourNom = new ArrayList<String>();
		ArrayList<String> aTableRetourMessage = new ArrayList<String>();

		objCursor.moveToFirst();
		ArrayList<String>[] aTableRetour = new ArrayList[4];

		/* Check if our result was valid. */
		if (objCursor != null) {
			for (int i = 0; i < itotal; i++) {
				String resultId = "", resultnom_produits = "", resultMessage = "";
				resultId = objCursor.getString(iPostidProduit);
				resultnom_produits = objCursor.getString(iPostnomProduit);
				resultMessage = objCursor.getString(iPostMessage);

				if (!resultId.equals(null)) {
					aTableRetourId.add(resultId);
				} else {
					aTableRetourId.add("vide");
				}
				if (!resultnom_produits.equals(null)) {
					aTableRetourNom.add(resultnom_produits);
				} else {
					aTableRetourNom.add("vide");
				}
				if (!resultMessage.equals(null)) {
					aTableRetourMessage.add(resultMessage);
				} else {
					aTableRetourMessage.add("vide");
				}
				objCursor.moveToNext();
			}
		}
		objCursor.close();
		aTableRetour[0] = aTableRetourId;
		aTableRetour[1] = aTableRetourNom;
		aTableRetour[2] = aTableRetourMessage;

		return aTableRetour;
	}

	/**
	 * @param colonne
	 * @param OrderBy
	 * @param GroupBy
	 * @return la liste des dates de peremptions
	 */

	public ArrayList<String>[] VerifAuDemarrage(String[] colonne, String OrderBy, String GroupBy) {

		String condition = "";
		String[] conditionArgs = null;

		String having = "";

		Cursor objCursor = mDb.query("produit_Enregistre", colonne, condition, conditionArgs, GroupBy, having, OrderBy);
		int iPostDatePermemp = objCursor.getColumnIndex(colonne[0]);
		int iPostIdProduit = objCursor.getColumnIndex(colonne[1]);
		int iPostDateAchat = objCursor.getColumnIndex(colonne[2]);
		int iPostDureeVie = objCursor.getColumnIndex(colonne[3]);

		int itotal = objCursor.getCount();
		ArrayList<String> aTableRetourId = new ArrayList<String>();
		ArrayList<String> aTableRetourNom = new ArrayList<String>();
		ArrayList<String> aTableRetourDateAchat = new ArrayList<String>();
		ArrayList<String> aTableRetourDureeVie = new ArrayList<String>();

		objCursor.moveToFirst();
		ArrayList<String>[] aTableRetour = new ArrayList[5];

		/* Check if our result was valid. */
		if (objCursor != null) {
			for (int i = 0; i < itotal; i++) {
				String resultDatePeremption = "", resultId_produits = "", resultDateAchat = "", resultDureeVie = "";// ,resultDatePeremption="";
				resultDatePeremption = objCursor.getString(iPostDatePermemp);
				resultId_produits = objCursor.getString(iPostIdProduit);
				resultDateAchat = objCursor.getString(iPostDateAchat);
				resultDureeVie = objCursor.getString(iPostDureeVie);

				if (resultDatePeremption != null) {
					aTableRetourId.add(resultDatePeremption);
				} else {
					aTableRetourId.add(null);
				}
				if (resultId_produits != null) {
					aTableRetourNom.add(resultId_produits);
				} else {
					aTableRetourNom.add("vide");
				}

				if (resultDateAchat != null) {
					aTableRetourDateAchat.add(resultDateAchat);
				} else {
					aTableRetourDateAchat.add("vide");
				}
				if (resultDureeVie != null) {
					aTableRetourDureeVie.add(resultDureeVie);
				} else {
					aTableRetourDureeVie.add(null);
				}

				objCursor.moveToNext();
			}
		}
		objCursor.close();
		aTableRetour[0] = aTableRetourId;
		aTableRetour[1] = aTableRetourNom;
		aTableRetour[2] = aTableRetourDateAchat;
		aTableRetour[3] = aTableRetourDureeVie;

		return aTableRetour;
	}

	// /**
	// * @param TABLE
	// * @return nombre de champs dans une table
	// */
	// public int renvoi_nbChamp(String TABLE) {
	// open();
	// Cursor objCursor = mDb.query(TABLE, null, null, null, null, null, null);
	// int iNbChamp = 0;
	// iNbChamp = objCursor.getCount();
	// objCursor.close();
	// return iNbChamp;
	//
	// }

	/**
	 * @param colonne
	 * @param id
	 * @return la liste complete
	 */
	public ArrayList<String>[] renvoi_liste_TrousseFinalComplete(String[] colonne, String id) {

		// String[] colonne= new String[]{"nom_produit","DateAchat","Date_Peremption"};
		String condition = "id_produits='" + id + "'";
		// String condition = "";
		String[] conditionArgs = null;

		String having = "";

		Cursor objCursor = mDb.query("produit_Enregistre", colonne, condition, conditionArgs, "", having, "");
		int iPostNomProduit = objCursor.getColumnIndex(colonne[0]);
		int iPostNomSousCat = objCursor.getColumnIndex(colonne[1]);
		int iPostNomCat = objCursor.getColumnIndex(colonne[2]);
		int iPostNumTeinte = objCursor.getColumnIndex(colonne[3]);
		int iPostDurreeVie = objCursor.getColumnIndex(colonne[4]);
		int iPostDatePeremption = objCursor.getColumnIndex(colonne[5]);
		int iPostDateAchat = objCursor.getColumnIndex(colonne[6]);
		int iPostMarque = objCursor.getColumnIndex(colonne[7]);

		int itotal = objCursor.getCount();

		ArrayList<String> aTableRetourNomProduit = new ArrayList<String>();
		ArrayList<String> aTableRetourNomSousCat = new ArrayList<String>();
		ArrayList<String> aTableRetourNomCat = new ArrayList<String>();
		ArrayList<String> aTableRetourNumTeinte = new ArrayList<String>();
		ArrayList<String> aTableRetourDureeVie = new ArrayList<String>();
		ArrayList<String> aTableRetourDatePeremp = new ArrayList<String>();
		ArrayList<String> aTableRetourDateAchat = new ArrayList<String>();
		ArrayList<String> aTableRetourMarque = new ArrayList<String>();

		objCursor.moveToFirst();

		ArrayList<String>[] aTableRetour = new ArrayList[objCursor.getColumnCount() + 1];

		/* Check if our result was valid. */
		if (objCursor != null) {
			for (int i = 0; i < itotal; i++) {
				String resultNomProduit = "", resultNomSousCat = "", resultNomCat = "", resultNumTeinte = "", resultDureeVie = "", resultDatePeremp = "", resultDateAchat = "", resultMarque = "";
				resultNomProduit = objCursor.getString(iPostNomProduit);
				resultNomSousCat = objCursor.getString(iPostNomSousCat);
				resultNomCat = objCursor.getString(iPostNomCat);
				resultNumTeinte = objCursor.getString(iPostNumTeinte);
				resultDureeVie = objCursor.getString(iPostDurreeVie);
				resultDatePeremp = objCursor.getString(iPostDatePeremption);
				resultDateAchat = objCursor.getString(iPostDateAchat);
				resultMarque = objCursor.getString(iPostMarque);

				if (resultNomProduit != null) {
					aTableRetourNomProduit.add(resultNomProduit);
				} else {
					aTableRetourNomProduit.add("vide");
				}
				if (resultNomSousCat != null) {
					aTableRetourNomSousCat.add(resultNomSousCat);
				} else {
					aTableRetourNomSousCat.add("vide");
				}

				if (resultNomCat != null) {
					aTableRetourNomCat.add(resultNomCat);
				} else {
					aTableRetourNomCat.add("vide");
				}
				if (resultNumTeinte != null) {
					aTableRetourNumTeinte.add(resultNumTeinte);
				} else {
					aTableRetourNumTeinte.add("vide");
				}
				if (resultDureeVie != null) {
					aTableRetourDureeVie.add(resultDureeVie);
				} else {
					aTableRetourDureeVie.add("vide");
				}
				if (resultDatePeremp != null) {
					aTableRetourDatePeremp.add(resultDatePeremp);
				} else {
					aTableRetourDatePeremp.add("vide");
				}
				if (resultDateAchat != null) {
					aTableRetourDateAchat.add(resultDateAchat);
				} else {
					aTableRetourDateAchat.add("vide");
				}
				if (resultMarque != null) {
					aTableRetourMarque.add(resultMarque);
				} else {
					aTableRetourMarque.add("vide");
				}
				objCursor.moveToNext();
			}
		}
		objCursor.close();
		aTableRetour[0] = aTableRetourNomProduit;
		aTableRetour[1] = aTableRetourNomSousCat;
		aTableRetour[2] = aTableRetourNomCat;
		aTableRetour[3] = aTableRetourNumTeinte;
		aTableRetour[4] = aTableRetourDureeVie;
		aTableRetour[5] = aTableRetourDatePeremp;
		aTableRetour[6] = aTableRetourDateAchat;
		aTableRetour[7] = aTableRetourMarque;

		return aTableRetour;
	}

	/**
	 * @return la liste des produits temporaire
	 */
	public ArrayList<String>[] renvoi_liste_TrousseTempo() {

		String[] colonne = new String[] { "nom_produit", "numero_Teinte", "DateAchat", "Duree_Vie" };
		// String condition = "nom_categorie='"+Catégorie+"'";
		String condition = "";
		String[] conditionArgs = null;
		String groupby = "";
		String having = "";
		String orderby = "";
		Cursor objCursor = mDb.query("trousse_tempo", colonne, condition, conditionArgs, groupby, having, orderby);
		int iPostnomProduit = objCursor.getColumnIndex("nom_produit");
		int iPostTeinte = objCursor.getColumnIndex("numero_Teinte");
		int iPostDateAchat = objCursor.getColumnIndex("DateAchat");
		int iPostDureeVie = objCursor.getColumnIndex("Duree_Vie");

		int itotal = objCursor.getCount();
		ArrayList<String> aTableRetourNom = new ArrayList<String>();
		ArrayList<String> aTableRetourTeinte = new ArrayList<String>();
		ArrayList<String> aTableRetourDateAchat = new ArrayList<String>();
		ArrayList<String> aTableRetourDureeVie = new ArrayList<String>();

		objCursor.moveToFirst();
		ArrayList<String>[] aTableRetour = new ArrayList[4];

		/* Check if our result was valid. */
		if (objCursor != null) {
			for (int i = 0; i < itotal; i++) {
				String resultnom_produits = "", resultTeinte = "", resultDateAchat = "", resultDureeVie = "";
				resultnom_produits = objCursor.getString(iPostnomProduit);
				resultTeinte = objCursor.getString(iPostTeinte);
				resultDateAchat = objCursor.getString(iPostDateAchat);
				resultDureeVie = objCursor.getString(iPostDureeVie);

				if (resultnom_produits.equals(null)) {
					aTableRetourNom.add(resultnom_produits);
				} else {
					aTableRetourNom.add("vide");
				}

				if (!resultTeinte.equals(null)) {
					aTableRetourTeinte.add(resultTeinte);
				} else {
					aTableRetourTeinte.add("vide");
				}

				if (!resultDateAchat.equals(null)) {
					aTableRetourDateAchat.add(resultDateAchat);
				} else {
					aTableRetourDateAchat.add("vide");
				}

				if (!resultDureeVie.equals(null)) {
					aTableRetourDureeVie.add(resultDureeVie);
				} else {
					aTableRetourDureeVie.add("vide");
				}
				objCursor.moveToNext();
			}
		}
		objCursor.close();
		aTableRetour[0] = aTableRetourNom;
		aTableRetour[1] = aTableRetourTeinte;
		aTableRetour[2] = aTableRetourDateAchat;
		aTableRetour[3] = aTableRetourDureeVie;

		return aTableRetour;
	}

	/**
	 * @param Table
	 * @param Colonne
	 * @return
	 */
	public String[] renvoi_liste_ValeurDansString(String Table, String Colonne) {

		String[] colonne = new String[] { Colonne };
		String condition = "";
		String[] conditionArgs = null;
		String groupby = "";
		String having = "";
		String orderby = "";
		Cursor objCursor = mDb.query(Table, colonne, condition, conditionArgs, groupby, having, orderby);
		int iPostNomProduits = objCursor.getColumnIndex(Colonne);
		// int iPostisChecked = objCursor.getColumnIndex("ischecked");

		int itotal = objCursor.getCount();
		// ArrayList<String> aTableRetourNom = new ArrayList<String>();
		// ArrayList<String> aTableRetourisChecked = new ArrayList<String>();

		objCursor.moveToFirst();
		String aTableRetour[] = new String[itotal];

		/* Check if our result was valid. */
		if (objCursor != null) {
			for (int i = 0; i < itotal; i++) {
				String resultnom_produits = objCursor.getString(iPostNomProduits);
				// String resultischecked = objCursor.getString(iPostisChecked);
				aTableRetour[i] = resultnom_produits;

				// aTableRetourisChecked.add(resultischecked);
				objCursor.moveToNext();
			}
		}
		objCursor.close();
		// aTableRetour=aTableRetourNom;
		// aTableRetour[1]=aTableRetourisChecked;
		return aTableRetour;
	}

	/**
	 * @param Table
	 * @param Colonne
	 * @return
	 * @throws Exception
	 */
	public String[] renvoi_liste_Valeur(String Table, String Colonne) throws Exception {
		open();
		String[] colonne = new String[] { Colonne };
		// String condition = "nom_categorie='"+Catégorie+"'";
		String condition = "";
		String[] conditionArgs = null;
		String groupby = "";
		String having = "";
		String orderby = "";
		Cursor objCursor = mDb.query(Table, colonne, condition, conditionArgs, groupby, having, orderby);
		int iPostNomProduits = objCursor.getColumnIndex(Colonne);
		// int iPostisChecked = objCursor.getColumnIndex("ischecked");

		int itotal = objCursor.getCount();
		// ArrayList<String> aTableRetourNom = new ArrayList<String>();
		// ArrayList<String> aTableRetourisChecked = new ArrayList<String>();

		objCursor.moveToFirst();
		String aTableRetour[] = new String[itotal + 1];

		/* Check if our result was valid. */
		if (objCursor != null) {
			for (int i = 0; i < itotal; i++) {
				String resultnom_produits = objCursor.getString(iPostNomProduits);
				// String resultischecked = objCursor.getString(iPostisChecked);
				aTableRetour[i] = resultnom_produits;

				// aTableRetourisChecked.add(resultischecked);
				objCursor.moveToNext();
			}
		}
		objCursor.close();
		return aTableRetour;
	}

	/**
	 * 
	 */
	public void update_bdd() {
		int newversion = mDb.getVersion() + 1;
		mDbHelper.onUpgrade(mDb, mDb.getVersion(), newversion);

	}

	// /**
	// * @param Table
	// * @param modifiedValues
	// * @param whereClause
	// * @param whereArgs
	// * @return
	// */
	// public int majTable(String Table, ContentValues modifiedValues, String whereClause, String[] whereArgs) {
	// // TODO Auto-generated method stub
	// int nbdeChampAffecté = 0;
	// try {
	// nbdeChampAffecté = mDb.update(Table, modifiedValues, whereClause, whereArgs);
	// } catch (Exception e) {
	// String message = "erreur: " + e;
	// System.out.println(message);
	// }
	//
	// return nbdeChampAffecté;
	//
	// }

	/**
	 * @return
	 */

	public ArrayList<String>[] renvoiCategorieCochée() {
		String[] colonne = new String[] { "nom_souscatergorie" };
		String condition = "ischecked='true'";
		String[] conditionArgs = null;
		String groupby = "";
		String having = "";
		String orderby = "";
		Cursor objCursor = mDb.query(PRODUITS_TABLE, colonne, condition, conditionArgs, groupby, having, orderby);
		int iPostNomProduits = objCursor.getColumnIndex("nom_souscatergorie");

		int itotal = objCursor.getCount();
		ArrayList<String>[] aTableRetour = new ArrayList[itotal + 1];
		ArrayList<String> aTableRetourNom = new ArrayList<String>();
		if (itotal != 0) {

			objCursor.moveToFirst();
			/* Check if our result was valid. */
			if (objCursor != null) {
				for (int i = 0; i < itotal; i++) {
					String resultnom_produits = objCursor.getString(iPostNomProduits);
					aTableRetourNom.add(resultnom_produits);
					objCursor.moveToNext();
				}
			}
			objCursor.close();
			aTableRetour[0] = aTableRetourNom;
		} else {
			aTableRetourNom.add("aucun");
			aTableRetour[0] = aTableRetourNom;
		}
		// aTableRetour[1]=aTableRetourisChecked;
		return aTableRetour;

	}

	/**
	 * @param Table
	 * @param values
	 * @return
	 */
	public boolean InsertDonnéedansTable(String Table, ContentValues values) {

		long RowNumber = mDb.insert(Table, null, values);
		if (RowNumber == -1) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * @param table
	 * @param whereClause
	 * @param whereArgs
	 * @return
	 */
	public int deleteTable(String table, String whereClause, String[] whereArgs) {

		int RowNumber = mDb.delete(table, whereClause, whereArgs);

		return RowNumber;
		// TODO Auto-generated method stub

	}

	/**
	 * @param Table
	 * @param Colonnes
	 * @return
	 */

	public ArrayList[] renvoi_liste_ValeurTroussetempo(String Table, String[] Colonnes) {
		String[] colonne = Colonnes;
		// String condition = "nom_categorie='"+Catégorie+"'";
		String condition = "";
		String[] conditionArgs = null;
		String groupby = "";
		String having = "";
		String orderby = "";
		Cursor objCursor = mDb.query(Table, colonne, condition, conditionArgs, groupby, having, orderby);
		int iPostNomMarques = objCursor.getColumnIndex(Colonnes[0]);
		int iPostNomProduit = objCursor.getColumnIndex(Colonnes[1]);
		int iPostNumeroTeinte = objCursor.getColumnIndex(Colonnes[2]);
		int iPostDateAchat = objCursor.getColumnIndex(Colonnes[3]);
		int iPostDureeVie = objCursor.getColumnIndex(Colonnes[4]);

		int itotal = objCursor.getCount();
		ArrayList<String> aTableRetourNomMarque = new ArrayList<String>();
		ArrayList<String> aTableRetourNomProduit = new ArrayList<String>();
		ArrayList<String> aTableRetourNumeroTeinte = new ArrayList<String>();
		ArrayList<String> aTableRetourDateAchat = new ArrayList<String>();
		ArrayList<String> aTableRetourDureeVie = new ArrayList<String>();

		objCursor.moveToFirst();
		ArrayList<?> aTableRetour[] = new ArrayList[5];

		/* Check if our result was valid. */
		if (objCursor != null) {
			for (int i = 0; i < itotal; i++) {
				String Nom_marque = objCursor.getString(iPostNomMarques);
				String Nom_Produit = objCursor.getString(iPostNomProduit);
				String Num_Teinte = objCursor.getString(iPostNumeroTeinte);
				String DateAchat = objCursor.getString(iPostDateAchat);
				String DureeVie = objCursor.getString(iPostDureeVie);

				aTableRetourNomMarque.add(Nom_marque);
				aTableRetourNomProduit.add(Nom_Produit);
				aTableRetourNumeroTeinte.add(Num_Teinte);
				aTableRetourDateAchat.add(DateAchat);
				aTableRetourDureeVie.add(DureeVie);

				objCursor.moveToNext();
			}
		}
		objCursor.close();
		aTableRetour[0] = aTableRetourNomMarque;
		aTableRetour[1] = aTableRetourNomProduit;
		aTableRetour[2] = aTableRetourNumeroTeinte;
		aTableRetour[3] = aTableRetourDateAchat;
		aTableRetour[4] = aTableRetourDureeVie;

		return aTableRetour;
	}

	public ArrayList[] renvoiCategorieEtProduitCochée() {
		String[] colonne = new String[] { "nom_souscatergorie", "nom_categorie" };
		String condition = "ischecked='true'";
		String[] conditionArgs = null;
		String groupby = "";
		String having = "";
		String orderby = "";
		Cursor objCursor = mDb.query(PRODUITS_TABLE, colonne, condition, conditionArgs, groupby, having, orderby);
		int iPostNomProduits = objCursor.getColumnIndex("nom_souscatergorie");
		int iPostNomCatégories = objCursor.getColumnIndex("nom_categorie");

		int itotal = objCursor.getCount();
		ArrayList[] aTableRetour = new ArrayList[2];
		ArrayList<String> aTableRetourNom = new ArrayList<String>();
		ArrayList<String> aTableRetourCatégorie = new ArrayList<String>();
		if (itotal != 0) {

			objCursor.moveToFirst();
			/* Check if our result was valid. */
			if (objCursor != null) {
				for (int i = 0; i < itotal; i++) {
					String NomProduits = objCursor.getString(iPostNomProduits);
					String NomCategorie = objCursor.getString(iPostNomCatégories);

					aTableRetourNom.add(NomProduits);
					aTableRetourCatégorie.add(NomCategorie);
					objCursor.moveToNext();
				}
			}
			objCursor.close();
			aTableRetour[0] = aTableRetourNom;
			aTableRetour[1] = aTableRetourCatégorie;
		} else {
			aTableRetourNom.add("aucun");
			aTableRetour[0] = aTableRetourNom;
		}
		// aTableRetour[1]=aTableRetourisChecked;
		return aTableRetour;
	}

	/**
	 * @param colonne
	 * @return
	 */

	public ArrayList<String>[] renvoi_param(String[] colonne) {
		// open();

		// String[] colonne= new String[]{"nom_produit","DateAchat","Date_Peremption"};
		// String condition = "id_produits='"+id+"'";
		String condition = "";
		String[] conditionArgs = null;

		String having = "";

		Cursor objCursor = mDb.query("Param", colonne, condition, conditionArgs, "", having, "");
		int iPostAfficheAlerte = objCursor.getColumnIndex(colonne[0]);
		int iPostDureeViePeremp = objCursor.getColumnIndex(colonne[1]);
		int PostTheme = objCursor.getColumnIndex(colonne[2]);

		int itotal = objCursor.getCount();

		ArrayList<String> aTableRetourAfficheAlerte = new ArrayList<String>();
		ArrayList<String> aTableRetourDureeViePeremp = new ArrayList<String>();
		ArrayList<String> aTableRetourTheme = new ArrayList<String>();

		objCursor.moveToFirst();

		ArrayList[] aTableRetour = new ArrayList[objCursor.getColumnCount() + 1];

		/* Check if our result was valid. */
		if (objCursor != null) {
			for (int i = 0; i < itotal; i++) {
				String resultDureeViePeremp = "", resultAfficheAlerte = "", resultTheme = "";
				resultAfficheAlerte = objCursor.getString(iPostAfficheAlerte);
				resultDureeViePeremp = objCursor.getString(iPostDureeViePeremp);
				resultTheme = objCursor.getString(PostTheme);

				if (!resultAfficheAlerte.equals(null)) {
					aTableRetourAfficheAlerte.add(resultAfficheAlerte);
				} else {
					aTableRetourAfficheAlerte.add("vide");
				}
				if (!resultDureeViePeremp.equals(null)) {
					aTableRetourDureeViePeremp.add(resultDureeViePeremp);
				} else {
					aTableRetourDureeViePeremp.add("vide");
				}
				if (!resultTheme.equals(null)) {
					aTableRetourTheme.add(resultTheme);
				} else {
					aTableRetourTheme.add("vide");
				}

				objCursor.moveToNext();
			}
		}
		objCursor.close();
		aTableRetour[0] = aTableRetourAfficheAlerte;
		aTableRetour[1] = aTableRetourDureeViePeremp;
		aTableRetour[2] = aTableRetourTheme;

		return aTableRetour;
	}

	/**
	 * @param ScriptSql
	 */
	public void execSQL(String ScriptSql) {
		// TODO Auto-generated method stub
		mDb.execSQL(ScriptSql);

	}

	/**
	 * @param Element
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<ArrayList> renvoi_liste_script_xml(String Element) throws Exception {

		// ***********************création de notre tableau dinamique
		ArrayList<ArrayList> aTableRetour = new ArrayList<ArrayList>();

		// ************************récupération du flux wml
		URL myURL = new URL("http://simon.mardine.free.fr/trousse_maquillage/bdd.xml");
		DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();
		// création d'un constructeur de documents
		DocumentBuilder constructeur = fabrique.newDocumentBuilder();
		// *****************lecture du flux xml**************
		Document document = constructeur.parse(myURL.openStream());
		Element racine = document.getDocumentElement();
		NodeList liste = racine.getElementsByTagName(Element);
		// remplissage de mon tableau
		for (int i = 0; i < liste.getLength(); i++) {
			ArrayList<String> aTableauTmp = new ArrayList<String>();
			Element E1 = (Element) liste.item(i);
			// aTableRetour[i]= "";
			aTableauTmp.add(E1.getAttribute("SCRIPT"));
			// aTableauTmp.add(E1.getAttribute("ordre"));
			aTableRetour.add(aTableauTmp);
		}

		return aTableRetour;

	}

	/**
	 * @param Element
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<ArrayList> renvoi_liste_script_maj_xml(String Element) throws Exception {

		// ***********************création de notre tableau dinamique
		ArrayList<ArrayList> aTableRetour = new ArrayList<ArrayList>();

		// ************************récupération du flux wml
		URL myURL = new URL("http://simon.mardine.free.fr/trousse_maquillage/maj_bdd.xml");
		DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();
		// création d'un constructeur de documents
		DocumentBuilder constructeur = fabrique.newDocumentBuilder();
		// *****************lecture du flux xml**************
		// Document document = constructeur.parse(myURL.openStream());
		// String cheminXml = "./data/data/"+PackageName;
		// File chemin = new File (cheminXml);
		Document document = constructeur.parse(myURL.openStream());
		Element racine = document.getDocumentElement();
		NodeList liste = racine.getElementsByTagName(Element);
		// remplissage de mon tableau
		for (int i = 0; i < liste.getLength(); i++) {
			ArrayList<String> aTableauTmp = new ArrayList<String>();
			Element E1 = (Element) liste.item(i);
			// aTableRetour[i]= "";
			aTableauTmp.add(E1.getAttribute("SCRIPT"));
			// aTableauTmp.add(E1.getAttribute("ordre"));
			aTableRetour.add(aTableauTmp);
		}

		return aTableRetour;

	}

	/**
	 * @return l'instance de connexion a la database
	 */
	public SQLiteDatabase getMdb() {
		return mDb;
	}

	// **************************fin de la classe**********************************
}
