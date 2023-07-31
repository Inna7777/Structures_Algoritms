package lesson1;

import java.util.Comparator;

/**
 * Связанный список
 * @param <T>
 */
public class LinkedList<T> {
    /**
     * Ссылка на первый элемент связанного списка
     */
    private Node head;

    /**
     * Узел
     */
    class Node{
        /**
         * ССылка на следующий элемент
         */
        public Node next;
        /**
         * значение
         */
        public T value;

    }

    /**
     * Добавление нового элемента в начало связанного списка
     * @param value значение
     */
    public void addFirst(T value){
        Node node = new Node();
        node.value = value;
        if (head != null){
            node.next = head;
        }
        head = node;
    }

    /**
     * удаление первого елемента из связанного списка
     */
    public void removeFirst(){
        if (head != null){
            head = head.next;
        }
    }

    public T contains(T value){
        Node node = head;
        while (node != null){
            if (node.value.equals(value))
                return node.value;
                node = node.next;
        }
        return null;
    }

    /**
     * сортировка
     * @return
     */
    public  void sort(Comparator <T> comparator){
        Node node = head;
        while (node != null) {

            Node minValueNode = node;

            Node node2 = node.next;
            while (node2 != null) {
                if (comparator.compare(minValueNode.value, node2.value) > 0) {
                    minValueNode = node2;
                }
                node2 = node2.next;
            }
            if (minValueNode != node) {
                T buf = node.value;
                node.value = minValueNode.value;
                minValueNode.value = buf;
            }
            node = node.next;
        }
    }

    /**
     * добавление элемента в конец списка
     * @param value
     */
    public  void  addLast(T value){
        Node node = new Node();
        node.value = value;
        if (head == null){
            head = node;
        }
        else {
            Node lastNode = head;
            while (lastNode.next != null){
                lastNode =lastNode.next;
            }
            lastNode.next = node;
        }
    }

    /**
     * удаление элемента из конца связанного списка
     */
    public void removeLast(){
        if (head == null)
            return;
        Node node = head;
        while (node.next != null){
            if (node.next.next == null){
                node.next = null;
                return;
            }
            node = node.next;
        }
        head = null;
    }

    /**
     * разворот связанного списка
     */
    public void reverse() {
        Node node= head; // переменная -текущий элемент списка
        Node prevNode = null; // переменная-предыдущий элемент списка
        Node nextNode; // переменная-следуюший элемент списка

        while (node != null) { //пока элемент списка  не станет равным null.
            nextNode = node.next;
            node.next = prevNode;
            prevNode = node;
            node = nextNode;
        }

        head = prevNode;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Node node = head;
        while (node != null){
            stringBuilder.append(node.value);
            stringBuilder.append('\n');
            node = node.next;
        }
        return stringBuilder.toString();
    }
}
