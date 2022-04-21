package cn.xusong.vaehome.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.xusong.vaehome.dao.UserDao;

//用户模块业务类
public class UserService {
	private UserDao userDao=new UserDao();
	//用户登录
	public boolean login(String username,String password) {
		boolean bool=userDao.login(username, password);
		return bool;
	}
	
	//注册新用户
	public int register(String username,String password) {
		int i=userDao.register(username, password);
		return i;
	}
	//为新用户添加热度，经验
	public int addHotExp(String username) {
		int id=userDao.addHotExp(username);
		return id;
	}
	//新用户完善资料
	public int updateUser(int user_id,String sex,String address,String birthday,String email,String phonenum) {
		int i=userDao.updateUser(user_id, sex, address, birthday, email, phonenum);
		return i;
	}
	//查询用户经验
/*	public String searchSummary(String username) {
		String exp=userDao.searchSummary(username);
		return exp;
	}*/
	//根据用户名查询用户id,用户名
	public Map searchUser(String username) {
		Map map=new HashMap();
		map=userDao.searchUser(username);
		return map;
	}
	//查询用户名
/*	public String searchUsername(String username) {
		String Username=userDao.searchUsername(username);
		return Username;
	}*/
	//查询用户关注数
	/*public int searchCareNum(int id) {
		int care_num=userDao.searchCareNum(id);
		return care_num;
	}
	//查询用户粉丝数
	public int searchFanNum(int id) {
		int fan_num=userDao.searchFanNum(id);
		return fan_num;
	}*/
	//查询用户关注数、粉丝数
	public Map searchNum(int id) {
		Map map=new HashMap();
		map=userDao.searchNum(id);
		return map;
	}
	//查询帖子列表
	public ArrayList searchAllPost(){
		ArrayList list=userDao.searchAllPost();
		return list;
	}
	//按照用户热度加载所有用户
	public ArrayList searchAllUser(){
		ArrayList list=userDao.searchAllUser();
		return list;
	}
	//查询用户点赞帖子
	public ArrayList searchLikePost(int id) {
		ArrayList lplist=userDao.searchLikePost(id);
		return lplist;
	}
	public ArrayList searchPostByCircle(int circle_id) {
		ArrayList list=userDao.searchPostByCircle(circle_id);
		return list;
	}
/*	public int searchFriendID(int id) {
		int friendID=userDao.searchFriendID(id);
		return friendID;
	}*/
	//发帖
	public int addPost(String post_detail,int circle_id,int user_id) {
		int i=userDao.addPost(user_id, post_detail, circle_id);
		return i;
	}
	//评论帖子
	public int addComment(String comment_detail,int post_id,int user_id) {
		int i=userDao.addComment(comment_detail, post_id, user_id);
		return i;
	}
	public int addLike(int post_id,int user_id) {
		int i=userDao.addLike(post_id, user_id);
		return i;
	}
	//读取当前帖子的评论列表
	public ArrayList loadCommentList(int post_id) {
		ArrayList list=userDao.loadCommentList(post_id);
		return list;
	}
	//读取当前帖子
	public ArrayList loadthisPost(int post_id) {
		ArrayList list=userDao.loadthisPost(post_id);
		return list;
	}
	//添加用户关注
	public int addCare(int user_id,int care_id) {
		int i=userDao.addCare(user_id, care_id);
		return i;
	}
	public ArrayList searchPostByUser(int user_id) {
		ArrayList list=userDao.searchPostByUser(user_id);
		return list;
	}
	public ArrayList searchPostByHot() {
		ArrayList list=userDao.searchPostByHot();
		return list;
	}
	public ArrayList searchPostByCare(int user_id) {
		ArrayList list=userDao.searchPostByCare(user_id);
		return list;
	}
	//用户消息列表
	public ArrayList searchMessage(int user_id) {
		ArrayList list=userDao.searchMessage(user_id);
		return list;
	}
}
