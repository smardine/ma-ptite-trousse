package fr.smardine.matroussedemaquillage.base.structure;

/**
 * @author smardine
 */
public enum EnTable {
	/**
	 * voir EnStructNotes pour la structure de la table
	 */
	NOTES("Notes"), //
	/**
	 * voir EnStructParam pour la structure de la table
	 */
	PARAM("Param"), //
	/**
	 * voir EnStructProduitEnregistre pour la structure de la table
	 */
	PRODUIT_ENREGISTRE("produit_Enregistre"), //
	/**
	 * voir EnStructMarque pour la structure de la table
	 */
	TROUSSE_MARQUE("trousse_marques"), //
	/**
	 * voir EnStructProduits pour la structure de la table
	 */
	TROUSSE_PRODUIT("trousse_produits"), //
	/**
	 * voir EnStructTempo pour la structure de la table
	 */
	TROUSSE_TEMPO("trousse_tempo");

	private String nomTable;

	EnTable(String p_nomTable) {
		this.nomTable = p_nomTable;
	}

	/**
	 * @return the nomTable
	 */
	public String getNomTable() {
		return nomTable;
	}

	@Override
	public String toString() {
		return nomTable;
	}

}
