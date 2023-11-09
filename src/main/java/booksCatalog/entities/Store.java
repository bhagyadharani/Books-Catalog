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
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity(name="Store")
@Table(name="stores")
public class Store {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="store_id")
	@Positive
	private Integer storeId;
	
	@NotBlank
	@Size(max=30,message="Maximum Length is 30")
	@Column(name="location",length=30)
	private String location;
	
	@NotBlank
	@Size(max=20,message="Maximum Length is 20")
	@Column(name = "city", length = 20)
	private String city;

	@Size(max=30,message="Maximum Length is 30")
	@Column(name = "country", length = 30)
	private String country;
	
	@ManyToMany
	@JoinTable(name="sales",joinColumns=@JoinColumn(name="store_id"),
	inverseJoinColumns=@JoinColumn(name="title_id"))
	@JsonIgnore
	private Set<Title>  titles=new HashSet<Title>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "store")
	@JsonIgnore
	private List<Sale> sales = new ArrayList<Sale>();

	public List<Sale> getSales() {
		return sales;
	}

	public void setSales(List<Sale> sales) {
		this.sales = sales;
	}

	public Set<Title> getTitles() {
		return titles;
	}

	public void setTitles(Set<Title> titles) {
		this.titles = titles;
	}

	public Store(String location, String city, String country) {
		super();
		this.location = location;
		this.city = city;
		this.country = country;
	}

	public Store() {
		super();
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public int hashCode() {
		return Objects.hash(city, country, location, storeId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Store))
			return false;
		Store other = (Store) obj;
		return Objects.equals(city, other.city) && Objects.equals(country, other.country)
				&& Objects.equals(location, other.location) && Objects.equals(storeId, other.storeId);
	}

	@Override
	public String toString() {
		return  String.format("%d  %-30s  %-20s  %s", storeId, location, city, country);
	}
	
}
