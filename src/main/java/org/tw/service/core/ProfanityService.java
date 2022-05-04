package org.tw.service.core;

import org.tw.domain.SanitizedText;

public interface ProfanityService {

    SanitizedText censor(String text);
}
