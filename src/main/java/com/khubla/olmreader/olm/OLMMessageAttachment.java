package com.khubla.olmreader.olm;

public class OLMMessageAttachment {
   private final String OPFAttachmentContentExtension;
   private final String OPFAttachmentContentFileSize;
   private final String OPFAttachmentContentID;
   private final String OPFAttachmentContentType;
   private final String OPFAttachmentName;
   private final String OPFAttachmentURL;

   public OLMMessageAttachment(String oPFAttachmentContentExtension, String oPFAttachmentContentFileSize, String oPFAttachmentContentID, String oPFAttachmentContentType, String oPFAttachmentName,
         String oPFAttachmentURL) {
      super();
      OPFAttachmentContentExtension = oPFAttachmentContentExtension;
      OPFAttachmentContentFileSize = oPFAttachmentContentFileSize;
      OPFAttachmentContentID = oPFAttachmentContentID;
      OPFAttachmentContentType = oPFAttachmentContentType;
      OPFAttachmentName = oPFAttachmentName;
      OPFAttachmentURL = oPFAttachmentURL;
   }

   public String getOPFAttachmentContentExtension() {
      return OPFAttachmentContentExtension;
   }

   public String getOPFAttachmentContentFileSize() {
      return OPFAttachmentContentFileSize;
   }

   public String getOPFAttachmentContentID() {
      return OPFAttachmentContentID;
   }

   public String getOPFAttachmentContentType() {
      return OPFAttachmentContentType;
   }

   public String getOPFAttachmentName() {
      return OPFAttachmentName;
   }

   public String getOPFAttachmentURL() {
      return OPFAttachmentURL;
   }
}
