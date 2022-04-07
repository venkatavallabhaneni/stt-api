package org.tw.service;

public interface STTService {

    String convertOffline(byte[] audio, String language);

    String convertOnline(byte[] audio, String language);

}
