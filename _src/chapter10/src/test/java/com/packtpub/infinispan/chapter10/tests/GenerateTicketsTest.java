package com.packtpub.infinispan.chapter10.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.ogm.util.impl.Log;
import org.hibernate.ogm.util.impl.LoggerFactory;
import org.junit.Test;

import com.packtpub.infinispan.chapter10.domain.*;

public class GenerateTicketsTest {
  
  private static final String PERSISTENCE_UNIT_NAME_WITHOUT_JTA = "ogm-jpa-without-jta";
  protected static List<Ticket> tickets = new ArrayList<Ticket>();
  
  private static final Log logger = LoggerFactory.make();
  EntityManagerFactory factory = null;
  
  @Test
  public void generateTicketDataWithoutJTA() {
    logger.warn("About to generate a couple of tickets");
    EntityManager em = getEntityManager();
    em.getTransaction().begin();
    TicketCategory theatre = new TicketCategory();
    theatre.setDescription("Theatre");
    
    em.persist(theatre);
    
    Section a1 = new Section();
    a1.setName("A1");
    a1.setNumberOfRows(1);
    a1.setRowCapacity(50);
    
    Seat seat101 = new Seat(a1, 1, 10);
    Seat seat102 = new Seat(a1, 1, 11);
    Seat seat103 = new Seat(a1, 1, 12);
    
    Section a2 = new Section();
    a2.setName("A2");
    a2.setNumberOfRows(5);
    a2.setRowCapacity(100);
    
    Seat seat201 = new Seat(a2, 1, 101);
    Seat seat202 = new Seat(a2, 1, 102);
    Seat seat203 = new Seat(a2, 1, 103);
    
    Set<Section> theatreSections = new HashSet<Section>();
    theatreSections.add(a1);
    theatreSections.add(a2);
    
    Ticket ticket101 = new Ticket(seat101, theatre, 2950d);
    Ticket ticket102 = new Ticket(seat102, theatre, 2950d);
    Ticket ticket103 = new Ticket(seat103, theatre, 2950d);
    
    Ticket ticket201 = new Ticket(seat201, theatre, 1100d);
    Ticket ticket202 = new Ticket(seat202, theatre, 1100d);
    Ticket ticket203 = new Ticket(seat203, theatre, 1100d);
    
    Venue opera = new Venue();
    opera.setName("Opera Garnier");
    opera.setCapacity(1979);
    opera.setSections(theatreSections);
    opera.setDescription("The Palais Garnier was built from 1861 to 1875 for the Paris Opera");
    
    em.persist(opera);
    
    a1.setVenue(opera);
    a2.setVenue(opera);
    
    em.persist(a1);
    em.persist(a2);
    
    em.persist(ticket101);
    em.persist(ticket102);
    em.persist(ticket103);
    
    em.persist(ticket201);
    em.persist(ticket202);
    em.persist(ticket203);
    
    tickets.add(ticket101);
    tickets.add(ticket102);
    tickets.add(ticket103);
    tickets.add(ticket201);
    tickets.add(ticket202);
    tickets.add(ticket203);
    
    assertNotNull(ticket101.getId());
    
    em.flush();
    em.getTransaction().commit();
    em.close();
    
    logger.warn("Retrieving a Ticket for test purpose");
    em = getEntityManager();
    em.getTransaction().begin();
    Ticket returnedTicket101 = em.find(Ticket.class, ticket101.getId());
    em.flush();
    assertNotNull(returnedTicket101);
    assertEquals(ticket101.getId(), returnedTicket101.getId());
    em.close();
    em.getTransaction().commit();
  }
  
  public synchronized EntityManager getEntityManager() {
    if (factory == null) {
      factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME_WITHOUT_JTA);
    }
    return factory.createEntityManager();
  }
  
}
