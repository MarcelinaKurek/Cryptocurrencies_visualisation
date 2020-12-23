package org.crypto.gui.plots;

import org.crypto.services.APIClient;
import tech.tablesaw.api.*;
import tech.tablesaw.columns.Column;
import tech.tablesaw.plotly.Plot;
import tech.tablesaw.plotly.api.BoxPlot;
import tech.tablesaw.plotly.api.CandlestickPlot;
import tech.tablesaw.plotly.api.LinePlot;
import tech.tablesaw.plotly.api.TimeSeriesPlot;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class Plots {

    public LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public void ExchangeRatePlot(String id, String currency, int days ){
        APIClient api = new APIClient();
        Plots plots = new Plots();
        Date[] dates = api.getMarketChart(id, currency, days).keySet().toArray(new Date[0]);
        Double[] values = api.getMarketChart(id, currency, days).values().toArray(new Double[0]);

        LocalDateTime[] datetimes = new LocalDateTime[dates.length];
        int i = 0;
        for (Date t : dates){
            LocalDateTime dt = plots.convertToLocalDateTimeViaInstant(t);
            datetimes[i] = dt;
            i++;
        }

        String yaxis = "value in " + currency;
        DateTimeColumn x1 = DateTimeColumn.create("date", datetimes);
        DoubleColumn y = DoubleColumn.create(yaxis, values);
        Table t = Table.create("Exchange Rate");
        t.addColumns(x1, y);
        Plot.show(TimeSeriesPlot.create("Exchange Rate", t, "date", yaxis));

    }

    public void ExchangeRateDiffPlot(String id, String currency, int days){
        APIClient api = new APIClient();
        Plots plots = new Plots();
        Date[] dates = api.getMarketChart(id, currency, days).keySet().toArray(new Date[0]);
        Double[] values = api.getMarketChart(id, currency, days).values().toArray(new Double[0]);

        double change = 0;
        Double[] ychange = new Double[dates.length];
        LocalDateTime[] datetimes = new LocalDateTime[dates.length];
        ychange[0] = 0.0;
        for (int i = 1; i < dates.length; i++){
            LocalDateTime dt = plots.convertToLocalDateTimeViaInstant(dates[i]);
            datetimes[i] = dt;
            ychange[i] = values[i] - values[i -1];
        }

        DateTimeColumn x1 = DateTimeColumn.create("date", datetimes);
        DoubleColumn y3 = DoubleColumn.create("values", ychange);
        Table t3 = Table.create("Exchange Rate Growth");
        t3.addColumns(x1, y3);
        Plot.show(TimeSeriesPlot.create("Exchange Rate Growth", t3, "date", "values"));
    }

    public void CandleStickPlot(String id, String currency, int days){
        APIClient api = new APIClient();
        Plots plots = new Plots();
        Map<Date, List<Double>> data = api.getOHLC(id, currency, days);
        Date[] dates = data.keySet().toArray(new Date[0]);

        LocalDate[] dt = new LocalDate[dates.length];
        int j = 0;
        for (Date t2 : dates){
            LocalDate dt2 = plots.convertToLocalDateViaInstant(t2);
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
        Plot.show(CandlestickPlot.create("Candlestick Plot", ohlc, "date", "open value", "max value", "min value", "close value" ));

    }

    public static void main(String[] args) throws ParseException {
        Plots plots = new Plots();
        APIClient api = new APIClient();
        plots.ExchangeRatePlot("bitcoin", "usd", 7);
        plots.ExchangeRateDiffPlot("bitcoin", "usd", 7);
        plots.CandleStickPlot("bitcoin", "usd", 14);



    }
}
