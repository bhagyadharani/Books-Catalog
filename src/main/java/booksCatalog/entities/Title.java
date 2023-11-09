
package booksCatalog.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity(name="Title")
@Table(name="titles")
public class Title {

	@Id
	@Column(name="title_id",length=6)
	@Pattern(regexp="^[A-Z]{2}[0-9]{4}$",message="Title Id must be a String and starts with 2 Capital Letters followed by 4 digits")
	private String titleId;
	
	@Column(name="title",length=80)
	@NotBlank
	@Size(max=80,message="Maximum Length is 80")
	private String title;
	
	@Positive
	@Column(name="price")
	
	private Double price;
	
	@Pattern(regexp="^[9]\\d{3}$",message="Publisher Id must be a Four Digit Number and starts with 9")
	@Column(name="pub_id",length=4)
	private String pubId;
	
	@Positive
	@Column(name="ytd_sales")
	private Integer ytdSales;
	
	@NotBlank
	@Pattern(regexp="^[1-2]\\d{3}[-][0-1][0-9][-][0-3][0-9]$",message="Release Year format must be 'yyyy-mm-dd'")
	@Column(name="release_year")
	private String releaseYear;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pub_id", updatable = false, insertable = false)
	@JsonIgnore
	private Publisher publisher;
	
	@ManyToMany
	@JoinTable(name="titleauthors",joinColumns=@JoinColumn(name="title_id"),
	inverseJoinColumns=@JoinColumn(name="au_id"))
	@JsonIgnore
	private Set<Author>  authors=new HashSet<Author>();
	
	@ManyToMany(mappedBy="titles")
	@JsonIgnore
	private Set<Store> stores=new HashSet<Store>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "title")
	@JsonIgnore
	private List<Sale> sales = new ArrayList<Sale>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "title")
	@JsonIgnore
	private List<TitleAuthor> titleAuthors = new ArrayList<TitleAuthor>();
	
	public List<TitleAuthor> getTitleAuthors() {
		return titleAuthors;
	}

	public void setTitleAuthors(List<TitleAuthor> titleAuthors) {
		this.titleAuthors = titleAuthors;
	}

	public Set<Store> getStores() {
		return stores;
	}

	public void setStores(Set<Store> stores) {
		this.stores = stores;
	}

	public List<Sale> getSales() {
		return sales;
	}

	public void setSales(List<Sale> sales) {
		this.sales = sales;
	}

	public Title(String titleId, String title, Double price, String pubId, Integer ytdSales, String releaseYear) {
		super();
		this.titleId = titleId;
		this.title = title;
		this.price = price;
		this.pubId = pubId;
		this.ytdSales = ytdSales;
		this.releaseYear = releaseYear;
	}

	public Title() {
		super();
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public Set<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(Set<Author> authors) {
		this.authors = authors;
	}

	public String getTitleId() {
		return titleId;
	}

	public void setTitleId(String titleId) {
		this.titleId = titleId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getPubId() {
		return pubId;
	}

	public void setPubId(String pubId) {
		this.pubId = pubId;
	}

	public Integer getYtdSales() {
		return ytdSales;
	}

	public void setYtdSales(Integer ytdSales) {
		this.ytdSales = ytdSales;
	}

	public String getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(String releaseYear) {
		this.releaseYear = releaseYear;
	}

	@Override
	public int hashCode() {
		return 1;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Title))
			return false;
		Title other = (Title) obj;
		return Objects.equals(price, other.price) && Objects.equals(pubId, other.pubId)
				&& Objects.equals(releaseYear, other.releaseYear) && Objects.equals(title, other.title)
				&& Objects.equals(titleId, other.titleId) && Objects.equals(ytdSales, other.ytdSales);
	}

	@Override
	public String toString() {
		return String.format("%s  %-80s %-10f %-6s %d  %s", titleId, title, price, pubId, ytdSales, releaseYear);
	}
	
}
