/*
 * GuessColors is a game to figure out a color code.  
 * Can you guess the sequence of colors before you run out of guesses
 * @author: Ronda Kopfhamer
 */
import java.util.Scanner;
import java.awt.*;
public class GuessColorsV2
{
  private Color colorArray []= {Color.RED, Color.WHITE, Color.BLUE, Color.BLACK, Color.CYAN, Color.MAGENTA};
  //stores the list of colors used in an array of Color which can be used by the GUI
  
  private String colorArrayString []= {"RED", "WHITE", "BLUE", "BLACK", "CYAN", "MAGENTA"};
 // the list of color names
  //0 - RED 1 - WHITE  2 - BLUE  3 - BLACK  4 - CYAN  5 - MAGENTA

  private String [] answer = new String[4];
  //a list of 4 numbers stored in a String, each digit can be 0-5
  //each number corresponds to one of the 6 colors above.

  private String guess[] =  new String[4];
  //the users current color guess stored as 4 numbers in a String, each digit can be 0-5

  private int numCorrectPositions = 0;
  //tracks the number of correct colors in the current guess

  private int numCorrectColors = 0;
  //tracks the number of correct colors in the wrong position.
  
  private boolean correct[] = {false, false, false, false};
  //tracks which color in the current guess is correct
  
  private boolean match[]= {false, false, false, false};
  //tracks which color in the current guess has a match to the answer

  
 /*
  * returns the number of correct colors in the wrong position
 */
  public int getNumCorrectColors()
  {
    return numCorrectColors;
  }
  
  /*
  * returns the number of colors in the correct position
 */
  public int getNumCorrectPositions()
  {
    return numCorrectPositions;
  }
  
  /*
  * returns the list of 6 color choices
 */
 public Color[] getColorArray()
 { 
   return colorArray;
 }
 
  /*
  * returns the user's latest guess
 */
 public String[] getGuess()
 {
   return guess;
 }
  
  
 /*
  * creates the 4 colors in the answer, given as int values
 */
 public void createAnswer()
 {
   //picks one of 6 colors to set the colors of the correct answer
    for (int i = 0; i<4; i++)
      answer[i] = ""+(int)(Math.random()*6);
 }

  /*
  * returns true when the first 4 characaters in the guess are 4 digits, each in the range from "0" to "5"
  * otherwise returns false
 */
public boolean guessIsValid(String numberGuess)
{
  boolean valid = false;
  if (numberGuess.length() < 4)
    return false;
  else
  {
    int digitsGood = 0;
  for (int i = 0; i <4; i ++)
  {
    guess[i] = numberGuess.substring(i, i+1);
    if (guess[i].compareTo("0") >=0 && guess[i].compareTo("5")<=0)
      digitsGood++;
  
   }
  //all 4 digits must be 0-5 or you don't have a valid String of digits
  if (digitsGood==4)
  {
    valid = true;
  }
  return valid;
  }
     
}


 /*
  * returns a message with the guess as a sequence of colors in a String
 */
public String outputGuesses()
{
  String message = "";
  //System.out.print("You chose:  ");  //for testing purposes
  for (int i = 0; i <4; i++)
  { int color = Integer.parseInt(guess[i]);
    message +=" " + colorArrayString[color];
    
  }
   // System.out.print(" " + message);  //for testing purposes
  // System.out.println();
  return message;
}


 /*
  * checks for colors in the correct position and color matches in the wrong position
 */
public void checkPositions()
{
  for(int i = 0; i<4; i++)
  {
  correct[i] =  false;
  match[i] =  false;
  }
  numCorrectPositions=0;
  numCorrectColors=0;
  //first 4 if statements will set color and match arrays to true if color is correct
  if (guess[0].equals(answer[0]))  //first color correct 
  {
        numCorrectPositions++;
        correct[0] =true;
        match[0] = true;
  }
  if (guess[1].equals(answer[1])) //second color correct
  {
        numCorrectPositions++;
        correct[1] =true;
        match[1] = true;
  }
  if (guess[2].equals(answer[2]))  //third color correct
  {
        numCorrectPositions++;
        correct[2] =true;
        match[2] = true;
  }
  if (guess[3].equals(answer[3]))  //fourth color correct
  {
        numCorrectPositions++;
        correct[3] =true;
        match[3] = true;
  }
  if (numCorrectPositions<3)  //if 3 correct colors exist, no need to check for matches on the last color
  {
    if (! correct[0])  //color 1 is not correct, check the remaining colors that are not correct for matches
    {
      if(!correct[1] && guess[0].equals(answer[1]))  //compare 1st guess number to 2nd number in answer
      {
        numCorrectColors++;
        match[1] = true;
      }
      else if (!correct[2]  && guess[0].equals(answer[2]))  //compare 1st guess number to 3rd number in answer
        {
        numCorrectColors++;
         match[2] = true;
      }
      else if (! correct[3] && guess[0].equals(answer[3]))  //compare 1st guess number to 4th number in answer
        {
        numCorrectColors++;
        match[3] = true;
      }
    }
    if(!correct[1]) //color 2 is not correct, check the remaining colors that are not correct for matches
    {
      if (!correct[0] && !match[0] && guess[1].equals(answer[0]))  //compare 2nd guess number to 1st number in answer
      {
        numCorrectColors++;
        match[0] = true;
      }
    else if (!correct[2] && !match[2] && guess[1].equals(answer[2])) //compare 2nd guess number to 3rd number in answer
      {
        numCorrectColors++;
        match[2] = true;
      }
    else if (!correct[3] && !match[3] && guess[1].equals(answer[3])) //compare 2nd guess number to 4th number in answer
    { 
      numCorrectColors++;
      match[3] = true;
    }
    }
    if (!correct[2])  //color 3 is not correct, check the remaining colors that are not correct for matches
    { 
      if (!correct[0]  && !match[0] && guess[2].equals(answer[0]))  //compare 3rd guess number to 1st number in answer
    {
      numCorrectColors++;
      match[0]=true;  
    }
      else if (!correct[1] && !match[1] && guess[2].equals(answer[1])) //compare 3rd guess number to 2nd number in answer
      {
         numCorrectColors++;
         match[1]=true; 
      }
      else if (!correct[3] && !match[3] && guess[2].equals(answer[3])) //compare 3rd guess number to 4th number in answer
      {
        numCorrectColors++;
        match[3]=true; 
      }
     
}
    if (!correct[3])  //color 4 is not correct, check the remaining colors that are not correct for matches
    {
      if (!correct[0] && !match[0] && guess[3].equals(answer[0])) //compare 4th guess number to 1st number in answer
    {
         numCorrectColors++;
          match[0]=true; 
    }
    else if (!correct[1] && !match[1] && guess[3].equals(answer[1])) //compare 4th guess number to 2nd number in answer
     {
          numCorrectColors++;
          match[1]=true;     
               
      }
    else if (!correct[2] && !match[2] && guess[3].equals(answer[2])) //compare 4th guess number to 3rd number in answer
    {
       numCorrectColors++;
        match[2]=true;  
    }
  }
  }
   // System.out.print("\n\nThe real answer " ); //used for testing
  //showColors();  //for testing 
  //  System.out.println(numCorrectPositions + "  " + numCorrectColors);  //used for testing
  
}


 /*
  * outputs the colors in the correct sequence for the answer created
  * use for testing purposes along with GUI
 */
public String showAnswer()
{
  String message = "";
  for (int i = 0; i <4; i++)
  { int color = Integer.parseInt(answer[i]);
      message += " " + colorArrayString[color];  //for testing purposes
  }
  return message;
}
}