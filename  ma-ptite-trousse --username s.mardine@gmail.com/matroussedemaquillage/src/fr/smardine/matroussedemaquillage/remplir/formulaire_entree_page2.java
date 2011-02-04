package fr.smardine.matroussedemaquillage.remplir;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;
import fr.smardine.matroussedemaquillage.R;
import fr.smardine.matroussedemaquillage.base.BDAcces;
import fr.smardine.matroussedemaquillage.note.note_page1;
import fr.smardine.matroussedemaquillage.param.tab_param;
import fr.smardine.matroussedemaquillage.recherche.recherchetabsbuttons;
import fr.smardine.matroussedemaquillage.variableglobale.EnTheme;




public class formulaire_entree_page2 extends Activity implements OnClickListener {
	Button BoutonValider2;
	AutoCompleteTextView textView;
	private  BDAcces objBd;
	String[] Marque;
	String[] TrousseTempo;
	String MarqueChoisie="";
	String DateFichierxml="";
	String cheminFichierLocal="";
	String cheminFichierDistant="";
	String DateChoisie="";
	String nomProduitRecup="";
	String numTeinte="";
	String DureeVie="";
	AlertDialog.Builder adNouvelleMarque,adAucuneMarque;
	
	
	
    /** Called when the activity is first created. */
  
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // ExceptionHandler.register(this, "http://simon.mardine.free.fr/trousse_maquillage/test/server.php","ma_ptite_trousse");
        choisiLeTheme();
        
      
        BoutonValider2 = (Button)this.findViewById(R.id.ButtonValider2_Page2);
        textView = (AutoCompleteTextView) findViewById(R.id.autocomplete_marque_Page2);
        
       // Animlineaire anim = new Animlineaire (true,false);
        //textView.startAnimation(anim.getAnim());
        
      //  anim = new Animlineaire (false,true);
      //  BoutonValider2.startAnimation(anim.getAnim());
        objBd = new BDAcces(this);
        objBd.open();
        
		Marque = objBd.renvoi_liste_ValeurDansString("trousse_marques","nom_marque");
				    
    	objBd.close();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_marque_auto,Marque);
        textView.setAdapter(adapter);
             
        BoutonValider2.setOnClickListener(this);
       
       adAucuneMarque = new AlertDialog.Builder(this);
       adAucuneMarque.setTitle("Attention");
       adAucuneMarque.setIcon(R.drawable.ad_attention);
       adAucuneMarque.setMessage("Vous n'avez rentré aucune marque \nMerci d'en saisir une");
       adAucuneMarque.setPositiveButton("Ok",null);
    
       
       adNouvelleMarque = new AlertDialog.Builder(this);
	   adNouvelleMarque.setIcon(R.drawable.ad_attention);
       adNouvelleMarque.setTitle("Petite vérification");
       adNouvelleMarque.setMessage("Nouvelle marque\nCette marque est inconnue de \"Ma p'tite trousse\"\nSouhaitez vous la partager avec les autres utilisateurs? (Connexion Edge, 3G ou wifi requise)");
       adNouvelleMarque.setPositiveButton("Oui",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				popUp("Ok, c'est bon, k'utilisateur confirme");
				PostMarqueSurServeur(textView.getText().toString());
				/*String ligneXml = writeXml(MarqueChoisie);
				File chemin = new File ("./sdcard/matrousse");
				if (!chemin.exists()){
				chemin.mkdirs();
				}
				try {
					Date actuelle = new Date();
					DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss");
					DateFichierxml = dateFormat.format(actuelle);
					cheminFichierLocal = "./sdcard/matrousse/marque"+DateFichierxml+".xml";
					cheminFichierDistant = "/trousse_maquillage/nouveautes/marque"+DateFichierxml+".xml";
				WriteFile.WriteLine(ligneXml, cheminFichierLocal);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println ("Erreur a la creation de connexionFTP : "+e);
					e.printStackTrace();
				}
				popUp("Creation nouveau fichier xml ok");
				connexionFTP con = null;
				try {
					 con = new connexionFTP("ftpperso.free.fr","user","password");
				} catch (FTPException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					System.out.println ("Erreur a la creation de connexionFTP : "+e);
				}
				con.ActiveOrPassiveMod(false, true);
				con.ASCIIMODE();
				boolean connexionOk = con.connexion();
				if (connexionOk){
					popUp("connexion au serveur ftp Ok");
					String directory = con.getDirectory();
					popUp ("repertoire : "+directory);
					//on se met dans le repertoire "troussemaquillage"
					boolean changementOk = con.ChangeDirectory("/trousse_maquillage/nouveautes");
					String directorybis = con.getDirectory();
					popUp ("nouveau repertoire : "+directorybis);
					if (changementOk){//on accede bien au repertoire => on upload le fichier
						popUp("repertoire "+directorybis+" atteind");
					 boolean uploadOk = con.UploadFile(cheminFichierLocal, cheminFichierDistant);
					 if (uploadOk){
						 popUp("upload Ok");
						 File xml = new File (cheminFichierLocal);
						 boolean effaceOk = xml.delete();
						 if(!effaceOk){
							 xml.deleteOnExit();
						 }
					 }else{
						 popUp("upload NOk"); 
					 }
					 popUp("deconnexion Ok");
					 con.deconnexion();
						
					}
					
				}*/
				Intent intent = new Intent(formulaire_entree_page2.this, formulaire_entree_page3.class);
				//on demarre la nouvelle activité
				intent.putExtra("MarqueChoisie",MarqueChoisie.trim());
				intent.putExtra("DurreeDeVie", DureeVie.trim());
				intent.putExtra("DateAchat", DateChoisie.trim());
				intent.putExtra("NumTeinte", numTeinte.trim());
				intent.putExtra("NomProduit", nomProduitRecup.trim());
				intent.putExtra("LaunchByPage2", true);
				startActivity(intent);
		        finish();
				
				
			}
		});
       adNouvelleMarque.setNegativeButton("Non",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(formulaire_entree_page2.this, formulaire_entree_page3.class);
				intent.putExtra("MarqueChoisie",MarqueChoisie.trim());
				intent.putExtra("DurreeDeVie", DureeVie.trim());
				intent.putExtra("DateAchat", DateChoisie.trim());
				intent.putExtra("NumTeinte", numTeinte.trim());
				intent.putExtra("NomProduit", nomProduitRecup.trim());
				intent.putExtra("LaunchByPage2", true);
				//on demarre la nouvelle activité
				startActivity(intent);
		        finish();
				 
			}});
       this.setTitle("Choix de la marque");
       //popUp("OnCreate-page2");
			
        
        
        
    }

	/**
	 * 
	 */
	private void choisiLeTheme() {
		objBd = new BDAcces(this);
        objBd.open();
        String [] champ = {"AfficheAlerte","DureeViePeremp","Theme"};
   		@SuppressWarnings("rawtypes")
		ArrayList[] Param = objBd.renvoi_param(champ);
   				    
   		String nomThemeChoisi = Param[2].get(0).toString().trim();
		if (EnTheme.Bisounours.getLib().equals(nomThemeChoisi)){
   			setContentView(R.layout.theme_bisounours_formulaire_entree_page2);
   			
   		}
   		if (EnTheme.Classique.getLib().equals(nomThemeChoisi)){
   			setContentView(R.layout.formulaire_entree_page2);
   			
   		}if(EnTheme.Fleur.getLib().equals(nomThemeChoisi)){
   			setContentView(R.layout.theme_fleur_formulaire_entree_page2);
   		}
   		
    	objBd.close();
	}
	
	 private void onCreateMenu(Menu menu){
	    	SubMenu recherche = menu.addSubMenu(0, 2000, 1, "Recherche");
	    	recherche.setIcon(R.drawable.menu_recherche);
	    	SubMenu note=menu.addSubMenu(0,2002,2,"Notes");
	    	note.setIcon(R.drawable.menu_note);
	    	SubMenu parametre = menu.addSubMenu(0, 2001, 3, "Parametres");
	    	parametre.setIcon(R.drawable.menu_param); //icone systeme
	    }
	    
	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	    	onCreateMenu(menu);    	
	    	return super.onCreateOptionsMenu(menu);
	    }
	    
	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	    	// évènement appelé lorsqu'un menu est choisi
	    	switch(item.getItemId()) {
	    	// l'identifiant integer est moins gourmand en ressource que le string
	    	case 2000: 
	    		Toast.makeText(this, "Recherche", 1000).show();
	    		Intent intentRecherche = new Intent(this, recherchetabsbuttons.class);
	    		intentRecherche.putExtra("calledFrompage1",true);
	    		//on demarre la nouvelle activité
	    		startActivity(intentRecherche);
	    		finish();
	    		break;
	    	case 2001:
	    		Toast.makeText(this, "Paramètres", 1000).show();
	    		Intent intentParametres = new Intent(this, tab_param.class);
	    		//on demarre la nouvelle activité
	    		startActivity(intentParametres);
	    		finish();
	    		break;
	    	case 2002:
	    		Toast.makeText(this, "Notes", 1000).show();
	    		Intent intentNote = new Intent(this, note_page1.class);
	    		//on demarre la nouvelle activité
	    		startActivity(intentNote);
	    		finish();
	    		break;
	    	
	    	}
	    	Log.i("", ""+item.getTitle());
	    	return super.onOptionsItemSelected(item);
	    }
    
protected void PostMarqueSurServeur(String Marque) {
	String TAG = "fr.smardine.matroussedemaquillage.remplir"; 
	DefaultHttpClient httpClient = new DefaultHttpClient();
     HttpPost httpPost = new HttpPost("http://simon.mardine.free.fr/trousse_maquillage/nouveautes/postmarque.php");
     List <NameValuePair> nvps = new ArrayList <NameValuePair>();
	  
	   	nvps.add(new BasicNameValuePair("marque", Marque));
        try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			Log.d(TAG, "UnsupportedEncodingException: "+ e);
			//e.printStackTrace();
		}
    
     // We don't care about the response, so we just hope it went well and on with it
     HttpResponse response = null;
	try {
		response = httpClient.execute(httpPost);
	} catch (ClientProtocolException e) {
		// TODO Auto-generated catch block
		Log.d(TAG, "ClientProtocolException: "+ e);
		//e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		Log.d(TAG, "IOException: "+ e);
		//e.printStackTrace();
	} 
     BufferedReader reader = null;
	try {
		reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	} catch (IllegalStateException e1) {
		// TODO Auto-generated catch block
		Log.d(TAG, "IllegalStateException: "+ e1);
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		Log.d(TAG, "IOException: "+ e1);
		//e1.printStackTrace();
	}
     String strLine;
     try {
		while ((strLine = reader.readLine()) != null)   {
		 	Log.d(TAG, "reponse du post : "+ strLine);
		 }
	} catch (IOException e) {
		// TODO Auto-generated catch block
		Log.d(TAG, "IOException: "+ e);
		//e.printStackTrace();
	} 
	}



public void onClick(View v) {
	
		if (v==textView){
			textView.setFocusable(true);	
		}
		
		
		if (v==BoutonValider2){
			//on recupere le nom de marque choisi par l'utilisateur
			MarqueChoisie = textView.getText().toString();
			
			if (!MarqueChoisie.equals("")){
			//on le compare à la liste des marques enregistrée en base
			boolean MarqueenBase = false;
			for (int i=0;i<Marque.length;i++){
				if (Marque[i].contains(MarqueChoisie)){
					MarqueenBase=true;
				}
			}
					
			if (MarqueenBase==true){
				
				popUp("On Continue");
				//on créer une nouvelle activité avec comme point de depart "Main" et comme destination "FicheClient"
				Intent intent = new Intent(formulaire_entree_page2.this, formulaire_entree_page3.class);
				//on demarre la nouvelle activité
				intent.putExtra("MarqueChoisie",MarqueChoisie);
				intent.putExtra("DurreeDeVie", DureeVie);
				intent.putExtra("DateAchat", DateChoisie);
				intent.putExtra("NumTeinte", numTeinte);
				intent.putExtra("NomProduit", nomProduitRecup);
				intent.putExtra("LaunchByPage2", true);
				
				startActivity(intent);
		        finish();
				 
			}
			
			else{
				//sinon, on stocke quand meme la marque ds les tables temporaire, 
				//mais en + on envoi via ftp un fichier xml contenant cette marque
				ContentValues values = new ContentValues();
				values.put("nom_marque", MarqueChoisie);
				values.put("ischecked", "false");
				
				objBd.open();
				objBd.InsertDonnéedansTable("trousse_marques", values);
				objBd.close();
				adNouvelleMarque.show();
			
			}
			
			
		}else{
			//popUp("Veuillez rensegner la marque");
			adAucuneMarque.show();
		}
			
		}
			 	
		      
		        
	}


/*
@SuppressWarnings("unused")
private String writeXml(String message){
    XmlSerializer serializer = Xml.newSerializer();
    StringWriter writer = new StringWriter();
    try {
        serializer.setOutput(writer);
        serializer.startDocument("UTF-8", true);
        serializer.startTag("", "messages");
       // serializer.attribute("", "number", String.valueOf(messages.size()));
       //serializer.attribute("", "date", msg.getDate());
            serializer.startTag("", "marque");
            serializer.text(message);
            serializer.endTag("", "marque");
           
       
        serializer.endTag("", "messages");
        serializer.endDocument();
        return writer.toString();
    } catch (Exception e) {
        throw new RuntimeException(e);
    } 
}*/


public void popUp(String message) {
   // Toast.makeText(this, message, 1).show();
}	

protected void onRestart() {
    super.onRestart();
    //popUp("onRestart()-Page2");
}

/**
 * Exécuté lorsque l'activité devient visible à l'utilisateur.
 *
 * La fonction onStart() est suivie de la fonction onResume().
 */
@Override
protected void onStart() {
    super.onStart();
    //popUp("onStart()-Page2");
}

/**
 * Exécutée a chaque passage en premier plan de l'activité.
 * Ou bien, si l'activité passe à nouveau en premier (si une autre activité était passé en premier plan entre temps).
 *
 * La fonction onResume() est suivie de l'exécution de l'activité.
 */
@Override
protected void onResume() {
    super.onResume();
    popUp("onResume()-Page2");
    
    boolean IsCalledFromPage1=getIntent().getBooleanExtra ("LaunchByPage1",false);
    boolean IsCalledFromPage3=getIntent().getBooleanExtra("LaunchByPage3", false);
  	popUp("IscreatFormPage3: "+IsCalledFromPage3);
  	popUp("IscreatFormPage1: "+IsCalledFromPage1);
  	if (IsCalledFromPage1){
  		MarqueChoisie=getIntent().getStringExtra ("MarqueChoisie").trim();   
  	     int taille=MarqueChoisie.length();
  			 if (taille!=0){
  		    	if (!MarqueChoisie.equals("")){
  		        	textView.setText(MarqueChoisie);
  		        }  
  		    }
  			
  			DateChoisie=getIntent().getStringExtra("DateAchat").trim();
  		    nomProduitRecup=getIntent().getStringExtra("NomProduit").trim();
  		    numTeinte=getIntent().getStringExtra("NumTeinte").trim();
  		    DureeVie=getIntent().getStringExtra("DurreeDeVie").trim();
  	  }	
  	  
  	if (IsCalledFromPage3){
  	 MarqueChoisie=getIntent().getStringExtra ("MarqueChoisie").trim();   
     int taille=MarqueChoisie.length();
		 if (taille!=0){
	    	if (!MarqueChoisie.equals("")){
	        	textView.setText(MarqueChoisie);
	        }  
	    }
		
		DateChoisie=getIntent().getStringExtra("DateAchat").trim();
	    nomProduitRecup=getIntent().getStringExtra("NomProduit").trim();
	    numTeinte=getIntent().getStringExtra("NumTeinte").trim();
	    DureeVie=getIntent().getStringExtra("DurreeDeVie").trim();
  	}
}
/**
 * La fonction onStop() est exécutée :
 * - lorsque l'activité n'est plus en premier plan
 * - ou bien lorsque l'activité va être détruite
 *
 * Cette fonction est suivie :
 * - de la fonction onRestart() si l'activité passe à nouveau en premier plan
 * - de la fonction onDestroy() lorsque l'activité se termine ou bien lorsque le système décide de l'arrêter
 */
@Override
protected void onStop() {
    super.onStop();
    popUp("onStop-Page2");	
}
/**
 * La fonction onPause() est suivie :
 * - d'un onResume() si l'activité passe à nouveau en premier plan
 * - d'un onStop() si elle devient invisible à l'utilisateur
 *
 * L'exécution de la fonction onPause() doit être rapide,
 * car la prochaine activité ne démarrera pas tant que l'exécution
 * de la fonction onPause() n'est pas terminée.
 */
@Override
protected void onPause() {
    super.onPause();
  
}

public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK) {
    	Intent page1 = new Intent(this, formulaire_entree_page1.class);
    	page1.putExtra("MarqueChoisie", textView.getText().toString().trim());
    	page1.putExtra("DurreeDeVie", DureeVie.trim());
    	page1.putExtra("DateAchat", DateChoisie.trim());
    	page1.putExtra("NumTeinte", numTeinte.trim());
    	page1.putExtra("NomProduit", nomProduitRecup.trim());
    	page1.putExtra("LaunchByPage2", true);
    	startActivity(page1);
        finish();
        return true;
    }
    if (keyCode == KeyEvent.KEYCODE_SEARCH){
    	Intent intentRecherche = new Intent(this, recherchetabsbuttons.class);
		//on demarre la nouvelle activité
		startActivity(intentRecherche);
		finish();
    }
    return super.onKeyDown(keyCode, event);
}
public void OnDestroy() {
	super.onDestroy();
	popUp("OnDestroy-Page2");	
	
}
		
}

