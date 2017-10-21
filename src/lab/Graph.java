/**
 *
 */
package lab;

import java.util.List;

/**
 * @author 111
 *
 */
public class Graph {
   /**
    *
    */
   private int wordNum; //顶点数
   /**
    * @return int
    */
   public int getWordNum() {
       return wordNum;
   }
   /**
    *
    */
   private List<String> wordList; //顶点集
   /**
    * @return wordList
    */
   public List<String> getWordList() {
       return wordList;
   }
   /**
    *
    */
   private int[][] e; //边集
   /**
    * @param wordlist Wordlist
    * @param e1 E1
    * @param wordnum WordNum
    */
   public Graph(final List<String> wordlist, final int[][] e1,
          final int wordnum) {
       this.wordList = wordlist;
       this.wordNum = wordnum;
   }

}
