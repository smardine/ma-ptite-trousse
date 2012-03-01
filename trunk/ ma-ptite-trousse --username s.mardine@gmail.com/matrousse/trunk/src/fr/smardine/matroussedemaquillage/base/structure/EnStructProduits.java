package fr.smardine.matroussedemaquillage.base.structure;

/**
 * @author smardine
 */
public enum EnStructProduits implements StructureTable {
	/**
	 * l'id du produit
	 */
	ID("id_produits", EnTypeChampsSQLite.INTEGER, null), //
	/**
	 * le nom de sous categorie
	 */
	NOM_SOUSCAT("nom_souscatergorie", EnTypeChampsSQLite.VARCHAR, 250), //
	/**
	 * le nom de categorie
	 */
	NOM_CAT("nom_categorie", EnTypeChampsSQLite.VARCHAR, 250), //
	/**
	 * le produit est il coche
	 */
	ISCHECKED("ischecked", EnTypeChampsSQLite.VARCHAR, 250);

	private final String nomChamp;
	private final EnTypeChampsSQLite typeClass;
	private final Integer tailleMax;

	EnStructProduits(String p_nomChamp, EnTypeChampsSQLite p_typeClass,
			Integer p_tailleMax) {
		this.nomChamp = p_nomChamp;
		this.typeClass = p_typeClass;
		this.tailleMax = p_tailleMax;
	}

	@Override
	public String getNomChamp() {
		return nomChamp;
	}

	@Override
	public EnTypeChampsSQLite getTypeChamp() {
		return typeClass;
	}

	@Override
	public Integer getTailleMax() {
		return tailleMax;
	}

	@Override
	public StructureTable[] getListeChamp() {
		return values();
	}

}
