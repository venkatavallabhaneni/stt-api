package org.tw.service.stt;

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

        Transcription transcription = new Transcription();
        try {
            String response = decodeSpeechToText.convert(audio, language);

            SanitizedText text = cleanResponse(response,sanitize);
            transcription.setText(text.getText());
            transcription.setId(RandomUtils.nextLong());
            transcription.setCussWords(text.getSwearWords());
            return transcription;

        } catch (IOException | UnsupportedAudioFileException e) {
            logger.error(e.getMessage(), e);
        }
        return transcription;
    }

    @Override
    public Transcription convertOnline(byte[] audio, String language,boolean sanitize) {

        Transcription transcription = new Transcription();
        try {
            List<String> response = voskClient.transcribe(audio);

            SanitizedText text = cleanResponse(response.get(0),sanitize);
            transcription.setText(text.getText());
            transcription.setId(RandomUtils.nextLong());
            transcription.setCussWords(text.getSwearWords());
            return transcription;

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return transcription;
    }

    private SanitizedText cleanResponse(String responseFromVosk,boolean sanitize){

        SanitizedText text = new SanitizedText();

        if(StringUtils.isBlank(responseFromVosk)){
            return null;
        }
        if(sanitize) {
             text  = profanityService.censor(responseFromVosk);

        }
        return text;

    }


}
