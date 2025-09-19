package forum;

import java.util.Scanner;

public class LInterface {
    public void loginInterface(User user, Scanner scanner,String userChoice, PostManager postManager, UserManager userManager){

        while (true) {
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
                case "2":

                case "3":

                case "4":

                case "5":

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
}
