package org.tw.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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


}
