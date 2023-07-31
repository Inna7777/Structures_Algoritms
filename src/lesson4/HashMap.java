package lesson4;

import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 класса HashMap- хэш-таблица,
 используется  для хранения пар ключ-значение.
 */
public class HashMap<K, V> implements Iterable<HashMap.Entity> {

    private static final int INIT_BUCKET_COUNT = 16;// начальный размер бакета
    private static final double LOAD_FACTOR = 0.5;
    private int size = 0; // кол-во элементов  в бакете

    private Bucket[] buckets; // массив бакет

    @Override
    public Iterator<HashMap.Entity> iterator() {
        return new HashMapIterator();
    }

    class HashMapIterator implements Iterator<HashMap.Entity>{
        private int currentBucketIndex = 0; // текущий индекс бакета
        private Bucket.Node currentNode = null;// текущее значение списка пусто
/*
Метод hasNext(), который проверяет наличие следующего элемента в HashMap.
 */
        @Override
        public boolean hasNext() {
            while (currentBucketIndex < buckets.length){ //пока индекс меньше длинны hashmap
                if (currentNode == null){ // если текущий узел пустой
                    Bucket bucket = buckets[currentBucketIndex++]; //выводим бакет с текущим индексом +1
                    if (bucket != null){ //если бакет не пустой
                        currentNode = bucket.head; // текущему узлу присваеваем значение головного узла
                    }
                }
                else {
                    return true; // возвращаем true
                }
            }
            return false; // возвращаем false
        }
/*
метод next(), который возвращает следующий элемент из HashMap.
 */
        @Override
        public Entity next() {
            if (!hasNext()){ // Если следующего элемента нет, то выброс исключения NoSuchElementException.
                throw new NoSuchElementException();
            }
            Entity entity = currentNode.value; //  присваиваем переменной entity значения текущего узла.
            currentNode = currentNode.next; // присваиваем  значение следующего узла текущему узлу
            if (currentNode == null){ // если следующего узла нет
                currentBucketIndex++; // увеличение индекса бакета
            }
            return entity; // возвращение значения entity
        }
    }
/*
Класс Entity - запись в HashMap, содержащий ключ и значение.
 */
    class Entity{
        K key; // ключ
        V value; //значение
    }
/*
Класс Bucket - это Hashmap cостоящий из узлов типа Node
 */
class Bucket<K, V>{
    private Node head;
/*
    Класс Node - узел в бакет, который содержит ссылку на следующий узел и значение типа Entity.
 */
        class Node{
            Node next;
            Entity value;
        }
/*
Метод добавления   add(), который принимает объект типа Entity и возвращает объект типа V.
 */
        public V add(Entity entity){
            Node node = new Node(); //  Создание нового объекта типа Node.
            node.value = entity; //Присваивание переменной node значения нового узла.

            if (head == null){ //Если головной узел равен null
               head = node;  //Присваивание головному узлу значения нового узла.
                return null;  //Возврат значения null
            }
            Node currentNode = head; //Присваивание переменной currentNode значения головного узла.
            while (true){
                if (currentNode.value.key.equals(entity.key)){ //Если ключ текущего узла равен ключу добавляемой записи
                    V buf = (V)currentNode.value.value; //Присваивание переменной buf значения текущего значения value.
                    currentNode.value.value = entity.value; //Присваивание текущему значению value значения добавляемого значения value.
                    return buf; //Возврат значения buf
                }
                if (currentNode.next != null){ //Если у текущего узла есть следующий узел
                    currentNode = currentNode.next; //Присваивание переменной currentNode значения следующего узла.
                }
                else {
                    currentNode.next = node;//Присваивание следующему узлу значения нового узла.
                    return null; //Возврат значения null
                }
            }

        }
/*
Метод удаления remove()  принимает ключ key и удаляет из хэш-таблицы элемент с таким ключом.
 */
        public V remove(K key){
            if (head == null) // Если корзина пуста , то метод возвращает null.
                return null;
            if (head.value.key.equals(key)){ //Если головной узел содержит элемент с заданным ключом
                V buf = (V)head.value.value; //то его значение сохраняется в переменную buf
                head = head.next; //головной узел заменяется на следующий узел
                return buf; // значение buf возвращается .
            }

            // Если головной узел не содержит элемент с заданным ключом
            // начинается цикл по всем узлам бакета
            else {
                Node node = head;
                while (node.next != null){ // проверяется, содержит ли следующий узел элемент с заданным ключом .
                    if (node.next.value.key.equals(key)){ // Если да, то его значение сохраняется в переменную buf
                        V buf = (V)node.next.value.value;
                        node.next = node.next.next; //следующий узел заменяется на следующий за ним узел
                        return buf; //buf возвращается
                    }
                    node = node.next;
                }
                return null; //метод возвращает null,
            }
        }
/*
Метод get()  принимает ключ key и возвращает значение элемента с таким ключом
 */
        public V get(K key){
            Node node = head;
            while (node != null){ //  цикл по всем узлам бакета.
                if (node.value.key.equals(key)) //проверяется, содержит ли текущий узел элемент с заданным ключом.
                    return (V)node.value.value; //усли да, то его значение возвращается
                node = node.next;
            }
            return null; // Если элемент с заданным ключом не найден , то метод возвращает null.
        }


    }
    /*
    Метод calculateBucketIndex() принимает ключ key
     и вычисляет индекс бакета в хэш-таблице для этого ключа.
     */
    private int calculateBucketIndex(K key){
        return Math.abs(key.hashCode()) % buckets.length;
    }
/*
Методе recalculate() обновляется
размер хэш-таблицы и перераспределяются элементы по новым бакетам.
 */
    private void recalculate(){
        size = 0; //  количество элементов в хэш-таблице.
        Bucket<K, V>[] old = buckets; // Переменная old хранит ссылку на старую хэш-таблицу
        buckets = new Bucket[old.length * 2]; // Создается новая хэш-таблица с увеличенным размером в два раза.
        for (int i = 0; i < old.length; i++){ // Цикл проходит по всем корзинам старой хэш-таблицы.
            Bucket<K, V> bucket = old[i]; // Переменная bucket хранит ссылку на текущий бакет.
            if (bucket != null){ //Если бакет не пустой, то происходит перенос элементов в новую хэш-таблицу.
                Bucket.Node node = bucket.head; // Переменная node хранит ссылку на текущий узел в бакет
                while (node != null){ //цикл  по всем узлам в бакет
                    put((K)node.value.key, (V)node.value.value); //добавляет узлы в новую хэш-таблицу методом put().
                    node = node.next;
                }
            }
        }
    }
/*
Метод put() принимает ключ и значение элемента,
создает новый объект Entity и добавляет его в текущий бакет
 */
    public V put(K key, V value){

        if (buckets.length *LOAD_FACTOR <= size){
            recalculate();
        } // если размер хэш-таблицы превышает заданный,
        // то вызывается метод recalculate() для перераспределения элементов.

        int index = calculateBucketIndex(key); // Переменная index хранит индекс бакета для текущего ключа.
        Bucket bucket = buckets[index]; //Переменная bucket хранит бакет с индексом index.
        if (bucket == null){ //Если корзина пуста,
            bucket = new Bucket();//то создается новый бакет
            buckets[index] = bucket;//и добавляется в хэш-таблицу
        }

        Entity entity = new Entity();//Создается новый объект Entity
        entity.key = key; //с заданным ключом
        entity.value = value;//и значением.

        V res = (V)bucket.add(entity); //Метод add() добавляет новый элемент в текущий бакет
        if (res == null){ //Если новый элемент был успешно добавлен,
            size++; //то увеличивается переменная size
        }
        return res; //Если элемент уже существовал, то возвращается его старое значение.
    }
/*
Метод улаления remove() принимает ключ key
и удаляет элемент с этим ключом из хэш-таблицы, если он существует.
 */
    public V remove(K key){
        int index = calculateBucketIndex(key); //Переменная index хранит индекс бакета ключа.
        Bucket bucket = buckets[index]; //Переменная bucket хранит ссылку на бакет с индексом index.
        if (bucket == null) //Если корзина пуста,
            return null; //то метод возвращает null.
        V res = (V)bucket.remove(key); //Вызывается метод remove() для удаления элемента из бакета с заданным ключом
        if (res != null){ //Если элемент был удален успешно,
            size--; //то уменьшается переменная size
        }
        return res;//и возвращается его значение.
    }
/*
Метод get() принимает ключ key и возвращает значение элемента с этим ключом
 */
    public V get(K key){
        int index = calculateBucketIndex(key); // Переменная index хранит индекс бакета текущего ключа
        Bucket bucket = buckets[index]; //Переменная bucket хранит ссылку на бакет с индексом index.
        if (bucket == null) //Если корзина пуста, то метод возвращает null.
            return null;
        return (V)bucket.get(key); //Вызывается метод get() для получения значения элемента с заданным ключом из бакет
    }
/*
Метод конструктор без параметров создает новую хэш-таблицу с начальным размером INIT_BUCKET_COUNT.
 */
    public HashMap(){
        buckets = new Bucket[INIT_BUCKET_COUNT];

    }
/*
Метод конструктор с параметром initCount создает новую хэш-таблицу с заданным начальным размером initCount.
 */
    public HashMap(int initCount){
        buckets = new Bucket[initCount];
    }


}
