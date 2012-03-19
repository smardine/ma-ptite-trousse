package helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * permet de gerer les calcul de date et de declarer si un produit est perimé ou
 * non.
 * @author sims
 */
public class DateHelper {
	/**
	 * le produit est il perimé?
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
		// // definition de la date a laquelle le produit est perimé
		// Calendar cDatePeremption = cDateAchat;
		// // on ajoute un nb de mois a la date d'achat pour connaitre la date
		// de
		// // peremtion.
		// cDatePeremption.add(Calendar.MONTH, p_durreeVie);
		// Date dtPermp = cDatePeremption.getTime();
		// on compare la date du jour a la date de peremption,
		// si la date de peremetpion est avant la date du jour,
		// alors le produit est perimé.

		return getDatePeremption(p_dateAchat, p_durreeVie).before(rightNow);
		// return isPerime;
	}

	/**
	 * le produit est il consideré comme perimé
	 * @param p_dateAchat
	 * @param p_durreeVie
	 * @param p_nbJourAvantPeremption int, indiqué dans les parametres par
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

		// definition de la date a laquelle le produit est perimé
		Calendar cDatePeremption = cDateAchat;
		// on ajoute un nb de mois a la date d'achat pour connaitre la date de
		// peremtion.
		cDatePeremption.add(Calendar.MONTH, p_durreeVie);
		// on retranche le nb de jour passé en parametres pour definir un
		// produit "presque perimé"
		cDatePeremption.add(Calendar.DATE, -p_nbJourAvantPeremption);
		Date dtPeremp = cDatePeremption.getTime();
		// on compare la date du jour a la date de peremption,
		// si la date de peremetpion est avant la date du jour,
		// alors le produit est perimé.

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
		cDateAchat.set(Calendar.YEAR, annee + 1900);
		// les mois sont en base 0 (JANVIER=0, FEVRIER=1.....DECEMBRE=11)
		cDateAchat.set(Calendar.MONTH, mois + 1);
		cDateAchat.set(Calendar.DAY_OF_MONTH, jourAchat);

		// definition de la date a laquelle le produit est perimé
		Calendar cDatePeremption = cDateAchat;
		// on ajoute un nb de mois a la date d'achat pour connaitre la date de
		// peremtion.
		cDatePeremption.add(Calendar.MONTH, p_durreeVie);
		// Date dtPermp = cDatePeremption.getTime();
		return cDatePeremption.getTime();
	}

	/**
	 * @param p_date
	 * @return chaine de caractere au bon format pour la bdd
	 */
	public static String getDateforDatabase(Date p_date) {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		// date de peremtion au format jj/mm/aaaa
		return dateFormat.format(p_date);
	}

	/**
	 * @param p_dateDatabase
	 * @return obtenir une Java.Util.Date au format jjMMyyyy
	 */
	public static Date getDateFromDatabase(String p_dateDatabase) {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		try {
			return dateFormat.parse(p_dateDatabase);
		} catch (ParseException e) {

			e.printStackTrace();
		}
		return null;
	}

}
