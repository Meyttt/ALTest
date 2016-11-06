/**
 * Created by Александра on 05.11.2016.
 */
public class Competition {
    Runnable run1 = new Runnable() {
        @Override
        public void run() {
            for(int i=0; i<8;i++){
                System.out.println("Participant1");
                try{
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    Thread participant1 = new Thread(run1);
    Runnable run2 = new Runnable() {
        @Override
        public void run() {
            for(int i=0; i<8;i++){
                System.out.println("Participant2");
                try{
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    Thread participant2 = new Thread(run2);

    public static void main(String[] args) {
        Competition competition= new Competition();
        competition.participant2.start();
        competition.participant1.start();
    }
}
