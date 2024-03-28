package org.listingbotv1.listingbot.listing;



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
	private String link;
	private double bedrooms,price;
	private String title;
	@Transient // Not going to show up in our database
	private int price_per_bedroom;
	
	
	public Listing() {	
	}
	
	
	public Listing(Long id,String link, double bedrooms, String title,double price) {
		this.id = id;
		this.link = link;
		this.bedrooms = bedrooms;
		this.price = price;
		this.title = title;
	}
	
	public Listing(String link, double bedrooms, String title,double price) {
		this.link = link;
		this.bedrooms = bedrooms;
		this.price = price;
		this.title = title;
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
	public double getBedrooms() {
		return bedrooms;
	}
	public void setBedrooms(double bedrooms) {
		this.bedrooms = bedrooms;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	public int getPrice_per_bedroom() {
		return (int) (this.getPrice()/this.getBedrooms());
	}



	@Override
	public String toString() {
		return "Listing [id=" + id + ",link=" + link + ", bedrooms=" + bedrooms + ", price=" + price + ", title=" + title + ", PPB=" + this.getPrice_per_bedroom() + "]";
	}
	
	
	
}
