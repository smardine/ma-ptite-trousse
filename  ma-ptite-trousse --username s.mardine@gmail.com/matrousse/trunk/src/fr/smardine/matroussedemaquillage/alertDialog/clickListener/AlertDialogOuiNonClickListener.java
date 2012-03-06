package fr.smardine.matroussedemaquillage.alertDialog.clickListener;

import fr.smardine.matroussedemaquillage.alertDialog.type.EnTypeAlertDialogOuiNon;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class AlertDialogOuiNonClickListener implements OnClickListener {

	private final Context ctx;
	private final EnTypeAlertDialogOuiNon typeClick;

	public AlertDialogOuiNonClickListener(Context p_ctx,
			EnTypeAlertDialogOuiNon p_type) {
		this.ctx = p_ctx;
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
