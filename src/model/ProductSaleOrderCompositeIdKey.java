package model;

import java.io.Serializable;
import java.util.StringTokenizer;

import javax.jdo.identity.LongIdentity;

/**
* This class is used to serve as a composite key for the SaleOrderItem
* model class using JDO annotation for persistence.
*
* @author Mahmood Khan
* @version 2012-04-24 1.0
*
*/
public class ProductSaleOrderCompositeIdKey implements Serializable {
	
	/**
	 * This has to do with serialization. It is not important, but is placed
	 * here to prevent a compiler warning.
	 */
	private static final long serialVersionUID = 1L;

	public LongIdentity product;
	public LongIdentity saleOrder;

	public ProductSaleOrderCompositeIdKey() {
	}

	public ProductSaleOrderCompositeIdKey(String s) {
		StringTokenizer st = new StringTokenizer(s, "::");
		this.product = new LongIdentity(Product.class, st.nextToken());
		this.saleOrder = new LongIdentity(SaleOrder.class, st.nextToken());
	}

	public String toString() {
		return (product.toString() + "::" + saleOrder.toString());
	}

	public int hashCode() {
		return product.hashCode() ^ saleOrder.hashCode();
	}

	public boolean equals(Object other) {
		if (other != null && (other instanceof ProductSaleOrderCompositeIdKey)) {
			ProductSaleOrderCompositeIdKey otherPK = (ProductSaleOrderCompositeIdKey) other;
			return this.product.equals(otherPK.product)
					&& this.saleOrder.equals(otherPK.saleOrder);
		}
		return false;
	}
}
