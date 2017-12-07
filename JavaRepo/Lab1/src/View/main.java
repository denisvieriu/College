package View;

import Repository.AutoRepo;
import Repository.ArrayAuto;
import Controller.Controller;
import Model.*;
import View.UI;

public class main {
    public static void main(String[] args)
    {
       AutoRepo v = new ArrayAuto();
       Controller c = new Controller(v);

       c.addAuto(new Bike("red", 12,"FAST"));
       c.addAuto(new Bike("blue", 12,"SLOW"));
       c.addAuto(new Car("red", "ULTRA_FAST"));
       c.addAuto(new Moto("green", "MEDIUM_FAST"));

        //c.display("red");

        UI ui =new UI(c);
        ui.menu();

    }
}
