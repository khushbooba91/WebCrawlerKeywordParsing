package KeywordAnalysis;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.*;

/**
 * Created by administrator on 6/8/17.
 */

public class KeywordParser {

    private String url;
    private Set<String> stopwords;
    private Document doc;
    private Map<String, List<List<Map.Entry<String, Integer>>>> DataBase;
    private String pageTitle;

    public KeywordParser(){}
    public KeywordParser(Set<String> stopwords, String url){
        this.stopwords = stopwords;
        this.url = url;
        try{
            this.doc = Jsoup.connect(url.toString()).timeout(10000).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.120 Safari/535.2").get();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public Map<String, List<List<Map.Entry<String, Integer>>>> getData(){
        return this.DataBase;
    }

    public String getTitle(){
        getTitleText();
        return this.pageTitle;
    }

    public void parseAll(){
        DataBase = new HashMap<String, List<List<Map.Entry<String, Integer>>>>();
        DataBase.put("all", parseWithOperation("all"));

    }

    // parseall the text according to stopwords and , . ? : ;
    public List<List<Map.Entry<String, Integer>>> parseWithOperation(String operation){
        List<Map.Entry<String, Integer>> singleWordList = new ArrayList<Map.Entry<String, Integer>>();
        List<Map.Entry<String, Integer>> doubleWordList = new ArrayList<Map.Entry<String, Integer>>();
        List<Map.Entry<String, Integer>> tripleWordList = new ArrayList<Map.Entry<String, Integer>>();

        List<List<String>> phrases = new ArrayList<List<String>>();
        List<List<Map.Entry<String, Integer>>> res = new ArrayList<List<Map.Entry<String, Integer>>> ();

        String textStr;
        switch(operation){
            case "all":
                textStr = getAllText();
                break;
            default:
                textStr = new String();
        }

        Scanner sc = new Scanner(textStr);
        List<String> tmpPhrase = new ArrayList<String>();

        while(sc.hasNext()){
            String word = sc.next();
            word = UtilityClass.purifyWord(word);
            
            if(word.length() <= 1) continue;

            boolean isEndPunc = UtilityClass.isPunc(word);
            boolean isStopWord;

            if(isEndPunc){
                word = word.substring(0, word.length()-1);

            }

            isStopWord = stopwords.contains(word);
            boolean stop = isEndPunc || isStopWord;

            if(stop){
                if(isStopWord){
                    if(tmpPhrase.size() != 0){
                        phrases.add(tmpPhrase);
                    }
                }
                else{
                    tmpPhrase.add(word);
                    phrases.add(tmpPhrase);
                }
                tmpPhrase = new ArrayList<String>();
            }
            else{
                tmpPhrase.add(word);
            }
        }
        if(tmpPhrase.size() != 0) phrases.add(tmpPhrase);


        singleWordList = parseSingle(phrases);
        doubleWordList = parseDouble(phrases);
        tripleWordList = parseTriple(phrases);
        res.add(singleWordList);
        res.add(doubleWordList);
        res.add(tripleWordList);
        return res;

    }

    // comparator to sort the list.
    class wordEntryCompare implements Comparator<Map.Entry<String, Integer>>{
        public int compare(Map.Entry<String, Integer> a, Map.Entry<String, Integer> b){
            return b.getValue() - a.getValue();
        }
    }
    // parse the short semantic sentence into one-word + counting, two-word + counting, three-word + counting.
    private List<Map.Entry<String, Integer>>  parseSingle(List<List<String>> phrases){
        List<Map.Entry<String, Integer>> res = new ArrayList<Map.Entry<String, Integer>> ();

        Map<String, Integer> singleWordCount = new HashMap<String, Integer>();
        for(List<String> strList : phrases){
            for(String str : strList){
                if(singleWordCount.containsKey(str)){
                    singleWordCount.put(str, singleWordCount.get(str)+1);
                }
                else{
                    singleWordCount.put(str, 1);
                }
            }
        }
        res = new ArrayList<Map.Entry<String, Integer>>(singleWordCount.entrySet());
        Collections.sort(res, new wordEntryCompare());
        return res;
    }

    private List<Map.Entry<String, Integer>>  parseDouble(List<List<String>> phrases){
        List<Map.Entry<String, Integer>> res = new ArrayList<Map.Entry<String, Integer>>();
        Map<String, Integer> doubleWordCount = new HashMap<String, Integer>();
        for(List<String> strList : phrases){
            if(strList.size() >= 2){
                List<String> doublePhrases = UtilityClass.slideWindow(strList, 2);
                for(String str : doublePhrases){
                    if(doubleWordCount.containsKey(str)){
                        doubleWordCount.put(str, doubleWordCount.get(str)+1);
                    }
                    else{
                        doubleWordCount.put(str, 1);
                    }
                }
            }
        }
        res = new ArrayList<Map.Entry<String, Integer>>(doubleWordCount.entrySet());
        Collections.sort(res, new wordEntryCompare());
        return res;
    }

    private List<Map.Entry<String, Integer>>  parseTriple(List<List<String>> phrases){
        List<Map.Entry<String, Integer>> res = new ArrayList<Map.Entry<String, Integer>> ();
        Map<String, Integer> tripleWordCount = new HashMap<String, Integer>();
        for(List<String> strList : phrases){
            if(strList.size() >= 3){
                List<String> triplePhrases = UtilityClass.slideWindow(strList, 3);
                for(String str : triplePhrases){
                    if(tripleWordCount.containsKey(str)){
                        tripleWordCount.put(str, tripleWordCount.get(str)+1);
                    }
                    else{
                        tripleWordCount.put(str, 1);
                    }
                }
            }
        }
        res = new ArrayList<Map.Entry<String, Integer>>(tripleWordCount.entrySet());
        Collections.sort(res, new wordEntryCompare());
        return res;
    }

    // jsoup get text
    private void getTitleText(){
        StringBuilder res = new StringBuilder();
        res.append(doc.title());
        pageTitle = UtilityClass.purifyString(res.toString());
    }

    private String getAllText(){
        StringBuilder res = new StringBuilder();
        res.append(doc.text());
        return UtilityClass.purifyString(res.toString());
    }
}
