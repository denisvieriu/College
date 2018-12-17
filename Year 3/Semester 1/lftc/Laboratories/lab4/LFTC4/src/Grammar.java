import com.google.common.collect.HashMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import javafx.util.Pair;

import java.util.*;

public class Grammar {
    private static List<String> nonTerminals;
    private static List<String> terminals;
    private static Multimap<String, Pair<Integer, String>> productions;
    private static String startingSymbol;
    private static Map<Pair<String, String>, String> table = new HashMap<>();
    private static Map<String, List<Pair<String, String>>> gotoMap;

    static {
        nonTerminals = Arrays.asList("S", "A");
        terminals = Arrays.asList("a", "b", "c");
        productions = HashMultimap.create();
        productions.put("K", new Pair<>(0, "S"));
        productions.put("S", new Pair<>(1, "aA"));
        productions.put("A", new Pair<>(2, "bA"));
        productions.put("A", new Pair<>(3, "c"));

        startingSymbol = "S";
    }

    public static Multimap<String, String> clossure(Multimap<String, String> input) {
        boolean addedVal;
        Multimap<String, String> result = HashMultimap.create();
        result.putAll(input);
        do {
            addedVal = false;
            for (String rhs : result.values()) {
                int dotIdx = rhs.indexOf('.');
                if (dotIdx < rhs.length() - 1) {
                    String B = rhs.charAt(dotIdx + 1) + "";

                    if (nonTerminals.contains(B)) {
                        for (Pair<Integer, String> value : productions.get(B)) {
                            boolean dotExists2 = false;
                            for (String val : result.get(B)) {
                                if (val.equals("." + value.getValue())) {
                                    dotExists2 = true;
                                }
                            }

                            if (!dotExists2) {
                                result.put(B, "." + value.getValue());
                                addedVal = true;
                            }

                        }
                    }
                }
            }
        } while (addedVal);

        return result;
    }

    public static Multimap<String, String> utilGoto(Multimap<String, String> input, String X) {
        Multimap<String, String> result = HashMultimap.create();

        for (Map.Entry<String, String> rhs : input.entries()) {
            String rhsReplaced = rhs.getValue().replace("." + X, X + ".");
            if (!rhs.getValue().equals(rhsReplaced)) {
                result.put(rhs.getKey(), rhsReplaced);
            }
        }
        return result;
    }

    public static Multimap<String, String> realGoto(Multimap<String, String> input, String X) {
        Multimap<String, String> closureParam = utilGoto(input, X);
        return clossure(closureParam);
    }

    public static Multimap<String, Multimap<String, String>> createCanonicalSet() {
        //buildTable();

        gotoMap = new HashMap<>();

        Multimap<String, String> startingProduction = HashMultimap.create();
        Multimap<String, String> s0;
        Multimap<String, Multimap<String, String>> result = HashMultimap.create();
        startingProduction.put("K", "." + startingSymbol);

        List<String> termianlsAndNontermianls = new ArrayList<>(terminals);
        termianlsAndNontermianls.addAll(nonTerminals);

        s0 = clossure(startingProduction);
        result.put("s0", s0);
        int i = 0;
        boolean cModified = false;
        do {
            cModified = false;
            Multimap<String, Multimap<String, String>> temp = HashMultimap.create();
            for (Map.Entry<String, Multimap<String, String>> keyValue : result.entries()) {

                List<Pair<String, String>> l = new ArrayList<>();

                for (String X : termianlsAndNontermianls) {
                    Multimap<String, String> s = realGoto(keyValue.getValue(), X);
                    if (!s.isEmpty() && !result.containsValue(s)) {
                        i++;
                        temp.put("s" + i, s);
                        l.add(new Pair<>(X, "" + i));
                        cModified = true;

                        //table.put(new Pair<>("s" + i, X), keyValue.getKey());
                    }
                }
                //System.out.println(l.size());
                gotoMap.put(keyValue.getKey(), l);
            }
            result.putAll(temp);

        } while (cModified);

        System.out.println(gotoMap);

        return result;
    }

    public static void buildTable() {
        Multimap<String, Multimap<String, String>> canSet = Grammar.createCanonicalSet();


        List<String> termianlsAndNontermianls = new ArrayList<>(terminals);
        termianlsAndNontermianls.addAll(nonTerminals);
        termianlsAndNontermianls.add("Action");

        for (String state : canSet.keySet()) {
            for (String elem : termianlsAndNontermianls) {
                table.put(new Pair<>(state, elem), "");
            }
        }
    }
}
