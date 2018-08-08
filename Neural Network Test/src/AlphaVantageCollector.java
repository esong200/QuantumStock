import java.util.ArrayList;
import java.util.Date;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.patriques.*;
import org.patriques.input.technicalindicators.FastKPeriod;
import org.patriques.input.technicalindicators.Interval;
import org.patriques.input.technicalindicators.SeriesType;
import org.patriques.input.technicalindicators.SlowDPeriod;
import org.patriques.input.technicalindicators.SlowKMaType;
import org.patriques.input.technicalindicators.SlowKPeriod;
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
    static String apiKey1 = "W7JYK9UWBIO9X75G";
    static String apiKey2 = "356GGMBFYQWEUOFR";
    static String apiKey3 = "M7NCSUIT1QIC6BRR";
    static int timeout = 3000;
    static AlphaVantageConnector apiConnector = new AlphaVantageConnector(apiKey, timeout);
    static AlphaVantageConnector apiConnector1 = new AlphaVantageConnector(apiKey1, timeout);
    static AlphaVantageConnector apiConnector2 = new AlphaVantageConnector(apiKey2 , timeout);
    static AlphaVantageConnector apiConnector3 = new AlphaVantageConnector(apiKey3 , timeout);
    static TimeSeries stockTimeSeries = new TimeSeries(apiConnector3);
    static TechnicalIndicators technicalIndicators = new TechnicalIndicators (apiConnector);
    static TechnicalIndicators technicalIndicators1 = new TechnicalIndicators (apiConnector1);
    static TechnicalIndicators technicalIndicators2 = new TechnicalIndicators (apiConnector2);
    static TechnicalIndicators technicalIndicators3 = new TechnicalIndicators (apiConnector3);
    private static int ArrayLength = 0;

  public static ArrayList<double[]> monthlyData (String symbol){

    //returns montly oepn high low close volume
    ArrayList<double[]> data = new ArrayList<double[]>();

    try {
      Monthly response = stockTimeSeries.monthly(symbol);
      Map<String, String> metaData = response.getMetaData();
      System.out.println("Information: " + metaData.get("1. Information"));
      System.out.println("Stock: " + metaData.get("2. Symbol"));

      List<StockData> stockData = response.getStockData();
      stockData.forEach(stock -> {
        double[] inputData = {0,0,0,0,0};
        inputData[0] = stock.getOpen();
        //System.out.println("high:   " + stock.getHigh());
        inputData[1] = stock.getHigh();
        //System.out.println("low:    " + stock.getLow());
        inputData[2] = stock.getLow();
        //System.out.println("close:  " + stock.getClose());
        inputData[3] = stock.getClose();
        //System.out.println("volume: " + stock.getVolume());
        inputData[4] = stock.getVolume();
        data.add(inputData);

      });

    } catch (AlphaVantageException e) {
      System.out.println("something went wrong");
    }
    data.remove(0);
    return data;
  }


  public static ArrayList<double[]> DataCollection (String symbol){
    /*returns data in form Open High Low close volume sma50 ema50 sma200 ema200 macdHist macdSig
    * macd stochd stochk rsi50 rsi200 adx50 adx200 aroon50down aroon50up aroon200down aroon200up
    * bband50Low bband50Mid bband50Hi bband200Low bband200Mid bband200Hi ad obv
    */

	ArrayList<double[]> returnArrayList = new ArrayList<double[]>();
	MACD macd = technicalIndicators.macd(symbol, Interval.MONTHLY,
	    		/*don't know why we need time period*/
	    		TimePeriod.of(10), SeriesType.OPEN, null, null, null);
	SMA sma = technicalIndicators.sma(symbol, Interval.MONTHLY, TimePeriod.of(50), SeriesType.OPEN);
	EMA ema = technicalIndicators.ema(symbol, Interval.MONTHLY, TimePeriod.of(50), SeriesType.OPEN);
	SMA smaTest = technicalIndicators.sma(symbol, Interval.MONTHLY, TimePeriod.of(200), SeriesType.OPEN);
	  System.out.println("Internet Works");
	try {
		System.out.println("sleeping");
		Thread.sleep(60000);
	} catch (InterruptedException e1) {
		e1.printStackTrace();
	}
	EMA emaTest = technicalIndicators1.ema(symbol, Interval.MONTHLY, TimePeriod.of(200), SeriesType.OPEN);

  STOCH stoch = technicalIndicators1.stoch(symbol, Interval.MONTHLY, FastKPeriod.of(5), SlowKPeriod.of(3), SlowDPeriod.of(3),
		  null, null);
  RSI rsi50 = technicalIndicators1.rsi(symbol, Interval.MONTHLY, TimePeriod.of(50), SeriesType.OPEN);
  RSI rsi200 = technicalIndicators1.rsi(symbol, Interval.MONTHLY, TimePeriod.of(200), SeriesType.OPEN);
  try {
	  	System.out.println("sleeping");
		Thread.sleep(60000);

	} catch (InterruptedException e1) {
		e1.printStackTrace();
	}
  ADX adx50 = technicalIndicators1.adx(symbol, Interval.MONTHLY, TimePeriod.of(50));
  ADX adx200 = technicalIndicators2.adx(symbol, Interval.MONTHLY, TimePeriod.of(200));

  AROON aroon50 = technicalIndicators2.aroon(symbol, Interval.MONTHLY, TimePeriod.of(50));
  AROON aroon200 = technicalIndicators2.aroon(symbol, Interval.MONTHLY, TimePeriod.of(200));

  try {
	  	System.out.println("sleeping");
		Thread.sleep(60000);

	} catch (InterruptedException e1) {
		e1.printStackTrace();
	}
  BBANDS bbands50 = technicalIndicators2.bbands(symbol, Interval.MONTHLY, TimePeriod.of(50), SeriesType.OPEN, null, null, null);
  BBANDS bbands200 = technicalIndicators2.bbands(symbol, Interval.MONTHLY, TimePeriod.of(200), SeriesType.OPEN, null, null, null);
  AD ad = technicalIndicators3.ad(symbol, Interval.MONTHLY);

  OBV obv = technicalIndicators3.obv(symbol, Interval.MONTHLY);
  try {
	  	System.out.println("sleeping");
		Thread.sleep(60000);

	} catch (InterruptedException e1) {
		e1.printStackTrace();
	}
  Monthly response = stockTimeSeries.monthly(symbol);

    try {
    	List<IndicatorData> smaData50 = sma.getData();
    //	System.out.println(smaData50.size());
    	List<IndicatorData> emaData50 = ema.getData();
    	//System.out.println(emaData50.size());

      List<IndicatorData> smaData = smaTest.getData();
  	//System.out.println(smaData.size());

      List<IndicatorData> emaData = emaTest.getData();
  	//System.out.println(emaData.size());

      List<MACDData> macdData = macd.getData();
  	//System.out.println(macdData.size());

      List<STOCHDataSlow> stochData = stoch.getData();
    //	System.out.println(stochData.size());
      List<IndicatorData> data50 = rsi50.getData();
    //	System.out.println(data50.size());
      List<IndicatorData> data200 = rsi200.getData();
    	//System.out.println(data200.size());
      List<IndicatorData> adxdata50 = adx50.getData();
    //	System.out.println(adxdata50.size());
     // List<IndicatorData> adxdata200 = adx200.getData();
   // 	System.out.println(adxdata200.size());
      List<AROONData> aroondata50 = aroon50.getData();

    //	System.out.println(aroondata50.size());
      List<AROONData> aroondata200 = aroon200.getData();
    //	System.out.println(aroondata200.size());
      List<BBANDSData> bband50 = bbands50.getData();
    	//System.out.println(bband50.size());
      List<BBANDSData> bband200 = bbands200.getData();
    //	System.out.println(bband200.size());
      List<IndicatorData> addata = ad.getData();
    //	System.out.println(addata.size());
      List<IndicatorData> obvdata = obv.getData();
    //	System.out.println(obvdata.size());
      List<StockData> stockData = response.getStockData();
    	//System.out.println(stockData.size());

        int index = 0;
	    for(IndicatorData data: smaData50) {
        if((index == stockData.size() || index == smaData50.size() || index == emaData50.size() || index == smaData.size() || index == emaData.size() ||
          index == macdData.size() || index == stochData.size() || index == data50.size() || index == data200.size()
          || index == adxdata50.size() || /*index == adxdata200.size() ||*/ index == aroondata50.size() || index == aroondata200.size()
          || index == bband50.size() || index == bband200.size() || index == addata.size() || index == obvdata.size())){
            break;
          }
        double[] dataArr = {stockData.get(index).getOpen(), stockData.get(index).getHigh(), stockData.get(index).getLow(),
           stockData.get(index).getClose(), stockData.get(index).getVolume(),
           data.getData(), emaData50.get(index).getData(), smaData.get(index).getData(),
           emaData.get(index).getData(),macdData.get(index).getHist(), macdData.get(index).getSignal(),
           macdData.get(index).getMacd(),stochData.get(index).getSlowD(), stochData.get(index).getSlowK(),
           data50.get(index).getData(),data200.get(index).getData(),adxdata50.get(index).getData(),
           /*adxdata200.get(index).getData(),*/aroondata50.get(index).getAroonDown(), aroondata50.get(index).getAroonUp(),
           aroondata200.get(index).getAroonDown(), aroondata200.get(index).getAroonUp(),
           bband50.get(index).getLowerBand(), bband50.get(index).getMidBand(), bband50.get(index).getUpperBand(),
           bband200.get(index).getLowerBand(), bband200.get(index).getMidBand(), bband200.get(index).getUpperBand(),
           addata.get(index).getData(), obvdata.get(index).getData()};
       returnArrayList.add(dataArr);
       index++;
	      };
        } catch (AlphaVantageException e) {
	      System.out.println("something went wrong dataCollection");
	    }
	  returnArrayList.remove(0);
	  returnArrayList.remove(0);
	  ArrayLength =  returnArrayList.size();
	  return returnArrayList;
  }

  public static ArrayList<double[]> answerCompile (String symbol){
    ArrayList<double[]> monthly = monthlyData(symbol);
    /*monthly.forEach(arr ->{
    	System.out.println(arr[0]);
    });*/
    monthly.remove(0);
    ArrayList<double[]> answers = new ArrayList<double[]>();
    for(int i = 0; i < monthly.size() -1; i++){

      double percentChange = (monthly.get(i)[0] - monthly.get(i+1)[0])/monthly.get(i+1)[0];
      int percent = (int) percentChange;
      double[] one = {0,1,0,0,0,0,0,0};
      double[] two = {0,0,1,0,0,0,0,0};
      double[] three = {0,0,0,1,0,0,0,0};
      double[] four = {0,0,0,0,1,0,0,0};
      double[] five = {0,0,0,0,0,1,0,0};
      double[] six = {0,0,0,0,0,0,1,0};
      double[] seven = {0,0,0,0,0,0,0,1};
      double[] zero = {1,0,0,0,0,0,0,0};
      switch (percent)
      {
      case -10:
    	  answers.add(one);
      case -9:
    	  answers.add(one);
      case -8:
    	  answers.add(one);
      case -7:
    	  answers.add(one);
      case -6:
    	  answers.add(one);
      case -5:
    	  answers.add(one);
      case -4:
    	  answers.add(two);
      case -3:
    	  answers.add(two);
      case -2:
    	  answers.add(two);
      case -1:
    	  answers.add(three);
      case 0:
    	  answers.add(three);
      case 1:
    	  answers.add(four);
      case 2:
    	  answers.add(four);
      case 3:
    	  answers.add(five);
      case 4:
    	  answers.add(five);
      case 5:
    	  answers.add(five);
      case 6:
    	  answers.add(six);
      case 7:
    	  answers.add(six);
      case 8:
    	  answers.add(six);
      case 9:
    	  answers.add(six);
      default:
    	  if(percent < -10){
    		  answers.add(zero);
       }
    	 else if(percent>=10){
    		 answers.add(seven);
    	     };
      }

    }

    return answers;
  }
