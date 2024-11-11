package club.gach_dong.controller;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MockUpController {

    @GetMapping("/authTest")
    public Boolean testAuth(@RequestParam String userId, int applyId, HttpServletRequest httpServletRequest) {
        System.out.println("통신 체결");
        String headerValue = httpServletRequest.getHeader("reqq-id");
        System.out.println("reqq-id: " + headerValue);
        System.out.println("UserID: " + userId);
        System.out.println("applyId: " + applyId);
        return true;
    }

    @GetMapping("/validApplyTest/{applyId}")
    public Boolean testAuth(@PathVariable Long applyId, HttpServletRequest httpServletRequest) {
        System.out.println("통신 체결 apply is valid");
        String headerValue = httpServletRequest.getHeader("reqq-id");
        System.out.println("reqq-id: " + headerValue);
        System.out.println("applyId: " + applyId);
        return true;
    }

//    @DeleteMapping("/test")
//    public void deleteApply() {
//        objectStorageService.deleteObject("ApplicationDocs", "testImg");
//    }
}
