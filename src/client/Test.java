package client;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Test {
    public static void main(String[] args) {
        List list = new ArrayList();

        list.add("Hello");

        list.add("Learn");

        list.add("Hello");

        list.add("Welcome");

        Set set = new HashSet();

        set.addAll(list);

        for (Object o : set) {
            System.out.println(o.toString());
        }

        System.out.println(set.size());
    }

    public float aMethod(float a,float b) {

        return a+b;
    }

    public int aMethod(int a,int b) {

        return a+b;
    }

}

class A {
    public Test t = new Test();
}
