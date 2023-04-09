/**
 * 
 */
package org.tedros.stock.model;

import org.tedros.server.model.TReportModel;

/**
 * @author Davis Gordon
 *
 */
public class ProductReportModel extends TReportModel<ProductItem> {

	private static final long serialVersionUID = 1963594675124013687L;

	private String name;
	private String codes;
	private String trademark;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the codes
	 */
	public String getCodes() {
		return codes;
	}
	/**
	 * @param codes the codes to set
	 */
	public void setCodes(String codes) {
		this.codes = codes;
	}
	/**
	 * @return the trademark
	 */
	public String getTrademark() {
		return trademark;
	}
	/**
	 * @param trademark the trademark to set
	 */
	public void setTrademark(String trademark) {
		this.trademark = trademark;
	}

}
