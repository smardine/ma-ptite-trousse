package fr.smardine.matroussedemaquillage.alertDialog.type;

import fr.smardine.matroussedemaquillage.alertDialog.IAlertDialogClickListener;
import fr.smardine.matroussedemaquillage.alertDialog.clickListener.buttonClick.AlertDialogAttentionClickListener;

/**
 * Definition des différente DialogBox de type "Attention"
 * @author smardine
 */
public enum EnTypeAlertDialogAttention {

	PLUSIEUR_CAT("Attention", "Vous avez séléctionné plus d'une categorie \n"
			+ "Veuillez n'en choisir qu'une.", "Ok",
			AlertDialogAttentionClickListener.class), //
	AUCUNE_CAT("Attention",
			"Vous n'avez rentré aucune catégorie \nMerci d'en saisir une",
			"Ok", null), //
	AUCUNE_MARQUE("Attention",
			"Vous n'avez rentré aucune marque \nMerci d'en saisir une", "Ok",
			null), //
	MANQUE_INFO("Attention",
			"Vous avez oublié de rentrer certaines informations.", "Ok", null), //
	AUCUN_PRODUIT_ENREGISTRE(
			"Pour information",
			"Aucun produit n'est actuellement enregistré dans Ma p'tite trousse",
			"Ok", null);

	private String titre;
	private String message;
	private String txtOkBtton;
	private Class<? extends IAlertDialogClickListener> clickListenerClass;

	EnTypeAlertDialogAttention(String p_titre, String p_message,
			String p_textOkButton,
			Class<? extends IAlertDialogClickListener> p_class) {
		this.titre = p_titre;
		this.message = p_message;
		this.txtOkBtton = p_textOkButton;
		this.clickListenerClass = p_class;
	}

	public String getTitre() {
		return titre;
	}

	public String getMessage() {
		return message;
	}

	public String getTxtOkBtton() {
		return txtOkBtton;
	}

	public Class<? extends IAlertDialogClickListener> getClickListenerClass() {
		return clickListenerClass;
	}

}
