package com.example.splitwise.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SettleUpGroupRequestDTO {
    private Long groupId;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
