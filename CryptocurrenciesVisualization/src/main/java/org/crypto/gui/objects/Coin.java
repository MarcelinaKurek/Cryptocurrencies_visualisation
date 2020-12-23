package org.crypto.gui.objects;

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
        return score + 1;
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

}
