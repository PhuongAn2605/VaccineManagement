package vn.com.vaccineman.it3.vaccineman.software;


import java.util.Date;

import domainapp.basics.exceptions.DataSourceException;
import domainapp.basics.exceptions.NotFoundException;
import domainapp.basics.util.Toolkit;
import domainapp.software.SoftwareFactory;
import domainapp.softwareimpl.DomSoftware;
import vn.com.vaccineman.it3.vaccineman.services.enums.PeopleGroup;
import vn.com.vaccineman.it3.vaccineman.services.enums.Gender;
import vn.com.vaccineman.it3.vaccineman.services.enums.HealthStatus;
import vn.com.vaccineman.it3.vaccineman.services.injectionman.injection.model.Injection;
import vn.com.vaccineman.it3.vaccineman.services.injectionman.injection.record.InjectionRecord;
import vn.com.vaccineman.it3.vaccineman.services.injectionman.injection.report.InjectionRecordsByVaccineAndEffectReport;
import vn.com.vaccineman.it3.vaccineman.services.injectionman.payment.model.Payment;
import vn.com.vaccineman.it3.vaccineman.services.injectionman.payment.report.PaymentsByStatusReport;
import vn.com.vaccineman.it3.vaccineman.services.injectionman.person.model.NormalPerson;
import vn.com.vaccineman.it3.vaccineman.services.injectionman.person.model.Person;
import vn.com.vaccineman.it3.vaccineman.services.injectionman.person.model.PriorityPerson;
import vn.com.vaccineman.it3.vaccineman.services.injectionman.person.report.PeopleByNameReport;
import vn.com.vaccineman.it3.vaccineman.services.injectionman.region.InjectRegion;
import vn.com.vaccineman.it3.vaccineman.services.injectionman.registration.model.Registration;
import vn.com.vaccineman.it3.vaccineman.services.injectionman.registration.report.RegistrationsByDateReport;
import vn.com.vaccineman.it3.vaccineman.services.injectionman.staff.address.Address;
import vn.com.vaccineman.it3.vaccineman.services.injectionman.staff.model.Accountant;
import vn.com.vaccineman.it3.vaccineman.services.injectionman.staff.model.Doctor;
import vn.com.vaccineman.it3.vaccineman.services.injectionman.staff.model.Staff;
import vn.com.vaccineman.it3.vaccineman.services.injectionman.staff.model.SupportStaff;
import vn.com.vaccineman.it3.vaccineman.services.injectionman.staff.report.StaffByNameReport;
import vn.com.vaccineman.it3.vaccineman.services.vaccineman.contract.Contract;
import vn.com.vaccineman.it3.vaccineman.services.vaccineman.country.Country;
import vn.com.vaccineman.it3.vaccineman.services.vaccineman.storage.model.Storage;
import vn.com.vaccineman.it3.vaccineman.services.vaccineman.storage.report.RemainVaccineQuantityReportLessThan;
import vn.com.vaccineman.it3.vaccineman.services.vaccineman.supplier.Supplier;
import vn.com.vaccineman.it3.vaccineman.services.vaccineman.trial.model.Trial;
import vn.com.vaccineman.it3.vaccineman.services.vaccineman.trial.report.TrialEfficacyGreaterThan;
import vn.com.vaccineman.it3.vaccineman.services.vaccineman.trial.report.TrialEfficacyLessThan;
import vn.com.vaccineman.it3.vaccineman.services.vaccineman.trial.report.TrialEvaluationAndPeoleGroupReport;
import vn.com.vaccineman.it3.vaccineman.services.vaccineman.vaccine.model.FreeVaccine;
import vn.com.vaccineman.it3.vaccineman.services.vaccineman.vaccine.model.PriceVaccine;
import vn.com.vaccineman.it3.vaccineman.services.vaccineman.vaccine.model.Vaccine;
import vn.com.vaccineman.it3.vaccineman.services.vaccineman.vaccine.report.VaccineByNameReport;
import vn.com.vaccineman.it3.vaccineman.services.vaccineman.vaccineCenter.model.VaccineCenter;
import vn.com.vaccineman.it3.vaccineman.services.vaccineman.vaccineCenter.report.VaccineCenterCapacityGreaterThanReport;



public class VaccineManSoftware {
	// the domain services of software
	  public static final Class[] models = {
		  Country.class,
		  Vaccine.class,
		  FreeVaccine.class,
		  PriceVaccine.class,
		  VaccineCenter.class,
		  Storage.class,
	      Supplier.class,
	      Contract.class,	      
	      Trial.class,
	          
	      Address.class,
		  Staff.class,
		  SupportStaff.class,
	      Doctor.class, 
	      Accountant.class,
	      
	      Person.class,
	      PriorityPerson.class,
	      NormalPerson.class,
	      Registration.class,
	      Injection.class,
	      InjectionRecord.class,
	      InjectRegion.class,
	      Payment.class,
	      
	      RemainVaccineQuantityReportLessThan.class,
	      TrialEvaluationAndPeoleGroupReport.class,
	      TrialEfficacyGreaterThan.class,
	      TrialEfficacyLessThan.class,
	      VaccineByNameReport.class,
	      VaccineCenterCapacityGreaterThanReport.class,

	      PeopleByNameReport.class,
	      StaffByNameReport.class,
	      RegistrationsByDateReport.class,
	      InjectionRecordsByVaccineAndEffectReport.class,
//	      InjectionsByDateReport.class,
	      PaymentsByStatusReport.class,

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
		    
		    sw.addClasses(models);
	    
		    createInjectRegion(sw);
		    createPriorityPerson(sw);
		    createNormalPerson(sw);
		    createDoctor(sw);
		    createAccountant(sw);
		    createSupportStaff(sw);
		    createCountry(sw);
		    createTrial(sw);
		    createPriceVaccine(sw);
		    createFreeVaccine(sw);
		 
		    
		    
		    createAddress(sw);
		    createVaccineCenter(sw);
		    createStorage(sw);
		    createRegistration(sw);
		    createPayment(sw);
		    
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
	  
	  private static void createPriorityPerson(DomSoftware sw) throws NotFoundException, DataSourceException {
		  Date dob = Toolkit.getDateZeroTime(26, 5, 2000);
		  InjectRegion address = sw.retrieveObjectById(InjectRegion.class, 1);
		PriorityPerson pp = new PriorityPerson("Phuong An", dob,"0387781469", HealthStatus.Good, "student");
		sw.addObject(PriorityPerson.class, pp);
	}
	  private static void createNormalPerson(DomSoftware sw) throws NotFoundException, DataSourceException {
		  Date dob = Toolkit.getDateZeroTime(1, 1, 2000);
		  InjectRegion address = sw.retrieveObjectById(InjectRegion.class, 1);
		NormalPerson np = new NormalPerson("Thuong Nguyen", dob,"0387781469", HealthStatus.Good);
		sw.addObject(NormalPerson.class, np);
	}
	  
	  private static void createDoctor(DomSoftware sw) throws NotFoundException, DataSourceException {
		  Date dob = Toolkit.getDateZeroTime(1, 1, 1988);
		  Address address = sw.retrieveObjectById(Address.class, 1);
		Doctor doctor = new Doctor("Mr Duc", dob, address, "0387781469", "FIT");
		sw.addObject(Doctor.class, doctor);
	}
	  
	  private static void createSupportStaff(DomSoftware sw) throws NotFoundException, DataSourceException {
		  Date dob = Toolkit.getDateZeroTime(5, 6, 1990);
		  Address address = sw.retrieveObjectById(Address.class, 1);
		SupportStaff supportStaff = new SupportStaff("Mr B", dob,Gender.Male, address, "0958346374");
		sw.addObject(SupportStaff.class, supportStaff);
	  }
	  
	  private static void createAccountant(DomSoftware sw) throws NotFoundException, DataSourceException {
		  Date dob = Toolkit.getDateZeroTime(1, 1, 1988);
		  Address address = sw.retrieveObjectById(Address.class, 1);
		Accountant accountant = new Accountant("Mr Cong", dob, Gender.Male, address, "0387781469");
		sw.addObject(Accountant.class, accountant);
	}
	  
	  private static void createCountry(DomSoftware sw) throws NotFoundException, DataSourceException {

		Country country = new Country("Vietnam", 920000L);
		sw.addObject(Country.class, country);
	}
	  
	  
	  private static void createPriceVaccine(DomSoftware sw) throws NotFoundException, DataSourceException {
		  Date expiryDate = Toolkit.getDateZeroTime(1, 1, 2022);
		  Country producer = sw.retrieveObjectById(Country.class, 1);
		PriceVaccine priceVaccine = new PriceVaccine("Pfizer-BioNTech", producer, "whole", "AAA", 1000D);
		sw.addObject(PriceVaccine.class, priceVaccine);
	}
	  
	  private static void createVaccineCenter(DomSoftware sw) throws NotFoundException, DataSourceException {
		  Country country = sw.retrieveObjectById(Country.class, 1);
		  Address add = sw.retrieveObjectById(Address.class, 1);
		  VaccineCenter center = new VaccineCenter("Pfizer-BioNTech", 1000.0,  12000D);
		sw.addObject(VaccineCenter.class, center);
	}
	  
	  
	  private static void createStorage(DomSoftware sw) throws NotFoundException, DataSourceException {
		  Vaccine vaccine = sw.retrieveObjectById(PriceVaccine.class, 1);
		  VaccineCenter vc = sw.retrieveObjectById(VaccineCenter.class, 1);
		  Storage storage = new Storage("Pfizer-BioNTech", 1000.0,  vaccine, 1000L, vc);
		sw.addObject(Storage.class, storage);
	}
	  private static void createFreeVaccine(DomSoftware sw) throws NotFoundException, DataSourceException {
		  Country producer = sw.retrieveObjectById(Country.class, 1);
			FreeVaccine freeVaccine = new FreeVaccine("Pfizer-BioNTech", producer, "whole", "AAA", 0D);
		sw.addObject(FreeVaccine.class, freeVaccine);
	}
	  
	  private static void createAddress(DomSoftware sw) throws NotFoundException, DataSourceException {
//		Country nation = sw.retrieveObjectById(Country.class, 1);
		Address address = new Address("Vietnam", "Hai Duong", "Thanh Mien");
		sw.addObject(Address.class, address);
	}
	  private static void createRegistration(DomSoftware sw) throws NotFoundException, DataSourceException {
//			Doctor doctor = sw.retrieveObjectById(Doctor.class, 1);
			PriorityPerson registerPerson = sw.retrieveObjectById(PriorityPerson.class, 1);
			SupportStaff supportStaff = sw.retrieveObjectById(SupportStaff.class, 1);
			Storage storage = sw.retrieveObjectById(Storage.class, 1);
			Date registerDate = Toolkit.getDateZeroTime(1, 1, 2020);
			PriceVaccine vaccineType = sw.retrieveObjectById(PriceVaccine.class, 1);
			
			Registration registration = new Registration(1,5,2021, storage, supportStaff);
			sw.addObject(Registration.class, registration);
		}
	  
	  private static void createPayment(DomSoftware sw) throws NotFoundException, DataSourceException {
			Accountant accountant = sw.retrieveObjectById(Accountant.class, 1);
			Registration registration = sw.retrieveObjectById(Registration.class, 1);
			Date payDate = Toolkit.getDateZeroTime(17, 5, 2020);
			
			Payment payment = new Payment(payDate, accountant, registration);
			sw.addObject(Payment.class, payment);
		}
	  
	  private static void createInjection(DomSoftware sw) throws NotFoundException, DataSourceException {
//		Doctor doctor = sw.retrieveObjectById(Doctor.class, 1);
		Doctor doctor = sw.retrieveObjectById(Doctor.class, sw);
		Date injectDate = Toolkit.getDateZeroTime(1, 4, 2020);
		
		Injection injection = new Injection(injectDate,doctor);
		sw.addObject(Injection.class, injection);
	}
	  
	  public static void createInjectRegion(DomSoftware sw) throws DataSourceException{
		  InjectRegion region1 = new InjectRegion("Hai Duong", 100);
		  sw.addObject(InjectRegion.class, region1);
		  
	  }
	  
	  public static void createTrial(DomSoftware sw) throws DataSourceException{
		  PriceVaccine pv = sw.retrieveObjectById(PriceVaccine.class, 1);
		  Date startDate = Toolkit.getDateZeroTime(1, 4, 2021);
		  Date endDate = Toolkit.getDateZeroTime(1, 6, 2021);
		  Trial trial = new Trial("Frist Stage", pv, PeopleGroup.Y_0_14,startDate, endDate, "sick", 70.0);
		  sw.addObject(Trial.class, trial);
	  }
	}

