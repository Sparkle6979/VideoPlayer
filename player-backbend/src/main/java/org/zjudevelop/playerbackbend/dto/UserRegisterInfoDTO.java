package org.zjudevelop.playerbackbend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisterInfoDTO implements Serializable {

    private Long id;

    private String username;

}
