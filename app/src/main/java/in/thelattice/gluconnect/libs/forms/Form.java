package in.thelattice.gluconnect.libs.forms;

import java.util.ArrayList;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Form {
	
	private ArrayList<FormElement> elements = new ArrayList<FormElement>();
	private ArrayList<FormElement> submit = new ArrayList<FormElement>();
	private String errors = null;
	private Activity activity=null;

	public Form(Activity a) {
		activity = a;
	}

	public FormElement addFormElement(int layoutId) {
		FormElement element = new FormElement(layoutId, this);
		elements.add(element);
		return element;
	}

	public FormElementSubmit addSubmit(int layoutId, Activity a) {
		Log.d("Call", "Onsubmit called");
		final FormElementSubmit element = new FormElementSubmit(layoutId, this);
		submit.add(element);
		a.findViewById(layoutId).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("Call", "OnClick called");
				if(isValid()){
					element.submit();
				}
				else{
					String errors =  getErrors();
					clearErrors();
					Toast.makeText(activity, "Form errors: "+errors, Toast.LENGTH_LONG).show();
				};
			}
        });
		return element;
	}
		
	public String getValue(String string) {
		for(FormElement e:elements){
			if(e.getName().equals(string)){
				View v = activity.findViewById(e.getId());
				if(v instanceof TextView){
					TextView v1 = (TextView)v;
					return v1.getText().toString();
				}
				Log.e("Form:getValue", "No method to get value is defined for this type of view.");
				// TODO Do for other form elements
			}
		}
		return "";
	}

	public boolean isValid() {
		String returnValue = "";
		for(FormElement e:elements){
			returnValue+=e.validate();
		}
		if(returnValue.length()>0){
			setErrors(returnValue);
			return false;
		}
		return true;
	}

	public String getErrors() {
		return errors;
	}
	
	public void setErrors(String errors) {
		this.errors = errors;
	}

	public void clearErrors() {
		errors = null;
	}

}
