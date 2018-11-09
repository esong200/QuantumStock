import java.util.List;
import java.util.Map;

import org.patriques.AlphaVantageConnector;
import org.patriques.TechnicalIndicators;
import org.patriques.input.technicalindicators.Interval;
import org.patriques.input.technicalindicators.SeriesType;
import org.patriques.input.technicalindicators.TimePeriod;
import org.patriques.output.AlphaVantageException;
import org.patriques.output.technicalindicators.MACD;
import org.patriques.output.technicalindicators.data.IndicatorData;
import org.patriques.output.technicalindicators.data.MACDData;
import org.patriques.output.technicalindicators.*;
import org.patriques.input.technicalindicators.*;


public class SMATest {
	  public static void main(String[] args) {
		    String apiKey = "50M3AP1K3Y";
		    int timeout = 3000;
		    AlphaVantageConnector apiConnector = new AlphaVantageConnector(apiKey, timeout);
		    TechnicalIndicators technicalIndicators = new TechnicalIndicators(apiConnector);

		    try {
		      MACD response = technicalIndicators.macd("MSFT", Interval.MONTHLY, TimePeriod.of(10), SeriesType.CLOSE, null, null, null);
		      Map<String, String> metaData = response.getMetaData();
		      System.out.println("Symbol: " + metaData.get("1: Symbol"));
		      System.out.println("Indicator: " + metaData.get("2: Indicator"));

		      List<MACDData> macdData = response.getData();
		      macdData.forEach(data -> {
		        System.out.println("date:           " + data.getDateTime());
		        System.out.println("MACD Histogram: " + data.getHist());
		        System.out.println("MACD Signal:    " + data.getSignal());
		        System.out.println("MACD:           " + data.getMacd());
		      });
		    } catch (AlphaVantageException e) {
		      System.out.println("something went wrong");
		    }
		  }
}
