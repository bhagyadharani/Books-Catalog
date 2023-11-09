package booksCatalog.entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

@Embeddable
public class SalePk implements Serializable{
	@Positive
	@Column(name="store_id")
	private Integer storeId;
	
	@Pattern(regexp="^[A-Z]{2}[0-9]{4}$",message="Title Id must be a String and starts with 2 Capital Letters followed by 4 digits")
	@Column(name="title_id",length=6)
	private String titleId;

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public String getTitleId() {
		return titleId;
	}

	public void setTitleId(String titleId) {
		this.titleId = titleId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(storeId, titleId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof SalePk))
			return false;
		SalePk other = (SalePk) obj;
		return Objects.equals(storeId, other.storeId) && Objects.equals(titleId, other.titleId);
	}

	@Override
	public String toString() {
		return "SalePk [storeId=" + storeId + ", titleId=" + titleId + "]";
	}
	
}
