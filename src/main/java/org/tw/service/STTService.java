package org.tw.service;

import org.tw.domain.Transcription;

public interface STTService {

    public String convertOffline(byte[] audio,String language);
    public String convertOnline(byte[] audio,String language);

}
