package fr.smardine.matroussedemaquillage.variableglobale;

/**
 * @author smardine
 */
public enum EnTheme {

	/**
	 * 
	 */
	Classique(0, "Classique"), //
	/**
	 * 
	 */
	Fleur(0, "Fleur"), //
	/**
	 * 
	 */
	Bisounours(1, "Bisounours");

	int code;
	String lib;

	EnTheme(int p_code, String p_lib) {
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

	/**
	 * Obtenir l'enumeration correspondante au theme
	 * @param p_nomTheme
	 * @return l'enum
	 */
	public static EnTheme getThemeFromValue(String p_nomTheme) {
		for (EnTheme e : values()) {
			if (((EnTheme) e).getLib().equals(p_nomTheme)) {
				return e;
			}
		}
		return null;
	}

}
