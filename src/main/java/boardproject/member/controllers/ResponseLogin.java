package boardproject.member.controllers;


import lombok.Builder;

@Builder
public record ResponseLogin(
        String accessToken
) {
}
