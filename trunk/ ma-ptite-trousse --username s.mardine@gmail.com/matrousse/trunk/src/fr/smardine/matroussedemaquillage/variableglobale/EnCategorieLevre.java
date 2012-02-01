package fr.smardine.matroussedemaquillage.variableglobale;

/**
 * @author smardine
 */
public enum EnCategorieLevre implements EnCategorie {
	/**
	 * 
	 */
	Crayons_contour(0, "Crayons contour"), /**
	 * 
	 */
	//
	RougesAlevres(1, "Rouges à lèvres");

	private int code;
	private String lib;

	private EnCategorieLevre(int p_code, String p_lib) {
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
		for (EnCategorieLevre e : EnCategorieLevre.values()) {
			if (((EnCategorie) e).getLib().equals(p_value)) {
				return e;
			}
		}

		return null;
	}

}
