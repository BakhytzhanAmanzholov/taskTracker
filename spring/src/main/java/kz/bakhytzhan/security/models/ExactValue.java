package kz.bakhytzhan.security.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
// This is a model, with the help of this model we can receive this data in json format and apply it to the Controller
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExactValue {
    private int value;
    private Long id;
}
