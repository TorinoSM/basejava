import java.util.Arrays;

public class test {

    public static void main(String[] args) {

        int size = 1;
        String[] storage = {"uuid2", ""};
        System.out.println("source array = " + Arrays.toString(storage));
        String key = "uuid3";
        System.out.println("key = " + key);
        int index = Arrays.binarySearch(storage, 0, size, key);
        System.out.println("raw index = " + index);
        if (index < 0) {
            System.out.println("key not found");
            index = (-index) - 1;
            System.out.println("corrected index = " + index);
            System.arraycopy(storage, index, storage, index + 1, size - index);
            storage[index] = key;
            System.out.println("dest array = " + Arrays.toString(storage));
            size++;
        } else {
            System.out.println("found index = " + index);
        }
    }
}
