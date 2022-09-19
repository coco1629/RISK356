package Websocket;

import java.net.InetSocketAddress;
import java.util.Iterator;


import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;


/**
 * websocket Server
 */
public class WebsocketServer extends WebSocketServer{

    public WebsocketServer(int port) {
        super(new InetSocketAddress(port));
    }
    /**
     * Called when closed.
     */
    @Override
    public void onClose(WebSocket ws, int arg1, String arg2, boolean arg3) {
        System.out.println("------------------onClose-------------------");
    }

    /**
     * Called when error occurs.
     */
    @Override
    public void onError(WebSocket ws, Exception e) {
        System.out.println("------------------onError-------------------");
        if(ws != null) {
        }
        e.getStackTrace();
    }

    /**
     * Received message
     */
    @Override
    public void onMessage(WebSocket ws, String msg) {
        System.out.println("Received message："+msg);

        ws.send("Got it.");
        if(ws.isClosed()) {
        } else if (ws.isClosing()) {
            System.out.println("ws is closing...");
        } else if (ws.isConnecting()) {
            System.out.println("ws is connecting...");
        } else if(ws.isOpen()) {
            System.out.println("ws has opened...");
            System.out.println(msg);
        }

    }

    /**
     * The request header information can be obtained by shaking hands
     */
    @Override
    public void onOpen(WebSocket ws, ClientHandshake shake) {
        System.out.println("-----------------onOpen--------------------"+ws.isOpen()+"--"+ws.getReadyState()+"--"+ws.getAttachment());
        for(Iterator<String> it=shake.iterateHttpFields();it.hasNext();) {
            String key = it.next();
            System.out.println(key+":"+shake.getFieldValue(key));
        }
    }
    /**
     * Called when the server successfully starts
     */
    @Override
    public void onStart() {
        System.out.println("------------------onStart-------------------");
    }
}

