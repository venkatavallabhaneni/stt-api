package org.tw.service.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tw.domain.SanitizedText;

@Service
public class ProfanityServiceImpl implements ProfanityService {

    private final ProfanityFilter filter;

    @Autowired
    public ProfanityServiceImpl(final ProfanityFilter filter) {
        this.filter = filter;
    }

    @Override
    public SanitizedText censor(String text) {
        return filter.getCensoredText(text);
    }
}
