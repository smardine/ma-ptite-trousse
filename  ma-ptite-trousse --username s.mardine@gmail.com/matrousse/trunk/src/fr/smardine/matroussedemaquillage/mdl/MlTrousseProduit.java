package fr.smardine.matroussedemaquillage.mdl;

import java.util.ArrayList;

import android.content.Context;
import fr.smardine.matroussedemaquillage.base.accesTable.AccesTableTrousseProduits;
import fr.smardine.matroussedemaquillage.mdl.cat.EnCategorie;
import fr.smardine.matroussedemaquillage.mdl.cat.EnTypeCategorie;

/**
 * @author smardine
 */
public class MlTrousseProduit {

	private final int idTrousseproduit;
	private final Context ctx;
	private final EnCategorie nomSousCat;
	private final EnTypeCategorie nomCat;
	private final boolean isChecked;

	/**
	 * @param p_idTrousseProduit
	 * @param p_ctx
	 */
	public MlTrousseProduit(int p_idTrousseProduit, Context p_ctx) {
		this.idTrousseproduit = p_idTrousseProduit;
		this.ctx = p_ctx;
		AccesTableTrousseProduits accesProduit = new AccesTableTrousseProduits(
				ctx);
		ArrayList<String> defTrousseProduit = accesProduit
				.getTrousseProduitById(idTrousseproduit);
		this.nomSousCat = MlProduit.rechercheSousCat(defTrousseProduit.get(0));

		this.nomCat = EnTypeCategorie
				.getEnumFromValue(defTrousseProduit.get(1));

		// this.nomSousCat = defTrousseProduit.get(0);
		// this.nomCat = defTrousseProduit.get(1);
		this.isChecked = Boolean.parseBoolean(defTrousseProduit.get(2));

	}

	/**
	 * @return the nomSousCat
	 */
	public EnCategorie getNomSousCat() {
		return nomSousCat;
	}

	/**
	 * @return the nomCat
	 */
	public EnTypeCategorie getNomCat() {
		return nomCat;
	}

	/**
	 * @return the isChecked
	 */
	public boolean isChecked() {
		return isChecked;
	}

}
