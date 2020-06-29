package model;

import java.lang.reflect.MalformedParametersException;

public class Customer {
	private String id;
	private String firstName;
	private String lastName;
	private String contact;

	public Customer(String id, String name, String contact) {
		setId(id);
		if (id == null || id.equals("")) {
			setName("");
			this.contact = id;
			return;
		}
		setName(name);
		setContact(contact);
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

	public void setName(String name) throws MalformedParametersException {
		//if (name == null || name.length() < 3)
		//	throw new MalformedParametersException("Name has to contain a first and last name");

		{
			int ln;
			do
			{
				ln = name.length();
				name = name.replaceAll("(^( *,*)*)|((,* *)*$)(,*$)", "");
			} while (ln != name.length());
		}
		if (!name.contains(" ")) {
			firstName = "";
			lastName = name;
			return;
		}
		var result = name.split(" ");
		if (result[0].trim().endsWith(",")) {
			lastName = result[0].substring(0, result[0].length() - 1);
			var sb = new StringBuilder();
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
		this.contact = contact == null ? "" : contact;
	}
}
