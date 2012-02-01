package helper;

import fr.smardine.matroussedemaquillage.base.structure.EnStructMarque;
import fr.smardine.matroussedemaquillage.base.structure.EnStructNotes;
import fr.smardine.matroussedemaquillage.base.structure.EnStructParam;
import fr.smardine.matroussedemaquillage.base.structure.EnStructProduitEnregistre;
import fr.smardine.matroussedemaquillage.base.structure.EnStructProduits;
import fr.smardine.matroussedemaquillage.base.structure.EnStructTempo;
import fr.smardine.matroussedemaquillage.base.structure.StructureTable;

/**
 * @author smardine
 */
public class EnumHelper {

	/**
	 * @param p_class
	 * @param p_columnName
	 * @return obtenir l'enum correspondant au nom de la colonne
	 */
	public static StructureTable getValueFromName(
			Class<StructureTable> p_class, String p_columnName) {
		for (StructureTable e : EnStructMarque.values()) {
			if (e.getNomChamp().equals(p_columnName)) {
				return e;
			}
		}
		for (StructureTable e : EnStructNotes.values()) {
			if (e.getNomChamp().equals(p_columnName)) {
				return e;
			}
		}
		for (StructureTable e : EnStructParam.values()) {
			if (e.getNomChamp().equals(p_columnName)) {
				return e;
			}
		}
		for (StructureTable e : EnStructProduits.values()) {
			if (e.getNomChamp().equals(p_columnName)) {
				return e;
			}
		}
		for (StructureTable e : EnStructProduitEnregistre.values()) {
			if (e.getNomChamp().equals(p_columnName)) {
				return e;
			}
		}
		for (StructureTable e : EnStructTempo.values()) {
			if (e.getNomChamp().equals(p_columnName)) {
				return e;
			}
		}
		return null;
	}

}
