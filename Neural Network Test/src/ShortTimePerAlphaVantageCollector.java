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
import org.patriques.output.technicalindicators.data.STOCHDataFast;
import org.patriques.output.technicalindicators.data.STOCHDataSlow;
import org.patriques.output.timeseries.Monthly;
import org.patriques.output.timeseries.data.StockData;
import java.util.List;


import org.patriques.output.technicalindicators.MACD;

public class ShortTimePerAlphaVantageCollector extends CSVReadWrite{
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
				inputData[1] = stock.getHigh();
				inputData[2] = stock.getLow();
				inputData[3] = stock.getClose();
				inputData[4] = stock.getVolume();
				data.add(inputData);

			});

		} catch (AlphaVantageException e) {
			System.out.println("something went wrong");
		}
		return data;
	}

	public static ArrayList<double[]> DataCollection (String symbol){
		/*returns data in form Open High Low close volume sma50 ema50 sma100 ema100 macdHist macdSig
		* macd stochd stochk rsi50 rsi100 adx50 aroon50down aroon50up aroon100down aroon100up
		* bband50Low bband50Mid bband50Hi bband100Low bband100Mid bband100Hi ad obv
		*/

	ArrayList<double[]> returnArrayList = new ArrayList<double[]>();
	MACD macd = technicalIndicators.macd(symbol, Interval.MONTHLY,
					/*don't know why we need time period*/
					TimePeriod.of(10), SeriesType.OPEN, null, null, null);
	SMA sma = technicalIndicators.sma(symbol, Interval.MONTHLY, TimePeriod.of(50), SeriesType.OPEN);
	EMA ema = technicalIndicators.ema(symbol, Interval.MONTHLY, TimePeriod.of(50), SeriesType.OPEN);
	SMA smaTest = technicalIndicators.sma(symbol, Interval.MONTHLY, TimePeriod.of(100), SeriesType.OPEN);
		System.out.println("Internet Works");
	try {
		System.out.println("sleeping");
		Thread.sleep(60000);
	} catch (InterruptedException e1) {
		e1.printStackTrace();
	}
	EMA emaTest = technicalIndicators1.ema(symbol, Interval.MONTHLY, TimePeriod.of(100), SeriesType.OPEN);
	STOCH stoch = technicalIndicators1.stoch(symbol, Interval.MONTHLY, FastKPeriod.of(5), SlowKPeriod.of(3), SlowDPeriod.of(3),
			null, null);
	RSI rsi50 = technicalIndicators1.rsi(symbol, Interval.MONTHLY, TimePeriod.of(50), SeriesType.OPEN);
	RSI rsi100 = technicalIndicators1.rsi(symbol, Interval.MONTHLY, TimePeriod.of(100), SeriesType.OPEN);
	try {
			System.out.println("sleeping");
		Thread.sleep(60000);

	} catch (InterruptedException e1) {
		e1.printStackTrace();
	}
	ADX adx50 = technicalIndicators1.adx(symbol, Interval.MONTHLY, TimePeriod.of(50));
	//ADX adx100 = technicalIndicators2.adx(symbol, Interval.MONTHLY, TimePeriod.of(100));
	CMO cmo = technicalIndicators.cmo(symbol, Interval.MONTHLY, TimePeriod.of(50), SeriesType.OPEN);
	AROON aroon50 = technicalIndicators2.aroon(symbol, Interval.MONTHLY, TimePeriod.of(50));
	AROON aroon100 = technicalIndicators2.aroon(symbol, Interval.MONTHLY, TimePeriod.of(100));

	try {
			System.out.println("sleeping");
		Thread.sleep(60000);

	} catch (InterruptedException e1) {
		e1.printStackTrace();
	}
	BBANDS bbands50 = technicalIndicators2.bbands(symbol, Interval.MONTHLY, TimePeriod.of(50), SeriesType.OPEN, null, null, null);
	BBANDS bbands100 = technicalIndicators2.bbands(symbol, Interval.MONTHLY, TimePeriod.of(100), SeriesType.OPEN, null, null, null);
	AD ad = technicalIndicators3.ad(symbol, Interval.MONTHLY);
	OBV obv = technicalIndicators3.obv(symbol, Interval.MONTHLY);
	try {
			System.out.println("sleeping");
		Thread.sleep(60000);

	} catch (InterruptedException e1) {
		e1.printStackTrace();
	}
	Monthly response = stockTimeSeries.monthly(symbol);
	CCI cci50 = technicalIndicators2.cci(symbol, Interval.MONTHLY, TimePeriod.of(50));
	MOM mom = technicalIndicators.mom(symbol, Interval.MONTHLY, TimePeriod.of(50), SeriesType.OPEN);
	TRIX trix = technicalIndicators.trix(symbol, Interval.MONTHLY, TimePeriod.of(50), SeriesType.OPEN);
	try {
		System.out.println("sleeping");
		Thread.sleep(60000);
	
	} catch (InterruptedException e1) {
		e1.printStackTrace();
	}
	WMA wma = technicalIndicators1.wma(symbol, Interval.MONTHLY, TimePeriod.of(50), SeriesType.OPEN);
	DEMA dema = technicalIndicators1.dema(symbol, Interval.MONTHLY, TimePeriod.of(50), SeriesType.OPEN);
	TEMA tema = technicalIndicators1.tema(symbol, Interval.MONTHLY, TimePeriod.of(50), SeriesType.OPEN);
	TRIMA trima = technicalIndicators1.trima(symbol, Interval.MONTHLY, TimePeriod.of(50), SeriesType.OPEN);
	try {
		System.out.println("sleeping");
		Thread.sleep(60000);
	
	} catch (InterruptedException e1) {
		e1.printStackTrace();
	}
	KAMA kama = technicalIndicators2.kama(symbol, Interval.MONTHLY, TimePeriod.of(50), SeriesType.OPEN);
	STOCHF stochf = technicalIndicators2.stochf(symbol, Interval.MONTHLY, null, null, null);
	ROC roc = technicalIndicators2.roc(symbol, Interval.MONTHLY, TimePeriod.of(50), SeriesType.OPEN);
	ROCR rocr = technicalIndicators2.rocr(symbol, Interval.MONTHLY, TimePeriod.of(50), SeriesType.OPEN);
	try {
		System.out.println("sleeping");
		Thread.sleep(60000);
	
	} catch (InterruptedException e1) {
		e1.printStackTrace();
	}
	
			
		try {
			List<IndicatorData> trixData = trix.getData();
			List<IndicatorData> momData = mom.getData();
			List<IndicatorData> cmoData = cmo.getData();
			List<IndicatorData> cci = cci50.getData();
			List<IndicatorData> smaData50 = sma.getData();
			List<IndicatorData> emaData50 = ema.getData();
			List<IndicatorData> smaData = smaTest.getData();
			List<IndicatorData> emaData = emaTest.getData();
			List<MACDData> macdData = macd.getData();
			List<STOCHDataSlow> stochData = stoch.getData();
			List<IndicatorData> data50 = rsi50.getData();
			List<IndicatorData> data100 = rsi100.getData();
			List<IndicatorData> adxdata50 = adx50.getData();
			List<AROONData> aroondata50 = aroon50.getData();
			List<AROONData> aroondata100 = aroon100.getData();
			List<BBANDSData> bband50 = bbands50.getData();
			List<BBANDSData> bband100 = bbands100.getData();
			List<IndicatorData> addata = ad.getData();
			List<IndicatorData> obvdata = obv.getData();
			List<StockData> stockData = response.getStockData();
			
			List<IndicatorData> wmaData = wma.getData();
			List<IndicatorData> demaData = dema.getData();
			List<IndicatorData> temaData= tema.getData();
			List<IndicatorData> trimaData = trima.getData();
			List<IndicatorData> kamaData = kama.getData();
			List<STOCHDataFast> stochfData = stochf.getData(); //2 gets
			List<IndicatorData> rocData = roc.getData();
			List<IndicatorData> rocrData = rocr.getData();

			int index = 0;
			for(IndicatorData data: smaData50) {
				if((index == stockData.size() || index == smaData50.size() || index == emaData50.size() || index == smaData.size() || index == emaData.size() ||
					index == macdData.size() || index == stochData.size() || index == data50.size() || index == data100.size()
					|| index == adxdata50.size() || /*index == adxdata100.size() ||*/ index == aroondata50.size() || index == aroondata100.size()
					|| index == bband50.size() || index == bband100.size() || index == addata.size() || index == obvdata.size())
					|| index == cci.size() || index == cmoData.size() || index == momData.size() || index == trixData.size() || index == wmaData.size()
					|| index == demaData.size() || index == temaData.size() || index == trimaData.size() || index == kamaData.size() || index == stochfData.size()
					|| index == rocData.size() || index == rocData.size() || index == rocrData.size())  {
						break;
					}
				double[] dataArr = {stockData.get(index).getOpen(), stockData.get(index).getHigh(), stockData.get(index).getLow(),
					 stockData.get(index).getClose(), stockData.get(index).getVolume(),
					 data.getData(), emaData50.get(index).getData(), smaData.get(index).getData(),
					 emaData.get(index).getData(),macdData.get(index).getHist(), macdData.get(index).getSignal(),
					 macdData.get(index).getMacd(),stochData.get(index).getSlowD(), stochData.get(index).getSlowK(),
					 data50.get(index).getData(),data100.get(index).getData(),adxdata50.get(index).getData(),
					 /*adxdata100.get(index).getData(),*/aroondata50.get(index).getAroonDown(), aroondata50.get(index).getAroonUp(),
					 aroondata100.get(index).getAroonDown(), aroondata100.get(index).getAroonUp(),
					 bband50.get(index).getLowerBand(), bband50.get(index).getMidBand(), bband50.get(index).getUpperBand(),
					 bband100.get(index).getLowerBand(), bband100.get(index).getMidBand(), bband100.get(index).getUpperBand(),
					 addata.get(index).getData(), obvdata.get(index).getData(), cci.get(index).getData(), cmoData.get(index).getData(),
					 momData.get(index).getData(), trixData.get(index).getData(), wmaData.get(index).getData(), demaData.get(index).getData(),
					 temaData.get(index).getData(), trimaData.get(index).getData(), kamaData.get(index).getData(), stochfData.get(index).getFastD(),
					 stochfData.get(index).getFastD(), rocData.get(index).getData(), rocrData.get(index).getData()};
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
		monthly.remove(0);
		ArrayList<double[]> answers = new ArrayList<double[]>();
		for(int i = 0; i < monthly.size() -1; i++){

			double percentChange = ((monthly.get(i)[0] - monthly.get(i+1)[0])/monthly.get(i+1)[0])*100;
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
				break;
			case -9:
				answers.add(one);
				break;
			case -8:
				answers.add(one);
				break;
			case -7:
			answers.add(one);
				break;
			case -6:
				answers.add(one);
				break;
			case -5:
				answers.add(one);
				break;
			case -4:
				answers.add(two);
				break;
			case -3:
				answers.add(two);
				break;
			case -2:
				answers.add(two);
				break;
			case -1:
				answers.add(three);
				break;
			case 0:
				answers.add(three);
				break;
			case 1:
				answers.add(four);
				break;
			case 2:
				answers.add(four);
				break;
			case 3:
				answers.add(five);
				break;
			case 4:
				answers.add(five);
				break;
			case 5:
				answers.add(five);
				break;
			case 6:
				answers.add(six);
				break;
			case 7:
				answers.add(six);
				break;
			case 8:
				answers.add(six);
				break;
			case 9:
				answers.add(six);
				break;
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
}