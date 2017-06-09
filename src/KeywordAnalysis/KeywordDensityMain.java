package KeywordAnalysis;

import java.awt.*;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * Created by administrator on 6/8/17.
 */
public class KeywordDensityMain {
    Set<String> stopwords = UtilityClass.getStopWord("stopwords.txt");
    //static String url = "https://www.amazon.com/Cuisinart-CPT-122-Compact-2-Slice-Toaster/dp/B009GQ034C/ref=sr_1_1?s=kitchen&ie=UTF8&qid=1431620315&sr=1-1&keywords=toaster";
    private static KeywordParser wordDensity;

    public void connection(String url) {
        try {
            wordDensity = new KeywordParser(stopwords, url);
            wordDensity.parseAll();
        } catch (Exception connectExcept) {
            System.out.println("Wrong URL");
            connectExcept.printStackTrace();

        }
    }

    public void actionParsing(){
        if(wordDensity == null || wordDensity.getData().isEmpty()){
           System.out.println("Please Submit Your Website Address First");
        }

        int countOne = 10, countTwo = 10, countThree = 10;
        for(int i=0; i<3; i++){
            if(i==0 && countOne > 0){
                System.out.println("");
                System.out.println("One word density:");
                System.out.println("");
                System.out.println("");
                countOne--;
            }
            if(i==1 && countTwo > 0){
                System.out.println("");
                System.out.println("Two word density:");
                System.out.println("");
                System.out.println("");
                countTwo--;
            }
            if(i==2 && countThree > 0){
                System.out.println("");
                System.out.println("Three word density:");
                System.out.println("");
                System.out.println("");
                countThree--;
            }
            for(Map.Entry<String, Integer> entry : wordDensity.getData().get("all").get(i)){

                if(i==0 && countOne > 0){

                    System.out.println(" Word: "+entry.getKey()+" Count: "+ entry.getValue());

                    countOne--;
                }
                else if(i==1 && countTwo > 0){

                    System.out.println(" Word: "+entry.getKey()+" Count: "+ entry.getValue());

                    countTwo--;
                }
                else if(i==2 && countThree > 0){

                    System.out.println(" Word: "+entry.getKey()+" Count: "+ entry.getValue());
                    countThree--;
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        KeywordDensityMain wda=new KeywordDensityMain();
        wda.connection(args[0]);
        wda.actionParsing();
    }
}
