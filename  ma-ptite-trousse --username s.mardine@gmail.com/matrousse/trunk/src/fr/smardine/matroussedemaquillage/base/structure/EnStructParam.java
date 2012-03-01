package fr.smardine.matroussedemaquillage.base.structure;

/**
 * @author smardine Structure de la table Param
 */
public enum EnStructParam implements StructureTable {
	/**
	 * Doit on afficher l'alerte de peremption au demarrage, prend "ture" ou
	 * "false"
	 */
	AFFICHE_ALERTE("AfficheAlerte", EnTypeChampsSQLite.VARCHAR, 255), //
	/**
	 * le nb de jour a partir duquel un produit est considere comme perime
	 */
	DUREE_VIE_PEREMP("DureeViePeremp", EnTypeChampsSQLite.INTEGER, null), //
	/**
	 * le nom du theme choisi
	 */
	THEME("Theme", EnTypeChampsSQLite.VARCHAR, 255);

	private final String nomChamp;
	private final EnTypeChampsSQLite typeClass;
	private final Integer tailleMax;

	EnStructParam(String p_nomChamp, EnTypeChampsSQLite p_typeClass,
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
