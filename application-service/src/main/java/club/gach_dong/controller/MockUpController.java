package club.gach_dong.controller;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequiredArgsConstructor
public class MockUpController {

    @GetMapping("/authTest")
    public Boolean testAuth(@RequestParam Long userId, int applyId, HttpServletRequest httpServletRequest) {
        System.out.println("통신 체결");
        String headerValue = httpServletRequest.getHeader("reqq-id");
        System.out.println("reqq-id: " + headerValue);
        System.out.println("UserID: " + userId);
        System.out.println("applyId: " + applyId);
        return true;
    }

//    @DeleteMapping("/test")
//    public void deleteApply() {
//        objectStorageService.deleteObject("ApplicationDocs", "testImg");
//    }
}
