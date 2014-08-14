package org.chibamu.apps.ticketmanager.model;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import java.lang.Override;
import org.chibamu.apps.ticketmanager.model.Show;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.chibamu.apps.ticketmanager.model.Section;
import org.chibamu.apps.ticketmanager.model.TicketCategory;

@Entity
public class TicketPrice implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id = null;
	@Version
	@Column(name = "version")
	private int version = 0;

	@ManyToOne
	@NotNull
	private Show show;

	@ManyToOne
	@NotNull
	private Section section;

	@ManyToOne
	@NotNull
	private TicketCategory ticketCategory;

	private float price;

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (id != null)
			result += "id: " + id;
		return result;
	}

	@Override
	public boolean equals(Object that) {
		if (this == that) {
			return true;
		}
		if (that == null) {
			return false;
		}
		if (getClass() != that.getClass()) {
			return false;
		}
		if (id != null) {
			return id.equals(((TicketPrice) that).id);
		}
		return super.equals(that);
	}

	@Override
	public int hashCode() {
		if (id != null) {
			return id.hashCode();
		}
		return super.hashCode();
	}

	public Show getShow() {
		return this.show;
	}

	public void setShow(final Show show) {
		this.show = show;
	}

	public Section getSection() {
		return this.section;
	}

	public void setSection(final Section section) {
		this.section = section;
	}

	public TicketCategory getTicketCategory() {
		return this.ticketCategory;
	}

	public void setTicketCategory(final TicketCategory ticketCategory) {
		this.ticketCategory = ticketCategory;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
}