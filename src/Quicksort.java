

public class Quicksort {


    Liste<Integer> out; // Liste mit sotierten Integers

    public Liste<Integer> quicksort(Liste<Integer> in) {
        System.out.println("Unsotierte Liste: " + in.makeString());
        out = new Liste<>();
        return quicksortRun(in);
    }

    public Liste<Integer> quicksortRun(Liste<Integer> in) {

        if (in.size() > 1) {
            Liste<Integer> smaller = new Liste<>();
            Liste<Integer> greater = new Liste<>();
            int pivot = in.getItem(1);
            in.delete(1);
            while (!in.isEmpty()) {
                int akt = in.getItem(1);
                if (akt < pivot)
                    smaller.append(akt);
                else
                    greater.append(akt);

                in.delete(1);
            }
            quicksortRun(smaller);    //Rekursionsschritt smaller
            out.concat(smaller);    //sortierte smaller an out anfuegen
            out.append(pivot);        //pivot an out anfuegen
            quicksortRun(greater);    //Rekursionsschritt greater
            out.concat(greater);    //sortierte greater an out anfügen
        }
        return out;
    }
}

