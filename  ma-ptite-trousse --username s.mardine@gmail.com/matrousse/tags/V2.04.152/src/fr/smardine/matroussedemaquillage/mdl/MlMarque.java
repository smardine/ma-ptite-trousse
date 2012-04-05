package fr.smardine.matroussedemaquillage.mdl;

/**
 * classe gerant la marque d'un produit
 * @author smardine
 */
public class MlMarque {

	private int idMarque;
	private String nomMarque;
	private boolean isChecked;

	public MlMarque() {

	}

	/**
	 * @return le nom de la marque
	 */
	public String getNomMarque() {
		return nomMarque;
	}

	public int getIdMarque() {
		return idMarque;
	}

	public void setIdMarque(int p_idMarque) {
		this.idMarque = p_idMarque;
	}

	public void setNomMarque(String p_nomMarque) {
		this.nomMarque = p_nomMarque;
	}

	/**
	 * @param isChecked the isChecked to set
	 */
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	/**
	 * @return the isChecked
	 */
	public boolean isChecked() {
		return isChecked;
	}
}
