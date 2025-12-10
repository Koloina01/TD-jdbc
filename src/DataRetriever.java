import java.sql.Statement;
import java.time.Instant;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever {

    private DBConnection dbConnection;

    public DataRetriever() {
        this.dbConnection = new DBConnection();
    }

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT id, name FROM categories";

        try (Connection conn = dbConnection.getDBConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                categories.add(new Category(
                        rs.getInt("id"),
                        rs.getString("name")));
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving categories: " + e.getMessage());
        }

        return categories;
    }

    public List<Product> getProductList(int page, int size) {
        List<Product> products = new ArrayList<>();
        int offset = (page - 1) * size;
        String sql = "SELECT p.id, p.name, p.creationDatetime, c.id AS category_id, c.name AS category_name " +
                "FROM product p " +
                "JOIN category c ON p.category_id = c.id " +
                "ORDER BY p.id " +
                "LIMIT ? OFFSET ?";

        try (Connection conn = dbConnection.getDBConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, size);
            stmt.setInt(2, offset);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getTimestamp("creationDatetime").toInstant(), 
                        new Category(
                                rs.getInt("category_id"),
                                rs.getString("category_name"))));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving products: " + e.getMessage());
        }

        return products;
    }

    public List<Product> getProductsByCriteria(String productName, String categoryName, Instant creationMin,
            Instant creationMax) {
        List<Product> products = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT p.id, p.name, p.creationDatetime, c.id AS category_id, c.name AS category_name " +
                        "FROM product p " +
                        "JOIN category c ON p.category_id = c.id " +
                        "WHERE 1=1");

        if (productName != null)
            sql.append(" AND p.name ILIKE ?");
        if (categoryName != null)
            sql.append(" AND c.name ILIKE ?");
        if (creationMin != null)
            sql.append(" AND p.creationDatetime >= ?");
        if (creationMax != null)
            sql.append(" AND p.creationDatetime <= ?");

        try (Connection conn = dbConnection.getDBConnection();
                PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            int index = 1;
            if (productName != null)
                stmt.setString(index++, "%" + productName + "%");
            if (categoryName != null)
                stmt.setString(index++, "%" + categoryName + "%");
            if (creationMin != null)
                stmt.setTimestamp(index++, Timestamp.from(creationMin));
            if (creationMax != null)
                stmt.setTimestamp(index++, Timestamp.from(creationMax));

            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                products.add(new Product(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getTimestamp("creationDatetime").toInstant(),
                        new Category(
                                result.getInt("category_id"),
                                result.getString("category_name"))));
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving products by criteria: " + e.getMessage());
        }

        return products;
    }

    public List<Product> getProductsByCriteria(String productName, String categoryName, Instant creationMin,
            Instant creationMax, int page, int size) {
        List<Product> filtered = getProductsByCriteria(productName, categoryName, creationMin, creationMax);

        int fromIndex = (page - 1) * size;
        int toIndex = Math.min(fromIndex + size, filtered.size());

        if (fromIndex >= filtered.size())
            return new ArrayList<>();

        return filtered.subList(fromIndex, toIndex);
    }
}
