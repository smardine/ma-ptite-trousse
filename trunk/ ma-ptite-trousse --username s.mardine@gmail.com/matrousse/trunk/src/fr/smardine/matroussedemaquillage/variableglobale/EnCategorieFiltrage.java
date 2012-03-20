package fr.smardine.matroussedemaquillage.variableglobale;

/**
 * @author smardine
 */
public enum EnCategorieFiltrage {
	/**
	 * 
	 */
	TOUT(0, "Tout"), //
	/**
	 * 
	 */
	MARQUE(1, "Marques"), //
	/**
	 * 
	 */
	CATEGORIE(2, "Catégories");

	private int code;
	private String lib;

	private EnCategorieFiltrage(int p_code, String p_lib) {
		code = p_code;
		lib = p_lib;
	}

	/**
	 * @return l'ident
	 */
	public Long getIdent() {
		return Long.valueOf(ordinal());
	}

	/**
	 * @return le code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @return the lib
	 */
	public String getLib() {
		return lib;
	}

}
