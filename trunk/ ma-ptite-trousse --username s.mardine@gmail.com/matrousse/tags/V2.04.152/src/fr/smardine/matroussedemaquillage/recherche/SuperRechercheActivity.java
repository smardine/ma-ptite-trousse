package fr.smardine.matroussedemaquillage.recherche;

import helper.DateHelper;

import java.util.List;

import android.app.Activity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.ListView;
import fr.smardine.matroussedemaquillage.base.accesTable.AccesTableProduitEnregistre;
import fr.smardine.matroussedemaquillage.mdl.MlListeProduits;
import fr.smardine.matroussedemaquillage.mdl.MlProduit;
import fr.smardine.matroussedemaquillage.variableglobale.EnCategorieFiltrage;

public class SuperRechercheActivity extends Activity {

	public void termineActivity() {
		finish();
	}

	public void AfficheLeContenu(EnCategorieFiltrage p_TypeRecherche,
			List<produitRecherche> produitRechercheTitre,
			ListView produitListView, String p_Filtrage, boolean rechPerime) {

		AccesTableProduitEnregistre accesProduit = new AccesTableProduitEnregistre(
				this);
		if (rechPerime) {
			produitRechercheTitre.add(new produitRecherche("-1",
					"Date Peremp.", "Produit", "Marque"));
			MlListeProduits lstProduit = accesProduit
					.getListeProduitPerimeFiltree(p_TypeRecherche, p_Filtrage);
			for (MlProduit p : lstProduit) {
				produitRechercheTitre
						.add(new produitRecherche("" + p.getIdProduit(),
								DateHelper.getDateforDatabase(p
										.getDatePeremption()), p
										.getNomProduit(), p.getMarque()));
			}
		} else {
			produitRechercheTitre.add(new produitRecherche("-1", "Catégorie",
					"Produit", "Marque"));
			MlListeProduits lstProduit = accesProduit.getListeProduitFiltree(
					p_TypeRecherche, p_Filtrage);
			for (MlProduit p : lstProduit) {
				produitRechercheTitre.add(new produitRecherche(""
						+ p.getIdProduit(), p.getCategorie().getCategorie()
						.name(), p.getNomProduit(), p.getMarque()));
			}
		}

		// animation d'affichage cascade du haut vers le bas
		AnimationSet set = new AnimationSet(true);
		Animation animation = new AlphaAnimation(0.0f, 1.0f);
		animation.setDuration(100);
		set.addAnimation(animation);
		animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
				-1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		animation.setDuration(100);
		set.addAnimation(animation);
		LayoutAnimationController controller = new LayoutAnimationController(
				set, 0.5f);
		produitListView.setLayoutAnimation(controller);

		// paramètrer l'adapteur correspondant
		produitRechercheListAdapter adpt = new produitRechercheListAdapter(
				this, produitRechercheTitre);
		// paramèter l'adapter sur la listview
		produitListView.setAdapter(adpt);

	}

}
