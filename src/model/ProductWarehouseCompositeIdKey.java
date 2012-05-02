package model;

import java.io.Serializable;
import java.util.StringTokenizer;

import javax.jdo.identity.LongIdentity;

/**
* This class is used to serve as a composite key for the ProductWarehouse
* model class using JDO annotation for persistence.
*
* @author Mahmood Khan
* @version 2012-05-01 1.0
*
*/
public class ProductWarehouseCompositeIdKey implements Serializable{
	/**
	 * This has to do with serialization. It is not important, but is placed
	 * here to prevent a compiler warning.
	 */
	private static final long serialVersionUID = 1L;
	
	public LongIdentity product;
	public LongIdentity warehouse;
	
	public ProductWarehouseCompositeIdKey(){
		
	}
	
	public ProductWarehouseCompositeIdKey(String s){
		StringTokenizer st = new StringTokenizer(s, "::");
		this.product = new LongIdentity(Product.class, st.nextToken());
		this.warehouse = new LongIdentity(Warehouse.class, st.nextToken());
	}
	
	public String toString() {
		return (product.toString() + "::" + warehouse.toString());
	}

	public int hashCode() {
		return product.hashCode() ^ warehouse.hashCode();
	}
	
	public boolean equals(Object other) {
		if (other != null && (other instanceof ProductWarehouseCompositeIdKey)) {
			ProductWarehouseCompositeIdKey otherPK = (ProductWarehouseCompositeIdKey) other;
			return this.product.equals(otherPK.product)
					&& this.warehouse.equals(otherPK.warehouse);
		}
		return false;
	}
}
