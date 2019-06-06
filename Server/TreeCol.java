import org.postgresql.core.SqlCommand;

import java.io.*;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

public class TreeCol {
    private String l="";
    //private static TreeMap<String, Human> HumansTree = new TreeMap<>();
    private ConcurrentSkipListMap<String,Human> HumansTree;
    private ZonedDateTime date;
//    public static TreeMap<String, Human> get() {
//        return HumansTree;
//    }
//    public static void set(TreeMap<String, Human> Tree) {
//        HumansTree=Tree;
//    }
    public TreeCol(){
        date=ZonedDateTime.now();
        HumansTree=new ConcurrentSkipListMap<>();
    }

    public void setL(String l) {
        this.l =l;
    }

    public String getL() {
        return l;
    }

    public    ConcurrentSkipListMap<String,Human> get(){
        return HumansTree;
    }

    public    void set(ConcurrentSkipListMap<String, Human> humansTree) {
        HumansTree = humansTree;
    }
    public void loadFromDb(){
        try{
            PreparedStatement preparedStatement=DbConnect.connection.prepareStatement("Select * from \"HumanTree\"");
            Result result=Main.db.change(preparedStatement,"get");
            while(result.getResultSet().next()){
                HumansTree.put(result.getResultSet().getString("nickname"),
                        new Human(result.getResultSet().getString("nickname"),
                                result.getResultSet().getString("surname"),
                                result.getResultSet().getString("owner"),
                                result.getResultSet().getInt("age"),
                                new Location((Integer [])result.getResultSet().getArray("Location").getArray()),
                                ZonedDateTime.parse(result.getResultSet().getString("date"))));
            }
        }catch (SQLException | NullPointerException e){
            System.out.println("Can't load collection");
        }
    }

    /**
     * <p>Показывает содержимое коллекции</p>
     */
//    public static void show(){
//        System.out.println(HumansTree.entrySet().toString());
//    }
    public void sendMessage(Human human){
        this.l=this.l+human.toString()+"\n";
    }
    public String show(){
//        try {
             HumansTree.entrySet().stream().forEach((human)->sendMessage(human.getValue()));
             return getL();
//        }
//        catch(IOException e){
//            System.out.println("Клиенг обнаглел, отлючился");
//        }
    }
    /**
     * <p>список и описание команд</p>
     */
//    public static String help(){
//        return "insert {String key} {element} - команда добавления нового элемента с заданным ключом. " +
//                "Параметры key - String(имя человека). " +
//                "Параметры element - передавать 5 значений: nickname,  surname, name, info, status. Пример : insert {\"nickname\":\"Red\"}" +
//                "{\"nickname\":\"Red\", \"surname\":\"Blue\",\"Location\":{\"x\":\"72\", \"y\":\"65\"},\"age\":\"14\"}"+
//        "show - вывод элементов в нашей коллекции"+
//        "save - сохранить измененную коллекцию в исходный файл"+
//        "info - информация о коллекции, ее тип, дата создания и кол-во элементов"+
//        "remove {String key} - удалить элемент коллекции по его ключу. Пример : remove {\"nickname\":\"Red\"}"+
//        "add_if_min - добавляет новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции. Пример: add_if_min {\"nickname\":\"Red\", \"surname\":\"Blue\",\"Skill\":{\"name\":\"Убийство\", \"info\":\"Смертельное повреждение объекту\", \"status\":\"Мертв\"}}"+
//        "add_if_max - добавляет новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции. Пример: add_if_max {\"nickname\":\"Red\", \"surname\":\"Blue\",\"Skill\":{\"name\":\"Убийство\", \"info\":\"Смертельное повреждение объекту\", \"status\":\"Мертв\"}}";
//    }
    public  void addset(ConcurrentSkipListMap<String,Human> humansTree,User user){
            humansTree.entrySet().stream().forEach(c->c.getValue().setOwner(user.getName()));
            HumansTree.putAll(humansTree);
    }
//    public  String load(String where){
//       try {
//           FileInputStream Try = new FileInputStream(where);
//           BufferedInputStream MyTree = new BufferedInputStream(Try);
//           if (MyTree.available() > 0) {
//               byte[] b = new byte[MyTree.available()];
//               MyTree.read(b);
//               String XmlTree = new String(b);
//               addset(FileOper.fromXml(XmlTree));
//               return "Данные загружены";
//           } else return "Документ пуст";
//       }
//       catch (FileNotFoundException e){
//           return "По указанному пути нет файла или нет прав доступа к нему";
//       }
//       catch (IOException e) {
//           return "Файл пуст или нет прав для доступа к файлу";
//       }
////       return "По указанному пути нет файла, он пуст или нет прав для доступа к файлу";
//    }
//    /**
//     *<p>Сохраняет коллекцию в файл</p>
//     */
//    public static void save(){
//        FileOper.XmlSaveFile(HumansTree);
//    }
//    public  String autosave(){
//        try {
//            File autosave = new File("autosave.xml");
//            autosave.createNewFile();
//            return FileOper.XmlSaveFile(HumansTree,autosave.getAbsolutePath());
//        }
//        catch (IOException e){
//            return "Недостаточно прав или чего-нибудь еще для создания файла(((, работа не сохранена((";
//        }
//    }
        public  String save(User user,TreeCol mytree) {
            String updatePattern = " UPDATE \"HumanTree\" SET %s WHERE \"nickname\"='%s'; ";
            String insertPattern = " INSERT INTO \"HumanTree\" VALUES(%s); ";
            String deletePattern = " DELETE FROM \"HumanTree\" WHERE \"nickname\"='%s'; ";
            try {
//                PreparedStatement preparedStatement=DbConnect.connection.prepareStatement("SELECT \"nickname\" FROM \"HumanTree\" WHERE \"owner\"='"+user.getName()+"'");
//                PreparedStatement preparedStatement1=DbConnect.connection.prepareStatement("DELETE FROM \"HumanTree\" WHERE \"nickname\"='?'");
//                Result objsName = Main.db.change("SELECT \"nickname\" FROM \"HumanTree\" WHERE \"owner\"='" + user.getName() + "'", "get");
                PreparedStatement preparedStatement=DbConnect.connection.prepareStatement("SELECT \"nickname\" FROM \"HumanTree\" WHERE \"owner\"=?");
                PreparedStatement preparedStatement1=null;
                preparedStatement.setString(1,user.getName());
                Result objsName=Main.db.change(preparedStatement,"get");
                while (objsName.getResultSet().next()) {
                    String name = objsName.getResultSet().getString("nickname");
                    if (!mytree.get().containsKey(name)){
//                        Main.db.change(String.format(deletePattern, name), "set");
                        preparedStatement1=DbConnect.connection.prepareStatement("DELETE FROM \"HumanTree\" WHERE \"nickname\"=?");
                        preparedStatement1.setString(1,name);
                        Main.db.change(preparedStatement1,"set");}
                }
//                StringBuilder req = new StringBuilder();
                if (!mytree.get().isEmpty()) {
                    mytree.get().entrySet().stream().filter(c -> c.getValue().getOwner().equals(user.getName())).forEach(
                            c -> {
                                try {
//                                    if (Main.db.dbHas("\"nickname\"='" + c.getKey() + "'", "\"HumanTree\"")){
                                    if (Main.db.dbHas(4, c.getKey())){
                                        PreparedStatement preparedStatement2=DbConnect.connection.prepareStatement(" UPDATE \"HumanTree\" SET " +
                                                "\"nickname\"=?, \"surname\"=?, \"date\"=?, \"Location\"=?, \"age\"=? WHERE \"nickname\"=?");
                                        preparedStatement2.setString(1,c.getValue().getNickname());
                                        preparedStatement2.setString(2,c.getValue().getSurName());
                                        preparedStatement2.setString(3,c.getValue().getDate().toString());
                                        Array array=DbConnect.connection.createArrayOf("integer",new Object[]{c.getValue().getLocation().getX(),c.getValue().getLocation().getY()});
                                        preparedStatement2.setArray(4,array);
                                        preparedStatement2.setInt(5,c.getValue().getAge());
                                        preparedStatement2.setString(6,c.getValue().getNickname());
                                        Main.db.change(preparedStatement2,"set");}
//                                        req.append(String.format(updatePattern, c.getValue().getStringForDBWithVarNames(), c.getValue().getNickname()));
                                    else{
                                        PreparedStatement preparedStatement3= DbConnect.connection.prepareStatement(" INSERT INTO \"HumanTree\" VALUES(?,?,?,?,?,?)");
                                        preparedStatement3.setString(1,c.getValue().getNickname());
                                        preparedStatement3.setString(2,c.getValue().getSurName());
                                        preparedStatement3.setString(3,c.getValue().getOwner());
                                        preparedStatement3.setInt(4,c.getValue().getAge());
                                        Array array=DbConnect.connection.createArrayOf("integer",new Object[]{c.getValue().getLocation().getX(),c.getValue().getLocation().getY()});
                                        preparedStatement3.setString(5,c.getValue().getDate().toString());
                                        preparedStatement3.setArray(6, array);
                                        Main.db.change(preparedStatement3,"set");
//                                        req.append(String.format(insertPattern, c.getValue().getStringForDB()));
                                    }
                                }catch (SQLException e){
                                    System.out.println("Database die");
                                }
                            }
                    );
//                    Main.db.change(req.toString(), "set");
                }
                return "Added uiiiiiii probably = /";
            } catch (SQLException e) {
                return "Can't save collection";
            }

        }
//            try {
//               return FileOper.XmlSaveFile(HumansTree, out);
//                return "Коллекция сохранена";
//            }
//            catch(IOException e){
//                System.out.println("Клиенг обнаглел, отлючился");
//            }

    /**
     *<p>Выводит информацию о коллекции</p>
     */
    public  String info(){
//        try {
            return "Type of collection - TreeMap. " + "Initialization Date - " + date.toString() + ". Amount of elements - " + HumansTree.size() + ". The value of collection objects is determined by the alphabet.";
//        }
//        catch(IOException e){
//            System.out.println("Клиенг обнаглел, отлючился");
//        }
    }
    /**
     * <p>Удаляет элемент из коллекции по его ключу</p>
     * @param key String
     * @throws NullPointerException
     */
    public  String remove(String key,User user){
//        try {
            if (HumansTree.containsKey(key)) {
                if(HumansTree.get(key).getOwner().equals(user.getName()))
                HumansTree.remove(key);
                else return "YOU SHALL NOT PASS";
                return "We do it, he is dieing";
            } else
                return "I can't find this element";
//        }
//        catch (NullPointerException|StringIndexOutOfBoundsException e){
//            System.out.println("Неверно введены данные");
//        }
//        catch(IOException e){
//            System.out.println("Клиенг обнаглел, отлючился");
//        }
    }
    /**
     * <p>Добавляет элемент в коллекцию</p>
     * @param human Human
     * @throws NullPointerException
     */
    public String insert(Human human,User user){
//        try {
            human.setOwner(user.getName());
            if (!HumansTree.containsKey(human.getNickname())) {
                HumansTree.put(human.getNickname(), human);
                return "Another one in this terrible world";
            } else {
                return "This key already exists";
            }
//        }
//        catch (NullPointerException|StringIndexOutOfBoundsException|IllegalArgumentException e){
//            System.out.println("Неверный ввод");
//        }
//        catch(IOException e){
//            System.out.println("Клиенг обнаглел, отлючился");
//        }
    }
    /**
     * <p>Добавляет новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции, или же,добавляет новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции</p>
     * @param human Human
     * @param comand String
     * @throws NullPointerException,IllegalArgumentException
     */
    public String add_if(Human human,String comand,User user){
//        try {
        human.setOwner(user.getName());
        if ("add_if_max".equals(comand)&&!HumansTree.isEmpty()) {
            if (human.compareTo(HumansTree.entrySet().stream().max((o1, o2) -> {if (o1.getValue().getNickname().toLowerCase().compareTo(o2.getValue().getNickname().toLowerCase())<0) return 1;
            else if (o1.getValue().getNickname().toLowerCase().compareTo(o2.getValue().getNickname().toLowerCase())>0) return -1;
            else if (o1.getValue().getSurName().toLowerCase().compareTo(o2.getValue().getSurName().toLowerCase())<0) return 1;
            else if (o1.getValue().getSurName().toLowerCase().compareTo(o2.getValue().getSurName().toLowerCase())>0) return -1;
                return 0;} ).get().getValue()) == 1) {
                HumansTree.put(human.getNickname(), human);
                return "Another one in this terrible world";
            }
            else return "Not another one in this terrible world";
        }
        if ("add_if_min".equals(comand)&&!HumansTree.isEmpty()) {
            if (human.compareTo(HumansTree.entrySet().stream().max((o1, o2) -> {if (o1.getValue().getNickname().toLowerCase().compareTo(o2.getValue().getNickname().toLowerCase())<0) return 1;
            else if (o1.getValue().getNickname().toLowerCase().compareTo(o2.getValue().getNickname().toLowerCase())>0) return -1;
            else if (o1.getValue().getSurName().toLowerCase().compareTo(o2.getValue().getSurName().toLowerCase())<0) return 1;
            else if (o1.getValue().getSurName().toLowerCase().compareTo(o2.getValue().getSurName().toLowerCase())>0) return -1;
                return 0;} ).get().getValue()) == -1) {
                HumansTree.put(human.getNickname(), human);
                return "Another one in this terrible world";
            }
            else return "Not another one in this terrible world";
        }
        else return "We can't do it, maybe collection is empty";
//            if ("add_if_max".equals(comand)) {
//                if (human.compareTo(Collections.max(HumansTree.values())) == 1) {
//                    HumansTree.put(human.getNickname(), human);
//                    return "Элемент успешно добавлен";
//                }
//                else return "Элемент не добавлен";
//            }
//            if ("add_if_min".equals(comand)) {
//                if (human.compareTo(Collections.min(HumansTree.values())) == -1) {
//                    HumansTree.put(human.getNickname(), human);
//                   return "Элемент успешно добавлен";
//                }
//                else return "Элемент не добавлен";
//            }
//            else return "Элемент не добавленннн";
        }
//        catch(NullPointerException|IllegalArgumentException e){
//            System.out.println("Неверный ввод");
//        }
//            catch(IOException e){
//                System.out.println("Клиенг обнаглел, отлючился");
//            }
}
