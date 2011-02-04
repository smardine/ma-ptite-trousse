package fr.smardine.matroussedemaquillage.variableglobale;

public enum EnCategorieYeux {
	Bases(0, "Bases"), Crayons_Eyeliners(1, "Crayons - Eyeliners"), Fards(2, "Fards"), Mascaras(3, "Mascaras");

	private int code;
	private String lib;

	private EnCategorieYeux(int p_code, String p_lib) {
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
