package controller;

import mongo.Currency;
import mongo.MongoDriver;
import mongo.Product;
import mongo.User;
import rx.Observable;

import java.util.List;
import java.util.Map;

public class CatalogController {
    private final MongoDriver catalogMongoDriver = new MongoDriver();

    public Observable<String> getResponse(String action,
                                          Map<String, List<String>> parameters) {
        switch (action) {
            case ("add-product"):
                return addProduct(parameters);
            case ("get-products-for-user"):
                return getProductsForUser(parameters);
            case ("register-user"):
                return registerUser(parameters);

        }
        return Observable.just("Unknown action: " + action);
    }


    private Observable<String> getProductsForUser(Map<String, List<String>> parameters) {
        int id = Integer.parseInt(parameters.get(User.ID).get(0));
        return catalogMongoDriver.getUser(id)
                .map(User::getCurrency)
                .flatMap(currency -> catalogMongoDriver.getAllProducts()
                        .map(product -> product.getProductDescription(currency) + '\n'));
    }

    private Observable<String> registerUser(Map<String, List<String>> parameters) {

        int id = Integer.parseInt(parameters.get(User.ID).get(0));
        String name = parameters.get(User.NAME).get(0);
        String login = parameters.get(User.LOGIN).get(0);
        Currency currency = Currency.getCurrency(parameters.get(User.CURRENCY).get(0));
        User user = new User(id, name, login, currency);
        return catalogMongoDriver.addUser(user).
                map(s -> "user registered:" + user);
    }

    private Observable<String> addProduct(Map<String, List<String>> parameters) {
        int id = Integer.parseInt(parameters.get(User.ID).get(0));
        String name = parameters.get(User.NAME).get(0);
        Currency currency = Currency.getCurrency(parameters.get(User.CURRENCY).get(0));
        double price = Double.parseDouble(parameters.get("price").get(0));
        Product product = new Product(id, name, price, currency);
        return catalogMongoDriver.addProduct(product)
                .map(s -> "added product:" + product);
    }
}
