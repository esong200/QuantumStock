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
public class CurrentDataCollector extends CSVReadWrite  {
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
		SMA smaTest = technicalIndicators.sma(symbol, Interval.MONTHLY, TimePeriod.of(75), SeriesType.OPEN);
		System.out.println("Internet Works");
		try {
			System.out.println("sleeping");
			Thread.sleep(60000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		EMA emaTest = technicalIndicators1.ema(symbol, Interval.MONTHLY, TimePeriod.of(75), SeriesType.OPEN);
		STOCH stoch = technicalIndicators1.stoch(symbol, Interval.MONTHLY, FastKPeriod.of(5), SlowKPeriod.of(3), SlowDPeriod.of(3),
			null, null);
		RSI rsi50 = technicalIndicators1.rsi(symbol, Interval.MONTHLY, TimePeriod.of(50), SeriesType.OPEN);
		RSI rsi100 = technicalIndicators1.rsi(symbol, Interval.MONTHLY, TimePeriod.of(75), SeriesType.OPEN);
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
		AROON aroon100 = technicalIndicators2.aroon(symbol, Interval.MONTHLY, TimePeriod.of(75));

		try {
			System.out.println("sleeping");
			Thread.sleep(60000);

		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		BBANDS bbands50 = technicalIndicators2.bbands(symbol, Interval.MONTHLY, TimePeriod.of(50), SeriesType.OPEN, null, null, null);
		BBANDS bbands100 = technicalIndicators2.bbands(symbol, Interval.MONTHLY, TimePeriod.of(75), SeriesType.OPEN, null, null, null);
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
		WILLR willr = technicalIndicators.willr(symbol, Interval.MONTHLY, TimePeriod.of(50));
		BOP bop = technicalIndicators.bop(symbol, Interval.MONTHLY);
		NATR natr = technicalIndicators.natr(symbol, Interval.MONTHLY, TimePeriod.of(50));
		AROONOSC aroonosc = technicalIndicators.aroonosc(symbol, Interval.MONTHLY, TimePeriod.of(50));
		try {
			System.out.println("sleeping");
			Thread.sleep(60000);
	
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		MFI mfi = technicalIndicators2.mfi(symbol, Interval.MONTHLY, TimePeriod.of(50));
		ATR atr = technicalIndicators2.atr(symbol, Interval.MONTHLY, TimePeriod.of(50));
		DX dx = technicalIndicators2.dx(symbol, Interval.MONTHLY, TimePeriod.of(50));
		TRANGE trange = technicalIndicators2.trange(symbol	, Interval.MONTHLY);
		try {
			System.out.println("sleeping");
			Thread.sleep(60000);
	
	} catch (InterruptedException e1) {
		e1.printStackTrace();
	}
	APO apo = technicalIndicators.apo(symbol, Interval.MONTHLY, SeriesType.OPEN, FastPeriod.of(12), SlowPeriod.of(26), MaType.SMA);
	PPO ppo = technicalIndicators.ppo(symbol, Interval.MONTHLY, SeriesType.OPEN, FastPeriod.of(12), SlowPeriod.of(26), MaType.SMA);
	MINUS_DI minusDi = technicalIndicators.minus_di(symbol, Interval.MONTHLY, TimePeriod.of(50));
	PLUS_DI plusDi = technicalIndicators.plus_di(symbol, Interval.MONTHLY, TimePeriod.of(50));
	try {
		System.out.println("sleeping");
		Thread.sleep(60000);
	
	} catch (InterruptedException e1) {
		e1.printStackTrace();
	}
	MINUS_DM minusDm = technicalIndicators2.minus_dm(symbol, Interval.MONTHLY, TimePeriod.of(50));
	PLUS_DM plusDm = technicalIndicators2.plus_dm(symbol, Interval.MONTHLY, TimePeriod.of(50));
	MIDPOINT midpoint = technicalIndicators2.midpoint(symbol, Interval.MONTHLY, TimePeriod.of(50), SeriesType.OPEN);
	MIDPRICE midprice = technicalIndicators2.midprice(symbol, Interval.MONTHLY, TimePeriod.of(50));
	try {
		System.out.println("sleeping");
		Thread.sleep(60000);
	
	} catch (InterruptedException e1) {
		e1.printStackTrace();
	}
	SAR sar = technicalIndicators.sar(symbol, Interval.MONTHLY, Acceleration.of(Float.parseFloat("0.01")), Maximum.of(Float.parseFloat("0.01")));
	ADOSC adosc = technicalIndicators.adosc(symbol, Interval.MONTHLY, FastPeriod.of(12), SlowPeriod.of(26));
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
			for(int i=1; i<returnArrayList.size(); i++) {
				returnArrayList.remove(i);
			}
		System.out.println("Finished collecting data");
		ArrayLength =  returnArrayList.size();
		ArrayList<double[]> finalReturn = new ArrayList<double[]>();
		finalReturn.add(returnArrayList.get(0));
		System.out.println("Finished compiling array");
		return finalReturn;
	}
}
