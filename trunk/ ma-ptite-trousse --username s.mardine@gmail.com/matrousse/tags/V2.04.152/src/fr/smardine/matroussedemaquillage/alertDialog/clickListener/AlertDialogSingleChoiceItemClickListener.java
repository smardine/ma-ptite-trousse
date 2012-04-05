package fr.smardine.matroussedemaquillage.alertDialog.clickListener;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import fr.smardine.matroussedemaquillage.alertDialog.IAlertDialogClickListener;
import fr.smardine.matroussedemaquillage.alertDialog.type.EnTypeAlertDialogSingleChoice;

public class AlertDialogSingleChoiceItemClickListener implements
		IAlertDialogClickListener, OnClickListener {

	private final EnTypeAlertDialogSingleChoice type;
	private boolean NouveauProduit;
	private boolean Duppliquer;
	private boolean acceuil;

	public AlertDialogSingleChoiceItemClickListener(
			EnTypeAlertDialogSingleChoice p_type) {
		this.type = p_type;
	}

	@Override
	public void onClick(DialogInterface p_dialog, int p_item) {
		switch (type) {
			case FINIOUPAS:
				switch (p_item) {
					case 0:
						NouveauProduit = true;
						break;
					case 1:
						Duppliquer = true;
						break;
					case 2:
						acceuil = true;
						break;
				}
				break;
		}

	}

	public boolean isNouveauProduit() {
		return NouveauProduit;
	}

	public boolean isDuppliquer() {
		return Duppliquer;
	}

	public boolean isAcceuil() {
		return acceuil;
	}

}
