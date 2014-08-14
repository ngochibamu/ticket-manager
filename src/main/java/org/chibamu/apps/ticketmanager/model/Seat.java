package org.chibamu.apps.ticketmanager.model;

import javax.persistence.Entity;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import java.lang.Override;

import org.chibamu.apps.ticketmanager.model.Section;

import javax.validation.constraints.Min;

@Entity
public class Seat implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id = null;
	@Version
	@Column(name = "version")
	private int version = 0;

	@Min(1)
	private int rowNumber;

	@Min(1)
	private int number;

	@ManyToOne
	private Section section;
	
	public Seat(){
		
	}

	public Seat(int rowNumber, int number, Section section) {
		this.rowNumber = rowNumber;
		this.number = number;
		this.section = section;
	}

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
			return id.equals(((Seat) that).id);
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

	public int getRowNumber() {
		return this.rowNumber;
	}

	public void setRowNumber(final int rowNumber) {
		this.rowNumber = rowNumber;
	}

	public int getNumber() {
		return this.number;
	}

	public void setNumber(final int number) {
		this.number = number;
	}

	public Section getSection() {
		return this.section;
	}

	public void setSection(final Section section) {
		this.section = section;
	}

	@Override
	public String toString() {
	return new StringBuilder().append(getSection()).append(" (").append(getRowNumber()).append(", ").append(getNumber()).append(")").toString();
	}
}