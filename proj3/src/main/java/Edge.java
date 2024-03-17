public class Edge{
    private Long id;
    private int maxspeed;
    private String name;
    private boolean valid;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public int getMaxspeed() {return this.maxspeed;}
    public void setMaxspeed(int maxspeed) {this.maxspeed = maxspeed;}
    public String getName() {return this.name;}
    public void setName(String name) {this.name = name;}
    public boolean getValid() {return this.valid;}
    public void setValid(boolean valid) {this.valid = valid;}
}