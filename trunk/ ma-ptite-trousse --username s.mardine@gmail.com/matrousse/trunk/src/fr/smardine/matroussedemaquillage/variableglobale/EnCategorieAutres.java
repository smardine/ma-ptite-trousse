package fr.smardine.matroussedemaquillage.variableglobale;

/**
 * @author smardine
 */
public enum EnCategorieAutres implements EnCategorie {
	Pinceaux(0, "Pinceaux"), //
	VernisAongles(1, "Vernis à ongles");

	private int code;
	private String lib;

	private EnCategorieAutres(int p_code, String p_lib) {
		code = p_code;
		lib = p_lib;
	}

	public Long getIdent() {
		return Long.valueOf(ordinal());
	}

	public int getCode() {
		return code;
	}

	public void setCode(int p_code) {
		code = p_code;
	}

	public String getLib() {
		return lib;
	}

	public void setLib(String p_lib) {
		lib = p_lib;
	}

}
