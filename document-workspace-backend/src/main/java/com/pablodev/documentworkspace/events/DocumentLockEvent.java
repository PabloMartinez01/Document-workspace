package com.pablodev.documentworkspace.events;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DocumentLockEvent {
    private Long id;
    private boolean lock;
}
