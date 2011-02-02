package fr.smardine.matroussedemaquillage.remplir;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import fr.smardine.matroussedemaquillage.Main;
import fr.smardine.matroussedemaquillage.R;
import fr.smardine.matroussedemaquillage.base.BDAcces;
import fr.smardine.matroussedemaquillage.note.note_page1;
import fr.smardine.matroussedemaquillage.param.tab_param;
import fr.smardine.matroussedemaquillage.recherche.recherchetabsbuttons;
import fr.smardine.matroussedemaquillage.variableglobale.EnTheme;




public class formulaire_entree_recap extends Activity implements OnClickListener {
	
	
	TextView CategorieRecap,MarqueRecap,nomProduitRecap,numeroTeinteRecap,DateAchatRecap,dureeVieRecap;
	Button BoutonValiderFinal;
	private  BDAcces objBd;
	AlertDialog.Builder ad,adFiniOuPas;
	@SuppressWarnings("rawtypes")
	ArrayList []trousse_tempo;
	boolean Continue;
	@SuppressWarnings("rawtypes")
	ArrayList []Categorie_Cochée;
	int RequestCodePage1=1,RequestCodePage2=2,RequestCodePage3=3;
	
		/////////////////////

	    static final int DATE_DIALOG_ID = 0;
	    ///////////////////////////
	
	
    /** Called when the activity is first created. */
  
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // ExceptionHandler.register(this, "http://simon.mardine.free.fr/trousse_maquillage/test/server.php","ma_ptite_trousse");
        choisiLeTheme();
        
       
        // capture our View elements
        CategorieRecap = (TextView) findViewById(R.id.CategorieRecap2_page4);
        MarqueRecap = (TextView) findViewById(R.id.MarqueRecap2_page4);
        nomProduitRecap = (TextView) findViewById(R.id.NomRecap2_page4);
        numeroTeinteRecap = (TextView) findViewById(R.id.TeinteRecap2_page4);
        DateAchatRecap = (TextView) findViewById(R.id.dateDisplayRecap2_page4);
        dureeVieRecap = (TextView) findViewById(R.id.DureeVieRecap2_page4);
        BoutonValiderFinal = (Button)this.findViewById(R.id.ButtonValiderFinal_page4);

        BoutonValiderFinal.setOnClickListener(this);
        
        objBd = new BDAcces(this);
        updateDisplay();
        adFiniOuPas = new AlertDialog.Builder(this);
        adFiniOuPas.setTitle("Que voulez vous faire?");
        adFiniOuPas.setIcon(R.drawable.ad_attention);
        CharSequence[] items = { "Continuer à remplir ma p'tite trousse", "Revenir à la page d'accueil" };
        adFiniOuPas.setSingleChoiceItems(items , -1, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item){
                /* User clicked on a radio button do some stuff */
            	if (item==0){//continuer a rentrer des produits
            		Continue=true;
            	}if (item==1){//sortir
            		Continue=false;
            	}
            	
            }
        });

        adFiniOuPas.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
            	 String Table = "trousse_produits";
				 ContentValues modifiedValues = new ContentValues();
				 modifiedValues.put("ischecked", "false");
				 String whereClause = "ischecked=?";
				 String[] whereArgs = new String[]{"true"};
				
				 objBd.open();
				 int nbdechamp= objBd.majTable(Table, modifiedValues, whereClause, whereArgs);
				 objBd.deleteTable("trousse_tempo","1",null);
				 System.out.println("Nombre de champ modifié : "+nbdechamp);
				 objBd.close();
				
				
				
            	if (Continue){// on retourne a la 1° fenetre du formulaire
            		Intent intent = new Intent(formulaire_entree_recap.this, formulaire_entree_page1.class);
            		//on demarre la nouvelle activité
            		 intent.putExtra("calledFromRecap", true);
            		 startActivity(intent);
 			        
            		 finish();
            		
            		
            	
            	}else{//on revient a la page d'acceuil
            		Intent intent = new Intent(formulaire_entree_recap.this, Main.class);
            		//on demarre la nouvelle activité
            		intent.putExtra("calledFromRecap", true);
            		
            		startActivity(intent);
			       
            		 finish();
            		 
            		
            	}
        }
        });

        
	        ad = new AlertDialog.Builder(this);
	    	ad.setTitle("Petite vérification");
			ad.setIcon(R.drawable.ad_attention);
	        ad.setMessage("Souhaitez vous ajouter ce produit à ma p'tite trousse?");
	        ad.setPositiveButton("Oui",new DialogInterface.OnClickListener() {
				
		
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				//popUp("Ok, c'est bon, k'utilisateur confirme");
				objBd.open();
				ContentValues valuesProduitsFinal = new ContentValues();
				valuesProduitsFinal.put("nom_produit", trousse_tempo[1].toString().replace("[", "").replace("]",""));
				valuesProduitsFinal.put("nom_marque",trousse_tempo[0].toString().replace("[", "").replace("]",""));
				valuesProduitsFinal.put("nom_souscatergorie", Categorie_Cochée[0].toString().replace("[", "").replace("]",""));
				valuesProduitsFinal.put("nom_categorie", Categorie_Cochée[1].toString().replace("[", "").replace("]",""));
				valuesProduitsFinal.put("numero_Teinte", trousse_tempo[2].toString().replace("[", "").replace("]",""));
				valuesProduitsFinal.put("DateAchat", trousse_tempo[3].toString().replace("[", "").replace("]",""));
				valuesProduitsFinal.put("Duree_Vie", trousse_tempo[4].toString().replace("[", "").replace("]",""));
				//calcul de la date de peremption problable
												
				int nbMoisDurreeDeVie=Integer.parseInt(trousse_tempo[4].toString().replace("[", "").replace("]",""));
				int nbJours = nbMoisDurreeDeVie*30;
				
				String DateAchat = trousse_tempo[3].toString().replace("[", "").replace("]","");
				String tabAchat [] = DateAchat.split("-");
				int jourAchat = Integer.parseInt(tabAchat[0]);
				int mois = Integer.parseInt(tabAchat[1])-1;//les mois commence à 0 (janvier) et se termine à 11 (decembre)
				int annee = Integer.parseInt(tabAchat[2])-1900;//les années commence à 0(1900), pour avoir l'année exacte a partir d'une velur contenu dans un string, il faut retrancher 1900 a la valeur de l'année.
				// exemple, l'année 2010 est considérée comme 2010-1900 = 110
				
				Date DateAchatAuformatDate = new Date (annee,mois,jourAchat);
				long DateAchatEnMilli = DateAchatAuformatDate.getTime();// on recupere la date d'achat au format milliseconde
				
				
				Date DatePeremption = getDateAfterDays(DateAchatEnMilli,nbJours);// on calcule la date de permetpion en fonction de la date d'achat+nb de jour donné par l'utilisateur
				DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
				String Date_Peremption = dateFormat.format(DatePeremption);// date de peremtion au format jj/mm/aaaa
				
				long DatePeremtInMilli = DatePeremption.getTime();  // on converti la date de permeption en milliseconde
				valuesProduitsFinal.put("Date_Peremption_milli", DatePeremtInMilli);//et on le stocke en base
				//////////////////////////////////////////////////////////
				valuesProduitsFinal.put("Date_Peremption",Date_Peremption);
				boolean insertOk = objBd.InsertDonnéedansTable("produit_Enregistre", valuesProduitsFinal);
				if (insertOk){
					popUp("Insert Ok");
					adFiniOuPas.show();
				}else{
					popUp ("Insert Pas Ok");
				}
				//popUp("date peremption: "+Date_Peremption);
				objBd.close();
						
				
				
			}
		});
        ad.setNegativeButton("Non",null);
        this.setTitle("Récapitulatif");
        
        popUp("OnCreate-pageRecap");   
        
         
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
   			setContentView(R.layout.theme_bisounours_formulaire_entree_recap);
   			
   		}
   		if (EnTheme.Classique.getLib().equals(nomThemeChoisi)){
   			setContentView(R.layout.formulaire_entree_recap);
   			
   		}if(EnTheme.Fleur.getLib().equals(nomThemeChoisi)){
   			setContentView(R.layout.theme_fleur_formulaire_entree_recap);
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
	
	/** 
	 * This utility method returns a future date after number of days. 
	 * @param days 
	 * @return 
	 */  
	 public static Date getDateAfterDays(long dateEnMilli,int days) {  
	 long backDateMS = dateEnMilli + ((long)days) *24*60*60*1000;  
	 Date backDate = new Date();  
	 backDate.setTime(backDateMS);  
	 return backDate;  
	 }    
	
	
	 // updates the date we display in the TextView
  
	private void updateDisplay() {
    	objBd.open();
    	Categorie_Cochée = objBd.renvoiCategorieEtProduitCochée();
    	    	    	
    	String[] Colonnes ={"nom_marque_choisie","nom_produit","numero_Teinte","DateAchat","Duree_Vie"}; 
    	trousse_tempo = objBd.renvoi_liste_ValeurTroussetempo("trousse_tempo",Colonnes);
       
    	//CategorieRecap.setText(new StringBuilder().append("Categorie : ").append(Categorie_Cochée[1].toString().replace("[", "").replace("]","")+" / ").append(Categorie_Cochée[0].toString().replace("[", "").replace("]","")));
    	CategorieRecap.setText(new StringBuilder().append(Categorie_Cochée[0].toString().replace("[", "").replace("]","")));
    	MarqueRecap.setText(new StringBuilder().append(trousse_tempo[0].toString().replace("[", "").replace("]","")));
    	nomProduitRecap.setText(new StringBuilder().append(trousse_tempo[1].toString().replace("[", "").replace("]","")));
    	numeroTeinteRecap.setText(new StringBuilder().append(trousse_tempo[2].toString().replace("[", "").replace("]","")));
    	DateAchatRecap.setText(new StringBuilder().append(trousse_tempo[3].toString().replace("[", "").replace("]","")));
        dureeVieRecap.setText(new StringBuilder().append(trousse_tempo[4].toString().replace("[", "").replace("]","")).append(" mois"));
        objBd.close();
    }
 
   
    
public void onClick(View v) {
	
	if (v==BoutonValiderFinal){
		
		ad.show();
		
		
	}
}






public void popUp(String message) {
   // Toast.makeText(this, message, 1).show();
	}	

protected void onRestart() {
    super.onRestart();
    //popUp("onRestart()-PageRecap");
}

/**
 * Exécuté lorsque l'activité devient visible à l'utilisateur.
 *
 * La fonction onStart() est suivie de la fonction onResume().
 */
@Override
protected void onStart() {
    super.onStart();
   // popUp("onStart()-PageRecap");
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
    //popUp("onResume()-PageRecap");
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
    //popUp("onStop-PageRecap");	
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
    	Intent page3 = new Intent(this, formulaire_entree_page3.class);
    	
    	String NomProduit = nomProduitRecap.getText().toString();
    	String NomProduitUnique = NomProduit.substring(NomProduit.lastIndexOf(":")+1);
    	page3.putExtra("NomProduit", NomProduitUnique.trim());
    	
    	String NumTeinte = numeroTeinteRecap.getText().toString();
    	String NumTeinteUnique = NumTeinte.substring(NumTeinte.lastIndexOf(":")+1);
    	page3.putExtra("NumTeinte", NumTeinteUnique.trim());
    	
    	String DateAchat=DateAchatRecap.getText().toString();
    	String DateAchatUnique=DateAchat.substring(DateAchat.lastIndexOf(":")+1);
    	page3.putExtra("DateAchat", DateAchatUnique.trim());
    	
    	String DurreeVie = dureeVieRecap.getText().toString();
    	String DurreeVIeUnique = DurreeVie.substring(0,DurreeVie.lastIndexOf(" ")+1);
    	page3.putExtra("DurreeDeVie", DurreeVIeUnique.trim());
    	
    	String Marque = MarqueRecap.getText().toString();
    	String MarqueUnique=Marque.substring(Marque.lastIndexOf(":")+1);
    	page3.putExtra("MarqueChoisie", MarqueUnique.trim());
    
    	
    	page3.putExtra("LaunchByrecap",true);
    	
    	startActivity(page3);
        
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
	popUp("OnDestroy-PageRecap");	
	super.onDestroy();
	
	
}
		
}

