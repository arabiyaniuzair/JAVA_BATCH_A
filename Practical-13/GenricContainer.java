class Box<T> {
    private T value;

    public void set(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }
}

class Pair<A, B> {
    A first;
    B second;

    Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    void display() {
        System.out.println(first + " " + second);
    }
}

public class GenricContainer{
    public static <T> T first(T[] array) {
        return array[0];
    }

    public static void main(String[] args) {
        Box<Integer> b1 = new Box<>();
        b1.set(100);
        System.out.println(b1.get());

        Box<String> b2 = new Box<>();
        b2.set("Hello");
        System.out.println(b2.get());

        Pair<String, Integer> p1 = new Pair<>("Uzair", 20);
        p1.display();

        Integer[] numbers = {10, 20, 30};
        String[] names = {"Java", "Python", "C++"};

        System.out.println(first(numbers));
        System.out.println(first(names));
    }
}