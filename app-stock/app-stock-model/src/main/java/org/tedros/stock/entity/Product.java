/**
 * 
 */
package org.tedros.stock.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.tedros.common.model.TFileEntity;
import org.tedros.server.annotation.TCaseSensitive;
import org.tedros.server.annotation.TEntityImportRule;
import org.tedros.server.annotation.TFieldImportRule;
import org.tedros.server.annotation.TFileType;
import org.tedros.server.entity.TVersionEntity;
import org.tedros.stock.domain.DomainSchema;
import org.tedros.stock.domain.DomainTables;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.product, schema = DomainSchema.schema, 
uniqueConstraints= {@UniqueConstraint(name="prodCodeUK", columnNames = { "code" })} )
@TEntityImportRule(description = "Regras para importar um arquivo para a tabela de produtos ", 
fileType = { TFileType.CSV, TFileType.XLS })
public class Product extends TVersionEntity {

	
	private static final long serialVersionUID = -4590169775657544834L;

	@Column(length=60)
	@TFieldImportRule(required = false, 
		description = "Codigo do produto", column = "Codigo", 
		numberType=Integer.class, example="22")
	private String code;
	
	@Column(length=120, nullable = false)
	@TFieldImportRule(required = true, 
	description = "Nome do produto", column = "Nome Produto",
	example="Arroz")
	private String name;
	
	@Column
	private String description;
	
	@Column(length=120)
	private String trademark;
	
	@Column(length=20)
	@TFieldImportRule(required = true, 
	description = "Unidade medida", column = "Unidade Medida",
	example="KG", caseSensitive=TCaseSensitive.UPPER, 
	possibleValues= {"KG", "LT", "UNID","PCT","ML","GR"})
	private String unitMeasure;
	
	@Column
	private Double measure;
	
	@Column(length=15)
	private String size;
	
	@Column
	private Double weight;
	
	@ManyToMany
	@JoinTable(name=DomainTables.product_images, schema=DomainSchema.schema, 
	uniqueConstraints=@UniqueConstraint(columnNames = { "prod_id", "file_id" }),
	joinColumns=@JoinColumn(name="prod_id"), inverseJoinColumns=@JoinColumn(name="file_id"))
	private List<TFileEntity> images;
	
	public Product() {
	}
	
	public Product(String cod) {
		this.code = cod;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTrademark() {
		return trademark;
	}

	public void setTrademark(String trademark) {
		this.trademark = trademark;
	}

	public String getUnitMeasure() {
		return unitMeasure;
	}

	public void setUnitMeasure(String unitMeasure) {
		this.unitMeasure = unitMeasure;
	}

	public Double getMeasure() {
		return measure;
	}

	public void setMeasure(Double measure) {
		this.measure = measure;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public List<TFileEntity> getImages() {
		return images;
	}

	public void setImages(List<TFileEntity> images) {
		this.images = images;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((images == null) ? 0 : images.hashCode());
		result = prime * result + ((measure == null) ? 0 : measure.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((size == null) ? 0 : size.hashCode());
		result = prime * result + ((trademark == null) ? 0 : trademark.hashCode());
		result = prime * result + ((unitMeasure == null) ? 0 : unitMeasure.hashCode());
		result = prime * result + ((weight == null) ? 0 : weight.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Product))
			return false;
		if (super.equals(obj))
			return true;
		Product other = (Product) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (images == null) {
			if (other.images != null)
				return false;
		} else if (!images.equals(other.images))
			return false;
		if (measure == null) {
			if (other.measure != null)
				return false;
		} else if (!measure.equals(other.measure))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (size == null) {
			if (other.size != null)
				return false;
		} else if (!size.equals(other.size))
			return false;
		if (trademark == null) {
			if (other.trademark != null)
				return false;
		} else if (!trademark.equals(other.trademark))
			return false;
		if (unitMeasure == null) {
			if (other.unitMeasure != null)
				return false;
		} else if (!unitMeasure.equals(other.unitMeasure))
			return false;
		if (weight == null) {
			if (other.weight != null)
				return false;
		} else if (!weight.equals(other.weight))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return (name != null ? name : "");
	}

}