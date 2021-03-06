/*
*kasiski test
*/

import java.text.DecimalFormat;
import java.util.*;

public class CleanKasiski3 {
    /*
    / Find re-occuring strings in the encrypted text
    / @param String cipher -> encrypted text
    / @param int min -> min length of repeating characters
    / @param int max -> max length of repeating characters
    / @return Arraylist 'distance', storing distances as int
    */

    //Finn, letzes Jahr
    public static ArrayList<Integer> findRepeats(String cipher, int min, int max) {
        // initalisieren und definieren einer neuen ArrayList als Integer-Typ
        ArrayList<Integer> distance = new ArrayList<>();     
        //convert cipher to uppercase
        cipher = cipher.toUpperCase();
        // Hochzählen einer for-Schleife, wobei die Länge l variiert für die gesuchten Buchstabenketten mit den Längen 4-10
        for (int l = min; l < max; l++) {
            // Hochzählen einer for-Schleife für die Länge des Strings
            for (int spos = 0; spos < cipher.length() - l + 1; spos++) {      
                String s = cipher.substring(spos, spos + l);
                // Herausfiltern der gef. Buchstabenketten
                for (int i = spos + l; i < cipher.length(); i++) {      
                    // storing a value in ArrayList 'distance', if 'distance' does not already contain the value
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


    // calculate keyword length
    // Finn, letztes Jahr, angepasst durch Jona
    public static int calcKeywordlength(ArrayList<Integer> distance) {
        int ggt = distance.get(0); // größter gemeinsamer Teiler
        for (int i = 1; i < distance.size(); i++) { 
            //größten gemeninsamen Teiler bestimmen des letzen ggt und der aktullen Distanz
            ggt = getGGT(ggt, distance.get(i));
        }
        return ggt;
    }

    /* Ermitteln des größten gemeinsamen Teilers
    * @param int a
    * @param int b
    * recursive!!
    */
    // Finn, letztes Jahr, angepasst durch Jona
    public static int getGGT(int a, int b) {
        if (b == 0) {
            return a;
        }
        if (b > a) {
            return getGGT(b, a);
        } // math: modulo operation. divide a/b and return remainder. | ex: 19 % 5 = 4 |
        return getGGT(a % b, b);
    }

    /*
    *calculate frequency absolut
    *@param String cipher - Geheimtext
    *@param int keywordlength - Schlüssellänge
    */
    // Jona, neu geschrieben auf Basis von Finns Struktur (Troubleshooting mit Moritz, Danke!!)
    public static int[][] calcFreqAbs(String cipher, int keywordlength) {
        //new two-dimensional int array for char frequency at keyword position
        int[][] freqAbs = new int[26][keywordlength];

        //iterate over every keyword position
        for (int keypos = 0; keypos < keywordlength; keypos++) {
            //iterate over every char at block position
            // i = i + keywordlength
            for (int i = keypos; i < cipher.length(); i += keywordlength) {
                //increase number of char for detected chat at key position
                freqAbs[(cipher.charAt(i) - 65)][keypos]++;
            }
        }
        return freqAbs;
    }

    /*
    * calculate frequency relative
    * @param int[][] freqAbs -> absolute count of each character on each keyword position
    * @param Sting cipher -> encrypted string 
    * @param int keywordlength -> the length of the keyword, as calculated above
    */
    // Jona, neu geschrieben auf Basis von Finns Struktur
    public static double[][] calcFreqRel(int[][] freqAbs, String cipher, int keywordlength){
        //new double 'block' initialize with block length
        double block = ((double)cipher.length()/keywordlength);
        //new two-dimensional int array for the relative frequency
        double[][] freqRel = new double[26][freqAbs[0].length];
        //iterate over every char in freqRel
        for (int i=0; i<freqRel.length; i++) {
            //iterate over every keyword position
            for(int j=0; j<freqRel[0].length; j++){
                //set relative frequency at current position
                freqRel[i][j] = Math.round(((double)freqAbs[i][j]/block*100)*100.0)/100.0;
            }
        }
        return freqRel ;
    }

    // Print most frequent letter with freqRel
    // Jona
    public static void printTop(double[][] freqRel){
        //initialize two arrays to store the value and index of the most frequent char
        double[] first = new double[freqRel[0].length];
        int[] firstpos = new int[freqRel[0].length];
        //initialize to arrays to store the value and index of the second most frequent char
        double[] second = new double[freqRel[0].length];
        int[] secondpos = new int[freqRel[0].length];

        //iterate Y Axis (all letters in the Alphabet)
        for(int i = 0; i < freqRel.length; i++){
            //iterate Z Axis (all Keyword positions)
            for(int j = 0; j <freqRel[i].length; j++){
                //store most frequent letter for every keyword position
                if(freqRel[i][j] > first[j]){
                    first[j] = freqRel[i][j];
                    //store current index + 65 (ASCII...)
                    firstpos[j] = i+65;
                }
                else if(freqRel[i][j] > second[j] && freqRel[i][j] < first[j]){
                    second[j] = freqRel[i][j];
                    secondpos[j] = i+65;
                }
            }
        }
        
        // initialize DecimalFomat with patten 00.00
        DecimalFormat df = new DecimalFormat("00.00");
        //print both arrays
        for(int l = 0; l < freqRel[0].length; l++){
            // for each row:  index + 1      Most frequent character        value                  Second most frequent character     value
            System.out.println((l+1) + ": " + (char)firstpos[l] + " | " + df.format(first[l]) +      " || " + (char)secondpos[l] + " | " + df.format(second[l]));
        }
    }

    // Häufkeitsverteilung der Sprache printen
    // Jona
    public static void printFreq(double[] language) {
        DecimalFormat df = new DecimalFormat("00.00");
        System.out.print("| ");
        // enhanced for-each loop: iterate through each position in the array
        for (double i : language) {
            //format to "00.00"
            String formatted = df.format(i);
            formatted = formatted.replaceAll("\\A0", " ");
            //print formatted String + |
            System.out.print(formatted + "|");
        }

        
        //print new line and "Zeichen    |   "
        System.out.print("\n" + "|   ");
        //iterate over every char in the alphabet
        for (char c = 'A'; c <= 'Z'; c++) {
            //print out current char and "  |  "
            System.out.print(c + "  |  ");
        }
    }
    // Alle Häufigkeiten printen (optional)
    // Jona
    public static void printAllFreq(int[][] freqAbs, double[][] freqRel, int keywordlength){
        //initialize 2 DecimalFormat objects
        DecimalFormat df = new DecimalFormat("00.00");
        DecimalFormat cf = new DecimalFormat("00");

        //initialize char c as 'A'
        char c = 'A';

        //iterate over the alphabet
        for (int i = 0; i < 26; i++) {
            //print current char and divider
            System.out.print(c + " |");
            //iterate over every keyword position
            for (int j = 0; j < keywordlength; j++) {
                System.out.print(cf.format(freqAbs[i][j]) + " | ");
            }
            System.out.println();
            c++;
        }

        // set char c to 'A'
        c ='A';
        //print two new line
        System.out.print("\n \n");
        //iterate over the alphabet
        for (int i = 0; i < 26; i++) {
            //print current char and divider
            System.out.print(c + " |");
            //iterate over every keyword position
            for (int j = 0; j < keywordlength; j++) {
                System.out.print(df.format(freqRel[i][j]) + " | ");
            }
            System.out.println();
            c++;
        }

    }
    // erste 5 Zeilen: Finn, Rest Jona
    public static void main(String[] args) {
        //initialize string cipher wirh decrypted text
        String cipher = "PWTMYTBADKDGPWPFYWFGUESOTLUPNVYWAPKCSOOJWWASTLSUZUSJMJBBRSTIMGPYSXOJWWASMMZQLCHJQWGYDHKOJWWASTMFPADWIPVKLHONZWPDPWRAAGQPRKNJCNPKGPJJLTHYOWOHPGYJWCUEKUZLGAOWKHOGPESMZMRWPBKVFVZTQNLAGSFSMVWTDPWRAAGQPRKNJCNPTGTKEOMSGVLYVCHKBVKLOFOBLGNCIVXWPLYBZAAEOOWKEWEODZKZOGPWGOMSWMPWTIFFLCTUTYGUOSLZSILYOHEWEODSRVVYHSFAVVHHWGIPTGHYHCWJVLERGJWKPDHGJWTUTQNBXGZEUKTWIAZPPMOGPWGJQWGYDHKNJCNPSOVWTZPFOMNQUQFGOWPYTQNBAIVOSXNSNZNVHMSPAHCXBWVDTFJRWFLASXAGPHYHCWJVLEOANWKUPTXIYGUFFSQLLHZRKZFGPYTXIYGUOWKVAEOEAOBBCVOSXVWKUMSGVLYVCHKBOGYOSTSGGUYSTAAPKYWIPLBBRSRIKULYJUVWKUPFHMDKLMWMMFRLCGUVKQSWAGVVWYNVLZSILYROMKKJSBAZSWMOWKHMILSCKZAIRPWZHMGPYSXLWTNCIVXWPIPNOMZGUSSXIMUIPYUUEGUKICMDEOPFMZMRWPGOMYGOZSXBOKLGWKTWHYLUKVEWZDAGVEKUOSYBWPZDHKTDGUFBJEWNJSSLZSILYYUMFPAPAGVKVLWZKV";

        //Häufigkeitsverteilung der Buchstaben in der deutschen Straße
        final double[] DEUTSCH = {6.51, 1.89, 3.06, 5.08, 17.41, 1.66, 3.01, 4.76, 7.55, 0.27, 1.21, 3.44, 2.53, 9.78, 2.51, 0.79, 0.02, 7.00, 7.89, 6.15, 4.35, 0.67, 1.89, 0.03, 0.04, 1.13};
                                //  a     b     c     d     e     f     g     h     i     j     k     l     m     n     o     p     q     r     s     t     u     v     w     x     y     z
        //initialize ArrayList distance with return value of findRepeats with min 4 and max 10
        ArrayList<Integer> distance = findRepeats(cipher, 4, 10);
        //initialize keywordlength with return value calcKeywordlength with keywordlentgh distance
        int keywordlength = calcKeywordlength(distance);
        //initialize freqAbs with return value of calcFreqAbs with cipher and keywordlength
        int[][] freqAbs = calcFreqAbs(cipher, keywordlength);
        //initialize freqRel with return value of calcFreqRel with absolute frequency cipher and keywordlength
        double[][] freqRel = calcFreqRel(freqAbs, cipher, keywordlength);

        //print distances and keywordlength
        System.out.println("\nHäufigkeitsverteilung im Deutschen Alphabet:");
        printFreq(DEUTSCH);
        
        System.out.println("\n \n" + "Gefundene Abstände: " + distance);
        System.out.println("\n" + "Schlüsselwortlänge: " + keywordlength);

        //print most frequent characters
        System.out.println("\n" + "Häufigster Buchstabe an der Schlüsselposition:");
        printTop(freqRel);

        System.out.println("\nHaufigkeiten an Blockpositionen");
        printAllFreq(freqAbs, freqRel, keywordlength);
    }
}