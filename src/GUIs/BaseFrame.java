package GUIs;
import DBObjs.People;

import javax.swing.*;
import java.awt.*;
public abstract class BaseFrame extends JFrame {

    protected People personne;

    public BaseFrame(String title) {
        initialize(title);
    }

    public BaseFrame(String title,People personne) {
        this.personne= personne;
        initialize(title);
    }

    private void initialize(String title) {
        //Adds title to each JFrame object made this way
        setTitle(title);

        //Sets default size of GUI.
        setSize(500,650);

        //Makes sure exit button works as usual instead of being useless.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Gives complete control to us of EVERY single GUI component's location
        // ...at the price of having to specify it manually!
        setLayout(null);

        //Makes size of GUI constant.
        setResizable(false);

        //Centers the GUI in question.
        setLocationRelativeTo(null);

        //See what abstract function right below does from comments.
        addGUIComponents();
    }
    /*
        This is the method every child class HAS to define for themselves,
        which is what actually allows us to specify them uniquely,
        while each easily inherits the common traits from this BaseFrame.
     */
    protected abstract void addGUIComponents();
}
