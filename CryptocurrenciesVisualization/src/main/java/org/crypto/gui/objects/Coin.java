package org.crypto.gui.objects;

<<<<<<< HEAD
import javafx.scene.image.Image;


public class Coin {

    private String name;

    private String symbol;

    private String score;

    private String market_cap;

    private Image logo;

    private String mktCap;

    private String price;

    private String _24h;

    private String _24hVolume;

    public Coin(String name, String symbol, String score, String market_cap, String logo, String mktCap, String price) {
        this.name = name;
        this.symbol = symbol;
        this.score = score;
        this.market_cap = market_cap;
        this.mktCap = mktCap;
        this.price = price;
    }

    public String getMktCap() {
        return mktCap;
    }

    public String getPrice() {
        return price;
    }

    public Coin(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getScore() {
        return score;
    }

    public String getMarket_cap() {
        return market_cap;
    }

    public String get_24h() {
        return _24h;
    }

    public String get_24hVolume() {
        return _24hVolume;
    }

    public Coin(String name, String symbol, String score, String market_cap, String logo, String mktCap, String price, String _24h, String _24hVolume) {
        this.name = name;
        this.symbol = symbol;
        this.score = score;
        this.market_cap = market_cap;
        this.mktCap = mktCap;
        this.price = price;
        this._24h = _24h;
        this._24hVolume = _24hVolume;
    }

    public Image getLogo() {
        logo = new Image("/stylesheets/bitcoin_logo.png");
        return logo;
    }

=======
public class Coin {

    private final String id;
    private final String name;
    private final String symbol;
    private final String imageUrl;
    private int marketCapRank;

    private int score = -1;
    private Double currentPrice = null;
    private int marketCap = -1;
    private Double priceChangePercentage24h = null;

    public Coin(String id, String name, String symbol, String imageUrl, int marketCapRank) {
        this.id = id;
        this.name = name;
        this.symbol = symbol.toUpperCase();
        this.imageUrl = imageUrl;
        this.marketCapRank = marketCapRank;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getMarketCapRank() {
        return marketCapRank;
    }

    public int getScore() {
        return score;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public int getMarketCap() { return marketCap; }

    public Double getPriceChangePercentage24h() { return priceChangePercentage24h; }

    public void setScore(int score) {
        this.score = score;
    }

    public void setCurrentPrice(Double currentPrice) { this.currentPrice = currentPrice; }

    public void setMarketCap(int marketCap) {
        this.marketCap = marketCap;
    }

    public void setPriceChangePercentage24h(Double priceChangePercentage24h) {
        this.priceChangePercentage24h = Math.round(priceChangePercentage24h * 100.0) / 100.0;
    }

    public void setMarketCapRank(int marketCapRank) {
        this.marketCapRank = marketCapRank;
    }
>>>>>>> master
}
