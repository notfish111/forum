package forum;

public class User {
    static int user_num = 0;

    private String user_id;
    private String password;
    //账户等级
    private int level;
    //粉丝数量
    private int fans = 0;
    //关注数量
    private int follow = 0;
    private int post_count = 0;

    //初始化问题
    //
    private Post[] post_list;
    private User[] fans_list;
    private User[] follow_list;

    public String getUser_id() {return user_id;}

    public void setUser_id(String user_id) {this.user_id = user_id;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public int getLevel() {return level;}

    public void setLevel(int level) {this.level = level;}

    public int getFans() {return fans;}

    public void setFans(int fans) {this.fans = fans;}

    public int getFollow() {return follow;}

    public void setFollow(int follow) {this.follow = follow;}

    public int getPost_count() {return post_count;}

    public void setPost_count(int post_count) {this.post_count = post_count;}

    public void increasePost_count(){
        this.post_count ++;
    }

    public void decreasePost_count(){
        this.post_count --;
    }

    public User(String user_id, String password) {
        this.user_id = user_id;
        this.password = password;
        user_num++;
    }

    public String serialize() {
        return user_id + "|" + password + "|" + level + "|" + fans + "|" + follow + "|" + post_count;
    }

    public static User deserialize(String data) {
        String[] parts = data.split("\\|"); // 使用转义符，因为|是正则表达式特殊字符
        if (parts.length != 6) {
            throw new IllegalArgumentException("无效的用户数据格式: " + data);
        }

        User user = new User(parts[0], parts[1]);
        user.setLevel(Integer.parseInt(parts[2]));
        user.setFans(Integer.parseInt(parts[3]));
        user.setFollow(Integer.parseInt(parts[4]));
        user.setPost_count(Integer.parseInt(parts[5]));

        return user;
    }
}
