package org.listingbotv1.listingbot.model;



import jakarta.persistence.*;



@Entity
@Table

public class Listing {
	@Id
	@SequenceGenerator(name="listing_sequence",sequenceName="listing_sequence",allocationSize=1)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "listing_sequence"
			)
	
	private long id;
	@Column(unique = true)
	private String link;
	private String lease_term,address;
	private String time_posted; // This will be the html element 'datetime' always in UTC.
	private double house_size,price;

	
	
	public Listing() {	
	}
	
	
	public Listing(Long id,String link, double house_size, String lease_term,String address,double price,String time_posted) {
		this.id = id;
		this.lease_term = lease_term;
		this.link = link;
		this.house_size = house_size;
		this.price = price;
		this.address = address;
		this.time_posted = time_posted;
	}
	
	public Listing(String link, double house_size, String lease_term,String address,double price,String time_posted) {
		this.link = link;
		this.house_size = house_size;
		this.price = price;
		this.lease_term = lease_term;
		this.address = address;
		this.time_posted = time_posted;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public double gethouse_size() {
		return house_size;
	}
	public void sethouse_size(double house_size) {
		this.house_size = house_size;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

	public String getLease_term() {
		return lease_term;
	}

	public void setLease_term(String lease_term) {
		this.lease_term = lease_term;
	}






	
	
	
}
