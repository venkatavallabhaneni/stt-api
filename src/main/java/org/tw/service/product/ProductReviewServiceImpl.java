package org.tw.service.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tw.domain.CustomerProductReview;
import org.tw.domain.ProductReview;
import org.tw.domain.Transcription;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class ProductReviewServiceImpl implements ProductReviewService {

    private final ProductReviewDao reviewDao;

    @Autowired
    public ProductReviewServiceImpl(ProductReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }

    private static Logger logger = LoggerFactory.getLogger(ProductReviewServiceImpl.class);


    @Override
    public CustomerProductReview reviews(String customerId, String productId) {

        CustomerProductReview customerProductReview = new CustomerProductReview();

        Optional<ProductReview> reviews = reviewDao.findByProductIdAndCustomerId(productId,customerId);

        if(reviews.isPresent()) {
            map(reviews.get(),customerProductReview);
        }
        return customerProductReview;
    }

    @Override
    public CustomerProductReview create(CustomerProductReview customerProductReview) {

        ProductReview productReviewEntity = new ProductReview();
        map(customerProductReview,productReviewEntity);

        productReviewEntity.setPublished(true);
        if(!customerProductReview.isAllowedToPublish()){
            productReviewEntity.setPublished(false);
        }
        ProductReview productReview1 =  reviewDao.save(productReviewEntity);

         map(productReview1,customerProductReview);

         return customerProductReview;

    }


    private void map(CustomerProductReview customerProductReview, ProductReview productReviewEntity){

        if(customerProductReview==null || productReviewEntity==null){
            return;
        }

        BeanUtils.copyProperties(customerProductReview,productReviewEntity);
        productReviewEntity.setTranscriptionId(customerProductReview.getReview().getId());
        productReviewEntity.setTranscriptionText(customerProductReview.getReview().getText());
    }

    private void map(ProductReview productReviewEntity,CustomerProductReview customerProductReview){

        if(customerProductReview==null || productReviewEntity==null){
            return;
        }
        BeanUtils.copyProperties(productReviewEntity, customerProductReview);
        Transcription transcription = new Transcription();
        transcription.setText(productReviewEntity.getTranscriptionText());
        transcription.setId(productReviewEntity.getTranscriptionId());
        customerProductReview.setReview(transcription);

    }

}
