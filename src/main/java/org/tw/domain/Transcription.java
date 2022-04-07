package org.tw.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.nio.ByteBuffer;
import java.util.Arrays;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class Transcription {

    private String customerId;
    private String productUpc;
    private Long id;
    private byte[] audio;
    private long chunkId;
    private String text;


    public void add(String newText){

        text = StringUtils.isAllBlank(text)?"".concat(newText): text.concat(newText);

    }



}
