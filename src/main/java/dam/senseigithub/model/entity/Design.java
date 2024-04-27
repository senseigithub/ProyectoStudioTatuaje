package dam.senseigithub.model.entity;

import java.util.Arrays;

public class Design {
    private int idDesign;
    private String name;
    private byte[] imagen;
    private float size;

    public Design(int idDesign, String name, byte[] imagen, float size) {
        this.idDesign = idDesign;
        this.name = name;
        this.imagen = imagen;
        this.size = size;
    }

    public Design() {

    }

    public int getIdDesign() {
        return idDesign;
    }

    public void setIdDesign(int idDesign) {
        this.idDesign = idDesign;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Design{" +
                "idDesign=" + idDesign +
                ", name='" + name + '\'' +
                ", imagen=" + Arrays.toString(imagen) +
                ", size=" + size +
                '}';
    }
}
