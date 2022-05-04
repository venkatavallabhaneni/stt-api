package org.tw.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class CustomerProductReview {


    private Long id;
    private String productId;
    private String customerId;
    private Transcription review;
    private Date date;
    private boolean published;
    private Integer stars;

    public boolean hasCussWords(){

        if(review.getCussWords()!=null && review.getCussWords().size()>0){
            return true;
        }
        return false;
    }

    public boolean isAllowedToPublish(){

        if(hasCussWords()){
            return false;
        }
        return true;
    }

}
