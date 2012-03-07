package fr.smardine.matroussedemaquillage.alertDialog.clickListener;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import fr.smardine.matroussedemaquillage.alertDialog.IAlertDialogClickListener;
import fr.smardine.matroussedemaquillage.alertDialog.type.EnTypeAlertDialogChoixCat;
import fr.smardine.matroussedemaquillage.mdl.cat.EnCategorie;
import fr.smardine.matroussedemaquillage.mdl.cat.EnCategorieAutres;
import fr.smardine.matroussedemaquillage.mdl.cat.EnCategorieLevre;
import fr.smardine.matroussedemaquillage.mdl.cat.EnCategorieVisage;
import fr.smardine.matroussedemaquillage.mdl.cat.EnCategorieYeux;
import fr.smardine.matroussedemaquillage.mdl.cat.EnTypeCategorie;

/**
 * Gestion des evenements lors du click sur un item pour les dialogBox
 * permettant de choisir une categorie d'un type defini
 * @author smardine
 */
public class AlertDialogSingleChoiceItemClickListener implements
		IAlertDialogClickListener, OnClickListener {
	private final EnTypeAlertDialogChoixCat type;
	private EnCategorie sousCategorieChoisie;
	private final EnTypeCategorie categorieMere;

	/**
	 * Constructeur
	 * @param p_type - le type de catégorie traitée par la dialogBox
	 */
	public AlertDialogSingleChoiceItemClickListener(
			EnTypeAlertDialogChoixCat p_type) {
		this.type = p_type;
		this.categorieMere = EnTypeCategorie
				.getEnumFromValue(p_type.getTitre());
	}

	@Override
	public void onClick(DialogInterface p_dialog, int p_item) {
		switch (type) {
			case VISAGE:
				traiteVisage(p_item);
				break;
			case YEUX:
				traiteYeux(p_item);
				break;
			case LEVRE:
				traiteLevre(p_item);
				break;
			case AUTRE:
				traiteAutre(p_item);
				break;

		}

	}

	/**
	 * S'occupe de traiter les autres categories en fonction de l'item cliqué
	 * @param p_item l'index de l'item cliqué
	 */
	private void traiteAutre(int p_item) {
		EnCategorieAutres catAutre = EnCategorieAutres
				.getCategorieFromCode(p_item);
		switch (catAutre) {
			case Pinceaux:
				setSousCategorieChoisie(EnCategorieAutres.Pinceaux);
				break;
			case VernisAongles:
				setSousCategorieChoisie(EnCategorieAutres.VernisAongles);
				break;
		}

	}

	/**
	 * S'occupe de traiter les categories concernant les levres en fonction de
	 * l'item cliqué
	 * @param p_item l'index de l'item cliqué
	 */
	private void traiteLevre(int p_item) {
		EnCategorieLevre catLevre = EnCategorieLevre
				.getCategorieFromCode(p_item);
		switch (catLevre) {
			case Crayons_contour:
				setSousCategorieChoisie(EnCategorieLevre.Crayons_contour);
				break;
			case RougesAlevres:
				setSousCategorieChoisie(EnCategorieLevre.RougesAlevres);
				break;
		}

	}

	/**
	 * S'occupe de traiter les categories concernant les yeux en fonction de
	 * l'item cliqué
	 * @param p_item l'index de l'item cliqué
	 */
	private void traiteYeux(int p_item) {
		EnCategorieYeux catYeux = EnCategorieYeux.getCategorieFromCode(p_item);
		switch (catYeux) {
			case Bases:
				setSousCategorieChoisie(EnCategorieYeux.Bases);
				break;
			case Crayons_Eyeliners:
				setSousCategorieChoisie(EnCategorieYeux.Crayons_Eyeliners);
				break;
			case Fards:
				setSousCategorieChoisie(EnCategorieYeux.Fards);
				break;
			case Mascaras:
				setSousCategorieChoisie(EnCategorieYeux.Mascaras);
		}

	}

	/**
	 * S'occupe de traiter les categories concernant le visage en fonction de
	 * l'item cliqué
	 * @param p_item l'index de l'item cliqué
	 */
	private void traiteVisage(int p_item) {
		EnCategorieVisage catVisage = EnCategorieVisage
				.getCategorieFromCode(p_item);
		switch (catVisage) {
			case FONDS_DE_TEINTS:
				setSousCategorieChoisie(EnCategorieVisage.FONDS_DE_TEINTS);
				break;
			case Correcteurs_Bases:
				setSousCategorieChoisie(EnCategorieVisage.Correcteurs_Bases);
				break;
			case Blush:
				setSousCategorieChoisie(EnCategorieVisage.Blush);
				break;
			case Poudres:
				setSousCategorieChoisie(EnCategorieVisage.Poudres);
				break;
		}

	}

	/**
	 * @param sousCategorieChoisie the sousCategorieChoisie to set
	 */
	private void setSousCategorieChoisie(EnCategorie p_sousCategorieChoisie) {
		this.sousCategorieChoisie = p_sousCategorieChoisie;
	}

	/**
	 * @return the sousCategorieChoisie
	 */
	public EnCategorie getSousCategorieChoisie() {
		return sousCategorieChoisie;
	}

	/**
	 * @return the categorieMere
	 */
	public EnTypeCategorie getCategorieMere() {
		return categorieMere;
	}
}
