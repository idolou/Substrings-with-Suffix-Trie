import java.util.Iterator;
import java.util.Spliterator;

public class CharLinkedListImpl extends CharLinkedList {

    @Override
    public void add(char c) {
        ICharLinkedListNode node = new CharLinkedListNodeImpl(c);
        if (this.last != null) {
            this.getLast().setNext(node);
        }
        this.last = node;
        if (this.first == null)
        {this.first = node;}
    }


    @Override
    public char firstChar() {
        return this.getFirst().getChar();
    }

    @Override
    public int size() {
        int i = 0;
        for (char c : this)
            i++;
        return i;
    }

    @Override
    public void append(CharLinkedList toAppend) {
        this.getLast().setNext(toAppend.getFirst());
        this.last = toAppend.getLast();
    }
}