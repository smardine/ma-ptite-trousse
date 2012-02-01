package fr.smardine.matroussedemaquillage.base;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author smardine
 */
public class BDAcces {

	private static final String DATABASE_NAME = "trousse_base";

	private final Context mCtx;

	/**
	 * 
	 */
	private DatabaseHelper mDbHelper;
	/**
	 * 
	 */
	private SQLiteDatabase mDb;

	/**
	 * 
	 */

	private static final String TAG = "BDAcces";
	private static final int DATABASE_VERSION = 14;

	/**
	 * @param ctx
	 */
	protected BDAcces(Context ctx) {
		this.mCtx = ctx;
	}

	private class DatabaseHelper extends SQLiteOpenHelper {

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
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
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

		private void LanceMiseAJour(SQLiteDatabase p_db,
				List<String> p_lstScripts) {
			for (String s : p_lstScripts) {
				p_db.execSQL(s);
			}
		}
	}

	/**
	 * @throws SQLException
	 */
	protected void open() {
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
		if (mDb == null) {
			open();
		}
		return mDb.getPath();
	}

	/**
	 * ferme la connexion a la database
	 * @throws SQLException
	 */
	protected void close() {
		// si il reste des transaction active, on les ferme.
		if (mDb.inTransaction()) {
			mDb.endTransaction();
		}
		// on commence par fermer la base
		mDb.close();
		// on ferme le helper.
		mDbHelper.close();
	}

	/**
	 * 
	 */
	public void update_bdd() {
		int newversion = mDb.getVersion() + 1;
		mDbHelper.onUpgrade(mDb, mDb.getVersion(), newversion);

	}

	/**
	 * @return
	 */

	// public ArrayList<String>[] renvoiCategorieCochée() {
	// String[] colonne = new String[] { "nom_souscatergorie" };
	// String condition = "ischecked='true'";
	// String[] conditionArgs = null;
	// String groupby = "";
	// String having = "";
	// String orderby = "";
	// Cursor objCursor = mDb.query(PRODUITS_TABLE, colonne, condition,
	// conditionArgs, groupby, having, orderby);
	// int iPostNomProduits = objCursor.getColumnIndex("nom_souscatergorie");
	//
	// int itotal = objCursor.getCount();
	// ArrayList<String>[] aTableRetour = new ArrayList[itotal + 1];
	// ArrayList<String> aTableRetourNom = new ArrayList<String>();
	// if (itotal != 0) {
	//
	// objCursor.moveToFirst();
	// /* Check if our result was valid. */
	// if (objCursor != null) {
	// for (int i = 0; i < itotal; i++) {
	// String resultnom_produits = objCursor
	// .getString(iPostNomProduits);
	// aTableRetourNom.add(resultnom_produits);
	// objCursor.moveToNext();
	// }
	// }
	// objCursor.close();
	// aTableRetour[0] = aTableRetourNom;
	// } else {
	// aTableRetourNom.add("aucun");
	// aTableRetour[0] = aTableRetourNom;
	// }
	// // aTableRetour[1]=aTableRetourisChecked;
	// return aTableRetour;
	//
	// }

	// /**
	// * @param Table
	// * @param values
	// * @return true ou false
	// */
	// public boolean InsertDonnéedansTable(String Table, ContentValues values)
	// {
	//
	// long RowNumber = mDb.insert(Table, null, values);
	// if (RowNumber == -1) {
	// return false;
	// } else {
	// return true;
	// }
	// }

	// /**
	// * @param table
	// * @param whereClause
	// * @param whereArgs
	// * @return le nombre de champ effacé
	// */
	// public int deleteTable(String table, String whereClause, String[]
	// whereArgs) {
	// if (mDb == null) {
	// open();
	// }
	// int RowNumber = mDb.delete(table, whereClause, whereArgs);
	//
	// return RowNumber;
	//
	// }

	// /**
	// * @param Table
	// * @param Colonnes
	// * @return
	// */
	//
	// public ArrayList[] renvoi_liste_ValeurTroussetempo(String Table,
	// String[] Colonnes) {
	// String[] colonne = Colonnes;
	// // String condition = "nom_categorie='"+Catégorie+"'";
	// String condition = "";
	// String[] conditionArgs = null;
	// String groupby = "";
	// String having = "";
	// String orderby = "";
	// Cursor objCursor = mDb.query(Table, colonne, condition, conditionArgs,
	// groupby, having, orderby);
	// int iPostNomMarques = objCursor.getColumnIndex(Colonnes[0]);
	// int iPostNomProduit = objCursor.getColumnIndex(Colonnes[1]);
	// int iPostNumeroTeinte = objCursor.getColumnIndex(Colonnes[2]);
	// int iPostDateAchat = objCursor.getColumnIndex(Colonnes[3]);
	// int iPostDureeVie = objCursor.getColumnIndex(Colonnes[4]);
	//
	// int itotal = objCursor.getCount();
	// ArrayList<String> aTableRetourNomMarque = new ArrayList<String>();
	// ArrayList<String> aTableRetourNomProduit = new ArrayList<String>();
	// ArrayList<String> aTableRetourNumeroTeinte = new ArrayList<String>();
	// ArrayList<String> aTableRetourDateAchat = new ArrayList<String>();
	// ArrayList<String> aTableRetourDureeVie = new ArrayList<String>();
	//
	// objCursor.moveToFirst();
	// ArrayList<?> aTableRetour[] = new ArrayList[5];
	//
	// /* Check if our result was valid. */
	// if (objCursor != null) {
	// for (int i = 0; i < itotal; i++) {
	// String Nom_marque = objCursor.getString(iPostNomMarques);
	// String Nom_Produit = objCursor.getString(iPostNomProduit);
	// String Num_Teinte = objCursor.getString(iPostNumeroTeinte);
	// String DateAchat = objCursor.getString(iPostDateAchat);
	// String DureeVie = objCursor.getString(iPostDureeVie);
	//
	// aTableRetourNomMarque.add(Nom_marque);
	// aTableRetourNomProduit.add(Nom_Produit);
	// aTableRetourNumeroTeinte.add(Num_Teinte);
	// aTableRetourDateAchat.add(DateAchat);
	// aTableRetourDureeVie.add(DureeVie);
	//
	// objCursor.moveToNext();
	// }
	// }
	// objCursor.close();
	// aTableRetour[0] = aTableRetourNomMarque;
	// aTableRetour[1] = aTableRetourNomProduit;
	// aTableRetour[2] = aTableRetourNumeroTeinte;
	// aTableRetour[3] = aTableRetourDateAchat;
	// aTableRetour[4] = aTableRetourDureeVie;
	//
	// return aTableRetour;
	// }

	// public ArrayList[] renvoiCategorieEtProduitCochée() {
	// String[] colonne = new String[] { "nom_souscatergorie", "nom_categorie"
	// };
	// String condition = "ischecked='true'";
	// String[] conditionArgs = null;
	// String groupby = "";
	// String having = "";
	// String orderby = "";
	// Cursor objCursor = mDb.query(PRODUITS_TABLE, colonne, condition,
	// conditionArgs, groupby, having, orderby);
	// int iPostNomProduits = objCursor.getColumnIndex("nom_souscatergorie");
	// int iPostNomCatégories = objCursor.getColumnIndex("nom_categorie");
	//
	// int itotal = objCursor.getCount();
	// ArrayList[] aTableRetour = new ArrayList[2];
	// ArrayList<String> aTableRetourNom = new ArrayList<String>();
	// ArrayList<String> aTableRetourCatégorie = new ArrayList<String>();
	// if (itotal != 0) {
	//
	// objCursor.moveToFirst();
	// /* Check if our result was valid. */
	// if (objCursor != null) {
	// for (int i = 0; i < itotal; i++) {
	// String NomProduits = objCursor.getString(iPostNomProduits);
	// String NomCategorie = objCursor
	// .getString(iPostNomCatégories);
	//
	// aTableRetourNom.add(NomProduits);
	// aTableRetourCatégorie.add(NomCategorie);
	// objCursor.moveToNext();
	// }
	// }
	// objCursor.close();
	// aTableRetour[0] = aTableRetourNom;
	// aTableRetour[1] = aTableRetourCatégorie;
	// } else {
	// aTableRetourNom.add("aucun");
	// aTableRetour[0] = aTableRetourNom;
	// }
	// // aTableRetour[1]=aTableRetourisChecked;
	// return aTableRetour;
	// }

	/**
	 * @param colonne
	 * @return
	 */

	// public ArrayList<String>[] renvoi_param(String[] colonne) {
	// // open();
	//
	// // String[] colonne= new
	// // String[]{"nom_produit","DateAchat","Date_Peremption"};
	// // String condition = "id_produits='"+id+"'";
	// String condition = "";
	// String[] conditionArgs = null;
	//
	// String having = "";
	//
	// Cursor objCursor = mDb.query("Param", colonne, condition,
	// conditionArgs, "", having, "");
	// int iPostAfficheAlerte = objCursor.getColumnIndex(colonne[0]);
	// int iPostDureeViePeremp = objCursor.getColumnIndex(colonne[1]);
	// int PostTheme = objCursor.getColumnIndex(colonne[2]);
	//
	// int itotal = objCursor.getCount();
	//
	// ArrayList<String> aTableRetourAfficheAlerte = new ArrayList<String>();
	// ArrayList<String> aTableRetourDureeViePeremp = new ArrayList<String>();
	// ArrayList<String> aTableRetourTheme = new ArrayList<String>();
	//
	// objCursor.moveToFirst();
	//
	// ArrayList[] aTableRetour = new ArrayList[objCursor.getColumnCount() + 1];
	//
	// /* Check if our result was valid. */
	// if (objCursor != null) {
	// for (int i = 0; i < itotal; i++) {
	// String resultDureeViePeremp = "", resultAfficheAlerte = "", resultTheme =
	// "";
	// resultAfficheAlerte = objCursor.getString(iPostAfficheAlerte);
	// resultDureeViePeremp = objCursor.getString(iPostDureeViePeremp);
	// resultTheme = objCursor.getString(PostTheme);
	//
	// if (!resultAfficheAlerte.equals(null)) {
	// aTableRetourAfficheAlerte.add(resultAfficheAlerte);
	// } else {
	// aTableRetourAfficheAlerte.add("vide");
	// }
	// if (!resultDureeViePeremp.equals(null)) {
	// aTableRetourDureeViePeremp.add(resultDureeViePeremp);
	// } else {
	// aTableRetourDureeViePeremp.add("vide");
	// }
	// if (!resultTheme.equals(null)) {
	// aTableRetourTheme.add(resultTheme);
	// } else {
	// aTableRetourTheme.add("vide");
	// }
	//
	// objCursor.moveToNext();
	// }
	// }
	// objCursor.close();
	// aTableRetour[0] = aTableRetourAfficheAlerte;
	// aTableRetour[1] = aTableRetourDureeViePeremp;
	// aTableRetour[2] = aTableRetourTheme;
	//
	// return aTableRetour;
	// }

	// /**
	// * @param ScriptSql
	// */
	// public void execSQL(String ScriptSql) {
	//
	// mDb.execSQL(ScriptSql);
	//
	// }

	// /**
	// * @param Element
	// * @return
	// * @throws Exception
	// */
	// public static ArrayList<ArrayList> renvoi_liste_script_xml(String
	// Element)
	// throws Exception {
	//
	// // ***********************création de notre tableau dinamique
	// ArrayList<ArrayList> aTableRetour = new ArrayList<ArrayList>();
	//
	// // ************************récupération du flux wml
	// URL myURL = new URL(
	// "http://simon.mardine.free.fr/trousse_maquillage/bdd.xml");
	// DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();
	// // création d'un constructeur de documents
	// DocumentBuilder constructeur = fabrique.newDocumentBuilder();
	// // *****************lecture du flux xml**************
	// Document document = constructeur.parse(myURL.openStream());
	// Element racine = document.getDocumentElement();
	// NodeList liste = racine.getElementsByTagName(Element);
	// // remplissage de mon tableau
	// for (int i = 0; i < liste.getLength(); i++) {
	// ArrayList<String> aTableauTmp = new ArrayList<String>();
	// Element E1 = (Element) liste.item(i);
	// // aTableRetour[i]= "";
	// aTableauTmp.add(E1.getAttribute("SCRIPT"));
	// // aTableauTmp.add(E1.getAttribute("ordre"));
	// aTableRetour.add(aTableauTmp);
	// }
	//
	// return aTableRetour;
	//
	// }

	// /**
	// * @param Element
	// * @return
	// * @throws Exception
	// */
	// public static ArrayList<ArrayList> renvoi_liste_script_maj_xml(
	// String Element) throws Exception {
	//
	// // ***********************création de notre tableau dinamique
	// ArrayList<ArrayList> aTableRetour = new ArrayList<ArrayList>();
	//
	// // ************************récupération du flux wml
	// URL myURL = new URL(
	// "http://simon.mardine.free.fr/trousse_maquillage/maj_bdd.xml");
	// DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();
	// // création d'un constructeur de documents
	// DocumentBuilder constructeur = fabrique.newDocumentBuilder();
	// // *****************lecture du flux xml**************
	// // Document document = constructeur.parse(myURL.openStream());
	// // String cheminXml = "./data/data/"+PackageName;
	// // File chemin = new File (cheminXml);
	// Document document = constructeur.parse(myURL.openStream());
	// Element racine = document.getDocumentElement();
	// NodeList liste = racine.getElementsByTagName(Element);
	// // remplissage de mon tableau
	// for (int i = 0; i < liste.getLength(); i++) {
	// ArrayList<String> aTableauTmp = new ArrayList<String>();
	// Element E1 = (Element) liste.item(i);
	// // aTableRetour[i]= "";
	// aTableauTmp.add(E1.getAttribute("SCRIPT"));
	// // aTableauTmp.add(E1.getAttribute("ordre"));
	// aTableRetour.add(aTableauTmp);
	// }
	//
	// return aTableRetour;
	//
	// }

	/**
	 * @return l'instance de connexion a la database
	 */
	protected SQLiteDatabase getMdb() {
		return mDb;
	}

	// **************************fin de la
	// classe**********************************
}
