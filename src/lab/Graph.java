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
   private int wordNum; //������
   /**
    * @return int
    */
   public int getWordNum() {
       return wordNum;
   }
   /**
    *
    */
   private List<String> wordList; //���㼯
   /**
    * @return wordList
    */
   public List<String> getWordList() {
       return wordList;
   }
   /**
    *
    */
   private int[][] e; //�߼�
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
