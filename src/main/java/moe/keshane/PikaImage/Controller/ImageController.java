package moe.keshane.PikaImage.Controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("image/")
public class ImageController {

    @RequestMapping("raw/{imageName}")
    @ResponseBody
    public String getRawImage(@PathVariable("imageName") String imageName){
        return "raw:"+imageName;
    }

    @RequestMapping("thumbnail/{imageName}")
    @ResponseBody
    public String getThumbnailImage(@PathVariable("imageName") String imageName){
        return "thumbnail:"+imageName;
    }

}
