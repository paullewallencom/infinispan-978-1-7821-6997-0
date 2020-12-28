package com.packtpub.infinispan.chapter9.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@SuppressWarnings("restriction")
@XmlRootElement(name = "person")
@XmlType(name = "", propOrder = { "name", "age", "maritalStatus", "credit" })
public class Customer implements Serializable {
  
  private static final long serialVersionUID = 1199252371719083359L;
  
  // Setters and toString() are omitted for brevity
  private String name;
  private int age;
  private String maritalStatus;
  private String doc;
  private double credit;
  
  @XmlElement
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  @XmlElement
  public int getAge() {
    return age;
  }
  
  public void setAge(int age) {
    this.age = age;
  }
  
  @XmlAttribute(name = "doc")
  public String getDoc() {
    return doc;
  }
  
  public void setDoc(String doc) {
    this.doc = doc;
  }
  
  @XmlElement
  public String getMaritalStatus() {
    return maritalStatus;
  }
  
  public void setMaritalStatus(String maritalStatus) {
    this.maritalStatus = maritalStatus;
  }
  
  @XmlElement
  public double getCredit() {
    return credit;
  }
  
  public void setCredit(double credit) {
    this.credit = credit;
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + age;
    long temp;
    temp = Double.doubleToLongBits(credit);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    result = prime * result + ((doc == null) ? 0 : doc.hashCode());
    result = prime * result + ((maritalStatus == null) ? 0 : maritalStatus.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
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
    Customer other = (Customer) obj;
    if (age != other.age)
      return false;
    if (Double.doubleToLongBits(credit) != Double.doubleToLongBits(other.credit))
      return false;
    if (doc == null) {
      if (other.doc != null)
        return false;
    } else if (!doc.equals(other.doc))
      return false;
    if (maritalStatus == null) {
      if (other.maritalStatus != null)
        return false;
    } else if (!maritalStatus.equals(other.maritalStatus))
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    return true;
  }
  
  @Override
  public String toString() {
    return "Customer [name=" + name + ", age=" + age + ", maritalStatus=" + maritalStatus + ", doc=" + doc
        + ", credit=" + credit + "]";
  }
  
}
