package forum;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class PostManager {
    public HashMap<String, Post> postHashMap = new HashMap<>();
    private  static final String DATA2_FILE = "posts.txt";

    //创建帖子需要判断登录状态和获取id
    public void createPost(User user, Scanner scanner) {
        String title;
        String content;

        System.out.println("=====现在开始创建新帖子=====");

        if (scanner.hasNextLine()) {
            scanner.nextLine(); // 清空缓冲区的一行（可能是换行符）
        }

        Post post;
        while (true) {
            System.out.println("标题:");
            title = scanner.nextLine();
            if (title.isEmpty()) {
                System.out.println("标题不能为空，请重新输入");
                continue;
            }
            System.out.println("正文:(以“&”结束)");
            scanner.useDelimiter("&");
            content = scanner.next();
            if (content.isEmpty()) {
                System.out.println("标题不能为空，请重新输入");
                continue;
            } else {
                post = new Post(user.getUser_id(), title, content);
                System.out.println("创建成功！");
                break;
            }
        }
        postHashMap.put(user.getUser_id(),post);
        user.increasePost_count();
        saveToFile();
    }
    //查找帖子，自定义算法



    //保存文件
    public void saveToFile(){
        try(PrintWriter writer = new PrintWriter(new FileWriter(DATA2_FILE))){
            for (Post post : postHashMap.values()) {
                writer.println(post.p_serialize());
            }
            System.out.println("帖子数据已保存到 " + DATA2_FILE);
        }catch (IOException e) {
            System.err.println("保存失败: " + e.getMessage());
        }
    }

    //加载文件
    public void loadFromFile() {
        postHashMap.clear();
        File file = new File(DATA2_FILE);

        if (!file.exists()) {
            System.out.println("帖子数据文件不存在，将创建新文件");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(DATA2_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    Post post = Post.deserialize(line);
                    postHashMap.put(post.getUser_id(), post);
                } catch (IllegalArgumentException e) {
                    System.err.println("跳过无效的帖子数据行: " + line);
                }
            }
            System.out.println("从 " + DATA2_FILE + " 加载了 " + postHashMap.size() + " 个用户");
        } catch (IOException e) {
            System.err.println("加载失败: " + e.getMessage());
        }
    }

    //获取帖子

    //获取所有帖子

    //删除帖子要判断帖子的user_id是否与用户id相同

    //打印帖子

    //打印帖子数量

    //打印所有帖子
    //用ansi优化
}
