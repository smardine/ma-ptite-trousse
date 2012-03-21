package fr.smardine.matroussedemaquillage.mdl;

import java.io.Serializable;
import java.util.ArrayList;

import android.content.Context;
import fr.smardine.matroussedemaquillage.base.accesTable.AccesTableNotes;

/**
 * @author smardine
 */
public class MlNote implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7340644404187848866L;
	private int idNote;
	private String message;
	private String titre;

	/**
	 * @param p_idNote
	 * @param p_ctx
	 */
	public MlNote(int p_idNote, Context p_ctx) {
		this.idNote = p_idNote;
		AccesTableNotes accesNote = new AccesTableNotes(p_ctx);
		ArrayList<String> defProduit = accesNote.getDefNoteById(idNote);
		this.message = defProduit.get(0);
		this.titre = defProduit.get(1);
	}

	/**
	 * 
	 */
	public MlNote() {

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

	/**
	 * @param p_idNote
	 */
	public void setIdNote(int p_idNote) {
		idNote = p_idNote;
	}

	/**
	 * @param p_message
	 */
	public void setMessage(String p_message) {
		message = p_message;
	}

	/**
	 * @param p_titre
	 */
	public void setTitre(String p_titre) {
		titre = p_titre;
	}

}
