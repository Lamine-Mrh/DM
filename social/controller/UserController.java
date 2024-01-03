package social.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import social.Microdon;
import social.model.User;
import social.view.LoginPanel;

public class UserController implements ActionListener {
	public final static String CREATE_USER_CMD = "Create";
	public final static String LOGIN_CMD = "Login";
	private JFrame controllingFrame; // needed for dialogs
	private LoginPanel myPanel;

	public UserController(LoginPanel myPanel) {
		this.myPanel = myPanel;
		this.controllingFrame = myPanel.getFrame();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();

		if (LOGIN_CMD.equals(cmd)) { // Process the password.
			String name = myPanel.getNameTextField().getText();
			char[] input = myPanel.getPassTextField().getPassword();
			if (User.hasUser(name)) {
				if (isPasswordCorrect(name, new String(input))) {
					Microdon.getTheInstance().setCurrentUser(User.getUser(name));
					JOptionPane.showMessageDialog(controllingFrame,
							"Congratulations " + name + "! You are now logged.");
					// Show list of messages now
					Microdon.getTheInstance().createAndShowMessageListPanel();
				} else {
					JOptionPane.showMessageDialog(controllingFrame, "Invalid password. Try again.", "Error Message",
							JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(controllingFrame, "User name or password is Invalid. Try again.",
						"Error Message", JOptionPane.ERROR_MESSAGE);

			}

			// Zero out the possible password, for security.
			Arrays.fill(input, '0');

			myPanel.getPassTextField().selectAll();
			myPanel.resetFocus();
		} else {
			if (CREATE_USER_CMD.equals(cmd)) {
				// Create the user if it doesn't yet exists.
				String name = myPanel.getNameTextField().getText();
				char[] input = myPanel.getPassTextField().getPassword();
				if (User.hasUser(name)) {
					JOptionPane.showMessageDialog(controllingFrame,
							"A user with this name (" + name + ") already exists.\n Choose another name or log in.",
							"Error Message", JOptionPane.ERROR_MESSAGE);
				} else {
					String password = new String(input);
					if (User.isValidUserName(name) && User.isValidPassword(password)) {
						new User(name, password);
						JOptionPane.showMessageDialog(controllingFrame,
								"Congratulations " + name + "! Now you can log in.");
					} else {
						JOptionPane.showMessageDialog(controllingFrame,
								"Name (" + name + ") or password is not valids.\n Choose another name or password.",
								"Error Message", JOptionPane.ERROR_MESSAGE);
					}
				}
			} else {
				JOptionPane.showMessageDialog(controllingFrame, "Unknow command!", "Error Message",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Checks the passed-in string against the correct password. After this method
	 * returns, you should invoke eraseArray on the passed-in array.
	 */
	private static boolean isPasswordCorrect(String name, String pass) {
		String correctPassword = User.getUser(name).getPassword();

		return correctPassword.equals(pass);
	}
}
