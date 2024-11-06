package club.gach_dong.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import club.gach_dong.annotation.RequestUserReferenceId;
import club.gach_dong.service.AdminService;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    public ResponseEntity<String> getClubName(@RequestUserReferenceId String userReferenceId) {
        String clubName = adminService.getClubNameByUserReferenceId(userReferenceId);
        return new ResponseEntity<>(clubName, HttpStatus.OK);
    }

    public ResponseEntity<String> transferClub(
            @RequestParam String targetUserReferenceId,
            @RequestUserReferenceId String userReferenceId) {

        String result = adminService.transferClub(userReferenceId, targetUserReferenceId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public ResponseEntity<String> generateInviteCode(@RequestUserReferenceId String userReferenceId) {
        String inviteCode = adminService.generateInviteCode(userReferenceId);
        return new ResponseEntity<>(inviteCode, HttpStatus.CREATED);
    }

    public ResponseEntity<String> registerInviteCode(@RequestParam String clubName, @RequestParam String inviteCode) {
        boolean success = adminService.registerInviteCode(clubName, inviteCode);

        if (success) {
            return new ResponseEntity<>("동아리에 성공적으로 가입되었습니다.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("초대코드가 유효하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
    }
}
