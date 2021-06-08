package vn.com.vaccinemane.it1.vaccineman.model.enums;

import domainapp.basics.model.meta.DAttr;
import domainapp.basics.model.meta.DAttr.Type;

public enum HealthStatus {
	Good,
	Normal,
	Bad;
	
	@DAttr(name="name", type=Type.String, id=true, length=10)
	public String getName() {
		return name();
	}
}
