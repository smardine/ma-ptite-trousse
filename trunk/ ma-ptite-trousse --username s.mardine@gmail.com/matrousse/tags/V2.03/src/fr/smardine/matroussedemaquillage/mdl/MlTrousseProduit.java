package fr.smardine.matroussedemaquillage.mdl;

import java.util.ArrayList;

import android.content.Context;
import fr.smardine.matroussedemaquillage.base.accesTable.AccesTableTrousseProduits;

/**
 * @author smardine
 */
public class MlTrousseProduit {

	private final int idTrousseproduit;
	private final Context ctx;
	private final String nomSousCat;
	private final String nomCat;
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
		this.nomSousCat = defTrousseProduit.get(0);
		this.nomCat = defTrousseProduit.get(1);
		this.isChecked = Boolean.parseBoolean(defTrousseProduit.get(2));

	}

	/**
	 * @return the nomSousCat
	 */
	public String getNomSousCat() {
		return nomSousCat;
	}

	/**
	 * @return the nomCat
	 */
	public String getNomCat() {
		return nomCat;
	}

	/**
	 * @return the isChecked
	 */
	public boolean isChecked() {
		return isChecked;
	}

}
