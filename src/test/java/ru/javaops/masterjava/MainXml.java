package ru.javaops.masterjava;

import com.google.common.io.Resources;
import ru.javaops.masterjava.xml.schema.ObjectFactory;
import ru.javaops.masterjava.xml.schema.Payload;
import ru.javaops.masterjava.xml.schema.Project;
import ru.javaops.masterjava.xml.schema.User;
import ru.javaops.masterjava.xml.util.JaxbParser;
import ru.javaops.masterjava.xml.util.Schemas;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class MainXml {
    public static void main(String[] args) throws Exception {
        JaxbParser JAXB_PARSER = new JaxbParser(ObjectFactory.class);
        JAXB_PARSER.setSchema(Schemas.ofClasspath("payload.xsd"));
        String projectName = args[0];
        Payload payload = JAXB_PARSER.unmarshal(
                Resources.getResource("payload.xml").openStream());
        Set<User> users = new TreeSet<>(Comparator.comparing((User::getValue)).thenComparing(User::getEmail));
        for (User user : payload.getUsers().getUser()) {
            for (Object ob : user.getGroupRef()) {
                Project.Group group = (Project.Group) ob;
                if (group.getName().equals(projectName)) {
                    users.add(user);
                }
            }
        }
        users.forEach(user -> System.out.println(user.getValue()));
    }
}
