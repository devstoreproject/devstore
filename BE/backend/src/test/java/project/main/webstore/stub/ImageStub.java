package project.main.webstore.stub;

import project.main.webstore.domain.image.entity.Image;

import java.util.ArrayList;
import java.util.List;

public class ImageStub {
    private String fileName = "testImage";
    private String ext = "png";
    private String path = "src/test/resources/image/testImage";
    public Image createImage(String originalName, String filePath, String ext, int order, boolean representative) {
        return new Image(originalName, "업로드 네임", filePath, ext, filePath, order, representative, "해쉬 코드가 들어올 자리");
    }

    public List<Image> createImageList(int idx) {
        List<Image> list = new ArrayList<>();
        for(int i = 0 ; i < idx ; i ++){
            boolean representative = i == 1;
            list.add(createImage(fileName + i, path + i + ext,ext,i, representative));
        }
        return list;
    }
}
