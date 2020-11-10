package orderManagement.Contracts.Common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * request/responseに対応するDTO。
 * DDDを活用するからといって全てのクラスがEntityやValue Objectに該当するわけではない。
 * orderManagement.Contracts.Commonのパッケージは全てDTO格納のパッケージ。
 * @author sho
 */
public class Address {
	public String AddressLine1;
	public String AddressLine2;
	public String Country;

	@JsonCreator
	private Address(@JsonProperty("addressLine1") String addressLine1,
			@JsonProperty("addressLine2") String addressLine2, @JsonProperty("country") String country) {
		AddressLine1 = addressLine1;
		AddressLine2 = addressLine2;
		Country = country;
	}

	public static Address Create(String addressLine1, String addressLine2, String country) {
		return new Address(addressLine1, addressLine2, country);
	}

}
