package fr.smardine.matroussedemaquillage.alertDialog.type;

import fr.smardine.matroussedemaquillage.alertDialog.IAlertDialogClickListener;
import fr.smardine.matroussedemaquillage.alertDialog.clickListener.AlertDialogOuiNonClickListener;

/**
 * Definition des dialogBox de type "Oui/Non"
 * @author smardine
 */
public enum EnTypeAlertDialogOuiNon {

	NOUVELLE_MARQUE(
			"Petite vérification",
			"Nouvelle marque\nCette marque est inconnue de \"Ma p'tite trousse\"\nSouhaitez vous la partager avec les autres utilisateurs? (Connexion Edge, 3G ou wifi requise)",
			"Oui", "Non", AlertDialogOuiNonClickListener.class, null);

	private String titre;
	private String message;
	private String txtOkBtton;
	private Class<? extends IAlertDialogClickListener> clickListenerClassOk;
	private Class<? extends IAlertDialogClickListener> clickListenerClassCancel;
	private String txtCancelButton;

	EnTypeAlertDialogOuiNon(String p_titre, String p_message,
			String p_textOkButton, String p_textCancelButton,
			Class<? extends IAlertDialogClickListener> p_classOk,
			Class<? extends IAlertDialogClickListener> p_classCancel) {
		this.titre = p_titre;
		this.message = p_message;
		this.txtOkBtton = p_textOkButton;
		this.txtCancelButton = p_textCancelButton;
		this.clickListenerClassOk = p_classOk;
		this.clickListenerClassCancel = p_classCancel;
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

	public Class<? extends IAlertDialogClickListener> getClickListenerClassOk() {
		return clickListenerClassOk;
	}

	public Class<? extends IAlertDialogClickListener> getClickListenerClassCancel() {
		return clickListenerClassCancel;
	}

	/**
	 * @return the txtCancelButton
	 */
	public String getTxtCancelButton() {
		return txtCancelButton;
	}

}
