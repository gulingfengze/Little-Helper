package bean;

import java.util.List;

/**
 * Created by Lenovo on 2016/8/9.
 * Description 新闻数据封装.
 */
public class NewsCenterData {


/*2.原始新闻数据*/
    public int retcode;

    public List<NewsData> data;//新闻的数据(LeftMenuFragment.java类中,NewsCenterBaseTagPage.java类中)

    public class NewsData {
        public List<ViewTagData> children;//新闻页签（NewsBaseNewsCenterPage.java类中）

        public class ViewTagData {//新闻中的页签的数据
            public String id;
            public String title;
            public int type;
            public String url;
        }

        public String id;

        public String title;
        public int type;


        public String url;
        public String url1;

        public String dayurl;
        public String excurl;

        public String weekurl;
    }

    public List<String> extend;
}