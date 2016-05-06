package ru.nik.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.parameter.Value;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.UidGenerator;
import ru.nik.dto.UserCalendarDTO;
import ru.nik.dto.UserCalendarEventsDTO;
import ru.nik.dto.UsersDTO;
import ru.nik.services.servicesImpl.UserCalendarEventsServiceBean;
import ru.nik.services.servicesImpl.UserCalendarServiceBean;
import ru.nik.services.servicesImpl.UserServiceBean;

@Path("/ics")
@Stateless
public class ICSApi
{
    @EJB
    UserServiceBean userLocalServiceBean;
    
    @EJB
    UserCalendarServiceBean userCalendarServiceBean;
    
    @EJB
    UserCalendarEventsServiceBean eventsBean;

    @GET
    @Path("/calendar/{url}")
    @Produces("text/calendar")
    public Response getStudentOverAgeList(@PathParam("url") String url) throws IOException, ValidationException, ParserException
    {
        //UsersDTO u = userLocalServiceBean.find(1L);
        //UserCalendarDTO calendarDTO = userCalendarServiceBean.getCalendarByURL(url);
        List<UserCalendarEventsDTO> events = eventsBean.getEventsByCalendarURL(url);
        
        String calFile = "mycalendar.ics";
        Calendar calendar = new Calendar();
        calendar.getProperties().add(new ProdId("CalendarDav"));
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getProperties().add(CalScale.GREGORIAN);

        java.util.Calendar cal = java.util.Calendar.getInstance();
        
        VEvent vevent;
        for (UserCalendarEventsDTO ev : events)
        {
            DateTime start = new DateTime(ev.getStartDatetime());
            DateTime end = new DateTime(ev.getEndDatetime());
            vevent = new VEvent(start, end, ev.getName());
            Description desc = new Description(ev.getDescription());
            UidGenerator uidGenerator = new UidGenerator("1");
            vevent.getProperties().add(uidGenerator.generateUid());
            vevent.getProperties().add(desc);

            calendar.getComponents().add(vevent);
        }
        // initialise as an all-day event..
        //vevent.getProperties().getProperty(Property.DTSTART).getParameters().add(Value.DATE);
        
        
        
        //Saving an iCalendar file
        FileOutputStream fout = new FileOutputStream(calFile);

        CalendarOutputter outputter = new CalendarOutputter();
        outputter.setValidating(false);
        outputter.output(calendar, fout);
        
        
        //Now Parsing an iCalendar file
        FileInputStream fin = new FileInputStream(calFile);

        CalendarBuilder builder = new CalendarBuilder();

        calendar = builder.build(fin);
        
        //Iterating over a Calendar
        for (Iterator i = calendar.getComponents().iterator(); i.hasNext();) {
            Component component = (Component) i.next();
            System.out.println("Component [" + component.getName() + "]");

            for (Iterator j = component.getProperties().iterator(); j.hasNext();) {
                Property property = (Property) j.next();
                System.out.println("Property [" + property.getName() + ", " + property.getValue() + "]");
            }
        }
        
        File file = new File(calFile);
        ResponseBuilder response = Response.ok((Object) file);
        // подумать позднее над названием файла
        response.header("Content-Disposition", "attachment; filename='mycalendar.ics'");
        return response.build();
    }

    List<Student> students = new ArrayList<Student>();

    // StringListConverter converter = new StringListConverter();

    public void StudentResource()
    {
        // Populate test data. Later each WS method will retrieve data from DB.
        students.add(new Student(12344, "Mike", 17));
        students.add(new Student(12345, "Jane", 19));
        students.add(new Student(12346, "Bob", 19));
        students.add(new Student(12347, "Susan", 22));
        students.add(new Student(12348, "Daniel", 25));
        students.add(new Student(12349, "John", 26));
        students.add(new Student(12350, "Debbie", 28));
    }

    @GET
    @Path("students")
    @Produces("application/json")
    public Response getStudentList()
    {
        StudentWrapper wrapper = new StudentWrapper();

        wrapper.setList(students);

        return Response.status(200).entity(wrapper).build();
    }

    /**
     * Pass concatenated names as a single string
     * 
     * @param namesString
     * @return
     */
    @GET
    @Path("studentsByName")
    @Produces("application/json")
    public Response getStudentsByNames(@QueryParam("names") String namesString)
    {
        StudentWrapper wrapper = new StudentWrapper();

        List<String> nameList = Arrays.asList(namesString.split(","));
        List<Student> newList = new ArrayList<Student>();

        for (Student student : students)
        {
            if (nameList.contains(student.getName()))
            {
                newList.add(student);
            }
        }
        wrapper.setList(newList);
        return Response.status(200).entity(wrapper).build();
    }

    /**
     * Pass concatenated names as a single string
     * 
     * @param namesString
     * @return
     */
    /*
     * @PUT
     * @Path("studentsByNameJson")
     * @Produces("application/json") public Response
     * getStudentsByNamesJson(String namesJson) { StudentWrapper wrapper = new
     * StudentWrapper(); List<String> nameList = converter.fromJson(namesJson);
     * List<Student> newList = new ArrayList<Student>(); for (Student student :
     * students) { if (nameList.contains(student.getName())) {
     * newList.add(student); } } wrapper.setList(newList); return
     * Response.status(200).entity(wrapper).build(); }
     */

}
