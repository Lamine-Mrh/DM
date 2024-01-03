/**
 * 
 */
package social.view;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import social.controller.MessageEditController;
import social.model.User;

/**
 * Vue permettant de saisir un nouveau message. Les commandes sont exécutées par
 * le MessageEditController associé à cette vue.
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
public class MessageEditView extends JPanel {
	private static final long serialVersionUID = -2128950612891940257L;
	private User theUser;
	private MessageEditController msgCtrl;
	private JTextArea textArea;
	
	/**
	 * Initialise et affiche une vue pour créer/éditer un nouveau message.
	 * 
	 * @param theUser l'utilisateur connecté auteur du nouveau message
	 * 
	 * @throws NullPointerException si l'argument spécifié est null
	 * 
	 * @requires theUser != null;
	 * @ensures getUser().equals(theUser);
	 * @ensures getController().getUser().equals(theUser);
	 * 
	 */
	public MessageEditView(User theUser) {
		super(new BorderLayout());
		this.theUser = theUser;
		msgCtrl = new MessageEditController(this);

		JLabel topLabel = new JLabel(theUser.getName() + " is logged in");
		add(topLabel, BorderLayout.NORTH);

		JPanel centerPanel = createCenterPanel();
		add(centerPanel, BorderLayout.CENTER);

		add(createButtonPanel(), BorderLayout.SOUTH);

		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
	}
	
	/**
	 * Renvoie l'utilisateur auteur du nouveau message.
	 * 
	 * @return l'utilisateur auteur du nouveau message
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
	public MessageEditController getController() {
		return msgCtrl;
	}

	/**
	 * Renvoie le texte du Post saisi par l'utilisateur.
	 * 
	 * @return le texte du Post saisi par l'utilisateur
	 * 
	 * @pure
	 */
	public String getText() {
		return textArea.getText();
	}

	private JPanel createCenterPanel() {
		JPanel textPanel = new JPanel(new BorderLayout());
		JLabel topLabel = new JLabel("Write your message here:");
		textPanel.add(topLabel, BorderLayout.NORTH);

		textArea = new JTextArea(10, 30);
		textArea.setEditable(true);
		JScrollPane scrollPane = new JScrollPane(textArea);
		textPanel.add(scrollPane, BorderLayout.CENTER);

		return textPanel;
	}

	private JPanel createButtonPanel() {
		JPanel p = new JPanel();
		JButton okButton = new JButton(MessageEditController.OK_CMD);
		JButton cancelButton = new JButton(MessageEditController.CANCEL_CMD);

		okButton.setActionCommand(MessageEditController.OK_CMD);
		cancelButton.setActionCommand(MessageEditController.CANCEL_CMD);
		okButton.addActionListener(msgCtrl);
		cancelButton.addActionListener(msgCtrl);

		p.add(cancelButton);
		p.add(okButton);

		return p;
	}
}
