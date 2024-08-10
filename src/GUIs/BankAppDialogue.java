package GUIs;

import DBObjs.BankJDBC;
import DBObjs.People;
import DBObjs.Transaction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;


public class BankAppDialogue extends JDialog implements ActionListener {
    private People personne;
    private BankAppUI bankAppUI;
    private JLabel newAmountLabel;
    private JLabel balanceLabel;
    private JLabel newUserLabel;
    private JTextField newAmountField;
    private JTextField newUserField;
    private JButton actionButton;
    private JPanel pastTransactionsPanel;
    private ArrayList<Transaction> previousTransactions;


    BankAppDialogue(BankAppUI bankAppUI, People personne) {
        setSize(600,600);
        //Gives it a specific size, of course.
        setModal(true);
        //Makes it such that you can only interact with this dialog box alone until it's closed.
        setLocationRelativeTo(bankAppUI);
        //Puts it in the middle of the bank app screen.
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        //Removes it completely when exiting.
        setResizable(false);
        //Makes the size from earlier fixed.
        setLayout(null);
        //Lets you manually say where each component is going.
        this.bankAppUI = bankAppUI;
        this.personne  = personne;
        /*
            The two right above show that you'll ensure you're picking updated
            details on the screen and from the database on a given user.
         */

    }

    protected void updateCurrentBalanceAndAmount() {
        balanceLabel = new JLabel("Balance = sh."+ personne.getBalance());
        balanceLabel.setBounds(0,10,(getWidth()-20),20);
        balanceLabel.setFont(new Font("Times New Roman",Font.BOLD,20));
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(balanceLabel);

        newAmountLabel = new JLabel("Please enter new amount:");
        newAmountLabel.setBounds(0,60,(getWidth()-20),20);
        newAmountLabel.setFont(new Font("Times New Roman",Font.BOLD,20));
        newAmountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(newAmountLabel);

        newAmountField = new JTextField();
        newAmountField.setBounds(10,100,(getWidth()-30),50);
        newAmountField.setFont(new Font("Times New Roman",Font.BOLD,20));
        newAmountField.setHorizontalAlignment(SwingConstants.CENTER);
        add(newAmountField);
    }

    protected void addActionButton(String actionButtonType) {
        actionButton = new JButton(actionButtonType);
        actionButton.setBounds(10,250,(getWidth()-30),50);
        actionButton.setFont(new Font("Times New Roman",Font.BOLD,20));
        actionButton.setHorizontalAlignment(SwingConstants.CENTER);
        actionButton.addActionListener(this);
        add(actionButton);
    }

    protected void addUserField() {
        newUserLabel = new JLabel("Enter user's name:");
        newUserLabel.setBounds(0,160,(getWidth()-20),20);
        newUserLabel.setFont(new Font("Times New Roman",Font.BOLD,20));
        newUserLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(newUserLabel);
        
        newUserField = new JTextField();
        newUserField.setBounds(10,190,(getWidth()-30),50);
        newUserField.setFont(new Font("Times New Roman",Font.PLAIN,20));
        newUserField.setHorizontalAlignment(SwingConstants.CENTER);
        add(newUserField);

    }

    public void showPastTransactions() {
        pastTransactionsPanel = new JPanel();
        pastTransactionsPanel.setLayout(new BoxLayout(pastTransactionsPanel,BoxLayout.Y_AXIS));
        //To make this pane scrollable...
        JScrollPane scrollPane = new JScrollPane(pastTransactionsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(0,30,(getWidth()-30),(getHeight()-20));
        previousTransactions = BankJDBC.showPreviousTransactions(personne);
        for(int i = 0 ; i < previousTransactions.size(); i++) {
            Transaction transaction = previousTransactions.get(i);
            JPanel previousTransactionsContainer = new JPanel();
            previousTransactionsContainer.setLayout(new BorderLayout());
            JLabel transactionTypeLabel = new JLabel(transaction.getTransactionType());
            transactionTypeLabel.setFont(new Font("Times New Roman",Font.BOLD,20));
            JLabel transactionAmountLabel = new JLabel(String.valueOf(transaction.getTransactionAmount()));
            transactionAmountLabel.setFont(new Font("Times New Roman",Font.BOLD,20));
            JLabel transactionDateLabel = new JLabel(String.valueOf(transaction.getTransactionDate()));
            transactionDateLabel.setFont(new Font("Times New Roman",Font.PLAIN,20));

            previousTransactionsContainer.add(transactionTypeLabel,BorderLayout.WEST);
            previousTransactionsContainer.add(transactionAmountLabel,BorderLayout.SOUTH);
            previousTransactionsContainer.add(transactionDateLabel,BorderLayout.EAST);

            previousTransactionsContainer.setBackground(Color.gray);
            previousTransactionsContainer.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            pastTransactionsPanel.add(previousTransactionsContainer);
        }
        add(scrollPane);
    }

    private void manageTransactions(String transactionType, double amountValue) {
        Transaction transaction;

        //This assumes you're dealing with depositing (hence chaining the add() method)
        //or withdrawing (hence chaining the subtract() method at the end of the second alternative)
        if(transactionType.equalsIgnoreCase("Deposit")) {
            personne.setBalance(personne.getBalance().add(new BigDecimal(amountValue)));
            transaction = new Transaction(personne.getId(),transactionType,new BigDecimal(amountValue),null);
        }
        else {
            personne.setBalance(personne.getBalance().subtract(new BigDecimal(amountValue)));
            transaction = new Transaction(personne.getId(),transactionType,new BigDecimal(-amountValue),null);
        }
        if(BankJDBC.addTranssactiontoDB(transaction) && BankJDBC.updateUserBalance(personne))  {
            JOptionPane.showMessageDialog(BankAppDialogue.this,"Your "+transactionType+" was successful!");
            balanceResetAndUpdate();
        }
        else {
            JOptionPane.showMessageDialog(BankAppDialogue.this,"Your "+transactionType+" was unsuccessful.");
        }
    }

    private void manageTransfers(People personne, String transferredUser, double amount) {
            if(BankJDBC.createTransfer(personne,transferredUser,amount)) {
                JOptionPane.showMessageDialog(this, "Transfer was a success!!");
                balanceResetAndUpdate();
            }
            else {
                JOptionPane.showMessageDialog(this, "Transfer was a bloody failure!!");
            }
    }

    private void balanceResetAndUpdate() {
        //Rests fields on some kind of transaction, starting with text
        newAmountField.setText("");
        //Should only happen if ans when transfer is clicked.
        if(newUserField != null) {
            newUserField.setText("");
        }
        balanceLabel = new JLabel("Balance = sh."+ personne.getBalance());
        bankAppUI.getCurrentBalanceField().setText("sh"+ personne.getBalance());

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonClicked = e.getActionCommand();
        double amountValue = Double.parseDouble(newAmountField.getText());
        if(buttonClicked.equalsIgnoreCase("Deposit")){
            manageTransactions(buttonClicked,amountValue);
        }
        else {
            /*
                This assumes withdrawal or transfer. In any case,
                ensure that what the user attempts to remove is less than what they have,
                so they aren't pulling money out of thin air. Therefore...
             */
            int resultat = personne.getBalance().compareTo(BigDecimal.valueOf(amountValue));
            /*
                The method chain above will return -1 if their entry exceeds their balance,
                0 if they are equal and 1 if it's within their limits.
             */
            if(resultat < 0) {
                JOptionPane.showMessageDialog(this,"Avoid trying to remove more than your balance, brokie!" );
                return;
            }
            if(buttonClicked.equalsIgnoreCase("Withdraw")) {
                manageTransactions(buttonClicked,amountValue);
            }
            else {
                //We start dealing with the case of transfers...
                String transferredUser = newUserField.getText();
                manageTransfers(personne,transferredUser,amountValue);
            }
        }
    }
}
