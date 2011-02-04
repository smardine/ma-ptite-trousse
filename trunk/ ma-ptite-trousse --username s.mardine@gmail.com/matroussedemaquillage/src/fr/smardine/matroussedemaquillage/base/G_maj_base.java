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

public class G_maj_base {

	public static String[] SCRIPT_MAJ_V02 = { "UPDATE trousse_marques SET nom_marque=''L'Oréal'' where id_marque=80" };
	public static String[] SCRIPT_MAJ_V03 = { "UPDATE trousse_marques SET nom_marque=''Black'Up'' where id_marque=17",
			"UPDATE trousse_marques SET nom_marque=''L'Action Cosmétique'' where id_marque=79",
			"UPDATE trousse_marques SET nom_marque=''Mosqueta's'' where id_marque=110",
			"UPDATE trousse_marques SET nom_marque=''Paula's Choice'' where id_marque=120",
			"UPDATE trousse_marques SET nom_marque=''Phyt's'' where id_marque=123",
			"UPDATE trousse_marques SET nom_marque=''Planter's'' where id_marque=124",
			"UPDATE trousse_marques SET nom_marque=''So'Bio Etic'' where id_marque=140",
			"UPDATE trousse_marques SET nom_marque=''Terre d'Oc'' where id_marque=148" };
	public static String[] SCRIPT_MAJ_V04 = {
			"UPDATE trousse_produits SET nom_souscatergorie=''Fonds de teint'' where nom_souscatergorie=''Fond de tein''",
			"UPDATE trousse_produits SET nom_souscatergorie=''Correcteurs - Bases'' where nom_souscatergorie=''Correcteurs - Bases''",
			"UPDATE trousse_produits SET nom_souscatergorie=''Crayons contour'' where nom_souscatergorie=''Crayon contour''",
			"UPDATE trousse_produits SET nom_souscatergorie=''Rouges à lèvres'' where nom_souscatergorie=''Rouge à lèvre''",
			"UPDATE trousse_produits SET nom_souscatergorie=''Vernis à ongles'' where nom_souscatergorie=''Vernis à ongle''",
			"UPDATE trousse_produits SET nom_souscatergorie=''Crayons - Eyeliners'' where nom_souscatergorie=''Crayons - Eyliners''" };
	public static String[] SCRIPT_MAJ_V05 = { "INSERT INTO trousse_produits (nom_souscatergorie,nom_categorie,ISCHECKED) VALUES ('Pinceaux','Autres','false')" };
	public static String[] SCRIPT_MAJ_V06 = { "ALTER TABLE produit_Enregistre ADD nom_marque Varchar(255)" };
	public static String[] SCRIPT_MAJ_V07 = { "ALTER TABLE produit_Enregistre ADD Date_Peremption_milli LONG" };
	public static String[] SCRIPT_MAJ_V08 = { "ALTER TABLE produit_Enregistre ADD IS_PERIME VARCHAR (255)",
			"ALTER TABLE produit_Enregistre ADD IS_PRESQUE_PERIME VARCHAR (255)",
			"ALTER TABLE produit_Enregistre ADD NB_JOUR_AVANT_PEREMP VARCHAR (255)" };
	public static String[] SCRIPT_MAJ_V09 = { "CREATE TABLE IF NOT EXISTS Notes ( id_note INTEGER PRIMARY KEY  AUTOINCREMENT,Titre VARCHAR(255) NULL, Message VARCHAR (9999) NULL)" };
	public static String[] SCRIPT_MAJ_V10 = {
			"CREATE TABLE IF NOT EXISTS Param ( AfficheAlerte VARCHAR(255) NULL, DureeViePeremp VARCHAR (255) NULL)",
			"INSERT INTO Param (AfficheAlerte,DureeViePeremp) VALUES (''true'',''30'')" };
	public static String[] SCRIPT_MAJ_V11 = { "UPDATE  Param set DureeViePeremp=30 where DureeViePeremp>99" };
	public static String[] SCRIPT_MAJ_V12 = { "DROP TABLE Param",
			"CREATE TABLE IF NOT EXISTS Param ( AfficheAlerte VARCHAR(255) NULL,DureeViePeremp INTEGER )",
			"INSERT INTO Param (AfficheAlerte,DureeViePeremp) VALUES (''true'',''30'')" };
	public static String[] SCRIPT_MAJ_V13 = { "DROP TABLE Notes",
			"CREATE TABLE IF NOT EXISTS Notes ( id_note INTEGER PRIMARY KEY  AUTOINCREMENT,Titre VARCHAR(255) NULL, Message VARCHAR (9999) NULL)" };
	public static String[] SCRIPT_MAJ_V14 = { "ALTER TABLE Param ADD Theme Varchar(255)", "UPDATE Param SET Theme =''Classique''" };
}
