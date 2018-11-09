import java.math.BigDecimal;
public class bigDecimalTest {
	public static void main(String[] args) {
		BigDecimal num = new BigDecimal("17.5");
		BigDecimal ans = new BigDecimal("7219");
		ans = num.divide(ans);
	}
}
