package forum;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserManager userManager = new UserManager();
        PostManager postManager = new PostManager();
        LInterface lInterface = new LInterface();
        userManager.loadFromFile();
        postManager.loadFromFile();

        String userChoice = "NOIPUT";

        while(!(userChoice.equals("exit"))){
            System.out.println("欢迎进入论坛！");
            System.out.println("1:登录");
            System.out.println("2:注册");
            System.out.println("以下是业务列表，请输入你想选择的业务");

            userChoice = scanner.next();
            switch (userChoice){
                case "1":
                    String input1,input2;
                    userManager.kong(scanner);
                    System.out.println("请输入你的账号:");
                    input1 = scanner.nextLine();
                    System.out.println("请输入你的密码:");
                    input2 = scanner.nextLine();
                    if (userManager.login(input1,input2)){
                        System.out.println("登录成功！");
                        lInterface.loginInterface(userManager.getUser(input1), scanner, userChoice, postManager, userManager, postManager.userPostHashMap);
                        //进入下一个场景


                    }else {
                        System.out.println("登录失败！");
                    }
                    continue;
                case "2":
                    userManager.createUser(scanner);
                    continue;
                case "exit":
                    postManager.saveToFile();
                    userManager.saveToFile();
                    break;
                default:
                    System.out.println("无效选择，请重新输入");

            }
            
        }

    }

    //待测试
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
