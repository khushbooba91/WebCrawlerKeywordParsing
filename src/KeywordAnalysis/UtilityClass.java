package KeywordAnalysis;

import java.util.*;

/**
 * Created by Khushbooba on 6/8/17.
 */
public class UtilityClass {
    public static void printResult(List<Map.Entry<String, Integer>> list){
        for(Map.Entry<String, Integer> entry : list){
            System.out.println(entry);
        }
    }

    public static List<String> slideWindow(List<String> textList, int winSize){
        List<String> res = new ArrayList<String>();
        for(int i=0; i<textList.size(); i++){
            String tmp = new String();
            int j=i;
            for(j=i; j<textList.size() && j-i<winSize; j++){
                tmp = tmp + textList.get(j) + " ";
            }

            if(j==i+winSize){
                tmp = tmp.substring(0, tmp.length()-1);
                res.add(tmp);
            }
        }
        return res;
    }


    public static Set<String> getStopWord(String filename){
        Set<String> stopwords = new HashSet<String>();

        Scanner sc = new Scanner(UtilityClass.class.getResourceAsStream(filename));
        while (sc.hasNext()){
            stopwords.add(sc.next());
        }
        return stopwords;
    }

    public static boolean isPunc(String str){
        return str.endsWith(".") ||str.endsWith(":") || str.endsWith(",") || str.endsWith("?") || str.endsWith("!");
    }

    public static String purifyWord(String str){
        StringBuilder tmp = new StringBuilder(str);
        for(int i=0; i<tmp.length(); i++){
            if(!Character.isDigit(tmp.charAt(i)) && !Character.isLetter(tmp.charAt(i)) && tmp.charAt(i) != '-' && tmp.charAt(i) != '_'){
                tmp.deleteCharAt(i);
                i--;
            }
        }
        return tmp.toString();
    }

    public static String purifyString(String str){
        str = str.toLowerCase();
        str = str.replace("&", "& ");
        str = str.replace(".", ". ");
        str = str.replace(",", ", ");
        str = str.replace("?", "? ");
        str = str.replace("!", "! ");
        str = str.replace(";", "; ");
        str = str.replace(":", ": ");
        str = str.replace("#", "# ");
        str = str.replace("(", " ");
        str = str.replace(")", " ");
        str = str.replace("[", " ");
        str = str.replace("]", " ");
        str = str.replace("{", " ");
        str = str.replace("}", " ");
        str = str.replace("...", " ");
        str = str.replace("/", " ");
        str = str.replace("<", " ");
        str = str.replace(">", " ");
        str = str.replace("'s", " ");
        str = str.replace("'d", " ");
        str = str.replace("'t", " ");
        str = str.replace("\'", " ");
        str = str.replace("\"", " ");
        str = str.replace("=", " ");
        str = str.replace("@", " ");
        return str;
    }
}
