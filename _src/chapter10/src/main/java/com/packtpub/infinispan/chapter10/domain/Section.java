package com.packtpub.infinispan.chapter10.domain;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

/**
 * <p>
 * A section is a specific area within a venue layout. A venue layout may consist of multiple
 * sections.
 * </p>
 * 
 * <p>
 * The name and venue form the natural id of this entity, and therefore must be unique. JPA requires
 * us to use the class level <code>@Table</code> constraint.
 * </p>
 * 
 * @author Shane Bryzak
 * @author Pete Muir
 */
/*
 * We suppress the warning about not specifying a serialVersionUID, as we are still developing this
 * app, and want the JVM to generate the serialVersionUID for us. When we put this app into
 * production, we'll generate and embed the serialVersionUID
 */
@SuppressWarnings("serial")
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "name", "venue_id" }))
@Indexed
/*
 * We indicate that some properties of the class shouldn't be marshalled in JSON format
 */
public class Section implements Serializable {
  
  /* Declaration of fields */
  
  /**
   * The synthetic id of the object.
   */
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Field(name = "id")
  private String id;
  
  /**
   * <p>
   * The short name of the section, may be a code such as A12, G7, etc.
   * </p>
   * 
   * <p>
   * The
   * <code>@NotEmpty<code> Bean Validation constraint means that the section name must be at least 1 character.
   * </p>
   */
  @NotNull
  private String name;
  
  /**
   * <p>
   * The description of the section, such as 'Rear Balcony', etc.
   * </p>
   * 
   * <p>
   * The
   * <code>@NotEmpty<code> Bean Validation constraint means that the section description must be at least 1 character.
   * </p>
   */
  @NotNull
  private String description;
  
  /**
   * <p>
   * The venue to which this section belongs. The
   * <code>@ManyToOne<code> JPA mapping establishes this relationship.
   * </p>
   * 
   * <p>
   * The <code>@NotNull</code> Bean Validation constraint means that the venue must be specified.
   * </p>
   */
  @ManyToOne
  @NotNull
  private Venue venue;
  
  /**
   * The number of rows that make up the section.
   */
  private int numberOfRows;
  
  /**
   * The number of seats in a row.
   */
  private int rowCapacity;
  
  /* Boilerplate getters and setters */
  
  public String getId() {
    return id;
  }
  
  public void setId(String id) {
    this.id = id;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getDescription() {
    return description;
  }
  
  public void setDescription(String description) {
    this.description = description;
  }
  
  public int getNumberOfRows() {
    return numberOfRows;
  }
  
  public void setNumberOfRows(int numberOfRows) {
    this.numberOfRows = numberOfRows;
  }
  
  public int getRowCapacity() {
    return rowCapacity;
  }
  
  public void setRowCapacity(int rowCapacity) {
    this.rowCapacity = rowCapacity;
  }
  
  public int getCapacity() {
    return this.rowCapacity * this.numberOfRows;
  }
  
  public Venue getVenue() {
    return venue;
  }
  
  public void setVenue(Venue venue) {
    this.venue = venue;
  }
  
  /* toString(), equals() and hashCode() for Section, using the natural identity of the object */
  
  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    
    Section section = (Section) o;
    
    if (venue != null ? !venue.equals(section.venue) : section.venue != null)
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
    return name;
  }
  
}
