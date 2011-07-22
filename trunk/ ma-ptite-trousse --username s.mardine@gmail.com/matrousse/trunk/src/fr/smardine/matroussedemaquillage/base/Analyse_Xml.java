package fr.smardine.matroussedemaquillage.base;

import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.content.Context;
import android.util.Log;
import fr.smardine.matroussedemaquillage.R;

public class Analyse_Xml {
	
	@SuppressWarnings("rawtypes")
		public static  ArrayList<ArrayList> renvoi_liste_script_xml(String Element) throws Exception{
		    	 
	    	  //***********************création de notre tableau dinamique   	  
	    	  ArrayList<ArrayList> aTableRetour = new ArrayList<ArrayList>();
	    	  
	    	  //************************récupération du flux wml
	    	  URL myURL = new URL("http://simon.mardine.free.fr/trousse_maquillage/bdd.xml");
	    	  DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();
	          // création d'un constructeur de documents
	          DocumentBuilder constructeur = fabrique.newDocumentBuilder();
	          //*****************lecture du flux xml**************
	          Document document = constructeur.parse(myURL.openStream());
	          Element racine = document.getDocumentElement();
	          NodeList liste = racine.getElementsByTagName(Element);
	          //remplissage de mon tableau
	          for(int i=0; i<liste.getLength(); i++){
	        	  ArrayList<String> aTableauTmp =  new ArrayList<String>(); 
	        	  Element E1= (Element) liste.item(i);
	        	  //aTableRetour[i]= "";
	        	  aTableauTmp.add(E1.getAttribute("SCRIPT"));
	        	//  aTableauTmp.add(E1.getAttribute("ordre"));
	        	  aTableRetour.add(aTableauTmp);
	          }
	          
	          return aTableRetour;
	    	  
	      }
		
		  
		  @SuppressWarnings("rawtypes")
		public static  ArrayList<ArrayList> renvoi_liste_script_maj_xml(String Element) throws Exception{
		    	 
	    	  //***********************création de notre tableau dinamique   	  
	    	  ArrayList<ArrayList> aTableRetour = new ArrayList<ArrayList>();
	    	  
	    	  //************************récupération du flux wml
	    	  URL myURL = new URL("http://simon.mardine.free.fr/trousse_maquillage/maj_bdd.xml");
	    	  DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();
	          // création d'un constructeur de documents
	          DocumentBuilder constructeur = fabrique.newDocumentBuilder();
	          //*****************lecture du flux xml**************
	         // Document document = constructeur.parse(myURL.openStream());
	         // String cheminXml = "./data/data/"+PackageName;
	          //File chemin = new File (cheminXml);
	          Document document = constructeur.parse(myURL.openStream());
	          Element racine = document.getDocumentElement();
	          NodeList liste = racine.getElementsByTagName(Element);
	          //remplissage de mon tableau
	          for(int i=0; i<liste.getLength(); i++){
	        	  ArrayList<String> aTableauTmp =  new ArrayList<String>(); 
	        	  Element E1= (Element) liste.item(i);
	        	  //aTableRetour[i]= "";
	        	  aTableauTmp.add(E1.getAttribute("SCRIPT"));
	        	//  aTableauTmp.add(E1.getAttribute("ordre"));
	        	  aTableRetour.add(aTableauTmp);
	          }
	          
	          return aTableRetour;
	    	  
	      }
		  
		  private void readFileFromLocal( Context context )
		  {
		  FileInputStream istream = null;
		  try {
		  istream = (FileInputStream) context.getResources().openRawResource(R.raw.maj_bdd);

		  /* Get a SAXParser from the SAXPArserFactory. */
		  SAXParserFactory spf = SAXParserFactory.newInstance();
		  SAXParser sp = spf.newSAXParser();
		  /* Get the XMLReader of the SAXParser we created. */
		  XMLReader xr = sp.getXMLReader();
		  /* Create a new ContentHandler and apply it to the XML-Reader*/
		  ExampleHandler myExampleHandler = new ExampleHandler();
		  xr.setContentHandler(myExampleHandler);
		  Log.v(logCat, "Calling parse() in ReadTourFromLocal: "+filename);
		  /* Parse the xml-data from our URL. */

		  xr.parse(new InputSource(istream));

		  Log.v(logCat, "Returned from Parse: "+filename);
		  /* Parsing has finished. */
		  int xcount = myExampleHandler.getNumber();

		  } catch (Exception FileNotFoundException){
		  Log.v(logCat, "File not found exception!:" + filename);
		  }
		  }

}
