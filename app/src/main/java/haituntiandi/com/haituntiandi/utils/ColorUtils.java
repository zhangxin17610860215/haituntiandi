package haituntiandi.com.haituntiandi.utils;

/**
 * 仅和本工程项目使用的工具方法写在此处
 *
 * @author dsj
 * @version V1.0
 */
public class ColorUtils {

    private static final String TAG = "ColorUtils";

    /**
     * @param positionColor 获取对应位置的颜色
     * @return 返还需要的颜色
     */
    public static int getStockColors(int positionColor) {

        // 第一个颜色值黄色
        int[] colors = {
                0xF5D323, 0xFF0056FF, 0xFF24DC4E, 0xFFAB52B1
        };

        if (positionColor >= colors.length) {
            return colors[0];
        }

        return colors[positionColor];
    }

}
