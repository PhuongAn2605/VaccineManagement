package vn.com.vaccinemane.it1.vaccineman.model.vaccine;

import java.util.Date;

import domainapp.basics.exceptions.ConstraintViolationException;
import domainapp.basics.model.meta.AttrRef;
import domainapp.basics.model.meta.DAttr;
import domainapp.basics.model.meta.DClass;
import domainapp.basics.model.meta.DOpt;
import domainapp.basics.model.meta.DAttr.Type;

@DClass(schema="vaccineman")
public class PriceVaccine extends Vaccine {
	//attribute
	@DAttr(name="price",type=Type.Integer,length=50,optional=false)
	  private int price;
	//objectFormConstructor
	 @DOpt(type=DOpt.Type.ObjectFormConstructor)
	 @DOpt(type=DOpt.Type.RequiredConstructor)
	  
	  public PriceVaccine(@AttrRef("name")String name,
			  @AttrRef("trialEfficacy")String trialEfficacy,
			  @AttrRef("quantity") int quantity,
			  @AttrRef("expiryDate")Date expiryDate,
			  @AttrRef("price") Integer price) {
		  this(null, name, trialEfficacy ,quantity, expiryDate, price);

}
	  @DOpt(type=DOpt.Type.DataSourceConstructor)
		public PriceVaccine(String id, 
				String name,
				 String trialEfficacy,
				int quantity,
				Date expiryDate, Integer price)
						throws ConstraintViolationException{
		super(id, name, trialEfficacy, quantity, expiryDate);
	       this.price =price;
		
	  }	

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}