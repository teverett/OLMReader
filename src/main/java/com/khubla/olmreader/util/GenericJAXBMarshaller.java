package com.khubla.olmreader.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * @author tome
 */
public class GenericJAXBMarshaller<T> {
   /**
    * the class
    */
   private final Class<T> persistentClass;

   /**
    * ctor
    */
   public GenericJAXBMarshaller(Class<T> persistentClass) {
      this.persistentClass = persistentClass;
   }

   /**
    * marshall
    */
   public byte[] marshall(T t) throws Exception {
      try {
         final ByteArrayOutputStream baos = new ByteArrayOutputStream();
         marshall(t, baos);
         return baos.toByteArray();
      } catch (final Exception e) {
         throw new Exception("Error marshalling", e);
      }
   }

   /**
    * marshall
    */
   public void marshall(T t, OutputStream outputStream) throws Exception {
      try {
         final JAXBContext jaxbContext = JAXBContext.newInstance(persistentClass);
         final Marshaller marshaller = jaxbContext.createMarshaller();
         marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
         marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
         marshaller.marshal(t, outputStream);
      } catch (final Exception e) {
         throw new Exception("Error marshalling", e);
      }
   }

   /**
    * ummarshall
    */
   public T unmarshall(byte[] data) throws Exception {
      try {
         return unmarshall(new ByteArrayInputStream(data));
      } catch (final Exception e) {
         throw new Exception("Error unmarshalling", e);
      }
   }

   /**
    * ummarshall
    */
   @SuppressWarnings("unchecked")
   public T unmarshall(InputStream inputStream) throws Exception {
      try {
         final JAXBContext jaxbContext = JAXBContext.newInstance(persistentClass);
         final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
         return (T) unmarshaller.unmarshal(inputStream);
      } catch (final Exception e) {
         throw new Exception("Error unmarshalling", e);
      }
   }
}
