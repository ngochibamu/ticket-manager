package org.chibamu.apps.ticketmanager.model;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import java.lang.Override;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Venue implements Serializable
{

   @Id
   private @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   Long id = null;
   @Version
   private @Column(name = "version")
   int version = 0;

   @NotNull
   @Column
   private @Size(message = "Must be > 3 and < 50", min = 3, max = 50)
   String name;

   @Column
   private @Size(message = "Must be > 10 and < 100", min = 10, max = 100)
   String description;

   @Column
   private @NotNull(message = "Capacity cannot be empty")
   int capacity;

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
         return id.equals(((Venue) that).id);
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

   public String getName()
   {
      return this.name;
   }

   public void setName(final String name)
   {
      this.name = name;
   }

   public String getDescription()
   {
      return this.description;
   }

   public void setDescription(final String description)
   {
      this.description = description;
   }

   public int getCapacity()
   {
      return this.capacity;
   }

   public void setCapacity(final int capacity)
   {
      this.capacity = capacity;
   }

   public String toString()
   {
      String result = "";
      if (name != null && !name.trim().isEmpty())
         result += name;
      if (description != null && !description.trim().isEmpty())
         result += " " + description;
      result += " " + capacity;
      return result;
   }
}