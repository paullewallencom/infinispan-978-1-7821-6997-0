package com.packtpub.infinispan.chapter10.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.ogm.util.impl.Log;
import org.hibernate.ogm.util.impl.LoggerFactory;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.DatabaseRetrievalMethod;
import org.hibernate.search.query.ObjectLookupMethod;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.junit.Before;
import org.junit.Test;

import com.packtpub.infinispan.chapter10.domain.Ticket;

public class QueryTicketsTest {
  
  private static final String PERSISTENCE_UNIT_NAME_WITH_JTA = "ogm-jpa-without-jta";
  
  private static final Log logger = LoggerFactory.make();
  EntityManagerFactory factory = null;
  private static Ticket ticket;
  private static String ticketNumber;
  private static Long categoryId;
  private EntityManager em;
  
  @Before
  public void prepare() {
    EntityManager em = getEntityManager();
    Session session = em.unwrap(Session.class);
    Query q = session.createQuery("from Ticket");
    
    ticket = (Ticket) q.list().get(0);
    ticketNumber = ticket.getId();
    session.close();
  }
  
  @Test
  public void queryTicketByIdWithHQL() {
    logger.info("Using Hibernate Search to find a Ticket!");
    
    EntityManager em = getEntityManager();
    em.getTransaction().begin();
    Session session = em.unwrap(Session.class);
    Query q = session.createQuery("from Ticket t where t.id = :ticketNumber");
    q.setString("ticketNumber", ticketNumber);
    Ticket retrievedTicket = (Ticket) q.uniqueResult();
    assertNotNull(retrievedTicket);
    assertEquals(retrievedTicket.getId(), ticketNumber);
    logger.infof("\nFound ticket %s ", retrievedTicket);
    em.getTransaction().commit();
    em.close();
  }
  
  @Test
  public void queryTicketWithQueryLuceneTest() {
    logger.info("Using QueryBuilder and FullTextQuery !!");
    
    EntityManager em = getEntityManager();
    em.getTransaction().begin();
    FullTextEntityManager ftem = Search.getFullTextEntityManager(em);
    
    QueryBuilder b = ftem.getSearchFactory().buildQueryBuilder().forEntity(Ticket.class).get();
    org.apache.lucene.search.Query lq = b.keyword().onField("id").matching(ticketNumber).createQuery();
    // Note: Here we are converting the Lucene Query to JPA Query
    FullTextQuery ftQuery = ftem.createFullTextQuery(lq, Ticket.class);
    
    ftQuery.initializeObjectsWith(ObjectLookupMethod.SKIP, DatabaseRetrievalMethod.FIND_BY_ID);
    
    List<Ticket> resultList = ftQuery.getResultList();
    assertEquals(1, resultList.size());
    em.getTransaction().commit();
    em.close();
  }
  
  public synchronized EntityManager getEntityManager() {
    if (factory == null) {
      factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME_WITH_JTA);
    }
    return factory.createEntityManager();
  }
  
}
