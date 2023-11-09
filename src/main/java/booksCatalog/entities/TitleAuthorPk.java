package booksCatalog.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Pattern;

@Embeddable
public class TitleAuthorPk implements Serializable{
	
	@Pattern(regexp="^\\d{2}[-]\\d{4}$",message="Author Id must be in '99-9999' format")
	@Column(name="au_id",length=11)
	private String auId;
	
	@Pattern(regexp="^[A-Z]{2}[0-9]{4}$",message="Title Id must be a String and starts with 2 Capital Letters followed by 4 digits")
	@Column(name="title_id",length=6)
	private String titleId;

	public String getAuId() {
		return auId;
	}

	public void setAuId(String auId) {
		this.auId = auId;
	}

	public String getTitleId() {
		return titleId;
	}

	public void setTitleId(String titleId) {
		this.titleId = titleId;
	}

	@Override
	public int hashCode() {
		return 1;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof TitleAuthorPk))
			return false;
		TitleAuthorPk other = (TitleAuthorPk) obj;
		return this.auId.equals(other.auId) && this.titleId.equals(other.titleId);
	}

	@Override
	public String toString() {
		return "TitleAuthorPk [auId=" + auId + ", titleId=" + titleId + "]";
	}

}
