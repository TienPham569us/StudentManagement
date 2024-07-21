import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ListStudent {
    private ArrayList<Student> _studentList;

    public ListStudent() {
        this._studentList = new ArrayList<Student>();
    }
    public Student getStudentAt(int index) {
        return this._studentList.get(index);
    }
    public void setStudent(int index, Student student) {
        _studentList.set(index, student);
    }
    public void deleteStudent(int index) {
        _studentList.remove(index);
    }
    public ArrayList<Student> get_studentList() {
        return _studentList;
    }

    public void addStudent(Student student) {
        this._studentList.add(student);
    }

    public void deleteStudent(Student student) {
        this._studentList.remove(student);
    }

    //public void updateStudent()
    public int getNumberOfStudent() { return this._studentList.size(); }
    public void printStudentList() {
        for (int i=0; i<this._studentList.size();i++) {
            System.out.println("Student "+i+": "+this._studentList.get(i).toString());
        }
    }
    public void printStudentListByIdAscending() {
       // Student[] tempStudentArray = new Student[this._studentList.size()];

        for (int i=0;i<this._studentList.size()-1;i++) {
            for (int j=i+1;j<this._studentList.size();j++) {
                if (_studentList.get(i).get_id()>_studentList.get(j).get_id()) {
                    Student temp = _studentList.get(i);
                    _studentList.set(i,_studentList.get(j));
                    _studentList.set(j,temp);
                }
            }
        }
        printStudentList();
    }

    public void printStudentListByIdDescending() {
        // Student[] tempStudentArray = new Student[this._studentList.size()];

        for (int i=0;i<this._studentList.size()-1;i++) {
            for (int j=i+1;j<this._studentList.size();j++) {
                if (_studentList.get(i).get_id()<_studentList.get(j).get_id()) {
                    Student temp = _studentList.get(i);
                    _studentList.set(i,_studentList.get(j));
                    _studentList.set(j,temp);
                }
            }
        }
        printStudentList();
    }

    public void printStudentListByGPAAscending() {
        for (int i=0;i<this._studentList.size()-1;i++) {
            for (int j=i+1;j<this._studentList.size();j++) {
                if (_studentList.get(i).get_gpa()>_studentList.get(j).get_gpa()) {
                    Student temp = _studentList.get(i);
                    _studentList.set(i,_studentList.get(j));
                    _studentList.set(j,temp);
                }
            }
        }
       printStudentList();
    }

    public void printStudentListByGPADescending() {
        for (int i=0;i<this._studentList.size()-1;i++) {
            for (int j=i+1;j<this._studentList.size();j++) {
                if (_studentList.get(i).get_gpa()<_studentList.get(j).get_gpa()) {
                    Student temp = _studentList.get(i);
                    _studentList.set(i,_studentList.get(j));
                    _studentList.set(j,temp);
                }
            }
        }
        printStudentList();
    }

    public void sortStudentListByIdAscending() {
        for (int i=0;i<this._studentList.size()-1;i++) {
            for (int j=i+1;j<this._studentList.size();j++) {
                if (_studentList.get(i).get_id()>_studentList.get(j).get_id()) {
                    Student temp = _studentList.get(i);
                    _studentList.set(i,_studentList.get(j));
                    _studentList.set(j,temp);
                }
            }
        }
    }
    public void sortStudentListByIdDescending() {
       for (int i=0;i<this._studentList.size()-1;i++) {
            for (int j=i+1;j<this._studentList.size();j++) {
                if (_studentList.get(i).get_id()<_studentList.get(j).get_id()) {
                    Student temp = _studentList.get(i);
                    _studentList.set(i,_studentList.get(j));
                    _studentList.set(j,temp);
                }
            }
        }
    }

    public void sortStudentListByGPAAscending() {
        for (int i=0;i<this._studentList.size()-1;i++) {
            for (int j=i+1;j<this._studentList.size();j++) {
                if (_studentList.get(i).get_gpa()>_studentList.get(j).get_gpa()) {
                    Student temp = _studentList.get(i);
                    _studentList.set(i,_studentList.get(j));
                    _studentList.set(j,temp);
                }
            }
        }
    }

    public void sortStudentListByGPADescending() {
        for (int i=0;i<this._studentList.size()-1;i++) {
            for (int j = i + 1; j < this._studentList.size(); j++) {
                if (_studentList.get(i).get_gpa() < _studentList.get(j).get_gpa()) {
                    Student temp = _studentList.get(i);
                    _studentList.set(i, _studentList.get(j));
                    _studentList.set(j, temp);
                }
            }
        }
    }

    public void exportStudentListToFileCSV(String filePath) {
        if (_studentList.size()<=0) {
            System.out.println("Empty student list");
            return;
        }
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8));

            bw.write("ID,Name,GPA,Image,Address,Note");
            //bw.newLine();

            for (Student student: _studentList) {
                bw.newLine();
                bw.write(student.get_id()+","+
                        student.get_name()+","+
                        student.get_gpa()+","+
                        student.get_image()+","+
                        student.get_address()+","+
                        student.get_note());
            }

            System.out.println("Information of all student has been written into file "+filePath);
            bw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void importStudentListFromFileCSV(String filePath) {
        try {

            //BufferedReader br = new BufferedReader(new FileReader(filePath));
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8));
            String str=null;

            _studentList.clear();
            str = br.readLine();

            while (true) {
                str = br.readLine();

                if (str==null) {
                    break;
                }

                _studentList.add(Student.createStudentFromCSVLineData(str));
            }

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exportStudentListToFileBinary(String filePath) {
        DataOutputStream dos=null;

        if (_studentList.size()<=0) {
            System.out.println("Empty student list");
            return;
        }

        try {

           /* BufferedWriter bw = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(filePath)
                    )
            );*/

             dos = new DataOutputStream(
                    new FileOutputStream(filePath)
            );

            for (Student student: _studentList) {
                dos.writeInt(student.get_id());
                dos.writeUTF(student.get_name());
                dos.writeDouble(student.get_gpa());
                dos.writeUTF(student.get_image());
                dos.writeUTF(student.get_address());
                dos.writeUTF(student.get_note());
            }

            System.out.println("All student's information has been written into " + filePath);
            dos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void importStudentListFromFileBinary(String filePath) {
        try {
            DataInputStream dis = new DataInputStream(
                    new FileInputStream(filePath)
            );
            _studentList.clear();

            while (dis.available()>0) {
                int id = dis.readInt();
                String name = dis.readUTF();
                double gpa = dis.readDouble();
                String image = dis.readUTF();
                String address = dis.readUTF();
                String note = dis.readUTF();

                _studentList.add(new Student(id,name,gpa,image,address,note));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
