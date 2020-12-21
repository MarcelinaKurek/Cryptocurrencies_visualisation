import org.crypto.gui.objects.Coin;
import org.crypto.gui.objects.TableData;
import org.crypto.services.APIClient;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class APIClientTest {

    private final APIClient client = new APIClient();

    @org.junit.Test
    public void testPing() { System.out.println(client.ping()); }

    @org.junit.Test
    public void testCoinList() {
        Map<String, String> coinList = client.getCoinList();

        coinList.entrySet().stream()
                .limit(10)
                .forEach(i -> System.out.println(i.getKey() + " " + i.getValue()));
    }

    @org.junit.Test
    public void testCoinList2() {
        Map<String, String> coinList = client.getCoinList();

        coinList.entrySet().stream()
                .filter(i -> i.getKey().equals("bitcoin"))
                .forEach(i -> System.out.println("id = " + i.getKey() + ", name = " + i.getValue()));
    }

    @org.junit.Test
    public void testTrending() {
        List<Coin> trending = client.getTrending();

        trending.forEach(coin -> System.out.println(coin.getScore()));
    }

    @org.junit.Test
    public void testTop() {
        List<Coin> top = client.getTop("usd");

        top.forEach(coin -> System.out.println("name = " + coin.getName() + ", imageUrl = " + coin.getImageUrl()));
    }

    @org.junit.Test
    public void testOhlc() {
        Map<Date, List<Double>> map = client.getOHLC("bitcoin", "usd", 7);

        map.forEach((key, value) -> System.out.println("date = " + key + ", ohlc = " + value));
    }

    @org.junit.Test
    public void testMarketChart() {
        Map<Date, Double> map = client.getMarketChart("bitcoin", "usd", 20);

        map.forEach((key, value) -> System.out.println("date = " + key + ", price = " + value));
    }

    @org.junit.Test
    public void testUppercaseSymbol() {
        List<Coin> top = client.getTop("usd");

        top.forEach(coin -> System.out.println(coin.getSymbol()));
    }

    @org.junit.Test
    public void testRoundedPrice() {
        List<Coin> top = client.getTop("usd");

        top.forEach(coin -> System.out.println(coin.getCurrentPrice()));
    }

    @org.junit.Test
    public void testTableData() {
        TableData bitcoinTableData = client.getTableData("bitcoin", "usd");

        System.out.println(bitcoinTableData);
    }

    @org.junit.Test
    public void testVsCurrencies() {
        List<String> li = client.getSupportedCurrencies();

        li.forEach(System.out::println);
    }

    @org.junit.Test
    public void testObscureCoin() {
        TableData tbl = client.getTableData("torcorp", "usd");

        System.out.println(tbl);
    }
}
