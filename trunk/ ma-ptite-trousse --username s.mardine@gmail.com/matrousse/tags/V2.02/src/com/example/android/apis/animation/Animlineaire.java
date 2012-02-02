package com.example.android.apis.animation;

import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * @author smardine
 */
public class Animlineaire extends Animation {
	TranslateAnimation trans1;

	/**
	 * 
	 */
	public Animlineaire() {
	}

	/**
	 * @param time
	 */
	public void setDroiteversGauche(int time) {
		trans1 = new TranslateAnimation(320, 0, 0, 0); // de x=320 à x=0 et y=0
														// à y=0
		// paramètrer l'endroit où démarre l'animation
		trans1.setStartOffset(320); // en px
		trans1.setFillAfter(true); // si on veut que l'objet reste à l'endroit
									// où on l'a envoyé (ici 0)
		trans1.setDuration(time);
		// appliquer cette animation au bouton :
		// le bouton va se déplacer sur l'axe horizontal et va prendre 2s pour
		// se déplacer
		// il apparaît de la droite et va vers la gauche de l'écran*/
	}

	/**
	 * @param time
	 */
	public void setBasversHaut(int time) {
		trans1 = new TranslateAnimation(00, 0, 480, 0); // de x=0 à x=0 et y=480
														// à y=0
		// paramètrer l'endroit où démarre l'animation
		trans1.setStartOffset(480); // en px
		trans1.setFillAfter(false); // si on veut que l'objet reste à l'endroit
									// où on l'a envoyé (ici 0)
		trans1.setDuration(250); // dure une 2 secondes
	}

	/**
	 * @return l'animation
	 */
	public Animation getAnim() {
		return trans1;
	}

}
