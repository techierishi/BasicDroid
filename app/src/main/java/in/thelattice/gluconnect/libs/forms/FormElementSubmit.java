package in.thelattice.gluconnect.libs.forms;

import java.util.ArrayList;

public class FormElementSubmit extends FormElement {

	private ArrayList<SubmitHandler> submitHandlers = new ArrayList<SubmitHandler>();
	
	public FormElementSubmit(int layoutId, Form f) {
		super(layoutId, f);
	}

	public FormElementSubmit setName(String name) {
		super.setName(name);
		return this;
	}

	public void addSubmitHandler(SubmitHandler submitHandler) {
		submitHandlers.add(submitHandler);
	}
	
	public void submit(){
		for(SubmitHandler s:submitHandlers){
			s.submit(super.form);
		}
	}
}
