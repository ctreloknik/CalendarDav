package ru.nik.api;

import javax.ejb.Stateless;
import javax.ws.rs.Path;

@Path("/json")
@Stateless
public class StudentResource{
	/**
	 * Pass concatenated names as a single string
	 * 
	 * @param namesString
	 * @return
	 */
/*	@PUT
	@Path("studentsByNameJson")
	@Produces("application/json")
	public Response getStudentsByNamesJson(String namesJson) {
		StudentWrapper wrapper = new StudentWrapper();

		List<String> nameList = converter.fromJson(namesJson);
		List<Student> newList = new ArrayList<Student>();

		for (Student student : students) {
			if (nameList.contains(student.getName())) {
				newList.add(student);
			}
		}
		wrapper.setList(newList);
		return Response.status(200).entity(wrapper).build();
	}*/

}
