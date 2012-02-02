package fr.smardine.matroussedemaquillage.variableglobale;

/**
 * @author smardine
 */
public enum EnCategorieVisage implements EnCategorie {
	/**
	 * 
	 */
	Blush(0, "Blush"), //
	/**
	 * 
	 */
	Correcteurs_Bases(1, "Correcteurs - Bases"), //
	/**
	 * 
	 */
	FONDS_DE_TEINTS(2, "Fonds de teint"), //
	/**
	 * 
	 */
	Poudres(3, "Poudres");

	private int code;
	private String lib;

	private EnCategorieVisage(int p_code, String p_lib) {
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

	@Override
	public EnCategorie getCategorieFromValue(String p_value) {
		for (EnCategorieVisage e : EnCategorieVisage.values()) {
			if (((EnCategorie) e).getLib().equals(p_value)) {
				return e;
			}
		}

		return null;
	}

}
