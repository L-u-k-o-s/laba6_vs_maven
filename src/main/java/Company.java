import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

/**
 * Created by Lenovo on 06.11.2017.
 */
public class Company {
    private String name;
    private String shortTitle;
    private Date dateUpdate;
    private String address;
    private Date dateFoundation;
    private int countEmployees;
    private String auditor;
    private String phone;
    private String email;
    private String branch;
    private String activity;
    private String internetAddress;

    public Company() {
    }

    public Company(String name, String shortTitle, Date dateUpdate,
                   String address, Date dateFoundation,
                   int countEmployees, String auditor, String phone,
                   String email, String branch, String activity, String internetAddress) {
        this.name = name;
        this.shortTitle = shortTitle;
        this.dateUpdate = dateUpdate;
        this.address = address;
        this.dateFoundation = dateFoundation;
        this.countEmployees = countEmployees;
        this.auditor = auditor;
        this.phone = phone;
        this.email = email;
        this.branch = branch;
        this.activity = activity;
        this.internetAddress = internetAddress;
    }


    public Company inicialization(String str) throws FileNotFoundException {
        Scanner sc = new Scanner(str).useDelimiter(";");
        String tmp;
        int day;
        int month;
        int year;
        name = sc.next().trim();
        shortTitle = sc.next().trim();
        tmp = sc.next();
        Scanner dateScan = new Scanner(tmp).useDelimiter("[.]");
        day = dateScan.nextInt();
        month = dateScan.nextInt() - 1;
        year = dateScan.nextInt() - 1900;
        dateUpdate = new Date(year, month, day);
        address = sc.next().trim();
        tmp = sc.next();
        dateScan = new Scanner(tmp).useDelimiter("[.]");
        day = dateScan.nextInt();
        month = dateScan.nextInt() - 1;
        year = dateScan.nextInt() - 1900;
        dateFoundation = new Date(year, month, day);
        countEmployees = sc.nextInt();
        auditor = sc.next().trim();
        phone = sc.next().trim();
        email = sc.next().trim();
        branch = sc.next().trim();
        activity = sc.next().trim();
        internetAddress = sc.next().trim();
        return new Company(name, shortTitle, dateUpdate, address, dateFoundation,
                countEmployees, auditor, phone, email, branch, activity, internetAddress);


    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Полное название ").append(name).append("\nКраткое название: ").append(shortTitle).append("\nДата актуализации:").append(dateUpdate).append("\nАдрес: ").append(address).
                append("\nДата основания: ").append(dateFoundation).append("\nКличество работников: ").append(countEmployees).append("\nАудитор: ").append(auditor).
                append("\nТелефон: ").append(phone).append("\nemail:").append(email).append("\nОтрасль: ").append(branch).append("\nВид деятельности: ").append(activity).
                append("\nСайт: ").append(internetAddress);
        return new String(stringBuilder);
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public String getBranch() {
        return branch;
    }

    public String getActivity() {
        return activity;
    }

    public Date getDateFoundation() {
        return dateFoundation;
    }

    public int getCountEmployees() {
        return countEmployees;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getAuditor() {
        return auditor;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getInternetAddress() {
        return internetAddress;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    ;
}
