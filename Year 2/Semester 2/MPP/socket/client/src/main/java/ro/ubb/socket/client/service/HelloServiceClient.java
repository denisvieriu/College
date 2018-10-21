package ro.ubb.socket.client.service;

import ro.ubb.socket.client.tcp.TcpClient;
import ro.ubb.socket.common.HelloService;
import ro.ubb.socket.common.Message;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class HelloServiceClient implements HelloService {
    private ExecutorService executorService;
    private TcpClient tcpClient;

    public HelloServiceClient(ExecutorService executorService, TcpClient tcpClient) {
        this.executorService = executorService;
        this.tcpClient = tcpClient;
    }

    @Override
    public Future<String> sayHello(String name) {
        return executorService.submit(() -> {
//            Message request = new Message(HelloService.SAY_HELLO, name);
            Message request = Message.builder()
                    .header(HelloService.SAY_HELLO)
                    .body(name)
                    .build();
            Message response = tcpClient.sendAndReceive(request);
            //!!!! err handling
            return response.getBody();
        });

    }
}
