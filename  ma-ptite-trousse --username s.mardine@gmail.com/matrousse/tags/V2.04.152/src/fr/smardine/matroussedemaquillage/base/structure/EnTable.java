package fr.smardine.matroussedemaquillage.base.structure;

/**
 * @author smardine
 */
public enum EnTable {
	/**
	 * voir EnStructNotes pour la structure de la table
	 */
	NOTES("Notes", EnStructNotes.class), //
	/**
	 * voir EnStructParam pour la structure de la table
	 */
	PARAM("Param", EnStructParam.class), //
	/**
	 * voir EnStructProduitEnregistre pour la structure de la table
	 */
	PRODUIT_ENREGISTRE("produit_Enregistre", EnStructProduitEnregistre.class), //
	/**
	 * voir EnStructMarque pour la structure de la table
	 */
	TROUSSE_MARQUE("trousse_marques", EnStructMarque.class), //
	/**
	 * voir EnStructProduits pour la structure de la table
	 */
	TROUSSE_PRODUIT("trousse_produits", EnStructProduits.class), //
	/**
	 * voir EnStructTempo pour la structure de la table
	 */
	TROUSSE_TEMPO("trousse_tempo", EnStructTempo.class);

	private String nomTable;
	private Class<? extends IStructureTable> structureTable;

	EnTable(String p_nomTable, Class<? extends IStructureTable> p_structure) {
		this.nomTable = p_nomTable;
		this.structureTable = p_structure;
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

	public Class<? extends IStructureTable> getStructureTable() {
		return structureTable;
	}

}
