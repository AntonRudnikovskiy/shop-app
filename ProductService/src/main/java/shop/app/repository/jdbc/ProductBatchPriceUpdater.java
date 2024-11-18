package shop.app.repository.jdbc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.UUID;

import static shop.app.repository.jdbc.JdbcQuery.SQL_FIND_ALL_PRODUCTS_QUERY;
import static shop.app.repository.jdbc.JdbcQuery.SQL_UPDATE_PRICE_QUERY;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ProductBatchPriceUpdater {
    private final JdbcTemplate jdbcTemplate;

    public void updateProductPrices(double percentage, List<UUID> productIds) {
        jdbcTemplate.batchUpdate(SQL_UPDATE_PRICE_QUERY, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setDouble(1, percentage / 100);
                preparedStatement.setObject(2, productIds.get(i));
            }

            @Override
            public int getBatchSize() {
                return productIds.size();
            }
        });
    }

    public List<UUID> fetchProductIds() {
        return jdbcTemplate.query(SQL_FIND_ALL_PRODUCTS_QUERY, (rs, rowNum) -> (UUID) rs.getObject("uuid"));
    }
}
