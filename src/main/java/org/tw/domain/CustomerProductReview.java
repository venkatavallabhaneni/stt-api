package org.tw.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProductReview {

    private Long id;
    private String productId;
    private String customerId;
    private Transcription review;
    private String date;

    public boolean hasCussWords(){



    }

}
