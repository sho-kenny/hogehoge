package orderManagement.Contracts.Common;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * request/responseに対応するDTO。
 * DDDを活用するからといって全てのクラスがEntityやValue Objectに該当するわけではない。
 * orderManagement.Contracts.Commonのパッケージは全てDTO格納のパッケージ。
 * @author sho
 */
public class OrderLine {
	public Product Product;
	public int Quantity;
	public BigDecimal UnitPrice;

	@JsonCreator
	private OrderLine(@JsonProperty("product") Product product, @JsonProperty("quantity") int quantity,
			@JsonProperty("unitPrice") BigDecimal unitPrice) {
		Product = product;
		Quantity = quantity;
		UnitPrice = unitPrice;
	}

	public static OrderLine Create(Product product, int quantity, BigDecimal unitPrice) {
		return new OrderLine(product, quantity, unitPrice);
	}
}
