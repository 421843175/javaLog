package jupiter;

public enum Tip {
    MESSAGE("信息"),
    WARRING("警告"),
    ERROR("错误"),
    EXCEPTION("异常");
    private final String v;
    private Tip(String v){
        this.v=v;
    }

    @Override
    public String toString() {
        return v;
    }
}
