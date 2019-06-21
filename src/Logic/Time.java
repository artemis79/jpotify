package Logic;

public class Time {
    private int minute;
    private int second;

    public Time (int minute , int second){
        this.minute = minute;
        this.second = second;
    }

    public void increment (){
        second++;
        if (second == 60){
            minute++;
            second = 0;
        }
    }

    public int getMinute (){
        return minute;
    }

    public int getSecond (){
        return second;
    }

    public void setSecond (int second){
        this.second = second;
    }

    public void setMinute (int minute){
        this.minute = minute;
    }

    @Override
    public String toString (){
        if (second < 10 )
            return " " + minute + " : 0" + second + " ";
        else
            return " " + minute + " : " + second + " ";
    }
}
