package ltu.m7019e.appt3typewriter;

/**
 * @author:bambanza
 */
public  class Shared1 {
    protected int x=0, y=0;

    public int dif(){
        return x-y;
    }

    public void bump() throws InterruptedException{
        x++;
        Thread.sleep(50);
        y++;
    }
}
