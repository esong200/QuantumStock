import java.util.List;
import java.util.Map;

import org.patriques.*;
import org.patriques.input.timeseries.Interval;
import org.patriques.output.AlphaVantageException;
import org.patriques.output.timeseries.IntraDay;
import org.patriques.output.timeseries.data.StockData;


public class AlphaVantageCollector{
  static String apiKey = "EL09735SU2ZIYXW5";
  static int timeout = 3000;
  static AlphaVantageConnector apiConnector = new AlphaVantageConnector(apiKey, timeout);
  TimeSeries stockTimeSeries = new TimeSeries(apiConnector);

  public ArrayList<double[]> monthlyData (String symbol){
    //returns montly oepn high low close volume
    ArrayList<double[]> data = new ArrayList<double[5]>()
    try {
      Monthly response = stockTimeSeries.monthly(symbol);
      Map<String, String> metaData = response.getMetaData();
      System.out.println("Information: " + metaData.get("1. Information"));
      System.out.println("Stock: " + metaData.get("2. Symbol"));

      List<StockData> stockData = response.getStockData();
      stockData.forEach(stock -> {
        double[] inputData = {0,0,0,0,0};
        System.out.println("open:   " + stock.getOpen());
        inputData[0] = stock.getOpen();
        System.out.println("high:   " + stock.getHigh());
        inputData[1] = stock.getHigh();
        System.out.println("low:    " + stock.getLow());
        inputData[2] = stock.getLow();
        System.out.println("close:  " + stock.getClose());
        inputData[3] = stock.getClose();
        System.out.println("volume: " + stock.getVolume());
        inputData[4] = stock.getVolume();
        data.append(inputData);
      });

    } catch (AlphaVantageException e) {
      System.out.println("something went wrong");
  }
  return data;
  }

  public ArrayList<double> simpleMovingAvg (String symbol){
    ArrayList<double> SMA = new ArrayList<double);
    try {
      //Monthly response = stockTimeSeries.monthly(symbol);
      //Need formula for each
      Map<String, String> metaData = response.getMetaData();
      System.out.println("Information: " + metaData.get("1. Information"));
      System.out.println("Stock: " + metaData.get("2. Symbol"));

      List<StockData> stockData = response.getStockData();
      stockData.forEach(stock ->{
        SMA.append(stock.getSMA);
      })
  }
  catch (AlphaVantageException e) {
    System.out.println("something went wrong SMA");
}
  return SMA;
}
  public ArrayList<double> expoMovAvg(String symbol){
    ArrayList<double> EMA = new ArrayList<double);
    try {
      //Monthly response = stockTimeSeries.monthly(symbol);
      //Need the formula for each
      Map<String, String> metaData = response.getMetaData();
      System.out.println("Information: " + metaData.get("1. Information"));
      System.out.println("Stock: " + metaData.get("2. Symbol"));

      List<StockData> stockData = response.getStockData();
      stockData.forEach(stock ->{
        EMA.append(stock.getEMA);
      })
  }
  catch (AlphaVantageException e) {
    System.out.println("something went wrong EMA");
}
  return EMA;
  }
}
