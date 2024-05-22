package BangDuLieu;

public class DanhMuc {
    int ID;
    String Name;

    public DanhMuc(){}

    public DanhMuc(int ID, String name) {
        this.ID = ID;
        Name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public String toString() {
        return "DanhMuc{" +
                "ID=" + ID +
                ", Name='" + Name + '\'' +
                '}';
    }
}
