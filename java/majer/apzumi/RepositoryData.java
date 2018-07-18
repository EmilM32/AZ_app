package majer.apzumi;

public class RepositoryData {
    private String image;
    private String name;
    private String description;
    private String rep;

    public RepositoryData(String name, String image, String description, String rep) {
        this.image = image;
        this.name = name;
        this.description = description;
        this.rep = rep;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRep() {
        return rep;
    }

    public void setRep(String rep) {
        this.rep = rep;
    }
}