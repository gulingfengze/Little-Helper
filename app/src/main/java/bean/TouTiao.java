package bean;

import java.util.List;

/**
 * Created by Lenovo on 2016/9/10.
 * Description 新闻头条.
 */
public class TouTiao {
    /*1.新闻头条数据*/
    public int error_code;
    public String reason;
    public NewsCenterData_Data result;
    public class NewsCenterData_Data{
        public String stat;
        public List<NewData> data;
        public class NewData{
            public String author_name;
            public String date;
            //public String realtype;
            public String thumbnail_pic_s;
            public String thumbnail_pic_s02;
            public String thumbnail_pic_s03;
            public String title;
            //public String type;
            public String uniquekey;
            public String url;
        }

    }
}
