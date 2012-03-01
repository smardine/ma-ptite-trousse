package fr.smardine.matroussedemaquillage.mdl.cat;

public enum EnTypeCategorie {

	LEVRES, VISAGE, YEUX, AUTRE;

	public static EnTypeCategorie getEnumFromValue(String p_value) {
		for (EnTypeCategorie e : values()) {
			if (e.name().equals(p_value.toUpperCase())) {
				return e;
			}
		}
		return null;
	}

}
