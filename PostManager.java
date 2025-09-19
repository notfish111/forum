package forum;

import java.io.*;
import java.util.*;

public class PostManager {
    //
    public Map<String, List<Post>> userPostHashMap = new HashMap<>();
    public HashMap<Integer, Post> postHashMap = new HashMap<>();
    public List<Post> userPosts;

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
        //
        userPosts = userPostHashMap.computeIfAbsent(user.getUser_id(),k -> new ArrayList<>());
        userPosts.add(post);

        postHashMap.put(post.getPost_id(), post);

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
                    //
                    postHashMap.put(post.getPost_id(), post);
                    userPosts = userPostHashMap.computeIfAbsent(post.getUser_id(),k -> new ArrayList<>());
                    userPosts.add(post);

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
    public Post getPost(int post_id){ return postHashMap.get(post_id);}
    //获取所有帖子
    public Map<Integer, Post> getAllPosts() {
        return new HashMap<>(postHashMap); // 返回副本以避免外部修改
    }

    //删除帖子要判断帖子的user_id是否与用户id相同


    //打印帖子
    //粗略
    public void printOnePostAbs(Post post){
        System.out.println("==========");
        System.out.println("<" + post.getTitle() + ">");
        System.out.println("id:" + post.getPost_id());
        System.out.println("[作者:" + post.getUser_id() + "]");
        System.out.println("观看量:" + post.getWatch_count());
        System.out.println();
    }
    //详细
    public void printOnePostAll(Post post){
        System.out.println("==========");
        System.out.println("<" + post.getTitle() + ">");
        System.out.println("[作者:" + post.getUser_id() + "]");
        System.out.println("   " + post.getContent());
        System.out.println("----------");
        System.out.println("创建时间:" + post.getCreate_date());
        System.out.println("最后修改时间:" + post.getUpdate_date());
        System.out.println("----------");
        System.out.println("观看量:" + post.getWatch_count());
        System.out.println("点赞数:" + post.getLike_count());
        System.out.println("评论数:" + post.getComment_count());
        System.out.println("==========");
        //评论展示
    }
    //指定用户
    public void printSpecialPost(String user_id){
        List<Post> post_list = userPostHashMap.get(user_id);
        for (Post post : post_list ){
            printOnePostAbs(post);
        }
    }

    //获取帖子数量
    public int printPostCount(){
        //System.out.println("论坛共有" + User.user_num + "名用户");
        return Post.post_num;
    }
    //打印所有帖子
    //用ansi优化
    public void printAllPostAbs(Scanner scanner){
        int oncePrintNum = 0;
        int paperNum = 1;
        String choice;
        for (Post post : postHashMap.values()){
            oncePrintNum++;
            printOnePostAbs(post);
            if (oncePrintNum > 4){
                kong(scanner);
                System.out.println("<N---" + "第" + paperNum + "页" + "---M");
                choice = scanner.nextLine();

                switch (choice){
                    //case "N":
                    //回退，用栈？

                    case "M":
                        continue;
                    case "exit":
                        break;
                }
            }
        }

    }


    //列举用户帖子

    public void kong(Scanner scanner){
        if (scanner.hasNextLine()) {
            scanner.nextLine(); // 清空缓冲区的一行（可能是换行符）
        }
    }
}
