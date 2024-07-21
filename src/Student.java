import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Student {
    private int _id;
    private String _name;
    private double _gpa;
    private String _image;
    private String _address;
    private String _note;

    public Student() {
        this._id = 0;
        this._name = "";
        this._gpa = 0;
        this._image = "";
        this._address = "";
        this._note = "";
    }
    public Student(int _id, String _name, double _gpa, String _image, String _address, String _note) {
        this._id = _id;
        this._name = _name;
        this._gpa = _gpa;
        this._image = _image;
        this._address = _address;
        this._note = _note;
    }

    public static Student createStudentFromCSVLineData(String csvLine) {
        try {
            String[] data = csvLine.split(",");
            int id = Integer.parseInt(data[0]);
            String name = data[1];
            double gpa = Double.parseDouble(data[2]);
            String image = data[3];
            String address = data[4];
            String note = data[5];

            return new Student(id, name, gpa, image, address, note);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public double get_gpa() {
        return _gpa;
    }

    public void set_gpa(double _gpa) {
        this._gpa = _gpa;
    }

    public String get_image() {
        return _image;
    }

    public void set_image(String _image) {
        this._image = _image;
    }

    public String get_address() {
        return _address;
    }

    public void set_address(String _address) {
        this._address = _address;
    }

    public String get_note() {
        return _note;
    }

    public void set_note(String _note) {
        this._note = _note;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Student{ID: ");
        sb.append(_id);
        sb.append(", name: ");
        sb.append(_name);
        sb.append(", gpa: ");
        sb.append(_gpa);
        sb.append(", address: ");
        sb.append(_address);
        sb.append(", note: ");
        sb.append(_note);
        sb.append("}");
        return sb.toString();
    }

    public void inputDataFromKeyboard() {
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(System.in, StandardCharsets.UTF_8));

            System.out.println("Enter student's id:");
            String str = br.readLine();
            _id = Integer.parseInt(str);

            System.out.println("Enter student's name: ");
            str = br.readLine();
            _name = str;

            System.out.println("Enter student's gpa: ");
            str = br.readLine();
            _gpa = Double.parseDouble(str);

            System.out.println("Enter student's image: ");
            str = br.readLine();
            _image = str;

            System.out.println("Enter student's address: ");
            str = br.readLine();
            _address = str;

            System.out.println("Enter student's note: ");
            str = br.readLine();
            _note = str;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
