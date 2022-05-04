package org.tw.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity(name="Product_Reviews")
public class ProductReview {

    @Id
    @SequenceGenerator(name = "productsSeqGen", sequenceName = "seq_id_products", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "productsSeqGen")
    private Long id;
    private String productId;
    private String customerId;
    private Long transcriptionId;
    private String transcriptionText;
    private Date date;
    private boolean published;
    private Integer stars;



}
