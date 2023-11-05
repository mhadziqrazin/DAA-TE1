import java.util.Arrays;

public class Peeksort {

	public static void peeksort(int[]A, int l, int r, int e, int s, int[] B) {
		if (e == r || s == l) return;

		int m = l + ((r - l) >> 1);
		
		if (m <= e) {
			peeksort(A, e+1, r, e+1, s, B);
			merge(A, l, e, r, B);
			System.out.println("L " + l + " R " + r + " E " + e + " S " + s);
			System.out.println(Arrays.toString(A));
			System.out.println();
		} else if (m >= s) {
			peeksort(A, l, s-1, e, s-1, B);
			merge(A, l, s-1, r, B);
			System.out.println("L " + l + " R " + r + " E " + e + " S " + s);
			System.out.println(Arrays.toString(A));
			System.out.println();

		} else {
			int i = extendRunLeft(A, m, l);
			int j = extendRunRight(A, m, r) ;
			if (i == l && j == r) return;
			if (m - i < j - m) {
				// |XX     x|xxxx   X|
				peeksort(A, l, i-1, e, i-1, B);
				peeksort(A, i, r, j, s, B);
				merge(A,l, i-1, r, B);
				System.out.println("L " + l + " R " + r + " E " + e + " S " + s);
				System.out.println(Arrays.toString(A));
				System.out.println();

			} else {
				// |XX   xxx|x      X|
				peeksort(A, l, j, e, i, B);
				peeksort(A, j+1, r, j+1, s, B);
				merge(A,l, j, r, B);
				System.out.println("L " + l + " R " + r + " E " + e + " S " + s);
				System.out.println(Arrays.toString(A));
				System.out.println();
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

	public static int extendStrictlyDecreasingRunLeft(int[] A, int i, int l) {
		while (i > l && A[i-1] > A[i]) --i;
		return i;
	}

	public static int extendStrictlyDecreasingRunRight(int[] A, int i, int r) {
		while (i < r && A[i+1] < A[i]) ++i;
		return i;
	}

	public static void main(String[] args) {
		int[] A = new int[] {2, 5, 8, 4, 3, 10, 12, 13, 11, 6, 7, 1, 9};
		System.out.println(Arrays.toString(A));
		System.out.println();
		peeksort(A, 0, A.length-1, 0, A.length-1, new int[A.length]);
		System.out.println(Arrays.toString(A));
	}
}