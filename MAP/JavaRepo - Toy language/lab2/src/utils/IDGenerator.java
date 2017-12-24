package utils;

public class IDGenerator {
    private static int counter = 1;
    public static int generate_id(){
        return counter++;
    }
}
