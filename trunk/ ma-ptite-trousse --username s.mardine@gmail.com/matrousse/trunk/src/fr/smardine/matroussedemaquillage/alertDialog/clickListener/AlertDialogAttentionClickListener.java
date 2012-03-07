package fr.smardine.matroussedemaquillage.alertDialog.clickListener;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import fr.smardine.matroussedemaquillage.alertDialog.IAlertDialogClickListener;
import fr.smardine.matroussedemaquillage.alertDialog.type.EnTypeAlertDialogAttention;
import fr.smardine.matroussedemaquillage.base.accesTable.AccesTableTrousseProduits;

/**
 * Gestion des evenements lors du click le bouton Ok les dialogBox permettant
 * d'afficher un message d'avertissement à l'utilisateur
 * @author smardine
 */

public class AlertDialogAttentionClickListener implements
		IAlertDialogClickListener, OnClickListener {

	private final Context ctx;
	private final EnTypeAlertDialogAttention typeClick;

	/**
	 * Constructeur
	 * @param p_ctx - le Context qui a lancé cette dialogBox
	 * @param p_type - le type de dialogBox à traiter
	 */
	public AlertDialogAttentionClickListener(Context p_ctx,
			EnTypeAlertDialogAttention p_type) {
		this.ctx = p_ctx;
		this.typeClick = p_type;
	}

	@Override
	public void onClick(DialogInterface p_arg0, int p_arg1) {
		switch (typeClick) {
			case AUCUNE_CAT:
			case AUCUNE_MARQUE:
				break;
			case PLUSIEUR_CAT:
				AccesTableTrousseProduits accesTrousse = new AccesTableTrousseProduits(
						ctx);
				accesTrousse.reinitProduitChoisi();
				break;

		}

	}

}
