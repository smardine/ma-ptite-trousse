package fr.smardine.matroussedemaquillage.variableglobale;

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
	 * @return
	 */
	public Long getIdent() {
		return Long.valueOf(ordinal());
	}

	/**
	 * @return
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param p_code
	 */
	public void setCode(int p_code) {
		code = p_code;
	}

	/**
	 * @return
	 */
	public String getLib() {
		return lib;
	}

	/**
	 * @param p_lib
	 */
	public void setLib(String p_lib) {
		lib = p_lib;
	}

}
