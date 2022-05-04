package org.tw.app;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.tw.domain.CustomerProductReview;
import org.tw.service.product.ProductReviewService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/tw/stt/productreview")
public class ProductReviewController {

    private static Logger logger = LoggerFactory.getLogger(ProductReviewController.class);

    private final ProductReviewService productReviewService;


    @Autowired
    public ProductReviewController(final ProductReviewService productReviewService) {
        this.productReviewService = productReviewService;
    }


    @Operation(summary = "Get Product Reviews", description = "Get product reviews, for now always latest")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successful")})
    @GetMapping(value = "/v0/{productId}/{customerId}")
    @ResponseBody
    public List<CustomerProductReview> reviews(@PathVariable String productId, @PathVariable String customerId) {

        List<CustomerProductReview> reviews  = new ArrayList<>();
        reviews.add(productReviewService.reviews(customerId,productId));
        return reviews;
    }

    @Operation(summary = "Create a Product Review", description = "Create a Product Review if it can be published and can auto approve text else will be submitted for admin review")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successful")})
    @PostMapping(value = "/v0/", headers = "Accept=Application/json", produces = "application/json", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public CustomerProductReview create(@RequestBody CustomerProductReview aReview) {
        return productReviewService.create(aReview);
    }




}
