package br.com.grupo63.techchallenge.common.domain;

import br.com.grupo63.techchallenge.common.domain.validation.group.Update;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class Entity implements Serializable {

    @NotNull(message = "order.create.idNotNull", groups = {Update.class})
    @Min(value = 1, message = "order.create.idNotNull", groups = {Update.class})
    protected Long id;

    protected boolean deleted = false;

    public void delete() {
        this.deleted = true;
    }

}
