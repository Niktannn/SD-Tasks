package mongo;

public enum Currency {
    RUB(0.0001), USD(1.0), EUR(1.001);

    private final double exchangeRate;

    Currency(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public static Currency getCurrency(String code) {
        return Currency.valueOf(code);
    }

    public double convert(double value, Currency to) {
        if (this == to)
            return value;
        return value * this.exchangeRate / to.exchangeRate;
    }
}
