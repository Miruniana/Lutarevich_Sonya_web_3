package org.jaxb.test;

import org.jaxb.model.Department;
import org.jaxb.model.Employee;
import org.jaxb.model.Organization;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class TestExample {

    private static final String XML_FILE = "src/main/resources/org-info.xml";

    public static void main(String[] args) {

        Organization org = createOrganization();

        try {
            // create JAXB context and instantiate marshaller
            JAXBContext context = JAXBContext.newInstance(Organization.class);

            Marshall(context, org);

            Unmarshall(context);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private static Organization createOrganization() {
        List<Department> departments = new ArrayList<Department>();

        // инициализация первого департамента
        Employee emp1 = new Employee("E01", "Tom", null);
        Employee emp2 = new Employee("E02", "Mary", "E01");
        Employee emp3 = new Employee("E03", "John", null);

        List<Employee> employees1 = new ArrayList<Employee>();
        employees1.add(emp1);
        employees1.add(emp2);
        employees1.add(emp3);

        Department dept1 = new Department("D01", "ACCOUNTING","NEW YORK");
        dept1.setEmployees(employees1);

        departments.add(dept1);

        // инициализация второго департамента
        Employee emp4 = new Employee("E04", "Julianna", "E02");
        Employee emp5 = new Employee("E05", "Anna", "E01");
        Employee emp6 = new Employee("E06", "Ksenia", "E03");
        Employee emp7 = new Employee("E07", "Sonya", null);

        List<Employee> employees2 = new ArrayList<Employee>();
        employees2.add(emp4);
        employees2.add(emp5);
        employees2.add(emp6);
        employees2.add(emp7);

        Department dept2 = new Department("D02", "DEPARTMENT_NAME","MENSK");
        dept2.setEmployees(employees2);

        departments.add(dept2);


        Organization org = new Organization("01", "ORG_NAME");
        List<Organization> organizations = new ArrayList<>();
        organizations.add(org);

        org.setDepartments(departments);
        return org;
    }

    private static void Unmarshall(JAXBContext context) throws JAXBException, FileNotFoundException {
        Unmarshaller um = context.createUnmarshaller();

        Organization organization = (Organization) um.unmarshal(new FileReader(
                XML_FILE));

        List<Department> deps = organization.getDepartments();

        System.out.println(organization.getOrgName() + "\n");
        for (Department dep :
                deps) {
            System.out.println("Department: " + dep.getDeptName());
            List<Employee> emps = dep.getEmployees();
            for (Employee emp : emps) {
                System.out.println(emp.getEmpName());
            }
        }
    }

    private static void Marshall(JAXBContext context, Organization org) throws JAXBException {
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        File outFile = new File(XML_FILE);
        m.marshal(org, outFile);
    }
}

