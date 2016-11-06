import com.sun.javafx.collections.MappingChange;

import java.io.*;
import java.util.*;

/**
 * Created by Александра on 05.11.2016.
 */
class CustomList<T> extends ArrayList<T> {
    private final int size;
    CustomList(int count){
        super(count);
        this.size=count;
    }
    public void printV(){
        System.out.println("---------------------");
        for (int i=0;i<this.size();i++){
            System.out.println(this.get(i));
        }
    }
    public void addNew(T object){
        if(this.size()==size){
            this.remove(this.size()-1);
        }
        this.add(0, object);
    }
}
public class Smth3 {
    static HashMap<Integer,Integer> loopStatistics = new HashMap<>();
    int originalLength=0;
    private int textLength=0;
    private int countOfDigits=0;
    private int countOfLowerLetters =0;
    private int countOfUpperLetters=0;
    private int countOfOtherSymbols;
    private List<Character> letters = new ArrayList<>();
    private ArrayList<String> result = new ArrayList<>();
    //Random rand = new Random();
    public int counterWriting =0;
    public int counter=0;
    private CustomList<String> customList = new CustomList<>(5);
    private CustomList<String> customList2= new CustomList<>(5);
    private BufferedReader bufferedReader= new BufferedReader(new FileReader("bop.txt"));
    //запись в 1 очередь
    Runnable writeWord = new Runnable() {
        @Override
        public void run() {
            try {
//                synchronized (customList) {


                String oneLine = "";

                while((oneLine=bufferedReader.readLine())!=null){
                    Thread.sleep(1);
                        originalLength++;
                        customList.addNew(oneLine);
                        counterWriting++;
//                    Thread.sleep(1);
                    }
//                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
    Thread writing= new Thread(writeWord);
    //работа с 1 очередью
    Runnable readWord = new Runnable() {
        @Override
        public void run() {
            while (writing.isAlive() || !customList.isEmpty()) {
                try {
                    String row = customList.remove(customList.size() - 1);
                    getStatistics(row);
                    row=deleteExcess(row);
                    customList2.addNew(row);
                    //customList.printV();
                    counter++;
                } catch (java.lang.ArrayIndexOutOfBoundsException e1) {
                    // System.err.println("Reading is faster");
                }

            }
        }
    };
    Thread showing = new Thread(readWord);
    Runnable runnable2 = new Runnable() {
        @Override
        public void run() {
            while(showing.isAlive()||!customList2.isEmpty()) {
                try {
                    //работа в 2 очереди
                    //Thread.sleep(0);
                    result.add(customList2.remove(customList2.size() - 1));
                } catch (ArrayIndexOutOfBoundsException e1) {
                    //System.err.println("Queue2 is empty!");
                }
            }

        }
    };
    public void showResult(){
        for (int i=0;i<result.size();i++){
            System.out.println(result.get(i));
        }
    }
    public Smth3() throws FileNotFoundException {
        for(char c='a';c<='z';c++){
            letters.add(c);
        }
        for(char c='A';c<='Z';c++){
            letters.add(c);
        }
        for(char c='0';c<='9';c++){
            letters.add(c);
        }
        letters.add(' ');
    }

    Thread finalPart = new Thread(runnable2);
    public void writeResult() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("result1.txt");
        for(int i=0;i<result.size();i++) {
            fileOutputStream.write((result.get(i)+'\n').getBytes());
        }
    }
    public void getStatistics(String row){
        for(int i=0;i<row.length();i++){
            char ch = row.charAt(i);
            textLength++;
            if(ch>='a'&&ch<='z'){
                countOfLowerLetters++;
            }else if(ch>='A'&&ch<='Z'){
                countOfUpperLetters++;
            }else if(ch>='0'&&ch<='9'){
                countOfDigits++;
            }else{
                countOfOtherSymbols++;
            }
        }
    }
    public String deleteExcess(String originalString){
        String result = "";
        for(int i=0;i<originalString.length();i++){
            char currentCh=originalString.charAt(i);
            for(int y=0;y<letters.size();y++){
                if (letters.get(y).equals(currentCh)){
                    result+=currentCh;
                }
            }
        }
        return result;
    }
    public  void oneLoop() throws InterruptedException, IOException {
        synchronized (customList) {
            writing.start();
            synchronized (customList2) {
                showing.start();
                finalPart.start();

            }
            writing.join();
            showing.join();
            finalPart.join();
        }
        writeResult();
        if(loopStatistics.containsKey(result.size())){
            int count = loopStatistics.get(result.size())+1;
            loopStatistics.put(result.size(),count);
        }else{
            loopStatistics.put(result.size(),1);
        }
        Thread.sleep(100);

    }
    public static void main(String[] args) throws IOException, InterruptedException {
            for(int i=0;i<100;i++) {
                Smth3 smth3 = new Smth3();
                smth3.oneLoop();

            }

//        Smth3 smth4 = new Smth3();
//        smth4.oneLoop();
//        Smth3 smth5 = new Smth3();
//        smth5.oneLoop();
//        Smth3 smth6 = new Smth3();
//        smth6.oneLoop();
//        Smth3 smth7 = new Smth3();
//        smth7.oneLoop();
//        Smth3 smth8 = new Smth3();
//        smth8.oneLoop();
//        Smth3 smth9 = new Smth3();
//        smth9.oneLoop();
//        Smth3 smth10 = new Smth3();
//        smth10.oneLoop();
        Set<Integer> keys = loopStatistics.keySet();
        for(int key:keys){
            System.out.println("size: "+key+"; count: "+loopStatistics.get(key));

        }

    }


}
//private char[] letters = new char[Arrays.asList({'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H',I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,1,2,3,4,5,6,7,8,9,0})]
