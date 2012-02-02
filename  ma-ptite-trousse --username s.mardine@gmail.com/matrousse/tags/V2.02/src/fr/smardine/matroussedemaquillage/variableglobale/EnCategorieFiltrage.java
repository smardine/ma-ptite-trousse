package fr.smardine.matroussedemaquillage.variableglobale;

/**
 * @author smardine
 */
public enum EnCategorieFiltrage {
	/**
	 * 
	 */
	Tout(0, "Tout"), //
	/**
	 * 
	 */
	Marque(1, "Marques"), //
	/**
	 * 
	 */
	Categorie(2, "Catégories");

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
