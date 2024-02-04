import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        MyArrayList<String> myArrayList = new MyArrayList<>();
        myArrayList.add("Раз");
        myArrayList.add("Два");
        myArrayList.add(null);
        myArrayList.remove("Три");
        myArrayList.remove(null);
        myArrayList.forEach(System.out::println);
        ArrayList<String> list = new ArrayList<>();
        list.add("GGG");
        list.add(null);
        list.remove(null);
        list.forEach(System.out::println);
    }
}
