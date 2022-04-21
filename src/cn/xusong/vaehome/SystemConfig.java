package cn.xusong.vaehome;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.redis.RedisPlugin;
import com.jfinal.template.Engine;

import cn.xusong.vaehome.user.UserController;

public class SystemConfig extends JFinalConfig {

	@Override
	public void configConstant(Constants constants) {
		constants.setDevMode(true);//以开发模式运行程序，把程序处理的各种日志都输出出来，便于调试
		
		
	}

	@Override
	public void configEngine(Engine arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configHandler(Handlers handlers) {
		handlers.add(new AjaxHandler());  //添加Handler程序
		
	}

	@Override
	public void configInterceptor(Interceptors arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	//配置插件
	public void configPlugin(Plugins plugins) {
		// MyCat连接信息
		String url="jdbc:mysql://localhost:6066/vaehome";
		String username="admin";
		String password="abc123456";
		
		DruidPlugin druid=new DruidPlugin(url,username,password);//数据库连接池
		plugins.add(druid);//把数据库连接池添加到Jfinal
		
		//配置SQL语句执行的插件
		ActiveRecordPlugin arp=new ActiveRecordPlugin(druid);
		//注册sql文件
		arp.setBaseSqlTemplatePath(PathKit.getRootClassPath()+"/cn/xusong/vaehome/sql");//SQL文件根目录
		arp.addSqlTemplate("user.sql");
		plugins.add(arp);
		
		//配置redis
		RedisPlugin redis=new RedisPlugin("redis", "localhost", 6379, 2000,"abc123456",1);
		plugins.add(redis);
	}

	@Override
	//给类配置网络地址
	public void configRoute(Routes routes) {
		routes.add("/user",UserController.class);
		
	}



}
