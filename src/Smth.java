import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Александра on 05.11.2016.
 */
public class Smth {
    public static int c =0;
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String> arrayList = new ArrayList<>(5);
        BufferedReader bufferedReader= new BufferedReader(new FileReader("bop.txt"));
        Runnable runnable2= new Runnable() {
            @Override
            public void run() {
                if (arrayList.size() > 0) {
                    System.out.println(arrayList.remove(arrayList.size() - 1));
                    c++;
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread thread2= new Thread(runnable2);
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        String nextLine = bufferedReader.readLine();
                        if (nextLine != null) {
                            arrayList.add(nextLine);
                            Thread.sleep(50);

                        }else{
                            break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };


        for(int i=0;i<30;i++){
            Thread thread1= new Thread(runnable1);
            thread1.run();

        }
        System.out.println(c);
    }
}
