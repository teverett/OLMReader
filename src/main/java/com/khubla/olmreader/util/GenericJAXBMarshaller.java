package com.khubla.olmreader.util;

import java.io.*;

import javax.xml.*;
import javax.xml.bind.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.validation.*;

import org.xml.sax.*;

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
	public GenericJAXBMarshaller(Class<T> persistentClass, Source[] schemas) throws SAXException {
		this.persistentClass = persistentClass;
		final SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		if (null != schemas) {
			this.schema = schemaFactory.newSchema(schemas);
		} else {
			this.schema = null;
		}
	}

	/**
	 * ctor
	 */
	public GenericJAXBMarshaller(Class<T> persistentClass, String[] schemafiles) throws SAXException {
		this.persistentClass = persistentClass;
		final SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		if (null != schemafiles) {
			final Source[] schemas = new Source[schemafiles.length];
			for (int i = 0; i < schemafiles.length; i++) {
				final InputStream inputStream = GenericJAXBMarshaller.class.getResourceAsStream(schemafiles[i]);
				schemas[i] = new StreamSource(inputStream);
			}
			this.schema = schemaFactory.newSchema(schemas);
		} else {
			this.schema = null;
		}
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
