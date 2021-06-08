package vn.com.vaccineman.it2.vaccineman.services.enums;

import domainapp.basics.model.meta.DAttr;
import domainapp.basics.model.meta.DAttr.Type;

public enum Gender {
  Male,
  Female,
  //Others
  ;
  
  @DAttr(name="name", type=Type.String, id=true, length=10)
  public String getName() {
    return name();
  }
}
