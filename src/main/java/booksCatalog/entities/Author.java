package booksCatalog.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity(name = "Author")
@Table(name = "authors")
public class Author {

	@Id
	@Column(name = "au_id", length = 7)
	@Size(min=7,max=7,message="Author Id length must be 7")
	@Pattern(regexp="^\\d{2}[-]\\d{4}$",message="Author Id must be in '99-9999' format")
	private String auId;

	@Size(max=50,message="Maximum Length is 50")
	@NotBlank
	@Column(name = "au_name", length = 50)
	private String auName;

	@Email
	@Size(max=40,message="Maximum Length is 40")
	@Column(name = "email", length = 40)
	private String email;

	@Pattern(regexp="^\\d{10}$",message="Mobile number must be a 10 digit number")
	@Column(name = "mobile", length = 13)
	private String mobile;

	@Size(max=20,message="Maximum Length is 20")
	@Column(name = "city", length = 20)
	private String city;

	@Size(max=30,message="Maximum Length is 30")
	@Column(name = "country", length = 30)
	private String country;
	
	@ManyToMany(mappedBy="authors")
	@JsonIgnore
	private Set<Title> titles=new HashSet<Title>();

	public Author(String auId, String auName, String email, String mobile, String city, String country) {
		super();
		this.auId = auId;
		this.auName = auName;
		this.email = email;
		this.mobile = mobile;
		this.city = city;
		this.country = country;
	}

	public Set<Title> getTitles() {
		return titles;
	}

	public void setTitles(Set<Title> titles) {
		this.titles = titles;
	}



	public Author() {
		super();
	}

	public String getAuId() {
		return auId;
	}

	public void setAuId(String auId) {
		this.auId = auId;
	}

	public String getAuName() {
		return auName;
	}

	public void setAuName(String auName) {
		this.auName = auName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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
		if (!(obj instanceof Author))
			return false;
		Author other = (Author) obj;
		return Objects.equals(auId, other.auId) && Objects.equals(auName, other.auName)
				&& Objects.equals(city, other.city) && Objects.equals(country, other.country)
				&& Objects.equals(email, other.email) && Objects.equals(mobile, other.mobile);
	}

	@Override
	public String toString() {
		return String.format("%s  %-40s %-40s %-15s %-20s  %s", auId, auName, email, mobile, city, country);
	}

}
