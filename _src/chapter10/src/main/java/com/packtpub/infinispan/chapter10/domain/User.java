package com.packtpub.infinispan.chapter10.domain;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class User implements Serializable {
  /**
	 * 
	 */
  private static final long serialVersionUID = 1896815338225041104L;
  
  @Id
  // @GeneratedValue(strategy = IDENTITY)
  private long id;
  
  private String name;
  
  @ManyToMany
  private Collection<UserGroup> groups;
  
  public long getId() {
    return id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public Collection<UserGroup> getGroups() {
    return groups;
  }
  
  public void setGroups(Collection<UserGroup> groups) {
    this.groups = groups;
  }
  
  @Override
  public String toString() {
    return "User [id=" + id + ", name=" + name + ", groups=" + groups.size() + "]";
  }
  
}
