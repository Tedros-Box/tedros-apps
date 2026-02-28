package org.tedros.it.tools.ejb.mongo;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.tedros.it.tools.model.ProductivityActivityDTO;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless(name = "ProductivityActivityRepository")
public class ProductivityActivityRepository {

    private static final String COLLECTION_NAME = "user_activities";

    @Inject
    private MongoConnectionManager mongoManager;

    private MongoCollection<Document> getCollection() {
        return mongoManager.getMongoClient()
                .getDatabase(mongoManager.getDatabaseName())
                .getCollection(COLLECTION_NAME);
    }

    public void save(ProductivityActivityDTO activity) {
        Document doc = new Document()
                .append("timestamp", toDate(activity.getTimestamp()))
                .append("username", activity.getUsername())
                .append("userId", activity.getUserId())
                .append("activeWindowTitle", activity.getActiveWindowTitle())
                .append("mouseEventCount", activity.getMouseEventCount())
                .append("keyboardEventCount", activity.getKeyboardEventCount());

        getCollection().insertOne(doc);
    }

    public List<ProductivityActivityDTO> findUserIdAndDateRange(Long userId, LocalDateTime start, LocalDateTime end) {
        Bson filter = Filters.and(
                Filters.eq("userId", userId),
                Filters.gte("timestamp", toDate(start)),
                Filters.lte("timestamp", toDate(end)));

        List<ProductivityActivityDTO> results = new ArrayList<>();

        for (Document doc : getCollection().find(filter)) {
            ProductivityActivityDTO dto = new ProductivityActivityDTO(
                    toLocalDateTime(doc.getDate("timestamp")),
                    doc.getString("username"),
                    doc.getLong("userId"),
                    doc.getString("activeWindowTitle"),
                    safeGetLong(doc, "mouseEventCount"),
                    safeGetLong(doc, "keyboardEventCount"));
            results.add(dto);
        }

        return results;
    }

    private Date toDate(LocalDateTime ldt) {
        if (ldt == null)
            return null;
        return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    }

    private LocalDateTime toLocalDateTime(Date date) {
        if (date == null)
            return null;
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    private long safeGetLong(Document doc, String field) {
        Object val = doc.get(field);
        if (val instanceof Number) {
            return ((Number) val).longValue();
        }
        return 0L;
    }
}
