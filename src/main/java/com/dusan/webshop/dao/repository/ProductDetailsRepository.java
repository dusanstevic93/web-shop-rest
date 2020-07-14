package com.dusan.webshop.dao.repository;

import com.dusan.webshop.entity.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ProductDetailsRepository extends JpaRepository<ProductDetails, Long> {

    @Modifying
    @Query(value = "INSERT INTO product_image VALUES(:productId, :imageId, :imageUrl)", nativeQuery = true)
    int insertImage(@Param("productId") long productId, @Param("imageId") String imageId, @Param("imageUrl") String imageUrl);

    @Modifying
    @Query(value = "DELETE FROM product_image WHERE image_id = :imageId", nativeQuery = true)
    int deleteImage(@Param("imageId") String imageId);
}
