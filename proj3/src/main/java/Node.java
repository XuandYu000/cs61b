import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: XuZhiyu
 * @Date: 2024/3/17 下午4:42
 */
public class Node {
    private Long id;
    private Double lon;
    private Double lat;
    private String location;
    private List<Long> neighbors = new ArrayList<>();
    private List<Long> edges = new ArrayList<>();


    public Node(Long id, Double lon, Double lat) {
        this.id = id;
        this.lon = lon;
        this.lat = lat;
    }

    public void connect(Long nid) {
        neighbors.add(nid);
    }

    public void setEdge(Long rid) {
        edges.add(rid);
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getId() {return this.id;}
    public Double getLon() {return this.lon;}
    public Double getLat() {return this.lat;}
    public List<Long> getNeighbors() {return neighbors;}
    public List<Long> getEdges() { return edges;}
}
