
public class SuffixTreeNodeImpl extends SuffixTreeNode {


    public SuffixTreeNodeImpl() {
        super();
    }

    public SuffixTreeNodeImpl(CharLinkedList linked, SuffixTreeNode parent) {
        super(linked, parent);
    }

    @Override
    public SuffixTreeNode search(char c) {
        return this.binarySearch(c, 0, this.getNumOfChildren()-1);
    }

    @Override
    public SuffixTreeNode binarySearch(char target, int left, int right) {

        if (right<left) return null;

        int middle = (left + right)/2;
        char found = this.children[middle].getChars().firstChar();

        if (found==target) {
            return this.children[middle];
        }

        if (found>target) {
            return binarySearch(target, left, middle-1);
        }
        else return binarySearch(target, middle+1 , right);
    }


    @Override
    public void shiftChildren(int until) {
        for (int i = this.getNumOfChildren()-1; i >= until; i--)
        { this.getChildren()[i+1] = this.getChildren()[i];}
    }

    @Override
    public void addChild(SuffixTreeNode node) {
        char nodeVal = node.getChars().firstChar();
        int i = 0;
        while(i<this.getNumOfChildren()&&nodeVal>children[i].getChars().firstChar()) {
            i++;
        }
        if (i<this.getNumOfChildren()) {this.shiftChildren(i);}

        int decNew = 0;
        children[i]=node;
        this.numOfChildren++;

        for (i =0; i<this.getNumOfChildren(); i++) {
            decNew += this.children[i].getDescendantLeaves();
        }
        this.descendantLeaves = decNew;
    }

    @Override
    public void addSuffix(char[] word, int from) {
        if (from<word.length) {
            char c = word[from];

            SuffixTreeNode cFound = this.search(c);
            if(cFound==null) {
                SuffixTreeNode parent = this;

                /**update parent's descendantLeaves until Root */
                SuffixTreeNode current = this.getParent();
                while (current != null) {
                    current.descendantLeaves +=1 ;
                    current = current.getParent();
                }

                for (int i = from; i<word.length;i++) {
                    CharLinkedList linkedList = CharLinkedListImpl.from(word[i]);
                    SuffixTreeNode newSon = new SuffixTreeNodeImpl(linkedList, parent);
                    newSon.totalWordLength = 1;
                    newSon.descendantLeaves = 1;
                    parent.addChild(newSon);
                    parent = newSon;
                }
            }
            else {
                cFound.addSuffix(word, from+1);
            }
        }
    }
    @Override
    public void compress(){
        while (this.getNumOfChildren() == 1 && this.getParent() != null){
            SuffixTreeNode child_to_compress = this.getChildren()[0];
            this.chars.append(child_to_compress.getChars());
            this.totalWordLength += child_to_compress.getTotalWordLength();
            this.numOfChildren = child_to_compress.getNumOfChildren();
            this.descendantLeaves = child_to_compress.getDescendantLeaves();
            this.children = child_to_compress.getChildren();

            for (int i = 0; i < child_to_compress.getNumOfChildren(); i++) {
                child_to_compress.getChildren()[i].parent = this;  }
        }
        if (this.getNumOfChildren() > 1 || this.getParent() == null)  {
            for (int i = 0; i < this.getNumOfChildren(); i++){
                this.getChildren()[i].compress();
            }
        }
    }

    @Override
    public int numOfOccurrences(char[] subword, int from) {


        if (subword.length <= from) {
            return this.getDescendantLeaves();
        }
        SuffixTreeNode found = this.search(subword[from]);
        int i = from;

        if (found != null) {
            ICharLinkedListNode childC = found.getChars().getFirst();

            while (i < subword.length && childC != null && subword[i] == childC.getChar()) {
                i++;
                childC = childC.getNext();
            }

            if (i == subword.length) return found.getDescendantLeaves();
            if (childC == null) return (found.numOfOccurrences(subword, from + found.getTotalWordLength()));
        }



        return 0;
    }

    @Override
    public SuffixTreeNode findSuffixLeaf(char[] word, int from) {
        if (word.length<=from) {
            return this;}
        SuffixTreeNode found = this.search(word[from]);
        if (word.length==0)
        {return this;}
        return(found.findSuffixLeaf(word, from+found.getTotalWordLength()));
    }

    @Override
    public SuffixTreeNode findLCA(SuffixTreeNode node2) {
        while (this != node2){
            if (this.getDescendantLeaves() < node2.getDescendantLeaves()){
                return this.getParent().findLCA(node2);
            }
            if (this.getDescendantLeaves() > node2.getDescendantLeaves()){
                return this.findLCA(node2.getParent());
            }
            else{
                return this.getParent().findLCA(node2.getParent());
            }
        }
        return this;

    }
}
