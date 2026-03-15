package org.tedros.it.tools.ejb.mongo;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.tedros.it.tools.model.ActivitySummaryDTO;
import org.tedros.it.tools.model.ProductivityActivityDTO;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;

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
    
    public void saveAll(List<ProductivityActivityDTO> activities) {
        if (activities == null || activities.isEmpty()) {
            return;
        }

        List<Document> documents = new ArrayList<>();
        for (ProductivityActivityDTO activity : activities) {
            Document doc = new Document()
                    .append("timestamp", toDate(activity.getTimestamp()))
                    .append("username", activity.getUsername())
                    .append("userId", activity.getUserId())
                    .append("activeWindowTitle", activity.getActiveWindowTitle())
                    .append("mouseEventCount", activity.getMouseEventCount())
                    .append("keyboardEventCount", activity.getKeyboardEventCount());
            documents.add(doc);
        }

        // A mágica acontece aqui: Uma única chamada ao banco para N registros!
        getCollection().insertMany(documents);
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

        for (Document doc : getCollection().find(filter).sort(Sorts.ascending("timestamp"))) {
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

    public List<ActivitySummaryDTO> getWindowUsageSummary(Long userId, LocalDateTime start, LocalDateTime end) {
        Bson match = Aggregates.match(Filters.and(
                Filters.eq("userId", userId),
                Filters.gte("timestamp", toDate(start)),
                Filters.lte("timestamp", toDate(end))));

        Bson group = Aggregates.group("$activeWindowTitle",
                Accumulators.sum("activeCount",
                        new Document("$cond", Arrays.asList(
                                new Document("$gt", Arrays.asList(
                                        new Document("$add", Arrays.asList(
                                                new Document("$ifNull", Arrays.asList("$mouseEventCount", 0)),
                                                new Document("$ifNull", Arrays.asList("$keyboardEventCount", 0)))),
                                        0)),
                                1, 0))),
                Accumulators.sum("inactiveCount",
                        new Document("$cond", Arrays.asList(
                                new Document("$eq", Arrays.asList(
                                        new Document("$add", Arrays.asList(
                                                new Document("$ifNull", Arrays.asList("$mouseEventCount", 0)),
                                                new Document("$ifNull", Arrays.asList("$keyboardEventCount", 0)))),
                                        0)),
                                1, 0))));

        Bson addFields = new Document("$addFields", new Document("total",
                new Document("$add", Arrays.asList("$activeCount", "$inactiveCount"))));

        Bson sort = Aggregates.sort(Sorts.descending("total"));

        List<ActivitySummaryDTO> results = new ArrayList<>();
        for (Document doc : getCollection().aggregate(Arrays.asList(match, group, addFields, sort))) {
            results.add(new ActivitySummaryDTO(
                    doc.getString("_id"),
                    safeGetLong(doc, "activeCount"),
                    safeGetLong(doc, "inactiveCount")));
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
