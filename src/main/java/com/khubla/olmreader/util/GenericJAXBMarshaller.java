package com.khubla.olmreader.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

/**
 * @author tome
 */
public class GenericJAXBMarshaller<T> {
   /**
    * schema
    */
   private final Schema schema;
   /**
    * the class
    */
   private final Class<T> persistentClass;

   /**
    * ctor
    */
   public GenericJAXBMarshaller(Class<T> persistentClass) {
      this.persistentClass = persistentClass;
      this.schema = null;
   }

   /**
    * ctor
    */
   public GenericJAXBMarshaller(Class<T> persistentClass, InputStream schemaStream) throws SAXException {
      this.persistentClass = persistentClass;
      final SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      this.schema = schemaFactory.newSchema(new StreamSource(schemaStream));
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
         if (this.schema != null) {
            marshaller.setSchema(this.schema);
         }
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
         if (this.schema != null) {
            unmarshaller.setSchema(this.schema);
         }
         return (T) unmarshaller.unmarshal(inputStream);
      } catch (final Exception e) {
         throw new Exception("Error unmarshalling", e);
      }
   }
}
