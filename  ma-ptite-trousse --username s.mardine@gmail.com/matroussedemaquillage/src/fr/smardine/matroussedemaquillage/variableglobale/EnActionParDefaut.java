package fr.smardine.matroussedemaquillage.variableglobale;

public enum EnActionParDefaut {
	RECHERCHE (0,"Affiche recherche"),
	PAGE_PRINC (1,"Affiche page principale"),
	PERIME (2,"Affiche produit périmé");
	private int code;
	private String lib;

	private EnActionParDefaut(int p_code, String p_lib) {
		code = p_code;
		lib = p_lib;
	}

	public Long getIdent() {
		return Long.valueOf(ordinal());
	}

	public int getCode() {
		return code;
	}

	public String getLib() {
		return lib;
	}

}
