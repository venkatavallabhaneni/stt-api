package org.tw.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vosk.Model;
import org.vosk.Recognizer;


public class ModelRecognizer {

    private static Logger logger = LoggerFactory.getLogger(ModelRecognizer.class);


    public static Recognizer getRecognizer(Languages language) {

        Recognizer recognizer = null;
        try (Model modelEnIn = new Model("models/vosk-model-small-en-in-0.4")) {
            recognizer = new Recognizer(modelEnIn, 16000);
        } catch (Exception e) {
            logger.error("ERROR", e);
        }

        return recognizer;
    }


}
