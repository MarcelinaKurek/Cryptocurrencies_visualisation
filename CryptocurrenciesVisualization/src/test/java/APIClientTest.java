import org.crypto.gui.objects.Coin;
import org.crypto.services.APIClient;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

        trending.forEach(coin -> System.out.println(coin.getName()));
    }

    @org.junit.Test
    public void testTop() {
        List<Coin> top = client.getTop("usd");

        top.forEach(coin -> System.out.println("name = " + coin.getName() + ", price = " + coin.getCurrentPrice()));
    }

    // nie bangla url
    @org.junit.Test
    public void testOhlc() {
        Map<Date, List<Double>> map = client.getOHLC("bitcoin", "usd", 7);

        map.forEach((key, value) -> System.out.println("date = " + key + ", ohlc = " + value));
    }
}
