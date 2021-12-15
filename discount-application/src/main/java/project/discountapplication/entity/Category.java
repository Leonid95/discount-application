package project.discountapplication.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "categories")
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	@OneToMany(mappedBy = "category", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	@JsonIgnore
	private List<Discount> discounts;

	public Category() {
	}

	public Category(String title, String description) {
		this.title = title;
		this.description = description;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Discount> getDiscounts() {
		return discounts;
	}

	public void setDiscounts(List<Discount> discounts) {
		this.discounts = discounts;
	}

	@JsonIgnore
	public List<Discount> getValidDiscounts() {
		List<Discount> validDiscounts = new ArrayList<>();

		validDiscounts.addAll(discounts);

		// Remove all invalid discounts from the list
		removeInvalidDiscounts(validDiscounts);

		return validDiscounts;
	}

	// Following function removes all the invalid discounts from the list of
	// discounts
	// Invalid discounts are all the discounts that have visibility status set as
	// false
	// and also all the discounts that are remaining after their end date expired
	private void removeInvalidDiscounts(List<Discount> discounts) {
		Date currentDate = new Date();

		for (int i = 0; i < discounts.size();) {
			if (discounts.get(i).getStatus() == false || discounts.get(i).getEndDate().compareTo(currentDate) < 0) {
				discounts.remove(i);
			} else {
				i++;
			}
		}
	}

	public void add(Discount theDiscount) {
		if (discounts == null) {
			discounts = new ArrayList<Discount>();
		}

		discounts.add(theDiscount);

		theDiscount.setCategory(this);
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", title=" + title + ", description=" + description + "]";
	}

}
