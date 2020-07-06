package com.dusan.webshop.dao;

import com.dusan.webshop.entity.ProductReview;
import com.dusan.webshop.entity.ProductReviewPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductReviewRepository extends JpaRepository<ProductReview, ProductReviewPK>, JpaSpecificationExecutor<ProductReview> {
}
