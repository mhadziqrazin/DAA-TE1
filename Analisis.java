import java.util.Arrays;
import java.util.Random;


public class Analisis {

  public static void peeksort(int[]A, int l, int r, int e, int s, int[] B) {
		if (e == r || s == l) return;

		int m = l + ((r - l) >> 1);

		if (m <= e) {
			peeksort(A, e+1, r, e+1, s, B);
			merge(A, l, e, r, B);
			// System.out.println("L " + l + " R " + r + " E " + e + " S " + s);
			// System.out.println(Arrays.toString(A));
		} else if (m >= s) {
			peeksort(A, l, s-1, e, s-1, B);
			merge(A, l, s-1, r, B);
			// System.out.println("L " + l + " R " + r + " E " + e + " S " + s);
			// System.out.println(Arrays.toString(A));

		} else {
			int i = extendRunLeft(A, m, l);
			int j = extendRunRight(A, m, r) ;
			if (i == l && j == r) return;
			if (m - i < j - m) {
				peeksort(A, l, i-1, e, i-1, B);
				peeksort(A, i, r, j, s, B);
				merge(A,l, i-1, r, B);
				// System.out.println("L " + l + " R " + r + " E " + e + " S " + s);
				// System.out.println(Arrays.toString(A));

			} else {
				peeksort(A, l, j, e, i, B);
				peeksort(A, j+1, r, j+1, s, B);
				merge(A,l, j, r, B);
				// System.out.println("L " + l + " R " + r + " E " + e + " S " + s);
				// System.out.println(Arrays.toString(A));
			}
		}
	}

	public static void merge(int[]A, int l, int m, int r, int[] B) {
		for (int i = l; i <= r; i++) {
			B[i] = A[i];
		}
		int i = l;
		int j = m+1;
		for (int k = l; k <= r; k++) {
			if (j == r+1 || (i <= m && B[i] < B[j])) {
				A[k] = B[i++];
			} else {
				A[k] = B[j++];
			}
		}
	}

	public static int extendRunLeft(int[] A, int i, int l) {
		while (i > l && A[i-1] < A[i]) --i;
		return i;
	}

	public static int extendRunRight(int[] A, int i, int r) {
		while (i < r && A[i+1] > A[i]) ++i;
		return i;
	}


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

  public static int[] radixCountingSort(int[] A) {
    int maxi = findMaxIter(A);
    for (int i = 0; i < maxi; i++) {
      A = countingSort(A, (int)Math.pow(10, i));
    }
    return A;
  }

  public static int[] generateRandomArray(int N, int min, int max) {
    int[] array = new int[N];
    Random random = new Random();

    for (int i = 0; i < N; i++) {
        array[i] = random.nextInt((max - min) + 1) + min;
    }

    return array;
  }

  public static void main(String[] args) {
    int[] N = new int[]{1_000, 10_000, 100_000};
    int min = 1;
    int max = 1_000_000_000;

    for (int i = 0; i < 3; i++) {
      if (i == 0) System.out.println("Sorted:");
      if (i == 1) System.out.println("Random:");
      if (i == 2) System.out.println("Reversed:");

      for (int k = 0; k < 3; k++) {
        if (k == 0) System.out.println("Dataset 1.000");
        if (k == 1) System.out.println("Dataset 10.000");
        if (k == 2) System.out.println("Dataset 100.000");
        int[] testArray = generateRandomArray(N[k], min, max);
        if (i == 0) {
          // status sorted
          Arrays.sort(testArray);
        } else if (i == 2) {
          // status reversed
          Arrays.sort(testArray);
          int[] temp = new int[testArray.length];
          for (int j = testArray.length - 1; j >= 0; j--) {
            temp[testArray.length - j - 1] = testArray[j];
          }
        }

        long memoryBefore = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        long startTime = System.nanoTime();

        int[] radixSorted = radixCountingSort(testArray);
        long endTime = System.nanoTime();
        long memoryAfter = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        double elapsedTimeInMilliseconds = (endTime - startTime) / 1_000_000.0;
        System.gc();

        System.out.println("[" + (i+1) + "]" + "Time taken radix counting sort: " + elapsedTimeInMilliseconds + " ms");
        System.out.println("[" + (i+1) + "]" + "Memory used radix counting sort: " + (memoryAfter - memoryBefore) + " bytes");

        memoryBefore = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        startTime = System.nanoTime();

        peeksort(testArray, 0, testArray.length-1, 0, testArray.length-1, new int[testArray.length]);
        endTime = System.nanoTime();
        memoryAfter = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        elapsedTimeInMilliseconds = (endTime - startTime) / 1_000_000.0;
        System.gc();
        System.out.println("[" + (i+1) + "]" + "Time taken peeksort: " + elapsedTimeInMilliseconds + " ms");
        System.out.println("[" + (i+1) + "]" + "Memory used peeksort: " + (memoryAfter - memoryBefore) + " bytes");

        boolean radixsortSorted = true;
        boolean peeksortSorted = true;
        for (int j = 0; j < radixSorted.length-1; j++) {
          if (radixSorted[j] > radixSorted[j+1]) {
            radixsortSorted = false;
          }
          if (testArray[i] > testArray[i+1]) {
            peeksortSorted = false;
          }
        }

        System.out.println("[" + (i+1) + "]" + "Radix Sort valid: " + radixsortSorted);
        System.out.println("[" + (i+1) + "]" + "Peek Sort valid: " + peeksortSorted);
        System.out.println();
      }
      System.out.println("-------------------");
    }
  }
}
