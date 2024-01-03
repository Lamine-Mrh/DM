package social.view;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import social.controller.MessageController;
import social.model.Post;
import social.model.User;

/**
 * Vue affichant le fil d'actualité de l'utilisateur connecté. Cette vue affiche
 * également des boutons permettant de naviguer dans le fil d'actualité, de
 * liker ou reposter un message, de créer un nouveau message ainsi que de
 * s'abonner/désabonner à d'autres utilisateurs. Ces commandes sont exécutées
 * par le MessageController associé à cette vue.
 * 
 * @invariant getUser() != null;
 * @invariant getController() != null;
 * @invariant getController().getUser().equals(getUser());
 * @invariant getController().getView().equals(this);
 * 
 * @author Marc Champesme
 * @since 2/08/2023
 * @version 10/12/2023
 */
public class MessageView extends JPanel {
	private static final long serialVersionUID = -4824213869096627427L;
	private User theUser;
	private MessageController msgCtrl;
	private JLabel authorLabel;
	private JLabel likeLabel;
	private JTextArea textArea;
	private JButton prevButton;
	private JButton nextButton;

	/**
	 * Initialise une vue pour afficher le fil d'actualité de l'utilisateur
	 * spécifié. Affiche le premier message du fil d'actualité.
	 * 
	 * @param theUser l'utilisateur connecté dont on veut afficher le fil
	 *                d'actualité
	 * 
	 * @throws NullPointerException si l'argument spécifié est null
	 * 
	 * @requires theUser != null;
	 * @ensures getUser().equals(theUser);
	 * @ensures getController().getUser().equals(theUser);
	 * 
	 */
	public MessageView(User theUser) {
		super(new BorderLayout());
		this.theUser = theUser;
		msgCtrl = new MessageController(this);

		JPanel topPanel = new JPanel();
		JLabel topLabel = new JLabel(theUser.getName() + " is logged in");
		topPanel.add(topLabel);
		JButton logoutButton = new JButton(MessageController.LOGOUT_CMD);
		logoutButton.addActionListener(msgCtrl);
		logoutButton.setActionCommand(MessageController.LOGOUT_CMD);
		topPanel.add(logoutButton);
		add(topPanel, BorderLayout.NORTH);

		JPanel centerPanel = createCenterPanel();
		add(centerPanel, BorderLayout.CENTER);

		add(createButtonPanel(), BorderLayout.SOUTH);
		msgCtrl.setFirstMessage();
	}

	/**
	 * Renvoie l'utilisateur dont cette vue affiche le fil d'actualité.
	 * 
	 * @return l'utilisateur dont cette vue affiche le fil d'actualité
	 * 
	 * @pure
	 */
	public User getUser() {
		return theUser;
	}

	/**
	 * Renvoie le controller exécutant les commandes de cette vue.
	 * 
	 * @return le controller exécutant les commandes de cette vue
	 * 
	 * @pure
	 */
	public MessageController getController() {
		return msgCtrl;
	}

	/**
	 * Remplace le message affiché actuellement dans cette vue par le message
	 * spécifié dont l'auteur est l'utilisateur spécifié.
	 * 
	 * @param author l'auteur du Post à afficher
	 * @param p      le Post à afficher
	 * 
	 * @requires author != null;
	 * @requires p != null;
	 * @requires author.getPosts().contains(p);
	 * 
	 * @throws NullPointerException     si un des arguments spécifiés est null
	 * @throws IllegalArgumentException si l'utilisateur spécifié n'est pas l'auteur
	 *                                  du Post spécifié
	 */
	public void setNewMessage(User author, Post p) {
		if (!author.getPosts().contains(p)) {
			throw new IllegalArgumentException();
		}
		authorLabel.setText("Message from " + author.getName() + " at " + p.getDate());
		textArea.setText(p.getText());
		likeLabel.setText(p.getLikeNumber() + " likes!");
		repaint();
	}

	/**
	 * Active ou désactive le bouton permettant d'aller au Post précédent (plus
	 * récent).
	 * 
	 * @param b spécifie si le bouton doit être activé (valeur true) ou désactivé
	 *          (valeur false)
	 */
	public void setPreviousEnabled(boolean b) {
		prevButton.setEnabled(b);
		repaint();
	}

	/**
	 * Active ou désactive le bouton permettant d'aller au Post suivant (plus
	 * ancien).
	 * 
	 * @param b spécifie si le bouton doit être activé (valeur true) ou désactivé
	 *          (valeur false)
	 */
	public void setNextEnabled(boolean b) {
		nextButton.setEnabled(b);
		repaint();
	}

	private JPanel createCenterPanel() {
		JPanel centerPanel = new JPanel();

		prevButton = new JButton(MessageController.PREV_CMD);
		prevButton.setActionCommand(MessageController.PREV_CMD);
		prevButton.setEnabled(false);
		prevButton.addActionListener(msgCtrl);
		centerPanel.add(prevButton);

		JPanel textPanel = new JPanel(new BorderLayout());
		authorLabel = new JLabel("Message from Nobody!");
		textPanel.add(authorLabel, BorderLayout.NORTH);

		textArea = new JTextArea(20, 40);
		textArea.setEditable(false);
		textArea.setText("No message!\n");
		textArea.setCaretPosition(0);
		JScrollPane scrollPane = new JScrollPane(textArea);
		textPanel.add(scrollPane, BorderLayout.CENTER);

		likeLabel = new JLabel("0 likes");
		textPanel.add(likeLabel, BorderLayout.SOUTH);

		centerPanel.add(textPanel);

		nextButton = new JButton(MessageController.NEXT_CMD);
		nextButton.setActionCommand(MessageController.NEXT_CMD);
		nextButton.addActionListener(msgCtrl);
		centerPanel.add(nextButton);

		return centerPanel;
	}

	private JPanel createButtonPanel() {
		JPanel p = new JPanel();
		JButton likeButton = new JButton(MessageController.LIKE_CMD);
		JButton newMsgButton = new JButton(MessageController.NEW_CMD);
		JButton rePostButton = new JButton(MessageController.REPOST_CMD);
		JButton followButton = new JButton(MessageController.FOLLOW_CMD);

		likeButton.setActionCommand(MessageController.LIKE_CMD);
		newMsgButton.setActionCommand(MessageController.NEW_CMD);
		rePostButton.setActionCommand(MessageController.REPOST_CMD);
		followButton.setActionCommand(MessageController.FOLLOW_CMD);
		likeButton.addActionListener(msgCtrl);
		newMsgButton.addActionListener(msgCtrl);
		rePostButton.addActionListener(msgCtrl);
		followButton.addActionListener(msgCtrl);

		p.add(likeButton);
		p.add(rePostButton);
		p.add(newMsgButton);
		p.add(followButton);

		return p;
	}
}
