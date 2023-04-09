/**
 * 
 */
package org.tedros.stock.model;

import java.util.ArrayList;
import java.util.List;

import org.tedros.server.model.ITReportItemModel;
import org.tedros.stock.entity.Product;

/**
 * @author Davis Gordon
 *
 */
public class ProductItem implements ITReportItemModel<Product>  {
	
	private static final long serialVersionUID = 3727717064033502648L;
	private Product model;
	private String code;
	private String name;
	private String description;
	private String trademark;
	private String unitMeasure;
	private Double measure;
	private String size;
	private Double weight;
	private List<ImageItem> images;

	public ProductItem(Product model) {
		super();
		this.model = model;
		this.code = model.getCode();
		this.name = model.getName();
		this.description = model.getDescription();
		this.trademark = model.getTrademark();
		this.unitMeasure = model.getUnitMeasure();
		this.measure = model.getMeasure();
		this.size = model.getSize();
		this.weight = model.getWeight();
		this.images = new ArrayList<>();
		if(model.getImages()!=null)
			model.getImages().forEach(i->{
				images.add(new ImageItem(i.getByteEntity().getBytes()));
			});
	}

	/**
	 * @return the model
	 */
	public Product getModel() {
		return model;
	}
	
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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

	/**
	 * @return the unitMeasure
	 */
	public String getUnitMeasure() {
		return unitMeasure;
	}

	/**
	 * @param unitMeasure the unitMeasure to set
	 */
	public void setUnitMeasure(String unitMeasure) {
		this.unitMeasure = unitMeasure;
	}

	/**
	 * @return the measure
	 */
	public Double getMeasure() {
		return measure;
	}

	/**
	 * @param measure the measure to set
	 */
	public void setMeasure(Double measure) {
		this.measure = measure;
	}

	/**
	 * @return the size
	 */
	public String getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(String size) {
		this.size = size;
	}

	/**
	 * @return the weight
	 */
	public Double getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(Double weight) {
		this.weight = weight;
	}

	/**
	 * @return the images
	 */
	public List<ImageItem> getImages() {
		return images;
	}

	/**
	 * @param images the images to set
	 */
	public void setImages(List<ImageItem> images) {
		this.images = images;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

}
