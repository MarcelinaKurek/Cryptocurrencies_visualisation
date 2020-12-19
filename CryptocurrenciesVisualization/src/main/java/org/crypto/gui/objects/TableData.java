package org.crypto.gui.objects;

public class TableData {

    private final String id;
    private int marketCap;
    private int marketCapRank;
    private Double currentPrice;
    private String website;
    private long totalVolume;
    private int low24h;
    private int high24h;
    private int ath;
    private int atl;

    public TableData(String id) {
        this.id = id;
    }

    public int getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(int marketCap) {
        this.marketCap = marketCap;
    }

    public int getMarketCapRank() {
        return marketCapRank;
    }

    public void setMarketCapRank(int marketCapRank) {
        this.marketCapRank = marketCapRank;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public long getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(long totalVolume) {
        this.totalVolume = totalVolume;
    }

    public int getLow24h() {
        return low24h;
    }

    public void setLow24h(int low24h) {
        this.low24h = low24h;
    }

    public int getHigh24h() {
        return high24h;
    }

    public void setHigh24h(int high24h) {
        this.high24h = high24h;
    }


    public int getAth() {
        return ath;
    }

    public void setAth(int ath) {
        this.ath = ath;
    }

    public int getAtl() {
        return atl;
    }

    public void setAtl(int atl) {
        this.atl = atl;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "TableData{" +
                "id='" + id + '\'' +
                ", marketCap=" + marketCap +
                ", marketCapRank=" + marketCapRank +
                ", currentPrice=" + currentPrice +
                ", website='" + website + '\'' +
                ", totalVolume=" + totalVolume +
                ", low24h=" + low24h +
                ", high24h=" + high24h +
                ", ath=" + ath +
                ", atl=" + atl +
                '}';
    }
}
