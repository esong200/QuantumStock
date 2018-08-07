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


  public ArrayList<double[]> DataCollection (String symbol){
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
  EMA emaTest = technicalIndicators.ema(symbol, Interval.MONTHLY, TimePeriod.of(200), SeriesType.OPEN);
  STOCH stoch = technicalIndicators.stoch(symbol, Interval.MONTHLY, null, null, null, null, null);
  RSI rsi50 = technicalIndicators.rsi(symbol, Interval.MONTHLY, TimePeriod.of(50), SeriesType.OPEN);
  RSI rsi200 = technicalIndicators.rsi(symbol, Interval.MONTHLY, TimePeriod.of(200), SeriesType.OPEN);
  ADX adx50 = technicalIndicators.adx(symbol, Interval.MONTHLY, TimePeriod.of(50));
  ADX adx200 = technicalIndicators.adx(symbol, Interval.MONTHLY, TimePeriod.of(200));
  AROON aroon50 = technicalIndicators.aroon(symbol, Interval.MONTHLY, TimePeriod.of(50));
  AROON aroon200 = technicalIndicators.aroon(symbol, Interval.MONTHLY, TimePeriod.of(200));
  BBANDS bbands50 = technicalIndicators.bbands(symbol, Interval.MONTHLY, TimePeriod.of(50), SeriesType.OPEN, null, null, null);
  BBANDS bbands200 = technicalIndicators.bbands(symbol, Interval.MONTHLY, TimePeriod.of(200), SeriesType.OPEN, null, null, null);
  AD ad = technicalIndicators.ad(symbol, Interval.MONTHLY);
  OBV obv = technicalIndicators.obv(symbol, Interval.MONTHLY);
  Monthly response = stockTimeSeries.monthly(symbol);
    try {
    	List<IndicatorData> smaData50 = sma.getData();
    	List<IndicatorData> emaData50 = ema.getData();
      List<IndicatorData> smaData = smaTest.getData();
      List<IndicatorData> emaData = emaTest.getData();
      List<MACDData> macdData = macd.getData();
      List<STOCHDataSlow> stochData = stoch.getData();
      List<IndicatorData> data50 = rsi50.getData();
      List<IndicatorData> data200 = rsi200.getData();
      List<IndicatorData> adxdata50 = adx50.getData();
      List<IndicatorData> adxdata200 = adx200.getData();
      List<AROONData> aroondata50 = aroon50.getData();
      List<AROONData> aroondata200 = aroon200.getData();
      List<BBANDSData> bband50 = bbands50.getData();
      List<BBANDSData> bband200 = bbands200.getData();
      List<IndicatorData> addata = ad.getData();
      List<IndicatorData> obvdata = obv.getData();
      List<StockData> stockData = response.getStockData();

        int index = 0;
	    for(IndicatorData data: smaData50) {
        if(!(index == stockData.size() || index == smaData50.size() || index == emaData50.size() || index == smaData.size() || index == emaData.size() ||
          index == macdData.size() || index == stochData.size() || index == data50.size() || index == data200.size()
          || index == adxdata50.size() || index == adxdata200.size() || index == aroondata50.size() || index == aroondata200.size()
          || index == bband50.size() || index == bband200.size() || index == addata.size() || index == obvdata.size())){
            break;
          }
        double[] dataArr = {stockData.get(index).getOpen(), stockData.get(index).getHigh(), stockData.get(index).getLow(),
           stockData.get(index).getClose(), stockData.get(index).getVolume(),
           data.getData(), emaData50.get(index).getData(), smaData.get(index).getData(),
           emaData.get(index).getData(),macdData.get(index).getHist(), macdData.get(index).getSignal(),
           macdData.get(index).getMacd(),stochData.get(index).getSlowD(), stochData.get(index).getSlowK(),
           data50.get(index).getData(),data200.get(index).getData(),adxdata50.get(index).getData(),
           adxdata200.get(index).getData(),aroondata50.get(index).getAroonDown(), aroondata50.get(index).getAroonUp(),
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
	  return returnArrayList;
  }

  public ArrayList<double[]> answerCompile (String symbol){
    ArrayList<double[]> monthly = monthlyData(symbol);
    monthly.remove(0);
    ArrayList<double[]> answers = new ArrayList<double[]>();
    double[] answer = new double[8];
    for(int i = 0; i < monthly.size() -1; i++){
      double percentChange = (monthly.get(i)[0] - monthly.get(i+1)[0])/monthly.get(i+1)[0];
      int percent = (int) percentChange;
      switch (percent)
      {
      case -10:
    	  answer[0]= 0;
          answer[1]= 1;
          answer[2]= 0;
          answer[3]= 0;
          answer[4]= 0;
          answer[5]= 0;
          answer[6]= 0;
          answer[7]= 0;
      case -9:
    	  answer[0]= 0;
          answer[1]= 1;
          answer[2]= 0;
          answer[3]= 0;
          answer[4]= 0;
          answer[5]= 0;
          answer[6]= 0;
          answer[7]= 0;
      case -8:
    	  answer[0]= 0;
          answer[1]= 1;
          answer[2]= 0;
          answer[3]= 0;
          answer[4]= 0;
          answer[5]= 0;
          answer[6]= 0;
          answer[7]= 0;
      case -7:
    	  answer[0]= 0;
          answer[1]= 1;
          answer[2]= 0;
          answer[3]= 0;
          answer[4]= 0;
          answer[5]= 0;
          answer[6]= 0;
          answer[7]= 0;
      case -6:
    	  answer[0]= 0;
          answer[1]= 1;
          answer[2]= 0;
          answer[3]= 0;
          answer[4]= 0;
          answer[5]= 0;
          answer[6]= 0;
          answer[7]= 0;
      case -5:
    	  answer[0]= 0;
          answer[1]= 1;
          answer[2]= 0;
          answer[3]= 0;
          answer[4]= 0;
          answer[5]= 0;
          answer[6]= 0;
          answer[7]= 0;
      case -4:
    	  answer[0]= 0;
          answer[1]= 0;
          answer[2]= 1;
          answer[3]= 0;
          answer[4]= 0;
          answer[5]= 0;
          answer[6]= 0;
          answer[7]= 0;
      case -3:
    	  answer[0]= 0;
          answer[1]= 0;
          answer[2]= 1;
          answer[3]= 0;
          answer[4]= 0;
          answer[5]= 0;
          answer[6]= 0;
          answer[7]= 0;
      case -2:
    	  answer[0]= 0;
          answer[1]= 0;
          answer[2]= 1;
          answer[3]= 0;
          answer[4]= 0;
          answer[5]= 0;
          answer[6]= 0;
          answer[7]= 0;
      case -1:
    	  answer[0]= 0;
          answer[1]= 0;
          answer[2]= 0;
          answer[3]= 1;
          answer[4]= 0;
          answer[5]= 0;
          answer[6]= 0;
          answer[7]= 0;
      case 0:
    	  answer[0]= 0;
          answer[1]= 0;
          answer[2]= 0;
          answer[3]= 1;
          answer[4]= 0;
          answer[5]= 0;
          answer[6]= 0;
          answer[7]= 0;
      case 1:
    	  answer[0]= 0;
          answer[1]= 0;
          answer[2]= 0;
          answer[3]= 0;
          answer[4]= 1;
          answer[5]= 0;
          answer[6]= 0;
          answer[7]= 0;
      case 2:
    	  answer[0]= 0;
          answer[1]= 0;
          answer[2]= 0;
          answer[3]= 0;
          answer[4]= 1;
          answer[5]= 0;
          answer[6]= 0;
          answer[7]= 0;
      case 3:
    	  answer[0]= 0;
          answer[1]= 0;
          answer[2]= 0;
          answer[3]= 0;
          answer[4]= 0;
          answer[5]= 1;
          answer[6]= 0;
          answer[7]= 0;
      case 4:
    	  answer[0]= 0;
          answer[1]= 0;
          answer[2]= 0;
          answer[3]= 0;
          answer[4]= 0;
          answer[5]= 1;
          answer[6]= 0;
          answer[7]= 0;
      case 5:
    	  answer[0]= 0;
          answer[1]= 0;
          answer[2]= 0;
          answer[3]= 0;
          answer[4]= 0;
          answer[5]= 1;
          answer[6]= 0;
          answer[7]= 0;
      case 6:
    	  answer[0]= 0;
          answer[1]= 0;
          answer[2]= 0;
          answer[3]= 0;
          answer[4]= 0;
          answer[5]= 0;
          answer[6]= 1;
          answer[7]= 0;
      case 7:
    	  answer[0]= 0;
          answer[1]= 0;
          answer[2]= 0;
          answer[3]= 0;
          answer[4]= 0;
          answer[5]= 0;
          answer[6]= 1;
          answer[7]= 0;
      case 8:
    	  answer[0]= 0;
          answer[1]= 0;
          answer[2]= 0;
          answer[3]= 0;
          answer[4]= 0;
          answer[5]= 0;
          answer[6]= 1;
          answer[7]= 0;
      case 9:
    	  answer[0]= 0;
          answer[1]= 0;
          answer[2]= 0;
          answer[3]= 0;
          answer[4]= 0;
          answer[5]= 0;
          answer[6]= 1;
          answer[7]= 0;
      default:
    	  if(percent < -10){
          answer[0]= 1;
          answer[1]= 0;
          answer[2]= 0;
          answer[3]= 0;
          answer[4]= 0;
          answer[5]= 0;
          answer[6]= 0;
          answer[7]= 0;
       }
    	 else if(percent>=10){
    	       answer[0]= 0;
    	       answer[1]= 0;
    	       answer[2]= 0;
    	       answer[3]= 0;
    	       answer[4]= 0;
    	       answer[5]= 0;
    	       answer[6]= 0;
    	       answer[7]= 1;
    	     };
      }


      answers.add(answer);
    }

    return answers;
  }

}
