import java.util.ArrayList;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.patriques.*;
import org.patriques.input.technicalindicators.Interval;
import org.patriques.input.technicalindicators.SeriesType;
import org.patriques.input.technicalindicators.TimePeriod;
import org.patriques.input.timeseries.Interval;
import org.patriques.output.AlphaVantageException;
import org.patriques.output.technicalindicators.MACD;
import org.patriques.output.technicalindicators.SMA;
import org.patriques.output.technicalindicators.data.IndicatorData;
import org.patriques.output.timeseries.IntraDay;
import org.patriques.output.timeseries.Monthly;
import org.patriques.output.timeseries.data.StockData;
import java.util.List;

import org.patriques.input.technicalindicators.SeriesType;
//import org.patriques.output.AlphaVantageException;
import org.patriques.output.technicalindicators.MACD;
import org.patriques.output.technicalindicators.data.IndicatorData;
import org.patriques.output.timeseries.Monthly;
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

  public ArrayList<double[]> SMAEMA50 (String symbol){
    //returns SMA,EMA
	  ArrayList<double[]> returnArrayList = new ArrayList<double[]>();
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
	      System.out.println("something went wrong SMAEMA50");
	    }
	  returnArrayList.remove(0);
	  returnArrayList.remove(0);
	  return returnArrayList;
  }

  public ArrayList<double[]> SMAEMA200 (String symbol){
    //returns SMA,EMA
	  ArrayList<double[]> returnArrayList = new ArrayList<double[]>();
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
	      System.out.println("something went wrong EMASMA200");
	    }
	  returnArrayList.remove(0);
	  returnArrayList.remove(0);
	  return returnArrayList;
  }

  public ArrayList<double[]> MACD (String symbol){
    //returns arraylist w/ double[3]'s
    ArrayList<double[]> returnArrayList = new ArrayList<double[]>();
    MACD macd = technicalIndicators.macd(symbol, Interval.MONTHLY, SeriesType.OPEN);
    try{
      Map<String, String> metaData = macd.getMetaData();

      List<IndicatorData> macdData = macd.getData();
      macdData.forEach(data ->{
        double[] dataArr = {data.getHist(), data.getSignal(), data.getMacd}
        returnArrayList.add(dataArr)
      });

    }
    catch(AlphaVantageException e){
      System.out.println("something went wrong MACD");
    }
    return returnArrayList;
  }

  public ArrayList<double[]> STOCH (String symbol){
    //returns double[2]
    ArrayList<double[]> returnArrayList = new ArrayList<double[]>();
    STOCH stoch = technicalIndicators.stoch(symbol, Interval.MONTHLY);
    try{
      Map<String, String> metaData = stoch.getMetaData();

      List<IndicatorData> stochData = stoch.getData();
      stochData.forEach(data ->{
        double[] dataArr = {data.getslowD(), data.getslowK()}
        returnArrayList.add(dataArr)
      });

    }
    catch(AlphaVantageException e){
      System.out.println("something went wrong STOCH");
    }
    return returnArrayList;
  }

  public ArrayList<double[]> RSI50200 (String symbol){
    //returns {50,200} for RSI
    ArrayList<double[]> returnArrayList = new ArrayList<double[]>();
    RSI rsi50 = technicalIndicators.rsi(symbol, Interval.MONTHLY, TimePeriod.of(50), SeriesType.OPEN);
    RSI rsi200 = technicalIndicators.rsi(symbol, Interval.MONTHLY, TimePeriod.of(200), SeriesType.OPEN);
    try {
       List<IndicatorData> data50 = rsi50.getData();
        List<IndicatorData> data200 = rsi200.getData();
       data50.forEach(data -> {
         System.out.println("SMA:            " + data.getData());
         double[] dataArr = {data.getData, 0};
          returnArrayList.add(dataArr);
       });
        int dataPoint = 0;
        data200.forEach(data ->{
          returnArrayList[dataPoint][1] = data.getData();
        })
     } catch (AlphaVantageException e) {
       System.out.println("something went wrong RSI");
     }
   returnArrayList.remove(0);
   returnArrayList.remove(0);
   return returnArrayList;
  }

  public ArrayList<double[]> ADX50200 (String symbol){
    //returns {50,200} for ADX
    ArrayList<double[]> returnArrayList = new ArrayList<double[]>();
    ADX rsi50 = technicalIndicators.adx(symbol, Interval.MONTHLY, TimePeriod.of(50), SeriesType.OPEN);
    ADX rsi200 = technicalIndicators.adx(symbol, Interval.MONTHLY, TimePeriod.of(200), SeriesType.OPEN);
    try {
       List<IndicatorData> data50 = rsi50.getData();
        List<IndicatorData> data200 = rsi200.getData();
       data50.forEach(data -> {
          double[] dataArr = {data.getData, 0};
          returnArrayList.add(dataArr);
       });
        int dataPoint = 0;
        data200.forEach(data ->{
          returnArrayList[dataPoint][1] = data.getData();
        })
     } catch (AlphaVantageException e) {
       System.out.println("something went wrong ADX");
     }
   returnArrayList.remove(0);
   returnArrayList.remove(0);
   return returnArrayList;
  }

  public ArrayList<double[]> AROON50200 (String symbol){
    //returns {50,200} for AROON
    ArrayList<double[]> returnArrayList = new ArrayList<double[]>();
    AROON rsi50 = technicalIndicators.aroon(symbol, Interval.MONTHLY, TimePeriod.of(50), SeriesType.OPEN);
    AROON rsi200 = technicalIndicators.aroon(symbol, Interval.MONTHLY, TimePeriod.of(200), SeriesType.OPEN);
    try {
       List<IndicatorData> data50 = rsi50.getData();
        List<IndicatorData> data200 = rsi200.getData();
       data50.forEach(data -> {
          double[] dataArr = {data.getData, 0};
          returnArrayList.add(dataArr);
       });
        int dataPoint = 0;
        data200.forEach(data ->{
          returnArrayList[dataPoint][1] = data.getData();
        })
     } catch (AlphaVantageException e) {
       System.out.println("something went wrong AROON");
     }
   returnArrayList.remove(0);
   returnArrayList.remove(0);
   return returnArrayList;
  }

  public ArrayList<double[]> BBANDS50200 (String symbol){
    //returns {50,200} for BBANDS
    ArrayList<double[]> returnArrayList = new ArrayList<double[]>();
    BBANDS rsi50 = technicalIndicators.bbands(symbol, Interval.MONTHLY, TimePeriod.of(50), SeriesType.OPEN);
    BBANDS rsi200 = technicalIndicators.bbands(symbol, Interval.MONTHLY, TimePeriod.of(200), SeriesType.OPEN);
    try {
       List<IndicatorData> data50 = rsi50.getData();
        List<IndicatorData> data200 = rsi200.getData();
       data50.forEach(data -> {
          double[] dataArr = {data.getData, 0};
          returnArrayList.add(dataArr);
       });
        int dataPoint = 0;
        data200.forEach(data ->{
          returnArrayList[dataPoint][1] = data.getData();
        })
     } catch (AlphaVantageException e) {
       System.out.println("something went wrong ADX");
     }
   returnArrayList.remove(0);
   returnArrayList.remove(0);
   return returnArrayList;
  }

  public ArrayList<double[]> ADOBV (String symbol){
    //returns arraylist of just doubles
    ArrayList<double[]> returnArrayList = new ArrayList<double[]>();
    AD rsi50 = technicalIndicators.ad(symbol, Interval.MONTHLY);
    OBV rsi200 = technicalIndicators.obv(symbol, Interval.MONTHLY);
    try {
       List<IndicatorData> data50 = rsi50.getData();
        List<IndicatorData> data200 = rsi200.getData();
       data50.forEach(data -> {
          double[] dataArr = {data.getData, 0};
          returnArrayList.add(dataArr);
       });
        int dataPoint = 0;
        data200.forEach(data ->{
          returnArrayList[dataPoint][1] = data.getData();
        })
     } catch (AlphaVantageException e) {
       System.out.println("something went wrong AD OBV");
     }
   returnArrayList.remove(0);
   returnArrayList.remove(0);
   return returnArrayList;
  }

}
