package fr.smardine.matroussedemaquillage.mdl.cat;

/**
 * @author smardine
 */
public enum EnCategorieYeux implements EnCategorie {
	/**
	 * 
	 */
	Bases(0, "Bases"), //
	/**
	 * 
	 */
	Crayons_Eyeliners(1, "Crayons - Eyeliners"), //
	/**
	 * 
	 */
	Fards(2, "Fards"), //
	/**
	 * 
	 */
	Mascaras(3, "Mascaras");

	private int code;
	private String lib;

	private EnCategorieYeux(int p_code, String p_lib) {
		code = p_code;
		lib = p_lib;
	}

	/**
	 * @return l'ident
	 */
	@Override
	public Long getIdent() {
		return Long.valueOf(ordinal());
	}

	/**
	 * @return le code
	 */
	@Override
	public int getCode() {
		return code;
	}

	/**
	 * @return the lib
	 */
	@Override
	public String getLib() {
		return lib;
	}

	public static EnCategorie getCategorieFromValue(String p_value) {
		for (EnCategorieYeux e : EnCategorieYeux.values()) {
			if (e.getLib().equals(p_value)) {
				return e;
			}
		}
		return null;
	}

	public static EnCategorieYeux getCategorieFromCode(int p_code) {
		for (EnCategorieYeux e : values()) {
			if (e.getCode() == p_code) {
				return e;
			}
		}

		return null;
	}

}
