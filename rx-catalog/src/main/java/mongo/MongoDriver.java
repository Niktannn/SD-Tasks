package mongo;

import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoClients;
import com.mongodb.rx.client.MongoCollection;
import com.mongodb.rx.client.Success;
import org.bson.Document;
import rx.Observable;
import static com.mongodb.client.model.Filters.eq;

public class MongoDriver {
    private static MongoClient client;

    private static final String DATABASE = "rx-catalog";

    public MongoDriver() {
        client = MongoClients.create("mongodb://localhost:27017");
    }

    public Observable<Success> addUser(User user) {
        return getUserCollection().insertOne(user.toDocument());
    }

    public Observable<Success> addProduct(Product product) {
        return getProductCollection().insertOne(product.toDocument());
    }

    public Observable<Product> getAllProducts() {
        return getProductCollection().find().toObservable().map(Product::new);
    }

    public Observable<User> getUser(int id) {
        return getUserCollection().find(eq(User.ID, id)).toObservable().map(User::new);
    }

    private MongoCollection<Document> getUserCollection() {
        return client.getDatabase(DATABASE).getCollection("user");
    }

    private MongoCollection<Document> getProductCollection() {
        return client.getDatabase(DATABASE).getCollection("product");
    }
}