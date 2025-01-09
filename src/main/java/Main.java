
import SelfTreeMap.TreeMapImpl;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        Map<Integer, Integer> map = new TreeMap<>();
        map.put(10, 5);
        map.put(5, 5);
        map.put(20, 5);
        map.put(3, 5);
        map.put(7, 5);
        map.put(15, 5);
        map.put(25, 5);
        map.put(26, 5);
        map.put(24, 5);
        map.put(23, 5);
        map.put(22, 5);
        System.out.println(map.size());
        System.out.println(map.containsKey(22));
        map.remove(23);
        System.out.println(map.size());


    }
}
