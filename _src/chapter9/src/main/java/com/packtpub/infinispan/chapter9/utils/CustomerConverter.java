package com.packtpub.infinispan.chapter9.utils;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.packtpub.infinispan.chapter9.domain.Customer;

@SuppressWarnings("restriction")
public class CustomerConverter {
  
  public static final Customer convertXMLtoJava(String xmlData) throws JAXBException {
    JAXBContext context = JAXBContext.newInstance(Customer.class);
    Unmarshaller unmarshaller = context.createUnmarshaller();
    Customer cust = (Customer) unmarshaller.unmarshal(new StringReader(xmlData));
    return cust;
  }
  
  public static final String convertJavaToXML(Customer customer) throws JAXBException {
    JAXBContext context = JAXBContext.newInstance(Customer.class);
    StringWriter writer = new StringWriter();
    Marshaller m = context.createMarshaller();
    m.marshal(customer, writer);
    return writer.getBuffer().toString();
  }
  
}
