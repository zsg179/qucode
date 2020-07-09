package com.zhusg02.qucode.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.zhusg02.qucode.utils.QRCodeUtil;
import com.zhusg02.qucode.vo.RespTemplate;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Map;

@RestController
public class QRCodeController {

    private static final String QR_CODE_IMAGE_PATH = "E:/svn/repository/core-app/qrcode/src/assets/";

    @CrossOrigin(allowCredentials = "true", allowedHeaders = "*", methods = { RequestMethod.POST, RequestMethod.GET,
            RequestMethod.HEAD, RequestMethod.PUT, RequestMethod.PATCH }, origins = "*")
    @PostMapping("/qrcode")
    public RespTemplate getQRCode(@RequestBody Map map) {
        RespTemplate res = new RespTemplate();
        String businessNumber = (String) map.get("businessNumber");
        String filePath = QR_CODE_IMAGE_PATH+businessNumber+".png";

        //本地生成图片
        try {
            generateQRCodeImage("https://mi.szpisp.cn/szsb/?salesmanCode=TKPC" + businessNumber, 350, 350, filePath);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*try {
            QRCodeUtil.encode("https://localhost:8082/toView/?url=https://mi.szpisp.cn/szsb/?salesmanCode=TKPC" + businessNumber
            , null,filePath,businessNumber+".jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        res.setCode("200");
        res.setMessage("success");
        res.setData(businessNumber);
        return res;
    }

    @CrossOrigin(allowCredentials = "true", allowedHeaders = "*", methods = { RequestMethod.POST, RequestMethod.GET,
            RequestMethod.HEAD, RequestMethod.PUT, RequestMethod.PATCH }, origins = "*")
    @PostMapping("/toView")
    public RespTemplate toView(String url) {
        System.out.println(url);
        RespTemplate res = new RespTemplate();
        return res;
    }

    private static void generateQRCodeImage(String text, int width, int height, String filePath) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(filePath);

        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

    }

}
