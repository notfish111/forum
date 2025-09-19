package forum;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserManager {
    public HashMap<String, User> userHashMap = new HashMap<>();
    private static final String DATA_FILE = "users.txt";

    //创建用户
    public void createUser(Scanner scanner){
        String user_id;
        System.out.println("现在开始创建账户！");

        if (scanner.hasNextLine()) {
            scanner.nextLine(); // 清空缓冲区的一行（可能是换行符）
        }

        while (true) {
            System.out.println("请输入你的账号:");
            user_id = scanner.nextLine();
            if (user_id.isEmpty()){
                System.out.println("账号不能为空，请重新输入");
                continue;
            }
            if (findUserExit(user_id)) {
                System.out.println("存在同名账户，请重新开始创建账户");
            }else {
                break;
            }
        }
        System.out.println("请输入你的密码:");
        String password = scanner.nextLine();
        User user = new User(user_id, password);
        userHashMap.put(user.getUser_id(),user);
        System.out.println("用户" + user.getUser_id() + "已注册");
        saveToFile();

    }

    //查找是否存在
    public boolean findUserExit(String user_id){
        User findUser = userHashMap.get(user_id);
        if (findUser != null){
            System.out.println("找到用户: " + user_id);
            return true;
        }else {
            System.out.println("未找到用户: " + user_id);
            return false;
        }
    }

    //保存文件
    public void saveToFile(){
        try(PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE))){
            for (User user : userHashMap.values()) {
                writer.println(user.serialize());
            }
            System.out.println("用户数据已保存到 " + DATA_FILE);
        }catch (IOException e) {
            System.err.println("保存失败: " + e.getMessage());
        }
    }

    //加载文件
    public void loadFromFile() {
        userHashMap.clear();
        File file = new File(DATA_FILE);

        if (!file.exists()) {
            System.out.println("用户数据文件不存在，将创建新文件");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    User user = User.deserialize(line);
                    userHashMap.put(user.getUser_id(), user);
                } catch (IllegalArgumentException e) {
                    System.err.println("跳过无效的用户数据行: " + line);
                }
            }
            System.out.println("从 " + DATA_FILE + " 加载了 " + userHashMap.size() + " 个用户");
        } catch (IOException e) {
            System.err.println("加载失败: " + e.getMessage());
        }
    }

    // 获取用户
    public User getUser(String user_id) {
        return userHashMap.get(user_id);
    }

    // 获取所有用户
    public Map<String, User> getAllUsers() {
        return new HashMap<>(userHashMap); // 返回副本以避免外部修改
    }

    //删除用户
    //为什么这里不能和下面一样
    public boolean deleteUser(String user_id,String password){
        if(login(user_id,password)){
            userHashMap.remove(user_id,getUser(user_id));
            saveToFile();
            return true;
        }else {
            return false;
        }
    }
    //登陆后删除
    public boolean deleteUserLogin(String user_id){
        userHashMap.remove(user_id,getUser((user_id)));
        saveToFile();
        if (getUser(user_id) != null){
            return true;
        }else {
            return false;
        }
    }

    //登录
    public boolean login(String user_id, String password){
        User user = getUser(user_id);
        if (user != null && user.getPassword().equals(password)){

            return true;
        }else {
            return false;
        }
    }

    //登录状态获取




    //打印信息
    public void printOneUser(String user_id){
        User user = getUser(user_id);
        if(user != null){
            System.out.println("===========");
            System.out.println("用户ID: " + user.getUser_id());
            System.out.println("等级: " + user.getLevel());
            System.out.println("粉丝数: " + user.getFans());
            System.out.println("关注数: " + user.getFollow());
            System.out.println("帖子数: " + user.getPost_count());
            System.out.println("===========");
        }else {
            System.out.println("用户不存在！");
        }

    }

    //打印用户数量

    public int printUserCount(){
        //System.out.println("论坛共有" + User.user_num + "名用户");
        return User.user_num;
    }

    //打印所有信息
    //用ansi优化

    public void kong(Scanner scanner){
        if (scanner.hasNextLine()) {
            scanner.nextLine(); // 清空缓冲区的一行（可能是换行符）
        }
    }
}
