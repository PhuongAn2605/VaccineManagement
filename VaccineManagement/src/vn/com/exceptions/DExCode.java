package vn.com.exceptions;

import java.text.MessageFormat;

import domainapp.basics.util.InfoCode;

/**
 * @overview Capture domain-specific exception codes.
 *  
 * <p><b>Enum value format</b>: <tt>VALUE("...{0}...{1}...")</tt>
 *  where <t>{0}, {1}, ....</tt> are template variables that will be replaced by run-time values
 *  
 * <p><b>Example</b>:
 * 
 * <br>enum value: INVALID_DOB("Date of birth {0} is not a valid date")
 * 
 * <br>usage: <pre>
 * // an error discovered in some code that validates a dob would throw this exception:
 * 
 *    throw new ConstraintViolationException(INVALID_DOB, dob)
 *  </pre>
 *  
 *  Here, <tt>dob.toString()</tt> is the run-time value that replaces the template variable {0} in 
 *  the enum INVALID_DOB.
 *  
 * @author Duc Minh Le (ducmle)
 *
 */
public enum DExCode implements InfoCode {

  /**
   * 0: date of birth
   */
	INVALID_DOB("Date of birth {0} is not a valid date"),
	INVALID_CAPACITY("Capacity of a storage in square meter is greater than 1000, entered capacity value = {0}"),
	INVALID_QUANTITY_IN_STORAGE("Quantity in unit must be greater than 1000, enntered quantity value= {0}"),
	INVALID_TRIAL_EFFICACY("The trial efficacy {0} in % must be between 0 and 100"),
	INVALID_INFECTED_PEOPLE_NUMBER("The number of infected people {0} must be less than the population in this country"),
	INVALID_REGISTER_DATE("Register date {0} is not valid date"),
	INJECTION_MUST_HAPPEN_AFTER_REGISTRATION("Injection {0} must happen after registration"),
	
	INVALID_MONEY("Money {0} is not valid"),
	INVALID_INPUT_MONEY("Input money {0} is less than the cost"),
	
	INVALID_PHONE_NUMBER("Phone number {0} is invalid"),
	
	INVALID_END_DATE("The end date {0} must be after the start date"),
	STORAGE_RUN_OUT_OF_VACCINE("The storage is run out of vaccine, entered storage {0}"),
	INVALID_DAY_IN_FEBRUARY("The days in February is equal or less than 29, entered day {0}"),
	INVALID_MONTH("31th day is not the day in {0}th month");
	
  
  /**
   * THE FOLLOWING CODE (EXCEPT FOR THE CONSTRUCTOR NAME) MUST BE KEPT AS IS
   */
  private String text;

  /**The {@link MessageFormat} object for formatting {@link #text} 
   * using context-specific data arguments
   */
  private MessageFormat messageFormat;

  private DExCode(String text) {
    this.text = text;
  }

  @Override
  public String getText() {
    return text;
  }
  
  @Override
  public MessageFormat getMessageFormat() {
    if (messageFormat == null) {
      messageFormat = new MessageFormat(text);
    }
    
    return messageFormat;
  }
}
