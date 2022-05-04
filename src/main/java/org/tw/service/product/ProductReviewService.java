package org.tw.service.product;

import org.tw.domain.CustomerProductReview;

import java.util.List;

public interface ProductReviewService {

    public CustomerProductReview reviews(String customerId, String productId);
    public CustomerProductReview create(CustomerProductReview aProductReview);
}
