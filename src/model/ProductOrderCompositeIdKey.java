package model;

import java.io.Serializable;
import java.util.StringTokenizer;

import javax.jdo.identity.LongIdentity;

/**
* This class is used to serve as a composite key for the ProductOrder
* model class using JDO annotation for persistence.
*
* @author Mahmood Khan
* @version 2012-02-29 1.0
*
*/
public class ProductOrderCompositeIdKey implements Serializable {
	
	/**
	 * This has to do with serialization. It is not important, but is placed
	 * here to prevent a compiler warning.
	 */
	private static final long serialVersionUID = 1L;

	public LongIdentity product;
	public LongIdentity purchaseOrder;

	public ProductOrderCompositeIdKey() {
	}

	public ProductOrderCompositeIdKey(String s) {
		StringTokenizer st = new StringTokenizer(s, "::");
		this.product = new LongIdentity(Product.class, st.nextToken());
		this.purchaseOrder = new LongIdentity(PurchaseOrder.class, st.nextToken());
	}

	public String toString() {
		return (product.toString() + "::" + purchaseOrder.toString());
	}

	public int hashCode() {
		return product.hashCode() ^ purchaseOrder.hashCode();
	}

	public boolean equals(Object other) {
		if (other != null && (other instanceof ProductOrderCompositeIdKey)) {
			ProductOrderCompositeIdKey otherPK = (ProductOrderCompositeIdKey) other;
			return this.product.equals(otherPK.product)
					&& this.purchaseOrder.equals(otherPK.purchaseOrder);
		}
		return false;
	}
}
