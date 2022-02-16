public class SuffixTreeImpl extends SuffixTree {


    public SuffixTreeImpl(String word) { super(word); }

    @Override
    public boolean contains(String subword) {
        return (this.getRoot().numOfOccurrences(subword.toCharArray(),0) > 0);
    }

    @Override
    public int numOfOccurrences(String subword) {
         return (this.getRoot().numOfOccurrences(subword.toCharArray(),0));
    }
}
