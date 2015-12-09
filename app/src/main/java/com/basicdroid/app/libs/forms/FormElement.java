package com.basicdroid.app.libs.forms;

import java.util.ArrayList;

import com.basicdroid.app.libs.forms.validators.BasicValidator;


public class FormElement {

	private Integer id = null;
	protected String name = null;

	Form form;
	private ArrayList<BasicValidator> validators = new ArrayList<BasicValidator>();
	
	public FormElement(int layoutId, Form f) {
		setId(layoutId);
		this.form = f;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public FormElement setName(String name) {
		this.name = name;
		return this;
	}
	
	public FormElement addValidator(BasicValidator validator) {
		validators.add(validator);
		return this;
	}
	public String validate(){
		String returnString="";
		for(BasicValidator v:validators){
			returnString+=v.validate(form, name);
		}
		return returnString;
	}


}
