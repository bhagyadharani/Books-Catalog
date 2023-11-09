package booksCatalog.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity(name = "booksCatalog.entities.Publisher")
@Table(name = "publishers")
public class Publisher {

	@Id
	@Column(name = "pub_id", length = 4)
	@Pattern(regexp="^[9]\\d{3}$",message="Publisher Id must be a Four Digit Number and starts with 9")
	private String pubId;
	
	@Size(max=40,message="Maximum Length is 40")
	@NotBlank
	@Column(name = "pub_name", length = 40)
	private String pubName;

	@Email
	@Size(max=40,message="Maximum Length is 40")
	@Column(name = "email", length = 40)
	private String email;

	@Size(max=20,message="Maximum Length is 20")
	@Column(name = "city", length = 20)
	private String city;

	@Size(max=30,message="Maximum Length is 30")
	@Column(name = "country", length = 30)
	private String country;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "publisher")
	@JsonIgnore
	private List<Title> titles = new ArrayList<Title>();

	public Publisher(String pubId, String pubName, String email, String city, String country) {
		super();
		this.pubId = pubId;
		this.pubName = pubName;
		this.email = email;
		this.city = city;
		this.country = country;
	}

	public Publisher() {
		super();
	}
	
	public List<Title> getTitles() {
		return titles;
	}

	public void setTitles(List<Title> titles) {
		this.titles = titles;
	}

	public String getPubId() {
		return pubId;
	}

	public void setPubId(String pubId) {
		this.pubId = pubId;
	}

	public String getPubName() {
		return pubName;
	}

	public void setPubName(String pubName) {
		this.pubName = pubName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
		return 1;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Publisher))
			return false;
		Publisher other = (Publisher) obj;
		return Objects.equals(city, other.city) && Objects.equals(country, other.country)
				&& Objects.equals(email, other.email) && Objects.equals(pubId, other.pubId)
				&& Objects.equals(pubName, other.pubName);
	}

	@Override
	public String toString() {
		return String.format("%s  %-40s %-40s  %-20s  %s", pubId, pubName, email, city, country);
	}

}
