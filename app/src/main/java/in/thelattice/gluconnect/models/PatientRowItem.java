package in.thelattice.gluconnect.models;

/**
 * Created by Ishan on 03-11-2015.
 */
public class PatientRowItem {
    private String name;
    private String ward;
    private int age;
    private char gender;

    public PatientRowItem(String name, String ward, int age, char gender) {
        this.name = name;
        this.ward = ward;
        this.age = age;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }
}
