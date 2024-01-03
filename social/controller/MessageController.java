/**
 * 
 */
package social.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ListIterator;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import social.Microdon;
import social.model.FusionSortedIterator;
import social.model.NewsFeed;
import social.model.Post;
import social.model.RePost;
import social.model.User;
import social.view.MessageView;

/**
 * Gère les commandes utilisateur d'une instance de MessageView (parcours du fil
 * d'actualité).
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
public class MessageController implements ActionListener {
	public final static String PREV_CMD = "Previous";
	public final static String NEXT_CMD = "Next";
	public final static String LIKE_CMD = "Like";
	public final static String NEW_CMD = "New Message";
	public final static String REPOST_CMD = "Re-post";
	public final static String FOLLOW_CMD = "Follow";
	public final static String LOGOUT_CMD = "Logout";

	private User myUser;
	private FusionSortedIterator<Post, User> newsFeed;
	private MessageView myView;
	private Post currentPost;
	private JFrame controllingFrame; // needed for dialogs

	/**
	 * Initialise un MessageController pour la vue spécifiée.
	 * 
	 * @param v la vue gérée par ce controller
	 * 
	 * @requires v != null;
	 * @ensures getUser().equals(v.getUser());
	 * 
	 * @throws NullPointerException si la vue spécifiée est null
	 * 
	 */
	public MessageController(MessageView v) {
		myView = v;
		myUser = v.getUser();
		// newsFeed = myUser.timeLine();
		newsFeed = myUser.newsFeed();
		controllingFrame = Microdon.getTheInstance().getFrame();
	}

	/**
	 * Renvoie l'utilisateur dont la vue associée doit afficher le fil d'actualité.
	 * 
	 * @return l'utilisateur dont la vue associée doit afficher le fil d'actualité
	 * 
	 * @pure
	 */
	public User getUser() {
		return myUser;
	}

	/**
	 * Renvoie la vue associée à ce controller, et dont ce controller est chargé
	 * d'exécuter les commandes.
	 * 
	 * @return la vue associée à ce controller
	 * 
	 * @pure
	 */
	public MessageView getView() {
		return this.myView;
	}

	/**
	 * Récupère le premier message du fil d'actualité et demande à la vue associée
	 * de l'afficher.
	 */
	public void setFirstMessage() {
		if (newsFeed.hasNext()) {
			currentPost = newsFeed.next();
			myView.setNewMessage(newsFeed.lastIterator(), currentPost);

		}
		myView.setNextEnabled(newsFeed.hasNext());
		myView.setPreviousEnabled(false);
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
	 */	@Override
	public void actionPerformed(ActionEvent e) {
		if (PREV_CMD.equals(e.getActionCommand())) {
			previousCommand();
		}
		if (NEXT_CMD.equals(e.getActionCommand())) {
			nextCommand();
		}
		if (LIKE_CMD.equals(e.getActionCommand())) {
			if (!currentPost.addLikeFrom(myUser)) {
				JOptionPane.showMessageDialog(controllingFrame, "You already liked this message.", "Error Message",
						JOptionPane.ERROR_MESSAGE);

			}
			myView.setNewMessage(newsFeed.lastIterator(), currentPost);
			return;
		}
		if (NEW_CMD.equals(e.getActionCommand())) {
			Microdon.getTheInstance().createAndShowEditMessagePanel();
			return;
		}
		if (REPOST_CMD.equals(e.getActionCommand())) {
			Post newmsg = new RePost("Read This…", newsFeed.lastIterator(), currentPost);
			myUser.addPost(newmsg);
			JOptionPane.showMessageDialog(controllingFrame, "Re post successful!", "Information Message",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if (FOLLOW_CMD.equals(e.getActionCommand())) {
			Microdon.getTheInstance().createAndShowFollowUserPanel();
			return;
		}
		if (LOGOUT_CMD.equals(e.getActionCommand())) {
			Microdon.getTheInstance().logout();
			Microdon.getTheInstance().createAndShowLoginPanel();
			return;
		}

	}

	/**
	 * Récupère le message précédent (plus récent) et demande à la vue de l'afficher.
	 */
	public void previousCommand() {
		// newsFeed.lastIndex() == previousIndex() || currentPostIndex == nextIndex()
		if (newsFeed.lastIndex() == newsFeed.previousIndex()) {
			// previous command was a next ==> hasPrevious()
			newsFeed.previous();
		}
		// newsFeed.lastIndex() == nextIndex()
		if (newsFeed.hasPrevious()) {
			currentPost = newsFeed.previous();
			myView.setNewMessage(newsFeed.lastIterator(), currentPost);
			myView.setNextEnabled(true);
		}
		myView.setPreviousEnabled(newsFeed.hasPrevious());
	}

	/**
	 * Récupère le message suivant (plus ancien) et demande à la vue de l'afficher.
	 */
	public void nextCommand() {
		// newsFeed.lastIndex() == previousIndex() || newsFeed.lastIndex() ==
		// nextIndex()
		if (newsFeed.lastIndex() == newsFeed.nextIndex()) {
			// previous command was a previous ==> hasNext()
			newsFeed.next();
		}
		// newsFeed.lastIndex() == iterPost.previousIndex()
		if (newsFeed.hasNext()) {
			currentPost = newsFeed.next();
			myView.setNewMessage(newsFeed.lastIterator(), currentPost);
			myView.setPreviousEnabled(true);
		}
		myView.setNextEnabled(newsFeed.hasNext());
	}

}
