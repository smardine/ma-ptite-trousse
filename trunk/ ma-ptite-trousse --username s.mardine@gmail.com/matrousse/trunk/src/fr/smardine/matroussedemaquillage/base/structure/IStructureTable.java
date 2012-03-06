package fr.smardine.matroussedemaquillage.base.structure;

/**
 * @author smardine
 */
public interface IStructureTable {

	/**
	 * @return le nom du champ
	 */
	String getNomChamp();

	/**
	 * @return le type de champ
	 */
	EnTypeChampsSQLite getTypeChamp();

	/**
	 * @return la taille max d'un champ
	 */
	Integer getTailleMax();

	/**
	 * @return le nom de l'enum
	 */
	String name();

	IStructureTable[] getListeChamp();

}
