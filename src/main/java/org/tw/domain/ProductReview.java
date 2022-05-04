package org.tw.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Data
@NoArgsConstructor
@Entity(name="Customer_Product_Reviews")
public class CustomerProductReview {

    @Id
    @SequenceGenerator(name = "productsSeqGen", sequenceName = "seq_id_products", initialValue = 5, allocationSize = 100)
    @GeneratedValue(generator = "productsSeqGen")
    private Long id;
    private String productId;
    private String customerId;
    private Transcription review;
    private String date;
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
