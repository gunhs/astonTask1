import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Мой ArrayList")
public class TestClass {
    MyArrayList<String> myArrayList;

    @BeforeEach
    void clean() {
        myArrayList = new MyArrayList<>();
        myArrayList.add("Первое");
    }

    @Test
    @DisplayName("Проверяется метод вставки  и получения значения")
    void addAndGetTest() {
        Assertions.assertEquals("Первое", myArrayList.get(0));
    }

    @Test
    @DisplayName("Проверяется метод вставки с указанием индекса")
    void addWithIndexTest() {
        myArrayList.add("Второе", 0);
        Assertions.assertEquals("Второе", myArrayList.get(0));
    }

    @Test
    @DisplayName("Проверяется метод замены элемента")
    void setTest() {
        myArrayList.set("Третье", 0);
        Assertions.assertEquals("Третье", myArrayList.get(0));
    }

    @Test
    @DisplayName("Проверяется метод наличия элемента")
    void containsTest() {
        myArrayList.add("Третье");
        Assertions.assertTrue(myArrayList.contains("Третье"));
    }

    @Test
    @DisplayName("Проверяется метод удаления первого совпадающего элемента")
    void removeFirstTest() {
        myArrayList.add("Второе");
        myArrayList.add("Первое");
        myArrayList.remove("Первое");
        Assertions.assertEquals("Второе", myArrayList.get(0));
        Assertions.assertTrue(myArrayList.contains("Первое"));
    }

    @Test
    @DisplayName("Проверяется метод сортировки и метод equals")
    void sortTest() {
        myArrayList.add("А");
        myArrayList.add("Б");
        myArrayList.add("Г");
        myArrayList.add("В");
        myArrayList.sort();
        MyArrayList<String> newArrayList = new MyArrayList<>();
        newArrayList.add("А");
        newArrayList.add("Б");
        newArrayList.add("В");
        newArrayList.add("Г");
        newArrayList.add("Первое");
        Assertions.assertTrue(myArrayList.equals(newArrayList));
    }

    @Test
    @DisplayName("Проверяется метод проверки пустого списка")
    void isEmptyTest() {
        myArrayList.remove("Первое");
        Assertions.assertTrue(myArrayList.isEmpty());
    }

    @Test
    @DisplayName("Проверяется метод проверки очистки списка")
    void clearTest() {
        myArrayList.clear();
        Assertions.assertTrue(myArrayList.isEmpty());
    }

    @Test
    @DisplayName("Проверяется метод возвращаюего размер списка")
    void sizeTest() {
        Assertions.assertEquals(myArrayList.size(), 1);
        myArrayList.clear();
        Assertions.assertEquals(myArrayList.size(), 0);
        for (int i = 0; i < 40; i++) {
            myArrayList.add(String.valueOf(i));
        }
        Assertions.assertEquals(myArrayList.size(), 40);
    }

}
