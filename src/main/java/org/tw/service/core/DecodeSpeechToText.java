package org.tw.service;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.tw.domain.Text;
import org.vosk.LibVosk;
import org.vosk.LogLevel;
import org.vosk.Recognizer;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class DecodeSpeechToText {

    private static Logger logger = LoggerFactory.getLogger(DecodeSpeechToText.class);
    private Recognizer recognizer = null;

    public DecodeSpeechToText() {
        recognizer = ModelRecognizer.getRecognizer(Languages.EN_IN);
    }

    public String convert(byte[] audio, String language) throws IOException, UnsupportedAudioFileException {

        LibVosk.setLogLevel(LogLevel.INFO);

        Gson gson = new Gson();

        StringBuilder badbuilder = new StringBuilder();
        StringBuilder goodbuilder = new StringBuilder();
        InputStream ais = AudioSystem.getAudioInputStream(new ByteArrayInputStream(audio));
        int nbytes;
        byte[] b = new byte[4096];
        while ((nbytes = ais.read(b)) >= 0) {
            if (recognizer.acceptWaveForm(b, nbytes)) {
                goodbuilder.append(gson.fromJson(recognizer.getResult(), Text.class).getText()+" ");
            } else {
                badbuilder.append(recognizer.getPartialResult());
            }
        }

        goodbuilder.append(gson.fromJson(recognizer.getFinalResult(), Text.class).getText());
        return goodbuilder.toString();

    }


}
