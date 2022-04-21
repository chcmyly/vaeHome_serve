package cn.xusong.vaehome.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
//用户模块网络类
public class UserController extends Controller{
   private UserService userService=new UserService();
	//登录方法
	public void login() {
		//获得移动端上传的数据
		String username=getPara("username");  //用户名
		String password=getPara("password");  //密码
		Cache redis=Redis.use("redis");
		//先到redis中查询是否存在登录信息
		boolean hasKey=redis.exists(username);
		if(hasKey) {
				boolean isLogin=redis.hget(username, "isLogin");		
					renderJson("result",isLogin);
					return;//结束方法执行
		}
	
		
		boolean bool=userService.login(username, password);
		if(bool) {
			
			HashMap map=new HashMap();
			map.put("isLogin", true);
			redis.hmset(username, map);//保存登录信息
			redis.expire(username, 24*60*60);//登录信息保存24小时			
			renderJson("result",true);
		}
		else {
			renderJson("result",false);
		}

	}
	//注册新用户
	public void register() {
		//获得上传的注册信息
		String username=getPara("username");
		String password=getPara("password");
		int i =userService.register(username, password);
		boolean bool=i==1?true:false;
	    //注册成功就算登录成功，在redis中写入登录状态
		if(bool) {
			Cache redis=Redis.use("redis");
			HashMap map=new HashMap();
			map.put("isLogin", true);
			redis.hmset(username, map);//保存登录信息
			redis.expire(username, 24*60*60);//登录信息保存24小时			
		}
		renderJson("result",bool);
	}
	//为新用户添加经验热度
	public void addHotExp() {
		String username=getPara("username");
		int id=userService.addHotExp(username);
		renderJson(id);
	}
	public void updateUser() {
		int user_id=getParaToInt("user_id");
		String sex=getPara("sex");
		String address=getPara("address");
		String birthday=getPara("birthday");
		String email=getPara("email");
		String phonenum=getPara("phonenum");
		int i=userService.updateUser(user_id, sex, address, birthday, email, phonenum);
		renderJson(i);
	}
	
	//检测用户是否已经登录
	public void isLogin() {
		String username=getPara("username");
		Cache redis=Redis.use("redis");
		boolean hasKey=redis.exists(username);
		if(hasKey) {
			boolean isLogin=redis.hget(username, "isLogin");
			renderJson("result",isLogin);
		}
		else {
			renderJson("result",false);
		}
	}
/*	public void searchSummary() {
		String username=getPara("username");
		String exp=userService.searchSummary(username);
		renderJson(exp);
	}*/
	//返回用户ID
	public void searchUser() {
		String username=getPara("username");
		Map map= new HashMap();
		map = userService.searchUser(username);
		renderJson(map);	
	}
	//查询用户名，用于当用户用手机号登录时在localStoraryge中保存用户名
/*	public void searchUsername() {
		String username=getPara("username");
		String Username=userService.searchUsername(username);
		renderJson(Username);
	}*/
	//查询用户经验，粉丝数，关注数
	public void searchNum() {
		int id=getParaToInt("id");
		Map map =new HashMap();
		map=userService.searchNum(id);
		renderJson(map);		
	}
	//查询帖子列表
	public void searchAllPost(){
		ArrayList list=userService.searchAllPost();
		renderJson(list);
	}
	public void searchAllUser(){
		ArrayList list=userService.searchAllUser();
		renderJson(list);
	}
	//查询用户点赞的帖子
	public void searchLikePost() {
		int id=getParaToInt("id");
		ArrayList lplist=userService.searchLikePost(id);
		renderJson(lplist);
	}
	public void searchPostByCircle(){
		int circle_id=getParaToInt("circle_id");
		ArrayList list=userService.searchPostByCircle(circle_id);
		renderJson(list);
	}
/*	public void searchFriendID() {
		int id=getParaToInt("id");
		int friendID=userService.searchFriendID(id);
		renderJson(friendID);
	}*/
	//发帖
	public void addPost() {
		int user_id=getParaToInt("user_id");
		String post_detail=getPara("post_detail");
		int circle_id=getParaToInt("circle_id");
		int i=userService.addPost(post_detail, circle_id, user_id);
		renderJson(i);
	}
	//评论帖子
	public void addComment() {
		String comment_detail=getPara("comment_detail");
		int post_id=getParaToInt("post_id");
		int user_id=getParaToInt("user_id");
		int i=userService.addComment(comment_detail,post_id,user_id);
		
		renderJson(i);
	}
	//点赞帖子
	public void addLike() {
		int post_id=getParaToInt("post_id");
		int user_id=getParaToInt("user_id");
		int i=userService.addLike(post_id,user_id);
		renderJson(i);
	}
	//读取当前帖子的评论列表
	public void loadCommentList() {
		int post_id=getParaToInt("post_id");
		ArrayList list=userService.loadCommentList(post_id);
		renderJson(list);
	}
	//读取当前帖子
	public void loadthisPost() {
		int post_id=getParaToInt("post_id");
		ArrayList list=userService.loadthisPost(post_id);
		renderJson(list);
	}
	//添加用户关注
	public void addCare() {
		int user_id=getParaToInt("user_id");
		int care_id=getParaToInt("care_id");
		int i=userService.addCare(user_id, care_id);
		renderJson(i);
	}
	//查找用户个人中心帖子
	public void searchPostByUser() {
		int user_id=getParaToInt("user_id");
		ArrayList list=userService.searchPostByUser(user_id);
		renderJson(list);
	}
	public void searchPostByHot() {
		ArrayList list=userService.searchPostByHot();
		renderJson(list);
	}
	public void searchPostByCare() {
		int user_id=getParaToInt("user_id");
		ArrayList list=userService.searchPostByCare(user_id);
		renderJson(list);
	}
	public void searchMessage() {
		int user_id=getParaToInt("user_id");
		ArrayList list=userService.searchMessage(user_id);
		renderJson(list);
	}
}
