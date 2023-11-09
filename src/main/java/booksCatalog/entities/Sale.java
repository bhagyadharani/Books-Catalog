package booksCatalog.entities;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;

@Entity(name="Sale")
@Table(name="sales")
public class Sale {

	@EmbeddedId
	private SalePk key;
	
	@Positive
	@Column(name="qty_sold")
	private Integer qtySold;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id", updatable = false, insertable = false)
	@JsonIgnore
	private Store store;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "title_id", updatable = false, insertable = false)
	@JsonIgnore
	private Title title;
	
	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public Sale(SalePk key, Integer qtySold) {
		super();
		this.key = key;
		this.qtySold = qtySold;
	}

	public Sale() {
		super();
	}

	public SalePk getKey() {
		return key;
	}

	public void setKey(SalePk key) {
		this.key = key;
	}

	public Integer getQtySold() {
		return qtySold;
	}

	public void setQtySold(Integer qtySold) {
		this.qtySold = qtySold;
	}

	@Override
	public int hashCode() {
		return 1;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Sale))
			return false;
		Sale other = (Sale) obj;
		return Objects.equals(key, other.key) && Objects.equals(qtySold, other.qtySold);
	}

	@Override
	public String toString() {
		return "Sale [key=" + key + ", qtySold=" + qtySold + "]";
	}
	
}
