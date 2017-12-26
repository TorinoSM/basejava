/**
 * Array based storage for Resumes
 */
public class ArrayStorage {

    // добавить констуктор

    Resume[] storage = new Resume[10000];
    private int lenght = storage.length; // максимальный размер массива
    private int size = 0; // количество непустых резюме в массиве

    void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    void save(Resume r) {
        if (r == null) {
            System.out.println("Resume can't be null");
            return;
        }
        if (size >= lenght) {
            System.out.println("Can't save resume: reached maximum capacity of storage (" + lenght + " records)");
            return;
        }
        storage[size] = r;
        size++;
    }

    Resume get(String uuid) {
        if (uuid.equals("")) {
            System.out.println("Resume's uuid can't be empty");
            return null;
        }
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid == uuid) {
                return storage[i];  // конкретное резюме найдено
            }
        }
        return null;  // конкретное резюме НЕ найдено
    }

    void delete(String uuid) {   // предполагаем, что null-элементов в массиве быть не может
        if (uuid.equals("")) {
            System.out.println("Resume's uuid can't be empty");
            return;
        }
        if (size == 0) {
            System.out.println("Nothing to delete: storage is empty");
        }
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid == uuid) {
                if (size == 1) { // если только один элемент в массиве то очищаем массив
                    clear();
                    return;
                }
                if (i == (size - 1)) {  // если найденный элемент находится к конце массива
                    storage[size - 1] = null; // то обнуляем его
                    size--;
                    return;
                }
                System.arraycopy(storage, i + 1, storage, i, size - i - 1); // сдвинули правую часть массива на один элемент влево, затерев текущий элемент
                storage[size - 1] = null;
                size--;
                return;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {

        Resume[] temp = new Resume[size];
        System.arraycopy(storage, 0, temp, 0, size);
        return temp;
    }

    int size() {
        return size;
    }
}
