package fr.smardine.matroussedemaquillage.base.accesTable;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import fr.smardine.matroussedemaquillage.base.RequeteFactory;
import fr.smardine.matroussedemaquillage.base.structure.EnStructProduits;
import fr.smardine.matroussedemaquillage.base.structure.EnTable;

/**
 * @author smardine Acces a la table "Trousse produits" Contient les categories
 *         Visage, Yeux, Levres et Autres, elle meme divisée en sous categorie:
 *         <ul>
 *         <li>Visage:</li>
 *         <ul>
 *         <li>Fonds de teint,Correcteur-Bases,Blush,Poudres</li>
 *         </ul>
 *         <li>Yeux</li>
 *         <ul>
 *         <li>Crayons-Eyeliners,Bases,Fards et Mascara</li>
 *         </ul>
 *         <li>Levres</li>
 *         <ul>
 *         <li>Crayons contour, Rouges à lèvres</li>
 *         </ul>
 *         <li>Autres</li>
 *         <ul>
 *         <li>Vernis à ongle, Pinceaux</li>
 *         </ul>
 */
public class AccesTableTrousseProduits {

	private final Context ctx;
	private final RequeteFactory requeteFact;

	/**
	 * @param p_ctx le contexte
	 */
	public AccesTableTrousseProduits(Context p_ctx) {
		this.ctx = p_ctx;
		requeteFact = new RequeteFactory(p_ctx);
	}

	/**
	 * @param Catégorie
	 * @return un tableau de liste de String
	 */
	public ArrayList<String>[] renvoi_liste_produits(String Catégorie) {
		String requete = "SELECT " + EnStructProduits.NOM_SOUSCAT.getNomChamp()
				+ ", " + EnStructProduits.ISCHECKED.getNomChamp() + " FROM "
				+ EnTable.TROUSSE_PRODUIT.getNomTable() + " WHERE "
				+ EnStructProduits.NOM_CAT.getNomChamp() + "='" + Catégorie
				+ "' ORDER BY " + EnStructProduits.NOM_SOUSCAT.getNomChamp();

		List<ArrayList<String>> retour = requeteFact.getListeDeChamp(requete);

		// String[] colonne = new String[] { "nom_souscatergorie", "ischecked"
		// };
		// String condition = "nom_categorie='" + Catégorie + "'";
		// String[] conditionArgs = null;
		// String groupby = "";
		// String having = "";
		// String orderby = "nom_souscatergorie";
		//
		// Cursor objCursor = mdb.query(EnTable.TROUSSE_PRODUIT.getNomTable(),
		// colonne, condition, conditionArgs, groupby, having, orderby);
		// int idxNomSousCat = objCursor.getColumnIndex("nom_souscatergorie");
		// int idxIsChecked = objCursor.getColumnIndex("ischecked");
		//
		// ArrayList<String> aTableRetourNom = new ArrayList<String>();
		// ArrayList<String> aTableRetourisChecked = new ArrayList<String>();
		//
		// objCursor.moveToFirst();
		// @SuppressWarnings("unchecked")
		ArrayList<String>[] aTableRetour = new ArrayList[25];

		/* Check if our result was valid. */
		// if (objCursor != null) {
		// for (int i = 0; i < objCursor.getCount(); i++) {
		// String resultnom_produits = objCursor.getString(idxNomSousCat);
		// String resultischecked = objCursor.getString(idxIsChecked);
		// aTableRetourNom.add(resultnom_produits);
		// aTableRetourisChecked.add(resultischecked);
		// objCursor.moveToNext();
		// }
		// }
		// objCursor.close();
		// aTableRetour[0] = aTableRetourNom;
		// aTableRetour[1] = aTableRetourisChecked;
		return aTableRetour;
	}

	/**
	 * 
	 */
	public void reinitProduitChoisi() {
		ContentValues modifiedValues = new ContentValues();
		modifiedValues.put("ischecked", "false");
		String whereClause = "ischecked=?";
		String[] whereArgs = new String[] { "true" };
		requeteFact.majTable(EnTable.TROUSSE_PRODUIT, modifiedValues,
				whereClause, whereArgs);
	}

	/**
	 * @param p_sousCat
	 */
	public void majSouscatChoisie(String p_sousCat) {
		ContentValues modifiedValues = new ContentValues();
		modifiedValues.put("ischecked", "true");
		String whereClause = "nom_souscatergorie=?";
		String[] whereArgs = new String[] { p_sousCat };
		requeteFact.majTable(EnTable.TROUSSE_PRODUIT, modifiedValues,
				whereClause, whereArgs);
	}
}
