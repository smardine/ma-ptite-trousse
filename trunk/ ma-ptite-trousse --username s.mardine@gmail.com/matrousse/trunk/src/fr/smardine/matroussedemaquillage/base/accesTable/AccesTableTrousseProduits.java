package fr.smardine.matroussedemaquillage.base.accesTable;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import fr.smardine.matroussedemaquillage.base.RequeteFactory;
import fr.smardine.matroussedemaquillage.base.structure.EnStructProduits;
import fr.smardine.matroussedemaquillage.base.structure.EnTable;

/**
 * @author smardine Acces a la table "Trousse produits" Contient les categories
 *         Visage, Yeux, Levres et Autres, elle meme divis�e en sous categorie:
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
 *         <li>Crayons contour, Rouges � l�vres</li>
 *         </ul>
 *         <li>Autres</li>
 *         <ul>
 *         <li>Vernis � ongle, Pinceaux</li>
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
	 * @param Cat�gorie
	 * @return un tableau de liste de String
	 */
	public ArrayList<String> renvoi_liste_produits(String Cat�gorie) {
		String requete = "SELECT " + EnStructProduits.NOM_SOUSCAT.getNomChamp()
				+ ", " + EnStructProduits.ISCHECKED.getNomChamp() + " FROM "
				+ EnTable.TROUSSE_PRODUIT.getNomTable() + " WHERE "
				+ EnStructProduits.NOM_CAT.getNomChamp() + "='" + Cat�gorie
				+ "' ORDER BY " + EnStructProduits.NOM_SOUSCAT.getNomChamp();

		return requeteFact.getListeDeChamp(requete).get(0);

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

	public ArrayList<String> getListeProduitCochee() {
		String requete = "SELECT " + EnStructProduits.NOM_SOUSCAT.getNomChamp()
				+ ", " + EnStructProduits.ISCHECKED.getNomChamp() + " FROM "
				+ EnTable.TROUSSE_PRODUIT.getNomTable() + " WHERE "
				+ EnStructProduits.ISCHECKED.getNomChamp() + "='true'";

		return requeteFact.getListeDeChamp(requete).get(0);
	}

	public int getNbProduitCochee() {
		return getListeProduitCochee().size();
	}
}
