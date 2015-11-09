package in.thelattice.gluconnect.libs.forms.validators;


import in.thelattice.gluconnect.libs.forms.Form;

public class EmailValidator extends BasicValidator {

	@Override
	public String validate(Form form, String thisname) {
		String regexStrforEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
		
		if(!form.getValue(thisname).matches(regexStrforEmail))
			return "\n Please enter a valid email id!";
		return "";
	}
}
