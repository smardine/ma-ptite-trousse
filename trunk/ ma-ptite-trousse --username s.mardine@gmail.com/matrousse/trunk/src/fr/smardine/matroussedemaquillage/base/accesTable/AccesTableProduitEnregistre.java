package fr.smardine.matroussedemaquillage.base.accesTable;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import fr.smardine.matroussedemaquillage.base.RequeteFactory;
import fr.smardine.matroussedemaquillage.base.structure.EnStructProduitEnregistre;
import fr.smardine.matroussedemaquillage.base.structure.EnTable;
import fr.smardine.matroussedemaquillage.mdl.MlListeProduits;
import fr.smardine.matroussedemaquillage.mdl.MlProduit;

/**
 * @author smardine Acces a la table des Produit enregistré en base
 */
public class AccesTableProduitEnregistre {

	private final RequeteFactory requeteFact;

	/**
	 * @param p_ctx le contexte
	 */
	public AccesTableProduitEnregistre(Context p_ctx) {
		requeteFact = new RequeteFactory(p_ctx);

	}

	/**
	 * @return obtenir le nombre d'enregistrement dans la table
	 */
	public int getNbEnregistrement() {
		return requeteFact.getNombreEnregistrement(EnTable.PRODUIT_ENREGISTRE);
	}

	/**
	 * @return MlListeProduits
	 */
	public MlListeProduits getListeProduits() {
		MlListeProduits lstProds = new MlListeProduits();
		String requete = "SELECT " + EnStructProduitEnregistre.ID.getNomChamp()
				+ " FROM " + EnTable.PRODUIT_ENREGISTRE.getNomTable();
		ArrayList<String> lstId = requeteFact.getListeDeChamp(requete).get(0);
		for (String anId : lstId) {
			MlProduit prod = new MlProduit(Integer.parseInt(anId), this);
			lstProds.add(prod);
		}
		return lstProds;

	}

	/**
	 * @return le nombre de produit périmé ou presque
	 */
	public int getNbProduitPerimeOuPresque() {
		String SQL = "SELECT Count("
				+ EnStructProduitEnregistre.ID.getNomChamp()
				+ ") FROM "
				+ EnTable.PRODUIT_ENREGISTRE.getNomTable()
				+ " WHERE "
				+ //
				"(" + EnStructProduitEnregistre.IS_PERIME.getNomChamp()
				+ "='true'" + " or "
				+ EnStructProduitEnregistre.IS_PRESQUE_PERIME.getNomChamp()
				+ "='true') ";
		return Integer.parseInt(requeteFact.get1Champ(SQL));
	}

	/**
	 * @param p_idProduits
	 * @return une liste de tableau de string
	 */
	public ArrayList<String> getTrousseComplete(int p_idProduits) {
		String requete = "Select "
				+ EnStructProduitEnregistre.NOM_PRODUIT.getNomChamp()
				+ " ,"
				+ EnStructProduitEnregistre.NOM_SOUSCAT.getNomChamp()
				+ //
				" , "
				+ EnStructProduitEnregistre.NOM_CAT.getNomChamp()
				+ //
				" , "
				+ EnStructProduitEnregistre.NUM_TEINTE.getNomChamp()
				+ //
				" , "
				+ EnStructProduitEnregistre.DUREE_VIE.getNomChamp()
				+ //
				" , "
				+ EnStructProduitEnregistre.DATE_PEREMP.getNomChamp()
				+ //
				" , "
				+ EnStructProduitEnregistre.DATE_ACHAT.getNomChamp()
				+ //
				" , "
				+ EnStructProduitEnregistre.NOM_MARQUE.getNomChamp()
				+ //
				", "
				+ EnStructProduitEnregistre.DATE_PEREMP_MILLI.getNomChamp()
				+ //
				" , "
				+ EnStructProduitEnregistre.IS_PERIME.getNomChamp()
				+ //
				" , "
				+ EnStructProduitEnregistre.IS_PRESQUE_PERIME.getNomChamp()
				+ //
				" , "
				+ EnStructProduitEnregistre.NB_JOUR_AVANT_PEREMP.getNomChamp()
				+ //
				" From " + EnTable.PRODUIT_ENREGISTRE.getNomTable()
				+ //
				" Where " + EnStructProduitEnregistre.ID.getNomChamp() + "="
				+ p_idProduits;
		return requeteFact.getListeDeChamp(requete).get(0);

	}

	/**
	 * @param p_idProduit le produit a corriger
	 */
	public void CorrigeProduitsEnregistre(int p_idProduit) {
		ArrayList<String> defProduit = getTrousseComplete(p_idProduit);

		String Nom_Produit = defProduit.get(0);
		String SousCat = defProduit.get(1);
		String Cat = defProduit.get(2);
		String Numeroteinte = defProduit.get(3);
		String DurreeVie = defProduit.get(4);
		String DatePeremption = defProduit.get(5);
		String DateAchat = defProduit.get(6);
		String NomMarque = defProduit.get(7);

		ContentValues modifiedValues = new ContentValues();
		modifiedValues.put("nom_produit", Nom_Produit.trim().replace("[", "")
				.replace("]", ""));
		modifiedValues.put("nom_souscatergorie", SousCat.trim()
				.replace("[", "").replace("]", ""));
		modifiedValues.put("nom_categorie", Cat.trim().replace("[", "")
				.replace("]", ""));
		modifiedValues.put("numero_Teinte", Numeroteinte.trim()
				.replace("[", "").replace("]", ""));
		modifiedValues.put("Duree_Vie", DurreeVie.trim().replace("[", "")
				.replace("]", ""));
		modifiedValues.put("Date_Peremption",
				DatePeremption.trim().replace("[", "").replace("]", ""));
		modifiedValues.put("DateAchat", DateAchat.trim().replace("[", "")
				.replace("]", ""));
		modifiedValues.put("nom_marque", NomMarque.trim().replace("[", "")
				.replace("]", ""));

		String whereClause = "id_produits=?";
		String[] whereArgs = new String[] { "" + p_idProduit + "" };
		requeteFact.majTable(EnTable.PRODUIT_ENREGISTRE, modifiedValues,
				whereClause, whereArgs);

	}

	/**
	 * Mise a jour des date de peremetion sur un produit, identifié par son ID
	 * @param p_idProduit
	 * @param p_datePeremption
	 * @param p_datePeremtInMilli
	 * @param p_perime
	 * @param p_presqueperime
	 */
	public void majDatepremeption(int p_idProduit, String p_datePeremption,
			long p_datePeremtInMilli, String p_perime, String p_presqueperime) {
		// String Table11 = "produit_Enregistre";
		ContentValues modifiedValues11 = new ContentValues();
		modifiedValues11.put("Date_Peremption", p_datePeremption);
		modifiedValues11.put("Date_Peremption_milli", p_datePeremtInMilli);
		modifiedValues11.put("IS_PERIME", p_perime);
		modifiedValues11.put("IS_PRESQUE_PERIME", p_presqueperime);
		String whereClause11 = "id_produits=?";
		String[] whereArgs11 = new String[] { "" + p_idProduit + "" };

		requeteFact.majTable(EnTable.PRODUIT_ENREGISTRE, modifiedValues11,
				whereClause11, whereArgs11);
	}

	/**
	 * Mise a jour de la catégorie et sous catégorie d'un produit identifié par
	 * son ID
	 * @param p_idProduit
	 * @param p_categorie
	 * @param p_sousCategorie
	 */
	public void majCatEtSousCat(int p_idProduit, String p_categorie,
			String p_sousCategorie) {
		ContentValues modifiedValues = new ContentValues();
		modifiedValues.put("nom_souscatergorie", p_sousCategorie);
		modifiedValues.put("nom_categorie", p_categorie);
		String whereClause = "id_produits=?";
		String[] whereArgs = new String[] { "" + p_idProduit + "" };
		requeteFact.majTable(EnTable.PRODUIT_ENREGISTRE, modifiedValues,
				whereClause, whereArgs);
	}

	/**
	 * @param p_idProduit
	 * @param p_nomProduit
	 * @param p_sousCat
	 * @param p_cat
	 * @param p_numeroTeinte
	 * @param p_durreeVie
	 * @param p_datePeremption
	 * @param p_dateAchat
	 * @param p_nomMarque
	 * @param p_datePeremtInMilli
	 */
	public void majProduitComplet(String p_idProduit, String p_nomProduit,
			String p_sousCat, String p_cat, String p_numeroTeinte,
			String p_durreeVie, String p_datePeremption, String p_dateAchat,
			String p_nomMarque, long p_datePeremtInMilli) {
		ContentValues modifiedValues = new ContentValues();
		modifiedValues.put("nom_produit", p_nomProduit);
		modifiedValues.put("nom_souscatergorie", p_sousCat);
		modifiedValues.put("nom_categorie", p_cat);
		modifiedValues.put("numero_Teinte", p_numeroTeinte);
		modifiedValues.put("Duree_Vie", p_durreeVie);
		modifiedValues.put("Date_Peremption", p_datePeremption);
		modifiedValues.put("DateAchat", p_dateAchat);
		modifiedValues.put("nom_marque", p_nomMarque);
		modifiedValues.put("Date_Peremption_milli", p_datePeremtInMilli);

		String whereClause = "id_produits=?";
		String[] whereArgs = new String[] { p_idProduit };
		requeteFact.majTable(EnTable.PRODUIT_ENREGISTRE, modifiedValues,
				whereClause, whereArgs);

	}

	/**
	 * @param p_filtrage
	 * @return la liste des produits correspondant au filtrage
	 */
	public MlListeProduits getProduitsAvecFiltrageSurCategorie(String p_filtrage) {
		MlListeProduits lst = new MlListeProduits();
		String requete = "SELECT id_produits FROM produit_Enregistre where nom_souscatergorie LIKE '%"
				+ p_filtrage + "%' ORDER BY nom_souscatergorie";
		for (String s : requeteFact.getListeDeChamp(requete).get(0)) {
			lst.add(new MlProduit(Integer.parseInt(s), this));
		}
		return lst;
	}

	public MlListeProduits getProduitsAvecFiltrageSurMarque(String p_Filtrage) {
		MlListeProduits lst = new MlListeProduits();
		String requete = "SELECT id_produits FROM produit_Enregistre where nom_marque LIKE '%"
				+ p_Filtrage + "%' ORDER BY nom_marque";
		for (String s : requeteFact.getListeDeChamp(requete).get(0)) {
			lst.add(new MlProduit(Integer.parseInt(s), this));
		}
		return lst;
	}

	public MlListeProduits getProduitsAvecFiltrageSurTout(String p_Filtrage) {
		MlListeProduits lst = new MlListeProduits();
		String requete = "SELECT" + " id_produits "
				+ "FROM produit_Enregistre " + "where nom_produit LIKE '%"
				+ p_Filtrage + "%' " + "or nom_marque LIKE '%" + p_Filtrage
				+ "%' " + "or nom_souscatergorie LIKE '%" + p_Filtrage
				+ "%' ORDER BY id_produits";
		for (String s : requeteFact.getListeDeChamp(requete).get(0)) {
			lst.add(new MlProduit(Integer.parseInt(s), this));
		}
		return lst;
	}

	public MlListeProduits getProduitsPerime() {
		MlListeProduits lst = new MlListeProduits();
		String requete = "SELECT "
				+ EnStructProduitEnregistre.ID.getNomChamp()
				+ " FROM "
				+ EnTable.PRODUIT_ENREGISTRE.getNomTable()
				+ " WHERE "
				+ //
				"(" + EnStructProduitEnregistre.IS_PERIME.getNomChamp()
				+ "='true'" + " or "
				+ EnStructProduitEnregistre.IS_PRESQUE_PERIME.getNomChamp()
				+ "='true') ";
		for (String s : requeteFact.getListeDeChamp(requete).get(0)) {
			lst.add(new MlProduit(Integer.parseInt(s), this));
		}
		return lst;
	}

	public MlListeProduits getProduitsPerimeAvecFiltrageSurCategorie(
			String p_filtrage) {
		MlListeProduits lst = new MlListeProduits();
		String requete = "SELECT id_produits FROM produit_Enregistre where nom_souscatergorie LIKE '%"
				+ p_filtrage
				+ "%' and (IS_PERIME='true' or IS_PRESQUE_PERIME='true') " //
				+ "ORDER BY Date_Peremption";
		for (String s : requeteFact.getListeDeChamp(requete).get(0)) {
			lst.add(new MlProduit(Integer.parseInt(s), this));
		}
		return lst;
	}

	public MlListeProduits getProduitsPerimeAvecFiltrageSurMarque(
			String p_Filtrage) {
		MlListeProduits lst = new MlListeProduits();
		String requete = "SELECT id_produits FROM produit_Enregistre where nom_marque LIKE '%"
				+ p_Filtrage
				+ "%' and (IS_PERIME='true' or IS_PRESQUE_PERIME='true') ORDER BY nom_marque";
		for (String s : requeteFact.getListeDeChamp(requete).get(0)) {
			lst.add(new MlProduit(Integer.parseInt(s), this));
		}
		return lst;
	}

	public MlListeProduits getProduitsPerimeAvecFiltrageSurTout(
			String p_Filtrage) {
		MlListeProduits lst = new MlListeProduits();
		String requete = "SELECT"
				+ " id_produits "
				+ "FROM produit_Enregistre "
				+ "where nom_produit LIKE '%"
				+ p_Filtrage
				+ "%' "
				+ "or nom_marque LIKE '%"
				+ p_Filtrage
				+ "%' "
				+ "or nom_souscatergorie LIKE '%"
				+ p_Filtrage
				+ "%' and (IS_PERIME='true' or IS_PRESQUE_PERIME='true')  ORDER BY id_produits";
		for (String s : requeteFact.getListeDeChamp(requete).get(0)) {
			lst.add(new MlProduit(Integer.parseInt(s), this));
		}
		return lst;
	}
}
