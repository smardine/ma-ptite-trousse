package fr.smardine.matroussedemaquillage.mdl;

/**
 * @author smardine
 */
public class MlTeinte {

	private final String teinte;

	/**
	 * Teinte d'un produit (peut etre chiffre et/ou lettre)
	 * @param p_teinte
	 */
	public MlTeinte(String p_teinte) {
		this.teinte = p_teinte;
	}

	/**
	 * @return la teinte
	 */
	public String getTeinte() {
		return teinte;
	}

}
