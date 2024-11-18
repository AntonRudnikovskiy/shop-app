package shop.app.repository.jdbc;

public class JdbcQuery {
    static final String SQL_UPDATE_PRICE_QUERY = "UPDATE products SET price = price * (1 + ?) WHERE uuid = ?";
    static final String SQL_FIND_ALL_PRODUCTS_QUERY = "SELECT uuid FROM products FOR UPDATE";
}
