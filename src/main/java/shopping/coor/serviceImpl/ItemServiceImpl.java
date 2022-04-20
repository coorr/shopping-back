package shopping.coor.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shopping.coor.model.Image;
import shopping.coor.model.Item;
import shopping.coor.payload.request.ItemRequestDto;
import shopping.coor.repository.ImageRepository;
import shopping.coor.repository.ItemRepository;
import shopping.coor.service.ItemService;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final ImageRepository imageRepository;

    @Override
    public List<Item> getItem() {
        return itemRepository.getItem();
    }

    @Override
    @Transactional
    public List<Item> insertItemAll(MultipartFile[] multipartFiles, String itemData) throws  Exception{
        System.out.println("multipartFiles = " + multipartFiles);
        System.out.println("item ="+ itemData);
        String UPLOAD_PATH = "src/main/resources/static/";

        try {
            // 객체는 client에서 직렬화를 하여 전달
            Item itemDto = new ObjectMapper().readValue(itemData, Item.class); // String to Object
            System.out.println("itemDto= " + itemDto);
            Item itemId = itemRepository.save(itemDto);
            Image image = new Image();

            for(int i=0; i<multipartFiles.length; i++) {
                MultipartFile file = multipartFiles[i];

                String fileId = (new Date().getTime()) + "" + (new Random().ints(1000, 9999).findAny().getAsInt()); // 현재 날짜와 랜덤 정수값으로 새로운 파일명 만들기
                String originName = file.getOriginalFilename(); // ex) 파일.jpg
                String fileExtension = originName.substring(originName.lastIndexOf(".") + 1); // ex) jpg
                originName = originName.substring(0, originName.lastIndexOf(".")); // ex) 파일
                long fileSize = file.getSize(); // 파일 사이즈

                String absolutePath = new File("").getAbsolutePath() + "\\";
                File fileSave = new File(absolutePath + UPLOAD_PATH, fileId + "." + fileExtension); // ex) fileId.jpg
//                if(!fileSave.exists()) { // 폴더가 없을 경우 폴더 만들기
//                    fileSave.mkdirs();
//                }

                file.transferTo(fileSave); // fileSave의 형태로 파일 저장

                System.out.println("fileId= " + fileId);
                System.out.println("originName= " + originName);
                System.out.println("fileExtension= " + fileExtension);
                System.out.println("fileSize= " + fileSize);


                image.setLocation(fileId+ "." +fileExtension);
                image.setItem(itemId);
            }

        } catch(IOException e) {
            System.out.println(e);
        }


        return itemRepository.getItem();

    }

    @Override
    public void orderItem(String itemId) {
        if (itemId.equals("ex")) {
            throw new IllegalStateException("예외 발생!");
        }
    }
}
