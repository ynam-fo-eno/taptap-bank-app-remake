package GUIs;

import DBObjs.BankJDBC;
import DBObjs.People;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginUI extends BaseFrame implements ActionListener {


    public LoginUI(String title) {
        super(title);
    }

    @Override
    protected void addGUIComponents() {
        //For header of Login...
        JLabel simpleBankAppLabel= new JLabel("Login Screen");
        //Specifies its placement...
        simpleBankAppLabel.setBounds(0,20,super.getWidth(),40);
        //Controls what font it  gets...
        simpleBankAppLabel.setFont(new Font("Times New Roman",Font.BOLD,30));
        //Centers it...
        simpleBankAppLabel.setHorizontalAlignment(SwingConstants.CENTER);
        //...and below adds it else hatungeona lol.
        add(simpleBankAppLabel);

        //For header of username ...
        JLabel usernameLabel= new JLabel("Enter Username");
        //Specifies its placement...
        usernameLabel.setBounds(0,120,(super.getWidth()-30),20);
        //Controls what font it  gets...
        usernameLabel.setFont(new Font("Comic Sans MS",Font.ITALIC,20));
        //Centers it...
        usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        //...and below adds it else hatungeona lol.
        add(usernameLabel);

        //For username textfield...
        JTextField usernameTextField = new JTextField();
        //To specify its placement...
        usernameTextField.setBounds(20,160,(super.getWidth()-50),50);
        //To specify the font...
        usernameTextField.setFont(new Font("Comic Sans MS",Font.PLAIN,20));
        //To center it(more aesthetically pleasign to me personally)...
        usernameTextField.setHorizontalAlignment(SwingConstants.CENTER);
        //...and now to add it!!
        add(usernameTextField);

        //For header of password ...
        JLabel passwordLabel= new JLabel("Enter Password");
        //Specifies its placement...
        passwordLabel.setBounds(0,230,(super.getWidth()-30),20);
        //Controls what font it  gets...
        passwordLabel.setFont(new Font("Comic Sans MS",Font.ITALIC,20));
        //Centers it...
        passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        //...and below adds it else hatungeona lol.
        add(passwordLabel);

        //For password textfield...
        JPasswordField passwordTextField = new JPasswordField();
        //To specify its placement...
        passwordTextField.setBounds(20,270,(super.getWidth()-50),50);
        //To specify the font...
        passwordTextField.setFont(new Font("Comic Sans MS",Font.PLAIN,20));
        //To center it(more aesthetically pleasing to me personally)...
        passwordTextField.setHorizontalAlignment(SwingConstants.CENTER);
        //...and now to add it!!
        add(passwordTextField);

        //For login button...
        JButton loginButton = new JButton("Login");
        //To specify its placement...
        loginButton.setBounds(70,360,(super.getWidth()-150),50);
        //To specify the font...
        loginButton.setFont(new Font("Times New Roman",Font.BOLD,30));
        //We will now add some functionality to the login button.
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username= usernameTextField.getText();

                String password= String.valueOf(passwordTextField.getPassword());
                People personne = BankJDBC.loginValidation(username,password);
                if (personne != null){
                    LoginUI.this.dispose();
                    BankAppUI bankAppUI = new BankAppUI("",personne);
                    bankAppUI.setVisible(true);
                    JOptionPane.showMessageDialog(bankAppUI,"Successful Login!");
                }
                else{
                    JOptionPane.showMessageDialog(LoginUI.this,"Failed.\nPlease check that credentials are correct.");
                }
            }
        });
        //To center it(more aesthetically pleasing to me personally)...
        loginButton.setHorizontalAlignment(SwingConstants.CENTER);
        //...and now to add it!!
        add(loginButton);

        //For header of password ...
        JLabel registerLabel= new JLabel("<html><a href=\"#\">Register if you don't have an account</a></html>");
        //Specifies its placement...
        registerLabel.setBounds(0,430,(super.getWidth()-10),50);
        //Controls what font it  gets...
        registerLabel.setFont(new Font("Comic Sans MS",Font.ITALIC,20));
        //Centers it...
        registerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        //...and below adds it else hatungeona lol.
        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                LoginUI.this.dispose();
                //Removes the login UI...
                new RegisterUI("").setVisible(true);
                //...and shows registration screen.
            }
        });
        //This overriden MouseListener method is what makes clicking the link functional.
        add(registerLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
