import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Created by Александра on 05.11.2016.
 */
class CustomList1<T> extends ArrayList<T>{
    private final int size;
    CustomList1(int count){
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
public class Smth2{
    CustomList1<String> list= new CustomList1(5);
    BufferedReader bufferedReader= new BufferedReader(new FileReader("bop.txt"));
    public Smth2() throws FileNotFoundException {
    }
    public void printV(){
        list.printV();
    }
    public void add(String string){
        list.addNew(string);
    }

}
