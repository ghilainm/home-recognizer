package com.home.recognizer.boundary;

import com.home.recognizer.entities.Image;
import com.home.recognizer.repositories.ImageRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/house/image")
public class HomeRecognizerService {

    @Value("${house.images.directory}")
    private String houseImagesDirectory;

    @Autowired
    private ImageRepository imageRepository;

    @GetMapping
    public String displayImages(Model model){
        model.addAttribute("images", imageRepository.findAll());
        return "upload-house-image";
    }

    @PostMapping
    public String postHouseImage(@RequestParam("file") MultipartFile houseImage) throws IOException {
        Image image = new Image();
        image.setOriginalName(houseImage.getOriginalFilename());
        String extension = FilenameUtils.getExtension(houseImage.getOriginalFilename());
        image.setExtension(extension);
        Path destinationFolder = Paths.get(houseImagesDirectory).resolve(image.getId());
        Files.createDirectories(destinationFolder);
        Files.write(destinationFolder.resolve(image.getOriginalName()), houseImage.getBytes());
        imageRepository.save(image);
        return "redirect:/house/image";
    }

}
