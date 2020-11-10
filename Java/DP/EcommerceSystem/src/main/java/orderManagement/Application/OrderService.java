package orderManagement.Application;

import orderManagement.Domain.Interfaces.*;
import orderManagement.Domain.Address;
import orderManagement.Domain.Order;
import orderManagement.Domain.OrderLine;
import orderManagement.Domain.Product;
import orderManagement.Domain.TransitLocation;
import orderManagement.Application.Interfaces.IOrderService;
import orderManagement.Application.Interfaces.IPublisher;
import orderManagement.Contracts.Input.PlaceOrderRequest;
import orderManagement.Contracts.Output.PlaceOrderResponse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

/**
 * DDDにおける「アプリケーションサービス」
 * Repositoryの依存関係はDIによるインジェクションされるため、
 * 「アプリケーションサービス」自体がインフラストラクチャ層のリポジトリに依存しない。
 * @author sho
 */
@Service
public class OrderService implements IOrderService {
	private IPublisher _publisher;
	private ICostCalculatorService _costCalculatorService;
	private IOrderRepository _orderRepository;
	private IOrderTrackingRepository _orderTrackingRepository;
	private IProductAvailabilityService _productAvailabilityService;

	public OrderService(final IPublisher publisher, final ICostCalculatorService costCalculatorService,
			final IOrderRepository orderRepository, final IOrderTrackingRepository orderTrackingRepository,
			final IProductAvailabilityService productAvailabilityService) {
		super();
		this._publisher = publisher;
		this._costCalculatorService = costCalculatorService;
		this._orderRepository = orderRepository;
		this._orderTrackingRepository = orderTrackingRepository;
		this._productAvailabilityService = productAvailabilityService;
	}

	/**
	 * 注文履歴を取得
	 */
	@Override
	public List<orderManagement.Contracts.Common.Order> GetOrderHistory(final int customerId) {
		// リポジトリからOrderを読込(カスタマIDに紐づくOrder全て)
		List<Order> orders = _orderRepository.Search(customerId);
		// リストに格納
		List<orderManagement.Contracts.Common.Order> listOrders = new ArrayList<orderManagement.Contracts.Common.Order>();
		for (Order order : orders) {
			listOrders.add(MapToContract(order, null));
		}
		return listOrders;
	}

	/**
	 * 追跡情報を取得
	 */
	@Override
	public List<orderManagement.Contracts.Common.TransitLocation> GetOrderTrackingInfo(final int orderId) {
		// リポジトリからOrderを読込
		Order domainOrder = _orderRepository.Load(orderId);
		List<orderManagement.Contracts.Common.TransitLocation> listTransitLocation = new ArrayList<orderManagement.Contracts.Common.TransitLocation>();
		if (domainOrder == null)
			return listTransitLocation;

		// ドメインであるOrderにorderTrackingRepositoryを設定
		domainOrder.WithOrderTrackingRepository(_orderTrackingRepository);
		// ドメインOrderが保有するドメインIDを基にTransitLocationsを取得
		domainOrder.LoadTransitLocations();

		// 取得したTransitLocationsをリストに格納
		for (TransitLocation transit : domainOrder.TransitLocations) {
			listTransitLocation.add(orderManagement.Contracts.Common.TransitLocation.Create(transit.Name, transit.mDate,
					orderManagement.Contracts.Common.Address.Create(domainOrder.BillingAddress.AddressLine1,
							domainOrder.BillingAddress.AddressLine2, domainOrder.BillingAddress.Country)));
		}
		return listTransitLocation;
	}

	/**
	 * 注文する
	 */
	@Override
	public PlaceOrderResponse PlaceOrder(final PlaceOrderRequest request) {
		// requestからOrderLineを生成し、リストに格納
		List<OrderLine> orderLines = new ArrayList<OrderLine>();
		for (orderManagement.Contracts.Common.OrderLine orderLine : request.Order.OrderLines) {
			orderLines
					.add(OrderLine.Create(Product.Create(orderLine.Product.Stockcode, orderLine.Product.ProductImageUrl,
							orderLine.Product.VolumetricWeight), orderLine.Quantity, orderLine.UnitPrice));
		}
		
		Order domainOrder = Order.Create(orderLines, request.Order.CustomerId,
				Address.Create(request.Order.BillingAddress.AddressLine1, request.Order.BillingAddress.AddressLine2,
						request.Order.BillingAddress.Country),
				Address.Create(request.Order.ShippingAddress.AddressLine1, request.Order.ShippingAddress.AddressLine2,
						request.Order.ShippingAddress.Country),
				request.Order.PromotionCode, request.Order.DatePlaced, _costCalculatorService,
				_productAvailabilityService, _orderTrackingRepository);

		// validation実施
		if (domainOrder.CanPlaceOrder(request.ExpectedTotalCost, request.ExpectedShippingCost)) {
			// 登録
			int orderId = _orderRepository.Store(domainOrder);
			PlaceOrderResponse response = PlaceOrderResponse.Create(true, String.valueOf(""),
					BigDecimal.valueOf(orderId));
			// publish
			_publisher.Publish(MapToContract(domainOrder, orderId));
			return response;
		} else {
			PlaceOrderResponse response = PlaceOrderResponse.Create(false, "Order validation failed", null);
			return response;
		}
	}

	private orderManagement.Contracts.Common.Order MapToContract(final Order order, final Integer orderId) {
		List<orderManagement.Contracts.Common.OrderLine> orderLines = new ArrayList<orderManagement.Contracts.Common.OrderLine>();
		for (OrderLine orderLine : order.OrderLines) {
			orderLines.add(orderManagement.Contracts.Common.OrderLine.Create(
					orderManagement.Contracts.Common.Product.Create(orderLine.Product.Stockcode,
							orderLine.Product.ProductImageUrl, orderLine.Product.VolumetricWeight),
					orderLine.Quantity, orderLine.UnitPrice));
		}

		return orderManagement.Contracts.Common.Order.Create((orderId != null) ? orderId : order.OrderId, orderLines,
				order.CustomerId, order.TotalCost, order.ShippingCost,
				orderManagement.Contracts.Common.Address.Create(order.BillingAddress.AddressLine1,
						order.BillingAddress.AddressLine2, order.BillingAddress.Country),
				orderManagement.Contracts.Common.Address.Create(order.ShippingAddress.AddressLine1,
						order.ShippingAddress.AddressLine2, order.ShippingAddress.Country),
				order.PromotionCode, order.DatePlaced);
	}
}
