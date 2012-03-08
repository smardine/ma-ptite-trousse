package fr.smardine.matroussedemaquillage.alertDialog.type;

public enum EnTypeAlertDialogAide {

	AIDE_PAGE3(
			"Aide",
			"La durée de vie de vos produits de maquillage est inscrite au dos de l'emballage\n\nDurée de vie moyenne:\n"
					+ "Mascaras : 3 à 6 mois\n"
					+ "Fonds de teint : 6 à 12 mois\n"
					+ "Blush-Poudres :\n"
					+ "12 à 18 mois\n"
					+ "Fards : 12 à 18 mois\n"
					+ "Crayons :\n"
					+ "12 à 36 mois\n"
					+ "Rouges à lèvres : 12 à 36 mois\n"
					+ "Pinceaux : nettoyage tout les mois\n"
					+ "\n"
					+ "Attention, ces informations sont fournies à titre indicatif uniquement.\n"
					+ "Quelques conseils :\n"
					+ "Un produits périmé à une odeur et une texture inhabituelle.N'hesitez pas à jeter tout produit suspect, plus particulierement ceux en contact avec vos yeux.\n"
					+ "Stockez vos produits dans un endroits sec, à l'abri des fluctuations de température.",
			"Ok");

	private String titre;
	private String message;
	private String txtOkButton;

	EnTypeAlertDialogAide(String p_titre, String p_message, String p_txtOkButton) {
		this.titre = p_titre;
		this.message = p_message;
		this.txtOkButton = p_txtOkButton;
	}

	public String getTitre() {
		return titre;
	}

	public String getMessage() {
		return message;
	}

	public String getTxtOkButton() {
		return txtOkButton;
	}

}
