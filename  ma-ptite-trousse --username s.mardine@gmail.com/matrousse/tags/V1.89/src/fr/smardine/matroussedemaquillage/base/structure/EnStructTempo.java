package fr.smardine.matroussedemaquillage.base.structure;

/**
 * @author smardine
 */
public enum EnStructTempo implements StructureTable {

	/**
	 * 
	 */
	NOM_MARQUE("nom_marque_choisie", String.class, 250), //
	/**
	 * 
	 */
	NOM_PRODUIT("nom_produit", String.class, 255), //
	/**
	 * 
	 */
	NUM_TEINTE("numero_Teinte", String.class, 255), //
	/**
	 * 
	 */
	DATE_ACHAT("DateAchat", String.class, 255), //
	/**
	 * 
	 */
	DUREE_VIE("Duree_Vie", String.class, 255);

	private final String nomChamp;
	private final Class<?> typeClass;
	private final Integer tailleMax;

	EnStructTempo(String p_nomChamp, Class<?> p_typeClass, Integer p_tailleMax) {
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
