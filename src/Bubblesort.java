public class Bubblesort {
    int smaller;
    int bigger;
    boolean run = true;

    public int[][] sort(int unsorted[][]) {
        System.out.println(unsorted[0].length + "adsf");
        for (int j = 0; j < unsorted[0].length; j++) {
            for (
                    int i = 0;
                    i < unsorted.length && run == true; i++) {
                run = false;

                for (int y = 0; y < unsorted.length - 1; y++) {
                    if (unsorted[y][j] > unsorted[y + 1][j]) {
                        bigger = unsorted[y][j];
                        smaller = unsorted[y + 1][j];
                        unsorted[y][j] = smaller;
                        unsorted[y + 1][j] = bigger;
                        run = true;
                    }
                }
            }
        }

        return unsorted;
    }
}
