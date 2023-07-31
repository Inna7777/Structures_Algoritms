package lesson1;

public class Program {

    public static void main(String[] args) {

        LinkedList<Employee> linkedList = new LinkedList<>();
        linkedList.addFirst(new Employee("FFF", 34));
        linkedList.addFirst(new Employee("CCCC", 44));
        linkedList.addFirst(new Employee("AAAAA", 22));
        linkedList.addFirst(new Employee("AAAAA", 42));
        linkedList.addFirst(new Employee("MMMMM", 44));
        linkedList.addFirst(new Employee("AAAAA", 32));

        Employee res = linkedList.contains(new Employee( "User3", 22));

        System.out.println(res);
        System.out.println();
        System.out.println(linkedList);

//        linkedList.sort(new EmployeeComparator(SortDirect.Ascending));
//        System.out.println();
//        System.out.println(linkedList);
//
//        linkedList.sort(new EmployeeComparator(SortDirect.Descending));
//        System.out.println();
//        System.out.println(linkedList);

//        linkedList.removeFirst();
//        linkedList.removeLast();
//        System.out.println();
//        System.out.println(linkedList);

        linkedList.reverse();
        System.out.println();
        System.out.println(linkedList);

    }
}
