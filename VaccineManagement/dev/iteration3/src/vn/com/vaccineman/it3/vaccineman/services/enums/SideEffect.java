package vn.com.vaccineman.it3.vaccineman.services.enums;

import domainapp.basics.model.meta.DAttr;
import domainapp.basics.model.meta.DAttr.Type;

public enum SideEffect {
	
	NotCheck,
	Fever,
	Fatigue,
	Headache,
	Fine,
	Others;
	
	@DAttr(name="name", type=Type.String, id=true, length=10)
	private String name;
	
	@DAttr(name="name", type=Type.String, id=true, length=10)
	public String getName() {
		return name();
	}
}
