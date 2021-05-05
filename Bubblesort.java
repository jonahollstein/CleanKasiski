/*
*Bubblesort
*/

public class Bubblesort {
    int smaller; //smaller number of comparison
    int bigger; //bigger number of comparison
    
    /*
    *@param int [][] unsorted
    *two-dimensional int array of unsorted int
    */
    
    
    public int[][] sort(int[][] unsorted) {
        for (int j = 0; j < unsorted[0].length; j++) {
            for (int i = 0; i < unsorted.length; i++) {
                for (int y = 0; y < unsorted.length - 1; y++) {
                    if (unsorted[y][j] > unsorted[y + 1][j]) {
                        bigger = unsorted[y][j];
                        smaller = unsorted[y + 1][j];
                        unsorted[y][j] = smaller;
                        unsorted[y + 1][j] = bigger;
                    }
                }
            }
        }

        return unsorted;
    }
}
