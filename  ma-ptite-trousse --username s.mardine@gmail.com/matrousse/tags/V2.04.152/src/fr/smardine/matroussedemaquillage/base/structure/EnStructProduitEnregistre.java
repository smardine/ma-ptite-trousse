/**
 * 
 */
package fr.smardine.matroussedemaquillage.base.structure;

/**
 * @author smardine
 */
public enum EnStructProduitEnregistre implements IStructureTable {
	/**
	 * id du produit
	 */
	ID("id_produits", EnTypeChampsSQLite.INTEGER, null), //
	/**
	 * nom du produit
	 */
	NOM_PRODUIT("nom_produit", EnTypeChampsSQLite.VARCHAR, 255), //
	/**
	 * nom de la sous categorie du produit
	 */
	NOM_SOUSCAT("nom_souscatergorie", EnTypeChampsSQLite.VARCHAR, 250), //
	/**
	 * nom de la categorie du produit
	 */
	NOM_CAT("nom_categorie", EnTypeChampsSQLite.VARCHAR, 250), //
	/**
	 * numero de teinte
	 */
	NUM_TEINTE("numero_Teinte", EnTypeChampsSQLite.VARCHAR, 255), //
	/**
	 * date d'achat
	 */
	DATE_ACHAT("DateAchat", EnTypeChampsSQLite.VARCHAR, 255), //
	/**
	 * duree de vie du produit
	 */
	DUREE_VIE("Duree_Vie", EnTypeChampsSQLite.VARCHAR, 255), //
	/**
	 * date de peremption
	 */
	DATE_PEREMP("Date_Peremption", EnTypeChampsSQLite.VARCHAR, 255), //
	/**
	 * nom de la marque du produit
	 */
	NOM_MARQUE("nom_marque", EnTypeChampsSQLite.VARCHAR, 255), //
	/**
	 * date de premption en milliseconde
	 */
	DATE_PEREMP_MILLI("Date_Peremption_milli", EnTypeChampsSQLite.LONG, null), //
	/**
	 * est ce que le produit est perime
	 */
	IS_PERIME("IS_PERIME", EnTypeChampsSQLite.VARCHAR, 255), //
	/**
	 * le produit est il bientot perime
	 */
	IS_PRESQUE_PERIME("IS_PRESQUE_PERIME", EnTypeChampsSQLite.VARCHAR, 255), //
	/**
	 * le nombre de jour avant peremption
	 */
	NB_JOUR_AVANT_PEREMP("NB_JOUR_AVANT_PEREMP", EnTypeChampsSQLite.VARCHAR,
			255);
	private final String nomChamp;
	private final EnTypeChampsSQLite typeClass;
	private final Integer tailleMax;

	EnStructProduitEnregistre(String p_nomChamp,
			EnTypeChampsSQLite p_typeClass, Integer p_tailleMax) {
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
