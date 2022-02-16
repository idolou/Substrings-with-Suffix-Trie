import java.util.Arrays;

public class ReverseSuffixTreeImpl extends ReverseSuffixTree {
    /**
     * Constructs a reverse suffix tree containing all suffices of a single word in both normal and reverse order.
     * A dollar and hashtag signs are appended to the end of the normal order and reverse order words poly_strpectively
     * to distinguish between them
     *
     * @param word The word to create a tree of all its suffices
     */
    public ReverseSuffixTreeImpl(String word) {
        super(word);
    }


    private char[] reverseArray(char[] array){
        int index = 0;
        char[] temp= new char[array.length];
        for ( int i = array.length-1 ; i >= 0; i--){
            temp[index]=array[i];
            index ++;}

        return temp;



    }

    @Override
    public String longestPalindrome() {

        String Longest_Odd="";
        for (int i=2 ; i<this.word.length ; i++) {

            char[] Ao = Arrays.copyOfRange(this.word, 0, i - 1);
            char[] Bo = Arrays.copyOfRange(this.word, i, word.length);
            Ao = reverseArray(Ao);

            String curr_lcp_O = LCPcalc(Ao, Bo);

            if (curr_lcp_O.length() > Longest_Odd.length()) {
                Longest_Odd = curr_lcp_O;
            }
        }

        Longest_Odd= new StringBuilder(Longest_Odd).reverse().toString()+"X"+Longest_Odd;

        String Longest_Even="";

        for (int i=1;i<this.word.length ; i++){

            char[] Ae = Arrays.copyOfRange(this.word, 0, i );
            char[] Be = Arrays.copyOfRange(this.word, i, word.length);
            Ae = reverseArray(Ae);

            String curr_lcp_E = LCPcalc(Ae, Be);

            if (curr_lcp_E.length() > Longest_Even.length()) {
                Longest_Even = curr_lcp_E;

            }

        }

        Longest_Even= new StringBuilder(Longest_Even).reverse().toString()+Longest_Even;

        if(Longest_Odd.length()>Longest_Even.length()){

            return Longest_Odd;}

            else{

                return Longest_Even;
            }
        }


    private String LCPcalc(char[] A,char[] B){

        SuffixTreeNode A_node = this.getRoot().findSuffixLeaf(A,0);
        SuffixTreeNode B_node = this.getRoot().findSuffixLeaf(B,0);
        SuffixTreeNode LCPe_node = (A_node.findLCA(B_node));
        String lcp = LCPe_node.restoreStringAlongPath();

        return lcp;

    }
}
