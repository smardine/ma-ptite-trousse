package fr.smardine.matroussedemaquillage.base.structure;

/**
 * @author smardine Structure de la table Note
 */
public enum EnStructNotes implements StructureTable {

	/**
	 * l'id de la note
	 */
	ID("id_note", Integer.class, null), //
	/**
	 * le titre de la note
	 */
	TITRE("Titre", String.class, 255), //
	/**
	 * le contenu de la note
	 */
	MESSAGE("Message", String.class, 9999);

	private final String nomChamp;
	private final Class<?> typeClass;
	private final Integer tailleMax;

	EnStructNotes(String p_nomChamp, Class<?> p_typeClass, Integer p_tailleMax) {
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
