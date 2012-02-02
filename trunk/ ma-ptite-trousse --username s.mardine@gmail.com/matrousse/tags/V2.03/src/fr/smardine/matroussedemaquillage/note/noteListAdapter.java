package fr.smardine.matroussedemaquillage.note;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import fr.smardine.matroussedemaquillage.R;
import fr.smardine.matroussedemaquillage.mdl.MlListeNote;

/**
 * @author smardine
 */
public class noteListAdapter extends BaseAdapter {

	private final MlListeNote lstNotes;
	// créer un layoutinflater pour intégrer la listview dedans
	private final LayoutInflater myInflater;

	/**
	 * @param p_ctx
	 * @param p_lstnotes
	 */
	public noteListAdapter(Context p_ctx, MlListeNote p_lstnotes) {
		// paramètrer le layout en prenant celui du context
		this.myInflater = LayoutInflater.from(p_ctx);
		this.lstNotes = p_lstnotes;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.lstNotes.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return this.lstNotes.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	/*
	 * astuce pour fluidifier au mieux l'affichage de la listview mémoriser le
	 * contenu des composants visuels qui sont présents dans une ligne de la
	 * listview La classe peut être déclarée dans un autre module pour être
	 * réutilisée
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	/**
	 * @author smardine
	 */
	public static class ViewHolder {
		// TextView text01;
		TextView TvIdNote;
		TextView TvTitreNote;
		ImageView BtModifNote;
	}

	/*
	 * cette méthode est appelée à chaque fois que la listview doit afficher une
	 * ligne
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		// convertView peut déjà être initialisé sinon alors l'initialiser
		if (convertView == null) {
			// affecter un linearlayout propre à la ligne à afficher dans le
			// listview
			convertView = myInflater.inflate(R.layout.notelistitem, null);
			holder = new ViewHolder();
			holder.BtModifNote = (ImageView) convertView
					.findViewById(R.id.IvModifNote);
			holder.TvIdNote = (TextView) convertView
					.findViewById(R.id.TvIdNote);
			holder.TvTitreNote = (TextView) convertView
					.findViewById(R.id.TvTitreNote);
			// tagger le convertView avec ce Holder créé pour que l'association
			// se fasse
			convertView.setTag(holder);
		} else {
			// puisque déjà valorisé une fois alors récupérer le holder à partir
			// du tag posé à la création
			holder = (ViewHolder) convertView.getTag();
		}

		holder.TvIdNote.setText("" + lstNotes.get(position).getIdNote());
		holder.TvTitreNote.setText(lstNotes.get(position).getTitre());

		// holder.text02.setText(produits.get(position).detail);

		return convertView;
	}

	// System.out.println(textDuBouton);

}
