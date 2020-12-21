package org.crypto.gui.objects;

public class TableData {

    private final String id;
    private long marketCap;
    private int marketCapRank;
    private Double currentPrice;
    private String website;
    private long totalVolume;
    private Double low24h;
    private Double high24h;
    private Double ath;
    private Double atl;

    private String imageSmallUrl;
    private String imageLargeUrl;

    public TableData(String id) {
        this.id = id;
    }

    public long getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(long marketCap) {
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

    public Double getLow24h() {
        return low24h;
    }

    public void setLow24h(Double low24h) {
        this.low24h = low24h;
    }

    public Double getHigh24h() {
        return high24h;
    }

    public void setHigh24h(Double high24h) {
        this.high24h = high24h;
    }

    public Double getAth() {
        return ath;
    }

    public void setAth(Double ath) {
        this.ath = ath;
    }

    public Double getAtl() {
        return atl;
    }

    public void setAtl(Double atl) {
        this.atl = atl;
    }

    public String getId() {
        return id;
    }

    public String getImageSmallUrl() {
        return imageSmallUrl;
    }

    public void setImageSmallUrl(String imageSmallUrl) {
        this.imageSmallUrl = imageSmallUrl;
    }

    public String getImageLargeUrl() {
        return imageLargeUrl;
    }

    public void setImageLargeUrl(String imageLargeUrl) {
        this.imageLargeUrl = imageLargeUrl;
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
                ", imageSmallUrl='" + imageSmallUrl + '\'' +
                ", imageLargeUrl='" + imageLargeUrl + '\'' +
                '}';
    }
}
