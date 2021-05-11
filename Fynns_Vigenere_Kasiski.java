import java.util.ArrayList;

public class Vigenere_Kasiski {
  
  static String cipher = "";    // String des verschlüsselten Texts
  static String plain = "";   // String des entschlüsselten Texts
  
  // COMMENT
  public static String readTextFromFile(String dateiname) {
    String text = "";
    
    // TODO
    
    return text;
  }
  
  // Bestimmen des größten gemeinsamen Teilers aus a u. b
  public static int getGGT(int a, int b){
    if (b==0) {
      return a;
    }
    if (b>a) {
      return getGGT(b, a);
    }
    return getGGT(a%b, b);
  }
  
  // Bestimmen der Schlüsselwortlänge vom String des verschlüsselten Texts
  public static int calcKeywordLength(String cipher, int min, int max) {
    ArrayList<Integer> distance = new ArrayList<Integer>(); // initalisieren und definieren einer neuen ArrayList als Integer-Typ 
    cipher = cipher.toUpperCase();
    for (int l=min;l<max;l++) {                 // Hochzählen einer for-Schleife, wobei die Länge l variiert für die gesuchten Buchstabenketten mit den Längen 4-10
      for (int spos=0;spos<cipher.length()-l+1;spos++) {  // Hochzählen einer for-Schleife für die Länge des Strings
        String s = cipher.substring(spos,spos+l);   // Herausfiltern der gef. Buchstabenketten 
        for (int i=spos+l;i<cipher.length();i++ ) {   // Herausfinden der Abstände für Buchstabenwiederholungen mit Hochzählen einer for-Schleife
          if(cipher.startsWith(s, i)){
            if (!distance.contains(i-spos)) {
              distance.add(i-spos);  
            }
          }
        }
      }
    }
    System.out.print("Gefundene Abstände: "+distance+"\n");
    
    int ggt=distance.get(0);
    for (int i=1; i<distance.size(); i++) {     // "ggt"(gemeinsamer Teiler) aller gefundener Abstände
      ggt=getGGT(ggt,distance.get(i));
    }
    
    System.out.println("Länge des Schlüsselwortes: "+ggt);
    return ggt;
  }
  
  // Berechnen der absoluten Häufigkeiten der Buchstaben
  public static int[] calcFreqAbs(String cipher) {
    cipher=cipher.toUpperCase();
      int[] freqAbs = new int[26];
      for (int i=0; i<cipher.length(); i++) {
        freqAbs[(cipher.charAt(i)-65)]++;
    }
      return freqAbs;
  }
  
  // Berechnen der relativen Häufigkeiten der Buchstaben
  public static double[] calcFreqRel(String cipher) {
    cipher=cipher.toUpperCase();
      int[] freqAbs = calcFreqAbs(cipher);
      double[] freqRel = new double[26];
      for (int i=0; i<freqAbs.length; i++) {
        freqRel[i] = ((double)freqAbs[i]/cipher.length())*100;
      }
    return freqRel;
  }
  
  // Herausfinden vom Lösungswort
  public static String calcKeyword(String cipher, int keylength) {
    String keyword = "";
    String[] splitCipher = new String[keylength]; // Verschiebungen speichern
    
    // COMMENT
    for (int keypos=0; keypos<keylength; keypos++) {
      splitCipher[keypos]="";
      for (int i=keypos; i<cipher.length(); i+=keylength) {
        splitCipher[keypos]+=cipher.charAt(i);
      }
    }

    // COMMENT
    final double[] DEUTSCH = {6.51, 1.89, 3.06, 5.08, 17.41, 1.66, 3.01, 4.76, 7.55, 0.27, 1.21, 3.44, 2.53, 9.78, 2.51, 0.79, 0.02, 7.00, 7.89, 6.15, 4.35, 0.67, 1.89, 0.03, 0.04, 1.13};
                            //  a     b     c     d     e     f     g     h     i     j     k     l     m     n     o     p     q     r     s     t     u     v     w     x     y     z
    
    // TODO: Für jeden Teilstring eine Häufigkeitsanalyse durchführen und daraus jeweils die Caesar-Verschiebung (=Schlüsselbuchstabe) bestimmen

    // TODO
        
    // TODO: Schlüsselbuchstaben zum Schlüsselwort zusammensetzen
    for(int i=0; i<DEUTSCH.length; i++)
      {
        System.out.print(DEUTSCH[i] + " | ");
      }
    System.out.println("\n" + "Schlüsselwort: "+keyword);
    return keyword;
  }
  
  public static void main(String[] args) {
    cipher = "PWTMYTBADKDGPWPFYWFGUESOTLUPNVYWAPKCSOOJWWASTLSUZUSJMJBBRSTIMGPYSXOJWWASMMZQLCHJQWGYDHKOJWWASTMFPADWIPVKLHONZWPDPWRAAGQPRKNJCNPKGPJJLTHYOWOHPGYJWCUEKUZLGAOWKHOGPESMZMRWPBKVFVZTQNLAGSFSMVWTDPWRAAGQPRKNJCNPTGTKEOMSGVLYVCHKBVKLOFOBLGNCIVXWPLYBZAAEOOWKEWEODZKZOGPWGOMSWMPWTIFFLCTUTYGUOSLZSILYOHEWEODSRVVYHSFAVVHHWGIPTGHYHCWJVLERGJWKPDHGJWTUTQNBXGZEUKTWIAZPPMOGPWGJQWGYDHKNJCNPSOVWTZPFOMNQUQFGOWPYTQNBAIVOSXNSNZNVHMSPAHCXBWVDTFJRWFLASXAGPHYHCWJVLEOANWKUPTXIYGUFFSQLLHZRKZFGPYTXIYGUOWKVAEOEAOBBCVOSXVWKUMSGVLYVCHKBOGYOSTSGGUYSTAAPKYWIPLBBRSRIKULYJUVWKUPFHMDKLMWMMFRLCGUVKQSWAGVVWYNVLZSILYROMKKJSBAZSWMOWKHMILSCKZAIRPWZHMGPYSXLWTNCIVXWPIPNOMZGUSSXIMUIPYUUEGUKICMDEOPFMZMRWPGOMYGOZSXBOKLGWKTWHYLUKVEWZDAGVEKUOSYBWPZDHKTDGUFBJEWNJSSLZSILYYUMFPAPAGVKVLWZKV";
    System.out.println("Geheimtext: "+cipher+"\n");
    int keywordlength = calcKeywordLength(cipher,4,10);   // 1. Schlüsselwortlänge bestimmen
    String keyword = calcKeyword(cipher, keywordlength);  // 2. Schlüsselwort bestimmen
    System.out.println("Klartext: "+plain+"\n");
    System.out.println(keyword);
  }

}
