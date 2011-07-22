package helper;

import java.util.Calendar;
import java.util.Date;
/**
 * permet de gerer les calcul de date et de declarer si un produit est perimé ou non.
 * @author sims
 *
 */
public class DateHelper {
	/**
	 * le produit est il perimé?
	 * @param dateAchat String
	 * @param DureedeVie String
	 * @return true si oui, false si non
	 */
	@SuppressWarnings("unused")
	public static boolean isProduitPerime (String dateAchat,String DureedeVie){
		
		int DureeVie = 0;
		DureeVie = Integer.valueOf(DureedeVie);

		String tabAchat[] = dateAchat.split("-");
		int jourAchat = Integer.parseInt(tabAchat[0]);
		int mois = Integer.parseInt(tabAchat[1]);
		int annee = Integer.parseInt(tabAchat[2]);
		
		//definition de la date d'achat au format "Calendar"
		Calendar cDateAchat =Calendar.getInstance();
		Date rightNow = cDateAchat.getTime();
		cDateAchat.set(Calendar.YEAR,annee);
		cDateAchat.set(Calendar.MONTH,mois-1);//les mois sont en base 0 (JANVIER=0, FEVRIER=1.....DECEMBRE=11)
		cDateAchat.set(Calendar.DAY_OF_MONTH,jourAchat);
		
		
		Date dtAchat = cDateAchat.getTime();
		
		
		//definition de la date a laquelle le produit est perimé
		Calendar cDatePeremption = cDateAchat;
		cDatePeremption.add(Calendar.MONTH,DureeVie);//on ajoute un nb de mois a la date d'achat pour connaitre la date de peremtion.
		Date dtPermp = cDatePeremption.getTime();
		//on compare la date du jour a la date de peremption,
		//si la date de peremetpion est avant la date du jour, 
		//alors le produit est perimé.
		
	
		boolean isPerime = dtPermp.before(rightNow);
		return isPerime;
	}
	
	/**
	 * le produit est il consideré comme perimé
	 * @param dateAchat
	 * @param DureedeVie
	 * @param nbJourAvantPeremption int, indiqué dans les parametres par l'utilisateur (valeur par defaut = 30 jours).
	 * @return true si oui, false si non
	 */
	
	public static boolean isProduitPresquePerime (String dateAchat, String DureedeVie,int nbJourAvantPeremption){
		int DureeVie = 0;
		DureeVie = Integer.valueOf(DureedeVie);

		String tabAchat[] = dateAchat.split("-");
		int jourAchat = Integer.parseInt(tabAchat[0]);
		int mois = Integer.parseInt(tabAchat[1]);
		int annee = Integer.parseInt(tabAchat[2]);
		
		//definition de la date d'achat au format "Calendar"
		Calendar cDateAchat =Calendar.getInstance();
		Date rightNow = cDateAchat.getTime();
		cDateAchat.set(Calendar.YEAR,annee);
		cDateAchat.set(Calendar.MONTH,mois-1);//les mois sont en base 0 (JANVIER=0, FEVRIER=1.....DECEMBRE=11)
		cDateAchat.set(Calendar.DAY_OF_MONTH,jourAchat);
		
		//definition de la date a laquelle le produit est perimé
		Calendar cDatePeremption = cDateAchat;
		cDatePeremption.add(Calendar.MONTH,DureeVie);//on ajoute un nb de mois a la date d'achat pour connaitre la date de peremtion.
		//on retranche le nb de jour passé en parametres pour definir un produit "presque perimé"
		cDatePeremption.add(Calendar.DATE, -nbJourAvantPeremption);
		Date dtPeremp = cDatePeremption.getTime();
		//on compare la date du jour a la date de peremption,
		//si la date de peremetpion est avant la date du jour, 
		//alors le produit est perimé.
		
		boolean isPerime = dtPeremp.before(rightNow);
		return isPerime;
	}

	public static Date getDatePeremption(String dateAchat, String DureedeVie) {
		int DureeVie = 0;
		DureeVie = Integer.valueOf(DureedeVie);

		String tabAchat[] = dateAchat.split("-");
		int jourAchat = Integer.parseInt(tabAchat[0]);
		int mois = Integer.parseInt(tabAchat[1]);
		int annee = Integer.parseInt(tabAchat[2]);
		
		//definition de la date d'achat au format "Calendar"
		Calendar cDateAchat =Calendar.getInstance();
		cDateAchat.set(Calendar.YEAR,annee);
		cDateAchat.set(Calendar.MONTH,mois-1);//les mois sont en base 0 (JANVIER=0, FEVRIER=1.....DECEMBRE=11)
		cDateAchat.set(Calendar.DAY_OF_MONTH,jourAchat);
				
		//definition de la date a laquelle le produit est perimé
		Calendar cDatePeremption = cDateAchat;
		cDatePeremption.add(Calendar.MONTH,DureeVie);//on ajoute un nb de mois a la date d'achat pour connaitre la date de peremtion.
		Date dtPermp = cDatePeremption.getTime();
		return dtPermp;
	}

}
