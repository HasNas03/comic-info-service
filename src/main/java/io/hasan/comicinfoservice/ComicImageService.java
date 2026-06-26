package io.hasan.comicinfoservice;

import io.hasan.comicinfoservice.models.Comic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.UUID;

@Service
public class ComicImageService {

    private static final double TARGET_HEIGHT_TO_WIDTH_RATIO = 1.5;

    private final ComicRepository comicRepository;
    private final Path uploadDir;

    // 1. CONSTRUCTOR
    public ComicImageService(ComicRepository comicRepository, @Value("${app.upload-dir:uploads}") String uploadDir) {
        // constructor argument reads from aapplication.properties to find save path for images

        this.comicRepository = comicRepository;
        this.uploadDir = Path.of(uploadDir) // turns the string "uploads" into a file path
                .toAbsolutePath() // makes it an absolute path : "/home/app/uploads"
                .normalize(); // collapses the "../" segments for security
    }

    public Comic saveImage(UUID comicId, MultipartFile image) {
        // get comic object that the cover image will represent
        Comic comic = getComic(comicId);
        validateImage(image);

        try {
            // create /uploads/comics/ folder if it doesn't exist
            Files.createDirectories(comicsDirectory());

            // decode the raw bytes into a Java image object
            BufferedImage original = ImageIO.read(image.getInputStream());
            if (original == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "file must be a readable image");
            }

            // crop to 2:3 comic book ratio
            BufferedImage cropped = cropToTargetRatio(original);
            // convert to JPEG-compatible format
            BufferedImage jpg = convertToRgb(cropped);

            // if comic already has an image, delete the old file first (meaning this save is a PUT to replace image, not POST)
            deleteExistingImageFile(comic);

            // generate local path by appending prefix/suffix
            String relativePath = "comics/" + comicId + ".jpg";
            // merge to full path: "/uploads/comics/abc-123.jpg"
            Path destination = uploadDir.resolve(relativePath).normalize();
            // write the image file to disk at that absolute/full path
            ImageIO.write(jpg, "jpg", destination.toFile());

            // save the path string to the Comic object attribute
            comic.setComicImagePath(relativePath);
            // save the new Comic object (with comicImagePath value) to DB
            return comicRepository.save(comic);
        }
        catch (IOException exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "could not store image");
        }
    }

    public Resource getImage(UUID comicId) {
        // get the comic from DB (to read its image path)
        Comic comic = getComic(comicId);
        // null/empty value check
        if (comic.getComicImagePath() == null || comic.getComicImagePath().isBlank()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comic image not found");}
        //append full path to value stored in DB (turns "comics/abc-123.jpg" → "/uploads/comics/abc-123.jpg)
        Path imagePath = uploadDir.resolve(comic.getComicImagePath()).normalize();
        // wraps the file path into a Spring Resource object - this is what streams the bytes to the client
        Resource resource = new FileSystemResource(imagePath);
        // handle edge case of DB had a path but the file is not actually on disk
        if (!resource.exists() || !resource.isReadable()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comic image path in DB, but not found on disk");}
        // controller streams this as raw bytes (not JSON)
        return resource;
    }

    public void deleteImage(UUID comicId) {
        // get the Comic object to get the image file path
        Comic comic = getComic(comicId);
        // attempt to delete image
        deleteExistingImageFile(comic);
        // if deleted, set Comic attribute value for image to null
        comic.setComicImagePath(null);
        // save new updated Comic object
        comicRepository.save(comic);
    }

    // ----------------------------------------- HELPERS -----------------------------------------

    // helper to get comic Object from DB
    private Comic getComic(UUID comicId) {
        return comicRepository.findById(comicId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comic not found"));
    }

    // checks the image file is real and is actually an image (not a .exe renamed to .jpg)
    private void validateImage(MultipartFile image) {
        if (image == null || image.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "image is required");
        }
        String contentType = image.getContentType();
        if (contentType == null || !contentType.toLowerCase(Locale.ROOT).startsWith("image/")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "file must be an image");
        }
    }

    // Crops image to 1.5:1 height/width ratio (standard comic book cover)
    // If image is too tall — crops height. If too wide — crops width. Centers the crop.
    private BufferedImage cropToTargetRatio(BufferedImage original) {
        int width = original.getWidth();
        int height = original.getHeight();
        double currentRatio = (double) height / width;

        int cropWidth = width;
        int cropHeight = height;

        if (currentRatio > TARGET_HEIGHT_TO_WIDTH_RATIO) {
            cropHeight = (int) Math.round(width * TARGET_HEIGHT_TO_WIDTH_RATIO);
        } else if (currentRatio < TARGET_HEIGHT_TO_WIDTH_RATIO) {
            cropWidth = (int) Math.round(height / TARGET_HEIGHT_TO_WIDTH_RATIO);
        }

        int x = (width - cropWidth) / 2;
        int y = (height - cropHeight) / 2;

        return original.getSubimage(x, y, cropWidth, cropHeight);
    }

    // JPEGs don't support transparency (alpha channel)
    // This converts the image to plain RGB so ImageIO.write() doesn't corrupt it
    private BufferedImage convertToRgb(BufferedImage image) {
        BufferedImage rgb = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = rgb.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics.drawImage(image, 0, 0, null);
        graphics.dispose();
        return rgb;
    }

    // deletes the old image file before saving a new one (called on update and delete (null path))
    private void deleteExistingImageFile(Comic comic) {
        if (comic.getComicImagePath() == null || comic.getComicImagePath().isBlank()) {
            return;
        }

        try {
            Files.deleteIfExists(uploadDir.resolve(comic.getComicImagePath()).normalize());
        } catch (IOException exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "could not delete image");
        }
    }

    // use .resolve to merge paths and get the /uploads/comics/ directory path cleanly
    private Path comicsDirectory() {
        return uploadDir.resolve("comics");
    }
}
