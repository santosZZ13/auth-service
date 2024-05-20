package org.authen.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RedirectController {

	@GetMapping("/api/redirect")
	public Map<String, String> redirectSuccess(@RequestParam(value = "access_token", required = false) String accessToken,
											   @RequestParam(value = "refresh_token", required = false) String refreshToken) {
		return Map.of("access_token", accessToken, "refresh_token", refreshToken);
	}
}
