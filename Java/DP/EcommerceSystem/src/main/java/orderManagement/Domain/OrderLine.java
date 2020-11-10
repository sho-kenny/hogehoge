package orderManagement.Domain;

import java.math.BigDecimal;

/**
 * DDDにおける「Domain Model」
 * @author kenny
 */
public class OrderLine {
	public Product Product;
	public int Quantity;
	public BigDecimal UnitPrice;

	private OrderLine(Product product, int quantity, BigDecimal unitPrice) {
		Product = product;
		Quantity = quantity;
		UnitPrice = unitPrice;
	}

	public static OrderLine Create(Product product, int quantity, BigDecimal unitPrice) {
		return new OrderLine(product, quantity, unitPrice);
	}
}
