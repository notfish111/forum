package forum;

import java.time.LocalDateTime;

public class Post {
    static int post_num = 0;

    private int post_id;
    private String user_id;
    private String title;
    private String content;
    //不可二次修改
    private String create_date;
    private String update_date;
    private int watch_count = 0;
    private int like_count = 0;
    private int comment_count = 0;
    //不可减小
    private int change_count = 0;

    public int getPost_id() {return post_id;}

    public void setPost_id(int post_id) {this.post_id = post_id;}

    public String getUser_id() {return user_id;}

    public void setUser_id(String user_id) {this.user_id = user_id;}

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public String getContent() {return content;}

    public void setContent(String content) {this.content = content;}

    public String getCreate_date() {return create_date;}

    public void setCreate_date(String create_date) {
        if (change_count == 0){
            this.create_date = create_date;
        }
    }

    //用于读取文件时使用
    private void loadCreate_date(String create_date){
        this.create_date = create_date;
    }

    public String getUpdate_date() {return update_date;}

    public void setUpdate_date(String update_date) {this.update_date = update_date;}

    public int getWatch_count() {return watch_count;}

    public void setWatch_count(int watch_count) {this.watch_count = watch_count;}

    public int getLike_count() {return like_count;}

    public void setLike_count(int like_count) {this.like_count = like_count;}

    public int getComment_count() {return comment_count;}

    public void setComment_count(int comment_count) {this.comment_count = comment_count;}

    public int getChange_count() {return change_count;}

    public void increaseChange_count() {
        change_count++;
    }

    //读取文件时用
    private void loadChange_count(int change_count){
        this.change_count = change_count;
    }

    public Post(int post_id, String user_id) {
        this.post_id = post_id;
        this.user_id = user_id;
        this.content = null;
        this.title = null;
        this.create_date = LocalDateTime.now().toString();
        this.update_date = LocalDateTime.now().toString();
        this.change_count++;
        post_num++;
    }

    public Post(){
        //仅仅用于加载
    }

    public String p_serialize(){
        return post_id + "|" + title + "|" + user_id + "|" + create_date + "|"+ update_date + "|" + change_count + "|" +content + "|" + watch_count + "|" + like_count + "|" +comment_count;
    }

    public static Post deserialize(String data){
        String[] parts = data.split("\\|");

        if (parts.length != 10) {
            throw new IllegalArgumentException("无效的帖子数据格式: " + data);
        }

        Post post = new Post();
        post.setPost_id(Integer.parseInt(parts[0]));
        post.setUser_id(parts[2]);
        post.setTitle(parts[1]);
        post.loadCreate_date(parts[3]);
        post.setUpdate_date(parts[4]);
        post.loadChange_count(Integer.parseInt(parts[5]));
        post.setContent(parts[6]);
        post.setWatch_count(Integer.parseInt(parts[7]));
        post.setLike_count(Integer.parseInt(parts[8]));
        post.setComment_count(Integer.parseInt(parts[9]));

        return post;
    }
}
