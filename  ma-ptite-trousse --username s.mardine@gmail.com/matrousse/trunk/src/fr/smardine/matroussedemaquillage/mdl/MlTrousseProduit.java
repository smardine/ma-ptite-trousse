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

	private int idTrousseproduit;
	private Context ctx;
	private EnCategorie nomSousCat;
	private EnTypeCategorie nomCat;
	private boolean isChecked;

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

		this.isChecked = Boolean.parseBoolean(defTrousseProduit.get(2));

	}

	public MlTrousseProduit() {
		// TODO Auto-generated constructor stub
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

	public int getIdTrousseproduit() {
		return idTrousseproduit;
	}

	public void setIdTrousseproduit(int p_idTrousseproduit) {
		idTrousseproduit = p_idTrousseproduit;
	}

	public void setNomSousCat(EnCategorie p_nomSousCat) {
		nomSousCat = p_nomSousCat;
	}

	public void setNomCat(EnTypeCategorie p_nomCat) {
		nomCat = p_nomCat;
	}

	public void setChecked(boolean p_isChecked) {
		isChecked = p_isChecked;
	}

}
