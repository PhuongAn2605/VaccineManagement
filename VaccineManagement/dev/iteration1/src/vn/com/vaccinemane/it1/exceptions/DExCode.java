package vn.com.vaccinemane.it1.exceptions;

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
  INVALID_DOB("Date of birth {0} is not a valid date")
  ,
	/**
	 *  internalMark
	 */
	INVALID_INTERNAL_MARK("Internal mark{0} is not valid mark"),
	/**
	 * examMark
	 */
	INVALID_EXAM_MARK(" Exam mark {0} is not valid mark"),
	/**
	 * semeter
	 */
	INVALID_SEMESTER("Semester {0} is not valid semester"),
	/**
	 * credit
	 */
	INVALID_CREDIT("Credit {0} is not valid credit"),
	
	INVALID_REGISTER_DATE("Register date {0} is not valid date");
	
  
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
