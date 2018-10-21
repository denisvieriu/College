package ro.ubb.socket.common;

import java.util.concurrent.Future;

public interface HelloService {
    String SERVER_HOST = "localhost";
    int SERVER_PORT = 1234;

    String SAY_HELLO = "sayHello";

    Future<String> sayHello(String name);
}
