package GUIs;

import DBObjs.BankJDBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RegisterUI extends BaseFrame{


    public RegisterUI(String title) {
        super(title);
    }

    @Override
    protected void addGUIComponents() {
        //For header of Register...
        JLabel simpleBankAppLabel= new JLabel("Registration Screen");
        //Specifies its placement...
        simpleBankAppLabel.setBounds(0,20,super.getWidth(),40);
        //Controls what font it gets...
        simpleBankAppLabel.setFont(new Font("Times New Roman",Font.BOLD,30));
        //Centers it...
        simpleBankAppLabel.setHorizontalAlignment(SwingConstants.CENTER);
        //...and below adds it else hatungeona lol.
        add(simpleBankAppLabel);

        //For header of username ...
        JLabel usernameLabel= new JLabel("Enter Username");
        //Specifies its placement...
        usernameLabel.setBounds(0,70,(super.getWidth()-30),20);
        //Controls what font it gets...
        usernameLabel.setFont(new Font("Comic Sans MS",Font.ITALIC,20));
        //Centers it...
        usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        //...and below adds it else hatungeona lol.
        add(usernameLabel);

        //For username textfield...
        JTextField usernameTextField = new JTextField();
        //To specify its placement...
        usernameTextField.setBounds(20,100,(super.getWidth()-50),50);
        //To specify the font...
        usernameTextField.setFont(new Font("Comic Sans MS",Font.PLAIN,20));
        //To center it(more aesthetically pleasign to me personally)...
        usernameTextField.setHorizontalAlignment(SwingConstants.CENTER);
        //...and now to add it!!
        add(usernameTextField);

        //For header of password ...
        JLabel passwordLabel= new JLabel("Enter Password");
        //Specifies its placement...
        passwordLabel.setBounds(0,160,(super.getWidth()-30),20);
        //Controls what font it gets...
        passwordLabel.setFont(new Font("Comic Sans MS",Font.ITALIC,20));
        //Centers it...
        passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        //...and below adds it else hatungeona lol.
        add(passwordLabel);

        //For password textfield...
        JPasswordField passwordTextField = new JPasswordField();
        //To specify its placement...
        passwordTextField.setBounds(20,190,(super.getWidth()-50),50);
        //To specify the font...
        passwordTextField.setFont(new Font("Comic Sans MS",Font.PLAIN,20));
        //To center it(more aesthetically pleasing to me personally)...
        passwordTextField.setHorizontalAlignment(SwingConstants.CENTER);
        //...and now to add it!!
        add(passwordTextField);

        //For header of confirming password ...
        JLabel confirmedPasswordLabel= new JLabel("Re-enter Password");
        //Specifies its placement...
        confirmedPasswordLabel.setBounds(0,250,(super.getWidth()-30),20);
        //Controls what font it gets...
        confirmedPasswordLabel.setFont(new Font("Comic Sans MS",Font.ITALIC,20));
        //Centers it...
        confirmedPasswordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        //...and below adds it else hatungeona lol.
        add(confirmedPasswordLabel);

        //For confirming password textfield...
        JPasswordField confirmedPasswordTextField = new JPasswordField();
        //To specify its placement...
        confirmedPasswordTextField.setBounds(20,280,(super.getWidth()-50),50);
        //To specify the font...
        confirmedPasswordTextField.setFont(new Font("Comic Sans MS",Font.PLAIN,20));
        //To center it(more aesthetically pleasing to me personally)...
        confirmedPasswordTextField.setHorizontalAlignment(SwingConstants.CENTER);
        //...and now to add it!!
        add(confirmedPasswordTextField);

        //For register button...
        JButton registerButton = new JButton("Register");
        //To specify its placement...
        registerButton.setBounds(70,360,(super.getWidth()-150),50);
        //To specify the font...
        registerButton.setFont(new Font("Times New Roman",Font.BOLD,30));
        //To center it(more aesthetically pleasing to me personally)...
        registerButton.setHorizontalAlignment(SwingConstants.CENTER);
        //...and now to add it!!
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Picks new username...
                String username = usernameTextField.getText();
                //Picks new password...
                String password = String.valueOf(passwordTextField.getPassword());
                //Picks repetition of password by user...
                String confirmedPassword = String.valueOf(confirmedPasswordTextField.getPassword());
                //Checking result of user input validation and moving accordingly...
                if(userEntriesValidation(username,password,confirmedPassword)) {
                    //Tries to put new user into DB, if it works...
                    if(BankJDBC.registrationValidation(username,password)){
                        //Means we can move so we can remove registration screen
                        RegisterUI.this.dispose();
                        LoginUI loginUI = new LoginUI("");
                        loginUI.setVisible(true);
                        JOptionPane.showMessageDialog(loginUI,"You've registered succesfully.\nNow you may login.");
                    }
                    else{
                        JOptionPane.showMessageDialog(RegisterUI.this,"Sorry, seems username is already taken!");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(RegisterUI.this,
                            "Please ensure that:\n" +
                                    "1.Nothing is left blank\n" +
                                    "2.Username is 6 characters long or more\n" +
                                    "3. Both entries of desired password match\n" +
                                    "So please try again, thank you.");
                }
            }
        });
        add(registerButton);

        //For telling sb to login if they already have an account
        JLabel loginLabel= new JLabel("<html><a href=\"#\">Login if you already have an account</a></html>");
        //Specifies its placement...
        loginLabel.setBounds(0,430,(super.getWidth()-10),50);
        //Controls what font it gets...
        loginLabel.setFont(new Font("Comic Sans MS",Font.ITALIC,20));
        //Centers it...
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        //...and below adds it else hatungeona lol.
        loginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                RegisterUI.this.dispose();
                new LoginUI("").setVisible(true);
            }
        });
        //Like with registration link, this takes you back to the login page if you
        //had an account after all,
        add(loginLabel);
    }

    private  boolean userEntriesValidation(String username,String password, String confrimedPassword) {
        //Checking if both entries of the password match and if not...
        if(!(password.equals(confrimedPassword))) {
            JOptionPane.showMessageDialog(RegisterUI.this,"Your password and your confirming it should match!\nTry again.");
            return false;
        }
        //Checking sensiblilty of username...
        if(username.length() < 6) {
            JOptionPane.showMessageDialog(RegisterUI.this,"Please make sure username is at least 6 characters long! ");
            return false;
        }
        //Checking that nothing is left empty...
        if((username.length() == 0) || (password.length() == 0) || (confrimedPassword.length() == 0)) {
            JOptionPane.showMessageDialog(RegisterUI.this,"Please put in values for ALL the slots!");
            return false;
        }

        //Assumes it's successfully passed preceding conditions, hence true hapa.
        return true;
    }
}
