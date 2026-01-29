package com.example.cqrs.query.dao;

import com.example.cqrs.query.dto.ProductDisplay;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcProductsDao implements ProductsDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcProductsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ProductDisplay findById(Long productId) {
        String sql = """
        SELECT p.id, p.name, p.description, p.unit_price,
               CASE WHEN 0 = 1 THEN true ELSE false END as out_of_stock,
               COALESCE(AVG(r.rating), 0) as user_rating
        FROM products p
        LEFT JOIN product_ratings r ON r.product_id = p.id
        WHERE p.id = ?
        GROUP BY p.id, p.name, p.description, p.unit_price
    """;

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                new ProductDisplay(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getLong("unit_price"),
                        rs.getBoolean("out_of_stock"),
                        rs.getDouble("user_rating")
                ), productId);
    }

    @Override
    public List<ProductDisplay> findByName(String name) {
        String sql = """
                    SELECT p.id, p.name, p.description, p.unit_price,
                           false as out_of_stock,
                           COALESCE(AVG(r.rating), 0) as user_rating
                    FROM products p
                    LEFT JOIN product_ratings r ON r.product_id = p.id
                    WHERE p.name LIKE ?
                    GROUP BY p.id, p.name, p.description, p.unit_price
                    ORDER BY p.id DESC
                """;
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new ProductDisplay(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getLong("unit_price"),
                        rs.getBoolean("out_of_stock"),
                        rs.getDouble("user_rating")
                ), "%" + name + "%");
    }

}
