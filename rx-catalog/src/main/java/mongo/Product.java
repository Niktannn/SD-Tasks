package mongo;

import org.bson.Document;

import java.util.Map;

public class Product {
    private final int id;
    private final String name;
    private final double price;
    private final Currency currency;

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String PRICE = "price";
    public static final String CURRENCY = "currency";

    public Product(Document doc) {
        this(doc.getInteger(ID),
                doc.getString(NAME),
                doc.getDouble(PRICE),
                Currency.getCurrency(doc.getString(CURRENCY)));
    }

    public Product(int id, String name, double price, Currency currency) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.currency = currency;
    }

    public String getProductDescription(Currency currency)
    {
        return '<' +
                NAME + ":" + name + ';' +
                PRICE + ":" + this.currency.convert(price, currency)
                + ' ' + currency.toString() +
                '>';
    }

    @Override
    public String toString() {
        return "Product{" +
                ID + "=" + id +
                ", " + NAME + "='" + name + '\'' +
                ", " + PRICE + "='" + price + '\'' +
                ", " + CURRENCY + "='" + currency.toString() + '\'' +
                '}';
    }

    public Document toDocument() {
        return new Document(Map.of(
                ID, this.id,
                NAME, this.name,
                PRICE, this.price,
                CURRENCY, this.currency.toString()
        ));
    }
}