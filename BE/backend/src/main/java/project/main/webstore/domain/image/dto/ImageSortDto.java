package project.main.webstore.domain.image.dto;

public interface ImageSortDto {
    Long findImageId();
    int getOrderNumber();
    void setOrderNumber(int orderNumber);
    boolean isRepresentative();
    void setRepresentative(boolean representative);
}
