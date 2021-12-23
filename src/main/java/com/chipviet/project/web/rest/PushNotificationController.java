package com.chipviet.project.web.rest;

import com.chipviet.project.domain.Device;
import com.chipviet.project.domain.User;
import com.chipviet.project.repository.DeviceRepository;
import com.chipviet.project.repository.UserRepository;
import com.chipviet.project.service.PushNotificationService;
import com.chipviet.project.service.dto.PushNotificationDTO;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Transactional
public class PushNotificationController {

    private final PushNotificationService pushNotificationService;
    private final DeviceRepository deviceRepository;
    private final UserRepository userRepository;

    public PushNotificationController(
        PushNotificationService pushNotificationService,
        DeviceRepository deviceRepository,
        UserRepository userRepository
    ) {
        this.pushNotificationService = pushNotificationService;
        this.deviceRepository = deviceRepository;
        this.userRepository = userRepository;
    }

    @PostMapping(value = "/v1/push-notification")
    public String pushNotification(HttpServletRequest request, @RequestBody PushNotificationDTO pushNotificationDTO) {
        String success = "Successfully";
        try {
            PushNotificationService.sendMessageToAllUsers("Hello minh la Chip Viet");
            return success;
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping(value = "/v1/push-notification/specific-user")
    public String pushNotificationToSpecificUser(HttpServletRequest request, @RequestBody PushNotificationDTO pushNotificationDTO) {
        String success = "Successfully";
        Optional<User> user = userRepository.findOneByPhoneNumber(pushNotificationDTO.getPhoneNumber());
        List<Device> devices = deviceRepository.findByUser(user.get());
        //        Long a = 1;
        try {
            //            PushNotificationService.sendMessageToUser(a,pushNotificationDTO.getNotification(), devices, user);
            return success;
        } catch (Exception e) {
            throw e;
        }
    }
}
