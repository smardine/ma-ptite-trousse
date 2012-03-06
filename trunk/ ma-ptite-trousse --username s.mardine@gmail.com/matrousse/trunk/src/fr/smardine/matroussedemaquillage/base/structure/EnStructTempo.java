package fr.smardine.matroussedemaquillage.base.structure;

/**
 * @author smardine
 */
public enum EnStructTempo implements IStructureTable {

	/**
	 * 
	 */
	NOM_MARQUE("nom_marque_choisie", EnTypeChampsSQLite.VARCHAR, 250), //
	/**
	 * 
	 */
	NOM_PRODUIT("nom_produit", EnTypeChampsSQLite.VARCHAR, 255), //
	/**
	 * 
	 */
	NUM_TEINTE("numero_Teinte", EnTypeChampsSQLite.VARCHAR, 255), //
	/**
	 * 
	 */
	DATE_ACHAT("DateAchat", EnTypeChampsSQLite.VARCHAR, 255), //
	/**
	 * 
	 */
	DUREE_VIE("Duree_Vie", EnTypeChampsSQLite.VARCHAR, 255);

	private final String nomChamp;
	private final EnTypeChampsSQLite typeClass;
	private final Integer tailleMax;

	EnStructTempo(String p_nomChamp, EnTypeChampsSQLite p_typeClass,
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
	public IStructureTable[] getListeChamp() {
		return values();
	}
}
