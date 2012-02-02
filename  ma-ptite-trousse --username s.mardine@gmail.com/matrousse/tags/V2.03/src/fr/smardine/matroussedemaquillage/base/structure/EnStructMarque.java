package fr.smardine.matroussedemaquillage.base.structure;

/**
 * @author smardine Table contenant les marque de produits
 */
public enum EnStructMarque implements StructureTable {
	/**
	 * l'id de la marque
	 */
	ID("id_marque", Integer.class, null), //
	/**
	 * le nom de la marque
	 */
	NOM("nom_marque", String.class, 250), //
	/**
	 * est elle cochee?
	 */
	ISCHECKED("ischecked", String.class, 250);

	private final String nomChamp;
	private final Class<?> typeClass;
	private final Integer tailleMax;

	EnStructMarque(String p_nomChamp, Class<?> p_typeClass, Integer p_tailleMax) {
		this.nomChamp = p_nomChamp;
		this.typeClass = p_typeClass;
		this.tailleMax = p_tailleMax;
	}

	@Override
	public String getNomChamp() {
		return nomChamp;
	}

	@Override
	public Class<?> getTypeChamp() {
		return typeClass;
	}

	@Override
	public Integer getTailleMax() {
		return tailleMax;
	}

}
