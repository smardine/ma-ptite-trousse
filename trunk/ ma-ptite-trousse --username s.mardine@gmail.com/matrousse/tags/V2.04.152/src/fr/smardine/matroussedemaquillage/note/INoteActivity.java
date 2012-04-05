package fr.smardine.matroussedemaquillage.note;

import android.content.Intent;
import fr.smardine.matroussedemaquillage.mdl.MlNote;

public interface INoteActivity {

	void initComposantVisuel();

	void ChoisiLeTheme();

	MlNote recupereNoteFromPreviousActivity();

	void transfereMlNoteToActivity(Intent p_intent);

}
