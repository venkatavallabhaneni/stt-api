package org.tw.service;

import org.springframework.stereotype.Component;
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

    private Recognizer recognizer = null;

    public DecodeSpeechToText() {
        recognizer = ModelRecognizer.getRecognizer(Languages.EN_IN);
    }

    public String convert(byte[] audio, String language) throws IOException, UnsupportedAudioFileException {

        LibVosk.setLogLevel(LogLevel.INFO);

        StringBuilder badbuilder = new StringBuilder();
        StringBuilder goodbuilder = new StringBuilder();
        InputStream ais = AudioSystem.getAudioInputStream(new ByteArrayInputStream(audio));
        int nbytes;
        byte[] b = new byte[4096];
        while ((nbytes = ais.read(b)) >= 0) {
            if (recognizer.acceptWaveForm(b, nbytes)) {
                goodbuilder.append(recognizer.getResult());
            } else {
                badbuilder.append(recognizer.getPartialResult());
            }
        }

        goodbuilder.append(recognizer.getFinalResult());

        return goodbuilder.toString();

    }
}
