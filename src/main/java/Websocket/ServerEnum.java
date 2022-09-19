package Websocket;

public enum ServerEnum {

    server;

    private static WebsocketServer socketServer = null;

    public static void init(WebsocketServer server) {
        socketServer = server;
        if (socketServer != null) {
            socketServer.start();
        }
    }
}
