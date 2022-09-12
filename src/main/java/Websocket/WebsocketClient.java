package Websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class WebsocketClient {

    private static Logger logger = LoggerFactory.getLogger(WebsocketClient.class);
    public static WebSocketClient client;
    public static void main(String[] args) {
        try {
            client = new WebSocketClient(new URI("ws://localhost:8090"),new Draft_6455()) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    logger.info("Succeed");
                }

                @Override
                public void onMessage(String msg) {
                    logger.info("Received message: "+msg);
                    if(msg.equals("over")){
                        client.close();
                    }
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    logger.info("Closed");
                }

                @Override
                public void onError(Exception e){
                    e.printStackTrace();
                    logger.info("Error");
                }
            };
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        client.connect();
        //logger.info(client.getDraft());
        while(!client.getReadyState().equals(WebSocket.READYSTATE.OPEN)){
            logger.info("Connecting...");
        }
        //If the connection is successful, the message is sent
        client.send("Hi");
    }

}