package fr.smardine.matroussedemaquillage.mdl;

import java.util.ArrayList;

import fr.smardine.matroussedemaquillage.base.accesTable.AccesTableNotes;

/**
 * @author smardine
 */
public class MlNote {

	private final int idNote;
	private final String message;
	private final String titre;

	/**
	 * @param p_idNote
	 * @param p_accesTableNote
	 */
	public MlNote(int p_idNote, AccesTableNotes p_accesTableNote) {
		this.idNote = p_idNote;
		ArrayList<String> defProduit = p_accesTableNote.getDefNoteById(idNote);
		this.message = defProduit.get(0);
		this.titre = defProduit.get(1);
	}

	/**
	 * @return l'id de la note
	 */
	public int getIdNote() {
		return idNote;
	}

	/**
	 * @return le contenu du message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return le titre de la note
	 */
	public String getTitre() {
		return titre;
	}

}
