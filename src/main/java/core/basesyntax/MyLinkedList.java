package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> first;
    private Node<T> last;
    private int size;

    public MyLinkedList() {
        size = 0;
    }

    public Node<T> getFirst() {
        return first;
    }

    public void setFirst(Node<T> first) {
        this.first = first;
    }

    public Node<T> getLast() {
        return last;
    }

    public void setLast(Node<T> last) {
        this.last = last;
    }

    public void setSize(int size) {
        this.size = size;
    }

    private void testOfIndex(int index) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public String toString() {
        Node<T> temp = getFirst();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < size(); i++) {
            stringBuilder.append(i).append(" ").append(temp.getElement()).append(" ");
            temp = temp.next;
        }
        stringBuilder.append("{size: ").append(size()).append("}");
        return stringBuilder.toString();
    }

    @Override
    public void add(T value, int index) {
        testOfIndex(index);
        if (index == 0 || index == size) {
            add(value);
        } else {
            Node<T> newNode = new Node<T>(value);
            Node<T> tempNode;
            if (index < size / 2) {
                tempNode = getFirst();
                for (int i = 0; i <= index; i++) {
                    if (i == index) {
                        newNode.setNext(tempNode);
                        newNode.setPrevious(tempNode.getPrevious());
                        tempNode.getPrevious().setNext(newNode);
                        break;
                    }
                    tempNode = tempNode.getNext();
                }
                size++;
            } else {
                tempNode = getLast();
                for (int i = size() - 1; i >= index; i--) {
                    if (i == index) {
                        newNode.setNext(tempNode);
                        newNode.setPrevious(tempNode.getPrevious());
                        tempNode.getPrevious().setNext(newNode);
                        break;
                    }
                    tempNode = tempNode.getPrevious();
                }
                size++;
            }
        }
    }

    @Override
    public void add(T value) {
        Node newNode = new Node(value);
        if (first == null) {
            setFirst(newNode);
            setLast(newNode);
            size++;
        } else {
            newNode.setPrevious(getLast());
            getLast().setNext(newNode);
            setLast(newNode);
            size++;
        }

    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> tempNode;
        if (index < size() / 2) {
            tempNode = getFirst();
            for (int j = 0; j < size(); j++) {
                if (index == j) {
                    return tempNode.getElement();
                }
                tempNode = tempNode.getNext();
            }
        } else {
            tempNode = getLast();
            for (int i = size() - 1; i >= index; i--) {
                if (i == index) {
                    return tempNode.getElement();
                }
                tempNode = tempNode.getPrevious();
            }
        }
        return null;
    }

    @Override
    public void set(T value, int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }

        Node<T> temp;
        if (index < size / 2) {
            temp = getFirst();
            for (int i = 0; i < size(); i++) {
                if (i == index) {
                    temp.setElement(value);
                    break;
                }
                temp = temp.getNext();
            }
        } else {
            temp = getLast();
            for (int i = size() - 1; i >= index; i--) {
                if (i == index) {
                    temp.setElement(value);
                    break;
                }
                temp = temp.getPrevious();
            }
        }
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }

        Node<T> temp;
        if (index == 0) {
            temp = getFirst();
            setFirst(temp.getNext());
            size--;
            return temp.getElement();
        }
        if (index == size - 1) {
            temp = getLast();
            setLast(null);
            size--;
            return temp.getElement();
        }
        if (index < size() / 2) {
            temp = getFirst();
            for (int i = 0; i < size(); i++) {
                if (i == index && i < size()) {
                    Node<T> next = temp.getNext();
                    Node<T> prev = temp.getPrevious();
                    next.setPrevious(prev);
                    prev.setNext(next);
                    break;
                }
                temp = temp.getNext();
            }
            size--;
            return temp.getElement();
        } else {
            temp = getLast();
            for (int i = size() - 1; i >= index; i--) {
                if (i == index && i < size()) {
                    Node<T> next = temp.getNext();
                    Node<T> prev = temp.getPrevious();
                    next.setPrevious(prev);
                    prev.setNext(next);
                    break;
                }
                temp = temp.getPrevious();
            }
            size--;
            return temp.getElement();
        }
    }

    @Override
    public T remove(T t) {
        Node<T> temp;
        if ((t == null && getFirst().getElement() == null)
                || (t != null && getFirst().getElement() != null
                && getFirst().getElement().equals(t))) {
            temp = getFirst();
            setFirst(getFirst().getNext());
            size--;
            return temp.getElement();
        }
        if ((t == null && getLast().getElement() == null)
                || (t != null && getLast().getElement() != null
                && getLast().getElement().equals(t))) {
            temp = getLast();
            setLast(null);
            size--;
            return temp.getElement();
        }
        temp = getFirst();
        for (int i = 0; i < size(); i++) {
            if ((temp.getElement() == null && t == null)
                    || (temp.getElement() != null && temp.getElement().equals(t))) {
                Node<T> next = temp.getNext();
                Node<T> prev = temp.getPrevious();
                next.setPrevious(prev);
                prev.setNext(next);
                size--;
                return temp.getElement();
            }
            temp = temp.getNext();
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return getFirst() == null;
    }

    private static class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> previous;

        public Node(T element) {
            this.element = element;
        }

        public Node(Node<T> next, Node<T> previous, T element) {
            this.element = element;
            this.next = next;
            this.previous = previous;
        }

        public T getElement() {
            return element;
        }

        public void setElement(T element) {
            this.element = element;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }

        public Node<T> getPrevious() {
            return previous;
        }

        public void setPrevious(Node<T> previous) {
            this.previous = previous;
        }

    }
}
