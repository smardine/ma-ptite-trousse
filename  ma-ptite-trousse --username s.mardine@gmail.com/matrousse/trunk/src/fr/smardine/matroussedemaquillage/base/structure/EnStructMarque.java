package fr.smardine.matroussedemaquillage.base.structure;

/**
 * @author smardine Table contenant les marque de produits
 */
public enum EnStructMarque implements StructureTable {
	/**
	 * l'id de la marque
	 */
	ID("id_marque", EnTypeChampsSQLite.INTEGER, null), //
	/**
	 * le nom de la marque
	 */
	NOM("nom_marque", EnTypeChampsSQLite.VARCHAR, 250), //
	/**
	 * est elle cochee?
	 */
	ISCHECKED("ischecked", EnTypeChampsSQLite.VARCHAR, 250);

	private final String nomChamp;
	private final EnTypeChampsSQLite typeClass;
	private final Integer tailleMax;

	EnStructMarque(String p_nomChamp, EnTypeChampsSQLite p_typeClass,
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
	public EnStructMarque[] getListeChamp() {
		return EnStructMarque.values();
	}

}
