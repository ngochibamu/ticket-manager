package org.chibamu.apps.ticketmanager.model;

import javax.persistence.Entity;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import java.lang.Override;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"name","venue_id"}))
public class Section implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id = null;
	@Version
	@Column(name = "version")
	private int version = 0;

	@ManyToOne
	@NotNull
	private Venue venue;

	@Column
	@NotNull
	private String name;

	@Column
	@NotNull
	private String description;

	@Column
	private int numberOfRows;

	@Column
	private int rowCapacity;

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

	public Venue getVenue() {
		return venue;
	}

	public void setVenue(Venue venue) {
		this.venue = venue;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public int getNumberOfRows() {
		return this.numberOfRows;
	}

	public void setNumberOfRows(final int numberOfRows) {
		this.numberOfRows = numberOfRows;
	}

	public int getRowCapacity() {
		return this.rowCapacity;
	}

	public void setRowCapacity(final int rowCapacity) {
		this.rowCapacity = rowCapacity;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Section section = (Section) o;
		if (venue != null ? !venue.equals(section.venue)
				: section.venue != null)
			return false;
		if (name != null ? !name.equals(section.name) : section.name != null)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + (venue != null ? venue.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (name != null && !name.trim().isEmpty())
			result += "name: " + name;
		if (description != null && !description.trim().isEmpty())
			result += ", description: " + description;
		result += ", numberOfRows: " + numberOfRows;
		result += ", rowCapacity: " + rowCapacity;
		return result;
	}
}