package org.crypto.services;

import org.crypto.gui.objects.Coin;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class APIClient {

    private final String sURL = "https://api.coingecko.com/api/v3";

    public APIClient() { }

    // Metody pomocnicze do połączenia się
    private HttpURLConnection connect(URL url) {
        HttpURLConnection con = null;

        try {
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert con != null;
        return con;
    }

    private String read(HttpURLConnection con) {
        StringBuilder content = new StringBuilder();

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            content = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content.toString();
    }

    /**
     * Sprawdza czy API jest online
     *
     * @return Odpowiedź od API
     */
    public JSONObject ping() {
        String jsonString = "";

        try {
            URL url = new URL(sURL + "/ping");
            jsonString = read(connect(url));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return new JSONObject(jsonString);
    }

    /**
     * Zwraca mapę wszystkich dostępnych kryptowalut.
     *
     * @return Mapa w postaci klucz - id kryptowaluty, wartość - nazwa kryptowaluty
     */
    public Map<String, String> getCoinList() {
        String jsonString = "";

        try {
            URL url = new URL(sURL + "/coins/list");
            jsonString = read(connect(url));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        JSONArray arr = new JSONArray(jsonString);
        Map<String, String> ret = new TreeMap<>();

        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);
            ret.put(obj.getString("id"), obj.getString("name"));
        }

        return ret;
    }

    /**
     * Zwraca listę trending kryptowalut.
     *
     * @return 7 elementowa lista obiektów Coin
     */
    public List<Coin> getTrending() {
        String jsonString = "";

        try {
            URL url = new URL(sURL + "/search/trending");
            jsonString = read(connect(url));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        JSONObject obj = new JSONObject(jsonString);
        JSONArray arr = obj.getJSONArray("coins");

        List<Coin> trending = new ArrayList<>();

        for (int i = 0; i < arr.length(); i++) {
            JSONObject coinObj = arr.getJSONObject(i).getJSONObject("item");
            Coin coin = new Coin(coinObj.getString("id"),
                    coinObj.getString("name"),
                    coinObj.getString("symbol"),
                    coinObj.getString("thumb"),
                    coinObj.getInt("market_cap_rank"));

            coin.setScore(coinObj.getInt("score"));

            trending.add(coin);
        }

        return trending;
    }

    /**
     * Zwraca listę najważniejszych kryptowalut
     *
     * @return 7 elementowa lista obiektów Coin
     */
    public List<Coin> getTop(String currency) {
        String jsonString = "";

        try {
            URL url = new URL(sURL + "/coins/markets?vs_currency=" + currency
                    + "&order=market_cap_desc&per_page=7&page=1&sparkline=false");
            jsonString = read(connect(url));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        JSONArray arr = new JSONArray(jsonString);
        List<Coin> coins = new ArrayList<>();

        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);
            Coin coin = new Coin(obj.getString("id"),
                    obj.getString("name"),
                    obj.getString("symbol"),
                    obj.getString("image"),
                    obj.getInt("market_cap_rank"));

            coin.setCurrentPrice(obj.getDouble("current_price"));
            coin.setMarketCap(obj.getInt("market_cap"));
            coin.setPriceChangePercentage24h(obj.getDouble("price_change_percentage_24h"));

            coins.add(coin);
        }

        return coins;
    }

    /**
     * Zwraca w cenę podanej kryptowaluty w podanym przedziale
     *
     * @param id kryptowaluty @see getCoinList
     * @param currency w jakiej walucie ma być np. "usd"
     * @param days zakres danych w dniach
     * @return mapa w formacie klucz - data, wartość - cena
     */

    public Map<Date, Double> getMarketChart(String id, String currency, int days) {
        String jsonString = "";

        try {
            URL url = new URL(sURL + "/coins/" + id + "/market_chart?vs_currency="
                    + currency + "&days=" + days + "&interval=daily");
            jsonString = read(connect(url));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        JSONObject obj = new JSONObject(jsonString);
        JSONArray prices = obj.getJSONArray("prices");
        Map<Date, Double> ret = new TreeMap<>();

        for (int i = 0; i < prices.length(); i++) {
            JSONArray j = prices.getJSONArray(i);
            Date time = new Date(j.getLong(0));
            Double price = j.getDouble(1);

            ret.put(time, price);
        }

        return ret;
    }

    /**
     * Zwraca OHLC danej kryptowaluty do wykresów świecowych.
     *
     * @param id kryptowaluty
     * @param currency w jakiej walucie ma być
     * @param days okres (dostępne wartości 1/7/14/30/90/180/365)
     * @return mapa w postaci klucz - data, wartość - lista OHLC w kolejności (open, high, low, close)
     */
    public Map<Date, List<Double>> getOHLC(String id, String currency, int days) {
        String jsonString = "";

        try {
            URL url = new URL(sURL + "/coins/" + id + "/ohlc?vs_currency="
                    + currency + "&days=" + days);
            jsonString = read(connect(url));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        JSONArray arr = new JSONArray(jsonString);
        Map<Date, List<Double>> ret = new TreeMap<>();

        for (int i = 0; i < arr.length(); i++) {
            JSONArray j = new JSONArray(arr.getJSONArray(i));
            List<Double> ohlc = new ArrayList<>();

            Date time = new Date(j.getLong(0));

            ohlc.add(j.getDouble(1));
            ohlc.add(j.getDouble(2));
            ohlc.add(j.getDouble(3));
            ohlc.add(j.getDouble(4));

            ret.put(time, ohlc);
        }

        return ret;
    }

}
