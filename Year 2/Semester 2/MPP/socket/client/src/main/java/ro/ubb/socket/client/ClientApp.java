package ro.ubb.socket.client;

import ro.ubb.socket.client.service.HelloServiceClient;
import ro.ubb.socket.client.tcp.TcpClient;
import ro.ubb.socket.client.ui.ClientConsole;
import ro.ubb.socket.common.HelloService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientApp {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        TcpClient tcpClient = new TcpClient(executorService, HelloService.SERVER_HOST, HelloService.SERVER_PORT);
        HelloService helloService = new HelloServiceClient(executorService, tcpClient);
        ClientConsole clientConsole = new ClientConsole(helloService);
        clientConsole.runConsole();

        executorService.shutdownNow();

        System.out.println("bye - client");
    }
}
