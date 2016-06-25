package com.khubla.olmreader.olm;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.khubla.olmreader.util.DOMUtils;

public class OLMMessage {
   public static OLMMessage read(InputStream inputStream) {
      try {
         final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         final DocumentBuilder builder = factory.newDocumentBuilder();
         final Document document = builder.parse(inputStream);
         if (null != document) {
            final Element emailsElement = document.getDocumentElement();
            if (emailsElement.getTagName().compareTo("emails") == 0) {
               final Node emailNode = emailsElement.getFirstChild();
               if ((null != emailNode) && (emailNode.getNodeName().compareTo("email") == 0)) {
                  final OLMMessage ret = new OLMMessage();
                  /*
                   * basic message data
                   */
                  ret.OPFMessageCopyModDate = DOMUtils.safeGetElementString(emailNode, "OPFMessageCopyModDate");
                  ret.OPFMessageCopySentTime = DOMUtils.safeGetElementString(emailNode, "OPFMessageCopySentTime");
                  ret.OPFMessageGetCalendarAcceptStatus = DOMUtils.safeGetElementString(emailNode, "OPFMessageGetCalendarAcceptStatus");
                  ret.OPFMessageGetHasRichText = DOMUtils.safeGetElementBoolean(emailNode, "OPFMessageGetHasRichText");
                  ret.OPFMessageGetIsRead = DOMUtils.safeGetElementBoolean(emailNode, "OPFMessageGetIsRead");
                  ret.OPFMessageGetOverrideEncoding = DOMUtils.safeGetElementString(emailNode, "OPFMessageGetOverrideEncoding");
                  ret.OPFMessageGetPriority = DOMUtils.safeGetElementInteger(emailNode, "OPFMessageGetPriority");
                  ret.OPFMessageCopyGetFlagStatus = DOMUtils.safeGetElementInteger(emailNode, "OPFMessageCopyGetFlagStatus");
                  ret.OPFMessageGetWasSent = DOMUtils.safeGetElementBoolean(emailNode, "OPFMessageGetWasSent");
                  ret.OPFMessageIsCalendarMessage = DOMUtils.safeGetElementBoolean(emailNode, "OPFMessageIsCalendarMessage");
                  ret.OPFMessageIsOutgoing = DOMUtils.safeGetElementBoolean(emailNode, "OPFMessageIsOutgoing");
                  ret.OPFMessageIsOutgoingMeetingResponse = DOMUtils.safeGetElementBoolean(emailNode, "OPFMessageIsOutgoingMeetingResponse");
                  ret.OPFMessageCopyMessageID = DOMUtils.safeGetElementString(emailNode, "OPFMessageCopyMessageID");
                  ret.OPFMessageCopySenderAddress = DOMUtils.safeGetElementString(emailNode, "OPFMessageCopySenderAddress");
                  ret.OPFMessageGetHasHTML = DOMUtils.safeGetElementBoolean(emailNode, "OPFMessageGetHasHTML");
                  ret.OPFMessageCopyBody = DOMUtils.safeGetElementString(emailNode, "OPFMessageCopyBody");
                  ret.OPFMessageCopyHTMLBody = DOMUtils.safeGetElementString(emailNode, "OPFMessageCopyHTMLBody");
                  /*
                   * cc
                   */
                  final Node ccNode = DOMUtils.safeGetElement(emailNode, "OPFMessageCopyCCAddresses");
                  if ((null != ccNode) && (ccNode.hasChildNodes())) {
                     ret.OPFMessageCopyCCAddresses = new ArrayList<OLMMessageAddress>();
                     final NodeList fromList = ccNode.getChildNodes();
                     for (int i = 0; i < fromList.getLength(); i++) {
                        final Node n = fromList.item(i);
                        if (n.getNodeName().compareTo("emailAddress") == 0) {
                           final OLMMessageAddress olmMessageAddress = readOLMMessageAddress(n);
                           ret.OPFMessageCopyCCAddresses.add(olmMessageAddress);
                        }
                     }
                  }
                  /*
                   * from
                   */
                  final Node fromNode = DOMUtils.safeGetElement(emailNode, "OPFMessageCopyFromAddresses");
                  if ((null != fromNode) && (fromNode.hasChildNodes())) {
                     ret.OPFMessageCopyFromAddresses = new ArrayList<OLMMessageAddress>();
                     final NodeList fromList = fromNode.getChildNodes();
                     for (int i = 0; i < fromList.getLength(); i++) {
                        final Node n = fromList.item(i);
                        if (n.getNodeName().compareTo("emailAddress") == 0) {
                           final OLMMessageAddress olmMessageAddress = readOLMMessageAddress(n);
                           ret.OPFMessageCopyFromAddresses.add(olmMessageAddress);
                        }
                     }
                  }
                  /*
                   * attachments
                   */
                  final Node attachmentNode = DOMUtils.safeGetElement(emailNode, "OPFMessageCopyAttachmentList");
                  if ((null != attachmentNode) && (attachmentNode.hasChildNodes())) {
                     final NodeList attachmentsList = attachmentNode.getChildNodes();
                     if ((null != attachmentsList) && (attachmentsList.getLength() > 0)) {
                        ret.attachments = new ArrayList<OLMMessageAttachment>();
                        for (int i = 0; i < attachmentsList.getLength(); i++) {
                           final Node n = attachmentsList.item(i);
                           if (n.getNodeName().compareTo("messageAttachment") == 0) {
                              final OLMMessageAttachment olmMessageAttachment = readOLMMessageAttachment(attachmentsList.item(i));
                              ret.attachments.add(olmMessageAttachment);
                           }
                        }
                     }
                  }
                  return ret;
               }
            }
         }
         return null;
      } catch (final Exception e) {
         e.printStackTrace();
         return null;
      }
   }

   /**
    * read an address
    */
   private static OLMMessageAddress readOLMMessageAddress(Node node) {
      final String OPFContactEmailAddressAddress = DOMUtils.safeGetAttributeString(node, "OPFContactEmailAddressAddress");
      final String OPFContactEmailAddressName = DOMUtils.safeGetAttributeString(node, "OPFContactEmailAddressName");
      final Integer OPFContactEmailAddressType = DOMUtils.safeGetAttributeInteger(node, "OPFContactEmailAddressType");
      return new OLMMessageAddress(OPFContactEmailAddressAddress, OPFContactEmailAddressName, OPFContactEmailAddressType);
   }

   /**
    * read an attachment
    */
   private static OLMMessageAttachment readOLMMessageAttachment(Node node) {
      final String OPFAttachmentContentExtension = DOMUtils.safeGetAttributeString(node, "OPFAttachmentContentExtension");
      final Integer OPFAttachmentContentFileSize = DOMUtils.safeGetAttributeInteger(node, "OPFAttachmentContentFileSize");
      final String OPFAttachmentContentID = DOMUtils.safeGetAttributeString(node, "OPFAttachmentContentID");
      final String OPFAttachmentContentType = DOMUtils.safeGetAttributeString(node, "OPFAttachmentContentType");
      final String OPFAttachmentName = DOMUtils.safeGetAttributeString(node, "OPFAttachmentName");
      final String OPFAttachmentURL = DOMUtils.safeGetAttributeString(node, "OPFAttachmentURL");
      return new OLMMessageAttachment(OPFAttachmentContentExtension, OPFAttachmentContentFileSize, OPFAttachmentContentID, OPFAttachmentContentType, OPFAttachmentName, OPFAttachmentURL);
   }

   private String OPFMessageCopyModDate;
   private String OPFMessageCopySentTime;
   private String OPFMessageGetCalendarAcceptStatus;
   private Boolean OPFMessageGetHasRichText;
   private Boolean OPFMessageGetIsRead;
   private String OPFMessageGetOverrideEncoding;
   private Integer OPFMessageGetPriority;
   private Boolean OPFMessageGetWasSent;
   private Boolean OPFMessageIsCalendarMessage;
   private Boolean OPFMessageIsOutgoing;
   private Boolean OPFMessageIsOutgoingMeetingResponse;
   private String OPFMessageCopyMessageID;
   private String OPFMessageCopySenderAddress;
   private String OPFMessageCopyBody;
   private String OPFMessageCopyHTMLBody;
   private Boolean OPFMessageGetHasHTML;
   private Integer OPFMessageCopyGetFlagStatus;
   private List<OLMMessageAddress> OPFMessageCopyFromAddresses;
   private List<OLMMessageAttachment> attachments;
   private List<OLMMessageAddress> OPFMessageCopyCCAddresses;

   public List<OLMMessageAttachment> getAttachments() {
      return attachments;
   }

   public String getOPFMessageCopyBody() {
      return OPFMessageCopyBody;
   }

   public List<OLMMessageAddress> getOPFMessageCopyCCAddresses() {
      return OPFMessageCopyCCAddresses;
   }

   public List<OLMMessageAddress> getOPFMessageCopyFromAddresses() {
      return OPFMessageCopyFromAddresses;
   }

   public Integer getOPFMessageCopyGetFlagStatus() {
      return OPFMessageCopyGetFlagStatus;
   }

   public String getOPFMessageCopyHTMLBody() {
      return OPFMessageCopyHTMLBody;
   }

   public String getOPFMessageCopyMessageID() {
      return OPFMessageCopyMessageID;
   }

   public String getOPFMessageCopyModDate() {
      return OPFMessageCopyModDate;
   }

   public String getOPFMessageCopySenderAddress() {
      return OPFMessageCopySenderAddress;
   }

   public String getOPFMessageCopySentTime() {
      return OPFMessageCopySentTime;
   }

   public String getOPFMessageGetCalendarAcceptStatus() {
      return OPFMessageGetCalendarAcceptStatus;
   }

   public Boolean getOPFMessageGetHasHTML() {
      return OPFMessageGetHasHTML;
   }

   public Boolean getOPFMessageGetHasRichText() {
      return OPFMessageGetHasRichText;
   }

   public Boolean getOPFMessageGetIsRead() {
      return OPFMessageGetIsRead;
   }

   public String getOPFMessageGetOverrideEncoding() {
      return OPFMessageGetOverrideEncoding;
   }

   public Integer getOPFMessageGetPriority() {
      return OPFMessageGetPriority;
   }

   public Boolean getOPFMessageGetWasSent() {
      return OPFMessageGetWasSent;
   }

   public Boolean getOPFMessageIsCalendarMessage() {
      return OPFMessageIsCalendarMessage;
   }

   public Boolean getOPFMessageIsOutgoing() {
      return OPFMessageIsOutgoing;
   }

   public Boolean getOPFMessageIsOutgoingMeetingResponse() {
      return OPFMessageIsOutgoingMeetingResponse;
   }

   public boolean isOPFMessageGetHasHTML() {
      return OPFMessageGetHasHTML;
   }
}
