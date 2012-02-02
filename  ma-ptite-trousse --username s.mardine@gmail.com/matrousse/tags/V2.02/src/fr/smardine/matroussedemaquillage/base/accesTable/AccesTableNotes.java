package fr.smardine.matroussedemaquillage.base.accesTable;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import fr.smardine.matroussedemaquillage.base.RequeteFactory;
import fr.smardine.matroussedemaquillage.base.structure.EnStructNotes;
import fr.smardine.matroussedemaquillage.base.structure.EnTable;
import fr.smardine.matroussedemaquillage.mdl.MlListeNote;
import fr.smardine.matroussedemaquillage.mdl.MlNote;
import fr.smardine.matroussedemaquillage.mdl.MlProduit;

/**
 * @author smardine Acces a la table des Notes enregistré en base
 */
public class AccesTableNotes {

	private final RequeteFactory requeteFact;
	private final Context ctx;

	/**
	 * @param p_ctx le contexte
	 */
	public AccesTableNotes(Context p_ctx) {
		this.ctx = p_ctx;
		requeteFact = new RequeteFactory(p_ctx);
	}

	/**
	 * @param p_idNote
	 * @param p_titre
	 * @param p_message
	 */
	public void majTitreEtMessage(String p_idNote, String p_titre,
			String p_message) {
		ContentValues modifiedValues = new ContentValues();
		modifiedValues.put("Titre", p_titre);
		modifiedValues.put("Message", p_message);
		String whereClause = "id_note=?";
		String[] whereArgs = { p_idNote };
		requeteFact.majTable(EnTable.NOTES, modifiedValues, whereClause,
				whereArgs);
	}

	/**
	 * @param p_idNote
	 * @return une liste de tableau de string
	 */
	public ArrayList<String> getDefNoteById(int p_idNote) {
		String requete = "Select " + EnStructNotes.MESSAGE.getNomChamp() + " ,"
				+ EnStructNotes.TITRE.getNomChamp() + //
				" From " + EnTable.NOTES.getNomTable() + //
				" Where " + EnStructNotes.ID.getNomChamp() + "=" + p_idNote;
		return requeteFact.getListeDeChamp(requete).get(0);

	}

	/**
	 * @return une liste de MlNote
	 */
	public MlListeNote getListeNote() {
		MlListeNote listeNote = new MlListeNote();
		String requete = "SELECT " + EnStructNotes.ID.getNomChamp() + " FROM "
				+ EnTable.NOTES.getNomTable() + " ORDER BY "
				+ EnStructNotes.ID.getNomChamp();

		List<ArrayList<String>> lstId = requeteFact.getListeDeChamp(requete);
		for (ArrayList<String> anId : lstId) {
			MlNote n = new MlNote(Integer.parseInt(anId.get(0)), ctx);
			listeNote.add(n);
		}
		return listeNote;
	}

	/**
	 * @param p_produit
	 */
	public void createNewNoteDepuisProduit(MlProduit p_produit) {
		ContentValues values = new ContentValues();
		values.put("Titre", "[Auto] " + p_produit.getNomProduit() + " "
				+ p_produit.getMarque());
		values.put(
				"Message",
				"Produit acheté le: " + p_produit.getDateAchat() + "\n"
						+ "Catégorie du produit: " + p_produit.getNomSousCat()
						+ "\n" + "Numéro de teinte: " + p_produit.getTeinte()
						+ "\n" + "Durée de vie du produit: "
						+ p_produit.getDureeVie() + " mois\n"
						+ "Date de péremption: "
						+ p_produit.getDatePeremption() + "\n");

		requeteFact.insertDansTable(EnTable.NOTES, values);

	}

	/**
	 * @param p_titre
	 * @param p_message
	 * @return true ou false
	 */
	public boolean createNewNote(String p_titre, String p_message) {
		ContentValues values = new ContentValues();
		values.put("Titre", p_titre);
		values.put("Message", p_message);
		return requeteFact.insertDansTable(EnTable.NOTES, values);
	}

	/**
	 * @param p_idNote
	 * @return true ou false
	 */
	public boolean deleteNote(int p_idNote) {
		String whereClause = "id_note=?";
		String[] WhereArgs = new String[] { "" + p_idNote };

		// objBd.open();
		int nbChampEfface = requeteFact.deleteTable(EnTable.NOTES, whereClause,
				WhereArgs);
		if (nbChampEfface == 1) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 */
	public void deleteTable() {
		requeteFact.deleteTable(EnTable.NOTES, "1", null);

	}
}
