/**
 * 
 */
package social.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import social.Microdon;
import social.model.SimplePost;
import social.model.User;
import social.view.MessageEditView;

/**
 * Gère les commandes utilisateur (édition d'un nouveau message) d'une
 * instance de MessageEditView.
 *
 * @invariant getView() != null;
 * @invariant getUser() != null;
 * @invariant getView().getUser().equals(getUser());
 *
 * @author Marc Champesme
 * @since 2/08/2023
 * @version 10/12/2023
 *
 */
public class MessageEditController implements ActionListener {
	/**
	 * Commande validant le texte du message pour création d'un nouveau
	 * Post et revenant à la vue du fil d'actualité.
	 */
	public final static String OK_CMD = "Ok";
	/**
	 * Commande anulant la saisie du message et revenant à la vue du fil
	 * d'actualité.
	 */
	public final static String CANCEL_CMD = "Cancel";
	
	private MessageEditView myView;
	
	/**
	 * Initialise un contrôleur pour les commandes associées à la MessageEditView
	 * spécifiée.
	 *
	 * @param myView la vue à laquelle ce contrôleur est associé
	 *
	 * @throws NullPointerException si la vue spécifiée est null
	 *
	 * @requires myView != null;
	 * @ensures getView().equals(myView);
	 * @ensures getUser().equals(myView.getUser());
	 */
	public MessageEditController(MessageEditView myView) {
		if (myView == null) {
			throw new NullPointerException();
		}
		this.myView = myView;
	}

	/**
	 * Renvoie l'utilisateur dont la vue associée doit afficher le fil d'actualité.
	 * 
	 * @return l'utilisateur dont la vue associée doit afficher le fil d'actualité
	 * 
	 * @pure
	 */
	public User getUser() {
		return this.myView.getUser();
	}
	
	/**
	 * Renvoie la MessageEditView dont ce contrôleur gère les commandes.
	 *
	 * @return la MessageEditView dont ce contrôleur gère les commandes
	 *
	 * @pure
	 */
	public MessageEditView getView() {
		return myView;
	}

	/**
	 * Exécute la commande associée à l'évenement spécifié.
	 *
	 * @param e évenement indiquant la commande à exécuter
	 *
	 * @throws NullPointerException si l'ActionEvent spécifié est null
	 *
	 * @requires e != null;
	 *
	 * @pure
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (OK_CMD.equals(e.getActionCommand())) {
			myView.getUser().addPost(new SimplePost(myView.getText()));
			Microdon.getTheInstance().createAndShowMessageListPanel();
			return;
		}
		if (CANCEL_CMD.equals(e.getActionCommand())) {
			Microdon.getTheInstance().createAndShowMessageListPanel();
			return;
		}	
	}

}
