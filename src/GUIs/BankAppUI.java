package GUIs;

import DBObjs.People;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BankAppUI extends BaseFrame implements ActionListener {

    private JTextField balanceTextField;
    public JTextField getCurrentBalanceField() {
        return balanceTextField;
    }
    public BankAppUI(String title, People personne) {
        super(title,personne);
    }

    /*
        I thank God that for once in my life I was able to figure out how to
        recreate a UI without directly copying his code, to the point I could make
        some interesting decisions on my own (like adding a JPanel within) while still
        having the necessary content.
        Maybe there IS hope for me as a programmer after all! Anyway...
        All I took from him was figuring out how to incorporate
        the model of the User (People in my case) class and that's been successful too.
     */
    @Override
    protected void addGUIComponents() {
        //For header of where user ACTUALLY gets to do stuff with their money...
        JLabel simpleBankAppLabel= new JLabel("Welcome to Timo's Bank!");
        //Specifies its placement...
        simpleBankAppLabel.setBounds(0,20,super.getWidth(),40);
        //Controls what font it gets...
        simpleBankAppLabel.setFont(new Font("Times New Roman",Font.BOLD,30));
        //Centers it...
        simpleBankAppLabel.setHorizontalAlignment(SwingConstants.CENTER);
        //...and below adds it else hatungeona lol.
        add(simpleBankAppLabel);

        //For welcoming users...
        JLabel welcomeLabel= new JLabel("Hello, "+ personne.getUsername()+".");
        //Specifies its placement...
        welcomeLabel.setBounds(0,70,super.getWidth(),20);
        //Controls what font it gets...
        welcomeLabel.setFont(new Font("Times New Roman",Font.PLAIN,20));
        //Centers it...
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        //...and below adds it else hatungeona lol.
        add(welcomeLabel);

        //For header explaining options...
        JLabel optionsLabel= new JLabel("Click a button to do what you'd like-");
        //Specifies its placement...
        optionsLabel.setBounds(0,100,super.getWidth(),20);
        //Controls what font it gets...
        optionsLabel.setFont(new Font("Times New Roman",Font.PLAIN,20));
        //Centers it...
        optionsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        //...and below adds it else hatungeona lol.
        add(optionsLabel);

        //For continuation of explaining options...
        JLabel optionsTwoLabel= new JLabel("based on your balance of course hehe...");
        //Specifies its placement...
        optionsTwoLabel.setBounds(0,130,super.getWidth(),20);
        //Controls what font it gets...
        optionsTwoLabel.setFont(new Font("Times New Roman",Font.PLAIN,20));
        //Centers it...
        optionsTwoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        //...and below adds it else hatungeona lol.
        add(optionsTwoLabel);

        //For header of users' current balance...
        JLabel balanceLabel= new JLabel("Current Balance");
        //Specifies its placement...
        balanceLabel.setBounds(0,160,super.getWidth(),50);
        //Controls what font it gets...
        balanceLabel.setFont(new Font("Times New Roman",Font.BOLD,40));
        //Centers it...
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        //...and below adds it else hatungeona lol.
        add(balanceLabel);

        //For textfield where balance will be viewed and (carefully!) modified...
        balanceTextField = new JTextField();
         //To set text, since it represents the balance...
        balanceTextField.setText("sh."+ personne.getBalance() );
        //To prevent sth that would be terrible irl...a user illegally adding themselves money!
        balanceTextField.setEditable(false);
        //Then the next two lines modify the textfield itself
        //and the color of the cursor(caret) to make sure cursor isn't visible.
        balanceTextField.setBackground(Color.WHITE);
        balanceTextField.setCaretColor(Color.WHITE);
        //To specify its placement...
        balanceTextField.setBounds(100,220,(super.getWidth()-190),30);
        //To specify the font...
        balanceTextField.setFont(new Font("Times New Roman",Font.PLAIN,20));
        //To center it(more aesthetically pleasing to me personally)...
        balanceTextField.setHorizontalAlignment(SwingConstants.CENTER);
        //...and now to add it!!
        add(balanceTextField);

        /*
            NB: To anyone wondering why the stark difference in the buttons,
            he (TapTap) chose to have all five buttons' layout as usual-
            all under the null layout of the main BaseFrame.
            I have taken a slightly different approach:
            to make my work easier and also make the buttons arguably
            neater on the UI (or at least more compact), I've placed the 4 buttons
            (directly related to bank matters) in a JPanel, which in turn has a Grid Layout,
            to make my work slightly easier in defining the buttons' bounds.
            Hopefully this won't negatively affect adding functionality to those four buttons especially,
            but if it does I'll comment it out and copy his layout.
         */

        JButton depositButton = new JButton("Deposit");
        depositButton.setFont(new Font("Times New Roman",Font.PLAIN,20));
        depositButton.addActionListener(this);
        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setFont(new Font("Times New Roman",Font.PLAIN,20));
        withdrawButton.addActionListener(this);
        JButton historyButton = new JButton("Previous Transactions");
        historyButton.setFont(new Font("Times New Roman",Font.PLAIN,20));
        historyButton.addActionListener(this);
        JButton transferButton = new JButton("Transfer");
        transferButton.setFont(new Font("Times New Roman",Font.PLAIN,20));
        transferButton.addActionListener(this);


        /*
            This is the JPanel I shall use to organize the buttons above to my preference.
         */
        JPanel mainButtonsPanel = new JPanel();
        mainButtonsPanel.setBounds(80,260,(super.getWidth()-150),150);
        mainButtonsPanel.setLayout(new GridLayout(2,2));
        mainButtonsPanel.add(depositButton);
        mainButtonsPanel.add(withdrawButton);
        mainButtonsPanel.add(historyButton);
        mainButtonsPanel.add(transferButton);
        add(mainButtonsPanel);


        //For logout button...
        JButton logoutButton = new JButton("Logout");
        //To specify its placement...
        logoutButton.setBounds(100,430,(super.getWidth()-190),50);
        //To specify the font...
        logoutButton.setFont(new Font("Times New Roman",Font.BOLD,30));
        //To center it(more aesthetically pleasing to me personally)...
        logoutButton.setHorizontalAlignment(SwingConstants.CENTER);
        //To make it functional...
        logoutButton.addActionListener(this);
        //...and now to add it!!
        add(logoutButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String kubonyezwa = e.getActionCommand();
        if(kubonyezwa.equalsIgnoreCase("logout")) {
            this.dispose();
            new LoginUI("").setVisible(true);
            return;
        }

        BankAppDialogue bankAppDialogue = new BankAppDialogue(this, personne);


        if(kubonyezwa.equalsIgnoreCase("Deposit" ) || kubonyezwa.equalsIgnoreCase("Withdraw") || kubonyezwa.equalsIgnoreCase("Transfer")) {
            bankAppDialogue.updateCurrentBalanceAndAmount();
            bankAppDialogue.addActionButton(kubonyezwa);

            if(kubonyezwa.equalsIgnoreCase("Transfer")) {
                bankAppDialogue.addUserField();
            }


        }
        else if (kubonyezwa.equalsIgnoreCase("Previous Transactions") ) {
                bankAppDialogue.showPastTransactions();

        }
        bankAppDialogue.setVisible(true);
    }
}
