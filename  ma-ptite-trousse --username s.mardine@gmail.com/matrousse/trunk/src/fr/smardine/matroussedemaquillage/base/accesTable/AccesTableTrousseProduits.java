package fr.smardine.matroussedemaquillage.base.accesTable;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import fr.smardine.matroussedemaquillage.base.RequeteFactory;
import fr.smardine.matroussedemaquillage.base.structure.EnStructProduits;
import fr.smardine.matroussedemaquillage.base.structure.EnTable;
import fr.smardine.matroussedemaquillage.mdl.MlListeTrousseProduit;
import fr.smardine.matroussedemaquillage.mdl.MlTrousseProduit;
import fr.smardine.matroussedemaquillage.mdl.cat.EnCategorie;

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
	public MlListeTrousseProduit getListeTrousseProduit(String Catégorie) {
		MlListeTrousseProduit lstRetour = new MlListeTrousseProduit();
		String requete = "SELECT " + EnStructProduits.ID.getNomChamp()
				+ " FROM " + EnTable.TROUSSE_PRODUIT.getNomTable() + " WHERE "
				+ EnStructProduits.NOM_CAT.getNomChamp() + "='" + Catégorie
				+ "' ORDER BY " + EnStructProduits.NOM_SOUSCAT.getNomChamp();

		List<ArrayList<String>> lstId = requeteFact.getListeDeChamp(requete);
		for (ArrayList<String> anId : lstId) {
			MlTrousseProduit p = new MlTrousseProduit(Integer.parseInt(anId
					.get(0)), ctx);
			lstRetour.add(p);
		}
		return lstRetour;

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
	 * @param p_categorieChoisie
	 */
	public void majSouscatChoisie(EnCategorie p_categorieChoisie) {
		reinitProduitChoisi();
		ContentValues modifiedValues = new ContentValues();
		modifiedValues.put("ischecked", "true");
		String whereClause = "nom_souscatergorie=?";
		String[] whereArgs = new String[] { p_categorieChoisie.getLib() };
		requeteFact.majTable(EnTable.TROUSSE_PRODUIT, modifiedValues,
				whereClause, whereArgs);
	}

	/**
	 * @return la liste des TrousseProduit coché
	 */
	public MlListeTrousseProduit getListeProduitCochee() {
		MlListeTrousseProduit lstRetour = new MlListeTrousseProduit();
		String requete = "SELECT " + EnStructProduits.ID.getNomChamp()
				+ " FROM " + EnTable.TROUSSE_PRODUIT.getNomTable() + " WHERE "
				+ EnStructProduits.ISCHECKED.getNomChamp() + "='true'";

		List<ArrayList<String>> lstId = requeteFact.getListeDeChamp(requete);
		for (ArrayList<String> anId : lstId) {
			MlTrousseProduit p = new MlTrousseProduit(Integer.parseInt(anId
					.get(0)), ctx);
			lstRetour.add(p);
		}
		return lstRetour;
	}

	/**
	 * @param p_idTrousseProduit
	 * @return la definition d'un TrousseProduit
	 */
	public ArrayList<String> getTrousseProduitById(int p_idTrousseProduit) {
		String requete = "SELECT " + EnStructProduits.NOM_SOUSCAT.getNomChamp()
				+ ", " + EnStructProduits.NOM_CAT.getNomChamp()
				+ ", "
				+ EnStructProduits.ISCHECKED.getNomChamp()//
				+ " FROM " + EnTable.TROUSSE_PRODUIT.getNomTable() + " WHERE "
				+ EnStructProduits.ID.getNomChamp() + "=" + p_idTrousseProduit;
		return requeteFact.getListeDeChamp(requete).get(0);
	}

	/**
	 * @return le nombre de produit coché.
	 */
	public int getNbProduitCochee() {
		return getListeProduitCochee().size();
	}
}
