package cn.xusong.vaehome.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.jfinal.plugin.activerecord.Db;

public class UserDao {
	// 登录方法
	public boolean login(String username, String password) {
		// 获得SQL语句
		String sql = Db.getSql("user.login");
		// 执行SQL语句
		Long count=Db.queryLong(sql, username, password, username, password, username, password);
		// 转换结果
		return count == 1 ? true : false;//Ctrl+Shift+F 格式化代码
	}
	
	//注册新用户
	public int register(String username,String password) {
		String sql=Db.getSql("user.register");
		int i=Db.update(sql,username,password);
		return i;
	}
	//为新用户添加经验，热度
	public int addHotExp(String username) {
		int id=Db.queryInt(Db.getSql("user.searchUserID"),username,username,username);
		Db.update(Db.getSql("user.addExp"),id);
		Db.update(Db.getSql("user.addHot"),id);
		return id;
	}
	//新用户完善资料
	public int updateUser(int user_id,String sex,String address,String birthday,String email,String phonenum) {
		String sql=Db.getSql("user.updateUser");
		int i=Db.update(sql,sex,address,birthday,email,phonenum,user_id);
		return i;
	}
	//返回用户经验值
	/*public String searchSummary(String username) {
		String sql=Db.getSql("user.searchSummary");
		String exp=Db.queryStr(sql,username,username,username);
		return exp;
		}*/
	public Map searchUser(String username) {
		String sql=Db.getSql("user.searchUserID");
		int id=Db.queryInt(sql,username,username,username);
		String sql1=Db.getSql("user.searchUsername");
		String Username=Db.queryStr(sql1,username,username,username);
		Map map=new HashMap();
		map.put("id",id);
		map.put("Username",Username);
		return map;
	}
	//查找用户名
	/*public String searchUsername(String username) {
		String sql=Db.getSql("user.searchUsername");
		String Username=Db.queryStr(sql,username,username,username);
		return Username;
	}*/
	public Map searchNum(int id) {
		//查询用户经验值
		String sql=Db.getSql("user.searchSummary");
		int exp=Db.queryInt(sql,id);
		//查询用户关注数
		String sql1=Db.getSql("user.searchCareNum");
		int care_num=Db.queryInt(sql1,id);
		//查询用户粉丝数
		String sql2=Db.getSql("user.searchFanNum");
		int fan_num=Db.queryInt(sql2,id);
		String sql3=Db.getSql("user.searchHot");
		int hot=Db.queryInt(sql3,id);
		String sql4=Db.getSql("user.searchFriendID");
		int friendID=Db.queryInt(sql4,id,id);
		String photo_path=Db.queryStr(Db.getSql("user.searchPhoto"),id);
		Map map=new HashMap();
		map.put("exp", exp);
		map.put("care_num", care_num);
		map.put("fan_num", fan_num);
		map.put("hot", hot);
		map.put("friendID", friendID);
		map.put("photo_path", photo_path);
		return map;
	}
	public ArrayList searchAllPost() {
		String sql=Db.getSql("user.searchAllPost");
		ArrayList list=(ArrayList) Db.find(sql);
		return list;
	}
	//按照用户热度加载所有用户
	public ArrayList searchAllUser() {
		String sql=Db.getSql("user.searchAllUser");
		ArrayList list=(ArrayList) Db.find(sql);
		return list;
	}
/*	//查询用户点赞的帖子
	public ArrayList searchLikePost(int id) {
		String sql=Db.getSql("user.getpostID");
		ArrayList plist=(ArrayList) Db.find(sql,id);
		String sql1=Db.getSql("user.getPost");
		ArrayList lplist=(ArrayList) Db.find(sql1,plist);
		return lplist;
	}*/
	//查询用户点赞的帖子
		public ArrayList searchLikePost(int id) {
			String sql=Db.getSql("user.searchLikePost");
			ArrayList lplist=(ArrayList) Db.find(sql,id);
			return lplist;
		}
		//按圈子查询帖子
		public ArrayList searchPostByCircle(int circle_id) {
			String sql=Db.getSql("user.searchPostByCircle");
			ArrayList list=(ArrayList) Db.find(sql,circle_id);
			return list;
		}
	/*//返回用户喜欢的帖子ID
	public ArrayList getpostID(int id) {
		String sql=Db.getSql("user.getpostID");
		ArrayList plist=(ArrayList) Db.find(sql,id);
		return plist;
		}*/
		
		
	/*	//查询用户互相关注的人ID
		public int searchFriendID(int id) {
			String sql=Db.getSql("user.searchFriendID");
			int friendID=Db.queryInt(sql,id,id);
			return friendID;
		}*/
		//发帖
		public int addPost(int user_id,String post_detail,int circle_id) {	
			//插入帖子
			String sql=Db.getSql("user.insertPost");
			int i=Db.update(sql,user_id,post_detail,circle_id);
			//当前用户经验值加10
			Db.update(Db.getSql("user.updateExp"),user_id);
			return i;
		}
		//评论帖子
		public int addComment(String comment_detail,int post_id,int user_id) {
			//根据帖子id查找作者用户id,传入updateHot方法,作者热度加5
			String sql1=Db.getSql("user.searchWriterID");
			int writerid=Db.queryInt(sql1,post_id);
			//执行插入、删除等没有返回值的方法时使用update
			Db.update(Db.getSql("user.updateHot"),writerid);
			//作者的消息提示+1
			String message="有人评论了你的帖子";
			Db.update(Db.getSql("user.addMessage"),writerid,message);
			//插入评论
			String sql2=Db.getSql("user.insertComment");
			int i=Db.update(sql2,post_id,user_id,comment_detail);
			//帖子评论数加1
			Db.update(Db.getSql("user.updateCommentNum"),post_id);
			//帖子热度加5
			Db.update(Db.getSql("user.updatePostHot"),post_id);
			//当前用户经验值加10
			Db.update(Db.getSql("user.updateExp"),user_id);
			return i;
		}
		//点赞帖子
		public int addLike(int post_id,int user_id) {
			//根据帖子id查找作者用户id,传入updateHot方法,作者热度加5
			String sql1=Db.getSql("user.searchWriterID");
			int writerid=Db.queryInt(sql1,post_id);
			//执行插入、删除等没有返回值的方法时使用update
			Db.update(Db.getSql("user.updateHot"),writerid);
			//作者消息列表
			String message="有人点赞了你的帖子";
			Db.update(Db.getSql("user.addMessage"),writerid,message);
			//插入Like表
			String sql2=Db.getSql("user.insertLike");
			int i=Db.update(sql2,user_id,post_id);
			//帖子点赞数加1
			Db.update(Db.getSql("user.updateCommentNum"),post_id);
			//帖子热度加5
			Db.update(Db.getSql("user.updatePostHot"),post_id);
			//当前用户经验值加10
			Db.update(Db.getSql("user.updateExp"),user_id);
			return i;
		}
		//读取当前帖子的评论列表
		public ArrayList loadCommentList(int post_id) {
			String sql=Db.getSql("user.loadCommentList");
			ArrayList list=(ArrayList) Db.find(sql,post_id);
			return list;
		}
		//读取当前帖子
		public ArrayList loadthisPost(int post_id) {
			String sql=Db.getSql("user.loadthisPost");
			ArrayList list=(ArrayList) Db.find(sql,post_id);
			return list;
		}
		//添加关注
		public int addCare(int user_id,int care_id) {
			String sql=Db.getSql("user.addCare");
			int i =Db.update(sql, user_id,care_id);
			String message="有人关注你啦";
			Db.update(Db.getSql("user.addMessage"),care_id,message);
			return i;
		}
		//查找用户中心帖子
		public ArrayList searchPostByUser(int user_id) {
			String sql=Db.getSql("user.searchPostByUser");
			ArrayList list=(ArrayList) Db.find(sql,user_id);
			return list;
		}
		//按热度值给帖子排序
		public ArrayList searchPostByHot(){
			String sql=Db.getSql("user.searchPostByHot");
			ArrayList list=(ArrayList) Db.find(sql);
			return list;
		}
		//用户关注的人发表的帖子
		public ArrayList searchPostByCare(int user_id) {
			String sql=Db.getSql("user.searchPostByCare");
			ArrayList list=(ArrayList)Db.find(sql,user_id);
			return list;
		}
		//查找用户消息列表
		public ArrayList searchMessage(int user_id) {
			String sql=Db.getSql("user.searchMessage");
			ArrayList list=(ArrayList)Db.find(sql,user_id);
			return list;
		}
		
}
