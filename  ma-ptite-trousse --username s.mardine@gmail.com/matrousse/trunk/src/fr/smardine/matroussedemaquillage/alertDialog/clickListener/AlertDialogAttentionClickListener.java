package fr.smardine.matroussedemaquillage.alertDialog.clickListener;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import fr.smardine.matroussedemaquillage.alertDialog.IAlertDialogClickListener;
import fr.smardine.matroussedemaquillage.alertDialog.type.EnTypeAlertDialogAttention;
import fr.smardine.matroussedemaquillage.base.accesTable.AccesTableTrousseProduits;

public class AlertDialogAttentionClickListener implements
		IAlertDialogClickListener, OnClickListener {

	private final Context ctx;
	private final EnTypeAlertDialogAttention typeClick;

	public AlertDialogAttentionClickListener(Context p_ctx,
			EnTypeAlertDialogAttention p_type) {
		this.ctx = p_ctx;
		this.typeClick = p_type;
	}

	@Override
	public void onClick(DialogInterface p_arg0, int p_arg1) {
		switch (typeClick) {
			case AUCUNE_CAT:
				break;
			case PLUSIEUR_CAT:
				AccesTableTrousseProduits accesTrousse = new AccesTableTrousseProduits(
						ctx);
				accesTrousse.reinitProduitChoisi();
				break;
			case AUCUNE_MARQUE:
				break;
		}

	}

}
