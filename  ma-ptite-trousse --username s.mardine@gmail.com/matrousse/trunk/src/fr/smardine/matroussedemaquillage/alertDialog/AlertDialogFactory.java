package fr.smardine.matroussedemaquillage.alertDialog;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import fr.smardine.matroussedemaquillage.R;
import fr.smardine.matroussedemaquillage.alertDialog.clickListener.AlertDialogAttentionClickListener;
import fr.smardine.matroussedemaquillage.alertDialog.clickListener.AlertDialogChoixCatClickListener;
import fr.smardine.matroussedemaquillage.alertDialog.clickListener.AlertDialogOuiNonClickListener;
import fr.smardine.matroussedemaquillage.alertDialog.clickListener.AlertDialogSingleChoiceItemClickListener;
import fr.smardine.matroussedemaquillage.alertDialog.type.EnTypeAlertDialogAttention;
import fr.smardine.matroussedemaquillage.alertDialog.type.EnTypeAlertDialogChoixCat;
import fr.smardine.matroussedemaquillage.alertDialog.type.EnTypeAlertDialogOuiNon;
import fr.smardine.matroussedemaquillage.base.accesTable.AccesTableTrousseProduits;
import fr.smardine.matroussedemaquillage.mdl.MlListeTrousseProduit;

/**
 * Classe generale permettant de contruire les DialogBox en fonction d'un type
 * souhaité
 * @author smardine
 */
public class AlertDialogFactory {

	private final Context ctx;
	private Builder ad;
	private AlertDialogChoixCatClickListener choixCatlickListener;
	private AlertDialogSingleChoiceItemClickListener choixCatSingleClickListener;

	/**
	 * Constructeur
	 * @param p_ctx - le Context qui a lancé cette DialogBox
	 */
	public AlertDialogFactory(Context p_ctx) {
		this.ctx = p_ctx;
	}

	/**
	 * Construit une boite de dialogue prevenant l'utilisateur d'un probleme.
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

		AlertDialogAttentionClickListener attentionClickListener = new AlertDialogAttentionClickListener(
				this.ctx, p_type);

		ad.setPositiveButton(p_type.getTxtOkBtton(), attentionClickListener);
		return ad;
	}

	/**
	 * Construit une dialogBox avec deux boutons (oui et Non) plus du texte, un
	 * titre et une icone
	 * @param p_type - le type d'alert dialog que l'on construit
	 * @return la boite de dialogue. Utiliser ad.show() pour l'afficher à
	 *         l'ecran
	 */
	public AlertDialog.Builder getOuiNonDialog(EnTypeAlertDialogOuiNon p_type) {
		ad = new AlertDialog.Builder(ctx);
		ad.setTitle(p_type.getTitre());
		ad.setIcon(R.drawable.ad_attention);
		ad.setMessage(p_type.getMessage());

		AlertDialogOuiNonClickListener ouinonClickListener = new AlertDialogOuiNonClickListener(
				p_type);
		ad.setPositiveButton(p_type.getTxtOkBtton(), ouinonClickListener);
		ad.setNegativeButton(p_type.getTxtCancelButton(), null);
		return ad;
	}

	/**
	 * Construit une dialogBox avec deux boutons (Choisir et Annuler) ,une liste
	 * d'element cliquable, un titre mais pas d'icone
	 * @param p_type - le type d'alert dialog que l'on construit
	 * @return la boite de dialogue. Utiliser ad.show() pour l'afficher à
	 *         l'ecran
	 */
	public AlertDialog.Builder getChoixCatDialog(
			EnTypeAlertDialogChoixCat p_type) {
		ad = new AlertDialog.Builder(ctx);
		ad.setTitle(p_type.getTitre());
		choixCatSingleClickListener = new AlertDialogSingleChoiceItemClickListener(
				p_type);

		choixCatlickListener = new AlertDialogChoixCatClickListener(ctx,
				p_type, choixCatSingleClickListener);

		String[] NomProduits = recupereSousCategorie(p_type.getTitre());
		int idProdCoche = recupereIndiceSousCategorieCochee(p_type.getTitre());

		ad.setSingleChoiceItems(NomProduits, idProdCoche,
				choixCatSingleClickListener);
		ad.setPositiveButton(p_type.getTxtOkBtton(), choixCatlickListener);
		ad.setNegativeButton(p_type.getTxtCancelButton(), null);

		return ad;
	}

	public AlertDialogChoixCatClickListener getChoixCatlickListener() {
		return choixCatlickListener;
	}

	public AlertDialogSingleChoiceItemClickListener getChoixCatSingleClickListener() {
		return choixCatSingleClickListener;
	}

	/**
	 * @param p_categorie la categorie recherchée (Visage,Yeux,Levres...)
	 * @return toutes les sous catgegorie sous forme de tableau de string
	 */
	private String[] recupereSousCategorie(String p_categorie) {

		AccesTableTrousseProduits accesProduits = new AccesTableTrousseProduits(
				this.ctx);
		MlListeTrousseProduit ListeProduits = accesProduits
				.getListeTrousseProduit(p_categorie);
		String[] NomProduits = new String[ListeProduits.size()];

		for (int j = 0; j < ListeProduits.size(); j++) {
			NomProduits[j] = ListeProduits.get(j).getNomSousCat().getLib();
		}

		return NomProduits;
	}

	/**
	 * @param p_categorie - la ategorie mere
	 * @return Recupere l'incice de la sous categorie cochée en fonction du nom
	 *         de la categorie mere
	 */
	private int recupereIndiceSousCategorieCochee(String p_categorie) {
		int idxProduitCoche = -1;
		AccesTableTrousseProduits accesProduits = new AccesTableTrousseProduits(
				this.ctx);
		MlListeTrousseProduit ListeProduits = accesProduits
				.getListeTrousseProduit(p_categorie);

		for (int j = 0; j < ListeProduits.size(); j++) {
			if (ListeProduits.get(j).isChecked()) {
				idxProduitCoche = j;
				break;
			}
		}
		return idxProduitCoche;
	}

}
