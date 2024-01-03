package social.view;

import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import social.Microdon;
import social.controller.UserController;


public class LoginPanel extends JPanel {
    private static final long serialVersionUID = -4438919576375333350L;

    private JFrame controllingFrame; //needed for dialogs
    private JTextField userNameField;
    private JPasswordField passwordField;
    private UserController uCtrl;

    public LoginPanel(Microdon app) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        controllingFrame = app.getFrame();
        uCtrl = new UserController(this);

        //Create everything.
        userNameField = new JTextField(10);
        userNameField.setActionCommand(UserController.CREATE_USER_CMD);
        userNameField.addActionListener(uCtrl);

        JLabel nameLabel = new JLabel("Enter your name: ");
        nameLabel.setLabelFor(userNameField);

        passwordField = new JPasswordField(10);
        passwordField.setActionCommand(UserController.CREATE_USER_CMD);
        passwordField.addActionListener(uCtrl);

        JLabel passLabel = new JLabel("Enter the password: ");
        passLabel.setLabelFor(passwordField);

        JComponent buttonPane = createButtonPanel();

        //Lay out everything.
        JPanel nameTextPane = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        nameTextPane.add(nameLabel);
        nameTextPane.add(userNameField);

        JPanel passTextPane = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        passTextPane.add(passLabel);
        passTextPane.add(passwordField);

        add(nameTextPane);
        add(passTextPane);
        add(buttonPane);
        setOpaque(true);
    }
    
    public JFrame getFrame() {
    	return this.controllingFrame;
    }
    
    public JTextField getNameTextField() {
    	return this.userNameField;
    }
    
    public JPasswordField getPassTextField() {
    	return this.passwordField;
    }

    protected JComponent createButtonPanel() {
        JPanel p = new JPanel();
        JButton createButton = new JButton(UserController.CREATE_USER_CMD);
        JButton loginButton = new JButton(UserController.LOGIN_CMD);
        

        createButton.setActionCommand(UserController.CREATE_USER_CMD);
        loginButton.setActionCommand(UserController.LOGIN_CMD);
        createButton.addActionListener(uCtrl);
        loginButton.addActionListener(uCtrl);

        p.add(createButton);
        p.add(loginButton);

        return p;
    }



    //Must be called from the event dispatch thread.
    public void resetFocus() {
        userNameField.requestFocusInWindow();
    }
}
