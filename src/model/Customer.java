package model;

import java.lang.reflect.MalformedParametersException;

public class Customer {
	private String id;
	private String firstName;
	private String lastName;
	private String contact;

	public Customer(String id, String name, String contact) {
		if(id==null||id.equals("")){
			this.id=id;
			this.firstName=this.lastName="";
			this.contact=id;
			return;
		}
		this.id = id;
		setName(name);
		this.contact = contact;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setName(String name) {
		//if (name == null || name.length() < 3)
		//	throw new MalformedParametersException("Name has to contain a first and last name");
		var result = name.split(" ");
		if(result.length==1){
			lastName=name;
			firstName="";
			return;
		}
		if (result.length < 2)
			throw new MalformedParametersException("Name must contain both a first and last name");
		if (result[0].endsWith(",")) {
			lastName=result[0].substring(0,result[0].length()-1);
			var sb=new StringBuilder();
			for (var n = 1; n < result.length; n++)
				sb.append(result[n]).append(' ');
			firstName = sb.toString().trim();
		} else {
			lastName = result[result.length - 1];
			var sb = new StringBuilder();
			for (var n = 0; n < result.length - 1; n++)
				sb.append(result[n]).append(' ');
			firstName = sb.toString().trim();
		}
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
}
