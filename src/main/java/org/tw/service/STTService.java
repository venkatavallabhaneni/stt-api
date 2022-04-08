package org.tw.service;

import org.tw.domain.Transcription;

public interface STTService {

    Transcription convertOffline(byte[] audio, String language, boolean sanitize);

    Transcription convertOnline(byte[] audio, String language,boolean sanitize);

}
