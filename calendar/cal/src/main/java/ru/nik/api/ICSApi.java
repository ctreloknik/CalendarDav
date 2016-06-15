package ru.nik.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.parameter.Value;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.Organizer;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.UidGenerator;
import ru.nik.dto.EventMembersDTO;
import ru.nik.dto.UserCalendarEventsDTO;
import ru.nik.services.EmailSenderUtil;
import ru.nik.services.servicesImpl.EventmembersServiceBean;
import ru.nik.services.servicesImpl.UserCalendarEventsServiceBean;
import ru.nik.services.servicesImpl.UserCalendarServiceBean;
import ru.nik.services.servicesImpl.UserServiceBean;

@Path("/ics")
@Stateless
public class ICSApi
{
    @EJB
    private UserServiceBean userLocalServiceBean;
    
    @EJB
    private UserCalendarServiceBean userCalendarServiceBean;
    
    @EJB
    private UserCalendarEventsServiceBean eventsBean;
    
    @EJB
    private EventmembersServiceBean membersService;
    
    EmailSenderUtil emailSenderUtil = new EmailSenderUtil();

    @GET
    @Path("/calendar/{url}")
    @Produces("text/calendar")
    public Response getStudentOverAgeList(@PathParam("url") String url) throws IOException, ValidationException, ParserException, URISyntaxException
    {
        List<UserCalendarEventsDTO> events = eventsBean.getEventsByCalendarURL(url);
        String calFile = "mycalendar.ics";
        Calendar calendar = new Calendar();
        calendar.getProperties().add(new ProdId("CalendarDav"));
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getProperties().add(CalScale.GREGORIAN);

        VEvent vevent;
        for (UserCalendarEventsDTO ev : events) {
            DateTime start = new DateTime(ev.getStartDatetime());
            DateTime end = new DateTime(ev.getEndDatetime());
            vevent = new VEvent(start, end, ev.getName());
            Description desc = new Description(ev.getDescription());
            if (ev.getAllDay()) {
                vevent.getProperties().getProperty(Property.DTSTART).getParameters().add(Value.DATE);
            }
            UidGenerator uidGenerator = new UidGenerator("1");
            vevent.getProperties().add(uidGenerator.generateUid());
            vevent.getProperties().add(desc);
            
            List<EventMembersDTO> members = membersService.getMembersByEventId(ev.getUserCalendarEventsId());
            for (EventMembersDTO mem : members) {
                vevent.getProperties().add(new Organizer("mailto:"+mem.getUser().getEmail()));
                emailSenderUtil.sendInvite(mem.getUser().getEmail(), ev.getName(), ev.getDescription());
            }
            calendar.getComponents().add(vevent);
        }
        
        FileOutputStream fout = new FileOutputStream(calFile);

        CalendarOutputter outputter = new CalendarOutputter();
        outputter.setValidating(false);
        outputter.output(calendar, fout);
        
        File file = new File(calFile);
        ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition", "attachment; filename='mycalendar.ics'");
        return response.build();
    }
    
    /* @PUT
    @Path("calendar/")
    @Produces("text/calendar")
    */
    public void setCalendarICS() throws IOException, ParserException
    {
        String calFile = "mycalendar.ics";
        FileInputStream fin = new FileInputStream(calFile);
        CalendarBuilder builder = new CalendarBuilder();
        Calendar calendar = builder.build(fin);
        
        //Iterating over a Calendar
        for (Iterator i = calendar.getComponents().iterator(); i.hasNext();) {
            Component component = (Component) i.next();
            System.out.println("Component [" + component.getName() + "]");

            for (Iterator j = component.getProperties().iterator(); j.hasNext();) {
                Property property = (Property) j.next();
                System.out.println("Property [" + property.getName() + ", " + property.getValue() + "]");
            }
        }
    }
}
