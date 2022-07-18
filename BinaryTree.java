public class BinaryTree<T> {
    
    private BTElem<T> root = null, last = null;
    private int size = 0;

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public BTElem<T> getRoot() {
        return root;
    }

    public BTElem<T> add(T cont) {
        BTElem<T> elem = new BTElem<T>(cont);
        if (size == 0) {
            root = elem;
        } else if (size == 1){
            root.setLeftChild(elem);
        } else if (size % 2 == 0) { // when size is even, last is left child
            last.getParent().setRightChild(elem);
        } else { // last is right child
            BTElem<T> temp = last.getParent();
            while (temp.isRightChild()) temp = temp.getParent(); // stops when temp is root or left child
            if (!temp.isRoot()) temp = temp.getParent().getRightChild();
            while (temp.getLeftChild() != null) temp = temp.getLeftChild();
            temp.setLeftChild(elem);
        }
        last = elem;
        size += 1;
        return elem;
    }
    
    // sets the last as root
    public T removeRoot() {
        if (size == 1) return removeLast();
        T cont = root.getContent();
        root.setContent(removeLast());
        return cont;
    }

    public T removeLast() {
        if (size == 0) return null;
        BTElem<T> elem = last;
        if (size == 1) {
            root = null;
            last = null;
        } else if (size % 2 == 1) { // when size is odd, last is right child
            last = last.getParent().getLeftChild();
        } else { // last is left child
            BTElem<T> temp = last.getParent();
            while (temp.isLeftChild()) temp = temp.getParent(); // stops when temp is root or right child
            if (!temp.isRoot()) temp = temp.getParent().getLeftChild();
            while (temp.getRightChild() != null) temp = temp.getRightChild();
            last = temp;
        }
        elem.setParent(null);
        size -= 1;
        return elem.getContent();
    }

}
