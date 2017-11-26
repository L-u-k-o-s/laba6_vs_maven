
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;


/**
 * Created by Lenovo on 06.11.2017.
 */
public class Main {
    public static void main(String[] args) {
        try {
            CompanyCollection companyCollection = new CompanyCollection();
            companyCollection.setArrayList();
            companyCollection.sqlRequestParsing("select * from dataBase where countEmployees BETWEEN 23 AND 1002");
            System.out.println("Вот эта компания была найдена по заданному краткому наименованию: " + companyCollection.searchByShortName("Google"));
            System.out.println();

            System.out.println("Вот компании, которые были найдены по заданной отрасли:");
            ArrayList<Company> arrayList1 = companyCollection.getCompaniesEqualsByBranch("auto");
            companyCollection.showArrayList(arrayList1);

            ArrayList<Company> arrayList2 = companyCollection.getCompaniesEqualsByActivity("auto");
            System.out.println("По виду деятельности:\n");
            companyCollection.showArrayList(arrayList2);

            ArrayList<Company> arrayList3 = companyCollection.getCompaniesInTimePeriod(new Date(1997 - 1900, 1 - 1, 1), new Date(2002 - 1900, 12 - 1, 31));
            System.out.println("По времени:\n");
            companyCollection.showArrayList(arrayList3);

            ArrayList<Company> arrayList4 = companyCollection.getCompaniesWithEmployeesCount(2000, 10000000);
            System.out.println("По сотрудникам:\n");
            companyCollection.showArrayList(arrayList4);

            companyCollection.xmlMaker(arrayList1, "output1.xml");
            companyCollection.xmlMaker(arrayList2, "output2.xml");
            companyCollection.xmlMaker(arrayList3, "output3.xml");
            companyCollection.xmlMaker(arrayList4, "output4.xml");

            companyCollection.jsonMaker(arrayList1, "output1.json");
            companyCollection.jsonMaker(arrayList1, "output2.json");
            companyCollection.jsonMaker(arrayList1, "output3.json");
            companyCollection.jsonMaker(arrayList1, "output4.json");


        } catch (InvalidRequest ir){
            System.out.println("у вас Invalid Request");
        }catch (UnexpectedRequest ue){
            System.out.println(ue);
        }
        catch (FileNotFoundException fnfe) {
            System.out.println("Файл не найден");
        } catch (TransformerException te) {
            System.out.println(te);
        } catch (IOException IOE) {
            System.out.println(IOE);
        } catch (ParserConfigurationException pce) {
            System.out.println(pce);
        }

    }
}
