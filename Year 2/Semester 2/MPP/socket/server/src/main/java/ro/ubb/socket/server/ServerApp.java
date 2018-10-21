package ro.ubb.socket.server;

import ro.ubb.socket.common.HelloService;
import ro.ubb.socket.common.Message;
import ro.ubb.socket.server.service.HelloServiceImpl;
import ro.ubb.socket.server.tcp.TcpServer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ServerApp {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        TcpServer tcpServer = new TcpServer(executorService, HelloService.SERVER_PORT);
        HelloService helloService = new HelloServiceImpl(executorService);

        tcpServer.addHandler(HelloService.SAY_HELLO, (request) -> {
            Future<String> res = helloService.sayHello(request.getBody());
            try {
                String result = res.get();
//                return new Message(Message.OK, result);
                return Message.builder()
                        .header(Message.OK)
                        .body(result)
                        .build();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                //return new Message(Message.ERROR, "");
                return Message.builder()
                        .header(Message.ERROR)
                        .build();
            }
        });

        tcpServer.startServer();

        System.out.println("bye - server");
    }
}
