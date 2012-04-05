package fr.smardine.matroussedemaquillage.alertDialog.clickListener.buttonClick;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;
import fr.smardine.matroussedemaquillage.alertDialog.IAlertDialogClickListener;
import fr.smardine.matroussedemaquillage.alertDialog.type.EnTypeAlertDialogOuiNon;

/**
 * Gestion des evenements lors du click sur le bouton Oui sur une boite de
 * dialogue "Oui/Non"
 * @author smardine
 */
public class AlertDialogOuiNonClickListener implements
		IAlertDialogClickListener, OnClickListener {

	private final EnTypeAlertDialogOuiNon typeClick;
	private final String marque;

	/**
	 * Constructeur
	 * @param p_type - le type d'alertDialog concerné
	 * @param p_marque - eventuellement la marque à "pusher" vers le site
	 */
	public AlertDialogOuiNonClickListener(EnTypeAlertDialogOuiNon p_type,
			String p_marque) {
		this.typeClick = p_type;
		this.marque = p_marque;
	}

	@Override
	public void onClick(DialogInterface p_arg0, int p_arg1) {
		switch (typeClick) {
			case NOUVELLE_MARQUE:
				PostMarqueSurServeur(marque);
				break;
		}

	}

	/**
	 * Envoi la marque sur le serveur via une requette HTTP
	 * @param p_marque - la marque a envoyer
	 */
	private void PostMarqueSurServeur(String p_marque) {
		String TAG = "fr.smardine.matroussedemaquillage.remplir";
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(
				"http://simon.mardine.free.fr/trousse_maquillage/nouveautes/postmarque.php");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();

		nvps.add(new BasicNameValuePair("marque", p_marque));
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

			// We don't care about the response, so we just hope it went well
			// and on
			// with it

			HttpResponse response = httpClient.execute(httpPost);

			BufferedReader reader = null;

			reader = new BufferedReader(new InputStreamReader(response
					.getEntity().getContent()));

			String strLine;

			while ((strLine = reader.readLine()) != null) {
				Log.d(TAG, "reponse du post : " + strLine);
			}
		} catch (IOException e) {

			Log.d(TAG, "IOException: " + e);
			return;
			// e.printStackTrace();
		} catch (IllegalStateException e1) {

			Log.d(TAG, "IllegalStateException: " + e1);
			return;
		}
	}

}
