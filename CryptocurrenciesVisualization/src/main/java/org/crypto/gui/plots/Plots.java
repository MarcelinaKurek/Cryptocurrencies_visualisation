package org.crypto.gui.plots;

import javafx.scene.Scene;
import org.crypto.services.APIClient;
import tech.tablesaw.api.*;
import tech.tablesaw.columns.Column;
import tech.tablesaw.io.Destination;
import tech.tablesaw.io.html.HtmlWriter;
import tech.tablesaw.plotly.Plot;
import tech.tablesaw.plotly.api.BoxPlot;
import tech.tablesaw.plotly.api.CandlestickPlot;
import tech.tablesaw.plotly.api.LinePlot;
import tech.tablesaw.plotly.api.TimeSeriesPlot;
import tech.tablesaw.plotly.components.Figure;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;


public class Plots {
    public enum PlotType {
        EXCHANGE_RATE_DIFF,
        CANDLE_PLOT,
        EXCHANGE_RATE
    }

    public static String toHtml(Figure figure) {
        String begin = "<!DOCTYPE html>\n" +
                "<html lang=\"en-US\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <script src=\"https://cdn.plot.ly/plotly-latest.min.js\"></script>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div id='target' ></div>";
        String end = "</body>\n" +
                "</html>";

        return begin + figure.asJavascript("target") + end;
    }

    private static LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    private static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static Figure ExchangeRatePlot(String id, String currency, int days ){
        Date[] dates = APIClient.getMarketChart(id, currency, days).keySet().toArray(new Date[0]);
        Double[] values = APIClient.getMarketChart(id, currency, days).values().toArray(new Double[0]);

        LocalDateTime[] datetimes = new LocalDateTime[dates.length];
        int i = 0;
        for (Date t : dates){
            LocalDateTime dt = convertToLocalDateTimeViaInstant(t);
            datetimes[i] = dt;
            i++;
        }

        String yaxis = "value in " + currency;
        DateTimeColumn x1 = DateTimeColumn.create("date", datetimes);
        DoubleColumn y = DoubleColumn.create(yaxis, values);
        Table t = Table.create("Exchange Rate");
        t.addColumns(x1, y);
        return TimeSeriesPlot.create("Exchange Rate", t, "date", yaxis);
    }

    public static Figure ExchangeRateDiffPlot(String id, String currency, int days){
        Date[] dates = APIClient.getMarketChart(id, currency, days).keySet().toArray(new Date[0]);
        Double[] values = APIClient.getMarketChart(id, currency, days).values().toArray(new Double[0]);

        double change = 0;
        Double[] ychange = new Double[dates.length];
        LocalDateTime[] datetimes = new LocalDateTime[dates.length];
        ychange[0] = 0.0;
        for (int i = 1; i < dates.length; i++){
            LocalDateTime dt = convertToLocalDateTimeViaInstant(dates[i]);
            datetimes[i] = dt;
            ychange[i] = values[i] - values[i -1];
        }

        DateTimeColumn x1 = DateTimeColumn.create("date", datetimes);
        DoubleColumn y3 = DoubleColumn.create("values", ychange);
        Table t3 = Table.create("Exchange Rate Growth");
        t3.addColumns(x1, y3);

        return TimeSeriesPlot.create("Exchange Rate Growth", t3, "date", "values");
    }

    public static Figure CandleStickPlot(String id, String currency, int days){
        Map<Date, List<Double>> data = APIClient.getOHLC(id, currency, days);
        Date[] dates = data.keySet().toArray(new Date[0]);

        LocalDate[] dt = new LocalDate[dates.length];
        int j = 0;
        for (Date t2 : dates){
            LocalDate dt2 = convertToLocalDateViaInstant(t2);
            dt[j] = dt2;
            j++;
        }

        Double[] open = new Double[data.values().size()];
        Double[] high = new Double[data.values().size()];
        Double[] low = new Double[data.values().size()];
        Double[] close = new Double[data.values().size()];
        int k = 0;
        for(List<Double> value: data.values()){
            open[k] = value.get(0);
            high[k] = value.get(1);
            low[k] = value.get(2);
            close[k] = value.get(3);
            k++;
        }

        DateColumn x2 = DateColumn.create("date",  dt);
        DoubleColumn yO = DoubleColumn.create("open value", open);
        DoubleColumn yH = DoubleColumn.create("max value", high);
        DoubleColumn yL = DoubleColumn.create("min value", low);
        DoubleColumn yC = DoubleColumn.create("close value", close);
        Table ohlc = Table.create("Wykres OHLC");
        ohlc.addColumns(x2, yO, yH, yL, yC);
        Figure plot = CandlestickPlot.create("Candlestick Plot", ohlc, "date", "open value", "max value", "min value", "close value" );
        //Plot.show(CandlestickPlot.create("Candlestick Plot", ohlc, "date", "open value", "max value", "min value", "close value" ));
        return plot;

    }

    public static void main(String[] args) throws ParseException {
        Plots plots = new Plots();
        APIClient api = new APIClient();
        plots.ExchangeRatePlot("bitcoin", "usd", 7);
        plots.ExchangeRateDiffPlot("bitcoin", "usd", 7);
        plots.CandleStickPlot("bitcoin", "usd", 14);


    }
}
