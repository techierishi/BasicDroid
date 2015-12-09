package com.basicdroid.app.libs.forms.validators;


import com.basicdroid.app.libs.forms.Form;

public class RequiredValidator extends BasicValidator {

	@Override
	public String validate(Form form, String thisname) {
		if(form.getValue(thisname)==null)
			return "\n "+thisname+" Can not be Empty!";
		if(form.getValue(thisname).length()==0)
			return "\n "+thisname+" Can not be Empty!";
		return "";
	}

}
