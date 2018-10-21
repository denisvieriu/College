class Main<T> {

    T t;

    public Main(T t) {
        this.t = t;
    }

    public String toString() {return t.toString();}



    public static void main(String[] args) {

        System.out.println(new Main<String>("hi"));
        System.out.println(new Main(5));
//        1
//        Person p = new Person();
//        p.age = 18;
//        PersonChecker.check(p, p1 -> p1.age >= 18);
        // -> Can drink beer

//        2
//        Comparator<Integer> c = (o1, o2) -> o2 - o1;
//        List<Integer> list = Arrays.asList(5, 4, 7, 1);
//        Collections.sort(list, c);
//        System.out.println(Collections.binarySearch(list, 1));
        // -> - 1

        // 3
//        Stream.generate(() -> "abracadabra")
//                .filter(n -> n.length() == 4)
//                .limit(2)
//               // .sorted()
//                .forEach(System.out::println);
//        System.out.println("Hello");
        // doesn't print anything


    }

}
