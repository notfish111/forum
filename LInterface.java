package forum;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class LInterface {
    public void loginInterface(User user, Scanner scanner, String userChoice, PostManager postManager, UserManager userManager, Map<String, List<Post>> userPostHashMap){

        boolean running = true;
        while (running) {
            String password = null;

            clearConsole();
            UserSelfInfGet(user);
            System.out.println("1:创建帖子");
            System.out.println("2:查看自己的所有帖子");
            System.out.println("3:修改密码");
            System.out.println("4:登出");
            System.out.println("5:注销账号");
            System.out.println("请选择你的操作:");

            //kong(scanner);
            userChoice = scanner.next();
            switch (userChoice){
                case "1":
                    postManager.createPost(user, scanner);
                    continue;
                case "2":
                    printSelfPost(user.getUser_id(),userPostHashMap,postManager);
                    System.out.println("如果要查看某一帖子，请输入PostID");
                    String choice = scanner.nextLine();
                    //if (postManager.getPost(Integer.parseInt(choice)) == userPostHashMap.get(user.getUser_id()).get(Integer.parseInt(choice))){}
                    if (userPostHashMap.get(user.getUser_id()).contains(postManager.getPost(Integer.parseInt(choice)))){
                        clearConsole();
                        postManager.printOnePostAll(postManager.getPost(Integer.parseInt(choice)));
                        System.out.println("==========");
                    }
                    System.out.println("返回请输入任意字符");
                    choice = scanner.nextLine();

                    //后期增加对post的操作

                case "3":
                    System.out.println("请输入您当前的密码:");
                    password = scanner.nextLine();
                    if (password.equals(user.getPassword())){
                        System.out.println("请输入您要修改的密码:");
                        password = scanner.nextLine();
                        user.setPassword(password);
                        userManager.saveToFile();
                        System.out.println("修改成功");
                    }
                case "4":
                    running = false;
                    clearConsole();
                case "5":
                    System.out.println("请输入您当前的密码以确定注销账户:");
                    password = scanner.nextLine();
                    if (userManager.deleteUser(user.getUser_id(),password)){
                        userManager.userHashMap.remove(user.getUser_id(),user);
                        User.user_num --;
                        userManager.saveToFile();
                        System.out.println("注销成功！");
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        clearConsole();
                        break;
                    }else {
                        System.out.println("注销失败！");
                        clearConsole();
                        continue;
                    }
                default:
                    System.out.println("无效选择，请重新输入");
            }
        }

    }








    private void kong(Scanner scanner){
        if (scanner.hasNextLine()) {
            scanner.nextLine(); // 清空缓冲区的一行（可能是换行符）
        }
    }

    private void UserSelfInfGet(User user){
        System.out.println("=====个人主页=====");
        System.out.println("用户id：" + user.getUser_id());
        System.out.println("用户等级：" + user.getLevel());
        System.out.println("粉丝数量：" + user.getFans());
        System.out.println("关注数量：" + user.getFollow());
        System.out.println("帖子数量：" + user.getPost_count());
        System.out.println("=================");
    }

    public static void clearConsole() {
        try {
            // 检查操作系统
            if (System.getProperty("os.name").contains("Windows")) {
                // Windows系统
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Unix/Linux/macOS系统
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // 如果上述方法失败，打印多个空行作为后备方案
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }

    public void printSelfPost(String user_id, Map<String, List<Post>> userPostHashMap, PostManager postManager){
        List<Post> post_list = userPostHashMap.get(user_id);
        for (Post post : post_list ){
            postManager.printOnePostAbs(post);
        }
    }
}
