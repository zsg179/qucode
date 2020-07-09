package com.zhusg02.qucode.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.zhusg02.qucode.vo.RespTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

@RestController
public class QRCodeController {

    private static final String QR_CODE_IMAGE_PATH = "E:/qrcodes/";
    @CrossOrigin(allowCredentials = "true", allowedHeaders = "*", methods = { RequestMethod.POST, RequestMethod.GET,
            RequestMethod.HEAD, RequestMethod.PUT, RequestMethod.PATCH }, origins = "*")
    @PostMapping("/qrcode")
    public RespTemplate getQRCode(@RequestBody String businessNumber,@RequestBody String projectName) {
        JSONObject jsonb = new JSONObject();
        RespTemplate res = new RespTemplate();
        System.out.println(businessNumber);
        String filePath = QR_CODE_IMAGE_PATH+businessNumber+".png";
        System.out.println(filePath);
        /*byte[] qrcode = null;
        try {
            qrcode = getQRCodeImage("https://mi.szpisp.cn/szsb/?salesmanCode=TKPC" + businessNumber, 360, 360);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return null;*/

        //本地生成图片
        try {
            generateQRCodeImage("https://mi.szpisp.cn/szsb/?salesmanCode=TKPC" + businessNumber, 350, 350, filePath);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        res.setCode("200");
        res.setMessage("success");
        res.setData(filePath);
        return res;
    }

    private static void generateQRCodeImage(String text, int width, int height, String filePath) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(filePath);

        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

    }

   /* public static byte[] getQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();
        return pngData;
    }*/

}
