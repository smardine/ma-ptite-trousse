package fr.smardine.matroussedemaquillage.base.accesTable;

import helper.DateHelper;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import fr.smardine.matroussedemaquillage.base.RequeteFactory;
import fr.smardine.matroussedemaquillage.base.structure.EnStructProduitEnregistre;
import fr.smardine.matroussedemaquillage.base.structure.EnTable;
import fr.smardine.matroussedemaquillage.factory.MlProduitEnregistreFactory;
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
		// String requete = "SELECT " +
		// EnStructProduitEnregistre.ID.getNomChamp()
		// + " FROM " + EnTable.PRODUIT_ENREGISTRE.getNomTable();
		// List<ArrayList<String>> lstId = requeteFact.getListeDeChamp(requete);
		// for (ArrayList<String> anId : lstId) {
		// // MlProduit prod = new MlProduit(Integer.parseInt(anId.get(0)),
		// // ctx);
		// MlProduit prod = new MlProduit(ctx);
		// prod.setIdProduit(p_idProduit)
		// lstProds.add(prod);
		// }

		List<ArrayList<Object>> lstRetour = requeteFact.getListeDeChampBis(
				EnTable.PRODUIT_ENREGISTRE, EnStructProduitEnregistre.class,
				null);
		MlProduitEnregistreFactory prodFact = new MlProduitEnregistreFactory();
		for (ArrayList<Object> aList : lstRetour) {
			lstProds.add(prodFact.creationMlProduitEnregistre(aList));
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
	public ArrayList<String> getDefProduitById(int p_idProduits) {
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
	 * @param p_produit le produit a corriger
	 */
	public void CorrigeProduitsEnregistre(MlProduit p_produit) {

		ContentValues modifiedValues = new ContentValues();
		modifiedValues.put("nom_produit", p_produit.getNomProduit());
		modifiedValues.put("nom_souscatergorie", p_produit.getCategorie()
				.getSousCategorie().getLib());
		modifiedValues.put("nom_categorie", p_produit.getCategorie()
				.getCategorie().name());
		modifiedValues.put("numero_Teinte", p_produit.getTeinte());
		modifiedValues.put("Duree_Vie", "" + p_produit.getDureeVie());
		modifiedValues.put("Date_Peremption",
				DateHelper.getDateforDatabase(p_produit.getDatePeremption()));
		modifiedValues.put("DateAchat",
				DateHelper.getDateforDatabase(p_produit.getDateAchat()));
		modifiedValues.put("nom_marque", p_produit.getMarque());

		String whereClause = "id_produits=?";
		String[] whereArgs = new String[] { "" + p_produit + "" };
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
	 * @param p_produit
	 */
	public void majProduitComplet(MlProduit p_produit) {
		ContentValues modifiedValues = new ContentValues();
		modifiedValues.put("nom_produit", p_produit.getNomProduit());
		modifiedValues.put("nom_souscatergorie", p_produit.getCategorie()
				.getSousCategorie().getLib());
		modifiedValues.put("nom_categorie", p_produit.getCategorie()
				.getCategorie().name());
		modifiedValues.put("numero_Teinte", p_produit.getTeinte());
		modifiedValues.put("Duree_Vie", p_produit.getDureeVie());
		modifiedValues.put("Date_Peremption",
				DateHelper.getDateforDatabase(p_produit.getDatePeremption()));
		modifiedValues.put("DateAchat",
				DateHelper.getDateforDatabase(p_produit.getDateAchat()));
		modifiedValues.put("nom_marque", p_produit.getMarque());
		modifiedValues.put("Date_Peremption_milli",
				p_produit.getDatePeremMilli());

		String whereClause = "id_produits=?";
		String[] whereArgs = new String[] { "" + p_produit.getIdProduit() + "" };
		requeteFact.majTable(EnTable.PRODUIT_ENREGISTRE, modifiedValues,
				whereClause, whereArgs);

	}

	/**
	 * @param p_produit
	 * @return true ou false
	 */
	public boolean insertProduit(MlProduit p_produit) {
		ContentValues newValueToInsert = new ContentValues();
		newValueToInsert.put("nom_produit", p_produit.getNomProduit());
		newValueToInsert.put("nom_marque", p_produit.getMarque());
		newValueToInsert.put("nom_souscatergorie", p_produit.getCategorie()
				.getSousCategorie().getLib());
		newValueToInsert.put("nom_categorie", p_produit.getCategorie()
				.getCategorie().name());
		newValueToInsert.put("numero_Teinte", p_produit.getTeinte());
		newValueToInsert.put("DateAchat",
				DateHelper.getDateforDatabase(p_produit.getDateAchat()));
		newValueToInsert.put("Duree_Vie", "" + p_produit.getDureeVie());
		// calcul de la date de peremption problable

		int nbMoisDurreeDeVie = p_produit.getDureeVie();

		java.util.Date DatePeremption = DateHelper.getDatePeremption(
				p_produit.getDateAchat(), nbMoisDurreeDeVie);

		newValueToInsert.put("Date_Peremption_milli", DatePeremption.getTime());

		newValueToInsert.put("Date_Peremption",
				DateHelper.getDateforDatabase(DatePeremption));

		return requeteFact.insertDansTable(EnTable.PRODUIT_ENREGISTRE,
				newValueToInsert);
	}

	/**
	 * @param p_filtrage
	 * @return la liste des produits correspondant au filtrage
	 */
	public MlListeProduits getListeProduitsAvecFiltrageSurCategorie(
			String p_filtrage) {
		MlListeProduits lst = new MlListeProduits();
		// String requete =
		// "SELECT id_produits FROM produit_Enregistre where nom_souscatergorie LIKE '%"
		// + p_filtrage + "%' ORDER BY nom_souscatergorie";
		// List<ArrayList<String>> lstId = requeteFact.getListeDeChamp(requete);
		List<ArrayList<Object>> lstRetour = requeteFact.getListeDeChampBis(
				EnTable.PRODUIT_ENREGISTRE, EnStructProduitEnregistre.class,
				" nom_souscatergorie LIKE '%" + p_filtrage
						+ "%' ORDER BY nom_souscatergorie");

		MlProduitEnregistreFactory prodFact = new MlProduitEnregistreFactory();
		for (ArrayList<Object> aList : lstRetour) {
			lst.add(prodFact.creationMlProduitEnregistre(aList));
		}
		return lst;
	}

	/**
	 * @param p_Filtrage
	 * @return la liste des produits correspondant au filtrage
	 */
	public MlListeProduits getListeProduitsAvecFiltrageSurMarque(
			String p_Filtrage) {
		MlListeProduits lst = new MlListeProduits();
		// String requete =
		// "SELECT id_produits FROM produit_Enregistre where nom_marque LIKE '%"
		// + p_Filtrage + "%' ORDER BY nom_marque";
		// List<ArrayList<String>> lstId = requeteFact.getListeDeChamp(requete);
		// for (ArrayList<String> anId : lstId) {
		// MlProduit prod = new MlProduit(Integer.parseInt(anId.get(0)), ctx);
		// lst.add(prod);
		// }

		List<ArrayList<Object>> lstRetour = requeteFact.getListeDeChampBis(
				EnTable.PRODUIT_ENREGISTRE, EnStructProduitEnregistre.class,
				"nom_marque LIKE '%" + p_Filtrage + "%' ORDER BY p_Filtrage");

		MlProduitEnregistreFactory prodFact = new MlProduitEnregistreFactory();
		for (ArrayList<Object> aList : lstRetour) {
			lst.add(prodFact.creationMlProduitEnregistre(aList));
		}

		return lst;
	}

	/**
	 * @param p_Filtrage
	 * @return la liste des produits correspondant au filtrage
	 */
	public MlListeProduits getListeProduitsAvecFiltrageSurTout(String p_Filtrage) {
		MlListeProduits lst = new MlListeProduits();
		// String requete = "SELECT" + " id_produits "
		// + "FROM produit_Enregistre " + "where nom_produit LIKE '%"
		// + p_Filtrage + "%' " + "or nom_marque LIKE '%" + p_Filtrage
		// + "%' " + "or nom_souscatergorie LIKE '%" + p_Filtrage
		// + "%' ORDER BY id_produits";
		// List<ArrayList<String>> lstId = requeteFact.getListeDeChamp(requete);
		// for (ArrayList<String> anId : lstId) {
		// MlProduit prod = new MlProduit(Integer.parseInt(anId.get(0)), ctx);
		// lst.add(prod);
		// }

		List<ArrayList<Object>> lstRetour = requeteFact.getListeDeChampBis(
				EnTable.PRODUIT_ENREGISTRE, EnStructProduitEnregistre.class,
				"nom_produit LIKE '%" + p_Filtrage + "%' "
						+ "or nom_marque LIKE '%" + p_Filtrage + "%' "
						+ "or nom_souscatergorie LIKE '%" + p_Filtrage
						+ "%' ORDER BY id_produits");

		MlProduitEnregistreFactory prodFact = new MlProduitEnregistreFactory();
		for (ArrayList<Object> aList : lstRetour) {
			lst.add(prodFact.creationMlProduitEnregistre(aList));
		}

		return lst;
	}

	/**
	 * @return la liste des produits perime ou presque perime
	 */
	public MlListeProduits getListeProduitsPerime() {
		MlListeProduits lst = new MlListeProduits();
		// String requete = "SELECT "
		// + EnStructProduitEnregistre.ID.getNomChamp()
		// + " FROM "
		// + EnTable.PRODUIT_ENREGISTRE.getNomTable()
		// + " WHERE "
		// + //
		// "(" + EnStructProduitEnregistre.IS_PERIME.getNomChamp()
		// + "='true'" + " or "
		// + EnStructProduitEnregistre.IS_PRESQUE_PERIME.getNomChamp()
		// + "='true') ";
		// List<ArrayList<String>> lstId = requeteFact.getListeDeChamp(requete);
		// for (ArrayList<String> anId : lstId) {
		// MlProduit prod = new MlProduit(Integer.parseInt(anId.get(0)), ctx);
		// lst.add(prod);
		// }

		List<ArrayList<Object>> lstRetour = requeteFact.getListeDeChampBis(
				EnTable.PRODUIT_ENREGISTRE,
				EnStructProduitEnregistre.class,
				"("
						+ EnStructProduitEnregistre.IS_PERIME.getNomChamp()
						+ "='true'"
						+ " or "
						+ EnStructProduitEnregistre.IS_PRESQUE_PERIME
								.getNomChamp() + "='true') ");

		MlProduitEnregistreFactory prodFact = new MlProduitEnregistreFactory();
		for (ArrayList<Object> aList : lstRetour) {
			lst.add(prodFact.creationMlProduitEnregistre(aList));
		}

		return lst;

	}

	/**
	 * @param p_filtrage
	 * @return la liste des produits correspondant au filtrage
	 */
	public MlListeProduits getListeProduitsPerimeAvecFiltrageSurCategorie(
			String p_filtrage) {
		MlListeProduits lst = new MlListeProduits();
		// String requete =
		// "SELECT id_produits FROM produit_Enregistre where nom_souscatergorie LIKE '%"
		// + p_filtrage
		// + "%' and (IS_PERIME='true' or IS_PRESQUE_PERIME='true') " //
		// + "ORDER BY Date_Peremption";
		// List<ArrayList<String>> lstId = requeteFact.getListeDeChamp(requete);
		// for (ArrayList<String> anId : lstId) {
		// MlProduit prod = new MlProduit(Integer.parseInt(anId.get(0)), ctx);
		// lst.add(prod);
		// }

		List<ArrayList<Object>> lstRetour = requeteFact
				.getListeDeChampBis(
						EnTable.PRODUIT_ENREGISTRE,
						EnStructProduitEnregistre.class,
						"nom_souscatergorie LIKE '%"
								+ p_filtrage
								+ "%' and (IS_PERIME='true' or IS_PRESQUE_PERIME='true') " //
								+ "ORDER BY Date_Peremption");

		MlProduitEnregistreFactory prodFact = new MlProduitEnregistreFactory();
		for (ArrayList<Object> aList : lstRetour) {
			lst.add(prodFact.creationMlProduitEnregistre(aList));
		}

		return lst;
	}

	/**
	 * @param p_Filtrage
	 * @return la liste des produits correspondant au filtrage
	 */
	public MlListeProduits getListeProduitsPerimeAvecFiltrageSurMarque(
			String p_Filtrage) {
		MlListeProduits lst = new MlListeProduits();
		// String requete =
		// "SELECT id_produits FROM produit_Enregistre where nom_marque LIKE '%"
		// + p_Filtrage
		// +
		// "%' and (IS_PERIME='true' or IS_PRESQUE_PERIME='true') ORDER BY nom_marque";
		//
		// List<ArrayList<String>> lstId = requeteFact.getListeDeChamp(requete);
		// for (ArrayList<String> anId : lstId) {
		// MlProduit prod = new MlProduit(Integer.parseInt(anId.get(0)), ctx);
		// lst.add(prod);
		// }

		List<ArrayList<Object>> lstRetour = requeteFact
				.getListeDeChampBis(
						EnTable.PRODUIT_ENREGISTRE,
						EnStructProduitEnregistre.class,
						"nom_marque LIKE '%"
								+ p_Filtrage
								+ "%' and (IS_PERIME='true' or IS_PRESQUE_PERIME='true') ORDER BY nom_marque");

		MlProduitEnregistreFactory prodFact = new MlProduitEnregistreFactory();
		for (ArrayList<Object> aList : lstRetour) {
			lst.add(prodFact.creationMlProduitEnregistre(aList));
		}

		return lst;
	}

	/**
	 * @param p_Filtrage
	 * @return la liste des produits perime ou presque correspondant au filtrage
	 */
	public MlListeProduits getListeProduitsPerimeAvecFiltrageSurTout(
			String p_Filtrage) {
		MlListeProduits lst = new MlListeProduits();
		// String requete = "SELECT"
		// + " id_produits "
		// + "FROM produit_Enregistre "
		// + "where nom_produit LIKE '%"
		// + p_Filtrage
		// + "%' "
		// + "or nom_marque LIKE '%"
		// + p_Filtrage
		// + "%' "
		// + "or nom_souscatergorie LIKE '%"
		// + p_Filtrage
		// +
		// "%' and (IS_PERIME='true' or IS_PRESQUE_PERIME='true')  ORDER BY id_produits";
		// List<ArrayList<String>> lstId = requeteFact.getListeDeChamp(requete);
		// for (ArrayList<String> anId : lstId) {
		// MlProduit prod = new MlProduit(Integer.parseInt(anId.get(0)), ctx);
		// lst.add(prod);
		// }
		//

		List<ArrayList<Object>> lstRetour = requeteFact
				.getListeDeChampBis(
						EnTable.PRODUIT_ENREGISTRE,
						EnStructProduitEnregistre.class,
						"nom_produit LIKE '%"
								+ p_Filtrage
								+ "%' "
								+ "or nom_marque LIKE '%"
								+ p_Filtrage
								+ "%' "
								+ "or nom_souscatergorie LIKE '%"
								+ p_Filtrage
								+ "%' and (IS_PERIME='true' or IS_PRESQUE_PERIME='true')  ORDER BY id_produits");

		MlProduitEnregistreFactory prodFact = new MlProduitEnregistreFactory();
		for (ArrayList<Object> aList : lstRetour) {
			lst.add(prodFact.creationMlProduitEnregistre(aList));
		}

		return lst;
	}

	/**
	 * @param p_idProduit
	 * @return true ou false
	 */
	public boolean deleteProduit(int p_idProduit) {
		String whereClause = "id_produits=?";
		String[] WhereArgs = new String[] { "" + p_idProduit };

		int nbChampEfface = requeteFact.deleteTable(EnTable.PRODUIT_ENREGISTRE,
				whereClause, WhereArgs);
		if (nbChampEfface != 1) {
			return false;
		}
		return true;

	}

	/**
	 * 
	 */
	public void deleteTable() {
		requeteFact.deleteTable(EnTable.PRODUIT_ENREGISTRE, "1", null);
	}
}
