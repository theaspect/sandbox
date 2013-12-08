package com.blazer.repl;

import com.blazer.repl.domain.Contact;
import com.blazer.repl.domain.ContactInfo;
import com.blazer.repl.service.Dao;
import com.blazer.repl.servlet.GuiceListener;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import javax.servlet.DispatcherType;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/** @author Constantine Linnick <theaspect@gmail.com> */
public class EmbeddedServer {
    public static void main(String[] args) throws Exception {
        GuiceListener listener = new GuiceListener();
        init(listener);
        startServer(listener);
    }

    private static void init(GuiceListener listener) {
        Injector injector = listener.getInjector();
        Dao dao = injector.getInstance(Dao.class);
        String[] firstNames = new String[]{
                "Артем", "Ярослав", "Дмитрий", "Александр", "Кирилл",
                "Максим", "Матвей", "Иван", "Петр", "Николай"};
        String[] lastNames = new String[]{
                "Смирнов", "Иванов", "Кузнецов", "Соколов", "Попов",
                "Лебедев", "Козлов", "Новиков", "Морозов", "Петров"};
        Random random = new Random();
        List<Contact> contacts = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            Contact contact = new Contact(
                    firstNames[random.nextInt(firstNames.length)],
                    lastNames[random.nextInt(lastNames.length)]);
            for (int j = 0; j < random.nextInt(3); j++) {
                ContactInfo contactInfo = new ContactInfo(
                        contact,
                        ContactInfo.InfoType.values()[random.nextInt(ContactInfo.InfoType.values().length)],
                        String.valueOf(100000 + random.nextInt(899999)));
                contact.getInfo().add(contactInfo);
            }
            contacts.add(contact);
        }
        dao.saveAll(contacts);
    }

    private static void startServer(GuiceListener listener) throws Exception {
        Server server = new Server(8080);
        WebAppContext context = new WebAppContext();
        context.setResourceBase(EmbeddedServer.class.getResource("/com/blazer/repl").toString());
        context.addFilter(GuiceFilter.class, "/*", EnumSet.allOf(DispatcherType.class));
        context.addEventListener(listener);
        context.setWelcomeFiles(new String[]{"com/blazer/repl/index.html"});
        context.setServer(server);
        context.setParentLoaderPriority(true);
        server.setHandler(context);
        server.start();
        server.join();
    }
}
