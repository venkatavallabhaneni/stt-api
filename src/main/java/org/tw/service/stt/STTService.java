package org.tw.service.product;

import org.tw.domain.Transcription;

public interface ProductReviewService {

    Transcription convertOffline(byte[] audio, String language, boolean sanitize);

    Transcription convertOnline(byte[] audio, String language,boolean sanitize);

}
