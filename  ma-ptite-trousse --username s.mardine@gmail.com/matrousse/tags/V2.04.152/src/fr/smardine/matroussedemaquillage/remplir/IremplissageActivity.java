package fr.smardine.matroussedemaquillage.remplir;

import android.content.Intent;
import fr.smardine.matroussedemaquillage.mdl.MlProduit;

public interface IremplissageActivity {

	MlProduit recupereMlProduitfromPreviousActivity();

	void transfereMlProduitToActivity(Intent p_itent);

	void initComposantVisuel();

	void ChoisiLeTheme();

}
