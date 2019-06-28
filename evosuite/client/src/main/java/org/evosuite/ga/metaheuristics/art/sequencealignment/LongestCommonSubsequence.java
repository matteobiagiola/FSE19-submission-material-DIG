package org.evosuite.ga.metaheuristics.art.sequencealignment;

import org.evosuite.ga.metaheuristics.art.distance.input.UsefulConstants;

import java.util.ArrayList;
import java.util.List;

public class LongestCommonSubsequence extends DynamicProgramming {

   private List<Integer> lcsReferencesFirstSequence = new ArrayList<Integer>();

   public LongestCommonSubsequence(MethodSequence methodSequence1, MethodSequence methodSequence2) {
      super(methodSequence1, methodSequence2);
   }

   public MethodSequence getLongestCommonSubsequence(String separator) {
      if (!isInitialized) {
         initialize();
      }
      if (!tableIsFilledIn) {
         fillIn();
      }

      List<String> lcsList = (List<String>) getTraceback();
      String[] lcsArray = new String[lcsList.size()];

      lcsArray = lcsList.toArray(lcsArray);

      MethodSequence methodMethodSequence = new MethodCallSequence(separator,lcsArray);

      return methodMethodSequence;
   }

   public List<Integer> getLcsReferencesFirstSequence() {
      return this.lcsReferencesFirstSequence;
   }

   public int getLevenshteinDistance(MethodSequence longestCommonSubsequence){
      int levenshteinDistance = methodSequence1.length() + methodSequence2.length() - 2 * longestCommonSubsequence.length();
      return levenshteinDistance;
   }


   @Override
   protected void fillInCell(Cell currentCell, Cell cellAbove, Cell cellToLeft,
         Cell cellAboveLeft) {
      int aboveScore = cellAbove.getScore();
      int leftScore = cellToLeft.getScore();
      int matchScore;
      if (methodSequence1.charAt(currentCell.getCol() - 1).equals(methodSequence2
            .charAt(currentCell.getRow() - 1))) {
         matchScore = cellAboveLeft.getScore() + 1;
      } else {
         matchScore = cellAboveLeft.getScore();
      }
      int cellScore;
      Cell cellPointer;
      if (matchScore >= aboveScore) {
         if (matchScore >= leftScore) {
            // matchScore >= aboveScore and matchScore >= leftScore
            cellScore = matchScore;
            cellPointer = cellAboveLeft;
         } else {
            // leftScore > matchScore >= aboveScore
            cellScore = leftScore;
            cellPointer = cellToLeft;
         }
      } else {
         if (aboveScore >= leftScore) {
            // aboveScore > matchScore and aboveScore >= leftScore
            cellScore = aboveScore;
            cellPointer = cellAbove;
         } else {
            // leftScore > aboveScore > matchScore
            cellScore = leftScore;
            cellPointer = cellToLeft;
         }
      }
      currentCell.setScore(cellScore);
      currentCell.setPrevCell(cellPointer);
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ibm.compbio.seqalign.DynamicProgramming#getInitialPointer(int,
    *      int)
    */
   @Override
   protected Cell getInitialPointer(int row, int col) {
      return null;
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ibm.compbio.seqalign.DynamicProgramming#getInitialScore(int, int)
    */
   @Override
   protected int getInitialScore(int row, int col) {
      return 0;
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ibm.compbio.seqalign.DynamicProgramming#getTraceback()
    */
   @Override
   protected Object getTraceback() {
      List<String> lcsList = new ArrayList<String>();
      //StringBuffer lCSBuf = new StringBuffer();
      Cell currentCell = scoreTable[scoreTable.length - 1][scoreTable[0].length - 1];
      while (currentCell.getScore() > 0) {
         Cell prevCell = currentCell.getPrevCell();
         if ((currentCell.getRow() - prevCell.getRow() == 1 && currentCell
               .getCol()
               - prevCell.getCol() == 1)
               && currentCell.getScore() == prevCell.getScore() + 1) {
            lcsList.add(0,methodSequence1.charAt(currentCell.getCol() - 1));
            //lcsList.add(methodSequence1.charAt(currentCell.getCol() - 1));
            this.lcsReferencesFirstSequence.add(0,currentCell.getCol() - 1);
            //lCSBuf.insert(0, sequence1.charAt(currentCell.getCol() - 1));
         }
         currentCell = prevCell;
      }

      //return lCSBuf.toString();
      return lcsList;
   }
}
