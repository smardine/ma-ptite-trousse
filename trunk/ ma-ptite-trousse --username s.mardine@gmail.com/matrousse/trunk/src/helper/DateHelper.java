package helper;

import java.util.Calendar;
import java.util.Date;

/**
 * permet de gerer les calcul de date et de declarer si un produit est perim� ou
 * non.
 * @author sims
 */
public class DateHelper {
	/**
	 * le produit est il perim�?
	 * @param p_dateAchat Date
	 * @param p_durreeVie int (en nombre de mois)
	 * @return true si oui, false si non
	 */

	public static boolean isProduitPerime(Date p_dateAchat, int p_durreeVie) {
		// getDatePeremption(p_dateAchat, p_durreeVie);

		// definition de la date d'achat au format "Calendar"
		Calendar cDateAchat = Calendar.getInstance();
		Date rightNow = cDateAchat.getTime();
		// cDateAchat.set(Calendar.YEAR, annee);
		// cDateAchat.set(Calendar.MONTH, mois - 1);// les mois sont en base 0
		// // (JANVIER=0,
		// // FEVRIER=1.....DECEMBRE=11)
		// cDateAchat.set(Calendar.DAY_OF_MONTH, jourAchat);
		//
		// // Date dtAchat = cDateAchat.getTime();
		//
		// // definition de la date a laquelle le produit est perim�
		// Calendar cDatePeremption = cDateAchat;
		// // on ajoute un nb de mois a la date d'achat pour connaitre la date
		// de
		// // peremtion.
		// cDatePeremption.add(Calendar.MONTH, p_durreeVie);
		// Date dtPermp = cDatePeremption.getTime();
		// on compare la date du jour a la date de peremption,
		// si la date de peremetpion est avant la date du jour,
		// alors le produit est perim�.

		return getDatePeremption(p_dateAchat, p_durreeVie).before(rightNow);
		// return isPerime;
	}

	/**
	 * le produit est il consider� comme perim�
	 * @param p_dateAchat
	 * @param p_durreeVie
	 * @param p_nbJourAvantPeremption int, indiqu� dans les parametres par
	 *            l'utilisateur (valeur par defaut = 30 jours).
	 * @return true si oui, false si non
	 */

	public static boolean isProduitPresquePerime(Date p_dateAchat,
			int p_durreeVie, int p_nbJourAvantPeremption) {

		// definition de la date d'achat au format "Calendar"
		Calendar cDateAchat = Calendar.getInstance();
		Date rightNow = cDateAchat.getTime();
		cDateAchat.set(Calendar.YEAR, p_dateAchat.getYear());
		// les mois sont en base 0 (JANVIER=0, FEVRIER=1.....DECEMBRE=11)
		cDateAchat.set(Calendar.MONTH, p_dateAchat.getMonth() - 1);
		cDateAchat.set(Calendar.DAY_OF_MONTH, p_dateAchat.getDay());

		// definition de la date a laquelle le produit est perim�
		Calendar cDatePeremption = cDateAchat;
		// on ajoute un nb de mois a la date d'achat pour connaitre la date de
		// peremtion.
		cDatePeremption.add(Calendar.MONTH, p_durreeVie);
		// on retranche le nb de jour pass� en parametres pour definir un
		// produit "presque perim�"
		cDatePeremption.add(Calendar.DATE, -p_nbJourAvantPeremption);
		Date dtPeremp = cDatePeremption.getTime();
		// on compare la date du jour a la date de peremption,
		// si la date de peremetpion est avant la date du jour,
		// alors le produit est perim�.

		return dtPeremp.before(rightNow);
	}

	/**
	 * @param p_dateAchat
	 * @param p_durreeVie (en nb de mois)
	 * @return la date de peremption d'un produit a partir de sa date d'achat et
	 *         d'un nombre de mois
	 */
	public static Date getDatePeremption(Date p_dateAchat, int p_durreeVie) {
		int jourAchat = p_dateAchat.getDay();
		int mois = p_dateAchat.getMonth();
		int annee = p_dateAchat.getYear();

		// definition de la date d'achat au format "Calendar"
		Calendar cDateAchat = Calendar.getInstance();
		cDateAchat.set(Calendar.YEAR, annee);
		// les mois sont en base 0 (JANVIER=0, FEVRIER=1.....DECEMBRE=11)
		cDateAchat.set(Calendar.MONTH, mois - 1);
		cDateAchat.set(Calendar.DAY_OF_MONTH, jourAchat);

		// definition de la date a laquelle le produit est perim�
		Calendar cDatePeremption = cDateAchat;
		// on ajoute un nb de mois a la date d'achat pour connaitre la date de
		// peremtion.
		cDatePeremption.add(Calendar.MONTH, p_durreeVie);
		// Date dtPermp = cDatePeremption.getTime();
		return cDatePeremption.getTime();
	}

}
