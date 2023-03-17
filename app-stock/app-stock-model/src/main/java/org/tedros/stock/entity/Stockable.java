package org.tedros.stock.entity;

import java.util.Date;
import java.util.List;

import org.tedros.server.entity.ITEntity;

public interface Stockable extends ITEntity{

	/**
	 * @return the date
	 */
	Date getDate();

	/**
	 * @param date the date to set
	 */
	void setDate(Date date);

	/**
	 * @return the costCenter
	 */
	CostCenter getCostCenter();

	/**
	 * @param costCenter the costCenter to set
	 */
	void setCostCenter(CostCenter costCenter);

	/**
	 * @return the items
	 */
	List<? extends StockableItem> getItems();


}