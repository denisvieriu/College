package View;

import Controller.Controller;
import java.util.Scanner;
import Model.*;
import Exception.*;

public class UI {
    private Controller ctrl;

    public UI(Controller _c)
    {
        ctrl = _c;
    }

    private void showMenu()
    {
        System.out.println("Available commands:");
        System.out.println("\t 0 - Exit");
        System.out.println("\t 1 - Add a vechicle");
        System.out.println("\t 2 - Remove a vechicle");
        System.out.println("\t 3 - List vechiles with a specific color");
        System.out.println("\t 4 - List all vechicles");
    }

    private boolean addCar()
    {
        System.out.println("What would you like to add? (Car, Bike, Moto)");
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        String s2;
        int i;

        Auto a = null;
        switch (s)
        {
            case "Car":
                System.out.println("Enter the color of the car:");
                s = sc.next();
                System.out.println("Enter the type of the car:");
                s2 = sc.next();
                a = new Car(s, s2);
                break;
            case "Bike":
                System.out.println("What color would you like the bike?");
                s = sc.next();
                System.out.println("What is the weight of the bike?");
                i = sc.nextInt();
                System.out.println("what is the type of the bike?");
                s2 = sc.next();
                a = new Bike(s, i, s2);
                break;
            case "Moto":
                System.out.println("Enter the color of the moto:");
                s = sc.next();
                System.out.println("Enter the max speed of the moto:");
                s2 = sc.next();
                a = new Moto(s, s2);
            default:
                return false;
        }

        return ctrl.addAuto(a);
    }


    private boolean deleteAuto()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("What would you like to delete? (Car, Moto, Bike)");
        String s = sc.next();
        String s2;
        String s3;
        Auto a = null;
        int i;
        boolean val;

        switch (s)
        {
            case "Car":
                System.out.println("What type of car would you like to delete?");
                s2 = sc.next();
                a = new Car("", s2);
                val = ctrl.removeAuto(a);
                break;
            case "Bike":
                System.out.println("What is the weight of the bike you want to delete?");
                i = sc.nextInt();
                System.out.println("What is the type of the bike you want to delete?");
                s3 = sc.next();
                a = new Bike("", i, s3);
                val = ctrl.removeAuto(a);
                break;
            case "Moto":
                System.out.println("What is the speed of the moto you want to delete?");
                s2 = sc.next();
                a = new Moto("", s2);
                val = ctrl.removeAuto(a);
                break;
            default:
                return false;
        }
        return val;
    }


    private void filterByColor()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("What color would you like to filter by?");
        String s = sc.next();
        ctrl.display(s);
    }

    private void showAll()
    {
        ctrl.displayAll();
    }

    public void menu()
    {
        int command, i;
        Scanner sc = new Scanner(System.in);
        boolean val;
        while(true)
        {
            val = true;
            showMenu();
            System.out.println("Enter a number: ");
            i = sc.nextInt();
            if (i == 0)
            {
                break;
            }
            try {
                switch (i) {
                    case 1:
                        val = addCar();
                        break;
                    case 2:
                        val = deleteAuto();
                        break;
                    case 3:
                        filterByColor();
                        break;
                    case 4:
                        showAll();
                        break;
                }
                if (val == false)
                {
                    throw new WordContainsException("Some error occured");
                }
            }
            catch (WordContainsException e)
            {
                System.out.println(e);
            }
        }

    }



}
