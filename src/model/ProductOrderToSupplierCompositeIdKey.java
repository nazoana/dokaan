package model;

import java.io.Serializable;
import java.util.StringTokenizer;

import javax.jdo.identity.LongIdentity;

/**
* This class is used to serve as a composite key for the ProductPurchaseOrderItem
* model class using JDO annotation for persistence.
*
* @author Mahmood Khan
* @version 2012-02-29 1.0
*
*/
public class ProductOrderToSupplierCompositeIdKey implements Serializable {
	
	/**
	 * This has to do with serialization. It is not important, but is placed
	 * here to prevent a compiler warning.
	 */
	private static final long serialVersionUID = 1L;

	public LongIdentity product;
	public LongIdentity orderToSupplier;

	public ProductOrderToSupplierCompositeIdKey() {
	}

	public ProductOrderToSupplierCompositeIdKey(String s) {
		StringTokenizer st = new StringTokenizer(s, "::");
		this.product = new LongIdentity(Product.class, st.nextToken());
		this.orderToSupplier = new LongIdentity(OrderToSupplier.class, st.nextToken());
	}

	public String toString() {
		return (product.toString() + "::" + orderToSupplier.toString());
	}

	public int hashCode() {
		return product.hashCode() ^ orderToSupplier.hashCode();
	}

	public boolean equals(Object other) {
		if (other != null && (other instanceof ProductOrderToSupplierCompositeIdKey)) {
			ProductOrderToSupplierCompositeIdKey otherPK = (ProductOrderToSupplierCompositeIdKey) other;
			return this.product.equals(otherPK.product)
					&& this.orderToSupplier.equals(otherPK.orderToSupplier);
		}
		return false;
	}
}
