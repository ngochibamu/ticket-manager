package org.chibamu.apps.ticketmanager.model;

import javax.persistence.Entity;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import java.lang.Override;

import org.chibamu.apps.ticketmanager.model.Event;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.chibamu.apps.ticketmanager.model.Venue;
import org.chibamu.apps.ticketmanager.model.Performance;

import java.util.Set;
import java.util.HashSet;

import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import org.chibamu.apps.ticketmanager.model.TicketPrice;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "event_id", "venue_id" }))
public class Show implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @ManyToOne
   @NotNull
   private Event event;

   @ManyToOne
   @NotNull(message = "Venue cannot be empty")
   private Venue venue;

   @OneToMany(mappedBy = "show", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
   @OrderBy("date")
   private Set<Performance> performances = new HashSet<Performance>();

   @OneToMany(mappedBy = "show", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
   private Set<TicketPrice> ticketPrices = new HashSet<TicketPrice>();
   
   public Long getId()
   {
      return this.id;
   }

   public void setId(final Long id)
   {
      this.id = id;
   }

   public int getVersion()
   {
      return this.version;
   }

   public void setVersion(final int version)
   {
      this.version = version;
   }

   @Override
   public boolean equals(Object that)
   {
      if (this == that)
      {
         return true;
      }
      if (that == null)
      {
         return false;
      }
      if (getClass() != that.getClass())
      {
         return false;
      }
      if (id != null)
      {
         return id.equals(((Show) that).id);
      }
      return super.equals(that);
   }

   @Override
   public int hashCode()
   {
      if (id != null)
      {
         return id.hashCode();
      }
      return super.hashCode();
   }

   public Event getEvent()
   {
      return this.event;
   }

   public void setEvent(final Event event)
   {
      this.event = event;
   }

   public Venue getVenue()
   {
      return this.venue;
   }

   public void setVenue(final Venue venue)
   {
      this.venue = venue;
   }

   public Set<Performance> getPerformances()
   {
      return this.performances;
   }

   public void setPerformances(final Set<Performance> performances)
   {
      this.performances = performances;
   }

   public Set<TicketPrice> getTicketPrices()
   {
      return this.ticketPrices;
   }

   public void setTicketPrices(final Set<TicketPrice> ticketPrices)
   {
      this.ticketPrices = ticketPrices;
   }
}