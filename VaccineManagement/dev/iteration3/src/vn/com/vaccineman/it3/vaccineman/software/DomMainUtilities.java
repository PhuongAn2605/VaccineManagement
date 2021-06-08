package vn.com.vaccineman.it3.vaccineman.software;

import domainapp.basics.exceptions.DataSourceException;
import domainapp.software.SoftwareFactory;
import domainapp.softwareimpl.DomSoftware;
import vn.com.vaccineman.services.injectionman.injection.model.Injection;
import vn.com.vaccineman.services.injectionman.person.model.Person;
import vn.com.vaccineman.services.injectionman.region.InjectRegion;
import vn.com.vaccineman.services.injectionman.registration.model.Registration;
import vn.com.vaccineman.services.injectionman.staff.model.Doctor;
import vn.com.vaccineman.services.vaccineman.vaccine.model.Vaccine;

public class DomMainUtilities {
  
  public static void main(String[] args) {
    DomSoftware sw = SoftwareFactory.createDefaultDomSoftware();
    	
    // this should be run subsequent times
    sw.init();
    
    try {
      // print materialised domain model
      printMaterialisedDomainModel(sw);
      
      // delete a domain class
//      deleteClass(sw, Person.class);
      
   // delete a domain class
//      deleteClass(sw, Injection.class);
   // delete a domain class
//      deleteClass(sw, Vaccine.class);
      
      // delete the domain model fragment
//      deleteDomainModel(sw, InjectRegion.class);
      
      // delete multiple domain model fragments 
      deleteDomainModel(sw, VaccineManSoftware.models);

//      printMaterialisedDomainModel(sw);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * @effects 
   * 
   * @version 
   * 
   */
  private static void printMaterialisedDomainModel(DomSoftware sw) {
    String modelName = sw.getDomainModelName(Person.class);
    if (modelName != null) {
      sw.printMaterialisedDomainModel(modelName);
    }
  }

  /**
   * @effects 
   * 
   * @version 
   * 
   */
  private static void deleteDomainModel(DomSoftware sw, Class c) {
    String modelName = sw.getDomainModelName(c);
    if (modelName != null) {
      try {
        sw.deleteDomainModel(modelName);
      } catch (DataSourceException e) {
        e.printStackTrace();
      }
    }
  }

  private static void deleteDomainModel(DomSoftware sw, Class... classes) {
    try {
      sw.deleteDomainModel(classes);
    } catch (DataSourceException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * @effects 
   * 
   * @version 
   * @param sw 
   * 
   */
  private static void deleteClass(DomSoftware sw, Class c) throws DataSourceException {
    boolean isReg = sw.isRegistered(c);
    boolean isMat = sw.isMaterialised(c);
    System.out.printf("%s%n  isRegistered: %b%n  isMaterialised: %b%n", 
        c.getSimpleName(), isReg, isMat);
    if (isMat) {
      Class[] toDelete = {c};
      System.out.printf("...unregistering/deleting%n");
      sw.deleteDomainModel(toDelete);
      isReg = sw.isRegistered(c);
      isMat = sw.isMaterialised(c);
      System.out.printf("  isRegistered: %b%n  isMaterialised: %b%n", isReg, isMat);
    }    
  }
}
