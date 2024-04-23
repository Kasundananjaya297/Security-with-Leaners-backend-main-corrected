package com.example.SecuritywithLeaners.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommonItemsOrServiesOfferedByService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemID;
    private String itemName;
    private String itemType;
    @OneToMany(mappedBy = "itemID")
    private List<ItemsORDone> itemsORDones;
}
