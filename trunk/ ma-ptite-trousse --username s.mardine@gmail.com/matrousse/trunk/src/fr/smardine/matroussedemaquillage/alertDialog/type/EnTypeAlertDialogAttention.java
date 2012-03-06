package fr.smardine.matroussedemaquillage.alertDialog.type;

import fr.smardine.matroussedemaquillage.alertDialog.IAlertDialogClickListener;
import fr.smardine.matroussedemaquillage.alertDialog.clickListener.AlertDialogAttentionClickListener;

public enum EnTypeAlertDialogAttention {

	PLUSIEUR_CAT("Attention", "Vous avez s�l�ctionn� plus d'une categorie \n"
			+ "Veuillez n'en choisir qu'une.", "Ok",
			AlertDialogAttentionClickListener.class), //
	AUCUNE_CAT("Attention",
			"Vous n'avez rentr� aucune marque \nMerci d'en saisir une", "Ok",
			null), //
	AUCUNE_MARQUE("Attention",
			"Vous n'avez rentr� aucune marque \nMerci d'en saisir une", "Ok",
			null); //

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
