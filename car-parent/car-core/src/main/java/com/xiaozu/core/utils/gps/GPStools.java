package com.xiaozu.core.utils.gps;

import java.util.List;

/**
 * @Author:80906
 * @Des:
 * @Date:2019/3/23
 */
public class GPStools {

    public static boolean isPolygonContainsPoint(List<Point> mPoints, Point point) {
        int nCross = 0;
        for (int i = 0; i < mPoints.size(); i++) {
            Point p1 = mPoints.get(i);
            Point p2 = mPoints.get((i + 1) % mPoints.size());
            if (p1.getY() == p2.getY())
                continue;
            if (point.getY() < Math.min(p1.getY(), p2.getY()))
                continue;
            if (point.getY() >= Math.max(p1.getY(), p2.getY()))
                continue;
            double x = (point.getY() - p1.getY()) * (p2.getX() - p1.getX()) / (p2.getY() - p1.getY()) + p1.getX();
            if (x > point.getX()) // 当x=point.x时,说明point在p1p2线段上
                nCross++; // 只统计单边交点
        }
        // 单边交点为偶数，点在多边形之外 ---
        return (nCross % 2 == 1);
    }

}
