package org.tw.service;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

@Component
public class STTVoskClient {

    private ArrayList<String> results = new ArrayList<String>();
    private CountDownLatch recieveLatch;

    public ArrayList<String> transcribe(byte[] audio) throws Exception {
        WebSocketFactory factory = new WebSocketFactory();
        WebSocket ws = factory.createSocket("ws://localhost:2700");
        ws.addListener(new WebSocketAdapter() {
            @Override
            public void onTextMessage(WebSocket websocket, String message) {
                results.add(message);
                recieveLatch.countDown();
            }
        });
        ws.connect();

        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(audio));
        byte[] buf = new byte[8000];
        while (true) {
            int nbytes = dis.read(buf);
            if (nbytes < 0) break;
            recieveLatch = new CountDownLatch(1);
            ws.sendBinary(buf);
            recieveLatch.await();
        }
        recieveLatch = new CountDownLatch(1);
        ws.sendText("Final Text {\"eof\" : 1}");
        recieveLatch.await();
        ws.disconnect();

        return results;
    }

}
