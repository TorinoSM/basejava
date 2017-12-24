/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int size = 0;

    void clear() {
        for (int i = 0; i < storage.length; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    void save(Resume r) {
        storage[size++] = r;
    }

    Resume get(String uuid) {
        for (Resume r : storage) {
            if (r == null) continue;
            if (r.uuid == uuid) {
                return r;
            }
        }
        return null;
    }

    void delete(String uuid) {
        int ind = 0;
        for (Resume r : storage) {
            if (r == null) {
                ind++;
                continue;
            }
            if (r.uuid == uuid) {
                storage[ind] = null;
            }
            ind++;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] temp = new Resume[storage.length];
        int ind = 0;
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null) {
                temp[ind++] = storage[i];
            }
        }
        Resume[] temp2 = new Resume[ind];
        for (int i = 0; i < ind; i++) {
            temp2[i] = temp[i];
        }
        return temp2;
    }

    int size() {
        return size;
    }
}
