public class FavouritesCats {
    String id;
    String image_id;
    String apiKey = "fa7d922f-4c76-4085-8be6-4131e1db8434";
    CatImage image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public CatImage getImage() {
        return image;
    }

    public void setImage(CatImage image) {
        this.image = image;
    }
}
