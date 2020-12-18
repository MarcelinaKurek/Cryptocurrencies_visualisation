package org.crypto.gui.objects;

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

}
