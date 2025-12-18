/*
 * GuessColorsV2 is a game to figure out a color code.  
 * Can you guess the sequence of colors before you run out of guesses
 * @author: Ronda Kopfhamer
 */
import java.awt.*;
import javax.swing.*;

import java.awt.event.*;

public class GuessColorsGUI extends JFrame implements ActionListener
{
  private int WIDTH = 400;
  private int HEIGHT = 600; 
  private JPanel panel, panelGrid, panelTop, panelBottom; 
  private JButton guessButton, newButton, quitButton;
  private JLabel messageLabel, colorLabel,  guessLabel;
  private JTextField guessText;
  private Font font1 = new Font("Arial", Font.BOLD, 38);
  private Font font2 = new Font("Comic Sans MS", Font.BOLD, 20);
  private JLabel [][] gridLabel = new JLabel[10][7];
  private GuessColorsV2 game = new GuessColorsV2();
  
  private int numGuesses = 0;
  private int currentRow = 0;
  private int currentCol = 0;
  
  //constructor
  public GuessColorsGUI()
  {
    super("Guess The Colors");
    game = new GuessColorsV2();
    game.createAnswer();
    panel = new JPanel(new BorderLayout()); 
    panelGrid = new JPanel(new GridLayout(10, 7,2,2));
    panelTop = new JPanel(new FlowLayout());
    panelBottom = new JPanel(new FlowLayout());
    setSize(WIDTH, HEIGHT);
    add(panel);
    //add Labels to the top panel  
    messageLabel = new JLabel("Can you guess correctly in 10 moves? Guesses Left: 10");
    colorLabel = new JLabel("0-RED 1-WHITE 2-BLUE 3-BLACK 4-CYAN 5-MAGENTA");
    panelTop.add(messageLabel);
    panelTop.add(colorLabel);
    panelTop.setPreferredSize(new Dimension(10,60));
    
    //add items to the bottom panel
    guessLabel = new JLabel("Type 4 Digit Guess:");
    guessText = new JTextField(4);
    guessButton = new JButton("Check");
    newButton = new JButton("New");
    quitButton = new JButton("Quit");
    panelBottom.add(guessLabel);
    panelBottom.add(guessText);
    panelBottom.add(guessButton);
    panelBottom.add(newButton);
    panelBottom.add(quitButton);
    panelBottom.setPreferredSize(new Dimension(10, 60));
    
    //add inner panels to the outside BorderLayout panel
    panel.add(panelTop, BorderLayout.NORTH);
    panel.add(panelGrid, BorderLayout.CENTER);
    panel.add(panelBottom, BorderLayout.SOUTH);
    // Set this button as the default button
    getRootPane().setDefaultButton(guessButton);
    guessButton.addActionListener(this);
    newButton.addActionListener(this);
    quitButton.addActionListener(this);
    
    //start of code that will add the components for the main board to the middle panel
    
    /*
     * the nested loops create JLabels to store 10 color guesses for the user in the first four columns
     * each row represents a guess of 4 color combinations.
     * The fifth column is hidden for visual space between the guess and the hints about the correct colors.
     * The second to last column stores the number of colors in the correct position for that guess
     * The last column stores the number of correct colors that are in the WRONG position.
     */
    for (int row = 0; row < gridLabel.length; row++)
    {
      for (int col = 0; col < gridLabel[0].length; col++)
      {
        gridLabel[row][col] = new JLabel();
        gridLabel[row][col].setOpaque(true);  //allows the colors to  be set 
        
        if (col!=0 && col % 4 ==0)
          gridLabel[row][col].setOpaque(false); //don't show these labels to separate the guess from the clues
          //gridLabel[row][col].setBackground(Color.GRAY);
        else if (col!=0 && col % 5 ==0) //the second to the last column -- how many colors are in correct position
          gridLabel[row][col].setBackground(Color.GREEN);
        else if (col!=0 && col % 6 ==0) //the last column -- how many colors are correct but in the WRONG position
          gridLabel[row][col].setBackground(Color.YELLOW);
        else
          gridLabel[row][col].setBackground(Color.GRAY);  //the board of guesses -- each row is 1 guess of 4 colors
        panelGrid.add(gridLabel[row][col]);
      }
     }
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    setVisible(true);
  }
  
  /*
   * shows the user's current colors guessed on the next empty row in the table
   */
  private void showColors()
  {
    String [] guessArray = game.getGuess();
    for (int col = 0; col < 4; col++)
    {
      int currentColor = Integer.parseInt(guessArray[col]);
      Color [] colorArray = game.getColorArray();
      gridLabel[currentRow][col].setBackground(colorArray[currentColor]);
    }
  }
  
  
  //required to implement actionListener
  public void actionPerformed(ActionEvent e)
  {
    Object source = e.getSource();
    String colors="";
    String message="";
    if (source == guessButton)
    {
      if (numGuesses<=10) 
      {
      if( game.guessIsValid(guessText.getText()))
      {
        numGuesses++;  //adds to number of Guesses
        //colors = game.outputGuesses();  //can use for testing purposes
       
        message = "Enter next guess.    Guesses Left: " + (10-numGuesses);
        messageLabel.setText(message);
        showColors();
        
        guessText.setText("");
        game.checkPositions();
        int numCorrect = game.getNumCorrectPositions();
        gridLabel[currentRow][5].setText("   "+numCorrect);
        gridLabel[currentRow][6].setText("   "+game.getNumCorrectColors());
        guessText.requestFocusInWindow();
        if (numGuesses<=10)
        {
          currentRow = numGuesses;
          if (numCorrect == 4)
          {
          messageLabel.setText("You won in " + numGuesses + " moves! Click New for new game");
          numGuesses = 10;
          }
          else if (numGuesses >= 10)  //the user lost on the last guess
                                     //must be checked here since numGuesses was incremented
                                     //otherwise game doesn't end at appropriate time
         {
          message = "You lost. Click New for a new game";
          messageLabel.setText(message);
          message = "Correct Answer was " + game.showAnswer();
          colorLabel.setText(message);
        }      
        }
       }
      else if(numGuesses<10)  //input was not correct
          {
          messageLabel.setText("Please re-enter guess  Guesses Left: " + (10-numGuesses));
          guessText.setText("");
          guessText.requestFocusInWindow();
           }
    }
        
    }  //end of if source == guessButton
    
    else if(source == newButton)
    {
      game = new GuessColorsV2();
      game.createAnswer();
      numGuesses=0;
      currentRow=0;
      messageLabel.setText("Can you guess correctly in 10 moves? Guesses Left: 10");
      colorLabel.setText("0-RED 1-WHITE 2-BLUE 3-BLACK 4-CYAN 5-MAGENTA");
      for (int row = 0; row < gridLabel.length; row++)
    {
      for (int col = 0; col < gridLabel[0].length; col++)
      {
        if (col!=0 && col % 5 ==0)
          gridLabel[row][col].setBackground(Color.GREEN);
        else if (col!=0 && col % 6 ==0)
          gridLabel[row][col].setBackground(Color.YELLOW);
        else
          gridLabel[row][col].setBackground(Color.GRAY);
        panelGrid.add(gridLabel[row][col]);
      }
      
      for (int col = 5; col< 7; col++)
      {
        gridLabel[row][col].setText("");
      }
 
    }
      guessText.requestFocusInWindow();
  } //end of if source == newButton
    
    else if(source == quitButton)
    {
      System.exit(0);
    }  //end of in source ==quitButton
  }
  
 
  
  
  
   public static void main(String [] args)
  {
     
    GuessColorsGUI window = new GuessColorsGUI();  //calls the constructor
   
    
  }

}