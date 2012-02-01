package fr.smardine.matroussedemaquillage.variableglobale;

/**
 * interface regroupant toutes les categories
 * @author smardine
 */
public interface EnCategorie {

	/**
	 * @return the lib
	 */
	String getLib();

	/**
	 * @return the code
	 */
	int getCode();

	/**
	 * @return the ident
	 */
	Long getIdent();

	/**
	 * @param p_value
	 * @return Enum correspondant ou null
	 */
	EnCategorie getCategorieFromValue(String p_value);

}
