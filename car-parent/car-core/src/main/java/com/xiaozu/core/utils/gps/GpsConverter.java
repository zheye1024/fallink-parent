package com.xiaozu.core.utils.gps;

/*
	众所周知，为了国家安全，国内的各种在线地图都根据天朝测绘局标准统一对经纬度作了纠偏处理。
即所谓的GCJ-02坐标系，而百度更是在此基础上进行了二次加密形成了BD-09坐标系。

1、	WGS-84 坐标系 转 GCJ-02坐标系 纠偏算法，即GPS真实经纬度转为Google地图经纬度。
由于Google、高德、soso等地图在国内均采用GCJ-02坐标系，此算法使用于这些地图。

2、 GCJ-02坐标系 转 BD-09坐标系 纠偏算法，即Google地图经纬度转百度地图经纬度。
上面已说到，百度地图是在GCJ-02坐标系基础上进行二次加密的，所以此算法适用于百度地图。

导航中GPS位置与地图匹配、道路匹配算法
http://blog.csdn.net/viewcode/article/details/7918721
http://bbs.csdn.net/topics/391546138?page=1
http://blog.jobbole.com/88993/ 【十分】
http://lbs.amap.com/api/javascript-api/example/overlayers/polyline-circle-polygon
http://www.gpsvisualizer.com/tutorials/track_filters.html //轨迹简化
https://en.wikipedia.org/wiki/Ramer%E2%80%93Douglas%E2%80%93Peucker_algorithm
https://developers.google.com/maps/documentation/roads/intro
https://roads.googleapis.com/v1/snapToRoads?path=【解决归路问题】
*/

/**
 * 坐标转换
 */
public class GpsConverter {
    static double pi = 3.14159265358979324;
    static double a = 6378245.0;
    static double ee = 0.00669342162296594323;
    public final static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;

    /**
     * 判断是否在中国
     * @param lat 纬度
     * @param lon 经度
     * @return
     */
    public static boolean outofChina(double lat, double lon) {
        if (lon < 72.004 || lon > 137.8347)
            return true;
        if (lat < 0.8293 || lat > 55.8271)
            return true;
        return false;
    }

    /**
     * wgs转百度，原始gps转百度坐标系
     * @param lat 纬度
     * @param lon 经度
     * @return
     */
    public static double[] wgs2bd(double lat, double lon) {
        double[] wgs2gcj = wgs2gcj(lat, lon);
        double[] gcj2bd = gcj2bd(wgs2gcj[0], wgs2gcj[1]);
        return gcj2bd;
    }

    /**
     * google、高德转百度
     * @param lat 纬度
     * @param lon 经度
     * @return
     */
    public static double[] gcj2bd(double lat, double lon) {
        double x = lon, y = lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
        double bd_lon = z * Math.cos(theta) + 0.0065;
        double bd_lat = z * Math.sin(theta) + 0.006;
        return new double[] { bd_lat, bd_lon };
    }

    /**
     * 百度转高德\google
     * @param lat 纬度
     * @param lon 经度
     * @return
     */
    public static double[] bd2gcj(double lat, double lon) {
        double x = lon - 0.0065, y = lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        double gg_lon = z * Math.cos(theta);
        double gg_lat = z * Math.sin(theta);
        return new double[] { gg_lat, gg_lon };
    }

    /**
     * wgs转高德、google
     * @param lat 纬度
     * @param lon 经度
     * @return
     */
    public static double[] wgs2gcj(double lat, double lon) {
        if (outofChina(lat, lon)) {
            return  new double[]{lat,lon};
        }
        double dLat = transformLat(lon - 105.0, lat - 35.0);
        double dLon = transformLon(lon - 105.0, lat - 35.0);
        double radLat = lat / 180.0 * pi;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
        double mgLat = lat + dLat;
        double mgLon = lon + dLon;
        double[] loc = { mgLat, mgLon };
        return loc;
    }

    /**
     * 84 to 火星坐标系 (GCJ-02) World Geodetic System ==> Mars Geodetic System
     *
     * @param lat
     * @param lon
     * @return
     */
    public static double[] gps84_To_Gcj02(double lat, double lon) {
        if (outofChina(lat, lon)) {
            return  new double[]{lat,lon};
        }
        double dLat = transformLat(lon - 105.0, lat - 35.0);
        double dLon = transformLon(lon - 105.0, lat - 35.0);
        double radLat = lat / 180.0 * pi;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
        double mgLat = lat + dLat;
        double mgLon = lon + dLon;
        double[] loc = { mgLat, mgLon };
        return loc;
    }

    private static double transformLat(double lat, double lon) {
        double ret = -100.0 + 2.0 * lat + 3.0 * lon + 0.2 * lon * lon + 0.1 * lat * lon + 0.2 * Math.sqrt(Math.abs(lat));
        ret += (20.0 * Math.sin(6.0 * lat * pi) + 20.0 * Math.sin(2.0 * lat * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(lon * pi) + 40.0 * Math.sin(lon / 3.0 * pi)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(lon / 12.0 * pi) + 320 * Math.sin(lon * pi  / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    private static double transformLon(double lat, double lon) {
        double ret = 300.0 + lat + 2.0 * lon + 0.1 * lat * lat + 0.1 * lat * lon + 0.1 * Math.sqrt(Math.abs(lat));
        ret += (20.0 * Math.sin(6.0 * lat * pi) + 20.0 * Math.sin(2.0 * lat * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(lat * pi) + 40.0 * Math.sin(lat / 3.0 * pi)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(lat / 12.0 * pi) + 300.0 * Math.sin(lat / 30.0 * pi)) * 2.0 / 3.0;
        return ret;
    }
}
