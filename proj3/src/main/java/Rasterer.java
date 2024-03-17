import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    private static final int FinalDepth = 7;
    private static final double MAX_LonDPP = 0.00034332275390625;
    public static final double ROOT_ULLAT = 37.892195547244356, ROOT_ULLON = -122.2998046875,
            ROOT_LRLAT = 37.82280243352756, ROOT_LRLON = -122.2119140625;

    public Rasterer() {
        // YOUR CODE HERE
    }

    // Pair contains a pair of coordinate <x, y>
    private class Pair{
        int x;
        int y;
        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Pair(){}

        private void setX(int x){
            this.x = x;
        }
        private void setY(int y){
            this.y = y;
        }
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
//         System.out.println(params);
        Map<String, Object> results = new HashMap<>();
//        System.out.println("Since you haven't implemented getMapRaster, nothing is displayed in "
//                           + "your browser.");
        double lrlon = params.get("lrlon");
        double ullon = params.get("ullon");
        double ullat = params.get("ullat");
        double lrlat = params.get("lrlat");
        double w = params.get("w");
        double h = params.get("h");
        if(!check(lrlon, ullon, ullat, lrlat)) {
            results.put("query_success", false);
            return results;
        }

        // Find the proper depth
        double LonDPP = (lrlon - ullon) / w;
        int depth = FindDepth(LonDPP);
        Pair lr = TargetTile(lrlon, lrlat, depth);
        Pair ul = TargetTile(ullon, ullat, depth);

        String[][] grid = GetFileNames(ul, lr, depth);
        results.put("render_grid", grid);
        results.put("raster_ul_lon", ROOT_ULLON + ul.x * (ROOT_LRLON - ROOT_ULLON)  / (1 << depth));
        results.put("raster_ul_lat", ROOT_ULLAT - ul.y * ((ROOT_ULLAT - ROOT_LRLAT) / (1 << depth)));
        results.put("raster_lr_lon", ROOT_ULLON + (lr.x + 1) * (ROOT_LRLON - ROOT_ULLON) / (1 << depth));
        results.put("raster_lr_lat", ROOT_ULLAT - (lr.y + 1) * ((ROOT_ULLAT - ROOT_LRLAT) / (1 << depth)));
        results.put("depth", depth);
        results.put("query_success", true);

        return results;
    }

    // versify the validity of the params
    private boolean check(double lrlon, double ullon, double ullat, double lrlat) {
        if(ullon >= lrlon || ullat <= lrlat) { return false; }
        if(ullon <= ROOT_ULLON || lrlon >= ROOT_LRLON || ullat >= ROOT_ULLAT || lrlat <= ROOT_LRLAT) { return false; }
        return true;
    }

    private int FindDepth(double LonDPP) {
        double ProperLonDPP = MAX_LonDPP;
        for(int i = 0; i < 7; i++) {
            if(ProperLonDPP <= LonDPP) {
                return i;
            }
            ProperLonDPP /= 2;
        }
        return FinalDepth;
    }

    private Pair TargetTile(double lon, double lat, int depth) {
        double lonDelta = (ROOT_LRLON - ROOT_ULLON)  / (1 << depth);
        double latDelta = (ROOT_ULLAT - ROOT_LRLAT)  / (1 << depth);
        Pair pos = new Pair();
        double nowlon = ROOT_ULLON;
        double nowlat = ROOT_ULLAT;
        for(int i = 0; i < (1 << depth); i++){
            if(nowlon <= lon && nowlon + lonDelta >= lon) {
                pos.setX(i);
                break;
            }
            nowlon += lonDelta;
        }
        for(int i = 0; i < (1 << depth); i++){
            if(nowlat >= lat && nowlat - latDelta <= lat) {
                pos.setY(i);
                break;
            }
            nowlat -= latDelta;
        }
        return pos;
    }

    private String[][] GetFileNames(Pair ul, Pair lr, int dep) {
        int row = lr.x - ul.x + 1;
        int col = lr.y - ul.y + 1;
        String[][] res = new String[col][row];
        for(int j = 0; j < col; j++){
            for(int i = 0; i < row; i++){
                res[j][i] = "d" + Integer.toString(dep) + "_x" + Integer.toString(i + ul.x) + "_y" + Integer.toString(j + ul.y) + ".png";
            }
        }
        return res;
    }
//    public static void main(String[] args) {
//        Map<String, Double> params = new HashMap<>();
//        params.put("ullon", -122.23995662778569);
//        params.put("ullat", 37.877266154010954);
//        params.put("lrlon", -122.22275132672245);
//        params.put("lrlat", 37.85829260830337);
//        params.put("w", 613.0);
//        params.put("h", 676.0);
//        double LonDPP = (params.get("lrlon") - params.get("ullon")) / params.get("w");
//        LonDPP = 0.00034332275390625;
//        int trueDep = 4;
//        System.out.println(FindDepth(LonDPP));
//        if(trueDep == FindDepth(LonDPP)) {
//            System.out.println("Yes");
//        }else {
//            System.out.println("No");
//        }
//    }
}
