package ru.ekbtreeshelp.auth.entity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        allowableValues = { "local", "fb", "vk" },
        type = "string"
)
public enum Provider {
    local,
    fb,
    vk
}
