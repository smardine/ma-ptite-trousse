/**
 * 
 */
package fr.smardine.matroussedemaquillage.base.structure;

/**
 * @author smardine
 */
public enum EnProduitEnregistre implements StructureTable {
	/**
	 * id du produit
	 */
	ID("id_produits", Integer.class, null), //
	/**
	 * nom du produit
	 */
	NOM_PRODUIT("nom_produit", String.class, 255), //
	/**
	 * nom de la sous categorie du produit
	 */
	NOM_SOUSCAT("nom_souscatergorie", String.class, 250), //
	/**
	 * nom de la categorie du produit
	 */
	NOM_CAT("nom_categorie", String.class, 250), //
	/**
	 * numero de teinte
	 */
	NUM_TEINTE("numero_Teinte", String.class, 255), //
	/**
	 * date d'achat
	 */
	DATE_ACHAT("DateAchat", String.class, 255), //
	/**
	 * duree de vie du produit
	 */
	DUREE_VIE("Duree_Vie", String.class, 255), //
	/**
	 * date de peremption
	 */
	DATE_PEREMP("Date_Peremption", String.class, 255), //
	/**
	 * nom de la marque du produit
	 */
	NOM_MARQUE("nom_marque", String.class, 255), //
	/**
	 * date de premption en milliseconde
	 */
	DATE_PEREMP_MILLI("Date_Peremption_milli", Long.class, null), //
	/**
	 * est ce que le produit est perime
	 */
	IS_PERIME("IS_PERIME", String.class, 255), //
	/**
	 * le produit est il bientot perime
	 */
	IS_PRESQUE_PERIME("IS_PRESQUE_PERIME", String.class, 255), //
	/**
	 * le nombre de jour avant peremption
	 */
	NB_JOUR_AVANT_PEREMP("NB_JOUR_AVANT_PEREMP", String.class, 255);
	private final String nomChamp;
	private final Class<?> typeClass;
	private final Integer tailleMax;

	EnProduitEnregistre(String p_nomChamp, Class<?> p_typeClass, Integer p_tailleMax) {
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
