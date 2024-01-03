/**
 * 
 */
package social.view;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import social.controller.FollowUserController;
import social.controller.MessageEditController;
import social.model.User;

/**
 * Fenêtre permettant de gérer la liste des abonnements d'un utilisateur. Une
 * liste de l'ensemble des utilisateurs ayant un compte dans l'application
 * Microdon est affichée et l'utilisateur connecté peut choisir de s'abonner ou
 * de se désabonner aux utilisateurs de son choix. Le rendu de l'affichage des
 * utilisateurs dans la liste est géré par la classe UserCellRenderer et la
 * gestion des commandes utilisateur est prise en charge par la classe
 * FollowUserView.
 * 
 * @invariant getUser() != null;
 * @invariant getController() != null;
 * @invariant getController().getUser().equals(getUser());
 * @invariant getController().getView().equals(this);
 * 
 * @author Marc Champesme
 * @since 2/08/2023
 * @version 13/11/2023
 * 
 */
public class FollowUserView extends JPanel {
	private static final long serialVersionUID = -6444217734849732926L;
	private User theUser;
	private FollowUserController msgCtrl;
	private JList<User> userList;
	private JButton unFollowButton;
	private JButton followButton;

	/**
	 * Crée et affiche une fenêtre permettant de gérer les abonnements de
	 * l'utilisateur spécifié.
	 * 
	 * @throws NullPointerException si l'utilisateur spécifié est null
	 * 
	 * @requires theUser != null;
	 * @ensures getUser().equals(theUser);
	 * @ensures getController().getUser().equals(theUser);
	 */
	public FollowUserView(User theUser) {
		super(new BorderLayout());
		if (theUser == null) {
			throw new NullPointerException();
		}
		this.theUser = theUser;

		add(createListPanel(), BorderLayout.CENTER);

		msgCtrl = new FollowUserController(this);
		add(createButtonPanel(), BorderLayout.SOUTH);

		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
	}

	/**
	 * Renvoie l'utilisateur dont cette fenêtre gère les abonnements.
	 * 
	 * @return l'utilisateur dont cette fenêtre gère les abonnements
	 * 
	 * @pure
	 */
	public User getUser() {
		return this.theUser;
	}

	/**
	 * Renvoie le controller exécutant les commandes de cette vue.
	 * 
	 * @return le controller exécutant les commandes de cette vue
	 * 
	 * @pure
	 */
	public FollowUserController getController() {
		return msgCtrl;
	}

	/**
	 * Renvoie l'utilisateur sélectionné dans la liste affichée ou null si aucun
	 * utilisateur n'est sélectionné.
	 * 
	 * @return l'utilisateur sélectionné dans la liste affichée ou null si aucun
	 *         utilisateur n'est sélectionné
	 * 
	 * @pure
	 */
	public User getSelectedUser() {
		return userList.getSelectedValue();
	}

	/**
	 * Active ou désactive le boutton permettant de s'abonner à un utilisateur.
	 * 
	 * @param b indique si le bouton doit être activé (valeur true) ou désactivé
	 *          (valeur false)
	 */
	public void setFollowEnabled(boolean b) {
		followButton.setEnabled(b);
	}

	/**
	 * Active ou désactive le boutton permettant de se désabonner à un utilisateur.
	 * 
	 * @param b indique si le bouton doit être activé (valeur true) ou désactivé
	 *          (valeur false)
	 */
	public void setUnFollowEnabled(boolean b) {
		unFollowButton.setEnabled(b);
	}

	private JScrollPane createListPanel() {
		DefaultListModel<User> listModel = new DefaultListModel<User>();
		for (User u : User.getAllUser()) {
			listModel.addElement(u);
		}
		userList = new JList<User>(listModel);
		userList.setCellRenderer(new UserCellRenderer(this, theUser));
		userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		return new JScrollPane(userList);
	}

	private JPanel createButtonPanel() {
		JPanel p = new JPanel();
		JButton backButton = new JButton(FollowUserController.BACK_CMD);
		unFollowButton = new JButton(FollowUserController.UNFOLLOW_CMD);
		followButton = new JButton(FollowUserController.FOLLOW_CMD);

		backButton.setActionCommand(FollowUserController.BACK_CMD);
		unFollowButton.setActionCommand(FollowUserController.UNFOLLOW_CMD);
		followButton.setActionCommand(FollowUserController.FOLLOW_CMD);
		backButton.addActionListener(msgCtrl);
		unFollowButton.addActionListener(msgCtrl);
		followButton.addActionListener(msgCtrl);
		followButton.setEnabled(false);
		unFollowButton.setEnabled(false);

		p.add(backButton);
		p.add(unFollowButton);
		p.add(followButton);

		return p;
	}

}
