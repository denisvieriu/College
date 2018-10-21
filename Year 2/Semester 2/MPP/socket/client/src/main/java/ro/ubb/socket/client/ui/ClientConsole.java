package ro.ubb.socket.client.ui;

import ro.ubb.socket.common.HelloService;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ClientConsole {
    private HelloService helloService;

    public ClientConsole(HelloService helloService) {
        this.helloService = helloService;
    }

    public void runConsole() {
        sayHello();
    }

    private void sayHello() {
        String name = "gigi";
        Future<String> res = helloService.sayHello(name);

        try {
            System.out.println(res.get());//blocking :(
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
