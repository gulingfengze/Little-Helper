package utils;

/**
 * Created by Lenovo on 2016/7/16.
 * Description 存储常量.
 */
public interface MyConstants {
    String CONFIGFILE = "cacheValue";//SP配置文件名
    String ISSETUP = "isSetup";//是否设置过向导界面
     int DEBUGLEVEL = LogUtils.LEVEL_ALL;//显示所有日志
    //public static final int DEBUGLEVEL = LogUtils.LEVEL_OFF;//关闭所有日志


    /*配置服务地址:
    * 1.GenyMotion模拟器访问地址http://10.0.3.2:8080/
    * 2.自带模拟器访问地址http://10.0.2.2:8080/
    * */
        //获取新闻数据接口地址(聚合数据接口)
    //String NEWSURL = "http://v.juhe.cn/toutiao/index?type=top&key=6de9aa7b535e4b595d6616d73cd4348a";
    String NEWSURL = "http://10.0.3.2:8080/zhbj/categories.json";//获取新闻数据接口地址
    String SERVERURL = "http://10.0.3.2:8080/zhbj";

    String NEWSTOUTIAOURL="http://v.juhe.cn/toutiao/index?type=top&key=6de9aa7b535e4b595d6616d73cd4348a";

}
