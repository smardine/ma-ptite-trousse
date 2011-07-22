package fr.smardine.matroussedemaquillage.variableglobale;

import fr.smardine.matroussedemaquillage.EntryPoint;
import fr.smardine.matroussedemaquillage.recherche.Recherche;
import fr.smardine.matroussedemaquillage.recherche.recherche_produit_perime;

/**
 * @author sims
 *
 */
public enum EnActionParDefaut {
	RECHERCHE(0, "Affiche recherche",Recherche.class),//
	PAGE_PRINC(1, "Affiche page principale",EntryPoint.class), //
	PERIME(2, "Affiche produit(s) p�rim�(s)",recherche_produit_perime.class);
	private int code;
	private String lib;
	private Class classes;

	private EnActionParDefaut(int p_code, String p_lib,Class p_class) {
		code = p_code;
		lib = p_lib;
		classes = p_class;
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

	public Class getClasses() {
		return classes;
	}

}
