package com.basicdroid.app.libs.forms.validators;


import com.basicdroid.app.libs.forms.Form;

public class PhoneValidator extends BasicValidator {

	@Override
	public String validate(Form form, String thisname) {
		String regexStrforPhn = "^[0-9]*$";
		
		if(form.getValue(thisname).length()>13)
			return "\n Please enter a valid "+thisname+"!";
		if(form.getValue(thisname).length()<10)
			return "\n Please enter a valid "+thisname+"!";
		if(!form.getValue(thisname).matches(regexStrforPhn))
			return "\n Please enter a valid "+thisname+"!";
		
		return "";
	}

}