package vn.com.vaccineman.it2.vaccineman.services.enums;

import domainapp.basics.model.meta.DAttr;
import domainapp.basics.model.meta.DAttr.Type;


public enum PeopleGroup {
  Y_0_14,//chidren
  Y_15_24,//youth
  Y_25_64,//adult
  Y_65_over//senior
  
  //Others
  ;
  
  @DAttr(name="name", type=Type.String, id=true, length=10)
  public String getName() {
    return name();
  }
}
