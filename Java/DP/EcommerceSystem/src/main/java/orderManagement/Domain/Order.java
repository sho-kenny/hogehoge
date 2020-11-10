package orderManagement.Domain;

import orderManagement.Domain.Interfaces.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.math.BigDecimal;

/**
 * DDDにおける「Domain Model」
 * ただし、このOrderは「Aggregation」のroot Entityとなる。
 * @author kenny
 */
public class Order {
	private ICostCalculatorService _costCalculatorService;
	private IOrderTrackingRepository _orderTrackingRepository;
	private IProductAvailabilityService _productAvailabilityService;

	public int OrderId;
	public List<OrderLine> OrderLines;
	public int CustomerId;
	public BigDecimal ShippingCost;
	public BigDecimal TotalCost;
	public Address BillingAddress;
	public Address ShippingAddress;
	public String PromotionCode;
	public Date DatePlaced;
	public List<TransitLocation> TransitLocations;

	private Order(int orderId, List<OrderLine> orderLines, int customerId, BigDecimal totalCost,
			BigDecimal shippingCost, Address billingAddress, Address shippingAddress, String promotionCode,
			Date datePlaced, List<TransitLocation> transitLocations, ICostCalculatorService costCalculatorService,
			IProductAvailabilityService productAvailabilityService, IOrderTrackingRepository orderTrackingRepository) {
		OrderId = orderId;
		OrderLines = orderLines;
		CustomerId = customerId;
		TotalCost = totalCost;
		ShippingCost = shippingCost;
		PromotionCode = promotionCode;
		BillingAddress = billingAddress;
		ShippingAddress = shippingAddress;
		DatePlaced = datePlaced;
		TransitLocations = transitLocations;
		_costCalculatorService = costCalculatorService;
		_orderTrackingRepository = orderTrackingRepository;
		_productAvailabilityService = productAvailabilityService;
	}

	// Factory
	public static Order Create(List<OrderLine> orderLines, int customerId, Address billingAddress,
			Address shippingAddress, String promotionCode, Date datePlaced,
			ICostCalculatorService costCalculatorService, IProductAvailabilityService productAvailabilityService,
			IOrderTrackingRepository orderTrackingRepository) {
		return new Order(-1, orderLines, customerId, BigDecimal.valueOf(-1), BigDecimal.valueOf(-1), billingAddress,
				shippingAddress, promotionCode, datePlaced, null, costCalculatorService, productAvailabilityService,
				orderTrackingRepository);
	}

	// シグネチャが異なるFactory
	public static Order Create(int orderId, List<OrderLine> orderLines, int customerId, BigDecimal totalCost,
			BigDecimal shippingCost, Address billingAddress, Address shippingAddress, String promotionCode,
			Date datePlaced) {
		return new Order(orderId, orderLines, customerId, totalCost, shippingCost, billingAddress, shippingAddress,
				promotionCode, datePlaced, null, null, null, null);
	}

	// Domain領域の発送費の計算ロジック
	private void CalculateShippingCost() {
		List<Product> products = new ArrayList<Product>();
		for (OrderLine orderLine : OrderLines) {
			products.add(orderLine.Product);
		}
		ShippingCost = _costCalculatorService.CalculateShippingPrice(products, ShippingAddress);
	}

	// Domain領域の総費用の計算ロジック
	private void CalculateTotalCost() {
		TotalCost = _costCalculatorService.CalculateTotalPrice(OrderLines, PromotionCode);
	}

	// 注文可能かチェック
	public boolean CanPlaceOrder(BigDecimal expectedTotalCost, BigDecimal expectedShippingCost) {
		// OrderLineの数を調べる
		if (OrderLines.isEmpty())
			return false;

		// 全てのproductがorderに適用できてるか
		for (OrderLine line : OrderLines) {
			if (!_productAvailabilityService.CheckProductAvailability(line.Product.Stockcode, line.Quantity))
				return false;
		}

		// 計算されたコストが予想したコストと合っているか
		CalculateShippingCost();
		CalculateTotalCost();
		if (TotalCost != expectedTotalCost || ShippingCost != expectedShippingCost)
			return false;

		// 全てのチェックが問題ない場合にtrue
		return true;
	}

	public void LoadTransitLocations() {
		TransitLocations = _orderTrackingRepository.GetTransitLocations(OrderId);
	}

	public void WithOrderTrackingRepository(IOrderTrackingRepository orderTrackingRepository) {
		_orderTrackingRepository = orderTrackingRepository;
	}
}
