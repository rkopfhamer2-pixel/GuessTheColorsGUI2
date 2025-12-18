/*
 * GuessColorsV2 is a game to figure out a color code.  
 * Can you guess the sequence of colors before you run out of guesses
 * @author: Ronda Kopfhamer
 */
import java.awt.*;
import javax.swing.*;

import java.awt.event.*;

public class GuessColorsGUI extends JFrame implements ActionListener, MouseListener
{
  private int WIDTH = 400;
  private int HEIGHT = 600; 
  private JPanel panel, panelGrid, panelTop, panelBottom; 
  private JButton guessButton, newButton, quitButton;
  private JLabel messageLabel, colorLabel; 
  private Font font1 = new Font("Arial", Font.BOLD, 38);
  private Font font2 = new Font("Comic Sans MS", Font.BOLD, 20);
  private JLabel [][] gridLabel = new JLabel[10][7];
  private Color colorArray[] = {Color.RED, Color.WHITE, Color.BLUE, Color.BLACK, Color.CYAN, Color.MAGENTA};
  private GuessColorsV2 game = new GuessColorsV2();
  
  private int numGuesses = 0;
  private int currentRow = 0;
  private int currentCol = 0;
  private int colorIndex[] = {-1, -1, -1, -1};
  
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
    colorLabel = new JLabel("Choices: RED WHITE BLUE BLACK CYAN MAGENTA");
    panelTop.add(messageLabel);
    panelTop.add(colorLabel);
    panelTop.setPreferredSize(new Dimension(10,60));
    
    //add items to the bottom panel
    guessButton = new JButton("Check");
    newButton = new JButton("New");
    quitButton = new JButton("Quit");
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
        else if (col!=0 && col % 5 ==0) //the second to the last column -- how many colors are in correct position
          gridLabel[row][col].setBackground(Color.GREEN);
        else if (col!=0 && col % 6 ==0) //the last column -- how many colors are correct but in the WRONG position
          gridLabel[row][col].setBackground(Color.YELLOW);
        else
          gridLabel[row][col].setBackground(Color.GRAY);  //the board of guesses -- each row is 1 guess of 4 colors
        panelGrid.add(gridLabel[row][col]);
        gridLabel[row][col].addMouseListener(this);  //allows label to be clicked
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
  
  public String setGuessValue()
  {
    String guess="";
    Color c = gridLabel[currentRow][0].getBackground();
    for(int i = 0; i<colorArray.length; i++)
    { 
      if (colorArray[i].equals( c))
        guess +=i;
    }
    c = gridLabel[currentRow][1].getBackground();
    for(int i = 0; i<colorArray.length; i++)
    { 
      if (colorArray[i].equals( c))
        guess +=i;
    }
    c = gridLabel[currentRow][2].getBackground();
    for(int i = 0; i<colorArray.length; i++)
    { 
      if (colorArray[i].equals( c))
        guess +=i;
    }
    c = gridLabel[currentRow][3].getBackground();
    for(int i = 0; i<colorArray.length; i++)
    { 
      if (colorArray[i].equals( c))
        guess +=i;
    }
    return guess;
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
      String guess = setGuessValue();
      //System.out.println(guess);  //used for testing purposes only
      if( game.guessIsValid(guess))
      {
        numGuesses++;  //adds to number of Guesses
        //colors = game.outputGuesses();  //used for testing purposes only
       
        message = "Enter next guess.    Guesses Left: " + (10-numGuesses);
        messageLabel.setText(message);
        showColors();
        
        game.checkPositions();
        int numCorrect = game.getNumCorrectPositions();
        gridLabel[currentRow][5].setText("   "+numCorrect);
        gridLabel[currentRow][6].setText("   "+game.getNumCorrectColors());
        if (numGuesses<=10)
        {
          currentRow = numGuesses;
          for(int i=0; i<colorIndex.length; i++)
            colorIndex[i]=-1;
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
           }
    }
        
    }  //end of if source == guessButton
    
    else if(source == newButton)
    {
      game = new GuessColorsV2();
      game.createAnswer();
      numGuesses=0;
      currentRow=0;
      for(int i=0; i< colorIndex.length; i++)
        colorIndex[i]=-1;
      messageLabel.setText("Can you guess correctly in 10 moves? Guesses Left: 10");
      colorLabel.setText("Choices: RED WHITE BLUE BLACK CYAN MAGENTA");
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
    } //end of if source == newButton
    
    else if(source == quitButton)
    {
      System.exit(0);
    }  //end of in source ==quitButton
  }
  
  
  
 //MouseListener methods required
  public void mouseClicked(MouseEvent e)
  {
    Object source = e.getSource();
    if (source instanceof JLabel)
    {
     
     if (source == gridLabel[currentRow][0])
      {
        if(colorIndex[0]>=5)
          colorIndex[0]=0;
        else
          colorIndex[0]++;
        gridLabel[currentRow][0].setBackground(colorArray[colorIndex[0]]);
      }
      else if (source == gridLabel[currentRow][1])
      {
        if(colorIndex[1]>=5)
          colorIndex[1]=0;
        else
          colorIndex[1]++;
        gridLabel[currentRow][1].setBackground(colorArray[colorIndex[1]]);
      }
      else if (source == gridLabel[currentRow][2])
      {
        if(colorIndex[2]>=5)
          colorIndex[2]=0;
        else
          colorIndex[2]++;
        gridLabel[currentRow][2].setBackground(colorArray[colorIndex[2]]);
      }
      else if (source == gridLabel[currentRow][3])
      {
        if(colorIndex[3]>=5)
          colorIndex[3]=0;
        else
          colorIndex[3]++;
        gridLabel[currentRow][3].setBackground(colorArray[colorIndex[3]]);
      }
    }
  }
  public void mouseEntered(MouseEvent e)
  {
  }
  
  public void mouseExited(MouseEvent e)
  {
  }
  
  public void mousePressed(MouseEvent e)
  {
  }
  public void mouseReleased(MouseEvent e)
  {
  }
  
  
   public static void main(String [] args)
  {
     
    GuessColorsGUI window = new GuessColorsGUI();  //calls the constructor
   
    
  }

}