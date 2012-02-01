package fr.smardine.matroussedemaquillage.variableglobale;

/**
 * @author smardine
 */
public enum EnCategorieAutres implements EnCategorie {
	/**
	 * 
	 */
	Pinceaux(0, "Pinceaux"), //
	/**
	 * 
	 */
	VernisAongles(1, "Vernis à ongles");

	private final int code;
	private final String lib;

	private EnCategorieAutres(int p_code, String p_lib) {
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
		for (EnCategorieAutres e : EnCategorieAutres.values()) {
			if (((EnCategorie) e).getLib().equals(p_value)) {
				return e;
			}
		}
		return null;
	}

}
