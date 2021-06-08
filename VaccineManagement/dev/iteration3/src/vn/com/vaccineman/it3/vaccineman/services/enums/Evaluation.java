package vn.com.vaccineman.it3.vaccineman.services.enums;
import domainapp.basics.model.meta.DAttr;
import domainapp.basics.model.meta.DAttr.Type;

public enum Evaluation {
	Bad,
	Medium,
	Good,
	Excellent;

	@DAttr(name="name", type=Type.String, id=true, length=10)
	public String getName() {
		return name();

}
}
