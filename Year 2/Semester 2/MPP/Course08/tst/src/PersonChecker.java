import java.util.function.Predicate;

class PersonChecker {
    static void check(Person p, Predicate<Person> predicate) {
        System.out.println(predicate.test(p) ? "can drink beer" : "can't drink beer");
    }
}