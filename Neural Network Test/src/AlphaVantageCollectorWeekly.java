import java.util.ArrayList;
import java.util.Date;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.patriques.*;
import org.patriques.input.technicalindicators.Acceleration;
import org.patriques.input.technicalindicators.FastKPeriod;
import org.patriques.input.technicalindicators.FastPeriod;
import org.patriques.input.technicalindicators.Interval;
import org.patriques.input.technicalindicators.MaType;
import org.patriques.input.technicalindicators.Maximum;
import org.patriques.input.technicalindicators.SeriesType;
import org.patriques.input.technicalindicators.SlowDPeriod;
import org.patriques.input.technicalindicators.SlowKMaType;
import org.patriques.input.technicalindicators.SlowKPeriod;
import org.patriques.input.technicalindicators.SlowPeriod;
import org.patriques.input.technicalindicators.TimePeriod;
import org.patriques.output.AlphaVantageException;
import org.patriques.output.technicalindicators.AD;
import org.patriques.output.technicalindicators.ADOSC;
import org.patriques.output.technicalindicators.ADX;
import org.patriques.output.technicalindicators.APO;
import org.patriques.output.technicalindicators.AROON;
import org.patriques.output.technicalindicators.AROONOSC;
import org.patriques.output.technicalindicators.ATR;
import org.patriques.output.technicalindicators.BBANDS;
import org.patriques.output.technicalindicators.BOP;
import org.patriques.output.technicalindicators.CCI;
import org.patriques.output.technicalindicators.CMO;
import org.patriques.output.technicalindicators.DEMA;
import org.patriques.output.technicalindicators.DX;
import org.patriques.output.technicalindicators.EMA;
import org.patriques.output.technicalindicators.KAMA;
import org.patriques.output.technicalindicators.MACD;
import org.patriques.output.technicalindicators.MFI;
import org.patriques.output.technicalindicators.MIDPOINT;
import org.patriques.output.technicalindicators.MIDPRICE;
import org.patriques.output.technicalindicators.MINUS_DI;
import org.patriques.output.technicalindicators.MINUS_DM;
import org.patriques.output.technicalindicators.MOM;
import org.patriques.output.technicalindicators.NATR;
import org.patriques.output.technicalindicators.OBV;
import org.patriques.output.technicalindicators.PLUS_DI;
import org.patriques.output.technicalindicators.PLUS_DM;
import org.patriques.output.technicalindicators.PPO;
import org.patriques.output.technicalindicators.ROC;
import org.patriques.output.technicalindicators.ROCR;
import org.patriques.output.technicalindicators.RSI;
import org.patriques.output.technicalindicators.SAR;
import org.patriques.output.technicalindicators.SMA;
import org.patriques.output.technicalindicators.STOCH;
import org.patriques.output.technicalindicators.STOCHF;
import org.patriques.output.technicalindicators.TEMA;
import org.patriques.output.technicalindicators.TRANGE;
import org.patriques.output.technicalindicators.TRIMA;
import org.patriques.output.technicalindicators.TRIX;
import org.patriques.output.technicalindicators.WILLR;
import org.patriques.output.technicalindicators.WMA;
//import org.patriques.output.digitalcurrencies.Weekly;
import org.patriques.output.technicalindicators.*;
import org.patriques.output.technicalindicators.data.AROONData;
import org.patriques.output.technicalindicators.data.BBANDSData;
import org.patriques.output.technicalindicators.data.IndicatorData;
import org.patriques.output.technicalindicators.data.MACDData;
import org.patriques.output.technicalindicators.data.STOCHDataFast;
import org.patriques.output.technicalindicators.data.STOCHDataSlow;
import org.patriques.output.timeseries.*;
import org.patriques.output.timeseries.data.StockData;
import java.util.List;

public class AlphaVantageCollectorWeekly extends CSVReadWrite{
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

	public static ArrayList<double[]> weeklyData (String symbol){

		//returns montly oepn high low close volume
		ArrayList<double[]> data = new ArrayList<double[]>();

		try {
			Weekly response = stockTimeSeries.weekly(symbol);
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

	MACD macd = technicalIndicators.macd(symbol, Interval.WEEKLY,
					/*don't know why we need time period*/
					TimePeriod.of(10), SeriesType.OPEN, null, null, null);
	SMA sma = technicalIndicators.sma(symbol, Interval.WEEKLY, TimePeriod.of(50), SeriesType.OPEN);
	
	EMA ema = technicalIndicators.ema(symbol, Interval.WEEKLY, TimePeriod.of(50), SeriesType.OPEN);
	SMA smaTest = technicalIndicators.sma(symbol, Interval.WEEKLY, TimePeriod.of(75), SeriesType.OPEN);
		System.out.println("Internet Works");
	try {
		System.out.println("sleeping");
		Thread.sleep(60000);
	} catch (InterruptedException e1) {
		e1.printStackTrace();
	}
	EMA emaTest = technicalIndicators1.ema(symbol, Interval.WEEKLY, TimePeriod.of(75), SeriesType.OPEN);
	STOCH stoch = technicalIndicators1.stoch(symbol, Interval.WEEKLY, FastKPeriod.of(5), SlowKPeriod.of(3), SlowDPeriod.of(3),
			null, null);
	RSI rsi50 = technicalIndicators1.rsi(symbol, Interval.WEEKLY, TimePeriod.of(50), SeriesType.OPEN);
	RSI rsi100 = technicalIndicators1.rsi(symbol, Interval.WEEKLY, TimePeriod.of(75), SeriesType.OPEN);
	try {
			System.out.println("sleeping");
		Thread.sleep(60000);

	} catch (InterruptedException e1) {
		e1.printStackTrace();
	}
	ADX adx50 = technicalIndicators1.adx(symbol, Interval.WEEKLY, TimePeriod.of(50));
	//ADX adx100 = technicalIndicators2.adx(symbol, Interval.MONTHLY, TimePeriod.of(100));
	CMO cmo = technicalIndicators.cmo(symbol, Interval.WEEKLY, TimePeriod.of(50), SeriesType.OPEN);
	AROON aroon50 = technicalIndicators2.aroon(symbol, Interval.WEEKLY, TimePeriod.of(50));
	AROON aroon100 = technicalIndicators2.aroon(symbol, Interval.WEEKLY, TimePeriod.of(75));

	try {
			System.out.println("sleeping");
		Thread.sleep(60000);

	} catch (InterruptedException e1) {
		e1.printStackTrace();
	}
	BBANDS bbands50 = technicalIndicators2.bbands(symbol, Interval.WEEKLY, TimePeriod.of(50), SeriesType.OPEN, null, null, null);
	BBANDS bbands100 = technicalIndicators2.bbands(symbol, Interval.WEEKLY, TimePeriod.of(75), SeriesType.OPEN, null, null, null);
	AD ad = technicalIndicators3.ad(symbol, Interval.WEEKLY);
	OBV obv = technicalIndicators3.obv(symbol, Interval.WEEKLY);
	try {
			System.out.println("sleeping");
		Thread.sleep(60000);

	} catch (InterruptedException e1) {
		e1.printStackTrace();
	}
	Weekly response = stockTimeSeries.weekly(symbol);
	CCI cci50 = technicalIndicators2.cci(symbol, Interval.WEEKLY, TimePeriod.of(50));
	MOM mom = technicalIndicators.mom(symbol, Interval.WEEKLY, TimePeriod.of(50), SeriesType.OPEN);
	TRIX trix = technicalIndicators.trix(symbol, Interval.WEEKLY, TimePeriod.of(50), SeriesType.OPEN);
	try {
		System.out.println("sleeping");
		Thread.sleep(60000);
	
	} catch (InterruptedException e1) {
		e1.printStackTrace();
	}
	WMA wma = technicalIndicators1.wma(symbol, Interval.WEEKLY, TimePeriod.of(50), SeriesType.OPEN);
	DEMA dema = technicalIndicators1.dema(symbol, Interval.WEEKLY, TimePeriod.of(50), SeriesType.OPEN);
	TEMA tema = technicalIndicators1.tema(symbol, Interval.WEEKLY, TimePeriod.of(50), SeriesType.OPEN);
	TRIMA trima = technicalIndicators1.trima(symbol, Interval.WEEKLY, TimePeriod.of(50), SeriesType.OPEN);
	try {
		System.out.println("sleeping");
		Thread.sleep(60000);
	
	} catch (InterruptedException e1) {
		e1.printStackTrace();
	}
	KAMA kama = technicalIndicators2.kama(symbol, Interval.WEEKLY, TimePeriod.of(50), SeriesType.OPEN);
	STOCHF stochf = technicalIndicators2.stochf(symbol, Interval.WEEKLY, null, null, null);
	ROC roc = technicalIndicators2.roc(symbol, Interval.WEEKLY, TimePeriod.of(50), SeriesType.OPEN);
	ROCR rocr = technicalIndicators2.rocr(symbol, Interval.WEEKLY, TimePeriod.of(50), SeriesType.OPEN);
	try {
		System.out.println("sleeping");
		Thread.sleep(60000);
	
	} catch (InterruptedException e1) {
		e1.printStackTrace();
	}
	WILLR willr = technicalIndicators.willr(symbol, Interval.WEEKLY, TimePeriod.of(50));
	BOP bop = technicalIndicators.bop(symbol, Interval.WEEKLY);
	NATR natr = technicalIndicators.natr(symbol, Interval.WEEKLY, TimePeriod.of(50));
	AROONOSC aroonosc = technicalIndicators.aroonosc(symbol, Interval.WEEKLY, TimePeriod.of(50));
	try {
		System.out.println("sleeping");
		Thread.sleep(60000);
	
	} catch (InterruptedException e1) {
		e1.printStackTrace();
	}
	MFI mfi = technicalIndicators2.mfi(symbol, Interval.WEEKLY, TimePeriod.of(50));
	ATR atr = technicalIndicators2.atr(symbol, Interval.WEEKLY, TimePeriod.of(50));
	DX dx = technicalIndicators2.dx(symbol, Interval.WEEKLY, TimePeriod.of(50));
	TRANGE trange = technicalIndicators2.trange(symbol	, Interval.WEEKLY);
	try {
		System.out.println("sleeping");
		Thread.sleep(60000);
	
	} catch (InterruptedException e1) {
		e1.printStackTrace();
	}
	APO apo = technicalIndicators.apo(symbol, Interval.WEEKLY, SeriesType.OPEN, FastPeriod.of(12), SlowPeriod.of(26), MaType.SMA);
	PPO ppo = technicalIndicators.ppo(symbol, Interval.WEEKLY, SeriesType.OPEN, FastPeriod.of(12), SlowPeriod.of(26), MaType.SMA);
	MINUS_DI minusDi = technicalIndicators.minus_di(symbol, Interval.WEEKLY, TimePeriod.of(50));
	PLUS_DI plusDi = technicalIndicators.plus_di(symbol, Interval.WEEKLY, TimePeriod.of(50));
	try {
		System.out.println("sleeping");
		Thread.sleep(60000);
	
	} catch (InterruptedException e1) {
		e1.printStackTrace();
	}
	MINUS_DM minusDm = technicalIndicators2.minus_dm(symbol, Interval.WEEKLY, TimePeriod.of(50));
	PLUS_DM plusDm = technicalIndicators2.plus_dm(symbol, Interval.WEEKLY, TimePeriod.of(50));
	MIDPOINT midpoint = technicalIndicators2.midpoint(symbol, Interval.WEEKLY, TimePeriod.of(50), SeriesType.OPEN);
	MIDPRICE midprice = technicalIndicators2.midprice(symbol, Interval.WEEKLY, TimePeriod.of(50));
	try {
		System.out.println("sleeping");
		Thread.sleep(60000);
	
	} catch (InterruptedException e1) {
		e1.printStackTrace();
	}
	SAR sar = technicalIndicators.sar(symbol, Interval.WEEKLY, Acceleration.of(Float.parseFloat("0.01")), Maximum.of(Float.parseFloat("0.01")));
	ADOSC adosc = technicalIndicators.adosc(symbol, Interval.WEEKLY, FastPeriod.of(12), SlowPeriod.of(26));
	
			
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
			
			List<IndicatorData> willrData = willr.getData();
			List<IndicatorData> bopData = bop.getData();
			List<IndicatorData> natrData = natr.getData();
			List<IndicatorData> aroonoscData = aroonosc.getData();
			List<IndicatorData> mfiData = mfi.getData();
			List<IndicatorData> atrData = atr.getData();
			List<IndicatorData> dxData = dx.getData();
			List<IndicatorData> trangeData = trange.getData();
			
			List<IndicatorData> apoData = apo.getData();
			List<IndicatorData> ppoData = ppo.getData();
			List<IndicatorData> minusDiData = minusDi.getData();
			List<IndicatorData> plusDiData = plusDi.getData();
			List<IndicatorData> minusDmData = minusDm.getData();
			List<IndicatorData> plusDmData = plusDm.getData();
			List<IndicatorData> midpointData = midpoint.getData();
			List<IndicatorData> midpriceData = midprice.getData();
			List<IndicatorData> sarData = sar.getData();
			List<IndicatorData> adoscData = adosc.getData();

			int index = 0;
			for(IndicatorData data: smaData50) {
				if((index == stockData.size() || index == smaData50.size() || index == emaData50.size() || index == smaData.size() || index == emaData.size() ||
					index == macdData.size() || index == stochData.size() || index == data50.size() || index == data100.size()
					|| index == adxdata50.size() || /*index == adxdata100.size() ||*/ index == aroondata50.size() || index == aroondata100.size()
					|| index == bband50.size() || index == bband100.size() || index == addata.size() || index == obvdata.size())
					|| index == cci.size() || index == cmoData.size() || index == momData.size() || index == trixData.size() || index == wmaData.size()
					|| index == demaData.size() || index == temaData.size() || index == trimaData.size() || index == kamaData.size() || index == stochfData.size()
					|| index == rocData.size() || index == rocData.size() || index == rocrData.size() || index == willrData.size() || index == bopData.size() 
					|| index == natrData.size() || index == aroonoscData.size() || index == mfiData.size() || index == atrData.size() || index == dxData.size()
					|| index == trangeData.size() || index == apoData.size() || index == ppoData.size() || index == minusDiData.size() || index == plusDiData.size()
					|| index == minusDmData.size() || index == plusDmData.size() || index == midpointData.size() || index == midpriceData.size()
					|| index == sarData.size() || index == adoscData.size())  {
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
					 stochfData.get(index).getFastD(), rocData.get(index).getData(), rocrData.get(index).getData(), willrData.get(index).getData(),
					 bopData.get(index).getData(), natrData.get(index).getData(), mfiData.get(index).getData(),
					 atrData.get(index).getData(), dxData.get(index).getData(), trangeData.get(index).getData(), apoData.get(index).getData(),
					 ppoData.get(index).getData(), minusDiData.get(index).getData(), plusDiData.get(index).getData(), minusDmData.get(index).getData(),
					 plusDmData.get(index).getData(), midpointData.get(index).getData(), midpriceData.get(index).getData(), sarData.get(index).getData(),
					 adoscData.get(index).getData()}; 
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
		ArrayList<double[]> weekly = weeklyData(symbol);
		weekly.remove(0);
		ArrayList<double[]> answers = new ArrayList<double[]>();
		for(int i = 0; i < weekly.size() -1; i++){

			double percentChange = ((weekly.get(i)[0] - weekly.get(i+1)[0])/weekly.get(i+1)[0])*100;
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
