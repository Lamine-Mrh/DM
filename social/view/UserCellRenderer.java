/**
 * 
 */
package social.view;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import social.model.User;

/**
 * Gestion de l'affichage des utilisateurs dans la liste affichée par une
 * FollowUserView. Contrôle également l'activation/désactivation des boutons
 * "Follow" et "UnFollow" de la FollowUserView. Le bouton "Follow" n'est activé
 * que lorsqu'un utilisateur est sélectionné, que l'utilisateur connecté n'est
 * pas déjà abonné à cet utilisateur et que l'utilisateur sélectionné est
 * différent de l'utilisateur connecté.
 * 
 * @invariant getUser() != null;
 * @invariant getView() != null;
 * 
 * @author Marc Champesme
 * @since 2/08/2023
 * @version 13/11/2023
 * 
 */
public class UserCellRenderer extends JLabel implements ListCellRenderer<User> {
	private static final long serialVersionUID = 4178535808203619078L;
	private User theUser;
	private FollowUserView myView;

	/**
	 * Initialise un nouveau UserCellRenderer pour la vue spécifiée et l'utilisateur
	 * spécifié. L'utilisateur spécifié est considéré comme l'utilisateur
	 * actuellement connecté.
	 * 
	 * @param myView la vue à laquelle ce  UserCellRenderer est associé
	 * @param theUser l'utilisateur connecté
	 * 
	 * @throws NullPointerException si la vue spécifiée ou l'utilisateur spécifié est null
	 * 
	 * @requires myView != null;
	 * @requires theUser != null;
	 * @ensures getView().equals(myView);
	 * @ensures getUser().equals(theUser);
	 */
	public UserCellRenderer(FollowUserView myView, User theUser) {
		if (myView == null || theUser == null) {
			throw new NullPointerException();
		}
		this.myView = myView;
		this.theUser = theUser;
	}

	public FollowUserView getView() {
		return myView;
	}

	public User getUser() {
		return theUser;
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends User> list, User aUser, int index, boolean isSelected,
			boolean cellHasFocus) {
		String s = aUser.getName();
		if (theUser.equals(aUser)) {
			s = s + " (me)";
		}
		if (theUser.hasSubscriptionTo(aUser)) {
			s = s + " subscribed";
		}
		setText(s);
		setOpaque(true);
		if (isSelected) {
			setBackground(Color.DARK_GRAY);
			setForeground(Color.WHITE);
			if (theUser.equals(aUser)) {
				myView.setFollowEnabled(false);
				myView.setUnFollowEnabled(false);
			} else if (theUser.hasSubscriptionTo(aUser)) {
				myView.setFollowEnabled(false);
				myView.setUnFollowEnabled(true);
			} else {
				myView.setFollowEnabled(true);
				myView.setUnFollowEnabled(false);
			}
		} else {
			setBackground(Color.WHITE);
			setForeground(Color.BLACK);

		}
		return this;
	}

}
