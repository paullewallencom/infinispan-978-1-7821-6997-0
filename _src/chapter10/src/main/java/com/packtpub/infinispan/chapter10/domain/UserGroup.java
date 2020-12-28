package com.packtpub.infinispan.chapter10.domain;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class UserGroup implements Serializable {
  
  private static final long serialVersionUID = 3458419352909191084L;
  
  @Id
  private long id;
  
  private String groupName;
  
  @ManyToMany(mappedBy = "groups")
  private Collection<User> users;
  
  public long getId() {
    return id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public String getGroupName() {
    return groupName;
  }
  
  public void setGroupName(String name) {
    this.groupName = name;
  }
  
  public Collection<User> getUsers() {
    return users;
  }
  
  public void setUsers(Collection<User> users) {
    this.users = users;
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (int) (id ^ (id >>> 32));
    result = prime * result + ((groupName == null) ? 0 : groupName.hashCode());
    result = prime * result + ((users == null) ? 0 : users.hashCode());
    return result;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    UserGroup other = (UserGroup) obj;
    if (id != other.id)
      return false;
    if (groupName == null) {
      if (other.groupName != null)
        return false;
    } else if (!groupName.equals(other.groupName))
      return false;
    if (users == null) {
      if (other.users != null)
        return false;
    } else if (!users.equals(other.users))
      return false;
    return true;
  }
  
  @Override
  public String toString() {
    return "UserGroup [id=" + id + ", name=" + groupName + ", users=" + users.size() + ", getId()=" + getId()
        + ", getName()=" + getGroupName() + ", getUsers()=" + getUsers() + ", hashCode()=" + hashCode()
        + ", getClass()=" + getClass() + ", toString()=" + super.toString() + "]";
  }
  
}
