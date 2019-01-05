import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class Parser {
    public static void main(String[] args) {

//        Multimap<String, String> initial = HashMultimap.create();
//        Multimap<String, String> res;
//        Multimap<String, String> closure;
//        initial.put("S", "a.A");
//        res = Grammar.clossure(initial);
//        closure = Grammar.realGoto(res, "c");

//        Grammar.createCanonicalSet().entries().forEach(k -> {
//            System.out.println("Key: " + k.getKey() + ", Value: " + k.getValue());
//        });

        Grammar.buildTable();
        Grammar.lr0();
//        closure.entries().forEach(System.out::println);
    }



}
