package vn.com.vaccineman.it2.vaccineman.services.injectionman.staff.address;

import domainapp.basics.exceptions.ConstraintViolationException;
import domainapp.basics.model.meta.AttrRef;
import domainapp.basics.model.meta.DAssoc;
import domainapp.basics.model.meta.DAttr;
import domainapp.basics.model.meta.DClass;
import domainapp.basics.model.meta.DOpt;
import domainapp.basics.util.Tuple;



@DClass(schema="vaccineman")
public class Address {

    public static final String A_name = "name";

    @DAttr(name="id",id=true,auto=true,length=3,mutable=false,optional=false,type= DAttr.Type.Integer)
    private int id;
    private static int idCounter;
    
    @DAttr(name="nation",type= DAttr.Type.String,length=20,optional=false)
    private String nation;

    @DAttr(name="province",type= DAttr.Type.String,length=20,optional=false, cid = true)
    private String province;

    @DAttr(name="district",type= DAttr.Type.String,length=20,optional=false)
    private String district;


//    @DAttr(name="person",type= DAttr.Type.Domain,serialisable=false)
//    @DAssoc(ascName="person-has-address",role="address",
//            ascType= DAssoc.AssocType.One2One, endType= DAssoc.AssocEndType.One,
//            associate=@DAssoc.Associate(type= Person.class,cardMin=1,cardMax=1,determinant=true))
//    private Person person;

    // from object form: Student is not included
    @DOpt(type=DOpt.Type.ObjectFormConstructor)
    @DOpt(type=DOpt.Type.RequiredConstructor)
    public Address(@AttrRef("nation")String nation,
                   @AttrRef("province") String province,
                   @AttrRef("district") String district) {
        this(null, nation, province, district);
    }
    

    // from data source
    @DOpt(type=DOpt.Type.DataSourceConstructor)
    public Address(@AttrRef("id") Integer id,
            @AttrRef("nation")String nation,
            @AttrRef("province") String province,
            @AttrRef("district") String district) {
//        this(id, country, province, district);
    	this.id = nextId(id);
    	this.nation = nation;
    	this.province = province;
     	this.district = district;
    }

    // based constructor (used by others)
//    public Address(@AttrRef("id") Integer id,
//                    @AttrRef("country") String country,
//                    @AttrRef("province") String province,
//                    @AttrRef("district") String district
//                    ) {
//        this.id = nextId(id);
//        this.country = country;
//        this.province = province;
//        this.district = district;
//      
//    }

    private static int nextId(Integer currID) {
        if (currID == null) {
            idCounter++;
            return idCounter;
        } else {
            int num = currID.intValue();
            if (num > idCounter)
                idCounter = num;

            return currID;
        }
    }

    /**
     * @requires
     *  minVal != null /\ maxVal != null
     * @effects
     *  update the auto-generated value of attribute <tt>attrib</tt>, specified for <tt>derivingValue</tt>, using <tt>minVal, maxVal</tt>
     */
    @DOpt(type=DOpt.Type.AutoAttributeValueSynchroniser)
    public static void updateAutoGeneratedValue(
            DAttr attrib,
            Tuple derivingValue,
            Object minVal,
            Object maxVal) throws ConstraintViolationException {

        if (minVal != null && maxVal != null) {
            //TODO: update this for the correct attribute if there are more than one auto attributes of this class
            int maxIdVal = (Integer) maxVal;
            if (maxIdVal > idCounter)
                idCounter = maxIdVal;
        }
    }

    public int getId() {
        return id;
    }



    public String getNation() {
		return nation;
	}


	public void setNation(String nation) {
		this.nation = nation;
	}


	public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

}

