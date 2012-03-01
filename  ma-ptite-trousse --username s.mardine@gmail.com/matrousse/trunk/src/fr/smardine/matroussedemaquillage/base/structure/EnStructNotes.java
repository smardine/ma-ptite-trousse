package fr.smardine.matroussedemaquillage.base.structure;

/**
 * @author smardine Structure de la table Note
 */
public enum EnStructNotes implements StructureTable {

	/**
	 * l'id de la note
	 */
	ID("id_note", EnTypeChampsSQLite.INTEGER, null), //
	/**
	 * le titre de la note
	 */
	TITRE("Titre", EnTypeChampsSQLite.VARCHAR, 255), //
	/**
	 * le contenu de la note
	 */
	MESSAGE("Message", EnTypeChampsSQLite.VARCHAR, 9999);

	private final String nomChamp;
	private final EnTypeChampsSQLite typeClass;
	private final Integer tailleMax;

	EnStructNotes(String p_nomChamp, EnTypeChampsSQLite p_typeClass,
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
