import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.json.*;
import javax.json.stream.JsonGenerator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.stream.Collectors;


/**
 * Created by Lenovo on 06.11.2017.
 */
public class CompanyCollection {

    private static Logger log = Logger.getLogger(CompanyCollection.class.getName());
    private ArrayList<Company> arrayList;


    public CompanyCollection() throws IOException {
        arrayList = new ArrayList<>();
        FileHandler fh = new FileHandler("log.xml", true);
        log.addHandler(fh);
        log.info("Запустился конструктор без параметров");
    }

    public void setArrayList() throws FileNotFoundException {
        log.info("Началась инициализация массива");
        Scanner sc = new Scanner(new File("input.in")).useDelimiter(";");
        Company company = new Company();
        int i = 0;
        while (sc.hasNextLine()) {
            arrayList.add(company.inicialization(sc.nextLine()));
            i++;
        }
        sc.close();
        log.info("Завершилась инициализация массива");
    }


    public Company searchByShortName(String string) {
        log.info("Начался поиск по краткому названию");

        for (Company company : arrayList) {
            if (company.getShortTitle().equalsIgnoreCase(string)) {
                return company;
            }
        }
        log.info("Завершился поиск по краткому названию");

        return null;
    }

    public ArrayList<Company> getCompaniesEqualsByBranch(String str) {
        log.info("Вызов функции getCompaniesEqualsByBranch()");
        return arrayList.stream().filter(company -> company.getBranch().equalsIgnoreCase(str)).collect(Collectors.toCollection(ArrayList<Company>::new));
    }

    public ArrayList<Company> getCompaniesEqualsByActivity(String str) {
        log.info("Вызов функции getCompaniesEqualsByActivity()");
        return arrayList.stream().filter(company -> company.getActivity().equalsIgnoreCase(str)).collect(Collectors.toCollection(ArrayList<Company>::new));
    }

    public ArrayList<Company> getCompaniesInTimePeriod(Date begin, Date end) {
        log.info("Вызов функции getCompaniesInTimePeriod()");
        return arrayList.stream().filter(company -> company.getDateFoundation().after(begin) && company.getDateFoundation().before(end)).collect(Collectors.toCollection(ArrayList<Company>::new));
    }

    public ArrayList<Company> getCompaniesWithEmployeesCount(int first, int second) {
        log.info("Вызов функции getCompaniesWithEmployeesCount()");
        return arrayList.stream().filter(company -> company.getCountEmployees() > first && company.getCountEmployees() < second).collect(Collectors.toCollection(ArrayList<Company>::new));

    }

    public void showArrayList(ArrayList<Company> arrayList1) {
        log.info("Вызов функции showArrayList()");
        for (Company company : arrayList1) {
            System.out.println(company);
            System.out.println();
        }
    }

    public void xmlMaker(ArrayList<Company> list, String fileName) throws TransformerException, IOException, ParserConfigurationException {
        log.info("Началось создание xml файла");
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        Document doc = documentBuilder.newDocument();
        Element rootElement = doc.createElement("ArrayList");
        doc.appendChild(rootElement);

        Element company;
        Attr attr;
        Element name;
        Element shortTitle;
        Element dateUpdate;
        Element address;
        Element dateFoundation;
        Element countEmployees;
        Element auditor;
        Element phone;
        Element email;
        Element branch;
        Element activity;
        Element internetAddress;

        int i = 1;
        for (Company comp : list) {

            company = doc.createElement("Company");
            rootElement.appendChild(company);

            attr = doc.createAttribute("id");
            attr.setValue(Integer.toString(i));
            company.setAttributeNode(attr);

            name = doc.createElement("name");
            name.appendChild(doc.createTextNode(comp.getName()));
            company.appendChild(name);

            shortTitle = doc.createElement("shortTitle");
            shortTitle.appendChild(doc.createTextNode(comp.getShortTitle()));
            company.appendChild(shortTitle);

            dateUpdate = doc.createElement("dateUpdate");
            dateUpdate.appendChild(doc.createTextNode(comp.getDateUpdate().toString()));
            company.appendChild(dateUpdate);

            address = doc.createElement("address");
            address.appendChild(doc.createTextNode(comp.getAddress()));
            company.appendChild(address);

            dateFoundation = doc.createElement("dateFoundation");
            dateFoundation.appendChild(doc.createTextNode(comp.getDateFoundation().toString()));
            company.appendChild(dateFoundation);

            countEmployees = doc.createElement("countEmployees");
            countEmployees.appendChild(doc.createTextNode(Integer.toString(comp.getCountEmployees())));
            company.appendChild(countEmployees);

            auditor = doc.createElement("auditor");
            auditor.appendChild(doc.createTextNode(comp.getAuditor()));
            company.appendChild(auditor);

            phone = doc.createElement("phone");
            phone.appendChild(doc.createTextNode(comp.getPhone()));
            company.appendChild(phone);

            email = doc.createElement("email");
            email.appendChild(doc.createTextNode(comp.getEmail()));
            company.appendChild(email);

            branch = doc.createElement("branch");
            branch.appendChild(doc.createTextNode(comp.getBranch()));
            company.appendChild(branch);

            activity = doc.createElement("activity");
            activity.appendChild(doc.createTextNode(comp.getActivity()));
            company.appendChild(activity);

            internetAddress = doc.createElement("internetAddress");
            internetAddress.appendChild(doc.createTextNode(comp.getInternetAddress()));
            company.appendChild(internetAddress);

            i++;
        }


        File file = new File(fileName);

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(new DOMSource(doc), new StreamResult(file));
        log.info("Завершилось создание xml файла");

    }

    public void jsonMaker(ArrayList<Company> list, String fileName) throws IOException {
        log.info("Началось создание json файла");
        FileWriter fileWriter = new FileWriter(new File(fileName));
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        Map<String, Boolean> config = new HashMap<>();
        config.put(JsonGenerator.PRETTY_PRINTING, true);
        JsonWriterFactory writerFactory = Json.createWriterFactory(config);


        for (Company comp : list) {

            JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
                    .add("Name", comp.getName())
                    .add("ShortTitle", comp.getShortTitle())
                    .add("DateUpdate", comp.getDateUpdate().toString())
                    .add("Adress", comp.getAddress())
                    .add("DateFoundation", comp.getDateFoundation().toString())
                    .add("CountEmployees", comp.getCountEmployees())
                    .add("Auditor", comp.getAuditor())
                    .add("Phone", comp.getPhone())
                    .add("Email", comp.getEmail())
                    .add("Branch", comp.getBranch())
                    .add("Activity", comp.getActivity())
                    .add("InternetAddress", comp.getInternetAddress());

            JsonObject jsonObject = objectBuilder.build();
            arrayBuilder.add(jsonObject);


        }
        writerFactory.createWriter(fileWriter).write(arrayBuilder.build());
        fileWriter.close();
        log.info("Завершилось создание json файла");
    }

    public void sqlRequestParsing(String str) throws InvalidRequest, UnexpectedRequest {
        str = str.trim();
        StringBuilder stringBuilder = new StringBuilder(str);
        Scanner sc = new Scanner(str).useDelimiter("[ ,\n\t]");
        //StringTokenizer stringTokenizer=new StringTokenizer(str);
        List<String> list = new ArrayList<>();
        while (sc.hasNext()) {
            list.add(sc.next());
        }
        sc.close();
        list = list.stream().filter(item -> !item.equals("") && !item.equals("\t")).collect(Collectors.toList());

        firstParce(list);

        if (isSatisfiedForChoosingByShortName(list)) {
            searchByShortName(whatSearchByShortName(list));
        } else if (isSatisfiedForChoosingByActivity(list)) {
            getCompaniesEqualsByActivity(whatSearchByActivity(list));
        } else if (isSatisfiedForChoosingByCountEmployees(list)) {
            Pair pair = rangeForSearch(list);
            System.out.println(
                    getCompaniesWithEmployeesCount(pair.getFirst(), pair.getSecond()));
        } else {
            throw new UnexpectedRequest("Непредвиденный запрос, кооторый не подходит ни под какие условия");
        }
    }

    public boolean isSatisfiedForChoosingByShortName(List<String> list) throws UnexpectedRequest {
        try {
            if (list.size() > 3) {
                return false;
            }
            if (list.get(0).trim().substring(0, 9).equalsIgnoreCase("shortName")) {
                return true;
            }
            return false;
        } catch (StringIndexOutOfBoundsException siobe) {
            throw new UnexpectedRequest("Непредвиденный параметр ");
        }
    }

    public String whatSearchByShortName(List<String> list) throws UnexpectedRequest {
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : list) {
            stringBuilder.append(str);
        }
        stringBuilder = new StringBuilder(stringBuilder.toString().replace(" ", ""));
        stringBuilder.replace(0, 9, "");
        if (stringBuilder.charAt(0) == '=' && stringBuilder.charAt(1) == '\'') {
            stringBuilder.replace(0, 2, "");
            int i = 0;
            while (i < stringBuilder.length()) {
                if (stringBuilder.charAt(i) == '\'' && i == 0 && i + 1 == stringBuilder.length()) {
                    return "";
                } else if (stringBuilder.charAt(i) == '\'' && i > 0 && stringBuilder.charAt(i) != '\\' && i + 1 == stringBuilder.length()) {
                    return stringBuilder.substring(0, stringBuilder.length() - 1).toString();
                }
                i++;
            }
            throw new UnexpectedRequest("В вашем запросе неправильные аргументы");
        }
        throw new UnexpectedRequest("В вашем запросе неправильные аргументы");
    }

    public boolean isSatisfiedForChoosingByActivity(List<String> list) throws UnexpectedRequest {
        try {
            if (list.size() > 3) {
                return false;
            }
            if (list.get(0).trim().substring(0, 8).equalsIgnoreCase("Activity")) {
                return true;
            }
            return false;
        } catch (StringIndexOutOfBoundsException siobe) {
            throw new UnexpectedRequest("Непредвиденный параметр ");
        }
    }

    public String whatSearchByActivity(List<String> list) throws UnexpectedRequest {
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : list) {
            stringBuilder.append(str);
        }
        stringBuilder = new StringBuilder(stringBuilder.toString().replace(" ", ""));
        stringBuilder.replace(0, 8, "");
        if (stringBuilder.charAt(0) == '=' && stringBuilder.charAt(1) == '\'') {
            stringBuilder.replace(0, 2, "");
            int i = 0;
            while (i < stringBuilder.length()) {
                if (stringBuilder.charAt(i) == '\'' && i == 0 && i + 1 == stringBuilder.length()) {
                    return "";
                } else if (stringBuilder.charAt(i) == '\'' && i > 0 && stringBuilder.charAt(i) != '\\' && i + 1 == stringBuilder.length()) {
                    return stringBuilder.substring(0, stringBuilder.length() - 1).toString();
                }
                i++;
            }
            throw new UnexpectedRequest("В вашем запросе неправильные аргументы ");
        }
        throw new UnexpectedRequest("В вашем запросе неправильные аргументы");
    }

    public boolean isSatisfiedForChoosingByCountEmployees(List<String> list) throws UnexpectedRequest {
        try {
            if (list.size() > 5) {
                return false;
            }
            if (list.get(0).trim().substring(0, 14).equalsIgnoreCase("countEmployees")) {
                return true;
            }
            return false;
        } catch (StringIndexOutOfBoundsException siobe) {
            throw new UnexpectedRequest("Непредвиденный параметр ");
        }
    }

    public Pair rangeForSearch(List<String> list) throws InvalidRequest {
        try {
            int first = 0;
            int second = 0;
            StringBuilder stringBuilder = new StringBuilder();
            for (String str : list) {
                stringBuilder.append(str).append(" ");
            }
            stringBuilder.replace(0, 14, "");
            //stringBuilder = new StringBuilder(stringBuilder.toString().trim());
            Scanner sc = new Scanner(stringBuilder.toString().trim());
            if (sc.next().equalsIgnoreCase("BETWEEN")) {
                if (sc.hasNextInt()) {
                    first = sc.nextInt();
                    if (sc.next().equalsIgnoreCase("AND")) {
                        if (sc.hasNextInt()) {
                            second = sc.nextInt();
                            return new Pair(first, second);
                        }
                    }
                }
            }
            throw new InvalidRequest();
        } catch (Exception exc) {
            throw new InvalidRequest();
        }


    }

    private void firstParce(List<String> list) throws InvalidRequest {
        if (list.get(0).equalsIgnoreCase("select") && list.get(1).equalsIgnoreCase("*") && list.get(2).equalsIgnoreCase("from")) {
            list.remove(0);
            list.remove(0);
            list.remove(0);

        } else if ((list.get(0).equalsIgnoreCase("select*") && list.get(1).equalsIgnoreCase("from")) || (list.get(0).equalsIgnoreCase("select") && list.get(1).equalsIgnoreCase("*from"))) {
            list.remove(0);
            list.remove(0);
        } else if (list.get(0).equalsIgnoreCase("select*from")) {
            list.remove(0);
        } else {
            throw new InvalidRequest();
        }
        if (list.get(0).equalsIgnoreCase("dataBase") && list.get(1).equalsIgnoreCase("where")) {
            list.remove(0);
            list.remove(0);
        } else {
            throw new InvalidRequest();
        }
    }
}
