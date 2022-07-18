public class BTElem<T> {

    private T content = null;
    private BTElem<T> parent = null, left_child = null, right_child = null;

    BTElem(T content) {
        this.content = content;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public BTElem<T> getRightChild() {
        return right_child;
    }

    public void setRightChild(BTElem<T> right_child) {
        this.right_child = right_child;
        if (right_child != null) right_child.setParent(this);
    }

    public BTElem<T> getLeftChild() {
        return left_child;
    }

    public void setLeftChild(BTElem<T> left_child) {
        this.left_child = left_child;
        if (left_child != null) left_child.setParent(this);
    }

    public BTElem<T> getParent() {
        return parent;
    }

    public void setParent(BTElem<T> parent) {
        if (parent == null && !this.isRoot()) {
            if (this.isRightChild()) this.getParent().setRightChild(null); else this.getParent().setLeftChild(null);
        }
        this.parent = parent;
    }

    public boolean isRoot() {
        return parent == null;
    }

    public boolean isRightChild() {
        return parent != null && this == this.parent.getRightChild();
    }

    public boolean isLeftChild() {
        return parent != null && this == this.parent.getLeftChild();
    }
    
}