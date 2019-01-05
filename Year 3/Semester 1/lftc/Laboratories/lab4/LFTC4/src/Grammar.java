import com.google.common.collect.HashMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.util.Pair;

import java.lang.reflect.Array;
import java.util.*;

public class Grammar {
    private static List<String> nonTerminals;
    private static List<String> terminals;
    private static Multimap<String, Pair<Integer, String>> productions;
    private static String startingSymbol;
    private static Map<Pair<String, String>, String> table = new HashMap<>();
    private static Map<String, List<Pair<String, String>>> gotoMap;

    private static Stack<String> workingStack, outputStack, actionStack;
    private static Queue<String> inputQueue;


    static {
        nonTerminals = Arrays.asList("S", "A");
        terminals = Arrays.asList("a", "b", "c");
        productions = HashMultimap.create();
        productions.put("K", new Pair<>(0, "S"));
        productions.put("S", new Pair<>(1, "aA"));
        productions.put("A", new Pair<>(2, "bA"));
        productions.put("A", new Pair<>(3, "c"));

        startingSymbol = "S";

        inputQueue = new ArrayDeque<>();
        workingStack = new Stack<>();
        outputStack = new Stack<>();
        actionStack = new Stack<>();

        inputQueue.add("a");
        inputQueue.add("b");
        inputQueue.add("b");
        inputQueue.add("c");
        inputQueue.add("$");

        workingStack.add("$");
        workingStack.add("0");
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
        boolean[] stateVisited = new boolean[100];
        Arrays.fill(stateVisited, Boolean.FALSE);

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

                List<Pair<String, String>> gotoMapHelper = new ArrayList<>();

                for (String X : termianlsAndNontermianls) {

                    Multimap<String, String> s = realGoto(keyValue.getValue(), X);
                    if (!s.isEmpty() && !result.containsValue(s)) {
                        i++;
                        temp.put("s" + i, s);
                        gotoMapHelper.add(new Pair<>(X, "" + i));
                        cModified = true;
                    } else if (!s.isEmpty() && result.containsValue(s) && !stateVisited[Integer.parseInt(keyValue.getKey().substring(1))]) {
                        String keyTmp = null;
                        for (Map.Entry<String, Multimap<String, String>> keyValueTemp : result.entries()) {
                            if (keyValueTemp.getValue().equals(s)) {
                                keyTmp = keyValueTemp.getKey();
                            }
                        }

                        if (keyTmp != null) {
                            int keyInt = Integer.parseInt(keyTmp.substring(1));
                            gotoMapHelper.add(new Pair<>(X, "" + keyInt));
                        }
                    }
                }
                if (!gotoMap.containsKey(keyValue.getKey())) {
                    gotoMap.put(keyValue.getKey(), gotoMapHelper);
                }

                stateVisited[Integer.parseInt(keyValue.getKey().substring(1))] = true;
            }
            result.putAll(temp);


        } while (cModified);

        for (Map.Entry<String, List<Pair<String, String>>> keyValue : gotoMap.entrySet()) {
            List<Pair<String, String>> value = keyValue.getValue();

            for (Pair<String, String> pair : value) {
                System.out.println("s" + pair.getValue() + "= goto(" + keyValue.getKey() + ", " + pair.getKey() + ")");
            }
        }
        return result;
    }

    static String determineRule(Collection<Multimap<String, String>> stateList) {
        Multimap<String, String> state = stateList.iterator().next();
//        System.out.println(state);

        Boolean allShift = true;
        for (Map.Entry<String, String> keyValue : state.entries()) {

            // Accept rule
            if (keyValue.getKey().equals("K") &&
                    keyValue.getValue().equals(startingSymbol + ".") &&
                    state.size() == 1) {
                return "accept";
            }

            // Reduce
            char lastChar = keyValue.getValue().charAt(keyValue.getValue().length() - 1);
            if (lastChar == '.' && state.size() == 1) {
                return "reduce";
            }

            // Shift
            if (lastChar == '.') {
                allShift = false;
            }
        }

        if (allShift) {
            return "shift";
        }

        return "error";
    }

    public static void buildTable() {
        Multimap<String, Multimap<String, String>> canSet = Grammar.createCanonicalSet();

        System.out.println();
        System.out.println("Canonical set: ");
        System.out.println(canSet);
        System.out.println();

        List<String> termianlsAndNontermianls = new ArrayList<>(terminals);
        termianlsAndNontermianls.addAll(nonTerminals);
        termianlsAndNontermianls.add("action");

        for (String state : canSet.keySet()) {
            for (String elem : termianlsAndNontermianls) {
                table.put(new Pair<>(state, elem), "");
            }

            String rule = determineRule(canSet.get(state));
            switch (rule) {
                case "shift": {
                    List<Pair<String, String>> values = gotoMap.get(state);
                    table.put(new Pair<>(state, "action"), "shift");
                    for (Pair<String, String> elem : values) {
                        table.put(new Pair<>(state, elem.getKey()), elem.getValue());
                    }

                    break;
                }
                case "reduce": {
                    Multimap<String, String> res = canSet.get(state).iterator().next();
                    String key = (String) res.keySet().toArray()[0];
                    String value = res.get(key).iterator().next();

                    for (Map.Entry<String, Pair<Integer, String>> keyValue : productions.entries()) {
                        if (keyValue.getKey().equals(key) &&
                                keyValue.getValue().getValue().equals(value.replace(".", ""))) {
                            int prodNum = keyValue.getValue().getKey();
                            table.put(new Pair<>(state, "action"), "reduce" + prodNum);
                            break;
                        }
                    }

                    break;
                }
                case "accept": {
                    table.put(new Pair<>(state, "action"), "accept");
                    break;
                }
                default: {
                    throw new RuntimeException("Rule conflict");
                }
            }
        }
        System.out.println("________________________________");

        System.out.print("Nr \t");
        for (String elem : termianlsAndNontermianls) {
            System.out.print(elem + "\t");
        }
        System.out.println();

        for (int i = 0; i < canSet.size(); i++) {
            String stateTable = "s" + i;
            System.out.print(i + "\t");
            for (String elem : termianlsAndNontermianls) {
                System.out.print(table.get(new Pair<>(stateTable, elem)) + "\t");
            }
            System.out.println();

        }
    }

    static void lr0() {
        String action;
        int idxReduce = ("reduce").length();

        table.put(new Pair<>("s" + 3, "b"), "3");
        table.put(new Pair<>("s" + 3, "c"), "4");
        do {
            //abbc

            String tempAcc = "";
            System.out.println();
            System.out.println("Working stack: " + workingStack);
            System.out.println("Input queue: " + inputQueue);

            System.out.print("Output stack: [" /*+ outputStack.stream().reduce(tempAcc, (a, b) -> tempAcc + a + b  )*/);
            for (int i = outputStack.size() - 1; i >= 0; i--) {
                if (i != 0)
                    System.out.print(outputStack.elementAt(i) + ", ");
                else
                    System.out.print(outputStack.elementAt(i));
            }
            System.out.print("]");
            System.out.println();

            String headInputQueue;
            if (inputQueue.size() != 1)
                headInputQueue = inputQueue.poll();
            else
                headInputQueue = inputQueue.peek();

            String lastElWorkingSt = workingStack.lastElement();

            action = table.get(new Pair<>("s" + lastElWorkingSt, "action"));
            System.out.println("Action: " + action);

            int reduceNo = -1;
            if (action.contains("reduce")) {
                reduceNo = Integer.parseInt("" + action.substring(idxReduce));
                action = action.substring(0, action.length() - 1);
            }

            switch (action) {
                case "shift": {
                    String coeff = table.get(new Pair<>("s" + lastElWorkingSt, headInputQueue));
                    workingStack.push(headInputQueue);
                    workingStack.push("" + coeff);
                    break;
                }
                case "reduce": {
                    outputStack.push("" + reduceNo);

                    for (Map.Entry<String, Pair<Integer, String>> keyValue : productions.entries()) {
                        Pair<Integer, String> prodRHS = keyValue.getValue();
                        int idxProd = prodRHS.getKey();

                        if (idxProd == reduceNo) {
                            //System.out.println("ReduceNo: " + reduceNo);

                            String parserResult = "";
                            while (!prodRHS.getValue().equals(parserResult)) {
                                workingStack.pop();
                                String var = workingStack.pop();
                                parserResult = var + parserResult;

                                if (!prodRHS.getValue().contains(var)) {
                                    throw new RuntimeException("Can't do reduce");
                                }
                            }

                            String gotoCoeff = workingStack.peek();

                            boolean stopFor = false;
                            for (Map.Entry<String, List<Pair<String, String>>> keyValueGotoMap : gotoMap.entrySet()) {
                                List<Pair<String, String>> value = keyValueGotoMap.getValue();

                                if (stopFor)
                                    break;

                                for (Pair<String, String> pair : value) {
                                    if (keyValueGotoMap.getKey().equals("s" + gotoCoeff) && keyValue.getKey().equals(pair.getKey())) {
                                        String stateCoeff = pair.getValue();
                                        workingStack.push(keyValue.getKey());
                                        workingStack.push(stateCoeff);
                                        stopFor = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    break;
                }
                case "accept": {
                    break;
                }
                default: {
                    action = "accept";
                }
            }
        } while (!action.equals("accept"));

        System.out.println();
        System.out.print("Output stack: [" /*+ outputStack.stream().reduce(tempAcc, (a, b) -> tempAcc + a + b  )*/);
        for (int i = outputStack.size() - 1; i >= 0; i--) {
            if (i != 0)
                System.out.print(outputStack.elementAt(i) + ", ");
            else
                System.out.print(outputStack.elementAt(i));
        }
        System.out.print("]");
        System.out.println();


        System.out.println("Working stack: " + workingStack);
    }
}
