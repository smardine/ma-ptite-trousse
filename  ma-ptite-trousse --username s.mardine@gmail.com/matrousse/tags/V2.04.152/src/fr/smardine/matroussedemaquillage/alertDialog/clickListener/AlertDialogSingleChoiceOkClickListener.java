package fr.smardine.matroussedemaquillage.alertDialog.clickListener;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import fr.smardine.matroussedemaquillage.Main;
import fr.smardine.matroussedemaquillage.alertDialog.IAlertDialogClickListener;
import fr.smardine.matroussedemaquillage.alertDialog.type.EnTypeAlertDialogSingleChoice;
import fr.smardine.matroussedemaquillage.base.accesTable.AccesTableTrousseProduits;
import fr.smardine.matroussedemaquillage.remplir.choix_produit_a_duppliquer;
import fr.smardine.matroussedemaquillage.remplir.formulaire_entree_page1bis;
import fr.smardine.matroussedemaquillage.variableglobale.ActivityParam;

public class AlertDialogSingleChoiceOkClickListener implements
		IAlertDialogClickListener, OnClickListener {

	private final Context ctx;
	private final EnTypeAlertDialogSingleChoice type;
	private final AlertDialogSingleChoiceItemClickListener itemClickListener;
	private Intent intent;

	public AlertDialogSingleChoiceOkClickListener(Context p_ctx,
			EnTypeAlertDialogSingleChoice p_type,
			AlertDialogSingleChoiceItemClickListener p_singleChoiceClickListener) {
		this.ctx = p_ctx;
		this.type = p_type;
		this.itemClickListener = p_singleChoiceClickListener;
	}

	@Override
	public void onClick(DialogInterface p_arg0, int p_arg1) {
		switch (type) {
			case FINIOUPAS:
				AccesTableTrousseProduits accesTrousse = new AccesTableTrousseProduits(
						ctx);
				accesTrousse.reinitProduitChoisi();
				if (itemClickListener.isNouveauProduit()) {
					intent = new Intent(ctx, formulaire_entree_page1bis.class);

				} else if (itemClickListener.isDuppliquer()) {
					intent = new Intent(ctx, choix_produit_a_duppliquer.class);

				} else if (itemClickListener.isAcceuil()) {
					intent = new Intent(ctx, Main.class);

				}
				intent.putExtra(ActivityParam.LaunchFromPageRecap, true);
				ctx.startActivity(intent);

				break;
		}

	}

	public Intent getIntent() {
		return intent;
	}

}
