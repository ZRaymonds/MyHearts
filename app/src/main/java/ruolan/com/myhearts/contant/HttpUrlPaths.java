package ruolan.com.myhearts.contant;


/**
 * Created by Administrator on 2016/10/20.
 * <p>
 * 该项目使用的数据接口   接口来源于抓取获取到的，禁止用于任何商业用途，如果有侵犯他人的接口
 * 请告知我删除。
 */
public class HttpUrlPaths {

    public static final String BASE_URL = "http://api.xinliji.me/";

    /**
     * userid=54442&cuserid=54442
     */
    public static final String USER_DETAIL_INFO = BASE_URL + "consultant/consultantProfile";

    //首页轮播图  type=consultant
    public static final String HOME_BANNER = BASE_URL + "com/loadBanner_v2";

    //首页推荐咨询师   http://api.xinliji.me/consultant/loadRecommendedConsultants
    public static final String HOME_RECOMMENT = BASE_URL + "consultant/loadRecommendedConsultants";

    //首页跑马灯    http://api.xinliji.me/com/loadActionLabels
    public static final String HOME_MARQUEE = BASE_URL + "com/loadActionLabels";

    //查看更多   userid=54442   分页加载  page
    public static final String SCAN_MORE = BASE_URL + "psychology/loadency";

    //新闻详情  userid=54442   id
    public static final String NEWS_USER_DETAIL = BASE_URL + "psychology/encydetail";

    //新闻webview  userid=54442   id
    public static final String NEWS_DETAIL = "http://console.xinliji.me/psychology/encypage";


    //首页  推荐两个
    public static final String HOME_RECOMMENT_ONE_BANNER = BASE_URL + "multimedia/recommandLiveShow";
    public static final String HOME_RECOMMENT_TWO_BANNER = BASE_URL + "psychology/dailyExam";

    //首页我要倾诉  咨询url
    public static final String HOME_POUR_OUT_CONSULT_URL=BASE_URL+"consultant/loadWebcallConsultants";


    //tag  http://api.xinliji.me/consultant/loadTags  分类
    public static final String ALL_CATEGORY_TAG = BASE_URL + "consultant/loadTags";

    //http://api.xinliji.me/consultant/loadFreeWebcallConsultants  免费体验
    public static final String FREE_TRIAL = BASE_URL + "consultant/loadFreeWebcallConsultants";

    //情感测试
    public static final String AFFECTIVE_TEST = BASE_URL + "psychology/randomExams";


    //咨询列表     userid=0
    public static final String SERACH_LIST = BASE_URL + "consultant/searchConsultants";

    //http://api.xinliji.me/social/loadGroupCatgs  群主分类
    public static final String LORD_CATEGORY = BASE_URL + "social/loadGroupCatgs";

    //http://api.xinliji.me/social/loadRecommendGroups  群组的两个轮播图
    public static final String LORD_BANNER = BASE_URL + "social/loadRecommendGroups";

    // 心事   userid = 54442
    //labelid=0&type=1&page=1&userid=0  最新
    //labelid=0&type=2&page=1&userid=0

    //labelid=2&type=1&page=1&userid=0
    //labelid=1&type=1&page=1&userid=0  其他
    public static final String THOUGHTS_URL = BASE_URL + "social/nearuserevents_v3";


    //page=1&userid=0&searchtype=0  亲子教育
    //catgId=1&page=1&userid=0 情绪困扰
    //catgId=4&page=1&userid=0 青春成长
    //catgId=5&page=1&userid=0
    //catgId=1&page=2&userid=0
    public static final String LORD_DETAIL_URL = BASE_URL + "social/loadGroupsByCatg";

    //?userid=0&groupid=309   群组详情
    public static final String LORD_GROUP_DETAIL_URL = BASE_URL+"social/groupinfo";

    //群成员url   page=1&groupid=309
    public static final String LORD_MEMBER_DETAILE_URL = BASE_URL +"social/groupmembers";

    //群成员详情 cuserid=0&userid=112730
    public static final String LORD_GROUP_MEMBER_DETAIL ="user/profile?cuserid=0&userid=112730";

    //直播url
    public static final String DOUYU_API = "http://capi.douyucdn.cn/api/v1/";


    /**
     * lol  dota2  炉石
     *
     * @param channelTag
     * @return
     */
    public static String getDouyuSubChannelBaseTag(int channelTag, int limit, int offset) {
        return DOUYU_API + "live/" + channelTag + "?&limit=" + limit + "&offset=" + offset;
    }

    /**
     * 推荐直播api
     *
     * @return
     */
    public static String getDouyuLiveChannel(int limit, int offset) {
        return DOUYU_API + "live?&limit=" + limit + "&offset=" + offset;
    }


    /**
     * 所有api
     *
     * @return
     */
    public static String getDouyuAllSubChannels() {
        return DOUYU_API + "getColumnDetail?shortName=game";
    }
}
