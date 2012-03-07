package fr.smardine.matroussedemaquillage.alertDialog.clickListener;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import fr.smardine.matroussedemaquillage.alertDialog.IAlertDialogClickListener;
import fr.smardine.matroussedemaquillage.alertDialog.type.EnTypeAlertDialogOuiNon;

/**
 * Gestion des evenements lors du click sur le bouton Oui sur une boite de
 * dialogue "Oui/Non"
 * @author smardine
 */
public class AlertDialogOuiNonClickListener implements
		IAlertDialogClickListener, OnClickListener {

	private final EnTypeAlertDialogOuiNon typeClick;

	/**
	 * Constructeur
	 * @param p_type - le type d'alertDialog concerné
	 */
	public AlertDialogOuiNonClickListener(EnTypeAlertDialogOuiNon p_type) {
		this.typeClick = p_type;
	}

	@Override
	public void onClick(DialogInterface p_arg0, int p_arg1) {
		switch (typeClick) {
			case NOUVELLE_MARQUE:

				break;
		}

	}

}
