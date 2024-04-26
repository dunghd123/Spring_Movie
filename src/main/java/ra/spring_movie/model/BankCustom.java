package ra.spring_movie.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankCustom {
    private String bankName;
    private String bankNumber;
    private String ownCardName;
    private double payMoney;
}
