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

@Entity(name="TitleAuthor")
@Table(name="titleauthors")
public class TitleAuthor {

	@EmbeddedId
	private TitleAuthorPk key;
	
	@Positive
	@Column(name="royalty_pct")
	private Integer royaltyPct;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "au_id", updatable = false, insertable = false)
	@JsonIgnore
	private Author author;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "title_id", updatable = false, insertable = false)
	@JsonIgnore
	private Title title;

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public Title getTitle() {
		return title;
	}

	public void setTitle(Title title) {
		this.title = title;
	}

	public TitleAuthorPk getKey() {
		return key;
	}

	public void setKey(TitleAuthorPk key) {
		this.key = key;
	}

	public Integer getRoyaltyPct() {
		return royaltyPct;
	}

	public void setRoyaltyPct(Integer royaltyPct) {
		this.royaltyPct = royaltyPct;
	}

	@Override
	public int hashCode() {
		return Objects.hash(key, royaltyPct);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof TitleAuthor))
			return false;
		TitleAuthor other = (TitleAuthor) obj;
		return Objects.equals(key, other.key) && Objects.equals(royaltyPct, other.royaltyPct);
	}

	@Override
	public String toString() {
		return "TitleAuthor [key=" + key + ", royaltyPct=" + royaltyPct + "]";
	}
	
	
}
