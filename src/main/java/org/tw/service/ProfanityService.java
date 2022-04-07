package org.tw.service;

import org.tw.domain.SanitizedText;

public interface ProfanityService {

    SanitizedText censor(String text);
}
