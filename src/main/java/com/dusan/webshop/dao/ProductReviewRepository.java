package com.dusan.webshop.dao;

import com.dusan.webshop.entity.ProductReview;
import com.dusan.webshop.entity.ProductReviewPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductReviewRepository extends JpaRepository<ProductReview, ProductReviewPK> {
}
