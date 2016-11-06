import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

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
    ArrayList<String> result = new ArrayList<>();
    Random rand = new Random();
    public int counterWriting =0;
    public int counter=0;
    CustomList<String> customList = new CustomList<>(5);
    CustomList<String> customList2= new CustomList<>(5);
    BufferedReader bufferedReader= new BufferedReader(new FileReader("bop.txt"));
    //запись в 1 очередь
    Runnable writeWord = new Runnable() {
        @Override
        public void run() {
            try {
                String oneLine = "";
                while((oneLine=bufferedReader.readLine())!=null){
                    try {
                        customList.addNew(oneLine);
                        counterWriting++;
                        Thread.sleep(rand.nextInt(100));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
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
                    Thread.sleep(rand.nextInt(200));
                    customList2.addNew(customList.remove(customList.size() - 1));
                    //customList.printV();
                    counter++;
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                } catch (java.lang.ArrayIndexOutOfBoundsException e1) {
                    System.err.println("Reading is faster");
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
                    Thread.sleep(rand.nextInt(300));
                    //работа в 2 очереди
                    System.out.println(customList2.get(customList2.size() - 1));
                    result.add(customList2.remove(customList2.size() - 1));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ArrayIndexOutOfBoundsException e1) {
                    System.err.println("Queue2 is empty!");
                }
            }
            showResult();

        }
    };
    public void showResult(){
        for (int i=0;i<result.size();i++){
            System.out.println(result.get(i));
        }
    }
    public Smth3() throws FileNotFoundException {
    }


    Thread finalPart = new Thread(runnable2);
    public static void main(String[] args) throws FileNotFoundException {
        Smth3 smth3 = new Smth3();
        synchronized (smth3.customList) {
            smth3.writing.start();
            synchronized (smth3.customList2) {
                smth3.showing.start();
                smth3.finalPart.start();
            }
        }

    }

}
