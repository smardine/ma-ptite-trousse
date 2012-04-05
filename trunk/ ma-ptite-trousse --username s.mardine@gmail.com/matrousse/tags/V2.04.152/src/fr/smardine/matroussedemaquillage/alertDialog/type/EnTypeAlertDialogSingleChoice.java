package fr.smardine.matroussedemaquillage.alertDialog.type;

import fr.smardine.matroussedemaquillage.alertDialog.IAlertDialogClickListener;
import fr.smardine.matroussedemaquillage.alertDialog.clickListener.buttonClick.AlertDialogChoixCatClickListener;

public enum EnTypeAlertDialogSingleChoice {

	FINIOUPAS("Que voulez vous faire?", "Ok", -1,
			AlertDialogChoixCatClickListener.class);

	private String titre;
	private String txtOkButton;
	private Class<? extends IAlertDialogClickListener> okClickListener;
	private int idxSelected;

	EnTypeAlertDialogSingleChoice(String p_titre, String p_textButtonOk,
			int p_idxSelected,

			Class<? extends IAlertDialogClickListener> p_clickListener) {
		this.titre = p_titre;
		this.txtOkButton = p_textButtonOk;
		this.idxSelected = p_idxSelected;
		this.okClickListener = p_clickListener;
	}

	public String getTitre() {
		return titre;
	}

	public String getTxtOkButton() {
		return txtOkButton;
	}

	public Class<? extends IAlertDialogClickListener> getOkClickListener() {
		return okClickListener;
	}

	/**
	 * @return the idxSelected
	 */
	public int getIdxSelected() {
		return idxSelected;
	}

	public CharSequence[] getItems() {

		switch (this) {
			case FINIOUPAS:
				CharSequence[] items = { "Ajouter un produit",
						"Dupliquer un produit", "Revenir à la page d'accueil" };
				return items;
		}
		return null;
	}

}
