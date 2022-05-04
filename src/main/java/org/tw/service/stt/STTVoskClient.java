package org.tw.service.stt;

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
        WebSocket ws = factory.createSocket("ws://2700-cs-605438052129-default.cs-asia-southeast1-yelo.cloudshell.dev/?authuser=0");
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
