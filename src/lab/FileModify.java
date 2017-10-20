/**
 *
 */
package lab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

//import lab.Graph;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
/**
 * @author 111
 *
 */
public final class FileModify {
    /**
    *
    */
   private static int[][] eee, ddd, path;
   /**
   *
   */
  private static String[] txtWordArray;
  /**
   *
   */
  private static int wordNum = 0;
  /**
   *
   */
  private static boolean flag = true;
  /**
   *
   */
  private static final int INFINITY = 10000;
  /**
   *
   */
  private static StringBuffer pathWay = new StringBuffer();
  /**
  *
  */
  private static StringBuffer randomPath = new StringBuffer();
  /**
  *
  */
  private static List<String> wordList = new ArrayList<String>();
  /**
  *
  */
  private static List<String> edgePairList = new ArrayList<String>();
  /**
  *
  */
  private static Map<String, List<String>> map = new HashMap<String,
         List<String>>();
  /**
  *
  */
  private static Pattern ppp = Pattern.compile("[.,\"\\?!:' ]");
   /**
    *
    */
   private FileModify() {
   }
   /**
    * @param args Args
    * @throws IOException ioe
    */
   public static void main(final String[] args) throws IOException {
   final StringBuffer preStr = new StringBuffer(); //初步处理字符串
   final Scanner scan = new Scanner(System.in);
   System.out.println("Please input file adress : ");
   final String fileAdr = scan.nextLine();  //读取字符串型输入
//   InputStream fi = new FileInputStream
   //("/Users/summerchaser/Desktop/111.txt");
   final InputStream fii = new FileInputStream(fileAdr);
    int ccc = fii.read();
    String word1;
    String word2;
    Character mmm;
   while (ccc != -1) {
       mmm = (char) ccc;
       final String temp = mmm.toString();
       if (Character.isLetter(mmm)) {
           preStr.append(temp);
       } else if (ppp.matcher(temp).matches()) {
           preStr.append(' ');
       }
       ccc = fii.read();
   }
    // create word array
   txtWordArray = preStr.toString().toLowerCase().trim().split("\\s+");
   System.out.print("The Text equal to : ");
   for (final String word : txtWordArray) {
       // if wordlist no contain word , then add
       System.out.print(word + " ");
       if (!wordList.contains(word)) {
           wordList.add(word);
           wordNum++;  // recored word number
       }
   }
   System.out.println("\nThe number of word is ：" + wordNum);
   eee = new int[wordNum][wordNum]; //建立边集
   buildEdge();
   System.out.println("-------------------------"
        + "-------------------------------");
   final Graph graph = new Graph(wordList, eee, wordNum); //创建图的实例
   showDirectedGraph(graph);
   System.out.println("-----------------------------"
        + "---------------------------");
   createBridgeMap();
   System.out.println("Please input word1 and word2 to query bridge : ");
   word1 = scan.nextLine(); word2 = scan.nextLine();
   System.out.println(queryBridgeWords(word1, word2));
// System.out.println(queryBridgeWords("to","strange"));
   System.out.println("---------------------"
        + "-----------------------------------");
   System.out.println("Please input string to create new text : ");
   final String inputText = scan.nextLine();
   System.out.println(generateNewText(inputText));
// System.out.println(generateNewText("Seek to explore
   //new and exciting synergies"));
   System.out.println("-------------------------"
        + "-------------------------------");
   floyd();
   System.out.println("Please input word1 and word2 to query shortest path: ");
   word1 = scan.nextLine(); word2 = scan.nextLine();
   System.out.println(calcShortestPath(word1, word2));
//   System.out.println(calcShortestPath("to","strange"));
   System.out.println("Please input a word to find"
        + " the shortest path to others :");
   word1 = scan.nextLine();
//   findPathToOther("to");
   findPathToOther(word1);
   System.out.println("----------------------------"
        + "----------------------------");
   final String randomText = randomWalk();
   System.out.println(randomText);
   System.out.println("Save to the path /Users/summerchaser/Desktop/2.txt:");
   try {
       final File file = new File("/Users/summerchaser/Desktop/2.txt");
       final FileWriter fileWriter = new FileWriter(file);
       fileWriter.write(randomText);
       fileWriter.close(); // 关闭数据流
   } catch (IOException exe) {
       //exe.printStackTrace();
   }
   fii.close();
   scan.close();
}
   /**
    * @param word1 Word1
    * @param word2 Word2
    * @return String
    */
   private static String queryBridgeWords(final String word1,
          final String word2) {
       if (wordList.contains(word1) && wordList.contains(word2)) {
           final String key = word1 + "#" + word2;
           final boolean istrue = !map.containsKey(key);
           if (!(wordList.contains(word1) && !wordList.contains(word2))) {
               return "No word1 or word2 in the graph!";
           } else if (istrue) {
               return "No bridge words from word1 to word2!";
           } else {
               final StringBuffer result = new StringBuffer("The bridge from "
           + word1 + " to " + word2 + "  are: ");
               for (final String bridge: map.get(key)) {
                   result.append(bridge + " ");
               }
               return result.toString();
           }
       } else {
           return "input error";
       }
   }
   /**
    * @param graph Graph
    */
   private static void showDirectedGraph(final Graph graph) {
       System.out.println("The graph presented is ：");
       for (int i = 0; i < graph.getWordNum(); i++) {
           for (int j = 0; j < graph.getWordNum(); j++) {
               if (eee[i][j] != INFINITY) {
                   final List<String> list = graph.getWordList();
                   final String word1 = list.get(i);
                   final String word2 = list.get(j);
                   System.out.printf("Edge: " + word1 + " --> " + word2
                         + "   Weigth: %d\n", eee[i][j]);
               }
           }
       }
   }

   /**
    *
    */
   private static void buildEdge() {
       int preNum;
       int curNum;
       int iii;
       int jjj;
       String pre = "#";
       for (final String word : txtWordArray) {
           if (!"#".equals(pre)) {
               preNum = wordList.indexOf(pre);
               curNum = wordList.indexOf(word);
               eee[preNum][curNum]++;
           }
           pre = word;
       }
      System.out.println("The graph matrix is ：");
       for (iii = 0; iii < wordNum; iii++) {
          for (jjj = 0; jjj < wordNum; jjj++) {
               final int eeeNum = eee[iii][jjj];
               if (eeeNum == 0) {
                   eee[iii][jjj] = INFINITY;
                   System.out.printf("0 ");
               } else {
                   System.out.printf("%d ", eeeNum);
               }
          }
          System.out.println("");
      }
   }
   /**
    *
    */
   private static void createBridgeMap() {
       int iii;
       final List<String> valueList = new ArrayList<String>();
       for (iii = 0; iii < txtWordArray.length - 2; iii++) {
           final String kkk = txtWordArray[iii + 1];
           final String key = txtWordArray[iii] + "#" + txtWordArray[iii + 2];
           if (map.containsKey(key)) {
               map.get(key).add(kkk);
           } else {
               valueList.add(kkk);
               map.put(key, valueList);
           }
       }
   }
   /**
    * @param inputText InputText
    * @return String
    */
   private static String generateNewText(final String inputText) {
       int iii;
       final String[] textWord = inputText.toLowerCase().trim().split("\\s+");
       final StringBuffer newText = new StringBuffer();
       newText.append(textWord[0] + " ");
       System.out.println("New text created is ：");
       for (iii = 0; iii < textWord.length - 1; iii++) {
           final String key = textWord[iii] + "#" + textWord[iii + 1];
           if (map.containsKey(key)) {
               final String bridge = map.get(key).get(0);
               newText.append(bridge + " " + textWord[iii + 1] + " ");
           } else {
               newText.append(textWord[iii + 1] + " ");
           }
       }
       return newText.toString();
   }

   /**
    * @param start Start
    * @param end End
    */
   private static void getPath(final int start, final int end) {
       if (path[start][end] == -1) {
           return;
       } else {
           final int pathEnd = path[start][end];
           getPath(start, pathEnd);
           pathWay.append(wordList.get(pathEnd) + " -> ");
           //->是非书面字符不合法
       }
   }

   /**
    * @param word1 Word1
    * @param word2 Word2
    * @return String
    */
   private static String calcShortestPath(final String word1,
               final String word2) {
       if (wordList.contains(word1) && wordList.contains(word2)) {
       System.out.println("The path " + word1 + " to " + word2 + " is :");
           final int start = wordList.indexOf(word1);
           final int end = wordList.indexOf(word2);
           if (ddd[start][end] == INFINITY) {
               return "no access";
           } else {
               pathWay.append(word1 + " -> ");
               getPath(start, end);
               pathWay.append(word2);
               final String pathw = pathWay.toString();
               pathWay.delete(0, pathWay.length());
               return pathw;
           }
       } else {
           return "input error";
       }
   }
   /**
    * @param word Word
    */
   private static void findPathToOther(final String word) {
       final int sss = wordList.indexOf(word);
       int iii;
       for (iii = 0; iii < wordNum; iii++) {
           pathWay.delete(0, pathWay.length());
           if (iii != sss) {
               final String word1 = wordList.get(sss);
               final String word2 = wordList.get(iii);
               System.out.println(calcShortestPath(word1, word2));
           }
       }
   }
   /**
 *
 */
    private static void floyd() {
       int iiii;
       int jjj;
       ddd = new int[wordNum][wordNum];
       path = new int[wordNum][wordNum];
       for (iiii = 0; iiii < wordNum; iiii++) {
           for (jjj = 0; jjj < wordNum; jjj++) {
               ddd[iiii][jjj] = eee[iiii][jjj];
               path[iiii][jjj] = -1;
           }
       }
       for (int k = 0; k < wordNum; k++) {
           for (iiii = 0; iiii < wordNum; iiii++) {
               for (jjj = 0; jjj < wordNum; jjj++) {
                   if (ddd[iiii][k] + ddd[k][jjj] < ddd[iiii][jjj]) {
                       ddd[iiii][jjj] = ddd[iiii][k] + ddd[k][jjj];
                       path[iiii][jjj] = k;
                   }
               }
           }
       }
   }
   /**
    * @param sss S
    * @return true
    */
   private static boolean isEnd(final int sss) {
       for (int i = 0; i < wordNum; i++) {
           final String edgePair =   String.valueOf(sss)
             + "#" + String.valueOf(i);
           if (eee[sss][i] != INFINITY
                && !edgePairList.contains(edgePair)) { //如果存在边
               return false;
           }
       }
       return true; // 不存在边，即是尽头
   }
   /**
    * @return rp
    */
   private static String randomWalk() {
       final int ranNum = (int) Math.round(Math.random() * (wordNum - 1));
       final String ranWord = wordList.get(ranNum);
       randomPath.append(ranWord);
       System.out.println("System choose -> " + ranWord + "\nRandom walk is ：");
       walkFrom(ranNum);
       final String randP = randomPath.toString();
       randomPath.delete(0, randomPath.length());
       return randP;
   }
   /**
    * @param ssssss S
    */
   private static void walkFrom(final int ssssss) {
       for (int i = 0; i < wordNum; i++) {
           if (flag) {
               final String edgePair =   String.valueOf(ssssss) + "#"
           + String.valueOf(i);
               if (eee[ssssss][i] < INFINITY
            && !edgePairList.contains(edgePair)) {
                   edgePairList.add(edgePair);
                   randomPath.append(" -> " + wordList.get(i));
                   walkFrom(i);
               } else if (isEnd(ssssss)) {
                   flag = false;
                   return;
               }
           }
       }
   }
   }
   ///Users/summerchaser/Desktop/111.txt
   //Seek to explore new and exciting synergies
