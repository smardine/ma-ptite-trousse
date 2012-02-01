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
		trans1 = new TranslateAnimation(320, 0, 0, 0); // de x=320 � x=0 et y=0
														// � y=0
		// param�trer l'endroit o� d�marre l'animation
		trans1.setStartOffset(320); // en px
		trans1.setFillAfter(true); // si on veut que l'objet reste � l'endroit
									// o� on l'a envoy� (ici 0)
		trans1.setDuration(time);
		// appliquer cette animation au bouton :
		// le bouton va se d�placer sur l'axe horizontal et va prendre 2s pour
		// se d�placer
		// il appara�t de la droite et va vers la gauche de l'�cran*/
	}

	/**
	 * @param time
	 */
	public void setBasversHaut(int time) {
		trans1 = new TranslateAnimation(00, 0, 480, 0); // de x=0 � x=0 et y=480
														// � y=0
		// param�trer l'endroit o� d�marre l'animation
		trans1.setStartOffset(480); // en px
		trans1.setFillAfter(false); // si on veut que l'objet reste � l'endroit
									// o� on l'a envoy� (ici 0)
		trans1.setDuration(250); // dure une 2 secondes
	}

	/**
	 * @return l'animation
	 */
	public Animation getAnim() {
		return trans1;
	}

}
