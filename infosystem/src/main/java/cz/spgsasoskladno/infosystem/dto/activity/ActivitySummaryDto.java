package cz.spgsasoskladno.infosystem.dto.activity;

import java.time.LocalDate;

public record ActivitySummaryDto(String name,
                                 String description,
                                 Integer minPoints,
                                 Integer maxPoints,
                                 LocalDate dueDate) {
}
