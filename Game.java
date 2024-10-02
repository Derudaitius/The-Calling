import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/** "The Calling" is a text-based, choose your own adventure game.
 * Finishing the game happens in two ways
 *      -The player reaches the end of the story
 *      -The player dies (run out of HP)
 *
 * In both cases, the player will receive a highscore.
 * The idea is that "winning" isnt just about finishing the game, its about
 * having made the smartest choices along the way, maintaining the
 * highest health possible.
 */
public class Game {
    //starter variables
    String position;
    boolean c2s11 = false;
    boolean c2s12 = false;

    //initializes program at title screen
    public static void main(String[] args) {
        Player main = new Player();
        Game game = new Game(main);
        game.createTitleScreen();
        game.setVisible();
    }

    //initializes game window including size, color, and exit operation
    JFrame window;
    public Game(Player main) {
        Player player1 = main;
        window = new JFrame();
        window.setSize(1000, 800);//800 x 600
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.black);
        window.setLayout(null);
    }

    //auxillary method to set window visible when adding new layers in main
    public void setVisible() {
        window.setVisible(true);
    }

    //title screen variables
    JPanel titlePanel, startPanel, infoPanel, loadPanel;
    JLabel titleLabel;
    Font titleFont = new Font("sans serif", Font.BOLD, 80);

    JButton startButton, infoButton, loadButton;
    Font startFont = new Font("Times New Roman", Font.PLAIN, 50);


    /** Creates title screen and included buttons. Background should be black with the title
    //  "THE CALLING" in large green font. Buttons are white with black lettering. START and
    //  LOAD buttons are centered to the left, with an "i" information button centered to the right.
    //  Specific requirements 1.0.0 - 1.0.2.3*/
    public void createTitleScreen() {//hide game screen panels
        try {
            playerPanel.setVisible(false);
            optionPanel.setVisible(false);
            mainTextPanel.setVisible(false);
            savePanel.setVisible(false);
        } catch (Exception e) {}

        position = "title";
        //create title panel and label
        titlePanel = new JPanel();
        titlePanel.setBounds(100, 100, 800, 100); //w600
        titlePanel.setBackground(Color.black);

        titleLabel = new JLabel("THE CALLING");
        titleLabel.setForeground(Color.GREEN);
        titleLabel.setFont(titleFont);

        titlePanel.add(titleLabel);
        window.add(titlePanel);

        //creates start button and panel
        startPanel = new JPanel();
        startPanel.setBounds(300, 400, 200, 80);
        startPanel.setBackground(Color.black);

        startButton = new JButton("START");
        startButton.addActionListener(tsh);
        startButton.setBackground(Color.green);
        startButton.setForeground(Color.black);
        startButton.setFont(startFont);
        startButton.setFocusPainted(false);

        startPanel.add(startButton);
        window.add(startPanel);

        //creates load button and panel
        loadPanel = new JPanel();
        loadPanel.setBounds(300, 500, 200, 80);
        loadPanel.setBackground(Color.black);

        loadButton = new JButton("LOAD");
        loadButton.addActionListener(tsh); // Ensure the action listener is correctly set
        loadButton.setBackground(Color.green);
        loadButton.setForeground(Color.black);
        loadButton.setFont(startFont);
        loadButton.setFocusPainted(false);

        loadPanel.add(loadButton);
        window.add(loadPanel);

        //creates more info button and panel
        infoPanel = new JPanel();
        infoPanel.setBounds(600, 400, 50, 80);
        infoPanel.setBackground(Color.black);

        infoButton = new JButton("i");
        infoButton.addActionListener(ish);
        infoButton.setBackground(Color.green);
        infoButton.setForeground(Color.black);
        infoButton.setFont(startFont);
        infoButton.setFocusPainted(false);

        infoPanel.add(infoButton);
        window.add(infoPanel);
    }

    //variables for actual game screen
    JPanel mainTextPanel;
    JTextArea mainText;
    Font mainTextFont = new Font("Times New Roman", Font.PLAIN, 24);

    JPanel optionPanel;
    JButton option1, option2, option3, option4;
    Font optionFont = new Font("Times New Roman", Font.PLAIN, 15);

    JPanel playerPanel;
    JLabel hpLabel, hp, invLabel, inventory;
    Player player = new Player();

    JPanel savePanel;
    JButton saveButton, quitButton;

    /** Creates game screen and included buttons. Background should be black with a grey top bar.
    //  In the top bar are fields for HP and Inventory in green lettering to fit the theme. Below
    //  the top bar is a field for the main text of each scene, using white lettering on the black
    //  background for contrast. Bellow the main text field are three action buttons with changeable
    //  labels to allow for in game decisions and game progression. To the bottom right of the game
    //  screen are two buttons, one to save the game and one to quit the game back to the main menu.
    //  Specific requirements 1.1.0 - 1.1.7*/
    public void createGameScreen() {
        //hide title screen
        titlePanel.setVisible(false);
        startPanel.setVisible(false);
        infoPanel.setVisible(false);
        loadPanel.setVisible(false);

        //create main text panel and adds text to panel
        mainTextPanel = new JPanel();
        mainTextPanel.setBounds(100, 100, 800, 450); //600 x 250
        mainTextPanel.setBackground(Color.black);
        mainTextPanel.setVisible(true);
        window.add(mainTextPanel);

        mainText = new JTextArea("");
        mainText.setBounds(100, 100, 800, 450);// 600 x 250
        mainText.setBackground(Color.black);
        mainText.setForeground(Color.white);
        mainText.setFont(mainTextFont);
        mainText.setLineWrap(true);
        mainText.setVisible(true);
        mainTextPanel.add(mainText);

        //prevents words from being cut off on a new line
        mainText.setLineWrap(true);
        mainText.setWrapStyleWord(true);

        //creates options panel
        optionPanel = new JPanel();
        optionPanel.setBounds(325, 350, 300, 150);
        optionPanel.setBackground(Color.lightGray);
        optionPanel.setLayout(new GridLayout(4, 1));
        optionPanel.setVisible(true);
        window.add(optionPanel);

        //create option buttons
        option1 = new JButton("");
        option2 = new JButton("");
        option3 = new JButton("");
        option4 = new JButton("");
        option1.setVisible(true);
        option2.setVisible(true);
        option3.setVisible(true);
        option4.setVisible(true);

        //set button colors
        option1.setBackground(Color.darkGray);
        option2.setBackground(Color.darkGray);
        option3.setBackground(Color.darkGray);
        option4.setBackground(Color.darkGray);

//        option1.setForeground(Color.white);
//        option2.setForeground(Color.white);
//        option3.setForeground(Color.white);
//        option4.setForeground(Color.white);

        //set button font
        option1.setFont(optionFont);
        option2.setFont(optionFont);
        option3.setFont(optionFont);
        option4.setFont(optionFont);

        //connects each option to the option handler
        option1.addActionListener(oh);
        option2.addActionListener(oh);
        option3.addActionListener(oh);
        option4.addActionListener(oh);

        //sets unique command for each option on each scene
        option1.setActionCommand("o1");
        option2.setActionCommand("o2");
        option3.setActionCommand("o3");
        option4.setActionCommand("o4");

        optionPanel.add(option1);
        optionPanel.add(option2);
        optionPanel.add(option3);
        optionPanel.add(option4);

        //creates character panel
        playerPanel = new JPanel();
        playerPanel.setBounds(90, 15, 800, 50);
        playerPanel.setBackground(Color.DARK_GRAY);
        GridBagLayout gridBagLayout = new GridBagLayout();
        playerPanel.setLayout(gridBagLayout);

        // Creates HP label
        hpLabel = new JLabel("  HP:");
        hpLabel.setFont(mainTextFont);
        hpLabel.setForeground(Color.green);
        // Configures constraints for HP label
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.gridx = 0;
        gbc1.gridy = 0;
        gbc1.anchor = GridBagConstraints.WEST;
        gbc1.insets = new Insets(0, 0, 0, 10); // Adds spacing to the right
        // Adds HP label to playerPanel
        playerPanel.add(hpLabel, gbc1);

        // Creates HP value
        hp = new JLabel(player.getHP());
        hp.setFont(mainTextFont);
        hp.setForeground(Color.green);
        // Configures constraints for HP value
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridx = 1;
        gbc2.gridy = 0;
        gbc2.anchor = GridBagConstraints.WEST;
        gbc2.insets = new Insets(0, 0, 0, 150); // Adds spacing to the right
        // Adds HP value to playerPanel
        playerPanel.add(hp, gbc2);

        // Creates Inventory label
        invLabel = new JLabel("Inventory: ");
        invLabel.setFont(mainTextFont);
        invLabel.setForeground(Color.green);
        // Configures constraints for Inventory label
        GridBagConstraints gbc3 = new GridBagConstraints();
        gbc3.gridx = 2;
        gbc3.gridy = 0;
        gbc3.anchor = GridBagConstraints.WEST;
        gbc3.insets = new Insets(0, 10, 0, 10); // Adds spacing to the left and right
        // Adds Inventory label to playerPanel
        playerPanel.add(invLabel, gbc3);

        // Creates Inventory value
        inventory = new JLabel(player.getItems());
        inventory.setFont(mainTextFont);
        inventory.setForeground(Color.green);
        // Configures constraints for Inventory value
        GridBagConstraints gbc4 = new GridBagConstraints();
        gbc4.gridx = 3; // Adjust this value to make the column wider or narrower
        gbc4.gridy = 0;
        gbc4.anchor = GridBagConstraints.WEST;
        // Adds Inventory value to playerPanel
        playerPanel.add(inventory, gbc4);

        //Add player panel to window and set visible
        window.add(playerPanel);
        playerPanel.setVisible(true);

        //Create save & quit Panel
        savePanel = new JPanel();
        savePanel.setBounds(650, 650, 200, 40);
        savePanel.setBackground(Color.black);
        savePanel.setLayout(new GridLayout(1, 2));

        // Create and configure the Save Game button
        Font saveFont = new Font("Helvetica", Font.PLAIN, 14);
        saveButton = new JButton("SAVE");
        saveButton.setFont(saveFont);
        saveButton.setActionCommand("save");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveGame("savefile");
            }
        });
        saveButton.setBackground(Color.green);
        saveButton.setForeground(Color.black);
        savePanel.add(saveButton);

        // Create and configure the Quit Game button
        quitButton = new JButton("QUIT");
        quitButton.setFont(saveFont);
        quitButton.setActionCommand("quit");
        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                player.resetPlayer();
                returnToTitle();
            }
        });
        quitButton.setBackground(Color.green);
        quitButton.setForeground(Color.black);
        savePanel.add(quitButton);

        //add save panel to window and set visible
        savePanel.setVisible(true);
        window.add(savePanel);

    }

    //method to call after player could receive damage
    private void updateHPColor() {
        if (player.getHPNum() < 5) {
            hpLabel.setForeground(Color.RED); // Set color to red if HP is less than 5
            hp.setForeground(Color.RED); // Set color to red if HP is less than 5
        } else {
            hpLabel.setForeground(Color.GREEN); // Set color to green if HP is 5 or greater
            hp.setForeground(Color.GREEN); // Set color to green if HP is 5 or greater
        }
    }

    /** This method allows the game to quickly return to the title menu after a death or if the
    // player decides to quit the game
    // Specific requirements 4.1.0, 4.2.0, 2.7.2, 2.5.23.1, , */
    public void returnToTitle() {
        position = "title";
        //hide game screen
        savePanel.setVisible(false);
        playerPanel.setVisible(false);
        mainTextPanel.setVisible(false);
        optionPanel.setVisible(false);
        //show title screen
        titlePanel.setVisible(true);
        startPanel.setVisible(true);
        infoPanel.setVisible(true);
        loadPanel.setVisible(true);

    }

    //closes game window from death
    public void quitGame() {
        window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
    }

    /** Creates a method to save the game, with fields for all variables: Position (chapter and scene),
    //  HP, TrickedCount, NoHesitation, DreamHelp, ImpHelp, BlindDemon, PoisonKnife, and Inventory.
    //  Specific requirements 3.1.0 - 3.1.10*/
    public void saveGame(String fileName) {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName + ".txt")))) {
            // Save player information
            writer.println("Position: " + position);
            writer.println("HP: " + player.getHP());
            writer.println("TrickedCount: " + player.getTrickedCount());
            writer.println("NoHesitation: " + player.getNoHesitation());
            writer.println("DreamHelp: " + player.getDreamHelp());
            writer.println("ImpHelp: " + player.getImpHelp());
            writer.println("BlindDemon: " + player.getBlindDemon());
            writer.println("PoisonKnife: " + player.getPoisonKnife());
            writer.println("Inventory: " + player.getItems());

            System.out.println("Game saved successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Creates a method for loading and resuming a game from a savefile.txt document. Accepts all fields
    // included in the saveGame() function and loads a game screen with this information. Calls each scene
    // method directly using the position field and updates HP and Inventory fields. Also catches if no savefile
    // is found and asks the user to start a new game.
    // Specific requirements: 1.2.0 - 1.2.6*/
    public void loadGame(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Parse and set game state based on the saved information
                if (line.startsWith("Position:")) {
                    player.setPosition(line.substring("Position: ".length()));
                } else if (line.startsWith("HP:")) {
                    player.setHP(Integer.parseInt(line.substring("HP: ".length())));
                } else if (line.startsWith("TrickedCount:")) {
                    player.setTrickedCount(Integer.parseInt(line.substring("TrickedCount: ".length())));
                } else if (line.startsWith("NoHesitation:")) {
                    player.setNoHesitation(Boolean.parseBoolean(line.substring("NoHesitation: ".length())));
                } else if (line.startsWith("DreamHelp:")) {
                    player.setDreamHelp(Boolean.parseBoolean(line.substring("DreamHelp: ".length())));
                } else if (line.startsWith("ImpHelp:")) {
                    player.setImpHelp(Boolean.parseBoolean(line.substring("ImpHelp: ".length())));
                } else if (line.startsWith("BlindDemon:")) {
                    player.setImpHelp(Boolean.parseBoolean(line.substring("BlindDemon: ".length())));
                } else if (line.startsWith("PoisonKnife:")) {
                    player.setImpHelp(Boolean.parseBoolean(line.substring("PoisonKnife: ".length())));
                }else if (line.startsWith("Inventory:")) {
                    if(line.contains("None")) {
                        break;
                    } else {
                        String[] items = line.substring("Inventory: ".length()).split(", ");
                        for (String item : items) {
                            player.acquireItem(item);
                        }
                    }
                }
            }
            //change to game screen from title screen
            createGameScreen();

            // Directly invoke the method associated with the loaded position to act as a GOTO
            switch (player.getPosition()) {
                case "instructions":
                    instructions();
                    break;
                case "intro":
                    intro();
                    break;
                case "intro1":
                    intro1();
                    break;
                case "intro2":
                    intro2();
                    break;
                case "intro3":
                    intro3();
                    break;
                case "intro4":
                    intro4();
                    break;
                case "intro5":
                    intro5();
                    break;
                case "c1s1":
                    c1s1();
                    break;
                case "c1s2":
                    c1s2();
                    break;
                case "c1s3":
                    c1s3();
                    break;
                case "c1s4":
                    c1s4();
                    break;
                case "c1s5":
                    c1s5();
                    break;
                case "c1s6":
                    c1s6();
                    break;
                case "c1s7":
                    c1s7();
                    break;
                case "c1s8":
                    c1s8();
                    break;
                case "c1s9":
                    c1s9();
                    break;
                case "c1s10":
                    c1s10();
                    break;
                case "c1s11":
                    c1s11();
                    break;
                case "c1s12":
                    c1s12();
                    break;
                case "c1s13":
                    c1s13();
                    break;
                case "c1s15":
                    c1s15();
                    break;
                case "c1s16":
                    c1s16();
                    break;
                case "c1s17":
                    c1s17();
                    break;
                case "c1s18":
                    c1s18();
                    break;
                case "c1s19":
                    c1s19();
                    break;
                case "c1s21":
                    c1s21();
                    break;
                case "c1s22":
                    c1s22();
                    break;
                case "c1s23":
                    c1s23();
                    break;
                case "c1s24":
                    c1s24();
                    break;
                case "c1s25":
                    c1s25();
                    break;
                case "c1s26":
                    c1s26();
                    break;
                case "c1s27":
                    c1s27();
                    break;
                case "c1s28":
                    c1s28();
                    break;
                case "c1s29":
                    c1s29();
                    break;
                case "c2s1":
                    c2s1();
                    break;
                case "c2s2":
                    c2s2();
                    break;
                case "c2s3":
                    c2s3();
                    break;
                case "c2s4":
                    c2s4();
                    break;
                case "c2s5":
                    c2s5();
                    break;
                case "c2s6":
                    c2s6();
                    break;
                case "c2s7":
                    c2s7();
                    break;
                case "c2s7a":
                    c2s7a();
                    break;
                case "c2s8":
                    c2s8();
                    break;
                case "c2s9":
                    c2s9();
                    break;
                case "c2s10":
                    c2s10();
                    break;
                case "c2s11":
                    c2s11();
                    break;
                case "c2s12":
                    c2s12();
                    break;
                case "c2s13":
                    c2s13();
                    break;
                case "c2s14":
                    c2s14();
                    break;
                case "c2s15":
                    c2s15();
                    break;
                case "c2s16":
                    c2s16();
                    break;
                case "c2s17":
                    c2s17();
                    break;
                case "c2s18":
                    c2s18();
                    break;
                case "c2s19":
                    c2s19();
                    break;
                case "c2s20":
                    c2s20();
                    break;
                case "c2s21":
                    c2s21();
                    break;
                case "c2s22":
                    c2s22();
                    break;
                case "c2s23":
                    c2s23();
                    break;
                case "c2s24":
                    c2s24();
                    break;
                case "c2s25":
                    c2s25();
                    break;
                case "c2s26":
                    c2s26();
                    break;
                case "c2s27":
                    c2s27();
                    break;
                case "c2s28":
                    c2s28();
                    break;
                case "c2s29":
                    c2s29();
                    break;
                case "c2s30":
                    c2s30();
                    break;
                case "c2s30a":
                    c2s30a();
                    break;
                case "c2s30b":
                    c2s30b();
                    break;
                case "c2s31":
                    c2s31();
                    break;
                case "c3s1":
                    c3s1();
                    break;
                case "c3s2":
                    c3s2();
                    break;
                case "c3s3":
                    c3s3();
                    break;
                case "c3s4":
                    c3s4();
                    break;
                case "c3s5":
                    c3s5();
                    break;
                case "c3s6":
                    c3s6();
                    break;
                case "c3s7":
                    c3s7();
                    break;
                case "c3s8":
                    c3s8();
                    break;
                case "c3s9":
                    c3s9();
                    break;
                case "c3s10":
                    c3s10();
                    break;
                case "c3s11":
                    c3s11();
                    break;
                case "c3s12":
                    c3s12();
                    break;
                case "c3s13":
                    c3s13();
                    break;
                case "c3s14":
                    c3s14();
                    break;
                case "c3s15":
                    c3s15();
                    break;
                case "c3s16":
                    c3s16();
                    break;
                case "c3s17":
                    c3s17();
                    break;
                case "c3s18":
                    c3s18();
                    break;
                case "c3s19":
                    c3s19();
                    break;
                case "c3s20":
                    c3s20();
                    break;
                case "c3s21":
                    c3s21();
                    break;
                case "c3s22":
                    c3s22();
                    break;
                case "c3s23":
                    c3s23();
                    break;
                case "c3s24":
                    c3s24();
                    break;
                case "c3s25":
                    c3s25();
                    break;
                case "c3s25a":
                    c3s25a();
                    break;
                case "c3s26":
                    c3s26();
                    break;
                case "c3s27":
                    c3s27();
                    break;
                case "c3s28":
                    c3s28();
                    break;
                case "c3s29":
                    c3s29();
                    break;
                case "c3s30":
                    c3s30();
                    break;
                case "c3s31":
                    c3s31();
                    break;
                case "c3s32":
                    c3s32();
                    break;
                case "c3s33":
                    c3s33();
                    break;
                case "c3s34":
                    c3s34();
                    break;
                case "c3s35":
                    c3s35();
                    break;
                case "c3s36":
                    c3s36();
                    break;
                case "c3s37":
                    c3s37();
                    break;
                case "c3s38":
                    c3s38();
                    break;
                case "c3s39":
                    c3s39();
                    break;
                case "c3s40":
                    c3s40();
                    break;
                case "c3s41":
                    c3s41();
                    break;
                case "c4s1":
                    c4s1();
                    break;
                case "c4s2":
                    c4s2();
                    break;
                case "c4s3":
                    c4s3();
                    break;
                case "c4s4":
                    c4s4();
                    break;
                case "c4s5":
                    c4s5();
                    break;
                case "c4s6":
                    c4s6();
                    break;
                // Continue this pattern until c4s31
                case "c4s7":
                    c4s7();
                    break;
                case "c4s8":
                    c4s8();
                    break;
                case "c4s9":
                    c4s9();
                    break;
                case "c4s10":
                    c4s10();
                    break;
                case "c4s11":
                    c4s11();
                    break;
                case "c4s12":
                    c4s12();
                    break;
                case "c4s13":
                    c4s13();
                    break;
                case "c4s14":
                    c4s14();
                    break;
                case "c4s15":
                    c4s15();
                    break;
                case "c4s16":
                    c4s16();
                    break;
                case "c4s17":
                    c4s17();
                    break;
                case "c4s18":
                    c4s18();
                    break;
                case "c4s19":
                    c4s19();
                    break;
                case "c4s20":
                    c4s20();
                    break;
                case "c4s21":
                    c4s21();
                    break;
                case "c4s22":
                    c4s22();
                    break;
                case "c4s23":
                    c4s23();
                    break;
                case "c4s24":
                    c4s24();
                    break;
                case "c4s25":
                    c4s25();
                    break;
                case "c4s26":
                    c4s26();
                    break;
                case "c4s27":
                    c4s27();
                    break;
                case "c4s28":
                    c4s28();
                    break;
                case "c4s29":
                    c4s29();
                    break;
                case "c4s30":
                    c4s30();
                    break;
                case "c4s31":
                    c4s31();
                    break;
                case "c5s1":
                    c5s1();
                    break;
                case "c5s2":
                    c5s2();
                    break;
                case "c5s3":
                    c5s3();
                    break;
                case "c5s4":
                    c5s4();
                    break;
                case "c5s5":
                    c5s5();
                case "c5s6d":
                    c5s6d();
                    break;
                case "c5s7d":
                    c5s7d();
                    break;
                case "c5s8d":
                    c5s8d();
                    break;
                case "c5s9d":
                    c5s9d();
                    break;
                case "c5s10d":
                    c5s10d();
                    break;
                case "c5s11d":
                    c5s11d();
                    break;
                case "c5s12d":
                    c5s12d();
                    break;
                case "c5s13d":
                    c5s13d();
                    break;
                case "c5s14d":
                    c5s14d();
                    break;
                case "c5s15d":
                    c5s15d();
                    break;
                case "c5s16d":
                    c5s16d();
                    break;
                case "c5s17d":
                    c5s17d();
                    break;
                case "c5s18d":
                    c5s18();
                    break;
                case "c5s19d":
                    c5s19d();
                    break;
                case "c5s20d":
                    c5s20d();
                    break;
                case "c5s21d":
                    c5s21d();
                    break;
                case "c5s22d":
                    c5s22d();
                    break;
                case "c5s23d":
                    c5s23d();
                    break;
                case "c5s6":
                    c5s6();
                    break;
                case "c5s7":
                    c5s7();
                    break;
                case "c5s8":
                    c5s8();
                    break;
                case "c5s9":
                    c5s9();
                    break;
                case "c5s10":
                    c5s10();
                    break;
                case "c5s11":
                    c5s11();
                    break;
                case "c5s12":
                    c5s12();
                    break;
                case "c5s13":
                    c5s13();
                    break;
                case "c5s14":
                    c5s14();
                    break;
                case "c5s15":
                    c5s15();
                    break;
                case "c5s16":
                    c5s16();
                    break;
                case "c5s17":
                    c5s17();
                    break;
                case "c5s18":
                    c5s18();
                    break;
                case "c5s19":
                    c5s19();
                    break;
                case "c5s19a":
                    c5s19a();
                    break;
                case "c5s20":
                    c5s20();
                    break;
                case "c5s21":
                    c5s21();
                    break;
                case "c5s22":
                    c5s22();
                    break;

            }

            System.out.println("Game loaded successfully!");
        } catch (Exception e) {
            position = "load error";
            createGameScreen();
            mainText.setText("No save file found. \nStarting new game... press to continue");
            option1.setText("Continue");
        }
    }

    /** Creates information screen with pertinent author info.
    // Specific requirements: 1.3.0-1.3.2 */
    infoScreenHandler ish = new infoScreenHandler();
    public class infoScreenHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            position = "info";
            createGameScreen();

            //don't show in info menu
            savePanel.setVisible(false);
            playerPanel.setVisible(false);

            window.setBackground(Color.green);
            mainText.setText("Developed and written by Dylan Erudaitius, Kyler Ramos, & Zachary Walker");
            option1.setText("Return to Title");
            option2.setText("");
            option3.setText("");
            option4.setText("");
        }
    }

    //load and start handler for title screen
    titleScreenHandler tsh = new titleScreenHandler();

    public class titleScreenHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == startButton) {
                createGameScreen();
                instructions();
            } else if (e.getSource() == loadButton) {
                loadGame("savefile.txt");
            }
        }
    }

    /** Creates the option handler for running each scene in desired order. Some game mechanics code
     // is also found in this method, such as fight scenes and in scenes where probability has an effect.
     // case/switch statements are used here to quickly determine position in the storyline and call the
     // next scenes method, which then updates the position field. Switches are also listening for
     // user selections of in game options in order to alter the path of the game.
     // Specific requirements: 2.1.0 - 2.7.2*/
    optionHandler oh = new optionHandler();

    /**The option handler is the brains of the game
     *each decision that is made in a method goes through the option handler
     * to route the player to the next scene associated with their decision.
     *
     * Where possible, offload game logic such as player damage to the option handler.
     * This allows for you to check if the player is still alive before advancing to the
     * next scene, and make sure that the player can't see or make new decisions after
     * death. Offloading logic also allows for better readability of the scenes. When
     * writing the story, it helps to see clearly the script and options, and
     * where they lead. Adding game logic to the screen makes it difficult to read
     * what you are writing.
     */
    public class optionHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String pickedOption = e.getActionCommand();
            switch (position) {
                case "save":
                    saveGame("savefile.txt");
                    break;
                case "load":
                    loadGame("savefile.txt");
                    break;
                case "load error":
                    switch (pickedOption) {
                        case "o1":
                            instructions();
                            break;
                    }
                    break;
                case "info":
                    switch (pickedOption) {
                        case "o1":
                            returnToTitle();
                            break;
                    }
                    break;
                case "Death":
                    switch (pickedOption) {
                        case "o1":
                            returnToTitle();
                            break;
                    }
                    break;
                //start game
                case "instructions":
                    switch (pickedOption) {
                        case "o1":
                            intro();
                            break;
                    }
                    break;
                //start intro
                case "intro":
                    switch (pickedOption) {
                        case "o1":
                            intro1();
                            break;
                    }
                    break;
                case "intro1":
                    switch (pickedOption) {
                        case "o1":
                            intro2();
                            break;
                    }
                    break;
                case "intro2":
                    switch (pickedOption) {
                        case "o1":
                            intro3();
                            break;
                    }
                    break;
                case "intro3":
                    switch (pickedOption) {
                        case "o1":
                            intro4();
                            break;
                    }
                    break;
                case "intro4":
                    switch (pickedOption) {
                        case "o1":
                            intro5();
                            break;
                    }
                    break;
                case "intro5":
                    switch (pickedOption) {
                        case "o1":
                            c1s1();
                            break;
                    }
                    break;//End Intro
                //Start chapter 1
                case "c1s1":
                    switch (pickedOption) {
                        case "o1":
                            c1s2();
                            break;
                        case "o2":
                            c1s3();
                            break;
                    }
                    break;
                case "c1s2":
                    switch (pickedOption) {
                        case "o1":
                            c1s5();
                            break;
                    }
                    break;
                case "c1s3":
                    switch (pickedOption) {
                        case "o1":
                            c1s4();
                            break;
                    }
                    break;
                case "c1s4":
                    switch (pickedOption) {
                        case "o1":
                            c1s5();
                            break;
                    }
                    break;
                case "c1s5":
                    switch (pickedOption) {
                        case "o1":
                            c1s6();
                            break;
                    }
                    break;
                case "c1s6":
                    switch (pickedOption) {
                        case "o1":
                            c1s7();
                            break;
                    }
                    break;
                case "c1s7":
                    switch (pickedOption) {
                        case "o1":
                            player.acquireItem("Compass");
                            inventory.setText(player.getItems());
                            c1s8();
                            break;
                        case "o2":
                            player.acquireItem("Rope");
                            inventory.setText(player.getItems());
                            c1s8();
                            break;
                        case "o3":
                            player.acquireItem("Knife");
                            inventory.setText(player.getItems());
                            c1s8();
                            break;
                        case "o4":
                            player.acquireItem("Whistle");
                            inventory.setText(player.getItems());
                            c1s8();
                            break;

                    }
                    break;
                case "c1s8":
                    switch (pickedOption) {
                        case "o1":
                            c1s10();
                            break;
                        case "o2":
                            c1s9();
                    }
                    break;
                case "c1s9":
                    switch (pickedOption) {
                        case "o1":
                            c1s13();
                            break;
                    }
                    break;
                case "c1s10":
                    switch (pickedOption) {
                        case "o1":
                            if (player.hasItem("Whistle")) {
                                c1s11();
                                break;
                            } else {
                                c1s12();
                                break;
                            }
                    }
                    break;
                case "c1s11":
                    switch (pickedOption) {
                        case "o1":
                            c1s13();
                            break;
                    }
                    break;

                case "c1s12":
                    switch (pickedOption) {
                        case "o1":
                            c1s13();
                            break;
                    }
                    break;

                case "c1s13":
                    switch (pickedOption) {
                        case "o1":
                            c1s15();
                            break;
                    }
                    break;

                case "c1s15":
                    switch (pickedOption) {
                        case "o1":
                            c1s16();
                            break;
                        case "o2":
                            c1s17();
                            break;
                    }
                    break;
                case "c1s16":
                    switch (pickedOption) {
                        case "o1":
                            c1s21();
                            break;
                    }
                    break;
                case "c1s17":
                    switch (pickedOption) {
                        case "o1":
                            c1s21();
                            break;
                        case "o2":
                            c1s18();
                            break;
                    }
                    break;
                case "c1s18":
                    switch (pickedOption) {
                        case "o1":
                            if (player.hasItem("Rope")) { //if you have a rope, you will repair
                                c1s19();
                            } else { //if no rope, you move on
                                c1s21();
                            }
                            break;
                    }
                    break;
                case "c1s19":
                    switch (pickedOption) {
                        case "o1":
                            c1s21();
                            break;
                    }
                    break;
                case "c1s21":
                    switch (pickedOption) {
                        case "o1":
                            c1s22();
                            break;
                        case "o2":
                            c1s23();
                            break;
                    }
                    break;
                case "c1s22":
                    switch (pickedOption) {
                        case "o1":
                            c1s28();
                            break;
                    }
                    break;
                case "c1s23":
                    switch (pickedOption) {
                        case "o1":
                            if (player.hasItem("Whistle")) {
                                c1s24();
                            } else {
                                c1s25();
                            }
                            break;
                        case "o2":
                            if (player.hasItem("Knife")) {
                                c1s26();
                            } else {
                                c1s27();
                            }
                            break;
                    }
                    break;
                case "c1s24":
                    switch (pickedOption) {
                        case "o1":
                            c1s28();
                            break;
                    }
                    break;
                case "c1s25":
                    switch (pickedOption) {
                        case "o1":
                            c1s28();
                            break;
                    }
                    break;
                case "c1s26":
                    switch (pickedOption) {
                        case "o1":
                            c1s28();
                            break;
                    }
                    break;
                case "c1s27":
                    switch (pickedOption) {
                        case "o1":
                            c1s28();
                            break;
                    }
                    break;
                case "c1s28":
                    switch (pickedOption) {
                        case "o1":
                            c1s29();
                            break;
                    }
                    break;
                case "c1s29":
                    switch (pickedOption) {
                        case "o1":
                            c2s1();
                            break;
                    }
                    break;

                //-------------------------------------------------------- Chapter 2 --------------------------------------------------------------------------

                case "c2s1":
                    switch (pickedOption) {
                        case "o1":
                            c2s2();
                            break;
                        case "o2":
                            c2s3();
                            break;
                    }
                    break;

                case "c2s2":
                    switch (pickedOption) {
                        case "o1":
                            c2s4();
                            break;
                        case "o2":
                            c2s5();
                            break;
                    }
                    break;

                case "c2s3":
                    switch (pickedOption) {
                        case "o1":
                            c2s10();
                            break;
                    }
                    break;

                case "c2s4":
                    switch (pickedOption) {
                        case "o1":
                            c2s6();
                            break;
                        case "o2":
                            c2s5();
                            break;
                    }
                    break;

                case "c2s5":
                    switch (pickedOption) {
                        case "o1":
                            c2s7();
                            break;
                        case "o2":
                            c2s10();
                            break;
                    }
                    break;

                case "c2s6":
                    switch (pickedOption) {
                        case "o1":
                            if (player.hasItem("Knife")) {
                                Random rand = new Random();
                                int randomNumber = rand.nextInt(3) + 1; //66% chance of success
                                if (randomNumber == 1 || randomNumber == 2) { //if you roll 1 HIT!
                                    c2s8();
                                } else { //else miss
                                    c2s9();
                                    break;
                                }
                            } else {
                                Random rand = new Random();
                                int randomNumber = rand.nextInt(3) + 1;//33% chance of success
                                if (randomNumber == 1) { //if you roll 1, HIT!
                                    c2s8();
                                    break;
                                } else { //else miss
                                    c2s9();
                                    break;
                                }
                            }
                            break;
                    }
                    break;

                case "c2s7":
                    switch (pickedOption) {
                        case "o1":
                            c2s7a();
                            break;
                    }
                    break;

                case "c2s7a":
                    switch (pickedOption) {
                        case "o1":
                            c2s10();
                            break;
                    }
                    break;

                case "c2s8":
                    switch (pickedOption) {
                        case "o1":
                            c2s10();
                            break;
                    }
                    break;

                case "c2s9":
                    switch (pickedOption) {
                        case "o1":
                            c2s10();
                            break;
                    }
                    break;

                case "c2s10":
                    switch (pickedOption) {
                        case "o1":
                            c2s11();
                            break;
                        case "o2":
                            c2s12();
                            break;
                    }
                    break;

                case "c2s11":
                    switch (pickedOption) {
                        case "o1":
                            if (c2s12) {
                                c2s13();
                            } else {
                                c2s12();
                            }
                    }
                    break;

                case "c2s12":
                    switch (pickedOption) {
                        case "o1":
                            if (c2s11) {
                                c2s13();
                            } else {
                                c2s11();
                            }
                    }
                    break;

                case "c2s13":
                    switch (pickedOption) {
                        case "o1":
                            c2s14();
                            break;
                    }
                    break;

                case "c2s14":
                    switch (pickedOption) {
                        case "o1":
                            c2s15();
                            break;
                    }
                    break;

                case "c2s15":
                    switch (pickedOption) {
                        case "o1":
                            c2s16();
                            break;
                    }
                    break;

                case "c2s16":
                    switch (pickedOption) {
                        case "o1":
                            c2s17();
                            break;
                    }
                    break;

                case "c2s17":
                    switch (pickedOption) {
                        case "o1":
                            c2s18();
                            break;
                        case "o2":
                            c2s19();
                            break;
                    }
                    break;

                case "c2s18":
                    switch (pickedOption) {
                        case "o1":
                            c2s19();
                            break;
                    }
                    break;

                case "c2s19":
                    switch (pickedOption) {
                        case "o1":
                            c2s20();
                            break;
                        case "o2":
                            c2s21();
                    }
                    break;
                case "c2s20":
                    switch (pickedOption) {
                        case "o1":
                            c2s24();
                            break;
                        case "o2":
                            c2s22();
                    }
                    break;
                case "c2s21":
                    switch (pickedOption) {
                        case "o1":
                            c2s22();
                            break;
                        case "o2":
                            c2s23();
                    }
                    break;
                case "c2s22":
                    switch (pickedOption) {
                        case "o1":
                            c2s23();
                            break;
                        case "o2":
                            c2s26();
                    }
                    break;
                case "c2s23":
                    switch (pickedOption) {
                        case "o1":
                            c2s25();
                            break;
                        case "o2":
                            c2s26();
                    }
                    break;
                case "c2s24":
                    switch (pickedOption) {
                        case "o1":
                            c2s23();
                            break;
                    }
                    break;
                case "c2s25":
                    switch (pickedOption) {
                        case "o1":
                            c2s27();
                            break;
                    }
                    break;
                case "c2s26":
                    switch (pickedOption) {
                        case "o1":
                            c2s27();
                            break;
                    }
                    break;
                case "c2s27":
                    switch (pickedOption) {
                        case "o1":
                            c2s28();
                            break;
                        case "o2":
                            c2s28a();
                    }
                    break;
                case "c2s28":
                    switch (pickedOption) {
                        case "o1":
                            int rando = (int) (Math.random() * 2) + 1;
                            if (rando == 1) {
                                c2s30();
                            } else {
                                c2s30a();
                            }
                            break;
                        case "o2":
                            c2s29();
                    }
                    break;
                case "c2s28a":
                    switch (pickedOption) {
                        case "o1":
                            int rando = (int) (Math.random() * 2) + 1;
                            if (rando == 1) {
                                c2s30();
                            } else {
                                c2s30a();
                            }
                            break;
                        case "o2":
                            c2s29();
                    }
                    break;
                case "c2s29":
                    switch (pickedOption) {
                        case "o1":
                            c2s31();
                            break;
                    }
                    break;
                case "c2s30":
                    switch (pickedOption) {
                        case "o1":
                            c2s30a();
                            break;
                    }
                    break;
                case "c2s30a":
                    switch (pickedOption) {
                        case "o1":
                            c2s30b();
                            break;
                    }
                    break;
                case "c2s30b":
                    switch (pickedOption) {
                        case "o1":
                            c2s31();
                            break;
                    }
                    break;
                case "c2s31":
                    switch (pickedOption) {
                        case "o1":
                            c3s1();
                            break;
                    }
                    break;

                //-------------------------------------------------------- Chapter 3 --------------------------------------------------------------------------
                case "c3s1":
                    switch (pickedOption) {
                        case "o1":
                            c3s2();
                            break;
                    }
                    break;
                case "c3s2":
                    switch (pickedOption) {
                        case "o1":
                            if (player.hasItem("Knife")) { //knife grants 100% chance of success to harvest the nightshade
                                c3s4();
                                break;
                            } else {
                                Random rand = new Random();
                                int randomNumber = rand.nextInt(2) + 1; //50% chance of success
                                if (randomNumber == 1) { //if you roll, success
                                    c3s4();
                                    break;
                                } else { //else fail
                                    player.setHP(player.getHPNum() - 1); //subtract 1 health
                                    hp.setText(player.getHP()); //update HP in GUI
                                    c3s3();
                                    break;
                                }
                            }
                        case "o2":
                            c3s5();
                            break;
                    }
                    break;
                case "c3s3":
                    switch (pickedOption) {
                        case "o1":
                            Random rand = new Random();
                            int randomNumber = rand.nextInt(4) + 1; //75% chance of success
                            if (player.getHPNum() > 1) {
                                if (randomNumber == 1 || randomNumber == 2 || randomNumber == 3) { //if you roll, success
                                    c3s4();
                                    break;
                                } }else { //else fail
                                player.setHP(player.getHPNum() - 1); //subtract 1 health
                                hp.setText(player.getHP()); //update HP in GUI
                                if (player.getHPNum() < 1) {
                                    Death();
                                    break;
                                } else {
                                    c3s3();
                                    break;
                                }
                            }
                        case "o2":
                            if (player.getHPNum() < 1) {
                                Death();
                                break;
                            } else {
                                c3s5();
                                break;
                            }
                    }
                    break;
                case "c3s4":
                    switch (pickedOption) {
                        case "o1":
                            c3s5();
                            break;
                    }
                    break;
                case "c3s5":
                    switch (pickedOption) {
                        case "o1":
                            c3s6();
                            break;
                    }
                    break;
                case "c3s6":
                    switch (pickedOption) {
                        case "o1":
                            c3s7();
                            break;
                    }
                    break;
                case "c3s7":
                    switch (pickedOption) {
                        case "o1":
                            c3s8();
                            break;
                        case "o2":
                            c3s9();
                    }
                    break;
                case "c3s8":
                    switch (pickedOption) {
                        case "o1":
                            c3s11();
                            break;
                    }
                    break;
                case "c3s9":
                    switch (pickedOption) {
                        case "o1":
                            c3s10();
                            break;
                    }
                    break;
                case "c3s10":
                    switch (pickedOption) {
                        case "o1":
                            c3s11();
                            break;
                    }
                    break;
                case "c3s11":
                    switch (pickedOption) {
                        case "o1":
                            c3s12();
                            break;
                    }
                    break;
                case "c3s12":
                    switch (pickedOption) {
                        case "o1":
                            c3s13();
                            break;
                    }
                    break;
                case "c3s13":
                    switch (pickedOption) {
                        case "o1":
                            c3s14();
                            break;
                        case "o2":
                            c3s15();
                    }
                    break;
                case "c3s14":
                    switch (pickedOption) {
                        case "o1":
                            c3s15();
                            break;
                        case "o2": //option only available if player received help from the mother tree in their dream
                            if (player.getDreamHelp()) {
                                c3s16();
                            }
                    }
                    break;
                case "c3s15":
                    switch (pickedOption) {
                        case "o1":
                            c3s17();
                            break;
                        case "o2":
                            c3s18();
                    }
                    break;
                case "c3s16":
                    switch (pickedOption) {
                        case "o1":
                            c3s17();
                            break;
                        case "o2":
                            c3s18();
                    }
                    break;
                case "c3s17":
                    switch (pickedOption) {
                        case "o1":
                            c3s18();
                            break;
                    }
                    break;
                case "c3s18":
                    switch (pickedOption) {
                        case "o1":
                            c3s19();
                            break;
                        case "o2":
                            c3s19a();
                    }
                    break;
                case "c3s19":
                    switch (pickedOption) {
                        case "o1":
                            c3s20();
                            break;
                    }
                    break;
                case "c3s19a":
                    switch (pickedOption) {
                        case "o1":
                            c3s20();
                            break;
                    }
                    break;
                case "c3s20":
                    switch (pickedOption) {
                        case "o1":
                            c3s22();
                            break;
                        case "o2": //option only available if player has whistle
                            if (player.hasItem("Whistle")) {
                                c3s21();
                            }
                    }
                    break;
                case "c3s21":
                    switch (pickedOption) {
                        case "o1":
                            c3s22();
                            break;
                    }
                    break;
                case "c3s22":
                    switch (pickedOption) {
                        case "o1":
                            c3s23();
                            break;
                        case "o2":
                            player.setHP(player.getHPNum() - 1); //subtract 1 health
                            hp.setText(player.getHP()); //update HP in GUI
                            c3s25();
                    }
                    break;
                case "c3s23":
                    switch (pickedOption) {
                        case "o1":
                            c3s24();
                            break;
                        case "o2":
                            player.setHP(player.getHPNum() - 1); //subtract 1 health
                            hp.setText(player.getHP()); //update HP in GUI
                            c3s25();
                    }
                    break;
                case "c3s24":
                    switch (pickedOption) {
                        case "o1":
                            c3s26();
                            break;
                        case "o2":
                            c3s27();
                            break;
                        case "o3":
                            c3s28();
                            break;
                    }
                    break;
                case "c3s25":
                    if (player.getHPNum() < 1) {
                        Death();
                        break;
                    } else {
                        switch (pickedOption) {
                            case "o1":
                                c3s25a();
                        }}
                    break;
                case "c3s25a":
                    switch (pickedOption) {
                        case "o1":
                            c3s27();
                            break;
                        case "o2":
                            c3s28();
                    }break;
                case "c3s26":
                    switch (pickedOption) {
                        case "o1":
                            c3s29();
                            break;
                        case "o2":
                            c3s30();
                    }
                    break;
                case "c3s27":
                    switch (pickedOption) {
                        case "o1":
                            c3s29();
                            break;
                        case "o2":
                            c3s30();
                    }
                    break;
                case "c3s28":
                    switch (pickedOption) {
                        case "o1":
                            c3s29();
                            break;
                        case "o2":
                            c3s30();
                    }
                    break;
                case "c3s29", "c3s30":
                    switch (pickedOption) {
                        case "o1": //option only available if player has nightshade
                            if (player.hasItem("Nightshade")) {
                                c3s31();
                                break;
                            } else {
                                c3s32();
                                break;
                            }
                    }
                    break;
                case "c3s31":
                    switch (pickedOption) {
                        case "o1":
                            c3s32();
                            break;
                    }
                    break;
                case "c3s32":
                    switch (pickedOption) {
                        case "o1":
                            c3s33();
                            break;
                        case "o2":
                            c3s34();
                            break;
                        case "o3":
                            c3s35();
                    }
                    break;
                case "c3s33", "c3s34", "c3s35":
                    switch (pickedOption) {
                        case "o1":
                            c3s36();
                            break;
                        case "o2":
                            c3s37();
                    }
                    break;
                case "c3s36", "c3s37":
                    switch (pickedOption) {
                        case "o1":
                            c3s39();
                            break;
                        case "o2":
                            c3s38();
                    }
                    break;
                case "c3s38":
                    switch (pickedOption) {
                        case "o1":
                            c3s39();
                    }
                    break;
                case "c3s39":
                    switch (pickedOption) {
                        case "o1":
                            if (player.getImpHelp()) {
                                c3s40();
                                break;
                            } else {
                                c3s41();
                                break;
                            }
                    }
                    break;
                case "c3s40":
                    switch (pickedOption) {
                        case "o1":
                            c3s41();
                    }
                    break;
                case "c3s41":
                    switch (pickedOption) {
                        case "o1":
                            c4s1();
                    }
                    break;
                case "c4s1":
                    switch (pickedOption) {
                        case "o1":
                            c4s2();
                    }
                    break;
                case "c4s2":
                    switch (pickedOption) {
                        case "o1":
                            c4s4();
                            break;
                        case "o2":
                            c4s3();
                    }
                    break;
                case "c4s3":
                    switch (pickedOption) {
                        case "o1":
                            c4s4();
                    }
                    break;
                case "c4s4":
                    switch (pickedOption) {
                        case "o1":
                            c4s5();
                    }
                    break;
                case "c4s5":
                    switch (pickedOption) {
                        case "o1":
                            c4s7();
                            break;
                        case "o2":
                            c4s10();
                            break;
                        case "o3":
                            if (player.hasItem("Compass")) {
                                c4s6();
                                break;
                            }
                    }
                    break;
                case "c4s6":
                    switch (pickedOption) {
                        case "o1":
                            c4s7();
                            break;
                        case "o2":
                            c4s10();
                    }
                    break;
                case "c4s7":
                    switch (pickedOption) {
                        case "o1":
                            c4s10();
                            break;
                        case "o2":
                            c4s8();
                    }
                    break;
                case "c4s8":
                    switch (pickedOption) {
                        case "o1":
                            c4s9();
                    }
                    break;
                case "c4s9":
                    switch (pickedOption) {
                        case "o1":
                            c4s11();
                            break;
                        case "o2":
                            c4s12();
                    }
                    break;
                case "c4s10", "c4s11":
                    switch (pickedOption) {
                        case "o1":
                            c4s12();
                    }
                    break;
                case "c4s12":
                    switch (pickedOption) {
                        case "o1":
                            c4s13();
                            break;
                        case "o2":
                            c4s19();
                    }
                    break;
                case "c4s13":
                    switch (pickedOption) {
                        case "o1":
                            c4s14();
                    }
                    break;
                case "c4s14":
                    switch (pickedOption) {
                        case "o1":
                            c4s18();
                            break;
                        case "o2":
                            c4s15();
                            break;
                        case "o3":
                            c4s16();
                    }
                    break;
                case "c4s15":
                    switch (pickedOption) {
                        case "o1":
                            c4s16();
                            break;
                        case "o2":
                            c4s18();
                    }
                    break;
                case "c4s16":
                    switch (pickedOption) {
                        case "o1":
                            c4s17();
                    }
                    break;
                case "c4s17", "c4s18", "c4s19":
                    switch (pickedOption) {
                        case "o1":
                            c4s20();
                    }
                    break;
                case "c4s20":
                    switch (pickedOption) {
                        case "o1":
                            c4s21();
                    }
                    break;
                case "c4s21":
                    switch (pickedOption) {
                        case "o1":
                            c4s22();
                    }
                    break;
                case "c4s22":
                    switch (pickedOption) {
                        case "o1":
                            c4s23();
                    }
                    break;
                case "c4s23":
                    switch (pickedOption) {
                        case "o1":
                            c4s25();
                            break;
                        case "o2":
                            c4s24();
                    }
                    break;
                case "c4s24":
                    switch (pickedOption) {
                        case "o1":
                            c4s25();
                    }
                    break;
                case "c4s25":
                    switch (pickedOption) {
                        case "o1":
                            c4s27();
                            break;
                        case "o2":
                            c4s26();
                    }
                    break;
                case "c4s26":
                    switch (pickedOption) {
                        case "o1":
                            c4s27();
                    }break;
                case "c4s27":
                    switch (pickedOption) {
                        case "o1":
                            c4s29();
                            break;
                        case "o2":
                            c4s28();
                    }break;
                case "c4s28":
                    switch (pickedOption) {
                        case "o1":
                            c4s30();
                            break;
                        case "o2":
                            c4s29();
                    }
                    break;
                case "c4s29":
                    switch (pickedOption) {
                        case "o1":
                            c4s30();
                            break;
                        case "o2":
                            c4s31();
                    }break;
                case "c4s30" , "c4s31":
                    switch (pickedOption) {
                        case "o1":
                            c5s1();
                    }break;
                case "c5s1":
                    switch (pickedOption) {
                        case "o1":
                            if (player.getTrickedCount() == 3) {
                                c5s1d();
                                break;
                            } else {
                                c5s2();
                            }
                    }break;
                case "c5s2":
                    switch (pickedOption) {
                        case "o1": c5s3();
                            break;
                        case "o2": c5s6d();
                            break;
                        case "o3":
                            if (player.hasItem("Compass")) {
                                c5s4();
                                break;
                            } else {
                                break;
                            }
                    }break;
                case "c5s3":
                    switch (pickedOption) {
                        case "o1": c5s6();
                            break;
                        case "o2": c5s6d();
                            break;
                        case "o3":
                            if (player.hasItem("Compass")) {
                                c5s5();
                                break;
                            } else {
                                c5s6();
                                break;
                            }
                    }break;
                case "c5s4":
                    switch (pickedOption) {
                        case "o1":
                            c5s3();
                            break;
                        case "o2":
                            c5s6d();
                    }break;
                case "c5s5":
                    switch (pickedOption) {
                        case "o1":
                            c5s6();
                            break;
                        case "o2":
                            c5s6d();
                            break;
                    }break;
                case "c5s6":
                    switch (pickedOption) {
                        case "o1": c5s7();
                    }break;
                case "c5s7":
                    switch (pickedOption) {
                        case "o1": c5s8();
                            break;
                    }break;
                case "c5s8":
                    switch (pickedOption) {
                        case "o1":
                            c5s9();
                            break;
                    }break;
                case "c5s9":
                    switch (pickedOption) {
                        case "o1":
                            c5s10();
                            break;
                    }break;
                case "c5s10":
                    switch (pickedOption) {
                        case "o1":
                            c5s11();
                            break;
                    }break;
                case "c5s11":
                    switch (pickedOption) {
                        case "o1":
                            c5s12();
                            break;
                        case "o2":
                            c5s13();
                    }break;
                case "c5s12" , "c5s13":
                    switch (pickedOption) {
                        case "o1", "o2":
                            c5s14();
                    }break;
                case "c5s14":
                    switch (pickedOption) {
                        case "o1":
                            c5s19();
                            break;
                        case "o2":
                            if (player.hasItem("Poison")) {
                                c5s15();
                                break;
                            }
                    }break;
                case "c5s15":
                    switch (pickedOption) {
                        case "o1":
                            c5s16();
                            break;
                        case "o2":
                            c5s19();
                    }break;
                case "c5s16":
                    switch (pickedOption) {
                        case "o1":
                            c5s17();
                            break;
                        case "o2":
                            c5s19a();
                    }break;
                case "c5s17":
                    switch (pickedOption) {
                        case "o1":
                            c5s18();
                            break;
                        case "o2":
                            c5s19a();
                    }break;
                case "c5s18":
                    switch (pickedOption) {
                        case "o1":
                            c5s22();
                    }break;
                case "c5s19":
                    switch (pickedOption) {
                        case "o1":
                            c5s20();
                    }break;
                case "c5s19a":
                    switch (pickedOption) {
                        case "o1":
                            c5s22();
                    }break;
                case "c5s20":
                    switch (pickedOption) {
                        case "o1":
                            c5s21();
                    }break;
                case "c5s21":
                    switch (pickedOption) {
                        case "o1":
                            c5s22();
                    }break;
                case "c5s22":
                    switch (pickedOption) {
                        case "o1": c5s11d();
                            break;
                        case "o2":
                            if (player.hasItem("Orion's beard")) {
                                c5s8d();
                                break;
                            }break;
                        case "o3":
                            if (player.hasItem("Whistle")) {
                                c5s10d();
                                break;
                            }break;
                        case "o4":
                            if (player.hasItem("Knife")) {
                                c5s9d();
                                break;
                            }break;
                    }break;
                // ---------------------------------- Demon Fight ------------------------------------------
                case "c5s6d":
                    switch (pickedOption) {
                        case "o1" , "o2": c5s7d();
                    }break;
                case "c5s7d":
                    switch (pickedOption) {
                        case "o1": c5s11d();
                            break;
                        case "o2":
                            if (player.hasItem("Orion's beard")) {
                                c5s8d();
                                break;
                            }break;
                        case "o3":
                            if (player.hasItem("Whistle")) {
                                c5s10d();
                                break;
                            }break;
                        case "o4":
                            if (player.hasItem("Knife")) {
                                c5s9d();
                                break;
                            }break;
                    }break;
                case "c5s8d":
                    switch (pickedOption) {
                        case "o1": c5s11d();
                            break;
                        case "o3":
                            if (player.hasItem("Whistle")) {
                                c5s10d();
                                break;
                            }break;
                        case "o4":
                            if (player.hasItem("Knife")) {
                                c5s9d();
                                break;
                            }break;
                    }break;
                case "c5s9d":
                    switch (pickedOption) {
                        case "o1": c5s11d();
                            break;
                        case "o2":
                            if (player.hasItem("Orion's beard")) {
                                c5s8d();
                                break;
                            }break;
                    }break;
                case "c5s10d":
                    player.setDemonHP(player.getDemonHP() - 3); //3 damage to demon
                    switch (pickedOption) {
                        case "o1": c5s11d();
                            break;
                        case "o2":
                            if (player.hasItem("Orion's beard")) {
                                c5s8d();
                                break;
                            }break;
                    }break;
                case "c5s11d":
                    switch (pickedOption) {
                        case "o1" , "o2" , "o3":
                            player.setHP(player.getHPNum() - 1); //1 damage to player
                            player.setDemonHP(player.getDemonHP() - 1); //1 damage to demon
                            hp.setText(player.getHP());
                            c5s12d();
                    }break;
                case "c5s12d":
                    if (player.getHPNum() < 1) {
                        Death();
                    } else {
                        switch (pickedOption) {
                            case "o1":
                                player.setHP(player.getHPNum() - 1); //1 damage to player
                                hp.setText(player.getHP());
                                c5s13d();
                                break;
                            case "o2":
                                c5s14d();
                        }}break;
                case "c5s13d":
                    if (player.getHPNum() < 1) {
                        Death();
                    } else {
                        switch (pickedOption) {
                            case "o1" , "o2" , "o3":
                                c5s14d();
                        }}break;
                case "c5s14d":
                    if (player.getHPNum() < 1) {
                        Death();
                    } else {
                        switch (pickedOption) {
                            case "o1" , "o2" , "o3":
                                c5s15d();
                        }}break;
                case "c5s15d":
                    player.setDemonHP(player.getDemonHP() -2);// -2 Damage to demon
                    if (player.getHPNum() < 1) {
                        Death();
                    } else {
                        switch (pickedOption) {
                            case "o1":
                                player.setHP(player.getHPNum() - 1);//1 damage to player
                                hp.setText(player.getHP());
                                c5s16d();
                        }}break;
                case "c5s16d":
                    if (player.getHPNum() < 1) {
                        Death();
                    } else {
                        switch (pickedOption) {
                            case "o1":
                                c5s17d();
                        }}break;
                case "c5s17d":
                    if (player.getHPNum() < 1) {
                        Death();
                    } else {
                        switch (pickedOption) {
                            case "o1":
                                c5s18d();
                        }}break;
                case "c5s18d":
                    player.setDemonHP(player.getDemonHP() -2);// -2 Damage to demon
                    if (player.getHPNum() < 1) {
                        Death();
                    } else {
                        switch (pickedOption) {
                            case "o1":
                                c5s19d();
                                break;
                            case "o2":
                                c5s20d();
                                break;
                            case "o3":
                                c5s21d();
                        }}break;

                case "c5s19d" , "c5s20d" , "c5s21d":
                    if (player.getHPNum() < 1) {
                        Death();
                        break;
                    }else if (player.getDemonHP() > 1) {
                        Random rand = new Random();
                        int bonus = 1;
                        switch (pickedOption) {
                            case "o1":
                                //hurt demon
                                if (player.hasItem("Knife")) { // +2 bonus for knife
                                    bonus = 2;
                                    if(player.getPoisonKnife()) { // +2 bonus for poison
                                        bonus = 4;
                                    }
                                }
                                int randomNumber = rand.nextInt(8) + 1 + bonus; //1-8 damage on Demon + any bonus
                                player.setDemonHP(player.getDemonHP() - randomNumber );

                                //hurt player
                                if (player.getBlindDemon()) {
                                    int randomNum = rand.nextInt(3) + 1; // 1-4 damage on player if demon is blinded
                                    player.setHP(player.getHPNum() - randomNum);
                                } else {
                                    int randomNum = rand.nextInt(5) + 1; //1-6 damage on player
                                    player.setHP(player.getHPNum() - randomNum);
                                }
                                hp.setText(player.getHP());
                                c5s19d();
                                break;
                            case "o2":
                                //hurt demon
                                if (player.hasItem("Knife")) { // +2 bonus for knife
                                    bonus = 2;
                                    if(player.getPoisonKnife()) { // +2 bonus for poison
                                        bonus = 4;
                                    }
                                }
                                int randomNumber2 = rand.nextInt(5) + 1 + bonus; //1-5 damage on Demon + any bonus
                                player.setDemonHP(player.getDemonHP() - randomNumber2 );

                                //hurt player
                                if (player.getBlindDemon()) {
                                    int randomNum = rand.nextInt(3) + 1; // 1-4 damage on player if demon is blinded
                                    player.setHP(player.getHPNum() - randomNum);
                                } else {
                                    int randomNum = rand.nextInt(5) + 1; //1-6 damage on player
                                    player.setHP(player.getHPNum() - randomNum);
                                }
                                hp.setText(player.getHP());
                                c5s20d();
                                break;
                            case "o3":
                                //hurt demon
                                if (player.hasItem("Knife")) { // +2 bonus for knife
                                    bonus = 2;
                                    if(player.getPoisonKnife()) { // +2 bonus for poison
                                        bonus = 4;
                                    }
                                }
                                player.setDemonHP(player.getDemonHP() - bonus - 2 );//2 damage on Demon + any bonus

                                //hurt player
                                if (player.getBlindDemon()) {
                                    int randomNum = rand.nextInt(3) + 1; // 1-4 damage on player if demon is blinded
                                    player.setHP(player.getHPNum() - randomNum);
                                } else {
                                    int randomNum = rand.nextInt(5) + 1; //1-6 damage on player
                                    player.setHP(player.getHPNum() - randomNum);
                                }
                                hp.setText(player.getHP());
                                c5s21d();
                                break;
                        }

                    } else {
                        hp.setText(player.getHP());
                        c5s22d();
                    }break;
                case "c5s22d":
                    switch (pickedOption) {
                        case "o1":
                            c5s23d();
                    }break;
                case "c5s23d":
                    switch (pickedOption) {
                        case "o1":
                            returnToTitle();
                    }break;

            }

        }
    }


    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Introduction text ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // This Chapter (or epilogue) sets up the scene for the player stepping into a new world. 7 introduction
    // scenes are presented, along with a death scene that could be called at many different points
    // throughout the game.
    // Specific requirements 1.1.4, 1.1.4.1, and 2.7.0-2.7.2
    // */
    private void Death() {
        position = "Death";
        player.setPosition("Death");
        mainText.setText("Suddenly, everything goes black. Despite a gallant effort, death arrives, and escorts you to the afterlife. Maybe in another life, things will turn out differently.\n\nYour score was:" + player.getHighScore());
        option1.setText(">");
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }

    private void instructions() {
        position = "instructions";
        player.setPosition("instructions");
        mainText.setText("Your choices seal your fate, shaping the journey ahead. Each decision ripples through the game, altering challenges and opportunities. Keep a vigilant watch on your health in the top left—your survival hangs by a thread. You are equipped with the items you gather along your travels, shown in your inventory on the top right. Use them wisely...");
        option1.setText(">");
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void intro() {
        position = "intro";
        player.setPosition("intro");
        mainText.setText("You stare down at the amulet hanging around your neck, eyeing the sigil carved on the back. The symbol and green color, both unique to the forest elves, had withstood the passage of time. You had always admired the rough and unpolished quality of the elven stone. It reminded you of your mother’s stories of your father, the ones you’d heard since a child. Stories of how your father conquered battle after battle during the Great War.");
        option1.setText(">");
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void intro1() {
        position = "intro1";
        player.setPosition("intro1");
        mainText.setText("How he returned home to her each time, helping her tend to the crops and herbs that kept her and the rest of the city fed. Until he didn’t. Your mother always cried at this part of the story, reminding you that you were just an infant. She always spared the details of how the city fell, and skipped to how she fled to the forest with you to survive.");
        option1.setText(">");
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void intro2() {
        position = "intro2";
        player.setPosition("intro2");
        mainText.setText("Her voice would quiver whenever she explained the fog that consumed us. How we wandered for days, searching for food or shelter, or an escape. How just on the brink of starvation, we emerged from the forest into a village. The village where you now rested, leaning under an apple tree near the house. ");
        option1.setText(">");
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void intro3() {
        position = "intro3";
        player.setPosition("intro3");
        mainText.setText("Your mother said that the villagers were kind, but they were wary of the forest. You were familiar with their tales of how the forest consumes, but never gives. Until you appeared. That’s why they had given you the name Keito, “Blessing of the forest”.");
        option1.setText(">");
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void intro4() {
        position = "intro4";
        player.setPosition("intro4");
        mainText.setText("It seemed to fit. The forest felt both familiar yet mysterious to you, and despite the villagers’ warnings of dangers that no one could comprehend, you felt a connection to it. Part of you wondered if it was your elven heritage. A secret your human mother had kept from the villagers your entire life.");
        option1.setText(">");
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void intro5() {
        position = "intro5";
        player.setPosition("intro5");
        mainText.setText("Aside from unusually vibrant green eyes and slightly pointed ears, there was no way of telling you apart from the other village children. Your mind drifted back to the forest. You could almost hear a soft whisper whenever you neared its edge. Your body relaxes at the thought, tired from a long day of farming. You allow yourself to melt into the earth as you drift into a deep sleep.");
        option1.setText(">");
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }

    /** -------------------------------------------- Chapter 1: Into the woods -----------------------------------
    //  Chapter one follows our hero into the woods, and allows the player to select which item they want to bring
    //  with them on their journey from a group of 4: Compass, Rope, Knife, and whistle. Some are more useful than
    //  others, but each one has an effect on the game play, allowing for better re-playability of the game. The
    //  player also encounters some wildlife large and small in the following scenes, quickly having an effect on
    //  the precious HP bar.
    //  Specific requirements: 2.1.0 - 2.1.29.1 */
    private void c1s1() {
        position = "c1s1";
        player.setPosition("c1s1");
        mainText.setText("A loud voice wakes you. You aren't sure what it was saying, only the feeling that it beckoned for you from the forest. The sound plays in your mind, overpowering the village's silence.");
        option1.setText("Follow the direction of the voice");
        option2.setText("Head to the village; ignoring the voice.");
    }
    private void c1s2() {
        position = "c1s2";
        player.setPosition("c1s2");
        mainText.setText("Before heading out, you make sure to bring home the basket of apples you gathered. Once home, you grab your carrying pouch, and an apple for the road.");
        player.setHP(player.getHPNum() + 1);
        option1.setText(">");
        option2.setText("");
        option3.setText("");
        option4.setText("");
        player.setNoHesitation(true);
        hp.setText(player.getHP());
    }
    private void c1s3() {
        position = "c1s3";
        player.setPosition("c1s3");
        mainText.setText("As you head home the voice reappears. Although unintelligible, you can hear the urgency in it's tone. By the time you reach home it's almost unbearable, etching itself into your thoughts. Your neighbor notices the grimace expression you carry and asks what was wrong. You barely manage to let out an unconvincing \"oh, nothing\" before closing the front door.");
        option1.setText(">");
        option2.setText("");
        option3.setText("");
        option4.setText("");

    }
    private void c1s4() {
        position = "c1s4";
        player.setPosition("c1s4");
        mainText.setText("Clearly you are the only one hearing this noise. Unable to bear it, you grab your carrying pouch and follow the voice into the forest, hoping you can stop whatever is causing this.");
        option1.setText(">");
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c1s5() {
        position = "c1s5";
        player.setPosition("c1s5");
        mainText.setText("As you enter the forest, a fog slowly surrounds you, muffling the voice to a soft and distant sound. As you regain your senses, a sudden fear sets in, driven by the stories of the fog your mother told you. You take a moment to calm yourself before continuing.");
        option1.setText(">");
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c1s6() {
        position = "c1s6";
        player.setPosition("c1s6");
        mainText.setText("You catch yourself lost in thought, your mother's words still ringing in your ears. You look up and realize its beginning to get dark, and much more difficult to see. You pick up the pace and continue down the trail.");
        option1.setText(">");
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c1s7() {
        position = "c1s7";
        player.setPosition("c1s7");
        mainText.setText("While walking you stumble upon a skeleton leaned up against an Oak tree, it's hand gripping an exposed root. It's dressed in tattered leather armor. You notice that the chest bears the same sigil as your father's amulet. Every clan has their own unique design and color, but you always thought the green and twisting features of the forest elf sigil to be the most beautiful. Beside them is a decomposed satchel with some useful items in it. You only have enough room for one... ");
        option1.setText("Take compass");
        option2.setText("Take rope");
        option3.setText("Take knife");
        option4.setText("Take whistle");
    }
    private void c1s8() {
        position = "c1s8";
        player.setPosition("c1s8");
        mainText.setText("You notice a patch of horse's mane grass, your mother had mentioned hiding in these when you were both was trapped here. They insulate heat and shield you from the cold, all while providing excellent shelter. You could settle down here for the night.");
        option1.setText("Sleep here");
        option2.setText("Continue looking for more suitable shelter");
        option3.setText("");
        option4.setText("");
    }
    private void c1s9() {
        position = "c1s9";
        player.setPosition("c1s9");
        mainText.setText("You find a makeshift tent made from leaning tree branches and packed leaves. There’s even a cot to sleep on. You wonder how long that elf was out here and if they also heard the voice that plagues your mind. Hopefully a night’s rest will prevent it from reappearing… You wake, after what feels like a blink of an eye, and step outside.");
        option1.setText(">");
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c1s10() {
        position = "c1s10";
        player.setPosition("c1s10");
        mainText.setText("You wake up to a loud snort. Your eyes shoot open as you quickly survey the area. Examining the grass is a giant brown bear. Maybe sleeping out in the open next to a pile of bones wasn’t a great idea!");
        option1.setText(">");
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    //If whistle
    private void c1s11() {
        position = "c1s11";
        player.setPosition("c1s11");
        mainText.setText("You slowly reach for your whistle. The bear seems to have caught your scent. As it sniffs its way toward you, you blow the whistle as hard as you can. The bear lets out a loud grunt and jumps away from the grass. You continue to blow as you slowly back away and make distance.");
        option1.setText(">");
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    //else
    private void c1s12() {
        position = "c1s12";
        player.setPosition("c1s12");
        mainText.setText("You slowly take a few steps backward while facing the bear. As you do, your foot snaps a twig, startling the bear. As the bear stands on its back legs you turn and run as fast as you can. While sprinting away you trip on a stray tree root and bang your knee against a rock.");
        player.setHP(player.getHPNum() - 1);
        option1.setText(">");
        option2.setText("");
        option3.setText("");
        option4.setText("");
        hp.setText(player.getHP());
    }
    private void c1s13() {
        position = "c1s13";
        player.setPosition("c1s13");
        if (player.hasItem("Compass")) {
            mainText.setText("As you look around your realize that you have no idea where you are. Nearby, a thin trail vanishes into the forest. You take your compass out and check your bearing. The compass seems to be broken. It holds it position, but certainly isn't pointing north.");
        } else {
            mainText.setText("As you look around your realize that you have no idea where you are. Nearby, a thin trail vanishes into the forest.");
        }
        option1.setText(">");
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    //c1S14 was deleted and skipped to accommodate new logic
    private void c1s15() {
        position = "c1s15";
        player.setPosition("c1s15");
        mainText.setText("All of a sudden you hear a faint voice. Is it in your head? It sounds frightened. You look around frantically, searching for someone. The voice gets louder. What direction is it coming from? You close your eyes to focus and immediately regret it. The voice shrieks so piercingly you feel as though your eardrums will burst. Then almost as quickly as it started, it stops. You look in the direction of the voice and wonder if you should follow it.");
        option1.setText("Follow the voice into the forest");
        option2.setText("Stick to the trail");
        option3.setText("");
        option4.setText("");
    }
    private void c1s16() {
        position = "c1s16";
        player.setPosition("c1s16");
        if (player.hasItem("Compass")) {
            mainText.setText("You head in the direction of the voice. After a while you notice the trees becoming denser. You start to stumble over roots and pause to catch your balance. As you rest you realize your fist is clenched around the compass. You loosen your grip and peer down at it to see that it’s pointing in the direction you’re walking. You wonder why you’re hanging on to this thing if it doesn’t even work properly. With no more sign of the voice or its origin, you head back and continue down the trail.");
        } else {
            mainText.setText("You head in the direction of the voice. After a while you notice the trees becoming denser. You start to stumble over roots and pause to catch your balance. With no more sign of the voice or its origin, you head back and continue down the trail.");
        }
        option1.setText(">");
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c1s17() {
        position = "c1s17";
        player.setPosition("c1s17");
        mainText.setText("You feel uneasy and decide to walk away. Behind you is a trail that leads into a clearing, and more importantly, away from where that voice was. You follow the trail. Finally something appears in the distance. As you move closer you see that it’s a well. It might be a good idea to check it out.");
        option1.setText("Continue onwards");
        option2.setText("Investigate well");
        option3.setText("");
        option4.setText("");
    }
    private void c1s18() {
        position = "c1s18";
        player.setPosition("c1s18");
        mainText.setText("The well appears to be broken. The bucket is sitting on the ground next to it, partially covered in rust. Without a rope, its rendered useless.");
        //change option text if player has rope
        if (player.hasItem("Rope")) {
            option1.setText("Repair the well");
        } else {
            option1.setText(">");
        }
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c1s19() {
        position = "c1s19";
        player.setPosition("c1s19");
        mainText.setText("The well appears to be broken. The bucket is sitting on the ground next to it, partially covered in rust. Remembering your rope, you pull it out and begin to tie a not around the buckets handle. Slowly you lower the bucket into the well. Just as you near the end of your rope, you hear the bucket slap the water. You quickly reel in the bucket, and drink until your thirst is completely quenched.");
        player.setHP(player.getHPNum() + 1);
        hp.setText(player.getHP());
        option1.setText(">");
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c1s21() {
        position = "c1s21";
        player.setPosition("c1s21");
        mainText.setText("As you continue your trek into the forest, you begin to get a faint feeling of being watched. It grows, until you start to hear the sound of cracking twigs and crunching leaves. You come to a stop, but the sounds stop too.");
        option1.setText("Continue onward");
        option2.setText("Stop to investigate the sound");
        option3.setText("");
        option4.setText("");
    }
    private void c1s22() {
        position = "c1s22";
        player.setPosition("c1s22");
        mainText.setText("As you continue forward, you once again begin to hear distinct footsteps, even closer this time. Just as you turn around, a mountain lion lunges at you! Quickly, you bring your left arm up to block its gnashing teeth while you scramble with your right arm to find anything that could help. Your hand finds a nearby rock and you swing it at the lion's face. It lets go and runs off into the trees. You tear a piece of your cloak to use as a bandage and dress the wound. In a hurry, you head off in the opposite direction of the mountain lion.");
        player.setHP(player.getHPNum() - 6);
        hp.setText(player.getHP());
        option1.setText(">");
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c1s23() {
        position = "c1s23";
        player.setPosition("c1s23");
        mainText.setText("You choose to bide your time, sinking into a crouch as you survey the terrain. After some time passes, you finally spot the source of the sound. There, camouflaged by the underbrush a mere 20 feet away, a mountain lion's gaze piercing through the foliage.");
        option1.setText("Try to scare it away");
        option2.setText("Attack!");
        option3.setText("");
        option4.setText("");
    }
    private void c1s24() {
        position = "c1s24";
        player.setPosition("c1s24");
        mainText.setText("You reach into your bag for the whistle and once again put it to good use. The big cat lopes off into the distance, spooked by the loud noise.");
        option1.setText(">");
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c1s25() {
        position = "c1s25";
        player.setPosition("c1s25");
        mainText.setText("You reach down and grab a few rocks, quickly throwing them in the lion's direction. She bounds off.");
        option1.setText(">");
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c1s26() {
        position = "c1s26";
        player.setPosition("c1s26");
        mainText.setText("Reaching into your pack, your hand finds the hilt of the knife. You run towards the animal just as it leaps in your direction. With a quick sidestep and slash, you manage cut its hind leg. It lets out a roar and jets off into the trees. Although it likely won't return, you flee the area just to be safe.");
        hp.setText(player.getHP());
        option1.setText(">");
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c1s27() {
        position = "c1s27";
        player.setPosition("c1s27");
        mainText.setText("Without much of a plan, you grab a rock and run towards the animal. It lunges at you, and you jump to the side to avoid its attack, falling to the ground in the process. It jumps on top of you and goes for a bite, but you brace the attack with your forearm. With your free hand you grab a nearby rock and swing at its face. It lets go and runs off into the trees. You tear a piece of your cloak to use as a bandage and dress the wound. In a hurry, you head off in the opposite direction of the mountain lion.");
        player.setHP(player.getHPNum() - 6);
        hp.setText(player.getHP());
        option1.setText(">");
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c1s28() {
        position = "c1s28";
        player.setPosition("c1s28");
        mainText.setText("Heart still pounding, you find a large rock to lean against and rest. You notice a strange moss growing along the bottom. A teal light twinkles faintly in its leaves, like stars in the night sky. You recognize it!");
        option1.setText(">");
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c1s29() {
        position = "c1s29";
        player.setPosition("c1s29");
        mainText.setText("This is a Orion’s Beard! Your mother had told you about how the soldiers would use it during the Great War. It can heal the user when consumed. You scrape some into a spare vessel, add it to your pack, and continue onward");
        player.acquireItem("Orion's beard");
        inventory.setText(player.getItems());
        option1.setText(">");
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }

    /** ----------------------------------------Chapter 2: An unraveling conflict -------------------------------------
    //  Being out in the woods is starting to have an effect on our weary player. In chapter 2, more background is given
    //  to the conflict and a few new characters are introduced. Inventory items are found and used to make other items
    //  that will have key roles in the impending conflict.
    //  Specific requirements: 2.2.0 - 2.2.31 */
    private void c2s1() {
        position = "c2s1";
        player.setPosition("c2s1");
        mainText.setText("You feel a sharp pang of hunger in your stomach and remember that you haven't eaten since you entered the forest.");
        option1.setText("Look for food");
        option2.setText("Ignore the hunger");
        option3.setText("");
        option4.setText("");
    }
    private void c2s2() {
        position = "c2s2";
        player.setPosition("c2s2");
        mainText.setText("You scan the surrounding area taking note of the foliage and possible hiding areas for animals. You notice a large tree nearby and another area that has a collection of small bushes.");
        option1.setText("Inspect bushes");
        option2.setText("Inspect tree");
        option3.setText("");
        option4.setText("");
    }
    private void c2s3() {
        position = "c2s3";
        player.setPosition("c2s3");
        mainText.setText("No time to waste, must keep moving");
        option1.setText(">");
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c2s4() {
        position = "c2s4";
        player.setPosition("c2s4");
        mainText.setText("You kneel down to investigate the soil around the bushes, finding tracks and scat, a clear sign that a small mammal lives in the area. You could wait for it to return and attempt to catch it or you could go check the tree you saw earlier. ");
        option1.setText("Wait and hunt"); //c2s6
        option2.setText("Go back and inspect tree"); //c2s5
        option3.setText("");
        option4.setText("");
    }
    private void c2s5() {
        position = "c2s5";
        player.setPosition("c2s5");
        mainText.setText("This is a cherry bark elm tree. Its sap is highly nutritious, but is usually processed and cleaned before eating. You notice some sap oozing from a section of the tree where the bark is lifted.");
        option1.setText("Eat sap"); //c2s7
        option2.setText("Move on"); //c2s10
        option3.setText("");
        option4.setText("");
    }
    private void c2s6() {
        position = "c2s6";
        player.setPosition("c2s6");
        if (player.hasItem("Knife")) {
            mainText.setText("Careful not to make any noise, you slowly reach into your bag and grab your knife. You slowly raise your hand, reach back, and swiftly throw the knife.");
        } else {
            mainText.setText("While looking on the ground for a suitable weapon, your eyes spot a perfectly sized stone. Careful not to make any noise, you gently pick up the rock. You slowly raise your hand, reach back, and swiftly throw the rock.");
        }
        option1.setText(">"); //c2s8
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c2s7() {
        position = "c2s7";
        player.setPosition("c2s7");
        if (player.hasItem("Knife")) {
            mainText.setText("You grab your knife and plunge it into the tree trunk, moving it back and forth to allow for the golden sap to escape. A thick pool of sap with a honey-like consistency is exposed. As it begins to run down the trunk you scoop it into your hands and take a drink. You feel better already!");
        } else {
            mainText.setText("You dig your fingers under the bark that is lifting from the tree and manage to peel it away, exposing a cluster of glittering amber sap.You pluck the small pieces until you have collected enough to chew. Its a bit bitter, but you feel better already!");
        }player.setHP(player.getHPNum() + 1);
        hp.setText(player.getHP());
        option1.setText(">"); //c2s7a
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c2s7a() {
        position = "c2s7a";
        player.setPosition("c2s7a");
        mainText.setText("As you chew the sap, a sudden numbness sets in. Slowly you are pulled from reality, floating in darkness. You can feel a pulse, an energy that beats through your entire body. You also feel another. It beats at a different rhythm. Like two waves in a pond, it feels as though it's canceling yours out, each pump jolting your entire body. The immense pressure knocks you back, and as you gasp for air you find yourself on the ground, beside the tree whose sap you had just eaten. You slowly make your way back to the trail, contemplating the meaning of your vision, if that's what it was.");
        option1.setText(">"); //c2s10
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c2s8() { //HIT THE SQUIRREL
        position = "c2s8";
        player.setPosition("c2s8");
        if (player.hasItem("Knife")) {
            mainText.setText("Your knife strikes the squirrel directly in its side, and you watch the life drain quickly from its eyes. Eagerly, you process the meat and dd it to your pack for later cooking.");
        } else {
            mainText.setText("The rock lands directly on the squirrel's head. You promptly strike it once to put it out of it's misery.");
        }
        option1.setText(">"); //c2s10
        option2.setText("");
        option3.setText("");
        option4.setText("");
        player.acquireItem("Raw meat");
        inventory.setText(player.getItems());
    }
    private void c2s9() { //MISS THE SQUIRREL
        position = "c2s9";
        player.setPosition("c2s9");
        if (player.hasItem("Knife")) {
            mainText.setText("Your knife narrowly misses the squirrel and disappears into the shrub. The squirrel darts off to a nearby tree and scrambles up until it is lost in the foliage. You walk over to the shrub and spend time frustratingly searching for your knife. Defeated and hungry, you make your way back to the trail and continue onward.");
        } else {
            mainText.setText("The rock narrowly misses the squirrel, skipping off of the ground beside it. The squirrel darts off to a nearby tree and scrambles up until it is lost in the foliage. Defeated and hungry, you make your way back to the trail and continue onward.");
        }
        option1.setText(">"); //c2s10
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c2s10() {
        position = "c2s10";
        player.setPosition("c2s10");
        mainText.setText("As you walk, you can’t help but notice the increasing number of trees that bear the scars of disease, bark mottled and leaves withering. Weeds surround their base. As you gaze upon the exposed roots of the tree beside you, you remember the elf you had encountered earlier. His hand joined with the tree that supported him, as if in silent communion. The tales of elves and their profound bond with the forest echo in your thoughts. You pause, considering the possibility that these trees, both afflicted and unharmed, may hold an untapped wisdom.");
        option1.setText("Investigate a healthy tree"); //c2s11
        option2.setText("Investigate a diseased tree"); //c2s12
        option3.setText("");
        option4.setText("");
    }
    private void c2s11() {
        c2s11 = true; // marks that "Investigate healthy tree" was selected
        position = "c2s11";
        player.setPosition("c2s11");
        //text changes if it is the first or second choice
        if (c2s12) {
            mainText.setText("You approach the healthy tree. Again, you reach out and touch it. You begin to feel a presence that dwells in the heart of the forest, its strength waning as it desperately fights to protect something. Although drained and wary, it emits an expansiveness that overwhelms you. It cries out in desperation as it pushes back against a shadow that threatens to overtake yet another loved one. Exhausted, the guardian staggers, unsure if its resolve is enough to prevent the seemingly inevitable destruction that it adamantly resists.");
        } else {
            mainText.setText("You approach the healthy tree, reaching your hand out to touch it. As you concentrate you feel a presence that dwells in the heart of the forest, its strength waning as it desperately fights to protect something. Although drained and wary, it emits an expansiveness that overwhelms you. It cries out in desperation as it pushes back against a shadow that threatens to overtake yet another loved one. Exhausted, the guardian staggers, unsure if its resolve is enough to prevent the seemingly inevitable destruction that it adamantly resists.");
        }
        //if the other option has been visited, next scene, otherwise visit it
        if (c2s12) {
            option1.setText(">"); //c2s13
        } else {
            option1.setText("Investigate a diseased tree"); //c2s12
        }
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c2s12() {
        c2s12 = true; // marks that "Investigate diseased tree" was selected
        position = "c2s12";
        player.setPosition("c2s12");
        //text changes if it is the first or second choice
        if (c2s11) {
            mainText.setText("You approach the diseased tree. Again, you reach out and touch it. Your senses begin to dull, then refocus on a concentrated force gathering in the depths of the forest, it's power surging as it revels in triumph. Though the war is not yet won, it boasts, certain of an unequivocal victory. Amused by the agony of its victims, it draws out their suffering, relishing each moment as they struggle and falter under its grip. Slowly, it reaches out, consuming more and more, desecrating its surroundings and sinking its claws into the one who challenges its supremacy. Rejuvenated, it consumes and transforms the resistance into the very fuel of its resurgence.");
        } else {
            mainText.setText("You approach the diseased tree, reaching your hand out to touch it. Your senses begin to dull, then refocus on a concentrated force gathering in the depths of the forest, it's power surging as it revels in triumph. Though the war is not yet won, it boasts, certain of an unequivocal victory. Amused by the agony of its victims, it draws out their suffering, relishing each moment as they struggle and falter under its grip. Slowly, it reaches out, consuming more and more, desecrating its surroundings and sinking its claws into the one who challenges its supremacy. Rejuvenated, it consumes and transforms the resistance into the very fuel of its resurgence.");
        }
        //if the other option has been visited, next scene, otherwise visit it
        if (c2s11) {
            option1.setText(">"); //c2s13
        } else {
            option1.setText("Investigate a healthy tree"); //c2s11
        }
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c2s13() {
        position = "c2s13";
        player.setPosition("c2s13");
        mainText.setText("You step back from the tree and continue walking, collecting yourself after the intense visions. The further you wander into the forest, the clearer it becomes that your tie to the woods is more than simple curiosity. It is a bond that is etched into your Elvin bloodline, passed down for generations. The voice calls out to you, and a primal instinct guides you in its direction with conviction. You acknowledge your destiny to come to its aid.");
        option1.setText(">"); //c2s14
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c2s14() {
        position = "c2s14";
        player.setPosition("c2s14");
        mainText.setText("As night begins to fall, you begin searching for a suitable shelter. Not far from the path is a large Ficus tree, its buttress roots reaching nearly 3 meters high. You find a roomy notch between the roots, and make a roof with leaves and branches. Once satisfied with your shelter, you begin collecting wood to build a fire.");
        option1.setText(">"); //c2s15
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c2s15() {
        position = "c2s15";
        player.setPosition("c2s15");
        if (player.hasItem("Raw meat")) {
            mainText.setText(" Eagerly, you begin building your fire. The smoke turns into a small flame, and in time a crackling fire. You take the raw meat from your bag and fashion a small rotisserie with twigs. The smell of cooked food makes you even hungrier than you realized. Though not much, you thoroughly enjoy your meal. Satisfied, you lie near the fire, watching the light dance off of the side of your shelter. Eyes heavy, you fall asleep, thinking of all that happened today and wondering what tomorrow will bring.");
            player.removeItem("Raw meat"); //remove raw meat
            player.setHP(player.getHPNum() + 3); //add 3 health
        } else {
            mainText.setText("You begin building your fire. The smoke turns into a small flame, and in time a crackling fire. Your stomach rumbles loudly. It's been a while since you’ve had a meal, and it's beginning to  wear on you. Exhausted, you lie near the fire, watching the light dance off of the side of your shelter. Eyes heavy, you fall asleep, thinking of all that happened today and wondering what tomorrow will bring.");
            player.setHP(player.getHPNum() - 1); //subtract 1 health
        }
        option1.setText(">"); //c2s16
        option2.setText("");
        option3.setText("");
        option4.setText("");
        inventory.setText(player.getItems());
        hp.setText(player.getHP());
    }
    private void c2s16() {
        position = "c2s16";
        player.setPosition("c2s16");
        mainText.setText("A cool mist passes over you, causing you to sit up and open your eyes. A thick fog surrounds you, making it difficult to see more than a meter away. As you stand, the ground disappears. You find it difficult to maintain balance, and are forced to shuffle your feet in order to walk forward. While attempting to find your bearing, you hear a voice emerge. You face its direction, only to see the silhouette of what looks like a large Willow tree.");
        option1.setText(">"); //c2s17
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c2s17() {
        position = "c2s17";
        player.setPosition("c2s17");
        mainText.setText("\"At last, we finally meet. What took you so long to find me?\" The voice seems to be coming from the tree itself.");
        option1.setText("Who are you?");//c2s18
        option2.setText("I was lost"); //c2s19
        option3.setText("");
        option4.setText("");
    }
    private void c2s18() {
        position = "c2s18";
        player.setPosition("c2s18");
        mainText.setText("\"You have spent days wandering my domain, breathing my air. I am the heart of this forest. Again, I ask, what took you so long to find me?\" ");
        option1.setText("I was lost"); //c2s19
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c2s19() {
        position = "c2s19";
        player.setPosition("c2s19");
        mainText.setText("\"I have drawn you to me since your entrance into my kingdom. You were not lost, just ignorant to my guidance. Are you also ignorant to that which threatens this forest?\"");
        option1.setText("I know of the threat"); //c2s20
        option2.setText("I'm here to save you"); //c2s21
        option3.setText("");
        option4.setText("");
    }
    private void c2s20() {
        position = "c2s20";
        player.setPosition("c2s20");
        mainText.setText("\"This threat once roamed the forest free, feared by all. It is a powerful being both in raw strength and cunning. Your false confidence protects only your ego. It is the wise who fear, for they understand the depth of their own ignorance\"");
        option1.setText("I fear nothing!"); //c2s24
        option2.setText("What should I do?"); //c2s22
        option3.setText("");
        option4.setText("");
    }
    private void c2s21() {
        position = "c2s21";
        player.setPosition("c2s21");
        mainText.setText("\"How is it that you plan to save me? This is no trivial fight. This threat once roamed the forest free, feared by all. It is a powerful being both in raw strength and cunning. It is a fool's errand to willingly face such an enemy.\"");
        option1.setText("What should I do?"); //c2s22
        option2.setText("I can fight!"); //c2s23
        option3.setText("");
        option4.setText("");
    }
    private void c2s22() {
        position = "c2s22";
        player.setPosition("c2s22");
        mainText.setText("\"You should run. It is too late for me, you could never reach me in time. Even if you did, you would surely parish. Escape is your only salvation.\"");
        option1.setText("I can fight!"); //c2s23
        option2.setText("Maybe your right"); //c2s26
        option3.setText("");
        option4.setText("");
    }
    private void c2s23() {
        position = "c2s23";
        player.setPosition("c2s23");
        if (player.hasItem("Compass")) {
            mainText.setText("\"Fight with what, your compass? Maybe your poor choice reflects how you seek the guidance of others to solve your problems, thus why you are asking me to decide your fate now. When nobody is there for you, what will you do? Your inability to take steps of your own will only lead to your demise.\" You look around and notice that the fog has slowly started to turn into what looks like the smoke of burning coal.The tree's silhouette fades.");
        } else if (player.hasItem("Rope")) {
            mainText.setText("\"Fight with what, your rope? Maybe your poor choice reflects how you lack focus. Your attempt to be well rounded is a safe option, but there is no safety in battle. Risks must be taken. You stand no chance against a force whose will and resolve towers over yours\" You look around and notice that the fog has slowly started to turn into what looks like the smoke of burning coal.The tree's silhouette fades.");
        } else if (player.hasItem("Whistle")) {
            mainText.setText("\"Fight with what, your whistle? Maybe your poor choice reflects how when faced with adversity, you call for others to come to your aid. I am dying, and you will be alone against an unmeasurable evil. You have not the strength to stand alone against a foe greater than your understanding.\" You look around and notice that the fog has slowly started to turn into what looks like the smoke of burning coal.The tree's silhouette fades.");
        } else {
            mainText.setText("\"Fight with what, your dulled knife? You've come ill prepared for battle. This foe has been waging war for thousands of years, its flesh encountering thousands of strikes and slashes. Even if you manage a successful blow, it will be met with a force far greater. Not even a scar will remain to tell the tale of how you wasted your life.\" You look around and notice that the fog has slowly started to turn into what looks like the smoke of burning coal. The tree's silhouette fades.");
        }
        option1.setText("There's always hope!"); //c2s25
        option2.setText("Maybe you're right"); //c2s26
        option3.setText("");
        option4.setText("");
    }
    private void c2s24() {
        position = "c2s24";
        player.setPosition("c2s24");
        mainText.setText("\"The beast that stalks these woods will not pause to ponder the courage in your heart. It will rend you limb from limb, indifferent to your fear or your valor.\"");
        option1.setText("I can fight!"); //c2s23
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c2s25() {
        position = "c2s25";
        player.setPosition("c2s25");
        mainText.setText("The black smoke begins to amass into a vague form that resembles a Minotaur. “Hope you say?” The voice scoffs, then with a deeper tone continues. “Hope is but a sweet poison that the damned consume in the midst of defeat. Carry your hope with you to your grave”");
        option1.setText("What are you really?"); //c2s27
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c2s26() {
        position = "c2s26";
        player.setPosition("c2s26");
        mainText.setText("The black smoke begins to amass into a vague form that resembles a Minotaur. “It was naive to question my judgment.” The voice deepens, and takes an indifferent tone. “Heed my advice and relish what's left of your petty existence\"");
        option1.setText("What are you really?"); //c2s27
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c2s27() {
        position = "c2s27";
        player.setPosition("c2s27");
        mainText.setText("“You seek truth from that which deceives you? If you trust me, you should run. If not, then there is no need for me to answer” The black smoke violently bubbles out of the ground around you.");
        option1.setText("I will kill you!"); //c2s28
        option2.setText("Reveal yourself!"); //c2s28a
        option3.setText("");
        option4.setText("");
    }
    private void c2s28a() {
        position = "c2s28a";
        player.setPosition("c2s28a");
        mainText.setText("The smoke becomes hot, burning you as it seeps from the ground beneath your feet. The smoke billows until the demon's true form is exposed. It towers above you, with spiraling horns like an antelope, and breath as hot as flames. Gazing down upon you, its lips curl into a menacing smile, revealing rows of tangled thin teeth. The demon laughs \"Kill me? You think highly of yourself mortal.\" Then with a voice like thunder, they shout \"I DARE YOU TO TAKE ONE STEP IN MY DIRECTION!\"");
        option1.setText("Try to move"); //c2s30
        option2.setText("Plea for mercy"); //c2s29
        option3.setText("");
        option4.setText("");
    }
    private void c2s28() {
        position = "c2s28";
        player.setPosition("c2s28");
        mainText.setText("The smoke becomes hot, burning your skin as it seeps from the ground beneath your feet. With a voice like thunder, the demon shouts  “I WELCOME YOU TO TAKE ONE STEP IN MY DIRECTION!” The smoke billows until the demon's true form is exposed. It towers above you, with spiraling horns like an antelope, and breath as hot as flames. Gazing down upon you, its lips curl into a menacing smile, revealing rows of tangled thin teeth.");
        option1.setText("Try to move"); //c2s30
        option2.setText("Plea for mercy"); //c2s29
        option3.setText("");
        option4.setText("");
    }
    private void c2s29() {
        position = "c2s29";
        player.setPosition("c2s29");
        mainText.setText("“I gave you a chance to leave, but your resistance intrigues me. I’ve decided that it would be fun to see you fill with despair” The demon’s arm begins to churn into a waterfall of smoke, crashing as it hits the ground and flows toward you.");
        option1.setText(">"); //c2s31
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    //cannot take a step
    private void c2s30() {
        position = "c2s30";
        player.setPosition("c2s30");
        mainText.setText("Trembling amidst the pressure of the demon’s malice, you attempt to take a step. As if sunken in quicksand, your legs don’t respond. The demon laughs “As I thought. Realize your fate, and die a painful death” ");
        option1.setText(">"); //c2s30a
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    //takes a step
    private void c2s30a() {
        position = "c2s30a";
        player.setPosition("c2s30a");
        mainText.setText("Trembling amidst the pressure of the demon’s malice, you attempt to take a step. A tremendous weight fights your movement. You close your eyes, collect your thoughts, and focus on moving even the slightest bit. Suddenly, a small root emerges from the soil, pushing the bottom of your foot and allowing enough momentum for you to take a step.");
        option1.setText(">"); //c2s30b
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c2s30b() {
        position = "c2s30b";
        player.setPosition("c2s30b");
        mainText.setText("This shocks the demon, who seemingly didn’t see the root that helped you. The demon shouts in frustration “A single step is all you will achieve!” The demon’s arm begins to transform into a waterfall of smoke, crashing as it hits the ground and flows toward you.");
        option1.setText(">"); //c2s31
        option2.setText("");
        option3.setText("");
        option4.setText("");
        player.setDreamHelp(true);
    }
    private void c2s31() {
        position = "c2s31";
        player.setPosition("c2s31");
        mainText.setText("The smoke crawls up your body, searing your skin as it makes its way into your mouth and fills your lungs. Unable to breath, all you hear is the demon laughing with pleasure as your vision slowly fades…");
        option1.setText(">"); //c3s1
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }

    /** --------------------------------------------------------Chapter 3: Words of warning----------------------------
    //  In chapter 3, looping scenes are introduced and further backstory is given. We meet more characters who give
    //  us riddles to ponder over in our travels. Discovering the solutions could help us get to the bottom of a much
    //  larger mystery.
    //  Specific requirements: 2.3.0 - 2.3.41 */

    private void c3s1() {
        position = "c3s1";
        player.setPosition("c3s1");
        mainText.setText("Your body jolts up as you gasp for air. Frantically you look around, and realize that you are back in camp, underneath your shelter. The embers of last night's fire are still emanating heat. It must have been a nightmare, although the phantom pains of the black smoke still linger in your mind. Daylight has just begun to break, and a sense of urgency compels you to move onward.");
        option1.setText(">"); //c3s2
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c3s2() {
        position = "c3s2";
        player.setPosition("c3s2");
        mainText.setText("As you set off from camp, you notice thorny nightshade growing at the base of a few of the dying trees. If you are careful enough, you could harvest this to make a poison.");
        option1.setText("Harvest the nightshade"); //c3s3 or c3s4
        option2.setText("Move on"); //c3s5
        option3.setText("");
        option4.setText("");
    }
    private void c3s3() { //fail to harvest the plant (-1HP)
        position = "c3s3";
        player.setPosition("c3s3");
        mainText.setText("As you reach for the plant, one of the thorns pricks you!");
        if (player.getHPNum() < 1) {
            option1.setText(">");//Death
            option2.setText("");
            option3.setText("");
            option4.setText("");
        } else {
            option1.setText("Try again"); //c3s3 or c3s4
            option2.setText("Move on"); //c3s5
            option3.setText("");
            option4.setText("");
        }
    }
    private void c3s4() { //successfully harvest the plant
        position = "c3s4";
        player.setPosition("c3s4");
        if (player.hasItem("Knife")) {
            mainText.setText("You use your knife to scrape off the thorns at the base of the plant. Once cleaned of thorns, you grasp the base and carefully pull the plant from the ground, being sure not to damage the roots. After successfully harvesting the plant, you add it to your pouch and continue your journey.");
        } else {
            mainText.setText("You slowly grasp the plant, being careful not to touch any thorns. You loosen the dirt at the base of the plant, then carefully pull the plant from the ground, being sure not to damage the roots. After successfully harvesting the plant, you add it to your pouch and continue your journey.");
        }
        option1.setText(">"); //c3s5
        option2.setText("");
        option3.setText("");
        option4.setText("");
        player.acquireItem("Nightshade");
        inventory.setText(player.getItems());
    }
    private void c3s5() {
        position = "c3s5";
        player.setPosition("c3s5");
        if (player.hasItem("Compass")) {
            mainText.setText("After walking for a while a thin trail begins to develop, making it easier to traverse the terrain. More and more of the trees are afflicted with disease, and the forest is beginning to lose its color. You check your compass, and the needle is pinned in the direction of the trail.");
        } else {
            mainText.setText("After walking for a while a thin trail begins to develop, making it easier to traverse the terrain. More and more of the trees are afflicted with disease, and the forest is beginning to lose its color.");
        }
        option1.setText(">"); //c3s6
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c3s6() {
        position = "c3s6";
        player.setPosition("c3s6");
        mainText.setText("Suddenly, you see a small patch of grass and wildflowers. It seems to taper off of the main trail, and leads to a small pristine grove of trees. You walk off of the trail to investigate.");
        option1.setText(">"); //c3s7
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c3s7() {
        position = "c3s7";
        player.setPosition("c3s7");
        mainText.setText("As you walk along the grass trail, you notice a small creature seated in the center of the grove. It looks to be a forest sprite!");
        option1.setText("Call out to the sprite"); //c3s8
        option2.setText("Approach quietly"); //c3s9
        option3.setText("");
        option4.setText("");
    }
    private void c3s8() {
        position = "c3s8";
        player.setPosition("c3s8");
        mainText.setText("The forest sprite perks up, and struggles to fly up to a branch on a tree at the edge of the grove. When she lands, you see that her left wing is damaged. “Please, I have no business with you traveler. Go back to where you came.”");
        option1.setText("I only want to help"); //c3s11
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c3s9() {
        position = "c3s9";
        player.setPosition("c3s9");
        mainText.setText("You approach the grove, and get a clearer view of the sprite. Her left wing appears to be damaged, and she looks exhausted. She catches a glimpse of you, and immediately flies toward the nearest tree. She clumsily misses the tree hollow, and scrambles up to a branch, panting heavily.");
        option1.setText(">"); //c3s10
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c3s10() {
        position = "c3s10";
        player.setPosition("c3s10");
        mainText.setText("\"Please, leave me be! I have no business with you.\" Clearly she is tired and anxious.");
        option1.setText("I mean no harm"); //c3s11
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c3s11() {
        position = "c3s11";
        player.setPosition("c3s11");
        mainText.setText("“I have no reason to trust what you say, but I feel a calmness about you that I can trust. So long as you stay seated there at the center of the grove, I will talk with you.” She gestures to the grass a few meters from where you stand.");
        option1.setText("What happened to you?"); //c3s12
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c3s12() {
        position = "c3s12";
        player.setPosition("c3s12");
        mainText.setText(" “A war has been waged on my home. A demon lurks in the depths of the forest, draining life from our protector. I attempted to stop it, but am much too weak. I was swatted away like a fly and damaged my wing”");
        option1.setText("Why is this grove untouched?"); //c3s13
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c3s13() {
        position = "c3s13";
        player.setPosition("c3s13");
        mainText.setText("“The mother tree used what little strength she had to protect me, creating this refuge in case a young traveler….” you see the sprite lean forward while squinting, to get a closer look at you. “ What do you know of this conflict?”");
        option1.setText("I saw the demon in my dream"); //c3s14
        option2.setText("I sensed it from the trees"); //c3s15
        option3.setText("");
        option4.setText("");
    }
    private void c3s14() {
        position = "c3s14";
        player.setPosition("c3s14");
        mainText.setText("“It reached out to you?!” her voice trembled. “The Mother tree must be near death, this is horrible news” Her lips quiver as she holds back tears. “I’m afraid it may be too late.”");
        option1.setText("But she's been calling me"); //c3s15
        if (player.getDreamHelp()){
            option2.setText("She helped in my dream!"); //c3s16
        } else {
            option2.setText("");
        }

        option3.setText("");
        option4.setText("");
    }
    private void c3s15() {
        position = "c3s15";
        player.setPosition("c3s15");
        if (player.getNoHesitation()) {
            mainText.setText("“So you’re of Elvin blood? Then you’ve heard the mother tree’s calls?” You explain to the sprite that it was the mother tree’s voice that led you into the forest. “I see, then you may very well be whom she intended this message for.”");
        } else {
            mainText.setText("“So you’re of Elvin blood? Then you’ve heard the mother tree’s calls?” You explain to the sprite that it was the mother tree’s voice that led you into the forest. How you were the only one in the village that could hear it. “I see, then you may very well be whom she intended this message for.”");
        }
        option1.setText("She spoke of me?"); //c3s17
        option2.setText("What message?"); //c3s18
        option3.setText("");
        option4.setText("");
    }
    private void c3s16() {
        position = "c3s16";
        player.setPosition("c3s16");
        mainText.setText("“Then she's still alive!” The sprite's smile fades, as she looks off into the distance and ponders. “Then you are of Elvin blood? It seems you are the one she intended this message for.”");
        option1.setText("She spoke of me?"); //c3s17
        option2.setText("What message?"); //c3s18
        option3.setText("");
        option4.setText("");
    }
    private void c3s17() {
        position = "c3s17";
        player.setPosition("c3s17");
        mainText.setText("“Yes, though she was unable to tell me just who you would be. Only that a call to the distance had caught the attention of a young soul, and that they would be our last hope.” The sprite sighs with a desperate tone.");
        option1.setText("What is the message?"); //c3s18
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c3s18() {
        position = "c3s18";
        player.setPosition("c3s18");
        mainText.setText("\"Listen carefully...\"\n“In shadow's clutch, the tree does weep,\nIts silent plea, a secret deep.\nTo break the chain, the night unveils,\nA precious thread that tips the scales.”");
        option1.setText("Any insights?"); //c3s19
        option2.setText("How does this help me?"); //c3s19a
        option3.setText("");
        option4.setText("");
    }
    private void c3s19() {
        position = "c3s19";
        player.setPosition("c3s19");
        mainText.setText("“She gave no explanation, only instructions to wait for you. I’ve been reciting it hundreds of times so as to not forget, and am still no closer to understanding it. I assume she wanted to obscure its meaning from the Demon, in case it got a hold of the message.”");
        option1.setText("Thank you for your help"); //c3s20
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c3s19a() {
        position = "c3s19a";
        player.setPosition("c3s19a");
        mainText.setText("\"I understand your frustration, but taking it out on me wont help. I assume she wanted to obscure its meaning from the Demon, in case it got a hold of the message. The mother tree must have thought you capable enough to decipher it.\"");
        option1.setText("I apologize, thank you"); //c3s20
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c3s20() {
        position = "c3s20";
        player.setPosition("c3s20");
        mainText.setText("“You're welcome. Please be cautious, many terrors are sure to await you”");
        option1.setText("I should get going"); //c3s22
        if (player.hasItem("Whistle")) {
            option2.setText("I will call for help"); //c3s21
        } else {
            option2.setText("");
        }
        option3.setText("");
        option4.setText("");
    }
    private void c3s21() {
        position = "c3s21";
        player.setPosition("c3s21");
        mainText.setText("“Should you ever need my assistance, rest assured that I will be there. My strength may not be great, but I will help to the best of my ability.”");
        option1.setText("Take care"); //c3s22
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c3s22() {
        position = "c3s22";
        player.setPosition("c3s22");
        mainText.setText("After saying goodbye, you make your way back to the main trail, reciting the message until each word had been etched into your mind. “A precious thread”, what could it possibly mean? Suddenly, something hits the back of your head and interrupts your thought.");
        option1.setText("Investigate"); //c3s23
        option2.setText("Keep walking"); //c3s25
        option3.setText("");
        option4.setText("");
    }
    private void c3s23() {
        position = "c3s23";
        player.setPosition("c3s23");
        mainText.setText("You look around to see what had hit you. A small smooth stone lie on the ground, but nobody is around.");
        option1.setText("Investigate further"); //c3s24
        option2.setText("Keep walking"); //c3s25
        option3.setText("");
        option4.setText("");
    }
    private void c3s24() {
        position = "c3s24";
        player.setPosition("c3s24");
        mainText.setText("You pick up the stone. Its shape and texture imply it came from a river, although there is not one near you. Someone must have taken it with them, and thrown it at you. Carefully, you scan the environment, starting with the ground and making your way to the trees. As you search for the culprit, you hear snickering. In a tree a few meters from you is an imp, hiding behind the trunk, holding its mouth to muffle it's laughter.");
        option1.setText("I've spotted you!"); //c3s26
        option2.setText("Come down here!"); //c3s27
        option3.setText("Throw the rock back"); //c3s28
        option4.setText("");
    }
    private void c3s25() {
        position = "c3s25";
        player.setPosition("c3s25");
        mainText.setText(" As you turn to continue walking, another stone hits your head.");
        option1.setText(">"); //c3s28
        option2.setText(""); //c3s27
        option3.setText("");
        option4.setText("");

    }
    private void c3s25a() {
        position = "c3s25a";
        player.setPosition("c3s25a");
        mainText.setText("This time the stone was larger, and caused a small bump to form on the back of your head. You turn quickly, eager to find the culprit, only to find an imp on the trail. He is rolling on his back roaring with laughter.");
        option1.setText("Throw the rock back"); //c3s28
        option2.setText("Cut it out!"); //c3s27
        option3.setText("");
        option4.setText("");
    }
    private void c3s26() {
        position = "c3s26";
        player.setPosition("c3s26");
        mainText.setText("The imp jumps down from the tree and approaches you. “You caught on more quickly than I expected.” He walks to a nearby stump and takes a seat. “What brings you here?”");
        option1.setText("Im here to save someone"); //c3s29
        option2.setText("Im here to vanquish a foe"); //c3s30
        option3.setText("");
        option4.setText("");
    }
    private void c3s27() {
        position = "c3s27";
        player.setPosition("c3s27");
        mainText.setText("Defeated, the imp slowly makes his way over to you. “It was only a game you know”. Head low, he walks over to a nearby stump and takes a seat. “Why are you out here anyway?”");
        option1.setText("Im here to save someone"); //c3s29
        option2.setText("Im here to vanquish a foe"); //c3s30
        option3.setText("");
        option4.setText("");
    }
    private void c3s28() {
        position = "c3s28";
        player.setPosition("c3s28");
        mainText.setText("You pick up the stone and throw it at the imp, but he manages to dodge it. “Hey! A stone that size could really hurt you know!” Giggling, he makes his way over to a nearby stump and takes a seat. “Fair enough. What brings you out here?”");
        option1.setText("Im here to save someone"); //c3s29
        option2.setText("Im here to vanquish a foe"); //c3s30
        option3.setText("");
        option4.setText("");
    }
    private void c3s29() {
        position = "c3s29";
        player.setPosition("c3s29");
        mainText.setText("“How noble of you! A hero setting off to save those in need.\" You sense the sarcastic tone in his voice. \"Is there anything I can help you with?”");
        if (player.hasItem("Nightshade")) {
            option1.setText("I need to make poison"); //c3s31
        } else {
            option1.setText("Do you know of the powerful demon?"); //c3s32
        }
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c3s30() {
        position = "c3s30";
        player.setPosition("c3s30");
        mainText.setText("“How vengeful of you! A hero sets out to conquer evil.\" You sense the sarcastic tone in his voice. \"Is there anything I can help you with?”");
        if (player.hasItem("Nightshade")) {
            option1.setText("I need to make poison"); //c3s31
        } else {
            option1.setText("Do you know of the powerful demon?"); //c3s32
        }
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c3s31() {
        position = "c3s31";
        player.setPosition("c3s31");
        mainText.setText("“You’ve come to the right imp!” He snaps his fingers and conjures a flame. You hand him the Nightshade, and he begins grinding it between two rocks. After creating a paste, he places it on a flat and wide rock, then sets it in the flame. “Anything else I can help with?”");
        option1.setText("Do you know of the powerful demon?"); //c3s32
        option2.setText("");
        option3.setText("");
        option4.setText("");
        player.removeItem("Nightshade");
        player.setImpHelp(true);
        inventory.setText(player.getItems());
    }
    private void c3s32() {
        position = "c3s32";
        player.setPosition("c3s32");
        mainText.setText("“You mean to tell me you're up against her?” The Imp shudders ”Lady Baphomet is as wicked as they come, and will stop at nothing to get what she wants. Are you sure you want to confront her?”");
        option1.setText("You don't think I can?"); //c3s33
        option2.setText("I have no choice"); //c3s34
        option3.setText("I'm the last hope"); //c3s35
        option4.setText("");
    }
    private void c3s33() {
        position = "c3s33";
        player.setPosition("c3s33");
        mainText.setText("“If i’m being honest, no, not at all” The imp takes a more serious tone, as his expression becomes worried. “I mean no disrespect, It's just…. I don’t know if anyone can.”");
        option1.setText("But people are depending on me"); //c3s36
        option2.setText("I cant give up"); //c3s37
        option3.setText("");
        option4.setText("");
    }
    private void c3s34() {
        position = "c3s34";
        player.setPosition("c3s34");
        mainText.setText("“There is always a choice. Nobody is forcing you, right?” The imp looks into his hands while fidgeting. “There is no need to face something if you aren’t willing, it won’t turn out good for you”");
        option1.setText("But people are depending on me"); //c3s36
        option2.setText("I cant give up"); //c3s37
        option3.setText("");
        option4.setText("");
    }
    private void c3s35() {
        position = "c3s35";
        player.setPosition("c3s35");
        mainText.setText("“There may be no hope then. To say you are the last hope for an impossible task isn’t saying much at all” The imp looks to the ground, “Sometimes you have to let go”");
        option1.setText("But people are depending on me"); //c3s36
        option2.setText("I cant give up"); //c3s37
        option3.setText("");
        option4.setText("");
    }
    private void c3s36() {
        position = "c3s36";
        player.setPosition("c3s36");
        mainText.setText("“Understandable. I would do the same for those I care about.” After a moment to ponder, the Imp perks up. “You will need to be at least as clever as Lady Baphomet if you are to stand a chance. How about we play a game? If you can solve my riddle, you will prove your cleverness. If not, well, you would have died anyway.”");
        option1.setText("Sure"); //c3s39
        option2.setText("Cant you just give me the hint?"); //c3s38
        option3.setText("");
        option4.setText("");
    }
    private void c3s37() {
        position = "c3s37";
        player.setPosition("c3s37");
        mainText.setText("“Stubborn are we? I don’t know when to quit either.”After a moment to ponder, the Imp perks up. “You will need to be at least as clever as Lady Baphomet if you are to stand a chance. How about we play a game? If you can solve my riddle, you will prove your cleverness. If not, well, you would have died anyway.”");
        option1.setText("Sure"); //c3s39
        option2.setText("Cant you just give me the hint?"); //c3s38
        option3.setText("");
        option4.setText("");
    }
    private void c3s38() {
        position = "c3s38";
        player.setPosition("c3s38");
        mainText.setText("“I could, but I won’t” The Imp smirks, pleased with his game. “This is more fun anyway” ");
        option1.setText("Ok then, tell me the riddle"); //c3s39
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c3s39() {
        position = "c3s39";
        player.setPosition("c3s39");
        mainText.setText("”Listen carefully”\n”An evil taints the seeds that sow,\nA treacherous friend, a hidden foe.\nCloaked in deceit, the demon lies,\nHeed the heart, not the disguise.”");
        if (player.getImpHelp()) {
            option1.setText(">"); //c3s40
        } else {
            option1.setText("Thank you for the help"); //c3s41
        }
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c3s40() {
        position = "c3s40";
        player.setPosition("c3s40");
        mainText.setText(" “Before I forget.” The imp scrapes the charred Nightshade into a vessel partially filled with water, and vigorously shakes it. The fluid turns into a deep burgundy. “Be careful with that stuff.”");
        option1.setText("Thank you for the help"); //c3s41
        option2.setText("");
        option3.setText("");
        option4.setText("");
        player.acquireItem("Poison");
        inventory.setText(player.getItems());
    }
    private void c3s41() {
        position = "c3s41";
        player.setPosition("c3s41");
        mainText.setText("“Sure thing! Hopefully you are smart enough to make sense of my riddle” The imp smiles while waving goodbye. “It was good knowing ya!” Gradually the imp starts to disappear until he is completely invisible. You can hear him giggle as he walks behind you. A small stone hits you in the back of the head! You hear his footsteps quickly gaining distance as he yells, “Until we meet again, traveler!”");
        option1.setText(">"); //c4s1
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    /** --------------------------------------------------- Chapter 4: The trials --------------------------------------
    //  A hidden challenge confronts the player in chapter 4, one that will alter the direction of the final chapter in
    //  a significant way. Can the player figure out what is real and what is a ruse? This chapter builds to the climax
    //  of our story in chapter 5, leaving a bit of a clif hanger ending.
    //  Specific requirements: 2.4.0 - 2.4.31*/
    private void c4s1() {
        position = "c4s1";
        player.setPosition("c4s1");
        mainText.setText("As you continue walking, pondering the meaning of each message you were given, a fog starts to develop on the ground. It reminds you of the fog that first trapped you in the forest, but much less thick, allowing you to still see the trail ahead. Suddenly, the voice appears.");
        option1.setText(">"); //c4s2
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c4s2() {
        position = "c4s2";
        player.setPosition("c4s2");
        mainText.setText("“Please, help me.” “help”. The soft whispers continue to cry for help with persistence.");
        option1.setText("Continue walking"); //c4s4
        option2.setText("Call out to voice"); //c4s3
        option3.setText("");
        option4.setText("");
    }
    private void c4s3() {
        position = "c4s3";
        player.setPosition("c4s3");
        mainText.setText("“Please, help. I need you”. The voice doesn't respond to your call, and continues to relentlessly plea for help.");
        option1.setText(">"); //c4s4
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c4s4() {
        position = "c4s4";
        player.setPosition("c4s4");
        mainText.setText("The voice grows steadily louder. As you walk, the fog becomes more dense, yet the trail ahead remains clear.\"You need to help me. You can't leave me, its your destiny.\"");
        option1.setText(">"); //c4s5
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c4s5() {
        position = "c4s5";
        player.setPosition("c4s5");
        mainText.setText("Like a maze, the fog now forms walls on either side of you, guiding you along. You approach a wall of fog veering off of the trail. The voice beckons you further down the tunnel of fog. \"Come to my aid and you will be greatly rewarded. \"");
        option1.setText("Continue on the trail"); //c4s7
        option2.setText("Let the fog guide you"); //c4s10
        if (player.hasItem("Compass")) {
            option3.setText("Check compass"); //c4s6
        } else {
            option3.setText("");
        }
        option4.setText("");
    }
    private void c4s6() {
        position = "c4s6";
        player.setPosition("c4s6");
        mainText.setText("You glance at your compass and see that the needle is still pinned in the direction of the trail. The voice becomes louder yet, desperately crying out  “Help me, Please! Don’t leave me, I need your help. You must help me right away!”");
        option1.setText("Follow the compass"); //c4s7
        option2.setText("Follow the voice"); //c4s10
        option3.setText("");
        option4.setText("");
    }
    private void c4s7() {
        position = "c4s7";
        player.setPosition("c4s7");
        mainText.setText("You step into the wall of fog that blocks the trail. The fog is dense enough to block most of the sunlight from breaking through. You feel a strong wind wisp the fog around you, and a new tunnel presents itself to the right of the trail. The voice calling with urgency “You’ve betrayed me, you leave me to die. Come back” “help me”");
        option1.setText("Help the voice"); //c4s10
        option2.setText("Continue down the trail"); // c4s8
        option3.setText("");
        option4.setText("");
    }
    private void c4s8() {
        position = "c4s8";
        player.setPosition("c4s8");
        mainText.setText("Ignoring the voice, you attempt to continue forward. The fog is so thick you feel it fill your lungs with each breath you take. As you struggle to make distance, you feel something gently wrap around your hand. You reactively pull away, and see that a vine with tendrils like fingers grasps you, it's presence calming. It pulls you lightly, guiding you out of the shroud of fog.");
        option1.setText(">"); //c4s9
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c4s9() {
        position = "c4s9";
        player.setPosition("c4s9");
        mainText.setText("As you step out from the edge of the fog, you see the trail again. The ivy plant slowly releases your hand and recedes back to the tree it inhabits. You notice beneath the tree, a dull glow, and discover more Orion’s beard! Without any room for more, you have to decide if you would like to use some to heal now.");
        option1.setText("Heal now"); //c4s11
        option2.setText("Continue without healing"); //c4s12
        option3.setText("");
        option4.setText("");
    }
    private void c4s10() {
        position = "c4s10";
        player.setPosition("c4s10");
        mainText.setText("You continue down the tunnel of fog in pursuit of the voice. “Follow my voice, come help me, please”. As you continue to walk, the voice and fog both begin to dissipate, until only a trace of fog is left. All signs of the voice are gone, and the fog no longer guides you. After carefully following your own tracks, you find your way back to the trail, and continue as you were before the fog appeared.");
        option1.setText(">"); //c4s12
        option2.setText("");
        option3.setText("");
        option4.setText("");
        player.setTrickedCount(player.getTrickedCount() + 1); //set tricked count to 1
    }
    private void c4s11() {
        position = "c4s11";
        player.setPosition("c4s11");
        mainText.setText("You bend down and pry a piece off of the underside of the tree root. After inspecting for any bugs, you throw it into your mouth and begin chewing. The taste is mildly sweet, and not but a few seconds after chewing you feel a sudden rush of energy. It almost seems to pulse through your entire body, leaving you with the same feeling as having gotten a good night's rest. You continue onward, feeling rejuvenated.");
        option1.setText(">"); //c4s12
        option2.setText("");
        option3.setText("");
        option4.setText("");
        player.setHP(player.getHPNum() + 5); //heal 5 from Orion's beard
        hp.setText(player.getHP()); //update HP in GUI
    }
    private void c4s12() {
        position = "c4s12";
        player.setPosition("c4s12");
        mainText.setText("The treeline appears to break in the distance where the trail stops at the edge of a canyon. As you approach, you notice something gleaming at the bottom of a canyon. It looks like a set of Dragonstone armor, a specialty of the mountain elves, known for its extreme durability. Armor like that could be useful in a battle against the demon.");
        option1.setText("Climb down to investigate"); //c4s13
        option2.setText("Leave the armor"); //c4s19
        option3.setText("");
        option4.setText("");
    }
    private void c4s13() {
        position = "c4s13";
        player.setPosition("c4s13");
        if (player.hasItem("Rope")) {
            mainText.setText("You cinch the rope to a nearby tree and walk to the canyon. Lowering yourself to the edge, you sit with your feet hanging into the crevice. You gather some slack, wrap the rope between your feet, grab the rope with your hands and descend into the canyon quickly. ");
        } else {
            mainText.setText("You begin your descent into the canyon, testing your weight on each rock before completely moving forward.You pause for a breath and look down. Halfway there. You feel a root nearby, and test its strength. It feels sturdy. As you shift your weight, it rips out of the canyon wall and you crash to the ground");
            player.setHP(player.getHPNum() - 1);
            hp.setText(player.getHP());
        }
        option1.setText(">"); //c4s14
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c4s14() {
        position = "c4s14";
        player.setPosition("c4s14");
        if (player.hasItem("Rope")) {
            mainText.setText("You reach the bottom safely. Perhaps the risk was worth it. You approach the armor, and lift it from the ground. As you do, your heart sinks. Dragonstone armor is notoriously heavy, suited to the muscular builds of the mountain elves. It looks like it will fit you, but could be difficult to carry while climbing back up the canyon, but your rope may prove useful");
        } else {
            mainText.setText("You stand up, feeling your body ache from the impact. You move around. No broken bones at least. Perhaps the risk was worth it. You approach the armor, lifting it from the ground. As you do, your heart sinks. Dragonstone armor is notoriously heavy, suited to the muscular builds of the mountain elves. It looks like it will fit you, but could be difficult to carry while climbing back up the canyon.");
        }
        option1.setText("Leave it behind"); //c4s18
        option2.setText("Inspect armor's condition"); //c4s15
        option3.setText("Haul it out "); //c4s16
        option4.setText("");
    }
    private void c4s15() {
        position = "c4s15";
        player.setPosition("c4s15");
        mainText.setText(" Scale-like carvings are etched throughout the black stone, appearing both delicate and ominous. You run your hands over the chestplate feeling for the characteristic sigil of the mountain elf clan. Wiping the dirt away, you reveal soft emerald lines that intertwine into the intricate shape that adorns your amulet. You pause, surprised to see the same sigil, before continuing to inspect the armor. All of it appears to be intact.");
        option1.setText("Haul it out "); //c4s16
        option2.setText("Leave it behind"); //c4s18
        option3.setText("");
        option4.setText("");
    }
    private void c4s16() {
        position = "c4s16";
        player.setPosition("c4s16");
        if (player.hasItem("Rope")) {
            mainText.setText("You grab the armor and make your way to the opposite end of the canyon. After looping the bottom of the rope through the armor sufficiently, you grab the rope and begin your ascent. Once at the top, you reel in the armor. Although tiring, you’re glad that you had the rope to assist you.");
        } else {
            mainText.setText("You grab the armor and make your way to the opposite end of the canyon. Seeing no other option, you decide that you’ll need to make multiple trips to get the armor to the top. At least this side of the canyon seems to have better holds. After several laborious trips, you manage to successfully transport all of the armor to the top.");
            player.setHP(player.getHPNum() - 1);
            hp.setText(player.getHP());
        }
        player.setTrickedCount(player.getTrickedCount() + 1);
        option1.setText("Put on the armor"); //c4s17
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c4s17() {
        position = "c4s17";
        player.setPosition("c4s17");
        mainText.setText("Excited to use your hard earned prize, you dress yourself in the armor. As you place the final piece on your head, you hear a sizzling sound. You look down and see that the armor is disintegrating! Holes begin to form as the set turns to ash and blows away in the wind. You had heard of enchantments that tie the armor to its owner, but nothing like this. Soon, nothing is left. Defeated and exhausted, you search for where the trail broke and continue your journey.");
        option1.setText(">"); //c4s20
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c4s18() {
        position = "c4s18";
        player.setPosition("c4s18");
        mainText.setText("Although the armor seems useful, you decide that the energy required to haul it out of the canyon is too great. Not to mention the energy required to continue your journey with a full set of armor on. You make your way to the opposite side of the canyon and begin your climb to the top. Out of breath from the climb, you find where the trail broke and continue.");
        option1.setText(">"); //c4s20
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c4s19() {
        position = "c4s19";
        player.setPosition("c4s19");
        mainText.setText("You decide that the trip down to the armor and back would be more work than it's worth. Who knows if it would even fit anyway. You begin walking along the canyon to see if there are any easy ways to cross. After just a few minutes you find a downed tree that bridges the canyon. Carefully you make your way to the other side, find where the trail broke, and continue your journey.");
        option1.setText(">"); //c4s20
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c4s20() {
        position = "c4s20";
        player.setPosition("c4s20");
        mainText.setText("The forest is beginning to smell of rot. Nearly every tree in sight appears scorched, with parasites and fungi growing rampant. You no longer hear the sounds of birds and crickets, just the wind whistling through the bare branches.");
        option1.setText(">"); //c4s21
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c4s21() {
        position = "c4s21";
        player.setPosition("c4s21");
        mainText.setText("Suddenly, you hear someone yelling in the distance. “Wait!”. You squint to focus on an object coming toward you, and see the forest sprite flying at full speed, waving her arms to catch your attention.");
        option1.setText(">"); //c4s22
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c4s22() {
        position = "c4s22";
        player.setPosition("c4s22");
        mainText.setText("The sprite perches on a tree branch to catch her breath. “I need your assistance, right away!” ");
        option1.setText("Are you ok?"); //c4s23
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c4s23() {
        position = "c4s23";
        player.setPosition("c4s23");
        mainText.setText("“Yes, im fine. Don’t worry about me” The sprite seems to be healthy, other than a damaged right wing. “Its the mother tree that needs our assistance! She’s not far from here.”");
        option1.setText("What happened?"); //c4s25
        option2.setText("Why did you leave the grove?"); //c4s24
        option3.setText("");
        option4.setText("");
    }
    private void c4s24() {
        position = "c4s24";
        player.setPosition("c4s24");
        mainText.setText(" “The grove?” the sprite pauses for a moment. “Well, I couldn't stand around and do nothing! It’s a good thing i did leave as I may not have been able to get to you in time”");
        option1.setText("What happened?"); //c4s25
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c4s25() {
        position = "c4s25";
        player.setPosition("c4s25");
        mainText.setText("“The mother tree must have regained some energy. She called to me, and told me to find you. She needs us to bring her Orion’s beard so that we can heal her enough for a final push”");
        option1.setText("I have some!"); // c4s27
        option2.setText("What about the message?"); //c4s26
        option3.setText("");
        option4.setText("");
    }
    private void c4s26() {
        position = "c4s26";
        player.setPosition("c4s26");
        mainText.setText("The sprite pauses, “yes, I think this is what she spoke of. “A precious thread the light unveils”, Orion’s beard glows, she must have wanted us to heal her all along!”");
        option1.setText("I have some!"); //c4s27
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c4s27() {
        position = "c4s27";
        player.setPosition("c4s27");
        mainText.setText(" “Excellent! Give it here and I can fly it to her. Meet us there as soon as you can!”");
        option1.setText("Give her your Orion's beard"); //c4s29
        option2.setText("Clarify plan"); //c4s28
        option3.setText("");
        option4.setText("");
    }
    private void c4s28() {
        position = "c4s28";
        player.setPosition("c4s28");
        mainText.setText("“I will fly it to her since it will be faster. I’ll need you to meet me there so that you can distract Lady Baphomet. Once I’ve healed the mother tree, she should have enough strength to aid you in your battle against Lady Baphomet”\n");
        option1.setText("Give her your Orion's beard"); //c4s30
        option2.setText("Keep Orion's beard"); //c4s29
        option3.setText("");
        option4.setText("");
    }
    private void c4s29() {
        position = "c4s29";
        player.setPosition("c4s29");
        mainText.setText("“Are you sure? I don’t think you will get it to her in time. If we can heal her we stand a chance against Lady Baphomet. Without the mother tree, I’m not sure we could even take one step toward Lady Baphomet”");
        option1.setText("Give her the Orion's beard"); //c4s30
        option2.setText("Yes, I'm sure"); //c4s31
        option3.setText("");
        option4.setText("");
    }
    private void c4s30() {
        position = "c4s30";
        player.setPosition("c4s30");
        mainText.setText("You give the sprite your Orion’s beard. The sprite smiles, overjoyed. “I’ll be sure to deliver this right away”. She turns and flies off in the same direction she came from. You pick up the pace and continue ahead.");
        option1.setText(">"); //c5s1
        option2.setText("");
        option3.setText("");
        option4.setText("");
        player.setTrickedCount(player.getTrickedCount() + 1);
        player.removeItem("Orion's beard");
        inventory.setText(player.getItems());
    }
    private void c4s31() {
        position = "c4s31";
        player.setPosition("c4s31");
        mainText.setText(" “No matter, I'm sure this won't change things in the end” Without a goodbye, the sprite turns around and flies off in the direction that she came from. You pick up the pace and continue ahead.");
        option1.setText(">"); //c5s1
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    //* --------------------------------------------------- Chapter 5: The Battle --------------------------------------
    //  The final chapter finds us in a battle with a formidable enimy, one who has a dark vision for the future.
    //  The actions of chapter 4 will give access to hidden scenes here, allowing for a significantly weakened enemy.
    //  All items are put to use in this chapter, to either heal us or harm our enemy. The fight scene loops until either
    //  victory is achieved, or the death of our hero.
    //  Specific requirements: 2.5.0 - 2.5.41.3 */
    private void c5s1() {
        position = "c5s1";
        player.setPosition("c5s1");
        mainText.setText("As night falls, an eerie feeling overcomes you. The bare trees cast jagged shadows on the ground, and the forest is blanketed by an unsettling silence. A full moon lights your path, and without coverage of the foliage, it's quite easy to find your way.");
        option1.setText(">"); //if tricked 3 times, lead straight to demon route c5s1d, else c5s2
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c5s1d() { //this scene is only for those that were tricked all 3 times
        position = "c5s1d";
        player.setPosition("c5s1d");
        mainText.setText("You cautiously make your way through the forest, looking for any signs of the Demon. Suddenly, a voice begins to call you. “Come to me. I am nearing my end, please help me”");
        option1.setText(">"); //c5s6d
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c5s2() {
        position = "c5s2";
        player.setPosition("c5s2");
        mainText.setText("You cautiously make your way through the forest, looking for any signs of the mother tree or demon. For a moment, you see a flash pulse through the ground. It happens so quickly, you question if it was in your mind. As you bend down to investigate, A voice begins to call you. “Come to me. I am nearing my end, please help me”");
        option1.setText("Continue to investigate"); //c5s3
        option2.setText("Follow the voice"); //c5s6d
        if (player.hasItem("Compass")) {
            option3.setText("Check your compass"); //c5s4
        }else{
            option3.setText("");
        }
        option4.setText("");
    }
    private void c5s3() {
        position = "c5s3";
        player.setPosition("c5s3");
        mainText.setText("Ignoring the call, you continue to investigate. You touch the ground where the flash occurred, hoping to gain some insight. Much to your surprise, the ground is warm along the path of the flash. Suddenly, the voice reappears, this time from another direction. “Young traveler, you’ve almost made it. Heed my final call”");
        option1.setText("Track the warm soil"); //c5s6
        option2.setText("Follow original voice direction"); //c5s6d
        if (player.hasItem("Compass")) {
            option3.setText("Check your compass"); //c5s5
        }else{
            option3.setText("Follow new voice direction"); //c5s6
        }
        option4.setText("");
    }
    private void c5s4() {
        position = "c5s4";
        player.setPosition("c5s4");
        mainText.setText("You glance at your compass and notice that again, the voice seems to be coming from a direction different from the needle. The compass still doesn’t point north, and has held a true position during your journey. You wonder what has changed.");
        option1.setText("Continue to investigate"); //c5s3
        option2.setText("Follow the voice"); //c5s6d
        option3.setText("");
        option4.setText("");
    }
    private void c5s5() {
        position = "c5s5";
        player.setPosition("c5s5");
        mainText.setText("You glance at your compass and notice that the needle points both in the direction of the flash, and now also in the direction of the voice.");
        option1.setText("Follow the flash direction"); // c5s6
        option2.setText("Follow original voice direction"); //c5s6d
        option3.setText("");
        option4.setText("");
    }
    // ----------------------------------------------------------------- Demon Path -------------------------------------------------------------------
    private void c5s6d() {
        position = "c5s6d";
        player.setPosition("c5s6d");
        mainText.setText("As you continue in the direction of the voice, guided by the light of the moon, you take in your surroundings. The trees are blackened and scorched; the ground is littered with brown leaves. Everything around you is devoid of life, and there isn’t even a hint of color in sight. Suddenly, you see a figure looming in the distance. It hasn’t appeared to notice you");
        option1.setText("Crouch to the ground"); //c5s7d
        option2.setText("Hide behind a fallen log"); //c5s7d
        option3.setText("");
        option4.setText("");
    }
    private void c5s7d() {
        position = "c5s7d";
        player.setPosition("c5s7d");
        mainText.setText("Although you can hardly make out the details, you are certain that this is Lady Baphomet. You realize now that the voice calling to you was a deception, and that she had led you here to prevent you from reaching the mother tree. You contemplate the best course of action.");
        option1.setText("Ambush Lady Baphomet"); //c5s11d
        if (player.hasItem("Orion's beard")) {
            option2.setText("Take Orion's beard"); //c5s8d
        } else {
            option2.setText("");
        }
        if (player.hasItem("Whistle")) {
            option3.setText("Call forest sprite"); //c5s10d
        } else {
            option3.setText("");
        }
        if (player.hasItem("Knife") & player.hasItem("Poison")) {
            option4.setText("Add poison to blade"); //c5s9d
        } else {
            option4.setText("");
        }
    }
    private void c5s8d() {
        position = "c5s8d";
        player.setPosition("c5s8d");
        mainText.setText("You quietly take Orion’s Beard from your pouch and consume it. Feeling rejuvenated, you redirect your attention back to the threat. ");
        option1.setText("Ambush Lady Baphomet");//c5s11d
        option2.setText("");
        if (player.hasItem("Whistle")) {
            option3.setText("Call forest sprite"); //c5s10d
        } else {
            option3.setText("");
        }
        if (player.hasItem("Knife") & player.hasItem("Poison")) {
            option4.setText("Add poison to blade"); //c5s9d
        } else {
            option4.setText("");
        }
        player.setHP(player.getHPNum() + 5); //heal 5 hp from Orion's beard
        player.removeItem("Orion's beard");
        inventory.setText(player.getItems());
        hp.setText(player.getHP());
    }
    private void c5s9d() {
        position = "c5s9d";
        player.setPosition("c5s9d");
        mainText.setText("You quietly take your vial of poison out of your pouch and uncinch the knife from your belt. You pour the poison over the blade, and redirect your attention back to the demon.");
        option1.setText("Ambush Lady Baphomet");//c5s11d
        if (player.hasItem("Orion's beard")) {
            option2.setText("Take Orion's beard"); //c5s8d
        } else {
            option2.setText("");
        }
        option3.setText("");
        option4.setText("");
        player.setPoisonKnife(); //sets poison knife to true
        player.removeItem("Poison");
        inventory.setText(player.getItems());
    }
    private void c5s10d() {
        position = "c5s10d";
        player.setPosition("c5s10d");
        mainText.setText("You quietly take the whistle from your pouch and bring it to your lips, exhaling sharply. The sound pierces the silence. Lady Baphomet’s head snaps towards you, but before she can advance the forest sprite appears before her and gouges out her left eye. The demon screams in agony and grabs the sprite, crushing her between her claws. Before you can act, the demon drops the sprite’s lifeless body to the ground.");
        option1.setText("Ambush Lady Baphomet");//c5s11d
        if (player.hasItem("Orion's beard")) {
            option2.setText("Take Orion's beard"); //c5s8d
        } else {
            option2.setText("");
        }
        option3.setText("");
        option4.setText("");
    }
    private void c5s11d() {
        position = "c5s11d";
        player.setPosition("c5s11d");
        mainText.setText(" With her back turned to you, you lunge forward quickly. In an attempt to cover some distance before alerting Lady Baphomet to your presence you step with intention, avoiding the piles of lifeless vegetation scattered across the ground. She notices you too late, and she spins around to face you.");
        option1.setText("Attack her head"); //c5s12d
        option2.setText("Attack her body"); //c5s12d
        option3.setText("Attack her limbs"); //c5s12d
        option4.setText("");
        hp.setText(player.getHP());
    }
    private void c5s12d() {
        position = "c5s12d";
        player.setPosition("c5s12d");
        mainText.setText("You land the strike and she stumbles backwards. Her face riddled with surprise, she straightens up and acknowledges your presence with a condescending sneer. “Was that supposed to harm me?” With a slight flick of her wrist the ground beneath you shifts. You stumble, your knees connecting with the earth and your skin breaking open");
        if (player.getHPNum() < 1) {
            option1.setText(">"); //Death
            option2.setText("");
            option3.setText("");
            option4.setText("");
        } else {
            option1.setText("Prepare to dodge"); //c5s13d
            option2.setText("Attack again"); //c5s14d
            option3.setText("");
            option4.setText("");
        }
        hp.setText(player.getHP());
    }
    private void c5s13d() {
        position = "c5s13d";
        player.setPosition("c5s13d");
        mainText.setText("She watches you curiously, and upon realizing that you don’t intend to run, she laughs. A cloud of hot black smoke surrounds you and you realize your mistake. Disoriented, you are unprepared to dodge her next attack, and suddenly you feel a surge of pain run through your body as a bolt of black lightning strikes you. As you crumble to the ground, the smoke fades and she reappears hovering over your weakened body, her hand close enough to touch. ");
        if (player.getHPNum() < 1) {
            option1.setText(">"); //Death
            option2.setText("");
            option3.setText("");
            option4.setText("");
        } else {
            option1.setText("Attack her head"); //c5s14d
            option2.setText("Attack her body"); //c5s14d
            option3.setText("Attack her limbs"); //c5s14d
            option4.setText("");
        }
        hp.setText(player.getHP());
    }
    private void c5s14d() {
        position = "c5s14d";
        player.setPosition("c5s14d");
        if (player.hasItem("Knife")) {
            mainText.setText("You lunge forward again, trying not to question whether your strength will be enough to hurt her. As she swiftly dodges you, you take the opportunity to feint losing your balance to prepare for a surprise attack.");
        } else {
            mainText.setText("You lunge forward again, trying not to question whether your strength will be enough to hurt her. As she swiftly dodges you, you take the opportunity to feint losing your balance. As you fall to the ground you grab a nearby rock larger than the palm of your hand");

        }
        option1.setText("Attack her head"); //c5s15d
        option2.setText("Attack her body"); //c5s15d
        option3.setText("Attack her limbs"); //c5s15d
        option4.setText("");
        hp.setText(player.getHP());
    }
    private void c5s15d() {
        position = "c5s15d";
        player.setPosition("c5s15d");
        mainText.setText("You land another strike and she grimaces. You begin to realize that the demon was never at full health to begin with. Her illusions and boasting, a simple ploy to distract you from the reality that you could possibly win this. With newfound confidence you stand tall ready to deliver another blow when the opportunity arises. ");
        option1.setText(">"); //c5s16d
        option2.setText("");
        option3.setText("");
        option4.setText("");
        hp.setText(player.getHP());
    }
    private void c5s16d() {
        position = "c5s16d";
        player.setPosition("c5s16d");
        mainText.setText("Lady Baphomet paces around you, watching your movements carefully. “I'd applaud you for your attempt to kill me if I didn’t know that it was fueled by your ignorance. Your attacks are mere childsplay. Let me show you real power!’” Black lightning shoots from her hands and strikes you. ");
        if (player.getHPNum() < 1) {
            option1.setText(">"); //Death
            option2.setText("");
            option3.setText("");
            option4.setText("");
        } else {
            option1.setText(">"); //c5s17d
            option2.setText("");
            option3.setText("");
            option4.setText("");
        }
        hp.setText(player.getHP());
    }
    private void c5s17d() {
        position = "c5s17d";
        player.setPosition("c5s17d");
        mainText.setText("You withstand the pain, not allowing it to bring you to your knees. You focus on your goal, reminding yourself that the demon is much weaker than she appears.");
        option1.setText(">"); //c5s18d
        option2.setText("");
        option3.setText("");
        option4.setText("");
        hp.setText(player.getHP());
    }
    private void c5s18d() {
        position = "c5s18d";
        player.setPosition("c5s18d");
        mainText.setText(" She attempts to dodge, but misjudges your distance. She winces in pain, and for a moment you see a glimmer of concern. She regains her composure and strikes again. ");
        option1.setText("Attack her head");//c5s19d
        option2.setText("Attack her body");//c5s20d
        option3.setText("Attack her limbs"); //c5s21d
        option4.setText("");
        hp.setText(player.getHP());
    }
    private void c5s19d() {
        position = "c5s19d";
        player.setPosition("c5s19d");
        mainText.setText("You attempt to land a strike on her head. The odds are low, but a headstrike has the potential for the most damage. You manage to land, but take damage in the process! ");
        if (player.getHPNum() < 1) {
            option1.setText(">"); //Death
            option2.setText("");
            option3.setText("");
            option4.setText("");
        } else {
            option1.setText("Attack her head");//c5s19d
            option2.setText("Attack her body");//c5s20d
            option3.setText("Attack her limbs"); //c5s21d
            option4.setText("");
        }
        hp.setText(player.getHP());
    }
    private void c5s20d() {
        position = "c5s20d";
        player.setPosition("c5s20d");
        mainText.setText("You attempt to land a strike on her body. The odds aren’t bad and there is potential for decent damage. You manage to land, but she retaliates and you’re hurt in the process!");
        if (player.getHPNum() < 1) {
            option1.setText(">"); //Death
            option2.setText("");
            option3.setText("");
            option4.setText("");
        } else {
            option1.setText("Attack her head");//c5s19d
            option2.setText("Attack her body");//c5s20d
            option3.setText("Attack her limbs"); //c5s21d
            option4.setText("");
        }
        hp.setText(player.getHP());
    }
    private void c5s21d() {
        position = "c5s21d";
        player.setPosition("c5s21d");
        mainText.setText("You attempt to land a strike on her limbs. The odds are high, but the damage is low. You successfully land a blow, but she manages to strike back!");
        if (player.getHPNum() < 1) {
            option1.setText(">"); //Death
            option2.setText("");
            option3.setText("");
            option4.setText("");
        } else {
            option1.setText("Attack her head");//c5s19d
            option2.setText("Attack her body");//c5s20d
            option3.setText("Attack her limbs"); //c5s21d
            option4.setText("");
        }
        hp.setText(player.getHP());
    }
    private void c5s22d() {
        position = "c5s22d";
        player.setPosition("c5s22d");
        if (player.getMotherTreeAlive()) {
            mainText.setText("The demon crumples to the ground! You pause, cautiously waiting for her to stand. Her motionless body begins to disintegrate into black smoke, drifting off into the wind, leaving only soil behind. You notice the soil change from a dark black to a warm, earthy brown. Color seeps back into the vegetation, the trees become green again, and the forest radiates with vibrancy. Victory! You’ve saved the mother tree and you can now rest easy. ");
        } else {
            mainText.setText("The demon crumples to the ground! You pause, cautiously waiting for her to stand. Her motionless body begins to disintegrate into black smoke, drifting off into the wind, leaving only soil behind. Victory! You’ve killed the demon and ensured that the village is safe. However, as the demon lies lifeless, so too does the forest.. You wonder what this may mean for your village. ");
        }
        option1.setText(">"); //c5s23d
        option2.setText("");
        option3.setText("");
        option4.setText("");
        player.setDemonAlive(false);
        hp.setText(player.getHP());
    }
    private void c5s23d() {
        position = "c5s23d";
        player.setPosition("c5s23d");
        mainText.setText("Your final score was: " + player.getHighScore());
        option1.setText(">"); //main menu
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    // ----------------------------------------------------------------- Tree Path -------------------------------------------------------------------
    private void c5s6() {
        position = "c5s6";
        player.setPosition("c5s6");
        mainText.setText("“To break the chain, the night unveils, a precious thread that tips the scales”. The message the mother tree gave you must be referring to this light. Although you still aren't sure what this light does, or where it leads, you continue to follow its direction. As you walk, feeling the ground for the warmth that guides you, the ground pulses with light again. This time, it leaves a residual glow.");
        option1.setText(">"); //c5s7
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c5s7() {
        position = "c5s7";
        player.setPosition("c5s7");
        mainText.setText("The glow in the ground finally emerges from the soil, revealing a tree root. You touch the root, and immediately see the mother tree in your mind. You are sure that you’re on the right path, and that this root leads directly to the mother tree. Eager to get to your destination, you sprint alongside the root. ");
        option1.setText(">"); //c5s8
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c5s8() {
        position = "c5s8";
        player.setPosition("c5s8");
        mainText.setText("The roots light grows brighter and brighter, until finally you see her. The mother tree, a grand Willow. Her branches cover a vast distance, though they hang low and color fades from her leaves. Despite this, the plants that surround her still remain healthy, showing her refusal to give in to the demon. “Welcome child”.");
        option1.setText(">"); //c5s9
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    //testing fight loop
    private void c5s9() {
        position = "c5s9";
        player.setPosition("c5s9");
        mainText.setText("“I have been waiting for you. I knew you would return to my forest once again. Your elven blood connects you to me, and I to you. Despite what little elven you have in you.”");
        option1.setText("So you really are the Mother tree"); //c5s10
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c5s10() {
        position = "c5s10";
        player.setPosition("c5s10");
        mainText.setText("“Yes, I’ve been calling to you long before you entered my domain. No doubt you sensed me during your time with the humans. I remember you as a child, and your frightened mother.”");
        option1.setText("You must have trapped her as well"); //c5s11
        option2.setText("");
        option3.setText("");
        option4.setText("");

    }
    private void c5s11() {
        position = "c5s11";
        player.setPosition("c5s11");
        mainText.setText("“I did. I owe nothing to humans who kill my children for their shallow desires. Unlike the elves, the humans do not wish to coexist, to help one another mutually. They only take, and never give. When she entered my realm, I trapped her here, intent on allowing her to perish. But then, I saw something in you. I sensed our connection and knew that one day you would return to help me when I would be in need. And here you are.” ");
        option1.setText("You were right"); //c5s12
        option2.setText("You're wrong about humans"); //c5s13
        option3.setText("");
        option4.setText("");
    }
    private void c5s12() {
        hp.setText(player.getHP());

        position = "c5s12";
        player.setPosition("c5s12");
        mainText.setText("“Of course I was. Now there isn’t time to waste. You are no doubt aware of Lady Baphomet. She is the reason my children are dying, and although I have prevented her from completely overcoming me, I fear that she is near victory.”");
        option1.setText("How can I weaken her?"); //c5s14
        option2.setText("What can we do?"); //c5s14
        option3.setText("");
        option4.setText("");
    }
    private void c5s13() {
        position = "c5s13";
        player.setPosition("c5s13");
        mainText.setText("“Whether I am or am not, does not matter. You are here now, and no doubt you are aware of Lady Baphomet. She is the reason my children are dying, and if you do not care about my forest so be it, but know that if she should rise to power your precious humans will cease to exist.”");
        option1.setText("What can we do?"); //c5s14
        option2.setText("What are her weaknesses?"); //c5s14
        option3.setText("");
        option4.setText("");
    }
    private void c5s14() {
        position = "c5s14";
        player.setPosition("c5s14");
        mainText.setText(" “Baphomet has connected her essence to my own, intertwined it so deeply that she is able to drain the life from everything I touch and try to protect. It makes her powerful now, but without it she would be weak. If you could sever the connection between Baphomet and myself, I would be able to guard the forest once again, and she would not not be able to harm your village.”");
        option1.setText("Sever the connection"); //c5s19
        if (player.hasItem("Poison")) {
            option2.setText("Fight another way"); //c5s15
        } else {
            option2.setText("");
        }
        option3.setText("");
        option4.setText("");
    }
    private void c5s15() {
        position = "c5s15";
        player.setPosition("c5s15");
        mainText.setText("You remember that you still have a vial of poison. Although the tree may be unhappy with your approach, you could probably damage the demon if you also poisoned the mother tree. ");
        option1.setText("Poison the mother tree"); //c5s16
        option2.setText("Sever the connection"); //c5s19
        option3.setText("");
        option4.setText("");
    }private void c5s16() {
        position = "c5s16";
        player.setPosition("c5s16");
        mainText.setText("You grab the vial from your pouch and walk over to the glowing root that branches out into the distance. You’re sure that this is the connection point from which the demon is drawing her power. You tilt the vial, letting just a drop escape. It soaks beneath the surface of the root and two screams echo inside your head. The demon has been damaged, but so has the tree. ");
        option1.setText("Add more poison"); //c5s17
        option2.setText("Stop"); //c5s19a
        option3.setText("");
        option4.setText("");
        player.setDemonHP(player.getDemonHP() - 5); //5 damage to demon
    }private void c5s17() {
        position = "c5s17";
        player.setPosition("c5s17");
        mainText.setText("You let another drop leave the vial and fall to the root. The tree and demon scream in unison. The mother tree’s leaves become wilted and gray, and the glowing root becomes duller. “Please stop,” the mother tree begs. ");
        option1.setText("Add more poison"); //c5s18
        option2.setText("Stop"); //c5s19a
        option3.setText("");
        option4.setText("");
        player.setDemonHP(player.getDemonHP() - 5); //5 damage to demon
    }
    private void c5s18() {
        position = "c5s18";
        player.setPosition("c5s18");
        mainText.setText("This time you empty the contents of the vial completely onto the root. The mother tree shrieks, and in an instant all color is drained from her and the forest. Centuries of wisdom and power, gone. However, you’ve also managed to weaken the demon. She is heading towards you, and now you must fight her on your own. ");
        option1.setText(">"); //c5s22
        option2.setText("");
        option3.setText("");
        option4.setText("");
        player.setDemonHP(player.getDemonHP() - 5); //5 damage to demon
        player.removeItem("Poison"); //remove poison from inventory
        inventory.setText(player.getItems()); //update inventory display
        player.setMotherTreeAlive(false); //record that mother tree is dead
    }
    private void c5s19() {
        position = "c5s19";
        player.setPosition("c5s19");
        if (player.hasItem("Knife")) {
            mainText.setText("You pull your knife out of your pouch, hoping that it is sharp enough to cut the glowing root that connects the demon to the mother tree. Raising it overhead, you bring it down on the root again and again. The mother tree seems pained but encourages you to continue. ");
        } else {
            mainText.setText("You look around for something strong enough to cut the glowing root that connects the demon to the mother tree. You find a blunt rock about the size of your foot. Raising it overhead you smash it on a nearby boulder, cracking it in half and creating a sharp, jagged edge. This will do. You use the sharpened rock like an ax, bringing it down on the root again and again. The mother tree seems pained but encourages you to continue. ");
        }
        option1.setText("This won't kill you?"); //c5s20
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c5s19a() {
        position = "c5s19a";
        player.setPosition("c5s19a");
        mainText.setText("The mother tree’s life is fading, too weak to be furious with you.. You can only hope that the end justifies the means.");
        option1.setText(">"); //c5s22
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c5s20() {
        position = "c5s20";
        player.setPosition("c5s20");
        mainText.setText("“No, severing this root will not kill me, as I am bound to the earth through many more connections. It is necessary to disconnect me from Baphomet, and so you must continue. But know that she is aware of your attempts and now approaches us.”");
        option1.setText("Continue to cut root"); //c5s21
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }
    private void c5s21() {
        position = "c5s21";
        player.setPosition("c5s21");
        mainText.setText("With one final blow the root snaps in half, and its color returns to the ashen brown that it once was. The mother tree relaxes. “Good, she is weakened now, only a small fraction of her former glory. You must fight her on your own, as I am not fully recovered and have no more strength to lend.”");
        option1.setText(">"); //c5s22
        option2.setText("");
        option3.setText("");
        option4.setText("");
        player.setDemonHP(player.getDemonHP() - 15); //15 damage to demon
    }
    private void c5s22() {
        position = "c5s22";
        player.setPosition("c5s22");
        mainText.setText("A dark figure approaches until she is but a few feet away. Although you have never seen her before, you are certain that this is Lady Baphomet. She pauses, turning to look behind her. You crouch behind one of the mother tree’s large roots and consider your options before the demon notices you.");
        option1.setText("Ambush Lady Baphomet"); //c5s11d
        if (player.hasItem("Orion's beard")) {
            option2.setText("Take Orion's beard"); //c5s8d
        } else {
            option2.setText("");
        }
        if (player.hasItem("Whistle")) {
            option3.setText("Call forest sprite"); //c5s10d
        } else {
            option3.setText("");
        }
        if (player.hasItem("Knife") & player.hasItem("Poison")) {
            option4.setText("Add poison to blade"); //c5s9d
        } else {
            option4.setText("");
        }
    }

}