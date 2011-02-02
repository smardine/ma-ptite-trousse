package fr.smardine.matroussedemaquillage.remplir;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import fr.smardine.matroussedemaquillage.R;
import fr.smardine.matroussedemaquillage.base.BDAcces;
import fr.smardine.matroussedemaquillage.note.note_page1;
import fr.smardine.matroussedemaquillage.param.tab_param;
import fr.smardine.matroussedemaquillage.recherche.recherchetabsbuttons;
import fr.smardine.matroussedemaquillage.variableglobale.EnTheme;




public class formulaire_entree_page3 extends Activity implements OnClickListener {
	Button BoutonValider3;
	
	
	private  BDAcces objBd;
	@SuppressWarnings("rawtypes")
	ArrayList[] TrousseTempo;
	//////////////////////
		private TextView mDateDisplay;
	    private Button mPickDate,BoutonAide;
	    private EditText nomProduit,numeroTeinte,dureeVie;

	    private int mYear;
	    private int mMonth;
	    private int mDay;
	  //  private int NbMoisChoisiBas=0,NbMoisChoisiHaut=0;

	    static final int DATE_DIALOG_ID = 0;
	    AlertDialog.Builder adManqueInfo,adAide;
	    String MarqueChoisie="";
	    String nomDuProduit="";
	    String numeroDeTeinte="";
	    String DateAchat="";
	    String DureeVie="";
	  
///////////////////////////
	
	
    /** Called when the activity is first created. */
  
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  ExceptionHandler.register(this, "http://simon.mardine.free.fr/trousse_maquillage/test/server.php","ma_ptite_trousse");
        choisiLeTheme();
        
        
        // capture our View elements
        mDateDisplay = (TextView) findViewById(R.id.dateDisplay_page3);
        mPickDate = (Button) findViewById(R.id.pickDate_page3);
        nomProduit = (EditText) findViewById(R.id.EditTextNom_page3);
        numeroTeinte = (EditText) findViewById(R.id.EditTextTeinte_page3);
        dureeVie = (EditText) findViewById(R.id.EditTextDureeDeVie_page3);
        BoutonValider3 = (Button)this.findViewById(R.id.ButtonValider3_page3);
        BoutonAide = (Button) this.findViewById(R.id.ButtonAide_page3);
        BoutonAide.setOnClickListener(this);
        
       /* nomProduit.addTextChangedListener(new TextWatcher(){
            
			@SuppressWarnings("unused")
			int len=0;
            @Override
            public void afterTextChanged(Editable s) { 
                String str = nomProduit.getText().toString(); 
                if(!str.equals("")){
                	
                	String str1 = UpperCaseWords(str);
                	
                	nomProduit.setText(""+str1+"");
                }
               
            }
            

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

             String str = nomProduit.getText().toString(); 
              len = str.length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {  
            }


        });*/
        
       
        dureeVie.addTextChangedListener(new TextWatcher() {
            @SuppressWarnings("unused")
			int len=0;
            @Override
            public void afterTextChanged(Editable s) { 
                String str = dureeVie.getText().toString(); 
                if(!str.equals("")){
                	int ValeurRentrée = Integer.parseInt(str);
                    if (ValeurRentrée>99){
                    	dureeVie.setText("99");
                    }
                    if (ValeurRentrée<=0){
                    	dureeVie.setText("1");
                    }
                }
               
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

             String str = dureeVie.getText().toString(); 
              len = str.length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {  
            }


        }); 
        
       
      /* Animlineaire anim = new Animlineaire (false,true);
       mDateDisplay.startAnimation(anim.getAnim());
       mPickDate.startAnimation(anim.getAnim());
       nomProduit.startAnimation(anim.getAnim());
       numeroTeinte.startAnimation(anim.getAnim());
       dureeVie.startAnimation(anim.getAnim());
       BoutonValider3.startAnimation(anim.getAnim());
       BoutonAide.startAnimation(anim.getAnim());*/
        
        adAide = new AlertDialog.Builder(this);
        adAide.setTitle("Aide");
       /* CharSequence[] items = { "Mascara (3 à 6 mois)", "Fond  de teint(6 à 12 mois )","Blush-Poudre (12 à 18 mois)","Fards (12 à 18 mois)","Crayons (12 à 36 mois)", "Rouge à lèvres (12 à 36 mois)", "Pinceau (nettoyage tout les mois)","Vernis à ongle (Voir sur la bouteille)"};
      /*  adAide.setSingleChoiceItems(items , -1, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item){
                /* User clicked on a radio button do some stuff */
        /*    	if (item==0){//Mascara
            		NbMoisChoisiBas=3;
            		NbMoisChoisiHaut=6;
            	}if (item==1){//fond de teint
            		NbMoisChoisiBas=6;
            		NbMoisChoisiHaut=12;
            	}
            	if (item==2){//blush-poudres
            		NbMoisChoisiBas=12;
            		NbMoisChoisiHaut=18;
            	}
            	if (item==3){//fards
            		NbMoisChoisiBas=12;
            		NbMoisChoisiHaut=18;
            	}
            	if (item==4){//crayons
            		NbMoisChoisiBas=12;
            		NbMoisChoisiHaut=36;
            	}
            	if (item==5){//Rouges à lèvres
            		NbMoisChoisiBas=12;
            		NbMoisChoisiHaut=36;
            	}
            	if (item==6){//pinceaux
            		NbMoisChoisiBas=1;
            		NbMoisChoisiHaut=1;
            	}
            	if (item==7){//Vernis
            		NbMoisChoisiBas=6;
            		NbMoisChoisiHaut=12;
            	}
            	
            }
        });*/
        
        adAide.setMessage("La durée de vie de vos produits de maquillage est inscrite au dos de l'emballage\n\nDurée de vie moyenne:\n" +
        		"Mascaras : 3 à 6 mois\n" +
        		"Fonds de teint : 6 à 12 mois\n" +
        		"Blush-Poudres :\n" +
        		"12 à 18 mois\n" +
        		"Fards : 12 à 18 mois\n" +
        		"Crayons :\n" +
        		"12 à 36 mois\n" +
        		"Rouges à lèvres : 12 à 36 mois\n" +
        		"Pinceaux : nettoyage tout les mois\n" +
        		"\n" +
        		"Attention, ces informations sont fournies à titre indicatif uniquement.\n" +
        		"Quelques conseils :\n" +
        		"Un produits périmé à une odeur et une texture inhabituelle.N'hesitez pas à jeter tout produit suspect, plus particulierement ceux en contact avec vos yeux.\n" +
        		"Stockez vos produits dans un endroits sec, à l'abri des fluctuations de température.");

        adAide.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
            	 
            	 // dureeVie.setText(""+NbMoisChoisiBas);
				
            	 }
        });

        
        // add a click listener to the button
        mPickDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });
        
        

        // get the current date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
       
        

        objBd = new BDAcces(this);
        BoutonValider3.setOnClickListener(this);
        
        adManqueInfo = new AlertDialog.Builder(this);
        adManqueInfo.setTitle("Attention");
        adManqueInfo.setIcon(R.drawable.ad_attention);
        adManqueInfo.setMessage("Vous avez oublié de rentrer certaines informations.");
        adManqueInfo.setPositiveButton("Ok",null);
        
        
       this.setTitle("Choix noms & dates");
        
       // popUp("OnCreate-page3");
         
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
   			setContentView(R.layout.theme_bisounours_formulaire_entree_page3);
   			
   		}
   		if (EnTheme.Classique.getLib().equals(nomThemeChoisi)){
   			setContentView(R.layout.formulaire_entree_page3);
   			
   		}if(EnTheme.Fleur.getLib().equals(nomThemeChoisi)){
   			setContentView(R.layout.theme_fleur_formulaire_entree_page3);
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
	/* public String UpperCaseWords(String line){ 
     	line = line.trim().toLowerCase(); 
     	String data[] = line.split("\\s"); 
     	line = ""; 
     	for(int i =0;i< data.length;i++) { 
     		if(data[i].length()>1) {
     			line = line + data[i].substring(0,1).toUpperCase()+data[i].substring(1)+" "; 
     			
     		}
     		else {
     			line = line + data[i].toUpperCase(); 
     			} 
     	}
     return line.trim(); 
     }*/
	
	protected Dialog onCreateDialog(int id) {
	    switch (id) {
	    case DATE_DIALOG_ID:
	        return new DatePickerDialog(this,mDateSetListener,mYear, mMonth, mDay);
	    }
	    return null;
	}
	 // updates the date we display in the TextView
    private void updateDisplay() {
    	
    	
        mDateDisplay.setText(
            new StringBuilder()
            		.append("Date choisie: ")
            		.append(mDay).append("-")
                    // Month is 0 based so add 1
                    .append(mMonth + 1).append("-")
                    .append(mYear).append(" "));
    }
 // the callback received when the user "sets" the date in the dialog
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateDisplay();
                }
            };
    
@SuppressWarnings("unused")
public void onClick(View v) {
	
	if (v==BoutonAide){
		adAide.show();
	}
	if (v==BoutonValider3){
		
		nomDuProduit = nomProduit.getText().toString().trim();
		numeroDeTeinte = numeroTeinte.getText().toString().trim();
		DateAchat = mDateDisplay.getText().toString().trim();
		DureeVie = dureeVie.getText().toString().trim();
		
		if (nomDuProduit.equals("")||numeroDeTeinte.equals("")||DateAchat.equals("")||DureeVie.equals("")){
			adManqueInfo.show();
		}
		else{
		objBd.open();
		
		//recuperation de la marque choisie
		//String[] marquechoisie = objBd.renvoi_liste_Valeur("trousse_tempo", "nom_marque_choisie");
		//String MarqueChoisie=marquechoisie[0];
		//MarqueChoisie=getIntent().getStringExtra("MarqueChoisie");
		ContentValues values = new ContentValues();
		values.put("nom_marque_choisie", MarqueChoisie);
		values.put("nom_produit", nomDuProduit);
		values.put("numero_teinte", numeroDeTeinte);
		values.put("DateAchat", DateAchat.substring(DateAchat.lastIndexOf(":")+1).trim());
		values.put("Duree_Vie", DureeVie);
		//String whereClause = "nom_marque_choisie=?";
		//String[] whereArgs = new String[]{MarqueChoisie};
		int nbLigneSuppr=objBd.deleteTable("trousse_tempo","1",null);
		//int nbDechampModifié = objBd.majTable("trousse_tempo", values, whereClause, whereArgs);
		boolean insertOk = objBd.InsertDonnéedansTable("trousse_tempo", values);
		
		objBd.close();
		Intent intent = new Intent(formulaire_entree_page3.this, formulaire_entree_recap.class);
		//on demarre la nouvelle activité
		
		startActivity(intent);
        
		 finish();
		
		}
		
	}
	}






public void popUp(String message) {
   // Toast.makeText(this, message, 1).show();
	}	

protected void onRestart() {
    super.onRestart();
    popUp("onRestart()-Page3");
}

/**
 * Exécuté lorsque l'activité devient visible à l'utilisateur.
 *
 * La fonction onStart() est suivie de la fonction onResume().
 */
@Override
protected void onStart() {
    super.onStart();
    popUp("onStart()-Page3");
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
    boolean IsCalledFromPageRecap=getIntent().getBooleanExtra ("LaunchByrecap",false);
    boolean IsCalledFromPage1=getIntent().getBooleanExtra ("LaunchByPage1",false);
    boolean IsCalledFromDupplique=getIntent().getBooleanExtra("LaunchByDupplique", false);
    		
    	if (IsCalledFromPage1||IsCalledFromDupplique){
    	
    	MarqueChoisie=getIntent().getStringExtra("MarqueChoisie").trim();
    	DateAchat=getIntent().getStringExtra("DateAchat").trim();
    	nomDuProduit=getIntent().getStringExtra("NomProduit").trim();
    	numeroDeTeinte=getIntent().getStringExtra("NumTeinte").trim();
        DureeVie=getIntent().getStringExtra("DurreeDeVie").trim();
        if (DateAchat.equals("")){
        	updateDisplay();	
        }else{
        	mDateDisplay.setText(
                    new StringBuilder()
                    		.append("Date choisie: ")
                    		.append(DateAchat));	
        }
        if (!nomDuProduit.equals("")){
        	nomProduit.setText(nomDuProduit);	
        }
        if (!numeroDeTeinte.equals("")){
        	numeroTeinte.setText(numeroDeTeinte);
        }
        if (!DureeVie.equals("")){
        	dureeVie.setText(DureeVie);
        }
    }
    
    if (IsCalledFromPageRecap){
    	MarqueChoisie=getIntent().getStringExtra("MarqueChoisie").trim();
    	DateAchat=getIntent().getStringExtra("DateAchat").trim();
    	nomDuProduit=getIntent().getStringExtra("NomProduit").trim();
    	numeroDeTeinte=getIntent().getStringExtra("NumTeinte").trim();
        DureeVie=getIntent().getStringExtra("DurreeDeVie").trim();
        if (DateAchat.equals("")){
        	updateDisplay();	
        }else{
        	mDateDisplay.setText(
                    new StringBuilder()
                    		.append("Date choisie: ")
                    		.append(DateAchat));	
        }
        if (!nomDuProduit.equals("")){
        	nomProduit.setText(nomDuProduit);	
        }
        if (!numeroDeTeinte.equals("")){
        	numeroTeinte.setText(numeroDeTeinte);
        }
        if (!DureeVie.equals("")){
        	dureeVie.setText(DureeVie);
        }
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
  //  popUp("onStop-Page3");	
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
    	Intent page2 = new Intent(this, formulaire_entree_page1bis.class);
     	page2.putExtra("MarqueChoisie", MarqueChoisie.trim());
    	page2.putExtra("DurreeDeVie", DureeVie.trim());
    	page2.putExtra("DateAchat", DateAchat.trim());
    	page2.putExtra("NumTeinte", numeroDeTeinte.trim());
    	page2.putExtra("NomProduit", nomDuProduit.trim());
    	page2.putExtra ("LaunchByPage3",true);
    	startActivity(page2);
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
//	popUp("OnDestroy");	
	super.onDestroy();
	
	
}
		
}

