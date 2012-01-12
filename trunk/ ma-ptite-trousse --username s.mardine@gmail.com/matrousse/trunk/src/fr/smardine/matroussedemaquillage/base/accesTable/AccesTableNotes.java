package fr.smardine.matroussedemaquillage.base.accesTable;

import android.content.ContentValues;
import android.content.Context;
import fr.smardine.matroussedemaquillage.base.RequeteFactory;
import fr.smardine.matroussedemaquillage.base.structure.EnTable;

/**
 * @author smardine Acces a la table des Notes enregistré en base
 */
public class AccesTableNotes {

	private final RequeteFactory requeteFact;

	/**
	 * @param p_ctx le contexte
	 */
	public AccesTableNotes(Context p_ctx) {
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
}
