package com.khubla.olmreader.olm;

public class OLMMessageAddress {
   private final String OPFContactEmailAddressAddress;
   private final String OPFContactEmailAddressName;
   private final Integer OPFContactEmailAddressType;

   public OLMMessageAddress(String oPFContactEmailAddressAddress, String oPFContactEmailAddressName, Integer oPFContactEmailAddressType) {
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

   public Integer getOPFContactEmailAddressType() {
      return OPFContactEmailAddressType;
   }
}
