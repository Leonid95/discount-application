package project.discountapplication.entity;

import java.math.BigDecimal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "discounts")
public class Discount {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	@Column(name = "discount_price")
	private BigDecimal discountPrice;

	@Column(name = "regular_price")
	private BigDecimal regularPrice;

	@Column(name = "user_id")
	private Long userId;

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST })
	@JoinColumn(name = "category_id")
	private Category category;

	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "status")
	private Boolean status;

	public Discount() {
	}

	public Discount(String title, String description, BigDecimal discountPrice, BigDecimal regularPrice, Long userId,
			Category category, Date startDate, Date endDate, Boolean status) {
		super();
		this.title = title;
		this.description = description;
		this.discountPrice = discountPrice;
		this.regularPrice = regularPrice;
		this.userId = userId;
		this.category = category;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
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

	public BigDecimal getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(BigDecimal discountPrice) {
		this.discountPrice = discountPrice;
	}

	public BigDecimal getRegularPrice() {
		return regularPrice;
	}

	public void setRegularPrice(BigDecimal regularPrice) {
		this.regularPrice = regularPrice;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	// Methods for pseudo-fields startDateText and endDateText were created
	// to convert String outputs of a form to the objects of java.util.Date type

	public void setStartDateText(String startDateText) {
		try {
			this.startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateText);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public String getStartDateText() {
		if (this.startDate == null) {
			return "";
		} else {
			// Return a string that represents date in yyyy-mm-dd format. Without hours,
			// minutes and seconds.
			return this.startDate.toString().substring(0, 10);
		}

	}

	public void setEndDateText(String endDateText) {
		try {
			this.endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDateText);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public String getEndDateText() {
		if (this.endDate == null) {
			return "";
		} else {
			// Return a string that represents date in yyyy-mm-dd format. Without hours,
			// minutes and seconds.
			return this.endDate.toString().substring(0, 10);
		}

	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Discount [id=" + id + ", title=" + title + ", description=" + description + ", discountPrice="
				+ discountPrice + ", regularPrice=" + regularPrice + ", userId=" + userId + ", category=" + category
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", status=" + status + "]";
	}

}
