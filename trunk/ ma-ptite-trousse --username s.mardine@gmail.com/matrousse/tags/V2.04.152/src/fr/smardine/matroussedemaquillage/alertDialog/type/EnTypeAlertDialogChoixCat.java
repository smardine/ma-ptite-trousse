package fr.smardine.matroussedemaquillage.alertDialog.type;

import fr.smardine.matroussedemaquillage.alertDialog.IAlertDialogClickListener;
import fr.smardine.matroussedemaquillage.alertDialog.clickListener.buttonClick.AlertDialogChoixCatClickListener;

/**
 * Definition des dialogBox de type "ChoixCategorie
 * @author smardine
 */
public enum EnTypeAlertDialogChoixCat {

	VISAGE("Visage", "Choisir", "Annuler",
			AlertDialogChoixCatClickListener.class), //
	YEUX("Yeux", "Choisir", "Annuler", AlertDialogChoixCatClickListener.class), //
	LEVRE("Levres", "Choisir", "Annuler",
			AlertDialogChoixCatClickListener.class), //
	AUTRE("Autre", "Choisir", "Annuler", AlertDialogChoixCatClickListener.class); //

	private String titre;
	private String txtOkBtton;
	private Class<? extends IAlertDialogClickListener> clickListenerClassOk;
	private String txtCancelButton;

	EnTypeAlertDialogChoixCat(String p_titre, String p_textOkButton,
			String p_textCancelButton,
			Class<? extends IAlertDialogClickListener> p_clickListener) {
		this.titre = p_titre;
		this.txtOkBtton = p_textOkButton;
		this.txtCancelButton = p_textCancelButton;
		this.clickListenerClassOk = p_clickListener;
	}

	public String getTitre() {
		return titre;
	}

	public String getTxtOkBtton() {
		return txtOkBtton;
	}

	public Class<? extends IAlertDialogClickListener> getClickListenerClassOk() {
		return clickListenerClassOk;
	}

	public String getTxtCancelButton() {
		return txtCancelButton;
	}

}
