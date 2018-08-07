import java.io.PrintStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.patriques.*;
import org.patriques.input.technicalindicators.SeriesType;
import org.patriques.input.technicalindicators.TimePeriod;
//import org.patriques.input.timeseries.Interval;
import org.patriques.output.AlphaVantageException;
import org.patriques.output.technicalindicators.MACD;
import org.patriques.output.technicalindicators.SMA;
import org.patriques.output.technicalindicators.data.IndicatorData;
import org.patriques.output.timeseries.IntraDay;
import org.patriques.output.timeseries.Monthly;
import org.patriques.input.technicalindicators.Interval;
import org.patriques.output.timeseries.data.StockData;



public class AlphaVantageCollector{
  static String apiKey = "EL09735SU2ZIYXW5";
  static int timeout = 3000;
  static AlphaVantageConnector apiConnector = new AlphaVantageConnector(apiKey, timeout);
  TimeSeries stockTimeSeries = new TimeSeries(apiConnector);
  TechnicalIndicators technicalIndicators = new TechnicalIndicators (apiConnector);
  SMA smaTest = technicalIndicators.sma("MSFT", Interval.DAILY, TimePeriod.of(50), SeriesType.OPEN);

  public ArrayList<double[]> monthlyData (String symbol){
    //returns montly oepn high low close volume
    ArrayList<double[]> data = new ArrayList<double[]>();
    double[] inputData = {0,0,0,0,0};
    try {
      Monthly response = stockTimeSeries.monthly(symbol);
      Map<String, String> metaData = response.getMetaData();
      System.out.println("Information: " + metaData.get("1. Information"));
      System.out.println("Stock: " + metaData.get("2. Symbol"));

      List<StockData> stockData = response.getStockData();
      stockData.forEach(stock -> {
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
        data.add(inputData);

      });

    } catch (AlphaVantageException e) {
      System.out.println("something went wrong");
    }
    data.remove(0);
    return data;
  }

  public ArrayList<Double[]> SMAEMA50 (String symbol){
	  ArrayList<Double[]> returnArrayList = new ArrayList<Double>();
	  SMA smaTest = technicalIndicators.sma(symbol, Interval.MONTHLY, TimePeriod.of(50), SeriesType.OPEN);
    EMA emaTest = technicalIndicators.ema(symbol, Interval.MONTHLY, TimePeriod.of(50), SeriesType.OPEN);
    try {
	      Map<String, String> metaDataS = smaTest.getMetaData();
        Map<String, String> metaDataE = emaTest.getMetaData();
        System.out.println("Symbol: " + metaData.get("1: Symbol"));
	      System.out.println("Indicator: " + metaData.get("2: Indicator"));

	      List<IndicatorData> smaData = smaTest.getData();
        List<IndicatorData> emaData = emaTest.getData();
	      smaData.forEach(data -> {
	        System.out.println("SMA:            " + data.getData());
	        double[] dataArr = {data.getData, 0};
          returnArrayList.add(dataArr);
	      });
        int dataPoint = 0;
        emaData.forEach(data ->{
          returnArrayList[dataPoint][1] = data.getData();
        })
	    } catch (AlphaVantageException e) {
	      System.out.println("something went wrong");
	    }
	  returnArrayList.remove(0);
	  returnArrayList.remove(0);
	  return returnArrayList;
  }

  public ArrayList<Double> SMAEMA200 (String symbol){
	  ArrayList<Double> returnArrayList = new ArrayList<Double>();
	  SMA smaTest = technicalIndicators.sma(symbol, Interval.MONTHLY, TimePeriod.of(200), SeriesType.OPEN);
    EMA emaTest = technicalIndicators.ema(symbol, Interval.MONTHLY, TimePeriod.of(200), SeriesType.OPEN);
    try {
	      Map<String, String> metaData = smaTest.getMetaData();
	      System.out.println("Symbol: " + metaData.get("1: Symbol"));
	      System.out.println("Indicator: " + metaData.get("2: Indicator"));

        List<IndicatorData> smaData = smaTest.getData();
        List<IndicatorData> emaData = emaTest.getData();
	      smaData.forEach(data -> {
	        System.out.println("SMA:            " + data.getData());
	        double[] dataArr = {data.getData, 0};
          returnArrayList.add(dataArr);
	      });
        int dataPoint = 0;
        emaData.forEach(data ->{
          returnArrayList[dataPoint][1] = data.getData();
        })
	    } catch (AlphaVantageException e) {
	      System.out.println("something went wrong");
	    }
	  returnArrayList.remove(0);
	  returnArrayList.remove(0);
	  return returnArrayList;
  }

}
