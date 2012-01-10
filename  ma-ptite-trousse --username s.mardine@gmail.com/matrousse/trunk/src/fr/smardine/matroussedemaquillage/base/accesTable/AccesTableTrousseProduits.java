package fr.smardine.matroussedemaquillage.base.accesTable;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import fr.smardine.matroussedemaquillage.base.BDAcces;
import fr.smardine.matroussedemaquillage.base.structure.EnTable;

/**
 * @author smardine Acces a la table "Trousse produits" Contient les categories Visage, Yeux, Levres et Autres, elle meme divisée en sous
 *         categorie:
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
	private final BDAcces bd;
	private final SQLiteDatabase mdb;

	/**
	 * @param p_ctx le contexte
	 */
	public AccesTableTrousseProduits(Context p_ctx) {
		this.ctx = p_ctx;
		bd = new BDAcces(ctx);
		mdb = bd.getMdb();
	}

	/**
	 * @param Catégorie
	 * @return un tableau de liste de String
	 */
	public ArrayList<String>[] renvoi_liste_produits(String Catégorie) {

		String[] colonne = new String[] { "nom_souscatergorie", "ischecked" };
		String condition = "nom_categorie='" + Catégorie + "'";
		String[] conditionArgs = null;
		String groupby = "";
		String having = "";
		String orderby = "nom_souscatergorie";

		Cursor objCursor = mdb.query(EnTable.TROUSSE_PRODUIT.getNomTable(), colonne, condition, conditionArgs, groupby, having, orderby);
		int idxNomSousCat = objCursor.getColumnIndex("nom_souscatergorie");
		int idxIsChecked = objCursor.getColumnIndex("ischecked");

		ArrayList<String> aTableRetourNom = new ArrayList<String>();
		ArrayList<String> aTableRetourisChecked = new ArrayList<String>();

		objCursor.moveToFirst();
		@SuppressWarnings("unchecked")
		ArrayList<String>[] aTableRetour = new ArrayList[25];

		/* Check if our result was valid. */
		if (objCursor != null) {
			for (int i = 0; i < objCursor.getCount(); i++) {
				String resultnom_produits = objCursor.getString(idxNomSousCat);
				String resultischecked = objCursor.getString(idxIsChecked);
				aTableRetourNom.add(resultnom_produits);
				aTableRetourisChecked.add(resultischecked);
				objCursor.moveToNext();
			}
		}
		objCursor.close();
		aTableRetour[0] = aTableRetourNom;
		aTableRetour[1] = aTableRetourisChecked;
		return aTableRetour;
	}

}
