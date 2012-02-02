package fr.smardine.matroussedemaquillage.variableglobale;

/**
 * Periodicité de dontrole des donnees en base pour le widget
 * @author smardine
 */
public enum EnPeriodicite {
	/**
	 * 
	 */
	HEURE(0, "Heures"), //
	/**
	 * 
	 */
	JOUR(1, "JOUR");

	private int code;
	private String lib;

	private EnPeriodicite(int p_code, String p_lib) {
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
