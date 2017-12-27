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

    void update(Resume oldResume, Resume updatedResume) {
        if(updatedResume==oldResume){
            System.out.println("Error: Update: Resumes can't be the same");
            return;
        }
        if (oldResume == null) {
            System.out.println("Error: Update: Resume to be replaced can't be null");
            return;
        }
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid == updatedResume.uuid) {
                System.out.println("Warning: Update: Resume with uuid = " + storage[i].uuid + " already exists");
                return;
            }
        }
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid == oldResume.uuid) {
                if (updatedResume != null) {
                    System.out.println("Success: Update: Resume with uuid = " + oldResume.uuid + " replaced with resume with uuid = " + updatedResume.uuid);
                }
                if (updatedResume == null) {
                    delete(storage[i].uuid); // замена на null-резюме равносильна удалению этого резюме из базы
                    return;
                }
                storage[i] = updatedResume;
            }
        }
        System.out.println("Warning: Update: Resume with uuid = " + oldResume.uuid + " did not found");
    }

    void save(Resume r) {
        if (r == null) {
            System.out.println("Error: Save: Resume can't be null");
            return;
        }
        if (size >= lenght) {
            System.out.println("Error: Save: Can't save resume: reached maximum capacity of storage (" + lenght + " records)");
            return;
        }
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid == r.uuid) {
                System.out.println("Warning: Save: Resume with uuid = " + r.uuid + " already exists");
            }
        }
        storage[size] = r;
        size++;
        System.out.println("Success: Save: Saved new resume with uuid = " + r.uuid);
    }

    Resume get(String uuid) {
        if ("".equals(uuid)) {
            System.out.println("Error: Get: Resume's uuid can't be empty");
            return null;
        }
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid == uuid) {
                System.out.println("Success: Get: Found resume with uuid = " + uuid);
                return storage[i];  // конкретное резюме найдено
            }
        }
        System.out.println("Warning: Get: Resume with uuid = " + uuid + " did not found");
        return null;  // конкретное резюме НЕ найдено
    }

    void delete(String uuid) {   // предполагаем, что null-элементов в массиве быть не может
        if ("".equals(uuid)) {
            System.out.println("Warning: Delete: Resume's uuid can't be empty");
            return;
        }
        if (size == 0) {
            System.out.println("Warning: Delete: Nothing to delete: storage is empty");
            return;
        }
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid == uuid) {
                System.out.println("Success: Delete: Deleted resume with uuid = " + uuid);
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
            System.out.println("Warning: Delete: Resume with uuid = " + uuid + " did not found");
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
