import java.util.ArrayList;
import java.util.Arrays;

public class CleanKasiski3 {

    //public static Bubblesort sort = new Bubblesort();


    // Bestimmen der Schlüsselwortlänge vom String des verschlüsselten Texts
    public static ArrayList<Integer> findRepeats(String cipher, int min, int max) {
        ArrayList<Integer> distance = new ArrayList<>();     // initalisieren und definieren einer neuen ArrayList als Integer-Typ
        //cipher = cipher.toUpperCase();

        for (int l = min; l < max; l++) {                                 // Hochzählen einer for-Schleife, wobei die Länge l variiert für die gesuchten Buchstabenketten mit den Längen 4-10
            for (int spos = 0; spos < cipher.length() - l + 1; spos++) {      // Hochzählen einer for-Schleife für die Länge des Strings
                String s = cipher.substring(spos, spos + l);
                // Herausfiltern der gef. Buchstabenketten
                for (int i = spos + l; i < cipher.length(); i++) {         // Herausfinden der Abstände für Buchstabenwiederholungen mit Hochzählen einer for-Schleife
                    if (cipher.startsWith(s, i)) {
                        if (!distance.contains(i - spos)) {
                            distance.add(i - spos);
                        }
                    }
                }
            }
        }
        return distance;
    }


    // Aus den oben bestimmten Wiederholungen die Schlüssellänge ermitteln
    public static int calcKeywordlength(ArrayList<Integer> distance) {
        int ggt = distance.get(0);
        for (int i = 1; i < distance.size(); i++) {     // "ggt"(gemeinsamer Teiler) aller gefundener Abstände
            ggt = getGGT(ggt, distance.get(i));
        }
        return ggt;
    }

    // Ermitteln des größten gemeinsamen Teilers aus a und b
    public static int getGGT(int a, int b) {
        if (b == 0) {
            return a;
        }
        if (b > a) {
            return getGGT(b, a);
        }
        return getGGT(a % b, b);
    }

    // Die Buchstabenhäufigkeiten vom Typ double runden
    /*public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }*/


    // Den Text in Blöcke entsprechend der Schlüssellänge unterteilen
    /*public static String divideString(String cipher, int keywordlength) {
        int p = 0;
        String divide = "";

        for (int i = 0; i < cipher.length() / keywordlength; i++) {
            String s = cipher.substring(p, p + keywordlength);
            divide += s += " ";

            p += keywordlength;
        }

        return divide;
    }*/

    public static int[][] calcFreqAbs(String cipher, int keywordlength) {
        int[][] freqAbs = new int[26][keywordlength];

        for (int keypos = 0; keypos < keywordlength; keypos++) {
            for (int i = keypos; i < cipher.length(); i += keywordlength) {
                freqAbs[(cipher.charAt(i) - 65)][keypos]++;
            }
        }

        return freqAbs;
    }

    public static double[][] calcFreqRel(int[][] freqAbs, String cipher, int keywordlength){

        //Für Später:

        /*int [][] copy = new int[freqAbs.length][];
        for (int i = 0; i < freqAbs.length; i++) {
            copy[i] = Arrays.copyOf(freqAbs[i], freqAbs[i].length);
        }

        int[][] freqAbsSort = sort.sort(copy);*/


        double block = ((double)cipher.length()/keywordlength);

        double[][] freqRel = new double[26][freqAbs[0].length];
        for (int i=0; i<freqRel.length; i++) {
            for(int j=0; j<freqRel[0].length; j++){
                freqRel[i][j] = Math.round(((double)freqAbs[i][j]/block*100)*100.0)/100.0;
            }
        }
        return freqRel ;
    }

    // Häufkeitsverteilung printen
    public static void printFreq(double[] language) {
        System.out.print("\n" + "Häufigkeit: | ");
        for (double v : language) {
            System.out.print(v + " | ");
        }

        /* 'Enhanced' foreach-Schleife

        for (int i = 0; i < language.length; i++) {
            System.out.print(language[i] + " | ");
        }*/

        System.out.print("\n" + "Zeichen:    |   ");

        for (char c = 'A'; c <= 'Z'; c++) {
            System.out.print(c + "  |   ");
        }
    }

    public static void main(String[] args) {
        String cipher = "PWTMYTBADKDGPWPFYWFGUESOTLUPNVYWAPKCSOOJWWASTLSUZUSJMJBBRSTIMGPYSXOJWWASMMZQLCHJQWGYDHKOJWWASTMFPADWIPVKLHONZWPDPWRAAGQPRKNJCNPKGPJJLTHYOWOHPGYJWCUEKUZLGAOWKHOGPESMZMRWPBKVFVZTQNLAGSFSMVWTDPWRAAGQPRKNJCNPTGTKEOMSGVLYVCHKBVKLOFOBLGNCIVXWPLYBZAAEOOWKEWEODZKZOGPWGOMSWMPWTIFFLCTUTYGUOSLZSILYOHEWEODSRVVYHSFAVVHHWGIPTGHYHCWJVLERGJWKPDHGJWTUTQNBXGZEUKTWIAZPPMOGPWGJQWGYDHKNJCNPSOVWTZPFOMNQUQFGOWPYTQNBAIVOSXNSNZNVHMSPAHCXBWVDTFJRWFLASXAGPHYHCWJVLEOANWKUPTXIYGUFFSQLLHZRKZFGPYTXIYGUOWKVAEOEAOBBCVOSXVWKUMSGVLYVCHKBOGYOSTSGGUYSTAAPKYWIPLBBRSRIKULYJUVWKUPFHMDKLMWMMFRLCGUVKQSWAGVVWYNVLZSILYROMKKJSBAZSWMOWKHMILSCKZAIRPWZHMGPYSXLWTNCIVXWPIPNOMZGUSSXIMUIPYUUEGUKICMDEOPFMZMRWPGOMYGOZSXBOKLGWKTWHYLUKVEWZDAGVEKUOSYBWPZDHKTDGUFBJEWNJSSLZSILYYUMFPAPAGVKVLWZKV";
        cipher = cipher.toUpperCase();

        final double[] DEUTSCH = {6.51, 1.89, 3.06, 5.08, 17.41, 1.66, 3.01, 4.76, 7.55, 0.27, 1.21, 3.44, 2.53, 9.78, 2.51, 0.79, 0.02, 7.00, 7.89, 6.15, 4.35, 0.67, 1.89, 0.03, 0.04, 1.13};
                                //  a     b     c     d     e     f     g     h     i     j     k     l     m     n     o     p     q     r     s     t     u     v     w     x     y     z

        String test = "ABCABCABCABC";
        ArrayList<Integer> distance = findRepeats(cipher, 4, 10);
        int keywordlength = calcKeywordlength(distance);
        //String divided = divideString(cipher, keywordlength);
        int[][] freqAbs = calcFreqAbs(cipher, keywordlength);
        double[][] freqRel = calcFreqRel(freqAbs, cipher, keywordlength);

        printFreq(DEUTSCH);
        System.out.println("\n \n" + "Gefundene Abstände: " + distance);
        System.out.println("\n" + "Schlüsselwortlänge: " + keywordlength);
        //System.out.println("\n" + "Geteilter Geheimtext: " + "\n" + divided);

        char c = 'A';

        System.out.println("\nHaufigkeiten an Blockpositionen:");

        for (int i = 0; i < 26; i++) {
            System.out.print(c + " | ");

            for (int j = 0; j < keywordlength; j++) {
                System.out.print(freqAbs[i][j] + ", ");
            }
            System.out.println();
            c++;
        }


        c ='A';
        System.out.print("\n \n");
        for (int i = 0; i < 26; i++) {
            System.out.print(c + " | ");
            for (int j = 0; j < keywordlength; j++) {
                System.out.print(freqRel[i][j] + ", ");
            }
            System.out.println();
            c++;
        }
    }
}
