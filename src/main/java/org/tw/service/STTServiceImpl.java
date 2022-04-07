package org.tw.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.List;
import java.util.Random;

@Service
public class STTServiceImpl implements STTService {

    private static Logger logger = LoggerFactory.getLogger(STTServiceImpl.class);

    @Autowired
    private DecodeSpeechToText decodeSpeechToText;

    @Autowired
    private STTVoskClient voskClient;

    public STTServiceImpl(DecodeSpeechToText decodeSpeechToText, STTVoskClient voskClient) {
        this.decodeSpeechToText = decodeSpeechToText;
        this.voskClient = voskClient;
    }

    private Random random = new Random();

    @Override
    public String convertOffline(byte[] audio, String language) {
        try {
            return decodeSpeechToText.convert(audio, language);
        } catch (IOException | UnsupportedAudioFileException e) {
            logger.error(e.getMessage(), e);
        }
        return StringUtils.EMPTY;
    }

    @Override
    public String convertOnline(byte[] audio, String language) {
        try {
            List<String> response = voskClient.transcribe(audio);

            return response.get(0);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return StringUtils.EMPTY;
    }


}
