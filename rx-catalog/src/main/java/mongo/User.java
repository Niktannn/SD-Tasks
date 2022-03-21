package mongo;

import org.bson.Document;

import java.util.Map;

public class User {
    private final int id;
    private final String name;
    private final String login;
    private final Currency currency;

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String LOGIN = "login";
    public static final String CURRENCY = "currency";

    public User(Document doc) {
        this(doc.getInteger(ID),
                doc.getString(NAME),
                doc.getString(LOGIN),
                Currency.getCurrency(doc.getString(CURRENCY)));
    }

    public User(int id, String name, String login, Currency currency) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.currency = currency;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "User{" +
                ID + "=" + id +
                ", " + NAME + "='" + name + '\'' +
                ", " + LOGIN + "='" + login + '\'' +
                ", " + CURRENCY + "='" + currency.toString() + '\'' +
                '}';
    }

    public Document toDocument() {
        return new Document(Map.of(
                ID, this.id,
                NAME, this.name,
                LOGIN, this.login,
                CURRENCY, this.currency.toString()
        ));
    }
}