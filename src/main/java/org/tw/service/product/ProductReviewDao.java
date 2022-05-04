package org.tw.service.product;

import org.springframework.data.repository.CrudRepository;
import org.tw.domain.ProductReview;

import java.util.Optional;

public interface ProductReviewDao extends CrudRepository<ProductReview, Number> {

    public Optional<ProductReview> findByProductIdAndCustomerId(String productId, String customerId);

}
