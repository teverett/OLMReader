package com.khubla.olmreader.olm;

public class OLMMessageAddress {
   private final String OPFContactEmailAddressAddress;
   private final String OPFContactEmailAddressName;
   private final String OPFContactEmailAddressType;

   public OLMMessageAddress(String oPFContactEmailAddressAddress, String oPFContactEmailAddressName, String oPFContactEmailAddressType) {
      super();
      OPFContactEmailAddressAddress = oPFContactEmailAddressAddress;
      OPFContactEmailAddressName = oPFContactEmailAddressName;
      OPFContactEmailAddressType = oPFContactEmailAddressType;
   }

   public String getOPFContactEmailAddressAddress() {
      return OPFContactEmailAddressAddress;
   }

   public String getOPFContactEmailAddressName() {
      return OPFContactEmailAddressName;
   }

   public String getOPFContactEmailAddressType() {
      return OPFContactEmailAddressType;
   }
}
