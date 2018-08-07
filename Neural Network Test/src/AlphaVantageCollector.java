import java.util.ArrayList;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.patriques.*;
import org.patriques.input.technicalindicators.Interval;
import org.patriques.input.technicalindicators.SeriesType;
import org.patriques.input.technicalindicators.TimePeriod;
import org.patriques.output.AlphaVantageException;
import org.patriques.output.technicalindicators.*;
import org.patriques.output.technicalindicators.data.AROONData;
import org.patriques.output.technicalindicators.data.BBANDSData;
import org.patriques.output.technicalindicators.data.IndicatorData;
import org.patriques.output.technicalindicators.data.MACDData;
import org.patriques.output.technicalindicators.data.STOCHDataSlow;
import org.patriques.output.timeseries.Monthly;
import org.patriques.output.timeseries.data.StockData;
import java.util.List;

//import org.patriques.output.AlphaVantageException;
import org.patriques.output.technicalindicators.MACD;



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

	    List<IndicatorData> smaData = smaTest.getData();
        List<IndicatorData> emaData = emaTest.getData();
	      smaData.forEach(data -> {
	        System.out.println("SMA:            " + data.getData());
	        double[] dataArr = {data.getData(), 0};
	        returnArrayList.add(dataArr);
	      });
        int dataPoint = 0;
        emaData.forEach(data ->{
        	double[] dataArr = {returnArrayList.get(dataPoint)[0], data.getData()};
        	returnArrayList.set(dataPoint, dataArr);
        });
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
        List<IndicatorData> smaData = smaTest.getData();
        List<IndicatorData> emaData = emaTest.getData();
	      smaData.forEach(data -> {
	        System.out.println("SMA:            " + data.getData());
	        double[] dataArr = {data.getData(), 0};
          returnArrayList.add(dataArr);
	      });
        int dataPoint = 0;
        emaData.forEach(data ->{
        	double[] dataArr = {returnArrayList.get(dataPoint)[0], data.getData()};
        	returnArrayList.set(dataPoint, dataArr);
        });
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
    MACD macd = technicalIndicators.macd(symbol, Interval.MONTHLY,
    		/*don't know why we need time period*/
    		TimePeriod.of(10), SeriesType.OPEN, null, null, null);
    try{
      Map<String, String> metaData = macd.getMetaData();

      List<MACDData> macdData = macd.getData();
      macdData.forEach(data ->{
        double[] dataArr = {data.getHist(), data.getSignal(), data.getMacd()};
        returnArrayList.add(dataArr);
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
    STOCH stoch = technicalIndicators.stoch(symbol, Interval.MONTHLY, null, null, null, null, null);
    try{
      List<STOCHDataSlow> stochData = stoch.getData();
      stochData.forEach(data ->{
        double[] dataArr = {data.getSlowD(), data.getSlowD()};
        returnArrayList.add(dataArr);
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
         double[] dataArr = {data.getData(), 0};
          returnArrayList.add(dataArr);
       });
        int dataPoint = 0;
        data200.forEach(data ->{
        	double[] dataArr = {returnArrayList.get(dataPoint)[0], data.getData()};
        	returnArrayList.set(dataPoint, dataArr);
        });
     } catch (AlphaVantageException e) {
       System.out.println("something went wrong RSI");
     }
   returnArrayList.remove(0);
   returnArrayList.remove(0);
   return returnArrayList;
  }

  public ArrayList<double[]> ADX50200 (String symbol){
    //returns double[2] for ADX
    ArrayList<double[]> returnArrayList = new ArrayList<double[]>();
    ADX rsi50 = technicalIndicators.adx(symbol, Interval.MONTHLY, TimePeriod.of(50));
    ADX rsi200 = technicalIndicators.adx(symbol, Interval.MONTHLY, TimePeriod.of(200));
    try {
       List<IndicatorData> data50 = rsi50.getData();
        List<IndicatorData> data200 = rsi200.getData();
       data50.forEach(data -> {
          double[] dataArr = {data.getData(), 0};
          returnArrayList.add(dataArr);
       });
        int dataPoint = 0;
        data200.forEach(data ->{
        double[] dataArr = {returnArrayList.get(dataPoint)[0], data.getData()};
    	returnArrayList.set(dataPoint, dataArr);
    });
        }
    catch (AlphaVantageException e) {
       System.out.println("something went wrong ADX");
     }
   returnArrayList.remove(0);
   returnArrayList.remove(0);
   return returnArrayList;
  }

  public ArrayList<double[]> AROON50200 (String symbol){
    //returns double[4] for AROON
    ArrayList<double[]> returnArrayList = new ArrayList<double[]>();
    AROON rsi50 = technicalIndicators.aroon(symbol, Interval.MONTHLY, TimePeriod.of(50));
    AROON rsi200 = technicalIndicators.aroon(symbol, Interval.MONTHLY, TimePeriod.of(200));
    try {
       List<AROONData> data50 = rsi50.getData();
        List<AROONData> data200 = rsi200.getData();
       data50.forEach(data -> {
          double[] dataArr = {data.getAroonUp(), data.getAroonDown()};
          returnArrayList.add(dataArr);
       });
        int dataPoint = 0;
        data200.forEach(data ->{
        	double[] dataArr = {returnArrayList.get(dataPoint)[0], returnArrayList.get(dataPoint)[1],
        			data.getAroonUp(), data.getAroonDown()};
        	returnArrayList.set(dataPoint, dataArr);
        });
     } catch (AlphaVantageException e) {
       System.out.println("something went wrong AROON");
     }
   returnArrayList.remove(0);
   returnArrayList.remove(0);
   return returnArrayList;
  }

  public ArrayList<double[]> BBANDS50200 (String symbol){
    //returns double[6] for BBANDS
    ArrayList<double[]> returnArrayList = new ArrayList<double[]>();
    BBANDS rsi50 = technicalIndicators.bbands(symbol, Interval.MONTHLY, TimePeriod.of(50), SeriesType.OPEN, null, null, null);
    BBANDS rsi200 = technicalIndicators.bbands(symbol, Interval.MONTHLY, TimePeriod.of(200), SeriesType.OPEN, null, null, null);
    try {
       List<BBANDSData> data50 = rsi50.getData();
        List<BBANDSData> data200 = rsi200.getData();
       data50.forEach(data -> {
          double[] dataArr = {data.getLowerBand(), data.getUpperBand(), data.getMidBand()};
          returnArrayList.add(dataArr);
       });
        int dataPoint = 0;
        data200.forEach(data ->{
        	double[] dataArr = {returnArrayList.get(dataPoint)[0], returnArrayList.get(dataPoint)[1],
        			returnArrayList.get(dataPoint)[2],data.getLowerBand(),
        			data.getUpperBand(), data.getMidBand()};
        	returnArrayList.set(dataPoint, dataArr);
        });
     } catch (AlphaVantageException e) {
       System.out.println("something went wrong ADX");
     }
   returnArrayList.remove(0);
   returnArrayList.remove(0);
   return returnArrayList;
  }

  public ArrayList<double[]> ADOBV (String symbol){
    //returns double[2] of just doubles
    ArrayList<double[]> returnArrayList = new ArrayList<double[]>();
    AD rsi50 = technicalIndicators.ad(symbol, Interval.MONTHLY);
    OBV rsi200 = technicalIndicators.obv(symbol, Interval.MONTHLY);
    try {
       List<IndicatorData> data50 = rsi50.getData();
        List<IndicatorData> data200 = rsi200.getData();
       data50.forEach(data -> {
          double[] dataArr = {data.getData(), 0};
          returnArrayList.add(dataArr);
       });
        int dataPoint = 0;
        data200.forEach(data ->{
        	double[] dataArr = {returnArrayList.get(dataPoint)[0], data.getData()};
    	returnArrayList.set(dataPoint, dataArr);
        });
     } catch (AlphaVantageException e) {
       System.out.println("something went wrong AD OBV");
     }
   returnArrayList.remove(0);
   returnArrayList.remove(0);
   return returnArrayList;
  }

  public ArrayList<double[]> dataCompile(String symbol){
    ArrayList<double[]> monthly = monthlyData(symbol);//5
    monthly.remove(0);
    monthly.remove(0);
    ArrayList<double[]> smaema50 = SMAEMA50(symbol);//2
    ArrayList<double[]> smaema200 = SMAEMA200(symbol);//2
    ArrayList<double[]> macd = MACD(symbol);//3
    ArrayList<double[]> stoch = STOCH(symbol);//2
    ArrayList<double[]> rsi = RSI50200(symbol);//2
    ArrayList<double[]> adx = ADX50200(symbol);//2
    ArrayList<double[]> aroon = AROON50200(symbol);//4
    ArrayList<double[]> bbands = BBANDS50200(symbol);//6
    ArrayList<double[]> adobv = ADOBV(symbol);//2

    ArrayList<double[]> Final = new ArrayList<double[]>();
    double[] inputs = new double[28];
    for(int i = 0; i<monthly.size(); i++){
      for(int j = 0; j<inputs.length; j++){
        switch (j)
        {
          case 0: inputs[0] = monthly.get(i)[0];
                  break;
          case 1: inputs[1] = monthly.get(i)[1];
                  break;
          case 2: inputs[2] = monthly.get(i)[2];
                  break;
          case 3: inputs[3] = smaema50.get(i)[0];
                  break;
          case 4:inputs[4] = smaema50.get(i)[1];
                  break;
          case 5:inputs[5] = smaema200.get(i)[0];
                  break;
          case 6:inputs[6] = smaema200.get(i)[1];
                  break;
          case 7:inputs[7] = macd.get(i)[0];
                  break;
          case 8: inputs[8] = macd.get(i)[1];
                  break;
          case 9: inputs[9] = macd.get(i)[2];
                  break;
          case 10: inputs[10] = stoch.get(i)[0];
                  break;
          case 11: inputs[11] = stoch.get(i)[1];
                  break;
          case 12:  inputs[12] = rsi.get(i)[0];
                  break;
          case 13: inputs[13] = rsi.get(i)[1];
                  break;
          case 14: inputs[14] = adx.get(i)[0];
                  break;
          case 15: inputs[15] = adx.get(i)[1];
                  break;
          case 16: inputs[16] = aroon.get(i)[0];
                  break;
          case 17: inputs[17] = aroon.get(i)[1];
                  break;
          case 18: inputs[18] = aroon.get(i)[2];
                  break;
          case 19: inputs[19] = aroon.get(i)[3];
                  break;
          case 20: inputs[20] = bbands.get(i)[0];
                  break;
          case 21: inputs[21] = bbands.get(i)[1];
                  break;
          case 22:inputs[22] = bbands.get(i)[2];
          case 23:inputs[23] = bbands.get(i)[3];
        	  break;
          case 24:inputs[24] = bbands.get(i)[4];
          case 25:inputs[25] = bbands.get(i)[5];
          case 26:inputs[26] = adobv.get(i)[0];
          case 27:inputs[27] = adobv.get(i)[1];
        }
        Final.add(inputs);
      }

    }

    return Final;
  }

  public ArrayList<double[]> answerCompile (String symbol){
    ArrayList<double[]> monthly = monthlyData(symbol);
    monthly.remove(0);
    ArrayList<double[]> answers = new ArrayList<double[]>();
    double[] answer = new double[8];
    for(int i = 0; i < monthly.size() -1; i++){
      double percentChange = (monthly.get(i)[0] - monthly.get(i+1)[0])/monthly.get(i+1)[0];
      if(percentChange <= -10){
         answer[0]= 1;
         answer[1]= 0;
         answer[2]= 0;
         answer[3]= 0;
         answer[4]= 0;
         answer[5]= 0;
         answer[6]= 0;
         answer[7]= 0;
      }
      else if(percentChange <=-5){
        answer[0]= 0;
        answer[1]= 1;
        answer[2]= 0;
        answer[3]= 0;
        answer[4]= 0;
        answer[5]= 0;
        answer[6]= 0;
        answer[7]= 0;
      }
      else if(percentChange <= -2){
        answer[0]= 0;
        answer[1]= 0;
        answer[2]= 1;
        answer[3]= 0;
        answer[4]= 0;
        answer[5]= 0;
        answer[6]= 0;
        answer[7]= 0;
      }
      else if(percentChange <= 0){
        answer[0]= 0;
        answer[1]= 0;
        answer[2]= 0;
        answer[3]= 1;
        answer[4]= 0;
        answer[5]= 0;
        answer[6]= 0;
        answer[7]= 0;
      }
      else if(percentChange <=2){
        answer[0]= 0;
        answer[1]= 0;
        answer[2]= 0;
        answer[3]= 0;
        answer[4]= 1;
        answer[5]= 0;
        answer[6]= 0;
        answer[7]= 0;
      }
      else if(percentChange <=5){
        answer[0]= 0;
        answer[1]= 0;
        answer[2]= 0;
        answer[3]= 0;
        answer[4]= 0;
        answer[5]= 1;
        answer[6]= 0;
        answer[7]= 0;
      }
      else if(percentChange <=10){
        answer[0]= 0;
        answer[1]= 0;
        answer[2]= 0;
        answer[3]= 0;
        answer[4]= 0;
        answer[5]= 0;
        answer[6]= 1;
        answer[7]= 0;
      }
      else if(i>10){
        answer[0]= 0;
        answer[1]= 0;
        answer[2]= 0;
        answer[3]= 0;
        answer[4]= 0;
        answer[5]= 0;
        answer[6]= 0;
        answer[7]= 1;
      };

      answers.add(answer);
    }

    return answers;
  }


}
