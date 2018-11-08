import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

public class Sort {

    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array nor comparator can be "
                    + "null. Please insert non-null comparator and array.");
        }

        boolean swapped = true;
        boolean upDown = true;
        int lastUp = 0;
        int lastDown = arr.length - 1;
        int finalSwapInd;

        while (swapped) {
            swapped = false;
            if (upDown) {
                upDown = false;
                finalSwapInd = lastDown - 1;
                for (int i = lastUp; i < lastDown; i++) {
                    if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                        T data = arr[i];
                        arr[i] = arr[i + 1];
                        arr[i + 1] = data;
                        finalSwapInd = i;
                        swapped = true;
                    }
                }
                lastDown = finalSwapInd;
            } else {
                upDown = true;
                finalSwapInd = lastUp + 1;
                for (int i = lastDown; i > lastUp; i--) {
                    if (comparator.compare(arr[i - 1], arr[i]) > 0) {
                        T data = arr[i - 1];
                        arr[i - 1] = arr[i];
                        arr[i] = data;
                        finalSwapInd = i;
                        swapped = true;
                    }
                }
                lastUp = finalSwapInd;
            }
        }
    }

    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array nor comparator can be "
                    + "null. Please insert non-null comparator and array.");
        }
        for (int i = 1; i < arr.length; i++) {
            int currInd = i;
            while (currInd > 0 && comparator.compare(arr[currInd],
                    arr[currInd - 1]) < 0) {
                T data = arr[currInd - 1];
                arr[currInd - 1] = arr[currInd];
                arr[currInd] = data;
                currInd--;
            }
        }
    }

    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array nor comparator can be "
                    + "null. Please insert non-null comparator and array.");
        }
        for (int i = 0; i < arr.length - 1; i++) {
            int minInd = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (comparator.compare(arr[j], arr[minInd]) < 0) {
                    minInd = j;
                }
            }
            T data = arr[minInd];
            arr[minInd] = arr[i];
            arr[i] = data;
        }
    }


    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array nor comparator can be "
                    + "null. Please insert non-null comparator and array.");
        }

        if (arr.length <= 1) {
            return;
        }

        int midInd = arr.length / 2;

        T[] leftArr = (T[]) new Object[arr.length / 2];
        T[] rightArr = (T[]) new Object[arr.length - (arr.length / 2)];

        for (int i = 0; i < midInd; i++) {
            leftArr[i] = arr[i];
        }
        for (int i = midInd; i < arr.length; i++) {
            rightArr[i - midInd] = arr[i];
        }

        mergeSort(leftArr, comparator);
        mergeSort(rightArr, comparator);

        int leftInd = 0;
        int rightInd = 0;
        int currInd = 0;

        while (leftInd < leftArr.length && rightInd < rightArr.length) {
            if (comparator.compare(leftArr[leftInd], rightArr[rightInd]) <= 0) {
                arr[currInd] = leftArr[leftInd];
                leftInd++;
            } else {
                arr[currInd] = rightArr[rightInd];
                rightInd++;
            }
            currInd++;
        }

        if (leftInd < leftArr.length) {
            for (int i = leftInd; i < leftArr.length; i++) {
                arr[currInd] = leftArr[i];
                currInd++;
            }
        } else if (rightInd < rightArr.length) {
            for (int i = rightInd; i < rightArr.length; i++) {
                arr[currInd] = rightArr[i];
                currInd++;
            }
        }
    }

    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Cannot sort null array. "
                    + "Please input non-null array.");
        }

        LinkedList<Integer>[] counter;
        counter = (LinkedList<Integer>[]) new LinkedList[19];
        for (int i = 0; i < 19; i++) {
            counter[i] = new LinkedList<>();
        }
        int mod = 10;
        int dev = 1;
        boolean cont = true;
        while (cont) {
            cont = false;
            for (int i = 0; i < arr.length; i++) {
                int beforeMod = arr[i] / dev;
                if (beforeMod != 0) {
                    cont = true;
                }
                int bucket = beforeMod % mod + 9;
                counter[bucket].add(arr[i]);
            }
            int idx = 0;
            for (int i = 0; i < counter.length; i++) {
                while (!counter[i].isEmpty()) {
                    arr[idx] = counter[i].remove();
                    idx++;
                }
            }
            dev = dev * 10;
        }
    }


    public static <T> T kthSelect(int k, T[] arr, Comparator<T> comparator,
                                  Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("Array nor comparator nor "
                    + "random object "
                    + "can be "
                    + "null. Please insert non-null comparator, array and "
                    + "random object"
                    + ".");
        }
        if (k < 1 || k > arr.length) {
            throw new IllegalArgumentException("Inputted k is not in the "
                    + "range of 1 to array.length. Please insert k in proper "
                    + "range.");
        }

        return kthSelectHelper(k, arr, comparator, rand, 0, arr.length - 1);
    }

    private static <T> T kthSelectHelper(int k, T[] arr,
                                        Comparator<T> comparator,
                                   Random rand, int start, int end) {
        int pivotInd = rand.nextInt(end - start + 1) + start;
        T pivot = arr[pivotInd];
        arr[pivotInd] = arr[start];
        arr[start] = arr[pivotInd];
        int i = start + 1;
        int j = end;
        while (i <= j) {
            while (i < j && comparator.compare(pivot, arr[i]) >= 0) {
                i++;
            }
            while (j >= i && comparator.compare(pivot, arr[j]) <= 0) {
                j--;
            }
            if (i == j) {
                i++;
            }
            if (i < j) {
                T data = arr[j];
                arr[j] = arr[i];
                arr[i] = data;
                i++;
                j--;
            }
        }
        arr[start] = arr[j];
        arr[j] = pivot;
        if (j == k - 1) {
            return arr[j];
        } else if (j > k - 1) {
            return kthSelectHelper(k, arr, comparator, rand, start, j - 1);
        } else {
            return kthSelectHelper(k, arr, comparator, rand, j + 1, end);
        }
    }
}
