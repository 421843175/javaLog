package jupiter;

public enum Tip {
    MESSAGE("��Ϣ"),
    WARRING("����"),
    ERROR("����"),
    EXCEPTION("�쳣");
    private final String v;
    private Tip(String v){
        this.v=v;
    }

    @Override
    public String toString() {
        return v;
    }
}
