package fr.smardine.matroussedemaquillage.recherche;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import fr.smardine.matroussedemaquillage.R;

/**
 * @author smardine
 */
public class produitRechercheListAdapter extends BaseAdapter {

	private final List<fr.smardine.matroussedemaquillage.recherche.produitRecherche> produitRecherche;
	// cr�er un layoutinflater pour int�grer la listview dedans
	private final LayoutInflater myInflaterRecherche;

	/**
	 * @param context
	 * @param produitRechercheTitre
	 */
	public produitRechercheListAdapter(
			Context context,
			List<fr.smardine.matroussedemaquillage.recherche.produitRecherche> produitRechercheTitre) {
		// param�trer le layout en prenant celui du context
		this.myInflaterRecherche = LayoutInflater.from(context);
		this.produitRecherche = produitRechercheTitre;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.produitRecherche.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return this.produitRecherche.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	/*
	 * astuce pour fluidifier au mieux l'affichage de la listview m�moriser le
	 * contenu des composants visuels qui sont pr�sents dans une ligne de la
	 * listview La classe peut �tre d�clar�e dans un autre module pour �tre
	 * r�utilis�e
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	/**
	 * @author smardine
	 */
	public static class ViewHolder {
		/**
		 * 
		 */
		public TextView text01;
		/**
		 * 
		 */
		public TextView text02;
		/**
		 * 
		 */
		public TextView text03;
		/**
		 * 
		 */
		public TextView text04;
	}

	/*
	 * cette m�thode est appel�e � chaque fois que la listview doit afficher une
	 * ligne
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		// convertView peut d�j� �tre initialis� sinon alors l'initialiser
		if (convertView == null) {
			// affecter un linearlayout propre � la ligne � afficher dans le
			// listview
			convertView = myInflaterRecherche.inflate(
					R.layout.produitlistitem_recherche, null);
			holder = new ViewHolder();
			holder.text01 = (TextView) convertView.findViewById(R.id.Txt1List);
			holder.text02 = (TextView) convertView.findViewById(R.id.Txt2List);
			holder.text03 = (TextView) convertView.findViewById(R.id.Txt3List);
			holder.text04 = (TextView) convertView.findViewById(R.id.Txt4List);
			// tagger le convertView avec ce Holder cr�� pour que l'association
			// se fasse
			convertView.setTag(holder);
		} else {
			// puisque d�j� valoris� une fois alors r�cup�rer le holder � partir
			// du tag pos� � la cr�ation
			holder = (ViewHolder) convertView.getTag();
		}

		holder.text01.setText(produitRecherche.get(position).champ1);
		holder.text02.setText(produitRecherche.get(position).champ2);
		holder.text03.setText(produitRecherche.get(position).champ3);
		holder.text04.setText(produitRecherche.get(position).champ4);

		return convertView;
	}

}
