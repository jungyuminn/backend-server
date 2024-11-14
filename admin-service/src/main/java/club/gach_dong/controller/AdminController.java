package club.gach_dong.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import club.gach_dong.annotation.RequestUserReferenceId;
import club.gach_dong.api.AdminApiSpecification;
import club.gach_dong.dto.response.InviteCodeRegisterResponse;
import club.gach_dong.dto.response.InviteCodeResponse;
import club.gach_dong.entity.InviteCode;
import club.gach_dong.exception.UserException;
import club.gach_dong.service.AdminService;

@RestController
@RequiredArgsConstructor
public class AdminController implements AdminApiSpecification {
    private final AdminService adminService;

    @Override
    public ResponseEntity<InviteCodeResponse> createInviteCode(
            @RequestUserReferenceId String userReferenceId,
            @RequestParam Long clubId) {
        if (userReferenceId.isEmpty()) {
            throw new UserException.UserNotFound();
        }

        InviteCode inviteCode = adminService.generateInviteCode(userReferenceId, clubId);
        InviteCodeResponse response = InviteCodeResponse.from(inviteCode);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<InviteCodeRegisterResponse> registerInviteCode(
            @RequestParam String inviteCode,
            @RequestUserReferenceId String userReferenceId) {

        if (userReferenceId.isEmpty()) {
            throw new UserException.UserNotFound();
        }

        InviteCode registeredCode = adminService.registerInviteCode(inviteCode, userReferenceId);
        InviteCodeRegisterResponse response = InviteCodeRegisterResponse.from(registeredCode, userReferenceId);

        return ResponseEntity.ok(response);
    }
}