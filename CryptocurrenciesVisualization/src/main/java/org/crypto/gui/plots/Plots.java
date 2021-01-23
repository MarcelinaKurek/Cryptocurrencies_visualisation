package org.crypto.gui.plots;

import org.crypto.services.APIClient;
import tech.tablesaw.api.*;
import tech.tablesaw.plotly.api.*;
import tech.tablesaw.plotly.components.*;
import tech.tablesaw.plotly.components.change.Decreasing;
import tech.tablesaw.plotly.components.change.Increasing;
import tech.tablesaw.plotly.traces.ScatterTrace;
import tech.tablesaw.plotly.traces.Trace;

import java.text.DecimalFormat;
import java.text.NumberFormat;
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

    /**
     * Funkcja przygotowuje wykres, z którego zczytuje kod w javascript, a nastepnie zwraca
     * kod html wyświetlający wykres.
     * @param figure - wykres */
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
        
        return TimeSeriesPlot.create(id.toUpperCase() + " exchange rate growth", t, "date", yaxis);
    }

    public static Figure ExchangeRateDiffPlot(String id, String currency, int days){
        Date[] dates = APIClient.getMarketChart(id, currency, days).keySet().toArray(new Date[0]);
        Double[] values = APIClient.getMarketChart(id, currency, days).values().toArray(new Double[0]);

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

        return TimeSeriesPlot.create(id.toUpperCase() + " exchange rate growth", t3, "date", "values");
    }

    public static Figure CandleStickPlot(String id, String currency, int days){
        Map<Date, List<Double>> data = APIClient.getOHLC(id, currency, days);
        Date[] dates = data.keySet().toArray(new Date[0]);

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

        DateTimeColumn x1 = null;
        DateColumn x2 = null;
        if (days == 1){
            LocalDateTime[] datetimes = new LocalDateTime[dates.length];
            for (int i = 1; i < dates.length; i++){
                LocalDateTime ldt = convertToLocalDateTimeViaInstant(dates[i]);
                datetimes[i] = ldt;
            }
            x1 = DateTimeColumn.create("date", datetimes);
        }
        else{
            LocalDate[] datatime = new LocalDate[dates.length];
            int j = 0;
            for (Date t2 : dates){
                LocalDate dt2 = convertToLocalDateViaInstant(t2);
                datatime[j] = dt2;
                j++;
            }
            x2 = DateColumn.create("date",  datatime);
        }
        DoubleColumn yO = DoubleColumn.create("open value", open);
        DoubleColumn yH = DoubleColumn.create("max value", high);
        DoubleColumn yL = DoubleColumn.create("min value", low);
        DoubleColumn yC = DoubleColumn.create("close value", close);
        Table ohlc = Table.create("Wykres OHLC");
        ohlc.addColumns(days == 1? x1:x2 , yO, yH, yL, yC);

        Trace trace;
        Layout layout;
        if (days == 1){
            trace =  ScatterTrace.builder(x1, yO, yH, yL, yC)
                    .increasing(Increasing.builder().build())
                    .decreasing(Decreasing.builder().build())
                    .whiskerWidth(0.5)
                    .type("candlestick")
                    .build();
        }
        else {
            Table sum1 = ohlc.summarize("max value", max).by("date");
            Table sum2 = ohlc.summarize("min value", min).by("date");
            Table open1 = ohlc.summarize("open value", first).by("date");
            Table close1 = ohlc.summarize("close value", last).by("date");

            trace =  ScatterTrace.builder(sum1.column("date"), (DoubleColumn) open1.column("First [open value]"), (DoubleColumn) sum1.column("Max [max value]"), (DoubleColumn) sum2.column("Min [min value]"), (DoubleColumn) close1.column("Last [close value]"))
                    .increasing(Increasing.builder().build())
                    .decreasing(Decreasing.builder().build())
                    .whiskerWidth(0.5)
                    .mode(ScatterTrace.Mode.MARKERS)
                    .showLegend(false)
                    .type("candlestick")
                    .build();
        }

        String day = days > 1 ? "days" : "day";
        layout = Layout.builder()
                .title(id.toUpperCase() + " price for the last " + days + " " + day)
                .height(650)
                .width(800)
                .build();

        return new Figure(layout, trace);
    }


    public static String formatNumber(long n){
        NumberFormat numFormat;
        numFormat = new DecimalFormat("0.####E0");
        return numFormat.format(n).replace("E", " x 10^");
    }
}
