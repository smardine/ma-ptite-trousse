package fr.smardine.matroussedemaquillage;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;




public class ReadFile {
	
	private static final long serialVersionUID = 1L;
	
	/**
     * Lecture ligne à ligne d'un fichier texte et affichage dans une jList
     * @param chemin -String le chemin du fichier texte
     * @param listModel -DefaultModelList le model de liste
     * @param nbLigne -JLabel sert a afficher le nb de ligne
     * @param nbAdresse -int le nb d'adresse trouvée.
     */

	public static int ReadLine(String chemin) {
		 
		  int nbAdresse=0;
		 			  try{
					    // Open the file that is the first 
					    // command line parameter
					    FileInputStream fstream = new FileInputStream(chemin);
					    // Get the object of DataInputStream
					    DataInputStream in = new DataInputStream(fstream);
					    BufferedReader br = new BufferedReader(new InputStreamReader(in));
					    String strLine;
					    //Read File Line By Line
					    while ((strLine = br.readLine()) != null)   {
					        	
					      // Print the content on the console
					     // System.out.println (strLine);
					      
					      if (!strLine.equals(""))
					      {
					      //listModel.addElement (strLine);
					      nbAdresse++;
					      }
					    }
					    //Close the input stream
					    in.close();
					    
					    }catch (Exception e){//Catch exception if any
					      System.err.println("Error: " + e.getMessage());
					    }
					return nbAdresse;  
			  }
		
		  
		 
		
			  
	/**
     * Trouver une chaine de caracteres dans un fichier
     * @param cheminFichier -String le chemin du fichier
     * @param OccurToFind -String la chaine a trouver ex "abc@hotmail.com"
     * @return result -boolean vrai si on trouve la chaine de caracteres.
     */
	public static boolean FindOccurInFile (String cheminFichier, String OccurToFind){
		 
		String line = null;
		boolean result=false;
		
		try
		   {
		   BufferedReader br = new BufferedReader (new FileReader(cheminFichier));
		
		 int i = 1; //initialisation du numero de ligne
		 while ((line = br.readLine()) != null)
		   {
		     if ( line.indexOf(OccurToFind) != -1)
		     {
		     System.out.println("Mot trouve a la ligne " + i );
		     result=true;
		     return result;
		     }
		     i++;
		   }	   
		 br.close();
		}
		 catch(FileNotFoundException exc) { System.out.println("File not found" );  }
		 catch(IOException ioe) { System.out.println("Erreur IO" ); }
		return result;
				 
		}





	public static int compteNbLigne(String chemin) {
		int nbLigne=0;
		try{
		    // Open the file that is the first 
		    // command line parameter
		    FileInputStream fstream = new FileInputStream(chemin);
		    // Get the object of DataInputStream
		    DataInputStream in = new DataInputStream(fstream);
		    BufferedReader br = new BufferedReader(new InputStreamReader(in));
		    String strLine;
		    //Read File Line By Line
		    while ((strLine = br.readLine()) != null)   {
		      // Print the content on the console
		     // System.out.println (strLine);
		      
		      if (!strLine.equals(""))
		      {	    
		    	  nbLigne++;
		      }
		    }
		    //Close the input stream
		    in.close();
		    
		    }catch (Exception e){//Catch exception if any
		      System.err.println("Error: " + e.getMessage());
		     /* JOptionPane.showMessageDialog(null, "Erreur : "+ e,
						"Erreur", JOptionPane.ERROR_MESSAGE);
		      try {
					Historique.ecrire("Erreur : "+ e);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
		      return -1;
		    }
		return nbLigne-1;  
		}


	@SuppressWarnings("unused")
	public static int FichierReadLineEtInsereEnBase(String chemin,
		int nbligneAimporter) throws IOException {
		int progression=0;
		
		int ID_SAUVEGARDE=0;
		long DATE_FICHIER=0;
		String EMPLACEMENT_FICHIER="";
		int nbLigne=0,nbDinsert=0;
		DataInputStream in = null;
		BufferedReader br;
		 
		    // Open the file that is the first 
		    // command line parameter
		    FileInputStream fstream;
			try {
				fstream = new FileInputStream(chemin);
				 // Get the object of DataInputStream
			    in = new DataInputStream(fstream);
			    br = new BufferedReader(new InputStreamReader(in));
			    String strLine;
			    
			    //Read File Line By Line
			    try {
					while ((strLine = br.readLine()) != null)   {
					    	
						if (nbLigne>=1){//si c'est la deuxieme ligne (ou plus) (la premiere contient les différents champs)
							strLine = strLine.replace("(", " ");
							strLine= strLine.replace(")", " ");
							strLine = strLine.trim();
							String [] tabChaine = strLine.split(";");
							ID_SAUVEGARDE = Integer.parseInt(tabChaine[0].trim());
							DATE_FICHIER = Long.parseLong(tabChaine[1].trim());
							EMPLACEMENT_FICHIER = tabChaine[2].trim();
							
					    	    	
							String InsertFichier ="INSERT INTO FICHIER (ID_SAUVEGARDE, DATE_FICHIER, EMPLACEMENT_FICHIER) " +
									"VALUES ("+ID_SAUVEGARDE+","+DATE_FICHIER+",'"+EMPLACEMENT_FICHIER+"')";
									 
							//Historique.ecrire("Insertion d'un fichier sauvegardé avec la requete : "+InsertFichier);
							
							/*boolean resultInsereCategorie = GestionDemandes.executeRequete(InsertFichier);
								if (resultInsereCategorie==false){
									Historique.ecrire("Insertion d'un fichier sauvegardé avec la requete : "+InsertFichier);
									Historique.ecrire("Insertion d'un fichier sauvegardé echoué");
									nbDinsert--;
								}else {
									//Historique.ecrire("Insertion d'un fichier sauvegardé réussi");
									nbDinsert++;
								}
								nbLigne++;
								progression = (100*nbLigne)/nbligneAimporter;
						    	progress.setValue (progression);
						    	progress.setString ("Import Fichier sauvegardé " +progression+" %");*/

							}else nbLigne++;
			
						}
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					//Historique.ecrire("erreur à l'importation : " +e);
					e.printStackTrace();
					return -1;
				} catch (IOException e) {
					// TODO Auto-generated catch block
				//	Historique.ecrire("erreur à l'importation : " +e);
					e.printStackTrace();
					return -1;
				}
			    	
			    	
			    	/*nbLigne++;
			    	progression = (100*nbLigne)/nbligneAimporter;
			    	progress.setValue (progression);
			    	progress.setString ("Import Fichier sauvegardé " +progression+" %");*/
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//Historique.ecrire("erreur à l'importation : " +e);
				return -1;
			}
		   
		    	
		    	
		    	
		    
		    //Close the input stream
		    try {
		    	in.close();
				br.close();
				fstream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return -1;
			}
		    
		    	    
		    return nbDinsert;
	}

	
	@SuppressWarnings("unused")
	public static int ExclutReadLineEtInsereEnBase(String chemin,
			int nbligneAimporter) {
			int progression=0;
			
			String EMPLACEMENT_FICHIER="";
			int nbLigne=0,nbDinsert=0;
			DataInputStream in = null;
			BufferedReader br;
			 
			    // Open the file that is the first 
			    // command line parameter
			    FileInputStream fstream;
				try {
					fstream = new FileInputStream(chemin);
					 // Get the object of DataInputStream
				    in = new DataInputStream(fstream);
				    br = new BufferedReader(new InputStreamReader(in));
				    String strLine;
				    
				    //Read File Line By Line
				    try {
						while ((strLine = br.readLine()) != null)   {
						    	
							if (nbLigne>=1){//si c'est la deuxieme ligne (ou plus) (la premiere contient les différents champs)
								strLine = strLine.replace("(", " ");
								strLine= strLine.replace(")", " ");
								strLine = strLine.trim();
								String [] tabChaine = strLine.split(";");
								EMPLACEMENT_FICHIER = tabChaine[0];
								
						    	    	
								String InsertExclut ="INSERT INTO LISTE_EXCLUT (EMPLACEMENT_FICHIER) " +
										"VALUES ('"+EMPLACEMENT_FICHIER+"')";
										 
								
								
								/*boolean resultInsereCategorie = GestionDemandes.executeRequete(InsertExclut);
									if (resultInsereCategorie==false){
										Historique.ecrire("Insertion d'un emplacement exclut avec la requete : "+InsertExclut);
										Historique.ecrire("Insertion d'un emplacment exclut echoué");
										nbDinsert--;
									}else {
										//Historique.ecrire("Insertion d'un fichier exclut réussi");
										nbDinsert++;
									}*/
									
									nbLigne++;
							    	progression = (100*nbLigne)/nbligneAimporter;
							    	/*progress.setValue (progression);
							    	progress.setString ("Import emplacement exclut " +progression+" %");*/

								}else nbLigne++;
				
							}
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return -1;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return -1;
					}
				    	
				    	
				    	/*nbLigne++;
				    	progression = (100*nbLigne)/nbligneAimporter;
				    	progress.setValue (progression);
				    	progress.setString ("Import Fichier exclut " +progression+" %");*/
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return -1;
				}
			   
			    	
			    	
			    	
			    
			    //Close the input stream
			    try {
			    	in.close();
					br.close();
					fstream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return -1;
				}
			    
			    	    
			    return nbDinsert;
		}




	





	





	public static String ReadLine(File CheminDuFichier) {
		DataInputStream in = null;
		BufferedReader br;
		 
		    // Open the file that is the first 
		    // command line parameter
		    FileInputStream fstream = null;
			
				try {
					fstream = new FileInputStream(CheminDuFichier);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					return null;
				}
				 // Get the object of DataInputStream
			    in = new DataInputStream(fstream);
			    br = new BufferedReader(new InputStreamReader(in));
			    String strLine = null;
				try {
					strLine = br.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					return null;
				}
	
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					return null;
				}
			
		return strLine;
	
		}
	
}






	

		

	