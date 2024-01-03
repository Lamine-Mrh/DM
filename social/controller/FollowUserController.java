/**
 * 
 */
package social.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import social.Microdon;
import social.model.User;
import social.view.FollowUserView;

/**
 * Gère les commandes utilisateur (abonnement/désabonnement à un compte) d'une
 * instance de FollowUserView.
 * 
 * @invariant getView() != null;
 * @invariant getUser() != null;
 * @invariant getView().getUser().equals(getUser());
 * 
 * @author Marc Champesme
 * @since 2/08/2023
 * @version 29/12/2023
 * 
 */
public class FollowUserController implements ActionListener {
	/**
	 * Commande pour s'abonner à un utilisateur.
	 */
	public static final String FOLLOW_CMD = "Follow";
	/**
	 * Commande pour se désabonner d'un utilisateur.
	 */
	public static final String UNFOLLOW_CMD = "Unfollow";
	/**
	 * Commande pour quitter la vue Follow User et revenir à la vue affichant le fil
	 * d'actualité.
	 */
	public static final String BACK_CMD = "Back";

	private FollowUserView myView;
	private JFrame controllingFrame;

	/**
	 * Initialise un contrôleur pour les commandes associées à la FollowUserView
	 * spécifiée.
	 * 
	 * @param myView la vue à laquelle ce contrôleur est associé
	 * 
	 * @throws NullPointerException si la vue spécifiée est null
	 * 
	 * @requires myView != null;
	 * @ensures getView().equals(myView);
	 */
	public FollowUserController(FollowUserView myView) {
		if (myView == null) {
			throw new NullPointerException();
		}
		this.myView = myView;
		controllingFrame = Microdon.getTheInstance().getFrame();
	}

	/**
	 * Renvoie la FollowUserView dont ce contrôleur gère les commandes.
	 * 
	 * @return la FollowUserView dont ce contrôleur gère les commandes
	 * 
	 * @pure
	 */
	public FollowUserView getView() {
		return myView;
	}
	/**
	 * Renvoie l'utilisateur dont ce controller gère les abonnements.
	 * 
	 * @return l'utilisateur dont ce controller gère les abonnements
	 * 
	 * @pure
	 */
	public User getUser() {
		return myView.getUser();
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
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (FOLLOW_CMD.equals(e.getActionCommand())) {
			User selectedUser = myView.getSelectedUser();
			if (selectedUser == null) {
				JOptionPane.showMessageDialog(controllingFrame, "You must select a user before.", "Error Message",
						JOptionPane.ERROR_MESSAGE);

			} else {
				if (selectedUser.equals(myView.getUser())) {
					JOptionPane.showMessageDialog(controllingFrame, "You can't follow yourself!", "Error Message",
							JOptionPane.ERROR_MESSAGE);
				} else {
					myView.getUser().addSubscriptionTo(selectedUser);
					myView.repaint();
				}
			}
			return;
		}
		if (UNFOLLOW_CMD.equals(e.getActionCommand())) {
			User selectedUser = myView.getSelectedUser();
			if (selectedUser == null) {
				JOptionPane.showMessageDialog(controllingFrame, "You must select a user before.", "Error Message",
						JOptionPane.ERROR_MESSAGE);

			} else {
				myView.getUser().removeSubscriptionTo(selectedUser);
			}
			myView.repaint();
			return;
		}
		if (BACK_CMD.equals(e.getActionCommand())) {
			Microdon.getTheInstance().createAndShowMessageListPanel();
			return;
		}
	}

}
