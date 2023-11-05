import java.util.Arrays;

public class Radixsort {
  public static int[] countingSort(int[] a, int radix) {
    int maxi = 0;
    int radix10 = radix*10;
    for (int i : a) {
      maxi = Math.max((i % radix10)/radix, maxi);
    }

    int[] c = new int[maxi+1];

    for (int i : a) {
      c[(i % radix10)/radix]++;
    }

    for (int i = 1; i < maxi+1; i++) {
      c[i] += c[i-1];
    }

    int[] b = new int[a.length];

    for (int i = a.length-1; i >= 0; i--) {
      int idx = a[i];
      int ii = --c[(idx % radix10)/radix];
      b[ii] = idx;
    }
    return b;
  }

  public static int findMaxIter(int[] A) {
    int maxi = 0;
    for (int i : A) {
      maxi = Math.max(maxi, String.valueOf(i).length());
    }
    return maxi;
  }

  public static int[] radixSort(int[] A) {
    int maxi = findMaxIter(A);
    for (int i = 0; i < maxi; i++) {
      A = countingSort(A, (int)Math.pow(10, i));
      System.out.println(Arrays.toString(A));
    }
    return A;
  }

  public static void main(String[] args) {
    long startTime = System.nanoTime();
    // long beforeUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
    // int[] A = new int[] {2, 5, 8, 4, 3, 10, 12, 13, 11, 6, 7, 1, 9};
    int[] A = new int[] {329, 457, 657, 839, 436, 720, 355};
		System.out.println("Before: " + Arrays.toString(A));
    
    // int maxi = findMaxIter(A);
    // for (int i = 0; i < maxi; i++) {
    //   A = countingSort(A, (int)Math.pow(10, i));
    // }
    A = radixSort(A);
		System.out.println("After: " + Arrays.toString(A));
    // long actualMemUsed=afterUsedMem-beforeUsedMem;

    // Your code to be measured goes here

    long endTime = System.nanoTime();
    long elapsedTime = endTime - startTime;

    // Convert nanoseconds to milliseconds for a more readable output
    double elapsedTimeInMilliseconds = (double) elapsedTime / 1_000_000.0;
    System.out.println("Execution Time: " + elapsedTimeInMilliseconds + " milliseconds");

  }
}
