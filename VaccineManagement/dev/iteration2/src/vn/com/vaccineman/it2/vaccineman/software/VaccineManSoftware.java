package vn.com.vaccineman.it2.vaccineman.software;


import java.util.Date;

import domainapp.basics.exceptions.DataSourceException;
import domainapp.basics.exceptions.NotFoundException;
import domainapp.basics.util.Toolkit;
import domainapp.software.SoftwareFactory;
import domainapp.softwareimpl.DomSoftware;
import vn.com.vaccineman.it2.vaccineman.services.injectionman.injection.model.Injection;
import vn.com.vaccineman.it2.vaccineman.services.injectionman.person.model.NormalPerson;
import vn.com.vaccineman.it2.vaccineman.services.injectionman.person.model.Person;
import vn.com.vaccineman.it2.vaccineman.services.injectionman.person.model.PriorityPerson;
import vn.com.vaccineman.it2.vaccineman.services.injectionman.region.InjectRegion;
import vn.com.vaccineman.it2.vaccineman.services.injectionman.registration.model.Registration;
import vn.com.vaccineman.it2.vaccineman.services.injectionman.staff.address.Address;
import vn.com.vaccineman.it2.vaccineman.services.injectionman.staff.model.Doctor;
import vn.com.vaccineman.it2.vaccineman.services.injectionman.staff.model.Staff;
import vn.com.vaccineman.it2.vaccineman.services.injectionman.staff.model.SupportStaff;
import vn.com.vaccineman.it2.vaccineman.services.vaccineman.country.Country;
import vn.com.vaccineman.it2.vaccineman.services.vaccineman.storage.model.Storage;
import vn.com.vaccineman.it2.vaccineman.services.vaccineman.vaccine.model.FreeVaccine;
import vn.com.vaccineman.it2.vaccineman.services.vaccineman.vaccine.model.PriceVaccine;
import vn.com.vaccineman.it2.vaccineman.services.vaccineman.vaccine.model.Vaccine;
import vn.com.vaccineman.it2.vaccineman.services.vaccineman.vaccineCenter.model.VaccineCenter;


public class VaccineManSoftware {
	// the domain services of software
	  public static final Class[] models = {
		  Country.class,
		  Vaccine.class,
		  FreeVaccine.class,
		  PriceVaccine.class,
		  VaccineCenter.class,
		  Storage.class,
//	      Supplier.class,
//	      Contract.class,	      
//	      Trial.class,
	      
	      
	      Address.class,
		  Staff.class,
		  SupportStaff.class,
	      Doctor.class, 
//	      Accountant.class,
	      
	      Person.class,
	      PriorityPerson.class,
	      NormalPerson.class,
	      Registration.class,
	      Injection.class,
	      InjectRegion.class,
//	      Payment.class,
	      
//	      RemainVaccineQuantityReportEqualLessThan.class,
//	      TrialByVaccineEvaluationAndPeoleGroupReport.class,
//	      TrialEfficacyEqualGreaterThan.class,
//	      TrialEfficacyEqualLessThan.class,
//	      VaccineByNameReport.class,
//	      VaccineCenterCapacityEqualGreaterThanReport.class,
//
//	      PeopleByNameReport.class,
//	      StaffByNameReport.class,
//	      RegistrationsByDateReport.class,
//	      InjectionRecordsByVaccineAndEffectReport.class,
////	      InjectionsByDateReport.class,
//	      PaymentsByStatusReport.class,

	  };
	  
	  /* (non-Javadoc)
	   * @see vn.com.courseman.software.Software#getModel()
	   */
	  /**
	   * @effects 
	   *  return {@link #model}.
	   */
	  protected Class[] getModel() {
	    return models;
	  }

	  /**
	   * The main method
	 * @throws DataSourceException 
	   * @effects 
	   *  run software with a command specified in args[0] and with the model 
	   *  specified by {@link #getModel()}. 
	   *  
	   *  <br>Throws NotPossibleException if failed for some reasons.
	   */
	//  public static void main(String[] args) throws NotPossibleException {
//	    new CourseManSoftware().exec(args);
	//  }
	  public static void main(String[] args) throws DataSourceException{
		   
		    
		    // 1. create UI software
		    DomSoftware sw;
		    sw = SoftwareFactory.createUIDomSoftware();
		    sw.init();
		    
		    deleteDomainModel(sw);
		    
//		    sw.addClasses(models);
//	    
//		    createInjectRegion(sw);
//		    createPriorityPerson(sw);
//		    createNormalPerson(sw);
//		    createDoctor(sw);
//		    createAccountant(sw);
//		    createSupportStaff(sw);
//		    createCountry(sw);
//		    createTrial(sw);
//		    createPriceVaccine(sw);
//		    createFreeVaccine(sw);
//		 
//		    
//		    
//		    createAddress(sw);
//		    createVaccineCenter(sw);
//		    createStorage(sw);
//		    createRegistration(sw);
//		    createPayment(sw);
//		    
//		    createInjection(sw);
////		    
		    // 3. run
		    // create in memory configuration
		    System.setProperty("domainapp.setup.SerialiseConfiguration", "false");
		    
		    // 3. run it
		    try {
//		    	String sw1 = sw.getDomainModelName(Person.class);
//		      sw.deleteDomainModel(sw1);
		      sw.run(models);
		    } catch (Exception e) {
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		    }   
		  }
	  
	  private static void deleteDomainModel(DomSoftware sw) {
	        try {
	            // delete the domain model
	            for (Class model : models) {
	                String modelName = sw.getDomainModelName(model);
	                if (modelName != null)
	                    sw.deleteDomainModel(modelName);
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
//	
	}

