package model;

import java.lang.reflect.MalformedParametersException;

public class Customer {
	private String id;
	private String firstName;
	private String lastName;
	private String contact;

	public Customer(String id, String name, String contact) {
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
		if (name == null || name.length() < 3)
			throw new MalformedParametersException("Name has to contain a first and last name");
		var result = name.split(" ");
		if (result.length < 2)
			throw new MalformedParametersException("Name must contain both a first and last name");
		lastName = result[result.length - 1];
		var sb=new StringBuilder();
		for(var n=0;n<result.length-1;n++)
			sb.append(result[n]).append(' ');
		firstName=sb.toString().trim();
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
}
