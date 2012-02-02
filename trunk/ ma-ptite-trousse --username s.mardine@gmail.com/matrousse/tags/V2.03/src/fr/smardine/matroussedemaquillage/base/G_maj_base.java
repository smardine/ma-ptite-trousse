/*
 * Copyright (c) 2009 nullwire aps Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software
 * is furnished to do so, subject to the following conditions: The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. Contributors: Mads
 * Kristiansen, mads.kristiansen@nullwire.com Glen Humphrey Evan Charlton Peter Hewitt
 */

package fr.smardine.matroussedemaquillage.base;

import java.util.ArrayList;
import java.util.List;

/**
 * @author smardine contient tous les scripts de mise a jour
 */
public class G_maj_base {
	private List<String> allversion;
	private List<String> version2;
	private List<String> version3;
	private List<String> version4;
	private List<String> version5;
	private List<String> version6;
	private List<String> version7;
	private List<String> version8;
	private List<String> version9;
	private List<String> version10;
	private List<String> version11;
	private List<String> version12;
	private List<String> version13;
	private List<String> version14;

	/**
	 * @return tout les scripts de mise a jours
	 */
	public List<String> getAllVersion() {
		allversion = new ArrayList<String>();
		allversion.addAll(getVersion2());
		allversion.addAll(getVersion3());
		allversion.addAll(getVersion4());
		allversion.addAll(getVersion5());
		allversion.addAll(getVersion6());
		allversion.addAll(getVersion7());
		allversion.addAll(getVersion8());
		allversion.addAll(getVersion9());
		allversion.addAll(getVersion10());
		allversion.addAll(getVersion11());
		allversion.addAll(getVersion12());
		allversion.addAll(getVersion13());
		allversion.addAll(getVersion14());
		return allversion;
	}

	/**
	 * @return les scripts de mise a jour en v02
	 */
	public List<String> getVersion2() {
		version2 = new ArrayList<String>();
		version2.add("UPDATE trousse_marques SET nom_marque=\"L'Oréal\" where id_marque=80");
		return version2;
	}

	/**
	 * @return les scripts de mise a jour en v03
	 */
	public List<String> getVersion3() {
		version3 = new ArrayList<String>();
		version3.add("UPDATE trousse_marques SET nom_marque=\"Black'Up\" where id_marque=17");
		version3.add("UPDATE trousse_marques SET nom_marque=\"L'Action Cosmétique\" where id_marque=79");
		version3.add("UPDATE trousse_marques SET nom_marque=\"Mosqueta's\" where id_marque=110");
		version3.add("UPDATE trousse_marques SET nom_marque=\"Paula's Choice\" where id_marque=120");
		version3.add("UPDATE trousse_marques SET nom_marque=\"Phyt's\" where id_marque=123");
		version3.add("UPDATE trousse_marques SET nom_marque=\"Planter's\" where id_marque=124");
		version3.add("UPDATE trousse_marques SET nom_marque=\"So'Bio Etic\" where id_marque=140");
		version3.add("UPDATE trousse_marques SET nom_marque=\"Terre d'Oc\" where id_marque=148");
		return version3;
	}

	/**
	 * @return les scripts de mise a jour en v04
	 */
	public List<String> getVersion4() {
		version4 = new ArrayList<String>();
		version4.add("UPDATE trousse_produits SET nom_souscatergorie=\"Fonds de teint\" where nom_souscatergorie=\"Fond de tein\"");
		version4.add("UPDATE trousse_produits SET nom_souscatergorie=\"Correcteurs - Bases\" where nom_souscatergorie=\"Correcteurs - Bases\"");
		version4.add("UPDATE trousse_produits SET nom_souscatergorie=\"Crayons contour\" where nom_souscatergorie=\"Crayon contour\"");
		version4.add("UPDATE trousse_produits SET nom_souscatergorie=\"Rouges à lèvres\" where nom_souscatergorie=\"Rouge à lèvre\"");
		version4.add("UPDATE trousse_produits SET nom_souscatergorie=\"Vernis à ongles\" where nom_souscatergorie=\"Vernis à ongle\"");
		version4.add("UPDATE trousse_produits SET nom_souscatergorie=\"Crayons - Eyeliners\" where nom_souscatergorie=\"Crayons - Eyliners\"");
		return version4;

	}

	/**
	 * @return les scripts de mise a jour en v05
	 */
	public List<String> getVersion5() {
		version5 = new ArrayList<String>();
		version5.add("INSERT INTO trousse_produits (nom_souscatergorie,nom_categorie,ISCHECKED) VALUES ('Pinceaux','Autres','false')");
		return version5;
	}

	/**
	 * @return les scripts de mise a jour en v06
	 */
	public List<String> getVersion6() {
		version6 = new ArrayList<String>(1);
		version6.add("ALTER TABLE produit_Enregistre ADD nom_marque Varchar(255)");
		return version6;
	}

	/**
	 * @return les scripts de mise a jour en v07
	 */
	public List<String> getVersion7() {
		version7 = new ArrayList<String>();
		version7.add("ALTER TABLE produit_Enregistre ADD Date_Peremption_milli LONG");
		return version7;
	}

	/**
	 * @return les scripts de mise a jour en v08
	 */
	public List<String> getVersion8() {
		version8 = new ArrayList<String>();
		version8.add("ALTER TABLE produit_Enregistre ADD IS_PERIME VARCHAR (255)");
		version8.add("ALTER TABLE produit_Enregistre ADD IS_PRESQUE_PERIME VARCHAR (255)");
		version8.add("ALTER TABLE produit_Enregistre ADD NB_JOUR_AVANT_PEREMP VARCHAR (255)");
		return version8;
	}

	/**
	 * @return les scripts de mise a jour en v09
	 */
	public List<String> getVersion9() {
		version9 = new ArrayList<String>(1);
		version9.add("CREATE TABLE IF NOT EXISTS Notes ( id_note INTEGER PRIMARY KEY  AUTOINCREMENT,Titre VARCHAR(255) NULL, Message VARCHAR (9999) NULL)");
		return version9;
	}

	/**
	 * @return les scripts de mise a jour en v10
	 */
	public List<String> getVersion10() {
		version10 = new ArrayList<String>();
		version10.add("CREATE TABLE IF NOT EXISTS Param ( AfficheAlerte VARCHAR(255) NULL, DureeViePeremp VARCHAR (255) NULL)");
		version10.add("INSERT INTO Param (AfficheAlerte,DureeViePeremp) VALUES (\"true\",\"30\")");
		return version10;
	}

	/**
	 * @return les scripts de mise a jour en v11
	 */
	public List<String> getVersion11() {
		version11 = new ArrayList<String>();
		version11.add("UPDATE  Param set DureeViePeremp=30 where DureeViePeremp>99");
		return version11;
	}

	/**
	 * @return les scripts de mise a jour en v12
	 */
	public List<String> getVersion12() {
		version12 = new ArrayList<String>();
		version12.add("DROP TABLE Param");
		version12.add("CREATE TABLE IF NOT EXISTS Param ( AfficheAlerte VARCHAR(255) NULL,DureeViePeremp INTEGER )");
		version12.add("INSERT INTO Param (AfficheAlerte,DureeViePeremp) VALUES (\"true\",\"30\")");
		return version12;
	}

	/**
	 * @return les scripts de mise a jour en v13
	 */
	public List<String> getVersion13() {
		version13 = new ArrayList<String>();
		version13.add("DROP TABLE Notes");
		version13
				.add("CREATE TABLE IF NOT EXISTS Notes ( id_note INTEGER PRIMARY KEY  AUTOINCREMENT,Titre VARCHAR(255) NULL, Message VARCHAR (9999) NULL)");
		return version13;
	}

	/**
	 * @return les scripts de mise a jour en v14
	 */
	public List<String> getVersion14() {
		version14 = new ArrayList<String>();
		version14.add("ALTER TABLE Param ADD Theme Varchar(255)");
		version14.add("UPDATE Param SET Theme =\"Classique\"");
		return version14;
	}

}
