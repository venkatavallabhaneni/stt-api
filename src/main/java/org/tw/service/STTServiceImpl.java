package org.tw.service;

import org.apache.commons.lang3.StringUtils;
import org.tw.domain.Transcription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class STTServiceImpl implements STTService {

    @Autowired
    private DecodeSpeechToText decodeSpeechToText;

    @Autowired
    private STTVoskClient voskClient;

    public STTServiceImpl(DecodeSpeechToText decodeSpeechToText,STTVoskClient voskClient) {
        this.decodeSpeechToText = decodeSpeechToText;
        this.voskClient=voskClient;
    }

    private Random random = new Random();

    @Override
    public String convertOffline(byte[] audio,String language) {
        try {
            return decodeSpeechToText.convert(audio,language);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
        return StringUtils.EMPTY;
    }

    @Override
    public String convertOnline(byte[] audio, String language) {
        try {
            List<String> response = voskClient.transcribe(audio);

            return response.get(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return StringUtils.EMPTY;
    }




}
