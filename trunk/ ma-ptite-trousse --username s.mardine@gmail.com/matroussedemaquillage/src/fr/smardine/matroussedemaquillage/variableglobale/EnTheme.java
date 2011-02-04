package fr.smardine.matroussedemaquillage.variableglobale;

public enum EnTheme {

	Classique(0, "Classique"), //
	Fleur(1, "Fleur"), Bisounours(2, "Bisounours");

	int code;
	String lib;

	EnTheme(int p_code, String p_lib) {
		code = p_code;
		lib = p_lib;
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
