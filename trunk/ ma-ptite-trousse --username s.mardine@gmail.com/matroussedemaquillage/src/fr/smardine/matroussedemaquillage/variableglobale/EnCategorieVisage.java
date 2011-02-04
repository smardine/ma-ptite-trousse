package fr.smardine.matroussedemaquillage.variableglobale;

public enum EnCategorieVisage {
	Blush(0, "Blush"), Correcteurs_Bases(1, "Correcteurs - Bases"), FONDS_DE_TEINTS(2, "Fonds de teint"), Poudres(3, "Poudres");

	private int code;
	private String lib;

	private EnCategorieVisage(int p_code, String p_lib) {
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
