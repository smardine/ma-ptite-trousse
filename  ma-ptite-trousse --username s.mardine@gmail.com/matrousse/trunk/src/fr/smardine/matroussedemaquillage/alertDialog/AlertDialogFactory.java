package fr.smardine.matroussedemaquillage.alertDialog;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import fr.smardine.matroussedemaquillage.R;
import fr.smardine.matroussedemaquillage.alertDialog.clickListener.AlertDialogAttentionClickListener;
import fr.smardine.matroussedemaquillage.alertDialog.clickListener.AlertDialogOuiNonClickListener;
import fr.smardine.matroussedemaquillage.alertDialog.type.EnTypeAlertDialogAttention;
import fr.smardine.matroussedemaquillage.alertDialog.type.EnTypeAlertDialogOuiNon;

public class AlertDialogFactory {

	private final Context ctx;
	private Builder ad;

	public enum TYPE_CLICK_LISTENER {
		PLUSIEUR_CAT, AUCUNE_CAT, AUCUNE_MARQUE, NOUVELLE_MARQUE
	};

	public AlertDialogFactory(Context p_ctx) {
		this.ctx = p_ctx;
	}

	/**
	 * Affiche une boite de dialogue prevenant l'utilisateur d'un probleme.
	 * @param p_type - le type d'alert dialog que l'on construit
	 * @return la boite de dialogue. Utiliser ad.show() pour l'afficher à
	 *         l'ecran
	 */
	public AlertDialog.Builder getAttentionDialog(
			EnTypeAlertDialogAttention p_type) {
		ad = new AlertDialog.Builder(ctx);
		ad.setTitle(p_type.getTitre());
		ad.setIcon(R.drawable.ad_attention);
		ad.setMessage(p_type.getMessage());

		AlertDialogAttentionClickListener clickListener = new AlertDialogAttentionClickListener(
				this.ctx, p_type);

		ad.setPositiveButton(p_type.getTxtOkBtton(), clickListener);
		return ad;
	}

	public AlertDialog.Builder getOuiNonDialog(EnTypeAlertDialogOuiNon p_type) {
		ad = new AlertDialog.Builder(ctx);
		ad.setTitle(p_type.getTitre());
		ad.setIcon(R.drawable.ad_attention);
		ad.setMessage(p_type.getMessage());

		AlertDialogOuiNonClickListener clickListener = new AlertDialogOuiNonClickListener(
				this.ctx, p_type);
		ad.setPositiveButton(p_type.getTxtOkBtton(), clickListener);
		ad.setNegativeButton(p_type.getTxtCancelButton(), null);
		return ad;

	}

}
