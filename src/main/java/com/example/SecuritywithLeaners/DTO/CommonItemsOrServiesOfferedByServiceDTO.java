package com.example.SecuritywithLeaners.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class CommonItemsOrServiesOfferedByServiceDTO {
    private Long itemID;
    private String itemName;
    private String itemType;
}
