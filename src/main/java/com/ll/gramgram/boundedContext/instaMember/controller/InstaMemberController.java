package com.ll.gramgram.boundedContext.instaMember.controller;

import com.ll.gramgram.base.file.FileUploadUtil;
import com.ll.gramgram.base.rq.Rq;
import com.ll.gramgram.base.rsData.RsData;
import com.ll.gramgram.boundedContext.instaMember.entity.InstaMember;
import com.ll.gramgram.boundedContext.instaMember.service.InstaMemberService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Controller
@RequestMapping("/instaMember")
@RequiredArgsConstructor
@Slf4j
public class InstaMemberController {
    private final Rq rq;
    private final InstaMemberService instaMemberService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/connect")
    public String showConnect() {
        return "usr/instaMember/connect";
    }

    @AllArgsConstructor
    @Getter
    public static class ConnectForm {
        @NotBlank
        @Size(min = 4, max = 30)
        private final String username;
        @NotBlank
        @Size(min = 1, max = 1)
        private final String gender;

    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/connect")
    public String connect(@Valid ConnectForm connectForm,
                          @RequestParam("profileImage") MultipartFile image) throws IOException {
        String imageName = "";
        if (!image.isEmpty()) {
            FileUploadUtil.saveFile(image);
            imageName = image.getOriginalFilename();
        }

        RsData<InstaMember> rsData = instaMemberService.connect(rq.getMember(), connectForm.getUsername(), connectForm.getGender(),imageName);

        if ( rsData.isFail() ) {
            return rq.historyBack(rsData);
        }

        return rq.redirectWithMsg("/likeablePerson/add", "인스타그램 계정이 연결되었습니다.");
    }
}
