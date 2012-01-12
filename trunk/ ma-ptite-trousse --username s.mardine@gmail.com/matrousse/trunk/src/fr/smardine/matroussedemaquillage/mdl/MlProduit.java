package fr.smardine.matroussedemaquillage.mdl;

import java.sql.Date;
import java.util.ArrayList;

import fr.smardine.matroussedemaquillage.base.accesTable.AccesTableProduitEnregistre;
import fr.smardine.matroussedemaquillage.variableglobale.EnCategorie;

/**
 * Classe gerant les produits en base
 * @author smardine
 */
public class MlProduit {
	private int idProduit;
	private String nomProduit;

	private Date dateAchat;
	private Date datePeremption;
	private int nbJourDureeVie;
	private EnCategorie categorie;

	private String marque;
	private String teinte;
	private boolean isPerime;
	private boolean isPresquePerime;
	private int nbJourAvantPeremp;
	private String nomSousCat;
	private String nomCat;
	private int dureeVie;
	private long datePeremMilli;

	/**
	 * 
	 */
	public MlProduit() {

	}

	/**
	 * Constructeur permettant la valorisation complete d'un MlProduit
	 * @param p_idProduit
	 * @param p_accesTableProduitEnregistre
	 */
	public MlProduit(int p_idProduit,
			AccesTableProduitEnregistre p_accesTableProduitEnregistre) {
		this.idProduit = p_idProduit;
		ArrayList<String> listProduit = p_accesTableProduitEnregistre
				.getTrousseComplete(idProduit);
		this.nomProduit = listProduit.get(0);
		this.nomSousCat = listProduit.get(1);
		this.nomCat = listProduit.get(2);
		this.teinte = listProduit.get(3);
		this.dureeVie = Integer.parseInt(listProduit.get(4));
		this.datePeremption = Date.valueOf(listProduit.get(5));
		this.dateAchat = Date.valueOf(listProduit.get(6));
		this.marque = listProduit.get(7);
		this.datePeremMilli = Long.parseLong(listProduit.get(8));
		this.isPerime = Boolean.getBoolean(listProduit.get(9));
		this.isPresquePerime = Boolean.getBoolean(listProduit.get(10));
		this.nbJourAvantPeremp = Integer.parseInt(listProduit.get(11));

	}

	/**
	 * @return l'id du produit
	 */
	public int getIdProduit() {
		return idProduit;
	}

	/**
	 * @param p_idProduit
	 */
	public void setIdProduit(int p_idProduit) {
		idProduit = p_idProduit;
	}

	/**
	 * @return le nom du produit
	 */
	public String getNomProduit() {
		return nomProduit;
	}

	/**
	 * @param p_nomProduit
	 */
	public void setNomProduit(String p_nomProduit) {
		nomProduit = p_nomProduit;
	}

	/**
	 * @return la date d'achat
	 */
	public Date getDateAchat() {
		return dateAchat;
	}

	/**
	 * @param p_dateAchat
	 */
	public void setDateAchat(Date p_dateAchat) {
		dateAchat = p_dateAchat;
	}

	/**
	 * @return la date de peremption
	 */
	public Date getDatePeremption() {
		return datePeremption;
	}

	/**
	 * @param p_datePeremption
	 */
	public void setDatePeremption(Date p_datePeremption) {
		datePeremption = p_datePeremption;
	}

	/**
	 * @return le nb de jour de durree de vie
	 */
	public int getNbJourDureeVie() {
		return nbJourDureeVie;
	}

	/**
	 * @param p_nbJourDureeVie
	 */
	public void setNbJourDureeVie(int p_nbJourDureeVie) {
		nbJourDureeVie = p_nbJourDureeVie;
	}

	/**
	 * @return la categorie du produit
	 */
	public EnCategorie getCategorie() {
		return categorie;
	}

	/**
	 * @param p_categorie
	 */
	public void setCategorie(EnCategorie p_categorie) {
		categorie = p_categorie;
	}

	/**
	 * @return la marque
	 */
	public String getMarque() {
		return marque;
	}

	/**
	 * @param p_marque
	 */
	public void setMarque(String p_marque) {
		marque = p_marque;
	}

	/**
	 * @return la teinte
	 */
	public String getTeinte() {
		return teinte;
	}

	/**
	 * @param p_teinte
	 */
	public void setTeinte(String p_teinte) {
		teinte = p_teinte;
	}

	/**
	 * @return le produit est il perime?
	 */
	public boolean isPerime() {
		return isPerime;
	}

	/**
	 * @param p_isPerime
	 */
	public void setPerime(boolean p_isPerime) {
		isPerime = p_isPerime;
	}

	/**
	 * @return le produit sera t il bientot perime
	 */
	public boolean isPresquePerime() {
		return isPresquePerime;
	}

	/**
	 * @param p_isPresquePerime
	 */
	public void setPresquePerime(boolean p_isPresquePerime) {
		isPresquePerime = p_isPresquePerime;
	}

	/**
	 * @return le nombre de jour avant que le produit soit perimé
	 */
	public int getNbJourAvantPeremp() {
		return nbJourAvantPeremp;
	}

	/**
	 * @param p_nbJourAvantPeremp
	 */
	public void setNbJourAvantPeremp(int p_nbJourAvantPeremp) {
		nbJourAvantPeremp = p_nbJourAvantPeremp;
	}

	/**
	 * @return le nom de la sous categorie
	 */
	public String getNomSousCat() {
		return nomSousCat;
	}

	/**
	 * @param p_nomSousCat
	 */
	public void setNomSousCat(String p_nomSousCat) {
		nomSousCat = p_nomSousCat;
	}

	/**
	 * @return le nom de la categorie
	 */
	public String getNomCat() {
		return nomCat;
	}

	/**
	 * @param p_nomCat
	 */
	public void setNomCat(String p_nomCat) {
		nomCat = p_nomCat;
	}

	/**
	 * @return la duree de vie du produit
	 */
	public int getDureeVie() {
		return dureeVie;
	}

	/**
	 * @param p_dureeVie
	 */
	public void setDureeVie(int p_dureeVie) {
		dureeVie = p_dureeVie;
	}

	/**
	 * @return la date de peremption en milliseconde
	 */
	public long getDatePeremMilli() {
		return datePeremMilli;
	}

	/**
	 * @param p_datePeremMilli
	 */
	public void setDatePeremMilli(long p_datePeremMilli) {
		datePeremMilli = p_datePeremMilli;
	}

}
