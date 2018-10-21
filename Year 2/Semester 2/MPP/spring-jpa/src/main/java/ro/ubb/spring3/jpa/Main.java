package ro.ubb.spring3.jpa;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.ubb.spring3.jpa.ui.Console;

/**

 */
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context=
                new AnnotationConfigApplicationContext("ro.ubb.spring3.jpa.config");

        Console console=context.getBean(Console.class);
        console.runConsole();

        System.out.println("bye");
    }
}
