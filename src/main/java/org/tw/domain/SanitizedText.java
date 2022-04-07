package org.tw.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SanitizedText {

    private String text;
    private List<String> swearWords;
}
