package fr.smardine.matroussedemaquillage.variableglobale;

public enum EnCategorieLevre {
	Crayons_contour (0,"Crayons contour"),
	RougesAlevres(1,"Rouges à lèvres");
	
	private int code;
	private String lib;

	private EnCategorieLevre(int p_code, String p_lib) {
		code = p_code;
		lib = p_lib;
	}
	
	

	public Long getIdent (){
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
