public class CharLinkedListNodeImpl implements ICharLinkedListNode {

    private ICharLinkedListNode next;
    protected char key;

    public CharLinkedListNodeImpl(char k){
        this.key = k;
        this.next = null;
    }


    @Override
    public char getChar() {
        return this.key;
    }

    @Override
    public ICharLinkedListNode getNext() {
        return this.next;

    }

    @Override
    public void setNext(ICharLinkedListNode next) {
        this.next = next;
    }
}

