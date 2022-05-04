package org.tw.service.product;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tw.domain.SanitizedText;
import org.tw.domain.Transcription;
import org.tw.service.core.DecodeSpeechToText;
import org.tw.service.core.ProfanityService;
import org.tw.service.stt.STTService;
import org.tw.service.stt.STTVoskClient;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.List;
import java.util.Random;

@Service
public class STTServiceImpl implements STTService {

    private static Logger logger = LoggerFactory.getLogger(STTServiceImpl.class);

    private final DecodeSpeechToText decodeSpeechToText;

    private final STTVoskClient voskClient;

    private final ProfanityService profanityService;

    @Autowired
    public STTServiceImpl(final DecodeSpeechToText decodeSpeechToText, final STTVoskClient voskClient,final ProfanityService profanityService) {
        this.decodeSpeechToText = decodeSpeechToText;
        this.voskClient = voskClient;
        this.profanityService=profanityService;
    }

    private Random random = new Random();

    @Override
    public Transcription convertOffline(byte[] audio, String language,boolean sanitize) {


        try {
            String response = decodeSpeechToText.convert(audio, language);
            return new Transcription(RandomUtils.nextLong(),cleanResponse(response,sanitize),null);

        } catch (IOException | UnsupportedAudioFileException e) {
            logger.error(e.getMessage(), e);
        }
        return new Transcription();
    }

    @Override
    public Transcription convertOnline(byte[] audio, String language,boolean sanitize) {
        try {
            List<String> response = voskClient.transcribe(audio);

            return new Transcription(RandomUtils.nextLong(),cleanResponse(response.get(0),sanitize),null);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return new Transcription();
    }

    private String cleanResponse(String responseFromVosk,boolean sanitize){

        if(StringUtils.isBlank(responseFromVosk)){
            return responseFromVosk;
        }

       // responseFromVosk= responseFromVosk.replaceAll("[^a-zA-Z0-9-./]", "");

        if(sanitize) {
            SanitizedText text  = profanityService.censor(responseFromVosk);
            responseFromVosk=text.getText();
        }
        return responseFromVosk;

    }


}
