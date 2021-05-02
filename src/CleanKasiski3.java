import java.util.ArrayList;

public class CleanKasiski3 {


    // Bestimmen der Schlüsselwortlänge vom String des verschlüsselten Texts
    public static ArrayList<Integer> findRepeats(String cipher, int min, int max) {
        ArrayList<Integer> distance = new ArrayList<Integer>();     // initalisieren und definieren einer neuen ArrayList als Integer-Typ
        cipher = cipher.toUpperCase();

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
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }


    // Den Text in Blöcke entsprechend der Schlüssellänge unterteilen
    public static String divideString(String cipher, int keywordlength) {
        int p = 0;
        String divide = "";

        for (int i = 0; i < cipher.length() / keywordlength; i++) {
            String s = cipher.substring(p, p + keywordlength);
            divide += s += " ";

            p += keywordlength;
        }

        return divide;
    }

    public static int[][] calcFreqAbs(String cipher, int keywordlength) {
        int[][] freqAbs = new int[26][keywordlength];

        for (int keypos = 0; keypos < keywordlength; keypos++) {
            for (int i = keypos; i < cipher.length(); i += keywordlength) {
                freqAbs[(cipher.charAt(i) - 65)][keypos]++;
            }
        }

        return freqAbs;
    }

    public static int[][] calcFreqRel(int[][] freqAbs){
        Bubblesort sort = new Bubblesort();
        int[][] freqRel = sort.sort(freqAbs);

        return freqRel ;
    }

    // Häufkeitsverteilung printen
    public static void printFreq(double[] lang) {
        System.out.print("\n" + "Häufigkeit: | ");
        for (int i = 0; i < lang.length; i++) {
            System.out.print(lang[i] + " | ");
        }

        System.out.print("\n" + "Zeichen:    |   ");

        for (char c = 'A'; c <= 'Z'; c++) {
            System.out.print(c + "  |   ");
        }
    }

    public static void main(String[] args) {
        String cipher = "PWTMYTBADKDGPWPFYWFGUESOTLUPNVYWAPKCSOOJWWASTLSUZUSJMJBBRSTIMGPYSXOJWWASMMZQLCHJQWGYDHKOJWWASTMFPADWIPVKLHONZWPDPWRAAGQPRKNJCNPKGPJJLTHYOWOHPGYJWCUEKUZLGAOWKHOGPESMZMRWPBKVFVZTQNLAGSFSMVWTDPWRAAGQPRKNJCNPTGTKEOMSGVLYVCHKBVKLOFOBLGNCIVXWPLYBZAAEOOWKEWEODZKZOGPWGOMSWMPWTIFFLCTUTYGUOSLZSILYOHEWEODSRVVYHSFAVVHHWGIPTGHYHCWJVLERGJWKPDHGJWTUTQNBXGZEUKTWIAZPPMOGPWGJQWGYDHKNJCNPSOVWTZPFOMNQUQFGOWPYTQNBAIVOSXNSNZNVHMSPAHCXBWVDTFJRWFLASXAGPHYHCWJVLEOANWKUPTXIYGUFFSQLLHZRKZFGPYTXIYGUOWKVAEOEAOBBCVOSXVWKUMSGVLYVCHKBOGYOSTSGGUYSTAAPKYWIPLBBRSRIKULYJUVWKUPFHMDKLMWMMFRLCGUVKQSWAGVVWYNVLZSILYROMKKJSBAZSWMOWKHMILSCKZAIRPWZHMGPYSXLWTNCIVXWPIPNOMZGUSSXIMUIPYUUEGUKICMDEOPFMZMRWPGOMYGOZSXBOKLGWKTWHYLUKVEWZDAGVEKUOSYBWPZDHKTDGUFBJEWNJSSLZSILYYUMFPAPAGVKVLWZKV";
        final double[] DEUTSCH = {6.51, 1.89, 3.06, 5.08, 17.41, 1.66, 3.01, 4.76, 7.55, 0.27, 1.21, 3.44, 2.53, 9.78, 2.51, 0.79, 0.02, 7.00, 7.89, 6.15, 4.35, 0.67, 1.89, 0.03, 0.04, 1.13};
                                //  a     b     c     d     e     f     g     h     i     j     k     l     m     n     o     p     q     r     s     t     u     v     w     x     y     z

        String test = "ABCABCABCABC";
        ArrayList<Integer> distance = findRepeats(cipher, 4, 10);
        int keywordlength = calcKeywordlength(distance);
        String divided = divideString(cipher, keywordlength);
        int[][] freqAbs = calcFreqAbs(cipher, keywordlength);
        int[][] freqRel = calcFreqRel(freqAbs);

        printFreq(DEUTSCH);
        System.out.println("\n \n" + "Gefundene Abstände: " + distance);
        System.out.println("\n" + "Schlüsselwortlänge: " + keywordlength);
        System.out.println("\n" + "Geteilter Geheimtext: " + "\n" + divided);

        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < keywordlength; j++) {
                System.out.print(freqAbs[i][j] + ", ");
            }
            System.out.println("");
        }

        System.out.print("\n \n");
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < keywordlength; j++) {
                System.out.print(freqRel[i][j] + ", ");
            }
            System.out.println("");
        }



    }
}
