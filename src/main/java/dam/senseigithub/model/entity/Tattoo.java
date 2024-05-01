package dam.senseigithub.model.entity;

import java.time.LocalDateTime;
import java.util.Arrays;

public class Tattoo {
    private int idTattoo;
    private Client client;
    private Design design;
    private byte[] finishedImage;
    private float price;
    private String type;
    private String status;

    public Tattoo(int idTattoo, Client client, Design design, byte[] finishedImage, float price, String type, String status) {
        this.idTattoo = idTattoo;
        this.client = client;
        this.design = design;
        this.finishedImage = finishedImage;
        this.price = price;
        this.type = type;
        this.status = status;
    }

    public Tattoo() {

    }

    public int getIdTattoo() {
        return idTattoo;
    }

    public void setIdTattoo(int idTattoo) {
        this.idTattoo = idTattoo;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Design getDesign() {
        return design;
    }

    public void setDesign(Design design) {
        this.design = design;
    }

    public byte[] getFinishedImage() {
        return finishedImage;
    }

    public void setFinishedImage(byte[] finishedImage) {
        this.finishedImage = finishedImage;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Tattoo{" +
                "idTattoo=" + idTattoo +
                ", client=" + client +
                ", design=" + design +
                ", finishedImage=" + Arrays.toString(finishedImage) +
                ", price=" + price +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
