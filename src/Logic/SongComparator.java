package Logic;

import java.util.Comparator;

public class SongComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {

        Song s1 = null , s2 = null;

        if (o1 instanceof Song && o2 instanceof Song){

            s1=(Song)o1;
            s2=(Song)o2;
        }
        return Integer.compare(s1.getNum(), s2.getNum());

    }
}
