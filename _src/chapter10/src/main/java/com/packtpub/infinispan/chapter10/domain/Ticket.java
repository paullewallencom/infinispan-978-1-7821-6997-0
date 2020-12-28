package com.packtpub.infinispan.chapter10.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

//import javax.validation.constraints.NotNull;

/**
 * <p>
 * A ticket represents a seat sold for a particular price.
 * </p>
 * 
 * @author Shane Bryzak
 * @author Marius Bogoevici
 * @author Pete Muir
 */
/*
 * We suppress the warning about not specifying a serialVersionUID, as we are still developing this
 * app, and want the JVM to generate the serialVersionUID for us. When we put this app into
 * production, we'll generate and embed the serialVersionUID
 */
@SuppressWarnings("serial")
@Entity
@Indexed
public class Ticket implements Serializable {
  
  /* Declaration of fields */
  
  /**
   * The synthetic id of the object.
   *
   */
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Field(name = "id")
  private String id;
  
  /**
   * <p>
   * The seat for which this ticket has been sold.
   * </p>
   * 
   * <p>
   * The seat must be specifed, and the Bean Validation constraint <code>@NotNull</code> ensures
   * this.
   * </p>
   */
  @NotNull
  private Seat seat;
  
  /**
   * <p>
   * The ticket price category for which this ticket has been sold.
   * </p>
   * 
   * <p>
   * The ticket price category must be specifed, and the Bean Validation constraint
   * <code>@NotNull</code> ensures this.
   * </p>
   */
  @ManyToOne
  @NotNull
  @IndexedEmbedded
  private TicketCategory ticketCategory;
  
  /**
   * The price which was charged for the ticket.
   */
  private Double price;
  
  /** No-arg constructor for persistence */
  public Ticket() {
    
  }
  
  public Ticket(Seat seat, TicketCategory ticketCategory, Double price) {
    this.seat = seat;
    this.ticketCategory = ticketCategory;
    this.price = price;
  }
  
  /* Boilerplate getters and setters */
  
  public String getId() {
    return id;
  }
  
  public TicketCategory getTicketCategory() {
    return ticketCategory;
  }
  
  public Double getPrice() {
    return price;
  }
  
  public Seat getSeat() {
    return seat;
  }
  
  @Override
  public String toString() {
    return new StringBuilder().append(getSeat()).append(" @ ").append(getPrice()).append(" (")
        .append(getTicketCategory()).append(")").toString();
  }
}
