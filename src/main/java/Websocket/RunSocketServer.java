package Websocket;

public class RunSocketServer {
    public static void main(String[] args) {
        ServerEnum.init(new WebsocketServer(8090));
    }
}
