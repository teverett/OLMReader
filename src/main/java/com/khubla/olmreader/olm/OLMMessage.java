package com.khubla.olmreader.olm;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class OLMMessage {
   public static OLMMessage read(InputStream inputStream) {
      try {
         final OLMMessage ret = new OLMMessage();
         final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         final DocumentBuilder builder = factory.newDocumentBuilder();
         final Document document = builder.parse(inputStream);
         if (null != document) {
            /*
             * basic message data
             */
            ret.emailAddress = DOMUtils.safeGetElement(document, "emailAddress");
            ret.OPFMessageCopySenderAddress = DOMUtils.safeGetElement(document, "OPFMessageCopySenderAddress");
            final String isHTML = DOMUtils.safeGetElement(document, "OPFMessageGetHasHTML");
            if ((null != isHTML) && (isHTML.compareTo("1") == 0)) {
               ret.OPFMessageGetHasHTML = true;
            } else {
               ret.OPFMessageGetHasHTML = false;
            }
            ret.OPFMessageCopyBody = DOMUtils.safeGetElement(document, "OPFMessageCopyBody");
            ret.OPFMessageCopyHTMLBody = DOMUtils.safeGetElement(document, "OPFMessageCopyHTMLBody");
            /*
             * attachments
             */
            final NodeList attachmentsList = document.getElementsByTagName("messageAttachment");
            if ((null != attachmentsList) && (attachmentsList.getLength() > 0)) {
               ret.attachments = new ArrayList<OLMMessageAttachment>();
               for (int i = 0; i < attachmentsList.getLength(); i++) {
                  final OLMMessageAttachment olmMessageAttachment = readAttachment(attachmentsList.item(i));
                  ret.attachments.add(olmMessageAttachment);
               }
            }
         }
         return ret;
      } catch (final Exception e) {
         e.printStackTrace();
         return null;
      }
   }

   /**
    * read an attachment
    */
   private static OLMMessageAttachment readAttachment(Node node) {
      final String OPFAttachmentContentExtension = DOMUtils.safeGetAttribute(node, "OPFAttachmentContentExtension");
      final String OPFAttachmentContentFileSize = DOMUtils.safeGetAttribute(node, "OPFAttachmentContentFileSize");
      final String OPFAttachmentContentID = DOMUtils.safeGetAttribute(node, "OPFAttachmentContentID");
      final String OPFAttachmentContentType = DOMUtils.safeGetAttribute(node, "OPFAttachmentContentType");
      final String OPFAttachmentName = DOMUtils.safeGetAttribute(node, "OPFAttachmentName");
      final String OPFAttachmentURL = DOMUtils.safeGetAttribute(node, "OPFAttachmentURL");
      return new OLMMessageAttachment(OPFAttachmentContentExtension, OPFAttachmentContentFileSize, OPFAttachmentContentID, OPFAttachmentContentType, OPFAttachmentName, OPFAttachmentURL);
   }

   private String emailAddress;
   private String OPFMessageCopySenderAddress;
   private String OPFMessageCopyBody;
   private String OPFMessageCopyHTMLBody;
   private boolean OPFMessageGetHasHTML;
   private List<OLMMessageAttachment> attachments;

   public List<OLMMessageAttachment> getAttachments() {
      return attachments;
   }

   public String getEmailAddress() {
      return emailAddress;
   }

   public String getOPFMessageCopyBody() {
      return OPFMessageCopyBody;
   }

   public String getOPFMessageCopyHTMLBody() {
      return OPFMessageCopyHTMLBody;
   }

   public String getOPFMessageCopySenderAddress() {
      return OPFMessageCopySenderAddress;
   }

   public boolean isOPFMessageGetHasHTML() {
      return OPFMessageGetHasHTML;
   }
}
