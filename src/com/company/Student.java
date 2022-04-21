package Human1;

import org.w3c.dom.ls.LSOutput;

import javax.imageio.IIOException;
import java.io.*;
import java.util.*;

public class Student {
    Scanner sc = new Scanner(System.in);
    BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    ArrayList<Student> studList = new ArrayList();
    private String id;
    private String name;
    private String lastName;
    private String date;
    private String group;
    private String marks = "Any marks";
    boolean status = true;

    Student(){}
    Student(String id,String name, String lastName, String date, String group,String marks) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.date = date;
        this.group = group;
        this.marks = marks;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getMarks() {
        return marks;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGroup() {
        return group;
    }

    public static void main(String[] args) throws IOException {
        Student student = new Student();
        try {
            File file = new File("student.txt");
            if(!file.exists()){
                file.createNewFile();
            }
            else{
                student.readStud();
            }
            student.menuStud();
        }catch (IOException e){
            System.out.println("Error"+e);
        }
    }

    public void menuStud()throws IOException{
        while(status){
            System.out.println("----- Список студентов -----");
            System.out.println("\t 1. Добавить \t 2. Удалить \t 3. Обновить \t 4. Запросить все ");
            System.out.println("\t 5. Сохранить \t 6. Студенты в группе \t 7. Добавить оценку\t 0. Выход");
            System.out.println("----- Список студентов -----");
            System.out.println("Введите номер, чтобы выбрать соответствующую функцию:");
            String selected = sc.nextLine();
            switch(selected) {
                case "1": {
                    //New
                    createStud();
                    break;
                }
                case "2":{
                    //Delete
                    deleteStud();
                    break;
                }
                case "3":{
                    //Update
                    updateStud();
                    break;
                }
                case "4":{
                    findAll();
                    break;
                }
                case "5":{
                    saveStud();
                    break;
                }
                case "6":{
                    sort();
                    break;
                }
                case "7":{
                    addMarks();
                    break;
                }
                case "0":{
                    sc.close();
                    bf.close();
                    status = false;
                    break;
                }
            }
        }
    }

    public void createStud() throws IOException{
        System.out.println("Введите айди студентческого билета:");
        id = bf.readLine();
        System.out.println("Введите имя студента:");
        name = bf.readLine();
        System.out.println("Введите фамилию студента:");
        lastName = bf.readLine();
        System.out.println("Ввдеите дату рождения студента (yyyy-mm-dd):");
        date = bf.readLine();
        System.out.println("Введите группу студента: ");
        group = bf.readLine();
        Student student = new Student(id,name, lastName, date, group, marks);
        studList.add(student);
        //System.out.println(student.show());
        System.out.println("Данные о студенте успешно добавлены!");
    }

    private String show() {
        return "\n Имя: "+this.name+"\n Фамилия: "+this.lastName+"\n Дата рождения: "+this.date;
    }

    public void readStud()throws IOException{
        FileInputStream filein;
        try{
            filein = new FileInputStream("student.txt");
            if (filein.available()==0){
                System.out.println("Содержимое базы студентов пустое, главня страница скоро будет загружена...");
            }else{
                BufferedReader br = new BufferedReader (new InputStreamReader(filein));
                String line = null;
                Student student = null;
                while ((line=br.readLine()) != null){
                    String[] str = line.split(",");
                    student = new Student(str[0],str[1],str[2],str[3],str[4],str[5]);
                    studList.add(student);
                    student = null;
                }
                filein.close();
                br.close();
                System.out.println("База студентов загружена, можете выполять операции с данными...");
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }
    public void sort() throws IOException {
        System.out.println("Введите название группы, чтобы увидеть список студентов этой группы: ");
        String gname = sc.nextLine();
        int n = 1;
        for (Student student:studList){
            if(student.getGroup().equals(gname)){
                System.out.print("--[Студент №"+n+"]--");
                System.out.println(student.show());
                n++;
            }
        }
    }

    private void addMarks(){
        System.out.println("Введите id студента для которого хоитите добавить оценки:");
        String id = sc.nextLine();
        String m;
        for(Student student:studList){
            if(student.getId().equals(id)){
                System.out.print("Введите оценку: ");
                String mark = sc.nextLine();

                if(student.getMarks() == "Any marks"){
                    student.setMarks(mark);
                }
                else {
                    m = student.getMarks();
                    m +=" " + mark;
                    student.setMarks(m);
                }
            }
        }
    }

    public void updateStud()throws IOException{
        System.out.println("Что вы хотите изменить?");
        System.out.println("[1] - Группа");
        System.out.println("[2] - Внести изменения в оценки");
        System.out.print("Введите номер: ");
        int n = sc.nextInt();
        if(n==1){
            System.out.println("Введите id студента: ");
            String  id = bf.readLine();
            System.out.println("Введите верную группу для студента:");
            String rgroup = bf.readLine();
            for(Student student:studList){
                if(student.getId().equals(id)){
                    student.setGroup(rgroup);
                    System.out.println("Успешно изменено!");
                }
            }
        }
        else if(n==2){
            System.out.println("Что вы хотите изменить?");
            System.out.println("[1] - Удалить оценку");
            System.out.println("[2] - Заменить оценку на другую");
            System.out.print("Введите номер: ");
            int n1 = sc.nextInt();
            if(n1 == 1){
                System.out.println("Введите id студента: ");
                String  id = bf.readLine();
                System.out.println("Введите оценку, которую хотите удалить:");
                String mark = bf.readLine();


                for(Student student:studList){
                    if(student.getId().equals(id)){
                        if(student.getMarks().length() > 2){
                            String[] second = student.getMarks().split(" ");
                            String[] third = new String[second.length - 1];
                            int index=0;
                            for (int i = 0; i < second.length; i++) {
                                if(second[i].equals(mark)) index = i;
                            }
                            System.arraycopy(second, 0, third, 0, index);
                            System.arraycopy(second, index + 1, third, index, second.length - index - 1);
                            student.setMarks(String.join(" ",third));
                        }else{
                            student.setMarks("Any marks");
                        }
                    }
                }
            }
            else if(n1 == 2){
                System.out.println("Введите id студента: ");
                String  id = bf.readLine();
                System.out.println("Введите оценку, которую хотите удалить:");
                String markDel = bf.readLine();
                System.out.println("Введите верную оценку:");
                String markTrue = bf.readLine();
                for(Student student:studList){
                    if(student.getId().equals(id)){
                        if(student.getMarks().length() > 2){
                            String[] str = student.getMarks().split(" ");
                            for (int i = 0; i < str.length; i++) {
                                if(str[i].equals(markDel)){
                                    str[i] = markTrue;
                                }
                            }
                            student.setMarks(String.join(" ",str));
                        }else {
                            student.setMarks(markTrue);
                        }

                    }
                }
            }
        }
    }

    private void deleteStud() throws IOException {
        System.out.println("Введите id студента, которого хотите удалить: ");
        String id = bf.readLine();
        for(Student student: studList){
            if(student.getId().equals(id)){
                studList.remove(student);
                System.out.println("Успешно удалено!");
            }
        }
        /*for (int i = 0; i < studList.size(); i++) {
            Student student = (Student) studList.get(i);
            if(student.getLastName().equals(dSurname)){
                studList.remove(student);
                System.out.println("Успешно удалено!");
            }
        }*/
    }

    String showStud() throws IOException {
        return "\n iD: "+this.id+"\n Имя: "+this.name+"\n Фамилия: "+this.lastName+"\n Год: "+this.date+"\n Группа: "+this.group+"\n Оценки: "+ marks;
    }

    public void findAll() throws IOException {
        for(Student student:studList){
            System.out.println(student.showStud());
        }
        /*studList.stream().forEach(student -> {
            try {
                System.out.println(student.showStud());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });*/
    }

    public void saveStud() throws IOException {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("student.txt"));
            for (Student student:studList){
                bw.write(student.getId()+","+student.getName()+","+student.getLastName()+","+student.getDate()+","+student.getGroup()+","+student.getMarks());
                bw.write("\r\n");
            }
            bw.flush();
            System.out.println("Успешно сохранено!");
            bw.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

/*    class Marks{
        private ArrayList<Marks> listMarks = new ArrayList();
        private int mark;

        public  Marks(){}

        public Marks(int mark){
            this.mark = mark;
        }

        public void addMark()throws IOException{
            System.out.print("Введите фамилию студента: ");
            String surname = sc.nextLine();
            for(Student student:studList){
                if(student.getLastName().equals(surname)){
                    System.out.print("Введите оценку: ");
                    mark = sc.nextInt();
                    Marks marks = new Marks(mark);
                    listMarks.add(marks);
                    continue;
                }
            }
        }

        public void deleteMark()throws IOException{
            System.out.print("Введите фамилию студента: ");
            String surname = sc.nextLine();
            for(Student student:studList){
                if(student.getLastName().equals(surname)){
                    System.out.print("Введите номер счета оценки: ");
                    int number = sc.nextInt();
                    int i = 0;
                    for(Marks marks:listMarks){
                        if(i==number){
                            listMarks.remove(marks);
                        }
                        i++;
                    }
                    continue;
                }
            }
        }
    }

    class Group{
        private ArrayList<Group> listGroups = new ArrayList();
        List<Student> list = new ArrayList();
        private String group;

        public Group(String group){
            this.group = group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public String getGroup() {
            return group;
        }

        public void addGroup(){
            System.out.print("Введите название группы: ");
            group = sc.nextLine();
            Group groups = new Group(group);
            listGroups.add(groups);
        }

        public void addToGroup(){
            System.out.print("Введите название группы, чтобы добавить туда студента:");
            String ngroup = sc.nextLine();
            for (Group group:listGroups){
                if(group.getGroup().equals(ngroup)){
                    System.out.print("Введите фамилию студента, которого хотите добавить: ");
                    String surname = sc.nextLine();
                    for(Student student: studList){
                        if(student.getLastName().equals(surname)){
                            list.add(student);
                        }
                    }
                }
            }
        }
    }*/
}
