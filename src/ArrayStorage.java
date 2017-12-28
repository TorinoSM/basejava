import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {

    // добавить констуктор

    Resume[] storage = new Resume[10000];
    private int lenght = storage.length; // максимальный размер массива
    private int size = 0; // количество непустых резюме в массиве

    public void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    public void update(Resume updatedResume) {
        if (updatedResume == null) {
            System.out.println("Error: Update: Cannot update with resume which is null");
        }
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(updatedResume.uuid)) {
                storage[i] = updatedResume;
                System.out.println("Success: Update: Resume with uuid = \"" + storage[i].uuid + "\" is updated");
                return;
            }
        }
        System.out.println("Warning: Update: Couldn't find resume with uuid = \"" + updatedResume.uuid + "\"");
    }

    public void save(Resume r) {
        if (r == null) {
            System.out.println("Error: Save: Resume can't be null");
            return;
        }
        if (size >= lenght) {
            System.out.println("Error: Save: Can't save resume: reached maximum capacity of storage (" + lenght + " records)");
            return;
        }
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(r.uuid)) {
                System.out.println("Warning: Save: Resume with uuid = \"" + r.uuid + "\" already exists");
                return;
            }
        }
        storage[size] = r;
        size++;
        System.out.println("Success: Save: Saved new resume with uuid = \"" + r.uuid + "\"");
    }

    public Resume get(String uuid) {
        if ("".equals(uuid)) {
            System.out.println("Error: Get: Resume's uuid can't be empty");
            return null;
        }
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid == uuid) {
                System.out.println("Success: Get: Found resume with uuid = \"" + uuid + "\"");
                return storage[i];  // конкретное резюме найдено
            }
        }
        System.out.println("Warning: Get: Resume with uuid = \"" + uuid + "\" did not found");
        return null;  // конкретное резюме НЕ найдено
    }

    public void delete(String uuid) {   // предполагаем, что null-элементов в массиве быть не может
        if ("".equals(uuid)) {
            System.out.println("Warning: Delete: Resume's uuid can't be empty");
            return;
        }
        if (size == 0) {
            System.out.println("Warning: Delete: Nothing to delete: storage is empty");
            return;
        }
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                storage[i] = storage[size - 1]; // moved the last element into the deleted one
                storage[size - 1] = null;
                size--;
                System.out.println("Success: Delete: Deleted resume with uuid = \"" + uuid + "\"");
                return;
            }
        }
        System.out.println("Warning: Delete: Resume with uuid = \"" + uuid + "\" did not found");
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int size() {
        return size;
    }
}
