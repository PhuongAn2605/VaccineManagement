package vn.com.vaccineman.services.enums;

import domainapp.basics.model.meta.DAttr;
import domainapp.basics.model.meta.DAttr.Type;

public enum CurrencyUnit {

	VND,
	USD,
	POUND,
	EURO,
	YEN,
	YUAN,
	WON;

	@DAttr(name="name", type=Type.String, id=true, length=10)
	public String getName() {
		return name();
}
}