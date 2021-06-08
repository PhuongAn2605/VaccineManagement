package vn.com.vaccinemane.it1.vaccineman.software;

import domainapp.basics.exceptions.NotPossibleException;
import domainapp.software.SoftwareFactory;
import domainapp.softwareimpl.DomSoftware;
import vn.com.vaccinemane.it1.vaccineman.model.doctor.Doctor;
import vn.com.vaccinemane.it1.vaccineman.model.person.NormalPerson;
import vn.com.vaccinemane.it1.vaccineman.model.person.Person;
import vn.com.vaccinemane.it1.vaccineman.model.person.PriorityPerson;
import vn.com.vaccinemane.it1.vaccineman.model.registration.Registration;
import vn.com.vaccinemane.it1.vaccineman.model.staff.SupportStaff;
import vn.com.vaccinemane.it1.vaccineman.model.storage.Storage;
import vn.com.vaccinemane.it1.vaccineman.model.vaccine.FreeVaccine;
import vn.com.vaccinemane.it1.vaccineman.model.vaccine.PriceVaccine;
import vn.com.vaccinemane.it1.vaccineman.model.vaccine.Vaccine;

/**
 * @overview 
 *  Encapsulate the basic functions for setting up and running a software given its domain model.  
 *  
 * @author dmle
 *
 * @version 
 */
public class VaccineManSoftware{
  
  // the domain model of software
  private static final Class[] model = {
      Vaccine.class,
      FreeVaccine.class,
      PriceVaccine.class,
      Person.class,
      PriorityPerson.class,
      NormalPerson.class,
      Registration.class,
      Storage.class,
      Doctor.class,
      SupportStaff.class
      
  };
  
  /* (non-Javadoc)
   * @see vn.com.courseman.software.Software#getModel()
   */
  /**
   * @effects 
   *  return {@link #model}.
   */
//  @Override
//  protected Class[] getModel() {
//    return model;
//  }

  /**
   * The main method
   * @effects 
   *  run software with a command specified in args[0] and with the model 
   *  specified by {@link #getModel()}. 
   *  
   *  <br>Throws NotPossibleException if failed for some reasons.
   */
  public static void main(String[] args){
	   
	    
	    // 2. create UI software
	    DomSoftware sw = SoftwareFactory.createUIDomSoftware();
	    
	    // 3. run
	    // create in memory configuration
	    System.setProperty("domainapp.setup.SerialiseConfiguration", "false");
	    
	    // 3. run it
	    try {
//	    	String compulsoryModule = sw.getDomainModelName(CompulsoryModule.class);
//	      sw.deleteDomainModel(compulsoryModule);
	      sw.run(model);
	    } catch (Exception e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	    }   
	  }
}
